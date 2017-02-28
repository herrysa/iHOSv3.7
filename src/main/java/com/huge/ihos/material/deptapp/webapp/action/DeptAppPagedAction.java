package com.huge.ihos.material.deptapp.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huge.exceptions.BillNumCreateException;
import com.huge.ihos.material.deptapp.model.DeptApp;
import com.huge.ihos.material.deptapp.model.DeptAppDetail;
import com.huge.ihos.material.deptapp.model.DeptAppDis;
import com.huge.ihos.material.deptapp.model.DeptAppDis.BatchDis;
import com.huge.ihos.material.deptapp.service.DeptAppDetailManager;
import com.huge.ihos.material.deptapp.service.DeptAppManager;
import com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail;
import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.ihos.material.documenttemplate.service.DocumentTemplateManager;
import com.huge.ihos.material.model.InvBillNumberSetting;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.DateConverter;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class DeptAppPagedAction extends JqGridBaseAction implements Preparable {
	private static final long serialVersionUID = -7242489949258552541L;
	
	private DeptAppManager deptAppManager;
	private List<DeptApp> deptApps;
	private DeptApp deptApp;
	private String deptAppId;
	//private InvBillNumberManager invBillNumberManager;
	private BillNumberManager billNumberManager;
	private DeptAppDetailManager deptAppDetailManager;
	
	public void setDeptAppDetailManager(DeptAppDetailManager deptAppDetailManager) {
		this.deptAppDetailManager = deptAppDetailManager;
	}

	public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}

	public void setDeptAppManager(DeptAppManager deptAppManager) {
		this.deptAppManager = deptAppManager;
	}

	public List<DeptApp> getDeptApps() {
		return deptApps;
	}

	public DeptApp getDeptApp() {
		return deptApp;
	}

	public void setDeptApp(DeptApp deptApp) {
		this.deptApp = deptApp;
	}

	public String getDeptAppId() {
		return deptAppId;
	}

	public void setDeptAppId(String deptAppId) {
        this.deptAppId = deptAppId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	
	@SuppressWarnings("unchecked")
	public String deptAppGridList() {
		log.debug("enter deptAppGridList method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			//SystemVariable systemVariable = this.getCurrentSystemVariable();
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = deptAppManager.getDeptAppCriteria(pagedRequests,filters);
			this.deptApps = (List<DeptApp>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
		} catch (Exception e) {
			log.error("deptAppGridList Error", e);
		}
		return SUCCESS;
	}
	
	private String deptAppDetailJson;
	
	public void setDeptAppDetailJson(String deptAppDetailJson) {
		this.deptAppDetailJson = deptAppDetailJson;
	}

	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			JSONArray jsa = JSONArray.fromObject(this.deptAppDetailJson);
			DeptAppDetail[] deptAppDetails = (DeptAppDetail[]) JSONArray.toArray(jsa, DeptAppDetail.class);
			UserContext userContext = UserContextUtil.getUserContext();
			deptApp = deptAppManager.saveDeptApp(deptApp,deptAppDetails,userContext.getBusinessDateStr(),this.getSessionUser().getPerson());
		} catch (BillNumCreateException bnce){
			return ajaxForwardError(bnce.getMessage());
		}  catch (Exception dre) {
			log.error("deptAppSave Error", dre);
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "deptApp.added" : "deptApp.updated";
		String saveType = this.getRequest().getParameter("saveType");
		if (saveType != null && saveType.equalsIgnoreCase("saveStay")) {
			this.setCallbackType(saveType);
			this.setForwardUrl(deptApp.getDeptAppId());
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
		String orgCode = userContext.getOrgCode();
		String copyCode = userContext.getCopyCode();
		String period = userContext.getPeriodMonth();
    	String docTemId = this.getRequest().getParameter("docTemId");
        if (deptAppId != null) {//edit
        	deptApp = deptAppManager.get(deptAppId);
        	docTemId = deptApp.getDocTemId();
        	if(docTemId!=null && !(docTemId.trim().equals(""))){//useDocTemp
				docTemp = documentTemplateManager.get(docTemId);
			}else{//not useDocTemp
				docTemp = documentTemplateManager.initDocumentTemplate("科室申领单",orgCode,copyCode);
			}
        	this.setEntityIsNew(false);
        } else {
        	if(docTemId!=null){//preview
        		docTemp = documentTemplateManager.get(docTemId);
        	}else{//new
        		docTemp = documentTemplateManager.getDocumentTemplateInUse("科室申领单",orgCode,copyCode);
        		docTemId = docTemp.getId();
        	}
        	deptApp = new DeptApp();
        	deptApp.setOrgCode(orgCode);
        	deptApp.setCopyCode(copyCode);
        	deptApp.setYearMonth(period);
        	deptApp.setAppState("0");
        	deptApp.setAppDate((Date) (new DateConverter().convert(Date.class,userContext.getBusinessDateStr())));
        	deptApp.setDocTemId(docTemId);
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String beforeCreate(){
		String deptAppDetailIds = this.getRequest().getParameter("deptAppDetailIds");
		String docTpye = this.getRequest().getParameter("docType");
		if("deptAppNeed".equals(docTpye)){
			List<DeptNeedPlanDetail> deptNeedPlanDetails = deptAppDetailManager.getDeptNeedPlanByDis(deptAppId,deptAppDetailIds);
			if(deptNeedPlanDetails==null){
				return ajaxForward(false, "没有需要处理的数据", false);
			}
		}else{
			List<InvDetail> invDetails = deptAppDetailManager.getInvOutDetailByDisLog(deptAppId,deptAppDetailIds);
			if(invDetails==null){
				return ajaxForward(false, "没有需要处理的数据", false);
			}
		}
		return SUCCESS;
	}
	public String deptAppDetailPass(){
		Map<String,List<DeptAppDis>> map = parsePassJson();
		if(map==null){
			return ajaxForward(false, "没有需要通过的数据", false);
		}
		try {
			Person curPerson = this.getSessionUser().getPerson();
			this.deptAppManager.executePass(map,curPerson);
			return ajaxForward(true, "本次通过成功", false);
		} catch (Exception e) {
			log.error("deptAppDetailPass Error", e);
			return ajaxForward(false, "本次通过失败", false);
		}
    }
	private String storeId;
	private String invDictId;
	private String subGridId;
	private String subRowId;
	
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getInvDictId() {
		return invDictId;
	}

	public void setInvDictId(String invDictId) {
		this.invDictId = invDictId;
	}

	public String getSubGridId() {
		return subGridId;
	}

	public void setSubGridId(String subGridId) {
		this.subGridId = subGridId;
	}

	public String getSubRowId() {
		return subRowId;
	}

	public void setSubRowId(String subRowId) {
		this.subRowId = subRowId;
	}

	private String deptAppPassJson;
	
    public void setDeptAppPassJson(String deptAppPassJson) {
		this.deptAppPassJson = deptAppPassJson;
	}
	@SuppressWarnings("unchecked")
	private Map<String,List<DeptAppDis>> parsePassJson(){
		Map<String,List<DeptAppDis>> result = null;
		JSONObject jso = JSONObject.fromObject(deptAppPassJson);
		JSONArray jsa = jso.getJSONArray("elements");
		if(jsa.size()>0){
			result = new HashMap<String,List<DeptAppDis>>();
			Object[] objects = jsa.toArray();
			Map<String,Object> map = null;
			String deptAppId = null;
			JSONArray details = null;
			List<DeptAppDis> deptAppDiss = null;
			for(Object obj:objects){//申领单主单集合
				map = (Map<String,Object>)obj;
				deptAppId = (String)map.get("key");
				details = (JSONArray)map.get("value");//数组
				deptAppDiss = parseBatchPassJson(details);
				result.put(deptAppId, deptAppDiss);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private List<DeptAppDis> parseBatchPassJson(JSONArray jsa){
		List<DeptAppDis> deptAppDiss = null;
		if(jsa.size()>0){
			deptAppDiss = new ArrayList<DeptAppDis>();
			DeptAppDis deptAppDis = null;
			Object[] objects = jsa.toArray();
			for(int i=0;i<objects.length;i++){//主单的明细集合
				JSONObject obj = (JSONObject)objects[i];
				JSONArray arr = obj.getJSONArray("elements");
				if(arr.size()>0 && arr.size()==1){
					deptAppDis = new DeptAppDis();
					Map<String,Object> map = (Map<String,Object>)arr.get(0);
					String deptAppDetailId = (String)map.get("key");
					Map<String,Object> deptAppDetailInfo = (Map<String,Object>)map.get("value");//数组
					Double throughAmount = Double.parseDouble(deptAppDetailInfo.get("throughAmount").toString());
					String remark = (String)deptAppDetailInfo.get("remark");
					deptAppDis.setDeptAppDetailId(deptAppDetailId);
					deptAppDis.setThroughAmount(throughAmount);
					deptAppDis.setRemark(remark);
					
					JSONArray array = (JSONArray)deptAppDetailInfo.get("batchDetail");
					Set<BatchDis> batchDiss = null;
					if(array.size()>0){
						Object[] batchs = array.toArray();
						batchDiss = new HashSet<BatchDis>(); 
						for(int j=0;j<batchs.length;j++){
							JSONObject batch =(JSONObject)batchs[j];
							JSONArray jsaa = batch.getJSONArray("elements");
							if(jsaa.size()>0 && jsaa.size()==1){
								map = (Map<String,Object>)jsaa.get(0);
								BatchDis batchDis = deptAppDis.new BatchDis();
								String batchId = (String)map.get("key");
								Map<String,Object> disInfo = (Map<String,Object>)map.get("value");
								Double disAmount = Double.parseDouble(disInfo.get("disAmount").toString());
								Double disPrice = Double.parseDouble(disInfo.get("disPrice").toString());
								batchDis.setBatchId(batchId);
								batchDis.setDisAmount(disAmount);
								batchDis.setDisPrice(disPrice);
								batchDiss.add(batchDis);
							}
						}
						deptAppDis.setBatchDiss(batchDiss);
					}
				}
				deptAppDiss.add(deptAppDis);
			}
		}
		return deptAppDiss;
	}
	
	public String deptAppGridEdit() {
		try {
			UserContext userContext = UserContextUtil.getUserContext();
			Person curPerson = this.getSessionUser().getPerson();
			Date curDate = userContext.getBusinessDate();
			StringTokenizer ids = new StringTokenizer(id,",");
			deptApps = new ArrayList<DeptApp>();
			if (oper.equals("del")) {
				boolean isLastNumber = true;
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					deptApp = this.deptAppManager.get(removeId);
					//isLastNumber = invBillNumberManager.isLastNumber(deptApp.getAppNo(), InvBillNumberSetting.DEPT_APP, userContext.getOrgCode(), userContext.getCopyCode(), userContext.getPeriodMonth());
					isLastNumber = true;
					if(!isLastNumber){
						return ajaxForward(false, "只能删除最后一条新建记录!", false);
					}else{
						this.deptAppManager.remove(removeId);
					}
				}
				gridOperationMessage = this.getText("deptApp.deleted");
			}else if(oper.equals("check")){
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					deptApp = deptAppManager.get(checkId);
					deptApp.setAppState("1");
					deptApp.setAppChecker(curPerson);
					deptApp.setCheckDate(curDate);
					deptApps.add(deptApp);
				}
				this.deptAppManager.saveAll(deptApps);
				gridOperationMessage = this.getText("deptApp.checked");
			}else if(oper.equals("cancelCheck")){
				while (ids.hasMoreTokens()) {
					String cancelId = ids.nextToken();
					deptApp = deptAppManager.get(cancelId);
					deptApp.setAppState("0");
					deptApp.setAppChecker(null);
					deptApp.setCheckDate(null);
					deptApps.add(deptApp);
				}
				this.deptAppManager.saveAll(deptApps);
				gridOperationMessage = this.getText("deptApp.cancelChecked");
			}else if(oper.equals("send")){
				while (ids.hasMoreTokens()) {
					String sendId = ids.nextToken();
					deptApp = deptAppManager.get(sendId);
					deptApp.setAppState("3");
					deptApp.setAppConfirmer(curPerson);
					deptApp.setConfirmDate(curDate);
					deptApps.add(deptApp);
				}
				this.deptAppManager.saveAll(deptApps);
				gridOperationMessage = this.getText("deptApp.sended");
			}else if(oper.equals("back")){
				while (ids.hasMoreTokens()) {
					String backId = ids.nextToken();
					deptApp = deptAppManager.get(backId);
					deptApp.setAppState("0");
					deptApp.setAppConfirmer(null);
					deptApp.setConfirmDate(null);
					deptApp.setCheckDate(null);
					deptApp.setAppChecker(null);
					deptApp.setRemark("库房退回");
					deptApps.add(deptApp);
				}
				this.deptAppManager.saveAll(deptApps);
				gridOperationMessage = this.getText("deptApp.backed");
			}else if(oper.equals("end")){
				while (ids.hasMoreTokens()) {
					String backId = ids.nextToken();
					deptApp = deptAppManager.get(backId);
					deptApp.setStoreChecker(curPerson);
					deptApp.setStoreCheckDate(curDate);
					deptApps.add(deptApp);
				}
				this.deptAppManager.endDeptApp(deptApps);
				gridOperationMessage = this.getText("deptApp.ended");
			}else if(oper.equals("abandon")){
				while (ids.hasMoreTokens()) {
					String sendId = ids.nextToken();
					deptApp = deptAppManager.get(sendId);
					deptApp.setAppState("2");
					deptApps.add(deptApp);
				}
				this.deptAppManager.saveAll(deptApps);
				gridOperationMessage = this.getText("deptApp.abandoned");
			}else if(oper.equals("copy") || oper.equals("import")){
				List<String> idl = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String copyId = ids.nextToken();
					idl.add(copyId);
				}
				this.deptAppManager.createFromHis(idl,curDate,curPerson);
				gridOperationMessage = (oper.equals("copy")?"复制":"引入")+"成功";
			}
			return ajaxForward(true, gridOperationMessage, false);
		} catch (Exception e) {
			log.error("checkDeptAppGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (deptApp == null) {
			return "Invalid deptApp Data";
		}
		return SUCCESS;
	}
}

