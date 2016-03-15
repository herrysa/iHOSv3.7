package com.huge.ihos.kaohe.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.kaohe.dao.InspectTemplDao;
import com.huge.ihos.kaohe.model.InspectTempl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("inspectTemplDao")
public class InspectTemplDaoHibernate extends GenericDaoHibernate<InspectTempl, String> implements InspectTemplDao {

    public InspectTemplDaoHibernate() {
        super(InspectTempl.class);
    }
    
    public JQueryPager getInspectTemplCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("inspectModelId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, InspectTempl.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getInspectTemplCriteria", e);
			return paginatedList;
		}

	}
    
    public InspectTempl deptIsSelected(String deptId,String periodType,String selfTemplId){
    	Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(InspectTempl.class);
		criteria.add(Restrictions.not(Restrictions.eq("inspectModelId", selfTemplId)));
		criteria.add(Restrictions.eq("periodType", periodType));
		criteria.createAlias("departments", "departments").setFetchMode("departments", FetchMode.JOIN).add(Restrictions.eq("departments.departmentId", deptId));
		criteria.add(Restrictions.eq("disabled", false));
		List<InspectTempl> rs = criteria.list();
		if(rs==null||rs.size()==0){
			return null;
		}else{
			return rs.get(0);
		}
    }
}
