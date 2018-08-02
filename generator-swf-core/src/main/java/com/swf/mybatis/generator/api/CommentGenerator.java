package com.swf.mybatis.generator.api;

import com.swf.mybatis.generator.api.dom.java.Field;

import java.util.Properties;

public interface CommentGenerator {

    void addConfigurationProperties(Properties properties);

    void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn);

    void
}
