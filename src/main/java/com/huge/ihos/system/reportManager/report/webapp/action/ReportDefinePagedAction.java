package com.huge.ihos.system.reportManager.report.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.report.model.ReportDefine;
import com.huge.ihos.system.reportManager.report.service.ReportDefineManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ReportDefinePagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 426833688182399544L;
	private ReportDefineManager reportDefineManager;
	private List<ReportDefine> reportDefines;
	private ReportDefine reportDefine;
	private String defineId;

	public ReportDefineManager getReportDefineManager() {
		return reportDefineManager;
	}

	public void setReportDefineManager(ReportDefineManager reportDefineManager) {
		this.reportDefineManager = reportDefineManager;
	}

	public List<ReportDefine> getreportDefines() {
		return reportDefines;
	}

	public void setReportDefines(List<ReportDefine> reportDefines) {
		this.reportDefines = reportDefines;
	}

	public ReportDefine getReportDefine() {
		return reportDefine;
	}

	public void setReportDefine(ReportDefine reportDefine) {
		this.reportDefine = reportDefine;
	}

	public String getDefineId() {
		return defineId;
	}

	public void setDefineId(String defineId) {
        this.defineId = defineId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String reportDefineGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = reportDefineManager
					.getReportDefineCriteria(pagedRequests,filters);
			this.reportDefines = (List<ReportDefine>) pagedRequests.getList();
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
			reportDefineManager.save(reportDefine);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "reportDefine.added" : "reportDefine.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (defineId != null) {
        	reportDefine = reportDefineManager.get(defineId);
        	this.setEntityIsNew(false);
        } else {
        	reportDefine = new ReportDefine();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String reportDefineGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					reportDefineManager.remove(removeId);
				}
				gridOperationMessage = this.getText("reportDefine.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkReportDefineGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (reportDefine == null) {
			return "Invalid reportDefine Data";
		}

		return SUCCESS;

	}
}

