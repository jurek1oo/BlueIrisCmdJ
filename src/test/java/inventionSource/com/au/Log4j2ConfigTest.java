package inventionSource.com.au;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

public class Log4j2ConfigTest {
    private static final Logger log = LogManager.getLogger(Log4j2ConfigTest.class.getName());
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
