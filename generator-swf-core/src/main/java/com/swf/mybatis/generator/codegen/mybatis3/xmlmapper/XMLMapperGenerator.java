package com.swf.mybatis.generator.codegen.mybatis3.xmlmapper;

import com.swf.mybatis.generator.api.FullyQualifiedTable;
import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.Document;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;
import com.swf.mybatis.generator.codegen.AbstractXmlGenerator;
import com.swf.mybatis.generator.codegen.XmlConstants;

import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class XMLMapperGenerator extends AbstractXmlGenerator {

    public XMLMapperGenerator() {
        super();
    }

    protected XmlElement getSqlMapElement() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString("Progress.12", table.toString()));

        XmlElement answer = new XmlElement("mapper");
        String namespace = introspectedTable.getMyBatis3SqlMapNamespace();
        answer.addAttribute(new Attribute("namespace", namespace));
        context.getCommentGenerator().addRootComment(answer);

        addResultMapWithoutBLOBsElement(answer);
        addResultMapWithBLOBsElement(answer);
        addExampleWhereClauseElement(answer);
        addMyBatis3UpdateByExampleWhereClauseElement(answer);
        addBaseColumnListElement(answer);
        addBlobColumnListElement(answer);
        addSelectByExampleWithBLOBsElement(answer);
        addSelectByExampleWithoutBLOBsElement(answer);
        addSelectByPrimaryKeyElement(answer);
        addDeleteByPrimaryKeyElement(answer);
        addDeleteByExampleElement(answer);
        addInsertElement(answer);
        addInsertSelectiveElement(answer);
        addCountByExampleElement(answer);
        addUpdateByExampleSelectiveElement(answer);
        addUpdateByExampleWithBLOBsElement(answer);
        addUpdateByExampleWithoutBLOBsElement(answer);
        addUpdateByPrimaryKeySelectiveElement(answer);
        addUpdateByPrimaryKeyWithBLOBsElement(answer);
        addUpdateByPrimaryKeyWithoutBLOBsElement(answer);

        return answer;
    }

    protected void addResultMapWithoutBLOBsElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateBaseResultMap()) {
            AbstractXmlElementGenerator elementGenerator = new ResultMapWithoutBLOBsElementGenerator(false);
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addResultMapWithBLOBsElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new ResultMapWithBLOBsElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addExampleWhereClauseElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateSQLExampleWhereClause()) {
            AbstractXmlElementGenerator elementGenerator = new ExampleWhereClauseElementGenerator(false);
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addMyBatis3UpdateByExampleWhereClauseElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateMyBatis3UpdateByExampleWhereClause()) {
            AbstractXmlElementGenerator elementGenerator = new ExampleWhereClauseElementGenerator(true);
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addBaseColumnListElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateBaseColumnList()) {
            AbstractXmlElementGenerator elementGenerator = new BaseColumnListElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addBlobColumnListElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateBlobColumnList()) {
            AbstraxtXmlElementGenerator elementGenerator = new BlobColumnListElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addSelectByExampleWithoutBLOBsElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateSelectByExampleWithoutBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new SelectByExampleWithoutBLOBsElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addSelectByExampleWithBLOBsElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateSelectByExampleWithBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new SelectByExampleWithBLOBsElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addSelectByPrimaryKeyElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractXmlElementGenerator elementGenerator = new SelectByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addDeleteByExampleElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateDeleteByExample()) {
            AbstractXmlElementGenerator elementGenerator = new DeleteByExampleElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addDeleteByPrimaryKeyElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateDeleteByPrimaryKey()) {
            AbstractXmlElementGenerator elementGenerator = new DeleteByPrimaryKeyElementGenerator(false);
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addInsertElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateInsert()) {
            AbstractXmlElementGenerator elementGenerator = new InsertElementGenerator(false);
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addInsertSelectiveElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateInsertSelective()) {
            AbstractXmlElementGenerator elementGenerator = new InsertSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addCountByExampleElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateCountByExample()) {
            AbstractXmlElementGenerator elementGenerator = new CountByExampleElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateByExampleSelectiveElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateUpdateByExampleSelective()) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByExampleSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateByExampleWithBLOBsElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateUpdateByExampleWithBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByExampleWithBLOBsElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateByExampleWithoutBLOBsElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateUpdateByExampleWithoutBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByExampleWithoutBLOBsElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateByPrimaryKeySelectiveElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeySelective()) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByPrimaryKeySelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateByPrimaryKeyWithBLOBsElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeyWithBLOBs()) {
            AbstractXmlelElementGenerator elementGenerator = new UpdateByPrimaryKeyWithBLOBsElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateByPrimaryKeyWithoutBLOBsElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeyWithoutBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByPrimaryKeyWithoutBLOBsElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void initializeAndExecuteGenerator(AbstractXmlElementGenerator elementGenerator, XmlElement parentElement) {
        elementGenerator.setContext(context);
        elementGenerator.setIntrospectedTable(introspectedTable);
        elementGenerator.setProgressCallback(progressCallback);
        elementGenerator.setWarnings(warnings);
        elementGenerator.addElements(parentElement);
    }

    @Override
    public Document getDocument() {
        Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID, XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
        document.setRootElement(getSqlMapElement());

        if (!context.getPlugins().sqlMapDocumentGenerated(document, introspectedTable)) {
            document = null;
        }
        return document;
    }
}
