package com.huge.ihos.bm.loanBill.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.bm.loanBill.model.BmLoanbillDetail;
import com.huge.ihos.bm.loanBill.service.BmLoanbillDetailManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BmLoanbillDetailPagedAction extends JqGridBaseAction implements Preparable {

	private BmLoanbillDetailManager bmLoanbillDetailManager;
	private List<BmLoanbillDetail> bmLoanbillDetails;
	private BmLoanbillDetail bmLoanbillDetail;
	private String billdetailId;

	public BmLoanbillDetailManager getBmLoanbillDetailManager() {
		return bmLoanbillDetailManager;
	}

	public void setBmLoanbillDetailManager(BmLoanbillDetailManager bmLoanbillDetailManager) {
		this.bmLoanbillDetailManager = bmLoanbillDetailManager;
	}

	public List<BmLoanbillDetail> getbmLoanbillDetails() {
		return bmLoanbillDetails;
	}

	public void setBmLoanbillDetails(List<BmLoanbillDetail> bmLoanbillDetails) {
		this.bmLoanbillDetails = bmLoanbillDetails;
	}

	public BmLoanbillDetail getBmLoanbillDetail() {
		return bmLoanbillDetail;
	}

	public void setBmLoanbillDetail(BmLoanbillDetail bmLoanbillDetail) {
		this.bmLoanbillDetail = bmLoanbillDetail;
	}

	public String getBilldetailId() {
		return billdetailId;
	}

	public void setBilldetailId(String billdetailId) {
        this.billdetailId = billdetailId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String bmLoanbillDetailGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = bmLoanbillDetailManager
					.getBmLoanbillDetailCriteria(pagedRequests,filters);
			this.bmLoanbillDetails = (List<BmLoanbillDetail>) pagedRequests.getList();
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
			bmLoanbillDetailManager.save(bmLoanbillDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "bmLoanbillDetail.added" : "bmLoanbillDetail.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (billdetailId != null) {
        	bmLoanbillDetail = bmLoanbillDetailManager.get(billdetailId);
        	this.setEntityIsNew(false);
        } else {
        	bmLoanbillDetail = new BmLoanbillDetail();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String bmLoanbillDetailGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BmLoanbillDetail bmLoanbillDetail = bmLoanbillDetailManager.get(new String(removeId));
					bmLoanbillDetailManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("bmLoanbillDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBmLoanbillDetailGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (bmLoanbillDetail == null) {
			return "Invalid bmLoanbillDetail Data";
		}

		return SUCCESS;

	}
}

