package com.huge.ihos.material.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huge.exceptions.BillNumCreateException;
import com.huge.ihos.material.businessType.model.MmBusinessType;
import com.huge.ihos.material.businessType.service.MmBusinessTypeManager;
import com.huge.ihos.material.deptapp.model.DeptApp;
import com.huge.ihos.material.deptapp.service.DeptAppManager;
import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.ihos.material.documenttemplate.service.DocumentTemplateManager;
import com.huge.ihos.material.model.InvBalanceBatch;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.model.InvMain;
import com.huge.ihos.material.service.InvBalanceBatchManager;
import com.huge.ihos.material.service.InvDetailManager;
import com.huge.ihos.material.service.InvMainManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.util.DateConverter;
import com.huge.util.DateUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class InvMainOutPagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = -837825550218144649L;
	private InvMainManager invMainManager;
	private InvDetailManager invDetailManager;
	private List<InvMain> invMains;
	private InvMain invMain;
	private MmBusinessTypeManager mmBusinessTypeManager;
	private String storeId;
	private String ioId;
	private String ioType;
	public String getIoType() {
		return ioType;
	}
	public void setIoType(String ioType) {
		this.ioType = ioType;
	}

	private String invDetailJson;
	private List<MmBusinessType> busTypeList;
	private List<HashMap<String,String>> invOutList;
	//private String userdata;
	private String currentPeriodBeginDate;
	private InvBalanceBatchManager invBalanceBatchManager;
	private DocumentTemplateManager documentTemplateManager;
	
	public void setDocumentTemplateManager(
			DocumentTemplateManager documentTemplateManager) {
		this.documentTemplateManager = documentTemplateManager;
	}
	public void setInvBalanceBatchManager(
			InvBalanceBatchManager invBalanceBatchManager) {
		this.invBalanceBatchManager = invBalanceBatchManager;
	}
	public String getCurrentPeriodBeginDate() {
		return currentPeriodBeginDate;
	}
	/*public String getUserdata() {
		return userdata;
	}*/
	

	public void prepare() throws Exception {
		this.clearSessionMessages();
		UserContext userContext = UserContextUtil.getUserContext();
		currentPeriodBeginDate = userContext.getPeriodBeginDateStr();
	}

	public String invMainOutList(){
		try {
			busTypeList = mmBusinessTypeManager.getBusTypeByIo("2");
			
		} catch (Exception e) {
			log.error(e);
		}
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String invMainGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext
					.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext
					.getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = invMainManager.getInvMainCriteria(pagedRequests, filters);
			this.invMains = (List<InvMain>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
			double ss = 0;
			for(InvMain invOut:invMains){
				ss += invOut.getTotalMoney();
			}
			getUserdata().put("sum",""+ss);
            //userdata = ""+ss;
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String save() {

		HttpServletRequest req = this.getRequest();

		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			InvDetail[] ids = gson.fromJson(this.invDetailJson, InvDetail[].class);
			String saveFrom = req.getParameter("saveFrom");
			//JSONArray jsa = JSONArray.fromObject(this.invDetailJson);
			//InvDetail[] ids = (InvDetail[]) JSONArray.toArray(jsa, InvDetail.class);
			if("deptAppOut".equals(saveFrom)){
				invMain = invMainManager.saveInvOutFromDeptApp(invMain,ids,deptAppId,deptAppDetailIds);
			}else{
				invMain = invMainManager.saveInvMain(invMain, ids);// save(invMain);
			}
			
		} catch (BillNumCreateException bnce){
			return ajaxForwardError(bnce.getMessage());
		}  catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		
		String key = ((this.isEntityIsNew())) ? docType+"添加成功" : docType+"修改成功";
		String saveType = req.getParameter("saveType");
		if (saveType == null) {
			return ajaxForward(true, this.getText(key), true);
		} else if (saveType.equalsIgnoreCase("saveStay") || saveType.equalsIgnoreCase("saveNew")) {
			this.setCallbackType(saveType);
			this.setForwardUrl(invMain.getIoId());
			return ajaxForward(true, this.getText(key), false);
		} else {
			return ajaxForward(true, this.getText(key), true);
		}

	}

	private DocumentTemplate docTemp;
	
	public DocumentTemplate getDocTemp() {
		return docTemp;
	}
	private String docType;
	
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String edit() {
		UserContext userContext = UserContextUtil.getUserContext();
		String docTemId = this.getRequest().getParameter("docTemId");
		if (ioId != null) {
			invMain = invMainManager.get(ioId);
			docTemId = invMain.getDocTemId();
        	if(docTemId!=null && !(docTemId.trim().equals(""))){//useDocTemp
				docTemp = documentTemplateManager.get(docTemId);
			}else{//not useDocTemp
				docTemp = documentTemplateManager.initDocumentTemplate(docType,userContext.getOrgCode(),userContext.getCopyCode());
			}
			this.setEntityIsNew(false);
		} else {
			if(docTemId!=null){//preview
        		docTemp = documentTemplateManager.get(docTemId);
        	}else{//new
        		docTemp = documentTemplateManager.getDocumentTemplateInUse(docType,userContext.getOrgCode(),userContext.getCopyCode());
        		docTemId = docTemp.getId();
        	}
			invMain = new InvMain();
			invMain.setOrgCode(userContext.getOrgCode());
			invMain.setCopyCode(userContext.getCopyCode());
			DateConverter dc = new DateConverter();
			invMain.setMakeDate(userContext.getBusinessDate());
			invMain.setMakePerson(this.getSessionUser().getPerson());

			invMain.setYearMonth(DateUtil.convertDateToString("yyyyMM", invMain.getMakeDate()));
			invMain.setIoType("2");
			invMain.setBusType(mmBusinessTypeManager.get("2"));
			invMain.setDocTemId(docTemId);
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}
	
	public String editMove() {
		UserContext userContext = UserContextUtil.getUserContext();
		String docTemId = this.getRequest().getParameter("docTemId");
	
		if (ioId != null) {
			invMain = invMainManager.get(ioId);
			docTemId = invMain.getDocTemId();
        	if(docTemId!=null && !(docTemId.trim().equals(""))){//useDocTemp
				docTemp = documentTemplateManager.get(docTemId);
			}else{//not useDocTemp
				docTemp = documentTemplateManager.initDocumentTemplate(docType,userContext.getOrgCode(),userContext.getCopyCode());
			}
			this.setEntityIsNew(false);
		} else {
			if(docTemId!=null){//preview
        		docTemp = documentTemplateManager.get(docTemId);
        	}else{//new
        		docTemp = documentTemplateManager.getDocumentTemplateInUse(docType,userContext.getOrgCode(),userContext.getCopyCode());
        		docTemId = docTemp.getId();
        	}
			invMain = new InvMain();
			invMain.setOrgCode(userContext.getOrgCode());
			invMain.setCopyCode(userContext.getCopyCode());
			DateConverter dc = new DateConverter();
			invMain.setMakeDate(userContext.getBusinessDate());
			invMain.setMakePerson(this.getSessionUser().getPerson());

			invMain.setYearMonth(DateUtil.convertDateToString("yyyyMM", invMain.getMakeDate()));
			invMain.setIoType("4");
			invMain.setBusType(mmBusinessTypeManager.get("14"));
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}
	
	private DeptAppManager deptAppManager;
	
	public void setDeptAppManager(DeptAppManager deptAppManager) {
		this.deptAppManager = deptAppManager;
	}
	private String deptAppDetailIds;
	private String deptAppId;
	
	public String getDeptAppDetailIds() {
		return deptAppDetailIds;
	}
	
	public String getDeptAppId() {
		return deptAppId;
	}
	
	public void setDeptAppDetailIds(String deptAppDetailIds) {
		this.deptAppDetailIds = deptAppDetailIds;
	}
	public void setDeptAppId(String deptAppId) {
		this.deptAppId = deptAppId;
	}
	public String createInvOutByDistribute(){
		DeptApp deptApp = deptAppManager.get(deptAppId);
		UserContext userContext = UserContextUtil.getUserContext();
		docTemp = documentTemplateManager.getDocumentTemplateInUse(docType,userContext.getOrgCode(),userContext.getCopyCode());
		invMain = new InvMain();
		invMain.setOrgCode(deptApp.getOrgCode());
		invMain.setCopyCode(deptApp.getCopyCode());
		DateConverter dc = new DateConverter();
		invMain.setMakeDate((Date) (dc.convert(Date.class, userContext.getBusinessDate())));
		invMain.setMakePerson(this.getSessionUser().getPerson());
		invMain.setYearMonth(DateUtil.convertDateToString("yyyyMM", invMain.getMakeDate()));
		invMain.setDocTemId(docTemp.getId());
		invMain.setStore(deptApp.getStore());
		invMain.setRemark("申领单号--"+deptApp.getAppNo());
		if("出库单".equals(docType)){
			invMain.setIoType("2");
			invMain.setBusType(mmBusinessTypeManager.get("2"));
			invMain.setUsedDept(deptApp.getAppDept());
			invMain.setApplyDept(deptApp.getAppDept());
			invMain.setApplyPersion(deptApp.getAppPerson());
			return "deptAppOut";
		}else if("移库单".equals(docType)){
			invMain.setIoType("4");
			invMain.setBusType(mmBusinessTypeManager.get("14"));
			return "deptAppMove";
		}
		return SUCCESS;
	}
	public String invMainGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = (ids.nextToken());
					log.debug("Delete Customer " + removeId);
					invMainManager.removeInvMainOut(new String(removeId));
				}
				if("3".equals(ioType)||"4".equals(ioType)){
					this.setMessage("移库单删除成功。");
				}else{
					this.setMessage("出库单删除成功");
				}
				return ajaxForward(true, this.getMessage(), false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInvMainGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String invMainOutConfirm() {
		try {
			StringTokenizer ids = new StringTokenizer(id, ",");
			List<String> l = new ArrayList<String>();
			while (ids.hasMoreTokens()) {
				String removeId = (ids.nextToken());
				l.add(removeId);
			}
			String[] idArray = new String[l.size()];
			l.toArray(idArray);
			
			UserContext userContext = UserContextUtil.getUserContext();
			DateConverter dc = new DateConverter();
			Date cfd =  (Date) (dc.convert(Date.class, userContext.getBusinessDate()));
			Person cfp=this.getSessionUser().getPerson();
			invMainManager.confirmOut(idArray,cfd, cfp);
			if("3".equals(ioType)||"4".equals(ioType)){
				this.setMessage("移入确认成功。");
			}else{
				this.setMessage("记账成功。");
			}
			return ajaxForward(true, this.getMessage(), false);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	public String invMainOutAudit() {
		try {
//			HttpServletRequest req = this.getRequest();
			StringTokenizer ids = new StringTokenizer(id, ",");
			List<String> l = new ArrayList<String>();
			List<InvDetail> invDetailOuts;
			while (ids.hasMoreTokens()) {
				String auditId = (ids.nextToken());
				invMain = invMainManager.get(auditId);
				invDetailOuts = invDetailManager.getInvDetailsInSameInvMain(invMain);
				if(invDetailOuts==null || invDetailOuts.size()<1){
					return ajaxForward(false, "单据["+invMain.getIoBillNumber()+"]没有明细数据，不能审核！", false);
				}
				l.add(auditId);
			}
			//审核前的检查
			//最后遍历map，如果某一种材料的库存小于0，则不能通过审核
			String[] idArray = new String[l.size()];
			l.toArray(idArray);
			Iterator<InvDetail> ite = invMainManager.beforeOutAudit(idArray);
			InvDetail invDetail = null;
			while(ite.hasNext()){
				invDetail = ite.next();
				if(invDetail.getCurrentStock()<0){
					return ajaxForward(false, "材料：["+invDetail.getInvDict().getInvName()+"]申请数量多于当前库存，请修改", false);
				}
			}
			UserContext userContext = UserContextUtil.getUserContext();
			DateConverter dc = new DateConverter();
			Date cfd =  userContext.getBusinessDate();
			Person cfp=this.getSessionUser().getPerson();
			invMainManager.auditOut(idArray,cfd, cfp);
			
			if("3".equals(ioType)||"4".equals(ioType)){
				this.setMessage("移出确认成功。");
			}else{
				this.setMessage("出库审核成功。");
			}
			return ajaxForward(true, this.getMessage(), false);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	public String invMainOutAuditNot() {
		try {
			StringTokenizer ids = new StringTokenizer(id, ",");
			List<String> l = new ArrayList<String>();
			while (ids.hasMoreTokens()) {
				String removeId = (ids.nextToken());
				l.add(removeId);
			}
			String[] idArray = new String[l.size()];
			l.toArray(idArray);
			
			invMainManager.auditOutNot(idArray);
			if("3".equals(ioType)||"4".equals(ioType)){
				this.setMessage("取消移出成功。");
			}else{
				this.setMessage("取消审核成功。");
			}
			return ajaxForward(true, this.getMessage(), false);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String batchAddOutInvs(){
		this.setRandom(this.getRequest().getParameter("random"));
		return SUCCESS;
	}
	
	private Double curAmount = 0d;
	
	public Double getCurAmount() {
		return curAmount;
	}
	public String getInvMainOutCurAmount(){
		UserContext userContext = UserContextUtil.getUserContext();
		String batchId = this.getRequest().getParameter("batchId");
		String invDictId = this.getRequest().getParameter("invDictId");
		InvBalanceBatch ibb = invBalanceBatchManager.getInvBalanceBatchsByStoreAndBatchAndInv(userContext.getOrgCode(),userContext.getCopyCode(), userContext.getPeriodMonth(), storeId, invDictId,batchId);
		curAmount = ibb.getCurAmount();
		return SUCCESS;
	}
	private String isValid() {
		if (invMain == null) {
			return "Invalid invMain Data";
		}

		return SUCCESS;

	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public void setInvDetailManager(InvDetailManager invDetailManager) {
		this.invDetailManager = invDetailManager;
	}

	public void setInvDetailJson(String invDetailJson) {
		this.invDetailJson = invDetailJson;
	}

	public void setInvMainManager(InvMainManager invMainManager) {
		this.invMainManager = invMainManager;
	}

	public void setInvMains(List<InvMain> invMains) {
		this.invMains = invMains;
	}

	public InvMain getInvMain() {
		return invMain;
	}

	public void setInvMain(InvMain invMain) {
		this.invMain = invMain;
	}

	public String getIoId() {
		return ioId;
	}

	public void setIoId(String ioId) {
		this.ioId = ioId;
	}

	public void setMmBusinessTypeManager(MmBusinessTypeManager mmBusinessTypeManager) {
		this.mmBusinessTypeManager = mmBusinessTypeManager;
	}

	public List<MmBusinessType> getBusTypeList() {
		return busTypeList;
	}

	public void setBusTypeList(List<MmBusinessType> busTypeList) {
		this.busTypeList = busTypeList;
	}

	public List<InvMain> getInvMains() {
		return invMains;
	}

	public String getInvDetailJson() {
		return invDetailJson;
	}

	public List<HashMap<String, String>> getInvOutList() {
		return invOutList;
	}

	public void setInvOutList(List<HashMap<String, String>> invOutList) {
		this.invOutList = invOutList;
	}
	
}
