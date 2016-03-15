package com.huge.ihos.hr.sysTables.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.sysTables.model.SysTableKind;
import com.huge.ihos.hr.sysTables.service.SysTableKindManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class SysTableKindPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1762618128879159506L;
	private SysTableKindManager sysTableKindManager;
	private List<SysTableKind> sysTableKinds;
	private SysTableKind sysTableKind;
	private String code;

	public SysTableKindManager getSysTableKindManager() {
		return sysTableKindManager;
	}

	public void setSysTableKindManager(SysTableKindManager sysTableKindManager) {
		this.sysTableKindManager = sysTableKindManager;
	}

	public List<SysTableKind> getsysTableKinds() {
		return sysTableKinds;
	}

	public void setSysTableKinds(List<SysTableKind> sysTableKinds) {
		this.sysTableKinds = sysTableKinds;
	}

	public SysTableKind getSysTableKind() {
		return sysTableKind;
	}

	public void setSysTableKind(SysTableKind sysTableKind) {
		this.sysTableKind = sysTableKind;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
        this.code = code;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String sysTableKindGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = sysTableKindManager
					.getSysTableKindCriteria(pagedRequests,filters);
			this.sysTableKinds = (List<SysTableKind>) pagedRequests.getList();
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
			sysTableKindManager.save(sysTableKind);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "sysTableKind.added" : "sysTableKind.updated";
		return ajaxForward(this.getText(key));
	}
	
	 private int editType = 0; // 0:new,1:edit

	    public void setEditType( int editType ) {
	        this.editType = editType;
	    }

	    public int getEditType() {
	        return editType;
	    }
	    
    public String edit() {
        if (id != null) {
        	sysTableKind = sysTableKindManager.get(id);
        	this.editType = 1;
        	this.setEntityIsNew(false);
        } else {
        	sysTableKind = new SysTableKind();
        	this.editType = 0;
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String sysTableKindGridEdit() {
		try {
			if (oper.equals("del")) {
				List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.sysTableKindManager.remove(ida);
				gridOperationMessage = this.getText("sysTableKind.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkSysTableKindGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (sysTableKind == null) {
			return "Invalid sysTableKind Data";
		}

		return SUCCESS;

	}
}

