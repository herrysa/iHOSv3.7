package com.huge.ihos.system.configuration.proj.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.configuration.proj.model.ProjNature;
import com.huge.ihos.system.configuration.proj.service.ProjNatureManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ProjNaturePagedAction extends JqGridBaseAction implements Preparable {

	private ProjNatureManager projNatureManager;
	private List<ProjNature> projNatures;
	private ProjNature projNature;
	private String natureId;
	private String natureName;
	private String isSys;
	private String disabled;
	
	public String getNatureName() {
		return natureName;
	}

	public void setNatureName(String natureName) {
		this.natureName = natureName;
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

	public List<ProjNature> getProjNatures() {
		return projNatures;
	}

	public ProjNatureManager getProjNatureManager() {
		return projNatureManager;
	}

	public void setProjNatureManager(ProjNatureManager projNatureManager) {
		this.projNatureManager = projNatureManager;
	}

	public List<ProjNature> getprojNatures() {
		return projNatures;
	}

	public void setProjNatures(List<ProjNature> projNatures) {
		this.projNatures = projNatures;
	}

	public ProjNature getProjNature() {
		return projNature;
	}

	public void setProjNature(ProjNature projNature) {
		this.projNature = projNature;
	}

	public String getNatureId() {
		return natureId;
	}

	public void setNatureId(String natureId) {
        this.natureId = natureId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String projNatureGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = projNatureManager
					.getProjNatureCriteria(pagedRequests,filters);
			this.projNatures = (List<ProjNature>) pagedRequests.getList();
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
			projNatureManager.save(projNature);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "projNature.added" : "projNature.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (natureId != null) {
        	projNature = projNatureManager.get(natureId);
        	this.setEntityIsNew(false);
        } else {
        	projNature = new ProjNature();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String projNatureGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					projNatureManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("projNature.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("add")|| oper.equals("edit")){
                if ( projNature == null ) {
                    if ( natureId == null || "".equals(natureId)){
                    	projNature = new ProjNature();
                    }else{
                    	projNature = projNatureManager.get(natureId);
                    }
                    projNature.setDisabled("Yes".equalsIgnoreCase(disabled) ? true:false);
                    projNature.setNatureName(natureName == null ? "":natureName);
                    projNature.setIsSys("Yes".equalsIgnoreCase(isSys) ? true:false);
                }
                projNatureManager.save( projNature );
                String key = ( oper.equals( "add" ) ) ? "projNature.added" : "projNature.updated";
                gridOperationMessage = this.getText( key );
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkProjNatureGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	private String isValid() {
		if (projNature == null) {
			return "Invalid projNature Data";
		}
		return SUCCESS;

	}
}

