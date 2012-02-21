/*
 * Portions of this file Copyright 1999-2005 University of Chicago
 * Portions of this file Copyright 1999-2005 The University of Southern California.
 *
 * This file or a portion of this file is licensed under the
 * terms of the Globus Toolkit Public License, found at
 * http://www.globus.org/toolkit/download/license.html.
 * If you redistribute this file, with or without
 * modifications, you must include this notice in the file.
 */

#include "globus_common.h"

#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>

#define CONTAINER_EXECUTABLE "bin/globus-start-container"
#define CONTAINER_LOG_FILE   "var/container.log"
#define CONTAINER_PID_FILE   "var/container.pid"
#define BUFFER_SIZE          500


static const char * help =
"Usage: globus-stop-container-detached\n"
"\n"
"Stops the running container by sending signals to the pid in\n"
"$GLOBUS_LOCATION/var/container.pid\n";


typedef struct timeout_context_s
{
    globus_mutex_t      mutex;
    globus_cond_t       cond;
    int                 done;
} timeout_context_t;


void
globus_i_timeout_callback(void *args);

int
globus_i_wait_for_container_shutdown(pid_t pid);


void
globus_i_timeout_callback(void *user_arg)
{
    timeout_context_t * context = (timeout_context_t *)user_arg;

    globus_mutex_lock(&context->mutex);
    {
        context->done = 1;
        globus_cond_signal(&context->cond);
    }
    globus_mutex_unlock(&context->mutex);
}


int
globus_i_wait_for_container_shutdown(pid_t pid)
{
    timeout_context_t   timeout_context;
    globus_reltime_t    timeout;
    int                 tries = 0;
    int                 status = 0;

    /* give the container at the most 25 5sec checks to finish shutdown
     * the container is configured to wait 2 minutes to cleanup */
    while (tries < 10) {
        /* wait some time and see if the container has shutdown */
        globus_mutex_init(&timeout_context.mutex, NULL);
        globus_cond_init(&timeout_context.cond, NULL);
        timeout_context.done = 0;
        GlobusTimeReltimeSet(timeout, 5, 0);
        globus_mutex_lock(&timeout_context.mutex);
        {
            globus_callback_register_oneshot(NULL,
                    &timeout,
                    &globus_i_timeout_callback,
                    &timeout_context);

            while (timeout_context.done == 0) {
                globus_cond_wait(&timeout_context.cond,
                        &timeout_context.mutex);
            }
        }
        globus_mutex_unlock(&timeout_context.mutex);

        if (kill(pid, 0) != 0) {
            /* container has finished shutdown */
            return 0;
        }

        tries++;
    }
    return 1;
}


int main(int argc, char *argv[])
{
    char *            globus_location = GLOBUS_NULL;
    char              full_path[512];
    char              buffer[BUFFER_SIZE];
    int               length;
    pid_t             pid;
    int               fd;
    int               rc;
    int               exit_code = 1;  /* assume failure */

    /* any arguments means -help */
    if (argc > 1) {
        fputs(help, stderr);
        exit(1);
    }

    globus_location = getenv("GLOBUS_LOCATION");
    if (globus_location == NULL || strlen(globus_location) == 0)
    {
        fprintf(stderr, "GLOBUS_LOCATION needs to be set\n");
        exit(1);
    }

    rc = globus_module_activate(GLOBUS_COMMON_MODULE);
    if (rc != GLOBUS_SUCCESS)
    {
        fprintf(stderr, "Error initializing Globus components\n");

        goto out;
    }

    strcpy(full_path, globus_location);
    strcat(full_path, "/");
    strcat(full_path, CONTAINER_PID_FILE);

    fd = globus_libc_open(full_path, O_RDONLY);
    if (fd < 0)
    {
        fprintf(stderr, "Unable to open PID file (%s)\n", full_path);
        rc = 2;
        goto deactivate_out;
    }
    length = globus_libc_read(fd, buffer, BUFFER_SIZE-1);
    if (length < 0)
    {
        fprintf(stderr, "Unable to read PID file content (%s)\n", full_path);
        rc = 3;
        goto deactivate_out;
    }
    globus_libc_close(fd);
    buffer[length] = '\0';
    pid = atoi(buffer);

    /* remove pid file to indicating no container is running */
    unlink(full_path);

    printf("Stopping Globus container. PID: %d\n", pid);
    if (kill(pid, SIGTERM) != 0) 
    {
        fprintf(stderr, "Stopping container failed\n");
        rc = 3;
        goto deactivate_out;
    }

    if (globus_i_wait_for_container_shutdown(pid) != 0) {
        /* container is still alive, give SIGKILL */
        fprintf(stderr, "Container did not shut down cleanly. "
                        "Sending KILL signal.\n");
        kill(pid, SIGKILL);
    }

    printf("Container stopped\n");
    exit_code = 0;

deactivate_out:
    globus_module_deactivate_all();
out:
    return exit_code;
}
/* main() */

