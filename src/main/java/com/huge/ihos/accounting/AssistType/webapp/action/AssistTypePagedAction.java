package com.huge.ihos.accounting.AssistType.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.accounting.AssistType.model.AssistType;
import com.huge.ihos.accounting.AssistType.service.AssistTypeManager;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class AssistTypePagedAction extends JqGridBaseAction implements Preparable {

	private AssistTypeManager assistTypeManager;
	private List<AssistType> assistTypes;
	private AssistType assistType;
	private String typeCode;
	private String disabled;
	private String typeName;
	private String filter;
	private String memo;
	private BdInfo bdInfo;
	private String acctId;

	public String getAcctId() {
		return acctId;
	}
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String assistTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = assistTypeManager
					.getAssistTypeCriteria(pagedRequests,filters);
			this.assistTypes = (List<AssistType>) pagedRequests.getList();
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
			assistTypeManager.save(assistType);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "assistType.added" : "assistType.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
    	HttpServletRequest req = this.getRequest();
        if (typeCode != null) {
        	assistType = assistTypeManager.get(typeCode);
        	this.setEntityIsNew(false);
        } else {
        	assistType = new AssistType();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String assistTypeGridEdit() {
		try {
			System.out.println(oper);
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					//AssistType assistType = assistTypeManager.get(new OrgCopyAsstIdPk(removeId));
					//assistTypeManager.remove(new OrgCopyAsstIdPk(removeId));
					assistTypeManager.remove(removeId);
					
					
				}
				gridOperationMessage = this.getText("assistType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("add") || oper.equals("edit")) {
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkAssistTypeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (assistType == null) {
			return "Invalid assistType Data";
		}

		return SUCCESS;
	}
	
	public AssistTypeManager getAssistTypeManager() {
		return assistTypeManager;
	}
	public void setAssistTypeManager(AssistTypeManager assistTypeManager) {
		this.assistTypeManager = assistTypeManager;
	}
	public List<AssistType> getAssistTypes() {
		return assistTypes;
	}
	public void setAssistTypes(List<AssistType> assistTypes) {
		this.assistTypes = assistTypes;
	}
	public AssistType getAssistType() {
		return assistType;
	}
	public void setAssistType(AssistType assistType) {
		this.assistType = assistType;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public BdInfo getBdInfo() {
		return bdInfo;
	}
	public void setBdInfo(BdInfo bdInfo) {
		this.bdInfo = bdInfo;
	}
	
	public String getAssitTypesByAcct(){
		try {
			assistTypes = assistTypeManager.getAssitTypesByAcct(acctId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
}

