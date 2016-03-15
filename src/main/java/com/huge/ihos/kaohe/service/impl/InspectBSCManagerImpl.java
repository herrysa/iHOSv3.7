package com.huge.ihos.kaohe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.kaohe.dao.InspectBSCDao;
import com.huge.ihos.kaohe.model.InspectBSC;
import com.huge.ihos.kaohe.model.KpiItem;
import com.huge.ihos.kaohe.service.InspectBSCManager;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("inspectBSCManager")
public class InspectBSCManagerImpl extends GenericManagerImpl<InspectBSC, String> implements InspectBSCManager {
    private InspectBSCDao inspectBSCDao;

    @Autowired
    public InspectBSCManagerImpl(InspectBSCDao inspectBSCDao) {
        super(inspectBSCDao);
        this.inspectBSCDao = inspectBSCDao;
    }
    
    public JQueryPager getInspectBSCCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return inspectBSCDao.getInspectBSCCriteria(paginatedList,filters);
	}

	@Override
	public InspectBSC findByKpiItem(KpiItem kpiItem,String inspectTemplId) {
		return inspectBSCDao.findByKpiItem(kpiItem,inspectTemplId);
	}

	@Override
	public List<InspectBSC> findByInspectTemplId(String inspectTemplId) {
		return inspectBSCDao.findByInspectTemplId(inspectTemplId);
	}

	@Override
	public void deleteByInspectTemplId(String inspectTemplId) {
		inspectBSCDao.deleteByInspectTemplId(inspectTemplId);
	}
	
	@Override
	public List<InspectBSC> findByInspectByDept(String inspectTemplId,
			Department dept) {
		return inspectBSCDao.findByInspectByDept(inspectTemplId,dept);
	}
}