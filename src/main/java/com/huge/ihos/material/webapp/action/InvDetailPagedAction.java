package com.huge.ihos.material.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.material.deptapp.service.DeptAppDetailManager;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.service.InvDetailManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class InvDetailPagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = 8494270506369331930L;
	private InvDetailManager invDetailManager;
	private List<InvDetail> invDetails;
	private InvDetail invDetail;
	private String invDetialId;
	private DeptAppDetailManager deptAppDetailManager;

	public void setDeptAppDetailManager(DeptAppDetailManager deptAppDetailManager) {
		this.deptAppDetailManager = deptAppDetailManager;
	}

	public void setInvDetailManager(InvDetailManager invDetailManager) {
		this.invDetailManager = invDetailManager;
	}

	public List<InvDetail> getInvDetails() {
		return invDetails;
	}

	public void setInvDetails(List<InvDetail> invDetails) {
		this.invDetails = invDetails;
	}

	public InvDetail getInvDetail() {
		return invDetail;
	}

	public void setInvDetail(InvDetail invDetail) {
		this.invDetail = invDetail;
	}

	public String getInvDetialId() {
		return invDetialId;
	}

	public void setInvDetialId(String invDetialId) {
        this.invDetialId = invDetialId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String invDetailGridList() {
		log.debug("enter list method!");
		try {
			if("deptAppOut".equals(docPreview)){
				String deptAppId = this.getRequest().getParameter("deptAppId");
				String detailIds = this.getRequest().getParameter("deptAppDetailIds");
				this.invDetails = deptAppDetailManager.getInvOutDetailByDisLog(deptAppId, detailIds);
			}else{
				List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
				JQueryPager pagedRequests = null;
				pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
				pagedRequests = invDetailManager.getInvDetailCriteria(pagedRequests,filters);
				this.invDetails = (List<InvDetail>) pagedRequests.getList();
				records = pagedRequests.getTotalNumberOfRows();
				total = pagedRequests.getTotalNumberOfPages();
				page = pagedRequests.getPageNumber();
			}
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
			invDetailManager.save(invDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "invDetail.added" : "invDetail.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (invDetialId != null) {
        	invDetail = invDetailManager.get(invDetialId);
        	this.setEntityIsNew(false);
        } else {
        	invDetail = new InvDetail();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String invDetailGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = (ids.nextToken()).trim();
					log.debug("Delete Customer " + removeId);
//					InvDetail invDetail = invDetailManager.get(new String(removeId));
					invDetailManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("invDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInvDetailGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (invDetail == null) {
			return "Invalid invDetail Data";
		}

		return SUCCESS;

	}
}

