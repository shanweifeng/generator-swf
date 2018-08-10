package com.swf.mybatis.generator.internal.rules;

import com.swf.mybatis.generator.api.IntrospectedTable;
import com.swf.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import com.swf.mybatis.generator.codegen.mybatis3.ListUtilities;
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
        if(generateRecordWithBLOBsClass()){
            answer = introspectedTable.getRecordWithBLOBsType();
        }else if(generateBaseRecordClass()){
            answer = introspectedTable.getBaseRecordType();
        }else{
            answer = introspectedTable.getPrimaryKeyType();
        }

        return new FullyQualifiedJavaType(answer);
    }

    @Override
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

    @Override
    public boolean generateUpdateByPrimaryKeyWithBLOBs() {
        if (isModelOnly) {
            return false;
        }

        if(ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns()).isEmpty()) {
            return false;
        }

        boolean rc = tableConfiguration.isUpdateByPrimaryKeyStatementEnabled()
                && introspectedTable.hasPrimaryKeyColumns() && introspectedTable.hasBLOBColumns();
        return rc;
    }

    @Override
    public boolean generateUpdateByPrimaryKeySelective() {
        if (isModelOnly) {
            return false;
        }

        if (ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns()).isEmpty()) {
            return false;
        }

        boolean rc = tableConfiguration.isUpdateByPrimaryKeyStatementEnabled() && introspectedTable.hasPrimaryKeyColumns()
                && (introspectedTable.hasBLOBColumns() || introspectedTable.hasBaseColumns());
        return rc;
    }

    @Override
    public boolean generateDeleteByPrimaryKey() {
        if (isModelOnly) {
            return false;
        }

        return tableConfiguration.isDeleteByPrimaryKeyStatementEnabled() && introspectedTable.hasPrimaryKeyColumns();
    }

    @Override
    public boolean generateDeleteByExample() {
        if (isModelOnly) {
            return false;
        }

        return tableConfiguration.isDeleteByExampleStatementEnabled();
    }

    @Override
    public boolean generateBaseResultMap() {
        if (isModelOnly) {
            return false;
        }

        return tableConfiguration.isSelectByExamplStatementEnabled() || tableConfiguration.isSelectByPrimaryKeyStatementEnabled();
    }

    @Override
    public boolean generateResultMapWithBLOBs() {
        boolean rc;
        if (introspectedTable.hasBLOBColumns()) {
            if (isModelOnly) {
                rc = true;
            } else {
                rc = tableConfiguration.isSelectByExamplStatementEnabled() || tableConfiguration.isSelectByPrimaryKeyStatementEnabled();
            }
        } else {
            rc = false;
        }
        return rc;
    }

    @Override
    public boolean generateSQLExampleWhereClause() {
        if (isModelOnly) {
            return false;
        }

        return tableConfiguration.isSelectByExamplStatementEnabled() || tableConfiguration.isDeleteByExampleStatementEnabled()
                || tableConfiguration.isCountByExampleStatementEnabled();
    }

    @Override
    public boolean generateMyBatis3UpdateByExampleWhereClause() {
        if (isModelOnly) {
            return false;
        }
        return introspectedTable.getTargetRuntime() == IntrospectedTable.TargetRuntime.MYBATIS3
                && tableConfiguration.isUpdateByExampleStatementEnabled();
    }

    @Override
    public boolean generateSelectByPrimaryKey() {
        if (isModelOnly) {
            return false;
        }

        return tableConfiguration.isSelectByPrimaryKeyStatementEnabled() && introspectedTable.hasPrimaryKeyColumns()
                && (introspectedTable.hasBaseColumns() || introspectedTable.hasBLOBColumns());
    }

    @Override
    public boolean generateSelectByExampleWithoutBLOBs() {
        if (isModelOnly) {
            return false;
        }
        return tableConfiguration.isSelectByExamplStatementEnabled();
    }

    @Override
    public boolean generateSelectByExampleWithBLOBs() {
        if (isModelOnly) {
            return false;
        }

        return tableConfiguration.isSelectByExamplStatementEnabled() && introspectedTable.hasBLOBColumns();
    }

    @Override
    public boolean generateExampleClass() {
        if (introspectedTable.getContext().getSqlMapGeneratorConfiguration() == null
                && introspectedTable.getContext().getJavaClientGeneratorConfiguration() == null) {
            return false;
        }

        if (isModelOnly) {
            return false;
        }

        return tableConfiguration.isSelectByExamplStatementEnabled() || tableConfiguration.isDeleteByExampleStatementEnabled()
                || tableConfiguration.isCountByExampleStatementEnabled() || tableConfiguration.isUpdateByExampleStatementEnabled();
    }

    @Override
    public boolean generateCountByExample() {
        if (isModelOnly) {
            return false;
        }

        return tableConfiguration.isCountByExampleStatementEnabled();
    }

    @Override
    public boolean generateUpdateByExampleSelective() {
        if (isModelOnly) {
            return false;
        }

        return tableConfiguration.isUpdateByExampleStatementEnabled();
    }

    @Override
    public boolean generateUpdateByExampleWithoutBLOBs() {
        if (isModelOnly) {
            return false;
        }

        return tableConfiguration.isUpdateByExampleStatementEnabled()
                && (introspectedTable.hasPrimaryKeyColumns() || introspectedTable.hasBaseColumns());
    }

    @Override
    public boolean generateUpdateByExampleWithBLOBs() {
        if (isModelOnly) {
            return false;
        }

        return tableConfiguration.isUpdateByExampleStatementEnabled() && introspectedTable.hasBLOBColumns();
    }

    @Override
    public IntrospectedTable getIntrospectedTable() {
        return introspectedTable;
    }

    @Override
    public boolean generateBaseColumnList() {
        if (isModelOnly) {
            return false;
        }

        return generateSelectByPrimaryKey() || generateSelectByExampleWithoutBLOBs();
    }

    @Override
    public boolean generateBlobColumnList() {
        if (isModelOnly) {
            return false;
        }

        return introspectedTable.hasBLOBColumns()
                && (tableConfiguration.isSelectByExamplStatementEnabled() || tableConfiguration.isSelectByPrimaryKeyStatementEnabled());
    }

    @Override
    public boolean generateJavaClient() {
        return !isModelOnly;
    }
}
