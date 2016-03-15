package com.huge.ihos.kq.generalHoliday.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.kq.generalHoliday.model.GeneralHolidayChange;
import com.huge.ihos.kq.generalHoliday.service.GeneralHolidayChangeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class GeneralHolidayChangePagedAction extends JqGridBaseAction implements Preparable {

	private GeneralHolidayChangeManager generalHolidayChangeManager;
	private List<GeneralHolidayChange> generalHolidayChanges;
	private GeneralHolidayChange generalHolidayChange;
	private String changeId;

	public GeneralHolidayChangeManager getGeneralHolidayChangeManager() {
		return generalHolidayChangeManager;
	}

	public void setGeneralHolidayChangeManager(GeneralHolidayChangeManager generalHolidayChangeManager) {
		this.generalHolidayChangeManager = generalHolidayChangeManager;
	}

	public List<GeneralHolidayChange> getgeneralHolidayChanges() {
		return generalHolidayChanges;
	}

	public void setGeneralHolidayChanges(List<GeneralHolidayChange> generalHolidayChanges) {
		this.generalHolidayChanges = generalHolidayChanges;
	}

	public GeneralHolidayChange getGeneralHolidayChange() {
		return generalHolidayChange;
	}

	public void setGeneralHolidayChange(GeneralHolidayChange generalHolidayChange) {
		this.generalHolidayChange = generalHolidayChange;
	}

	public String getChangeId() {
		return changeId;
	}

	public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String generalHolidayChangeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = generalHolidayChangeManager
					.getGeneralHolidayChangeCriteria(pagedRequests,filters);
			this.generalHolidayChanges = (List<GeneralHolidayChange>) pagedRequests.getList();
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
			generalHolidayChangeManager.save(generalHolidayChange);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "generalHolidayChange.added" : "generalHolidayChange.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (changeId != null) {
        	generalHolidayChange = generalHolidayChangeManager.get(changeId);
        	this.setEntityIsNew(false);
        } else {
        	generalHolidayChange = new GeneralHolidayChange();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String generalHolidayChangeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					//GeneralHolidayChange generalHolidayChange = generalHolidayChangeManager.get(removeId);
					generalHolidayChangeManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("generalHolidayChange.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkGeneralHolidayChangeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (generalHolidayChange == null) {
			return "Invalid generalHolidayChange Data";
		}

		return SUCCESS;

	}
}

