package com.huge.ihos.material.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.dao.InvCheckDao;
import com.huge.ihos.material.model.InvCheck;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("invCheckDao")
public class InvCheckDaoHibernate extends GenericDaoHibernate<InvCheck, String> implements InvCheckDao {

    public InvCheckDaoHibernate() {
        super(InvCheck.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getInvCheckCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("checkId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, InvCheck.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getInvCheckCriteria", e);
			return paginatedList;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public InvCheck getInvCheckByCheckNo(String checkNo, String orgCode,
			String copyCode) {
		InvCheck invCheck = null;
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where checkNo= ? and orgCode= ? and copyCode=?";
	    List<InvCheck> l = this.getHibernateTemplate().find( hql,checkNo,orgCode,copyCode );
	    if(l!=null && l.size()>0){
	    	invCheck =  (InvCheck)l.get(0);
	    }
		return invCheck;
	}
    
}
