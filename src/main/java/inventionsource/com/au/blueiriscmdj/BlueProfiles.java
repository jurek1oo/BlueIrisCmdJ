package inventionsource.com.au.blueiriscmdj;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class BlueProfiles {

    private ArrayList<String> _profiles = null;

    public BlueProfiles(ArrayList<String> profiles) throws Exception {
        if (profiles==null) throw new Exception("profiles can not be null");
        _profiles = profiles;
    }

    public int size() {
        return _profiles.size();
    }

    public String getProfile(int profileInt) {
        return _profiles.get(profileInt);
    }

    public int getProfileInt(String profile) {

        return _profiles.indexOf(profile);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Profiless: " + Arrays.toString(_profiles.toArray()) + "." );
        return sb.toString();
    }
}
