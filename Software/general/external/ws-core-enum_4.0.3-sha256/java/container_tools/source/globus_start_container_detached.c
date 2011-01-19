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
#include <sys/types.h>
#include <sys/wait.h>


#define STARTUP_WAIT_SECS    10
#define CONTAINER_EXECUTABLE "bin/globus-start-container"
#define CONTAINER_LOG_FILE   "var/container.log"
#define CONTAINER_PID_FILE   "var/container.pid"
#define BUFFER_SIZE          500

#define ERR_GENERAL            1
#define ERR_PID_FILE           2
#define ERR_CONTAINER_RUNNING  3
#define ERR_CONTAINER_DIED     4
#define ERR_GLOBUS_LOCATION    5


static const char * help =
"Usage: globus-start-container-detached -help | [arguments to container]\n"
"\n"
"Starts the WSRF Java container in the background. Logging goes to\n"
"$GLOBUS_LOCATION/var/container.log and a PID file is written to\n"
"$GLOBUS_LOCATION/var/container.pid so that globus-stop-container-detached\n"
"can be used to stop the container.\n"
"\n"
"Arguments to globus-start-container-detached are just passed on\n"
"to globus-start-container. Please run\n"
"\n"
"     globus-start-container -help\n"
"\n"
"for full help.\n";


typedef struct timeout_context_s
{
    globus_mutex_t      mutex;
    globus_cond_t       cond;
    int                 done;
} timeout_context_t;


void
globus_i_timeout_callback(void *args);

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


int main(int argc, char *argv[])
{
    char *                              globus_location = GLOBUS_NULL;
    char                                globus_start_container[] =
                                        "globus-start_container\0";
    char                                executable_path[512];
    char                                logfile_path[512];
    char                                pidfile_path[512];
    char                                buffer[50];
    struct stat                         stat_info;
    timeout_context_t                   timeout_context;
    globus_reltime_t                    timeout;
    pid_t                               pid;
    int                                 length;
    int                                 current = 0;
    int                                 status = 0;
    int                                 fd;
    int                                 rc;

    /* only check for -help, all other args goes to globus-start-container */
    for (current = 1; current < argc; current++) {
        if (strcmp(argv[current], "-help") == 0) {
            fputs(help, stderr);
            rc = ERR_GENERAL;
            goto out;
        }
    }

    globus_location = getenv("GLOBUS_LOCATION");
    if (globus_location == NULL || strlen(globus_location) == 0)
    {
        fprintf(stderr, "GLOBUS_LOCATION needs to be set\n");
        rc = ERR_GLOBUS_LOCATION;
        goto out;
    }

    rc = globus_module_activate(GLOBUS_COMMON_MODULE);
    if (rc != GLOBUS_SUCCESS)
    {
        fprintf(stderr, "Error initializing Globus components\n");
        rc = ERR_GENERAL;
        goto out;
    }

    /* if a pid file exists a container is probably already running
     * check to see if a process with such a pid is running */
    strcpy(pidfile_path, globus_location);
    strcat(pidfile_path, "/");
    strcat(pidfile_path, CONTAINER_PID_FILE);
    if (stat(pidfile_path, &stat_info) == 0) {
        fd = globus_libc_open(pidfile_path, O_RDONLY);
        if (fd < 0)
        {
            fprintf(stderr, "Unable to open PID file (%s)\n", pidfile_path);
            rc = ERR_PID_FILE;
            goto deactivate_out;
        }
        length = globus_libc_read(fd, buffer, BUFFER_SIZE-1);
        if (length < 0)
        {
            fprintf(stderr, 
                    "Unable to read PID file content (%s)\n",
                    pidfile_path);
            rc = ERR_PID_FILE;
            goto deactivate_out;
        }
        globus_libc_close(fd);
        buffer[length] = '\0';
        pid = atoi(buffer);

        status = kill(pid, 0);
        if (status == 0 || errno == EPERM) {
            fprintf(stderr,
                    "ERROR: "
                    " A container with pid %d is already running\n",
                    pid);
            rc = ERR_CONTAINER_RUNNING;
            goto deactivate_out;
        }
        else {
            fprintf(stderr, "Stale pid file detected. It will be removed\n");
            if (unlink(pidfile_path) != 0) {
                 fprintf(stderr, "ERROR: Unable to remove "
                                 "$GLOBUS_LOCATION/container.pid");
                 rc = ERR_PID_FILE;
                 goto deactivate_out;
            }
        }
    }

    pid = fork();
    if (pid > 0)
    {
        /* parent */
        printf("Starting Globus container. PID: %d\n", pid);

        /* wait some time and make sure the child is still alive
         * if not warn the user that something went wrong */
        globus_mutex_init(&timeout_context.mutex, NULL);
        globus_cond_init(&timeout_context.cond, NULL);
        timeout_context.done = 0;
        GlobusTimeReltimeSet(timeout, STARTUP_WAIT_SECS, 0);
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

        if (waitpid(pid, &status, WNOHANG) > 0) {
            fprintf(stderr,
                    "WARNING: It seems like the container died directly\n"
                    "         Please see $GLOBUS_LOCATION/var/container.log"
                    " for more information\n");
            rc = ERR_CONTAINER_DIED;
            goto deactivate_out;
        }
        else {
            /* create a pid file */
            fd = globus_libc_open(pidfile_path, O_RDWR|O_CREAT|O_TRUNC, 0644);
            if (fd < 0)
            {
                fprintf(stderr, 
                        "Unable to open PID file (%s)\n",
                        pidfile_path);
                rc = ERR_PID_FILE;
                goto deactivate_out;
            }
            globus_libc_sprintf(buffer, "%d\n", pid);
            write(fd, buffer, globus_libc_strlen(buffer));
            globus_libc_close(fd);
            globus_module_deactivate_all();
            return 0;
        }

        goto deactivate_out;
    }
    else
    {
        /* child */

        /* get a new process group - no need to check error, as the only
           failure is if we are already the proceess group leader */
        setsid();

        /* set the umask */
        umask(022);

        /* close all fds */
        for (fd = getdtablesize(); fd>=0; --fd)
        {
            close(fd);
        }

        strcpy(logfile_path, globus_location);
        strcat(logfile_path, "/");
        strcat(logfile_path, CONTAINER_LOG_FILE);

        /* stdin  */
        fd = globus_libc_open("/dev/null", O_RDWR);
        /* stdout and stderr */
        fd = globus_libc_open(logfile_path, O_RDWR|O_CREAT|O_TRUNC, 0644);
        dup(fd);

        rc = chdir(globus_location);
        if (rc != GLOBUS_SUCCESS)
        {
            fprintf(stderr, "Could not cd to $GLOBUS_LOCATION.\n");
            rc = ERR_GLOBUS_LOCATION;
            goto deactivate_out;
        }

        strcpy(executable_path, globus_location);
        strcat(executable_path, "/");
        strcat(executable_path, CONTAINER_EXECUTABLE);

        argv[0] = globus_start_container;

        rc = execv(executable_path, &argv[0]);
        if (rc != GLOBUS_SUCCESS)
        {
            fprintf(stderr, "Unable to execute %s\n", executable_path);
            rc = ERR_GENERAL;
            goto deactivate_out;
        }
    }

    /* success */
    rc = 0;

deactivate_out:
    globus_module_deactivate_all();
out:
    return rc;
}
/* main() */

