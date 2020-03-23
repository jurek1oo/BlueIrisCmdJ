package inventionsource.com.au.blueiriscmdj;
//https://commons.apache.org/proper/commons-cli/usage.html
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
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

    private boolean _is_alerts_delete = false;
    private boolean _is_alerts_list = false;
    private boolean _is_cams_list =false;
    private boolean _is_cams_reset_stats =false;
    private boolean _is_status_get =false;
    private boolean _is_profiles_list =false;
    private boolean _is_schedules_list =false;


    private boolean _is_status_set =false;

    private String _alerts_list_4_cam = null;
    private String _alerts_list_date = null;
    private String _camconfig_get =null;
    private String _camconfig_set =null;
    private String _json=null;
    private String _ptzcam=null;
    private int _ptzbutton=-1;
    private String _trigger=null;

    public boolean is_alerts_delete() { return _is_alerts_delete; }
    public boolean is_alerts_list() { return _is_alerts_list; }
    public boolean is_cams_list() { return _is_cams_list; }
    public boolean is_cams_reset_stats() {   return _is_cams_reset_stats;  }
    public boolean is_profiles_list() {        return _is_profiles_list;    }
    public boolean is_schedules_list() {        return _is_schedules_list;    }
    public boolean is_status_get() {        return _is_status_get;    }
    public boolean is_status_set() {
        return _is_status_set;
    }

    public String get_camconfig() { return _camconfig_get; }
    public String get_camconfig_set() {  return _camconfig_set;  }
    public String get_json() {  return _json; }
    public String get_trigger() {       return _trigger;   }
    public String get_ptzcam() {        return _ptzcam;    }
    public int get_ptzbutton() {        return _ptzbutton;    }
    public String get_alerts_list_4_cam() { return _alerts_list_4_cam;  }
    public String get_alerts_list_date() { return _alerts_list_date; }

    public Cli(String[] args) {

        this.args = args;

        options.addOption("ad", "alerts-delete", false, "Delete all alerts.");
        Option option = new Option("al", "alerts-list", true, "List alerts for camera short name, optional.");
        option.setOptionalArg(true);
        options.addOption(option);
        options.addOption("ald", "alerts-list-date", true, "List alerts from date (sql type) yyyy-mm-dd hh:mm e.g. 2020-03-27 23:05");
        options.addOption("ccg", "camconfig-get", true, "Get camera configuration: cam short name.");
        options.addOption("ccs", "camconfig-set", true, "Get camera configuration: cam short name. Use -json.");
        options.addOption("cl", "cams-list", false, "List all avaiable cameras.");
        options.addOption("cr", "cams-reset-stats", false, "Reset statistics of for all cameras.");
        options.addOption("h", "help", false, "show help.");
        options.addOption("host", "host", true,"Blue Iris host to connect to.");
        options.addOption("j", "json", true, "Json for options: -camconfig-set or -status-set. Check project Github wiki for help.");
        options.addOption("ll", "log-level", true, "Log level: error, info, debug or trace.");
        options.addOption("lf", "log-file", true, "log file name, eg. /temp/BlueIrisCmdJ.log.");
        options.addOption("p", "password", true, "Password to use when connecting.");
        options.addOption("pl", "profiles-list", false, "List all available profiles.");
        options.addOption("ptzc", "ptz-cam", true, "PTZ camera-short-name to send PTZ Button Number: ptz-button to.");
        options.addOption("ptzb", "ptz-button", true, "PTZ Button Number: ptz-button to send to cam");
        options.addOption("schl", "schedules-list", false, "List all avaiable schedules.");
        options.addOption("stg", "status-get", false, "Get Blue Iris status: signal, active profile and schedule");
        options.addOption("sts", "status-set", false, "Set Blue Iris status: signal, active profile or/and schedule. Use -json.");
        options.addOption("t", "trigger", true, "Trigger camera: camera-short-name.");
        options.addOption("u", "user", true, "User to use when connecting.");
        options.addOption("v", "version", false, "show BlueIrisCmdJ version.");
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
//log.debug("Cmd line arguments: " + Arrays.toString(args));
            }
            cmd = parser.parse(options, args);

            if (cmd.hasOption("h")) {
                gob.good = "Ignoring ALL other arguments. Only --help used: \n" + help(null) + getPtzNumbersHelp();
                return gob;
            }
            if (cmd.hasOption("v")) {
                gob.good = "Ignoring ALL other arguments. Only --version used: \n" ;
                return gob;
            }
            sb.append("\n");
            if (cmd.hasOption("ll")) {
                logLevel = cmd.getOptionValue("ll");
                sb.append("Logging --log-level: " + logLevel + "\n");
            }
            if (cmd.hasOption("lf")) {
                logFile = cmd.getOptionValue("lf");
                sb.append ("Logging --log-file: " + logFile + "\n");
            }
            Log4j2Config log4j = new Log4j2Config(logFile, logLevel);

            if (cmd.hasOption("u")) {
                _user = cmd.getOptionValue("u");
                sb.append("Using cli argument --user=" + _user + "\n");
            } else {
                errSb.append("Missing -user option\n");
            }
            if (cmd.hasOption("p")) {
                _password = cmd.getOptionValue("p");
                sb.append("Using cli argument --password=***" + _password.length() + "***\n");
            } else {
                errSb.append("Missing --password option\n");
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

// ******************************************************************************

            if (cmd.hasOption("ad")) {
                _is_alerts_delete = true;
                sb.append("Using cli option --alerts-delete\n");
            }
            if (cmd.hasOption("al")) {
                _is_alerts_list = true;
                _alerts_list_4_cam = cmd.getOptionValue("alerts-list");
                sb.append("Using cli option --alerts-list cam name: " + _alerts_list_4_cam +"\n");
            }
            if (cmd.hasOption("ald")) {
                _alerts_list_date = cmd.getOptionValue("alerts-list-date");
                sb.append("Using cli option --alerts-list-date: " +_alerts_list_date + "\n");
                if (!cmd.hasOption("al")) {
                    errSb.append("Error: --alerts-list-date has to be used with --alerts-list option, which is not present.");
                }
            }
            if (cmd.hasOption("ccg")) {
                _camconfig_get = cmd.getOptionValue("ccg");
                sb.append("Using cli option --camconfig-get: " +  _camconfig_get + "\n");
            }
            if (cmd.hasOption("ccs")) {
                if ( !cmd.hasOption("j")) {
                    errSb.append("Error: Both options need to be present --camconfig-set and --json.");
                }
                _camconfig_set = cmd.getOptionValue("ccs");
                sb.append("Using cli option --camconfig-set: " + _camconfig_set + "\n");
            }
            if (cmd.hasOption("cl")) {
                _is_cams_list = true;
                sb.append("Using cli option --cams-list\n");
            }
            if (cmd.hasOption("crt")) {
                _is_cams_reset_stats = true;
                sb.append("Using cli option --cams_reset_stats\n");
            }
            if (cmd.hasOption("j")) {
                _json = cmd.getOptionValue("j");
                sb.append("Using cli argument --json=" + _json + "\n");
            }
            if (cmd.hasOption("pl")) {
                _is_profiles_list = true;
                sb.append("Using cli option --profiles-list\n");
            }
            if (cmd.hasOption("ptzc")) {
                _ptzcam = cmd.getOptionValue("ptzc");
                sb.append("Using cli argument --ptz-cam=" + _ptzcam + "\n");
            }
            if (cmd.hasOption("ptzb")) {
                try {
                    _ptzbutton = Integer.parseInt(cmd.getOptionValue("ptzb"));
                    sb.append("Using cli argument --ptz-button=" + _ptzbutton + "\n");
                } catch (Exception ex) {
                    errSb.append("Error: ptz-button not an integer: "+ cmd.getOptionValue("ptzb"));
                }
            }
            if( (cmd.hasOption("ptzc") || cmd.hasOption("ptzb")) &&
                    !(cmd.hasOption("ptzc") && cmd.hasOption("ptzb") ))
            {
                errSb.append("Missing one of ptz option. You need --ptz-cam and --ptz-button.\n");
            }
            if (cmd.hasOption("schl")) {
                _is_schedules_list = true;
                sb.append("Using cli option --schedules-list\n");
            }
            if (cmd.hasOption("stg")) {
                _is_status_get = true;
                sb.append("Using cli option --status-get\n");
            }
            if (cmd.hasOption("sts")) {
                _is_status_set = true;
                sb.append("Using cli option --status-set\n");
                if ( !cmd.hasOption("j")) {
                    errSb.append("Error: Both options need to be present -status-set and -json.");
                }
            }
            if (cmd.hasOption("sts") && cmd.hasOption("ccs")){
                errSb.append("Error: Only one of the options can be used at one time:" +
                        " --status-set or --camconfig-set.");
            }
            if (cmd.hasOption("j")  &&
                    !(cmd.hasOption("ccs") || cmd.hasOption("sts"))  ){
                errSb.append("Error: -json option needs to be used with -camconfig-set or -status-set.");
            }
            if (cmd.hasOption("t")) {
                _trigger = cmd.getOptionValue("t");
                sb.append("Using cli argument --trigger=" + _trigger + "\n");
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