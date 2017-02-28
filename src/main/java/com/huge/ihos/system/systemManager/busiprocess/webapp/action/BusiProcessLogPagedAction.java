package com.huge.ihos.system.systemManager.busiprocess.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.systemManager.busiprocess.model.BusiProcessLog;
import com.huge.ihos.system.systemManager.busiprocess.service.BusiProcessLogManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class BusiProcessLogPagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = 3352729716246046396L;
	private BusiProcessLogManager businessProcessLogManager;
	private List<BusiProcessLog> businessProcessLogs;
	private BusiProcessLog businessProcessLog;
	private String id;

	public void setBusinessProcessLogManager(BusiProcessLogManager businessProcessLogManager) {
		this.businessProcessLogManager = businessProcessLogManager;
	}

	public List<BusiProcessLog> getBusinessProcessLogs() {
		return businessProcessLogs;
	}

	public void setBusinessProcessLogs(List<BusiProcessLog> businessProcessLogs) {
		this.businessProcessLogs = businessProcessLogs;
	}

	public BusiProcessLog getBusinessProcessLog() {
		return businessProcessLog;
	}

	public void setBusinessProcessLog(BusiProcessLog businessProcessLog) {
		this.businessProcessLog = businessProcessLog;
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
	public String businessProcessLogGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = businessProcessLogManager
					.getBusinessProcessLogCriteria(pagedRequests,filters);
			this.businessProcessLogs = (List<BusiProcessLog>) pagedRequests.getList();
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
			businessProcessLogManager.save(businessProcessLog);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "businessProcessLog.added" : "businessProcessLog.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	businessProcessLog = businessProcessLogManager.get(Long.parseLong(id));
        	this.setEntityIsNew(false);
        } else {
        	businessProcessLog = new BusiProcessLog();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String businessProcessLogGridEdit() {
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
				this.businessProcessLogManager.remove(ida);
				gridOperationMessage = this.getText("businessProcessLog.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBusinessProcessLogGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (businessProcessLog == null) {
			return "Invalid businessProcessLog Data";
		}

		return SUCCESS;

	}
}

