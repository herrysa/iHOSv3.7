package com.huge.ihos.system.systemManager.busiprocess.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.busiprocess.dao.BusiProcessDao;
import com.huge.ihos.system.systemManager.busiprocess.model.BusiProcess;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
@Repository("busiProcessDao")
public class BusiProcessDaoHibernate extends GenericDaoHibernate<BusiProcess, Long> implements BusiProcessDao {

    public BusiProcessDaoHibernate() {
        super(BusiProcess.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getBusinessProcessCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BusiProcess.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBusinessProcessCriteria", e);
			return paginatedList;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusiProcess> getBusinessProcessByCode(String code,Boolean ignoreError) {
		String hql = "";
		List<BusiProcess> l = null;
		if(ignoreError==null){
			hql = "from " + this.getPersistentClass().getSimpleName() + " where code=? and disabled = 0 order by sequence asc";
			 l= this.hibernateTemplate.find(hql,code);
		}else{
			hql = "from " + this.getPersistentClass().getSimpleName() + " where code=? and ignoreError = ? and disabled = 0 order by sequence asc";
			l= this.hibernateTemplate.find(hql,code,ignoreError);
		}
		if(l == null || l.size() == 0){
			return null;
		}
		return l;
	}
    
}
