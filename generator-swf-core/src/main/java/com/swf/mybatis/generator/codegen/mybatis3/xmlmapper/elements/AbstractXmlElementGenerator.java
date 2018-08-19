package com.swf.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import com.swf.mybatis.generator.api.IntrospectedColumn;
import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.TextElement;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;
import com.swf.mybatis.generator.codegen.AbstractGenerator;
import com.swf.mybatis.generator.config.GeneratedKey;

public abstract class AbstractXmlElementGenerator extends AbstractGenerator {

    public abstract void addElements(XmlElement parentElement);

    public AbstractXmlElementGenerator() {
        super();
    }

    protected XmlElement getSelectedKey(IntrospectedColumn introspectedColumn, GeneratedKey generatedKey) {
        String identityColumnType = introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName();

        XmlElement answer = new XmlElement("selectKey");
        answer.addAttribute(new Attribute("resultType", identityColumnType));
        answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty()));;
        answer.addAttribute(new Attribute("order", generatedKey.getMyBatis3Order()));
        answer.addElement(new TextElement(generatedKey.getRuntimeSqlStatement()));

        return answer;
    }

    protected XmlElement getBaseColumnListElement() {
        XmlElement answer = new XmlElement("include");
        answer.addAttribute(new Attribute("refid", introspectedTable.getBaseColumnListId()));
        return answer;
    }

    protected XmlElement getBlobColumnListElement() {
        XmlElement answer = new XmlElement("include");
        answer.addAttribute(new Attribute("refid", introspectedTable.getBlobColumnListId()));
        return answer;
    }

    protected XmlElement getExampleIncludeElement() {
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", "_parameter != null"));

        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid", introspectedTable.getExampleWhereClauseId()));
        ifElement.addElement(includeElement);
        return ifElement;
    }

    protected XmlElement getUpdateByExampleIncludeElement() {
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", "_parameter != null"));

        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid", introspectedTable.getMyBatis3UpdateByExampleWhereClauseId()));
        ifElement.addElement(includeElement);
        return ifElement;
    }
}
