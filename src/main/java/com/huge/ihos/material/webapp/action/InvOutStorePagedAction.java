package com.huge.ihos.material.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.material.model.InvOutStore;
import com.huge.ihos.material.service.InvOutStoreManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class InvOutStorePagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = -2471578814793896327L;
	private InvOutStoreManager invOutStoreManager;
	private List<InvOutStore> invOutStores;
	private InvOutStore invOutStore;
	private String id;

	public void setInvOutStoreManager(InvOutStoreManager invOutStoreManager) {
		this.invOutStoreManager = invOutStoreManager;
	}

	public List<InvOutStore> getInvOutStores() {
		return invOutStores;
	}

	public void setInvOutStores(List<InvOutStore> invOutStores) {
		this.invOutStores = invOutStores;
	}

	public InvOutStore getInvOutStore() {
		return invOutStore;
	}

	public void setInvOutStore(InvOutStore invOutStore) {
		this.invOutStore = invOutStore;
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
	@SuppressWarnings("unchecked")
	public String invOutStoreGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
			filters.add(new PropertyFilter("EQS_yearMonth", userContext.getPeriodMonth()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = invOutStoreManager
					.getInvOutStoreCriteria(pagedRequests,filters);
			this.invOutStores = (List<InvOutStore>) pagedRequests.getList();
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
			invOutStoreManager.save(invOutStore);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "invOutStore.added" : "invOutStore.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	invOutStore = invOutStoreManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	invOutStore = new InvOutStore();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String invOutStoreGridEdit() {
		try {
			if (oper.equals("del")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.invOutStoreManager.remove(ida);
				gridOperationMessage = this.getText("invOutStore.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInvOutStoreGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (invOutStore == null) {
			return "Invalid invOutStore Data";
		}

		return SUCCESS;

	}
}

