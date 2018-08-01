package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class SqlMapGeneratorConfiguration extends PropertyHolder {

    private String targetPackage;

    private String targetProject;

    public SqlMapGeneratorConfiguration(){super();}

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

    public XmlElement toXmlElement(){
        XmlElement answer = new XmlElement("sqlMapGenerator");

        if(targetPackage != null){
            answer.addAttribute(new Attribute("targetPackage",targetPackage));
        }

        if(targetProject != null){
            answer.addAttribute(new Attribute("targetProject",targetProject));
        }
        addPropertyXmlElements(answer);

        return answer;
    }

    public void validate(List<String> errors, String contentId){
        if(!stringHasValue(targetProject)){
            errors.add(getString("ValidationError.1",contentId));
        }

        if(!stringHasValue(targetPackage)){
            errors.add(getString("ValidationError.12","SQLMapGenerator",contentId));
        }
    }
}
