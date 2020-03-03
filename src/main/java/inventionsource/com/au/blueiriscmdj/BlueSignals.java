package inventionsource.com.au.blueiriscmdj;

import java.util.ArrayList;
import java.util.Arrays;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class BlueSignals {
    private ArrayList<String> _signalsList = new ArrayList<String>();

    public BlueSignals() {
        _signalsList.add("red");//0
        _signalsList.add("green");//1
        _signalsList.add("yellow");//2
    }

    public String getSignal(int signalInt) {
        return _signalsList.get(signalInt);
    }

    public int getSignalInt(String signal) {
        return _signalsList.indexOf(signal);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Signals: " + Arrays.toString(_signalsList.toArray()) + "." );
        return sb.toString();
    }
}
