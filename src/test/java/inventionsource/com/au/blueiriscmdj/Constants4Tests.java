package inventionsource.com.au.blueiriscmdj;

public class Constants4Tests {
    //
    //TODO set here your own system values
    //
    // BlueIris user, password and host Ip:Port
    public static  String USER =null;
    public static  String PASSWORD =null;
    public static  String HOST =null;
    public static  String LOG_LEVEL=null;
    public static  String LOG_FILE =null;

    // Cam Ceiling1 is inside the house, active in EXPECTED_Profile2 OnTheRoad, not active AtHome
    public static  String CAM_NAME1;
    // Cam Front-Watashi is outside the house, always active in EXPECTED_Profile1 and  EXPECTED_Profile2
    // This cam is used in the trigger tests, as cam1  will want work, when not active.
    public static  String CAM_NAME2 = null;

    public static  String SCHEDULE1 = null;

    // AtHome:
    // CAM_NAME1 - not active, CAM_NAME2 is active
    public static  int PROFILE1 =-1;
    // OnTheRoad:
    // CAM_NAME1 - is active and CAM_NAME2 is active
    public static  int  PROFILE2 =-1;

    public Constants4Tests() throws Exception{
        Config cfg = new Config("./config.properties");

        HOST = cfg.getProperty("HOST");
        USER = cfg.getProperty("USER");
        PASSWORD = cfg.getProperty("PASSWORD");
        LOG_LEVEL = cfg.getProperty("LOG_LEVEL");
        LOG_FILE = cfg.getProperty("LOG_FILE");

        CAM_NAME1 = cfg.getProperty("CAM_NAME1");
        CAM_NAME2 = cfg.getProperty("CAM_NAME2");
        SCHEDULE1 = cfg.getProperty("SCHEDULE1");

        try {
            PROFILE1 = Integer.parseInt(cfg.getProperty("PROFILE1"));
            PROFILE2 = Integer.parseInt(cfg.getProperty("PROFILE2"));
        } catch (Exception e) {
            throw new Exception("got: " + cfg.getProperty("PROFILE1") + " : " +
                    cfg.getProperty("PROFILE1") + " Error profile is integer 0-7. " + e.getMessage());
        }
    }
}
