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
        try {
            Cameras.Camera camera = null;
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            CommandOther commandOther = new CommandOther(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );

            commandOther.TriggerCam(Constants4Tests.CAM_NAME1);
            blueLogin.BlueIrisLogout();

            Thread.sleep(1000);
            blueLogin.BlueIrisLogin(_loginParams);
            CommandCamList commandCamList = new CommandCamList(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );
            Cameras cameras = commandCamList.GetCamList();
            camera = cameras.get(Constants4Tests.CAM_NAME1);
            //when in mvnb build it fails sometimes.
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
