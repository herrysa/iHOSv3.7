package com.huge.webapp.taglib.select;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.huge.ihos.system.reportManager.search.util.SearchUtils;
import com.huge.webapp.util.SpringContextHelper;

public class SqlSelect
    extends SingleSelect {

    /*	public SqlSelect(String[][] paramArrayOfString, String paramString1, String paramString2, boolean paramBoolean, String cssString) {
     super(paramArrayOfString, paramString1, paramString2, paramBoolean, cssString);
     }
    
     public SqlSelect(String[][] paramArrayOfString, String paramString1, String paramString2, boolean paramBoolean) {
     super(paramArrayOfString, paramString1, paramString2, paramBoolean);
     }*/

    public SqlSelect( String paraString, String name, String initValue, boolean required ) {
        super( to2Array( paraString ), name, initValue, required );
    }

    public static String[][] to2Array( String sqlString ) {

        JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" ) );

        sqlString = sqlString.replaceAll( "&#039;", "'" ).replaceAll( "&#034;", "'" );

        SearchUtils su = new SearchUtils();
        
        sqlString = su.realSQL( sqlString );
        
        
        
        SqlRowSet rs = jt.queryForRowSet( sqlString );
        ArrayList l1 = new ArrayList();
        ArrayList l2 = new ArrayList();
        while ( rs.next() ) {

            l1.add( rs.getString( 1 ) );
            l2.add( rs.getString( 2 ) );
        }

        String[][] arr = new String[l2.size()][2];
        for ( int i = 0; i < l1.size(); i++ ) {
            arr[i][0] = (String) l1.get( i );
            arr[i][1] = (String) l2.get( i );
        }
        return arr;
    }
}
