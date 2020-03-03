package inventionsource.com.au.blueiriscmdj;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;
import java.util.Arrays;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */

public class BlueLogin {
    private static final Logger log = (Logger)LogManager.getLogger(BlueLogin.class);

    private RequestHttp _requestHttp = new RequestHttp();

    private String _url = null;
    private String _session = null;

    public String getMd5HexResponse() {
        return _md5HexResponse;
    }

    private String _md5HexResponse = null;
    private String _systemName = null;
    private ArrayList<String> _profiles = null;
    private ArrayList<String> _schedules = null;

    public String getUrl() {  return _url;  }
    public String getSession() {  return _session;  }
    public String getSystemName() {
        return _systemName;
    }
    public ArrayList<String> getProfiles() {
        return _profiles;
    }
    public ArrayList<String> getSchedules() { return _schedules; }

    public void BlueIrisLogout() throws Exception {
        log.debug("BlueIrisLogout: " );

        String cmd = "logout";
        boolean hasToBeSuccess =  true;
        try {
            if (_session==null) {
                throw new Exception("Error. session is null - login first.");
            }
            String result = _requestHttp.PostRequest(
                    _url, Utils.MakeCmdJson(cmd,_session,_md5HexResponse));
            JsonObject jsonObject = JsonEater.GetResultElement(result, hasToBeSuccess);

            _session=null;
            _md5HexResponse=null;
             log.info("BlueIrisLogout - OK" );
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n", e);
            throw e;
        }
    }

    public String BlueIrisLogin(LoginParams loginParams) throws Exception {
        log.debug("host: " + loginParams.getHost() + " user: " + loginParams.getUser()+
                " password: ***" + loginParams.getPassword().length() + "***");

        if(loginParams==null || loginParams.getHost() ==  null || loginParams.getHost().length()==0)
            throw new Exception("Error. loginParams is null or getHost() is empty.");

        _url = Utils.MakeUrl(loginParams.getHost());

        String cmd = "login";
        boolean hasToBeSuccess =  false;
        String result = null;
        String status = null;
        String jsonData = "{\"cmd\":\"" + cmd + "\"}";
        try {
            result = _requestHttp.PostRequest(_url, jsonData);

            JsonObject jsonObject = JsonEater.GetResultElement(result, hasToBeSuccess);
            _session = jsonObject.get("session").getAsString();

            log.debug("1st login call: OK");

            _md5HexResponse = Utils.Md5HexResponse(loginParams.getUser(), _session, loginParams.getPassword());

            jsonData = Utils.MakeCmdJson( cmd,  _session,  _md5HexResponse);
            log.debug("2nd call url: " + _url + " jsondata: " + jsonData );

            result = _requestHttp.PostRequest(_url, jsonData);

            hasToBeSuccess = true;
            final JsonElement dataElement = JsonEater.GetDataElement(result, hasToBeSuccess);

            _systemName = dataElement.getAsJsonObject().get("system name").getAsString();
            status = "BlueIris json API login OK, systemName: " + _systemName + " user: " +
                    loginParams.getUser()  + " host: " + loginParams.getHost();
            log.info(status);

            JsonElement profilesElement = dataElement.getAsJsonObject().get("profiles");
            _profiles = (new Gson()).fromJson(profilesElement, new TypeToken<ArrayList<String>>() {}.getType());
            log.debug("profiles list: " + _profiles.size() + " : " + Arrays.toString(_profiles.toArray()));

            JsonElement schedulesElement = dataElement.getAsJsonObject().get("schedules");
            _schedules = (new Gson()).fromJson(schedulesElement, new TypeToken<ArrayList<String>>() {}.getType());
            log.debug("schedules list: " + _schedules.size() + " : " + Arrays.toString(_schedules.toArray()));

            return status;
        } catch (Exception e) {
            log.error("Error loging to BlueIris user: " + loginParams.getUser() +
                    " host: " + loginParams.getHost() +"\n", e);
            throw e;
        }
    }
}
