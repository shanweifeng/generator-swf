package com.swf.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.TextElement;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

public class DeleteByExampleElementGenerator extends AbstractXmlElementGenerator {

    public DeleteByExampleElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parenElement) {
        XmlElement answer = new XmlElement("delete");

        String fqjt = introspectedTable.getExampleType();
        answer.addAttribute(new Attribute("id", introspectedTable.getDeleteByExampleStatementId()));;
        answer.addAttribute(new Attribute("parameterType", fqjt));
        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("delete from ");
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getExampleIncludeElement());
        if (context.getPlugins().sqlMapDeleteByExampleElementGenerated(answer, introspectedTable)) {
            parenElement.addElement(answer);
        }
    }
}
