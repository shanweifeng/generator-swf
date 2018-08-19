package com.swf.mybatis.generator.codegen;

import com.swf.mybatis.generator.api.dom.java.*;
import com.swf.mybatis.generator.config.PropertyRegistry;

import java.util.List;
import java.util.Properties;

import static com.swf.mybatis.generator.internal.util.JavaBeansUtil.getGetterMethodName;

public abstract class AbstractJavaGenerator extends AbstractGenerator {

    public abstract List<CompilationUnit> getCompilationUnits();

    public static Method getGetter(Field field) {
        Method method = new Method();
        method.setName(getGetterMethodName(field.getName(), field.getType()));
        method.setReturnType(field.getType());
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder sb = new StringBuilder();
        sb.append("return ");
        sb.append(field.getName());
        sb.append(';');
        method.addBodyLine(sb.toString());
        return method;
    }

    public String getRootClass() {
        String rootClass = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_CLASS);
        if (rootClass == null) {
            Properties properties = context.getJavaModelGeneratorConfiguration().getProperties();
            rootClass = properties.getProperty(PropertyRegistry.ANY_ROOT_CLASS);
        }

        return rootClass;
    }

    protected void addDefaultConstructor(TopLevelClass topLevelClass) {
        topLevelClass.addMethod(getDefaultConstructor(topLevelClass));
    }

    protected Method getDefaultConstructor(TopLevelClass topLevelClass) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName(topLevelClass.getType().getShortName());
        method.addBodyLine("super();");
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }
}
