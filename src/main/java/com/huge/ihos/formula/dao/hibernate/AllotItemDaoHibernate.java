package com.huge.ihos.formula.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.formula.dao.AllotItemDao;
import com.huge.ihos.formula.model.AllotItem;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "allotItemDao" )
public class AllotItemDaoHibernate
    extends GenericDaoHibernate<AllotItem, Long>
    implements AllotItemDao {

    public AllotItemDaoHibernate() {
        super( AllotItem.class );
    }

    public JQueryPager getAllotItemCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("allotItemId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new AllotItemByParentCallBack(paginatedList, AllotItem.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, AllotItem.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getAllotItemCriteria", e );
            return paginatedList;
        }
    }
    /*
    class AllotItemByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	AllotItemByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("allotItem.allotItemId", parentId);
    		return criteria;
    	}
    }
     */
}
