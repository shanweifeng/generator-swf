package com.swf.mybatis.generator.api;

import com.swf.mybatis.generator.config.*;
import com.swf.mybatis.generator.internal.rules.ConditionalModelRules;
import com.swf.mybatis.generator.internal.rules.FlatModelRules;
import com.swf.mybatis.generator.internal.rules.HierarchicalModelRules;
import com.swf.mybatis.generator.internal.rules.Rules;

import java.util.*;

import static com.swf.mybatis.generator.internal.util.StringUtility.isTrue;
import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;

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

    protected FullyQualifiedTable fullyQualifiedTable;

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

    public FullyQualifiedTable getFullyQualifiedJTable() {
        return fullyQualifiedTable;
    }

    public String getSelectByExampleQueryId() {
        return tableConfiguration.getSelectByExampleQueryId();
    }

    public String getSelectByPrimaryKeyQueryId() {
        return tableConfiguration.getSelectByPrimaryKeyQueryId();
    }

    public GeneratedKey getGeneratedKey() {
        return tableConfiguration.getGeneratedKey();
    }

    public IntrospectedColumn getColumn(String columnName) {
        if (columnName == null) {
            return null;
        } else {
            // search primary key column
            for (IntrospectedColumn introspectedColumn : primaryKeyColumn) {
                if (introspectedColumn.isColumnNameDelimited()) {
                    if (introspectedColumn.getActualColumnName().equals(columnName)) {
                        return introspectedColumn;
                    }
                } else {
                    if (introspectedColumn.getActualColumnName().equalsIgnoreCase(columnName)) {
                        return introspectedColumn;
                    }
                }
            }
            // search base columns
            for (IntrospectedColumn introspectedColumn : baseColumns) {
                if (introspectedColumn.isColumnNameDelimited()) {
                    if (introspectedColumn.getActualColumnName().equals(columnName)) {
                        return introspectedColumn;
                    }
                } else {
                    if (introspectedColumn.getActualColumnName().equalsIgnoreCase(columnName)) {
                        return introspectedColumn;
                    }
                }
            }
            // search blob columns
            for (IntrospectedColumn introspectedColumn : blobColumns) {
                if (introspectedColumn.isColumnNameDelimited()) {
                    if (introspectedColumn.getActualColumnName().equals(columnName)) {
                        return introspectedColumn;
                    }
                } else {
                    if (introspectedColumn.getActualColumnName().equalsIgnoreCase(columnName)) {
                        return introspectedColumn;
                    }
                }
            }
            return null;
        }
    }

    public boolean hasJDBCDateColumns() {
        boolean rc = false;

        for (IntrospectedColumn introspectedColumn : primaryKeyColumn) {
            if (introspectedColumn.isJDBCDateColumn()) {
                rc = true;
                break;
            }
        }

        if (!rc) {
            for (IntrospectedColumn introspectedColumn : baseColumns) {
                if (introspectedColumn.isJDBCDateColumn()) {
                    rc = true;
                    break;
                }
            }
        }
        return rc;
    }

    public boolean hasJDBCTimeColumns() {
        boolean rc = false;
        for (IntrospectedColumn introspectedColumn : primaryKeyColumn) {
            if (introspectedColumn.isJDBCTimeColumn()) {
                rc = true;
                break;
            }
        }

        if (!rc) {
            for (IntrospectedColumn introspectedColumn : baseColumns) {
                if (introspectedColumn.isJDBCTimeColumn()) {
                    rc = true;
                    break;
                }
            }
        }
        return rc;
    }

    public List<IntrospectedColumn> getPrimaryKeyColumn() {
        return primaryKeyColumn;
    }


    public boolean hasPrimaryKeyColumns() {
        return primaryKeyColumn.size() > 0;
    }

    public List<IntrospectedColumn> getBaseColumns() {
        return baseColumns;
    }

    public List<IntrospectedColumn> getAllColumns() {
        List<IntrospectedColumn> answer = new ArrayList<>();
        answer.addAll(primaryKeyColumn);
        answer.addAll(baseColumns);
        answer.addAll(blobColumns);
        return answer;
    }

    public List<IntrospectedColumn> getNonBLOBColumns() {
        List<IntrospectedColumn> answer = new ArrayList<>();
        answer.addAll(primaryKeyColumn);
        answer.addAll(baseColumns);
        return answer;
    }

    public int getNonBLOBColumnCount() {
        return primaryKeyColumn.size() + baseColumns.size();
    }

    public List<IntrospectedColumn> getNonPrimaryKeyColumns() {
        List<IntrospectedColumn> answer = new ArrayList<>();
        answer.addAll(baseColumns);
        answer.addAll(blobColumns);
        return answer;
    }

    public List<IntrospectedColumn> getBLOBColumns() {
        return blobColumns;
    }

    public boolean hasBLOBColumns() {
        return blobColumns.size() > 0;
    }

    public boolean hasBaseColumns() {
        return baseColumns.size() > 0;
    }

    public Rules getRules() {
        return rules;
    }

    public String getTableConfigurationProperty(String property) {
        return tableConfiguration.getProperty(property);
    }

    public String getPrimaryKeyType() {
        return internalAttributes.get(InternalAttribute.ATTR_PRIMARY_KEY_TYPE);
    }

    public String getBaseRecordType() {
        return internalAttributes.get(InternalAttribute.ATTR_BASE_RECORD_TYPE);
    }

    public String getExampleType() {
        return internalAttributes.get(InternalAttribute.ATTR_EXAMPLE_TYPE);
    }

    public String getRecordWithBLOBsType() {
        return internalAttributes.get(InternalAttribute.ATTR_RECORD_WITH_BLOBS_TYPE);
    }

    public String getMyBatis3SqlMapNamespace() {
        String namespace = getMyBatis3JavaMapperType();
        if (namespace == null) {
            namespace = getMyBatis3FallbackSqlMapNamespace();
        }
        return namespace;
    }

    public String getMyBatis3FallbackSqlMapNamespace() {
        return internalAttributes.get(InternalAttribute.ATTR_MYBATIS3_FALLBACK_SQL_MAP_NAMESPACE);
    }

    public boolean hasAnyColumns() {
        return primaryKeyColumn.size() > 0 || baseColumns.size() > 0
                || blobColumns.size() > 0;
    }

    public void setTableConfiguration(TableConfiguration tableConfiguration) {
        this.tableConfiguration = tableConfiguration;
    }

    public void setFullyQualifiedTable(FullyQualifiedTable fullyQualifiedTable) {
        this.fullyQualifiedTable = fullyQualifiedTable;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void addColumn(IntrospectedColumn introspectedColumn) {
        if (introspectedColumn.isBLOBColumn()) {
            blobColumns.add(introspectedColumn);
        } else {
            baseColumns.add(introspectedColumn);
        }
        introspectedColumn.setIntrospectedTable(this);
    }

    public void addPrimaryKeyColumn(String columnName) {
        boolean found = false;
        // first search base columns
        Iterator<IntrospectedColumn> iter = baseColumns.iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn =iter.next();
            if (introspectedColumn.getActualColumnName().equals(columnName)) {
                primaryKeyColumn.add(introspectedColumn);
                iter.remove();
                found = true;
                break;
            }
        }

        if (!found) {
            iter = blobColumns.iterator();
            while (iter.hasNext()) {
                IntrospectedColumn introspectedColumn = iter.next();
                if (introspectedColumn.getActualColumnName().equals(columnName)) {
                    primaryKeyColumn.add(introspectedColumn);
                    iter.remove();
                    found = true;
                    break;
                }
            }
        }
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void setAttibute(String name, Object value) {
        attributes.put(name,value);
    }

    public void initialize() {
        calculateJavaClientAttributes();
        calculateModelAttibutes();
        calculateXmlAttributes();

        if (tableConfiguration.getModelType() == ModelType.HIERARCHICAL) {
            rules = new HierarchicalModelRules(this);
        } else if (tableConfiguration.getModelType() == ModelType.FLAT) {
            rules = new FlatModelRules(this);
        } else {
            rules = new ConditionalModelRules(this);
        }
        context.getPlugins().initialized(this);
    }

    protected void calculateXmlAttributes() {
        setMyBatis3XmlMapperFileName(calculateMyBatis3XmlMapperFileName());
        setMyBatis3XmlMapperPackage(calculateSqlMapPackage());

        setMyBatis3FallbackSqlMapNamespace(calculateMyBatis3FallbackSqlMapNamespace());

        setSqlMapFullyQualifiedRuntimeTableName(calculateSqlMapFullyQualifiedRuntimeTableName());
        setSqlMapAliasedFullyQualifiedRuntimeTableName(calculateSqlMapAliasedFullyQualifiedRuntieTableName());

        setCountByExampleStatementId("countByExample");
        setDeleteByExampleStatementId("deleteByExample");
        setDeleteByPrimaryKeyStatementId("deleteByPrimaryKey");
        setInsertStatementId("insert");
        setInsertSelectiveStatementId("insertSelective");
        setSelectAllStatementId("selectAll");
        setSelectByExampleStatementId("selectByExample");
        setSelectByExampleWithBLOBsStatementId("selectByExampleWithBLOBs");
        setSelectByPrimaryKeyStatementId("selectByPrimaryKey");
        setUpdateByExampleStatementId("updateByExample");
        setUpdateByExampleSelectiveStatementId("updateByExampleSelective");
        setUpdateByExampleWithBLOBsStatementId("updateByExampleWithBLOBs");
        setUpdateByPrimaryKeyStatementId("updateByPrimaryKey");
        setUpdateByPrimaryKeySelectiveStatementId("updateByPrimaryKeySelective");
        setUpdateByPrimaryKeyWithBLOBsStatementId("updateByPrimaryKeyWithBLOBs");
        setBaseResultMapId("BaseResultMap");
        setResultMapWithBLOBsId("ResultMapWithBLOBs");
        setExampleWhereClauseId("example_Where_Clause");
        setBaseColumnListId("Base_Column_List");
        setBlobColumnListId("Blob_Column_List");
        setMyBatis3UpdateByExampleWhereClauseId("Update_By_Example_Where_Clause");
    }

    public void setBlobColumnListId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_BLOB_COLUMN_LIST_ID, s);
    }

    public void setBaseColumnListId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_BASE_COLUMN_LIST_ID, s);
    }

    public void setExampleWhereClauseId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_EXAMPLE_WHERE_CLAUSE_ID, s);
    }

    public void setMyBatis3UpdateByExampleWhereClauseId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_MYBATIS3_UPDATE_BY_EXAMPLE_WHERE_CLAUSE_ID, s);
    }

    public void setResultMapWithBLOBsId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_MYBATIS3_UPDATE_BY_EXAMPLE_WHERE_CLAUSE_ID, s);
    }

    public void setBaseResultMapId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_BASE_RESULT_MAP_ID, s);
    }

    public void setUpdateByPrimaryKeyWithBLOBsStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_UPDATE_BY_PRIMARY_KEY_WITH_BLOBS_STATEMENT_ID, s);
    }

    public void setUpdateByPrimaryKeySelectiveStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_UPDATE_BY_PRIMARY_KEY_SELECTIVE_STATEMENT_ID, s);
    }

    public void setUpdateByPrimaryKeyStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_UPDATE_BY_PRIMARY_KEY_STATEMENT_ID, s);
    }

    public void setUpdateByExampleWithBLOBsStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_UPDATE_BY_EXAMPLE_WITH_BLOBS_STATEMENT_ID, s);
    }

    public void setUpdateByExampleSelectiveStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_UPDATE_BY_EXAMPLE_SELECTIVE_STATEMENT_ID, s);
    }

    public void setUpdateByExampleStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_UPDATE_BY_EXAMPLE_STATEMENT_ID, s);
    }

    public void setSelectByPrimaryKeyStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_SELECT_BY_PRIMARY_KEY_STATEMENT_ID, s);
    }

    public void setSelectByExampleWithBLOBsStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_SELECT_BY_EXAMPLE_WITH_BLOBS_STATEMENT_ID, s);
    }

    public void setSelectAllStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_SELECT_ALL_STATEMENT_ID, s);
    }

    public void setSelectByExampleStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_SELECT_BY_EXAMPLE_STATEMENT_ID, s);
    }

    public void setInsertSelectiveStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_INSERT_SELECTIVE_STATEMENT_ID, s);
    }

    public void setInsertStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_INSERT_STATEMENT_ID, s);
    }

    public void setDeleteByPrimaryKeyStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_DELETE_BY_PRIMARY_KEY_STATEMENT_ID, s);
    }

    public void setDeleteByExampleStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_DELETE_BY_EXAMPLE_STATEMENT_ID, s);
    }

    public void setCountByExampleStatementId(String s) {
        internalAttributes.put(InternalAttribute.ATTR_COUNT_BY_EXAMPLE_STATEMENT_ID, s);
    }

    public String getBlobColumnListId() {
        return internalAttributes.get(InternalAttribute.ATTR_BLOB_COLUMN_LIST_ID);
    }

    public String getBaseColumnListId() {
        return internalAttributes.get(InternalAttribute.ATTR_BASE_COLUMN_LIST_ID);
    }

    public String getExampleWhereClauseId() {
        return internalAttributes.get(InternalAttribute.ATTR_EXAMPLE_WHERE_CLAUSE_ID);
    }

    public String getMyBatis3UpdateByExampleWhereClauseId() {
        return internalAttributes.get(InternalAttribute.ATTR_MYBATIS3_UPDATE_BY_EXAMPLE_WHERE_CLAUSE_ID);
    }

    public String getResultMapWithBLOBsId() {
        return internalAttributes.get(InternalAttribute.ATTR_RESULT_MAP_WITH_BLOBS_ID);
    }

    public String getBaseResultMapId() {
        return internalAttributes.get(InternalAttribute.ATTR_BASE_RESULT_MAP_ID);
    }

    public String getUpdateByPrimaryKeyWithBLOBsStatement() {
        return internalAttributes.get(InternalAttribute.ATTR_UPDATE_BY_PRIMARY_KEY_WITH_BLOBS_STATEMENT_ID);
    }

    public String getUpdateByPrimaryKeySelectiveStatementId() {
        return internalAttributes.get(InternalAttribute.ATTR_UPDATE_BY_PRIMARY_KEY_SELECTIVE_STATEMENT_ID);
    }

    public String getUpdateByPrimaryKeyStatementId() {
        return internalAttributes.get(InternalAttribute.ATTR_UPDATE_BY_PRIMARY_KEY_STATEMENT_ID);
    }

    public String getUpdateByExampleWithBLOBsStatementId() {
        return internalAttributes.get(InternalAttribute.ATTR_UPDATE_BY_EXAMPLE_WITH_BLOBS_STATEMENT_ID);
    }

    public String getUpdateByExampleSelectiveStatementId() {
        return internalAttributes.get(InternalAttribute.ATTR_UPDATE_BY_EXAMPLE_SELECTIVE_STATEMENT_ID);
    }

    public String getUpdateByExampleStatementId() {
        return internalAttributes.get(InternalAttribute.ATTR_UPDATE_BY_EXAMPLE_STATEMENT_ID);
    }

    public String getSelectByPrimaryKeyStatementId() {
        return internalAttributes.get(InternalAttribute.ATTR_SELECT_BY_PRIMARY_KEY_STATEMENT_ID);
    }

    public String getSelectByExampleWithBLOBsStatementId() {
        return internalAttributes.get(InternalAttribute.ATTR_SELECT_BY_EXAMPLE_WITH_BLOBS_STATEMENT_ID);
    }

    public String getSelectAllStatementId() {
        return internalAttributes.get(InternalAttribute.ATTR_SELECT_BY_EXAMPLE_STATEMENT_ID);
    }

    public String getSelectByExampleStatementId() {
        return internalAttributes.get(InternalAttribute.ATTR_SELECT_BY_EXAMPLE_STATEMENT_ID);
    }

    public String getInsertSelectiveStatementId() {
        return internalAttributes.get(InternalAttribute.ATTR_INSERT_SELECTIVE_STATEMENT_ID);
    }

    public String getInsertStatementId() {
        return internalAttributes.get(InternalAttribute.ATTR_INSERT_STATEMENT_ID);
    }

    public String getDeleteByPrimaryKeyStatementId() {
        return internalAttributes.get(InternalAttribute.ATTR_DELETE_BY_PRIMARY_KEY_STATEMENT_ID);
    }

    public String getDeleteByExampleStatementId() {
        return internalAttributes.get(InternalAttribute.ATTR_DELETE_BY_EXAMPLE_STATEMENT_ID);
    }

    public String getCountByExampleStatementId() {
        return internalAttributes.get(InternalAttribute.ATTR_COUNT_BY_EXAMPLE_STATEMENT_ID);
    }

    protected String calculateJavaClientImplementationPackage() {
        JavaClientGeneratorConfiguration config = context.getJavaClientGeneratorConfiguration();
        if (config == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (stringHasValue(config.getImplementationPackage())) {
            sb.append(config.getImplementationPackage());
        } else {
            sb.append(config.getTargetPackage());
        }

        sb.append(fullyQualifiedTable.getSubPackageForClientOrSqlMap(isSubPackagesEnabled(config)));
        return sb.toString();
    }

    private boolean isSubPackagesEnabled(PropertyHolder propertyHolder) {
        return isTrue((propertyHolder.getProperty(PropertyRegistry.ANY_ENABLE_SUB_PACKAGES)));
    }

    protected String calculateJavaClientInterfacePackage() {
        JavaClientGeneratorConfiguration config = context.getJavaClientGeneratorConfiguration();
        if (config == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(config.getTargetPackage());
        sb.append(fullyQualifiedTable.getSubPackageForClientOrSqlMap(isSubPackagesEnabled(config)));
        return sb.toString();
    }

    protected void calculateJavaClientAttributes() {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(calculateJavaClientInterfacePackage());
        sb.append('.');
        if (stringHasValue(tableConfiguration.getMapperName())) {
            sb.append(tableConfiguration.getMapperName());
        } else {
            if (stringHasValue(fullyQualifiedTable.getDomainObjectSubPackage())) {
                sb.append(fullyQualifiedTable.getDomainObjectSubPackage());
                sb.append('.');
            }
            sb.append(fullyQualifiedTable.getDomainObjectName());
        }

        setMyBatis3JavaMapperType(sb.toString());

        sb.setLength(0);
        sb.append(calculateJavaClientInterfacePackage());
        sb.append('.');
        if (stringHasValue(tableConfiguration.getSqlProviderName())) {
            sb.append(tableConfiguration.getSqlProviderName());
        } else {
            if (stringHasValue(fullyQualifiedTable.getDomainObjectSubPackage())) {
                sb.append(fullyQualifiedTable.getDomainObjectSubPackage());
                sb.append('.');
            }
            sb.append(fullyQualifiedTable.getDomainObjectName());
            sb.append("SqlProvider");
        }

        setMyBatis3SqlProviderType(sb.toString());

        sb.setLength(0);;
        sb.append(calculateJavaClientInterfacePackage());
        sb.append('.');
        sb.append(fullyQualifiedTable.getDomainObjectName());
        sb.append("DynamicSqlSupport");
        setMyBatisDynamicSqlSupportType(sb.toString());
    }

    protected String calculateJavaModelPackage() {
        JavaModelGeneratorConfiguration config = context.getJavaModelGeneratorConfiguration();

        StringBuilder sb = new StringBuilder();
        sb.append(config.getTargetPackage());
        sb.append(fullyQualifiedTable.getSubPackageForClientOrSqlMap(isSubPackagesEnabled(config)));
        return sb.toString();
    }

    protected void calculateModelAttibutes() {
        String pakkage = calculateJavaModelPackage();

        StringBuilder sb = new StringBuilder();
        sb.append(pakkage);
        sb.append('.');
        sb.append(fullyQualifiedTable.getDomainObjectName());
        sb.append("key");
        setPrimaryKeyType(sb.toString());

        sb.setLength(0);
        sb.append(pakkage);
        sb.append('.');
        sb.append(fullyQualifiedTable.getDomainObjectName());
        setBaseRecordType(sb.toString());

        sb.setLength(0);
        sb.append(pakkage);
        sb.append('.');
        sb.append(fullyQualifiedTable.getDomainObjectName());
        sb.append("WithBLOBs");
        setRecordWithBLOBsType(sb.toString());

        sb.setLength(0);
        sb.append(pakkage);
        sb.append('.');
        sb.append(fullyQualifiedTable.getDomainObjectName());
        sb.append("Example");
        setExampleType(sb.toString());
    }

    protected String calculateSqlMapPackage() {
        StringBuilder sb = new StringBuilder();
        SqlMapGeneratorConfiguration config = context.getSqlMapGeneratorConfiguration();

        if (config != null) {
            sb.append(config.getTargetPackage());
            sb.append(fullyQualifiedTable.getSubPackageForClientOrSqlMap(isSubPackagesEnabled(config)));
            if (stringHasValue(tableConfiguration.getMapperName())) {
                String mapperName = tableConfiguration.getMapperName();
                int ind = mapperName.lastIndexOf('.');
                if (ind != -1) {
                    sb.append('.').append(mapperName.substring(0, ind));
                }
            } else if (stringHasValue(fullyQualifiedTable.getDomainObjectSubPackage())) {
                sb.append('.').append(fullyQualifiedTable.getDomainObjectSubPackage());
            }
        }
        return sb.toString();
    }

    protected String calculateMyBatis3XmlMapperFileName() {
        StringBuilder sb = new StringBuilder();
        if (stringHasValue(tableConfiguration.getMapperName())) {
            String mapperName = tableConfiguration.getMapperName();
            int ind = mapperName.lastIndexOf('.');
            if (ind == -1) {
                sb.append(mapperName);
            } else {
                sb.append(mapperName.substring(0, ind));
            }
            sb.append(".xml");
        } else {
            sb.append(fullyQualifiedTable.getDomainObjectName());
            sb.append("Mapper.xml");
        }
        return sb.toString();
    }

    protected String calculateMyBatis3FallbackSqlMapNamespace() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateSqlMapPackage());
        sb.append('.');
        if (stringHasValue(tableConfiguration.getMapperName())) {
            sb.append(tableConfiguration.getMapperName());
        } else {
            sb.append(fullyQualifiedTable.getDomainObjectName());
            sb.append("Mapper");
        }
        return sb.toString();
    }

    protected String calculateSqlMapFullyQualifiedRuntimeTableName() {
        return fullyQualifiedTable.getFullyQualifiedTableNameAtRuntime();
    }

    protected String calculateSqlMapAliasedFullyQualifiedRuntieTableName() {
        return fullyQualifiedTable.getAliasedFullyQualifiedTableNameAtRuntime();
    }

    protected String getFullyQualifiedTableNameAtRuntime() {
        return internalAttributes.get(InternalAttribute.ATTR_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME);
    }

    public String getAliasedFullyQualifiedTableNameAtRuntime() {
        return internalAttributes.get(InternalAttribute.ATTR_ALIASED_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME);
    }

    public abstract void calculateGenerators(List<String> warnings, ProgressCallback progressCallback);

    public abstract List<GeneratedJavaFile> getGeneratedJavaFiles();

    public abstract List<GeneratedXmlFile> getGeneratedXmlFiles();

    public abstract int getGenerationSteps();

    public void setRules(Rules rules) {
        this.rules = rules;
    }

    public TableConfiguration getTableConfiguration() {
        return tableConfiguration;
    }

    public void setPrimaryKeyType(String primaryKeyType) {
        internalAttributes.put(InternalAttribute.ATTR_PRIMARY_KEY_TYPE, primaryKeyType);
    }

    public void setBaseRecordType(String baseRecordType) {
        internalAttributes.put(InternalAttribute.ATTR_BASE_RECORD_TYPE, baseRecordType);
    }

    public void setRecordWithBLOBsType(String recordWithBLOBsType) {
        internalAttributes.put(InternalAttribute.ATTR_RECORD_WITH_BLOBS_TYPE, recordWithBLOBsType);
    }

    public void setExampleType(String exampleType) {
        internalAttributes.put(InternalAttribute.ATTR_EXAMPLE_TYPE, exampleType);
    }

    public void setMyBatis3FallbackSqlMapNamespace(String sqlMapNamespace) {
        internalAttributes.put(InternalAttribute.ATTR_MYBATIS3_FALLBACK_SQL_MAP_NAMESPACE, sqlMapNamespace);
    }

    public void setSqlMapFullyQualifiedRuntimeTableName(String fullQualifiedRuntimeTableName) {
        internalAttributes.put(InternalAttribute.ATTR_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME, fullQualifiedRuntimeTableName);
    }

    public void setSqlMapAliasedFullyQualifiedRuntimeTableName(String  aliasedFullyQualifiedRuntimeTableName) {
        internalAttributes.put(InternalAttribute.ATTR_ALIASED_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME, aliasedFullyQualifiedRuntimeTableName);
    }

    public String getMyBatis3XmlMapperPackage() {
        return internalAttributes.get(InternalAttribute.ATTR_MYBATIS3_XML_MAPPER_PACKAGE);
    }

    public void setMyBatis3XmlMapperPackage(String mybatis3XmlMapperpackage) {
        internalAttributes.put(InternalAttribute.ATTR_MYBATIS3_XML_MAPPER_PACKAGE, mybatis3XmlMapperpackage);
    }

    public String getMyBatis3XmlMapperFileName() {
        return internalAttributes.get(InternalAttribute.ATTR_MYBATIS3_XML_MAPPER_FILE_NAME);
    }

    public void setMyBatis3XmlMapperFileName(String mybBatis3XmlMapperFileName) {
        internalAttributes.put(InternalAttribute.ATTR_MYBATIS3_XML_MAPPER_FILE_NAME, mybBatis3XmlMapperFileName);
    }

    public String getMyBatis3JavaMapperType() {
        return internalAttributes.get(InternalAttribute.ATTR_MYBATIS3_JAVA_MAPPER_TYPE);
    }

    public void setMyBatis3JavaMapperType(String myBatis3JavaMapperType) {
        internalAttributes.put(InternalAttribute.ATTR_MYBATIS3_JAVA_MAPPER_TYPE, myBatis3JavaMapperType);
    }

    public String getMyBatis3SqlProviderType() {
        return internalAttributes.get(InternalAttribute.ATTR_MYBATIS3_SQL_PROVIDER_TYPE);
    }

    public void setMyBatis3SqlProviderType(String myBatis3SqlProviderType) {
        internalAttributes.put(InternalAttribute.ATTR_MYBATIS3_SQL_PROVIDER_TYPE, myBatis3SqlProviderType);
    }

    public String getMyBatisDynamicSqlSupportType() {
        return internalAttributes.get(InternalAttribute.ATTR_MYBATIS_DYNAMIC_SQL_SUPPORT_TYPE);
    }

    public void setMyBatisDynamicSqlSupportType(String myBatisDynamicSqlSupportType) {
        internalAttributes.put(InternalAttribute.ATTR_MYBATIS_DYNAMIC_SQL_SUPPORT_TYPE, myBatisDynamicSqlSupportType);
    }

    public TargetRuntime getTargetRuntime() {
        return targetRuntime;
    }

    public boolean isImmutable() {
        Properties properties;
        if (tableConfiguration.getProperties().contains(PropertyRegistry.ANY_IMMUTABLE)) {
            properties = tableConfiguration.getProperties();
        } else {
            properties = context.getJavaModelGeneratorConfiguration().getProperties();
        }
        return isTrue(properties.getProperty(PropertyRegistry.ANY_IMMUTABLE));
    }

    public boolean isConstructorBased() {
        if (isImmutable()) {
            return true;
        }

        Properties properties;
        if (tableConfiguration.getProperties().contains(PropertyRegistry.ANY_CONSTRUCTOR_BASED)) {
            properties = tableConfiguration.getProperties();
        } else {
            properties = context.getJavaModelGeneratorConfiguration().getProperties();
        }
        return isTrue(properties.getProperty(PropertyRegistry.ANY_CONSTRUCTOR_BASED));
    }

    public abstract boolean requiredXMLGenerator();

    public Context getContext() {
        return context;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }
}