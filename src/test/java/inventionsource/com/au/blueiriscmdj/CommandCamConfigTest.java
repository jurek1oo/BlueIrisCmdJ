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
public class CommandCamConfigTest
{
    private static final Logger log = (Logger) LogManager.getLogger(CommandCamConfigTest.class.getName());
    private LoginParams _loginParams = null;

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
        _loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
    }

    @Test
    public void CamConfig_SetJsonHelpTest() throws Exception
    {
        try {
            Thread.sleep(1000);// let BI rest
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            CommandCamConfig commandCamConfig = new CommandCamConfig(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );

            log.info(commandCamConfig.setJsonHelp());

            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
    @Test
    public void CamConfigGetTest() throws Exception
    {
        try {
            Thread.sleep(1000);// let BI rest
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            CommandCamConfig commandCamConfig = new CommandCamConfig(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );

            BlueCamConfig blueCamConfig= commandCamConfig.GetCamConfig(Constants4Tests.CAM_NAME1);

            assertNotNull("result has blueCamConfig", blueCamConfig);
            assertTrue("getCamera name: ",  blueCamConfig.getCameraName().contains(Constants4Tests.CAM_NAME1));
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void CamConfig_DisableEnableTest() throws Exception {
        BlueCamConfig blueCamConfig=null;
        try {
            Cameras.Camera camera = null;
            BlueLogin blueLogin = new BlueLogin();
            Thread.sleep(1000);

            blueLogin.BlueIrisLogin(_loginParams);
            CommandCamConfig commandCamConfig = new CommandCamConfig(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );

            String json = "{ \"enable\": 0 }";

            blueCamConfig= commandCamConfig.SetCamConfig(Constants4Tests.CAM_NAME1, json);

            assertNotNull("result has blueCamConfig", blueCamConfig);

            //Thread.sleep(5000);
            //Cameras.Camera cam = (new CommandCamList(blueLogin)).GetCamList().get(Constants4Tests.CAM_NAME1);

            //assertNotNull( "Not null get(enable)" ,cam);
            //assertTrue( " cam(isEnabled) false" , !cam.isEnabled());
            blueLogin.BlueIrisLogout();

            Thread.sleep(1000);
            blueLogin.BlueIrisLogin(_loginParams);
            commandCamConfig = new CommandCamConfig(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );

            json = "{ \"enable\": 1 }";

            blueCamConfig= commandCamConfig.SetCamConfig(Constants4Tests.CAM_NAME1, json);

            assertNotNull("result has blueCamConfig", blueCamConfig);

            //Thread.sleep(5000);
            //cam = (new CommandCamList(blueLogin)).GetCamList().get(Constants4Tests.CAM_NAME1);

            //assertNotNull( "Not null get(enable)" ,cam);
            //assertTrue( "Not null cam(enable) false" , cam.isEnabled());
            blueLogin.BlueIrisLogout();

        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void CamConfigSetTest() throws Exception {
        BlueCamConfig blueCamConfig=null;
        try {
            Cameras.Camera camera = null;
            String json = "{ \"reset\": 0, \"enable\": 1, \"pause\": 0 }";

            BlueLogin blueLogin = new BlueLogin();

            blueLogin.BlueIrisLogin(_loginParams);
            CommandCamConfig commandCamConfig = new CommandCamConfig(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );

            blueCamConfig= commandCamConfig.SetCamConfig(Constants4Tests.CAM_NAME1, json);

            assertNotNull("result has blueCamConfig", blueCamConfig);
            assertTrue("getCamera name: ",  blueCamConfig.getCameraName().contains(Constants4Tests.CAM_NAME1));
            blueLogin.BlueIrisLogout();

        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }}
