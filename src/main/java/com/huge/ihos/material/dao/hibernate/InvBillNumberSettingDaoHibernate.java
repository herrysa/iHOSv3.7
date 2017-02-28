package com.huge.ihos.material.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.dao.InvBillNumberSettingDao;
import com.huge.ihos.material.model.InvBillNumberSetting;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("invBillNumberSettingDao")
public class InvBillNumberSettingDaoHibernate extends GenericDaoHibernate<InvBillNumberSetting, Long> implements InvBillNumberSettingDao {

	public InvBillNumberSettingDaoHibernate() {
		super(InvBillNumberSetting.class);
	}

	@Override
	public InvBillNumberSetting getByCode(String code,String org,String copy) {
		String hql = "from " + getPersistentClass().getSimpleName() + " where businessCode=? and orgCode=? and copyCode=? and disabled = 0";
		Object[] args ={code,org,copy};
		
		List ss = getHibernateTemplate().find(hql, args);

		if (ss.isEmpty()) {
			return null;
		} else {
			return (InvBillNumberSetting) ss.get(0);
		}
	}

	public void copySetting(String code,String org,String copy,String toCopy){
//TODO 需要验证一下是否可行
		InvBillNumberSetting from = this.getByCode(code, org, copy);
		from.setCopyCode(toCopy);
		from.setId(null);
		this.save(from);
	}
	
	
	public JQueryPager getSerialSettingCriteria(final JQueryPager paginatedList, List<PropertyFilter> filters) {
		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, InvBillNumberSetting.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getSerialSettingCriteria", e);
			return paginatedList;
		}
	}
}
