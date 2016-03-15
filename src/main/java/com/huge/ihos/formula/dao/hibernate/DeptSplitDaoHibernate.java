package com.huge.ihos.formula.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.formula.dao.DeptSplitDao;
import com.huge.ihos.formula.model.DeptSplit;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "deptSplitDao" )
public class DeptSplitDaoHibernate
    extends GenericDaoHibernate<DeptSplit, Long>
    implements DeptSplitDao {

    public DeptSplitDaoHibernate() {
        super( DeptSplit.class );
    }

    public JQueryPager getDeptSplitCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("deptSplitId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new DeptSplitByParentCallBack(paginatedList, DeptSplit.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, DeptSplit.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDeptSplitCriteria", e );
            return paginatedList;
        }
    }
    /*
    class DeptSplitByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	DeptSplitByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("deptSplit.deptSplitId", parentId);
    		return criteria;
    	}
    }
     */
}
