package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.swf.mybatis.generator.internal.util.EqualsUtil.areEqual;
import static com.swf.mybatis.generator.internal.util.HashCodeUtil.SEED;
import static com.swf.mybatis.generator.internal.util.HashCodeUtil.hash;
import static com.swf.mybatis.generator.internal.util.StringUtility.composeFullyQualifiedTableName;
import static com.swf.mybatis.generator.internal.util.StringUtility.isTrue;
import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class TableConfiguration extends PropertyHolder {
    private boolean insertStatementEnabled;

    private boolean selectByPrimaryKeyStatementEnabled;

    private boolean selectByExampleStatementEnabled;

    private boolean updateByPrimaryKeyStatementEnabled;

    private boolean deleteByPrimaryKeyStatementEnabled;

    private boolean deleteByExampleStatementEnabled;

    private boolean countByExampleStatementEnabled;

    private boolean updateByExampleStatementEnabled;

    private List<ColumnOverride> columnOverrides;

    private Map<IgnoredColumn,Boolean> ignoredColumns;

    private GeneratedKey generatedKey;

    private String selectByPrimaryKeyQueryId;

    private String selectByExampleQueryId;

    private String catalog;

    private String schema;

    private String tableName;

    private String domainObjectName;

    private String alias;

    private  ModelType modelType;

    private boolean wildcardEscapingEnabled;

    private String configuredModelType;

    private boolean delimitIdentifiers;

    private DomainObjectRenamingRule domainObjectRenamingRule;

    private ColumnRenamingRule columnRenamingRule;

    private boolean isAllColumnDelimitingEnabled;

    private String mapperName;

    private String sqlProviderName;

    private List<IgnoredColumnPattern> ignoredColumnPatterns = new ArrayList<>();

    public TableConfiguration(Context context){
        super();
        this.modelType= context.getDefaultModelType();
        columnOverrides = new ArrayList<>();
        ignoredColumns = new HashMap<>();

        insertStatementEnabled = true;
        selectByPrimaryKeyStatementEnabled = true;
        selectByExampleStatementEnabled = true;
        updateByPrimaryKeyStatementEnabled = true;
        updateByExampleStatementEnabled = true;
        deleteByPrimaryKeyStatementEnabled = true;
        deleteByExampleStatementEnabled = true;
        countByExampleStatementEnabled = true;
        updateByExampleStatementEnabled = true;
    }

    public boolean isInsertStatementEnabled() {
        return insertStatementEnabled;
    }

    public void setInsertStatementEnabled(boolean insertStatementEnabled) {
        this.insertStatementEnabled = insertStatementEnabled;
    }

    public boolean isSelectByPrimaryKeyStatementEnabled() {
        return selectByPrimaryKeyStatementEnabled;
    }

    public void setSelectByPrimaryKeyStatementEnabled(boolean selectByPrimaryKeyStatementEnabled) {
        this.selectByPrimaryKeyStatementEnabled = selectByPrimaryKeyStatementEnabled;
    }

    public boolean isUpdateByPrimaryKeyStatementEnabled() {
        return updateByPrimaryKeyStatementEnabled;
    }

    public void setUpdateByPrimaryKeyStatementEnabled(boolean updateByPrimaryKeyStatementEnabled) {
        this.updateByPrimaryKeyStatementEnabled = updateByPrimaryKeyStatementEnabled;
    }

    public boolean isDeleteByPrimaryKeyStatementEnabled() {
        return deleteByPrimaryKeyStatementEnabled;
    }

    public void setDeleteByPrimaryKeyStatementEnabled(boolean deleteByPrimaryKeyStatementEnabled) {
        this.deleteByPrimaryKeyStatementEnabled = deleteByPrimaryKeyStatementEnabled;
    }

    public boolean isColumnIgnored(String columnName) {
        for (Map.Entry<IgnoredColumn, Boolean> entry : ignoredColumns.entrySet()) {
            if (entry.getKey().matches(columnName)) {
                entry.setValue(Boolean.TRUE);
            }
        }

        for (IgnoredColumnPattern ignoredColumnPattern : ignoredColumnPatterns) {
            if (ignoredColumnPattern.matches(columnName)) {
                return true;
            }
        }
        return false;
    }

    public void addIgnoredColumn(IgnoredColumn ignoredColumn) {
        ignoredColumns.put(ignoredColumn, Boolean.FALSE);
    }

    public void addIgnoredColumnPattern(IgnoredColumnPattern ignoredColumnPattern) {
        ignoredColumnPatterns.add(ignoredColumnPattern);
    }

    public void addColumnOverride(ColumnOverride columnOverride) {
        columnOverrides.add(columnOverride);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof TableConfiguration)) {
            return false;
        }
        TableConfiguration other = (TableConfiguration) obj;
        return areEqual(this.catalog, other.catalog)
                && areEqual(this.schema, other.schema)
                && areEqual(this.tableName, other.tableName);
    }

    @Override
    public int hashCode() {
        int result = SEED;
        result = hash(result, catalog);
        result = hash(result, schema);
        result = hash(result, tableName);
        return result;
    }

    public boolean isSelectByExampleStatementEnabled() {
        return selectByExampleStatementEnabled;
    }

    public void setSelectByExampleStatementEnabled(boolean selectByExampleStatementEnabled) {
        this.selectByExampleStatementEnabled = selectByExampleStatementEnabled;
    }

    public ColumnOverride getColumnOverride(String columnName) {
        for (ColumnOverride co : columnOverrides) {
            if (co.isColumnNameDelimited()) {
                if (columnName.equals(co.getColumnName())) {
                    return  co;
                }
            } else {
                if (columnName.equalsIgnoreCase(co.getColumnName())) {
                    return co;
                }
            }
        }
        return null;
    }

    public GeneratedKey getGeneratedKey() {
        return generatedKey;
    }

    public void setGeneratedKey(GeneratedKey generatedKey) {
        this.generatedKey = generatedKey;
    }

    public boolean isDeleteByExampleStatementEnabled() {
        return deleteByExampleStatementEnabled;
    }

    public void setDeleteByExampleStatementEnabled(boolean deleteByExampleStatementEnabled) {
        this.deleteByExampleStatementEnabled = deleteByExampleStatementEnabled;
    }

    public String getSelectByPrimaryKeyQueryId() {
        return selectByPrimaryKeyQueryId;
    }

    public void setSelectByPrimaryKeyQueryId(String selectByPrimaryKeyQueryId) {
        this.selectByPrimaryKeyQueryId = selectByPrimaryKeyQueryId;
    }

    public String getSelectByExampleQueryId() {
        return selectByExampleQueryId;
    }

    public void setSelectByExampleQueryId(String selectByExampleQueryId) {
        this.selectByExampleQueryId = selectByExampleQueryId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean areAnyStatementsEnabled() {
        return selectByExampleStatementEnabled || selectByPrimaryKeyStatementEnabled
                || insertStatementEnabled || updateByExampleStatementEnabled
                || updateByPrimaryKeyStatementEnabled ||deleteByExampleStatementEnabled
                || deleteByPrimaryKeyStatementEnabled ||countByExampleStatementEnabled;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDomainObjectName() {
        return domainObjectName;
    }

    public void setDomainObjectName(String domainObjectName) {
        this.domainObjectName = domainObjectName;
    }

    public List<ColumnOverride> getColumnOverrides() {
        return columnOverrides;
    }

    public List<String> getIgnoredColumnsInError() {
        List<String> answer = new ArrayList<>();
        for (Map.Entry<IgnoredColumn, Boolean> entry : ignoredColumns.entrySet()) {
            if (Boolean.FALSE.equals(entry.getValue())) {
                answer.add(entry.getKey().getColumnName());
            }
        }
        return answer;
    }

    public ModelType getModelType() {
        return modelType;
    }

    public void setConfiguredModelType(String configuredModelType) {
        this.configuredModelType = configuredModelType;
        this.modelType = ModelType.getModelType(configuredModelType);
    }

    public boolean isWildcardEscapingEnabled() {
        return wildcardEscapingEnabled;
    }

    public void setWildcardEscapingEnabled(boolean wildcardEscapingEnabled) {
        this.wildcardEscapingEnabled = wildcardEscapingEnabled;
    }

    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("table");
        xmlElement.addAttribute(new Attribute("tableName", tableName));
        if (stringHasValue(catalog)) {
            xmlElement.addAttribute(new Attribute("catalog", catalog));
        }

        if (stringHasValue(schema)) {
            xmlElement.addAttribute(new Attribute("schema", schema));
        }

        if (stringHasValue(alias)) {
            xmlElement.addAttribute(new Attribute("alias", alias));
        }

        if (stringHasValue(domainObjectName)) {
            xmlElement.addAttribute(new Attribute("domainObjectName", domainObjectName));
        }

        if (!insertStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableInsert", "false"));
        }

        if (!selectByPrimaryKeyStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableSelectByPrimaryKey", "false"));
        }

        if (!selectByExampleStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableSelectByExample", "false"));
        }

        if (!updateByPrimaryKeyStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableUpdateByPrimaryKey", "false"));
        }

        if (!deleteByPrimaryKeyStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableDeleteByPrimaryKey", "false"));
        }

        if (!deleteByExampleStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableDeleteByPExample", "false"));
        }

        if (!countByExampleStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableCountByExample", "false"));
        }

        if (!updateByExampleStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableUpdateByExample", "false"));
        }

        if (stringHasValue(selectByPrimaryKeyQueryId)) {
            xmlElement.addAttribute(new Attribute("selectByPrimaryKeyQueryId", selectByPrimaryKeyQueryId));
        }

        if (stringHasValue(selectByExampleQueryId)) {
            xmlElement.addAttribute(new Attribute("selectByExampleQueryId", selectByExampleQueryId));
        }

        if (configuredModelType != null) {
            xmlElement.addAttribute(new Attribute("modelType", configuredModelType));
        }

        if (wildcardEscapingEnabled) {
            xmlElement.addAttribute(new Attribute("escapeWildcards","true"));
        }

        if (isAllColumnDelimitingEnabled) {
            xmlElement.addAttribute(new Attribute("delimitAllColumns","true"));
        }

        if (delimitIdentifiers) {
            xmlElement.addAttribute(new Attribute("delimitIdentifiers","true"));
        }

        if (stringHasValue(mapperName)) {
            xmlElement.addAttribute(new Attribute("mapperName",mapperName));
        }

        if (stringHasValue(sqlProviderName)) {
            xmlElement.addAttribute(new Attribute("sqlProviderName",sqlProviderName));
        }

        addPropertyXmlElements(xmlElement);

        if (generatedKey != null) {
            xmlElement.addElement(generatedKey.toXmlElement());
        }

        if (domainObjectRenamingRule != null) {
            xmlElement.addElement(domainObjectRenamingRule.toXmlElement());
        }


        if (columnRenamingRule != null) {
            xmlElement.addElement(columnRenamingRule.toXmlElement());
        }

        if (ignoredColumns.size() > 0) {
            for (IgnoredColumn ignoredColumn : ignoredColumns.keySet()) {
                xmlElement.addElement(ignoredColumn.toXmlElement());
            }
        }

        for (IgnoredColumnPattern ignoredColumnPattern : ignoredColumnPatterns) {
            xmlElement.addElement(ignoredColumnPattern.toXmlElement());
        }

        if (columnOverrides.size() > 0) {
            for (ColumnOverride columnOverride : columnOverrides) {
                xmlElement.addElement(columnOverride.toXmlElement());
            }
        }
        return xmlElement;
    }

    @Override
    public String toString() {
        return composeFullyQualifiedTableName(catalog, schema, tableName, '.');
    }

    public boolean isCountByExampleStatementEnabled() {
        return countByExampleStatementEnabled;
    }

    public void setCountByExampleStatementEnabled(boolean countByExampleStatementEnabled) {
        this.countByExampleStatementEnabled = countByExampleStatementEnabled;
    }

    public boolean isUpdateByExampleStatementEnabled() {
        return updateByExampleStatementEnabled;
    }

    public void setUpdateByExampleStatementEnabled(boolean updateByExampleStatementEnabled) {
        this.updateByExampleStatementEnabled = updateByExampleStatementEnabled;
    }

    public boolean isDelimitIdentifiers() {
        return delimitIdentifiers;
    }

    public void setDelimitIdentifiers(boolean delimitIdentifiers) {
        this.delimitIdentifiers = delimitIdentifiers;
    }

    public void validate(List<String> errors, int listPosition) {
        if (!stringHasValue(tableName)) {
            errors.add(getString("ValidationError.6", Integer.toString(listPosition)));
        }

        String fqTableName = composeFullyQualifiedTableName(catalog, schema, tableName, '.');
        if (generatedKey != null) {
            generatedKey.validate(errors, fqTableName);
        }

        if (isTrue(getProperty(PropertyRegistry.TABLE_USE_COLUMN_INDEXES)) && selectByExampleStatementEnabled
                && selectByPrimaryKeyStatementEnabled) {
            boolean queryId1Set = stringHasValue(selectByExampleQueryId);
            boolean queryId2Set = stringHasValue(selectByPrimaryKeyQueryId);
            if (queryId1Set != queryId2Set) {
                errors.add(getString("ValidationError.13", fqTableName));
            }
        }

        if (domainObjectRenamingRule != null) {
            domainObjectRenamingRule.validate(errors, fqTableName);
        }

        if (columnRenamingRule != null) {
            columnRenamingRule.validate(errors,fqTableName);
        }

        for (ColumnOverride columnOverride : columnOverrides) {
            columnOverride.validate(errors, fqTableName);
        }

        for (IgnoredColumn ignoredColumn : ignoredColumns.keySet()) {
            ignoredColumn.validate(errors, fqTableName);
        }

        for (IgnoredColumnPattern ignoredColumnPattern : ignoredColumnPatterns) {
            ignoredColumnPattern.validate(errors, fqTableName);
        }
    }

    public DomainObjectRenamingRule getDomainObjectRenamingRule() {
        return domainObjectRenamingRule;
    }

    public void setDomainObjectRenamingRule(DomainObjectRenamingRule domainObjectRenamingRule) {
        this.domainObjectRenamingRule = domainObjectRenamingRule;
    }

    public ColumnRenamingRule getColumnRenamingRule() {
        return columnRenamingRule;
    }

    public void setColumnRenamingRule(ColumnRenamingRule columnRenamingRule) {
        this.columnRenamingRule = columnRenamingRule;
    }

    public boolean isAllColumnDelimitingEnabled() {
        return isAllColumnDelimitingEnabled;
    }

    public void setAllColumnDelimitingEnabled(boolean allColumnDelimitingEnabled) {
        isAllColumnDelimitingEnabled = allColumnDelimitingEnabled;
    }

    public String getMapperName() {
        return mapperName;
    }

    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
    }

    public String getSqlProviderName() {
        return sqlProviderName;
    }

    public void setSqlProviderName(String sqlProviderName) {
        this.sqlProviderName = sqlProviderName;
    }
}
