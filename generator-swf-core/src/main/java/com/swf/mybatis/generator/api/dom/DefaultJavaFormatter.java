package com.swf.mybatis.generator.api.dom;

import com.swf.mybatis.generator.api.JavaFormatter;
import com.swf.mybatis.generator.api.dom.java.CompilationUnit;
import com.swf.mybatis.generator.config.Context;

public class DefaultJavaFormatter implements JavaFormatter {

    protected Context context;

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public String getFormattedContent(CompilationUnit compilationUnit) {
        return compilationUnit.getFormattedContent();
    }
}
