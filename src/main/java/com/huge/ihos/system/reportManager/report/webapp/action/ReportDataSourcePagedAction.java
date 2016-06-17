package com.huge.ihos.system.reportManager.report.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.report.model.ReportDataSource;
import com.huge.ihos.system.reportManager.report.service.ReportDataSourceManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ReportDataSourcePagedAction extends JqGridBaseAction implements Preparable {

	private ReportDataSourceManager reportDataSourceManager;
	private List<ReportDataSource> reportDataSources;
	private ReportDataSource reportDataSource;
	private String code;

	public ReportDataSourceManager getReportDataSourceManager() {
		return reportDataSourceManager;
	}

	public void setReportDataSourceManager(ReportDataSourceManager reportDataSourceManager) {
		this.reportDataSourceManager = reportDataSourceManager;
	}

	public List<ReportDataSource> getreportDataSources() {
		return reportDataSources;
	}

	public void setReportDataSources(List<ReportDataSource> reportDataSources) {
		this.reportDataSources = reportDataSources;
	}

	public ReportDataSource getReportDataSource() {
		return reportDataSource;
	}

	public void setReportDataSource(ReportDataSource reportDataSource) {
		this.reportDataSource = reportDataSource;
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
	public String reportDataSourceGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = reportDataSourceManager
					.getReportDataSourceCriteria(pagedRequests,filters);
			this.reportDataSources = (List<ReportDataSource>) pagedRequests.getList();
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
			reportDataSourceManager.save(reportDataSource);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "reportDataSource.added" : "reportDataSource.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (code != null) {
        	reportDataSource = reportDataSourceManager.get(code);
        	this.setEntityIsNew(false);
        } else {
        	reportDataSource = new ReportDataSource();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String reportDataSourceGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					ReportDataSource reportDataSource = reportDataSourceManager.get(removeId);
					reportDataSourceManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("reportDataSource.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkReportDataSourceGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (reportDataSource == null) {
			return "Invalid reportDataSource Data";
		}

		return SUCCESS;

	}
}

