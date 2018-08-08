package com.swf.mybatis.generator.logging;

import com.swf.mybatis.generator.logging.commons.JakartaCommonsLoggingLogFactory;
import com.swf.mybatis.generator.logging.jdk14.Jdk14LoggingLogFactory;
import com.swf.mybatis.generator.logging.log4j.Log4jLoggingLogFactory;
import com.swf.mybatis.generator.logging.log4j2.Log4j2LoggingLogFactory;
import com.swf.mybatis.generator.logging.nologging.NoLoggingLogFactory;
import com.swf.mybatis.generator.logging.slf4j.Slf4jLoggingLogFactory;

import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class LogFactory {

    private static AbstractLogFactory logFactory;
    public static String MARKER = "MYBATIS-GENERATOR";

    static {
        tryImplementation(new Slf4jLoggingLogFactory());
        tryImplementation(new JakartaCommonsLoggingLogFactory());
        tryImplementation(new Log4j2LoggingLogFactory());
        tryImplementation(new Log4jLoggingLogFactory());
        tryImplementation(new Jdk14LoggingLogFactory());
        tryImplementation(new NoLoggingLogFactory());
    }

    public static Log getLog(Class<?> clazz) {
        try {
            return logFactory.getLog(clazz);
        } catch (Throwable t) {
            throw new RuntimeException(getString("RntimeError.21", clazz.getName(), t.getMessage()), t);
        }
    }

    public static synchronized void forceJavaLogging() {
        setImplementation(new Jdk14LoggingLogFactory());
    }

    public static synchronized void forceSlf4Logging() {
        setImplementation(new Slf4jLoggingLogFactory());
    }

    public static synchronized void forceCommonsLogging() {
        setImplementation(new JakartaCommonsLoggingLogFactory());
    }

    public static synchronized void forceJLog4jLogging() {
        setImplementation(new Log4jLoggingLogFactory());
    }

    public static synchronized void forceJLog4j2Logging() {
        setImplementation(new Log4j2LoggingLogFactory());
    }

    public static synchronized void forceNoLogging() {
        setImplementation(new NoLoggingLogFactory());
    }

    public static void setLogFactory(AbstractLogFactory logFactory) {
        LogFactory.logFactory = logFactory;
    }

    private static void tryImplementation(AbstractLogFactory factory) {
        if (logFactory == null) {
            try {
                setImplementation(factory);
            } catch (LogException e) {
                // ignore
            }
        }
    }

    private static void setImplementation(AbstractLogFactory factory) {
        try {
            Log log = factory.getLog(LogFactory.class);
            if(log.isDebugEnabled()) {
                log.debug("Logging initialized using '" +  factory + "' adapter.");
            }
            logFactory = factory;
        } catch (Throwable t) {
            throw new LogException("Error setting Log implementation. Cause: " + t.getMessage(), t);
        }
    }
}
