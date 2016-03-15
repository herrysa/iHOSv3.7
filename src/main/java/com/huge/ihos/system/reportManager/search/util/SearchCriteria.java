package com.huge.ihos.system.reportManager.search.util;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.huge.foundation.util.StringUtil;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.waf.util.Preference;

public class SearchCriteria
    implements Serializable {
    private int replaceWhereCount;

    private SearchOption[] sumOpts;

    public SearchOption[] getSumOpts() {
        return sumOpts;
    }

    public void setSumOpts( SearchOption[] sumOpts ) {
        this.sumOpts = sumOpts;
    }

    private List<SearchBinding> bindings = new ArrayList( 0 );

    private List sorts = new ArrayList();

    private List groups = new ArrayList();

    private String extraWhereSql = "";

    private String sourceSql = "";

    public String getSourceSql() {
        return sourceSql;
    }

    public void setSourceSql( String sourceSql ) {
        this.sourceSql = sourceSql;
    }

    public String getExtraWhereSql() {
        return extraWhereSql;
    }

    public void setExtraWhereSql( String extraWhereSql ) {
        this.extraWhereSql = extraWhereSql;
    }

    public void addBinding( SearchBinding binding ) {
        this.bindings.add( binding );
    }

    public void addBinding( SearchCriteria criteria ) {
        this.bindings.add( new SearchBinding( criteria ) );
    }

    public void addBinding( String field, Object searchValue, String htmlField ) {
        this.bindings.add( new SearchBinding( field, searchValue, htmlField ) );
    }

    public void addBinding( SearchBoolean searchBoolean, String field, SearchOperator opr, Object searchValue, String htmlField ) {
        this.bindings.add( new SearchBinding( searchBoolean, field, opr, searchValue, htmlField ) );
    }

    public void addBinding( SearchBoolean searchBoolean, String field, SearchOperator opr, Object searchValue, RSearchBracket rsb, String htmlField ) {
        this.bindings.add( new SearchBinding( searchBoolean, field, opr, searchValue, rsb, htmlField ) );
    }

    public void addBinding( SearchBoolean searchBoolean, LSearchBracket lsb, String field, SearchOperator opr, Object searchValue, String htmlField ) {
        this.bindings.add( new SearchBinding( searchBoolean, lsb, field, opr, searchValue, htmlField ) );
    }

    public void addBinding( SearchBoolean searchBoolean, LSearchBracket lsb, String field, SearchOperator operator, Object searchValue,
                            RSearchBracket rsb, String htmlField ) {
        this.bindings.add( new SearchBinding( searchBoolean, lsb, field, operator, searchValue, rsb, htmlField ) );
    }

    public void addSort( String sortField ) {
        if ( sortField != null )
            if ( Preference.isSQLServer() ) {
                if ( sortField.toUpperCase().startsWith( "A." ) )
                    this.sorts.add( sortField );
                else if ( sortField.indexOf( "." ) > 1 )
                    this.sorts.add( sortField.substring( sortField.indexOf( "." ) + 1 ) );
                else
                    this.sorts.add( sortField );
            }
            else if ( sortField.indexOf( "." ) > 1 )
                this.sorts.add( sortField.substring( sortField.indexOf( "." ) + 1 ) );
            else
                this.sorts.add( sortField );
    }

    public void addSorts( String[] sortFields ) {
        for ( int i = 0; i < sortFields.length; i++ ) {
            if ( sortFields[i] == null )
                continue;
            this.sorts.add( sortFields[i] );
        }
    }

    public void addSorts( Iterator itr ) {
        while ( itr.hasNext() )
            this.sorts.add( itr.next() );
    }

    public void addGroup( String groupField ) {
        if ( groupField != null )
            this.groups.add( groupField );
    }

    public void addGroups( String[] groupFields ) {
        for ( int i = 0; i < groupFields.length; i++ ) {
            if ( groupFields[i] == null )
                continue;
            this.groups.add( groupFields[i] );
        }
    }

    public void addGroups( Iterator itr ) {
        while ( itr.hasNext() )
            this.groups.add( itr.next() );
    }

    public Iterator bindings() {
        return this.bindings.iterator();
    }

    public Iterator sorts() {
        return this.sorts.iterator();
    }

    public Iterator groups() {
        return this.groups.iterator();
    }

    public String getWhere() {
        StringBuffer sb = null;
        Iterator itr = this.bindings.iterator();
        if ( !itr.hasNext() )
            return "";
        do {
            SearchBinding binding = (SearchBinding) itr.next();
            Object bindValue = binding.getValue();
            String str = binding.getField();
            if ( sb == null )
                sb = new StringBuffer();
            else
                sb.append( " " + binding.getBoolean().toString() + " " );
            sb.append( binding.getLBracket().toString() );
            if ( ( bindValue instanceof SearchCriteria ) ) {
                SearchCriteria criteria = (SearchCriteria) bindValue;
                sb.append( "(" );
                sb.append( criteria.getWhere() );
                sb.append( ")" );
            }
            else if ( binding.getOperator().equals( SearchOperator.LIKE ) ) {
                sb.append( str + " like '" + bindValue + "'" );
            }
            else {
                sb.append( str );
                sb.append( " " + binding.getOperator().toString() + "?" );
            }
            sb.append( binding.getRBracket().toString() );
        }
        while ( itr.hasNext() );
        if ( sb == null )
            return "";
        return sb.toString();
    }

    public int bind( PreparedStatement pst, int index )
        throws SQLException {
        Iterator itr = this.bindings.iterator();
        while ( itr.hasNext() ) {
            SearchBinding binding = (SearchBinding) itr.next();
            Object obj = binding.getValue();
            if ( ( obj instanceof SearchCriteria ) ) {
                SearchCriteria criteria = (SearchCriteria) obj;
                criteria.bind( pst, index );
            }
            else if ( !binding.getOperator().equals( SearchOperator.LIKE ) ) {
                pst.setObject( index, obj );
                index += 1;
            }
        }
        return index;
    }

    public String getOrder() {
        Iterator itr = this.sorts.iterator();
        StringBuffer sb = null;
        if ( !itr.hasNext() )
            return "";
        for ( int i = 0; itr.hasNext(); i++ ) {
            if ( sb == null )
                sb = new StringBuffer();
            else
                sb.append( "," );
            String str = (String) itr.next();
            sb.append( str );
        }
        return sb.toString();
    }

    public String getGroup() {
        Iterator itr = this.groups.iterator();
        StringBuffer sb = null;
        if ( !itr.hasNext() )
            return "";
        for ( int i = 0; itr.hasNext(); i++ ) {
            if ( sb == null )
                sb = new StringBuffer();
            else
                sb.append( "," );
            String str = (String) itr.next();
            sb.append( str );
        }
        return sb.toString();
    }

    public void addSortNew( String sort ) {
        clearSorts();
        addSort( sort );
    }

    public void addGroupNew( String group ) {
        clearGroups();
        if ( group != null )
            this.groups.add( group );
    }

    public void clearSorts() {
        this.sorts.clear();
    }

    public void clearGroups() {
        this.groups.clear();
    }

    public void clearBindings() {
        this.bindings.clear();
    }

    public void getWhereReplaceCount() {
        String REGEX = "%s";
        Pattern p = Pattern.compile( REGEX );
        // get a matcher object
        Matcher m = p.matcher( this.sourceSql );
        int count = 0;
        while ( m.find() ) {
            count++;
        }
        this.replaceWhereCount = count;
    }

    public String getRealSql() {
        if ( this.sourceSql.toUpperCase().trim().startsWith( "SELECT " ) ) {

            String str1 = this.getWhere();
            String str2;
            if ( this.sourceSql.indexOf( "%s" ) > 0 ) {
                if ( ( this.extraWhereSql != null ) && ( str1 != null ) && ( str1.trim().length() > 0 ) && ( this.extraWhereSql.length() > 0 ) )
                    str1 = str1 + " and ";
                if ( ( this.extraWhereSql != null ) && ( this.extraWhereSql.length() > 0 ) )
                    str1 = str1 + " (" + this.extraWhereSql + ")";
                if ( ( str1.length() == 0 ) && ( this.extraWhereSql.length() == 0 ) )
                    str1 = " 1=1 ";
                this.sourceSql = StringUtil.replaceString( this.sourceSql, "%s", "(" + str1 + ")" );
            }
            else {
                if ( ( str1 != null ) && ( str1.length() > 0 ) )
                    this.sourceSql = this.sourceSql + " (" + str1 + ") ";
                if ( ( this.extraWhereSql != null ) && ( str1 != null ) && ( str1.trim().length() > 0 ) && ( this.extraWhereSql.length() > 0 ) )
                    this.sourceSql = this.sourceSql + " and ";
                if ( ( this.extraWhereSql != null ) && ( this.extraWhereSql.length() > 0 ) )
                    this.sourceSql = this.sourceSql + " (" + this.extraWhereSql + ")";
                if ( ( str1.length() == 0 ) && ( this.extraWhereSql.length() == 0 ) )
                    this.sourceSql = this.sourceSql + " 1=1 ";
                str2 = this.getGroup();
                if ( ( str2 != null ) && ( !str2.equals( "null" ) ) && ( str2.length() > 0 ) )
                    this.sourceSql = this.sourceSql + " GROUP BY " + str2;
            }
            if ( true ) {
                str2 = this.getOrder();
                if ( ( str2 != null ) && ( !str2.equals( "null" ) ) && ( str2.length() > 0 ) ) {

                    StringBuilder sb = new StringBuilder( this.sourceSql.trim().toLowerCase() );

                    int orderByIndex = sb.indexOf( "order by" );
                    if ( orderByIndex > 0 ) {
                        CharSequence orderby = sb.subSequence( orderByIndex, sb.length() );

                        // Delete the order by clause at the end of the query

                        sb.delete( orderByIndex, orderByIndex + orderby.length() );
                    }

                    this.sourceSql = sb.toString();
                    this.sourceSql = this.sourceSql + " ORDER BY " + str2;

                }
            }

            SearchUtils su = new SearchUtils();

            sourceSql = su.realSQL( sourceSql );
            return this.sourceSql;
        }
        else {
            return this.sourceSql;
        }
    }

    public Object[] getRealAgrs() {
        List list = new ArrayList();
        Iterator itr = bindings.iterator();
        while ( itr.hasNext() ) {
            SearchBinding b = (SearchBinding) itr.next();
            if ( !b.getOperator().equals( SearchOperator.LIKE ) )
                list.add( b.getValue() );
        }

        List realList = new ArrayList();
        if ( this.sourceSql.toUpperCase().trim().startsWith( "SELECT " ) ) {
            for ( int i = 0; i < this.replaceWhereCount; i++ ) {
                realList.addAll( list );
            }
        }
        else {
            realList = list;
        }

        return realList.toArray();
    }

    /*
     * public static String getRealSQL(String sqlString, SearchCriteria
     * criteria, String whereString, boolean needOrder) { String str1 =
     * criteria.getWhere(); String str2; if (sqlString.indexOf("%s") > 0) { if
     * ((whereString != null) && (str1 != null) && (str1.trim().length() > 0) &&
     * (whereString.length() > 0)) str1 = str1 + " and "; if ((whereString !=
     * null) && (whereString.length() > 0)) str1 = str1 + " (" + whereString +
     * ")"; if ((str1.length() == 0) && (whereString.length() == 0)) str1 =
     * " 1=1 "; sqlString = StringUtil.replaceString(sqlString, "%s", "(" + str1
     * + ")"); } else { if ((str1 != null) && (str1.length() > 0)) sqlString =
     * sqlString + " (" + str1 + ") "; if ((whereString != null) && (str1 !=
     * null) && (str1.trim().length() > 0) && (whereString.length() > 0))
     * sqlString = sqlString + " and "; if ((whereString != null) &&
     * (whereString.length() > 0)) sqlString = sqlString + " (" + whereString +
     * ")"; if ((str1.length() == 0) && (whereString.length() == 0)) sqlString =
     * sqlString + " 1=1 "; str2 = criteria.getGroup(); if ((str2 != null) &&
     * (!str2.equals("null")) && (str2.length() > 0)) sqlString = sqlString +
     * " GROUP BY " + str2; } if (needOrder) { str2 = criteria.getOrder(); if
     * ((str2 != null) && (!str2.equals("null")) && (str2.length() > 0))
     * sqlString = sqlString + " ORDER BY " + str2; } return sqlString; }
     * 
     * public static String getRealSQL(String sqlString, SearchCriteria
     * criteria, String whereString) { return getRealSQL(sqlString, criteria,
     * whereString, true); }
     */
    public static void main( String[] paramArrayOfString ) { /*
                                                              * //int count; String str = "%s a.deptcode %s  dfasdfasdfas %s"; String
                                                              * REGEX ="%s"; Pattern p = Pattern.compile(REGEX); // get a matcher object
                                                              * Matcher m = p.matcher(str); int count = 0; while(m.find()) { count++;
                                                              * System.out.println("Match number " + count);
                                                              * System.out.println("start(): " + m.start()); System.out.println("end(): "
                                                              * + m.end()); }
                                                              */
        /*
         * int lastIndex = -2; int count =0;
         * 
         * while(lastIndex != -1){
         * 
         * lastIndex = str.indexOf("%s",lastIndex);
         * 
         * if( lastIndex != -1){ count ++; } }
         */

        List list = new ArrayList();
        list.add( 1 );
        list.add( 2 );

        List listreal = new ArrayList();
        listreal.addAll( list );
        //listreal.addAll(list);

        System.out.println( listreal.size() );
        // System.out.println(str.substring(str.indexOf(".") + 1));
    }
}
