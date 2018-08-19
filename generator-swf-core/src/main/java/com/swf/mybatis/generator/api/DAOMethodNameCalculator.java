package com.swf.mybatis.generator.api;

import org.omg.PortableInterceptor.INACTIVE;

public interface DAOMethodNameCalculator {

    String getInsertMethodName(IntrospectedTable introspectedTable);

    String getInsertSelectiveMethodName(IntrospectedTable introspectedTable);

    String getUpdateByPrimaryKeyWithoutBLOBsMethodName(IntrospectedTable introspectedTable);

    String getUpdateByPrimaryKeyWithBLOBsMethodName(IntrospectedTable introspectedTable);

    String getUpdateByPrimaryKeySelectiveMethodName(IntrospectedTable introspectedTable);

    String getSelectByPrimaryKeyMethodName(IntrospectedTable introspectedTable);

    String getSelectByExampleWithoutBLOBsMethodName(IntrospectedTable introspectedTable);

    String getSelectByExampleWithBLOBsMEthodName(IntrospectedTable introspectedTable);

    String getDeleteByPrimaryKeyMethodName(IntrospectedTable introspectedTable);

    String getDeleteByExampleMethodName(IntrospectedTable introspectedTable);

    String getCountByExampleMethodName(IntrospectedTable introspectedTable);

    String getUpdateByExampleSelectiveMethodName(IntrospectedTable introspectedTable);

    String getUpdateByExampleWithBLOBsMethodName(IntrospectedTable introspectedTable);

    String getUpdateByExampleWithoutBLOBsMethodName(IntrospectedTable introspectedTable);
}
