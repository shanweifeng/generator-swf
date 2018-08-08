package com.swf.mybatis.generator.internal;

import com.swf.mybatis.generator.api.GeneratedXmlFile;
import com.swf.mybatis.generator.config.MergeConstants;
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

    public static class NullEntityResolver implements EntityResolver {
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
        for (int i = attributaCount - 1; i >= 0; i--) {
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
        // pretty print the result
        return prettyPrint(existingDocument);
    }

    private static String prettyPrint(Document document) throws ShellException {
        DomWriter dw = new DomWriter();
        String s = dw.toString(document);
        return s;
    }

    private static boolean isGeneratedNode(Node node) {
        boolean rc = false;
        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String id = element.getAttribute("id");
            if (id != null) {
                for (String prefix : MergeConstants.OLD_XML_ELEMENT_PREFIXES) {
                    if (id.startsWith(prefix)) {
                        rc = true;
                        break;
                    }
                }
            }

            if (rc == false) {
                NodeList children = node.getChildNodes();
                int length = children.getLength();
                for (int i = 0; i < length; i++) {
                    Node childNode = children.item(i);
                    if (isWhiteSpace(childNode)) {
                        continue;
                    } else if (childNode.getNodeType() == Node.COMMENT_NODE) {
                        Comment comment = (Comment) childNode;
                        String commentData = comment.getData();
                        for (String tag : MergeConstants.OLD_ELEMENT_TAGS) {
                            if (commentData.contains(tag)) {
                                rc = true;
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return rc;
    }

    private static boolean isWhiteSpace(Node node) {
        boolean rc = false;
        if (node != null && node.getNodeType() == Node.TEXT_NODE) {
            Text tn = (Text) node;
            if (tn.getData().trim().length() == 0) {
                rc = true;
            }
        }
        return rc;
    }
}
