# blueiriscmdj under GNU gpl-2.0 licence --- https://opensource.org/licenses/gpl-2.0.php

This is Java port of blueiriscmd Python software written by Magnus Appelquist (https://github.com/magapp/blueiriscmd). 
I have used Python code as my template, and have upgraded code to run on Python 3.8.
Python version is in doc/python directory. Blue Iris have web server enabled and configured, to be accessible on local lan.
On BlueIris you need to create user with password access to the web server on the local lan.

Python code has been ported to Java (hence blueiriscmdj), to run on my Raspberry pi 3, to control my Blue Iris (BI) system.
I am not sure if I fully understand the BI operation, hence the "doc/notes_issues_list.txt".
I control camera activity (recording) setting different profiles (AtHome and OnTheRoad).
The class HelperSetProfile is used for integration with another Java code running on raspberry PI.

You need to change login and other system parameters to make the unit test works,
in Constants4Tests.calss. You need to create this class, as it may not be in git. The template is below:
I am using InteliJ Idea community edition, ide.

To make executable jar, cd to project directory with pom.xml and execute in the terminal window:

mvn package -Dmaven.test.skip  

This will compile and build snapshot jar, without running tests.
Or you can use the existing jar in /executablejar directory.

This code is avaiable under GNU GPL 2 licence.  

Good luck, 

Jurek Kurianski jurek@inventionsource.com.au, Phuket Thailand, March 2020.

-----------------------------------------------------------------------------
Use Examples
-----------------------------------------------------------------------------

Make sure you have Java installed in your path. The jar is build for Java 11.
You would need to change pom from 11 to lower version of Java if you need.
I got it working even with Java 8.
In comand terminal change to directory where /executablejar/ is and enter:

To get the list of all commands:
java -jar blueiriscmdj-1.1.53_SNAPSHOT-jar-with-dependencies.jar -help

To connect to BlueIris, in BI setting enable and configure web server with a user and password. To get BI profiles:
java -jar blueiriscmdj-1.1.50_SNAPSHOT-jar-with-dependencies.jar -host 192.168.1.42:8882 -u admin -p password --list-profiles

To enable debug logging to a file, and get all cameras parameters:
java -jar blueiriscmdj-1.1.50_SNAPSHOT-jar-with-dependencies.jar -host 192.168.1.42:8882 -u admin -p password --list-cams --logFile mylogFile.log --logLevel debug

Have fun and let me know if you have issues.

Jurek 2020-March-06

------------------------ help ----------------------------------

 -cd,--cam-disable <arg>      Camera disable: camera-short-name.
 -ce,--cam-enable <arg>       Camera enable: camera-short-name.
 -gcc,--get-camconfig <arg>   Get camera configuration: cam short name.
 -gs,--get-status             Get Blue Iris status: signal, active profile and
                              schedule
 -h,--help                    show help.
 -host,--host <arg>           Blue Iris host to connect to.
 -lc,--list-cams              List all avaiable cameras.
 -lf,--logFile <arg>          log file name, eg. /home/jurek/bi_log.log.
 -ll,--logLevel <arg>         Log leve; error, info, debug or trace.
 -lp,--list-profiles          List all available profiles.
 -lsch,--list-schedules       List all avaiable schedules.
 -p,--password <arg>          Password to use when connecting.
 -ptzb,--ptz-button <arg>     PTZ Button Number: ptz-button to send to cam
 -ptzc,--ptz-cam <arg>        PTZ camera-short-name to send PTZ Button Number:
                              ptz-button to.
 -sch,--set-schedule <arg>    Set current schedule: schedule name.
 -sp,--set-profile <arg>      Set current profile: profile name
 -ss,--set-signal <arg>       Set current signal.
 -t,--trigger <arg>           Trigger camera: camera-short-name.
 -u,--user <arg>              User to use when connecting.
----------------------------------

PTZ Numbers:
#0: Pan left#1: Pan right
#2: Tilt up
#3: Tilt down
#4: Center or home (if supported by camera)
#5: Zoom in
#6: Zoom out
#8..10: Power mode, 50, 60, or outdoor
#11..26: Brightness 0-15
#27..33: Contrast 0-6
#34..35: IR on, off
#101..120: Go to preset position 1..20

----------------------- change to your values -----------------------------------

package inventionsource.com.au.blueiriscmdj;
public class Constants4Tests {
    //
    //TODO set here your own system values
    //
    public static final String CAM_NAME1 = "Ceiling1";

    public static final String EXPECTED_Schedule1 = "Default";

    public static final String EXPECTED_Profile1 = "AtHome";
    public static final String EXPECTED_PROFILE_2 = "OnTheRoad";

    // BlueIris user, password and host Ip:Port
    public static final String USER = "admin";
    public static final String PASSWORD = "password";
    public static final String HOST = "192.168.1.42:8882";
}
----------------------------------------------------------------------------