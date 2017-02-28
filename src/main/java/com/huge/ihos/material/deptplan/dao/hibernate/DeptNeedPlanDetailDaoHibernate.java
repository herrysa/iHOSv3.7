package com.huge.ihos.material.deptplan.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail;
import com.huge.ihos.material.deptplan.dao.DeptNeedPlanDetailDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("deptNeedPlanDetailDao")
public class DeptNeedPlanDetailDaoHibernate extends GenericDaoHibernate<DeptNeedPlanDetail, String> implements DeptNeedPlanDetailDao {

    public DeptNeedPlanDetailDaoHibernate() {
        super(DeptNeedPlanDetail.class);
    }
    
    public JQueryPager getDeptNeedPlanDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("needDetailId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, DeptNeedPlanDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getDeptNeedPlanDetailCriteria", e);
			return paginatedList;
		}

	}
    @Override
    public Double getLastAmount(String lastPeriodMonth,String storeId,String deptId,String invId){
    	Session session = this.getSessionFactory().getCurrentSession();
		String hql = "select sum(amount) from DeptNeedPlanDetail where deptNeedPlan.periodMonth = ? and deptNeedPlan.store.id = ? and deptNeedPlan.dept.departmentId = ? and invDict.invId = ? and deptNeedPlan.state in ('1','2')";
		Query query = session.createQuery(hql);
		query.setString(0, lastPeriodMonth);
		query.setString(1, storeId);
		query.setString(2, deptId);
		query.setString(3, invId);
		Object object =  query.uniqueResult();
		return Double.parseDouble(object==null?"0":object.toString());
    }
}
