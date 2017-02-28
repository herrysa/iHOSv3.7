package com.huge.ihos.system.repository.paymode.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.repository.paymode.model.PayCondition;
import com.huge.ihos.system.repository.paymode.service.PayConditionManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class PayConditionPagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = 8849032689523295690L;
	private PayConditionManager payConditionManager;
	private List<PayCondition> payConditions;
	private PayCondition payCondition;
	private String payConId;
	private String payConCode;
	private String payConName;
	private String endDate;//天
	private String disabled;

	public void setPayConCode(String payConCode) {
		this.payConCode = payConCode;
	}

	public void setPayConName(String payConName) {
		this.payConName = payConName;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public void setPayConditionManager(PayConditionManager payConditionManager) {
		this.payConditionManager = payConditionManager;
	}

	public List<PayCondition> getPayConditions() {
		return payConditions;
	}

	public void setPayConditions(List<PayCondition> payConditions) {
		this.payConditions = payConditions;
	}

	public PayCondition getPayCondition() {
		return payCondition;
	}

	public void setPayCondition(PayCondition payCondition) {
		this.payCondition = payCondition;
	}

	public String getPayConId() {
		return payConId;
	}

	public void setPayConId(String payConId) {
        this.payConId = payConId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String payConditionGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = payConditionManager
					.getPayConditionCriteria(pagedRequests,filters);
			this.payConditions = (List<PayCondition>) pagedRequests.getList();
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
			payConditionManager.save(payCondition);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "payCondition.added" : "payCondition.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (payConId != null) {
        	payCondition = payConditionManager.get(payConId);
        	this.setEntityIsNew(false);
        } else {
        	payCondition = new PayCondition();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String payConditionGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
//					PayCondition payCondition = payConditionManager.get(new String(removeId));
					payConditionManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("payCondition.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			if (oper.equals("edit")) {
				if(payCondition==null){
					if (payConId == null || "".equals(payConId)) {
						payCondition = new PayCondition();
					} else {
						payCondition = payConditionManager.get(payConId);
						payConCode = payCondition.getPayConCode();
					}
				}
				payCondition.setPayConCode(payConCode);
				payCondition.setPayConName(payConName);
				payCondition.setEndDate((endDate==null || endDate.trim().equals(""))?null:Integer.parseInt(endDate));
				payCondition.setDisabled("Yes".equalsIgnoreCase(disabled) ? true
						: false);
				payConditionManager.save(payCondition);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPayConditionGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (payCondition == null) {
			return "Invalid payCondition Data";
		}

		return SUCCESS;

	}
}

