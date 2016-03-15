package com.huge.ihos.nursescore.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.nursescore.model.NurseShiftRate;
import com.huge.ihos.nursescore.service.NurseShiftRateManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class NurseShiftRatePagedAction extends JqGridBaseAction  {

	private NurseShiftRateManager nurseShiftRateManager;
	private List<NurseShiftRate> nurseShiftRates;
	private NurseShiftRate nurseShiftRate;
	private String code;

	public NurseShiftRateManager getNurseShiftRateManager() {
		return nurseShiftRateManager;
	}

	public void setNurseShiftRateManager(NurseShiftRateManager nurseShiftRateManager) {
		this.nurseShiftRateManager = nurseShiftRateManager;
	}

	public List<NurseShiftRate> getnurseShiftRates() {
		return nurseShiftRates;
	}

	public void setNurseShiftRates(List<NurseShiftRate> nurseShiftRates) {
		this.nurseShiftRates = nurseShiftRates;
	}

	public NurseShiftRate getNurseShiftRate() {
		return nurseShiftRate;
	}

	public void setNurseShiftRate(NurseShiftRate nurseShiftRate) {
		this.nurseShiftRate = nurseShiftRate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
        this.code = code;
    }


	public String nurseShiftRateGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = nurseShiftRateManager
					.getNurseShiftRateCriteria(pagedRequests,filters);
			this.nurseShiftRates = (List<NurseShiftRate>) pagedRequests.getList();
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
			nurseShiftRateManager.save(nurseShiftRate);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "nurseShiftRate.added" : "nurseShiftRate.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (code != null) {
        	nurseShiftRate = nurseShiftRateManager.get(code);
        	this.setEntityIsNew(false);
        } else {
        	nurseShiftRate = new NurseShiftRate();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String nurseShiftRateGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					NurseShiftRate nurseShiftRate = nurseShiftRateManager.get(removeId);
					nurseShiftRateManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("nurseShiftRate.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkNurseShiftRateGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (nurseShiftRate == null) {
			return "Invalid nurseShiftRate Data";
		}

		return SUCCESS;

	}
}

