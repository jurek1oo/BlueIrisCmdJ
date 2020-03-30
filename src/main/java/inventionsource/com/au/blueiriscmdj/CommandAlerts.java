package inventionsource.com.au.blueiriscmdj;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandAlerts {

    private static final Logger log = (Logger)LogManager.getLogger(CommandAlerts.class);

    private BlueLogin _blueLogin = null;
    public BlueLogin getBlueLogin() { return _blueLogin; }
    private String _problemMsg = null;
    public String getProblemMsg() {        return _problemMsg;    }

    public CommandAlerts(BlueLogin blueLogin) throws Exception {
        _blueLogin = blueLogin;
    }

    public BlueAlerts GetAlertsList(String json) throws Exception {
        String cmd = "alertlist";
        String msg= "cmd: " + cmd +" json: " + json;
        log.debug(msg);

        String camera= "index";
        long startdateSeconds= 0;
        String startDateUsedStr= null;
        JsonElement jsonElement = null;
        JsonObject jsonObject = null;
        if(json!=null && json.trim().length()>2) {
            if (!Utils.isJSONValid(json)) {
                throw new Exception("Error. invalid json: " + msg+ "\n" + BlueAlerts.JsonHelpGet());
            }
            jsonElement = (new Gson()).fromJson(json, JsonElement.class);
            jsonObject = jsonElement.getAsJsonObject();
            try {
                camera = jsonObject.get("camera").getAsString();
            } catch (Exception e) {
                log.warn("Warn. camera not present, used: index");
            }
            try {
                startDateUsedStr = Utils.CorrectDateString(jsonObject.get("startdate").getAsString());
                startdateSeconds = Utils.GetSecondsFromDateSql(startDateUsedStr);
            } catch (Exception e) {
                log.warn("Warn startdate not present or invalid, used: "+ startDateUsedStr);
            }
        } else {
            log.warn("Warn. Empty json, used: camera : " + camera + ", startdate: " + startDateUsedStr);
        }
        msg = msg + "Used :camera: "+ camera + " startDate: " + startDateUsedStr;
        log.info("Used :camera: "+ camera + " startDate: " + startDateUsedStr );

        // return json data element with all alerts details
        // for camera short name or index, 4 all and start date/time
        String cmdParams = ",\"camera\":\"" + camera + "\",\"startdate\":" + startdateSeconds;
        JsonElement jsonDataElement = null;
        CommandCoreRequest commandCoreRequest = null;
        try {
            boolean hasToBeSuccess = true;
            boolean getDataElement = true;
            commandCoreRequest = new CommandCoreRequest(_blueLogin);
            jsonDataElement = commandCoreRequest.RunTheCmd(cmd, cmdParams,hasToBeSuccess,getDataElement);

            if(commandCoreRequest.getProblemMsg()== null){
                BlueAlerts alerts = new BlueAlerts(jsonDataElement);
                log.debug("Alerts: " + alerts.toString());
                return alerts;
            } else {
                _problemMsg = "Error. cmd: " + cmd + " cmdParams: " +
                        cmdParams + "\n" + commandCoreRequest.getProblemMsg() +
                        "\n" + BlueAlerts.JsonHelpGet()+"\n";
                return null;
            }
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " msg: " +msg + "\n", e);
            throw e;
        }
    }

    public void AlertsDelete() throws Exception {
        log.debug("AlertsDelete");
        String cmd = "alertlist";
        String cmdParams = ",\"camera\":\"index\",\"startdate\":0,\"reset\":true" ;
        try {
            boolean hasToBeSuccess = true;
            boolean getDataElement = false;
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            commandCoreRequest.RunTheCmd(cmd, cmdParams,hasToBeSuccess,getDataElement);
            if(commandCoreRequest.getProblemMsg()!= null){
                _problemMsg = "Error. cmd: " + cmd + " cmdParams: " +
                        cmdParams + "\n" + commandCoreRequest.getProblemMsg() + "\n";
            }
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n", e);
            throw e;
        }
    }
}