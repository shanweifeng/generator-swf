package com.swf.mybatis.generator.internal;

import com.swf.mybatis.generator.api.*;
import com.swf.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import com.swf.mybatis.generator.config.*;
import com.swf.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import sun.text.resources.cldr.yav.FormatData_yav;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class ObjectFactory {

    private static List<ClassLoader> externalClassLoaders;

    static {
        externalClassLoaders = new ArrayList<>();
    }

    private ObjectFactory(){super();}

    public static void reset(){externalClassLoaders.clear();}

    public static synchronized void addExternalClassLoader(ClassLoader classLoader){
        ObjectFactory.externalClassLoaders.add(classLoader);
    }

    public static Class<?> externalClassForName(String type) throws ClassNotFoundException{
        Class<?> clazz;
        for(ClassLoader classLoader : externalClassLoaders){
            try{
                clazz = Class.forName(type,true,classLoader);
                return clazz;
            }catch (Throwable e){
                // ignore - fail safe below
            }
        }
        return internalClassForName(type);
    }

    public static Object createExternaObject(String type){
        Object answer;
        try{
            Class<?> clazz = externalClassForName(type);
            answer = clazz.newInstance();
        }catch (Exception e){
            throw new RuntimeException(getString("RuntimeError.6",type),e);
        }
        return answer;
    }

    public static Class<?> internalClassForName(String type)throws ClassNotFoundException{
        Class<?> clazz = null;
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            clazz = Class.forName(type,false,cl);
        }catch (Exception e){
            // ignore - fail safe  below
        }
        if(clazz == null){
            clazz = Class.forName(type,true,ObjectFactory.class.getClassLoader());
        }

        return clazz;
    }

    public static URL getResource(String resource) {
        URL url;
        for (ClassLoader classLoader : externalClassLoaders) {
            url = classLoader.getResource(resource);
            if (url != null) {
                return url;
            }
        }

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        url = cl.getResource(resource);
        if (url == null) {
            url = ObjectFactory.class.getClassLoader().getResource(resource);
        }
        return url;
    }

    public static Object createInternalObject(String type) {
        Object answer;
        try {
            Class<?> clazz = internalClassForName(type);
            answer = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(getString("RuntimeError.6",type),e);
        }
        return answer;
    }

    public static JavaTypeResolver createJavaTypeResolver(Context context, List<String> warning) {
        JavaTypeResolverConfiguration config = context.getJavaTypeResolverConfiguration();
        String type;
        if (config != null && config.getConfigurationType() != null) {
            type = config.getConfigurationType();
            if ("DEFAULT".equalsIgnoreCase(type)) {
                type = JavaTypeResolverDefaultImpl.class.getName();
            }
        } else {
            type = JavaTypeResolverDefaultImpl.class.getName();
        }
        JavaTypeResolver answer =(JavaTypeResolver)createInternalObject(type);
        answer.setWarnings(warning);
        if (config != null) {
            answer.addConfigurationProperties(config.getProperties());
        }
        answer.setContext(context);
        return answer;
    }

    public static Plugin createPlugin(Context context, PluginConfiguration pluginConfiguration){
        Plugin plugin = (Plugin) createInternalObject(pluginConfiguration.getConfigurationType());
        plugin.setContext(context);
        plugin.setProperties(pluginConfiguration.getProperties());
        return plugin;
    }

    public static CommentGenerator createCommentGenerator(Context context) {
        CommentGeneratorConfiguration config = context.getCommentGeneratorConfiguration();
        CommentGenerator answer;

        String type;
        if (config == null || config.getConfigurationType() == null) {
            type = DefaultCommentGenerator.class.getName();
        } else {
            type = config.getConfigurationType();
        }

        answer = (CommentGenerator) createInternalObject(type);

        if (config != null) {
            answer.addConfigurationProperties(config.getProperties());
        }
        return answer;
    }

    public static ConnectionFactory createConnectionFactory(Context context) {
        ConnectionFactoryConfiguration config = context.getConnectionFactoryConfiguration();
        ConnectionFactory answer;

        String type;
        if (config == null || config.getConfigurationType() == null) {
            type = JDBCConnectionFactory.class.getName();
        } else {
            type = config.getConfigurationType();
        }

        answer = (ConnectionFactory) createInternalObject(type);

        if (config != null){
            answer.addConfigurationProperties(config.getProperties());
        }

        return answer;
    }

    public static JavaFormatter createJavaFormatter(Context context) {
        String type = context.getProperty(PropertyRegistry.CONTEXT_JAVA_FORMATTER);
        if (!stringHasValue(type)) {
            type = DefaultJavaFormatter.class.getName();
        }

        JavaFormatter answer = (JavaFormatter) createInternalObject(type);
        answer.setContext(context);
        return answer;
    }

    public static XmlFormatter createXmlFormatter(Context context) {
        String type = context.getProperty(PropertyRegistry.CONTEXT_XML_FORMATTER);
        if (!stringHasValue(type)) {
            type = DefaultXmlFormatter.class.getName();
        }

        XmlFormatter answer = (XmlFormatter) createInternalObject(type);
        answer.setContext(context);
        return answer;
    }

    public static IntrospectedTable createIntrospectedTable(TableConfiguration tableConfiguration, FullyQualifiedTable table
    ,Context context) {
        IntrospectedTable answer = createIntrospectedTableForValidation(context);
        answer.setFullyQualifiedTable(table);
        answer.setTableConfiguration(tableConfiguration);
    }

    public static IntrospectedTable createIntrospectedTableForValidation(Context context) {
        String type = context.getTargetRuntime();
        if(!stringHasValue(type)){
            type = IntrospectedTableMyBatis3Impl.class.getName();
        } else if ("MyBatis3".equalsIgnoreCase(type)) {
            type = IntrospectedTableMyBatis3Impl.class.getName();
        } else if ("MyBatis3Simple".equalsIgnoreCase(type)) {
            type = IntrospectedTableMyBatis3SimpleImpl.class.getName();
        } else if ("MyBatis3DynamicSql".equalsIgnoreCase(type)) {
            type = IntrospectedTableMyBatis3DynamicSqlimpl.class.getName();
        }

        IntrospectedTable answer = (IntrospectedTable) createInternalObject(type);
        answer.setContext(context);
        return answer;
    }

    public static IntrospectedColumn createIntrospectedColumn(Context context) {
        String type = context.getIntrospectedColumnImpl();
        if(!stringHasValue(type)) {
            type = IntrospectedColumn.class.getName();
        }

        IntrospectedColumn answer = (IntrospectedColumn) createInternalObject(type);
        answer.setContext(context);
        return answer;
    }
}
