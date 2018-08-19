package com.swf.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.TextElement;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

public class CountByExampleElementGenerator extends AbstractXmlElementGenerator {
    public CountByExampleElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        String fqjt = introspectedTable.getExampleType();
        answer.addAttribute(new Attribute("id", introspectedTable.getCountByExampleStatementId()));;
        answer.addAttribute(new Attribute("parameterType", fqjt));
        answer.addAttribute(new Attribute("resultType", "java.lang.Long"));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from ");
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));;
        answer.addElement(getExampleIncludeElement());

        if (context.getPlugins().sqlMapCountByExampleElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
