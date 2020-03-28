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
public class CommandClipListTest
{
    private static final Logger log = (Logger) LogManager.getLogger(CommandClipListTest.class.getName());
    private LoginParams _loginParams = null;

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
        _loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
    }

    @Test
    public void GetClipsTest() throws Exception {
        try {
            String dateNow = Utils.DateStringNow();

            Thread.sleep(1000);
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            assertNotNull( "Not null " , blueLogin.getSession() );
            CommandClipList commandClipList = new CommandClipList(blueLogin);
            BlueClips blueClips = commandClipList.GetClips(
                    Constants4Tests.CAM_NAME2,0,
                    Utils.GetSecondsFromDateSql(dateNow),false);
            assertNotNull( "Not null blueClipList " , blueClips);
            assertTrue( "blueClips.size()>0" ,blueClips.size()>0 );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }


    @Test
    public void GetClipsJustOneTest() throws Exception {
/*{
"camera":"Front-Watashi"
"path":"@116993071.bvr"
"offset":0
"date":1585224065
"localdatetime":2020-03-26T19:01:05
"color":8151097
"flags":0
"res":"720x1280"
"msec":14471
"filesize":"17sec (1.65M)"
"filetype":"bvr H264 New"
} */
        try {
            String dateNow = Utils.DateStringNow();

            //1585224065
            long startdatesec = 1585224000;
            long enddatesec   = 1585224120;
            Thread.sleep(1000);
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            assertNotNull( "Not null " , blueLogin.getSession() );
            CommandClipList commandClipList = new CommandClipList(blueLogin);
            BlueClips blueClips = commandClipList.GetClips(
                    Constants4Tests.CAM_NAME2, startdatesec, enddatesec,false);
            assertNotNull( "Not null blueClipList " , blueClips);
            assertTrue( "blueClips.size()>0" ,blueClips.size()>0 );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
    @Test
    public void GetClipsByJsonGoodTest() throws Exception {
        try {
            String dateStr = Utils.DateStringNow();
            Thread.sleep(1000);

            String json = "{\"camera\":\"" + Constants4Tests.CAM_NAME2 + "\"," +
                        "\"startdate\":\"1970-01-02\",\"enddate\":\"" + dateStr+  "\",\"tiles\":false}";
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            assertNotNull( "Not null " , blueLogin.getSession() );
            CommandClipList commandClipList = new CommandClipList(blueLogin);
            BlueClips blueClips = commandClipList.GetClips(json);
            assertNotNull( "Not null blueClipList " , blueClips);
            assertTrue( "blueClips.size()>0" ,blueClips.size()>0 );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void GetClipsByJsonJustCamera() throws Exception {
        try {
            Thread.sleep(1000);
            String json = "{\"camera\":\"" + Constants4Tests.CAM_NAME2 + "\"}";
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            assertNotNull( "Not null " , blueLogin.getSession() );
            CommandClipList commandClipList = new CommandClipList(blueLogin);
            BlueClips blueClips = commandClipList.GetClips(json);
            assertNotNull( "Not null blueClipList " , blueClips);
            assertTrue( "blueClips.size()>0" ,blueClips.size()>0 );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }

    @Test
    public void GetClipsByEmptyJson() throws Exception {
        try {
            Thread.sleep(1000);
            String json = null;// all cams all dates
            BlueLogin blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            assertNotNull( "Not null " , blueLogin.getSession() );
            CommandClipList commandClipList = new CommandClipList(blueLogin);
            BlueClips blueClips = commandClipList.GetClips(json);
            assertNotNull( "Not null blueClipList " , blueClips);
            assertTrue( "blueClips.size()>0" ,blueClips.size()>0 );
            blueLogin.BlueIrisLogout();
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
