package com.swf.mybatis.generator.api;

import com.swf.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import com.swf.mybatis.generator.config.Context;
import com.swf.mybatis.generator.config.TableConfiguration;
import com.swf.mybatis.generator.internal.rules.Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class IntrospectedTable {

    public enum TargetRuntime {
        MYBATIS3,
        MYBATIS3_DSQL
    }

    protected enum InternalAttribute {
        ATTR_PRIMARY_KEY_TYPE,
        ATTR_BASE_RECORD_TYPE,
        ATTR_RECORD_WITH_BLOBS_TYPE,
        ATTR_EXAMPLE_TYPE,
        ATTR_MYBATIS3_XML_MAPPER_PACKAGE,
        ATTR_MYBATIS3_XML_MAPPER_FILE_NAME,
        /** also used as XML Mapper namespace if a Java mapper is generated. */
        ATTR_MYBATIS3_JAVA_MAPPER_TYPE,
        /** used as XML Mapper namespace if no client is generated. */
        ATTR_MYBATIS3_FALLBACK_SQL_MAP_NAMESPACE,
        ATTR_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME,
        ATTR_ALIASED_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME,
        ATTR_COUNT_BY_EXAMPLE_STATEMENT_ID,
        ATTR_DELETE_BY_EXAMPLE_STATEMENT_ID,
        ATTR_DELETE_BY_PRIMARY_KEY_STATEMENT_ID,
        ATTR_INSERT_STATEMENT_ID,
        ATTR_INSERT_SELECTIVE_STATEMENT_ID,
        ATTR_SELECT_ALL_STATEMENT_ID,
        ATTR_SELECT_BY_EXAMPLE_STATEMENT_ID,
        ATTR_SELECT_BY_EXAMPLE_WITH_BLOBS_STATEMENT_ID,
        ATTR_SELECT_BY_PRIMARY_KEY_STATEMENT_ID,
        ATTR_UPDATE_BY_EXAMPLE_STATEMENT_ID,
        ATTR_UPDATE_BY_EXAMPLE_SELECTIVE_STATEMENT_ID,
        ATTR_UPDATE_BY_EXAMPLE_WITH_BLOBS_STATEMENT_ID,
        ATTR_UPDATE_BY_PRIMARY_KEY_STATEMENT_ID,
        ATTR_UPDATE_BY_PRIMARY_KEY_SELECTIVE_STATEMENT_ID,
        ATTR_UPDATE_BY_PRIMARY_KEY_WITH_BLOBS_STATEMENT_ID,
        ATTR_BASE_RESULT_MAP_ID,
        ATTR_RESULT_MAP_WITH_BLOBS_ID,
        ATTR_EXAMPLE_WHERE_CLAUSE_ID,
        ATTR_BASE_COLUMN_LIST_ID,
        ATTR_BLOB_COLUMN_LIST_ID,
        ATTR_MYBATIS3_UPDATE_BY_EXAMPLE_WHERE_CLAUSE_ID,
        ATTR_MYBATIS3_SQL_PROVIDER_TYPE,
        ATTR_MYBATIS_DYNAMIC_SQL_SUPPORT_TYPE
    }

    protected TableConfiguration tableConfiguration;

    protected FullyQualifiedJavaType fullyQualifiedJavaType;

    protected Context context;

    protected Rules rules;

    protected List<IntrospectedColumn> primaryKeyColumn;

    protected List<IntrospectedColumn> baseColumns;

    protected List<IntrospectedColumn> blobColumns;

    protected TargetRuntime targetRuntime;

    protected Map<String,Object> attributes;

    protected Map<IntrospectedTable.InternalAttribute,String> internalAttributes;

    protected String remarks;

    protected String tableType;

    public IntrospectedTable(TargetRuntime targetRuntime){
        super();
        this.targetRuntime = targetRuntime;
        primaryKeyColumn = new ArrayList<>();
        baseColumns = new ArrayList<>();
        blobColumns = new ArrayList<>();
        attributes = new HashMap<>();
        internalAttributes = new HashMap<>();
    }

    public
    // todo 待处理
}
