package com.huge.ihos.system.systemManager.organization.dao.hibernate;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.organization.dao.OrgDao;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("orgDao")
public class OrgDaoHibernate extends GenericDaoHibernate<Org, String> implements OrgDao {

    public OrgDaoHibernate() {
        super(Org.class);
    }
    
    public JQueryPager getOrgCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("orgCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Org.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getOrgCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<Org> getRightOrg(List dataprivi) {
		List<Org> orgList = new ArrayList<Org>();
		if(dataprivi.size()!=0){
			Criteria criteria = getCriteria();
			criteria.add(Restrictions.in("orgCode", dataprivi));
			orgList = criteria.list();
		}
		return orgList;
	}

	@Override
	public List<Org> getOrgsByParent(String parentId) {
		List<Org> resultList = new ArrayList<Org>();
		Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql = "select * from t_org where parentOrgCode = '"+parentId+"'";
		Query query = session.createSQLQuery(sql).addEntity(Org.class);
		resultList = query.list();
		return resultList;
	}

	@Override
	public Org getOrgById(String orgId) {
		List<Org> resultList = new ArrayList<Org>();
		Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql = "select * from t_org where orgCode = '"+orgId+"'";
		Query query = session.createSQLQuery(sql).addEntity(Org.class);
		resultList = query.list();
		if(resultList == null || resultList.size()==0){
			return null;
		} else {
			return resultList.get(0);
		}
	}
	@Override
	public void disableOrgAfterSync(String snapCode){
		String sql="update T_Org set disabled = 1 where snapCode is not NULL and snapCode > '"+snapCode+"'";
    	excuteSql(sql);
	}
}
