package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

public class CommentGeneratorConfiguration extends TypePropertyHolder{

    public CommentGeneratorConfiguration(){}

    public XmlElement toXmlElement(){
        XmlElement answer = new XmlElement("commentGenerator");
        if(getConfigurationType() != null){
            answer.addAttribute(new Attribute("type",getConfigurationType()));
        }
        addPropertyXmlElements(answer);
        return answer;
    }
}
