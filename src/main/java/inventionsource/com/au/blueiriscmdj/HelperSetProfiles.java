package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class HelperSetProfiles {

    private static final Logger log = (Logger) LogManager.getLogger(HelperSetProfiles.class);

    private LoginParams _loginParams = null;

    public HelperSetProfiles(LoginParams loginParams) {
        _loginParams =  loginParams;
    }

    public void SetProfile(String profile) throws Exception
    {
        BlueCmdRequest blueCmdRequest =  Login();
        if(blueCmdRequest.getSession()==null ){
            throw new Exception("blueCmdRequest.getSession()==null - cant login");
        }
        try {
            BlueStatus blueStatus = blueCmdRequest.SetProfile(profile);
            if( blueStatus.getActiveProfile().compareTo(profile)!=0){
                throw new Exception("Error. profile: " + profile + " not set: " +
                        blueStatus.toJsonString()) ;
            }
            log.info("Profile: " + profile + " set OK");
        } catch (Exception e) {
            throw e;
        } finally {
            blueCmdRequest.getBlueLogin().BlueIrisLogout();
        }
    }

    public String GetActiveProfile() throws Exception
    {
        BlueCmdRequest blueCmdRequest = null;
        BlueStatus blueStatus = null;
        try {
            blueCmdRequest = Login();
            blueStatus = blueCmdRequest.GetStatus();
            return blueStatus.getActiveProfile();
        } catch (Exception e) {
            log.error("Error getting profile. ",e);
            throw e;
        } finally {
            blueCmdRequest.getBlueLogin().BlueIrisLogout();
        }
    }

    public BlueCmdRequest Login() throws Exception
    {
        BlueLogin blueLogin = new BlueLogin();
        blueLogin.BlueIrisLogin(_loginParams);
        BlueCmdRequest blueCmdRequest = new BlueCmdRequest(blueLogin);
        if(blueCmdRequest.getSession()==null ){
            throw new Exception("blueCmdRequest.getSession()==null - cant login");
        }
        log.debug("Login to BI OK");
        return blueCmdRequest;
    }
}
