package com.swf.mybatis.generator.api.dom;

import com.swf.mybatis.generator.api.XmlFormatter;
import com.swf.mybatis.generator.api.dom.xml.Document;
import com.swf.mybatis.generator.config.Context;

public class DefaultXmlFormatter implements XmlFormatter {

    protected Context context;

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public String getFormattedContent(Document document) {
        return document.getFormattedContent();
    }
}
