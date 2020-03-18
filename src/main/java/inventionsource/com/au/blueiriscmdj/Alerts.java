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
public class Alerts {
    private static final Logger log = (Logger)LogManager.getLogger(Alerts.class.getName());

    private JsonElement _dataJson = null;

    private ArrayList<Alert> _alerts = new ArrayList<Alert>();

    public Alert get(int i) {
        return (Alert)_alerts.get(i);
    }

    public Alert get(String value) throws Exception {
        for (int i=0; i < size(); i++) {
            //if (get(i).getOptionValue().compareTo((optionValue))==0) {
            //    return (Alert)_alerts.get(i);
            //}
        }
        throw new Exception("Error. No Alert with the value: " + value);
    }

    public int size() {
        return _alerts.size();
    }

    public String toStringAll() {
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

    public Alerts(JsonElement dataJson) throws Exception {
        if(dataJson==null)
            throw new Exception("Error. null dataJson");
        _dataJson = dataJson;
        JsonArray jsonArray = dataJson.getAsJsonArray();
        boolean active = false;
        for (int i = 0, size = jsonArray.size(); i < size; i++)
        {
            JsonElement jsonElement = jsonArray.get(i);
            JsonObject jsonObject =  jsonElement.getAsJsonObject();

            Alert alert = new Alert(jsonObject);
            _alerts.add(alert);
        }

    }
    public class  Alert {
        private final Logger log = (Logger)LogManager.getLogger(Alerts.class.getName());
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
        public int getColor() { return _color; }
        public String getFilesize() { return _filesize; }


        public JsonObject getJsonObject() {  return _jsonObject;  }

        public Alert(JsonObject jsonObject) {
            _jsonObject = jsonObject;

            _camera = jsonObject.get("camera").getAsString();
            _path = jsonObject.get("path").getAsString();
            _clip = jsonObject.get("clip").getAsString();
            _offset = jsonObject.get("offset").getAsInt();
            _flags = jsonObject.get("flags").getAsInt();
            _res = jsonObject.get("res").getAsString();
            _zones = jsonObject.get("zones").getAsInt();
            _dateInSeconds = jsonObject.get("date").getAsLong();
            _color = jsonObject.get("color").getAsInt();
            _filesize = jsonObject.get("filesize").getAsString();
        }

        public String toString() {
            return Utils.GetPrettyJsonString(_jsonObject);
        }
    }
}