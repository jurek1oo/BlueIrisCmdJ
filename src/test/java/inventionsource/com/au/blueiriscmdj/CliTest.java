package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CliTest
{
    private static final Logger log =(Logger) LogManager.getLogger(BlueCmdRequestTest.class.getName());
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
    public void help()  throws Exception{
        String[] args = new String[1];
        args[0] = "-h";// help
        Cli cli = new Cli(args);
        try {
            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has help: ", gob.good.indexOf("help") >= 0);

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }
    @Test
    public void list_profiles () throws Exception
    {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-lp");//list-profiles
            String[] args = loginParams.getArgs();
            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has is-list-profiles = true: ", cli.is_list_profiles());
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }
    @Test
    public void list_schedules () throws Exception {

        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-lsch");//list-schedules
            String[] args = loginParams.getArgs();
            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has is-list-schedules = true: ", cli.is_list_schedules());
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void ListCamsTest() throws Exception {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-lc");
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has list-cams: ", gob.good.indexOf("list-cams") >= 0);
            assertTrue("is list_cams: ", cli.is_list_cams());

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void ListAlertsEmptyCamNameTest() throws Exception {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-la");
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has list-alerts: ", gob.good.indexOf("list-alerts") >= 0);
            assertTrue("is_list_alerts: ", cli.is_list_alerts());

            assertNull("get_list_alerts : ", cli.get_list_alerts());
            assertNull("list_alerts_date()", cli.get_list_alerts_date());
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void ListAlerts4CamTest() throws Exception {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-la");
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has list-alerts: ", gob.good.indexOf("list-alerts") >= 0);
            assertTrue("is_list_alerts: ", cli.is_list_alerts());

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void ListAlertsDate_ButNoListAlertsTest() throws Exception {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-lad");
            loginParams.addElement("2020-03-25");
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad list-alerts-date has to be used with list-alerts option",
                    gob.bad!=null && gob.bad.indexOf("list-alerts-date has to be used with list-alerts option")>=0);
            assertTrue("has list-alerts: ", gob.good.indexOf("list-alerts") >= 0);
            assertFalse("is_list_alerts: ", cli.is_list_alerts());

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void GetCamConfigTest() throws Exception
{
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-gcc");
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has camconfig: ", gob.good.indexOf("camconfig") >= 0);
            assertTrue("is get_camconfig=CAM_NAME1: ", cli.get_camconfig().compareTo(Constants4Tests.CAM_NAME1)==0);

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
    public void CamEnableTest()  throws Exception{
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-ce");
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has enable: ", gob.good.indexOf("enable") >= 0);
            assertTrue("is get_cam_enable=CAM_NAME1: ", cli.get_cam_enable().compareTo(Constants4Tests.CAM_NAME1)==0);

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void CamDisableTest()  throws Exception{
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,
                    Constants4Tests.HOST);
            loginParams.addElement("-cd");
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            String[] args = loginParams.getArgs();

            Cli cli = new Cli(args);

            Cli.GoodOrBad gob = cli.parse();
            assertTrue("gob.good has text", gob.good != null && gob.good.length() > 0);
            assertTrue("gob.bad is null or empty", gob.bad==null || gob.bad.length()==0);
            assertTrue("has disable: ", gob.good.indexOf("disable") >= 0);
            assertTrue("is get_cam_disable=CAM_NAME1: ", cli.get_cam_disable().compareTo(Constants4Tests.CAM_NAME1)==0);

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
            assertTrue("Missing ptz option\" ", gob.bad.indexOf("Missing ptz option") >= 0);
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

}