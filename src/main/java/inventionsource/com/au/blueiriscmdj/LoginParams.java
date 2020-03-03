package inventionsource.com.au.blueiriscmdj;
import java.util.ArrayList;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class LoginParams {

    private ArrayList<String> _argsList = null;
    private String _user = null;
    private String _password = null;
    private String _host = null;

    public LoginParams(String user, String password, String host) throws Exception {
        if (user==null || user.trim().length()==0) {
            throw new Exception("Error. empty user name");
        }
        if (password==null || password.trim().length()==0) {
            throw new Exception("Error. empty password");
        }
        if (host==null || host.trim().length()==0) {
            throw new Exception("Error. empty host");
        }
        _user = user;
        _password = password;
        _host = host;
        _argsList = new ArrayList<>();
        _argsList.add("-u");
        _argsList.add(user);
        _argsList.add("-p");
        _argsList.add(password);
        _argsList.add("-host");
        _argsList.add(host);
    }

    public String getUser() {
        return _user;
    }
    public String getPassword() {
        return _password;
    }
    public String getHost() {
        return _host;
    }

    public void addElement(String element)
    {
        _argsList.add(element);
    }
    public String[] getArgs() {
       return _argsList.toArray(new String[_argsList.size()]);
    }
}
