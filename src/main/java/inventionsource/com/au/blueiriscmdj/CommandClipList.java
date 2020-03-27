package inventionsource.com.au.blueiriscmdj;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandClipList {
    /*
    cliplist
       Get a list of clips from the New folder
       camera: a camera's short name or a group name; "index" will return clips from all cameras
       startdate: expressed as the integer number of seconds since January 1, 1970
       enddate: expressed as the integer number of seconds since January 1, 1970
       tiles: true or false; true to send only 1 entry per day in order to mark tiles on the calendar
       The returned data value is an array of JSON objects each describing a camera or a camera group.
       For each of these objects, the following values are defined:
           camera: the camera or group name
           path: the part of the absolute file path that follows the New clips folder path;
                   if there are no subfolders, this is simply \ and the filename.
           date: file creation date, expressed as the integer number of seconds since January 1, 1970
           color: 24-bit RGB value (red least significant) representing the camera's display color
        */
    private static final Logger log = (Logger)LogManager.getLogger(CommandClipList.class);

    private BlueLogin _blueLogin = null;
    public BlueLogin getBlueLogin() { return _blueLogin; }

    public CommandClipList(BlueLogin blueLogin) throws Exception {
        _blueLogin = blueLogin;
    }
    public BlueClips GetClips(String json) throws Exception {
        String dateNow = Utils.DateStringNow();

        String camera = "index";
        String startDateUsedStr = "1970-01-02 00;00:00";
        String endDateUsedStr = dateNow;;
        long startDate = 0;
        long endDate = Utils.GetSecondsFromDateSql(dateNow);;
        boolean tiles = false;
        try {
            if(json!=null && json.trim().length()>2) {
                if (!Utils.isJSONValid(json)) throw new Exception("Error. invalid json");
                JsonElement jsonElement = (new Gson()).fromJson(json, JsonElement.class);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                try {
                    camera = jsonObject.get("camera").getAsString();
                } catch (Exception e) {
                    log.warn("Warn camera not present, used: index");
                }
                try {
                    startDateUsedStr = Utils.CorrectDateString(jsonObject.get("startdate").getAsString());
                    startDate = Utils.GetSecondsFromDateSql(startDateUsedStr);
                } catch (Exception e) {
                    log.warn("Warn startdate not present or invalid, used: 1970-01-01 00:00");
                }
                try {
                    endDateUsedStr = Utils.CorrectDateString(jsonObject.get("enddate").getAsString());
                    endDate = Utils.GetSecondsFromDateSql(endDateUsedStr);
                } catch (Exception e) {
                    log.warn("Warn. enddate not present or invalid, used: " + dateNow);
                }
                try {
                    tiles = jsonObject.get("tiles").getAsBoolean();
                } catch (Exception e) {
                    log.warn("Warn tiles not present, replaces by false");
                }
            } else {
                log.warn("Warn. Empty json, used: camera index, startdate 1970-01-02 00:00:00, enddate : " +
                        dateNow + ", tiles: false");
            }

            log.info("Used :camera: "+ camera + " startDate: " + startDateUsedStr + " endDate: " + endDateUsedStr+
                    " tiles: " + tiles);
            return GetClips( camera,  startDate,  endDate,  tiles);
        } catch (Exception e) {
            log.error("Error executing for BlueIris json:\n" + json
                    + BlueClips.setJsonHelp() +"\n\n", e);
            throw e;
        }
    }

    public BlueClips GetClips(String camera, long startDate, long endDate, boolean tiles) throws Exception {
        // camera: a camera's short name or a group name; "index" will return clips from all cameras
        // startdate: expressed as the integer number of seconds since January 1, 1970
        // enddate: expressed as the integer number of seconds since January 1, 1970
        // tiles: true or false
        String msg = "Camera: " + camera + " startDate: " +startDate +
             " endDate: " +endDate + " tiles: " +tiles ;
        log.debug(msg);
        String cmd = "cliplist";
        String cmdParams = ",\"camera\":\"" + camera + "\",\"startDate\":" + startDate +
                ",\"endDate\":" + endDate +",\"tiles\":" + tiles;
        try {
            boolean hasToBeSuccess = true;
            boolean getDataElement = true;
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);

            JsonElement jsonDataElement = commandCoreRequest.RunTheCmd(cmd, cmdParams,hasToBeSuccess,getDataElement);

            BlueClips blueClips = new BlueClips(jsonDataElement) ;
            log.debug("blueClipList: " + blueClips.toString());
            return blueClips;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n" + "\n\n", e);
            throw e;
        }
    }
}