package inventionsource.com.au.blueiriscmdj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class Utils {
    private static final Logger log = (Logger) LogManager.getLogger(BlueCmdRequest.class);


    public static String GetPrettyJsonString(JsonElement jsonElement) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonElement);
    }

    public static String GetPrettyJsonString(JsonObject jsonObject) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    public static String Md5HexResponse(String user, String session, String password) {
        String md5HexResponse = null;
        try {
            String response = user + ":" + session + ":" + password;
            md5HexResponse = DigestUtils.md5Hex(response);
        } catch (Exception e) {
            log.error("Exception:  user: " + user + " session: " + session + " password.length(): " +
                    password.length(), e);
        }
        return md5HexResponse;
    }

    public static String MakeUrl(String host) {
        return "http://" + host + "/json";
    }

    public static String MakeCmdJson(String cmd, String session, String md5HexResponse) {
        return "{\"cmd\":\"" + cmd + "\",\"session\":\"" +
                session + "\",\"response\":\"" + md5HexResponse + "\"}";
    }
}
