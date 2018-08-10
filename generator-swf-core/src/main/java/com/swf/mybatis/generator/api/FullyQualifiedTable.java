package com.swf.mybatis.generator.api;

import com.swf.mybatis.generator.config.Context;
import com.swf.mybatis.generator.config.DomainObjectRenamingRule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.swf.mybatis.generator.internal.util.EqualsUtil.areEqual;
import static com.swf.mybatis.generator.internal.util.HashCodeUtil.SEED;
import static com.swf.mybatis.generator.internal.util.HashCodeUtil.hash;
import static com.swf.mybatis.generator.internal.util.JavaBeansUtil.getCamelCaseString;
import static com.swf.mybatis.generator.internal.util.StringUtility.composeFullyQualifiedTableName;
import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class FullyQualifiedTable {
    private String introspectedCatalog;

    private String introspectedSchema;

    private String introspectedTableName;

    private String runtimeCatalog;

    private String runtimeSchema;

    private String runtimeTableName;

    private String domainObjectName;

    private String domainObjectSubPackage;

    private String alias;

    private boolean ignoreQualifiersAtRuntime;

    private String beginningDelimiter;

    private String endingDelimiter;

    private DomainObjectRenamingRule domainObjectRenamingRule;

    public FullyQualifiedTable(String introspectedCatalog, String introspectedSchema,
                               String introspectedTableName, String domainObjectName, String alias,
                               boolean ignoreQualifiersAtRuntime, String runtimeCatalog, String runtimeSchema,
                               String runtimeTableName, boolean delimitIdentifiers, DomainObjectRenamingRule domainObjectRenamingRule,
                               Context context) {
        super();
        this.introspectedCatalog = introspectedCatalog;
        this.introspectedSchema = introspectedSchema;
        this.introspectedTableName = introspectedTableName;
        this.ignoreQualifiersAtRuntime = ignoreQualifiersAtRuntime;
        this.runtimeCatalog = runtimeCatalog;
        this.runtimeSchema = runtimeSchema;
        this.runtimeTableName = runtimeTableName;
        this.domainObjectRenamingRule = domainObjectRenamingRule;

        if (stringHasValue(domainObjectName)) {
            int index = domainObjectName.lastIndexOf('.');
            if (index == -1) {
                this.domainObjectName = domainObjectName;
            } else {
                this.domainObjectName = domainObjectName.substring(index + 1);
                this.domainObjectSubPackage = domainObjectName.substring(0, index);
            }
        }

        if(alias == null) {
            this.alias = null;
        } else {
            this.alias = alias.trim();
        }

        beginningDelimiter = delimitIdentifiers ? context.getBeginningDelimiter() : "";
        endingDelimiter = delimitIdentifiers ? context.getEndingDelimiter() : "";
    }

    public String getIntrospectedCatalog() {
        return introspectedCatalog;
    }

    public String getIntrospectedSchema() {
        return introspectedSchema;
    }

    public String getIntrospectedTableName() {
        return introspectedTableName;
    }

    public String getFullyQualifiedTableNameAtRuntime() {
        StringBuilder localCatalog = new StringBuilder();
        if (!ignoreQualifiersAtRuntime) {
            if (stringHasValue(runtimeCatalog)) {
                localCatalog.append(runtimeCatalog);
            } else if (stringHasValue(introspectedCatalog)) {
                localCatalog.append(introspectedCatalog);
            }
        }

        if (localCatalog.length() > 0) {
            addDelimiters(localCatalog);
        }

        StringBuilder localSchema = new StringBuilder();
        if (!ignoreQualifiersAtRuntime) {
            if (stringHasValue(runtimeSchema)) {
                localSchema.append(runtimeSchema);
            } else if (stringHasValue(introspectedSchema)) {
                localSchema.append(introspectedSchema);
            }
        }
        if (localSchema.length() > 0) {
            addDelimiters(localSchema);
        }

        StringBuilder localTableName = new StringBuilder();
        if (stringHasValue(runtimeTableName)) {
            localTableName.append(runtimeTableName);
        } else {
            localTableName.append(introspectedTableName);
        }
        addDelimiters(localTableName);
        return composeFullyQualifiedTableName(localCatalog.toString(), localSchema.toString(), localTableName.toString(), '.');
    }

    public String getAliasedFullyQualifiedTableNameAtRuntime() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFullyQualifiedTableNameAtRuntime());
        if (stringHasValue(alias)) {
            sb.append(' ');
            sb.append(alias);
        }
        return sb.toString();
    }

    public String getDomainObjectName() {
        if (stringHasValue(domainObjectName)) {
            return domainObjectName;
        }

        String finalDomainObjectName;
        if (stringHasValue(runtimeTableName)) {
            finalDomainObjectName = getCamelCaseString(runtimeTableName, true);
        } else {
            finalDomainObjectName = getCamelCaseString(introspectedTableName, true);
        }

        if (domainObjectRenamingRule != null) {
            Pattern pattern = Pattern.compile(domainObjectRenamingRule.getSearchString());
            String replaceString = domainObjectRenamingRule.getReplaceString();
            replaceString  = replaceString == null ? "" : replaceString;
            Matcher matcher = pattern.matcher(finalDomainObjectName);
            finalDomainObjectName = getCamelCaseString(matcher.replaceAll(replaceString), true);
        }

        return finalDomainObjectName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof  FullyQualifiedTable)) {
            return false;
        }

        FullyQualifiedTable other = (FullyQualifiedTable) obj;

        return areEqual(this.introspectedTableName, other.introspectedTableName)
                && areEqual(this.introspectedCatalog, other.introspectedCatalog)
                && areEqual(this.introspectedSchema, other.introspectedSchema);
    }

    @Override
    public int hashCode() {
        int result = SEED;
        result = hash(result, introspectedTableName);
        result = hash(result, introspectedCatalog);
        result = hash(result, introspectedSchema);

        return result;
    }

    @Override
    public String toString() {
        return composeFullyQualifiedTableName(introspectedCatalog, introspectedSchema, introspectedTableName, '.');
    }

    public String getAlias() {
        return alias;
    }

    public String getSubPackageForClientOrSqlMap(boolean isSubPackagesEnabled) {
        StringBuilder sb = new StringBuilder();
        if (!ignoreQualifiersAtRuntime && isSubPackagesEnabled) {
            if (stringHasValue(runtimeCatalog)) {
                sb.append('.');
                sb.append(runtimeCatalog.toLowerCase());
            } else if (stringHasValue(introspectedCatalog)) {
                sb.append('.');
                sb.append(introspectedCatalog.toLowerCase());
            }

            if (stringHasValue(runtimeSchema)) {
                sb.append('.');
                sb.append(runtimeSchema.toLowerCase());
            } else if (stringHasValue(introspectedSchema)) {
                sb.append('.');
                sb.append(introspectedSchema.toLowerCase());
            }
        }
        // todo - strip characters that are not valid in package names
        return sb.toString();
    }

    public String getSubPackageForModel(boolean isSubPackagesEnabled) {
        StringBuilder sb = new StringBuilder();
        sb.append(getSubPackageForClientOrSqlMap(isSubPackagesEnabled));
        if (stringHasValue(domainObjectSubPackage)) {
            sb.append('.');
            sb.append(domainObjectSubPackage);
        }
        return sb.toString();
    }

    public void addDelimiters(StringBuilder sb) {
        if (stringHasValue(beginningDelimiter)) {
            sb.insert(0,beginningDelimiter);
        }

        if (stringHasValue(endingDelimiter)) {
            sb.append(endingDelimiter);
        }
    }

    public String getDomainObjectSubPackage() {
        return domainObjectSubPackage;
    }
}
