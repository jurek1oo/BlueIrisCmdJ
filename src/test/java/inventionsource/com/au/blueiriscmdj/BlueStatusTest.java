package inventionsource.com.au.blueiriscmdj;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BlueStatusTest
{
    private static final Logger log = (Logger) LogManager.getLogger(BlueStatusTest.class.getName());

    private String _dataJson = "{\n" +
            "  \"result\": \"fail\",\n" +
            "  \"session\": \"38c33cb457776db1261a2a085bf51f86\",\n" +
            "  \"data\": {\n" +
            "    \"signal\": \"1\",\n" +
            "    \"cxns\": 1,\n" +
            "    \"cpu\": 21,\n" +
            "    \"mem\": \"581.8M\",\n" +
            "    \"memfree\": \"858.9M\",\n" +
            "    \"memload\": \"78%\",\n" +
            "    \"disks\": [\n" +
            "      {\n" +
            "        \"disk\": \"C:\",\n" +
            "        \"allocated\": 2048,\n" +
            "        \"used\": 1601,\n" +
            "        \"free\": 411534,\n" +
            "        \"total\": 460911\n" +
            "      },\n" +
            "      {\n" +
            "        \"disk\": \"F:\",\n" +
            "        \"allocated\": 819200,\n" +
            "        \"used\": 44513,\n" +
            "        \"free\": 995184,\n" +
            "        \"total\": 1907493\n" +
            "      }\n" +
            "    ],\n" +
            "    \"profile\": 1,\n" +
            "    \"lock\": \"0\",\n" +
            "    \"schedule\": \"Default\",\n" +
            "    \"dio\": [\n" +
            "      0,\n" +
            "      0,\n" +
            "      0,\n" +
            "      0,\n" +
            "      0,\n" +
            "      0,\n" +
            "      0,\n" +
            "      0\n" +
            "    ],\n" +
            "    \"uptime\": \"7:22:30:46\",\n" +
            "    \"clips\": \"Clips: 14054 files, 45.0G/802.0G; C: +401.4G, F: +215.3G\",\n" +
            "    \"warnings\": \"9\",\n" +
            "    \"alerts\": \"5\",\n" +
            "    \"tzone\": \"420\"\n" +
            "  }\n" +
            "}\n";

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
        new Constants4Tests();
    }

    @Test
    public void CreateBlueStatusTest() throws Exception {
        try {
            JsonElement jsonElement = (new Gson()).fromJson (_dataJson, JsonElement.class);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement dataElement = jsonObject.get("data");

            BlueStatus blueStatus = new BlueStatus(dataElement);
            assertNotNull("assertNotNull blueStatus", blueStatus);

        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
