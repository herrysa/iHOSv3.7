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
import com.huge.ihos.system.systemManager.dataprivilege.dao.UserDataPrivilegeDao;
import com.huge.ihos.system.systemManager.dataprivilege.model.PrivilegeClass;
import com.huge.ihos.system.systemManager.dataprivilege.model.UserDataPrivilege;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("userDataPrivilegeDao")
public class UserDataPrivilegeDaoHibernate extends GenericDaoHibernate<UserDataPrivilege, String> implements UserDataPrivilegeDao {

    public UserDataPrivilegeDaoHibernate() {
        super(UserDataPrivilege.class);
    }
    
    public JQueryPager getUserDataPrivilegeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("dataPrivilegeId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, UserDataPrivilege.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getUserDataPrivilegeCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public void deleteByUserIdAndClass(String userId, PrivilegeClass privilegeClass,String exceptIds) {
		//String classType = privilegeClass.getClassType();
		
		String kjYearColName = "";//privilegeClass.getBdInfo().getKjYearColName();
		Criteria criteria = getCriteria();
		if(OtherUtil.measureNotNull(kjYearColName)){
			UserContext userContext = UserContextUtil.getUserContext();
			criteria.add(Restrictions.eq("periodYear", userContext.getPeriodYear()));
		}
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("classCode", privilegeClass.getClassCode()));
		
		List<UserDataPrivilege> udps = criteria.list();
		
		if((privilegeClass.getClassCode().equals("01")||privilegeClass.getClassCode().equals("02"))&&exceptIds!=null&&!"".equals(exceptIds)){
			Criteria criteria2 = getCriteria();
			criteria2.add(Restrictions.eq("userId", userId));
			String[] eids = exceptIds.split(",");;
			if(privilegeClass.getClassCode().equals("01")){
				criteria2.add(Restrictions.in("orgCode", eids));
			}else if(privilegeClass.getClassCode().equals("02")){
				criteria2.add(Restrictions.eq("copyCode", eids));
			}
			udps.addAll(criteria2.list());
		}
		
		hibernateTemplate.deleteAll(udps);
	}
	
	@Override
	public List<UserDataPrivilege> findByUserIdAndClass(String userId, String classCode) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("classCode", classCode));
		return criteria.list();
	}

	@Override
	public List<UserDataPrivilege> getByUserId(String userId) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("userId", userId));
		criteria.addOrder(Order.asc("item"));
		return criteria.list();
	}
}
