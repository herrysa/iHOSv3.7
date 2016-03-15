package com.huge.ihos.system.reportManager.search.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SQLQuery;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.system.reportManager.search.dao.QueryDao;
import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.util.SearchCriteria;
import com.huge.util.ReturnUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.SpringContextHelper;

@Repository( "queryDao" )
public class QueryDaoHibernate
    extends GenericDaoHibernate
    implements QueryDao {

    public QueryDaoHibernate() {
        super( Search.class );
    }

    public JQueryPager getQueryCriteria( JQueryPager paginatedList, SearchCriteria cri ) {
        try {

            if ( cri.getSourceSql().toUpperCase().trim().startsWith( "SELECT " ) ) {

                if ( paginatedList.getSortCriterion() != null ) {
                    cri.addSortNew( paginatedList.getSortCriterion() + " " + paginatedList.getSortDirection().toSqlString() );
                }

                HibernateCallback callback =
                    new QueryJqueryPagerHibernateNativeSqlCallBack( paginatedList, cri.getRealSql(), cri.getRealAgrs(), cri.getSumOpts() );
                Map<String, Object> resultMap = (Map<String, Object>) this.getHibernateTemplate().execute( callback );

                paginatedList.setList( (List) resultMap.get( "list" ) );
                int count = 0;
                Integer icount = (Integer) resultMap.get( "count" );
                if ( icount != null )
                    count = icount.intValue();
                paginatedList.setTotalNumberOfRows( count );
                if ( resultMap.get( "sumData" ) != null )
                    paginatedList.setInitSumData( (Map) resultMap.get( "sumData" ) );
                return paginatedList;
            }
            else {
            	Object[] args = cri.getRealAgrs();
            	//Object[] argsAdd = Arrays.copyOf(args, args.length+1);
            	//argsAdd[args.length] = "1 and "+cri.getExtraWhereSql();
                List l = this.callQuery( cri.getRealSql(), args );
                /*	String[] as =  {"201201","全部","全部"};
                	List l = this.callQuery(cri.getRealSql(),as);*/
                paginatedList.setList( l );
                paginatedList.setTotalNumberOfRows( l.size() );

                return paginatedList;
            }
        }
        catch ( Exception e ) {
            log.error( "getSearchCriteria", e );
            return paginatedList;
        }
    }

    public Map<String, Object> getExcelQueryCriteria( SearchCriteria cri ) {
        try {
            if ( cri.getSourceSql().toUpperCase().trim().startsWith( "SELECT " ) ) {
                HibernateCallback callback = new QueryHibernateNativeSqlCallBack( cri.getRealSql(), cri.getRealAgrs(), cri.getSumOpts() );
                Map<String, Object> resultMap = (Map<String, Object>) this.getHibernateTemplate().execute( callback );
                return resultMap;
            }
            else {
            	Object[] args = cri.getRealAgrs();
            	//Object[] argsAdd = Arrays.copyOf(args, args.length+1);
            	//argsAdd[args.length] = "1 and "+cri.getExtraWhereSql();
                List l = this.callQuery( cri.getRealSql(), args );
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put( "list", l );
                return resultMap;
            }
        }
        catch ( Exception e ) {
            log.error( "getSearchCriteria", e );
        }
        return null;
    }

    public String deletePublic( String tableName, String idName, String[] ids ) {
        String msg = "success";
        String sql = "delete from " + tableName + " where " + idName + "=?";
        try {

            if ( this.log.isInfoEnabled() ) {
                this.log.info( "public delete method,sql is : " + sql + "\n" + "parameter ids is: " );
                for ( int i = 0; i < ids.length; i++ ) {
                    this.log.info( ids[i] );
                }
            }

            SQLQuery sq = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery( sql );
            for ( int j = 0; j < ids.length; j++ ) {
                sq.setString( 0, ids[j] );
                sq.executeUpdate();
            }

        }
        catch ( DataAccessException ex ) {
            msg = ex.getMessage();
            throw new BusinessException( ex.getMessage() );
        }
        return msg;
    }

    private List getPageList( SearchCriteria sc ) {
        String sql = "";
        if ( sc != null && !sc.getSourceSql().isEmpty() && !sc.getExtraWhereSql().isEmpty() )
            sql = sc.getRealSql();
        else
            return null;
        DataSource ds = (DataSource) SpringContextHelper.getBean( "dataSource" );
        JdbcTemplate jt = new JdbcTemplate( ds );
        UpperCaseColumnMapRowMapper rm = new UpperCaseColumnMapRowMapper();
        List recList = jt.query( sql, rm );
        return recList;
    }

    public String[] queryTableColumn( String tableName ) {
        String sql =
            "SELECT dbo.syscolumns.name AS Column_name FROM dbo.syscolumns INNER JOIN dbo.sysobjects ON dbo.syscolumns.id = dbo.sysobjects.id WHERE dbo.sysobjects.name='"
                + tableName + "'and (dbo.sysobjects.xtype = 'u') AND (NOT (dbo.sysobjects.name LIKE 'dtproperties'))";
        List list = this.sessionFactory.getCurrentSession().createSQLQuery( sql ).list();
        String[] strs = new String[list.size()];
        for ( int i = 0; i < list.size(); i++ )
            strs[i] = (String) list.get( i );
        return strs;
    }

    public ReturnUtil processPublic( String taskName, Object[] args ) {
        String retMsg = "";
        int retCode = 0;
        ReturnUtil returnUtil = new ReturnUtil();
        try {
            if ( this.log.isInfoEnabled() ) {
                this.log.info( "public process method,task name is : " + taskName + "\n" + "parameter ids is: " );
                for ( int i = 0; i < args.length; i++ ) {
                    this.log.info( args[i] );
                }
            }

            StringBuffer sb = new StringBuffer();
            for ( int i = 0; i < args.length; i++ ) {
                sb.append( "?" );
                sb.append( "," );
            }
            String callString = "{call " + taskName + "(" + sb.toString() + "?,?)}";
            Connection con = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();

            CallableStatement callStat = null;
            callStat = con.prepareCall( callString );
            int j = 1;
            for ( int k = 0; k < args.length; k++ ) {
                callStat.setObject( j + 1, args[k] );
                j++;
            }
            callStat.registerOutParameter( 1, Types.INTEGER );
            callStat.registerOutParameter( j + 1, Types.VARCHAR );
            callStat.executeUpdate();
            retCode = callStat.getInt( 1 );
            retMsg = callStat.getString( j + 1 );
            returnUtil.setStatusCode(retCode);
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
            retMsg = ex.getMessage();
            returnUtil.setStatusCode(300);

        }
        if ( retMsg == null || retMsg.trim().equals( "" ) ) {
            if ( ( retCode != 100 ) && ( retCode != 0 ) )
                retMsg = "处理失败。";
            else
                retMsg = "处理成功。";
        }
        returnUtil.setMessage(retMsg);
        return returnUtil;

    }

    public String updateQueryRow( String tableName, String idName, String[] fieldNames, String[] values ) {
        String msg = "success";
        try {
            String str3 = "update " + tableName + " set ";
            for ( int i = 0; i < fieldNames.length; i++ )
                str3 = str3 + fieldNames[i] + "=?,";
            str3 = str3.substring( 0, str3.length() - 1 );
            str3 = str3 + " where " + idName + "=?";
            DataSource ds = (DataSource) SpringContextHelper.getBean( "dataSource" );
            JdbcTemplate jt = new JdbcTemplate( ds );
            jt.update( str3, values );
            return "success";
        }
        catch ( Exception e ) {
            msg = e.getMessage();
            throw new BusinessException( msg );
        }
        //return msg;
    }
    
    @Override
	public String updateQueryRow(String[] sqls) {
    	String msg = "success";
    	try {
			DataSource ds = (DataSource) SpringContextHelper.getBean( "dataSource" );
			JdbcTemplate jt = new JdbcTemplate( ds );
			jt.batchUpdate(sqls);
			return "success";
		} catch (DataAccessException e) {
			msg = e.getMessage();
            throw new BusinessException( msg );
		}
	}

    public String executeSqls( String sql ) {
        Connection conn = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
        try {
            conn.setAutoCommit( false );
            conn.prepareStatement( sql ).executeUpdate();
            conn.commit();
            return "success";
        }
        catch ( SQLException e ) {
            e.printStackTrace();
            throw new BusinessException( e.getMessage() );
        }
        //return "success";
    }

    private List callQuery( String taskName, Object[] args ) {
        String retMsg = "";
        int retCode = 0;
        ResultSet rs = null;
        try {
            if ( this.log.isInfoEnabled() ) {
                this.log.info( "调用存储过程进行数据查询，存储过程名称为 : " + taskName + "\n" + "传入参数为: " );
                for ( int i = 0; i < args.length; i++ ) {
                    this.log.info( args[i] );
                }
            }

            StringBuffer sb = new StringBuffer();
            for ( int i = 0; i < args.length; i++ ) {
                sb.append( "?" );
                sb.append( "," );
            }
            String callString = "{call " + taskName + "(" + sb.toString() + "?,?)}";
            Connection con = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();

            CallableStatement callStat = null;
            callStat = con.prepareCall( callString );
            int j = 1;
            for ( int k = 0; k < args.length; k++ ) {
                callStat.setObject( j + 1, args[k] );
                j++;
            }
            callStat.registerOutParameter( 1, Types.INTEGER );
            callStat.registerOutParameter( j + 1, Types.VARCHAR );
            //callStat.executeUpdate();

            callStat.executeQuery();
            rs = callStat.getResultSet();
            UpperCaseColumnMapRowMapper rm = new UpperCaseColumnMapRowMapper();
            ResultSetExtractor rse = new RowMapperResultSetExtractor( rm );
            return (List) rse.extractData( rs );

        }
        catch ( Exception ex ) {
            ex.printStackTrace();
            retMsg = ex.getMessage();
            return new ArrayList();

        }
        finally {

            JdbcUtils.closeResultSet( rs );
        }

    }

	

	
}
