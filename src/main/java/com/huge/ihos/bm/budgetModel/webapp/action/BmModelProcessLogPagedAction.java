package com.huge.ihos.bm.budgetModel.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.bm.budgetModel.model.BmModelProcessLog;
import com.huge.ihos.bm.budgetModel.service.BmModelProcessLogManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BmModelProcessLogPagedAction extends JqGridBaseAction implements Preparable {

	private BmModelProcessLogManager bmModelProcessLogManager;
	private List<BmModelProcessLog> bmModelProcessLogs;
	private BmModelProcessLog bmModelProcessLog;
	private String logId;
	private String updataId;

	public String getUpdataId() {
		return updataId;
	}

	public void setUpdataId(String updataId) {
		this.updataId = updataId;
	}

	public BmModelProcessLogManager getBmModelProcessLogManager() {
		return bmModelProcessLogManager;
	}

	public void setBmModelProcessLogManager(BmModelProcessLogManager bmModelProcessLogManager) {
		this.bmModelProcessLogManager = bmModelProcessLogManager;
	}

	public List<BmModelProcessLog> getBmModelProcessLogs() {
		return bmModelProcessLogs;
	}

	public void setBmModelProcessLogs(List<BmModelProcessLog> bmModelProcessLogs) {
		this.bmModelProcessLogs = bmModelProcessLogs;
	}

	public BmModelProcessLog getBmModelProcessLog() {
		return bmModelProcessLog;
	}

	public void setBmModelProcessLog(BmModelProcessLog bmModelProcessLog) {
		this.bmModelProcessLog = bmModelProcessLog;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
        this.logId = logId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String bmModelProcessLogGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = bmModelProcessLogManager
					.getBmModelProcessLogCriteria(pagedRequests,filters);
			this.bmModelProcessLogs = (List<BmModelProcessLog>) pagedRequests.getList();
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
			bmModelProcessLogManager.save(bmModelProcessLog);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "bmModelProcessLog.added" : "bmModelProcessLog.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (logId != null) {
        	bmModelProcessLog = bmModelProcessLogManager.get(logId);
        	this.setEntityIsNew(false);
        } else {
        	bmModelProcessLog = new BmModelProcessLog();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String bmModelProcessLogGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BmModelProcessLog bmModelProcessLog = bmModelProcessLogManager.get(removeId);
					bmModelProcessLogManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("bmModelProcessLog.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBmModelProcessLogGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (bmModelProcessLog == null) {
			return "Invalid bmModelProcessLog Data";
		}

		return SUCCESS;

	}
}

