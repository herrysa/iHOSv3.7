package com.huge.ihos.system.datacollection.maptables.dao.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.ihos.system.datacollection.maptables.dao.MatetypeDao;
import com.huge.ihos.system.datacollection.maptables.model.Matetype;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.PropertyFilter.MatchType;

@Repository( "matetypeDao" )
public class MatetypeDaoHibernate
    extends GenericDaoHibernate<Matetype, Long>
    implements MatetypeDao {

    public MatetypeDaoHibernate() {
        super( Matetype.class );
    }

    public JQueryPager getMatetypeCriteria( JQueryPager paginatedList, String mateTypeId, String costItemId, String costitem, String mateType ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                // paginatedList.setSortCriterion("mateTypeId");
                paginatedList.setSortCriterion( null );

            // TODO create a callback like this:
            HibernateCallback callback = new MatetypeByParentCallBack( paginatedList, Matetype.class, mateTypeId, costItemId, costitem, mateType );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, Matetype.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getMatetypeCriteria", e );
            return paginatedList;
        }
    }

    class MatetypeByParentCallBack
        extends JqueryPagerHibernateCallBack {
        String mateTypeId;

        String costItemId;

        String costitem;

        String mateType;

        MatetypeByParentCallBack( final JQueryPager paginatedList, final Class object, String mateTypeId, String costItemId, String costitem,
                                  String mateType ) {
            super( paginatedList, object );
            this.mateTypeId = mateTypeId;
            this.costItemId = costItemId;
            this.costitem = costitem;
            this.mateType = mateType;
        }

        @Override
        public Criteria getCustomCriterion( Criteria criteria ) {
            if ( mateTypeId != null && !mateTypeId.equals( "" ) )
                criteria.add( Restrictions.like( "mateTypeId", mateTypeId, MatchMode.ANYWHERE ) );
            if ( costItemId != null && !costItemId.equals( "" ) )
                criteria.add( Restrictions.like( "costItemId", costItemId, MatchMode.ANYWHERE ) );
            if ( costitem != null && !costitem.equals( "" ) )
                criteria.add( Restrictions.like( "costitem", costitem, MatchMode.ANYWHERE ) );
            if ( mateType != null && !mateType.equals( "" ) )
                criteria.add( Restrictions.like( "mateType", mateType, MatchMode.ANYWHERE ) );
            return criteria;
        }
    }

    public JQueryPager getMatetypeCriteria( final JQueryPager paginatedList, List<PropertyFilter> filters ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                // paginatedList.setSortCriterion("mateTypeId");
                paginatedList.setSortCriterion( null );

            // TODO create a callback like this:
            HibernateCallback callback = new MatetypeByParentCallBackFilter( paginatedList, Matetype.class, filters );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, Matetype.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getMatetypeCriteria", e );
            return paginatedList;
        }
    }

    class MatetypeByParentCallBackFilter
        extends JqueryPagerHibernateCallBack {
        List<PropertyFilter> filters;

        MatetypeByParentCallBackFilter( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters ) {
            super( paginatedList, object );
            this.filters = filters;

        }

        @Override
        public Criteria getCustomCriterion( Criteria criteria ) {
            Iterator itr = this.filters.iterator();
            while ( itr.hasNext() ) {
                PropertyFilter pf = (PropertyFilter) itr.next();
                if ( pf.getMatchType().equals( MatchType.LIKE ) ) {
                    criteria.add( Restrictions.like( pf.getPropertyName(), (String) pf.getMatchValue(), MatchMode.ANYWHERE ) );
                }

            }

            /*
             * if(mateTypeId!=null&&!mateTypeId.equals(""))
             * criteria.add(Restrictions.like("mateTypeId",
             * mateTypeId,MatchMode.ANYWHERE));
             * if(costItemId!=null&&!costItemId.equals(""))
             * criteria.add(Restrictions.like("costItemId",
             * costItemId,MatchMode.ANYWHERE));
             * if(costitem!=null&&!costitem.equals(""))
             * criteria.add(Restrictions.like("costitem",
             * costitem,MatchMode.ANYWHERE));
             * if(mateType!=null&&!mateType.equals(""))
             * criteria.add(Restrictions.like("mateType",
             * mateType,MatchMode.ANYWHERE));
             */
            return criteria;
        }
    }
}
