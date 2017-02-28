package com.huge.ihos.material.deptplan.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.util.JSONUtils;

import com.huge.exceptions.BillNumCreateException;
import com.huge.ihos.material.deptplan.model.DeptMRSummary;
import com.huge.ihos.material.deptplan.model.DeptMRSummaryDetail;
import com.huge.ihos.material.deptplan.model.DeptNeedPlan;
import com.huge.ihos.material.deptplan.service.DeptMRSummaryManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.util.DateConverter;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class DeptMRSummaryPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8814912860690718840L;
	private DeptMRSummaryManager deptMRSummaryManager;
	private List<DeptMRSummary> deptMRSummaries;
	private DeptMRSummary deptMRSummary;
	private String mrId;
	private String purchaseId;
	private String store;
	private String planType;
	private List<DeptNeedPlan> deptNeedPlans;
	private String deptMRSummaryDetailJson;
	private String storeId;
	private String loadtype;
	
	public void setDeptMRSummaryDetailJson(String deptMRSummaryDetailJson) {
		this.deptMRSummaryDetailJson = deptMRSummaryDetailJson;
	}
	
	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public List<DeptNeedPlan> getDeptNeedPlans() {
		return deptNeedPlans;
	}

	public void setDeptNeedPlans(List<DeptNeedPlan> deptNeedPlans) {
		this.deptNeedPlans = deptNeedPlans;
	}
	public DeptMRSummaryManager getDeptMRSummaryManager() {
		return deptMRSummaryManager;
	}

	public void setDeptMRSummaryManager(DeptMRSummaryManager deptMRSummaryManager) {
		this.deptMRSummaryManager = deptMRSummaryManager;
	}

	public List<DeptMRSummary> getdeptMRSummaries() {
		return deptMRSummaries;
	}

	public void setDeptMRSummarys(List<DeptMRSummary> deptMRSummaries) {
		this.deptMRSummaries = deptMRSummaries;
	}

	public DeptMRSummary getDeptMRSummary() {
		return deptMRSummary;
	}

	public void setDeptMRSummary(DeptMRSummary deptMRSummary) {
		this.deptMRSummary = deptMRSummary;
	}

	public String getMrId() {
		return mrId;
	}

	public void setMrId(String mrId) {
        this.mrId = mrId;
    }
	
	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	public String getLoadtype() {
		return loadtype;
	}

	public void setLoadtype(String loadtype) {
		this.loadtype = loadtype;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String selectDeptMRSummaryList(){
		this.setRandom(this.getRequest().getParameter("random"));
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String deptMRSummaryGridList() {
		log.debug("enter list method!");
		try {
			UserContext userContext = UserContextUtil.getUserContext();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
			if(oper!=null&&oper.equals("nopurchaseId")){
				PropertyFilter purchaseIdfilter=new PropertyFilter("ISNULLS_purchaseId","");
				filters.add(purchaseIdfilter);
				if(storeId!=null&&!storeId.equals("")){
					PropertyFilter storeIdfilter=new PropertyFilter("EQS_store.id",storeId);
					filters.add(storeIdfilter);
				}
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = deptMRSummaryManager
					.getDeptMRSummaryCriteria(pagedRequests,filters);
			this.deptMRSummaries = (List<DeptMRSummary>) pagedRequests.getList();
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
			JSONUtils.getMorpherRegistry().registerMorpher( new DateMorpher(new String[] { "yyyy-MM-dd" }));
			JSONArray jsa = JSONArray.fromObject(this.deptMRSummaryDetailJson);
			DeptMRSummaryDetail[] deptMRSummaryDetails = (DeptMRSummaryDetail[]) JSONArray.toArray(jsa, DeptMRSummaryDetail.class);		    
			deptMRSummary = deptMRSummaryManager.saveDeptMRSummary(deptMRSummary,deptMRSummaryDetails);
		} catch (BillNumCreateException bnce){
			return ajaxForwardError(bnce.getMessage());
		}  catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "deptMRSummary.added" : "deptMRSummary.updated";
		String saveType = this.getRequest().getParameter("saveType");
		if (saveType != null && saveType.equalsIgnoreCase("saveStay")) {
			this.setCallbackType(saveType);
			this.setForwardUrl(deptMRSummary.getMrId());
			return ajaxForward(true, this.getText(key), false);
		} else {
			return ajaxForward(true, this.getText(key), true);
		}
	}
//	private DocumentTemplateManager documentTemplateManager;
//	
//	public void setDocumentTemplateManager(DocumentTemplateManager documentTemplateManager) {
//		this.documentTemplateManager = documentTemplateManager;
//	}
//	private DocumentTemplate docTemp;
//	
//	public DocumentTemplate getDocTemp() {
//		return docTemp;
//	}
    public String edit() {

    	UserContext userContext = UserContextUtil.getUserContext();
    	//String docTemId = this.getRequest().getParameter("docTemId");
        if (mrId != null) {
        	deptMRSummary = deptMRSummaryManager.get(mrId);
//        	docTemId = deptMRSummary.getDocTemId();
//        	if(docTemId!=null && !(docTemId.trim().equals(""))){//useDocTemp
//				docTemp = documentTemplateManager.get(docTemId);
//			}else{//not useDocTemp
//				docTemp = documentTemplateManager.initDocumentTemplate("科室需求计划单",sv.getOrgCode(),sv.getCopyCode());
//			}
        	this.setEntityIsNew(false);
        } else {
//        	if(docTemId!=null){//preview
//        		docTemp = documentTemplateManager.get(docTemId);
//        	}else{//new
//        		docTemp = documentTemplateManager.getDocumentTemplateInUse("科室需求计划单",sv.getOrgCode(),sv.getCopyCode());
//        		docTemId = docTemp.getId();
//        	}
        	deptMRSummary = new DeptMRSummary();
        	deptMRSummary.setOrgCode(userContext.getOrgCode());
        	deptMRSummary.setCopyCode(userContext.getCopyCode());
        	deptMRSummary.setPeriodMonth(userContext.getPeriodMonth());
        	deptMRSummary.setMakePerson(this.getSessionUser().getPerson());
        	deptMRSummary.setMakeDate(userContext.getBusinessDate());
//        	deptMRSummary.setDocTemId(docTemId);
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String deptMRSummaryGridEdit() {
		try {
			if(oper.equals("editpurchaseId")){
				if(mrId!=null){
				StringTokenizer mrIds = new StringTokenizer(mrId,",");
				while (mrIds.hasMoreTokens()) {
					String editId = mrIds.nextToken();
					this.deptMRSummary=deptMRSummaryManager.get(editId);
					this.deptMRSummary.setPurchaseId(purchaseId);
					deptMRSummaryManager.save(this.deptMRSummary);
				}
			}				
			}
			if (oper.equals("del")) {
				List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.deptMRSummaryManager.remove(ida);
				gridOperationMessage = this.getText("deptMRSummary.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkDeptMRSummaryGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
	   if (deptMRSummary == null) {
			return "Invalid deptMRSummary Data";
		}
		return SUCCESS;

	}
}

