package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UtilsTest
{
    private static final Logger log = (Logger) LogManager.getLogger(UtilsTest.class.getName());

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
     }

     //GetSecondsFromDateSql

    @Test
    public void GetSecondsFromDateSql() {
        String date = "2020-03-27";
        long expectedResponse = 0;
        try {
            long result = Utils.GetSecondsFromDateSql(date);

            assertTrue("GetSecondsFromDateSql > 0 ", result > 0);
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
    @Test
    public void Md5HexResponse5Test() {
        String session = "5b29593655f0117b5e36214f072b3e9f";
        String expectedResponse ="757b6684fbab0b4c3574f3eaf7c8b453";
        log.info("Md5HexResponse5Test() : " );
        log.debug("Md5HexResponse5Test() : " );


        try {
            String md5HexResponse = Utils.Md5HexResponse("admin", session, "password");

            assertNotNull("assertNotNull md5Hex ", md5HexResponse);
            assertTrue("md5Hex.length() > 0 ", md5HexResponse.length() > 0);
            //assertTrue("md5HexResponse.indexOf(expectedResponse) >= 0", md5HexResponse.indexOf(expectedResponse) >= 0);
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
