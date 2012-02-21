Globus Toolkit 4.0.3 with patches for caGrid

=== PATCHES AND CHANGES ===

== WS-Enumeration Support
http://dev.globus.org/wiki/Java_WS_Core_WS_Enumeration

== Avoid forking 'id -u' on every call to a grid service
https://tracker.nci.nih.gov/browse/CAGRID-824
Changes to org.globus.util.ConfigUtil.getUID() to just return System.getProperty("user.name") instead of using Runtime.exec to invoke 'id -u', which doesn't work on non *nix platforms anyway and falls back to the system property in that case.

== Synchronized access to the trusted certificates dir
This version was patched to support synchronized access to the trusted certificates directory, necessary for syncgts to be able to modify the directory without causing errors in Globus's code that reads the directory.
