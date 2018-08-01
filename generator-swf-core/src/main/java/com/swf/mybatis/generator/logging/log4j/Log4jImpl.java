package com.swf.mybatis.generator.logging.log4j;

import com.swf.mybatis.generator.logging.Log;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4jImpl implements Log {
    private static final String FQCN = Log4jImpl.class.getName();

    private Logger log;

    public Log4jImpl(Class<?> clazz){log = Logger.getLogger(clazz);}

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public void error(String s, Throwable e) {
        log.log(FQCN,Level.ERROR,s,e);
    }

    @Override
    public void error(String s) {
        log.log(FQCN,Level.ERROR,s,null);
    }

    @Override
    public void debug(String s) {
        log.log(FQCN,Level.DEBUG,s,null);
    }

    @Override
    public void warn(String s) {
        log.log(FQCN,Level.WARN,s,null);
    }
}
