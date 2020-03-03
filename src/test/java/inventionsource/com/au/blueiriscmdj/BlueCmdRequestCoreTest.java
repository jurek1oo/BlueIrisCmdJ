package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BlueCmdRequestCoreTest
{
    private static final Logger log = (Logger) LogManager.getLogger(BlueCmdRequestCoreTest.class.getName());
    private LoginParams _loginParams = null;

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
        _loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
    }

    @Test
    public void GetStatusTest() throws Exception {
        try {
            String cmd = "status";
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            BlueCmdRequestCore blueCmdRequestCore = new BlueCmdRequestCore(blueLogin);
            BlueStatus blueStatus =
                    (new BlueStatus(blueCmdRequestCore.RunTheCmd(cmd,null),blueLogin.getProfiles()));
            String jsonresult = blueStatus.toJsonString();
            assertNotNull( "Not null blueStatus" ,jsonresult );
            assertTrue( "blueStatus length()>0" ,jsonresult.length()>0 );
            assertTrue( "signal is there" ,jsonresult.indexOf("signal")>0 );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
