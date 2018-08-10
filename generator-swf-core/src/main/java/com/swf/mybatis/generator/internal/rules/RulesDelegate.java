package com.swf.mybatis.generator.internal.rules;

import com.swf.mybatis.generator.api.IntrospectedTable;
import com.swf.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

public class RulesDelegate implements Rules {
    protected Rules rules;

    public RulesDelegate(Rules rules) {
        this.rules = rules;
    }

    @Override
    public FullyQualifiedJavaType calculateAllFieldsClass() {
        return rules.calculateAllFieldsClass();
    }

    @Override
    public boolean generateBaseRecordClass() {
        return rules.generateBaseRecordClass();
    }

    @Override
    public boolean generateBaseResultMap() {
        return rules.generateBaseResultMap();
    }


    @Override
    public boolean generateCountByExample() {
        return rules.generateCountByExample();
    }

    @Override
    public boolean generateDeleteByExample() {
        return rules.generateDeleteByExample();
    }

    @Override
    public boolean generateDeleteByPrimaryKey() {
        return rules.generateDeleteByPrimaryKey();
    }

    @Override
    public boolean generateExampleClass() {
        return rules.generateExampleClass();
    }

    @Override
    public boolean generateInsert() {
        return rules.generateInsert();
    }

    @Override
    public boolean generateInsertSelective() {
        return rules.generateInsertSelective();
    }

    @Override
    public boolean generatePrimaryKeyClass() {
        return rules.generatePrimaryKeyClass();
    }

    @Override
    public boolean generateRecordWithBLOBsClass() {
        return rules.generateRecordWithBLOBsClass();
    }

    @Override
    public boolean generateResultMapWithBLOBs() {
        return rules.generateResultMapWithBLOBs();
    }

    @Override
    public boolean generateSelectByExampleWithBLOBs() {
        return rules.generateSelectByExampleWithBLOBs();
    }

    @Override
    public boolean generateSelectByExampleWithoutBLOBs() {
        return rules.generateSelectByExampleWithoutBLOBs();
    }

    @Override
    public boolean generateSelectByPrimaryKey() {
        return rules.generateSelectByPrimaryKey();
    }

    @Override
    public boolean generateSQLExampleWhereClause() {
        return rules.generateSQLExampleWhereClause();
    }

    @Override
    public boolean generateMyBatis3UpdateByExampleWhereClause() {
        return rules.generateMyBatis3UpdateByExampleWhereClause();
    }

    @Override
    public boolean generateUpdateByExampleSelective() {
        return rules.generateUpdateByExampleSelective();
    }

    @Override
    public boolean  generateUpdateByExampleWithBLOBs() {
        return rules.generateUpdateByExampleWithBLOBs();
    }

    @Override
    public boolean generateUpdateByExampleWithoutBLOBs() {
        return rules.generateUpdateByExampleWithoutBLOBs();
    }

    @Override
    public boolean generateUpdateByPrimaryKeySelective() {
        return rules.generateUpdateByPrimaryKeySelective();
    }

    @Override
    public boolean generateUpdateByPrimaryKeyWithBLOBs(){
        return rules.generateUpdateByPrimaryKeyWithBLOBs();
    }

    @Override
    public boolean generateUpdateByPrimaryKeyWithoutBLOBs() {
        return rules.generateUpdateByPrimaryKeyWithoutBLOBs();
    }

    @Override
    public IntrospectedTable getIntrospectedTable() {
        return rules.getIntrospectedTable();
    }

    @Override
    public boolean generateBaseColumnList() {
        return rules.generateBaseColumnList();
    }

    @Override
    public boolean generateBlobColumnList() {
        return rules.generateBlobColumnList();
    }

    @Override
    public boolean generateJavaClient() {
        return rules.generateJavaClient();
    }
}
