package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;
import com.swf.mybatis.generator.internal.db.DatabaseDialects;

import java.util.List;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class GeneratedKey {

    private String column;

    private String configuredSqlStatement;

    private String runtimeSqlStatement;

    private boolean isIdentity;

    private String type;

    public GeneratedKey(String column,String configuredSqlStatement,boolean isIdentity,String type){
        super();
        this.column = column;
        this.type = type;
        this.isIdentity = isIdentity;
        this.configuredSqlStatement = configuredSqlStatement;

        DatabaseDialects dialect = DatabaseDialects.getDatabaseDialect(configuredSqlStatement);

        if(dialect == null){
            this.runtimeSqlStatement = configuredSqlStatement;
        }else{
            this.runtimeSqlStatement = dialect.getIdentityRetrievalStatement();
        }
    }

    public String getColumn() {
        return column;
    }

    public String getRuntimeSqlStatement() {
        return runtimeSqlStatement;
    }

    public boolean isIdentity() {
        return isIdentity;
    }

    public String getType() {
        return type;
    }

    public String getMyBatis3Order(){
        return isIdentity ? "AFTER" : "BEFORE";
    }

    public XmlElement toXmlElement(){
        XmlElement xmlElement = new XmlElement("generatedkey");
        xmlElement.addAttribute(new Attribute("column",column));
        xmlElement.addAttribute(new Attribute("sqlStatement",configuredSqlStatement));

        if(stringHasValue(type)){
            xmlElement.addAttribute(new Attribute("type",type));
        }
        xmlElement.addAttribute(new Attribute("identity",isIdentity ? "true" : "false"));
        return xmlElement;
    }

    public void validate(List<String> errors, String tableName){
        if(!stringHasValue(runtimeSqlStatement)){
            errors.add(getString("ValidationError.7",tableName));
        }

        if(stringHasValue(type) && !"pre".equals(type) && !"post".equals(type)){
            errors.add(getString("ValidationError.15",tableName));
        }

        if("pre".equals(type) && isIdentity){
            errors.add(getString("ValidationError.23",tableName));
        }

        if("post".equals(type) && isIdentity){
            errors.add(getString("ValidationError.24",tableName));
        }
    }

    public boolean isJdbcStandard(){
        return "JDBC".equals(runtimeSqlStatement);
    }
}
