package com.huge.ihos.formula.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.formula.dao.UpCostDao;
import com.huge.ihos.formula.model.UpCost;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("upCostDao")
public class UpCostDaoHibernate extends GenericDaoHibernate<UpCost, Long> implements UpCostDao {

    public UpCostDaoHibernate() {
        super(UpCost.class);
    }
    
    public JQueryPager getUpCostCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("upcostId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, UpCost.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getUpCostCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<UpCost> getBycheckPeriodAndDept(String checkPeriod, String dept,Long upItemId,Integer state) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("checkperiod", checkPeriod));
		if(dept!=null&&!dept.equals("")){
			if(dept.contains(",")){
				String[] deptArr = dept.split(",");
				criteria.add(Restrictions.in("deptId.departmentId", deptArr));
			}else{
				criteria.add(Restrictions.eq("deptId.departmentId", dept));
			}
		}
		if(state!=null){
			if(state==0){
				criteria.add(Restrictions.eq("state", state));
			}else{
				criteria.add(Restrictions.not(Restrictions.eq("state", 0)));
			}
		}
		
		criteria.add(Restrictions.eq("upitemid.id", upItemId));
		return criteria.list();
	}

	@Override
	public List<UpCost> getBycheckPeriodAndOperator(String checkPeriod,
			String operator, Long upItemId) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("checkperiod", checkPeriod));
		criteria.add(Restrictions.eq("operatorId.personId", operator));
		criteria.add(Restrictions.eq("upitemid.id", upItemId));
		return criteria.list();
	}
	
	
}
