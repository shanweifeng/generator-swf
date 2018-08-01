package com.swf.mybatis.generator.logging.log4j;

import com.swf.mybatis.generator.logging.AbstractLogFactory;
import com.swf.mybatis.generator.logging.Log;

public class Log4jLoggingLogFactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> targetClass) {
        return new Log4jImpl(targetClass);
    }
}
