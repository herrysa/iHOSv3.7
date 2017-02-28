package com.huge.ihos.system.repository.paymode.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.repository.paymode.model.PayMode;
import com.huge.ihos.system.repository.paymode.service.PayModeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class PayModePagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = -6328484000016510657L;
	private PayModeManager payModeManager;
	private List<PayMode> payModes;
	private PayMode payMode;
	private String payModeId;
	private String payModeCode;
	private String payModeName;
	private String isCheck;
	private String disabled;

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public void setPayModeCode(String payModeCode) {
		this.payModeCode = payModeCode;
	}

	public void setPayModeName(String payModeName) {
		this.payModeName = payModeName;
	}

	public void setPayModeManager(PayModeManager payModeManager) {
		this.payModeManager = payModeManager;
	}

	public List<PayMode> getPayModes() {
		return payModes;
	}

	public void setPayModes(List<PayMode> payModes) {
		this.payModes = payModes;
	}

	public PayMode getPayMode() {
		return payMode;
	}

	public void setPayMode(PayMode payMode) {
		this.payMode = payMode;
	}

	public String getPayModeId() {
		return payModeId;
	}

	public void setPayModeId(String payModeId) {
		this.payModeId = payModeId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	@SuppressWarnings("unchecked")
	public String payModeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = payModeManager.getPayModeCriteria(pagedRequests,
					filters);
			this.payModes = (List<PayMode>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			payModeManager.save(payMode);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "payMode.added"
				: "payMode.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (payModeId != null) {
			payMode = payModeManager.get(payModeId);
			this.setEntityIsNew(false);
		} else {
			payMode = new PayMode();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String payModeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
//					PayMode payMode = payModeManager.get(new String(removeId));
					payModeManager.remove(new String(removeId));

				}
				gridOperationMessage = this.getText("payMode.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			if (oper.equals("edit")) {
				if (payMode == null) {
					if (payModeId == null || "".equals(payModeId)) {
						payMode = new PayMode();
					} else {
						payMode = payModeManager.get(payModeId);
						payModeCode = payMode.getPayModeCode();
					}
				}
				payMode.setPayModeCode(payModeCode);
				payMode.setPayModeName(payModeName);
				payMode.setIsCheck("Yes".equalsIgnoreCase(isCheck) ? true
						: false);
				payMode.setDisabled("Yes".equalsIgnoreCase(disabled) ? true
						: false);
				payModeManager.save(payMode);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPayModeGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (payMode == null) {
			return "Invalid payMode Data";
		}

		return SUCCESS;

	}
}
