include $(top_srcdir)/globus_automake_pre
include $(top_srcdir)/globus_automake_pre_top

SUBDIRS= \
	pkgdata 

INCLUDES = $(GPT_INCLUDES)

sbin_PROGRAMS = globus-start-container-detached globus-stop-container-detached

globus_start_container_detached_SOURCES = globus_start_container_detached.c \
                                          version.h
globus_start_container_detached_LDADD = $(GPT_PGM_LINKS)

globus_stop_container_detached_SOURCES = globus_stop_container_detached.c \
                                         version.h
globus_stop_container_detached_LDADD = $(GPT_PGM_LINKS)

EXTRA_DIST= version.h.in

include $(top_srcdir)/globus_automake_post
include $(top_srcdir)/globus_automake_post_top

