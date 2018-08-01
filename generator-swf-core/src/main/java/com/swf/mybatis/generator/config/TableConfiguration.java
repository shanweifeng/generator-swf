package com.swf.mybatis.generator.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableConfiguration extends PropertyHolder {
    private boolean insertStatementEnabled;

    private boolean selectByPrimaryKeyStatementEnabled;

    private boolean selectByExamplStatementEnabled;

    private boolean updateByPrimaryKeyStatementEnabled;

    private boolean deleteByPrimaryKeyStatementEnabled;

    private boolean deleteByExampleStatementEnabled;

    private boolean countByExampleStatementEnabled;

    private boolean updateByExampleStatementEnabled;

    private List<ColumnOverride> columnOverrides;

    private Map<IgnoredColumn,Boolean> ignoredColumn;

    private GeneratedKey generatedKey;

    private String selectByPrimaryKeyQueryId;

    private String selectByExampleQueryId;

    private String catalog;

    private String schema;

    private String tableName;

    private String domainObjectName;

    private String alias;

    private  ModelType modelType;

    private boolean wildcardExcapingEnabled;

    private String configuredModelType;

    private boolean delimitIdentifiers;

    private DomainObjectRenamingRule domainObjectRenamingRule;

    private ColumnRenamingRule columnRenamingRule;

    private boolean isAllColumnDelimitingEnabled;

    private String mapperName;

    private String sqlProviderName;

    private List<IgnoredColumnPattern> ignoredColumnPatterns = new ArrayList<>();

    public TableConfiguration(Context context){

    }
}
