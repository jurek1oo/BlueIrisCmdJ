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

    public CommandStatus(BlueLogin blueLogin) throws Exception {
        _blueLogin = blueLogin;
    }

    public BlueStatus GetStatus() throws Exception {
        log.debug("status-get");
        String cmd = "status";
        String cmdParams = null;
        BlueStatus blueStatus = null;
        try {
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            JsonElement jsonElement = commandCoreRequest.RunTheCmd(cmd, cmdParams, _resultHasToBeSuccess, _getDataElemet);

            blueStatus = new BlueStatus(jsonElement);
            log.debug("get-status: \n" + blueStatus.toString());
            return blueStatus;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n", e);
            throw e;
        }
    }

    public BlueStatus SetStatus(String json) throws Exception {
        log.debug("json: " + json);

        if(json==null || json.length()==0) throw new Exception("Error empty json string");
         String jsonInside = json.replace("{","").replace("}","").
                replace('"', '\"');
        String cmd = "status";
        String cmdParams = "," + jsonInside;

        BlueStatus blueStatus = null;
        try {
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            JsonElement jsonElement = commandCoreRequest.RunTheCmd(cmd, cmdParams, _resultHasToBeSuccess, _getDataElemet);
            blueStatus = new BlueStatus(jsonElement);
            return blueStatus;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " json: " +
                    json + " after changes blueStatus: " + blueStatus.toString(), e);
            throw e;
        }
    }

    public BlueStatus SetSignal(String signal) throws Exception {
        log.debug("set-signal: " + signal);
        return SetSignal((new BlueSignals()).getSignalInt(signal));
    }

    public BlueStatus SetSignal(int signalInt) throws Exception {
        log.debug("set-signal: " + signalInt);

        BlueStatus blueStatus = null;
        try {
            if (signalInt < 0 || signalInt > 2 )
                throw new Exception("Error. 0-2 Wrong signal: " +signalInt);
            String json = "\"signal\":" + signalInt ;
            blueStatus = SetStatus(json);;

            if (blueStatus.getSignalInt()==signalInt) {
                log.info("set-signal: " + signalInt  + " OK. ");
            } else {
                throw new Exception("Error. signal: " + signalInt + " different in returned status: " +
                        blueStatus.toString());
            }
            return blueStatus;
        } catch (Exception e) {
            log.error("Error executing command:SetSignal signal: " +
                    signalInt + " for BlueIris\n" +
                    " after changes blueStatus: " + blueStatus.toString(), e);
            throw e;
        }
    }

    public BlueStatus SetSchedule(String schedule) throws Exception {
        log.debug("set-schedule: " + schedule);
        String cmd = "status";
        BlueStatus blueStatus = null;
        try {
            if (_blueLogin.getSchedules() == null || _blueLogin.getSchedules().size() < 1) {
                throw new Exception("Error. schedules are empty.");
            } else {
                if (!_blueLogin.getSchedules().contains(schedule)) {
                    throw new Exception("Error. schedule: " + schedule +
                            " not found. Use -get-schedules command to get all of them.");
                }
            }
            String json = "\"schedule\":\"" + schedule + "\"";
            blueStatus = SetStatus(json);
            if (blueStatus.toString().indexOf(schedule) >= 0) {
                log.info("set-schedule: " + schedule + " OK. After changes blueStatus: \n" +
                        blueStatus.toString());
            } else {
                String msg = "Schedule: " + schedule + " different in status: " +
                        blueStatus.toString();
                throw new Exception("Error. " + msg);
            }
            return blueStatus;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n" +
                    " after changes blueStatus: " + blueStatus.toString(), e);
            throw e;
        }
    }

    public BlueStatus SetProfile(int profileInt) throws Exception {
        log.debug("profileInt: " + profileInt);
         BlueStatus blueStatus = null;
        try {
            if (profileInt < 0 || profileInt > 7) {
                throw new Exception("Error. profile out of range 0-7. profileInt: " + profileInt);
            }
            String json = "\"profile\":" + profileInt;
            blueStatus = SetStatus(json);
            return blueStatus;
        } catch (Exception e) {
            log.error("Error executing command: SetProfile for BlueIris profileInt: " +
                            profileInt + " after changes blueStatus: " + blueStatus.toString(), e);
            throw e;
        }
    }

}