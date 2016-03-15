package com.huge.ihos.system.reportManager.search.dao.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.system.reportManager.search.dao.SearchDao;
import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.model.Test;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "searchDao" )
public class SearchDaoHibernate
    extends GenericDaoHibernate<Search, String>
    implements SearchDao {

    public SearchDaoHibernate() {
        super( Search.class );
    }

    public JQueryPager getSearchCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("searchId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new SearchByParentCallBack(paginatedList, Search.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, Search.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getSearchCriteria", e );
            return paginatedList;
        }
    }

    /*
    class SearchByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	SearchByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criterion getCustomCriterion() {
    		return Restrictions.eq("search.searchId", parentId);
    	}
    }
     */

    public Search getBySearchName( String searchName ) {
        String hql = "from Search s where s.searchName=?";
        List list = this.getHibernateTemplate().find( hql, searchName );

        if ( list.size() > 1 ) {
            throw new BusinessException( "search of " + searchName + " is more than one." );
        }
        if ( list.size() < 1 ) {
            throw new BusinessException( "search of " + searchName + " is less than one." );
        }
        return (Search) list.get( 0 );
    }

    public String commandScript( String str )
        throws SQLException {
        Connection conn = this.sessionFactory.getCurrentSession().connection();
        Statement stmt = null;
        String strChage = str.replace( "GO", ";" );
        //System.out.println(strChage);
        String[] sqlList = strChage.split( ";" );
        try {
            conn.setAutoCommit( false );
            stmt = conn.createStatement();
            for ( String sql : sqlList ) {
                if ( OtherUtil.measureNotNull( sql ) )
                    stmt.addBatch( sql );
            }
            int[] rows = stmt.executeBatch();
            conn.commit();
            return "success";
            //System.out.println("Row count:" + Arrays.toString(rows));
        }
        catch ( SQLException e ) {
            e.printStackTrace();
            conn.rollback();
        }
        return "error";
    }

    public List<Test> getAllTest() {
        String hql = "from Test t";
        List<Test> list = this.getHibernateTemplate().find( hql );
        return list;
    }

}
