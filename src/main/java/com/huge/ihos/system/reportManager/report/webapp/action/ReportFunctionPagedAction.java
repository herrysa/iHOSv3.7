package com.huge.ihos.system.reportManager.report.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.report.model.ReportFunction;
import com.huge.ihos.system.reportManager.report.service.ReportFunctionManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ReportFunctionPagedAction extends JqGridBaseAction implements Preparable {

	private ReportFunctionManager reportFunctionManager;
	private List<ReportFunction> reportFunctions;
	private ReportFunction reportFunction;
	private String code;

	public ReportFunctionManager getReportFunctionManager() {
		return reportFunctionManager;
	}

	public void setReportFunctionManager(ReportFunctionManager reportFunctionManager) {
		this.reportFunctionManager = reportFunctionManager;
	}

	public List<ReportFunction> getreportFunctions() {
		return reportFunctions;
	}

	public void setReportFunctions(List<ReportFunction> reportFunctions) {
		this.reportFunctions = reportFunctions;
	}

	public ReportFunction getReportFunction() {
		return reportFunction;
	}

	public void setReportFunction(ReportFunction reportFunction) {
		this.reportFunction = reportFunction;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
        this.code = code;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String reportFunctionGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = reportFunctionManager
					.getReportFunctionCriteria(pagedRequests,filters);
			this.reportFunctions = (List<ReportFunction>) pagedRequests.getList();
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
			reportFunctionManager.save(reportFunction);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "reportFunction.added" : "reportFunction.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (code != null) {
        	reportFunction = reportFunctionManager.get(code);
        	this.setEntityIsNew(false);
        } else {
        	reportFunction = new ReportFunction();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String reportFunctionGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					ReportFunction reportFunction = reportFunctionManager.get(removeId);
					reportFunctionManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("reportFunction.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkReportFunctionGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (reportFunction == null) {
			return "Invalid reportFunction Data";
		}

		return SUCCESS;

	}
}

