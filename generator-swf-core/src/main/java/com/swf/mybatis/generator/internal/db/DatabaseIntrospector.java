package com.swf.mybatis.generator.internal.db;

import com.swf.mybatis.generator.api.JavaTypeResolver;
import com.swf.mybatis.generator.config.Context;
import com.swf.mybatis.generator.logging.Log;
import com.swf.mybatis.generator.logging.LogFactory;

import java.sql.DatabaseMetaData;
import java.util.List;

public class DatabaseIntrospector {

    private DatabaseMetaData databaseMetaData;

    private JavaTypeResolver javaTypeResolver;

    private List<String> warnings;

    private Context context;

    private Log logger;

    public DatabaseIntrospector(Context context,DatabaseMetaData databaseMetaData, JavaTypeResolver javaTypeResolver, List<String> warnins) {
        super();
        this.context = context;
        this.databaseMetaData = databaseMetaData;
        this.javaTypeResolver = javaTypeResolver;
        this.warnings = warnins;
        logger = LogFactory.getLog(getClass());
    }

    private void calculatePrimaryKey(FullyQualifiedTable )
}
