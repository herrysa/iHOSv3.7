package com.huge.ihos.system.datacollection.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.datacollection.dao.DataCollectionTaskDefineDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskDefine;
import com.huge.ihos.system.systemManager.user.dao.UserDao;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "dataCollectionTaskDefineDao" )
public class DataCollectionTaskDefineDaoHibernate
    extends GenericDaoHibernate<DataCollectionTaskDefine, Long>
    implements DataCollectionTaskDefineDao {

	@Autowired
	private UserDao userDao;
	
    public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public DataCollectionTaskDefineDaoHibernate() {
        super( DataCollectionTaskDefine.class );
    }

    public JQueryPager getDataCollectionTaskDefineCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("dataCollectionTaskDefineId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new DataCollectionTaskDefineByParentCallBack(paginatedList, DataCollectionTaskDefine.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, DataCollectionTaskDefine.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDataCollectionTaskDefineCriteria", e );
            return paginatedList;
        }
    }

    /*
    class DataCollectionTaskDefineByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	DataCollectionTaskDefineByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criterion getCustomCriterion() {
    		return Restrictions.eq("dataCollectionTaskDefine.dataCollectionTaskDefineId", parentId);
    	}
    }
     */

    public Integer getAllTaskDefineCount() {
    	SecurityContext ctx = SecurityContextHolder.getContext();
        UserDetails uds = (UserDetails) ctx.getAuthentication().getPrincipal();
        String un = uds.getUsername();
        User user = (User)userDao.loadUserByUsername( un );
        Session session = this.getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria( DataCollectionTaskDefine.class );
        criteria.add(Restrictions.eq("department.departmentId", user.getPerson().getDepartment().getJjDept().getDepartmentId()));
        criteria.setProjection( Projections.rowCount() );
        List countList = criteria.list();
        int count = 0;
        if ( !countList.isEmpty() )
            count = ( (Long) countList.get( 0 ) ).intValue();
        return count;

    }

	@Override
	public List<DataCollectionTaskDefine> getBySourceId(Long sourceDefineId) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("dataSourceDefine.dataSourceDefineId", sourceDefineId));
		return criteria.list();
	}

	@Override
	public List<DataCollectionTaskDefine> getByDept(String deptId) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("department.departmentId", deptId));
		return criteria.list();
	}
	@Override
	public List<DataCollectionTaskDefine> getByName(String name) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("dataCollectionTaskDefineName", name));
		return criteria.list();
	}
}
