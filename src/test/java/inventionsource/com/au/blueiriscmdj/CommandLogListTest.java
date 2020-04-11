package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandLogListTest {
    private static final Logger log = (Logger) LogManager.getLogger(CommandLogListTest.class.getName());
    private LoginParams _loginParams = null;

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log", "debug");
        new Constants4Tests();
        _loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
    }

    @Test
    public void GetLogsJsonNullTest() throws Exception {
        try {
            String dateNow = Utils.DateStringNow();

            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            assertNotNull("Not null ", blueLogin.getSession());
            CommandLogsList command = new CommandLogsList(blueLogin);
            BlueLogs blueLogs = command.GetLogs(null);
            assertNotNull("Not null blueClipList ", blueLogs);
            assertTrue("blueClips.size()>0", blueLogs.size() > 0);
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }


    @Test
    public void GetLogsJustPastWeekTest() throws Exception {

        try {
            String dateNow = Utils.DateStringNow(-7);

            String json = "{\"startdate\":\"" + dateNow + "\"}";

            Thread.sleep(1000);
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            assertNotNull("Not null ", blueLogin.getSession());
            CommandLogsList command = new CommandLogsList(blueLogin);
            BlueLogs blueLogs = command.GetLogs(json);
            assertNotNull("Not null blueClipList ", blueLogs);
            assertTrue("blueClips.size()>0", blueLogs.size() > 0);
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
