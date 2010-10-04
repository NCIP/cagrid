---- INFO

This zip and jar contain a pacthed version of Purls documentation jar, version 1.6.1

---- ISSUE
see https://jira.citih.osumc.edu/browse/CAGRID-496

---- DETAILS
(from the linked issue)
All current releases of the PURL server in the v1.x.x range contain a
bug resulting in broken images. This message contains directions to
repair an existing PURL server without requiring a re-installation. A
new PURL server release repairing this problem will be available as
soon as possible.


BACKGROUND:

A bug in the file utilpurls.js contains three absolute URLs pointing
to the PURL community site instead of referencing images from the PURL
server itself. The broken links are:

http://purlz.org/images/edit.png
http://purlz.org/images/history.png
http://purlz.org/images/tearoff_icon.png

These links became inoperative when the PURL community site moved to
Google Code recently. Thanks to Karen Sieger for pointing out this
embarrassing problem!


REPAIR:

To repair an existing PURL server, take the following steps in order:

1. Download the files to be added.

utilpurl.js: http://groups.google.com/group/persistenturls/web/utilpurl.js
edit.png: http://groups.google.com/group/persistenturls/web/edit.png
history.png: http://groups.google.com/group/persistenturls/web/history.png
tearoff_icon.png: http://groups.google.com/group/persistenturls/web/tearoff_icon.png


2. Find the mod-purl-documentation module and unzip it.

[[
$ cd <install_dir>/modules
$ mkdir tmp
$ cd tmp
$ # NB: The next line assumes you are using PURL server version
1.6.3.
$ # Adjust the version number as needed.
$ unzip ../mod-purl-documentation-1.6.3.jar
]]


3. Add the missing images to the module's images directory.

[[
$ cp <path_to_image>/edit.png resources/images/
$ cp <path_to_image>/history.png resources/images/
$ cp <path_to_image>/tearoff_icon.png resources/images/
]]


4. Fix the broken hyperlinks.

[[
$ cp -i <path_to_file>/utilpurls.js resources/javascripts/utilpurl.js
]]


5. Re-create the module's JAR file with the new material.

NB: You may also leave the module's content unzipped and serve it
directly from its location on the file system. If you prefer to do
that, change the entry for mod-purl-documentation in <install_dir>/etc/
deployedModules.xml, otherwise follow the directions below.

[[
$ # Remember to change the version number of your module file as
needed.
$ mv ../mod-purl-documentation-1.6.3.jar ../mod-purl-
documentation-1.6.3.jar_orig
$ zip -r ../mod-purl-documentation-1.6.3.jar *
]]


6. Restart the PURL server.

7. Remove the temporary files when tested.

Our sincere apologies for this nasty bug! 
