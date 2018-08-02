package com.swf.mybatis.generator.internal.rules;

import com.swf.mybatis.generator.api.IntrospectedTable;
import com.swf.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import com.swf.mybatis.generator.config.PropertyRegistry;
import com.swf.mybatis.generator.config.TableConfiguration;
import com.swf.mybatis.generator.internal.util.StringUtility;

public abstract class BaseRules implements Rules {

    protected TableConfiguration tableConfiguration;

    protected IntrospectedTable introspectedTable;

    protected final boolean isModelOnly;

    public BaseRules(IntrospectedTable introspectedTable){
        super();
        this.introspectedTable = introspectedTable;
        this.tableConfiguration = introspectedTable.getTableConfiguration();
        String modelOnly = tableConfiguration.getProperty(PropertyRegistry.TABLE_MODEL_ONLY);
        isModelOnly = StringUtility.isTrue(modelOnly);
    }

    @Override
    public boolean generateInsert(){
        if(isModelOnly){
            return false;
        }
        return tableConfiguration.isInsertStatementEnabled();
    }

    @Override
    public boolean generateInsertSelective(){
        if(isModelOnly){
            return false;
        }
        return tableConfiguration.isInsertStatementEnabled();
    }

    @Override
    public FullyQualifiedJavaType calculateAllFieldsClass(){
        String answer;
        if(generateRecodWithBLOBsClass()){
            answer = introspectedTable.getRecordWithBLOBsType();
        }else if(generateBaseRecordClass()){
            answer = introspectedTable.getBaseRecordType();
        }else{
            answer = introspectedTable.getPrimaryKeyType();
        }

        return new FullyQualifiedJavaType(answer);
    }

    public boolean generateUpdateByPrimaryKeyWithoutBLOBs(){
        if(isModelOnly){
            return false;
        }

        if(ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getBaseColumns()).isEmpty()){
            return false;
        }

        boolean rc = tableConfiguration.isUpdateByPrimaryKeyStatementEnabled()
                && introspectedTable.hasPrimaryKeyColumns()
                && introspectedTable.hasBaseColumns();
        return rc;
    }
}
