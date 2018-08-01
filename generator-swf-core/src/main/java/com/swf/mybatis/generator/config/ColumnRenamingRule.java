package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class ColumnRenamingRule {

    private String searchString;

    private String replaceString;

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getReplaceString() {
        return replaceString;
    }

    public void setReplaceString(String replaceString) {
        this.replaceString = replaceString;
    }

    public void validate(List<String> errors, String tableName){
        if(!stringHasValue(searchString)){
            errors.add(getString("Validationg.14",tableName));
        }
    }

    public XmlElement toXmlElement(){
        XmlElement xmlElement = new XmlElement("columnRenamingRule");
        xmlElement.addAttribute(new Attribute("serchString",searchString));

        if(replaceString != null){
            xmlElement.addAttribute(new Attribute("replaceString",replaceString));
        }
        return xmlElement;
    }
}
