package com.huge.ihos.system.systemManager.searchButtonPriv.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivDetail;
import com.huge.ihos.system.systemManager.searchButtonPriv.service.ButtonPrivDetailManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ButtonPrivDetailPagedAction extends JqGridBaseAction implements Preparable {

	private ButtonPrivDetailManager buttonPrivDetailManager;
	private List<ButtonPrivDetail> buttonPrivDetails;
	private ButtonPrivDetail buttonPrivDetail;
	private String bpDetailId;

	public void setButtonPrivDetailManager(ButtonPrivDetailManager buttonPrivDetailManager) {
		this.buttonPrivDetailManager = buttonPrivDetailManager;
	}

	public List<ButtonPrivDetail> getButtonPrivDetails() {
		return buttonPrivDetails;
	}

	public void setButtonPrivDetails(List<ButtonPrivDetail> buttonPrivDetails) {
		this.buttonPrivDetails = buttonPrivDetails;
	}

	public ButtonPrivDetail getSearchButtoPrivDetail() {
		return buttonPrivDetail;
	}

	public void setButtonPrivDetail(ButtonPrivDetail buttonPrivDetail) {
		this.buttonPrivDetail = buttonPrivDetail;
	}

	public String getBpDetailId() {
		return bpDetailId;
	}

	public void setBpDetailId(String bpDetailId) {
        this.bpDetailId = bpDetailId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String buttonPrivDetailGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = buttonPrivDetailManager
					.getButtonPrivDetailCriteria(pagedRequests,filters);
			this.buttonPrivDetails = (List<ButtonPrivDetail>) pagedRequests.getList();
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
			buttonPrivDetailManager.save(buttonPrivDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "searchButtoPrivDetail.added" : "searchButtoPrivDetail.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (bpDetailId != null) {
        	buttonPrivDetail = buttonPrivDetailManager.get(bpDetailId);
        	this.setEntityIsNew(false);
        } else {
        	buttonPrivDetail = new ButtonPrivDetail();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String buttonPrivDetailGridEdit() {
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
				this.buttonPrivDetailManager.remove(ida);
				gridOperationMessage = this.getText("searchButtoPrivDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("buttonPrivDetailGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (buttonPrivDetail == null) {
			return "Invalid buttonPrivDetail Data";
		}

		return SUCCESS;

	}
}

