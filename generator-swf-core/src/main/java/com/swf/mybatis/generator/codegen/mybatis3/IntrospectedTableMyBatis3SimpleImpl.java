package com.swf.mybatis.generator.codegen.mybatis3;

import com.swf.mybatis.generator.api.ProgressCallback;
import com.swf.mybatis.generator.codegen.AbstractJavaClientGenerator;
import com.swf.mybatis.generator.codegen.AbstractJavaGenerator;
import com.swf.mybatis.generator.internal.ObjectFactory;

import java.util.List;

public class IntrospectedTableMyBatis3SimpleImpl implements IntrospectedTableMyBatis3Impl {

    public IntrospectedTableMyBatis3SimpleImpl() {
        super();
    }

    @Override
    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator,
                                               List<String> warnings, ProgressCallback progressCallback) {
        if (javaClientGenerator == null) {
            if (context.getSqlMapGeneratorConfiguration() != null) {
                xmlMapperGenerator = new SimpleXMLMapperGenerator();
            }
        } else {
            xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
        }
        initializeAbstractGenerator(xmlMapperGenerator, warnings, progressCallback);
    }

    @Override
    protected AbstractJavaClientGenerator createJavaClientGenerator() {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return null;
        }

        String type = context.getJavaClientGeneratorConfiguration().getConfigurationType();

        AbstractJavaClientGenerator javaGenerator;
        if ("XMLMapper".equalsIgnoreCase(type)) {
            javaGenerator = new SimpleJavaClientGenerator();
        } else if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) {
            javaGenerator = new SimpleAnnotatedClientGenerator();
        } else if ("MAPPER".equalsIgnoreCase(type)) {
            javaGenerator = new SimpleJavaClientGenerator();
        } else {
            javaGenerator = (AbstractJavaClientGenerator) ObjectFactory.createInternalObject(type);
        }
        return javaGenerator;
    }

    @Override
    protected void calculateJavaModelGenerators(List<String> warnings, ProgressCallback progressCallback) {
        AbstractJavaGenerator javaGenerator = new SimpleModelGenerator();
        initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        javaModelGenerators.add(javaGenerator);
    }
}
