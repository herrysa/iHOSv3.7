package com.huge.ihos.system.configuration.proj.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.configuration.proj.model.ProjUse;
import com.huge.ihos.system.configuration.proj.service.ProjUseManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ProjUsePagedAction extends JqGridBaseAction implements Preparable {

	private ProjUseManager projUseManager;
	private List<ProjUse> projUses;
	private ProjUse projUse;
	private String useCode;
	private String useName;
	private String disabled;

	
	
	public String getUseName() {
		return useName;
	}

	public void setUseName(String useName) {
		this.useName = useName;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public List<ProjUse> getProjUses() {
		return projUses;
	}

	public ProjUseManager getProjUseManager() {
		return projUseManager;
	}

	public void setProjUseManager(ProjUseManager projUseManager) {
		this.projUseManager = projUseManager;
	}

	public List<ProjUse> getprojUses() {
		return projUses;
	}

	public void setProjUses(List<ProjUse> projUses) {
		this.projUses = projUses;
	}

	public ProjUse getProjUse() {
		return projUse;
	}

	public void setProjUse(ProjUse projUse) {
		this.projUse = projUse;
	}

	public String getUseCode() {
		return useCode;
	}

	public void setUseCode(String useCode) {
        this.useCode = useCode;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String projUseGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = projUseManager
					.getProjUseCriteria(pagedRequests,filters);
			this.projUses = (List<ProjUse>) pagedRequests.getList();
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
			projUseManager.save(projUse);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "projUse.added" : "projUse.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (useCode != null) {
        	projUse = projUseManager.get(useCode);
        	this.setEntityIsNew(false);
        } else {
        	projUse = new ProjUse();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String projUseGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					projUseManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("projUse.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("add")|| oper.equals("edit")){
                if ( projUse == null ) {
                    if ( useCode == null || "".equals(useCode)){
                    	projUse = new ProjUse();
                    }else{
                    	projUse = projUseManager.get(useCode);
                    }
                    projUse.setDisabled("Yes".equalsIgnoreCase(disabled) ? true:false);
                    projUse.setUseName(useName == null ? "":useName);
                }
                projUseManager.save( projUse );
                String key = ( oper.equals( "add" ) ) ? "projUse.added" : "projUse.updated";
                gridOperationMessage = this.getText( key );
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkProjUseGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (projUse == null) {
			return "Invalid projUse Data";
		}

		return SUCCESS;

	}
}

