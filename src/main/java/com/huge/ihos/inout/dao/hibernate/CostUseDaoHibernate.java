package com.huge.ihos.inout.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.inout.dao.CostUseDao;
import com.huge.ihos.inout.model.CostUse;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "costUseDao" )
public class CostUseDaoHibernate
    extends GenericDaoHibernate<CostUse, String>
    implements CostUseDao {

    public CostUseDaoHibernate() {
        super( CostUse.class );
    }

    public JQueryPager getCostUseCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("costUseId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new CostUseByParentCallBack(paginatedList, CostUse.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, CostUse.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getCostUseCriteria", e );
            return paginatedList;
        }
    }
    /*
    class CostUseByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	CostUseByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("costUse.costUseId", parentId);
    		return criteria;
    	}
    }
     */
}
