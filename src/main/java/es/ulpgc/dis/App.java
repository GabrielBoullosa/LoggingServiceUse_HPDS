package es.ulpgc.dis;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class App {
    public static void main(String[] args){
        ConfigurationBuilder<BuiltConfiguration> builder =
                ConfigurationBuilderFactory.newConfigurationBuilder();

        /*Canales de aprendizaje
        * console*/
        AppenderComponentBuilder console = builder
                .newAppender("stdout","Console");
        console.addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
        console.add(builder.newLayout("PatternLayout")
                .addAttribute("pattern","%d [%t] %-5level: %msg%n%throwable"));
        builder.add(console);

        /*file*/
        AppenderComponentBuilder file = builder
                .newAppender("log","File");
        console.addAttribute("fileName", "log/logging.log");
        console.add(builder.newLayout("PatternLayout")
                .addAttribute("pattern","%d [%t] %-5level: %msg%n%throwable"));
        builder.add(file);

        /*loggers base:
        * first*/
        LoggerComponentBuilder firstLogger = builder
                .newLogger("logger_file", Level.WARN);
        firstLogger.add(builder.newAppenderRef("log"));
        firstLogger.addAttribute("additivity", false);
        builder.add(firstLogger);

        /*base*/
        RootLoggerComponentBuilder rootLogger = builder
                .newRootLogger(Level.ERROR);
        rootLogger.add(builder.newAppenderRef("stdout"));
        builder.add(rootLogger);

        /**/
        Configurator.initialize(builder.build());

        Logger logger_0 = LogManager.getLogger("logger_file");
        Logger logger_1 = LogManager.getLogger("stdout");

        logger_0.warn("esto es un warning...");
        logger_0.debug("esto es información de debug...");
        logger_0.trace("esto es una traza...");
        logger_1.error("esto es un error...");
    }
}
