package com.swf.mybatis.generator.internal.rules;

import com.swf.mybatis.generator.api.IntrospectedTable;

public class FlatModelRules extends BaseRules{
    public FlatModelRules(IntrospectedTable introspectedTable) {
        super(introspectedTable);
    }

    @Override
    public boolean generatePrimaryKeyClass() {
        return false;
    }

    @Override
    public boolean generateBaseRecordClass() {
        return true;
    }

    @Override
    public boolean generateRecordWithBLOBsClass() {
        return false;
    }
}
