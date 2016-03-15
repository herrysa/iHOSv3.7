package com.huge.dao.hibernate;

import org.hibernate.dialect.SQLServer2005Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;

public class SQLServerIhosDialect
    extends SQLServer2005Dialect {

    public SQLServerIhosDialect() {
        super();
        //this.registerFunction("", new StandardSQLFunction())
        registerFunction( "func_getpycodes", new StandardSQLFunction( "dbo.func_getpycodes" ) );
        registerFunction( "func_getUserCount", new StandardSQLFunction( "dbo.func_getUserCount" ) );

    }

}
