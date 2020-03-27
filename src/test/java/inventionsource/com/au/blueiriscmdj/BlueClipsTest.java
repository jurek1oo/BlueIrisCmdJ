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

public class BlueClipsTest
{
    private static final Logger log = (Logger) LogManager.getLogger(BlueClipsTest.class.getName());

    private String _dataJson = "{\n" +
            "  \"result\": \"success\",\n" +
            "  \"session\": \"722152374eac6f8f3bb47552579f0c55\",\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@111375420.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1580545535,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 15283,\n" +
            "      \"filesize\": \"16sec (5.21M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@111331267.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1580469120,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 3921,\n" +
            "      \"filesize\": \"04sec (1.93M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@111321869.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1580469105,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 13958,\n" +
            "      \"filesize\": \"14sec (7.11M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@111271385.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1580450460,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 359,\n" +
            "      \"filesize\": \"00sec (251K)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@111262038.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1580450444,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 15956,\n" +
            "      \"filesize\": \"17sec (7.24M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@106609729.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1580121900,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 9950,\n" +
            "      \"filesize\": \"10sec (3.84M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@106600180.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1580121895,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 4959,\n" +
            "      \"filesize\": \"05sec (2.16M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@104392205.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1580046156,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 18035,\n" +
            "      \"filesize\": \"18sec (11.2M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@102483659.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1579953995,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 19478,\n" +
            "      \"filesize\": \"19sec (15.8M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@79699934.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1579433763,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 14965,\n" +
            "      \"filesize\": \"15sec (10.7M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@75404807.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1579359261,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 19127,\n" +
            "      \"filesize\": \"19sec (9.38M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@61325123.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1579132705,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 15015,\n" +
            "      \"filesize\": \"15sec (10.6M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@61092358.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1579104843,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 18145,\n" +
            "      \"filesize\": \"18sec (10.7M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@59273349.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1579059605,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 14965,\n" +
            "      \"filesize\": \"15sec (5.51M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@59263886.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1579059574,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 18099,\n" +
            "      \"filesize\": \"18sec (6.17M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@57703459.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1579003582,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 16062,\n" +
            "      \"filesize\": \"16sec (11.4M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@52892098.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1578920414,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 14945,\n" +
            "      \"filesize\": \"15sec (8.31M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@46573967.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1578668379,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 17053,\n" +
            "      \"filesize\": \"17sec (10.8M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@46564901.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1578668243,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 17108,\n" +
            "      \"filesize\": \"17sec (12.7M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@34190221.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1578395545,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 13923,\n" +
            "      \"filesize\": \"14sec (9.63M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@24095463.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1578115036,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 15979,\n" +
            "      \"filesize\": \"16sec (10.7M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@22457710.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1578049897,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 16092,\n" +
            "      \"filesize\": \"16sec (13.3M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@18727581.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1577961840,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 4792,\n" +
            "      \"filesize\": \"05sec (2.99M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@18717243.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1577961827,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 12816,\n" +
            "      \"filesize\": \"13sec (13.1M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@4520546.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1577594955,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 13936,\n" +
            "      \"filesize\": \"14sec (8.98M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@418975.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1577468743,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 13963,\n" +
            "      \"filesize\": \"14sec (12.2M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@123029.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1577457916,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 22668,\n" +
            "      \"filesize\": \"23sec (23.9M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@99844.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1577457327,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 15007,\n" +
            "      \"filesize\": \"15sec (15.1M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@32254.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1577438599,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 15059,\n" +
            "      \"filesize\": \"15sec (6.71M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@21601.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1577438558,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 14010,\n" +
            "      \"filesize\": \"14sec (7.98M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@10703.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1577431003,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 15082,\n" +
            "      \"filesize\": \"15sec (10.5M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"camera\": \"Ceiling1\",\n" +
            "      \"path\": \"@0.bvr\",\n" +
            "      \"offset\": 0,\n" +
            "      \"date\": 1577425347,\n" +
            "      \"color\": 8151097,\n" +
            "      \"flags\": 1,\n" +
            "      \"res\": \"2048x1536\",\n" +
            "      \"msec\": 15581,\n" +
            "      \"filesize\": \"16sec (6.69M)\",\n" +
            "      \"filetype\": \"bvr H264 Stored\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
     }

    @Test
    public void CreateClipsTest() throws Exception {
        try {
            JsonElement jsonElement = (new Gson()).fromJson (_dataJson, JsonElement.class);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement dataElement = jsonObject.get("data");

            BlueClips blueClips = new BlueClips(dataElement);

            assertNotNull("assertNotNull cameras ", blueClips);
            assertTrue("size()", blueClips.size() > 0);


        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
