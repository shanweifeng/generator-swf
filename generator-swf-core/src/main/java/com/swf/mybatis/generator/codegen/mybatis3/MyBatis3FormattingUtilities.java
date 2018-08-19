package com.swf.mybatis.generator.codegen.mybatis3;

import com.swf.mybatis.generator.api.IntrospectedColumn;

import static com.swf.mybatis.generator.internal.util.StringUtility.escapeStringForJava;
import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class MyBatis3FormattingUtilities {

    private MyBatis3FormattingUtilities() {
        super();
    }

    public static String getParameterClause(IntrospectedColumn introspectedColumn) {
        return getParameterClause(introspectedColumn, null);
    }

    public static String getParameterClause(IntrospectedColumn introspectedColumn, String prefix) {
        StringBuilder sb = new StringBuilder();

        sb.append("#{");
        sb.append(introspectedColumn.getJavaProperty(prefix));
        sb.append(",jdbcType=");
        sb.append(introspectedColumn.getJdbcTypeName());

        if (stringHasValue(introspectedColumn.getTypeHandler())) {
            sb.append(",typeHandler=");
            sb.append(introspectedColumn.getTypeHandler());
        }

        sb.append('}');
        return sb.toString();
    }

    public static String getSelectListPhrase(IntrospectedColumn introspectedColumn) {
        if (stringHasValue(introspectedColumn.getTableAlias())) {
            StringBuilder sb = new StringBuilder();

            sb.append(getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" as ");
            if (introspectedColumn.isColumnNameDelimited()) {
                sb.append(introspectedColumn.getContext().getBeginningDelimiter());
            }

            sb.append(introspectedColumn.getTableAlias());
            sb.append('_');
            sb.append(escapeStringForMyBatis3(introspectedColumn.getActualColumnName()));
            if (introspectedColumn.isColumnNameDelimited()) {
                sb.append(introspectedColumn.getContext().getEndingDelimiter());
            }
            return sb.toString();
        } else {
            return getEscapedColumnName(introspectedColumn);
        }
    }

    public static String getEscapedColumnName(IntrospectedColumn introspectedColumn) {
        StringBuilder sb = new StringBuilder();
        sb.append(escapeStringForMyBatis3(introspectedColumn.getActualColumnName()));
        if (introspectedColumn.isColumnNameDelimited()) {
            sb.insert(0, introspectedColumn.getContext().getBeginningDelimiter());
            sb.append(introspectedColumn.getContext().getEndingDelimiter());
        }
        return sb.toString();
    }

    public static String getAliasedEscapedColumnName(IntrospectedColumn introspectedColumn) {
        if (stringHasValue(introspectedColumn.getTableAlias())) {
            StringBuilder sb = new StringBuilder();
            sb.append(introspectedColumn.getTableAlias());
            sb.append('.');
            sb.append(getEscapedColumnName(introspectedColumn));
            return sb.toString();
        } else {
            return getEscapedColumnName(introspectedColumn);
        }
    }

    public static String getAliasedActualColumnName(IntrospectedColumn introspectedColumn) {
        StringBuilder sb = new StringBuilder();
        if (stringHasValue(introspectedColumn.getTableAlias())) {
            sb.append(introspectedColumn.getTableAlias());
            sb.append('.');
        }

        if (introspectedColumn.isColumnNameDelimited()) {
            sb.append(escapeStringForJava(introspectedColumn.getContext().getBeginningDelimiter()));
        }

        sb.append(introspectedColumn.getActualColumnName());

        if (introspectedColumn.isColumnNameDelimited()) {
            sb.append(escapeStringForJava(introspectedColumn.getContext().getEndingDelimiter()));
        }

        return sb.toString();
    }

    public static String getRenamedColumnNameForResultMap(IntrospectedColumn introspectedColumn) {
        if (stringHasValue(introspectedColumn.getTableAlias())) {
            StringBuilder sb = new StringBuilder();
            sb.append(introspectedColumn.getTableAlias());
            sb.append('_');
            sb.append(introspectedColumn.getActualColumnName());
            return sb.toString();
        } else {
            return introspectedColumn.getActualColumnName();
        }
    }

    public static String escapeStringForMyBatis3(String s) {
        // nothing to do for MyBatis3 so far
        return s;
    }
}
