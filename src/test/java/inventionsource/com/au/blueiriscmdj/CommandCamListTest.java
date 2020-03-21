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
public class CommandCamListTest
{
    private static final Logger log = (Logger) LogManager.getLogger(CommandCamListTest.class.getName());
    private LoginParams _loginParams = null;

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
        _loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
    }

    @Test
    public void GetCamlistTest() throws Exception {
        String session = null;

        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            CommandCamList commandCamList = new CommandCamList(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );
            Cameras cameras = commandCamList.GetCamList();
            assertNotNull( "Not null cameras " ,cameras );
            assertTrue( "cameras.size()>0" ,cameras.size()>0 );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void ResetStatsCamsListTest() throws Exception {
        String session = null;

        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            CommandCamList commandCamList = new CommandCamList(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );
            Cameras cameras = commandCamList.ResetCamsStats();
            assertNotNull( "Not null cameras " ,cameras );
            assertTrue( "cameras.size()>0" ,cameras.size()>0 );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

}
