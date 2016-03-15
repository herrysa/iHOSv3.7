package com.huge.ihos.kaohe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.kaohe.dao.InspectBSCColumnDao;
import com.huge.ihos.kaohe.model.InspectBSCColumn;
import com.huge.ihos.kaohe.service.InspectBSCColumnManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("inspectBSCColumnManager")
public class InspectBSCColumnManagerImpl extends GenericManagerImpl<InspectBSCColumn, Long> implements InspectBSCColumnManager {
    private InspectBSCColumnDao inspectBSCColumnDao;

    @Autowired
    public InspectBSCColumnManagerImpl(InspectBSCColumnDao inspectBSCColumnDao) {
        super(inspectBSCColumnDao);
        this.inspectBSCColumnDao = inspectBSCColumnDao;
    }
    
    public JQueryPager getInspectBSCColumnCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return inspectBSCColumnDao.getInspectBSCColumnCriteria(paginatedList,filters);
	}
    
    @Override
	public List<InspectBSCColumn> findByInspectTemplId(String inspectTemplId) {
		return inspectBSCColumnDao.findByInspectTemplId(inspectTemplId);
	}

	@Override
	public void delByInspectTemplId(String inspectTemplId) {
		inspectBSCColumnDao.delByInspectTemplId(inspectTemplId);
	}
}