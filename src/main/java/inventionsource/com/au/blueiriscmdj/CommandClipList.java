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

    private static final Logger log = (Logger)LogManager.getLogger(CommandClipList.class);

    private BlueLogin _blueLogin = null;
    public BlueLogin getBlueLogin() { return _blueLogin; }

    public CommandClipList(BlueLogin blueLogin) throws Exception {
        _blueLogin = blueLogin;
    }
    public BlueClips GetClips(String json) throws Exception {
        String dateNow = Utils.DateStringNow();

        String camera = "index";
        String startDateUsedStr = "1970-01-02 00:00:00";
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
                    log.warn("Warn startdate not present or invalid, used: "+ startDateUsedStr);
                }
                try {
                    endDateUsedStr = Utils.CorrectDateString(jsonObject.get("enddate").getAsString());
                    endDate = Utils.GetSecondsFromDateSql(endDateUsedStr);
                } catch (Exception e) {
                    log.warn("Warn. enddate not present or invalid, used: " + endDateUsedStr);
                }
                try {
                    tiles = jsonObject.get("tiles").getAsBoolean();
                } catch (Exception e) {
                    log.warn("Warn tiles not present, replaces by: " + tiles);
                }
            } else {
                log.warn("Warn. Empty json, used: camera index, startdate: " + startDateUsedStr + " enddate : " +
                        endDateUsedStr + ", tiles: " + tiles);
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

            BlueClips blueClips = new BlueClips(jsonDataElement, startDate, endDate ) ;
            log.debug("blueClipList: " + blueClips.toString());
            return blueClips;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n" + "\n\n", e);
            throw e;
        }
    }
}