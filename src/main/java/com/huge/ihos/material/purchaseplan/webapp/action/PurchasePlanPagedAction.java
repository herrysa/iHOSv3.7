package com.huge.ihos.material.purchaseplan.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.util.JSONUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huge.exceptions.BillNumCreateException;
import com.huge.ihos.material.deptplan.model.DeptMRSummary;
import com.huge.ihos.material.deptplan.service.DeptMRSummaryManager;
import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.ihos.material.documenttemplate.service.DocumentTemplateManager;
import com.huge.ihos.material.model.InvBillNumberSetting;
import com.huge.ihos.material.order.model.OrderDetail;
import com.huge.ihos.material.purchaseplan.model.PurchasePlan;
import com.huge.ihos.material.purchaseplan.model.PurchasePlanDetail;
import com.huge.ihos.material.purchaseplan.service.PurchasePlanManager;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class PurchasePlanPagedAction extends JqGridBaseAction implements Preparable {
	private static final long serialVersionUID = -5778650563422605227L;
	
	private PurchasePlanManager purchasePlanManager;
	private List<PurchasePlan> purchasePlans;
	private PurchasePlan purchasePlan;
	private String purchaseId;
	private String purchasePlanDetailJson;
	private String storeId;
	private BillNumberManager billNumberManager;
	
	private DeptMRSummaryManager deptMRSummaryManager;
	
	public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
	
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public void setPurchasePlanDetailJson(String purchasePlanDetailJson) {
		this.purchasePlanDetailJson = purchasePlanDetailJson;
	}

	public void setPurchasePlanManager(PurchasePlanManager purchasePlanManager) {
		this.purchasePlanManager = purchasePlanManager;
	}

	public List<PurchasePlan> getPurchasePlans() {
		return purchasePlans;
	}

	public void setPurchasePlans(List<PurchasePlan> purchasePlans) {
		this.purchasePlans = purchasePlans;
	}

	public PurchasePlan getPurchasePlan() {
		return purchasePlan;
	}

	public void setPurchasePlan(PurchasePlan purchasePlan) {
		this.purchasePlan = purchasePlan;
	}

	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }
	
	public DeptMRSummaryManager getDeptMRSummaryManager() {
		return deptMRSummaryManager;
	}

	public void setDeptMRSummaryManager(DeptMRSummaryManager deptMRSummaryManager) {
		this.deptMRSummaryManager = deptMRSummaryManager;
	}


	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String purchasePlanGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = purchasePlanManager
					.getPurchasePlanCriteria(pagedRequests,filters);
			this.purchasePlans = (List<PurchasePlan>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			Gson gson = new GsonBuilder()
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
			.create();
			PurchasePlanDetail[] purchasePlanDetails = gson.fromJson(this.purchasePlanDetailJson, PurchasePlanDetail[].class);
			/*JSONUtils.getMorpherRegistry().registerMorpher( new DateMorpher(new String[] { "yyyy-MM-dd" }));
			JSONArray jsa = JSONArray.fromObject(this.purchasePlanDetailJson);
			PurchasePlanDetail[] purchasePlanDetails = (PurchasePlanDetail[]) JSONArray.toArray(jsa, PurchasePlanDetail.class);*/
			purchasePlan = purchasePlanManager.savePurchasePlan(purchasePlan,purchasePlanDetails);
		} catch (BillNumCreateException bnce){
			return ajaxForwardError(bnce.getMessage());
		}  catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "purchasePlan.added" : "purchasePlan.updated";
		String saveType = this.getRequest().getParameter("saveType");
		if (saveType != null && saveType.equalsIgnoreCase("saveStay")) {
			this.setCallbackType(saveType);
			this.setForwardUrl(purchasePlan.getPurchaseId());
			return ajaxForward(true, this.getText(key), false);
		} else {
			return ajaxForward(true, this.getText(key), true);
		}
	}
	private DocumentTemplateManager documentTemplateManager;
	
	public void setDocumentTemplateManager(DocumentTemplateManager documentTemplateManager) {
		this.documentTemplateManager = documentTemplateManager;
	}
	
	private DocumentTemplate docTemp;
	
	public DocumentTemplate getDocTemp() {
		return docTemp;
	}
	
    public String edit() {
    	UserContext userContext = UserContextUtil.getUserContext();
    	String docTemId = this.getRequest().getParameter("docTemId");
        if (purchaseId != null) {
        	purchasePlan = purchasePlanManager.get(purchaseId);
        	docTemId = purchasePlan.getDocTemId();
        	if(docTemId!=null && !(docTemId.trim().equals(""))){//useDocTemp
				docTemp = documentTemplateManager.get(docTemId);
			}else{//not useDocTemp
				docTemp = documentTemplateManager.initDocumentTemplate("采购计划单",userContext.getOrgCode(),userContext.getCopyCode());
			}
        	this.setEntityIsNew(false);
        } else {
        	if(docTemId!=null){//preview
        		docTemp = documentTemplateManager.get(docTemId);
        	}else{//new
        		docTemp = documentTemplateManager.getDocumentTemplateInUse("采购计划单",userContext.getOrgCode(),userContext.getCopyCode());
        		docTemId = docTemp.getId();
        	}
        	purchasePlan = new PurchasePlan();
        	purchasePlan.setOrgCode(userContext.getOrgCode());
        	purchasePlan.setCopyCode(userContext.getCopyCode());
        	purchasePlan.setPeriodMonth(userContext.getPeriodMonth());
        	purchasePlan.setMakePerson(this.getSessionUser().getPerson());
        	purchasePlan.setMakeDate(userContext.getBusinessDate());
        	purchasePlan.setState("0");
        	purchasePlan.setDocTemId(docTemId);
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    
    public String createPurchasePlanByNeed(){
    	try {
			StringTokenizer ids = new StringTokenizer(id,",");
			List<String> needIds = new ArrayList<String>();
			while (ids.hasMoreTokens()) {
				String extendId = ids.nextToken();
				needIds.add(extendId);
			}
			storeId = "t0123_02";
			purchasePlan = purchasePlanManager.createPurchasePlanByNeed(needIds,storeId,this.getSessionUser().getPerson());
			purchaseId = purchasePlan.getPurchaseId();
			gridOperationMessage = this.getText("采购计划已生成,请编辑");
			return ajaxForward(true, gridOperationMessage, false);
		}catch (BillNumCreateException bnce){
			return ajaxForwardError(bnce.getMessage());
		}catch (Exception e) {
			log.error("extendPurchasePlan Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
    }
    public String createPurchasePlanByBase(){
    	try {
			HttpServletRequest request = this.getRequest();
			Map<String,String> conditions = new HashMap<String,String>();
			conditions.put("deptId", request.getParameter("deptId"));
			conditions.put("purchaserId", request.getParameter("purchaserId"));
			conditions.put("storeId", storeId);
			conditions.put("invTypeId", request.getParameter("invTypeId"));
			conditions.put("makeDate",request.getParameter("makeDate"));
			conditions.put("arrivalDate", request.getParameter("arrivalDate"));
			conditions.put("makepersonId", this.getSessionUser().getId().toString());
			UserContext userContext = UserContextUtil.getUserContext();
			purchasePlan = purchasePlanManager.createPurchasePlanByBase(conditions,this.getSessionUser().getPerson());
			if(purchasePlan == null){
				purchaseId= "";
				gridOperationMessage = this.getText("该条件下没有相应数据");
				return ajaxForwardError(gridOperationMessage);
			}else{
				if(purchasePlan.getPurchasePlanDetails()==null){
					purchaseId=null;
					gridOperationMessage = this.getText("该条件下没有相应数据");
					return ajaxForwardError(gridOperationMessage);
				}else{
				purchaseId = purchasePlan.getPurchaseId();
				gridOperationMessage = this.getText("采购计划已生成,请编辑");
				}
			}
			return ajaxForward(true, gridOperationMessage, false);
		}catch (BillNumCreateException bnce){
			return ajaxForwardError(bnce.getMessage());
		}catch (Exception e) {
			log.error("extendPurchasePlan Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
    }
	public String purchasePlanGridEdit() {
		try {
			UserContext userContext = UserContextUtil.getUserContext();
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,",");
				boolean isLastNumber = true;
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					purchasePlan = this.purchasePlanManager.get(removeId);
					//isLastNumber = invBillNumberManager.isLastNumber(purchasePlan.getPurchaseNo(), InvBillNumberSetting.PURCHASE_PLAN, userContext.getOrgCode(), userContext.getCopyCode(), userContext.getPeriodMonth());
					
					if(!isLastNumber){
						return ajaxForward(false, "只能删除最后一条新建记录!", false);
					}else{
						List<PropertyFilter> purchaseIdfilters = new ArrayList<PropertyFilter>();
						PropertyFilter purchaseIdFilter = new PropertyFilter("EQS_purchaseId",removeId);
						purchaseIdfilters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
						purchaseIdfilters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
						purchaseIdfilters.add(purchaseIdFilter);
						List<DeptMRSummary> deptMRSummarys = deptMRSummaryManager.getByFilters(purchaseIdfilters);
						if(deptMRSummarys!=null){
							for(DeptMRSummary deptMRSummarytemp:deptMRSummarys){
								deptMRSummarytemp.setPurchaseId(null);
								deptMRSummaryManager.save(deptMRSummarytemp);
							}
						}
						this.purchasePlanManager.remove(removeId);
					}
				}
				gridOperationMessage = this.getText("purchasePlan.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("check")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				purchasePlanManager.auditPurchasePlan(checkIds,this.getSessionUser().getPerson(),userContext.getBusinessDate());
				gridOperationMessage = this.getText("审核成功");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("cancelCheck")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> cancelCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					cancelCheckIds.add(cancelCheckId);
				}
				purchasePlanManager.antiAuditPurchasePlan(cancelCheckIds);
				gridOperationMessage = this.getText("销审成功");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("stop")){
				StringTokenizer ids = new StringTokenizer(id,",");
				purchasePlans = new ArrayList<PurchasePlan>();
				while (ids.hasMoreTokens()) {
					String stopId = ids.nextToken();
					purchasePlan = purchasePlanManager.get(stopId);
					purchasePlan.setState("3");
					purchasePlans.add(purchasePlan);
				}
				purchasePlanManager.saveAll(purchasePlans);
				gridOperationMessage = this.getText("中止计划成功");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("abandon")){
				StringTokenizer ids = new StringTokenizer(id,",");
				purchasePlans = new ArrayList<PurchasePlan>();
				while (ids.hasMoreTokens()) {
					String stopId = ids.nextToken();
					purchasePlan = purchasePlanManager.get(stopId);
					purchasePlan.setState("4");
					purchasePlans.add(purchasePlan);
				}
				purchasePlanManager.saveAll(purchasePlans);
				gridOperationMessage = this.getText("作废计划成功");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("copy")){
				StringTokenizer ids = new StringTokenizer(id,",");
				purchasePlans = new ArrayList<PurchasePlan>();
				while(ids.hasMoreTokens()){
					String copyId = ids.nextToken();
					purchasePlan = this.purchasePlanManager.get(copyId);
					//String tempPurchasePlanNo = invBillNumberManager.createNextBillNumber(InvBillNumberSetting.PURCHASE_PLAN, userContext.getOrgCode(), userContext.getCopyCode(),purchasePlan.getPeriodMonth(),true);
					String tempPurchasePlanNo = billNumberManager.createNextBillNumber("MM",InvBillNumberSetting.PURCHASE_PLAN, true, userContext.getOrgCode(), userContext.getCopyCode(),purchasePlan.getPeriodMonth());
					docTemp = documentTemplateManager.getDocumentTemplateInUse("采购计划单",userContext.getOrgCode(),userContext.getCopyCode());
					PurchasePlan purchasePlanTemp = new PurchasePlan();
					purchasePlanTemp = purchasePlan.clone();
					purchasePlanTemp.setPurchaseId(null);
					purchasePlanTemp.setPurchaseNo(tempPurchasePlanNo);
					purchasePlanTemp.setOrderNo(null);
					purchasePlanTemp.setState("0");
					purchasePlanTemp.setCheckDate(null);
					purchasePlanTemp.setCheckPerson(null);
					purchasePlanTemp.setMakeDate(userContext.getBusinessDate());
					purchasePlanTemp.setMakePerson(this.getSessionUser().getPerson());
					Set<PurchasePlanDetail> purchasePlanDetailSetOld = purchasePlan.getPurchasePlanDetails();
					Set<PurchasePlanDetail> purchasePlanDetailSetNew = new HashSet<PurchasePlanDetail>();
					for(PurchasePlanDetail purchasePlanDetailTemp:purchasePlanDetailSetOld){
						PurchasePlanDetail purchasePlanDetailTempClone = new PurchasePlanDetail();
						purchasePlanDetailTempClone = purchasePlanDetailTemp.clone();
						purchasePlanDetailTempClone.setPurchaseDetailId(null);
						purchasePlanDetailTempClone.setPurchasePlan(purchasePlanTemp);
						purchasePlanDetailSetNew.add(purchasePlanDetailTempClone);
					}
					purchasePlanTemp.setPurchasePlanDetails(purchasePlanDetailSetNew);
					purchasePlans.add(purchasePlanTemp);
				}
				purchasePlanManager.saveAll(purchasePlans);
				gridOperationMessage = this.getText("复制成功");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPurchasePlanGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (purchasePlan == null) {
			return "Invalid purchasePlan Data";
		}

		return SUCCESS;

	}
}

