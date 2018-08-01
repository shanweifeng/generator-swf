package com.swf.mybatis.generator.logging;

public interface AbstractLogFactory {
    Log getLog(Class<?> targetClass);
}
