package com.huge.ihos.material.deptapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.deptapp.dao.DeptAppDLDetailDao;
import com.huge.ihos.material.deptapp.dao.DeptAppDao;
import com.huge.ihos.material.deptapp.dao.DeptAppDetailDao;
import com.huge.ihos.material.deptapp.model.DeptApp;
import com.huge.ihos.material.deptapp.model.DeptAppDLDetail;
import com.huge.ihos.material.deptapp.model.DeptAppDetail;
import com.huge.ihos.material.deptapp.model.DeptAppDisLog;
import com.huge.ihos.material.deptapp.service.DeptAppDLDetailManager;
import com.huge.ihos.material.deptapp.service.DeptAppDisLogManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Service("deptAppDLDetailManager")
public class DeptAppDLDetailManagerImpl extends GenericManagerImpl<DeptAppDLDetail, String> implements DeptAppDLDetailManager {
    private DeptAppDLDetailDao deptAppDLDetailDao;
    private DeptAppDisLogManager deptAppDisLogManager;
    private DeptAppDao deptAppDao;
    private DeptAppDetailDao deptAppDetailDao;

    @Autowired
    public DeptAppDLDetailManagerImpl(DeptAppDLDetailDao deptAppDLDetailDao) {
        super(deptAppDLDetailDao);
        this.deptAppDLDetailDao = deptAppDLDetailDao;
    }
    
    public JQueryPager getDeptAppDLDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return deptAppDLDetailDao.getDeptAppDLDetailCriteria(paginatedList,filters);
	}
    @Autowired
    public void setDeptAppDao(DeptAppDao deptAppDao) {
		this.deptAppDao = deptAppDao;
	}
    @Autowired
	public void setDeptAppDetailDao(DeptAppDetailDao deptAppDetailDao) {
		this.deptAppDetailDao = deptAppDetailDao;
	}

	@Autowired
	public void setDeptAppDisLogManager(DeptAppDisLogManager deptAppDisLogManager) {
		this.deptAppDisLogManager = deptAppDisLogManager;
	}

	@Override
	public List<DeptAppDLDetail> getDeptAppDLDetails(DeptApp deptApp,String invDictId) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_orgCode",deptApp.getOrgCode()));
		filters.add(new PropertyFilter("EQS_copyCode",deptApp.getCopyCode()));
		filters.add(new PropertyFilter("EQS_yearMonth",deptApp.getYearMonth()));
		filters.add(new PropertyFilter("EQS_deptAppNo",deptApp.getAppNo()));
		filters.add(new PropertyFilter("EQS_invDict.invId",invDictId));
		List<DeptAppDisLog> deptAppDisLogs = deptAppDisLogManager.getByFilters(filters);
		if(deptAppDisLogs!=null && deptAppDisLogs.size()==1){
			DeptAppDisLog deptAppDisLog = deptAppDisLogs.get(0);
			filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_deptAppDL.logId",deptAppDisLog.getLogId()));
			filters.add(new PropertyFilter("ISNULLS_outNo",""));
			List<DeptAppDLDetail> deptAppDLDetails = this.getByFilters(filters);
			if(deptAppDLDetails!=null && deptAppDLDetails.size()>0){
				return deptAppDLDetails;
			}
		}
		return null;
	}

	@Override
	public void setOutNoByInvMainOut(String outNo, String type,String deptAppId,String deptAppDetailIds) {
		if("save".equals(type)){
			DeptApp deptApp = deptAppDao.get(deptAppId);
			String[] ids = deptAppDetailIds.split(",");
			if(ids.length>0){
				for(String id:ids) {
					String detailId = id.trim();
					DeptAppDetail deptAppDetail = deptAppDetailDao.get(detailId);
					List<DeptAppDLDetail> deptAppDLDetails = this.getDeptAppDLDetails(deptApp, deptAppDetail.getInvDict().getInvId());
					if(deptAppDLDetails!=null && deptAppDLDetails.size()>0){
						for(DeptAppDLDetail detail:deptAppDLDetails){
							detail.setOutNo(outNo);
							this.save(detail);
						}
					}
				}
			}
		}else if("delete".equals(type)){
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_outNo",outNo));
			List<DeptAppDLDetail> deptAppDLDetails = this.getByFilters(filters);
			for(DeptAppDLDetail deptAppDLDetail:deptAppDLDetails){
				deptAppDLDetail.setOutNo(null);
				this.save(deptAppDLDetail);
			}
		}else if("comfirm".equals(type)){
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_outNo","临-"+outNo));
			List<DeptAppDLDetail> deptAppDLDetails = this.getByFilters(filters);
			for(DeptAppDLDetail deptAppDLDetail:deptAppDLDetails){
				deptAppDLDetail.setOutNo(outNo);
				this.save(deptAppDLDetail);
			}
		}
	}
    
}