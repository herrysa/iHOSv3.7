package com.huge.ihos.system.configuration.businessprocess.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcess;
import com.huge.ihos.system.configuration.businessprocess.service.BusinessProcessManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BusinessProcessPagedAction extends JqGridBaseAction implements Preparable {

	private BusinessProcessManager businessProcessManager;
	private List<BusinessProcess> businessProcesses;
	private BusinessProcess businessProcess;
	private String processCode;

	public BusinessProcessManager getBusinessProcessManager() {
		return businessProcessManager;
	}

	public void setBusinessProcessManager(BusinessProcessManager businessProcessManager) {
		this.businessProcessManager = businessProcessManager;
	}

	public List<BusinessProcess> getbusinessProcesses() {
		return businessProcesses;
	}

	public void setBusinessProcesss(List<BusinessProcess> businessProcesses) {
		this.businessProcesses = businessProcesses;
	}

	public BusinessProcess getBusinessProcess() {
		return businessProcess;
	}

	public void setBusinessProcess(BusinessProcess businessProcess) {
		this.businessProcess = businessProcess;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String businessProcessGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = businessProcessManager
					.getBusinessProcessCriteria(pagedRequests,filters);
			this.businessProcesses = (List<BusinessProcess>) pagedRequests.getList();
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
			businessProcessManager.save(businessProcess);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "businessProcess.added" : "businessProcess.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (processCode != null) {
        	businessProcess = businessProcessManager.get(processCode);
        	this.setEntityIsNew(false);
        } else {
        	businessProcess = new BusinessProcess();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String businessProcessGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BusinessProcess businessProcess = businessProcessManager.get(removeId);
					businessProcessManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("businessProcess.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBusinessProcessGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (businessProcess == null) {
			return "Invalid businessProcess Data";
		}

		return SUCCESS;

	}
}

