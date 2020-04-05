package inventionsource.com.au.blueiriscmdj;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class Log4j2Config {

    private static Logger log = (Logger)LogManager.getLogger(Log4j2Config.class);

    Log4j2Config(String logFilePathAndName, String logLevel) {
        if (logFilePathAndName == null || logFilePathAndName.length() < 1)
            logFilePathAndName = "BlueIrisCmdJ.log";
        Level log4jLevel = Log4j2Config.getLevel(logLevel);
        try
        {
            final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            final Configuration config = ctx.getConfiguration();
            //"%d{yyyy-MM-dd HH:mm:sss} %-5p %c{1}.%M:%L - %m%n"
            // LayPatternLayout.SIMPLE_CONVERSION_PATTERN
            Layout layout = PatternLayout.createLayout("%d{yyyy-MM-dd HH:mm:sss} %-5p %c{1}.%M:%L - %m%n",
                    null , config, null,
                    null,true, true, null, null);

            //file
            Appender appender = FileAppender.createAppender(logFilePathAndName, "false", "false",
                    "File", "true","false", "true",
                    "4000", layout, null, "false", null, config);
            appender.start();
            config.addAppender(appender);
            AppenderRef ref = AppenderRef.createAppenderRef("File", log4jLevel, null);
            AppenderRef[] refs = new AppenderRef[] {ref};
            LoggerConfig loggerConfig = LoggerConfig.createLogger(
                    false, log4jLevel, "inventionsource.com.au.blueiriscmdj",
                    null, refs, null, config, null );
            loggerConfig.addAppender(appender, null, null);
            config.addLogger("inventionsource.com.au.blueiriscmdj", loggerConfig);

            //console
            Appender appenderCon = ConsoleAppender.createAppender(layout,null,
                    ConsoleAppender.Target.SYSTEM_OUT,"console",false,false);
            appenderCon.start();
            config.addAppender(appenderCon);
            AppenderRef refCon = AppenderRef.createAppenderRef("console", log4jLevel, null);
            AppenderRef[] refsCon = new AppenderRef[] {refCon};
            LoggerConfig loggerConfigCon = LoggerConfig.createLogger(
                    false, log4jLevel, "inventionsource.com.au.blueiriscmdj",
                    null, refsCon, null, config, null );
            loggerConfig.addAppender(appenderCon, null, null);
            config.addLogger("inventionsource.com.au.blueiriscmdj", loggerConfigCon);
            ctx.updateLoggers();

            Configurator.setAllLevels(LogManager.getRootLogger().getName(), log4jLevel);
        }
        catch (Exception e)
        {
            System.out.println("Failed to add appender !!\n" + e);
        }
    }

    public static Level getLevel(String logLevel) {
        Level log4jLevel = Level.INFO;
        if (logLevel != null) {
            if (logLevel.toLowerCase().indexOf("er") == 0) log4jLevel = Level.ERROR;
            if (logLevel.toLowerCase().indexOf("in") == 0) log4jLevel = Level.INFO;
            if (logLevel.toLowerCase().indexOf("de") == 0) log4jLevel = Level.DEBUG;
            if (logLevel.toLowerCase().indexOf("tr") == 0) log4jLevel = Level.TRACE;
        }
        return log4jLevel;
    }
}
