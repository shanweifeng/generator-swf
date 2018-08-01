package com.swf.mybatis.generator.internal.db;

public enum DatabaseDialects {
    DB2("VALUES IDENTITY_VAL_LOCAL()"), //$NON-NLS-1$
    MYSQL("SELECT LAST_INSERT_ID()"), //$NON-NLS-1$
    SQLSERVER("SELECT SCOPE_IDENTITY()"), //$NON-NLS-1$
    CLOUDSCAPE("VALUES IDENTITY_VAL_LOCAL()"), //$NON-NLS-1$
    DERBY("VALUES IDENTITY_VAL_LOCAL()"), //$NON-NLS-1$
    HSQLDB("CALL IDENTITY()"), //$NON-NLS-1$
    SYBASE("SELECT @@IDENTITY"), //$NON-NLS-1$
    DB2_MF("SELECT IDENTITY_VAL_LOCAL() FROM SYSIBM.SYSDUMMY1"), //$NON-NLS-1$
    INFORMIX("select dbinfo('sqlca.sqlerrd1') from systables where tabid=1"); //$NON-NLS-1$

    private String identityRetrievalStatement;

    private DatabaseDialects(String identityRetrievalStatement){
        this.identityRetrievalStatement = identityRetrievalStatement;
    }

    public String getIdentityRetrievalStatement() {
        return identityRetrievalStatement;
    }

    public static DatabaseDialects getDatabaseDialect(String database){
        DatabaseDialects returnValue = null;

        if("DB2".equalsIgnoreCase(database)){
            returnValue = DB2;
        }else if("MySQL".equalsIgnoreCase(database)){
            returnValue = MYSQL;
        }else if("SqlServer".equalsIgnoreCase(database)){
            returnValue = SQLSERVER;
        }else if("Cloudscape".equalsIgnoreCase(database)){
            returnValue = CLOUDSCAPE;
        }else if("DerBy".equalsIgnoreCase(database)){
            returnValue = DERBY;
        }else if("HSQLDB".equalsIgnoreCase(database)){
            returnValue = HSQLDB;
        }else if("SYBASE".equalsIgnoreCase(database)){
            returnValue = SYBASE;
        }else if("DB2_MF".equalsIgnoreCase(database)){
            returnValue = DB2_MF;
        }else if("Informix".equalsIgnoreCase(database)){
            returnValue = INFORMIX;
        }
        return returnValue;
    }
}
