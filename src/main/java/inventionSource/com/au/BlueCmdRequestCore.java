package inventionSource.com.au;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class BlueCmdRequestCore {
    private static final Logger log = LogManager.getLogger(BlueCmdRequest.class);

    private RequestHttp _requestHttp = new RequestHttp();
    private BlueLogin _blueLogin = null;


    public BlueCmdRequestCore(BlueLogin blueLogin) throws Exception {
        _blueLogin = blueLogin;
        if (blueLogin ==null || blueLogin.getSession() == null || blueLogin.getSession().length()<1){
            throw new Exception("Error. Login first.");
        }
    }

    public JsonElement RunTheCmd(String cmd, String cmdParams ) throws Exception {
        // cmdParams = ",\"signal\":" + signalInt
        // cmdParams = ',"signal":red' -- add to jsondata before }
        log.debug("RunTheCmd: " + cmd + " cmdParams: " + cmdParams);
        boolean resultHasToBeSuccess = false ;
        boolean getDataElemet =  true;
        return RunTheCmd( cmd,  cmdParams,  resultHasToBeSuccess,  getDataElemet);
    }

    public JsonElement RunTheCmd(String cmd, String cmdParams, boolean resultHasToBeSuccess, boolean getDataElemet) throws Exception {
        // cmdParams = ",\"signal\":" + signalInt
        // cmdParams = ',"signal":red' -- add to jsondata before }

        log.debug("get-status: " );

        String result = null;
        String jsonData = null;
        JsonElement dataElement = null;
        JsonObject jsonObject = null;
        try {
            if (_blueLogin.getSession()==null) {
                throw new Exception("Error. session is null - login first.");
            }
            jsonData = Utils.MakeCmdJson(cmd, _blueLogin.getSession(), _blueLogin.getMd5HexResponse());
            if (cmdParams!= null && cmdParams.trim().length()>0) {
                jsonData = jsonData.replace("}",cmdParams + "}");
            }
            log.debug("Cmd url: " + _blueLogin.getUrl() + " jsondata: " + jsonData );

            result = _requestHttp.PostRequest(_blueLogin.getUrl(), jsonData);

            if(getDataElemet) {
                dataElement = (new JsonEater()).GetDataElement(result,resultHasToBeSuccess);
            } else {
                jsonObject = (new JsonEater()).GetResultElement(result,resultHasToBeSuccess);
            }
            log.debug("got data element OK." );
            return dataElement;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " cmdParams: " + cmdParams +"\n", e);
            throw e;
        }
    }
}
