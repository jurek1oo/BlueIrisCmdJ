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
        log.debug("get-status: ");
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

    public BlueStatus SetSignal(String signal) throws Exception {
        log.debug("set-signal: " + signal);
        return SetSignal((new BlueSignals()).getSignalInt(signal));
    }

    public BlueStatus SetSignal(int signalInt) throws Exception {
        log.debug("set-signal: " + signalInt);
        String cmd = "status";

        BlueStatus blueStatus = null;
        try {
            if (signalInt < 0 || signalInt > 2 )
                throw new Exception("Error. 0-2 Wrong signal: " +signalInt);
            String cmdParams = ",\"signal\":" + signalInt ;

            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            JsonElement jsonElement = commandCoreRequest.RunTheCmd(cmd, cmdParams, _resultHasToBeSuccess, _getDataElemet);
            blueStatus = new BlueStatus(jsonElement);

            if (blueStatus.getSignalInt()==signalInt) {
                log.info("set-signal: " + signalInt  + " OK. ");
            } else {
                throw new Exception("Error. signal: " + signalInt + " different in returned status: " +
                        blueStatus.toString());
            }

            return blueStatus;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " signal: " +
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
            if (_blueLogin.getSchedules() == null || _blueLogin.getSchedules().size() < 0) {
                throw new Exception("Error. schedules are empty.");
            } else {
                if (!_blueLogin.getSchedules().contains(schedule)) {
                    throw new Exception("Error. schedule: " + schedule +
                            " not found. Use -get-schedules command to get all of them.");
                }
            }
            String cmdParams = ",\"schedule\":\"" + schedule + "\"";

            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            JsonElement jsonElement = commandCoreRequest.RunTheCmd(cmd, cmdParams, _resultHasToBeSuccess, _getDataElemet);
            blueStatus = new BlueStatus(jsonElement);

            if (blueStatus.toString().indexOf(schedule) >= 0) {
                log.info("set-schedule: " + schedule + " OK. After changes blueStatus: \n" +
                        blueStatus.toString());
            } else {
                String msg = "Schedule: " + schedule + " different in status: " +
                        blueStatus.toString();
                log.warn("Warning. Schedule not set to Default by BI. " + msg);
                //throw new Exception("Error. " + msg);resultHasToBeSuccess
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
        String cmd = "status";
        BlueStatus blueStatus = null;
        try {
            if (profileInt < 0) {
                throw new Exception("Error. profile is < 0.");
            }
            String cmdParams = ",\"profile\":" + profileInt;

            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            JsonElement jsonElement = commandCoreRequest.RunTheCmd(cmd, cmdParams, _resultHasToBeSuccess, _getDataElemet);
            blueStatus = new BlueStatus(jsonElement);

            return blueStatus;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n" +
                    " after changes blueStatus: " + blueStatus.toString(), e);
            throw e;
        }
    }

}