package com.huge.ihos.accounting.kjyear.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.accounting.kjyear.model.KjYear;
import com.huge.ihos.accounting.kjyear.service.KjYearManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class KjYearPagedAction extends JqGridBaseAction implements Preparable {

	private KjYearManager kjYearManager;
	private List<KjYear> kjYears;
	private KjYear kjYear;
	private String kjYearId;

	public KjYearManager getKjYearManager() {
		return kjYearManager;
	}

	public void setKjYearManager(KjYearManager kjYearManager) {
		this.kjYearManager = kjYearManager;
	}

	public List<KjYear> getkjYears() {
		return kjYears;
	}

	public void setKjYears(List<KjYear> kjYears) {
		this.kjYears = kjYears;
	}

	public KjYear getKjYear() {
		return kjYear;
	}

	public void setKjYear(KjYear kjYear) {
		this.kjYear = kjYear;
	}

	public String getKjYearId() {
		return kjYearId;
	}

	public void setKjYearId(String kjYearId) {
        this.kjYearId = kjYearId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String kjYearGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = kjYearManager
					.getKjYearCriteria(pagedRequests,filters);
			this.kjYears = (List<KjYear>) pagedRequests.getList();
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
			kjYearManager.save(kjYear);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "kjYear.added" : "kjYear.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (kjYearId != null) {
        	kjYear = kjYearManager.get(kjYearId);
        	this.setEntityIsNew(false);
        } else {
        	kjYear = new KjYear();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String kjYearGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					KjYear kjYear = kjYearManager.get(new String(removeId));
					kjYearManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("kjYear.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkKjYearGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (kjYear == null) {
			return "Invalid kjYear Data";
		}

		return SUCCESS;

	}
}

