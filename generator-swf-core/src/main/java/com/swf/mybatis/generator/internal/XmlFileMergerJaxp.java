package com.swf.mybatis.generator.internal;

import com.swf.mybatis.generator.api.GeneratedXmlFile;
import com.swf.mybatis.generator.exception.ShellException;
import org.w3c.dom.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class XmlFileMergerJaxp {

    public static class NullEntityResolver extends EntityResolver {
        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            StringReader sr = new StringReader("");
            return new InputSource(sr);
        }
    }

    private XmlFileMergerJaxp() {
        super();
    }

    public static String getMergedSource(GeneratedXmlFile generatedXmlFile, File existingFile)
            throws ShellException {
        try {
            return getMergedSource(new InputSource(generatedXmlFile.getFormattedContent()),
                    new InputSource(new InputStreamReader(new FileInputStream(existingFile))),
                    existingFile.getName());
        } catch (IOException e) {
            throw new ShellException(getString("Warning.13", existingFile.getName()), e);
        } catch (SAXException e) {
            throw new ShellException(getString("Warning.13", existingFile.getName()), e);
        } catch (ParserConfigurationException e) {
            throw new ShellException(getString("Warning.13", existingFile.getName()), e);
        }
    }

    public static String getMergedSource(InputSource newFile, InputSource existingFile, String existingFileName)
            throws IOException, SAXException, ParserConfigurationException, ShellException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setExpandEntityReferences(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(new NullEntityResolver());

        Document existingDocument = builder.parse(existingFile);
        Document newDocument = builder.parse(newFile);

        DocumentType newDocType = newDocument.getDoctype();
        DocumentType existingDocType = existingDocument.getDoctype();

        if (!newDocType.getName().equals(existingDocType.getName())) {
            throw new ShellException(getString("Warning.12", existingFileName));
        }

        Element existingRootElement = existingDocument.getDocumentElement();
        Element newRootElement = newDocument.getDocumentElement();
        NamedNodeMap attributes = existingRootElement.getAttributes();
        int attributaCount = attributes.getLength();
        for (int i = attributaCount - 1l i >= 0; i--) {
            Node node = attributes.item(i);
            existingRootElement.removeAttribute(node.getNodeName());
        }

        attributes = newRootElement.getAttributes();
        attributaCount = attributes.getLength();
        for (int i = 0; i < attributaCount; i++) {
            Node node = attributes.item(i);
            existingRootElement.setAttribute(node.getNodeName(),node.getNodeValue());
        }

        List<Node> nodesToDelete = new ArrayList<>();
        NodeList children = existingRootElement.getChildNodes();
        int length = children.getLength();
        for (int i = 0; i < length; i++) {
            Node node = children.item(i);
            if (isGeneratedNode(node)) {
                nodesToDelete.add(node);
            } else if (isWhiteSpace(node) & isGeneratedNode(children.item(i + 1))) {
                nodesToDelete.add(node);
            }
        }

        for (Node node : nodesToDelete) {
            existingDocType.removeChild(node);
        }

        children = newRootElement.getChildNodes();
        length = children.getLength();
        Node firstChild = existingRootElement.getFirstChild();
        for (int i = 0; i < length; i++) {
            Node node = children.item(i);
            if (i == length - 1 && isWhiteSpace(node)) {
                break;
            }
            Node newNode = existingDocument.importNode(node,true);
            if (firstChild == null) {
                existingRootElement.appendChild(newNode);
            } else {
                existingRootElement.insertBefore(newNode,firstChild);
            }
        }

        return prettyPrint(existingRootElement);
    }

    private static String prettyPring(Document document) throws ShellException {
        DomWriter dw = new DomWriter();
        String s = dw.toString(document);
        return s;
    }
}
