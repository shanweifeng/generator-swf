package com.swf.mybatis.generator.logging.slf4j;

import com.swf.mybatis.generator.logging.AbstractLogFactory;
import com.swf.mybatis.generator.logging.Log;

public class Slf4jLoggingLogFactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> targetClass) {
        return new Slf4jImpl(targetClass);
    }
}
