package com.swf.mybatis.generator.api.dom.xml;

public abstract class Element {

    public Element(){
        super();
    }

    public abstract String getFormattedContent(int indentLevel);
}
