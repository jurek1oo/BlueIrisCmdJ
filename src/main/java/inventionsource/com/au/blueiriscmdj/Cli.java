package inventionsource.com.au.blueiriscmdj;
//https://commons.apache.org/proper/commons-cli/usage.html
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class Cli {
    private static final Logger log = (Logger)LogManager.getLogger(Cli.class.getName());

    public LoginParams getLoginParams() {
        return _loginParams;
    }

    private LoginParams _loginParams = null;

    private String[] args = null;
    private Options options = new Options();
    private String _user=null;
    private String _host=null;
    private String _password=null;
    private boolean _get_status =false;
    private boolean _list_profiles=false;
    private boolean _list_schedules=false;
    private boolean _list_cams=false;
    private boolean _list_alerts = false;
    private String _set_profile=null;
    private String _set_schedule=null;
    private String _set_signal=null;

    private String _get_camconfig =null;
    private String _trigger=null;
    private String _ptzcam=null;
    private int _ptzbutton=-1;
    private String _cam_enable=null;
    private String _cam_disable=null;

    public String get_user() {
        return _user;
    }
    public String get_host() {
        return _host;
    }
    public String get_password() {
        return _password;
    }

    public boolean is_get_status() {
        return _get_status;
    }
    public boolean is_list_profiles() {
        return _list_profiles;
    }
    public boolean is_list_schedules() { return _list_schedules; }
    public boolean is_list_cams() { return _list_cams; }
    public boolean is_list_alerts() { return _list_alerts;  }

    public String get_set_profile() {
        return _set_profile;
    }
    public String get_set_schedule() {
        return _set_schedule;
    }
    public String get_set_signal() {
        return _set_signal;
    }
    public String get_camconfig() { return _get_camconfig; }
    public String get_trigger() {
        return _trigger;
    }
    public String get_ptzcam() {
        return _ptzcam;
    }
    public int get_ptzbutton() {
        return _ptzbutton;
    }
    public String get_cam_enable() {
        return _cam_enable;
    }
    public String get_cam_disable() {
        return _cam_disable;
    }

    public Cli(String[] args) {

        this.args = args;

        options.addOption("h", "help", false, "show help.");
        options.addOption("v", "version", false, "show BlueIrisCmdJ version.");
        options.addOption("debug", "debug", false, "Debug Log level - to be compatible with BlueIrisCmd. sets -ll debug.");
        options.addOption("ll", "log-level", true, "Log level: error, info, debug or trace.");
        options.addOption("lf", "log-file", true, "log file name, eg. /home/jurek/biLog.log.");
        options.addOption("u", "user", true, "User to use when connecting.");
        options.addOption("p", "password", true, "Password to use when connecting.");
        options.addOption("host", "host", true,"Blue Iris host to connect to.");
        options.addOption("lp", "list-profiles", false, "List all available profiles.");
        options.addOption("lsch", "list-schedules", false, "List all avaiable schedules.");
        options.addOption("lc", "list-cams", false, "List all avaiable cameras.");
        options.addOption("la", "list-alerts", false, "List all alerts.");
        options.addOption("sp", "set-profile", true, "Set current profile: profile name");
        options.addOption("gs", "get-status", false, "Get Blue Iris status: signal, active profile and schedule");
        options.addOption("sch", "set-schedule", true, "Set current schedule: schedule name.");
        options.addOption("ss", "set-signal", true, "Set current signal.");
        options.addOption("gcc", "get-camconfig", true, "Get camera configuration: cam short name.");
        options.addOption("t", "trigger", true, "Trigger camera: camera-short-name.");
        options.addOption("ptzcam", "ptz-cam", true, "PTZ camera-short-name to send PTZ Button Number: ptz-button to.");
        options.addOption("ptzbutton", "ptz-button", true, "PTZ Button Number: ptz-button to send to cam");
        options.addOption("ce", "cam-enable", true, "Camera enable: camera-short-name.");
        options.addOption("cd", "cam-disable", true, "Camera disable: camera-short-name.");
    }

    public GoodOrBad parse() {
        String logFile = "BlueIrisCmdJ.log";
        String logLevel = "info";
        CommandLineParser parser = new BasicParser();
        GoodOrBad gob = new GoodOrBad();

        StringBuilder errSb = new StringBuilder();
        StringBuilder sb = new StringBuilder();

        CommandLine cmd = null;
        try {
            if (args == null || args.length == 0) {
                gob.bad = "Error. No arguments.\n" + help(null)+getPtzNumbersHelp();
                return gob;
            } else {
                log.debug("Cmd line arguments: " + Arrays.toString(args));
            }
            cmd = parser.parse(options, args);

            if (cmd.hasOption("h")) {
                gob.good = "Ignoring ALL other arguments. Only -help used: \n" + help(null) + getPtzNumbersHelp();
                return gob;
            }
            if (cmd.hasOption("v")) {
                gob.good = "Ignoring ALL other arguments. Only -version used: \n" ;
                return gob;
            }
            if (cmd.hasOption("debug")) {
                logLevel = "debug";
                sb.append ("Logging -log-level: " + logLevel + "\n");
            } else {
                if (cmd.hasOption("ll")) {
                    logLevel = cmd.getOptionValue("ll");
                    sb.append("Logging -log-level: " + logLevel + "\n");
                }
            }
            if (cmd.hasOption("lf")) {
                logFile = cmd.getOptionValue("lf");
                sb.append ("Logging -log-file: " + logFile + "\n");
            }

            Log4j2Config log4j = new Log4j2Config(logFile, logLevel);

            if (cmd.hasOption("u")) {
                _user = cmd.getOptionValue("u");
                sb.append("Using cli argument -user=" + _user + "\n");
            } else {
                errSb.append("Missing -user option\n");
            }
            if (cmd.hasOption("p")) {
                _password = cmd.getOptionValue("p");
                sb.append("Using cli argument -password=***" + _password.length() + "***\n");
            } else {
                errSb.append("Missing -password option\n");
            }
            if (cmd.hasOption("host")) {
                _host = cmd.getOptionValue("host");
                sb.append("Using cli argument -host=" + _host + "\n");
            } else {
                errSb.append("Missing -host option\n");
            }
            if (errSb.toString().length() > 0 ) {
                gob.good = sb.toString();
                gob.bad = errSb.toString() + "\n" + help(null) + getPtzNumbersHelp();
                return gob;
            }
            _loginParams = new LoginParams(_user, _password, _host);

            if (cmd.hasOption("lp")) {
                _list_profiles = true;
                sb.append("Using cli option -list-profiles\n");
            }
            if (cmd.hasOption("lsch")) {
                _list_schedules = true;
                sb.append("Using cli option -list-schedules\n");
            }
            if (cmd.hasOption("lc")) {
                _list_cams = true;
                sb.append("Using cli option -list-cams\n");
            }
            if (cmd.hasOption("la")) {
                _list_alerts = true;
                sb.append("Using cli option -list-alerts\n");
            }
            if (cmd.hasOption("gs")) {
                _get_status = true;
                sb.append("Using cli option -get-status\n");
            }
            if (cmd.hasOption("sp")) {
                _set_profile = cmd.getOptionValue("sp");
                sb.append("Using cli argument -set-profile=" + _set_profile);
            }
            if (cmd.hasOption("sch")) {
                _set_schedule = cmd.getOptionValue("sch");
                sb.append("Using cli argument -set-schedule=" + _set_schedule + "\n");
            }
            if (cmd.hasOption("ss")) {
                _set_signal = cmd.getOptionValue("ss");
                sb.append("Using cli argument -set-signal=" + _set_signal + "\n");
            }
            if (cmd.hasOption("gcc")) {
                _get_camconfig= cmd.getOptionValue("gcc");
               sb.append("Using cli option -get-camconfig\n");
            }
            if (cmd.hasOption("t")) {
                _trigger = cmd.getOptionValue("t");
                sb.append("Using cli argument -trigger=" + _trigger + "\n");
            }
            if (cmd.hasOption("ptzcam")) {
                _ptzcam = cmd.getOptionValue("ptz-cam");
                sb.append("Using cli argument -ptz-cam=" + _ptzbutton + "\n");
            }
            if (cmd.hasOption("ptzbutton")) {
                try {
                    _ptzbutton = Integer.parseInt(cmd.getOptionValue("ptz-button"));
                    sb.append("Using cli argument -ptz-button=" + _ptzbutton + "\n");
                } catch (Exception ex) {
                    errSb.append("Error: ptz-button not an integer: "+ cmd.getOptionValue("ptz-button"));
                }
            }
            if((cmd.hasOption("ptzcam") || cmd.hasOption("ptzbutton")) &&
                    !(cmd.hasOption("ptzcam") && cmd.hasOption("ptzbutton") ))
            {
                errSb.append("Missing ptz option. If one ptz option is present, the other has to be present as well.\n");
            }
            if (cmd.hasOption("ce")) {
                _cam_enable = cmd.getOptionValue("ce");
                sb.append("Using cli argument -cam-enable=" + _cam_enable + "\n");
            }
            if (cmd.hasOption("cd")) {
                _cam_disable = cmd.getOptionValue("cd");
                sb.append("Using cli argument -cam-disable=" + _cam_disable + "\n");
            }
            gob.good = sb.toString();
            gob.bad = errSb.toString();
            return gob;
        } catch (ParseException e) {
            log.error("Error: ", e);
            gob.good = sb.toString();
            gob.bad = errSb.toString() + "\nFailed to parse comand line properties: " + help(null) + getPtzNumbersHelp()+ "\n\n" + e;
            return gob;
        } catch (Exception ex) {
            log.error("Error: ", ex);
            gob.good = sb.toString();
            gob.bad = errSb.toString() + "\n\n" + help(null) + getPtzNumbersHelp()+ "\n\n" + ex;
            return gob;
        }
    }

    private String help(StringBuilder sb) {
        if (sb == null) {
            sb = new StringBuilder();
        }
        HelpFormatter formatter = new HelpFormatter();
        StringWriter out = new StringWriter();
        PrintWriter pw = new PrintWriter(out);
        formatter.printHelp(pw, 80, "BluIrisCmdJ", "----------------------------------", options,
                formatter.getLeftPadding(), formatter.getDescPadding(), "----------------------------------", true);
        pw.flush();
        sb.append("\n" + out.toString());
        return sb.toString();
    }

    public class GoodOrBad {
        public String good = null;// return good comment
        public String bad = null;// if anything bad, return here

        public String toString(){
            String s = "good: " + good + "\nbad: " + bad + "\n";
            log.debug(s);
            return s;
        }
    }

    public String getPtzNumbersHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nPTZ Numbers:\n#0: Pan left");
        sb.append("#1: Pan right\n");
        sb.append("#2: Tilt up\n");
        sb.append("#3: Tilt down\n");
        sb.append("#4: Center or home (if supported by camera)\n");
        sb.append("#5: Zoom in\n");
        sb.append("#6: Zoom out\n");
        sb.append("#8..10: Power mode, 50, 60, or outdoor\n");
        sb.append("#11..26: Brightness 0-15\n");
        sb.append("#27..33: Contrast 0-6\n");
        sb.append("#34..35: IR on, off\n");
        sb.append("#101..120: Go to preset position 1..20\n");
        return sb.toString() ;
    }
}