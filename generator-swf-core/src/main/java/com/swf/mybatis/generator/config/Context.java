package com.swf.mybatis.generator.config;

import org.apache.maven.model.PluginConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Context extends PropertyHolder {

    private String id;

    private JDBCConnectionConfiguration jdbcConnectionConfiguration;

    private ConnectionFactoryConfiguration connectionFactoryConfiguration;

    private SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration;

    private JavaTypeResolverConfiguration javaTypeResolverConfiguration;

    private JavaModelGeneratorConfiguration  javaModelGeneratorConfiguration;

    private JavaClientGeneratorConfiguration javaClientGeneratorConfiguration;

    private ArrayList<TableConfiguration> tableConfigurations;

    private ModelType modelType;

    private String beginningDelimiter = "\"";

    private String endingDelimiter = "\"";

    private CommentGeneratorConfiguration commentGeneratorConfiguration;

    private CommentGenerator commentGenerator;

    private PluginAggregator pluginAggregator;

    private List<PluginConfiguration> pluginConfigurations;

    private String targetRuntime;

    private String introspectedColimnImpl;

    private Boolean autoDelimitkeywords;

    private JavaFormatter javaFormatter;

    private XmlFormatter xmlFormatter;

    private boolean isJava8Targeted = true;
}
