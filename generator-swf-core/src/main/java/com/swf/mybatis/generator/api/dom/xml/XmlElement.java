package com.swf.mybatis.generator.api.dom.xml;

import com.swf.mybatis.generator.api.dom.OutputUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class XmlElement extends Element {

    private List<Attribute> attributes;

    private List<Element> elements;

    private String name;

    public XmlElement(String name){
        super();
        attributes = new ArrayList<>();
        elements = new ArrayList<>();
        this.name = name;
    }

    public XmlElement(XmlElement original){
        super();
        attributes = new ArrayList<>();
        attributes.addAll(original.attributes);
        elements = new ArrayList<>();
        elements.addAll(original.elements);
        this.name = original.name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void addAttribute(Attribute attribute){
        attributes.add(attribute);
    }

    public List<Element> getElements() {
        return elements;
    }

    public void addElement(Element element){
        elements.add(element);
    }

    public void addElement(int index,Element element){
        elements.add(index,element);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFormattedContent(int indentLevel) {
        StringBuilder sb = new StringBuilder();
        OutputUtilities.xmlIndent(sb,indentLevel);
        sb.append('<');
        sb.append(name);
        Collections.sort(attributes);

        for (Attribute att : attributes){
            sb.append(' ');
            sb.append(att.getFormattedContent());
        }

        if(elements.size() > 0){
            sb.append(">");
            for (Element element : elements){
                OutputUtilities.newLine(sb);
                sb.append(element.getFormattedContent(indentLevel));
            }
            OutputUtilities.newLine(sb);
            OutputUtilities.xmlIndent(sb,indentLevel);
            sb.append("</");
            sb.append(name);
            sb.append('>');
        }else{
            sb.append(" />");
        }
        return sb.toString();
    }
}
