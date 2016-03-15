package com.huge.ihos.nursescore.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.nursescore.model.NurseYearRate;
import com.huge.ihos.nursescore.service.NurseYearRateManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class NurseYearRatePagedAction extends JqGridBaseAction  {

	private NurseYearRateManager nurseYearRateManager;
	private List<NurseYearRate> nurseYearRates;
	private NurseYearRate nurseYearRate;
	private String code;

	public NurseYearRateManager getNurseYearRateManager() {
		return nurseYearRateManager;
	}

	public void setNurseYearRateManager(NurseYearRateManager nurseYearRateManager) {
		this.nurseYearRateManager = nurseYearRateManager;
	}

	public List<NurseYearRate> getnurseYearRates() {
		return nurseYearRates;
	}

	public void setNurseYearRates(List<NurseYearRate> nurseYearRates) {
		this.nurseYearRates = nurseYearRates;
	}

	public NurseYearRate getNurseYearRate() {
		return nurseYearRate;
	}

	public void setNurseYearRate(NurseYearRate nurseYearRate) {
		this.nurseYearRate = nurseYearRate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
        this.code = code;
    }


	public String nurseYearRateGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = nurseYearRateManager
					.getNurseYearRateCriteria(pagedRequests,filters);
			this.nurseYearRates = (List<NurseYearRate>) pagedRequests.getList();
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
			nurseYearRateManager.save(nurseYearRate);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "nurseYearRate.added" : "nurseYearRate.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (code != null) {
        	nurseYearRate = nurseYearRateManager.get(code);
        	this.setEntityIsNew(false);
        } else {
        	nurseYearRate = new NurseYearRate();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String nurseYearRateGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					NurseYearRate nurseYearRate = nurseYearRateManager.get(removeId);
					nurseYearRateManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("nurseYearRate.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkNurseYearRateGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (nurseYearRate == null) {
			return "Invalid nurseYearRate Data";
		}

		return SUCCESS;

	}
}

