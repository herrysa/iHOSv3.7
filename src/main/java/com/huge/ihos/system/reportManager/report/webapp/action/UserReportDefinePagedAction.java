package com.huge.ihos.system.reportManager.report.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.report.model.UserReportDefine;
import com.huge.ihos.system.reportManager.report.service.UserReportDefineManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class UserReportDefinePagedAction extends JqGridBaseAction implements Preparable {

	private UserReportDefineManager userReportDefineManager;
	private List<UserReportDefine> userReportDefines;
	private UserReportDefine userReportDefine;
	private String defineId;

	public UserReportDefineManager getUserReportDefineManager() {
		return userReportDefineManager;
	}

	public void setUserReportDefineManager(UserReportDefineManager userReportDefineManager) {
		this.userReportDefineManager = userReportDefineManager;
	}

	public List<UserReportDefine> getuserReportDefines() {
		return userReportDefines;
	}

	public void setUserReportDefines(List<UserReportDefine> userReportDefines) {
		this.userReportDefines = userReportDefines;
	}

	public UserReportDefine getUserReportDefine() {
		return userReportDefine;
	}

	public void setUserReportDefine(UserReportDefine userReportDefine) {
		this.userReportDefine = userReportDefine;
	}

	public String getDefineId() {
		return defineId;
	}

	public void setDefineId(String defineId) {
        this.defineId = defineId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String userReportDefineGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = userReportDefineManager
					.getUserReportDefineCriteria(pagedRequests,filters);
			this.userReportDefines = (List<UserReportDefine>) pagedRequests.getList();
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
			userReportDefineManager.save(userReportDefine);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "userReportDefine.added" : "userReportDefine.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (defineId != null) {
        	userReportDefine = userReportDefineManager.get(defineId);
        	this.setEntityIsNew(false);
        } else {
        	userReportDefine = new UserReportDefine();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String userReportDefineGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					log.debug("Delete Customer " + removeId);
					//UserReportDefine userReportDefine = userReportDefineManager.get(new String(removeId));
					//userReportDefineManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("userReportDefine.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkUserReportDefineGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (userReportDefine == null) {
			return "Invalid userReportDefine Data";
		}

		return SUCCESS;

	}
}

