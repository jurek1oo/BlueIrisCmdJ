package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

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

    public CommandOther getCommandOther() {
        return _commandOther;
    }

    public BlueLogin getBlueLogin() {   return _blueLogin;  }

    private BlueLogin _blueLogin = null;
    private CommandOther _commandOther = null;
    private CommandStatus _commandStatus = null;
    private CommandAlerts _commandAlerts = null;
    private CommandCamConfig _commandCamConfig = null;

    private BlueStatus _lastBlueStatus = null;

    public BlueStatus getLastBlueStatus() { return _lastBlueStatus; }

    public void Action() {
        log.debug("BlueIrisCmdJ MasterController in action.");
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
                _commandOther = new CommandOther(_blueLogin);
                _commandCamConfig = new CommandCamConfig(_blueLogin);
                _commandStatus = new CommandStatus(_blueLogin);
                _commandAlerts = new CommandAlerts(_blueLogin);
                session = _blueLogin.getSession();
                if (session == null || session.length() == 0) {
                    _blueLogin = null;
                    _commandOther = null;
                    throw new Exception("Error. Could not loggin. No session. ");
                } else {
                    log.info("Login OK host: " + cli.getLoginParams().getHost() + " user: " + cli.getLoginParams().getUser()+
                            " password: ***" + cli.getLoginParams().getPassword().length() + "***");
                }
                if (cli.is_list_profiles()) {
                    log.info("list-profiles: " +_blueLogin.getBlueProfiles());
                }
                if (cli.is_list_schedules()) {
                    log.info("list-schedules: " +
                            Arrays.toString(_blueLogin.getSchedules().toArray()));
                }
                if (cli.is_list_cams()) {
                    log.info("list-cameras: \n" +
                            _commandOther.GetList_Cams().toStringAll());
                }
                if (cli.is_list_alerts() ) {
                    log.info("list-alerts: \n" +
                            _commandAlerts.GetList_Alerts(cli.get_list_alerts(),
                                    cli.get_list_alerts_date()).toString());
                }
                if (cli.is_delete_alerts() ) {
                    log.info("delete-alerts: \n");
                    _commandAlerts.Delete_Alerts();
                }
                if (cli.get_cam_disable() != null) {
                    _commandCamConfig.CamEnableDisable(cli.get_cam_disable(),false);
                }
                if (cli.get_cam_enable() != null) {
                    _commandCamConfig.CamEnableDisable(cli.get_cam_enable(), true);
                }
                if (cli.get_trigger() != null) {
                    _commandOther.TriggerCam(cli.get_trigger());
                }
                if (cli.get_camconfig() != null && cli.get_camconfig().length() > 0) {
                    _commandCamConfig.GetCamConfig(cli.get_camconfig());
                }
                if (cli.is_get_status()) {
                    _lastBlueStatus = _commandStatus.GetStatus();
                    log.info(_lastBlueStatus.toString());
                }
                if (cli.get_set_profile() != null && cli.get_set_profile().length() > 0) {
                    int profileInt = _blueLogin.getBlueProfiles().getProfileInt(cli.get_set_profile());
                    _lastBlueStatus = _commandStatus.SetProfile(profileInt);
                }
                if (cli.get_set_schedule() != null && cli.get_set_schedule().length() > 0) {
                    _lastBlueStatus = _commandStatus.SetSchedule(cli.get_set_schedule());
                }
                if (cli.get_set_signal() != null && cli.get_set_signal().length() > 0) {
                    _lastBlueStatus = _commandStatus.SetSignal(cli.get_set_signal());
                }
                if (cli.get_ptzcam() != null ) {
                    _commandOther.SendPtzButton(cli.get_ptzcam(),cli.get_ptzbutton());
                }
            }
        } catch (Exception e) {
            log.error("Exception: " + e);
        } finally {
            log.info("Logging out.");
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
