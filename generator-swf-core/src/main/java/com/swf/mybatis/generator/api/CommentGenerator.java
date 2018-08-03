package com.swf.mybatis.generator.api;

import com.google.common.collect.ImmutableMap;
import com.swf.mybatis.generator.api.dom.java.*;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

import java.util.Properties;
import java.util.Set;

public interface CommentGenerator {

    void addConfigurationProperties(Properties properties);

    void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn);

    void addFieldComment(Field field, IntrospectedTable introspectedTable);

    void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable);

    void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markDoNotDelete);

    void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable);

    void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn);

    void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn);

    void GeneralMethodComment(Method method, IntrospectedTable introspectedTable);

    void addJavaFileComment(CompilationUnit compilationUnit);

    void addComment(XmlElement xmlElement);

    void addRootComment(XmlElement rootElement);

    void GeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports);

    void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn
    , Set<FullyQualifiedJavaType> imports);

    void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports);

    void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports);

    void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports);
}
