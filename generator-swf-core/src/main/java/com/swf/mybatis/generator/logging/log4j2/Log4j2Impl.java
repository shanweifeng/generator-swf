package com.swf.mybatis.generator.logging.log4j2;

import com.swf.mybatis.generator.logging.AbstractLogFactory;
import com.swf.mybatis.generator.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.AbstractLogger;

public class Log4j2Impl implements Log {
    private Log log;

    public Log4j2Impl(Class<?> clazz){
        Logger logger = LogManager.getLogger(clazz);
        if(logger  instanceof AbstractLogFactory){
            log = new Log4j2AbstractLoggerImpl((AbstractLogger) logger);
        }else{
            log = new Log4j2LoggerImpl(logger);
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public void error(String s, Throwable e) {
        log.error(s,e);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void debug(String s) {
        log.debug(s);
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }
}
