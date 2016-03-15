package com.huge.ihos.kq.kqHoliday.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.kq.kqHoliday.model.KqHoliday;
import com.huge.ihos.kq.kqHoliday.service.KqHolidayManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class KqHolidayPagedAction extends JqGridBaseAction implements Preparable {

	private KqHolidayManager kqHolidayManager;
	private List<KqHoliday> kqHolidays;
	private KqHoliday kqHoliday;
	private String holidayId;

	public KqHolidayManager getKqHolidayManager() {
		return kqHolidayManager;
	}

	public void setKqHolidayManager(KqHolidayManager kqHolidayManager) {
		this.kqHolidayManager = kqHolidayManager;
	}

	public List<KqHoliday> getkqHolidays() {
		return kqHolidays;
	}

	public void setKqHolidays(List<KqHoliday> kqHolidays) {
		this.kqHolidays = kqHolidays;
	}

	public KqHoliday getKqHoliday() {
		return kqHoliday;
	}

	public void setKqHoliday(KqHoliday kqHoliday) {
		this.kqHoliday = kqHoliday;
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
	public String kqHolidayList() {
		try {
			List<MenuButton> menuButtons = getMenuButtons();
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("kqHolidayList Error",e);
		}
		return SUCCESS;
	}
	public String kqHolidayGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = kqHolidayManager
					.getKqHolidayCriteria(pagedRequests,filters);
			this.kqHolidays = (List<KqHoliday>) pagedRequests.getList();
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
			kqHolidayManager.save(kqHoliday);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "kqHoliday.added" : "kqHoliday.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (holidayId != null) {
        	kqHoliday = kqHolidayManager.get(holidayId);
        	this.setEntityIsNew(false);
        } else {
        	kqHoliday = new KqHoliday();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String kqHolidayGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					//KqHoliday kqHoliday = kqHolidayManager.get(removeId);
					kqHolidayManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("kqHoliday.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkKqHolidayGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (kqHoliday == null) {
			return "Invalid kqHoliday Data";
		}

		return SUCCESS;

	}
}

