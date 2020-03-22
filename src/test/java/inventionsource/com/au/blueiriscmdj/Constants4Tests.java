package inventionsource.com.au.blueiriscmdj;

public class Constants4Tests {
    //
    //TODO set here your own system values
    //

    // Cam Ceiling1 is inside the house, active in EXPECTED_Profile2 OnTheRoad, not active AtHome
    public static final String CAM_NAME1 = "Ceiling1";
    // Cam Front-Watashi is outside the house, always active in EXPECTED_Profile1 and  EXPECTED_Profile2
    // This cam is used in the trigger tests, as cam1  will want work, when not active.
    public static final String CAM_NAME2 = "Front-Watashi";

    public static final String EXPECTED_Schedule1 = "Default";

    // AtHome:
    // CAM_NAME1 - not active, CAM_NAME2 is active
    public static final String EXPECTED_Profile1 = "AtHome";
    // OnTheRoad:
    // CAM_NAME1 - is active and CAM_NAME2 is active
    public static final String EXPECTED_Profile2 = "OnTheRoad";

    // BlueIris user, password and host Ip:Port
    public static final String USER = "admin";
    public static final String PASSWORD = "password";
    public static final String HOST = "192.168.1.42:8882";
}
