package com.huge.ihos.hr.statistics.dao.hibernate;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.statistics.dao.StatisticsItemDao;
import com.huge.ihos.hr.statistics.model.StatisticsDetail;
import com.huge.ihos.hr.statistics.model.StatisticsItem;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("statisticsItemDao")
public class StatisticsItemDaoHibernate extends GenericDaoHibernate<StatisticsItem, String> implements StatisticsItemDao {

    public StatisticsItemDaoHibernate() {
        super(StatisticsItem.class);
    }
    
    public JQueryPager getStatisticsItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, StatisticsItem.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getStatisticsItemCriteria", e);
			return paginatedList;
		}

	}
    @SuppressWarnings("unchecked")
	@Override
    public StatisticsDetail getStatisticsDetail(String statisticsCode){
    	StatisticsDetail statisticsDetail=new StatisticsDetail();
    	String hql="from StatisticsDetail where code=?";
    	List<StatisticsDetail> statisticsDetails=new ArrayList<StatisticsDetail>();
    	statisticsDetails=this.hibernateTemplate.find(hql, statisticsCode);
    	if(statisticsDetails!=null&&statisticsDetails.size()>0){
    		statisticsDetail=statisticsDetails.get(0);
    	}
    	return statisticsDetail;
    }
}
