package com.huge.ihos.formula.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.ihos.formula.dao.AllotItemDetailDao;
import com.huge.ihos.formula.model.AllotItemDetail;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "allotItemDetailDao" )
public class AllotItemDetailDaoHibernate
    extends GenericDaoHibernate<AllotItemDetail, Long>
    implements AllotItemDetailDao {

    public AllotItemDetailDaoHibernate() {
        super( AllotItemDetail.class );
    }

    public JQueryPager getAllotItemDetailCriteria( JQueryPager paginatedList, Long allotItemId ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("allotItemDetailId");
                paginatedList.setSortCriterion( null );

            HibernateCallback callback = new AllotItemDetailByParentCallBack( paginatedList, AllotItemDetail.class, allotItemId );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, AllotItemDetail.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getAllotItemDetailCriteria", e );
            return paginatedList;
        }
    }

    class AllotItemDetailByParentCallBack
        extends JqueryPagerHibernateCallBack {
        Long parentId;

        AllotItemDetailByParentCallBack( final JQueryPager paginatedList, final Class object, Long parentId ) {
            super( paginatedList, object );
            this.parentId = parentId;
        }

        @Override
        public Criteria getCustomCriterion( Criteria criteria ) {
            criteria.createAlias( "allotItem", "allotItem" );
            criteria.add( Restrictions.eq( "allotItem.allotItemId", parentId ) );
            return criteria;

            //Restrictions.eq("allotItemDetail.allotItemDetailId", parentId);
            //return criteria;
        }
    }

}
