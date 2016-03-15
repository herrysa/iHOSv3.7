package com.huge.dao.hibernate;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.huge.ihos.system.configuration.bdinfo.util.BdInfoUtil;
import com.huge.ihos.system.reportManager.search.dao.hibernate.UpperAliasToEntityMapResultTransformer;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public class JqueryPagerHibernateBdinfoCallBack implements HibernateCallback{
	
	protected final Log log = LogFactory.getLog( getClass() );
	JQueryPager paginatedList;
	BdInfoUtil bdInfoUtil;
	List<PropertyFilter> filters;
	
	public JqueryPagerHibernateBdinfoCallBack( final JQueryPager paginatedList, final BdInfoUtil bdInfoUtil ,final List<PropertyFilter> filters) {
        this.paginatedList = paginatedList;
        this.bdInfoUtil = bdInfoUtil;
        this.filters = filters;
    }

    public Object doInHibernate( Session session )
        throws HibernateException, SQLException {
       
    	if ( this.log.isInfoEnabled() ) {
            this.log.info( "BdInfo querySQL : " + bdInfoUtil.bdInfo.getBdInfoName() );
        }
    	//BdInfoUtil bdInfoUtil = new  BdInfoUtil(bdInfo);
    	//bdInfoUtil.addFilters(filters);
    	String sql = bdInfoUtil.makeSQL();
    	List rsList = getPageList(session,sql,bdInfoUtil.getPrameterList());
    	rsList = bdInfoUtil.formatResult(rsList);
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put( "list", rsList );
        String countSql = bdInfoUtil.makeCountSQL();
        Integer count = getPageCount( session ,countSql ,bdInfoUtil.getPrameterList());
        resultMap.put( "count", count );
        return resultMap;
    }
    
    private List getPageList( Session session, String sql, List<Object> parameterList ) {
        Query q = session.createSQLQuery( sql );
        UpperAliasToEntityMapResultTransformer tran1 = UpperAliasToEntityMapResultTransformer.INSTANCE;
        q.setResultTransformer( tran1 );
        for(int p=0;p<parameterList.size();p++){
        	q.setParameter(p, parameterList.get(p));
        }
        //q.setProperties(parameterMap);
        q.setFirstResult(paginatedList.getStart());
        q.setMaxResults(paginatedList.getPageSize());
        return q.list();
    }
    
    private int getPageCount( Session session,String sql, List<Object> parameterList ) {
        if ( log.isInfoEnabled() ) {
            log.info( "countSQL:" + sql );
        }
        Query q = session.createSQLQuery( sql );
        for(int p=0;p<parameterList.size();p++){
        	q.setParameter(p, parameterList.get(p));
        }
        List countlist = q.list();
        int totalCount = 0;
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
