
To build caGrid, type:
	ant clean all

	To find out other targets available, and type:
	ant -p

	To find out targets available in each project, cd to the project's
	directory (projects/*), and type:
	ant -p
	
	More information can be found on the project Wiki:
	http://www.cagrid.org/mwiki/index.php?title=CaGrid:How-To:Build


DIRECTORIES
========================================
antfiles		The shared/common antfiles should go here
projects		Each subproject should go in here as a top-level subdirectory and share a common layout and build process
share			Common location for shared libraries.
test			Location for testing code/configuration/libraries.

FILES
========================================
build.xml			This is the main build process that manages all of the project to project dependencies, and has a call-thru ability to build any subproject
project.properties	This is the main project properties file which controls aspects of the build for caGrid projects.
 
For more information, refer to the Documentation module of the repository.  Specifically the Documentation/docs/BuildProcess directory.


CVS TAGS
========================================
caGrid_Annual_Meeting_06 = Code used to demonstrate at caBIG Annual Meeting April 10th
caGrid-1_0_beta_final = Code released for Beta release
caGrid-1_0_release_final = Code released for 1.0
caGrid-1_0_release_pointrelease_portal_r1 = First point release of 1.0 branch for portal updates
caGrid-1_1_release_final = Code released for 1.1