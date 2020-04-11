package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BlueLoginTest
{
    private static final Logger log = (Logger) LogManager.getLogger(BlueLoginTest.class.getName());
    private LoginParams _loginParams = null;

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
        new Constants4Tests();
        _loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
    }

    @Test
    public void BlueIrisLogoutTest() throws Exception
    {
        String session = null;
        BlueLogin blueLogin = new BlueLogin();

        try {
            blueLogin.BlueIrisLogin(_loginParams);
            assertNotNull( "Not null " , blueLogin.getSession() );

            assertTrue( "session.length()>0" , blueLogin.getSession().length()>0 );
            blueLogin.BlueIrisLogout();

        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
    @Test
    public void BlueIrisLoginTest() throws Exception
    {
        BlueLogin blueLogin = new BlueLogin();

        try {
            blueLogin.BlueIrisLogin(_loginParams);
            assertNotNull( "Not null " , blueLogin.getSession() );
            assertTrue( "session.length()>0" , blueLogin.getSession().length()>0 );

            assertNotNull( "Not null " , blueLogin.getBlueProfiles() );
            assertTrue( "session.length()>0" , blueLogin.getBlueProfiles().size()>0 );
            assertNotNull( "Not null getSchedules" , blueLogin.getSchedules() );
            assertTrue( "getSchedules.length()>0" , blueLogin.getSchedules().size()>0 );

            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

}
