package com.huge.ihos.hr.hrPerson.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.hrPerson.dao.HrPersonHisDao;
import com.huge.ihos.hr.hrPerson.model.HrPersonHis;
import com.huge.ihos.hr.hrPerson.model.HrPersonPK;
import com.huge.ihos.hr.hrPerson.service.HrPersonHisManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("hrPersonHisManager")
public class HrPersonHisManagerImpl extends GenericManagerImpl<HrPersonHis, HrPersonPK> implements HrPersonHisManager {
    private HrPersonHisDao hrPersonHisDao;

    @Autowired
    public HrPersonHisManagerImpl(HrPersonHisDao hrPersonHisDao) {
        super(hrPersonHisDao);
        this.hrPersonHisDao = hrPersonHisDao;
    }
    
    public JQueryPager getHrPersonHisCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return hrPersonHisDao.getHrPersonHisCriteria(paginatedList,filters);
	}
}