package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RequestHttpTest
{
    private static final Logger log = (Logger) LogManager.getLogger(RequestHttpTest.class.getName());
    private LoginParams _loginParams = null;

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
        _loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
    }

    @Test
    public void PostRequestTest() throws IOException {
        String host = _loginParams.getHost();
        String url = "http://"+host+"/json";

        RequestHttp requestHttp = new RequestHttp();
        String jsonData = "{\"cmd\": \"login\"}";

        try {
            String result = requestHttp.PostRequest(url, jsonData);

            assertNotNull( "Not null" ,result );
            assertTrue( "result.length()>0" ,result.length()>0 );
            assertTrue( "indexOf(session)>=0 " ,result.indexOf("session")>=0 );
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
