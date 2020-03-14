package inventionsource.com.au.blueiriscmdj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.Calendar;
import java.util.TimeZone;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class Utils {
    private static final Logger log = (Logger) LogManager.getLogger(BlueCmdRequest.class);


    public static long GetSecondsFromDateSql(String sqldate) {
        // 2020-03-27
        long secondsSinceEpoch =0;
        try {
            if (sqldate ==  null || sqldate.trim().length() < 10)
                throw new Exception( "Date too short");
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.clear();
            log.debug("sqldate: " + sqldate + " y: " + sqldate.substring(0,4) + " m: " +
                    sqldate.substring(5,7) + " d: " + sqldate.substring(8,10));
            calendar.set(Integer.parseInt(sqldate.substring(0,4)),
                    Integer.parseInt(sqldate.substring(5,7))-1,
                    Integer.parseInt(sqldate.substring(8,10)));
           // calendar.set(2020, Calendar.OCTOBER, 1);
            secondsSinceEpoch = calendar.getTimeInMillis() / 1000L;
        }catch(Exception e){
            String msg = "Error date format. expected yyyy-mm-dd (e.g. 2020-03-27). sqldate: " + sqldate + " ";
            log.error(msg + e.toString());
            return -1;
        }
        return secondsSinceEpoch;
    }

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
