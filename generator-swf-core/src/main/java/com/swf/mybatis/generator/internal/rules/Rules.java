package com.swf.mybatis.generator.internal.rules;

import com.swf.mybatis.generator.api.IntrospectedTable;
import com.swf.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

public interface Rules {

    boolean generateInsert();

    boolean generateInsertSelective();

    FullyQualifiedJavaType calculateAllFieldsClass();

    boolean generateUpdateByPrimaryKeyWithoutBLOBs();

    boolean generateUpdateByPrimaryKeyWithBLOBs();

    boolean generateUpdateByPrimaryKeySelective();

    boolean generateDeleteByPrimaryKey();

    boolean generateDeleteByEzample();

    boolean generateBaseResultMap();

    boolean generateResultMapWithBLOBs();

    boolean generateSQLExampleWhereClause();

    boolean generateMyBatis3UpdateByExampleWhereClause();

    boolean generateBaseCloumnList();

    boolean generateBlobColumnList();

    boolean generateSelectByPrimaryKey();

    boolean generateSelectByExampleWithoutBLOBs();

    boolean generateSelectByExampleWithBLOBs();

    boolean generateExampleClass();

    boolean generateCountByExample();

    boolean generateUpdateByExampleSelective();

    boolean generateUpdateByExampleWithoutBLOBs();

    boolean generateUpdateByExampleWithBLOBs();

    boolean generatePrimaryKeyClass();

    boolean generateBaseRecordClass();

    boolean generateRecordWithBLOBsClass();

    boolean generateJavaClient();

    IntrospectedTable getIntrospectedTable();
}
