package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandCamList {

    private static final Logger log = (Logger)LogManager.getLogger(CommandCamList.class);

    private BlueLogin _blueLogin = null;
    public BlueLogin getBlueLogin() { return _blueLogin; }
    private String _problemMsg = null;
    public String getProblemMsg() {        return _problemMsg;    }

    public CommandCamList(BlueLogin blueLogin) throws Exception {
        _blueLogin = blueLogin;
    }
    public BlueCameras GetCamList() throws Exception {
        log.debug("camlist: "); // return json data element with all cameras details
        String cmd = "camlist";
        try {
            boolean hasToBeSuccess = true;
            boolean getDataElement = true;
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);

            JsonElement jsonDataElement = commandCoreRequest.RunTheCmd(cmd, null,hasToBeSuccess,getDataElement);
            if(commandCoreRequest.getProblemMsg()== null){
                BlueCameras cameras = new BlueCameras(jsonDataElement);
                log.debug("Cameras: " + cameras.toString());
                return cameras;
            } else {
                _problemMsg = "Error. cmd: " + cmd +" : " + commandCoreRequest.getProblemMsg() + "\n";
                return null;
            }
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n" + "\n\n", e);
            throw e;
        }
    }

    public BlueCameras ResetCamsStats() throws Exception {
         String cmd = "camlist";
        log.debug(cmd);
        try {
            String cmdParams = ",\"reset\":true";
            boolean hasToBeSuccess = true;
            boolean getDataElement = true;
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);

            JsonElement jsonDataElement = commandCoreRequest.RunTheCmd(cmd, cmdParams,hasToBeSuccess,getDataElement);
            if(commandCoreRequest.getProblemMsg()== null){
                BlueCameras cameras = new BlueCameras(jsonDataElement);
                log.debug("Cameras: " + cameras.toString());
                return cameras;
            } else {
                _problemMsg = "Error. cmd: " + cmd + " cmdParams: " + cmdParams + " : " + commandCoreRequest.getProblemMsg() + "\n";
                return null;
            }
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n" + "\n\n", e);
            throw e;
        }
    }
}