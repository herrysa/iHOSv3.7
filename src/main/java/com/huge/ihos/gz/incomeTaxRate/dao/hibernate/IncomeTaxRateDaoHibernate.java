package com.huge.ihos.gz.incomeTaxRate.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.gz.incomeTaxRate.model.IncomeTaxRate;
import com.huge.ihos.gz.incomeTaxRate.dao.IncomeTaxRateDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("incomeTaxRateDao")
public class IncomeTaxRateDaoHibernate extends GenericDaoHibernate<IncomeTaxRate, String> implements IncomeTaxRateDao {

    public IncomeTaxRateDaoHibernate() {
        super(IncomeTaxRate.class);
    }
    
    public JQueryPager getIncomeTaxRateCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("rateId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, IncomeTaxRate.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getIncomeTaxRateCriteria", e);
			return paginatedList;
		}

	}
}
