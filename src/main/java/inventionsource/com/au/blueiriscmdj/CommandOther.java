package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandOther {

    private static final Logger log = (Logger)LogManager.getLogger(CommandOther.class);

    private BlueLogin _blueLogin = null;

    public BlueLogin getBlueLogin() { return _blueLogin; }

    public CommandOther(BlueLogin blueLogin) throws Exception {
        _blueLogin = blueLogin;
    }

    public Cameras GetList_Cams() throws Exception {
        log.debug("list-cams: "); // return json data element with all cameras details
        String cmd = "camlist";
        try {
            boolean hasToBeSuccess = true;
            boolean getDataElement = true;
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);

            JsonElement jsonDataElement = commandCoreRequest.RunTheCmd(cmd, null,hasToBeSuccess,getDataElement);

            Cameras cameras = new Cameras(jsonDataElement);
            log.debug("Cameras: " + cameras.toStringAll());
            return cameras;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n", e);
            throw e;
        }
    }

    public void TriggerCam(String camera) throws Exception {
        log.debug("trigger: " + camera);
        String cmd = "trigger";
        String cmdParams = ",\"camera\":\"" + camera + "\"";
        boolean resultHasToBeSuccess = true;
        boolean getDataElemet = false;
        try {
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            commandCoreRequest.RunTheCmd(cmd, cmdParams, resultHasToBeSuccess, getDataElemet);
            log.info(cmd + " camera: " + camera + " OK.");
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " camera: " + camera + " for BlueIris\n" +
                    "Get list of cameras using -lc option.\n", e);
            throw e;
        }
    }

    public JsonElement SendPtzButton(String camera, int button) throws Exception {
        log.debug("send ptz: " + camera + " button: " + button);
        //{bi.cmd("ptz", {"camera": args.ptzcam,"button": int(args.ptzbutton),"updown": 0})
        String cmd = "ptz";
        String cmdParams = ",\"camera\":\"" + camera + "\",\"button\":" + button + ",\"updown\":0";
        boolean hasToBeSuccess = true;
        boolean getDataElement = false;
        try {
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            commandCoreRequest.RunTheCmd(cmd, cmdParams, hasToBeSuccess, getDataElement);
            log.info(cmd + " sent OK. cam: " + camera + " button: " + button);
            return null;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " camera: " + camera + " for BlueIris\n"+
                    "Get list of cameras using -lc option.\n", e);
            throw e;
        }
    }

}