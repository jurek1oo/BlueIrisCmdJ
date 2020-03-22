package inventionsource.com.au.blueiriscmdj;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 * BlueIrisCmdJ Issues http commands, to control BlueIris, via json API.
 *
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class BlueIrisCmdJ
{
    public static final Logger log = (Logger)LogManager.getLogger(BlueIrisCmdJ.class.getName());
    public static final String VERSION = "1.2.00";// 2020-03-22 Major refactor. Command line naming convention changed.
    //public static final String VERSION = "1.1.56";// 2020-03-21 refactor, add set camconfig with json, reser cams stats.
    //public static final String VERSION = "1.1.53";// 2020-03-05 split java 8 / 11 versions in pom.
    //public static final String VERSION = "1.1.50";// 2020-03-01  1st in git.
    //public static final String VERSION = "1.1.4";// 2020-03-01  added the rest of commands.
    //public static final String VERSION = "1.1.3";// 2020-02-26  refactor. added: get/set status components: signal, profile, schedule
    //public static final String VERSION = "1.1.2";// 2020-02-24 basic version working,set profile, get status.
    //public static final String VERSION = "1.1.0";// start 2020-02-16

    public static void main( String[] args ) {
        log.info("BlueIrisCmd version: " + BlueIrisCmdJ.VERSION + " - starting!");
        log.info("Timezone: " + Utils.GetDefaultTimeZoneName());
        MasterController masterController=  new MasterController(args);
        masterController.Action();
    }

}
