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
    }

    @Test
    public void setPtzButtonTest() throws Exception
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
    @Test
    public void StatusGetTest() throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-status-get");
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();
            assertNotNull(" assertNotNull getBlueCmdRequest", masterController.getCommandOther());

            assertNotNull("assertNotNull GetStatus", masterController.getLastBlueStatus());
            assertNotNull("assertNotNull GetStatus().toJsonString",
                    masterController.getLastBlueStatus());
            assertTrue("GetStatus().toJsonString().length() ",
                    masterController.getLastBlueStatus().toString().length()>0);

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
                    Arrays.toString(schedules.toArray()).indexOf(Constants4Tests.EXPECTED_Schedule1)>=0);
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
            int expectedProfileInt = blueProfiles.getProfileInt(Constants4Tests.EXPECTED_Profile1);

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
            int expectedProfileInt = blueProfiles.getProfileInt(Constants4Tests.EXPECTED_Profile1);

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }
}
