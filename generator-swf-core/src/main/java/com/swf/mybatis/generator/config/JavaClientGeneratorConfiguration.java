package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class JavaClientGeneratorConfiguration extends TypePropertyHolder{

    private String targetPackage;

    private String implementationPackage;

    private String targetProject;

    public JavaClientGeneratorConfiguration(){super();}

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }

    public String getImplementationPackage() {
        return implementationPackage;
    }

    public void setImplementationPackage(String implementationPackage) {
        this.implementationPackage = implementationPackage;
    }

    public XmlElement toXmlElement(){
        XmlElement answer = new XmlElement("javaClientGenerator");

        if(getConfigurationType() != null){
            answer.addAttribute(new Attribute("type",getConfigurationType()));
        }

        if(targetPackage != null){
            answer.addAttribute(new Attribute("targetPackage",targetPackage));
        }

        if(targetProject != null){
            answer.addAttribute(new Attribute("targetProject",targetProject));
        }

        if(implementationPackage != null){
            answer.addAttribute(new Attribute("implementationPackage",targetProject));
        }

        addPropertyXmlElements(answer);
        return answer;
    }

    public void validate(List<String> errors, String contextId){
        if(!stringHasValue(targetProject)){
            errors.add(getString("ValidationError.2",contextId));
        }

        if(!stringHasValue(targetPackage)){
            errors.add(getString("ValidationError.12",contextId));
        }

        if(!stringHasValue(getConfigurationType())){
            errors.add(getString("ValidationError.20",contextId));
        }
    }
}
