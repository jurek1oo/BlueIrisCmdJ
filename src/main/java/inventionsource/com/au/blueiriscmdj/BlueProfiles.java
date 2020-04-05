package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class BlueProfiles {
    private final Logger log = (Logger) LogManager.getLogger(BlueProfiles.class.getName());

    private ArrayList<String> _profiles = null;

    public BlueProfiles(ArrayList<String> profiles) throws Exception {
        if (profiles==null) throw new Exception("profiles can not be null");
        _profiles = profiles;
    }

    public int size() {
        return _profiles.size();
    }

    public String getProfile(int profileInt) {
        if (profileInt<0 || profileInt > 7){
            log.warn("Warn profile out of range 0-7: " +profileInt);
            return "unknown";
        }
        return _profiles.get(profileInt);
    }

    public int getProfileInt(String profile) {

        try {
            return _profiles.indexOf(profile);
        } catch (Exception e) {
            log.warn("Warn profile does not exists: " +profile);
            return -1;
        }

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Profiless: " + Arrays.toString(_profiles.toArray()) + "." );
        return sb.toString();
    }
}
