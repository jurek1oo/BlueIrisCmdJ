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

    public BlueCmdRequest getBlueCmdRequest() {
        return _blueCmdRequest;
    }

    private BlueLogin _blueLogin = null;
    private BlueCmdRequest _blueCmdRequest = null;

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
                _blueCmdRequest = new BlueCmdRequest(_blueLogin);
                session = _blueCmdRequest.getSession();
                if (session == null || session.length() == 0) {
                    _blueLogin = null;
                    _blueCmdRequest = null;
                    throw new Exception("Error. Could not loggin. No session. ");
                }
                if (cli.list_profiles()) {
                    log.info("list-profiles: " +
                            Arrays.toString(_blueCmdRequest.getProfiles().toArray()));
                }
                if (cli.list_schedules()) {
                    log.info("list-schedules: " +
                            Arrays.toString(_blueCmdRequest.getSchedules().toArray()));
                }
                if (cli.list_cams()) {
                    log.info("list-cameras: \n" +
                            _blueCmdRequest.GetList_Cams().toStringAll());
                }
                if (cli.get_cam_disable() != null) {
                    _blueCmdRequest.CamDisable(cli.get_cam_disable());
                }
                if (cli.get_cam_enable() != null) {
                    _blueCmdRequest.CamEnable(cli.get_cam_enable());
                }
                if (cli.get_trigger() != null) {
                    _blueCmdRequest.TriggerCam(cli.get_trigger());
                }
                if (cli.get_camconfig() != null && cli.get_camconfig().length() > 0) {
                    _blueCmdRequest.GetCamConfig(cli.get_camconfig());
                }
                if (cli.get_status()) {
                    _blueCmdRequest.GetStatus();
                }
                if (cli.get_set_profile() != null && cli.get_set_profile().length() > 0) {
                    _blueCmdRequest.SetProfile(cli.get_set_profile());
                }
                if (cli.get_set_schedule() != null && cli.get_set_schedule().length() > 0) {
                    _blueCmdRequest.SetSchedule(cli.get_set_schedule());
                }
                if (cli.get_set_signal() != null && cli.get_set_signal().length() > 0) {
                    _blueCmdRequest.SetSignal(cli.get_set_signal());
                }
                if (cli.get_ptzcam() != null ) {
                    _blueCmdRequest.SendPtzButton(cli.get_ptzcam(),cli.get_ptzbutton());
                }
            }
        } catch (Exception e) {
            log.error("Exception: " + e);
        } finally {
            log.info("Logging out.");
            if( _blueCmdRequest != null){
                try {
                    _blueLogin.BlueIrisLogout();
                } catch (Exception ex){
                    log.error("Error logging out: ", ex);
                }
            }
        }
    }
}
