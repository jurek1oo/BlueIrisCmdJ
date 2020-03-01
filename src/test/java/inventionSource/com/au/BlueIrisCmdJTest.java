package inventionSource.com.au;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class BlueIrisCmdJTest
{
    /**
     * Rigorous Test :-)
     */
    private static final Logger log = LogManager.getLogger(BlueIrisCmdJTest.class.getName());
    private LoginParams _loginParams = null;

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","info");
        _loginParams = new LoginParams(Constants4Tests.USER, Constants4Tests.PASSWORD, Constants4Tests.HOST);
    }
    @Test
    public void Args_Help()
    {
        String[] args = new String[1];
        args[0] = "-h";
        BlueIrisCmdJ.main(args);

        assertTrue( true );
    }
}
