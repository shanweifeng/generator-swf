package com.swf.mybatis.generator.internal.types;

import com.swf.mybatis.generator.api.IntrospectedColumn;
import com.swf.mybatis.generator.api.JavaTypeResolver;
import com.swf.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import com.swf.mybatis.generator.config.Context;
import com.swf.mybatis.generator.config.PropertyRegistry;
import com.swf.mybatis.generator.internal.util.StringUtility;
import org.omg.PortableInterceptor.INACTIVE;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.*;

public class JavaTypeResolverDefaultImpl implements JavaTypeResolver {

    protected List<String> warnings;

    protected Properties properties;

    protected Context context;

    protected boolean forceBigDecimals;

    protected boolean useJSR310Types;

    protected Map<Integer,JdbcTypeInfomation> typeMap;

    private static final int TIME_WITH_TIMEZONE = 2013;

    private static final int TIMESTAMP_WITH_TIMEZONE = 2014;

    public JavaTypeResolverDefaultImpl(){
        super();
        properties = new Properties();
        typeMap = new HashMap<>();
        typeMap.put(Types.ARRAY,new JdbcTypeInfomation("ARRAY",new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.BIGINT,new JdbcTypeInfomation("BIGINT",new FullyQualifiedJavaType(Long.class.getName())));
        typeMap.put(Types.BINARY,new JdbcTypeInfomation("BINARY",new FullyQualifiedJavaType("byte[]")));
        typeMap.put(Types.BIT,new JdbcTypeInfomation("BIT",new FullyQualifiedJavaType(Boolean.class.getName())));
        typeMap.put(Types.BLOB,new JdbcTypeInfomation("BLOB",new FullyQualifiedJavaType("byte[]")));
        typeMap.put(Types.BOOLEAN,new JdbcTypeInfomation("BOOLEAN",new FullyQualifiedJavaType(Boolean.class.getName())));
        typeMap.put(Types.CHAR,new JdbcTypeInfomation("CHAR",new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.CLOB,new JdbcTypeInfomation("CLOB",new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.DATALINK,new JdbcTypeInfomation("DATALINK",new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.DATE,new JdbcTypeInfomation("DATE",new FullyQualifiedJavaType(Date.class.getName())));
        typeMap.put(Types.DECIMAL,new JdbcTypeInfomation("DECIMAL",new FullyQualifiedJavaType(BigDecimal.class.getName())));
        typeMap.put(Types.DISTINCT,new JdbcTypeInfomation("DISTINCT",new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.DOUBLE,new JdbcTypeInfomation("DOUBLE",new FullyQualifiedJavaType(Double.class.getName())));
        typeMap.put(Types.FLOAT,new JdbcTypeInfomation("FLOAT",new FullyQualifiedJavaType(Float.class.getName())));
        typeMap.put(Types.INTEGER,new JdbcTypeInfomation("INTEGER",new FullyQualifiedJavaType(Integer.class.getName())));
        typeMap.put(Types.JAVA_OBJECT,new JdbcTypeInfomation("JAVA_OBJECT",new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.LONGNVARCHAR,new JdbcTypeInfomation("LONGNVARCHAR",new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.LONGVARBINARY,new JdbcTypeInfomation("LONGVARBINARY",new FullyQualifiedJavaType("byte[]")));
        typeMap.put(Types.LONGVARCHAR,new JdbcTypeInfomation("LONGVARCHAR",new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.NCHAR,new JdbcTypeInfomation("NCHAR",new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.NCLOB,new JdbcTypeInfomation("NCLOB",new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.NVARCHAR,new JdbcTypeInfomation("NVARCHAR",new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.NULL,new JdbcTypeInfomation("NULL",new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.NUMERIC,new JdbcTypeInfomation("NUMERIC",new FullyQualifiedJavaType(BigDecimal.class.getName())));
        typeMap.put(Types.OTHER,new JdbcTypeInfomation("OTHER",new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.REAL,new JdbcTypeInfomation("REAL",new FullyQualifiedJavaType(Float.class.getName())));
        typeMap.put(Types.REF,new JdbcTypeInfomation("REF",new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.SMALLINT,new JdbcTypeInfomation("SMALLINT",new FullyQualifiedJavaType(Short.class.getName())));
        typeMap.put(Types.STRUCT,new JdbcTypeInfomation("STRUCT",new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.TIME,new JdbcTypeInfomation("TIME",new FullyQualifiedJavaType(Date.class.getName())));
        typeMap.put(Types.TIMESTAMP,new JdbcTypeInfomation("TIMESTAMP",new FullyQualifiedJavaType(Date.class.getName())));
        typeMap.put(Types.TINYINT,new JdbcTypeInfomation("TINYINT",new FullyQualifiedJavaType(Byte.class.getName())));
        typeMap.put(Types.VARBINARY,new JdbcTypeInfomation("VARBINARY",new FullyQualifiedJavaType("byte[]")));
        typeMap.put(Types.VARCHAR,new JdbcTypeInfomation("VARCHAR",new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(TIME_WITH_TIMEZONE,new JdbcTypeInfomation("TIME_WITH_TIMEZONE",new FullyQualifiedJavaType("java.time.OffsetTime")));
        typeMap.put(TIMESTAMP_WITH_TIMEZONE,new JdbcTypeInfomation("TIMESTAMP_WITH_TIMEZONE",new FullyQualifiedJavaType("java.time.OffsetDateTime")));
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        forceBigDecimals = StringUtility.isTrue(properties.getProperty(PropertyRegistry.TYPE_RESOLVER_FORCE_BIG_DECIMALS));
        useJSR310Types = StringUtility.isTrue(properties.getProperty(PropertyRegistry.TYPE_RESOLVER_USE_JSR310_TYPES));
    }

    @Override
    public void setContext(Context context) {

    }

    @Override
    public void setWarnings(List<String> wargings) {

    }

    @Override
    public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
        FullyQualifiedJavaType answer = null;
        JdbcTypeInfomation jdbcTypeInfomation = typeMap.get(introspectedColumn.getJdbcType());
        if(jdbcTypeInfomation != null){
            answer = jdbcTypeInfomation.getFullyQualifiedJavaType();
            answer = overrideDefaultType(introspectedColumn,answer);
        }
        return answer;
    }

    protected FullyQualifiedJavaType overrideDefaultType(IntrospectedColumn column,FullyQualifiedJavaType defaultType){
        FullyQualifiedJavaType answer = defaultType;
        switch (column.getJdbcType()){
            case Types.BIT:
                answer = calculateBitReplacement(column,defaultType);
                break;
                case:
        }
    }

    @Override
    public String calculateJdbcTypeName(IntrospectedColumn introspectedColumn) {
        return null;
    }

    public static class JdbcTypeInfomation{
        private String jdbcTypeName;

        private FullyQualifiedJavaType fullyQualifiedJavaType;

        public JdbcTypeInfomation(String jdbcTypeName,FullyQualifiedJavaType fullyQualifiedJavaType){
            this.jdbcTypeName = jdbcTypeName;
            this.fullyQualifiedJavaType = fullyQualifiedJavaType;
        }

        public String getJdbcTypeName() {
            return jdbcTypeName;
        }

        public FullyQualifiedJavaType getFullyQualifiedJavaType() {
            return fullyQualifiedJavaType;
        }
    }
}
