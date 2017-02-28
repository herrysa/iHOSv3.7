package com.huge.ihos.material.businessType.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.material.businessType.model.MmBusinessType;
import com.huge.ihos.material.businessType.service.MmBusinessTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BusinessTypePagedAction extends JqGridBaseAction implements Preparable {

	private MmBusinessTypeManager businessTypeManager;
	private List<MmBusinessType> businessTypes;
	private MmBusinessType businessType;
	private String businessTypeId;

	public MmBusinessTypeManager getBusinessTypeManager() {
		return businessTypeManager;
	}

	public void setBusinessTypeManager(MmBusinessTypeManager businessTypeManager) {
		this.businessTypeManager = businessTypeManager;
	}

	public List<MmBusinessType> getbusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(List<MmBusinessType> businessTypes) {
		this.businessTypes = businessTypes;
	}

	public MmBusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(MmBusinessType businessType) {
		this.businessType = businessType;
	}

	public String getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(String businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String businessTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = businessTypeManager
					.getBusinessTypeCriteria(pagedRequests,filters);
			this.businessTypes = (List<MmBusinessType>) pagedRequests.getList();
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
			businessTypeManager.save(businessType);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "businessType.added" : "businessType.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (businessTypeId != null) {
        	businessType = businessTypeManager.get(businessTypeId);
        	this.setEntityIsNew(false);
        } else {
        	businessType = new MmBusinessType();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String businessTypeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					MmBusinessType businessType = businessTypeManager.get(new String(removeId));
					businessTypeManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("businessType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBusinessTypeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (businessType == null) {
			return "Invalid businessType Data";
		}

		return SUCCESS;

	}
}

