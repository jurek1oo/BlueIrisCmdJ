package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandAlertsTest
{
    private static final Logger log = (Logger) LogManager.getLogger(CommandAlertsTest.class.getName());
    private LoginParams _loginParams = null;

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
        new Constants4Tests();
        _loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
    }

    @Test
    public void Alerts_List_partTest() throws Exception {
        String session = null;

        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            CommandAlerts commandAlerts = new CommandAlerts(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );
            String camera = "index";
            String dateStart = "2020-03-23 17:00";
            //"1970-01-01"; 1584542400 -2020-0 3-18 21:40 |
            // 1584542728 - 2020-03-18T21:45:28
            // 1584542375 - 2020-03-18T21:39:35
            // 1584542112 - 2020-03-18T21:35:12
            String json = "{\"camera\":\"" + camera + "\"," +
                    "\"startdate\":\"1970-01-02\"}";
            BlueAlerts alerts = commandAlerts.GetAlertsList(json);
            assertNull( "Not null getProblemMsg " ,commandAlerts.getProblemMsg() );
            log.debug(alerts.toString());
            assertNotNull( "Not null alerts " ,alerts );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void Alerts_List_AllTest() throws Exception {
        String session = null;

        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            CommandAlerts commandAlerts = new CommandAlerts(blueLogin);
            assertNotNull( "Not null " , blueLogin.getSession() );
            String camera = "index";
            String dateStart = "2020-03-18 21:40";
            //"1970-01-01"; 1584542400 -2020-0 3-18 21:40 |
            // 1584542728 - 2020-03-18T21:45:28
            // 1584542375 - 2020-03-18T21:39:35
            // 1584542112 - 2020-03-18T21:35:12
            String json = "{\"camera\":\"" + camera + "\"," +
                    "\"startdate\":\"" + dateStart + "\"}";

            BlueAlerts alerts = commandAlerts.GetAlertsList(json);
            assertNull( "Not null getProblemMsg " ,commandAlerts.getProblemMsg() );

            log.debug(alerts.toString());
            assertNotNull( "Not null alerts " ,alerts );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
    @Test
    public void AlertsDeleteTest() throws Exception {
        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            assertNotNull( "Not null " , blueLogin.getSession() );
            CommandAlerts commandAlerts = new CommandAlerts(blueLogin);

            commandAlerts.AlertsDelete();
            assertNull( "Not null getProblemMsg " ,commandAlerts.getProblemMsg() );

            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }}
