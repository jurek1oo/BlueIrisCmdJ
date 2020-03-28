package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class BlueCamConfig {
    private static final Logger log = (Logger)LogManager.getLogger(BlueCamConfig.class.getName());

    private JsonObject _jsonObject = null;


    private String _camera = null;
    private int _profile = -1;
    private int _pause = -1;
    private int _lock = -1;
    private boolean _motion = false;
    private boolean _output = false;
    private boolean _push = false;
    private boolean _audio = false;

    private boolean _schedule = false;
    private boolean _ptzcycle = false;
    private boolean _ptzevents = false;
    private int _alerts = 0;
    private int _record = 0;

    public String getCameraName() {        return _camera;    }
    public int getProfile() {        return _profile;    }
    public int get_pause() {        return _pause;    }
    public int getLock() {        return _lock;    }
    public boolean is_motion() {        return _motion;    }
    public boolean is_output() {        return _output;    }
    public boolean is_push() {        return _push;    }
    public boolean is_audio() {        return _audio;    }

    public boolean is_schedule() {        return _schedule;    }
    public boolean is_ptzcycle() {        return _ptzcycle;    }
    public boolean is_ptzevents() {        return _ptzevents;    }
    public int get_alerts() {        return _alerts;    }
    public int get_record() {        return _record;    }

    public JsonObject getJsonObject() {
        return _jsonObject;
    }

    private JsonElement _dataJson = null;

    public String toStringPretyJson() {
        return "{\"camera\": "+ _camera + ", " +Utils.GetPrettyJsonString(_dataJson) + "}";
    }

    public BlueCamConfig(JsonElement dataJson, String camera) throws Exception {
        try {
            if(dataJson==null) throw new Exception("Error. null dataJson");
            _dataJson = dataJson;
            if(camera==null) throw new Exception("Error. null camera name");
            _camera = camera;

            _jsonObject = _dataJson.getAsJsonObject();
//{"profile":0,"lock":0,"pause":0,"push":false,"audio":true,"motion":true,"schedule":false,
// "ptzcycle":false,"ptzevents":false,"alerts":0,"output":false,"setmotion":
// {"audio_trigger":false,"audio_sense":10000,"usemask":true,"sense":10480,"contrast":40,"showmotion":2,"shadows":true,"luminance":false,"objects":true,"maketime":10,"breaktime":100},
// "record":2}

            _pause = _jsonObject.get("pause").getAsInt();
            _motion = _jsonObject.get("motion").getAsBoolean();;
            _push = _jsonObject.get("push").getAsBoolean();;
            _output = _jsonObject.get("output").getAsBoolean();;
            _lock = _jsonObject.get("lock").getAsInt();;
            _profile = _jsonObject.get("profile").getAsInt();;
            _audio = _jsonObject.get("audio").getAsBoolean();
            _ptzcycle = _jsonObject.get("ptzcycle").getAsBoolean();
            _ptzevents = _jsonObject.get("ptzevents").getAsBoolean();
            _alerts =_jsonObject.get("alerts").getAsInt();
            _record = _jsonObject.get("record").getAsInt();
        } catch (Exception e) {
            log.error("\nError camera: " + camera  + " in dataJson: " +  dataJson +
                    " : " + setJsonHelp(), e);
            throw e;
        }
    }

    public String setJsonHelp(){
        StringBuilder sb = new StringBuilder();
        sb.append("camconfig-set json example:\n'{ \"reset\":false,\"enable\":true,\"pause\":0," +
                "\"motion\":true,\"schedule\":true,\"ptzcycle\":true," +
                "\"ptzevents\":true,\"alerts\":0\"record\":2}'\n");
        sb.append("reset:true -reset the camera\n");
        sb.append("enable:true or false, enable or disable the camera\n");
        sb.append("pause:n sends a pause command, and returns a value in seconds\n");
        sb.append("   -1:   pause\n");
        sb.append("    0:   un-pause\n");
        sb.append(" 1..3:   add 30 seconds  1 minute  1 hour to the pause\n");
        sb.append("motion:true or false enable / disable the motion detector\n");
        sb.append("schedule:true or false enable / disable the camera's custom schedule\n");
        sb.append("ptzcycle:true or false enable / disable the preset-cycle feature\n");
        sb.append("ptzevents:true or false enable / disable the PTZ event schedule\n");
        sb.append("alerts:n (get/set) alert function number\n");
        sb.append("record:n (get/set) record function number\n");
        return sb.toString();
    }

}
