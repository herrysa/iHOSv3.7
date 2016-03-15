package com.huge.ihos.orgprivilege.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.orgprivilege.model.OrgPrivilege;
import com.huge.ihos.orgprivilege.service.OrgPrivilegeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class OrgPrivilegePagedAction extends JqGridBaseAction implements Preparable {

	private OrgPrivilegeManager orgPrivilegeManager;
	private List<OrgPrivilege> orgPrivileges;
	private OrgPrivilege orgPrivilege;
	private String privilegeId;

	public OrgPrivilegeManager getOrgPrivilegeManager() {
		return orgPrivilegeManager;
	}

	public void setOrgPrivilegeManager(OrgPrivilegeManager orgPrivilegeManager) {
		this.orgPrivilegeManager = orgPrivilegeManager;
	}

	public List<OrgPrivilege> getorgPrivileges() {
		return orgPrivileges;
	}

	public void setOrgPrivileges(List<OrgPrivilege> orgPrivileges) {
		this.orgPrivileges = orgPrivileges;
	}

	public OrgPrivilege getOrgPrivilege() {
		return orgPrivilege;
	}

	public void setOrgPrivilege(OrgPrivilege orgPrivilege) {
		this.orgPrivilege = orgPrivilege;
	}

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String orgPrivilegeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = orgPrivilegeManager
					.getOrgPrivilegeCriteria(pagedRequests,filters);
			this.orgPrivileges = (List<OrgPrivilege>) pagedRequests.getList();
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
			orgPrivilegeManager.save(orgPrivilege);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "orgPrivilege.added" : "orgPrivilege.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (privilegeId != null) {
        	orgPrivilege = orgPrivilegeManager.get(privilegeId);
        	this.setEntityIsNew(false);
        } else {
        	orgPrivilege = new OrgPrivilege();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String orgPrivilegeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					OrgPrivilege orgPrivilege = orgPrivilegeManager.get(removeId);
					orgPrivilegeManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("orgPrivilege.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkOrgPrivilegeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (orgPrivilege == null) {
			return "Invalid orgPrivilege Data";
		}

		return SUCCESS;

	}
}

