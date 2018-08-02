package com.swf.mybatis.generator.api;

import com.swf.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import com.swf.mybatis.generator.config.Context;
import com.swf.mybatis.generator.internal.util.StringUtility;
import sun.reflect.generics.tree.TypeSignature;

import java.lang.reflect.Type;
import java.sql.Types;
import java.util.Properties;

public abstract class IntrospectedColumn {

    protected String actualColumnName;

    protected int jdbcType;

    protected String jdbcTypeName;

    protected boolean nullable;

    protected int length;

    protected int scale;

    protected boolean identity;

    protected boolean isSequenceColumn;

    protected String javaProperty;

    protected FullyQualifiedJavaType fullyQualifiedJavaType;

    protected String tableAlias;

    protected String typeHandler;

    protected Context context;

    protected boolean isColumnNameDelimited;

    protected IntrospectedTable introspectedTable;

    protected Properties properties;

    protected String remarks;

    protected String defaultValue;

    protected boolean isAutoIncrement;

    protected boolean isGeneratedColumn;

    protected boolean isGeneratedAlways;

    public IntrospectedColumn(){
        super();
        properties = new Properties();
    }

    public int getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(int jdbcType) {
        this.jdbcType = jdbcType;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("Actual column name: ");
        sb.append(actualColumnName);
        sb.append(", JDBC Type: ");
        sb.append(jdbcType);
        sb.append(", Nullable: ");
        sb.append(nullable);
        sb.append(", Length: ");
        sb.append(length);
        sb.append(", Scale: ");
        sb.append(scale);
        sb.append(", Identity: ");
        sb.append(identity);
        return sb.toString();
    }

    public void setActualColumnName(String actualColumnName) {
        this.actualColumnName = actualColumnName;
        isColumnNameDelimited = StringUtility.stringContainsSpace(actualColumnName);
    }

    public boolean isIdentity() {
        return identity;
    }

    public void setIdentity(boolean identity) {
        this.identity = identity;
    }

    public boolean isBLOBVolumn(){
        String typeName = getJdbcTypeName();
        return "BINARY".equals(typeName) || "BLOB".equals(typeName)
                || "CLOB".equals(typeName) || "LONGNVARCHAR".equals(typeName)
                || "LONGVARBINARY".equals(typeName) || "LONGVARCHAR".equals(typeName)
                || "NCLOB".equals(typeName) || "VARBINARY".equals(typeName);
    }

    public boolean isStringColumn(){
        return fullyQualifiedJavaType.equals(FullyQualifiedJavaType.getStringInstance());
    }

    public boolean isJdbcCharacterColumn(){
        return jdbcType == Types.CHAR || jdbcType == Types.CLOB
                || jdbcType == Types.LONGVARCHAR || jdbcType == Types.VARCHAR
                || jdbcType == Types.LONGNVARCHAR || jdbcType == Types.NCHAR
                || jdbcType == Types.NCLOB || jdbcType == Types.NVARCHAR;
    }

    public String getJavaProperty(){
        return getJavaProperty(null);
    }

    public String getJavaProperty(String prefix){
        if(prefix == null){
            return javaProperty;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(javaProperty);
        return sb.toString();
    }

    public void setJavaProperty(String javaProperty) {
        this.javaProperty = javaProperty;
    }

    public boolean isJDBCDateColumn(){
        return fullyQualifiedJavaType.equals(FullyQualifiedJavaType.getDateInstance())
                && "Date".equalsIgnoreCase(jdbcTypeName);
    }

    public boolean isJDBCTimeColumn(){
        return fullyQualifiedJavaType.equals(FullyQualifiedJavaType.getDateInstance())
                || "TIME".equalsIgnoreCase(jdbcTypeName);
    }

    public String getActualColumnName() {
        return actualColumnName;
    }

    public String getJdbcTypeName() {
        if(jdbcTypeName == null){
            return "OTHER";
        }
        return jdbcTypeName;
    }

    public void setJdbcTypeName(String jdbcTypeName) {
        this.jdbcTypeName = jdbcTypeName;
    }

    public String getTypeHandler() {
        return typeHandler;
    }

    public void setTypeHandler(String typeHandler) {
        this.typeHandler = typeHandler;
    }

    public boolean isColumnNameDelimited() {
        return isColumnNameDelimited;
    }

    public void setColumnNameDelimited(boolean columnNameDelimited) {
        isColumnNameDelimited = columnNameDelimited;
    }

    public FullyQualifiedJavaType getFullyQualifiedJavaType() {
        return fullyQualifiedJavaType;
    }

    public void setFullyQualifiedJavaType(FullyQualifiedJavaType fullyQualifiedJavaType) {
        this.fullyQualifiedJavaType = fullyQualifiedJavaType;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public IntrospectedTable getIntrospectedTable() {
        return introspectedTable;
    }

    public void setIntrospectedTable(IntrospectedTable introspectedTable) {
        this.introspectedTable = introspectedTable;
    }

    public boolean isSequenceColumn() {
        return isSequenceColumn;
    }

    public void setSequenceColumn(boolean sequenceColumn) {
        isSequenceColumn = sequenceColumn;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }

    public boolean isGeneratedColumn() {
        return isGeneratedColumn;
    }

    public void setGeneratedColumn(boolean generatedColumn) {
        isGeneratedColumn = generatedColumn;
    }

    public boolean isGeneratedAlways() {
        return isGeneratedAlways;
    }

    public void setGeneratedAlways(boolean generatedAlways) {
        isGeneratedAlways = generatedAlways;
    }
}
