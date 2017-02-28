package com.huge.ihos.system.configuration.procType.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.procType.dao.ProcTypeDao;
import com.huge.ihos.system.configuration.procType.model.ProcType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("procTypeDao")
public class ProcTypeDaoHibernate extends GenericDaoHibernate<ProcType, String> implements ProcTypeDao {

    public ProcTypeDaoHibernate() {
        super(ProcType.class);
    }
    
    public JQueryPager getProcTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("typeId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ProcType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getProcTypeCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public ProcType getProcTypeByCode(String code) {
		ProcType procType = null;
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where typeCode= ?";
	    List l = this.getHibernateTemplate().find( hql,code);
	    if(l!=null && l.size()>0){
	    	procType =  (ProcType)l.get(0);
	    }
		return procType;
	}
    
    
}
