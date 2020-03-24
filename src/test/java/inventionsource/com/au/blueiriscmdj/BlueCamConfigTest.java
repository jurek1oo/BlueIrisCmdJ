package inventionsource.com.au.blueiriscmdj;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BlueCamConfigTest
{
    private static final Logger log = (Logger) LogManager.getLogger(BlueCamConfigTest.class.getName());

    private String _dataJson = "{\n" +
            "  \"result\": \"success\",\n" +
            "  \"session\": \"3b2873501b1d685863782e5072d46882\",\n" +
            "  \"data\": {\n" +
            "    \"profile\": 0,\n" +
            "    \"lock\": 0,\n" +
            "    \"pause\": 0,\n" +
            "    \"push\": false,\n" +
            "    \"audio\": true,\n" +
            "    \"motion\": true,\n" +
            "    \"schedule\": false,\n" +
            "    \"ptzcycle\": false,\n" +
            "    \"ptzevents\": false,\n" +
            "    \"alerts\": 0,\n" +
            "    \"output\": false,\n" +
            "    \"setmotion\": {\n" +
            "      \"audio_trigger\": false,\n" +
            "      \"audio_sense\": 10000,\n" +
            "      \"usemask\": true,\n" +
            "      \"sense\": 10480,\n" +
            "      \"contrast\": 40,\n" +
            "      \"showmotion\": 2,\n" +
            "      \"shadows\": true,\n" +
            "      \"luminance\": false,\n" +
            "      \"objects\": true,\n" +
            "      \"maketime\": 10,\n" +
            "      \"breaktime\": 100\n" +
            "    },\n" +
            "    \"record\": 2\n" +
            "  }\n" +
            "}";

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
     }

    @Test
    public void GetCamConfigHelpTest() throws Exception {
        try {
            JsonElement jsonElement = (new Gson()).fromJson (_dataJson, JsonElement.class);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement dataElement = jsonObject.get("data");

            BlueCamConfig blueCamConfig = new BlueCamConfig(dataElement, "Ceiling1");

            log.info(blueCamConfig.setJsonHelp());
            assertNotNull("assertNotNull cameras ", blueCamConfig);

        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
    @Test
    public void CreateCamerasTest() throws Exception {
        try {
            JsonElement jsonElement = (new Gson()).fromJson (_dataJson, JsonElement.class);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement dataElement = jsonObject.get("data");

            BlueCamConfig blueCamConfig = new BlueCamConfig(dataElement, "Ceiling1");
            assertNotNull("assertNotNull blueCamConfig", blueCamConfig);

        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
