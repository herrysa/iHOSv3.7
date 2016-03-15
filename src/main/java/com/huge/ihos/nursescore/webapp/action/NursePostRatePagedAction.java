package com.huge.ihos.nursescore.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.nursescore.model.NursePostRate;
import com.huge.ihos.nursescore.service.NursePostRateManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class NursePostRatePagedAction extends JqGridBaseAction  {

	private NursePostRateManager nursePostRateManager;
	private List<NursePostRate> nursePostRates;
	private NursePostRate nursePostRate;
	private String code;

	public NursePostRateManager getNursePostRateManager() {
		return nursePostRateManager;
	}

	public void setNursePostRateManager(NursePostRateManager nursePostRateManager) {
		this.nursePostRateManager = nursePostRateManager;
	}

	public List<NursePostRate> getnursePostRates() {
		return nursePostRates;
	}

	public void setNursePostRates(List<NursePostRate> nursePostRates) {
		this.nursePostRates = nursePostRates;
	}

	public NursePostRate getNursePostRate() {
		return nursePostRate;
	}

	public void setNursePostRate(NursePostRate nursePostRate) {
		this.nursePostRate = nursePostRate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
        this.code = code;
    }


	public String nursePostRateGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = nursePostRateManager
					.getNursePostRateCriteria(pagedRequests,filters);
			this.nursePostRates = (List<NursePostRate>) pagedRequests.getList();
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
			nursePostRateManager.save(nursePostRate);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "nursePostRate.added" : "nursePostRate.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (code != null) {
        	nursePostRate = nursePostRateManager.get(code);
        	this.setEntityIsNew(false);
        } else {
        	nursePostRate = new NursePostRate();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String nursePostRateGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					NursePostRate nursePostRate = nursePostRateManager.get(removeId);
					nursePostRateManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("nursePostRate.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkNursePostRateGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (nursePostRate == null) {
			return "Invalid nursePostRate Data";
		}

		return SUCCESS;

	}
}

