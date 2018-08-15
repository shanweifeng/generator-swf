package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.Document;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;
import com.swf.mybatis.generator.codegen.XmlConstants;
import com.swf.mybatis.generator.exception.InvalidConfigurationException;

import java.util.ArrayList;
import java.util.List;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class Configuration {

    private List<Context> contexts;

    private List<String> classPathEntries;

    public Configuration() {
        super();
        contexts = new ArrayList<>();
        classPathEntries = new ArrayList<>();
    }

    public void addClasspathEntry(String entry) {
        classPathEntries.add(entry);
    }

    public List<String> getClassPathEntries() {return classPathEntries;}

    public void validate() throws InvalidConfigurationException {
        List<String> errors = new ArrayList<>();

        for (String classPathEntry : classPathEntries){
            if(!stringHasValue(classPathEntry)){
                errors.add(getString("ValidationError.19"));
                // only need to state this error once
                break;
            }
        }

        if (contexts.size() == 0) {
            errors.add(getString("ValidationError.11"));
        } else {
            for (Context context : contexts) {
                context.validate(errors);
            }
        }

        if (errors.size() > 0) {
            throw new InvalidConfigurationException(errors);
        }
    }

    public List<Context> getContexts() {
        return contexts;
    }

    public void addContext(Context context) {
        contexts.add(context);
    }

    public Context getContext(String id) {
        for (Context context : contexts) {
            if (id.equalsIgnoreCase(context.getId())) {
                return context;
            }
        }
        return null;
    }

    public Document toDocument() {
        Document document = new Document(XmlConstants.MYBATIS_GENERATOR_CONFIG_PUBLIC_ID,XmlConstants.MYBATIS_GENERATOR_CONFIG_SYSTEM_ID);
        XmlElement rootElement = new XmlElement("generatorConfiguration");
        document.setRootElement(rootElement);

        for (String classPathEntry : classPathEntries) {
            XmlElement cpeElement = new XmlElement("classPathEntry");
            cpeElement.addAttribute(new Attribute("location",classPathEntry));
            rootElement.addElement(cpeElement);
        }

        for (Context context : contexts) {
            rootElement.addElement(context.toXmlElement());
        }

        return document;
    }
}
