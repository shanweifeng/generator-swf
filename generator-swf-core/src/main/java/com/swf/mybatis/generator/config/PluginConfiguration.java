package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class PluginConfiguration extends TypePropertyHolder {
    public PluginConfiguration() {

    }

    public XmlElement toXmlElement() {
        XmlElement answer = new XmlElement("plugin");
        if(getConfigurationType() != null) {
            answer.addAttribute(new Attribute("type",getConfigurationType()));
        }

        addPropertyXmlElements(answer);
        return answer;
    }

    public void validate(List<String> errors, String contextId) {
        if (!stringHasValue(getConfigurationType())) {
            errors.add(getString("ValidationError.17", contextId));
        }
    }
}
