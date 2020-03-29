package inventionsource.com.au.blueiriscmdj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class Utils {
    private static final Logger log = (Logger) LogManager.getLogger(Utils.class);

    public static String GetDefaultTimeZoneName() {
        return "ID: " + TimeZone.getDefault().getID() + " : " + TimeZone.getDefault().getDisplayName();
    }

    public static String DateStringNow() {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }

    public static String DateStringNow(int extraDays) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, extraDays);
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(c.getTime());
    }

    public static String CorrectDateString(String datein) throws Exception {
        if (datein == null || datein.trim().length()< 10) throw new Exception("Error date to short: " + datein);
        datein = datein.trim();
        String dateTime = " 00:00:00";
        // 2020-03-28 00:00:00
        // 1234567890123456789
        int len = datein.length();
        String dateAdd = dateTime.substring(len-10,9);
        return  datein + dateAdd;

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

    public static String GetLocalDateTimeStrFromSeconds(long dateInSeconds) {
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
            String dateout = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return dateout;
        } catch (Exception e){
            log.error("Error converting seconds to date: " + dateInSeconds + ".\n" + e.toString());
            return null;
        }
     }

    public static long GetSecondsFromDateSql(String sqldate) {
        log.debug("Using sqldate: " +sqldate);
        String correctSqlDate = "";
        // empty or null sqldate will return 0 seconds.
        //   2020-03-27 23:05:00
        long secondsSinceEpoch =0;
        try {
            if (sqldate ==  null || sqldate.trim().length() == 0) {
                    log.info("Setting start date to 1970-01-01 00:00:00.");
                    return 0;
            }
            correctSqlDate =  Utils.CorrectDateString(sqldate);

            log.debug("Using timezone: " + TimeZone.getDefault().getID() +
                    " : " + TimeZone.getDefault().getDisplayName());
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.clear();
            log.debug("correctSqlDate: " + correctSqlDate + " y: " + correctSqlDate.substring(0,4) + " m: " +
                    correctSqlDate.substring(5,7) + " d: " + correctSqlDate.substring(8,10)+ " h: " +
                    correctSqlDate.substring(11,13) + " min: " + correctSqlDate.substring(14,16) +
                    " sec: " + correctSqlDate.substring(17,19));
            calendar.set(Integer.parseInt(correctSqlDate.substring(0,4)),
                    Integer.parseInt(correctSqlDate.substring(5,7))-1,
                            Integer.parseInt(correctSqlDate.substring(8,10)),
                            Integer.parseInt(correctSqlDate.substring(11,13)),
                            Integer.parseInt( correctSqlDate.substring(14,16)),
                            Integer.parseInt( correctSqlDate.substring(17,19)));
            secondsSinceEpoch = calendar.getTimeInMillis() / 1000L;
            if (secondsSinceEpoch <0 && TimeZone.getDefault().getID().compareTo("UTC") != 0) {
                 log.warn("Warn. your zone is not UTC. Set 1970-01-01 00:00:00 to proper hour, 0 is not correct secondsSinceEpoch: " +
                        secondsSinceEpoch + " in hours: " + secondsSinceEpoch/3600);
                secondsSinceEpoch = 0;// 1970-01-01 in not utc zone, can NOT be negative.
            }
        } catch (Exception e){
            String msg = "Error date format. expected yyyy-mm-dd hh:mm:ss (e.g. 2020-03-27 23:05:00). was: " + sqldate + ". ";
            log.error(msg + e.toString());
            return -1;
        }
        return secondsSinceEpoch;
    }

    public static String GetPrettyJsonString(JsonElement jsonElement) {
        if (jsonElement != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(jsonElement);
        } else {
            return "{}";
        }
    }

    public static String GetPrettyJsonString(JsonObject jsonObject) {
        if (jsonObject != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(jsonObject);
        } else {
            return "{}";
        }
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
