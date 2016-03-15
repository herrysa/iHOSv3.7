package com.huge.ihos.system.systemManager.dataprivilege.dao.hibernate;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.dataprivilege.dao.PrivilegeClassDao;
import com.huge.ihos.system.systemManager.dataprivilege.model.PrivilegeClass;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("privilegeClassDao")
public class PrivilegeClassDaoHibernate extends GenericDaoHibernate<PrivilegeClass, String> implements PrivilegeClassDao {

    public PrivilegeClassDaoHibernate() {
        super(PrivilegeClass.class);
    }
    
    public JQueryPager getPrivilegeClassCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("classCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, PrivilegeClass.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getPrivilegeClassCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<PrivilegeClass> findEnabledClass() {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("disabled", false));
		criteria.addOrder(Order.asc("sn"));
		return criteria.list();
	}
	
	@Override
	public List<PrivilegeClass> getAllByCode(String classCode) {
		Criteria criteria = getCriteria();
		List<PrivilegeClass> privateClasses = new ArrayList<PrivilegeClass>();
		List<PrivilegeClass> privilegeClassesTemp = criteria.add(Restrictions.eq("classCode", classCode)).list();
		privateClasses.addAll(privilegeClassesTemp);
		for(PrivilegeClass privilegeClass : privilegeClassesTemp) {
			String parentClass = privilegeClass.getClassCode();
			getByParentCode(privateClasses,parentClass);
		}
		return privateClasses;
	}
	private void getByParentCode(List<PrivilegeClass> privilegeClasses,String parentCode) {
		Criteria criteria = getCriteria();
		List<PrivilegeClass> privilegeClassTemp = criteria.add(Restrictions.eq("parentClass.classCode", parentCode)).list();
		if(privilegeClassTemp != null && !privilegeClassTemp.isEmpty()) {
			privilegeClasses.addAll(privilegeClassTemp);
			for(PrivilegeClass privilegeClass : privilegeClassTemp) {
				String classCode = privilegeClass.getClassCode();
				getByParentCode(privilegeClasses,classCode);
			}
		}
	}
}
