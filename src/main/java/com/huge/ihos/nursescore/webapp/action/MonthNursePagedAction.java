package com.huge.ihos.nursescore.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.nursescore.model.MonthNurse;
import com.huge.ihos.nursescore.service.MonthNurseManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class MonthNursePagedAction extends JqGridBaseAction  {

	private MonthNurseManager monthNurseManager;
	private List<MonthNurse> monthNurses;
	private MonthNurse monthNurse;
	private Long nurseid;

	public MonthNurseManager getMonthNurseManager() {
		return monthNurseManager;
	}

	public void setMonthNurseManager(MonthNurseManager monthNurseManager) {
		this.monthNurseManager = monthNurseManager;
	}

	public List<MonthNurse> getmonthNurses() {
		return monthNurses;
	}

	public void setMonthNurses(List<MonthNurse> monthNurses) {
		this.monthNurses = monthNurses;
	}

	public MonthNurse getMonthNurse() {
		return monthNurse;
	}

	public void setMonthNurse(MonthNurse monthNurse) {
		this.monthNurse = monthNurse;
	}

	public Long getNurseid() {
		return nurseid;
	}

	public void setNurseid(Long nurseid) {
        this.nurseid = nurseid;
    }


	public String monthNurseGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = monthNurseManager
					.getMonthNurseCriteria(pagedRequests,filters);
			this.monthNurses = (List<MonthNurse>) pagedRequests.getList();
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
			monthNurseManager.save(monthNurse);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "monthNurse.added" : "monthNurse.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	monthNurse = monthNurseManager.get(nurseid);
        	this.setEntityIsNew(false);
        } else {
        	monthNurse = new MonthNurse();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String monthNurseGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					log.debug("Delete Customer " + removeId);
					MonthNurse monthNurse = monthNurseManager.get(new Long(removeId));
					monthNurseManager.remove(new Long(removeId));
					
				}
				gridOperationMessage = this.getText("monthNurse.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkMonthNurseGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (monthNurse == null) {
			return "Invalid monthNurse Data";
		}

		return SUCCESS;

	}
}

