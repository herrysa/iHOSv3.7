package com.huge.ihos.gz.gzItem.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzItem.dao.GzItemDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("gzItemDao")
public class GzItemDaoHibernate extends GenericDaoHibernate<GzItem, String> implements GzItemDao {

    public GzItemDaoHibernate() {
        super(GzItem.class);
    }
    
    public JQueryPager getGzItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("itemId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, GzItem.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getGzItemCriteria", e);
			return paginatedList;
		}

	}
	@Override
	public int getMaxSn(String gzTypeId) {
	    String sql = "select max(sn) as sn from gz_gzItem where gzTypeId = '"+gzTypeId+"'";
	    List lst = this.getBySql(sql);
	    //当不是第一条工资项时
	    if(lst.size()>0){
	    	 Integer maxSn =(Integer) lst.get(0);
	    	return maxSn+1;
	    }
		return 1;
	}
}
