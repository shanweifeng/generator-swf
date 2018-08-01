package com.swf.mybatis.generator.logging.jdk14;

import com.swf.mybatis.generator.logging.AbstractLogFactory;
import com.swf.mybatis.generator.logging.Log;

public class Jdk14LoggingLogFactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> targetClass) {
        return new Jdk14LoggingImpl(targetClass);
    }
}
