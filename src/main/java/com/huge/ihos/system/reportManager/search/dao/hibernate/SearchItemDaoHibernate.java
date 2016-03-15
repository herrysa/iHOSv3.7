package com.huge.ihos.system.reportManager.search.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.reportManager.search.dao.SearchItemDao;
import com.huge.ihos.system.reportManager.search.model.SearchItem;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "searchItemDao" )
public class SearchItemDaoHibernate
    extends GenericDaoHibernate<SearchItem, String>
    implements SearchItemDao {

    public SearchItemDaoHibernate() {
        super( SearchItem.class );
    }

    public JQueryPager getSearchItemCriteria( JQueryPager paginatedList, String parentId ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                // paginatedList.setSortCriterion("searchItemId");
                paginatedList.setSortCriterion( null );

            // TODO create a callback like this:
            HibernateCallback callback = new SearchItemByParentCallBack( paginatedList, SearchItem.class, parentId );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, SearchItem.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getSearchItemCriteria", e );
            return paginatedList;
        }
    }

    class SearchItemByParentCallBack
        extends JqueryPagerHibernateCallBack {
        String parentId;

        SearchItemByParentCallBack( final JQueryPager paginatedList, final Class object, String parentId ) {
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

    public SearchItem[] getEnabledSearchItemsBySearchIdOrdered( String searchId ) {
    	String herpType = ContextUtil.herpType;
        String hql = "from SearchItem s where  s.searchFlag=true and s.search.searchId=? and (s.herpType = '' or s.herpType is null or s.herpType=?) order by s.oorder";// and s.herpType="+herpType+"
        List list = this.getHibernateTemplate().find( hql, new Object[]{searchId,herpType});
        SearchItem[] sis = new SearchItem[0];
        if ( list.size() > 0 ) {
            sis = new SearchItem[list.size()];
            list.toArray( sis );
        }
        return sis;
    }

}
