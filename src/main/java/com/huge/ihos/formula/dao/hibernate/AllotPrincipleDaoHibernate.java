package com.huge.ihos.formula.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.formula.dao.AllotPrincipleDao;
import com.huge.ihos.formula.model.AllotPrinciple;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "allotPrincipleDao" )
public class AllotPrincipleDaoHibernate
    extends GenericDaoHibernate<AllotPrinciple, String>
    implements AllotPrincipleDao {

    public AllotPrincipleDaoHibernate() {
        super( AllotPrinciple.class );
    }

    public JQueryPager getAllotPrincipleCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("allotPrincipleId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new AllotPrincipleByParentCallBack(paginatedList, AllotPrinciple.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, AllotPrinciple.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getAllotPrincipleCriteria", e );
            return paginatedList;
        }
    }
    /*
    class AllotPrincipleByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	AllotPrincipleByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("allotPrinciple.allotPrincipleId", parentId);
    		return criteria;
    	}
    }
     */
}
