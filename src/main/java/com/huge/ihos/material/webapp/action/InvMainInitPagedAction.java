package com.huge.ihos.material.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import com.huge.exceptions.BillNumCreateException;
import com.huge.ihos.material.businessType.model.MmBusinessType;
import com.huge.ihos.material.businessType.service.MmBusinessTypeManager;
import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.ihos.material.documenttemplate.service.DocumentTemplateManager;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.model.InvMain;
import com.huge.ihos.material.service.InvDetailManager;
import com.huge.ihos.material.service.InvMainManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.busiprocess.service.BusiProcessManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.period.service.PeriodMonthManager;
import com.huge.util.DateConverter;
import com.huge.util.DateUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class InvMainInitPagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = 9093762107417224395L;
	private InvMainManager invMainManager;
	private List<InvMain> invMains;
	private InvMain invMain;
	private MmBusinessTypeManager mmBusinessTypeManager;

	private InvDetailManager invDetailManager;
	
	private String ioId;
	private String invDetailJson;
	//private String userdata;
	private String currentPeriodBeginDate;
	private BusiProcessManager busiProcessManager;
	private PeriodMonthManager periodMonthManager;
	private DocumentTemplateManager documentTemplateManager;
	
	public void setDocumentTemplateManager(
			DocumentTemplateManager documentTemplateManager) {
		this.documentTemplateManager = documentTemplateManager;
	}
	
	public void setPeriodMonthManager(PeriodMonthManager periodMonthManager) {
		this.periodMonthManager = periodMonthManager;
	}

	public void setBusiProcessManager(BusiProcessManager busiProcessManager) {
		this.busiProcessManager = busiProcessManager;
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
		UserContext userContext = UserContextUtil.getUserContext();
		currentPeriodBeginDate = userContext.getPeriodBeginDateStr();
	}

	public void setMmBusinessTypeManager(MmBusinessTypeManager mmBusinessTypeManager) {
		this.mmBusinessTypeManager = mmBusinessTypeManager;
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
			for(InvMain invInit:invMains){
				ss += invInit.getTotalMoney();
			}
			Map ud = getUserdata();
			if(ud==null){
				ud = new HashMap();
			}
			ud.put("sum",  ""+ss);
			setUserdata(ud);
           // userdata = ""+ss;
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
			JSONArray jsa = JSONArray.fromObject(this.invDetailJson);
			InvDetail[] ids = (InvDetail[]) JSONArray.toArray(jsa, InvDetail.class);
			invMain = invMainManager.saveInvMain(invMain, ids);// save(invMain);

		} catch (BillNumCreateException bnce){
			return ajaxForwardError(bnce.getMessage());
		}  catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "期初入库添加成功" : "期初入库修改成功";
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
	
	public String edit() {
		UserContext userContext = UserContextUtil.getUserContext();
		String docTemId = this.getRequest().getParameter("docTemId");
		if (ioId != null) {
			invMain = invMainManager.get(ioId);
        	docTemId = invMain.getDocTemId();
        	if(docTemId!=null && !(docTemId.trim().equals(""))){//useDocTemp
				docTemp = documentTemplateManager.get(docTemId);
			}else{//not useDocTemp
				docTemp = documentTemplateManager.initDocumentTemplate("期初入库单",userContext.getOrgCode(),userContext.getCopyCode());
			}
			this.setEntityIsNew(false);
		} else {
			if(docTemId!=null){//preview
        		docTemp = documentTemplateManager.get(docTemId);
        	}else{//new
        		docTemp = documentTemplateManager.getDocumentTemplateInUse("期初入库单",userContext.getOrgCode(),userContext.getCopyCode());
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
			MmBusinessType busType = mmBusinessTypeManager.get("39");
			invMain.setBusType(busType);
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
					invMainManager.remove(new String(removeId));
				}
				gridOperationMessage = this.getText("期初入库删除成功");
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

	public String invMainInitConfirm() {
		try {
			StringTokenizer ids = new StringTokenizer(id, ",");
			List<String> l = new ArrayList<String>();
			List<InvDetail> invDetailInits;
			while (ids.hasMoreTokens()) {
				String confirmId = (ids.nextToken()).trim();
				invMain = invMainManager.get(confirmId);
				invDetailInits = invDetailManager.getInvDetailsInSameInvMain(invMain);
				if(invDetailInits==null || invDetailInits.size()<1){
					return ajaxForward(false, "单据["+invMain.getIoBillNumber()+"]没有明细数据，不能记账！", false);
				}
				l.add(confirmId);
			}
			String[] idArray = new String[l.size()];
			l.toArray(idArray);
			
			UserContext userContext = UserContextUtil.getUserContext();
			DateConverter dc = new DateConverter();
			Date cfd =  userContext.getBusinessDate();
			Person cfp=this.getSessionUser().getPerson();
			
			invMainManager.initConfirm(idArray,cfd,cfp);

			return ajaxForward(true, "期初入库记账成功。", false);
		} catch (Exception e) {
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String invMainBookConfirm() {
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
			DateConverter dc = new DateConverter();
			invMainManager.bookStore(userContext.getOrgCode(), userContext.getCopyCode(), userContext.getPeriodYear(),idArray, userContext.getBusinessDate());
			return ajaxForward(true, "仓库期初记账成功。", false);
		} catch (Exception e) {
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	/**
	 * 1.更改仓库标志
	 * 2.更改单据状态为新建
	 * 3.删除本期balance数据
	 * 4.删除记账时插入到材料明细账表的记录???
	 * @return
	 */
	public String invMainUnBookConfirm(){
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
			invMainManager.unBookStore(idArray,userContext.getOrgCode(),userContext.getCopyCode(),userContext.getPeriodYear());
			return ajaxForward(true, "仓库期初反记账成功。", false);
		} catch (Exception e) {
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String invMainBatchEndBook(){
		try {
			UserContext userContext = UserContextUtil.getUserContext();
			String periodPlanId = userContext.getPeriodPlanCode();
			String nextPeriod = periodMonthManager.getNextPeriod(periodPlanId, userContext.getPeriodYear(),userContext.getPeriodMonth());
			if(nextPeriod!=null){
				Object[] args = {userContext.getOrgCode(),userContext.getCopyCode(),userContext.getPeriodMonth(),nextPeriod};
				busiProcessManager.execBusinessProcess("MMQMJZ", args);
			}
			return ajaxForward(true, "月末结账成功。", false);
		} catch (Exception e) {
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (invMain == null) {
			return "Invalid invMain Data";
		}

		return SUCCESS;

	}

}
