package com.swf.mybatis.generator.internal.rules;

import com.swf.mybatis.generator.api.IntrospectedTable;

public class ConditionalModelRules extends BaseRules {

    public ConditionalModelRules(IntrospectedTable introspectedTable) {
        super(introspectedTable);
    }

    @Override
    public boolean generatePrimaryKeyClass() {
        return introspectedTable.getPrimaryKeyColumn().size() > 1;
    }

    @Override
    public boolean generateBaseRecordClass() {
        return introspectedTable.hasBaseColumns() || introspectedTable.getPrimaryKeyColumn().size() == 1
                || blobsAreInBaseRecord();
    }

    private boolean blobsAreInBaseRecord() {
        return introspectedTable.hasBLOBColumns() && !generateRecordWithBLOBsClass();
    }

    @Override
    public boolean generateRecordWithBLOBsClass() {
        int otherColumnCount = introspectedTable.getPrimaryKeyColumn().size() + introspectedTable.getBaseColumns().size();

        return otherColumnCount > 1 && introspectedTable.getBLOBColumns().size() > 1;
    }
}
