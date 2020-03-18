package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class BlueCmdRequestTest
{
    private static final Logger log = (Logger) LogManager.getLogger(BlueCmdRequestTest.class.getName());
    private LoginParams _loginParams = null;

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
        _loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
    }

    @Test
    public void SetSignalStrTest() throws Exception
    {
        String signalRed = new BlueSignals().getSignal(0);
        String signalGreen2 = new BlueSignals().getSignal(1);
        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            BlueCmdRequest blueCmdRequest = new BlueCmdRequest(blueLogin);
            assertNotNull( "Not null " , blueCmdRequest.getSession() );

            Thread.sleep(1000);// give time to  BI to rest
            BlueStatus blueStatus = blueCmdRequest.SetSignal(signalRed);
            String jsonresult = blueStatus.toJsonString();
            assertNotNull( "Not null " ,jsonresult );
            assertTrue( "blueStatus.toJsonString.length()>0" ,jsonresult.length()>0 );
            assertTrue( "indexOf " + signalRed + " >0" ,
                    jsonresult.indexOf(signalRed)>0 );

            Thread.sleep(1000);// give time to  BI to rest
            blueStatus = blueCmdRequest.SetSignal(signalGreen2);
            jsonresult = blueStatus.toJsonString();
            assertNotNull( "Not null " ,jsonresult );
            assertTrue( "blueStatus.toJsonString.length()>0" ,jsonresult.length()>0 );
            assertTrue( "indexOf  " + signalGreen2 + " >0" ,
                    jsonresult.indexOf(signalGreen2)>0 );

            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
    @Test
    public void SetSignalIntTest() throws Exception
    {
        int signalRed0 = new BlueSignals().getSignalInt("red");
        int signalGreen1 = new BlueSignals().getSignalInt("green");
        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            BlueCmdRequest blueCmdRequest = new BlueCmdRequest(blueLogin);
            assertNotNull( "Not null " , blueCmdRequest.getSession() );

            Thread.sleep(1000);// give time to  BI to rest
            BlueStatus blueStatus = blueCmdRequest.SetSignal(signalRed0);
            String jsonresult = blueStatus.toJsonString();
            assertNotNull( "Not null " ,jsonresult );
            assertTrue( "blueStatus.toJsonString.length()>0" ,jsonresult.length()>0 );
            assertTrue( "indexOf red >0" ,   jsonresult.indexOf("red")>=0 );

            Thread.sleep(1000);// give time to  BI to rest
            blueStatus = blueCmdRequest.SetSignal(signalGreen1);
            jsonresult = blueStatus.toJsonString();
            assertNotNull( "Not null " ,jsonresult );
            assertTrue( "blueStatus.toJsonString.length()>0" ,jsonresult.length()>0 );
            assertTrue( "indexOf green>0" ,jsonresult.indexOf("green")>0 );

            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void SetScheduleTest() throws Exception
    {
        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            BlueCmdRequest blueCmdRequest = new BlueCmdRequest(blueLogin);
            assertNotNull( "Not null " , blueCmdRequest.getSession() );

            Thread.sleep(1000);// give time to  BI to rest
            BlueStatus blueStatus = blueCmdRequest.SetSchedule(Constants4Tests.EXPECTED_Schedule1);
            String jsonresult = blueStatus.toJsonString();
            assertNotNull( "Not null " ,jsonresult );
            assertTrue( "blueStatus.toJsonString.length()>0" ,jsonresult.length()>0 );
            assertTrue( "indexOf EXPECTED_Schedule1>0" ,
                    jsonresult.indexOf(Constants4Tests.EXPECTED_Schedule1)>0 );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
    @Test
    public void SetProfileTest() throws Exception
    {
        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            BlueCmdRequest blueCmdRequest = new BlueCmdRequest(blueLogin);
            assertNotNull( "Not null " , blueCmdRequest.getSession() );
            Thread.sleep(3000);// give time to  BI to rest
            BlueStatus blueStatus = blueCmdRequest.SetProfile(Constants4Tests.EXPECTED_Profile1);
            String jsonresult = blueStatus.toJsonString();
            assertNotNull( "Not null " ,jsonresult );
            assertTrue( "blueStatus.toJsonString.length()>0" ,jsonresult.length()>0 );
            assertTrue( "ndexOf expectedProfile1>0" ,jsonresult.indexOf(Constants4Tests.EXPECTED_Profile1)>0 );

            Thread.sleep(3000);// give time to  BI to rest
            blueStatus = blueCmdRequest.SetProfile( Constants4Tests.EXPECTED_PROFILE_2);
            jsonresult = blueStatus.toJsonString();
            assertNotNull( "Not null " ,jsonresult );
            assertTrue( "blueStatus.toJsonString.length()>0" ,jsonresult.length()>0 );
            assertTrue( "ndexOf expectedProfile1>0" ,
                    jsonresult.indexOf(Constants4Tests.EXPECTED_PROFILE_2)>0 );

            Thread.sleep(3000);// give time to  BI to rest
            blueStatus = blueCmdRequest.SetProfile( Constants4Tests.EXPECTED_Profile1);
            jsonresult = blueStatus.toJsonString();
            assertNotNull( "Not null " ,jsonresult );
            assertTrue( "blueStatus.toJsonString.length()>0" ,jsonresult.length()>0 );
            assertTrue( "indexOf expectedProfile1>0" ,jsonresult.indexOf(Constants4Tests.EXPECTED_Profile1)>0 );
            Thread.sleep(3000);// give time to  BI to rest

            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void GetStatusTest() throws Exception
    {
        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            BlueCmdRequest blueCmdRequest = new BlueCmdRequest(blueLogin);
            assertNotNull( "Not null " , blueCmdRequest.getSession() );
            BlueStatus blueStatus = blueCmdRequest.GetStatus();
            String jsonresult = blueStatus.toJsonString();
            assertNotNull( "Not null " ,jsonresult );
            assertTrue( "session.length()>0" ,jsonresult.length()>0 );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void GetList_CamsTest() throws Exception {
        String session = null;

        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            BlueCmdRequest blueCmdRequest = new BlueCmdRequest(blueLogin);

            assertNotNull( "Not null " , blueCmdRequest.getSession() );
            Cameras cameras = blueCmdRequest.GetList_Cams();
            assertNotNull( "Not null cameras " ,cameras );
            assertTrue( "cameras.size()>0" ,cameras.size()>0 );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
    @Test
    public void GetCamConfigTest() throws Exception
    {
        try {
            Thread.sleep(1000);// let BI rest
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            BlueCmdRequest blueCmdRequest = new BlueCmdRequest(blueLogin);
            assertNotNull( "Not null " , blueCmdRequest.getSession() );

            JsonElement jsonElement= blueCmdRequest.GetCamConfig(Constants4Tests.CAM_NAME1);

            assertNotNull( "Not null jsonElement" ,jsonElement );
            assertTrue( "jsonElement.toString().length()>0" ,jsonElement.toString().length()>0 );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void TriggerCamTest() throws Exception
    {
        try {
            Cameras.Camera camera = null;
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            BlueCmdRequest blueCmdRequest = new BlueCmdRequest(blueLogin);
            assertNotNull( "Not null " , blueCmdRequest.getSession() );

            blueCmdRequest.TriggerCam(Constants4Tests.CAM_NAME1);
            blueLogin.BlueIrisLogout();

            Thread.sleep(1000);
            blueLogin.BlueIrisLogin(_loginParams);
            blueCmdRequest = new BlueCmdRequest(blueLogin);
            assertNotNull( "Not null " , blueCmdRequest.getSession() );
            camera =(blueCmdRequest.GetList_Cams()).get(Constants4Tests.CAM_NAME1);
            //assertTrue( "isTriggered" , camera.isTriggered() ); when in mvnb build it fails sometimes.
            //assertTrue( "getNTriggers" ,camera.getNTriggers()>0 );

            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void CamDisableEnableTest() throws Exception {
        try {
            Cameras.Camera camera = null;
            BlueLogin blueLogin = new BlueLogin();

            blueLogin.BlueIrisLogin(_loginParams);
            BlueCmdRequest blueCmdRequest = new BlueCmdRequest(blueLogin);
            assertNotNull( "Not null " , blueCmdRequest.getSession() );
            JsonElement jsonElement= blueCmdRequest.CamDisable(Constants4Tests.CAM_NAME1);
            assertNotNull( "Not null jsonElement" ,jsonElement );
            blueLogin.BlueIrisLogout();

 //           Thread.sleep(1000);
//            blueCmdRequest = new BlueCmdRequest(blueLogin);
 //           assertNotNull( "Not null " , blueCmdRequest.getSession() );
            //camera = blueCmdRequest.GetList_Cams().get(Constants4Tests.CAM_NAME1);
           // assertNotNull( "Not null camera" ,camera );
           // assertTrue( "!camera.isEnabled() " ,!camera.isEnabled() );
            //blueLogin.BlueIrisLogout();

            Thread.sleep(1000);
            blueLogin.BlueIrisLogin(_loginParams);
            blueCmdRequest = new BlueCmdRequest(blueLogin);
            assertNotNull( "Not null " , blueCmdRequest.getSession() );
            jsonElement= blueCmdRequest.CamEnable(Constants4Tests.CAM_NAME1);
            assertNotNull( "Not null jsonElement" ,jsonElement );
            blueLogin.BlueIrisLogout();

           // Thread.sleep(1000);
            //blueCmdRequest = new BlueCmdRequest(blueLogin);
            //assertNotNull( "Not null " , blueCmdRequest.getSession() );
          //  camera = blueCmdRequest.GetList_Cams().get(Constants4Tests.CAM_NAME1);
          //  assertNotNull( "Not null camera" ,camera );
          //  assertTrue( "camera.isEnabled() " ,camera.isEnabled() );
           // blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
//
    @Test
    public void SendPtzButtonTest() throws Exception {
        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            BlueCmdRequest blueCmdRequest = new BlueCmdRequest(blueLogin);
            assertNotNull( "Not null " , blueCmdRequest.getSession() );

            int ptzButton = 1;
            blueCmdRequest.SendPtzButton(Constants4Tests.CAM_NAME1,ptzButton);

            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void GetList_AlertsAllTest() throws Exception {
        String session = null;

        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            BlueCmdRequest blueCmdRequest = new BlueCmdRequest(blueLogin);
            assertNotNull( "Not null " , blueCmdRequest.getSession() );
            String camera = "index";
            String dateStart = "2020-03-18 21:40";
            //"1970-01-01"; 1584542400 -2020-0 3-18 21:40 |
            // 1584542728 - 2020-03-18T21:45:28
            // 1584542375 - 2020-03-18T21:39:35
            // 1584542112 - 2020-03-18T21:35:12
            boolean reset = false;

            Alerts alerts = blueCmdRequest.GetList_Alerts(camera, dateStart);
            log.debug(alerts.toString());
            assertNotNull( "Not null alerts " ,alerts );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void DeleteAlertsTest() throws Exception {
        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            BlueCmdRequest blueCmdRequest = new BlueCmdRequest(blueLogin);
            assertNotNull( "Not null " , blueCmdRequest.getSession() );

            blueCmdRequest.Delete_Alerts();
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }}
