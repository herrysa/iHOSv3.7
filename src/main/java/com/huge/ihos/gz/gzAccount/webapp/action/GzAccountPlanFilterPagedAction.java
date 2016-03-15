package com.huge.ihos.gz.gzAccount.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.gz.gzAccount.model.GzAccountPlanFilter;
import com.huge.ihos.gz.gzAccount.service.GzAccountPlanFilterManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class GzAccountPlanFilterPagedAction extends JqGridBaseAction implements Preparable {

	private GzAccountPlanFilterManager gzAccountPlanFliterManager;
	private List<GzAccountPlanFilter> gzAccountPlanFliters;
	private GzAccountPlanFilter gzAccountPlanFliter;
	private String filterId;

	public GzAccountPlanFilterManager getGzAccountPlanFliterManager() {
		return gzAccountPlanFliterManager;
	}

	public void setGzAccountPlanFliterManager(GzAccountPlanFilterManager gzAccountPlanFliterManager) {
		this.gzAccountPlanFliterManager = gzAccountPlanFliterManager;
	}

	public List<GzAccountPlanFilter> getgzAccountPlanFliters() {
		return gzAccountPlanFliters;
	}

	public void setGzAccountPlanFliters(List<GzAccountPlanFilter> gzAccountPlanFliters) {
		this.gzAccountPlanFliters = gzAccountPlanFliters;
	}

	public GzAccountPlanFilter getGzAccountPlanFliter() {
		return gzAccountPlanFliter;
	}

	public void setGzAccountPlanFliter(GzAccountPlanFilter gzAccountPlanFliter) {
		this.gzAccountPlanFliter = gzAccountPlanFliter;
	}

	public String getFilterId() {
		return filterId;
	}

	public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String gzAccountPlanFliterGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = gzAccountPlanFliterManager
					.getGzAccountPlanFliterCriteria(pagedRequests,filters);
			this.gzAccountPlanFliters = (List<GzAccountPlanFilter>) pagedRequests.getList();
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
			gzAccountPlanFliterManager.save(gzAccountPlanFliter);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "gzAccountPlanFliter.added" : "gzAccountPlanFliter.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (filterId != null) {
        	gzAccountPlanFliter = gzAccountPlanFliterManager.get(filterId);
        	this.setEntityIsNew(false);
        } else {
        	gzAccountPlanFliter = new GzAccountPlanFilter();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String gzAccountPlanFliterGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					GzAccountPlanFilter gzAccountPlanFliter = gzAccountPlanFliterManager.get(new String(removeId));
					gzAccountPlanFliterManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("gzAccountPlanFliter.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkGzAccountPlanFliterGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (gzAccountPlanFliter == null) {
			return "Invalid gzAccountPlanFliter Data";
		}

		return SUCCESS;

	}
}

