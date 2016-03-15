package com.huge.ihos.kq.generalHoliday.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.kq.generalHoliday.model.GeneralHoliday;
import com.huge.ihos.kq.generalHoliday.service.GeneralHolidayManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class GeneralHolidayPagedAction extends JqGridBaseAction implements Preparable {

	private GeneralHolidayManager generalHolidayManager;
	private List<GeneralHoliday> generalHolidays;
	private GeneralHoliday generalHoliday;
	private String holidayId;

	public GeneralHolidayManager getGeneralHolidayManager() {
		return generalHolidayManager;
	}

	public void setGeneralHolidayManager(GeneralHolidayManager generalHolidayManager) {
		this.generalHolidayManager = generalHolidayManager;
	}

	public List<GeneralHoliday> getgeneralHolidays() {
		return generalHolidays;
	}

	public void setGeneralHolidays(List<GeneralHoliday> generalHolidays) {
		this.generalHolidays = generalHolidays;
	}

	public GeneralHoliday getGeneralHoliday() {
		return generalHoliday;
	}

	public void setGeneralHoliday(GeneralHoliday generalHoliday) {
		this.generalHoliday = generalHoliday;
	}

	public String getHolidayId() {
		return holidayId;
	}

	public void setHolidayId(String holidayId) {
        this.holidayId = holidayId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String generalHolidayGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = generalHolidayManager
					.getGeneralHolidayCriteria(pagedRequests,filters);
			this.generalHolidays = (List<GeneralHoliday>) pagedRequests.getList();
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
			generalHolidayManager.save(generalHoliday);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "generalHoliday.added" : "generalHoliday.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (holidayId != null) {
        	generalHoliday = generalHolidayManager.get(holidayId);
        	this.setEntityIsNew(false);
        } else {
        	generalHoliday = new GeneralHoliday();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String generalHolidayGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					//GeneralHoliday generalHoliday = generalHolidayManager.get(removeId);
					generalHolidayManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("generalHoliday.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkGeneralHolidayGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (generalHoliday == null) {
			return "Invalid generalHoliday Data";
		}

		return SUCCESS;

	}
}

