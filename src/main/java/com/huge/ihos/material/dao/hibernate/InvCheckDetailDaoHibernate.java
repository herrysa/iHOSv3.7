package com.huge.ihos.material.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.dao.InvCheckDetailDao;
import com.huge.ihos.material.model.InvCheck;
import com.huge.ihos.material.model.InvCheckDetail;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("invCheckDetailDao")
public class InvCheckDetailDaoHibernate extends GenericDaoHibernate<InvCheckDetail, String> implements InvCheckDetailDao {

    public InvCheckDetailDaoHibernate() {
        super(InvCheckDetail.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getInvCheckDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, InvCheckDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getInvCheckDetailCriteria", e);
			return paginatedList;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvCheckDetail> getInvCheckDetailsInSameCheck(InvCheck invCheck) {
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where checkId= ?";
	    List<InvCheckDetail> l = this.getHibernateTemplate().find( hql,invCheck.getCheckId());
		return l;
	}
    
}
