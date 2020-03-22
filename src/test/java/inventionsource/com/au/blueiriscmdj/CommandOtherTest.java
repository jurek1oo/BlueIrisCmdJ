package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandOtherTest
{
    private static final Logger log = (Logger) LogManager.getLogger(CommandOtherTest.class.getName());
    private LoginParams _loginParams = null;

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
        _loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
    }

    @Test
    public void TriggerCamTest() throws Exception
    {
        // This test only works if the cam is in active state, i.e. it can be triggered.
        // cam1 is active in profile "OnTheRoad",
        // cam2 is active in profile "AtHome".
        // So we use cam2, as the default system profile is "AtHome",
        // where only  front house cam2 is active and is recording constantly.
        try {
            Cameras.Camera camera = null;
            String camName = Constants4Tests.CAM_NAME2;

            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);

            CommandOther commandOther = new CommandOther(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );

            commandOther.TriggerCam(camName);

            Thread.sleep(1000);
            assertNotNull( "Not null " , blueLogin.getSession() );

            camera = (new CommandCamList(blueLogin)).GetCamList().get(camName);
            assertTrue( "isTriggered" , camera.isTriggered() );
            assertTrue( "getNTriggers" ,camera.getNTriggers()>0 );

            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void SendPtzButtonTest() throws Exception {
        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            CommandOther commandOther = new CommandOther(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );

            int ptzButton = 1;
            commandOther.SendPtzButton(Constants4Tests.CAM_NAME1,ptzButton);

            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
