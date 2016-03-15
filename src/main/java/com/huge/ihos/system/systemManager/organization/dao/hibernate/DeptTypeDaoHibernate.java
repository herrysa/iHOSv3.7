package com.huge.ihos.system.systemManager.organization.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.organization.dao.DeptTypeDao;
import com.huge.ihos.system.systemManager.organization.model.DeptType;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "deptTypeDao" )
public class DeptTypeDaoHibernate
    extends GenericDaoHibernate<DeptType, String>
    implements DeptTypeDao {

    public DeptTypeDaoHibernate() {
        super( DeptType.class );
    }

    public JQueryPager getDeptTypeCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("deptTypeId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new DeptTypeByParentCallBack(paginatedList, DeptType.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, DeptType.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDeptTypeCriteria", e );
            return paginatedList;
        }
    }
    /*
    class DeptTypeByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	DeptTypeByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("deptType.deptTypeId", parentId);
    		return criteria;
    	}
    }
     */
}
