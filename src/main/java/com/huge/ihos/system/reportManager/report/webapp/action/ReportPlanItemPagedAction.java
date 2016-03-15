package com.huge.ihos.system.reportManager.report.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.report.model.ReportPlanItem;
import com.huge.ihos.system.reportManager.report.service.ReportPlanItemManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ReportPlanItemPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3444852513373740229L;
	private ReportPlanItemManager reportPlanItemManager;
	private List<ReportPlanItem> reportPlanItems;
	private ReportPlanItem reportPlanItem;
	private String colId;

	public ReportPlanItemManager getReportPlanItemManager() {
		return reportPlanItemManager;
	}

	public void setReportPlanItemManager(ReportPlanItemManager reportPlanItemManager) {
		this.reportPlanItemManager = reportPlanItemManager;
	}

	public List<ReportPlanItem> getreportPlanItems() {
		return reportPlanItems;
	}

	public void setReportPlanItems(List<ReportPlanItem> reportPlanItems) {
		this.reportPlanItems = reportPlanItems;
	}

	public ReportPlanItem getReportPlanItem() {
		return reportPlanItem;
	}

	public void setReportPlanItem(ReportPlanItem reportPlanItem) {
		this.reportPlanItem = reportPlanItem;
	}

	public String getColId() {
		return colId;
	}

	public void setColId(String colId) {
        this.colId = colId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String reportPlanItemGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = reportPlanItemManager
					.getReportPlanItemCriteria(pagedRequests,filters);
			this.reportPlanItems = (List<ReportPlanItem>) pagedRequests.getList();
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
			reportPlanItemManager.save(reportPlanItem);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "reportPlanItem.added" : "reportPlanItem.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (colId != null) {
        	reportPlanItem = reportPlanItemManager.get(colId);
        	this.setEntityIsNew(false);
        } else {
        	reportPlanItem = new ReportPlanItem();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String reportPlanItemGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					reportPlanItemManager.remove(removeId);
				}
				gridOperationMessage = this.getText("reportPlanItem.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkReportPlanItemGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (reportPlanItem == null) {
			return "Invalid reportPlanItem Data";
		}

		return SUCCESS;

	}
}

