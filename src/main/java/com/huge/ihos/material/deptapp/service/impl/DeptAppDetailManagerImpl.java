package com.huge.ihos.material.deptapp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.deptapp.dao.DeptAppDao;
import com.huge.ihos.material.deptapp.dao.DeptAppDetailDao;
import com.huge.ihos.material.deptapp.model.DeptApp;
import com.huge.ihos.material.deptapp.model.DeptAppDLDetail;
import com.huge.ihos.material.deptapp.model.DeptAppDetail;
import com.huge.ihos.material.deptapp.service.DeptAppDLDetailManager;
import com.huge.ihos.material.deptapp.service.DeptAppDetailManager;
import com.huge.ihos.material.deptplan.model.DeptNeedPlan;
import com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail;
import com.huge.ihos.material.model.InvDetail;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("deptAppDetailManager")
public class DeptAppDetailManagerImpl extends GenericManagerImpl<DeptAppDetail, String> implements DeptAppDetailManager {
    private DeptAppDetailDao deptAppDetailDao;
    private DeptAppDao deptAppDao;
    private DeptAppDLDetailManager deptAppDLDetailManager;

    @Autowired
    public DeptAppDetailManagerImpl(DeptAppDetailDao deptAppDetailDao) {
        super(deptAppDetailDao);
        this.deptAppDetailDao = deptAppDetailDao;
    }
    
    public JQueryPager getDeptAppDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return deptAppDetailDao.getDeptAppDetailCriteria(paginatedList,filters);
	}
    @Autowired
	public void setDeptAppDao(DeptAppDao deptAppDao) {
		this.deptAppDao = deptAppDao;
	}
    
    @Autowired
	public void setDeptAppDLDetailManager(
			DeptAppDLDetailManager deptAppDLDetailManager) {
		this.deptAppDLDetailManager = deptAppDLDetailManager;
	}

	@Override
	public List<InvDetail> getInvOutDetailByDisLog(String deptAppId,String detailIds) {
		DeptApp deptApp = deptAppDao.get(deptAppId);
		String state = deptApp.getAppState();
		if("3".equals(state) || "8".equals(state)){
			return null;
		}
		List<InvDetail> invDetails = null;
		InvDetail invDetail = null;
		String[] ids = detailIds.split(",");
		if(ids.length>0){
			invDetails = new ArrayList<InvDetail>();
			for(String id:ids) {// multiply invDict
				String detailId = id.trim();
				DeptAppDetail deptAppDetail = deptAppDetailDao.get(detailId);
				List<DeptAppDLDetail> deptAppDLDetails = deptAppDLDetailManager.getDeptAppDLDetails(deptApp, deptAppDetail.getInvDict().getInvId());
				if(deptAppDLDetails!=null){
					Map<String,InvDetail> map = new HashMap<String,InvDetail>();
					for(DeptAppDLDetail deptAppDLDetail:deptAppDLDetails){// multiply batch
						String batchId = deptAppDLDetail.getInvBatch().getId();
						if(map.get(batchId)==null){
							invDetail = new InvDetail();
							invDetail.setInvDict(deptAppDetail.getInvDict());
							invDetail.setInvBatch(deptAppDLDetail.getInvBatch());
							invDetail.setInAmount(deptAppDLDetail.getDisAmount());
							invDetail.setInPrice(deptAppDLDetail.getDisPrice());
							invDetail.setInMoney(invDetail.getInAmount()*invDetail.getInPrice());
							map.put(batchId, invDetail);
						}else{
							invDetail = map.get(batchId);
							invDetail.setInAmount(invDetail.getInAmount()+deptAppDLDetail.getDisAmount());
							invDetail.setInMoney(invDetail.getInAmount()*invDetail.getInPrice());
							map.put(batchId, invDetail);
						}
					}
					Set<String> set = map.keySet();
					for(String s:set){
						invDetails.add(map.get(s));
					}
				}
			}
			if(invDetails.size()==0){
				invDetails = null;
			}
		}
		return invDetails;
	}

	@Override
	public List<DeptNeedPlanDetail> getDeptNeedPlanByDis(String deptAppId,String deptAppDetailIds) {
		DeptApp deptApp = deptAppDao.get(deptAppId);
		if(deptAppId!=null){
			String state = deptApp.getAppState();
			if("3".equals(state) || "5".equals(state) || "6".equals(state)){
				return null;
			}
		}
		DeptNeedPlan dnp = new DeptNeedPlan();
		dnp.setStore(deptApp.getStore());
		dnp.setOrgCode(deptApp.getOrgCode());
		dnp.setCopyCode(deptApp.getCopyCode());
		dnp.setPeriodMonth(deptApp.getYearMonth());
		dnp.setDept(deptApp.getAppDept());
		String[] ids = deptAppDetailIds.split(",");
		List<DeptNeedPlanDetail> deptNeedPlanDetails = null;
		DeptNeedPlanDetail deptNeedPlanDetail = null;
		if(ids.length>0){
			deptNeedPlanDetails = new ArrayList<DeptNeedPlanDetail>();
			for(String id:ids){
				String detailId = id.trim();
				DeptAppDetail deptAppDetail = deptAppDetailDao.get(detailId);
				Double needAmount = deptAppDetail.getNoThroughAmount();
				String needNo = deptAppDetail.getNeedNo();
				if(needAmount.equals(new Double(0)) || OtherUtil.measureNotNull(needNo)){
					continue;
				}
				deptNeedPlanDetail = new DeptNeedPlanDetail();
				deptNeedPlanDetail.setInvDict(deptAppDetail.getInvDict());
				deptNeedPlanDetail.setAmount(needAmount);
				deptNeedPlanDetail.setPrice(deptAppDetail.getAppPrice());
				deptNeedPlanDetail.setDeptNeedPlan(dnp);
				deptNeedPlanDetails.add(deptNeedPlanDetail);
			}
			if(deptNeedPlanDetails.size()==0){
				return null;
			}
		}
		return deptNeedPlanDetails;
	}

	@Override
	public void setNeedNoToDetail(String needNo, String deptAppDetailIds,String type) {
		if("save".equals(type)){
			String[] ids = deptAppDetailIds.split(",");
			if(ids.length>0){
				for(String id:ids){
					String detailId = id.trim();
					DeptAppDetail deptAppDetail = deptAppDetailDao.get(detailId);
					deptAppDetail.setNeedNo(needNo);
					deptAppDetailDao.save(deptAppDetail);
				}
			}
		}else if("delete".equals(type)){
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_needNo",needNo));
			List<DeptAppDetail> deptAppDetails = this.getByFilters(filters);
			for(DeptAppDetail deptAppDetail:deptAppDetails){
				deptAppDetail.setNeedNo(null);
				this.save(deptAppDetail);
			}
		}
	}
    
}