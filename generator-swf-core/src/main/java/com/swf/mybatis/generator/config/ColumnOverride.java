package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringContainsSpace;
import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class ColumnOverride extends PropertyHolder {
    private String columnName;

    private String javaProperty;

    private String jdbcType;

    private String javaType;

    private String typeHandler;

    private boolean isColumnNameDelimited;

    private String configuredDelimitedColumnName;

    private boolean isGeneratedAlways;

    public ColumnOverride(String columnName){
        super();
        this.columnName = columnName;
        isColumnNameDelimited = stringContainsSpace(columnName);
    }

    public String getColumnName() {
        return columnName;
    }

    public String getJavaProperty() {
        return javaProperty;
    }

    public void setJavaProperty(String javaProperty) {
        this.javaProperty = javaProperty;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getTypeHandler() {
        return typeHandler;
    }

    public void setTypeHandler(String typeHandler) {
        this.typeHandler = typeHandler;
    }

    public XmlElement toXmlElement(){
        XmlElement xmlElement = new XmlElement("columnOverride");
        xmlElement.addAttribute(new Attribute("column",columnName));

        if(stringHasValue(javaProperty)){
            xmlElement.addAttribute(new Attribute("property",javaProperty));
        }

        if(stringHasValue(javaType)){
            xmlElement.addAttribute(new Attribute("javaType", javaType));
        }

        if(stringHasValue(jdbcType)){
            xmlElement.addAttribute(new Attribute("jdbcType",jdbcType));
        }

        if(stringHasValue(typeHandler)){
            xmlElement.addAttribute(new Attribute("typeHandler",typeHandler));
        }

        if(stringHasValue(configuredDelimitedColumnName)){
            xmlElement.addAttribute(new Attribute("delimitedColumnName",configuredDelimitedColumnName));
        }

        addPropertyXmlElements(xmlElement);
        return xmlElement;
    }

    public boolean isColumnNameDelimited() {
        return isColumnNameDelimited;
    }

    public void setColumnNameDelimited(boolean columnNameDelimited) {
        isColumnNameDelimited = columnNameDelimited;
    }

    public void validate(List<String> errors, String tableName){
        if(!stringHasValue(columnName)){
            errors.add(getString("ValidationError",tableName));
        }
    }

    public boolean isGeneratedAlways() {
        return isGeneratedAlways;
    }

    public void setGeneratedAlways(boolean generatedAlways) {
        isGeneratedAlways = generatedAlways;
    }
}
