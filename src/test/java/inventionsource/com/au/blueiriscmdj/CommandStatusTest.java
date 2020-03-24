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
public class CommandStatusTest
{
    private static final Logger log = (Logger) LogManager.getLogger(CommandStatusTest.class.getName());
    private LoginParams _loginParams = null;

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
        _loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
    }

    @Test
    public void Status_GetSetJsonTest() throws Exception
    {
        //{"signal":1,"profile":1,"schedule":"Default"}

        String json = "";
        try {
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            assertNotNull( "Not null " , blueLogin.getSession() );

            CommandStatus commandStatus = new CommandStatus(blueLogin);
            BlueStatus blueStatusStart = commandStatus.GetStatus();

            int signalCurrentInt = 1;
            int profileCurrentInt = 1;
            String scheduleCurrent = "Default";

            Thread.sleep(1000);// give time to  BI to rest

            json = "{\"signal\":" + 2 + ",\"profile\":" + 2 + ",\"schedule\":\"" + scheduleCurrent + "\"}";

            Thread.sleep(2000);// give time to  BI to rest
            BlueStatus blueStatusAfter = commandStatus.SetStatus(json);

            assertNotNull( "Not null blueStatus " ,blueStatusAfter );
            assertTrue( "blueStatusAfter !=signalCurrentInt" ,blueStatusAfter.getSignalInt()!=signalCurrentInt);
            assertTrue( "blueStatusAfter !=profileCurrentInt" ,blueStatusAfter.getActiveProfileInt()!=profileCurrentInt);

            String jsonAtStart = "{\"signal\":1,\"profile\":1,\"schedule\":\"" + scheduleCurrent + "\"}";

            Thread.sleep(2000);// give time to  BI to rest
            BlueStatus blueStatusBackToStart = commandStatus.SetStatus(jsonAtStart);
            assertTrue( "blueStatusAfter !=signalCurrentInt" ,
                    blueStatusBackToStart.getSignalInt()==signalCurrentInt);
            assertTrue( "blueStatusAfter !=profileCurrentInt" ,
                    blueStatusBackToStart.getActiveProfileInt()==profileCurrentInt);

            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
    @Test
    public void GetSetSignalTest() throws Exception
    {
        //TODO fix signal setting
         try {
             String signalRed = new BlueSignals().getSignal(0);
             String signalGreen = new BlueSignals().getSignal(1);

            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            assertNotNull( "Not null " , blueLogin.getSession() );

            CommandStatus commandStatus = new CommandStatus(blueLogin);
            BlueStatus blueStatus = commandStatus.GetStatus();

            int signalCurrentInt = blueStatus.getSignalInt();
             Thread.sleep(1000);// give time to  BI to rest

             if(signalCurrentInt ==1) {
                 blueStatus = commandStatus.SetSignal(0);
                 assertNotNull( "Not null " ,blueStatus );
                 assertTrue( "blueStatus.getSignalInt()==0" ,
                         blueStatus.getSignalInt()==0);
             } else{
                 blueStatus = commandStatus.SetSignal(1);
                 assertNotNull( "Not null " ,blueStatus );
                assertTrue( "blueStatus.getSignalInt()==1" ,blueStatus.getSignalInt()==1);
             }

            Thread.sleep(1000);// give time to  BI to rest
            blueStatus = commandStatus.SetSignal(signalCurrentInt);
            assertNotNull( "Not null blueStatus " ,blueStatus );
            assertTrue( "blueStatus.toJsonString.length()>0" ,blueStatus.getSignalInt()==signalCurrentInt);

            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
