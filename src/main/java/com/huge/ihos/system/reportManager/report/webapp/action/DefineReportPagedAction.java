package com.huge.ihos.system.reportManager.report.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.report.model.DefineReport;
import com.huge.ihos.system.reportManager.report.service.DefineReportManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class DefineReportPagedAction extends JqGridBaseAction implements Preparable {

	private DefineReportManager defineReportManager;
	private List<DefineReport> defineReports;
	private DefineReport defineReport;
	private String code;

	public DefineReportManager getDefineReportManager() {
		return defineReportManager;
	}

	public void setDefineReportManager(DefineReportManager defineReportManager) {
		this.defineReportManager = defineReportManager;
	}

	public List<DefineReport> getdefineReports() {
		return defineReports;
	}

	public void setDefineReports(List<DefineReport> defineReports) {
		this.defineReports = defineReports;
	}

	public DefineReport getDefineReport() {
		return defineReport;
	}

	public void setDefineReport(DefineReport defineReport) {
		this.defineReport = defineReport;
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
	public String defineReportGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = defineReportManager
					.getDefineReportCriteria(pagedRequests,filters);
			this.defineReports = (List<DefineReport>) pagedRequests.getList();
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
			defineReportManager.save(defineReport);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "defineReport.added" : "defineReport.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (code != null) {
        	defineReport = defineReportManager.get(code);
        	this.setEntityIsNew(false);
        } else {
        	defineReport = new DefineReport();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String defineReportGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					DefineReport defineReport = defineReportManager.get(removeId);
					defineReportManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("defineReport.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkDefineReportGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String editDefineReport(){
		try {
			if (code != null) {
	        	defineReport = defineReportManager.get(code);
	        	reportXml = defineReport.getReport();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	String reportXml;
	public String getReportXml() {
		return reportXml;
	}

	public void setReportXml(String reportXml) {
		this.reportXml = reportXml;
	}

	public String saveDefineReportXml(){
		try {
			if (code != null) {
	        	defineReport = defineReportManager.get(code);
	        	defineReport.setReport(reportXml);
	        	defineReportManager.save(defineReport);
	        	return ajaxForward("保存成功！");
	        } else {
	        	return ajaxForward(false, "保存失败！");
	        }
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "保存失败！");
		}
		
	}

	private String isValid() {
		if (defineReport == null) {
			return "Invalid defineReport Data";
		}

		return SUCCESS;

	}
}

