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
