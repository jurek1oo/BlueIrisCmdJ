package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Test;

public class Log4j2ConfigTest {
    private static final Logger log = (Logger) LogManager.getLogger(Log4j2ConfigTest.class.getName());
    @Test
    public void config()
    {
        try {
            Log4j2Config log4j = new Log4j2Config("test.log", "debug");
            log.info("Hello Log4j2ConfigTest version: " + BlueIrisCmdJ.VERSION);
        } catch(Exception e) {
            log.error("error: " ,e);
            throw e;
        }

    }}
