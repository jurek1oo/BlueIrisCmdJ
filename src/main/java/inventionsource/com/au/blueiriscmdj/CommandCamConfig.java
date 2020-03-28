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

    public BlueCamConfig GetCamConfig(String camera) throws Exception {
        String msg ="camera: " + camera ;
        log.debug(msg);
        String cmd = "camconfig";
        String cmdParams = ", \"camera\": \"" + camera + "\"";
        boolean hasToBeSuccess = true;
        boolean getDataElement = true;
        try {
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            JsonElement jsonDataElement = commandCoreRequest.RunTheCmd(cmd, cmdParams, hasToBeSuccess, getDataElement);
            BlueCamConfig blueCamConfig = new BlueCamConfig(jsonDataElement, camera);
            log.debug(blueCamConfig.toStringPretyJson());
            return blueCamConfig;
        } catch (Exception e) {
            log.error("\nError executing command: " + cmd  + " : " + msg + " for BlueIris.\n" +
                    "Make sure the camera name is correct, Get list of cameras using -cl option.\n.", e);
            throw e;
        }
    }

    public BlueCamConfig SetCamConfig(String camera, String json) throws Exception {
        // json = { "reset":0, "enable":1, "pause":0 } - check setJsonHelp()
        String msg ="camera: " + camera + " json: " + json;
        log.debug(msg);
        if(json==null || json.length()==0) throw new Exception("Error empty json string");
        if (!Utils.isJSONValid(json)) throw new Exception("Error not valid json->" + json + "<-");
    //{"cmd":"camconfig","session":"65e833d64d6349a578801ce90c944f0a","response":"1a703c5c8f0017804aca748069433a0b",
    // "camera":"Ceiling1","enable":1}
        String cmd = "camconfig";
        String jsonInside = json.replace("{","").replace("}","").
                replace('"', '\"');

        String cmdParams = ", \"camera\": \"" + camera + "\", " + jsonInside;
        boolean hasToBeSuccess = true;
        boolean getDataElement = true;
        try {
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            JsonElement jsonDataElement = commandCoreRequest.RunTheCmd(cmd, cmdParams, hasToBeSuccess, getDataElement);
            BlueCamConfig blueCamConfig = new BlueCamConfig(jsonDataElement, camera);

            log.debug(blueCamConfig.toStringPretyJson());
            return blueCamConfig;
        } catch (Exception e) {
            log.error("\nError executing: " + cmd  + " : " +  msg +
                    "  \nGet list of cameras using -cl option.\n" , e);
            throw e;
        }
    }

}