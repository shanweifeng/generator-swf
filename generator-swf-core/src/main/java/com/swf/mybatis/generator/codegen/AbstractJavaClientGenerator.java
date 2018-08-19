package com.swf.mybatis.generator.codegen;

public abstract class AbstractJavaClientGenerator extends AbstractGenerator {

    private boolean requiresXMLGenerator;

    public AbstractJavaClientGenerator(boolean requiresXMLGenerator) {
        super();
        this.requiresXMLGenerator = requiresXMLGenerator;
    }

    public boolean requiresXMLGenerator() {
        return requiresXMLGenerator;
    }

    public abstract AbstractXmlGenerator getMatchedXMLGenerator();
}
