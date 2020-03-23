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
    private CommandCamConfig _commandCamConfig = null;

    private BlueStatus _lastBlueStatus = null;

    public CommandOther getCommandOther() throws Exception { if (_commandOther==null) _commandOther = new CommandOther(_blueLogin);  return _commandOther;  }
    public CommandCamList getCommandCamList() throws Exception { if (_commandCamList==null) _commandCamList = new CommandCamList(_blueLogin);  return _commandCamList;  }
    public CommandAlerts getCommandAlerts() throws Exception { if (_commandAlerts==null) _commandAlerts = new CommandAlerts(_blueLogin);  return _commandAlerts;  }
    public CommandStatus getCommandStatus() throws Exception { if (_commandStatus==null) _commandStatus = new CommandStatus(_blueLogin);  return _commandStatus;  }
    public CommandCamConfig getCommandCamConfig() throws Exception { if (_commandCamConfig==null) _commandCamConfig = new CommandCamConfig(_blueLogin);  return _commandCamConfig;  }

    public BlueStatus getLastBlueStatus() { return _lastBlueStatus; }

    public void Action() {
        String session = null;
        Cli cli = new Cli(_args);
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
                if (cli.is_profiles_list()) {
                    BlueProfiles profiles =_blueLogin.getBlueProfiles();
                    log.info("profiles-list: number: " + profiles.size() + "\n" +
                            profiles);
                }
                if (cli.is_schedules_list()) {
                    ArrayList<String> schedules =_blueLogin.getSchedules();
                    log.info("schedules-list: number: " + schedules.size() + "\n" +
                            Arrays.toString(schedules.toArray()));
                }
                if (cli.is_cams_list()) {
                    Cameras cameras =  getCommandCamList().GetCamList();
                    log.info("cameras-list number: " +  getCommandCamList().GetCamList().size() + "\n" +
                            getCommandCamList().GetCamList().toString());
                }
                if (cli.is_cams_reset_stats()) {
                    log.info("cams-reset-stats: \n" +
                            getCommandCamList().ResetCamsStats().toString());
                }
                if (cli.is_alerts_list() ) {
                    Alerts alersts = getCommandAlerts().GetAlertsList(
                            cli.get_alerts_list_4_cam(),
                            cli.get_alerts_list_date());
                    log.info("alerts-list number: " + alersts.size() + "\n" +
                            alersts.toString());
                }
                if (cli.is_alerts_delete() ) {
                    log.info("alerts-delete: \n");
                    getCommandAlerts().AlertsDelete();
                }
                if (cli.get_trigger() != null) {
                    getCommandOther().TriggerCam(cli.get_trigger());
                }
                if (cli.get_camconfig() != null && cli.get_camconfig().length() > 0) {
                    log.info("CamConfig: " + cli.get_camconfig() + "\n" +
                            Utils.GetPrettyJsonString(
                                    getCommandCamConfig().GetCamConfig(cli.get_camconfig())));
                }
                if (cli.is_status_get()) {
                    _lastBlueStatus = getCommandStatus().GetStatus();
                    log.info(_lastBlueStatus.toString());
                }
                if (cli.is_status_set()) {
                    if (cli.get_json() !=null && cli.get_json().length()>0) {
                        _lastBlueStatus = getCommandStatus().SetStatus(cli.get_json() );
                        log.info(_lastBlueStatus.toString());
                    } else {
                        throw new Exception("-status_set option needs -json to be provided.");
                    }
                }
                if (cli.get_ptzcam() != null ) {
                    getCommandOther().SendPtzButton(cli.get_ptzcam(),cli.get_ptzbutton());
                }
            }
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
