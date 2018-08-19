package com.swf.mybatis.generator.codegen;

import com.swf.mybatis.generator.api.IntrospectedTable;
import com.swf.mybatis.generator.api.ProgressCallback;
import com.swf.mybatis.generator.config.Context;

import java.util.List;

public class AbstractGenerator {

    protected Context context;

    protected IntrospectedTable introspectedTable;

    protected List<String> warnings;

    protected ProgressCallback progressCallback;

    public AbstractGenerator() {
        super();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public IntrospectedTable getIntrospectedTable() {
        return introspectedTable;
    }

    public void setIntrospectedTable(IntrospectedTable introspectedTable) {
        this.introspectedTable = introspectedTable;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public ProgressCallback getProgressCallback() {
        return progressCallback;
    }

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }
}
