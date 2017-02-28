package com.huge.ihos.material.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.json.JSONArray;

import com.huge.exceptions.BillNumCreateException;
import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.ihos.material.documenttemplate.service.DocumentTemplateManager;
import com.huge.ihos.material.model.InvCheck;
import com.huge.ihos.material.model.InvCheckDetail;
import com.huge.ihos.material.service.InvCheckDetailManager;
import com.huge.ihos.material.service.InvCheckManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.repository.store.service.StoreManager;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.util.DateConverter;
import com.huge.util.DateUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class InvCheckPagedAction extends JqGridBaseAction implements Preparable {
	private static final long serialVersionUID = -2679017059375645088L;
	private InvCheckManager invCheckManager;
	private List<InvCheck> invChecks;
	private List<InvCheckDetail> invCheckDetails;
	private InvCheck invCheck;
	private String checkId;
	private InvCheckDetailManager invCheckDetailManager;
	private StoreManager storeManager;
	private String invCheckDetailJson;
	private String currentPeriodBeginDate;
	//private String userdata;
	private DocumentTemplateManager documentTemplateManager;
	
	public void setDocumentTemplateManager(
			DocumentTemplateManager documentTemplateManager) {
		this.documentTemplateManager = documentTemplateManager;
	}
	
	/*public String getUserdata() {
		return userdata;
	}*/
	
	public String getCurrentPeriodBeginDate() {
		return currentPeriodBeginDate;
	}

	public void setInvCheckDetailJson(String invCheckDetailJson) {
		this.invCheckDetailJson = invCheckDetailJson;
	}

	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}

	public void setInvCheckDetailManager(
			InvCheckDetailManager invCheckDetailManager) {
		this.invCheckDetailManager = invCheckDetailManager;
	}

	public void setInvCheckManager(InvCheckManager invCheckManager) {
		this.invCheckManager = invCheckManager;
	}

	public List<InvCheck> getInvChecks() {
		return invChecks;
	}

	public void setInvChecks(List<InvCheck> invChecks) {
		this.invChecks = invChecks;
	}

	public InvCheck getInvCheck() {
		return invCheck;
	}

	public void setInvCheck(InvCheck invCheck) {
		this.invCheck = invCheck;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
		UserContext userContext = UserContextUtil.getUserContext();
		currentPeriodBeginDate = DateUtil.convertDateToString(userContext.getPeriodBeginDate());
	}

	@SuppressWarnings("unchecked")
	public String invCheckGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext
					.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext
					.getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = invCheckManager.getInvCheckCriteria(pagedRequests,
					filters);
			this.invChecks = (List<InvCheck>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
			double ss = 0;
			for(InvCheck invCheck:invChecks){
				ss += invCheck.getTotalDiffMoney();
			}
			getUserdata().put("sum", ""+ss);
           // userdata = ""+ss;
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			JSONArray jsa = JSONArray.fromObject(this.invCheckDetailJson);
			InvCheckDetail[] invCheckDetails = (InvCheckDetail[]) JSONArray.toArray(jsa, InvCheckDetail.class);
			invCheck = invCheckManager.saveInvCheck(invCheck, invCheckDetails);
		} catch (BillNumCreateException bnce){
			return ajaxForwardError(bnce.getMessage());
		}  catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "invCheck.added"
				: "invCheck.updated";
		String saveType = this.getRequest().getParameter("saveType");
		if (saveType == null) {
			return ajaxForward(true, this.getText(key), true);
		} else if (saveType.equalsIgnoreCase("saveStay")) {
			this.setCallbackType(saveType);
			this.setForwardUrl(invCheck.getCheckId());
			return ajaxForward(true, this.getText(key), false);
		} else {
			return ajaxForward(true, this.getText(key), true);
		}
	}

	private DocumentTemplate docTemp;
	
	public DocumentTemplate getDocTemp() {
		return docTemp;
	}
	
	public String edit() {
		UserContext userContext = UserContextUtil.getUserContext();
		String docTemId = this.getRequest().getParameter("docTemId");
		if (checkId != null) {
			invCheck = invCheckManager.get(checkId);
        	docTemId = invCheck.getDocTemId();
        	if(docTemId!=null && !(docTemId.trim().equals(""))){//useDocTemp
				docTemp = documentTemplateManager.get(docTemId);
			}else{//not useDocTemp
				docTemp = documentTemplateManager.initDocumentTemplate("盘点单",userContext.getOrgCode(),userContext.getCopyCode());
			}
			this.setEntityIsNew(false);
		} else {
			if(docTemId!=null){//preview
        		docTemp = documentTemplateManager.get(docTemId);
        	}else{//new
        		docTemp = documentTemplateManager.getDocumentTemplateInUse("盘点单",userContext.getOrgCode(),userContext.getCopyCode());
        		docTemId = docTemp.getId();
        	}
			invCheck = new InvCheck();
			this.setEntityIsNew(true);
			invCheck.setCheckId("new");
			invCheck.setState("0");
			invCheck.setOrgCode(userContext.getOrgCode());
			invCheck.setCopyCode(userContext.getCopyCode());
			invCheck.setMakePerson(this.getSessionUser().getPerson());
			DateConverter dc = new DateConverter();
			invCheck.setMakeDate(userContext.getBusinessDate());
			invCheck.setYearMonth(DateUtil.convertDateToString("yyyyMM",
					invCheck.getMakeDate()));
			invCheck.setDocTemId(docTemId);
		}
		return SUCCESS;
	}

	public String invCheckGridEdit() {
		try {
			UserContext userContext = UserContextUtil.getUserContext();
			StringTokenizer ids = new StringTokenizer(id, ",");
			if (oper.equals("del")) {
				invCheckDetails = null;
				while (ids.hasMoreTokens()) {
					String removeId = (ids.nextToken());
					log.debug("Delete Customer " + removeId);
					InvCheck invCheck = invCheckManager
							.get(new String(removeId));
					invCheckDetails = invCheckDetailManager
							.getInvCheckDetailsInSameCheck(invCheck);
					invCheckManager.removeInvCheck(invCheck,invCheckDetails);
				}
				gridOperationMessage = this.getText("invCheck.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			if (oper.equals("check")) {
				while (ids.hasMoreTokens()) {
					String checkId = (ids.nextToken());
					InvCheck invCheck = invCheckManager
							.get(new String(checkId));
					invCheckDetails = invCheckDetailManager.getInvCheckDetailsInSameCheck(invCheck);
					if(invCheckDetails==null || invCheckDetails.size()<1){
						return ajaxForward(false, "该盘点单没有明细数据，不能审核！", false);
					}
					invCheck.setState("1");
					invCheck.setCheckPerson(this.getSessionUser().getPerson());
					invCheck.setCheckDate((Date) (new DateConverter().convert(Date.class,
							userContext.getBusinessDate())));
					invCheckManager.save(invCheck);
				}
				gridOperationMessage = this.getText("审核完成!");
				return ajaxForward(true, gridOperationMessage, false);
			}
			if (oper.equals("cancelcheck")) {
				while (ids.hasMoreTokens()) {
					String checkId = (ids.nextToken());
					InvCheck invCheck = invCheckManager
							.get(new String(checkId));
					invCheck.setState("0");
					invCheck.setCheckPerson(null);
					invCheck.setCheckDate(null);
					invCheckManager.save(invCheck);
				}
				gridOperationMessage = this.getText("销审成功!");
				return ajaxForward(true, gridOperationMessage, false);
			}
			if(oper.equals("exportInOut")){
				List<String> exportNull = new ArrayList<String>();
				String exportId = "";
				boolean exported = false;
				while(ids.hasMoreTokens()){
					exportId = ids.nextToken();
					invCheck = invCheckManager
							.get(new String(exportId));
					exported = invCheckManager.exportCheckInOut(exportId, this.getSessionUser().getPerson());
					if(!exported){
						exportNull.add(invCheck.getCheckNo());
					}
					Store store = invCheck.getStore();
					store.setIsLock(false);
					storeManager.save(store);
				}
				if(exportNull.size()>=1){
					String msg = "盘点单[";
					for(int i=0;i<exportNull.size()-1;i++){
						msg += ""+exportNull.get(i)+",";
					}
					msg += exportNull.get(exportNull.size()-1) + "]结果没有出入，无需生成出入库单.其他单据已经成功生成出入库单";
					gridOperationMessage = this.getText(msg);
				}else{
					gridOperationMessage = this.getText("已生成出入库单!");
				}
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("invCheckGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (invCheck == null) {
			return "Invalid invCheck Data";
		}

		return SUCCESS;

	}
}
