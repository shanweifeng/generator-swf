package com.swf.mybatis.generator.internal.rules;

import com.swf.mybatis.generator.api.IntrospectedTable;

public class HierarchicalModelRules extends BaseRules {

    public HierarchicalModelRules(IntrospectedTable introspectedTable) {
        super(introspectedTable);
    }

    @Override
    public boolean generatePrimaryKeyClass() {
        return introspectedTable.hasPrimaryKeyColumns();
    }

    @Override
    public boolean generateBaseRecordClass() {
        return introspectedTable.hasBaseColumns();
    }

    @Override
    public boolean generateRecordWithBLOBsClass() {
        return introspectedTable.hasBLOBColumns();
    }
}
