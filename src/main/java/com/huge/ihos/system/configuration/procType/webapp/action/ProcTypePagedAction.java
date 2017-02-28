package com.huge.ihos.system.configuration.procType.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.configuration.procType.model.ProcType;
import com.huge.ihos.system.configuration.procType.service.ProcTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ProcTypePagedAction extends JqGridBaseAction implements Preparable {

	private ProcTypeManager procTypeManager;
	private List<ProcType> procTypes;
	private ProcType procType;
	private String typeId;

	public ProcTypeManager getProcTypeManager() {
		return procTypeManager;
	}

	public void setProcTypeManager(ProcTypeManager procTypeManager) {
		this.procTypeManager = procTypeManager;
	}

	public List<ProcType> getprocTypes() {
		return procTypes;
	}

	public void setProcTypes(List<ProcType> procTypes) {
		this.procTypes = procTypes;
	}

	public ProcType getProcType() {
		return procType;
	}

	public void setProcType(ProcType procType) {
		this.procType = procType;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String procTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = procTypeManager
					.getProcTypeCriteria(pagedRequests,filters);
			this.procTypes = (List<ProcType>) pagedRequests.getList();
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
			procTypeManager.save(procType);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "procType.added" : "procType.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (typeId != null) {
        	procType = procTypeManager.get(typeId);
        	this.setEntityIsNew(false);
        } else {
        	procType = new ProcType();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String procTypeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					ProcType procType = procTypeManager.get(new String(removeId));
					procTypeManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("procType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkProcTypeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (procType == null) {
			return "Invalid procType Data";
		}

		return SUCCESS;

	}
}

