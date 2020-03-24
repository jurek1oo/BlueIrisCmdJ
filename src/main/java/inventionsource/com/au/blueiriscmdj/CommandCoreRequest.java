package inventionsource.com.au.blueiriscmdj;
import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandCoreRequest {

    private static final Logger log = (Logger)LogManager.getLogger(CommandCoreRequest.class);

    private RequestHttp _requestHttp = new RequestHttp();
    private BlueLogin _blueLogin = null;


    public CommandCoreRequest(BlueLogin blueLogin) throws Exception {
        _blueLogin = blueLogin;
        if (blueLogin ==null || blueLogin.getSession() == null || blueLogin.getSession().length()<1){
            throw new Exception("Error. Login first.");
        }
    }

    public JsonElement RunTheCmd(String cmd, String cmdParams, boolean resultHasToBeSuccess, boolean getDataElemet) throws Exception {
        // cmdParams = ",\"signal\":" + signalInt
        // cmdParams = ',"signal": 1' --> add to jsondata before }
        log.debug("cmd: " + cmd + " cmdParams: " + cmdParams + " resultHasToBeSuccess: " + resultHasToBeSuccess +
                " getDataElemet: " + getDataElemet);

        String result = null;
        String jsonData = null;
        JsonElement dataElement = null;
        try {
            if (_blueLogin.getSession()==null) {
                throw new Exception("Error. session is null - login first.");
            }
            jsonData = Utils.MakeCmdJson(cmd, _blueLogin.getSession(), _blueLogin.getMd5HexResponse());
            if (cmdParams!= null && cmdParams.trim().length()>0) {
                jsonData = jsonData.replace("}",cmdParams + "}");// ", \"enable\": 1}"
            }
            log.debug("Cmd url: " + _blueLogin.getUrl() + " jsondata: " + jsonData );
            result = _requestHttp.PostRequest(_blueLogin.getUrl(), jsonData);

            if(getDataElemet) {
                dataElement = (new JsonEater()).GetDataElement(result,resultHasToBeSuccess);
            } else {
               (new JsonEater()).GetResultElement(result,resultHasToBeSuccess);
            }
            log.debug("got dataElement: " + dataElement );
            return dataElement;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " cmdParams: " + cmdParams +"\n", e);
            throw e;
        }
    }
}
