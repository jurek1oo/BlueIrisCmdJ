package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class HelperSetProfiles {

    private static final Logger log = (Logger) LogManager.getLogger(HelperSetProfiles.class);

    private LoginParams _loginParams = null;
    private BlueLogin _blueLogin = null;
    private CommandStatus _commandStatus = null;

    public HelperSetProfiles(LoginParams loginParams) {
        _loginParams =  loginParams;
    }

    public void SetProfile(int profileInt) throws Exception
    {
        Login();
        try {
            BlueStatus blueStatus = _commandStatus.SetProfile(profileInt);
            if( blueStatus.getActiveProfileInt()!=profileInt){
                throw new Exception("Error. profileInt: " + profileInt + " not set: " +
                        blueStatus.toString()) ;
            }
            log.info("Profile: " + profileInt + " set OK");
        } catch (Exception e) {
            throw e;
        } finally {
            _blueLogin.BlueIrisLogout();
        }
    }

    public int GetActiveProfile() throws Exception
    {
        BlueStatus blueStatus = null;
        try {
            Login();
            blueStatus = _commandStatus.GetStatus();
            return blueStatus.getActiveProfileInt();
        } catch (Exception e) {
            log.error("Error getting profile. ",e);
            throw e;
        } finally {
            _blueLogin.BlueIrisLogout();
        }
    }

    public void Login() throws Exception
    {
            _blueLogin = new BlueLogin();
            _blueLogin.BlueIrisLogin(_loginParams);
            _commandStatus = new CommandStatus(_blueLogin);
            log.debug("Login to BI OK");
    }
}
