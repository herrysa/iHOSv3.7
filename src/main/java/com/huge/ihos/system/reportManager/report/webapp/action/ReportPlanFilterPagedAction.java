package com.huge.ihos.system.reportManager.report.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.report.model.ReportPlanFilter;
import com.huge.ihos.system.reportManager.report.service.ReportPlanFilterManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ReportPlanFilterPagedAction extends JqGridBaseAction implements Preparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2363867643397639506L;
	private ReportPlanFilterManager reportPlanFilterManager;
	private List<ReportPlanFilter> reportPlanFilters;
	private ReportPlanFilter reportPlanFilter;
	private String filterId;

	public ReportPlanFilterManager getReportPlanFilterManager() {
		return reportPlanFilterManager;
	}

	public void setReportPlanFilterManager(ReportPlanFilterManager reportPlanFilterManager) {
		this.reportPlanFilterManager = reportPlanFilterManager;
	}

	public List<ReportPlanFilter> getreportPlanFilters() {
		return reportPlanFilters;
	}

	public void setReportPlanFilters(List<ReportPlanFilter> reportPlanFilters) {
		this.reportPlanFilters = reportPlanFilters;
	}

	public ReportPlanFilter getReportPlanFilter() {
		return reportPlanFilter;
	}

	public void setReportPlanFilter(ReportPlanFilter reportPlanFilter) {
		this.reportPlanFilter = reportPlanFilter;
	}

	public String getFilterId() {
		return filterId;
	}

	public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String reportPlanFilterGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = reportPlanFilterManager
					.getReportPlanFilterCriteria(pagedRequests,filters);
			this.reportPlanFilters = (List<ReportPlanFilter>) pagedRequests.getList();
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
			reportPlanFilterManager.save(reportPlanFilter);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "reportPlanFilter.added" : "reportPlanFilter.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (filterId != null) {
        	reportPlanFilter = reportPlanFilterManager.get(filterId);
        	this.setEntityIsNew(false);
        } else {
        	reportPlanFilter = new ReportPlanFilter();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String reportPlanFilterGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					reportPlanFilterManager.remove(removeId);
				}
				gridOperationMessage = this.getText("reportPlanFilter.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkReportPlanFilterGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (reportPlanFilter == null) {
			return "Invalid reportPlanFilter Data";
		}

		return SUCCESS;

	}
}

