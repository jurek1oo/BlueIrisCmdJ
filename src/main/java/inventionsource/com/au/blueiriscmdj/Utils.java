package inventionsource.com.au.blueiriscmdj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.TimeZone;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class Utils {
    private static final Logger log = (Logger) LogManager.getLogger(Utils.class);

    public static String GetDefaultTimeZoneName() {
        return "ID: " + TimeZone.getDefault().getID() + " : " + TimeZone.getDefault().getDisplayName();
    }

    public static boolean isJSONValid(String jsonInString) {
        try {
            Gson gson = new Gson();
            gson.fromJson(jsonInString, Object.class);
            if (!jsonInString.contains("\":")) {
                throw new Exception("Error. Element names are not double quoted." +
                        "Enclose json with single quotes.\n");
            }
            return true;
        } catch(Exception ex) {
            return false;
        }
    }

    public static LocalDateTime GetLocalDateTimeFromSeconds(long dateInSeconds) {
        log.debug("Using dateInSeconds: " +dateInSeconds);
                LocalDateTime localDateTime = null;
        try {
            if (dateInSeconds < 0) {
                  throw new Exception( "Wrong Date in seconds.");
            }
            log.debug("Using date/time ZoneId: " + ZoneId.systemDefault().getId() +
                    " : " + ZoneId.systemDefault().toString());
            OffsetDateTime odt = OffsetDateTime.now ( ZoneId.systemDefault () );
            ZoneOffset zoneOffset = odt.getOffset ();
            localDateTime = LocalDateTime.ofEpochSecond(dateInSeconds, 0, zoneOffset);

        } catch (Exception e){
            log.error("Error converting seconds to date: " + dateInSeconds + ".\n" + e.toString());
            return null;
        }
        return localDateTime;
    }

    public static long GetSecondsFromDateSql(String sqldate) {
        log.debug("Using sqldate: " +sqldate);
        // empty or null sqldate will return 0 seconds.
        //   2020-03-27 23:05
        //or 2020-03-27
        //   1234567890123456
        //
        long secondsSinceEpoch =0;
        try {
            if (sqldate ==  null || sqldate.trim().length() < 16) {
                // date part only
                if (sqldate !=  null && sqldate.trim().length() == 10) {
                    log.info("Setting time to hour:minute= 00:00");
                    sqldate = sqldate + " 00:00";
                } else if (sqldate ==  null || sqldate.trim().length() == 0) {
                    log.info("Setting start date to 1970-01-01 00:00.");
                    return 0;
                } else {
                    throw new Exception( "Date time too short.");
                }
            }

            if (sqldate ==  null || sqldate.trim().length() > 16)
                throw new Exception( "Date too long.");
            log.debug("Using timezone: " + TimeZone.getDefault().getID() +
                    " : " + TimeZone.getDefault().getDisplayName());
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.clear();
            log.debug("sqldate: " + sqldate + " y: " + sqldate.substring(0,4) + " m: " +
                    sqldate.substring(5,7) + " d: " + sqldate.substring(8,10)+ " h: " +
            sqldate.substring(11,13) + " min: " + sqldate.substring(14,16));
            calendar.set(Integer.parseInt(sqldate.substring(0,4)),
                    Integer.parseInt(sqldate.substring(5,7))-1,
                    Integer.parseInt(sqldate.substring(8,10)),
                            Integer.parseInt(sqldate.substring(11,13)) ,
                            Integer.parseInt( sqldate.substring(14,16)),0);
            secondsSinceEpoch = calendar.getTimeInMillis() / 1000L;
            if (secondsSinceEpoch <0 && TimeZone.getDefault().getID().compareTo("UTC") != 0) {
                 log.warn("Warn. your zone is not UTC. Set 1970-01-01 00:00:00 to proper hour, 0 is not correct secondsSinceEpoch: " +
                        secondsSinceEpoch + " in hours: " + secondsSinceEpoch/3600);
                secondsSinceEpoch = 0;// 1970-01-01 in not utc zone, can NOT be negative.
            }
        } catch (Exception e){
            String msg = "Error date format. expected yyyy-mm-dd hh:mm (e.g. 2020-03-27 23:05). was: " + sqldate + ". ";
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
        return "{\"session\": \"" + session + "\", \"response\": \"" + md5HexResponse + "\"" +
                ", \"cmd\": \"" + cmd + "\"}";
    }
}
