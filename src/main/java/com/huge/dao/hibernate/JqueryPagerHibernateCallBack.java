package com.huge.dao.hibernate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.hibernate.HibernateUtil;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.SortOrderEnum;

public abstract class JqueryPagerHibernateCallBack
    implements HibernateCallback {

   // protected SessionFactory sessionFactory;

    JQueryPager paginatedList;

    Class object;

    public JqueryPagerHibernateCallBack( final JQueryPager paginatedList, final Class object ) {
        this.paginatedList = paginatedList;
        this.object = object;
    }

    public Object doInHibernate( Session session )
        throws HibernateException, SQLException {
       
      //TODO 加入实体执行回调
        session = ActionEntityHelper.getSession( session );
       // ActionEntityThreadLocalHolder.setActionEntityName( null );
        
        Criteria criteria = session.createCriteria( object );
        String orderBy = paginatedList.getSortCriterion();
        criteria.setFirstResult( paginatedList.getStart() );
        criteria.setMaxResults( paginatedList.getPageSize() );

        if ( orderBy != null ) {
        	String[] orderByArr = orderBy.split(",");
    		for(String ob : orderByArr){
    			String[] obArr = ob.trim().split(" ");
    			String sd = null;
    			if(obArr.length>1){
    				sd = obArr[1];
    			}
    			criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, obArr[0], CriteriaSpecification.LEFT_JOIN );
    			//sd = sd==null?sortDirection:sd;
                if ( paginatedList.getSortDirection() == SortOrderEnum.DESCENDING)
                    criteria.addOrder( Order.desc( obArr[0] ) );
                else
                    criteria.addOrder( Order.asc( obArr[0] ) );
    		}
        }

        criteria = getCustomCriterion( criteria );

        // for (int i = 0; i < list.length; i++) {
        //
        // criteria.add(list[i]);
        // }
        // criteria.add(getCustomCriterion());

        Map<String, Object> resultMap = new HashMap<String, Object>();
        List recList = criteria.list();
        resultMap.put( "list", recList );
        // int count = 0;
        // criteria.setProjection(Projections.rowCount());
        // List countList = criteria.list();
        // if (!countList.isEmpty())
        // count = ((Long) countList.get(0)).intValue();
        Integer count = getCriteriaCount( session );
        resultMap.put( "count", count );
        Map sumMap = paginatedList.getSumData();
        if ( sumMap != null && sumMap.size() > 0 ) {
            sumMap = this.getCriteriaSum( session, sumMap );
            resultMap.put( "sumData", sumMap );
        }
        return resultMap;
    }

    private Integer getCriteriaCount( Session session ) {
        int count = 0;
        Criteria criteria = session.createCriteria( object );
        criteria = getCustomCriterion( criteria );
        // for (int i = 0; i < list.length; i++) {
        // criteria.add(list[i]);
        // }
        criteria.setProjection( Projections.rowCount() );
        List countList = criteria.list();
        if ( !countList.isEmpty() )
            count = ( (Long) countList.get( 0 ) ).intValue();
        return new Integer( count );
    }

    private Map getCriteriaSum( Session session, Map map ) {
        Criteria criteria = session.createCriteria( object );
        criteria = getCustomCriterion( criteria );
        Set ks = map.keySet();
        Iterator itr = ks.iterator();
        while ( itr.hasNext() ) {
            String fN = (String) itr.next();
            criteria.setProjection( Projections.sum( fN ) );
            List countList = criteria.list();
            if ( !countList.isEmpty() ) {
                // count = ((Long) countList.get(0)).intValue();
                map.put( fN, countList.get( 0 ) );
            }
        }

        return map;
    }

    public abstract Criteria getCustomCriterion( Criteria criteria );

  
}
