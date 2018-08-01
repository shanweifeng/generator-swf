package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class IgnoredColumnException extends IgnoredColumn {

    public IgnoredColumnException(String columnName){
        super(columnName);
    }

    @Override
    public XmlElement toXmlElement(){
        XmlElement xmlElement = new XmlElement("except");
        xmlElement.addAttribute(new Attribute("column",columnName));

        if(stringHasValue(configuredDelimitedColumnName)){
            xmlElement.addAttribute(new Attribute("delimitedColumnName",configuredDelimitedColumnName));
        }
        return xmlElement;
    }

    @Override
    public void validate(List<String> errors, String tableName){
        if(!stringHasValue(columnName)){
            errors.add(getString("ValidationError.26",tableName));
        }
    }
}
