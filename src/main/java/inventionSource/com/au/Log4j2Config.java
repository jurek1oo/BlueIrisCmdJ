package inventionSource.com.au;
import org.apache.log4j.*;
import java.io.IOException;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class Log4j2Config {

    private static Logger log = LogManager.getLogger(Log4j2Config.class);

    Log4j2Config(String logFilePathAndName, String logLevel) {
        if (logFilePathAndName == null || logFilePathAndName.length() < 1)
            logFilePathAndName = "BlueIrisCmdJ.log";
        Level log4jLevel = Level.INFO;
        if (logLevel != null) {
            if (logLevel.toLowerCase().indexOf("er") == 0) log4jLevel = Level.ERROR;
            if (logLevel.toLowerCase().indexOf("in") == 0) log4jLevel = Level.INFO;
            if (logLevel.toLowerCase().indexOf("de") == 0) log4jLevel = Level.DEBUG;
            if (logLevel.toLowerCase().indexOf("tr") == 0) log4jLevel = Level.TRACE;
        }

        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(log4jLevel);
        PatternLayout layout =
                new PatternLayout("%d{yyyy-MM-dd HH:mm:sss} %-5p %c{1}.%M:%L - %m%n");
        try
        {
            RollingFileAppender fileAppender = new RollingFileAppender(layout, logFilePathAndName);
            fileAppender.setMaximumFileSize(10000000);
            fileAppender.setMaxBackupIndex(5);
            rootLogger.addAppender(fileAppender);
            log.debug("log4j configured OK.");
        }
        catch (IOException e)
        {
            System.out.println("Failed to add appender !!\n" + e);
        }
    }
}
