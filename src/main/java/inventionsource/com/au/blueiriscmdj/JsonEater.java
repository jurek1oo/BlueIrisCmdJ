package inventionsource.com.au.blueiriscmdj;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class JsonEater {
    private static final Logger log = (Logger)LogManager.getLogger(JsonEater.class);

    public JsonElement GetDataElement (String jsonStrFromRequest) throws Exception {
        log.debug("response jsonStrFromRequest: " + jsonStrFromRequest);
        JsonObject jsonObject = null;
        String problemMsg = null;

        Gson gson = new GsonBuilder().create();
        jsonObject = (gson.fromJson (jsonStrFromRequest, JsonElement.class)).getAsJsonObject();
        JsonElement jsonDataElement = jsonObject.get("data");
        if (jsonDataElement== null) {
            log.debug("got NULL data element." );
        } else {
            log.debug("got data element OK." );
        }
       return jsonDataElement;
    }
/*
    public static String GetResultElement (String jsonStrFromRequest, boolean hasToBeSuccess) throws Exception {
        log.debug("hasToBeSuccess: " + hasToBeSuccess + " jsonStrFromRequest: " + jsonStrFromRequest);

        if (hasToBeSuccess && !CheckResultSuccess (jsonStrFromRequest) ) {
            return "Response result is NOT success, and it has to be.";
        }
        return null;
        //Gson gson = new GsonBuilder().create();
        //return (gson.fromJson (jsonStrFromRequest, JsonElement.class)).getAsJsonObject();
    }
*/
    public static JsonObject GetJsonObject(String responseStr) {
        Gson gson = new GsonBuilder().create();
        return (gson.fromJson (responseStr, JsonElement.class)).getAsJsonObject();
    }

    public static boolean CheckResultSuccess (String jsonStrFromRequest, boolean hasToBeSuccess) throws Exception {
        log.debug("hasToBeSuccess: " + hasToBeSuccess + " jsonStrFromRequest: " + jsonStrFromRequest);
        if (hasToBeSuccess) {
            return CheckResultSuccess(jsonStrFromRequest);
        } else {
            return true;
        }
    }

    private static boolean CheckResultSuccess (String jsonStrFromRequest) throws Exception {
        log.debug(" jsonStrFromRequest: " + jsonStrFromRequest);
        Gson gson = new GsonBuilder().create();

        JsonElement jsonElement = gson.fromJson (jsonStrFromRequest, JsonElement.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String cmdResult = jsonObject.get("result").getAsString();

        if(cmdResult==null ) {
            throw new Exception("cmdResult result is null, not present.");
        }
        if(cmdResult.compareTo("success") != 0) {
            log.debug("got result = fail.");
            return false;
        } else {
            log.debug("got result = success." );
            return true;
        }
    }
}
