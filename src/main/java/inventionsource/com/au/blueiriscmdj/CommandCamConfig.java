package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandCamConfig {
/*
camconfig - set json elements:
    Get (and optionally set) the state of many camera properties:
        reset:true reset the camera
        enable:true or false enable or disable the camera
        pause:n sends a pause command, and returns a value in seconds
            -1: pause indefinitely
            0: un-pause
            1..3: add 30 seconds, 1 minute, 1 hour to the pause time
        motion:true or false enable or disable the motion detector
        schedule:true or false enable or disable the camera's custom schedule
        ptzcycle:true or false enable or disable the preset-cycle feature
        ptzevents:true or false enable or disable the PTZ event schedule
        alerts:n sets the corresponding alert function
        record:n sets the corresponding record function
 */
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
                    "  \nGet list of cameras using -cl option.\n" + setJsonHelp(), e);
            throw e;
        }
    }

    public String setJsonHelp(){
        StringBuilder sb = new StringBuilder();
        sb.append("camconfig-set json example:\n'{ \"reset\":0,\"enable\":1,\"pause\":0," +
                "\"motion\":true,\"schedule\":true,\"ptzcycle\":true," +
                "\"ptzevents\":true,\"alerts\":0\"record\":2}'\n");
        sb.append("reset:true reset the camera\n");
        sb.append("enable:true or false enable or disable the camera\n");
        sb.append("pause:n sends a pause command, and returns a value in seconds\n");
        sb.append("   -1: pause indefinitely\n");
        sb.append("    0: un-pause\n");
        sb.append(" 1..3: add 30 seconds, 1 minute, 1 hour to the pause time\n");
        sb.append("motion:true or false enable or disable the motion detector\n");
        sb.append("schedule:true or false enable or disable the camera's custom schedule\n");
        sb.append("ptzcycle:true or false enable or disable the preset-cycle feature\n");
        sb.append("ptzevents:true or false enable or disable the PTZ event schedule\n");
        sb.append("alerts:n sets the corresponding alert function\n");
        sb.append("record:n sets the corresponding record function\n");
        return sb.toString();
    }

}