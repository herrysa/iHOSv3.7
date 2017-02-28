package com.huge.ihos.material.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huge.exceptions.BillNumCreateException;
import com.huge.ihos.material.businessType.service.MmBusinessTypeManager;
import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.ihos.material.documenttemplate.service.DocumentTemplateManager;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.model.InvMain;
import com.huge.ihos.material.service.InvDetailManager;
import com.huge.ihos.material.service.InvMainManager;
import com.huge.ihos.system.configuration.procType.model.ProcType;
import com.huge.ihos.system.configuration.procType.service.ProcTypeManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.DateConverter;
import com.huge.util.DateUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class InvMainInPagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = -8846557968016906340L;
	private InvMainManager invMainManager;
	private List<InvMain> invMains;
	private InvMain invMain;

	private String ioId;
	private String invDetailJson;
	private MmBusinessTypeManager mmBusinessTypeManager;
	private InvDetailManager invDetailManager;
	private ProcTypeManager procTypeManager;
	private List<ProcType> procTypeList;
	//private String userdata;
	private String currentPeriodBeginDate;
	private DocumentTemplateManager documentTemplateManager;
	
	public void setDocumentTemplateManager(
			DocumentTemplateManager documentTemplateManager) {
		this.documentTemplateManager = documentTemplateManager;
	}
	
	public String getCurrentPeriodBeginDate() {
		return currentPeriodBeginDate;
	}

	/*public String getUserdata() {
		return userdata;
	}*/
	
	public void setInvDetailManager(InvDetailManager invDetailManager) {
		this.invDetailManager = invDetailManager;
	}

	public List<ProcType> getProcTypeList() {
		return procTypeList;
	}
	
	public void setProcTypeManager(ProcTypeManager procTypeManager) {
		this.procTypeManager = procTypeManager;
	}

	public void setMmBusinessTypeManager(MmBusinessTypeManager mmBusinessTypeManager) {
		this.mmBusinessTypeManager = mmBusinessTypeManager;
	}

	public void setInvDetailJson(String invDetailJson) {
		this.invDetailJson = invDetailJson;
	}

	public void setInvMainManager(InvMainManager invMainManager) {
		this.invMainManager = invMainManager;
	}

	public List<InvMain> getInvMains() {
		return invMains;
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

	public void prepare() throws Exception {
		this.clearSessionMessages();
		this.procTypeList = this.procTypeManager.getAllExceptDisable();
		UserContext userContext = UserContextUtil.getUserContext();
		currentPeriodBeginDate = userContext.getPeriodBeginDateStr();
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
			for(InvMain invIn:invMains){
				ss += invIn.getTotalMoney();
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
			String fromOrderData = req.getParameter("fromOrderData");
			/*JSONArray jsa = JSONArray.fromObject(this.invDetailJson);
			InvDetail[] ids = (InvDetail[]) JSONArray.toArray(jsa, InvDetail.class);*/
			if(fromOrderData!=null){
				Person person = this.getSessionUser().getPerson();
				invMain = invMainManager.saveInvMainFromOrder(invMain, ids,fromOrderData,person);
			}else{
				invMain = invMainManager.saveInvMain(invMain, ids);// save(invMain);
			}
		} catch (BillNumCreateException bnce){
			return ajaxForwardError(bnce.getMessage());
		}  catch (Exception e){
			gridOperationMessage = e.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		
		String key = ((this.isEntityIsNew())) ? "入库单添加成功" : "入库单修改成功";
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
	private String selectInvDictByVendor;
	
	public String getSelectInvDictByVendor() {
		return selectInvDictByVendor;
	}

	public String edit() {
		selectInvDictByVendor = this.getGlobalParamByKey("selectInvDictByVendor");
		UserContext userContext = UserContextUtil.getUserContext();
		String docTemId = this.getRequest().getParameter("docTemId");
		String invMainInNo = this.getRequest().getParameter("invMainInNo");
		if(invMainInNo!=null){
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_ioBillNumber",invMainInNo));
			invMains = this.invMainManager.getByFilters(filters);
			if(invMains!=null && invMains.size()==1){
				ioId = invMains.get(0).getIoId();
			}
		}
		if (ioId != null) {
			invMain = invMainManager.get(ioId);
			docTemId = invMain.getDocTemId();
        	if(docTemId!=null && !(docTemId.trim().equals(""))){//useDocTemp
				docTemp = documentTemplateManager.get(docTemId);
			}else{//not useDocTemp
				docTemp = documentTemplateManager.initDocumentTemplate("入库单",userContext.getOrgCode(),userContext.getCopyCode());
			}
			this.setEntityIsNew(false);
		} else {
			if(docTemId!=null){//preview
        		docTemp = documentTemplateManager.get(docTemId);
        	}else{//new
        		docTemp = documentTemplateManager.getDocumentTemplateInUse("入库单",userContext.getOrgCode(),userContext.getCopyCode());
        		docTemId = docTemp.getId();
        	}
			invMain = new InvMain();
			invMain.setOrgCode(userContext.getOrgCode());
			invMain.setCopyCode(userContext.getCopyCode());
			DateConverter dc = new DateConverter();
			invMain.setMakeDate(userContext.getBusinessDate());
			invMain.setMakePerson(this.getSessionUser().getPerson());
			invMain.setYearMonth(DateUtil.convertDateToString("yyyyMM", invMain.getMakeDate()));
			invMain.setIoType("1");
			invMain.setBusType(mmBusinessTypeManager.get("0"));
			invMain.setProctypeCode(procTypeManager.getProcTypeByCode("02"));
			invMain.setDocTemId(docTemId);
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String invMainGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = (ids.nextToken()).trim();
					log.debug("Delete Customer " + removeId);
					invMainManager.removeInvMainIn(new String(removeId));
				}
				gridOperationMessage = this.getText("入库单删除成功");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInvMainGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String invMainInConfirm() {
		try {
			StringTokenizer ids = new StringTokenizer(id, ",");
			List<String> l = new ArrayList<String>();
			while (ids.hasMoreTokens()) {
				String removeId = (ids.nextToken()).trim();
				l.add(removeId);
			}
			String[] idArray = new String[l.size()];
			l.toArray(idArray);
			
			UserContext userContext = UserContextUtil.getUserContext();
			Date cfd =  userContext.getBusinessDate();
			Person cfp=this.getSessionUser().getPerson();
			invMainManager.initConfirm(idArray,cfd,cfp);
			return ajaxForward(true, "记账成功。", false);
		} catch (Exception e) {
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String invMainInAudit(){
		try {
			StringTokenizer ids = new StringTokenizer(id, ",");
			List<String> l = new ArrayList<String>();
			List<InvDetail> invDetailIns;
			while (ids.hasMoreTokens()) {
				String auditId = (ids.nextToken()).trim();
				invMain = invMainManager.get(auditId);
				invDetailIns = invDetailManager.getInvDetailsInSameInvMain(invMain);
				if(invDetailIns==null || invDetailIns.size()<1){
					return ajaxForward(false, "单据["+invMain.getIoBillNumber()+"]没有明细数据，不能审核！", false);
				}
				l.add(auditId);
			}
			String[] idArray = new String[l.size()];
			l.toArray(idArray);
			
			UserContext userContext = UserContextUtil.getUserContext();
			Date cfd =  userContext.getBusinessDate();
			Person cfp=this.getSessionUser().getPerson();
			invMainManager.invInAudit(idArray,cfd,cfp);
			return ajaxForward(true, "入库审核成功。", false);
		} catch (Exception e) {
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	public String invMainInUnAudit(){
		try {
			StringTokenizer ids = new StringTokenizer(id, ",");
			List<String> l = new ArrayList<String>();
			while (ids.hasMoreTokens()) {
				String removeId = (ids.nextToken()).trim();
				l.add(removeId);
			}
			String[] idArray = new String[l.size()];
			l.toArray(idArray);
			
			invMainManager.invInUnAudit(idArray);
			
			return ajaxForward(true, "取消审核入库成功。", false);
		} catch (Exception e) {
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	
	
/*	public String invMainBookConfirm() {
		try {
			SystemVariable sv = this.getCurrentSystemVariable();
			DateConverter dc = new DateConverter();
			invMainManager.bookStore(sv.getOrgCode(), sv.getCopyCode(), this.storeId, (Date) (dc.convert(Date.class, sv.getBusinessDate())));
			return ajaxForward(true, "期初入库记账成功。", false);
		} catch (Exception e) {
			return ajaxForward(false, e.getMessage(), false);
		}
	}*/

	private String isValid() {
		if (invMain == null) {
			return "Invalid invMain Data";
		}

		return SUCCESS;

	}
}
