package com.huge.ihos.system.configuration.businessprocess.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcess;
import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcessStep;
import com.huge.ihos.system.configuration.businessprocess.service.BusinessProcessManager;
import com.huge.ihos.system.configuration.businessprocess.service.BusinessProcessStepManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BusinessProcessStepPagedAction extends JqGridBaseAction implements Preparable {

	private BusinessProcessStepManager businessProcessStepManager;
	private BusinessProcessManager businessProcessManager;
	private List<BusinessProcessStep> businessProcessSteps;
	private BusinessProcessStep businessProcessStep;
	private String stepCode;
	private String processId;

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setBusinessProcessStepManager(BusinessProcessStepManager businessProcessStepManager) {
		this.businessProcessStepManager = businessProcessStepManager;
	}
	public void setBusinessProcessManager(
			BusinessProcessManager businessProcessManager) {
		this.businessProcessManager = businessProcessManager;
	}
	public List<BusinessProcessStep> getBusinessProcessSteps() {
		return businessProcessSteps;
	}

	public void setBusinessProcessSteps(List<BusinessProcessStep> businessProcessSteps) {
		this.businessProcessSteps = businessProcessSteps;
	}

	public BusinessProcessStep getBusinessProcessStep() {
		return businessProcessStep;
	}

	public void setBusinessProcessStep(BusinessProcessStep businessProcessStep) {
		this.businessProcessStep = businessProcessStep;
	}

	public String getStepCode() {
		return stepCode;
	}

	public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String businessProcessStepGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = businessProcessStepManager
					.getBusinessProcessStepCriteria(pagedRequests,filters);
			this.businessProcessSteps = (List<BusinessProcessStep>) pagedRequests.getList();
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
			if(businessProcessStep.getOkStep()!=null&&"".equals(businessProcessStep.getOkStep().getStepCode())){
				businessProcessStep.setOkStep(null);
			}
			if(businessProcessStep.getOkStep()!=null&&"end".equals(businessProcessStep.getOkStep().getStepCode())){
				businessProcessStep.setOkStep(null);
				businessProcessStep.setIsEnd(true);
			}
			if(businessProcessStep.getNoStep()!=null&&"".equals(businessProcessStep.getNoStep().getStepCode())){
				businessProcessStep.setNoStep(null);
			}
			businessProcessStepManager.save(businessProcessStep);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "businessProcessStep.added" : "businessProcessStep.updated";
		return ajaxForward(this.getText(key));
	}
	List<BusinessProcessStep> okStepList;
	List<BusinessProcessStep> noStepList;
	public List<BusinessProcessStep> getOkStepList() {
		return okStepList;
	}

	public void setOkStepList(List<BusinessProcessStep> okStepList) {
		this.okStepList = okStepList;
	}

	public List<BusinessProcessStep> getNoStepList() {
		return noStepList;
	}

	public void setNoStepList(List<BusinessProcessStep> noStepList) {
		this.noStepList = noStepList;
	}
    public String edit() {
    	
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	BusinessProcessStep endBusinessProcessStep = new BusinessProcessStep();
        endBusinessProcessStep.setStepCode("end");
        endBusinessProcessStep.setStepName("结束");
        if (stepCode != null) {
        	businessProcessStep = businessProcessStepManager.get(stepCode);
        	processId = businessProcessStep.getBusinessProcess().getProcessCode();
        	BusinessProcess businessProcess = businessProcessManager.get(processId);
        	businessProcessStep.setBusinessProcess(businessProcess);
        	this.setEntityIsNew(false);
        	filters.add(new PropertyFilter("OAS_state",""));
        	filters.add(new PropertyFilter("GTI_state",""+businessProcessStep.getState()));
            okStepList = businessProcessStepManager.getByFilters(filters);
            okStepList.add(endBusinessProcessStep);
        	filters.clear();
            filters.add(new PropertyFilter("LTI_state",""+businessProcessStep.getState()));
            filters.add(new PropertyFilter("OAS_state",""));
            noStepList = businessProcessStepManager.getByFilters(filters);
        } else {
        	businessProcessStep = new BusinessProcessStep();
        	BusinessProcess businessProcess = businessProcessManager.get(processId);
        	businessProcessStep.setBusinessProcess(businessProcess);
        	businessProcessStep.setStepCode(businessProcess.getProcessCode());
            okStepList = new ArrayList<BusinessProcessStep>();
            okStepList.add(endBusinessProcessStep);
        	filters.clear();
            filters.add(new PropertyFilter("OAS_state",""));
            noStepList = businessProcessStepManager.getByFilters(filters);
        	this.setEntityIsNew(true);
        }
        //noStepList.add(endBusinessProcessStep);
        return SUCCESS;
    }
	public String businessProcessStepGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BusinessProcessStep businessProcessStep = businessProcessStepManager.get(removeId);
					businessProcessStepManager.remove( removeId);
					
				}
				gridOperationMessage = this.getText("businessProcessStep.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBusinessProcessStepGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (businessProcessStep == null) {
			return "Invalid businessProcessStep Data";
		}

		return SUCCESS;

	}
}

