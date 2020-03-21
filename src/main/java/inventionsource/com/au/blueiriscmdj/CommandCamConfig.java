package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandCamConfig {

    private static final Logger log = (Logger)LogManager.getLogger(CommandCamConfig.class);

    private BlueLogin _blueLogin = null;
    public BlueLogin getBlueLogin() { return _blueLogin; }

    public CommandCamConfig(BlueLogin blueLogin) throws Exception {
        _blueLogin = blueLogin;
    }

    public JsonElement GetCamConfig(String camera) throws Exception {
        String msg ="camera: " + camera ;
        log.debug(msg);
        String cmd = "camconfig";
        String cmdParams = ",\"camera\":\"" + camera + "\"";
        boolean hasToBeSuccess = true;
        boolean getDataElement = true;
        try {
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            JsonElement jsonDataElement = commandCoreRequest.RunTheCmd(cmd, cmdParams, hasToBeSuccess, getDataElement);
            log.debug(camConfigPrytty(camera, jsonDataElement));
            return jsonDataElement;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd  + " : " + msg + " for BlueIris\n", e);
            throw e;
        }
    }

    public JsonElement CamEnableDisable(String camera, boolean enableStatus) throws Exception {
        String msg ="camera: " + camera + " enableStatus: " + enableStatus;
        log.debug(msg);
        //{"cmd":"camconfig","session":"65e833d64d6349a578801ce90c944f0a","response":"1a703c5c8f0017804aca748069433a0b",
        // "camera":"Ceiling1","enable":1}
        String cmd = "camconfig";
        String cmdParams = ",\"camera\":\"" + camera + "\",\"enable\":" + ( enableStatus? 1 : 0);
        boolean hasToBeSuccess = true;
        boolean getDataElement = true;
        try {
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            JsonElement jsonDataElement = commandCoreRequest.RunTheCmd(cmd, cmdParams, hasToBeSuccess, getDataElement);
            log.debug(camConfigPrytty(camera, jsonDataElement));
            return jsonDataElement;
        } catch (Exception e) {
            log.error("Error executing: " + cmd  + " : " +  msg +
                    " for BlueIris\nGet list of cameras using -lc option.\n", e);
            throw e;
        }
    }

    public String camConfigPrytty(String camera, JsonElement jsonDataElement){
        return "camera: " + camera + "\n" + Utils.GetPrettyJsonString(jsonDataElement);
    }

}