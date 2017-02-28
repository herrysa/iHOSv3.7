package com.huge.ihos.material.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.material.model.InvBalanceBatch;
import com.huge.ihos.material.model.InvCheck;
import com.huge.ihos.material.model.InvCheckDetail;
import com.huge.ihos.material.service.InvBalanceBatchManager;
import com.huge.ihos.material.service.InvCheckDetailManager;
import com.huge.ihos.material.service.InvCheckManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.repository.store.service.StoreManager;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class InvCheckDetailPagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = 7910959729168905488L;
	private InvCheckDetailManager invCheckDetailManager;
	private List<InvCheckDetail> invCheckDetails;
	private InvCheckDetail invCheckDetail;
	private String id;

	private InvCheck invCheck;
	private String checkNo;
	private InvCheckManager invCheckManager;

	private Store store;
	private StoreManager storeManager;

	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}

	public Store getStore() {
		return store;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public void setInvCheckManager(InvCheckManager invCheckManager) {
		this.invCheckManager = invCheckManager;
	}

	public InvCheck getInvCheck() {
		return invCheck;
	}

	public void setInvCheckDetailManager(
			InvCheckDetailManager invCheckDetailManager) {
		this.invCheckDetailManager = invCheckDetailManager;
	}

	public List<InvCheckDetail> getInvCheckDetails() {
		return invCheckDetails;
	}

	public void setInvCheckDetails(List<InvCheckDetail> invCheckDetails) {
		this.invCheckDetails = invCheckDetails;
	}

	public InvCheckDetail getInvCheckDetail() {
		return invCheckDetail;
	}

	public void setInvCheckDetail(InvCheckDetail invCheckDetail) {
		this.invCheckDetail = invCheckDetail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
		UserContext userContext = UserContextUtil.getUserContext();
		invCheck = invCheckManager.getInvCheckByCheckNo(checkNo, userContext.getOrgCode(), userContext.getCopyCode());
	}

	@SuppressWarnings("unchecked")
	public String invCheckDetailGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = invCheckDetailManager.getInvCheckDetailCriteria(
					pagedRequests, filters);
			this.invCheckDetails = (List<InvCheckDetail>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	private InvBalanceBatchManager invBalanceBatchManager;
	private List<InvBalanceBatch> invCheckImportDicts;

	public List<InvBalanceBatch> getInvCheckImportDicts() {
		return invCheckImportDicts;
	}

	public void setInvBalanceBatchManager(
			InvBalanceBatchManager invBalanceBatchManager) {
		this.invBalanceBatchManager = invBalanceBatchManager;
	}

	@SuppressWarnings("unchecked")
	public String invCheckImportDictGridList() {
		log.debug("enter invCheckImportDictGridList method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			UserContext userContext = UserContextUtil.getUserContext();
			if(filters.size()<2){
				filters.add(new PropertyFilter("GTN_curAmount", "0"));
			}
			String storeId = (String)filters.get(0).getMatchValue();
			StringTokenizer ids = new StringTokenizer(ivbhIds, ",");
			String tempId = "",batchId = "",invId = "",ibbIds = "";
			int semicolon = -1;
			InvBalanceBatch invBalanceBatch = null;
			while (ids.hasMoreTokens()) {
				tempId = ids.nextToken();
				semicolon = tempId.indexOf('^');
				batchId = tempId.substring(0, semicolon);
				invId = tempId.substring(semicolon+1);
				invBalanceBatch = invBalanceBatchManager.getInvBalanceBatchsByStoreAndBatchAndInv(userContext.getOrgCode(),
						userContext.getCopyCode(),userContext.getPeriodMonth(),storeId,invId,batchId);
				ibbIds += invBalanceBatch.getId()+",";
			}
			if((!"".equals(ibbIds)) && ibbIds.length()>1){
				ibbIds = OtherUtil.subStrEnd(ibbIds, ",");
				filters.add(new PropertyFilter("NIS_id",ibbIds));
			}
			filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
			filters.add(new PropertyFilter("EQS_yearMonth", userContext.getPeriodMonth()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = invBalanceBatchManager.getInvBalanceBatchCriteria(pagedRequests, filters);
			this.invCheckImportDicts = (List<InvBalanceBatch>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
			this.setRandom(this.getRequest().getParameter("random"));

		} catch (Exception e) {
			log.error("invBalanceBatchGridList Error", e);
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
			invCheckDetailManager.save(invCheckDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "invCheckDetail.added"
				: "invCheckDetail.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (id != null) {
			invCheckDetail = invCheckDetailManager.get(id);
			this.setEntityIsNew(false);
		} else {
			invCheckDetail = new InvCheckDetail();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String invCheckDetailGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = (ids.nextToken());
					log.debug("Delete Customer " + removeId);
					invCheckDetailManager.remove(new String(removeId));
				}
				gridOperationMessage = this.getText("invCheckDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInvCheckDetailGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	private String ivbhIds;
	
	public String getIvbhIds() {
		return ivbhIds;
	}
	
	public void setIvbhIds(String ivbhIds) {
		this.ivbhIds = ivbhIds;
	}

	public String invCheckImportDict(){
		store = storeManager.get(this.getRequest().getParameter("storeId"));
		ivbhIds = this.getRequest().getParameter("ivbhIds");
		return SUCCESS;
	}
	private String isValid() {
		if (invCheckDetail == null) {
			return "Invalid invCheckDetail Data";
		}

		return SUCCESS;

	}
}
