package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HelperSetProfilesTest
{
    private static final Logger log = (Logger) LogManager.getLogger(HelperSetProfilesTest.class.getName());

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
     }

    @Test
    public void GetProfileTest() throws Exception {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,Constants4Tests.HOST);
            HelperSetProfiles helper = new HelperSetProfiles(loginParams);

            String profile = helper.GetActiveProfile();

            assertNotNull("assertNotNull profile ", profile);
            assertTrue("profile.length() > 0 ", profile.length() > 0);
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }


    @Test
    public void SetProfileTest() throws Exception {
        try {
            LoginParams loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD,Constants4Tests.HOST);
            HelperSetProfiles helper = new HelperSetProfiles(loginParams);

            String profile = helper.GetActiveProfile();
            helper.SetProfile((profile));

            assertNotNull("assertNotNull profile ", profile);
            assertTrue("profile.length() > 0 ", profile.length() > 0);
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
