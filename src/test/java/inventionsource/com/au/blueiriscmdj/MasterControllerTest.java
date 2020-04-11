package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class MasterControllerTest {
    private static final Logger log = (Logger) LogManager.getLogger(MasterControllerTest.class.getName());

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","info");
        new Constants4Tests();
    }

    @Test
    public void alerts_listAllCams() throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-al");//alerts-list
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();
            assertNotNull("",masterController.getCommandOther() );
            assertNotNull("",masterController.getBlueLogin().getBlueProfiles());

            BlueProfiles blueProfiles = masterController.getBlueLogin().getBlueProfiles();
            assertNotNull("assertNotNull blueProfiles",blueProfiles );

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void camconfig_get() throws Exception
    {
        BlueCamConfig blueCamConfig= null;
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-ccg");//camconfig-get
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            MasterController masterController = new MasterController(loginParams.getArgs());
            masterController.Action();
            blueCamConfig = masterController.getBlueCamConfig();

            assertNotNull("result has blueCamConfig", blueCamConfig);
            assertTrue("getCamera name: ",  blueCamConfig.getCameraName().contains(Constants4Tests.CAM_NAME1));

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void camconfig_set() throws Exception
    {
        BlueCamConfig blueCamConfig= null;
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-ccs");//camconfig-set
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            loginParams.addElement("-j");//json
            //
            loginParams.addElement(
                    "{\"enable\": 0}");

            MasterController masterController = new MasterController(loginParams.getArgs());
            masterController.Action();
            blueCamConfig = masterController.getBlueCamConfig();

            assertNotNull("result has blueCamConfig", blueCamConfig);
            assertTrue("getCamera name: ",  blueCamConfig.getCameraName().contains(Constants4Tests.CAM_NAME1));


            // ------ back to default --------------------------------------

            loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-ccs");//status-set
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            loginParams.addElement("-j");//json

            loginParams.addElement(
                    "{\"enable\": 1}");

            masterController = new MasterController(loginParams.getArgs());
            masterController.Action();
            blueCamConfig = masterController.getBlueCamConfig();

            assertNotNull("result has blueCamConfig", blueCamConfig);
            assertTrue("getCamera name: ",  blueCamConfig.getCameraName().contains(Constants4Tests.CAM_NAME1));


        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void cams_reset_stats() throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-crt");//cams-reset-stats
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void cams_list () throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-cl");//cams-list
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void logs_list() throws Exception
    {
        BlueLogs blueLogs= null;
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-l");//logs-list
            MasterController masterController = new MasterController(loginParams.getArgs());
            masterController.Action();
            blueLogs = masterController.getBlueLogs();

            assertNotNull("result has blueCamConfig", blueLogs);
            assertTrue("getCamera name: ",  blueLogs.size()>0);

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void profiles_list () throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-pl");//profiles-list
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();
            assertNotNull("",masterController.getCommandOther() );
            assertNotNull("",masterController.getBlueLogin().getBlueProfiles());

            BlueProfiles blueProfiles = masterController.getBlueLogin().getBlueProfiles();
            assertNotNull("",blueProfiles);

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void PtzButtonSetTest() throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-ptz-cam");
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            loginParams.addElement("-ptz-button");
            loginParams.addElement("1");
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void schedules_list() throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-schl");//is_schedules_list
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();
            ArrayList<String> schedules  = masterController.getBlueLogin().getSchedules();
            assertTrue("result schedules has text", schedules != null);
            assertTrue("is_schedules_list has expected schedule: ",
                    Arrays.toString(schedules.toArray()).indexOf(Constants4Tests.SCHEDULE1)>=0);
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }
    @Test
    public void status_get () throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-stg");//status-get
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();
            assertNotNull("result has text", masterController.getLastBlueStatus());
            assertNotNull("status has schedule: ",  masterController.getLastBlueStatus());
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void status_set_signal() throws Exception
    {
        try {
            String activeSchedule = "Default";

            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-status-set");//status-set
            loginParams.addElement("-j");//json
            //
            //{"signal":1,"profile":1,"schedule":"Default"}
            //
            loginParams.addElement(
                    "{\"signal\":2}");

            MasterController masterController = new MasterController(loginParams.getArgs());
            masterController.Action();
            BlueStatus blueStatus = masterController.getLastBlueStatus();

            assertNotNull("result has blueStatus", blueStatus);
             assertTrue("status has signalInt: ",
                    blueStatus.getSignalInt() == 2);
            assertTrue("status has activeSchedule: ",
                    blueStatus.getActiveSchedule().contains(activeSchedule));

            // ------ back to default --------------------------------------

            loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-status-set");//status-set
            loginParams.addElement("-j");//json
            loginParams.addElement(
                    "{\"signal\":1}");

            masterController = new MasterController(loginParams.getArgs());
            masterController.Action();
            blueStatus = masterController.getLastBlueStatus();

            assertNotNull("result has blueStatus", blueStatus);
             assertTrue("status has signalInt: ",
                    blueStatus.getSignalInt() == 1);
            assertTrue("status has activeSchedule: ",
                    blueStatus.getActiveSchedule().contains(activeSchedule));
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }
    @Test
    public void status_set() throws Exception
    {
        try {
            String activeSchedule = "Default";

            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-sts");//status-set
            loginParams.addElement("-j");//json
            //
            //{"signal":1,"profile":1,"schedule":"Default"}
            //
            loginParams.addElement(
                    "{\"signal\":2,\"profile\":2,\"schedule\":\"" + activeSchedule + "\"}");

            MasterController masterController = new MasterController(loginParams.getArgs());
            masterController.Action();
            BlueStatus blueStatus = masterController.getLastBlueStatus();

            assertNotNull("result has blueStatus", blueStatus);
            assertTrue("status has activeProfileInt: ",
                    blueStatus.getActiveProfileInt() == 2);
            assertTrue("status has signalInt: ",
                    blueStatus.getSignalInt() == 2);
            assertTrue("status has activeSchedule: ",
                    blueStatus.getActiveSchedule().contains(activeSchedule));

            // ------ back to default --------------------------------------

            loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-sts");//status-set
            loginParams.addElement("-j");//json
            loginParams.addElement(
                    "{\"signal\":1,\"profile\":1,\"schedule\":\"" + activeSchedule + "\"}");

            masterController = new MasterController(loginParams.getArgs());
            masterController.Action();
            blueStatus = masterController.getLastBlueStatus();

            assertNotNull("result has blueStatus", blueStatus);
            assertTrue("status has activeProfileInt: ",
                    blueStatus.getActiveProfileInt() == 1);
            assertTrue("status has signalInt: ",
                    blueStatus.getSignalInt() == 1);
            assertTrue("status has activeSchedule: ",
                    blueStatus.getActiveSchedule().contains(activeSchedule));
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void triggerCamTest() throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-t");//trigger cam
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

}
