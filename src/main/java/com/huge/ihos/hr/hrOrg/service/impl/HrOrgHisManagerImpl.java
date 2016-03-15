package com.huge.ihos.hr.hrOrg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.hrOrg.dao.HrOrgHisDao;
import com.huge.ihos.hr.hrOrg.model.HrOrgHis;
import com.huge.ihos.hr.hrOrg.model.HrOrgPk;
import com.huge.ihos.hr.hrOrg.service.HrOrgHisManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("hrOrgHisManager")
public class HrOrgHisManagerImpl extends GenericManagerImpl<HrOrgHis, HrOrgPk> implements HrOrgHisManager {
    private HrOrgHisDao hrOrgHisDao;

    @Autowired
    public HrOrgHisManagerImpl(HrOrgHisDao hrOrgHisDao) {
        super(hrOrgHisDao);
        this.hrOrgHisDao = hrOrgHisDao;
    }
    
    public JQueryPager getHrOrgHisCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return hrOrgHisDao.getHrOrgHisCriteria(paginatedList,filters);
	}
}