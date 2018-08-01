package com.swf.mybatis.generator.logging.log4j2;

import com.swf.mybatis.generator.logging.AbstractLogFactory;
import com.swf.mybatis.generator.logging.Log;

public class Log4j2LoggingLogFactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> targetClass) {
        return new Log4j2Impl(targetClass);
    }
}
