package inventionsource.com.au.blueiriscmdj;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

public class BlueLogsTest
{
    private static final Logger log = (Logger) LogManager.getLogger(BlueLogsTest.class.getName());

    private String _dataJson = "\n" +
            "  {\n" +
            "  \"result\": \"success\",\n" +
            "  \"session\": \"1c2c0ebc19d6052f601d4ba840335fb3\",\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"date\": 1585372230,\n" +
            "      \"level\": 10,\n" +
            "      \"obj\": \"admin\",\n" +
            "      \"msg\": \"192.168.1.36: Login\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"date\": 1585372230,\n" +
            "      \"count\": \"4543\",\n" +
            "      \"level\": 10,\n" +
            "      \"obj\": \"Server\",\n" +
            "      \"msg\": \"Connected: 192.168.1.36\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"date\": 1585372201,\n" +
            "      \"count\": \"4\",\n" +
            "      \"level\": 10,\n" +
            "      \"obj\": \"Anonymous\",\n" +
            "      \"msg\": \"192.168.1.36: Logout, 1:33\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"date\": 1585372184,\n" +
            "      \"count\": \"7948\",\n" +
            "      \"level\": 10,\n" +
            "      \"obj\": \"admin\",\n" +
            "      \"msg\": \"192.168.1.39: Logout, 0:01\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"date\": 1585372173,\n" +
            "      \"count\": \"45\",\n" +
            "      \"level\": 3,\n" +
            "      \"obj\": \"Front-Watashi\",\n" +
            "      \"msg\": \"MOTION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"date\": 1585372144,\n" +
            "      \"count\": \"253976\",\n" +
            "      \"level\": 1,\n" +
            "      \"obj\": \"Clips\",\n" +
            "      \"msg\": \"MoveFile 82: C:\\\\Users\\\\Jurek\\\\Dropbox\\\\BI\\\\New\\\\Front-Watashi.20200327_100803.bvr\"\n" +
            "     }\n" +
            "  ]}\n";

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
        new Constants4Tests();
    }

    @Test
    public void CreateLogsTest() throws Exception {
        try {
            JsonElement jsonElement = (new Gson()).fromJson (_dataJson, JsonElement.class);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement dataElement = jsonObject.get("data");

            BlueLogs blueLogs = new BlueLogs(dataElement,0);

            assertNotNull("assertNotNull cameras ", blueLogs);
            assertTrue("size()", blueLogs.size() > 0);


        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
