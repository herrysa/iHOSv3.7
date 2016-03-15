package com.huge.ihos.system.systemManager.dataprivilege.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.systemManager.dataprivilege.model.UserDataPrivilege;
import com.huge.ihos.system.systemManager.dataprivilege.service.UserDataPrivilegeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class UserDataPrivilegePagedAction extends JqGridBaseAction implements Preparable {

	private UserDataPrivilegeManager userDataPrivilegeManager;
	private List<UserDataPrivilege> userDataPrivileges;
	private UserDataPrivilege userDataPrivilege;
	private String dataPrivilegeId;
	
	private String privilegeClass;
	private String ruId;
	private String saveIds;
	private String names;
	private String orgcodes;
	private String copycodes;
	private String periodyears;
	private String readrights;
	private String writerights;
	private String controlalls;
	
	public String getReadrights() {
		return readrights;
	}

	public void setReadrights(String readrights) {
		this.readrights = readrights;
	}

	public String getWriterights() {
		return writerights;
	}

	public void setWriterights(String writerights) {
		this.writerights = writerights;
	}

	public String getControlalls() {
		return controlalls;
	}

	public void setControlalls(String controlalls) {
		this.controlalls = controlalls;
	}
	public String getPeriodyears() {
		return periodyears;
	}

	public void setPeriodyears(String periodyears) {
		this.periodyears = periodyears;
	}

	private String exceptIds;

	public String getExceptIds() {
		return exceptIds;
	}

	public void setExceptIds(String exceptIds) {
		this.exceptIds = exceptIds;
	}

	public UserDataPrivilegeManager getUserDataPrivilegeManager() {
		return userDataPrivilegeManager;
	}

	public void setUserDataPrivilegeManager(UserDataPrivilegeManager userDataPrivilegeManager) {
		this.userDataPrivilegeManager = userDataPrivilegeManager;
	}

	public List<UserDataPrivilege> getuserDataPrivileges() {
		return userDataPrivileges;
	}

	public void setUserDataPrivileges(List<UserDataPrivilege> userDataPrivileges) {
		this.userDataPrivileges = userDataPrivileges;
	}

	public UserDataPrivilege getUserDataPrivilege() {
		return userDataPrivilege;
	}

	public void setUserDataPrivilege(UserDataPrivilege userDataPrivilege) {
		this.userDataPrivilege = userDataPrivilege;
	}

	public String getDataPrivilegeId() {
		return dataPrivilegeId;
	}

	public void setDataPrivilegeId(String dataPrivilegeId) {
        this.dataPrivilegeId = dataPrivilegeId;
    }

	public String getPrivilegeClass() {
		return privilegeClass;
	}

	public void setPrivilegeClass(String privilegeClass) {
		this.privilegeClass = privilegeClass;
	}

	public String getRuId() {
		return ruId;
	}

	public void setRuId(String ruId) {
		this.ruId = ruId;
	}

	public String getSaveIds() {
		return saveIds;
	}

	public void setSaveIds(String saveIds) {
		this.saveIds = saveIds;
	}
	
	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getOrgcodes() {
		return orgcodes;
	}

	public void setOrgcodes(String orgcodes) {
		this.orgcodes = orgcodes;
	}

	public String getCopycodes() {
		return copycodes;
	}

	public void setCopycodes(String copycodes) {
		this.copycodes = copycodes;
	}
	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String userDataPrivilegeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = userDataPrivilegeManager
					.getUserDataPrivilegeCriteria(pagedRequests,filters);
			this.userDataPrivileges = (List<UserDataPrivilege>) pagedRequests.getList();
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
			userDataPrivilegeManager.save(userDataPrivilege);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "userDataPrivilege.added" : "userDataPrivilege.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (dataPrivilegeId != null) {
        	userDataPrivilege = userDataPrivilegeManager.get(dataPrivilegeId);
        	this.setEntityIsNew(false);
        } else {
        	userDataPrivilege = new UserDataPrivilege();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String userDataPrivilegeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					UserDataPrivilege userDataPrivilege = userDataPrivilegeManager.get(new String(removeId));
					userDataPrivilegeManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("userDataPrivilege.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkUserDataPrivilegeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (userDataPrivilege == null) {
			return "Invalid userDataPrivilege Data";
		}

		return SUCCESS;

	}
	
	public String saveUserDataPrivilege(){
		try {
			//userDataPrivilegeManager.deleteByUserIdAndClass(ruId, privilegeClass,exceptIds);
			String[] sidArr = saveIds.split(",");
			String[] nameArr = names.split(",");
			String[] orgcodeArr = orgcodes.split(",");
			String[] copycodeArr = copycodes.split(",");
			String[] periodyearArr = periodyears.split(",");
			String[] readrightArr = readrights.split(",");
			String[] writerightArr = writerights.split(",");
			String[] controlallArr = controlalls.split(",");
			for(int i=0;i<sidArr.length;i++){
				UserDataPrivilege userDataPrivilege = new UserDataPrivilege();
				userDataPrivilege.setItem(sidArr[i].trim());
				userDataPrivilege.setClassCode(privilegeClass);
				userDataPrivilege.setUserId(ruId);
				userDataPrivilege.setItemName(nameArr[i].trim());
				if(!"".equals(orgcodes)&&orgcodeArr!=null&&orgcodeArr.length>0){
					userDataPrivilege.setOrgCode(orgcodeArr[i]);
				}
				if(!"".equals(copycodes)&&copycodeArr!=null&&copycodeArr.length>0){
					userDataPrivilege.setCopyCode(copycodeArr[i]);
				}
				if(!"".equals(periodyears)&&periodyearArr!=null&&periodyearArr.length>0){
					userDataPrivilege.setPeriodYear(periodyearArr[i]);
				}
				if(!"".equals(readrights)&&readrightArr!=null&&readrightArr.length>0){
					userDataPrivilege.setReadRight(new Boolean(readrightArr[i].trim()));
				}
				if(!"".equals(writerights)&&writerightArr!=null&&writerightArr.length>0){
					userDataPrivilege.setWriteRight(new Boolean(writerightArr[i].trim()));
				}
				if(!"".equals(controlalls)&&controlallArr!=null&&controlallArr.length>0){
					userDataPrivilege.setControlAll(new Boolean(controlallArr[i].trim()));
				}
				userDataPrivilegeManager.save(userDataPrivilege);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward("保存失败！");
		}
		return ajaxForward(true,"保存成功！",false);
	}
}

