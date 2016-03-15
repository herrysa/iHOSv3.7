package com.huge.ihos.system.reportManager.search.dao.hibernate;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.RowSelection;
import org.hibernate.impl.SessionFactoryImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.SpringContextHelper;

public class QueryJqueryPagerHibernateNativeSqlCallBack
    implements HibernateCallback {
    protected final Log log = LogFactory.getLog( getClass() );

    JQueryPager paginatedList;

    String querySql;

    Object[] queryArgs;

    private SearchOption[] sumOpts;

    /*
     * public SearchOption[] getSumOpts() { return sumOpts; }
     * 
     * public void setSumOpts(SearchOption[] sumOpts) { this.sumOpts = sumOpts;
     * }
     */
    public QueryJqueryPagerHibernateNativeSqlCallBack( JQueryPager paginatedList, String sql, Object[] args, SearchOption[] sumOpts ) {
        this.paginatedList = paginatedList;
        this.querySql = sql;
        this.queryArgs = args;
        this.sumOpts = sumOpts;
    }

    public Object doInHibernate( Session session )
        throws HibernateException, SQLException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //
        // Query q = session.createSQLQuery(querySql);
        // AliasToEntityMapResultTransformer tran1 =
        // AliasToEntityMapResultTransformer.INSTANCE;
        // q.setResultTransformer(tran1);
        //
        if ( this.log.isInfoEnabled() ) {
            this.log.info( "public query method,sql is : " + querySql + "\n" + "parameter ids is: " );
            for ( int i = 0; i < queryArgs.length; i++ ) {
                this.log.info( queryArgs[i] );
            }
        }
        List recList = getPageList( session, paginatedList, querySql, queryArgs );
        resultMap.put( "list", recList );

        Integer count = getPageCount( session, paginatedList, querySql, queryArgs );
        resultMap.put( "count", count );

        if ( this.sumOpts != null && this.sumOpts.length > 0 ) {
            Map sumData = this.getSumData( session, paginatedList, querySql, queryArgs, sumOpts );
            resultMap.put( "sumData", sumData );
        }

        return resultMap;
    }

    private Map getSumData( Session session, JQueryPager paginatedList, String sql, Object[] args, SearchOption[] sumOpts ) {
        Map sumd = new HashMap();
        for ( int i = 0; i < sumOpts.length; i++ ) {
            SearchOption so = sumOpts[i];

            String sumQueryString = " select sum(" + so.getFieldName() + ") as cnt " + removeSelect( removeOrders( sql ) );
            if ( log.isInfoEnabled() ) {
                log.info( "sumQueryString:" + sumQueryString );
            }
            Query q = session.createSQLQuery( sumQueryString );
            q = this.setQueryArgs( q, args );
            List countlist = q.list();
            Object obj = countlist.get( 0 );
            sumd.put( so.getFieldName(), obj );
        }

        return sumd;
    }

    /**
     * order之类的不在这里处理，sql语句应该在传到这里之前就已经处理完了。
     * 
     * @param session
     * @param paginatedList
     * @param sql
     * @param args
     * @return
     */
    private List getPageList( Session session, JQueryPager paginatedList, String sql, Object[] args ) {

        Query q = session.createSQLQuery( querySql );
        UpperAliasToEntityMapResultTransformer tran1 = UpperAliasToEntityMapResultTransformer.INSTANCE;

        q.setResultTransformer( tran1 );
        q.setFirstResult( paginatedList.getStart() );
        q.setMaxResults( paginatedList.getPageSize() );
        q = this.setQueryArgs( q, args );

        String qs = q.getQueryString();
        // SessionImpl si = (SessionImpl)session;
        SessionFactoryImpl sf = (SessionFactoryImpl) session.getSessionFactory();
        final Dialect dialect = sf.getDialect();
        // final Dialect dialect =
        // ((SessionImpl)session).getFactory().getDialect();
        /*
         * String dialect = ((SessionImpl);
         * getSession(););.getFactory();.getDialect(); .getClass();.getName();;
         */

        String limsql = dialect.getLimitString( sql.trim(), // use of trim() here is ugly?
                                                paginatedList.getStart(), paginatedList.getPageSize() );

        if ( this.log.isInfoEnabled() )
            log.info( limsql );

        RowSelection selection = new RowSelection();
        selection.setFirstRow( paginatedList.getStart() );
        selection.setMaxRows( paginatedList.getPageSize() );

        /*
         * ArrayList al = new ArrayList(); for (int i = 0; i < args.length; i++)
         * { al.add(args[i]); } if(paginatedList.getStart()<1){ //al.add(0);
         * al.add(paginatedList.getPageSize()); }else{
         * al.add(paginatedList.getStart()+1); al.add(paginatedList.getStart() +
         * paginatedList.getPageSize()+1); } args = al.toArray();
         */
        args = prepareSqlArgs( dialect, args, selection );
        DataSource ds = (DataSource) SpringContextHelper.getBean( "dataSource" );
        JdbcTemplate jt = new JdbcTemplate( ds );
        // List recList = q.list();
        UpperCaseColumnMapRowMapper rm = new UpperCaseColumnMapRowMapper();
        List recList = jt.query( limsql, args, rm );

        // List recList = q.list();
        return recList;
    }

    private List getPageList( Session session, String sql, Object[] args ) {
        Query q = session.createSQLQuery( querySql );
        UpperAliasToEntityMapResultTransformer tran1 = UpperAliasToEntityMapResultTransformer.INSTANCE;
        q.setResultTransformer( tran1 );
        q = this.setQueryArgs( q, args );
        String qs = q.getQueryString();
        SessionFactoryImpl sf = (SessionFactoryImpl) session.getSessionFactory();
        final Dialect dialect = sf.getDialect();
        DataSource ds = (DataSource) SpringContextHelper.getBean( "dataSource" );
        JdbcTemplate jt = new JdbcTemplate( ds );
        UpperCaseColumnMapRowMapper rm = new UpperCaseColumnMapRowMapper();
        List recList = jt.query( sql, args, rm );
        return recList;
    }

    private Object[] prepareSqlArgs( final Dialect dialect, Object[] srcArgs, final RowSelection selection ) {

        int firstRow = dialect.convertToFirstRowValue( getFirstRow( selection ) );
        int lastRow = getMaxOrLimit( selection, dialect );
        boolean hasFirstRow = dialect.supportsLimitOffset() && ( firstRow > 0 || dialect.forceLimitUsage() );
        boolean reverse = dialect.bindLimitParametersInReverseOrder();

        int limitArgNum = hasFirstRow ? 2 : 1;
        int firstRowIndex = 1 + ( reverse ? 1 : 0 );
        int lastRowIndex = 1 + ( reverse || !hasFirstRow ? 0 : 1 );

        if ( hasFirstRow ) {
            srcArgs = insertArgs( srcArgs, firstRowIndex - 1, firstRow );

            // statement.setInt( index + ( reverse ? 1 : 0 ), firstRow );
        }
        // statement.setInt( index + ( reverse || !hasFirstRow ? 0 : 1 ),
        // lastRow );
        // return hasFirstRow ? 2 : 1;
        srcArgs = insertArgs( srcArgs, lastRowIndex - 1, lastRow );
        return srcArgs;
    }

    private static int getFirstRow( RowSelection selection ) {
        if ( selection == null || selection.getFirstRow() == null ) {
            return 0;
        }
        else {
            return selection.getFirstRow().intValue();
        }
    }

    static Object[] insertArgs( Object[] srcArgs, int pos, Object arg ) {

        /*
         * if(srcArgs.length==0) { srcArgs = new Object[1]; srcArgs[0]=arg;
         * return srcArgs; }else{
         * //srcArgs=Arrays.copyOf(srcArgs,srcArgs.length+1); Object[] newArgs =
         * new Object[srcArgs.length+1]; System.arraycopy(srcArgs, 0, newArgs,
         * 0, srcArgs.length); }
         */

        Object[] newArgs = new Object[srcArgs.length + 1];
        System.arraycopy( srcArgs, 0, newArgs, 0, srcArgs.length );

        /*for (int i = srcArgs.length; i > pos; i--)
        {
        	newArgs[i] = newArgs[i - 1];
        }*/
        newArgs[srcArgs.length] = arg;
        return newArgs;
        /*
         * if(sum == a.length) { // 扩充数组空间 a=Arrays.copyOf(a,a.length*2); }
         * for(int i=sum;i>pos;i--) { a[i]=a[i-1]; } a[pos]=num; sum++; //有效位数加1
         */}

    private static int getMaxOrLimit( final RowSelection selection, final Dialect dialect ) {
        final int firstRow = dialect.convertToFirstRowValue( getFirstRow( selection ) );
        final int lastRow = selection.getMaxRows().intValue();
        if ( dialect.useMaxForLimit() ) {
            return lastRow + firstRow;
        }
        else {
            return lastRow;
        }
    }

    private int getPageCount( Session session, JQueryPager paginatedList, String sql, Object[] args ) {
        String countQueryString = " select count(*) as cnt " + removeSelect( removeOrders( sql ) );
        if ( log.isInfoEnabled() ) {
            log.info( "countQueryString:" + countQueryString );
        }
        Query q = session.createSQLQuery( countQueryString );
        q = this.setQueryArgs( q, args );

        List countlist = q.list();
        int totalCount = 0;
        if(countlist.size()==0){
        	return totalCount;
        }else if(countlist.size()>1){
        	totalCount = countlist.size();
        	return totalCount;
        }else{
	        Object obj = countlist.get( 0 );
	        if ( obj instanceof String ) {
	            totalCount = Integer.parseInt( (String) obj );
	        }
	        if ( obj instanceof Integer ) {
	            totalCount = (Integer) obj;
	        }
	        if ( obj instanceof Long ) {
	            totalCount = Integer.parseInt( "" + obj );
	        }
	        if ( obj instanceof Map ) {
	            totalCount = Integer.parseInt( "" + ( (Map) obj ).get( "cnt" ) );
	        }
	        if ( obj instanceof BigInteger ) {
	            totalCount = ( (BigInteger) obj ).intValue();
	        }
	        return totalCount;
        }
    }

    private static String removeOrders( String hql ) {
        Pattern p = Pattern.compile( "order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE );
        Matcher m = p.matcher( hql );
        StringBuffer sb = new StringBuffer();
        while ( m.find() ) {
            m.appendReplacement( sb, "" );
        }
        m.appendTail( sb );
        return sb.toString();
    }
    
    private static String removeGroupBys( String hql ) {
        Pattern p = Pattern.compile( "group\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE );
        Matcher m = p.matcher( hql );
        StringBuffer sb = new StringBuffer();
        while ( m.find() ) {
            m.appendReplacement( sb, "" );
        }
        m.appendTail( sb );
        return sb.toString();
    }

    private static String removeSelect( final String hql ) {
        final int beginPos = hql.toLowerCase().indexOf( "from" );
        return hql.substring( beginPos );
    }

    private Query setQueryArgs( Query query, Object[] args ) {
        if ( args != null )
            for ( int i = 0; i < args.length; i++ )
                query.setParameter( i, args[i] );
        return query;
    }

    public static void main( String[] args ) {
        /*Integer[] ia = { 1, 3, 5, 7, 9 };

        Object[] nia = insertArgs( ia, 1, 20 );
        System.out.print( "afd" );*/
    	
    	String sql = "select count(*) as cnt from v_sourcecost  where  ( 1=1 ) group by deptid,internalcode,deptname";
    	sql = removeGroupBys(sql);
    	System.out.println(sql);
    }
}
