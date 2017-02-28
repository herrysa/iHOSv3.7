package com.huge.ihos.material.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.material.model.InvInStore;
import com.huge.ihos.material.service.InvInStoreManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;


public class InvInStorePagedAction extends JqGridBaseAction implements Preparable {
	private static final long serialVersionUID = -4544420437072206091L;
	private InvInStoreManager invInStoreManager;
	private List<InvInStore> invInStores;
	private InvInStore invInStore;
	private String id;
	private String storeId;
	private String fromType;
	private String vendorId;
	
	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public void setInvInStoreManager(InvInStoreManager invInStoreManager) {
		this.invInStoreManager = invInStoreManager;
	}

	public List<InvInStore> getInvInStores() {
		return invInStores;
	}

	public void setInvInStores(List<InvInStore> invInStores) {
		this.invInStores = invInStores;
	}

	public InvInStore getInvInStore() {
		return invInStore;
	}

	public void setInvInStore(InvInStore invInStore) {
		this.invInStore = invInStore;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
		this.setRandom(this.getRequest().getParameter("random"));
	}
	@SuppressWarnings("unchecked")
	public String invInStoreGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if(storeId==null || "".equals(storeId.trim())){
				
			}else{
				filters.add(new PropertyFilter("INS_storeId",storeId+",1"));
			}
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = invInStoreManager
					.getInvInStoreCriteria(pagedRequests,filters);
			this.invInStores = (List<InvInStore>) pagedRequests.getList();
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
			invInStoreManager.save(invInStore);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "invInStore.added" : "invInStore.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	invInStore = invInStoreManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	invInStore = new InvInStore();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String invInStoreGridEdit() {
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
				this.invInStoreManager.remove(ida);
				gridOperationMessage = this.getText("invInStore.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInvInStoreGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (invInStore == null) {
			return "Invalid invInStore Data";
		}

		return SUCCESS;

	}
}

