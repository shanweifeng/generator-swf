package com.swf.mybatis.generator.internal.db;

import java.util.Objects;

import static com.swf.mybatis.generator.internal.util.StringUtility.composeFullyQualifiedTableName;

public class ActualTableName {

    private String tableName;

    private String catalog;

    private String schema;

    private String fullName;

    public ActualTableName(String catalog,String schema,String tableName){
        this.catalog = catalog;
        this.schema = schema;
        this.tableName = tableName;
        fullName = composeFullyQualifiedTableName(catalog,schema,tableName,'.');
    }

    public String getTableName() {
        return tableName;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getSchema() {
        return schema;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof ActualTableName)){
            return false;
        }
        return obj.toString().equals(this.toString());
    }

    @Override
    public int hashCode(){
        return fullName.hashCode();
    }

    @Override
    public String toString(){
        return fullName;
    }
}
