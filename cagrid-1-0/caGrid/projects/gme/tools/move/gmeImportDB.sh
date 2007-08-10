#!/bin/sh

#
# The argument to this script is the basefile name. 
# This script is designed to work when there is no password on database.  If there is
#   a password on the database just add a "-p <password>" after the "-u root" line
#   of the mysqldump call.  The "root" username for the database can also be changed.
#



databases="GlobusGME_GME_REGISTRY GlobusGME_GME_SCHEMA_STORE GlobusGME_GME_SCHEMA_CACHE"

for database in $databases ; do

echo Importing database ${database}

gunzip ${database}.sql.gz

mysql -u root ${database} < ${backupdir}/${database}.sql

rm -fr ${backupdir}/${database}.sql

done

echo Finished Importing databases

exit 