package com.swf.mybatis.generator.api;

import com.swf.mybatis.generator.api.dom.java.CompilationUnit;
import com.swf.mybatis.generator.config.Context;

public interface JavaFormatter {
    void setContext(Context context);

    String getFormattedContent(CompilationUnit compilationUnit);
}
