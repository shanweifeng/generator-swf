package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.*;
import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;
import com.swf.mybatis.generator.internal.JDBCConnectionFactory;
import com.swf.mybatis.generator.internal.ObjectFactory;
import com.swf.mybatis.generator.internal.PluginAggregator;
import com.swf.mybatis.generator.internal.db.DatabaseIntrospector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.swf.mybatis.generator.internal.util.StringUtility.*;
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

    private List<IntrospectedTable> introspectedTables;

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

        if(it != null && it.requiresXMLGenerator()) {
            if (sqlMapGeneratorConfiguration == null) {
                errors.add(getString("ValidationError.9", id));
            } else {
                sqlMapGeneratorConfiguration.validate(errors, id);
            }
        }

        if (tableConfigurations.size() == 0) {
            errors.add(getString("ValidationError.3", id));
        } else {
            for (int i = 0; i < tableConfigurations.size(); i++) {
                TableConfiguration tc = tableConfigurations.get(i);
                tc.validate(errors, i);
            }
        }

        for (PluginConfiguration pluginConfiguration : pluginConfigurations) {
            pluginConfiguration.validate(errors, id);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJdbcConnectionConfiguration(JDBCConnectionConfiguration jdbcConnectionConfiguration) {
        this.jdbcConnectionConfiguration = jdbcConnectionConfiguration;
    }

    public void setSqlMapGeneratorConfiguration(SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration) {
        this.sqlMapGeneratorConfiguration = sqlMapGeneratorConfiguration;
    }

    public void setJavaTypeResolverConfiguration(JavaTypeResolverConfiguration javaTypeResolverConfiguration) {
        this.javaTypeResolverConfiguration = javaTypeResolverConfiguration;
    }

    public void setJavaModelGeneratorConfiguration(JavaModelGeneratorConfiguration javaModelGeneratorConfiguration) {
        this.javaModelGeneratorConfiguration = javaModelGeneratorConfiguration;
    }

    public void setJavaClientGeneratorConfiguration(JavaClientGeneratorConfiguration javaClientGeneratorConfiguration) {
        this.javaClientGeneratorConfiguration = javaClientGeneratorConfiguration;
    }

    public ModelType getDefaultModelType() {
        return defaultModelType;
    }

    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("context");
        xmlElement.addAttribute(new Attribute("id", id));

        if (defaultModelType != ModelType.CONDITIONAL) {
            xmlElement.addAttribute(new Attribute("defaultModelType", defaultModelType.getModelType()));;
        }

        if (stringHasValue(introspectedColimnImpl)) {
            xmlElement.addAttribute(new Attribute("introspectedColimnImpl", introspectedColimnImpl));
        }

        if (stringHasValue(targetRuntime)) {
            xmlElement.addAttribute(new Attribute("targetRuntime", targetRuntime));
        }

        addPropertyXmlElements(xmlElement);

        for (PluginConfiguration pluginConfiguration : pluginConfigurations) {
            xmlElement.addElement(pluginConfiguration.toXmlElement());
        }

        if (commentGeneratorConfiguration != null) {
            xmlElement.addElement(commentGeneratorConfiguration.toXmlElement());
        }

        if (jdbcConnectionConfiguration != null) {
            xmlElement.addElement(jdbcConnectionConfiguration.toXmlElement());
        }

        if (connectionFactoryConfiguration != null) {
            xmlElement.addElement(connectionFactoryConfiguration.toXmlElement());
        }

        if (javaTypeResolverConfiguration != null) {
            xmlElement.addElement(javaTypeResolverConfiguration.toXmlElement());
        }

        if (javaModelGeneratorConfiguration != null) {
            xmlElement.addElement(javaModelGeneratorConfiguration.toXmlElement());
        }

        if (sqlMapGeneratorConfiguration != null) {
            xmlElement.addElement(sqlMapGeneratorConfiguration.toXmlElement());
        }

        if (javaClientGeneratorConfiguration != null) {
            xmlElement.addElement(javaClientGeneratorConfiguration.toXmlElement());
        }

        for (TableConfiguration tableConfiguration : tableConfigurations) {
            xmlElement.addElement(tableConfiguration.toXmlElement());
        }
        return xmlElement;
    }

    public ArrayList<TableConfiguration> getTableConfigurations() {
        return tableConfigurations;
    }

    public String getBeginningDelimiter() {
        return beginningDelimiter;
    }

    public String getEndingDelimiter() {
        return endingDelimiter;
    }

    public void addProperty(String name, String value) {
        super.addProperty(name, value);
        if (PropertyRegistry.CONTEXT_BEGINNING_DELIMITER.equals(name)) {
            beginningDelimiter = value;
        } else if (PropertyRegistry.CONTEXT_ENDING_DELIMITER.equals(name)) {
            endingDelimiter = value;
        } else if (PropertyRegistry.CONTEXT_AUTO_DELIMITE_KEYWORDS.equals(name) && stringHasValue(value)) {
            autoDelimitkeywords = isTrue(value);
        } else if (PropertyRegistry.CONTEXT_TARGET_JAVA8.equals(name) && stringHasValue(value)) {
            isJava8Targeted = isTrue(value);
        }
    }

    public CommentGenerator getCommentGenerator() {
        if (commentGenerator == null) {
            commentGenerator = ObjectFactory.createCommentGenerator(this);
        }
        return commentGenerator;
    }

    public JavaFormatter getJavaFormatter() {
        if (javaFormatter == null) {
            javaFormatter = ObjectFactory.createJavaFormatter(this);
        }
        return javaFormatter;
    }

    public XmlFormatter getXmlFormatter() {
        if (xmlFormatter == null) {
            xmlFormatter = ObjectFactory.createXmlFormatter(this);
        }
        return xmlFormatter;
    }

    public void setCommentGeneratorConfiguration(CommentGeneratorConfiguration commentGeneratorConfiguration) {
        this.commentGeneratorConfiguration = commentGeneratorConfiguration;
    }

    public Plugin getPlugins() {return pluginAggregator;}

    public String getTargetRuntime() {
        return targetRuntime;
    }

    public void setTargetRuntime(String targetRuntime) {
        this.targetRuntime = targetRuntime;
    }

    public String getIntrospectedColimnImpl() {
        return introspectedColimnImpl;
    }

    public void setIntrospectedColimnImpl(String introspectedColimnImpl) {
        this.introspectedColimnImpl = introspectedColimnImpl;
    }

    public int getIntrospectionSteps() {
        int steps = 0;
        steps++;
        //connect to database
        // for each table
        // create introspected table implementation
        steps += tableConfigurations.size() *1;
        return steps;
    }

    public void introspectTables(ProgressCallback callback, List<String> warnings, Set<String> fullyQualifiedTableNames)
        throws SQLException, InterruptedException {
        introspectedTables = new ArrayList<>();
        JavaTypeResolver javaTypeResolver = ObjectFactory.createJavaTypeResolver(this, warnings);
        Connection connection = null;

        try {
            callback.startTask(getString("Progress.0"));
            connection = getConnection();

            DatabaseIntrospector databaseIntrospector = new DatabaseIntrospector(this, connection.getMetaData(), javaTypeResolver, warnings);

            for (TableConfiguration tc : tableConfigurations) {
                String tableName = composeFullyQualifiedTableName(tc.getCatalog(), tc.getSchema(), tc.getTableName(), '.');

                if (fullyQualifiedTableNames != null && fullyQualifiedTableNames.size() > 0 && !fullyQualifiedTableNames.contains(tableName)) {
                    continue;
                }

                if (!tc.areAnyStatementsEnabled()) {
                    warnings.add(getString("Warning.0", tableName));
                }

                callback.startTask(getString("Progress.1", tableName));
                List<IntrospectedTable> tables = databaseIntrospector.introspectTables(tc);
                if(tables != null) {
                    introspectedTables.addAll(tables);
                }
                callback.checkCancel();
            }
        } finally {
            closeConnection(connection);
        }
    }

    public int getGenerationSteps() {
        int steps = 0;
        if (introspectedTables != null) {
            for (IntrospectedTable introspectedTable : introspectedTables) {
                steps += introspectedTable.getGenerationSteps();
            }
        }
        return steps;
    }

    public void generateFiles(ProgressCallback callback, List<GeneratedJavaFile> generatedJavaFiles,
                              List<GeneratedXmlFile> generatedXmlFiles, List<String> warnings)
        throws InterruptedException {
        pluginAggregator = new PluginAggregator();
        for (PluginConfiguration pluginConfiguration : pluginConfigurations) {
            Plugin plugin = ObjectFactory.createPlugin(this, pluginConfiguration);
            if (plugin.validate(warnings)) {
                pluginAggregator.addPlugin(plugin);
            } else {
                warnings.add(getString("Warning.24", pluginConfiguration.getConfigurationType(), id));
            }
        }

        if (introspectedTables != null) {
            for (IntrospectedTable introspectedTable : introspectedTables) {
                callback.checkCancel();

                introspectedTable.initialize();
                introspectedTable.calculateGenerators(warnings, callback);
                generatedJavaFiles.addAll(introspectedTable.getGeneratedJavaFiles());
                generatedXmlFiles.addAll(introspectedTable.getGeneratedXmlFiles());
                generatedJavaFiles.addAll(pluginAggregator.contextGenerateAdditionalJavaFiles(introspectedTable));
                generatedXmlFiles.addAll(pluginAggregator.contextGenerateAdditionalXmlFiles(introspectedTable));
            }
        }
        generatedJavaFiles.addAll(pluginAggregator.contextGenerateAdditionalJavaFiles());
        generatedXmlFiles.addAll(pluginAggregator.contextGenerateAdditionalXmlFiles());
    }

    private Connection getConnection() throws SQLException {
        ConnectionFactory connectionFactory;
        if (jdbcConnectionConfiguration != null) {
            connectionFactory = new JDBCConnectionFactory(jdbcConnectionConfiguration);
        } else {
            connectionFactory = ObjectFactory.createConnectionFactory(this);
        }
        return connectionFactory.getConnection();
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            }catch (SQLException e) {
                // ignore
            }
        }
    }

    public boolean autoDelimitKeywords() {
        return autoDelimitkeywords != null && autoDelimitkeywords.booleanValue();
    }

    public ConnectionFactoryConfiguration getConnectionFactoryConfiguration() {
        return connectionFactoryConfiguration;
    }

    public void setConnectionFactoryConfiguration(ConnectionFactoryConfiguration connectionFactoryConfiguration) {
        this.connectionFactoryConfiguration = connectionFactoryConfiguration;
    }

    public boolean isJava8Targeted() {
        return isJava8Targeted;
    }

    public void setJava8Targeted(boolean java8Targeted) {
        isJava8Targeted = java8Targeted;
    }
}
