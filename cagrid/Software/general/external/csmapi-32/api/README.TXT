This project should be built with JDK 5, NOT JDK 6 - it's supposed to be backwards compatible with some older software that in turn is built and runs on Java 5, so nice things like Deque don't exist yet :(.

Builds exist on the caGrid quality dashboard which can be used to generate artifacts to drop in the caGrid Ivy repository.
http://quality.cagrid.org/job/caGrid-CSMAPI-3.2-Matrix/

-- Building --
cd script
ant clean dist
... artifacts land in dist

A 'test' ant target has been added to the build script.  Many tests (the ones the caGrid team wrote, in particular) pass without any additional magic.  Many more, however, do not.  Use for informational purposes only. 