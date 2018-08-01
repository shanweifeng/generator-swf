package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

public class JavaTypeResolverConfiguration extends TypePropertyHolder {
    public JavaTypeResolverConfiguration(){super();}

    public XmlElement toXmlElement(){
        XmlElement answer = new XmlElement("javaTypeResolver");
        if(getConfigurationType() != null){
            answer.addAttribute(new Attribute("type",getConfigurationType()));
        }

        addPropertyXmlElements(answer);
        return answer;
    }
}
