package com.swf.mybatis.generator.logging.slf4j;

import com.swf.mybatis.generator.logging.Log;
import org.slf4j.Logger;

public class Slf4jLoggerImpl implements Log {

    private Logger log;

    public Slf4jLoggerImpl(Logger logger){
        this.log = logger;
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
