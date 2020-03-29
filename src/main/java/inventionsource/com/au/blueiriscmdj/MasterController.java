package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class MasterController {
    private static final Logger log =  (Logger)LogManager.getLogger(MasterController.class.getName());

    private String[] _args = null;
    public MasterController(String[] args) {
        _args = args;
    }

    public BlueLogin getBlueLogin() {   return _blueLogin;  }

    private BlueLogin _blueLogin = null;
    private CommandOther _commandOther = null;
    private CommandStatus _commandStatus = null;
    private CommandAlerts _commandAlerts = null;
    private CommandCamList _commandCamList = null;
    private CommandClipList _commandClipList = null;
    private CommandCamConfig _commandCamConfig = null;
    private CommandLogsList _commandLogsList = null;

    private BlueStatus _lastBlueStatus = null;
    private BlueCamConfig _blueCamConfig = null;
    private BlueLogs _blueLogs = null;


    public CommandOther getCommandOther() throws Exception { if (_commandOther==null) _commandOther = new CommandOther(_blueLogin);  return _commandOther;  }
    public CommandCamList getCommandCamList() throws Exception { if (_commandCamList==null) _commandCamList = new CommandCamList(_blueLogin);  return _commandCamList;  }
    public CommandClipList getCommandClipList() throws Exception { if (_commandClipList==null) _commandClipList = new CommandClipList(_blueLogin);  return _commandClipList;  }
    public CommandAlerts getCommandAlerts() throws Exception { if (_commandAlerts==null) _commandAlerts = new CommandAlerts(_blueLogin);  return _commandAlerts;  }
    public CommandStatus getCommandStatus() throws Exception { if (_commandStatus==null) _commandStatus = new CommandStatus(_blueLogin);  return _commandStatus;  }
    public CommandCamConfig getCommandCamConfig() throws Exception { if (_commandCamConfig==null) _commandCamConfig = new CommandCamConfig(_blueLogin);  return _commandCamConfig;  }
    public CommandLogsList getCommandLogsList() throws Exception { if (_commandLogsList==null) _commandLogsList = new CommandLogsList(_blueLogin);  return _commandLogsList;  }

    public BlueCamConfig getBlueCamConfig() {      return _blueCamConfig;    }
    public BlueStatus getLastBlueStatus() { return _lastBlueStatus; }
    public BlueLogs getBlueLogs() {        return _blueLogs;    }

    public void Action() {
        String session = null;
        Cli cli = new Cli(_args);
        boolean foundOneCmd=false;

        try {
            Cli.GoodOrBad gob = cli.parse();
            if (gob.bad != null && gob.bad.length() > 0) {
                throw new Exception("Error in parameters. " + gob.bad);
            } else {
                log.info(gob.good);
            }
            if (cli.getLoginParams() == null) {
                log.info("Nothing to do. Closing BlueIrisCmdJ LoginParams not there.");
            } else {
                //login and process commands
                _blueLogin = new BlueLogin();
                _blueLogin.BlueIrisLogin(cli.getLoginParams());
                session = _blueLogin.getSession();
                if (session == null || session.length() == 0) {
                    _blueLogin = null;
                    throw new Exception("Error. Could not loggin. No session. ");
                } else {
                    log.info("Login OK.");
                }
                // ---------------------------------------------------------------------------

                if (cli.is_alerts_delete() ) {
                    foundOneCmd = true;
                    log.info("alerts-delete: \n");
                    getCommandAlerts().AlertsDelete();
                    if (getCommandAlerts().getProblemMsg()!=null){
                        throw new ExceptionBiCmd(getCommandAlerts().getProblemMsg());
                    }
                }
                if (cli.is_alerts_list() ) {
                    foundOneCmd = true;
                    BlueAlerts alersts = getCommandAlerts().GetAlertsList(
                            cli.get_json());
                    if (getCommandAlerts().getProblemMsg()==null){
                        log.info("alerts-list number: " + alersts.size() + "\n" + alersts);
                        log.info("alerts-list number: " + alersts.size() );
                    } else {
                        throw new ExceptionBiCmd(getCommandAlerts().getProblemMsg());
                    }
               }
                if (cli.get_camconfig() != null && cli.get_camconfig().length() > 0) {
                    foundOneCmd = true;
                    _blueCamConfig = getCommandCamConfig().GetCamConfig(cli.get_camconfig());
                    if (getCommandCamConfig().getProblemMsg()==null){
                        log.info("CamConfigGet: " + cli.get_camconfig() + "\n" +
                                _blueCamConfig.toStringPretyJson());
                        log.info("CamConfigGet result for: " + cli.get_camconfig()+"\n");
                    } else {
                        throw new ExceptionBiCmd(getCommandCamConfig().getProblemMsg());
                    }
                }
                if (cli.get_camconfig_set()!= null && cli.get_camconfig_set().length() > 0) {
                    foundOneCmd = true;
                    _blueCamConfig = getCommandCamConfig().SetCamConfig(cli.get_camconfig_set(),
                            cli.get_json());
                    if (getCommandCamConfig().getProblemMsg()==null){
                        log.info("CamConfigSet: " + cli.get_camconfig_set() + "\n" +_blueCamConfig.toStringPretyJson());
                        log.info("CamConfigSet result for: " + cli.get_camconfig_set()+"\n");
                   } else {
                        throw new ExceptionBiCmd(getCommandCamConfig().getProblemMsg());
                    }
                 }
                if (cli.is_cams_list()) {
                    BlueCameras cameras =  getCommandCamList().GetCamList();
                    if (getCommandCamList().getProblemMsg()!=null){
                        throw new ExceptionBiCmd(getCommandCamList().getProblemMsg());
                    }
                    log.info("cameras-list number: " +  getCommandCamList().GetCamList().size() + "\n" +
                            getCommandCamList().GetCamList());
                    log.info("cameras-list number: " +  getCommandCamList().GetCamList().size());
                }
                if (cli.is_clips_list()) {
                    foundOneCmd = true;
                    BlueClips clips =  getCommandClipList().GetClips(cli.get_json());
                    if (getCommandClipList().getProblemMsg()!=null){
                        throw new ExceptionBiCmd(getCommandClipList().getProblemMsg());
                    }
                    log.info("Clips-list number: " + clips.size() + "\n" + clips);
                    log.info("Clips-list number: " + clips.size() + "\n");
                }
                if (cli.is_cams_reset_stats()) {
                    foundOneCmd = true;
                    BlueCameras blueCameras = getCommandCamList().ResetCamsStats();
                    if (getCommandCamList().getProblemMsg()!=null){
                        throw new ExceptionBiCmd(getCommandCamList().getProblemMsg());
                    }
                    log.info("cams-reset-stats: \n" + blueCameras);
                }
                if (cli.is_logs_list()) {
                    foundOneCmd = true;
                    _blueLogs = getCommandLogsList().GetLogs(cli.get_json());
                    if (getCommandLogsList().getProblemMsg()!=null){
                        throw new ExceptionBiCmd(getCommandLogsList().getProblemMsg());
                    }
                    log.info("logs-list number: " + _blueLogs.size() + "\n" +_blueLogs);
                    log.info("logs-list number: " + _blueLogs.size() );
                }
                if (cli.is_profiles_list()) {
                    foundOneCmd = true;
                    BlueProfiles profiles =_blueLogin.getBlueProfiles();
                    log.info("profiles-list: number: " + profiles.size() + "\n" + profiles);
                    log.info("profiles-list: number: " + profiles.size() );
                }
                if (cli.get_ptzcam() != null ) {
                    foundOneCmd = true;
                    getCommandOther().SendPtzButton(cli.get_ptzcam(),cli.get_ptzbutton());
                    if (getCommandOther().getProblemMsg()!=null){
                        throw new ExceptionBiCmd(getCommandOther().getProblemMsg());
                    }
                }
                if (cli.is_schedules_list()) {
                    foundOneCmd = true;
                    ArrayList<String> schedules =_blueLogin.getSchedules();
                    log.info("schedules-list: number: " + schedules.size() + "\n" +
                            Arrays.toString(schedules.toArray()));
                }
                 if (cli.is_status_get()) {
                    foundOneCmd = true;
                    _lastBlueStatus = getCommandStatus().GetStatus();
                     if (_lastBlueStatus!= null && getCommandStatus().getProblemMsg()== null) {
                         log.info("--status-get:\n" +_lastBlueStatus);
                         log.info("--status-gExceptionet result\n");
                     } else if (_lastBlueStatus== null)  {
                         throw new ExceptionBiCmd("Error in --status-get. null data returned ");
                     } else {
                         throw new ExceptionBiCmd("Error in --status-get. " + getCommandStatus().getProblemMsg());
                     }
                }
                if (cli.is_status_set()) {
                    foundOneCmd = true;
                    if (cli.get_json() !=null && cli.get_json().length()>0) {
                        String msg = " Check your -j parameters help:  " + BlueStatus.toStringJsonHelp();
                        _lastBlueStatus = getCommandStatus().SetStatus(cli.get_json() );
                        if (_lastBlueStatus!= null && getCommandStatus().getProblemMsg()== null) {
                            log.info("--status-set OK result: " + _lastBlueStatus);
                            log.info("--status-set OK result for parameters: " + cli.get_json() + "\n");
                        } else if (_lastBlueStatus== null)  {
                            throw new ExceptionBiCmd("Error in --status-get. null data returned.\n" + msg);
                        } else {
                            throw new ExceptionBiCmd("Error in --status-get. " +
                                    getCommandStatus().getProblemMsg() + "\n" + msg);
                        }
                    } else {
                        throw new Exception("-status_set option needs -json to be provided.\n" +
                                BlueStatus.toStringJsonHelp());
                    }
                }
                if (cli.get_trigger() != null) {
                    foundOneCmd = true;
                    getCommandOther().TriggerCam(cli.get_trigger());
                    if (getCommandOther().getProblemMsg()!=null){
                        throw new ExceptionBiCmd(getCommandOther().getProblemMsg());
                    }
                }
            }
            if (!foundOneCmd) log.info("Nothing to do...");

        } catch (ExceptionBiCmd e) { // for returned response not succesfull
            log.error("BI cmd ExecutionException: " + e.getMessage());
        } catch (Exception e) {
            log.error("Exception: " + e);
        } finally {
             if( _blueLogin != null && _blueLogin.getSession()!=null){
                try {
                    _blueLogin.BlueIrisLogout();
                    log.info("Logout OK.");
                } catch (Exception ex){
                    log.error("Error logging out: ", ex);
                }
            }
        }
    }
}
