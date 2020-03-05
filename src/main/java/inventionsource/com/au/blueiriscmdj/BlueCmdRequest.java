package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class BlueCmdRequest {

    private static final Logger log = (Logger)LogManager.getLogger(BlueCmdRequest.class);

    private RequestHttp _requestHttp = new RequestHttp();

    private BlueLogin _blueLogin = null;
    private BlueStatus _blueStatus = null;

    public BlueStatus getBlueStatus() {
        return _blueStatus;
    }
    public BlueLogin getBlueLogin() { return _blueLogin; }

    public String getUrl() { return _blueLogin.getUrl();  }
    public String getSession() { return _blueLogin.getSession();  }
    public String getSystemName() { return _blueLogin.getSystemName();   }
    public ArrayList<String> getProfiles() { return _blueLogin.getProfiles();  }
    public ArrayList<String> getSchedules() { return _blueLogin.getSchedules(); }

    public BlueCmdRequest(BlueLogin blueLogin) throws Exception {
        _blueLogin = blueLogin;
    }

    public BlueStatus SetSignal(String signal) throws Exception {
        log.debug("set-signal: " + signal);
        if (signal == null || signal.length() < 1)
            throw new Exception("Error. signal is null.");
        int signalInt = (new BlueSignals()).getSignalInt(signal);
        if (signalInt < 0) {
            if (signal.compareTo("0") == 0) signalInt = 0;
            if (signal.compareTo("1") == 0) signalInt = 1;
            if (signal.compareTo("2") == 0) signalInt = 2;
        }
        return SetSignal(signalInt);
    }

    public BlueStatus SetSignal(int signalInt) throws Exception {
        log.debug("set-signalIn: " + signalInt);
        String signal = null;
        String cmd = "status";
        try {
            if (signalInt < 0 || signalInt > 2) {
                throw new Exception("Error. signalInt: " + signalInt + " should be >= 0 and <= 2.");
            }
            signal = (new BlueSignals()).getSignal(signalInt);
            String cmdParams = ",\"signal\":" + signalInt;

            BlueCmdRequestCore blueCmdRequestCore = new BlueCmdRequestCore(_blueLogin);
            JsonElement jsonElement = blueCmdRequestCore.RunTheCmd(cmd, cmdParams);
            _blueStatus = new BlueStatus(jsonElement, getProfiles());

            if (_blueStatus.toJsonString().indexOf(signal) >= 0) {
                log.info("set-signal: " + signal
                        + " OK. After changes blueStatus: \n" + _blueStatus.toJsonString());
            } else {
                throw new Exception("Error. signal: " + signal + " different in status: " +
                        _blueStatus.toJsonString());
            }
            return _blueStatus;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " signal: " + signal + " for BlueIris\n" +
                    " after changes blueStatus: " + _blueStatus, e);
            throw e;
        }
    }

    public BlueStatus SetSchedule(String schedule) throws Exception {
        log.debug("set-schedule: " + schedule);
        String cmd = "status";
        try {
            if (getSchedules() == null || getSchedules().size() < 0) {
                throw new Exception("Error. schedules are empty.");
            } else {
                if (!getSchedules().contains(schedule)) {
                    throw new Exception("Error. schedule: " + schedule + " not found. Use -get-schedules command to get all of them.");
                }
            }
            String cmdParams = ",\"schedule\":\"" + schedule + "\"";

            BlueCmdRequestCore blueCmdRequestCore = new BlueCmdRequestCore(_blueLogin);
            JsonElement jsonElement = blueCmdRequestCore.RunTheCmd(cmd, cmdParams);
            _blueStatus = new BlueStatus(jsonElement, getProfiles());

            if (_blueStatus.toJsonString().indexOf(schedule) >= 0) {
                log.info("set-schedule: " + schedule + " OK. After changes blueStatus: \n" +
                        _blueStatus.toJsonString());
            } else {
                String msg = "Schedule: " + schedule + " different in status: " + _blueStatus.toJsonString();
                log.warn("Warning. Schedule not set to Default by BI. " + msg);
                //throw new Exception("Error. " + msg);
            }
            return _blueStatus;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n" +
                    " after changes blueStatus: " + _blueStatus, e);
            throw e;
        }
    }

    public BlueStatus SetProfile(String profile) throws Exception {
        log.debug("set-profile: " + profile);
        String cmd = "status";
        try {
            if (profile == null || profile.length() == 0) {
                throw new Exception("Error. profile is null or empty.");
            }
            int profileInt = -1;
            profileInt = getProfiles().indexOf(profile);
            if (profileInt == -1) {
                throw new Exception("Error. profile: " + profile + " not found. Use -get-profiles command to get all of them.");
            }

            String cmdParams = ",\"profile\":" + profileInt;

            BlueCmdRequestCore blueCmdRequestCore = new BlueCmdRequestCore(_blueLogin);
            JsonElement jsonElement = blueCmdRequestCore.RunTheCmd(cmd, cmdParams);
            _blueStatus = new BlueStatus(jsonElement, getProfiles());

            if (_blueStatus.toJsonString().indexOf(profile) >= 0) {
                log.info("set-profile: " + profile + " OK. After changes blueStatus: \n" +
                        _blueStatus.toJsonString());
            } else {
                throw new Exception("profile: " + profile + " different in status: " +
                        _blueStatus.toJsonString());
            }
            return _blueStatus;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n" +
                    " after changes blueStatus: " + _blueStatus, e);
            throw e;
        }
    }

    public BlueStatus GetStatus() throws Exception {
        log.debug("get-status: ");
        String cmd = "status";
        try {
            BlueCmdRequestCore blueCmdRequestCore = new BlueCmdRequestCore(_blueLogin);
            JsonElement jsonElement = blueCmdRequestCore.RunTheCmd(cmd, null);

            _blueStatus = new BlueStatus(jsonElement, getProfiles());
            log.debug("get-status: \n" + _blueStatus.toJsonString());
            return _blueStatus;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n", e);
            throw e;
        }
    }

    public Cameras GetList_Cams() throws Exception {
        log.debug("list-cams: "); // return json data element with all cameras details
        String cmd = "camlist";
        try {
            boolean hasToBeSuccess = true;
            boolean getDataElement = true;
            BlueCmdRequestCore blueCmdRequestCore = new BlueCmdRequestCore(_blueLogin);
            JsonElement jsonDataElement = blueCmdRequestCore.RunTheCmd(cmd, null,hasToBeSuccess,getDataElement);

            Cameras cameras = new Cameras(jsonDataElement);

            log.info(cmd + " : " + cameras.toStringAll());
            return cameras;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n", e);
            throw e;
        }
    }

    public JsonElement GetCamConfig(String camera) throws Exception {
        log.debug("get-camconfig: " + camera);
        String cmd = "camconfig";
        String cmdParams = ",\"camera\":\"" + camera + "\"";
        boolean hasToBeSuccess = true;
        boolean getDataElement = true;
        try {
            BlueCmdRequestCore blueCmdRequestCore = new BlueCmdRequestCore(_blueLogin);
            JsonElement jsonDataElement = blueCmdRequestCore.RunTheCmd(cmd, cmdParams, hasToBeSuccess, getDataElement);
            String resultPretty = Utils.GetPrettyJsonString(jsonDataElement);
            log.info(cmd + " : " + camera + "\n" + resultPretty);
            return jsonDataElement;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " camera: " + camera + " for BlueIris\n", e);
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
            BlueCmdRequestCore blueCmdRequestCore = new BlueCmdRequestCore(_blueLogin);
            blueCmdRequestCore.RunTheCmd(cmd, cmdParams, resultHasToBeSuccess, getDataElemet);
            log.info(cmd + " camera: " + camera + " OK.");
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " camera: " + camera + " for BlueIris\n" +
                    "Get list of cameras using -lc option.\n", e);
            throw e;
        }
    }

    public JsonElement CamEnable(String camera) throws Exception {
        log.debug("cam-enable: " + camera);
        //{"cmd":"camconfig","session":"65e833d64d6349a578801ce90c944f0a","response":"1a703c5c8f0017804aca748069433a0b",
        // "camera":"Ceiling1","enable":1}
        String cmd = "camconfig";
        String cmdParams = ",\"camera\":\"" + camera + "\",\"enable\":1";
        boolean hasToBeSuccess = true;
        boolean getDataElement = true;
        try {
            BlueCmdRequestCore blueCmdRequestCore = new BlueCmdRequestCore(_blueLogin);
            JsonElement jsonDataElement = blueCmdRequestCore.RunTheCmd(cmd, cmdParams, hasToBeSuccess, getDataElement);
            log.info(cmd + " camera: " + camera + " :\n" + Utils.GetPrettyJsonString(jsonDataElement));
            return jsonDataElement;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " camera: " + camera + " for BlueIris\n"+
                    "Get list of cameras using -lc option.\n", e);
            throw e;
        }
    }

    public JsonElement CamDisable(String camera) throws Exception {
        log.debug("cam-enable: " + camera);
        //{"cmd":"camconfig","session":"65e833d64d6349a578801ce90c944f0a","response":"1a703c5c8f0017804aca748069433a0b",
        // "camera":"Ceiling1","enable":0}
        String cmd = "camconfig";
        String cmdParams = ",\"camera\":\"" + camera + "\",\"enable\":0";
        boolean hasToBeSuccess = true;
        boolean getDataElement = true;
        try {
            BlueCmdRequestCore blueCmdRequestCore = new BlueCmdRequestCore(_blueLogin);
            JsonElement jsonDataElement = blueCmdRequestCore.RunTheCmd(cmd, cmdParams, hasToBeSuccess, getDataElement);
            log.info(cmd + " camera: " + camera + " :/n "+ Utils.GetPrettyJsonString(jsonDataElement));
            return jsonDataElement;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " camera: " + camera + " for BlueIris\n"+
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
            BlueCmdRequestCore blueCmdRequestCore = new BlueCmdRequestCore(_blueLogin);
            blueCmdRequestCore.RunTheCmd(cmd, cmdParams, hasToBeSuccess, getDataElement);
            log.info(cmd + " sent OK. cam: " + camera + " button: " + button);
            return null;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " camera: " + camera + " for BlueIris\n"+
                    "Get list of cameras using -lc option.\n", e);
            throw e;
        }
    }}