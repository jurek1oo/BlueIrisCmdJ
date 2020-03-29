package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

public class BlueClips {

    private static final Logger log = (Logger) LogManager.getLogger(BlueClips.class.getName());

    private ArrayList<BlueClip> _blueClips = new ArrayList<BlueClip>();
    public int size() {       return _blueClips.size();   }

    public BlueClip get(int i) {
        return (BlueClip)_blueClips.get(i);
    }

    private JsonElement _dataJson = null;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        int size = _blueClips.size();
        if (size()>0) {
            for (int i = 0; i < size; i++) {
                BlueClip blueClip = get(i);
                if (i == size - 1) {
                    sb.append(blueClip.toString() + "\n");
                } else {
                    sb.append(blueClip.toString() + ",\n");
                }
            }
        }
        sb.append( " ]\n");
        return sb.toString();
    }

    public BlueClips(JsonElement dataJson, long startdate, long enddate) throws Exception {
        _dataJson = dataJson;
        try {
            if(dataJson!=null) // no clips if null
            {
                JsonArray jsonArray = dataJson.getAsJsonArray();
                for (int i = 0, size = jsonArray.size(); i < size; i++)
                {
                    JsonElement jsonElement = jsonArray.get(i);
                    JsonObject jsonObject =  jsonElement.getAsJsonObject();
                    BlueClip blueClip = new BlueClip(jsonObject);
                    // limit clip number, as BI does not.
                    if (blueClip._date >= startdate && blueClip._date <= enddate) {
                        _blueClips.add(blueClip);
                    }
                 }
             }
        } catch (Exception e) {
            log.error("\nError in dataJson: " +  dataJson +
                    " : " + setJsonHelp(), e);
            throw e;
        }
    }

    public static String setJsonHelp(){
        StringBuilder sb = new StringBuilder();
        sb.append("  camera:    a camera's short name. 'index' will give clips from all cameras");
        sb.append("  startdate: List only clips after startdate.");
        sb.append("  enddate:   List only clips before enddate.");
        sb.append("  tiles:     true/false. If true - list only 1 clip per day");

        return sb.toString();
    }

        public class  BlueClip {
            private final Logger log = (Logger) LogManager.getLogger(BlueClip.class.getName());
            private JsonObject _jsonObject = null;
            private String _camera = null;
            private String _path = null;
            private int _date = -1;
            private String _localdatetime = null;
            private int _color = -1;
            private int _offset = -1;
            private int _flags = -1;
            private int _msec = -1;
            private String _res = null;
            private String _filesize = null;
            private String _filetype = null;

            public int get_offset() {        return _offset;    }
            public int get_flags() {        return _flags;    }
            public int get_msec() {        return _msec;    }
            public String get_res() {        return _res;    }
            public String get_filesize() {        return _filesize;    }
            public String get_filetype() {        return _filetype;    }
            public String getCameraName() {        return _camera;    }
            public String getPath() {        return _path;    }
            public int getDate() {        return _date;    }
            public String getLocalDateTime() {        return _localdatetime;    }
            public int getColor() {        return _color;    }

            public BlueClip(JsonObject jsonObject) {
                _jsonObject = jsonObject;

                _offset = _jsonObject.get("offset").getAsInt();
                _flags = _jsonObject.get("flags").getAsInt();
                _msec = _jsonObject.get("msec").getAsInt();
                _camera = _jsonObject.get("camera").getAsString();
                _filesize = _jsonObject.get("filesize").getAsString();
                _res = _jsonObject.get("res").getAsString();
                _filetype = _jsonObject.get("filetype").getAsString();
                _path = _jsonObject.get("path").getAsString();
                _date = _jsonObject.get("date").getAsInt();
                _localdatetime = Utils.GetLocalDateTimeStrFromSeconds(_date);
                _color = _jsonObject.get("color").getAsInt();
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("{\n");
                sb.append("\"camera\": \"" + _camera + "\"\n");
                sb.append("\"path\": \"" + _path + "\"\n");
                sb.append("\"offset\": " + _offset + "\n");
                sb.append("\"date\": " + _date + "\n");
                sb.append("\"localdatetime\": " + _localdatetime + "\n");
                sb.append("\"color\": " + _color + "\n");
                sb.append("\"flags\": " + _flags + "\n");
                sb.append("\"res\": \"" + _res + "\"\n");
                sb.append("\"msec\": " + _msec + "\n");
                sb.append("\"filesize\": \"" + _filesize + "\"\n");
                sb.append("\"filetype\": \"" + _filetype + "\"\n");
                sb.append("}\n");
                return sb.toString();
            }
        }
    }
