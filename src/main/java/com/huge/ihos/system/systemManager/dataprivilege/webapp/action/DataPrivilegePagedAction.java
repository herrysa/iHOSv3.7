package com.huge.ihos.system.systemManager.dataprivilege.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.systemManager.dataprivilege.model.DataPrivilege;
import com.huge.ihos.system.systemManager.dataprivilege.service.DataPrivilegeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class DataPrivilegePagedAction extends JqGridBaseAction implements Preparable {

	private DataPrivilegeManager dataPrivilegeManager;
	private List<DataPrivilege> dataPrivileges;
	private DataPrivilege dataPrivilege;
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

	public DataPrivilegeManager getDataPrivilegeManager() {
		return dataPrivilegeManager;
	}

	public void setDataPrivilegeManager(DataPrivilegeManager dataPrivilegeManager) {
		this.dataPrivilegeManager = dataPrivilegeManager;
	}

	public List<DataPrivilege> getdataPrivileges() {
		return dataPrivileges;
	}

	public void setDataPrivileges(List<DataPrivilege> dataPrivileges) {
		this.dataPrivileges = dataPrivileges;
	}

	public DataPrivilege getDataPrivilege() {
		return dataPrivilege;
	}

	public void setDataPrivilege(DataPrivilege dataPrivilege) {
		this.dataPrivilege = dataPrivilege;
	}

	public String getDataPrivilegeId() {
		return dataPrivilegeId;
	}

	public void setDataPrivilegeId(String dataPrivilegeId) {
        this.dataPrivilegeId = dataPrivilegeId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String dataPrivilegeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = dataPrivilegeManager
					.getDataPrivilegeCriteria(pagedRequests,filters);
			this.dataPrivileges = (List<DataPrivilege>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String dataPrivilegeList(){
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
			pagedRequests = dataPrivilegeManager
					.getDataPrivilegeCriteria(pagedRequests,filters);
			this.dataPrivileges = (List<DataPrivilege>) pagedRequests.getList();
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
			dataPrivilegeManager.save(dataPrivilege);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "dataPrivilege.added" : "dataPrivilege.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (dataPrivilegeId != null) {
        	dataPrivilege = dataPrivilegeManager.get(dataPrivilegeId);
        	this.setEntityIsNew(false);
        } else {
        	dataPrivilege = new DataPrivilege();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String dataPrivilegeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					DataPrivilege dataPrivilege = dataPrivilegeManager.get(new String(removeId));
					dataPrivilegeManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("dataPrivilege.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkDataPrivilegeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (dataPrivilege == null) {
			return "Invalid dataPrivilege Data";
		}

		return SUCCESS;

	}
	
	public String saveRoleDataPrivilege(){
		try {
			//dataPrivilegeManager.deleteByRoleIdAndClass(ruId, privilegeClass);
			String[] sidArr = saveIds.split(",");
			String[] nameArr = names.split(",");
			String[] orgcodeArr = orgcodes.split(",");
			String[] copycodeArr = copycodes.split(",");
			String[] periodyearArr = periodyears.split(",");
			String[] readrightArr = readrights.split(",");
			String[] writerightArr = writerights.split(",");
			String[] controlallArr = controlalls.split(",");
			for(int i=0;i<sidArr.length;i++){
				DataPrivilege roleDataPrivilege = new DataPrivilege();
				roleDataPrivilege.setItem(sidArr[i].trim());
				roleDataPrivilege.setClassCode(privilegeClass.trim());
				roleDataPrivilege.setRoleId(ruId.trim());
				roleDataPrivilege.setItemName(nameArr[i].trim());
				if(!"".equals(orgcodes)&&orgcodeArr!=null&&orgcodeArr.length>0){
					roleDataPrivilege.setOrgCode(orgcodeArr[i].trim());
				}
				if(!"".equals(copycodes)&&copycodeArr!=null&&copycodeArr.length>0){
					roleDataPrivilege.setCopyCode(copycodeArr[i].trim());
				}
				if(!"".equals(periodyears)&&periodyearArr!=null&&periodyearArr.length>0){
					roleDataPrivilege.setPeriodYear(periodyearArr[i].trim());
				};
				if(!"".equals(readrights)&&readrightArr!=null&&readrightArr.length>0){
					roleDataPrivilege.setReadRight(new Boolean(readrightArr[i].trim()));
				}
				if(!"".equals(writerights)&&writerightArr!=null&&writerightArr.length>0){
					roleDataPrivilege.setWriteRight(new Boolean(writerightArr[i].trim()));
				}
				if(!"".equals(controlalls)&&controlallArr!=null&&controlallArr.length>0){
					roleDataPrivilege.setControlAll(new Boolean(controlallArr[i].trim()));
				}
				dataPrivilegeManager.save(roleDataPrivilege);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward("数据权限保存失败！");
		}
		return ajaxForward(true,"数据权限保存成功！",false);
	}
//	public String saveDataPrivilege(){
//		try {
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
}

