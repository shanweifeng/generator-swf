package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class JavaModelGeneratorConfiguration extends PropertyHolder {

    private String targetPackage;

    private String targetProject;

    public JavaModelGeneratorConfiguration(){super();}

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

    public XmlElement toXmlelement(){
        XmlElement anser = new XmlElement("javaModelGenerator");
        if(targetPackage != null){
            anser.addAttribute(new Attribute("targetPackage",targetPackage));
        }

        if(targetProject != null){
            anser.addAttribute(new Attribute("targetProject",targetProject));
        }
        addPropertyXmlElements(anser);
        return anser;
    }

    public void validate(List<String> errors, String contextId){
        if(!stringHasValue(targetProject)){
            errors.add(getString("ValidationError.0",contextId));
        }

        if(!stringHasValue(targetPackage)){
            errors.add(getString("ValidationError.12","JavaModelGenerator",contextId));
        }
    }
}
