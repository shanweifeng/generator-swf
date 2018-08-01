package com.swf.mybatis.generator.logging.commons;

import com.swf.mybatis.generator.logging.AbstractLogFactory;
import com.swf.mybatis.generator.logging.Log;

public class JakartaCommonsLoggingLogFactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> targetClass) {
        return new JakartaCommonsLoggingImpl(targetClass);
    }
}
