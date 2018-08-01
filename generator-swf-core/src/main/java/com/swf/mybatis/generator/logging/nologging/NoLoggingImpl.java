package com.swf.mybatis.generator.logging.nologging;

import com.swf.mybatis.generator.logging.Log;

public class NoLoggingImpl implements Log {

    public NoLoggingImpl(Class<?> clazz){

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void error(String s, Throwable e) {

    }

    @Override
    public void error(String s) {

    }

    @Override
    public void debug(String s) {

    }

    @Override
    public void warn(String s) {

    }
}
