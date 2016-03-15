package com.huge.ihos.kaohe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.kaohe.dao.InspectTemplDao;
import com.huge.ihos.kaohe.model.InspectTempl;
import com.huge.ihos.kaohe.service.InspectTemplManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("inspectTemplManager")
public class InspectTemplManagerImpl extends GenericManagerImpl<InspectTempl, String> implements InspectTemplManager {
    private InspectTemplDao inspectTemplDao;

    @Autowired
    public InspectTemplManagerImpl(InspectTemplDao inspectTemplDao) {
        super(inspectTemplDao);
        this.inspectTemplDao = inspectTemplDao;
    }
    
    public JQueryPager getInspectTemplCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return inspectTemplDao.getInspectTemplCriteria(paginatedList,filters);
	}

	@Override
	public InspectTempl deptIsSelected(String deptId, String periodType,
			String selfTemplId) {
		return inspectTemplDao.deptIsSelected(deptId, periodType, selfTemplId);
	}
}