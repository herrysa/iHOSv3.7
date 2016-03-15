package com.huge.ihos.system.systemManager.organization.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.dao.BranchDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("branchDao")
public class BranchDaoHibernate extends GenericDaoHibernate<Branch, String> implements BranchDao {

    public BranchDaoHibernate() {
        super(Branch.class);
    }
    
    public JQueryPager getBranchCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("branchCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Branch.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBranchCriteria", e);
			return paginatedList;
		}

	}
    
    @Override
    public List<Branch> getAllAvailable() {
    	Criteria criteria = this.getCriteria();
    	criteria.add(Restrictions.ne("branchCode", "XT"));
    	criteria.add(Restrictions.eq("disabled", false));
    	return criteria.list();
    }
    
    @Override
    public List<Branch> getAllAvailable(String branchPriv) {
    	Criteria criteria = this.getCriteria();
    	criteria.add(Restrictions.ne("branchCode", "XT"));
    	criteria.add(Restrictions.eq("disabled", false));
    	if(!branchPriv.startsWith("select")&&!branchPriv.startsWith("SELECT")) {
    		String[] branchPrivArr = branchPriv.split(",");
    		criteria.add(Restrictions.in("branchCode",branchPrivArr));
    	}
    	return criteria.list();
    }
    
    @Override
    public List<Branch> getByOrgCode(String orgCode) {
    	Criteria criteria = this.getCriteria();
    	criteria.add(Restrictions.eq("orgCode", orgCode));
    	criteria.addOrder(Order.asc("branchCode"));
    	return criteria.list();
    }
}
