package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class ConnectionFactoryConfiguration extends TypePropertyHolder {

    public ConnectionFactoryConfiguration(){super();}

    public void validate(List<String> errors){
        if(getConfigurationType() == null || "DEFAULT".equals(getConfigurationType())){
            if(!stringHasValue(getProperty("driverClass"))){
                errors.add(getString("ValidationError.18","connectionFactory","driverClass"));
            }

            if(!stringHasValue(getProperty("connectionURl"))){
                errors.add(getString("ValidationError.18","connectionFactory","connectionURl"));
            }
        }
    }

    public XmlElement toXmlElement(){
        XmlElement xmlElement = new XmlElement("connectionFactory");

        if(stringHasValue(getConfigurationType())){
            xmlElement.addAttribute(new Attribute("type",getConfigurationType()));
        }
        addPropertyXmlElements(xmlElement);
        return xmlElement;
    }
}
