package com.swf.mybatis.generator.config.xml;

import com.swf.mybatis.generator.codegen.XmlConstants;
import com.swf.mybatis.generator.config.Configuration;
import com.swf.mybatis.generator.exception.XMLParserException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class ConfigurationParser {

    private List<String> warnings;

    private List<String> parseErrors;

    private Properties extraProperties;

    public ConfigurationParser(List<String> warnings) {
        this(null, warnings);
    }

    public ConfigurationParser(Properties extraProperties, List<String> warnings) {
        super();
        this.extraProperties = extraProperties;

        if (warnings == null) {
            this.warnings = new ArrayList<>();
        } else {
            this.warnings = warnings;
        }

        parseErrors = new ArrayList<>();
    }

    public Configuration parseConfiguration(File inputFile) throws IOException, XMLParserException {
        FileReader fr = new FileReader(inputFile);
        return parseConfiguration(fr);
    }

    public Configuration parseConfiguration(Reader reader) throws IOException, XMLParserException {
        InputSource is = new InputSource(reader);
        return parseConfiguration(is);
    }

    public Configuration parseConfiguration(InputStream inputStream) throws IOException, XMLParserException {
        InputSource is = new InputSource(inputStream);
        return parseConfiguration(is);
    }

    private Configuration parseConfiguration(InputSource inputSource) throws IOException, XMLParserException {
        parseErrors.clear();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new ParserEntityResolver());

            ParserErrorHandler handler = new ParserErrorHandler(warnings, parseErrors);
            builder.setErrorHandler(handler);
            Document document = null;
            try {
                document = builder.parse(inputSource);
            } catch (SAXParseException e) {
                throw new XMLParserException(parseErrors);
            } catch (SAXException e) {
                if (e.getException() == null) {
                    parseErrors.add(e.getMessage());
                } else {
                    parseErrors.add(e.getException().getMessage());
                }
            }

            if (parseErrors.size() > 0) {
                throw new XMLParserException(parseErrors);
            }

            Configuration config;
            Element rootNode = document.getDocumentElement();
            DocumentType docType = document.getDoctype();
            if (rootNode.getNodeType() == Node.ELEMENT_NODE && docType.getPublicId().equals(XmlConstants.MYBATIS_GENERATOR_CONFIG_PUBLIC_ID)) {
                config = parseMyBatisGeneratorConfiguration(rootNode);
            } else {
                throw new XMLParserException(getString("RuntimeError.5"));
            }

            if (parseErrors.size() > 0) {
                throw new XMLParserException(parseErrors);
            }

            return config;
        } catch (Exception e) {
            // todo ParserConfigurationException change to Exception
            parseErrors.add(e.getMessage());
            throw new XMLParserException(parseErrors);
        }
    }

    private Configuration parseMyBatisGeneratorConfiguration(Element rootNode) throws XMLParseException {
        MyBatisGeneratorConfigurationParser parser = new MyBatisGeneratorConfigurationParser(extraProperties);
        return parser.parseConfiguration(rootNode);
    }
}
