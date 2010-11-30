#!/usr/bin/env perl

use POSIX qw(getcwd);
use strict;
use Getopt::Long;

my $help = 0;
my $rc = 0;
my $globus_location = $ENV{GLOBUS_LOCATION};

if (! -d "$ENV{GLOBUS_LOCATION}")
{
    die "You need to set GLOBUS_LOCATION in your environment\n";
}


GetOptions('help' => \$help);

if ($help) {
    print STDERR "Usage: $0 [-help]\n";
    exit 1;
}


chdir "$globus_location";

### Start the container ###

my $rc_1 = 0;

my $pid_1 = open (CONT_1, "$globus_location/bin/globus-start-container -nosec 2>&1 |")
    or die "Error: unable to start the container";
while (<CONT_1>)
{
    if (/Starting SOAP server/)
    {
        $rc_1 = 1;
        last;
    }

    print;
}

if ($rc_1 == 0) {
    close(CONT_1);
    die "Failed to start the container";
}


### Run tests ###    

my $rc = 0;

open (ANT, "ant -f $globus_location/share/globus_wsrf_test/runtests.xml runServer generateTestReport -Dtests.jar=$globus_location/lib/wsrf_test_interop.jar 2>&1 |")
    or die "Error: unable to run ant";
while (<ANT>)
{
    if (/BUILD FAILED/)
    {
        $rc = 1;
    }

    print;
}
close(ANT);


### Stop the container ### 
kill(15, $pid_1);
while (<CONT_1>)
{
}
close(CONT_1);

### Exit out ###
exit($rc);
