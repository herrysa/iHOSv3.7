package com.huge.ihos.system.oa.bulletin.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.oa.bulletin.dao.BulletinDao;
import com.huge.ihos.system.oa.bulletin.model.Bulletin;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository( "bulletinDao" )
public class BulletinDaoHibernate
    extends GenericDaoHibernate<Bulletin, Long>
    implements BulletinDao {

    public BulletinDaoHibernate() {
        super( Bulletin.class );
    }

    public JQueryPager getBulletinCriteria( JQueryPager paginatedList, List<PropertyFilter> filters, String group_on ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                paginatedList.setSortCriterion( "bulletinId" );
            Map<String, Object> resultMap = getAppManagerCriteriaWithSearch( paginatedList, Bulletin.class, filters, group_on );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getBulletinCriteria", e );
            return paginatedList;
        }

    }

    public List<Bulletin> getBulletinsByUser( User user ) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( Bulletin.class );
        criteria.add( Restrictions.or( Restrictions.eq( "department", user.getPerson().getDepartment().getName() ), Restrictions.eq( "secretLevel",
                                                                                                                                     "全院" ) ) );
        criteria.addOrder( Order.desc( "createTime" ) );
        criteria.setFirstResult( 0 );
        criteria.setMaxResults( 5 );
        return criteria.list();
    }
}
