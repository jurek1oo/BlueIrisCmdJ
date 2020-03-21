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
    public int getSignalInt() {  return _signalInt; }

    private int _signalInt = -1;

    public BlueStatus(JsonElement dataElement) throws Exception {
        if (dataElement == null) throw new Exception("null dataElement");
        this._dataElement = dataElement;
        _activeSchedule = dataElement.getAsJsonObject().get("schedule").getAsString();

        _signalInt = dataElement.getAsJsonObject().get("signal").getAsInt();
        _signal = (new BlueSignals()).getSignal(_signalInt);

        _activeProfileInt = dataElement.getAsJsonObject().get("profile").getAsInt();
    }
    public int getActiveProfileInt() {
        return _activeProfileInt;
    }
    public String getActiveSchedule() {
        return _activeSchedule;
    }
    public String getSignal() {
        return _signal;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n\"signal\": \"" + _signal + "\",\n" +
                "\"profile\": \"" + _activeProfileInt + "\",\n" +
                "\"schedule\": \"" + _activeSchedule + "\",\n}");
        return sb.toString();
    }
}
