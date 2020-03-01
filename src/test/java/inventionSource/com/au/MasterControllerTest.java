package inventionSource.com.au;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
    private static final Logger log = LogManager.getLogger(MasterControllerTest.class.getName());

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
            assertNotNull(" assertNotNull getBlueCmdRequest", masterController.getBlueCmdRequest());

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
            assertNotNull(" assertNotNull getBlueCmdRequest", masterController.getBlueCmdRequest());

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
            assertNotNull(" assertNotNull getBlueCmdRequest", masterController.getBlueCmdRequest());

            assertNotNull("assertNotNull GetStatus", masterController.getBlueCmdRequest().getBlueStatus());
            assertNotNull("assertNotNull GetStatus().toJsonString",
                    masterController.getBlueCmdRequest().getBlueStatus().toJsonString());
            assertTrue("GetStatus().toJsonString().length() ",masterController.getBlueCmdRequest().
                    getBlueStatus().toJsonString().length()>0);


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
            assertNotNull(" assertNotNull getBlueCmdRequest", masterController.getBlueCmdRequest());

            assertNotNull("assertNotNull GetStatus", masterController.getBlueCmdRequest().getBlueStatus());
            assertNotNull("assertNotNull getSignal",
                    masterController.getBlueCmdRequest().getBlueStatus().getSignal());
            assertTrue("status has active signal : red ",masterController.getBlueCmdRequest().
                    getBlueStatus().getSignal().indexOf("red")>=0);

            Thread.sleep(1000);
            loginParams  =
                    new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
            loginParams.addElement("-set-signal");
            loginParams.addElement("green");
            args = loginParams.getArgs();
            masterController = new MasterController(args);
            masterController.Action();

            assertNotNull("result has text", masterController.getBlueCmdRequest().getBlueStatus());
            assertTrue("status has active signal : ",masterController.getBlueCmdRequest().
                    getBlueStatus().getSignal().indexOf("green")>=0);
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

            assertNotNull("result has text", masterController.getBlueCmdRequest().getBlueStatus());
            assertTrue("status has active signal : ",masterController.getBlueCmdRequest().
                    getBlueStatus().getSignal().indexOf("green")>=0);
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
            ArrayList<String> schedules  = masterController.getBlueCmdRequest().getSchedules();
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
            assertNotNull("result has text", masterController.getBlueCmdRequest().getBlueStatus());
            assertNotNull("status has schedule: ",
                    masterController.getBlueCmdRequest().getBlueStatus().getActiveProfile());
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

            assertNotNull("result has text", masterController.getBlueCmdRequest().getBlueStatus());
            assertTrue("status has active profile : ",
                    masterController.getBlueCmdRequest().getBlueStatus().getActiveProfile().indexOf(Constants4Tests.EXPECTED_Profile1)>=0);
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
            assertNotNull("",masterController.getBlueCmdRequest() );
            assertNotNull("",masterController.getBlueCmdRequest().getProfiles());
            ArrayList<String> profiles  = masterController.getBlueCmdRequest().getProfiles();
            assertTrue("list-profiles has expected profile: ",
                    Arrays.toString(profiles.toArray()).indexOf(Constants4Tests.EXPECTED_Profile1)>=0);
        } catch (Exception e) {
            log.error("Exception: " + e);
            throw e;
        }
    }
}
