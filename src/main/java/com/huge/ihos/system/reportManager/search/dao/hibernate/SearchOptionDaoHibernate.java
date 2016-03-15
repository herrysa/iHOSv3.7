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
import com.huge.ihos.system.reportManager.search.dao.SearchOptionDao;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.webapp.pagers.JQueryPager;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Repository( "searchOptionDao" )
public class SearchOptionDaoHibernate
    extends GenericDaoHibernate<SearchOption, String>
    implements SearchOptionDao {

    public SearchOptionDaoHibernate() {
        super( SearchOption.class );
    }

    public JQueryPager getSearchOptionCriteria( JQueryPager paginatedList, String parentId ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                // paginatedList.setSortCriterion("searchOptionId");
                paginatedList.setSortCriterion( null );

            // TODO create a callback like this:
            HibernateCallback callback = new SearchOptionByParentCallBack( paginatedList, SearchOption.class, parentId );
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

    class SearchOptionByParentCallBack
        extends JqueryPagerHibernateCallBack {
        String parentId;

        SearchOptionByParentCallBack( final JQueryPager paginatedList, final Class object, String parentId ) {
            super( paginatedList, object );
            this.parentId = parentId;
        }

        public Criteria getCustomCriterion( Criteria criteria ) {
            criteria.createAlias( "search", "search" );
            criteria.add( Restrictions.eq( "search.searchId", parentId ) );
            return criteria;
        }
    }

    public SearchOption[] getSearchOptionsBySearchIdOrdered( String searchId ) {
    	String herpType = ContextUtil.herpType;
        String hql = "from SearchOption s where  s.search.searchId=? and (s.herpType = '' or s.herpType is null or s.herpType=?) order by s.oorder";// and herpType="+herpType+"
        List list = this.getHibernateTemplate().find(hql,new Object[]{searchId,herpType});
        SearchOption[] so = new SearchOption[0];
        if ( list.size() > 0 ) {
            so = new SearchOption[list.size()];
            list.toArray( so );
        }
        return so;
    }
    
    public SearchOption[] getFormedSearchOptions( String searchId ) {

        String hql = "from SearchOption s where  s.search.searchId=? and isForm=1 order by s.oorder";
        List list = this.getHibernateTemplate().find( hql, searchId );
        SearchOption[] so = new SearchOption[0];
        if ( list.size() > 0 ) {
            so = new SearchOption[list.size()];
            list.toArray( so );
        }
        return so;
    }

    public SearchOption[] getSearchSumOptionsBySearchIdOrdered( String searchId ) {

        String hql = "from SearchOption s where  s.search.searchId=? and s.calcType='sum' order by s.oorder";
        List list = this.getHibernateTemplate().find( hql, searchId );
        SearchOption[] so = new SearchOption[0];
        if ( list.size() > 0 ) {
            so = new SearchOption[list.size()];
            list.toArray( so );
        }
        return so;
    }

    public SearchOption[] getSearchEditOptionsBySearchIdOrdered( String searchId ) {

        String hql = "from SearchOption s where  s.search.searchId=? and s.editType<>'Label' order by s.oorder";
        List list = this.getHibernateTemplate().find( hql, searchId );
        SearchOption[] so = new SearchOption[0];
        if ( list.size() > 0 ) {
            so = new SearchOption[list.size()];
            list.toArray( so );
        }
        return so;
    }

    public SearchOption[] getSearchAllOptionsBySearchIdOrdered( String searchId ) {

        String hql = "from SearchOption s where  s.search.searchId=? order by s.oorder";
        List list = this.getHibernateTemplate().find( hql, searchId );
        SearchOption[] so = new SearchOption[0];
        if ( list.size() > 0 ) {
            so = new SearchOption[list.size()];
            list.toArray( so );
        }
        return so;
    }
}
