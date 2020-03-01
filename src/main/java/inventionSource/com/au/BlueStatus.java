package inventionSource.com.au;

import com.google.gson.JsonElement;

import java.util.ArrayList;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class BlueStatus {
    private ArrayList<String> _profiles = null;
    private JsonElement _dataElement = null;
    private String _activeProfile = null;
    private int _activeProfileInt = -1;
    private String _activeSchedule = null;
    private String _signal = null;

    public BlueStatus(JsonElement dataElement) throws Exception {
        if (dataElement == null) throw new Exception("null dataElement");
        this._dataElement = dataElement;
        _activeSchedule = dataElement.getAsJsonObject().get("schedule").getAsString();

        int signalInt = dataElement.getAsJsonObject().get("signal").getAsInt();
        _signal = null;
        if (signalInt == 0) this._signal = "red";
        if (signalInt == 1) this._signal = "green";
        if (signalInt == 2) this._signal = "yellow";

        _activeProfileInt = dataElement.getAsJsonObject().get("profile").getAsInt();
    }

    public BlueStatus(JsonElement dataElement, ArrayList<String> profiles) throws Exception {
        this(dataElement);
        if (profiles!=null && profiles.size()>0) {
            _profiles = profiles;
            _activeProfile = _profiles.get(_activeProfileInt);
        }
    }

    public void setProfiles(ArrayList<String> profiles) {
        this._profiles = profiles;
        if (profiles!=null && profiles.size()>0) {
            _activeProfile = _profiles.get(_activeProfileInt);
        }
    }
    public String getActiveProfile() {
        return _activeProfile;
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

    public String toJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n\"signal\": \"" + _signal + "\",\n" +
                "\"profile\": \"" + _activeProfile + "\",\n" +
                "\"schedule\": \"" + _activeSchedule + "\",\n}");
        return sb.toString();
    }
}
