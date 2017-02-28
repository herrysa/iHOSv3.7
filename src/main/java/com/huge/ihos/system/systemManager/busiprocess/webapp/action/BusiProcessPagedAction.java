package com.huge.ihos.system.systemManager.busiprocess.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.busiprocess.model.BusiProcess;
import com.huge.ihos.system.systemManager.busiprocess.service.BusiProcessManager;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class BusiProcessPagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = -8372348103908794773L;
	private BusiProcessManager businessProcessManager;
	private List<BusiProcess> businessProcesses;
	private BusiProcess businessProcess;
	private String id;

	public void setBusinessProcessManager(BusiProcessManager businessProcessManager) {
		this.businessProcessManager = businessProcessManager;
	}

	public List<BusiProcess> getBusinessProcesses() {
		return businessProcesses;
	}

	public void setBusinessProcesss(List<BusiProcess> businessProcesses) {
		this.businessProcesses = businessProcesses;
	}

	public BusiProcess getBusinessProcess() {
		return businessProcess;
	}

	public void setBusinessProcess(BusiProcess businessProcess) {
		this.businessProcess = businessProcess;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String businessProcessGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = businessProcessManager
					.getBusinessProcessCriteria(pagedRequests,filters);
			this.businessProcesses = (List<BusiProcess>) pagedRequests.getList();
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
        if (id != null) {
        	businessProcess = businessProcessManager.get(Long.parseLong(id));
        	this.setEntityIsNew(false);
        } else {
        	businessProcess = new BusiProcess();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    public String copyBusinessProcess(){
    	businessProcess = businessProcessManager.get(Long.parseLong(id));
    	businessProcess.setId(null);
    	this.setEntityIsNew(true);
    	return SUCCESS;
    }
	public String businessProcessGridEdit() {
		try {
			if (oper.equals("del")) {
				List<Long> idl = new ArrayList<Long>();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					//String removeId = ids.nextToken();
					Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
				Long[] ida=new Long[idl.size()];
				idl.toArray(ida);
				this.businessProcessManager.remove(ida);
				gridOperationMessage = this.getText("businessProcess.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			if (oper.equals("exec")) {
				StringTokenizer ids = new StringTokenizer(id,",");
				String code = "";
				ArrayList<String> taskList = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					Long execId = Long.parseLong(ids.nextToken());
					code = businessProcessManager.get(execId).getCode();
					if(!taskList.contains(code)){
						taskList.add(code);
					}
				}
				String param = this.getRequest().getParameter("param");
				UserContext userContext = UserContextUtil.getUserContext();
				Object[] args = {userContext.getOrgCode(),userContext.getCopyCode(),userContext.getPeriodMonth(),this.getSessionUser().getPerson().getPersonId(),param};
				for(String task:taskList){
					businessProcessManager.execBusinessProcess(task, args);
				}
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

	private boolean exists;
	
	public boolean getExists(){
		return exists;
	}
	
	public String checkEditBusinessProcess(){
		String code = this.getRequest().getParameter("code");
		String sequence = this.getRequest().getParameter("seq");
		BusiProcess businessProcessFind = new BusiProcess();
		businessProcessFind.setCode(code);
		businessProcessFind.setSequence(Integer.parseInt(sequence));
		businessProcessFind.setDisabled(null);
		businessProcessFind.setIgnoreError(null);
		exists = businessProcessManager.existByExample(businessProcessFind);
		//exists = businessProcessManager.existCode("sy_business_process", "code", "sequence", code, sequence);
		return SUCCESS;
	}
	
	private String isValid() {
		if (businessProcess == null) {
			return "Invalid businessProcess Data";
		}
		return SUCCESS;

	}
}

