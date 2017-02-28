package com.huge.ihos.system.configuration.proj.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.configuration.proj.model.ProjType;
import com.huge.ihos.system.configuration.proj.service.ProjTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ProjTypePagedAction extends JqGridBaseAction implements Preparable {

	private ProjTypeManager projTypeManager;
	private List<ProjType> projTypes;
	private ProjType projType;
	private String typeId;
	private String typeName;
	private String isSys;
	private String disabled;
	private String projDetailKind;
	
	

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getIsSys() {
		return isSys;
	}

	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getProjDetailKind() {
		return projDetailKind;
	}

	public void setProjDetailKind(String projDetailKind) {
		this.projDetailKind = projDetailKind;
	}

	public List<ProjType> getProjTypes() {
		return projTypes;
	}

	public ProjTypeManager getProjTypeManager() {
		return projTypeManager;
	}

	public void setProjTypeManager(ProjTypeManager projTypeManager) {
		this.projTypeManager = projTypeManager;
	}

	public List<ProjType> getprojTypes() {
		return projTypes;
	}

	public void setProjTypes(List<ProjType> projTypes) {
		this.projTypes = projTypes;
	}

	public ProjType getProjType() {
		return projType;
	}

	public void setProjType(ProjType projType) {
		this.projType = projType;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	
	public String projMainList(){
		
		return SUCCESS;
	}
	public String projTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = projTypeManager
					.getProjTypeCriteria(pagedRequests,filters);
			this.projTypes = (List<ProjType>) pagedRequests.getList();
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
			projTypeManager.save(projType);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "projType.added" : "projType.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (typeId != null) {
        	projType = projTypeManager.get(typeId);
        	this.setEntityIsNew(false);
        } else {
        	projType = new ProjType();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String projTypeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					projTypeManager.remove(removeId);
				}
				gridOperationMessage = this.getText("projType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("add")|| oper.equals("edit")){
                if ( projType == null ) {
                    if ( typeId == null || "".equals(typeId)){
                    	projType = new ProjType();
                    }else{
                    	projType = projTypeManager.get(typeId);
                    }
                    projType.setDisabled("Yes".equalsIgnoreCase(disabled) ? true:false);
                    projType.setIsSys("Yes".equalsIgnoreCase(isSys) ? true:false);
                    projType.setTypeName(typeName == null ? "":typeName);
                    projType.setProjDetailKind(projDetailKind == null ? "":projDetailKind);
                    projType.setCnCode(projTypeManager.pyCode(typeName == null ? "":typeName));
                }
                projTypeManager.save( projType );
                String key = ( oper.equals( "add" ) ) ? "projType.added" : "projType.updated";
                gridOperationMessage = this.getText( key );
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkProjTypeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (projType == null) {
			return "Invalid projType Data";
		}

		return SUCCESS;

	}
}

