package com.huge.ihos.accounting.voucher.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.accounting.voucher.model.VoucherDetailAssist;
import com.huge.ihos.accounting.voucher.service.VoucherDetailAssistManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class VoucherDetailAssistPagedAction extends JqGridBaseAction implements Preparable {

	private VoucherDetailAssistManager voucherDetailAssistManager;
	private List<VoucherDetailAssist> voucherDetailAssists;
	private VoucherDetailAssist voucherDetailAssist;
	private String voucherDetailAssistId;

	public VoucherDetailAssistManager getVoucherDetailAssistManager() {
		return voucherDetailAssistManager;
	}

	public void setVoucherDetailAssistManager(VoucherDetailAssistManager voucherDetailAssistManager) {
		this.voucherDetailAssistManager = voucherDetailAssistManager;
	}

	public List<VoucherDetailAssist> getvoucherDetailAssists() {
		return voucherDetailAssists;
	}

	public void setVoucherDetailAssists(List<VoucherDetailAssist> voucherDetailAssists) {
		this.voucherDetailAssists = voucherDetailAssists;
	}

	public VoucherDetailAssist getVoucherDetailAssist() {
		return voucherDetailAssist;
	}

	public void setVoucherDetailAssist(VoucherDetailAssist voucherDetailAssist) {
		this.voucherDetailAssist = voucherDetailAssist;
	}

	public String getVoucherDetailAssistId() {
		return voucherDetailAssistId;
	}

	public void setVoucherDetailAssistId(String voucherDetailAssistId) {
        this.voucherDetailAssistId = voucherDetailAssistId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String voucherDetailAssistGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = voucherDetailAssistManager
					.getVoucherDetailAssistCriteria(pagedRequests,filters);
			this.voucherDetailAssists = (List<VoucherDetailAssist>) pagedRequests.getList();
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
			voucherDetailAssistManager.save(voucherDetailAssist);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "voucherDetailAssist.added" : "voucherDetailAssist.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (voucherDetailAssistId != null) {
        	voucherDetailAssist = voucherDetailAssistManager.get(voucherDetailAssistId);
        	this.setEntityIsNew(false);
        } else {
        	voucherDetailAssist = new VoucherDetailAssist();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String voucherDetailAssistGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					VoucherDetailAssist voucherDetailAssist = voucherDetailAssistManager.get(new String(removeId));
					voucherDetailAssistManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("voucherDetailAssist.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkVoucherDetailAssistGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (voucherDetailAssist == null) {
			return "Invalid voucherDetailAssist Data";
		}

		return SUCCESS;

	}
}

