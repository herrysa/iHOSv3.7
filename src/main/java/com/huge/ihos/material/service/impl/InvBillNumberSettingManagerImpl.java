package com.huge.ihos.material.service.impl;

import java.util.List;
import com.huge.ihos.material.dao.InvBillNumberSettingDao;
import com.huge.ihos.material.model.InvBillNumberSetting;
import com.huge.ihos.material.service.InvBillNumberSettingManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("invBillNumberSettingManager")
public class InvBillNumberSettingManagerImpl extends GenericManagerImpl<InvBillNumberSetting, Long> implements InvBillNumberSettingManager {
    private InvBillNumberSettingDao invBillNumberSettingDao;

    @Autowired
    public InvBillNumberSettingManagerImpl(InvBillNumberSettingDao invBillNumberSettingDao) {
        super(invBillNumberSettingDao);
        this.invBillNumberSettingDao = invBillNumberSettingDao;
    }
    
    public JQueryPager getInvBillNumberSettingCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return invBillNumberSettingDao.getSerialSettingCriteria(paginatedList,filters);
	}
}