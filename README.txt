# blueiriscmdj under GNU gpl-2.0 licence --- https://opensource.org/licenses/gpl-2.0.php

This is Java port of blueiriscmd Python software written by Magnus Appelquist (https://github.com/magapp/blueiriscmd). 
I have used Python code as my template, and have upgraded code to run on Python 3.8. Python is in doc/python directory.
Python code has been ported to Java (hence blueiriscmdj), to run on my Raspberry pi 3, to control my Blue Iris (BI) system.
I am not sure if I fully understand the BI operation, hence the "doc/notes_issues_list.txt".
I control camera activity setting different profiles (AtHome and OnTheRoad).

You need to change login and other system parameters to make the unit test works,
in Constants4Tests.calss. You need to create this class, as it is not in git. The template is below:
I am using InteliJ Idea community edition, ide.

To make executable jar, cd to project directory with pom and execute in the terminal window:

mvn package -Dmaven.test.skip  

This will compile and build snapshot jar. 
Or you can use the existing jar.

This code is avaiable under GNU GPL 2 licence.  

Good luck, 

Jurek Kurianski jurek@inventionsource.com.au, Phuket Thailand, March 2020.

-----------------------------------------------------------------------------
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