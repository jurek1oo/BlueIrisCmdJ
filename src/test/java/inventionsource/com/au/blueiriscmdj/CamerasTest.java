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

public class CamerasTest
{
    private static final Logger log = (Logger) LogManager.getLogger(CamerasTest.class.getName());

    private String _dataJson = "{\"data\":[\n" +
            "  {\n" +
            "    \"optionDisplay\": \"Ceiling1\",\n" +
            "    \"optionValue\": \"Ceiling1\",\n" +
            "    \"active\": true,\n" +
            "    \"FPS\": 19.98,\n" +
            "    \"color\": 8151097,\n" +
            "    \"ptz\": true,\n" +
            "    \"audio\": false,\n" +
            "    \"width\": 672,\n" +
            "    \"height\": 1008,\n" +
            "    \"newalerts\": 7,\n" +
            "    \"lastalert\": 116177991,\n" +
            "    \"alertutc\": 1582633612,\n" +
            "    \"webcast\": true,\n" +
            "    \"isEnabled\": true,\n" +
            "    \"isOnline\": true,\n" +
            "    \"hidden\": false,\n" +
            "    \"tempfull\": false,\n" +
            "    \"type\": 4,\n" +
            "    \"profile\": 1,\n" +
            "    \"lock\": 0,\n" +
            "    \"pause\": 0,\n" +
            "    \"isPaused\": false,\n" +
            "    \"isRecording\": false,\n" +
            "    \"isManRec\": false,\n" +
            "    \"ManRecElapsed\": 0,\n" +
            "    \"ManRecLimit\": 0,\n" +
            "    \"isYellow\": false,\n" +
            "    \"isMotion\": false,\n" +
            "    \"isTriggered\": false,\n" +
            "    \"isNoSignal\": false,\n" +
            "    \"isAlerting\": false,\n" +
            "    \"nAlerts\": 0,\n" +
            "    \"nTriggers\": 175,\n" +
            "    \"nClips\": 208,\n" +
            "    \"nNoSignal\": 2075,\n" +
            "    \"error\": \"\"\n" +
            "  }\n" +
            "]}";

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
     }

    @Test
    public void CreateCamerasTest() throws Exception {
        try {
            JsonElement jsonElement = (new Gson()).fromJson (_dataJson, JsonElement.class);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement dataElement = jsonObject.get("data");

            Cameras cameras = new Cameras(dataElement);

            assertNotNull("assertNotNull cameras ", cameras);
            assertTrue("size()", cameras.size() > 0);

            Cameras.Camera camera = cameras.get(Constants4Tests.CAM_NAME1);
            assertNotNull("assertNotNull cameras.get(0)", camera);

        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
