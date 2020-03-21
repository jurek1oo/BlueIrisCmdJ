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
    public void camDisableEnableTest() throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-cd");//cam-disable
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();

            Thread.sleep(3000);
            loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-ce");//cam-enable
            loginParams.addElement(Constants4Tests.CAM_NAME1);
            args = loginParams.getArgs();
            masterController = new MasterController(args);
            masterController.Action();

        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void getStatusTest() throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-get-status");
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
    public void set_signalTest() throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-set-signal");//set-signal
            loginParams.addElement("red");//signal
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();
            assertNotNull(" assertNotNull getBlueCmdRequest", masterController.getCommandOther());

            assertNotNull("assertNotNull GetStatus", masterController.getLastBlueStatus());
            assertNotNull("assertNotNull getSignal",
                    masterController.getLastBlueStatus().getSignal());
            assertTrue("status has active signal : red ",
                    masterController.getLastBlueStatus().getSignal().indexOf("red")>=0);

            Thread.sleep(1000);
            loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-set-signal");
            loginParams.addElement("green");
            args = loginParams.getArgs();
            masterController = new MasterController(args);
            masterController.Action();

            assertNotNull("result has text", masterController.getLastBlueStatus());
            assertTrue("status has active signal : ",
                    masterController.getLastBlueStatus().getSignal().indexOf("green")>=0);
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }
    @Test
    public void set_signal_green() throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-set-signal");
            loginParams.addElement("green");
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();

            assertNotNull("result has text", masterController.getLastBlueStatus());
            assertTrue("status has active signal : ",
                    masterController.getLastBlueStatus().getSignal().indexOf("green")>=0);
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void list_schedules() throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-lsch");//list-schedules
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();
            ArrayList<String> schedules  = masterController.getBlueLogin().getSchedules();
            assertTrue("result schedules has text", schedules != null);
            assertTrue("list-schedules has expected schedule: ",
                    Arrays.toString(schedules.toArray()).indexOf(Constants4Tests.EXPECTED_Schedule1)>=0);
       } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }
    @Test
    public void get_status () throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-gs");//get-status
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
    public void set_profile () throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-set-profile");//set-profile
            loginParams.addElement(Constants4Tests.EXPECTED_Profile1);//profile name
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();

            BlueProfiles blueProfiles = masterController.getBlueLogin().getBlueProfiles();
            int expectedProfileInt = blueProfiles.getProfileInt(Constants4Tests.EXPECTED_Profile1);

            assertNotNull("result has text", masterController.getLastBlueStatus());
            assertTrue("status has active profile : ",
                    masterController.getLastBlueStatus().getActiveProfileInt() ==expectedProfileInt);
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void list_profiles () throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-lp");//list-profiles
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
            loginParams.addElement("-lc");//list-cams
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }

    @Test
    public void reset_cams_stats() throws Exception
    {
        try {
            LoginParams loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-rct");//reset-cams-stats
            String[] args = loginParams.getArgs();
            MasterController masterController = new MasterController(args);
            masterController.Action();
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }
}
