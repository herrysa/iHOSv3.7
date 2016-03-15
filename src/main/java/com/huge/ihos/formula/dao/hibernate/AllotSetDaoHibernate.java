package com.huge.ihos.formula.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.formula.dao.AllotSetDao;
import com.huge.ihos.formula.model.AllotSet;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "allotSetDao" )
public class AllotSetDaoHibernate
    extends GenericDaoHibernate<AllotSet, String>
    implements AllotSetDao {

    public AllotSetDaoHibernate() {
        super( AllotSet.class );
    }

    public JQueryPager getAllotSetCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("allotSetId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new AllotSetByParentCallBack(paginatedList, AllotSet.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, AllotSet.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getAllotSetCriteria", e );
            return paginatedList;
        }
    }
    /*
    class AllotSetByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	AllotSetByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("allotSet.allotSetId", parentId);
    		return criteria;
    	}
    }
     */
}
