package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class BlueCameras {
    private static final Logger log = (Logger)LogManager.getLogger(BlueCameras.class.getName());

    private JsonElement _dataJson = null;

    private ArrayList<BlueCamera> _cameras = new ArrayList<BlueCamera>();

    public BlueCamera get(int i) {
        return (BlueCamera)_cameras.get(i);
    }

    public BlueCamera get(String optionValue) throws Exception {
        for (int i=0; i < size(); i++) {
            if (get(i).getOptionValue().compareTo((optionValue))==0) {
                return (BlueCamera)_cameras.get(i);
            }
        }

        throw new Exception("Error. No camera with the optionValue: " + optionValue);
    }

    public int size() {
        return _cameras.size();
    }

    public String toString() {
        if (size()>0){
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < size(); i++){
                if (i>0 && i < size()-1){
                    sb.append(",\n");
                }
                sb.append(get(i).toString());
            }
            sb.append("\n}\n");
            return sb.toString();
        }
        return null;
    }

    public BlueCameras (JsonElement dataJson) throws Exception {
        if(dataJson==null)
            throw new Exception("Error. null dataJson");
        _dataJson = dataJson;
        JsonArray jsonArray = dataJson.getAsJsonArray();
        boolean active = false;
        for (int i = 0, size = jsonArray.size(); i < size; i++)
        {
            JsonElement jsonElement = jsonArray.get(i);

            JsonObject jsonObject =  jsonElement.getAsJsonObject();
            try {
                active = jsonObject.get("active").getAsBoolean();
            } catch (Exception ex) {
                continue;
            }
            BlueCamera camera = new BlueCamera(jsonObject);
            _cameras.add(camera);
        }

    }

    public static String HelpGet(){
        StringBuilder sb = new StringBuilder();
        sb.append(" Check wiki for more info:\n " +
                "https://github.com/jurek1oo/blueiriscmdj/wiki/cams-list.\n");
        return sb.toString();
    }

    public class  BlueCamera {
        private final Logger log = (Logger)LogManager.getLogger(BlueCamera.class.getName());

        private JsonObject _jsonObject = null;
        private String _optionDisplay = null;
        private String _optionValue = null;
        private boolean _active = false;
        private boolean _isEnabled = false;
        private boolean _webcast = false;
        private boolean _isOnline = false;
        private boolean _hidden = false;
        private boolean _isPaused = false;
        private boolean _isRecording = false;
        private boolean _isManRec = false;
        private boolean _isNoSignal = false;
        private boolean _isAlerting = false;
        private boolean _isTriggered = false;
        private boolean _isYellow = false;
        private boolean _isMotion = false;
        private String _error = null;
        private int _newalerts = 0;
        private int _profile = 0;
        private int _nTriggers = 0;
        private int _nClips = 0;
        private int _nNoSignal = 0;

        public JsonObject getJsonObject() {
            return _jsonObject;
        }
        public String getOptionDisplay() {
            return _optionDisplay;
        }
        public String getOptionValue() {
            return _optionValue;
        }
        public boolean isActive() {
            return _active;
        }
        public boolean isEnabled() {
            return _isEnabled;
        }
        public boolean isWebcast() {
            return _webcast;
        }
        public boolean isOnline() {
            return _isOnline;
        }
        public boolean isHidden() {
            return _hidden;
        }
        public boolean isPaused() {
            return _isPaused;
        }
        public boolean is_isRecording() {
            return _isRecording;
        }
        public boolean isManRec() {
            return _isManRec;
        }
        public boolean isNoSignal() { return _isNoSignal;  }
        public boolean is_isAlerting() {
            return _isAlerting;
        }
        public boolean isTriggered() {
            return _isTriggered;
        }
        public boolean isYellow() {
            return _isYellow;
        }
        public boolean isMotion() {
            return _isMotion;
        }
        public String getError() {
            return _error;
        }
        public int getNewalerts() {
            return _newalerts;
        }
        public int getProfile() {
            return _profile;
        }
        public int getNTriggers() {
            return _nTriggers;
        }
        public int getNClips() {
            return _nClips;
        }
        public int getNNoSignal() {
            return _nNoSignal;
        }

        public BlueCamera(JsonObject jsonObject) {
            _jsonObject = jsonObject;

            _optionDisplay = jsonObject.get("optionDisplay").getAsString();
            _optionValue = jsonObject.get("optionValue").getAsString();
            _error = jsonObject.get("error").getAsString();
            _active = jsonObject.get("active").getAsBoolean();
            _isEnabled = jsonObject.get("isEnabled").getAsBoolean();
            _webcast = jsonObject.get("webcast").getAsBoolean();
            _isOnline = jsonObject.get("isOnline").getAsBoolean();
            _hidden = jsonObject.get("hidden").getAsBoolean();
            _isPaused = jsonObject.get("isPaused").getAsBoolean();
            _isRecording = jsonObject.get("isRecording").getAsBoolean();
            _isManRec = jsonObject.get("isManRec").getAsBoolean();
            _isNoSignal = jsonObject.get("isNoSignal").getAsBoolean();
            _isAlerting = jsonObject.get("isAlerting").getAsBoolean();
            _isTriggered = jsonObject.get("isTriggered").getAsBoolean();
            _isYellow = jsonObject.get("isYellow").getAsBoolean();
            _isMotion = jsonObject.get("isMotion").getAsBoolean();
            _newalerts = jsonObject.get("newalerts").getAsInt();
            _profile = jsonObject.get("profile").getAsInt();
            _nTriggers = jsonObject.get("nTriggers").getAsInt();
            _nClips = jsonObject.get("nClips").getAsInt();
            _nNoSignal = jsonObject.get("nNoSignal").getAsInt();
        }

        public String toString() {
            return Utils.GetPrettyJsonString(_jsonObject);
        }

    }
}
