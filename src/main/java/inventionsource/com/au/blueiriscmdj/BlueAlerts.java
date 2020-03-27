package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class BlueAlerts {
    private static final Logger log = (Logger)LogManager.getLogger(BlueAlerts.class.getName());

    private JsonElement _dataJson = null;

    private ArrayList<BlueAlert> _alerts = new ArrayList<BlueAlert>();

    public BlueAlert get(int i) {
        return (BlueAlert)_alerts.get(i);
    }

    public int size() {
        return _alerts.size();
    }

    public String toString() {
        if (size()>0){
            StringBuilder sb = new StringBuilder();
            sb.append("[\n");
            for(int i = 0; i < size(); i++){
                if (i>0 && i < size()-1){
                    sb.append(",\n");
                }
                sb.append(get(i).toString());
            }
            sb.append("\n]\n");
            return sb.toString();
        }
        return "[]";
    }

    public BlueAlerts(JsonElement dataJson) throws Exception {
        if(dataJson==null)
            log.debug("null dataJson");
        _dataJson = dataJson;
        if (dataJson != null) {
            JsonArray jsonArray = dataJson.getAsJsonArray();
            boolean active = false;
            for (int i = 0, size = jsonArray.size(); i < size; i++) {
                JsonElement jsonElement = jsonArray.get(i);
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                BlueAlert alert = new BlueAlert(jsonObject);
                _alerts.add(alert);
            }
        }
    }
    public class  BlueAlert {
        private final Logger log = (Logger)LogManager.getLogger(BlueAlert.class.getName());
         /*   "camera": "Front-Watashi",
                "path": "@118580940.bvr",
                "clip": "@118572667.bvr",
                "offset": 0,
                "flags": 196608,
                "res": "720x1280",
                "zones": 1,
                "date": 1584322618,
                "color": 8151097,
                "filesize": "14sec (562K)"
                */
        private JsonObject _jsonObject = null;
        private String _camera = null;
        private String _path = null;
        private String _clip = null;
        private int _offset = -1;
        private int _flags = -1;
        private String _res = null;
        private int _zones = -1;
        private long _dateInSeconds = -1;
        private LocalDateTime _localDate = null;
        private int _color = -1;
        private String _filesize = null;

        public String getCamera() { return _camera; }
        public String getPath() {  return _path; }
        public String getClip() { return _clip; }
        public int getOffset() { return _offset; }
        public int getFlags() { return _flags; }
        public String getRes() { return _res;}
        public int getZones() { return _zones; }
        public long getDateInSeconds() { return _dateInSeconds; }
        public LocalDateTime getLocalDate() { return _localDate;   }
        public int getColor() { return _color; }
        public String getFilesize() { return _filesize; }


        public JsonObject getJsonObject() {  return _jsonObject;  }

        public BlueAlert(JsonObject jsonObject) {
            _jsonObject = jsonObject;

            _camera = jsonObject.get("camera").getAsString();
            _path = jsonObject.get("path").getAsString();
            _clip = jsonObject.get("clip").getAsString();
            _offset = jsonObject.get("offset").getAsInt();
            _flags = jsonObject.get("flags").getAsInt();
            _res = jsonObject.get("res").getAsString();
            _zones = jsonObject.get("zones").getAsInt();
            _dateInSeconds = jsonObject.get("date").getAsLong();
            _localDate = Utils.GetLocalDateTimeFromSeconds(_dateInSeconds);
            _color = jsonObject.get("color").getAsInt();
            _filesize = jsonObject.get("filesize").getAsString();
        }

        public String toString() {
            return Utils.GetPrettyJsonString(_jsonObject);
        }
    }
}
