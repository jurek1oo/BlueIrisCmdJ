package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
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
    private static final Logger log = (Logger) LogManager.getLogger(BlueIrisCmdJTest.class.getName());
    private LoginParams _loginParams = null;

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","info");
        new Constants4Tests();
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
