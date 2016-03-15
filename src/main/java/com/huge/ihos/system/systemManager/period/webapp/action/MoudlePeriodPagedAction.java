package com.huge.ihos.system.systemManager.period.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.systemManager.period.model.MoudlePeriod;
import com.huge.ihos.system.systemManager.period.service.MoudlePeriodManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class MoudlePeriodPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 763044542991712744L;
	private MoudlePeriodManager moudlePeriodManager;
	private List<MoudlePeriod> moudlePeriods;
	private MoudlePeriod moudlePeriod;
	private String moudlePeriodId;

	public MoudlePeriodManager getMoudlePeriodManager() {
		return moudlePeriodManager;
	}

	public void setMoudlePeriodManager(MoudlePeriodManager moudlePeriodManager) {
		this.moudlePeriodManager = moudlePeriodManager;
	}

	public List<MoudlePeriod> getmoudlePeriods() {
		return moudlePeriods;
	}

	public void setMoudlePeriods(List<MoudlePeriod> moudlePeriods) {
		this.moudlePeriods = moudlePeriods;
	}

	public MoudlePeriod getMoudlePeriod() {
		return moudlePeriod;
	}

	public void setMoudlePeriod(MoudlePeriod moudlePeriod) {
		this.moudlePeriod = moudlePeriod;
	}

	public String getMoudlePeriodId() {
		return moudlePeriodId;
	}

	public void setMoudlePeriodId(String moudlePeriodId) {
        this.moudlePeriodId = moudlePeriodId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String moudlePeriodGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = moudlePeriodManager
					.getMoudlePeriodCriteria(pagedRequests,filters);
			this.moudlePeriods = (List<MoudlePeriod>) pagedRequests.getList();
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
			moudlePeriodManager.save(moudlePeriod);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "moudlePeriod.added" : "moudlePeriod.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (moudlePeriodId != null) {
        	moudlePeriod = moudlePeriodManager.get(moudlePeriodId);
        	this.setEntityIsNew(false);
        } else {
        	moudlePeriod = new MoudlePeriod();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String moudlePeriodGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId =ids.nextToken();
					log.debug("Delete Customer " + removeId);
					MoudlePeriod moudlePeriod = moudlePeriodManager.get(removeId);
					moudlePeriodManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("moudlePeriod.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkMoudlePeriodGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (moudlePeriod == null) {
			return "Invalid moudlePeriod Data";
		}

		return SUCCESS;

	}
}

