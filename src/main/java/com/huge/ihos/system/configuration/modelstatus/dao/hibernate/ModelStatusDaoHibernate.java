package com.huge.ihos.system.configuration.modelstatus.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.modelstatus.dao.ModelStatusDao;
import com.huge.ihos.system.configuration.modelstatus.model.ModelStatus;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("modelStatusDao")
public class ModelStatusDaoHibernate extends GenericDaoHibernate<ModelStatus, String> implements ModelStatusDao {

    public ModelStatusDaoHibernate() {
        super(ModelStatus.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getModelStatusCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ModelStatus.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getModelStatusCriteria", e);
			return paginatedList;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public String getUsingPeriod(String modelId, String periodType) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("modelId", modelId));
		criteria.add(Restrictions.eq("periodType", periodType));
		criteria.add(Restrictions.eq("status", "1"));
//		criteria.add(Restrictions.like("checkperiod", year,MatchMode.START));
		List<ModelStatus> list = criteria.list();
		if(list!=null && list.size()==1){
			return list.get(0).getCheckperiod();
		}
		return null;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getClosedPeriod(String modelId, String periodType) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("modelId", modelId));
		criteria.add(Restrictions.eq("periodType", periodType));
		criteria.add(Restrictions.eq("status", "2"));
		criteria.addOrder(Order.desc("checkperiod"));
		List<ModelStatus> list = criteria.list();
		if(list!=null && list.size()>0){
			return list.get(0).getCheckperiod();
		}
		return null;
	}
	
	
}
