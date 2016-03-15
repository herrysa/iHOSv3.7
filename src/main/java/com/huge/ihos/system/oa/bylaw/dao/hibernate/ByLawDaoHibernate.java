package com.huge.ihos.system.oa.bylaw.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.oa.bylaw.dao.ByLawDao;
import com.huge.ihos.system.oa.bylaw.model.ByLaw;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository( "byLawDao" )
public class ByLawDaoHibernate
    extends GenericDaoHibernate<ByLaw, Long>
    implements ByLawDao {

    public ByLawDaoHibernate() {
        super( ByLaw.class );
    }

    public JQueryPager getByLawCriteria( JQueryPager paginatedList, List<PropertyFilter> filters, String group_on ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                paginatedList.setSortCriterion( "byLawId" );
            Map<String, Object> resultMap = getAppManagerCriteriaWithSearch( paginatedList, ByLaw.class, filters, group_on );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getByLawCriteria", e );
            return paginatedList;
        }

    }

    public List<ByLaw> getByLawsByUser( User user ) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( ByLaw.class );
        criteria.addOrder( Order.desc( "createTime" ) );
        criteria.setFirstResult( 0 );
        criteria.setMaxResults( 5 );
        return criteria.list();
    }
}
