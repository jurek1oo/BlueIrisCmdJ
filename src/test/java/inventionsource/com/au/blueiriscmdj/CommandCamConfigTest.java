package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
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
    public void GetCamConfigTest() throws Exception
    {
        try {
            Thread.sleep(1000);// let BI rest
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            CommandCamConfig commandCamConfig = new CommandCamConfig(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );

            JsonElement jsonElement= commandCamConfig.GetCamConfig(Constants4Tests.CAM_NAME1);

            assertNotNull( "Not null jsonElement" ,jsonElement );
            assertTrue( "jsonElement.toString().length()>0" ,jsonElement.toString().length()>0 );
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
            Thread.sleep(1000);

            blueLogin.BlueIrisLogin(_loginParams);
            CommandCamConfig commandCamConfig = new CommandCamConfig(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );

            String json = "{ \"enable\":false }";

            JsonElement jsonElement= commandCamConfig.SetCamConfig(Constants4Tests.CAM_NAME1, json);
            assertNotNull( "Not null jsonElement" ,jsonElement );
            assertNotNull( "Not null getAsJsonObject" ,jsonElement.getAsJsonObject() );

            Thread.sleep(5000);
            Cameras.Camera cam = (new CommandCamList(blueLogin)).GetCamList().get(Constants4Tests.CAM_NAME1);

            assertNotNull( "Not null get(enable)" ,cam);
            assertFalse( "Not null cam(enable) false" , cam.isEnabled());
            blueLogin.BlueIrisLogout();

            Thread.sleep(5000);
            blueLogin.BlueIrisLogin(_loginParams);
            commandCamConfig = new CommandCamConfig(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );

            json = "{ \"enable\":true }";

            jsonElement= commandCamConfig.SetCamConfig(Constants4Tests.CAM_NAME1, json);
            assertNotNull( "Not null jsonElement" ,jsonElement );


            Thread.sleep(5000);
            cam = (new CommandCamList(blueLogin)).GetCamList().get(Constants4Tests.CAM_NAME1);

            assertNotNull( "Not null get(enable)" ,cam);
            assertTrue( "Not null cam(enable) false" , cam.isEnabled());
            blueLogin.BlueIrisLogout();

        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void SetCamConfigTest() throws Exception {
        try {
            Cameras.Camera camera = null;
            String json = "{ \"reset\":true, \"enable\":true, \"pause\":0 }";

            BlueLogin blueLogin = new BlueLogin();

            blueLogin.BlueIrisLogin(_loginParams);
            CommandCamConfig commandCamConfig = new CommandCamConfig(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );
            JsonElement jsonElement= commandCamConfig.SetCamConfig(Constants4Tests.CAM_NAME1, json);
            assertNotNull( "Not null jsonElement" ,jsonElement );
            blueLogin.BlueIrisLogout();

        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }}
