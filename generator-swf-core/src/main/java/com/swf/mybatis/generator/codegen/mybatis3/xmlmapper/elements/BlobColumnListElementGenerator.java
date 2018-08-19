package com.swf.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import com.swf.mybatis.generator.api.IntrospectedColumn;
import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.TextElement;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;
import com.swf.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import java.util.Iterator;

public class BlobColumnListElementGenerator extends AbstractXmlElementGenerator {

    public BlobColumnListElementGenerator() {
        super();
    }


    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", introspectedTable.getBlobColumnListId()));
        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        Iterator<IntrospectedColumn> iter = introspectedTable.getBLOBColumns().iterator();
        while (iter.hasNext()) {
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(iter.next()));

            if (iter.hasNext()) {
                sb.append(", ");
            }

            if (sb.length() > 80) {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }

        if (sb.length() > 0) {
            answer.addElement(new TextElement(sb.toString()));
        }

        if (context.getPlugins().sqlMapBlobColumnListElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
