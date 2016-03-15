package com.huge.ihos.hr.hrDeptment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.hrDeptment.dao.HrDepartmentHisDao;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentHis;
import com.huge.ihos.hr.hrDeptment.model.HrDeptSnapPk;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentHisManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("hrDepartmentHisManager")
public class HrDepartmentHisManagerImpl extends GenericManagerImpl<HrDepartmentHis, HrDeptSnapPk> implements HrDepartmentHisManager {
    private HrDepartmentHisDao hrDepartmentHisDao;

    @Autowired
    public HrDepartmentHisManagerImpl(HrDepartmentHisDao hrDepartmentHisDao) {
        super(hrDepartmentHisDao);
        this.hrDepartmentHisDao = hrDepartmentHisDao;
    }
    
    public JQueryPager getHrDepartmentHisCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return hrDepartmentHisDao.getHrDepartmentHisCriteria(paginatedList,filters);
	}
}