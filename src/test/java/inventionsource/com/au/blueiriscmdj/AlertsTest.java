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

public class AlertsTest
{
    private static final Logger log = (Logger) LogManager.getLogger(AlertsTest.class.getName());
    private static final String _rsponseAlerts = "{\"result\":\"success\",\"session\":\"4d1a48541c485ac42d8366625dd6105c\",\"data\":[{\"camera\":\"Front-Watashi\",\"newalerts\":5,\"path\":\"@115940681.bvr\",\"clip\":\"@115932197.bvr\",\"offset\":0,\"flags\":196608,\"res\":\"720x1280\",\"zones\":1,\"date\":1584494632,\"color\":8151097,\"filesize\":\"16sec (601K)\"},{\"camera\":\"Front-Watashi\",\"path\":\"@115916211.bvr\",\"clip\":\"@115908161.bvr\",\"offset\":0,\"flags\":196608,\"res\":\"720x1280\",\"zones\":1,\"date\":1584493121,\"color\":8151097,\"filesize\":\"36sec (619K)\"},{\"camera\":\"Front-Watashi\",\"path\":\"@115876070.bvr\",\"clip\":\"@115868168.bvr\",\"offset\":0,\"flags\":196608,\"res\":\"720x1280\",\"zones\":1,\"date\":1584491458,\"color\":8151097,\"filesize\":\"02m38s (590K)\"},{\"camera\":\"Front-Watashi\",\"path\":\"@115852705.bvr\",\"clip\":\"@115844665.bvr\",\"offset\":0,\"flags\":196608,\"res\":\"720x1280\",\"zones\":1,\"date\":1584490846,\"color\":8151097,\"filesize\":\"17sec (577K)\"},{\"camera\":\"Front-Watashi\",\"path\":\"@115836927.bvr\",\"clip\":\"@115829140.bvr\",\"offset\":0,\"flags\":196608,\"res\":\"720x1280\",\"zones\":1,\"date\":1584489690,\"color\":8151097,\"filesize\":\"13sec (587K)\"},{\"camera\":\"Ceiling1\",\"newalerts\":2,\"path\":\"@115821323.bvr\",\"clip\":\"@115815775.bvr\",\"offset\":0,\"flags\":196608,\"res\":\"2048x1536\",\"zones\":1,\"date\":1584467862,\"color\":8151097,\"filesize\":\"11sec\"},{\"camera\":\"Ceiling1\",\"path\":\"@115807474.bvr\",\"clip\":\"@115799190.bvr\",\"offset\":0,\"flags\":196608,\"res\":\"2048x1536\",\"zones\":1,\"date\":1584467484,\"color\":8151097,\"filesize\":\"11sec\"}]}";

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
     }

    @Test
    public void CreateAlertsTest() throws Exception {
        try {
            JsonElement jsonElement = (new Gson()).fromJson (_rsponseAlerts, JsonElement.class);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement dataElement = jsonObject.get("data");

            Alerts alerts = new Alerts(dataElement);

            assertNotNull("assertNotNull alerts ", alerts);
            assertTrue("size()", alerts.size() ==7);

            Alerts.Alert alert = alerts.get(0);
            assertNotNull("assertNotNull alert.get(0)", alert);

        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
