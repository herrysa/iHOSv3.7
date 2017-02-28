package com.huge.ihos.material.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.material.model.InvBatch;
import com.huge.ihos.material.service.InvBatchManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.repository.store.service.StoreManager;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class InvBatchPagedAction extends JqGridBaseAction implements Preparable {

	private InvBatchManager invBatchManager;
	private List<InvBatch> invBatches;
	private InvBatch invBatch;
	private String id;
	
	private StoreManager storeManager;
	
	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}

	public InvBatchManager getInvBatchManager() {
		return invBatchManager;
	}

	public void setInvBatchManager(InvBatchManager invBatchManager) {
		this.invBatchManager = invBatchManager;
	}

	public List<InvBatch> getinvBatches() {
		return invBatches;
	}

	public void setInvBatchs(List<InvBatch> invBatches) {
		this.invBatches = invBatches;
	}

	public InvBatch getInvBatch() {
		return invBatch;
	}

	public void setInvBatch(InvBatch invBatch) {
		this.invBatch = invBatch;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String invBatchGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext
					.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext
					.getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = invBatchManager.getInvBatchCriteria(pagedRequests, filters);
			this.invBatches = (List<InvBatch>) pagedRequests.getList();
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
			invBatchManager.save(invBatch);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "invBatch.added" : "invBatch.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (id != null) {
			invBatch = invBatchManager.get(id);
			this.setEntityIsNew(false);
		} else {
			invBatch = new InvBatch();
			UserContext userContext = UserContextUtil.getUserContext();
			invBatch.setOrgCode(userContext.getOrgCode());
			invBatch.setCopyCode(userContext.getCopyCode());
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}
	
	public boolean doubleBatchNo;
	
	public boolean getDoubleBatchNo() {
		return doubleBatchNo;
	}

	public String checkBatchNo(){
		try {
			String invDictId = this.getRequest().getParameter("invDictId");
			String batchNo = this.getRequest().getParameter("batchNo");
			String storeId = this.getRequest().getParameter("storeId");
			UserContext userContext = UserContextUtil.getUserContext();
			doubleBatchNo = invBatchManager.isDoubleBatchNo(invDictId, batchNo, userContext.getOrgCode(), userContext.getCopyCode(),storeManager.get(storeId));
		} catch (Exception e) {
			log.error("checkBatchNo Error", e);
		}
		return SUCCESS;
	}

	public String invBatchGridEdit() {
		try {
			if (oper.equals("del")) {
				List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = (ids.nextToken());
					idl.add(removeId);
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.invBatchManager.remove(ida);
				
				gridOperationMessage = this.getText("invBatch.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInvBatchGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (invBatch == null) {
			return "Invalid invBatch Data";
		}

		return SUCCESS;

	}
	
	
	
}
