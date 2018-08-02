package com.swf.mybatis.generator.api;

import com.swf.mybatis.generator.api.dom.xml.Document;
import com.swf.mybatis.generator.config.Context;

public interface XmlFormatter {

    void setContext(Context context);

    String getFormattedContent(Document document);
}
