package com.huge.ihos.accounting.kjyear.dao.hibernate;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.accounting.kjyear.dao.KjYearDao;
import com.huge.ihos.accounting.kjyear.model.KjYear;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("kjYearDao")
public class KjYearDaoHibernate extends GenericDaoHibernate<KjYear, String> implements KjYearDao {

    public KjYearDaoHibernate() {
        super(KjYear.class);
    }
    
    public JQueryPager getKjYearCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("kjYearId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, KjYear.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getKjYearCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public KjYear getKjyearByDate(String orgCode,String optDate) {
		try {
			Criteria criteria = getCriteria();
			criteria.add(Restrictions.eq("orgId", orgCode));
			criteria.add(Restrictions.le("startDate", optDate));
			criteria.add(Restrictions.ge("endDate", optDate));
			List<KjYear> kjYears = criteria.list();
			if(kjYears!=null&&kjYears.size()!=0){
				return kjYears.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public KjYear getKjYear(HashMap<String, String> environment) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("orgCode", environment.get("orgCode")));
		criteria.add(Restrictions.eq("copyCode", environment.get("copyCode")));
		criteria.add(Restrictions.eq("kjYear", environment.get("kjYear")));
		List<KjYear> kjYears = criteria.list();
		if(kjYears!=null&&kjYears.size()!=0){
			return kjYears.get(0);
		}else{
			return null;
		}
	}
}
