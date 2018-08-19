package com.swf.mybatis.generator.config.xml;

import com.swf.mybatis.generator.codegen.XmlConstants;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

public class ParserEntityResolver implements EntityResolver {

    public ParserEntityResolver() {
        super();
    }


    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        if (XmlConstants.MYBATIS_GENERATOR_CONFIG_PUBLIC_ID.equalsIgnoreCase(publicId)) {
            InputStream is = getClass().getClassLoader().getResourceAsStream("org/mybatis/generator/config/xml/mybatis-generator-config_1_0.dtd");
            InputSource ins = new InputSource(is);
            return ins;
        } else {
            return null;
        }
    }
}
