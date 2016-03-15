package com.huge.ihos.system.systemManager.dataprivilege.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.dataprivilege.dao.DataPrivilegeDao;
import com.huge.ihos.system.systemManager.dataprivilege.model.DataPrivilege;
import com.huge.ihos.system.systemManager.dataprivilege.model.PrivilegeClass;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("dataPrivilegeDao")
public class DataPrivilegeDaoHibernate extends GenericDaoHibernate<DataPrivilege, String> implements DataPrivilegeDao {
	
    public DataPrivilegeDaoHibernate() {
        super(DataPrivilege.class);
    }
    
    public JQueryPager getDataPrivilegeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("dataPrivilegeId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, DataPrivilege.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getDataPrivilegeCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<DataPrivilege> getDataPrivilegesByRole(String roleId) {
		try {
			Criteria criteria = getCriteria();
			criteria.add(Restrictions.eq("roleId", roleId));
			criteria.addOrder(Order.asc("item"));
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<DataPrivilege> getDataPrivilegesByRole(String roleId,
			String classCode) {
		try {
			Criteria criteria = getCriteria();
			criteria.add(Restrictions.eq("roleId", roleId));
			criteria.add(Restrictions.eq("classCode", classCode));
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteByRoleIdAndClass(String roleId, PrivilegeClass privilegeClass) {
		//String classType = privilegeClass.getClassType();
		Criteria criteria = getCriteria();
		String kjYearColName ="";//= privilegeClass.getBdInfo().getKjYearColName();
		if(OtherUtil.measureNotNull(kjYearColName)){
			UserContext userContext = UserContextUtil.getUserContext();
			criteria.add(Restrictions.eq("periodYear", userContext.getPeriodYear()));
		}
		criteria.add(Restrictions.eq("roleId", roleId));
		criteria.add(Restrictions.eq("classCode", privilegeClass.getClassCode()));
		hibernateTemplate.deleteAll(criteria.list());
		
	}
	
}
