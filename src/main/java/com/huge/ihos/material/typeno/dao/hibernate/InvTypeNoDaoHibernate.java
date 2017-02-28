package com.huge.ihos.material.typeno.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.typeno.dao.InvTypeNoDao;
import com.huge.ihos.material.typeno.model.InvTypeNo;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("invTypeNoDao")
public class InvTypeNoDaoHibernate extends GenericDaoHibernate<InvTypeNo, Long> implements InvTypeNoDao {

    public InvTypeNoDaoHibernate() {
        super(InvTypeNo.class);
    }
    
    public JQueryPager getInvTypeNoCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, InvTypeNo.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getInvTypeNoCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public String getTypeByNo(String no,String orgCode,String copyCode) {
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where no=? and orgCode=? and copyCode = ?";
		List<InvTypeNo> l = this.hibernateTemplate.find(hql, no,orgCode,copyCode);
		if(l!=null && l.size()==1){
			return l.get(0).getType();
		}
		return null;
	}
    
    
}
