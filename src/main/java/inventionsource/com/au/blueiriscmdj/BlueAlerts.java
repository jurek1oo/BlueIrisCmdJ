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
        return "[]\n";
    }

    public BlueAlerts(JsonElement dataJson) throws Exception {
        _dataJson = dataJson;
        if(dataJson!=null) // no clips if null
        {
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
    }

    public static String JsonHelpGet(){
        //'{"camera":"Ceiling1","startdate":"2020-03-27 23:05:00"}'
        StringBuilder sb = new StringBuilder();
        sb.append("alarms-list json e.g.:" +
                "\n'{\"camera\":\"Ceiling1\",\"startdate\":\"2020-03-27 23:05:00\"}'\n");
        sb.append("camera: camera short name\n");
        sb.append("startdate: List alerts from the date in sql format.\n");
        sb.append(" Check wiki for more info: " +
                "https://github.com/jurek1oo/blueiriscmdj/wiki/alerts-list.\n");
        return sb.toString();
    }

    public class  BlueAlert {
        private final Logger log = (Logger)LogManager.getLogger(BlueAlert.class.getName());
        private JsonObject _jsonObject = null;
        private String _camera = null;
        private String _path = null;
        private String _clip = null;
        private int _offset = -1;
        private int _flags = -1;
        private String _res = null;
        private int _zones = -1;
        private long _dateInSeconds = -1;
        private String _localDate = null;
        private int _color = -1;
        private String _filesize = null;

        public String getCamera() { return _camera; }
        public String getPath() {  return _path; }
        public String getClip() { return _clip; }
        public int getOffset() { return _offset; }
        public int getFlags() { return _flags; }
        public String getRes() { return _res;}
        public int getZones() { return _zones; };

        public long getDateInSeconds() { return _dateInSeconds; }
        public String getLocalDateTime() { return _localDate;   }
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
            _localDate = Utils.GetLocalDateTimeStrFromSeconds(_dateInSeconds);
            _color = jsonObject.get("color").getAsInt();
            _filesize = jsonObject.get("filesize").getAsString();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{\n");
            sb.append("\"camera\": \"" + _camera + "\",\n");
            sb.append("\"path\": \"" + _path + "\",\n");
            sb.append("\"clip\": \"" + _clip + "\",\n");
            sb.append("\"offset\": " + _offset+ ",\n");
            sb.append("\"flags\": \"" + _flags+ ",\n");
            sb.append("\"res\": \"" + _res + "\",\n");
            sb.append("\"zones\": \"" + _zones+ ",\n");
            sb.append("\"date\": \"" + _dateInSeconds+ ",\n");
            sb.append("\"localDateTime\": \"" + _localDate + "\",\n");
            sb.append("\"color\": \"" + _color+ ",\n");
            sb.append("\"filesize\": \"" + _filesize + "\",\n");
            sb.append("}\n");
            return sb.toString();
        }

    }
}
