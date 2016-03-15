package com.huge.ihos.hr.sysTables.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.sysTables.model.SysTableKind;
import com.huge.ihos.hr.sysTables.dao.SysTableKindDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("sysTableKindDao")
public class SysTableKindDaoHibernate extends GenericDaoHibernate<SysTableKind, String> implements SysTableKindDao {

    public SysTableKindDaoHibernate() {
        super(SysTableKind.class);
    }
    
    public JQueryPager getSysTableKindCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, SysTableKind.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getSysTableKindCriteria", e);
			return paginatedList;
		}

	}
    @SuppressWarnings("unchecked")
   	@Override
    public List<SysTableKind> getFullSysTableKind(String orgCode){
    	//String hql = "from " + this.getPersistentClass().getSimpleName() + " where orgCode=?";	
    	String hql = "from " + this.getPersistentClass().getSimpleName() + "";	
		hql += " ORDER BY sn ASC";
		List<SysTableKind> sysTableKinds = this.getHibernateTemplate().find( hql);
		return sysTableKinds;
    }
}
