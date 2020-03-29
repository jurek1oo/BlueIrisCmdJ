package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

public class BlueLogs {

    private static final Logger log = (Logger) LogManager.getLogger(BlueLogs.class.getName());

    private ArrayList<BlueLog> _blueLogs = new ArrayList<BlueLog>();
    public int size() {       return _blueLogs.size();   }

    public BlueLog get(int i) {
        return (BlueLog) _blueLogs.get(i);
    }

    private JsonElement _dataJson = null;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        int size = _blueLogs.size();
        if (size()>0) {
            for (int i = 0; i < size; i++) {
                BlueLog blueLog = get(i);
                if (i == size - 1) {
                    sb.append(blueLog.toString() + "\n");
                } else {
                    sb.append(blueLog.toString() + ",\n");
                }
            }
        }
        sb.append( " ]\n");
        return sb.toString();
    }

    public BlueLogs(JsonElement dataJson, long startDateSeconds) throws Exception {
        _dataJson = dataJson;
        try {
            if(dataJson!=null) // no clips if null
            {
                JsonArray jsonArray = dataJson.getAsJsonArray();
                for (int i = 0, size = jsonArray.size(); i < size; i++)
                {
                    JsonElement jsonElement = jsonArray.get(i);
                    JsonObject jsonObject =  jsonElement.getAsJsonObject();
                    BlueLog blueLog = new BlueLog(jsonObject);
                    if ( blueLog.getDate() > startDateSeconds) {
                        _blueLogs.add(blueLog);
                    }
                 }
             }
        } catch (Exception e) {
            log.error("\nError in dataJson: " +  dataJson , e);
            throw e;
        }
    }

        public class BlueLog {
            private final Logger log = (Logger) LogManager.getLogger(BlueLog.class.getName());
            private JsonObject _jsonObject = null;

            private int _level = -1;
            private String _objname = null;
            private int _date = -1;
            private String _localdatetime = null;
            private String _count = null;
            private String _msg = null;

            public int getDate() {        return _date;    }
            public String getLocalDateTime() {        return _localdatetime;    }
            public int getLevel() {        return _level;    }
            public String getObjName() {        return _objname;    }
            public String getCount() {        return _count;    }
            public String getMsg() {        return _msg;    }

            public BlueLog(JsonObject jsonObject) {
                _jsonObject = jsonObject;
                _date = _jsonObject.get("date").getAsInt();

                _localdatetime = Utils.GetLocalDateTimeStrFromSeconds(_date);
                if (_jsonObject.has("count")){
                    _count = _jsonObject.get("count").getAsString();
                }
                _level = _jsonObject.get("level").getAsInt();
                _objname = _jsonObject.get("obj").getAsString();
                _msg = _jsonObject.get("msg").getAsString();
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("{\n");
                sb.append("\"date\": " + _date + "\n");
                sb.append("\"localDateTime\": " + _localdatetime + "\n");
                if(_count!=null) sb.append("\"count\": \"" + _count + "\"\n");
                sb.append("\"level\": \"" + _level + "\"\n");
                sb.append("\"obj\": \"" + _objname + "\"\n");
                sb.append("\"msg\": \"" + _msg + "\"\n");
                sb.append("}\n");
                return sb.toString();
            }
        }
    }
