package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandCamList {
/*
camlist
    Returns a list of cameras on the system ordered by group.
    Cameras not belonging to any group are shown beneath the "all cameras" group.
    Disabled cameras are placed at the end of the list.

    reset: send a value of true for this argument to reset the statistics for the cameras.

    data is an array of objects (note the [] surrounding a JSON array),
    each describing a camera or a camera group.  For each of these objects,
    the following values are defined:

        optionsDisplay: the camera or group name
        optionsValue: the camera or group short name, used for other requests and
            commands requiring a camera short name
        FPS: the current number of frames/second delivered from the camera
        color: 24-bit RGB value (red least significant) representing the camera's display color
        clipsCreated: the number of clips created since the camera stats were last reset
        isAlerting: true or false; currently sending an alert
        isEnabled: true or false
        isOnline true or false
        isMotion: true or false
        isNoSignal: true or false
        isPaused: true or false
        isTriggered: true or false
        isRecording: true or false
        isYellow: true or false; the yellow caution icon
        profile: the camera's currently active profile, or as overridden by
            the global schedule or the UI profile buttons.
        ptz: is PTZ supported, true or false
        audio: is audio supported, true or false
        width: width of the standard video frame
        height: height of the standard video frame
        nTriggers: number of trigger events since last reset
        nNoSignal: number of no signal events since last reset
        nClips: number of no recording events since last reset
 */
    private static final Logger log = (Logger)LogManager.getLogger(CommandCamList.class);

    private BlueLogin _blueLogin = null;
    public BlueLogin getBlueLogin() { return _blueLogin; }

    public CommandCamList(BlueLogin blueLogin) throws Exception {
        _blueLogin = blueLogin;
    }

    public Cameras GetCamList() throws Exception {
        log.debug("camlist: "); // return json data element with all cameras details
        String cmd = "camlist";
        try {
            boolean hasToBeSuccess = true;
            boolean getDataElement = true;
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);

            JsonElement jsonDataElement = commandCoreRequest.RunTheCmd(cmd, null,hasToBeSuccess,getDataElement);

            Cameras cameras = new Cameras(jsonDataElement);
            log.debug("Cameras: " + cameras.toStringAll());
            return cameras;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n" + "\n\n", e);
            throw e;
        }
    }

    public Cameras ResetCamsStats() throws Exception {
         String cmd = "camlist";
        log.debug(cmd);
        try {
            String cmdParams = ",\"reset\":true";
            boolean hasToBeSuccess = true;
            boolean getDataElement = true;
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);

            JsonElement jsonDataElement = commandCoreRequest.RunTheCmd(cmd, cmdParams,hasToBeSuccess,getDataElement);

            Cameras cameras = new Cameras(jsonDataElement);
            log.debug("Cameras: " + cameras.toStringAll());
            return cameras;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n" + "\n\n", e);
            throw e;
        }
    }
}