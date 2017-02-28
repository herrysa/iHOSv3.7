package com.huge.ihos.material.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.material.model.InvBillNumberSetting;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InvBillNumberSettingDao extends GenericDao<InvBillNumberSetting, Long> {
	public InvBillNumberSetting getByCode(String code,String org,String copy);
	
    public JQueryPager getSerialSettingCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}
