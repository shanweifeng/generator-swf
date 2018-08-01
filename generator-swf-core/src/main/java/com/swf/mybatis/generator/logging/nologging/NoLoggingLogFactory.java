package com.swf.mybatis.generator.logging.nologging;

import com.swf.mybatis.generator.logging.AbstractLogFactory;
import com.swf.mybatis.generator.logging.Log;

public class NoLoggingLogFactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> targetClass) {
        return new NoLoggingImpl(targetClass);
    }
}
