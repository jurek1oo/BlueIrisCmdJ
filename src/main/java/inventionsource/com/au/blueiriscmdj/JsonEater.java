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

    public static JsonElement GetDataElement (String jsonStrFromRequest, boolean hasToBeSuccess) throws Exception {
        log.debug("hasToBeSuccess: " + hasToBeSuccess + " jsonStrFromRequest: " + jsonStrFromRequest);

        if (hasToBeSuccess && !CheckResultSuccess (jsonStrFromRequest) ) {
            throw new Exception("result is NOT success");
        }
        JsonObject jsonObject = GetResultElement(jsonStrFromRequest, hasToBeSuccess);
        JsonElement dataElement = jsonObject.get("data");
        if (dataElement== null) {
            log.debug("got NULL data element." );
        }
        log.debug("got data element OK." );
        return dataElement;
    }

    public static JsonObject GetResultElement (String jsonStrFromRequest, boolean hasToBeSuccess) throws Exception {
        log.debug("hasToBeSuccess: " + hasToBeSuccess + " jsonStrFromRequest: " + jsonStrFromRequest);

        if (hasToBeSuccess && !CheckResultSuccess (jsonStrFromRequest) ) {
            throw new Exception("result is NOT success");
        }
        Gson gson = new GsonBuilder().create();
        return (gson.fromJson (jsonStrFromRequest, JsonElement.class)).getAsJsonObject();
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
            log.debug("got result fail.");
            return false;
        } else {
            log.debug("got result success." );
            return true;
        }
    }
}
