package com.huge.ihos.bm.expensectrl.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.bdinfo.util.BdInfoUtil;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.webapp.action.SupcanAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class BmLoanbillAction extends SupcanAction implements Preparable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4310732875669458145L;
	
	public Map<String, String> getOptParamMap(){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("formTable", "bm_loanbill");
		paramMap.put("detailTable", "bm_loanbill_detail");
		paramMap.put("formId", "billId");
		paramMap.put("formCode", "billCode");
		paramMap.put("detailId", "billdetailId");
		paramMap.put("detailPId", "billId");
		paramMap.put("sys", BillNumberConstants.SUBSYSTEM_BM);
		paramMap.put("bus", BillNumberConstants.BM_LOAN_BILL);
		return paramMap;
	}

	public String bmLoanbillList(){
		try {
			BdInfo bdInfo = bdInfoManager.get("bm_loanbill");
			BdInfoUtil bdInfoUtil = new BdInfoUtil();
			bdInfoUtil.addBdInfo(bdInfo);
			chNameMap = bdInfoUtil.getChNameMap();
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public String bmLoanbillGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			BdInfo bdInfo = bdInfoManager.get("bm_loanbill");
			//BdInfo bmSubjBdInfo = bdInfoManager.get("bm_dict_bugetSubj");
			BdInfoUtil bdInfoUtil = new BdInfoUtil();
			//BdInfoUtil bmSubjBdInfoUtil = new BdInfoUtil();
			//bmSubjBdInfoUtil.addBdInfo(bmSubjBdInfo);
			//bdInfoUtil.setOrgJoin(true);
			//bdInfoUtil.setDeptJoin(true);
			//bdInfoUtil.setOnlyShowMain(true);
			//bdInfoUtil.setFilterXT(true);
			//bdInfoUtil.setFilterDisabled(true);
			//bdInfoUtil.setUnionInfilter(true);
			bdInfoUtil.addBdInfo(bdInfo);
			//bdInfoUtil.addJoin(bmSubjBdInfoUtil);
			bdInfoUtil.addFilters(filters);
			pagedRequests = utilOptService.getBdInfoCriteriaWithSearch(pagedRequests, bdInfoUtil, filters);
			this.sdatas = pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public String editBmLoanbill(){
		try {
			super.edit(getOptParamMap() );
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public String saveBmLoanbill(){
		try {
			super.save(getOptParamMap());
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"保存失败！",false);
		}
		return ajaxForward("保存成功！");
	}
	
	public String delBmLoanbill(){
		try {
			super.del(getOptParamMap());
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"删除失败！",false);
		}
		return ajaxForward("删除成功！");
	}
	
	private int state;
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String changeBmLoanbillState(){
		String stateType = "";
		switch (state) {
		case 1:
			stateType = "审核";
			break;
		case 2:
			stateType = "记账";
			break;
		case 3:
			stateType = "作废";
			break;
		default:
			break;
		}
		try {
			String[] suIdArr = suId.split(",");
			for(String idd : suIdArr){
				idd = idd.trim();
				utilOptService.executeSql("update bm_loanbill set state="+state+" where billId='"+idd+"'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,stateType+"失败！",false);
		}
		return ajaxForward(stateType+"成功！");
	}

}
