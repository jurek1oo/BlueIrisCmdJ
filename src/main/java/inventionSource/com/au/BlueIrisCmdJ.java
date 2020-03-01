package inventionSource.com.au;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * BlueIrisCmdJ Issues http commands, to control BlueIris, via json API.
 *
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class BlueIrisCmdJ
{
    public static final Logger log = LogManager.getLogger(BlueIrisCmdJ.class.getName());
    public static final String VERSION = "1.5";// 2020-03-01  1st in git.
    //public static final String VERSION = "1.4";// 2020-03-01  added the rest of commands.
    //public static final String VERSION = "1.3";// 2020-02-26  refactor. added: get/set status components: signal, profile, schedule
    //public static final String VERSION = "1.2";// 2020-02-24 basic version working,set profile, get status.
    //public static final String VERSION = "1.0";// start 2020-02-16

    public static void main( String[] args ) {
        log.info("BlueIrisCmd version: " + BlueIrisCmdJ.VERSION + " - starting!");
        MasterController masterController=  new MasterController(args);
        masterController.Action();
    }

}
