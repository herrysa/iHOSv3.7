package com.huge.ihos.orgprivilege.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.orgprivilege.model.OrgPrivilege;
import com.huge.ihos.orgprivilege.dao.OrgPrivilegeDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("orgPrivilegeDao")
public class OrgPrivilegeDaoHibernate extends GenericDaoHibernate<OrgPrivilege, String> implements OrgPrivilegeDao {

    public OrgPrivilegeDaoHibernate() {
        super(OrgPrivilege.class);
    }
    
    public JQueryPager getOrgPrivilegeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("privilegeId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, OrgPrivilege.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getOrgPrivilegeCriteria", e);
			return paginatedList;
		}

	}
    
    public String[] getByOperatorId(String personId) {
		String sql="select orgIds from sy_orgPrivilege where personId=?";
		Query query=this.sessionFactory.getCurrentSession().createSQLQuery( sql );
		query.setString(0, personId);
		List list = query.list();
		String[] orgIds = new String[0];
		if(list.size()>0)
			orgIds=list.get(0).toString().split(",");
		return orgIds;
	}
}
