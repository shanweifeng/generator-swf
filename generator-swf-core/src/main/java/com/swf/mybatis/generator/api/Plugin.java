package com.swf.mybatis.generator.api;

import com.swf.mybatis.generator.api.dom.java.Field;
import com.swf.mybatis.generator.api.dom.java.Interface;
import com.swf.mybatis.generator.api.dom.java.Method;
import com.swf.mybatis.generator.api.dom.java.TopLevelClass;
import com.swf.mybatis.generator.api.dom.xml.Document;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;
import com.swf.mybatis.generator.config.Context;

import java.util.List;
import java.util.Properties;

public interface Plugin {

    public enum ModelClassType{
        PRIMARY_KEY,
        BASE_RECORD,
        RECORD_WITH_BLOBS
    }

    void setContext(Context context);

    void setProperties(Properties properties);

    void initialized(IntrospectedTable introspectedTable);

    boolean validate(List<String> warning);

    List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles();

    List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable);

    List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles();

    List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable);

    boolean clientGenerated(Interface interfaze,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean clientBasicCountMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable);

    boolean clientBasicDeleteMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientBasicInsertMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientBasicSelectManyMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientBasicSelectOneMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientBasicUpdateMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientCountByExampleMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean clientCountByExampleMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientDeleteByExampleMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean clientDeleteByExampleMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientDeleteByPrimaryKeyMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientDeleteByPrimaryKeyMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean clientInsertMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientInsertMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable) ;

    boolean clientInsertSelectiveMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientInsertSelectiveMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean clientSelectByPrimaryKeyMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientSelectByPrimaryKeyMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientSelectAllMethodGenerated(Method method,Interface interfaze,IntrospectedTable introspectedTable);

    boolean clientSelectAllMethodGenerated(Method method,TopLevelClass toplevelClass,IntrospectedTable introspectedTable);

    boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType);

    boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType);

    boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType);

    boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean modelExampleClassGenerated(TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean sqlMapGenerated(GeneratedXmlFile sqlMap,IntrospectedTable introspectedTable);

    boolean sqlMapDocumentGenerated(Document document,IntrospectedTable introspectedTable);

    boolean sqlMapResultMapWithoutBLOBsElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapCountByExampleElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapDeleteByExampleElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapBaseColumnListElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapBlobColumnListElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapInsertElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapInsertSelectiveElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapResultMapWithBLOBsElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapSelectAllElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element,IntrospectedTable introspectedTable);

    boolean providerGenerated(TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean providerApplyWhereMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedTable introspectedTable);

    boolean providerCountByExampleMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    boolean providerDeleteByExampleMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    boolean providerInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    boolean providerSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    boolean providerSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    boolean providerUpdateByExampleSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    boolean providerUpdateByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    boolean providerUpdateByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    boolean providerUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable);
}
