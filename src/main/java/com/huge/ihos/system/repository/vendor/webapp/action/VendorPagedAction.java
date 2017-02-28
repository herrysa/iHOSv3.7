package com.huge.ihos.system.repository.vendor.webapp.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.repository.paymode.model.PayCondition;
import com.huge.ihos.system.repository.paymode.model.PayMode;
import com.huge.ihos.system.repository.paymode.service.PayConditionManager;
import com.huge.ihos.system.repository.paymode.service.PayModeManager;
import com.huge.ihos.system.repository.vendor.model.Vendor;
import com.huge.ihos.system.repository.vendor.model.VendorType;
import com.huge.ihos.system.repository.vendor.service.VendorManager;
import com.huge.ihos.system.repository.vendor.service.VendorTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.PropertyFilter.MatchType;
import com.opensymphony.xwork2.Preparable;

public class VendorPagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = 294952367363033566L;
	private VendorManager vendorManager;
	private List<Vendor> vendors;
	private Vendor vendor;
	private String vendorId;
	
	private VendorTypeManager vendorTypeManager;
	private PayModeManager payModeManager;
	private PayConditionManager payConditionManager;
	/*private InventoryDictManager inventoryDictManager;

	public void setInventoryDictManager(InventoryDictManager inventoryDictManager) {
		this.inventoryDictManager = inventoryDictManager;
	}*/

	public void setPayModeManager(PayModeManager payModeManager) {
		this.payModeManager = payModeManager;
	}

	public void setPayConditionManager(PayConditionManager payConditionManager) {
		this.payConditionManager = payConditionManager;
	}

	public void setVendorTypeManager(VendorTypeManager vendorTypeManager) {
		this.vendorTypeManager = vendorTypeManager;
	}

	public void setVendorManager(VendorManager vendorManager) {
		this.vendorManager = vendorManager;
	}

	public List<Vendor> getVendors() {
		return vendors;
	}

	public void setVendors(List<Vendor> vendors) {
		this.vendors = vendors;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
	
	private List<PayMode> payModeList;
	private List<PayCondition> payConList;
	
	public List<PayMode> getPayModeList() {
		return payModeList;
	}

	public List<PayCondition> getPayConList() {
		return payConList;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
		this.payModeList = this.payModeManager.getAllExceptDisable();
		this.payConList = this.payConditionManager.getAllExceptDisable();
	}
	@SuppressWarnings("unchecked")
	public String vendorGridList() {
		log.debug("enter list method!");
		try {
			String orgCode = UserContextUtil.getOrgCodeOrLoginOrgCode();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if (filters.size() == 1) {
				PropertyFilter pf = filters.get(0);
				if (pf.getPropertyName().equals("vendorType.id") && pf.getMatchType().equals(MatchType.IN)) {

					List<VendorType> list = this.vendorTypeManager.getAllDescendant(orgCode,((String[]) pf.getMatchValue())[0]);

					List<String> sl = new ArrayList<String>();

					for (Iterator<VendorType> iterator = list.iterator(); iterator.hasNext();) {
						VendorType o = (VendorType) iterator.next();
						sl.add(o.getId());
					}
					sl.add(((String[]) pf.getMatchValue())[0]);
					Object[] objs = sl.toArray();
					pf.setMatchValue(objs);
				}
			}
			filters.add(new PropertyFilter("EQS_orgCode", orgCode));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = vendorManager
					.getVendorCriteria(pagedRequests,filters);
			this.vendors = (List<Vendor>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	/*public QueryManager queryManager;
	
	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}*/

	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			vendor = vendorManager.save(vendor);
			//String userId = UserContextUtil.getLoginUserId();
			//Object[] proArgs = {vendor.getVendorId(),userId};
            //ReturnUtil returnUtil = this.queryManager.publicPrecess( "sp_tb_wldw", proArgs );
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "vendor.added" : "vendor.updated";
		return ajaxForward(this.getText(key));
	}
	private String vendorTypeId;
	
    public void setVendorTypeId(String vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}

	public String edit() {
        if (vendorId != null) {
        	vendor = vendorManager.get(vendorId);
        	this.setEntityIsNew(false);
        } else {
        	vendor = new Vendor();
        	if(vendorTypeId!=null && !vendorTypeId.trim().equals("")){
        		VendorType vt = vendorTypeManager.get(vendorTypeId);
        		vendor.setVendorType(vt);
        	}
        	UserContext userContext = UserContextUtil.getUserContext();
        	vendor.setOrgCode(UserContextUtil.getOrgCodeOrLoginOrgCode());
        	vendor.setVenDevDate( userContext.getBusinessDate());
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String vendorGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					Vendor vendor = vendorManager.get(new String(removeId));
					//如果供应商被引用，不能被删除
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					PropertyFilter vendorFilter = new PropertyFilter("EQS_vendor.vendorId",vendor.getVendorId());
					PropertyFilter disabledFilter = new PropertyFilter("EQB_disabled","false");
					filters.add(disabledFilter);
					filters.add(vendorFilter);
					/*Boolean vendorInUse = inventoryDictManager.existByFilter(filters);
					
					if(vendorInUse){
						return ajaxForwardError("供应商被引用，不能删除！");
					}*/
					vendorManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("vendor.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkVendorGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	private boolean exists;
	
	public boolean getExists(){
		return exists;
	}
	
	public String checkVendorCode(){
		try {
			UserContext userContext = UserContextUtil.getUserContext();
			String vendorCode = this.getRequest().getParameter("vendorCode");
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			PropertyFilter vendorCodeFilter = new PropertyFilter("EQS_vendorCode",vendorCode);
			PropertyFilter orgFilter = new PropertyFilter("EQS_orgCode",userContext.getOrgCode());
			filters.add(vendorCodeFilter);
			filters.add(orgFilter);
			exists = vendorManager.existByFilter(filters);
			//exists = vendorManager.existCode("sy_vendor", "vendorCode", "orgCode", vendorCode, this.getCurrentSystemVariable().getOrgCode());
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkVendorCode Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (vendor == null) {
			return "Invalid vendor Data";
		}
		if(vendor.getPayMode()!=null && vendor.getPayMode().getPayModeId()!=null && vendor.getPayMode().getPayModeId().equalsIgnoreCase("")){
			vendor.setPayMode(null);
		}
		if(vendor.getPayCon()!=null && vendor.getPayCon().getPayConId()!=null && vendor.getPayCon().getPayConId().equalsIgnoreCase("")){
			vendor.setPayCon(null);
		}
		return SUCCESS;
	}
}

