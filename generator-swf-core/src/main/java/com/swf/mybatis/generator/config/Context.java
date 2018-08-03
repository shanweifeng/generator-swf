package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.CommentGenerator;
import com.swf.mybatis.generator.api.IntrospectedTable;
import com.swf.mybatis.generator.api.JavaFormatter;
import com.swf.mybatis.generator.api.XmlFormatter;
import com.swf.mybatis.generator.internal.ObjectFactory;
import com.swf.mybatis.generator.internal.PluginAggregator;
import org.apache.maven.model.PluginConfiguration;

import java.util.ArrayList;
import java.util.List;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class Context extends PropertyHolder {

    private String id;

    private JDBCConnectionConfiguration jdbcConnectionConfiguration;

    private ConnectionFactoryConfiguration connectionFactoryConfiguration;

    private SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration;

    private JavaTypeResolverConfiguration javaTypeResolverConfiguration;

    private JavaModelGeneratorConfiguration  javaModelGeneratorConfiguration;

    private JavaClientGeneratorConfiguration javaClientGeneratorConfiguration;

    private ArrayList<TableConfiguration> tableConfigurations;

    private ModelType defaultModelType;

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

    public Context(ModelType defaultModelType) {
        super();
        if(defaultModelType == null) {
            this.defaultModelType = ModelType.CONDITIONAL;
        } else {
            this.defaultModelType = defaultModelType;
        }

        tableConfigurations = new ArrayList<>();
        pluginConfigurations = new ArrayList<>();
    }

    public void addTableConfiguration(TableConfiguration tc) {
        tableConfigurations.add(tc);
    }

    public JDBCConnectionConfiguration getJdbcConnectionConfiguration() {
        return jdbcConnectionConfiguration;
    }

    public SqlMapGeneratorConfiguration getSqlMapGeneratorConfiguration() {
        return sqlMapGeneratorConfiguration;
    }

    public JavaTypeResolverConfiguration getJavaTypeResolverConfiguration() {
        return javaTypeResolverConfiguration;
    }

    public JavaModelGeneratorConfiguration getJavaModelGeneratorConfiguration() {
        return javaModelGeneratorConfiguration;
    }

    public JavaClientGeneratorConfiguration getJavaClientGeneratorConfiguration() {
        return javaClientGeneratorConfiguration;
    }

    public void addPluginConfiguration(PluginConfiguration pluginConfiguration) {
        pluginConfigurations.add(pluginConfiguration);
    }

    public void validate(List<String> errors){
        if(!stringHasValue(id)){
            errors.add(getString("ValidationError.16"));
        }

        if(jdbcConnectionConfiguration == null && connectionFactoryConfiguration == null) {
            // must specify on
            errors.add(getString("ValidationError.10", id));
        } else if (jdbcConnectionConfiguration != null && connectionFactoryConfiguration != null) {
            // must not specify both
            errors.add(getString("ValidationError.10", id));
        } else if (jdbcConnectionConfiguration != null) {
            jdbcConnectionConfiguration.validate(errors);
        } else {
            connectionFactoryConfiguration.validate(errors);
        }

        if(javaModelGeneratorConfiguration == null) {
            errors.add(getString("ValidationError.8", id));
        } else {
            javaModelGeneratorConfiguration.validate(errors, id);
        }

        if(javaClientGeneratorConfiguration != null) {
            javaClientGeneratorConfiguration.validate(errors, id);
        }

        IntrospectedTable it = null;
        try {
            it = ObjectFactory.createIntrospectedTableForValidation(this);
        } catch (Exception e) {
            errors.add(getString("ValidationError.25", id));
        }

        if(it != null && it.re)
    }
}
