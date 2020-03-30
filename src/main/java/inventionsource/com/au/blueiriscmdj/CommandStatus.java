package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandStatus {

    private static final Logger log = (Logger)LogManager.getLogger(CommandStatus.class);

    private BlueLogin _blueLogin = null;
    private boolean _resultHasToBeSuccess = false ;
    private boolean _getDataElemet =  true;
    public CommandStatus(BlueLogin blueLogin) throws Exception { _blueLogin = blueLogin;  }
    private String _problemMsg = null;
    public String getProblemMsg() {        return _problemMsg;    }

    public BlueStatus GetStatus() throws Exception {
        log.debug("status-get");
        String cmd = "status";
        String cmdParams = null;
        BlueStatus blueStatus = null;
        try {
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            JsonElement jsonElement = commandCoreRequest.RunTheCmd(cmd, cmdParams, _resultHasToBeSuccess, _getDataElemet);
            if(commandCoreRequest.getProblemMsg()== null){
                blueStatus = new BlueStatus(jsonElement);
                log.debug("get-status: \n" + blueStatus.toString());
                return blueStatus;
            } else {
                _problemMsg = "Error. cmd: " + cmd + " cmdParams: " +
                        cmdParams + " : " + commandCoreRequest.getProblemMsg() + "\n";
                return null;
            }
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n", e);
            _problemMsg = "Error in command: GetStatus. " + e.getMessage();
            return null;
        }
    }

    public BlueStatus SetStatus(String json) throws Exception {
        log.debug("json: " + json);
// {"signal":1,"profile":1,"schedule":"Default"}

        if(json==null || json.length()==0) throw new Exception("Error empty json string");
        if (!Utils.isJSONValid(json)) {
            throw new ExceptionBiCmd("Error. invalid json->" + json+ "<-\n" + BlueStatus.JsonHelpSet());
        }

         String jsonInside = json.replace("{","").replace("}","").
                replace('"', '\"');
        String cmd = "status";
        String cmdParams = "," + jsonInside;

        BlueStatus blueStatus = null;
        try {
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            JsonElement jsonElement = commandCoreRequest.RunTheCmd(cmd, cmdParams, _resultHasToBeSuccess, _getDataElemet);
            if(commandCoreRequest.getProblemMsg()== null){
                blueStatus = new BlueStatus(jsonElement);
                return blueStatus;
            } else {
                _problemMsg = "Error. cmd: " + cmd + " cmdParams: " +
                        cmdParams + " : " + commandCoreRequest.getProblemMsg() +
                        "\n" + BlueStatus.JsonHelpSet()+"\n";
             }
        } catch (ExceptionBiCmd e) {
            _problemMsg = e.getMessage();
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " json: " +
                    json + " after changes blueStatus: " + blueStatus.toString() +
                    " : " + BlueStatus.JsonHelpSet() + "\n", e);
            _problemMsg = "Error in command: " + cmd + " json: " +json  + e.getMessage();
        }
        return null;
    }

    public BlueStatus SetSignal(String signal) throws Exception {
        log.debug("set-signal: " + signal);
        return SetSignal((new BlueSignals()).getSignalInt(signal));
    }

    public BlueStatus SetSignal(int signalInt) throws Exception {
        log.debug("set-signal: " + signalInt);

        BlueStatus blueStatus = null;
        try {
            if (signalInt < 0 || signalInt > 2 ) {
                throw new ExceptionBiCmd("Error. 0-2 Wrong signal: " +signalInt + "\n"+BlueStatus.JsonHelpSet());
            }
            String json = "{\"signal\":" + signalInt +"}";
            blueStatus = SetStatus(json);;

            if (blueStatus.getSignalInt()==signalInt) {
                log.info("set-signal: " + signalInt  + " OK. ");
            } else {
                throw new ExceptionBiCmd("Error. signal: " + signalInt + " different in returned status: " +
                        blueStatus.toString() + "\n"+BlueStatus.JsonHelpSet());
            }
            return blueStatus;
        } catch (ExceptionBiCmd e) {
            _problemMsg = e.getMessage();
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error("Error executing command:SetSignal signal: " +
                    signalInt + " for BlueIris\n" +
                    " after changes blueStatus: " + blueStatus.toString() +
                    "\n" +BlueStatus.JsonHelpSet() + "\n", e);
            _problemMsg = "Error in command:SetSignal signalInt: " +signalInt +"\n"  + e.getMessage();
        }
        return null;
    }

    public BlueStatus SetSchedule(String schedule) throws Exception {
        log.debug("set-schedule: " + schedule);
        String cmd = "status";
        BlueStatus blueStatus = null;
        try {
            if (_blueLogin.getSchedules() == null || _blueLogin.getSchedules().size() < 1) {
                throw new ExceptionBiCmd("Error. schedules are empty." + "\n"+BlueStatus.JsonHelpSet());
            } else {
                if (!_blueLogin.getSchedules().contains(schedule)) {
                    throw new ExceptionBiCmd("Error. schedule: " + schedule +
                            " not found. Use -get-schedules command to get all of them.\n"+BlueStatus.JsonHelpSet());
                }
            }
            String json = "{\"schedule\":\"" + schedule + "\"}";
            blueStatus = SetStatus(json);
            if (blueStatus.toString().indexOf(schedule) >= 0) {
                log.info("set-schedule: " + schedule + " OK. After changes blueStatus: \n" +
                        blueStatus.toString());
            } else {
                String msg = "Schedule: " + schedule + " different in status: " +
                        blueStatus.toString();
                throw new ExceptionBiCmd("Error. " + msg+ "\n"+BlueStatus.JsonHelpSet());
            }
            return blueStatus;
        } catch (ExceptionBiCmd e) {
            _problemMsg = e.getMessage();
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n" +
                    " after changes blueStatus: " + blueStatus.toString() +
                    "\n" +BlueStatus.JsonHelpSet() + "\n", e);
            _problemMsg = "Error in command:SetSchedule schedule: " +schedule +"\n"  + e.getMessage();
        }
        return null;
    }

    public BlueStatus SetProfile(int profileInt) throws Exception {
        log.debug("profileInt: " + profileInt);
         BlueStatus blueStatus = null;
        try {
            if (profileInt < 0 || profileInt > 7) {
                throw new ExceptionBiCmd("Error. profile out of range 0-7. profileInt: " + profileInt +"/n"+ "\n"+BlueStatus.JsonHelpSet());
            }
            String json = "{\"profile\":" + profileInt + "}";
            blueStatus = SetStatus(json);
            return blueStatus;
        } catch (ExceptionBiCmd e) {
            _problemMsg = e.getMessage();
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error("Error executing command: SetProfile for BlueIris profileInt: " +
                            profileInt + " after changes blueStatus: " + blueStatus.toString() +
                    "\n" +BlueStatus.JsonHelpSet() + "\n", e);
            _problemMsg = "Error in command:SetProfile profileInt: " +profileInt +"\n"  + e.getMessage();
       }
        return null;
    }
}