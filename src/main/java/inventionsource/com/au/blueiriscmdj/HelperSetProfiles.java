package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/*
 * A helper class used by Raspberry pi 3 Java software.
 */
public class HelperSetProfiles {

    private static final Logger log = (Logger) LogManager.getLogger(HelperSetProfiles.class);

    private LoginParams _loginParams = null;

    public HelperSetProfiles(LoginParams loginParams) {
        _loginParams =  loginParams;
    }

    public void SetActiveProfile(int profileInt) throws Exception
    {
        BlueLogin blueLogin = null;
        try {
            blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            CommandStatus commandStatus = new CommandStatus(blueLogin);
            log.debug("Login to BI OK");
            BlueStatus blueStatus = commandStatus.SetProfile(profileInt);
            if( blueStatus.getActiveProfileInt()!=profileInt ||
                    commandStatus.getProblemMsg() != null){
                throw new Exception("Error. profileInt: " + profileInt + " not set: " +
                        blueStatus.toString() + " : " + commandStatus.getProblemMsg()) ;
            }
            log.info("Profile: " + profileInt + " set OK");
        } catch (Exception e) {
            throw e;
        } finally {
            blueLogin.BlueIrisLogout();
        }
    }

    public int GetActiveProfile() throws Exception
    {
        BlueStatus blueStatus = null;
        BlueLogin blueLogin = null;
        try {
            blueLogin = new BlueLogin();
            blueLogin.BlueIrisLogin(_loginParams);
            CommandStatus commandStatus = new CommandStatus(blueLogin);
            log.debug("Login to BI OK");
            blueStatus = commandStatus.GetStatus();
            if( commandStatus.getProblemMsg() != null) {
                throw new Exception("Error. profile get: " + commandStatus.getProblemMsg());
            }
            return blueStatus.getActiveProfileInt();
        } catch (Exception e) {
            log.error("Error getting profile. ",e);
            throw e;
        } finally {
            blueLogin.BlueIrisLogout();
        }
    }

}
