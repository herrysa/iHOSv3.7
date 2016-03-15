package com.huge.ihos.accounting.voucherFrom.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.accounting.voucherFrom.model.VoucherFrom;
import com.huge.ihos.accounting.voucherFrom.service.VoucherFromManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class VoucherFromPagedAction extends JqGridBaseAction implements Preparable {

	private VoucherFromManager voucherFromManager;
	private List<VoucherFrom> voucherFroms;
	private VoucherFrom voucherFrom;
	private String voucherFromCode;

	public VoucherFromManager getVoucherFromManager() {
		return voucherFromManager;
	}

	public void setVoucherFromManager(VoucherFromManager voucherFromManager) {
		this.voucherFromManager = voucherFromManager;
	}

	public List<VoucherFrom> getvoucherFroms() {
		return voucherFroms;
	}

	public void setVoucherFroms(List<VoucherFrom> voucherFroms) {
		this.voucherFroms = voucherFroms;
	}

	public VoucherFrom getVoucherFrom() {
		return voucherFrom;
	}

	public void setVoucherFrom(VoucherFrom voucherFrom) {
		this.voucherFrom = voucherFrom;
	}

	public String getVoucherFromCode() {
		return voucherFromCode;
	}

	public void setVoucherFromCode(String voucherFromCode) {
        this.voucherFromCode = voucherFromCode;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String voucherFromGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = voucherFromManager
					.getVoucherFromCriteria(pagedRequests,filters);
			this.voucherFroms = (List<VoucherFrom>) pagedRequests.getList();
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
			voucherFromManager.save(voucherFrom);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "voucherFrom.added" : "voucherFrom.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (voucherFromCode != null) {
        	voucherFrom = voucherFromManager.get(voucherFromCode);
        	this.setEntityIsNew(false);
        } else {
        	voucherFrom = new VoucherFrom();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String voucherFromGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					VoucherFrom voucherFrom = voucherFromManager.get(new String(removeId));
					voucherFromManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("voucherFrom.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkVoucherFromGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (voucherFrom == null) {
			return "Invalid voucherFrom Data";
		}

		return SUCCESS;

	}
}

