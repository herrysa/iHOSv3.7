package com.huge.ihos.bm.budgetModel.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.bm.budgetModel.model.BmDepartment;
import com.huge.ihos.bm.budgetModel.service.BmDepartmentManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BmDepartmentPagedAction extends JqGridBaseAction implements Preparable {

	private BmDepartmentManager bmDepartmentManager;
	private List<BmDepartment> bmDepartments;
	private BmDepartment bmDepartment;
	private String departmentId;

	public BmDepartmentManager getBmDepartmentManager() {
		return bmDepartmentManager;
	}

	public void setBmDepartmentManager(BmDepartmentManager bmDepartmentManager) {
		this.bmDepartmentManager = bmDepartmentManager;
	}

	public List<BmDepartment> getBmDepartments() {
		return bmDepartments;
	}

	public void setBmDepartments(List<BmDepartment> bmDepartments) {
		this.bmDepartments = bmDepartments;
	}

	public BmDepartment getBmDepartment() {
		return bmDepartment;
	}

	public void setBmDepartment(BmDepartment bmDepartment) {
		this.bmDepartment = bmDepartment;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String bmDepartmentGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = bmDepartmentManager
					.getBmDepartmentCriteria(pagedRequests,filters);
			this.bmDepartments = (List<BmDepartment>) pagedRequests.getList();
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
			bmDepartmentManager.save(bmDepartment);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "bmDepartment.added" : "bmDepartment.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (departmentId != null) {
        	bmDepartment = bmDepartmentManager.get(departmentId);
        	this.setEntityIsNew(false);
        } else {
        	bmDepartment = new BmDepartment();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String bmDepartmentGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BmDepartment bmDepartment = bmDepartmentManager.get(removeId);
					bmDepartmentManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("bmDepartment.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBmDepartmentGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (bmDepartment == null) {
			return "Invalid bmDepartment Data";
		}

		return SUCCESS;

	}
}

