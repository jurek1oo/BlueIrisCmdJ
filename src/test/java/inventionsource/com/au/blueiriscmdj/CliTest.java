package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CliTest
{
    private static final Logger log =(Logger) LogManager.getLogger(CliTest.class.getName());
        @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","info");
    }

    @Test
    public void empty() throws Exception
    {
        String[] args = new String[0];//no args
        Cli cli = new Cli(args);
        try {
            Cli.GoodOrBad gob = cli.parse();
            assertTrue( "gob.bad has text" ,gob.bad != null && gob.bad.length() > 0 );
            assertNull( "gob.good is null" ,gob.good );

        } catch (Exception e) {
            log.error( "Exception: " + e );
            throw e;
        }
    }
    @Test
    public void logLevel() throws Exception {
         try {
             LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
             loginParams.addElement("-ll");
             loginParams.addElement("error");
             String[] args = loginParams.getArgs();

             Cli cli = new Cli(args);

             Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has error: ", gob.good.indexOf("error") >= 0);

        } catch (Exception e) {
            log.error("Exception: " + e);
             throw e;
         }
    }
    @Test
    public void helpTest()  throws Exception{
        String[] args = new String[1];
        args[0] = "-h";// help
        Cli cli = new Cli(args);
        try {
            Cli.GoodOrBad gob = cli.parse();
            log.info(gob.good);
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has help: ", gob.good.indexOf("help") >= 0);

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }
    @Test
    public void profiles_listTest () throws Exception
    {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-pl");//profiles-list
            String[] args = loginParams.getArgs();
            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has is-profiles-list = true: ", cli.is_profiles_list());
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }
    @Test
    public void schedules_listTest () throws Exception {

        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-schl");//-schedules-list
            String[] args = loginParams.getArgs();
            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has is_schedules_list = true: ", cli.is_schedules_list());
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void Cams_ListTest() throws Exception {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-cl");//cams-list
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has cams-list: ", gob.good.indexOf("cams-list") >= 0);
            assertTrue("is cams-list: ", cli.is_cams_list());

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void TriggerTest() throws Exception {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-t");
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has trigger: ", gob.good.indexOf("trigger") >= 0);
            assertTrue("is get_trigger=CAM_NAME1: ", cli.get_trigger().compareTo(Constants4Tests.CAM_NAME1)==0);

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }


    @Test
    public void PtzCamPtzButtonTestOK()  throws Exception{
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-ptz-cam");
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            loginParams.addElement("-ptz-button");
            loginParams.addElement("1");
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has ptz-cam: ", gob.good.indexOf("ptz-cam") >= 0);
            assertTrue("has ptz-button: ", gob.good.indexOf("ptz-button") >= 0);
            assertTrue("is get_ptz-cam=CAM_NAME1: ", cli.get_ptzcam().compareTo(Constants4Tests.CAM_NAME1)==0);
            assertTrue("get_ptzbutton()>0 ", cli.get_ptzbutton()>0);

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void PtzCamPtzButtonTestOnePresent()  throws Exception{
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-ptz-cam");
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is Not empty", gob.bad!=null || gob.bad.length()>0);
            assertTrue("has ptz-cam: ", gob.good.indexOf("ptz-cam") >= 0);
            assertTrue("Missing ptz option\" ", gob.bad.indexOf("Missing one of ptz option. You need -ptz-cam and -ptz-button") >= 0);
            assertTrue("is get_ptz-cam=CAM_NAME1: ", cli.get_ptzcam().compareTo(Constants4Tests.CAM_NAME1)==0);


        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void PtzCamPtzButton_notIntegertest()  throws Exception{
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-ptz-cam");
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            loginParams.addElement("-ptz-button");
            loginParams.addElement("x");
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is NOT empty", gob.bad!=null || gob.bad.length()>0);
            assertTrue("ptz-button not an integer ", gob.bad.indexOf("ptz-button not an integer") >= 0);
            assertTrue("is get_ptz-cam=CAM_NAME1: ", cli.get_ptzcam().compareTo(Constants4Tests.CAM_NAME1)==0);

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void Alerts_ListEmptyCamNameTest() throws Exception {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-al");//alerts-list
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has alerts-list: ", gob.good.indexOf("alerts-list") >= 0);
            assertTrue("is_alerts_list: ", cli.is_alerts_list());

            assertNull("alerts_list : ", cli.get_alerts_list());
            assertNull("list_alerts_date()", cli.get_alerts_list_date());
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void Alerts_List4CamTest() throws Exception {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-al");//alerts-list 4 cam
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has alerts_list: ", gob.good.indexOf("alerts-list") >= 0);
            assertTrue("is_alerts_list: ", cli.is_alerts_list());

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void Alerts_ListDate_ButNoListAlertsTest() throws Exception {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-ald");//alerts-list-date
            loginParams.addElement("2020-03-25");
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad alerts-list-date has to be used with alerts-list option",
                    gob.bad!=null && gob.bad.indexOf("alerts-list-date has to be used with alerts-list option")>=0);

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }
    @Test
    public void AlertsDeleteTest() throws Exception {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-ad");//alerts-delete
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has alerts-delete: ", gob.good.indexOf("alerts-delete") >= 0);
            assertTrue("is_list_alerts: ", cli.is_alerts_delete());

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void CamConfig_GetTest() throws Exception
    {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-ccg");//camconfig-get
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has camconfig: ", gob.good.indexOf("camconfig") >= 0);
            assertTrue("get_camconfig=CAM_NAME1: ", cli.get_camconfig().compareTo(Constants4Tests.CAM_NAME1)==0);

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void CamConfig_SetTestFail() throws Exception
    {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-ccs");//camconfig-set
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.bad is not null or empty", gob.bad!=null || gob.bad.length()>0);
            assertTrue("has Both options need to be present: ",
                    gob.bad.indexOf("Both options need to be present") >= 0);
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void CamConfig_SetTestOK() throws Exception
    {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-ccs");//camconfig-set
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            loginParams.addElement("-j");
            loginParams.addElement("{ \"reset\":true }");
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has camconfig-set: ", gob.good.indexOf("camconfig-set") >= 0);
            assertTrue("camconfig-set=CAM_NAME1: ", cli.get_camconfig_set().compareTo(Constants4Tests.CAM_NAME1)==0);

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }}
