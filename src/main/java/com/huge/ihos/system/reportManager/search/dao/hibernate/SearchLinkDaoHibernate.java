package com.huge.ihos.system.reportManager.search.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.ihos.system.reportManager.search.dao.SearchLinkDao;
import com.huge.ihos.system.reportManager.search.model.SearchLink;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "searchLinkDao" )
public class SearchLinkDaoHibernate
    extends GenericDaoHibernate<SearchLink, String>
    implements SearchLinkDao {

    public SearchLinkDaoHibernate() {
        super( SearchLink.class );
    }

    public JQueryPager getSearchLinkCriteria( JQueryPager paginatedList, String searchId ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("searchLinkId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:
            HibernateCallback callback = new SearchLinkByParentCallBack( paginatedList, SearchLink.class, searchId );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, SearchLink.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getSearchLinkCriteria", e );
            return paginatedList;
        }
    }

    class SearchLinkByParentCallBack
        extends JqueryPagerHibernateCallBack {
        String parentId;

        SearchLinkByParentCallBack( final JQueryPager paginatedList, final Class object, String parentId ) {
            super( paginatedList, object );
            this.parentId = parentId;
        }

        @Override
        public Criteria getCustomCriterion( Criteria criteria ) {
            criteria.createAlias( "search", "search" );
            criteria.add( Restrictions.eq( "search.searchId", parentId ) );
            return criteria;
        }
    }

}
