package inventionsource.com.au.blueiriscmdj;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandLogsList {

    private static final Logger log = (Logger)LogManager.getLogger(CommandLogsList.class);

    private BlueLogin _blueLogin = null;
    public BlueLogin getBlueLogin() { return _blueLogin; }

    public CommandLogsList(BlueLogin blueLogin) throws Exception {
        _blueLogin = blueLogin;
    }

    public BlueLogs GetLogs(String json) throws Exception {
        String startDateUsedStr = "1970-01-02 00:00:00";
        long startDateSeconds = 0;
        String cmd = "log";
        boolean hasToBeSuccess = true;
        boolean getDataElement = true;
        try {
            if (json != null && json.trim().length() > 2) {
                if (!Utils.isJSONValid(json)) throw new Exception("Error. invalid json");
                JsonElement jsonElement = (new Gson()).fromJson(json, JsonElement.class);
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                try {
                    startDateUsedStr = Utils.CorrectDateString(jsonObject.get("startdate").getAsString());
                    startDateSeconds = Utils.GetSecondsFromDateSql(startDateUsedStr);
                } catch (Exception e) {
                    log.warn("Warn startdate not present or invalid, used: " + startDateUsedStr);
                }
            } else {
                log.warn("Warn. Empty json, used startdate: " + startDateUsedStr);
            }
            log.info("Used startDate: " + startDateUsedStr);

            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);

            JsonElement jsonDataElement = commandCoreRequest.RunTheCmd(cmd, null, hasToBeSuccess, getDataElement);

            BlueLogs blueLogs = new BlueLogs(jsonDataElement, startDateSeconds);
            log.debug("blueLogs: " + blueLogs.toString());
            return blueLogs;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n" + "\n\n", e);
            throw e;
        }
    }
}