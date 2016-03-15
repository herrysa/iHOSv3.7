package com.huge.dao.hibernate;

import org.hibernate.EmptyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component( "entityExecInterceptor" )
public class EntityExecInterceptor
    extends EmptyInterceptor {
    public EntityExecInterceptor() {

    }

    public EntityExecInterceptor( String enSql ) {
        this.entitySql = enSql;
    }

    protected final transient Logger log = LoggerFactory.getLogger( getClass() );

    private String entitySql;

    // WebApplicationContextUtils

    @Override
    public String onPrepareStatement( String sql ) {
        int selIndex = sql.toLowerCase().indexOf( "select" );
        if ( selIndex == 0 ) {
            int whereindexB = sql.lastIndexOf( "WHERE" );
            int whereindexl = sql.lastIndexOf( "where" );

            if ( whereindexB < 0 && whereindexl < 0 ) {
                sql = sql + "  where " + entitySql;
            }
            else {
                sql = sql + " and " + entitySql;

            }
        }
        else {
            int first = sql.indexOf( "(" );
            int end = sql.lastIndexOf( ")" );
            String tsql = sql.substring( first + 1, end );

            int whereindexB = tsql.lastIndexOf( "WHERE" );
            int whereindexl = tsql.lastIndexOf( "where" );

            if ( whereindexB < 0 && whereindexl < 0 ) {
                tsql = tsql + "  where " + entitySql;
            }
            else {
                tsql = tsql + " and " + entitySql;

            }

            sql = sql.replace( sql.substring( first + 1, end ), tsql );
        }

        this.log.info( entitySql );
        this.log.info( sql );

        return super.onPrepareStatement( sql );
    }

    public String getEntitySql() {
        return entitySql;
    }

    public void setEntitySql( String entitySql ) {
        this.entitySql = entitySql;
    }

}
