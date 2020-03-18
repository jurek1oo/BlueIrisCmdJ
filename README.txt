# blueiriscmdj under GNU gpl-2.0 licence --- https://opensource.org/licenses/gpl-2.0.php

This is Java port of blueiriscmd Python software written by Magnus Appelquist (https://github.com/magapp/blueiriscmd). 
I have used Python code as my template, and have upgraded code to run on Python 3.8.
Python version is in doc/python directory. Blue Iris has to have web server enabled and configured, to be accessible on local lan.
On BlueIris you need to create user with password access to the web server from the local lan.

Python code has been ported to Java (hence blueiriscmdj), to run on my Raspberry pi 3, to control my Blue Iris (BI) system.
I am not sure if I fully understand the BI operation, hence the "doc/notes_issues_list.txt".
I control camera activity (recording) setting different profiles (AtHome and OnTheRoad).
The class HelperSetProfile is used for integration with another Java code running on raspberry PI.

You need to change login and other system parameters to make the unit test works,
in Constants4Tests.calss. You may need to create this class, as it may not be in git. The template is attached below.
I am using InteliJ Idea community edition, as ide. To develop code on Raspberry I use Netbeans 8.2  on win10, with scripted upload of jars to PI for execution. A fully automated process. The only issue is that killing process on win10 Netbeans, dos not stop pi process. So you end up with multiple java processes running on raspberry. You need to kill them manually with pkill java, on pi. 

To make executable jar, cd to project directory with pom.xml and execute in the terminal window:

mvn package -Dmaven.test.skip  

This will compile and build snapshot jar, without running tests.
Or you can use the existing jar in /executablejar directory.

For more info check the project Github wiki.

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
java -jar blueiriscmdj-x.x.x_SNAPSHOT-jar-with-dependencies.jar -help

For more, check the project wiki.
Have fun and let me know if you have issues.

Jurek 2020-March-06

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
