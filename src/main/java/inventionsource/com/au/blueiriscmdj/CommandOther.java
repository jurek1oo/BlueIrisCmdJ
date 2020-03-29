package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandOther {

    private static final Logger log = (Logger)LogManager.getLogger(CommandOther.class);

    private BlueLogin _blueLogin = null;
    public BlueLogin getBlueLogin() { return _blueLogin; }
    private String _problemMsg = null;
    public String getProblemMsg() {        return _problemMsg;    }

    public CommandOther(BlueLogin blueLogin) throws Exception {
        _blueLogin = blueLogin;
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
            if(commandCoreRequest.getProblemMsg()== null){
                log.info(cmd + " camera: " + camera + " OK.");
            } else {
                _problemMsg = "Error. cmd: " + cmd + " cmdParams: " +
                        cmdParams + " : " + commandCoreRequest.getProblemMsg() + "\n";
            }
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " camera: " + camera + " for BlueIris\n" +
                    "Get list of cameras using -lc option.\n", e);
            throw e;
        }
    }

    public void SendPtzButton(String camera, int button) throws Exception {
        log.debug("send ptz: " + camera + " button: " + button);
        //{bi.cmd("ptz", {"camera": args.ptzcam,"button": int(args.ptzbutton),"updown": 0})
        String cmd = "ptz";
        String cmdParams = ",\"camera\":\"" + camera + "\",\"button\":" + button + ",\"updown\":0";
        boolean hasToBeSuccess = true;
        boolean getDataElement = false;
        try {
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            commandCoreRequest.RunTheCmd(cmd, cmdParams, hasToBeSuccess, getDataElement);
            if(commandCoreRequest.getProblemMsg()== null){
                log.info(cmd + " sent OK. cam: " + camera + " button: " + button);
            } else {
                _problemMsg = "Error. cmd: " + cmd  + " button: " + button +
                        " : " + commandCoreRequest.getProblemMsg() + "\n";
            }
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " camera: " + camera + " for BlueIris\n"+
                    "Get list of cameras using -lc option.\n", e);
            throw e;
        }
    }

}