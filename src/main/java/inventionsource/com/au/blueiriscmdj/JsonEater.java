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
    private static final Logger log = (Logger)LogManager.getLogger(BlueCmdRequest.class);

    public static JsonElement GetDataElement (String jsonStrFromRequest, boolean hasToBeSuccess) throws Exception {
        log.debug("hasToBeSuccess: " + hasToBeSuccess + " jsonStrFromRequest: " + jsonStrFromRequest);

        JsonObject jsonObject = GetResultElement(jsonStrFromRequest, hasToBeSuccess);
        JsonElement dataElement = jsonObject.get("data");
        if (dataElement== null) {
            throw new Exception("Error no data element in response json");
        }
        log.debug("got data element OK." );
        return dataElement;
    }

    public static JsonObject GetResultElement (String jsonStrFromRequest, boolean hasToBeSuccess) throws Exception {
        log.debug("hasToBeSuccess: " + hasToBeSuccess + " jsonStrFromRequest: " + jsonStrFromRequest);
        Gson gson = new GsonBuilder().create();

        JsonElement jsonElement = gson.fromJson (jsonStrFromRequest, JsonElement.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String cmdResult = jsonObject.get("result").getAsString();

        if(cmdResult==null ) {
            throw new Exception("cmdResult cmdResult is null");
        }
        if(hasToBeSuccess && cmdResult.compareTo("success") != 0) {
            throw new Exception("cmdResult cmdResult is NOT success");
        }

        log.debug("got result OK. hasToBeSuccess: " + hasToBeSuccess);
        return jsonObject;
    }
}
