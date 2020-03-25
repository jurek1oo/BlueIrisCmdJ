package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonElement;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class BlueStatus {
    private JsonElement _dataElement = null;

    private int _activeProfileInt = -1;
    private String _activeSchedule = null;
    private String _signal = null;

    private int _signalInt = -1;
    private int _cxns = -1;
    private int _cpu = -1;
    private int _lock = -1;
    private int _warnings = -1;
    private int _alerts = -1;
    private int _tzone = -1;
    private String _mem = null;
    private String _memfree = null;
    private String _memload = null;
    private String _uptime = null;
    private String _clips = null;

    public int getActiveProfileInt() {        return _activeProfileInt;    }
    public int get_cxns() {        return _cxns;    }
    public int get_cpu() {        return _cpu;    }
    public int get_lock() {        return _lock;    }
    public int get_warnings() {        return _warnings;    }
    public int get_alerts() {        return _alerts;    }
    public int get_tzone() {        return _tzone;    }
    public String get_mem() {        return _mem;    }
    public String get_memfree() {        return _memfree;    }
    public String get_memload() {        return _memload;    }
    public String get_uptime() {        return _uptime;    }
    public String get_clips() {        return _clips;    }
    public String getActiveSchedule() {        return _activeSchedule;    }
    public String getSignal() {        return _signal;    }
    public int getSignalInt() {  return _signalInt; }


    public BlueStatus(JsonElement dataElement) throws Exception {
        if (dataElement == null) throw new Exception("null dataElement");
        this._dataElement = dataElement;
        _activeSchedule = dataElement.getAsJsonObject().get("schedule").getAsString();

        _signalInt = dataElement.getAsJsonObject().get("signal").getAsInt();
        _signal = (new BlueSignals()).getSignal(_signalInt);
        _activeProfileInt = dataElement.getAsJsonObject().get("profile").getAsInt();

        _cxns = dataElement.getAsJsonObject().get("cxns").getAsInt();
        _cpu = dataElement.getAsJsonObject().get("cpu").getAsInt();
        _lock = dataElement.getAsJsonObject().get("lock").getAsInt();
        _warnings = dataElement.getAsJsonObject().get("warnings").getAsInt();
        _alerts = dataElement.getAsJsonObject().get("alerts").getAsInt();
        _tzone = dataElement.getAsJsonObject().get("tzone").getAsInt();
        _signalInt = dataElement.getAsJsonObject().get("signal").getAsInt();

        _mem = dataElement.getAsJsonObject().get("mem").getAsString();
        _memfree = dataElement.getAsJsonObject().get("memfree").getAsString();
        _memload = dataElement.getAsJsonObject().get("memload").getAsString();
        _uptime = dataElement.getAsJsonObject().get("uptime").getAsString();
        _clips = dataElement.getAsJsonObject().get("clips").getAsString();
    }

    public String toString() {
        return Utils.GetPrettyJsonString(this._dataElement);
    }
}
