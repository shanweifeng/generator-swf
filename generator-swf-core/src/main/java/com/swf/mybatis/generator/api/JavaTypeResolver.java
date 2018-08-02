package com.swf.mybatis.generator.api;

import com.swf.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import com.swf.mybatis.generator.config.Context;

import java.util.List;
import java.util.Properties;

public interface JavaTypeResolver {

    void addConfigurationProperties(Properties properties);

    void setContext(Context context);

    void setWarnings(List<String> wargings);

    FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn);

    String calculateJdbcTypeName(IntrospectedColumn introspectedColumn);
}
