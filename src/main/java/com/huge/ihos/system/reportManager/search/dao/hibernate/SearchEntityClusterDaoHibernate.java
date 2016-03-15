package com.huge.ihos.system.reportManager.search.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.ihos.system.reportManager.search.dao.SearchEntityClusterDao;
import com.huge.ihos.system.reportManager.search.model.SearchEntityCluster;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "searchEntityClusterDao" )
public class SearchEntityClusterDaoHibernate
    extends GenericDaoHibernate<SearchEntityCluster, Long>
    implements SearchEntityClusterDao {

    public SearchEntityClusterDaoHibernate() {
        super( SearchEntityCluster.class );
    }

    @Override
    public JQueryPager getSearchEntityClusterCriteria( JQueryPager paginatedList, Long parentId ) {
        try {

            if ( paginatedList.getSortCriterion() == null )
                paginatedList.setSortCriterion( null );

            HibernateCallback callback = new SearchEntityClusterByParentCallBack( paginatedList, SearchEntityCluster.class, parentId );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, SearchOption.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getSearchOptionCriteria", e );
            return paginatedList;
        }
    }

    class SearchEntityClusterByParentCallBack
        extends JqueryPagerHibernateCallBack {
        Long parentId;

        SearchEntityClusterByParentCallBack( final JQueryPager paginatedList, final Class object, Long parentId ) {
            super( paginatedList, object );
            this.parentId = parentId;
        }

        public Criteria getCustomCriterion( Criteria criteria ) {
            criteria.createAlias( "searchEntity", "searchEntity" );
            criteria.add( Restrictions.eq( "searchEntity.entityId", parentId ) );
            return criteria;
        }
    }
}
