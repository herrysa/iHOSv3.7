package com.huge.ihos.material.purchaseplan.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.material.deptplan.model.DeptMRSummaryDetail;
import com.huge.ihos.material.deptplan.service.DeptMRSummaryDetailManager;
import com.huge.ihos.material.purchaseplan.model.PurchasePlanDetail;
import com.huge.ihos.material.purchaseplan.service.PurchasePlanDetailManager;
import com.huge.ihos.material.service.InvBalanceBatchManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class PurchasePlanDetailPagedAction extends JqGridBaseAction implements Preparable {
	private static final long serialVersionUID = 1146188524709140656L;
	private PurchasePlanDetailManager purchasePlanDetailManager;
	private DeptMRSummaryDetailManager deptMRSummaryDetailManager;
	private List<PurchasePlanDetail> purchasePlanDetails;
	private PurchasePlanDetail purchasePlanDetail;
	private String purchaseDetailId;
	private String mrId;
	private InvBalanceBatchManager invBalanceBatchManager;
	
	public String getMrId(){
		return mrId;
	}

	public void setMrId(String mrId){
		this.mrId=mrId;
	}
	
	public void setPurchasePlanDetailManager(PurchasePlanDetailManager purchasePlanDetailManager) {
		this.purchasePlanDetailManager = purchasePlanDetailManager;
	}
	public void setDeptMRSummaryDetailManager(DeptMRSummaryDetailManager deptMRSummaryDetailManager) {
		this.deptMRSummaryDetailManager = deptMRSummaryDetailManager;
	}

	public List<PurchasePlanDetail> getPurchasePlanDetails() {
		return purchasePlanDetails;
	}

	public void setPurchasePlanDetails(List<PurchasePlanDetail> purchasePlanDetails) {
		this.purchasePlanDetails = purchasePlanDetails;
	}

	public PurchasePlanDetail getPurchasePlanDetail() {
		return purchasePlanDetail;
	}

	public void setPurchasePlanDetail(PurchasePlanDetail purchasePlanDetail) {
		this.purchasePlanDetail = purchasePlanDetail;
	}

	public String getPurchaseDetailId() {
		return purchaseDetailId;
	}

	public void setPurchaseDetailId(String purchaseDetailId) {
        this.purchaseDetailId = purchaseDetailId;
    }
	
	public InvBalanceBatchManager getInvBalanceBatchManager() {
		return invBalanceBatchManager;
	}

	public void setInvBalanceBatchManager(InvBalanceBatchManager invBalanceBatchManager) {
		this.invBalanceBatchManager = invBalanceBatchManager;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String purchasePlanDetailGridList() {
		log.debug("enter list method!");
		try {
			if(mrId!=null){
				String storeid="";
				StringTokenizer mrIds = new StringTokenizer(mrId,",");
				Map<String, Double> map = new LinkedHashMap<String, Double>();
				Map<String, DeptMRSummaryDetail> mapDeptMRSummaryDetail = new LinkedHashMap<String, DeptMRSummaryDetail>();
				List<PurchasePlanDetail> purchasePlanDetailstemp = new ArrayList<PurchasePlanDetail>();
				while (mrIds.hasMoreTokens()) {
					String checkId = mrIds.nextToken();
					PropertyFilter mrIdfilter = new PropertyFilter("EQS_deptMRSummary.mrId",checkId);
					List<PropertyFilter> deptMRSummaryDetailfilters = new ArrayList<PropertyFilter>();
					deptMRSummaryDetailfilters.add(mrIdfilter);
				    List <DeptMRSummaryDetail> deptMRSummaryDetails = new ArrayList<DeptMRSummaryDetail>();		
				    deptMRSummaryDetails = deptMRSummaryDetailManager.getByFilters(deptMRSummaryDetailfilters);
				    if(deptMRSummaryDetails != null){
					for(DeptMRSummaryDetail deptMRSummaryDetailtemp:deptMRSummaryDetails){
						String invid = deptMRSummaryDetailtemp.getInvDict().getInvId();
						storeid = deptMRSummaryDetailtemp.getDeptMRSummary().getStore().getId();
						Double amount = deptMRSummaryDetailtemp.getAmount();
						if(map.containsKey(invid)){
							map.put(invid, (map.get(invid)+amount));
						}else{
							map.put(invid, amount);
							mapDeptMRSummaryDetail.put(invid, deptMRSummaryDetailtemp);
						}
					}					
				    }				  
				}
				  if(!map.isEmpty()){
						for (String key : map.keySet()) {
							PurchasePlanDetail purchasePlanDetail = new PurchasePlanDetail();
							purchasePlanDetail.setAmount(map.get(key));
							purchasePlanDetail.setNeedAmount(map.get(key));
							purchasePlanDetail.setInvDict(mapDeptMRSummaryDetail.get(key).getInvDict());
							purchasePlanDetail.setPrice(mapDeptMRSummaryDetail.get(key).getPrice());
							purchasePlanDetail.setVendor(mapDeptMRSummaryDetail.get(key).getInvDict().getVendor());
							purchasePlanDetail.setArrivalDate(mapDeptMRSummaryDetail.get(key).getMakeDate());
							purchasePlanDetail.setPurchaser(this.getSessionUser().getPerson());
							UserContext userContext = UserContextUtil.getUserContext();
							purchasePlanDetail.setCurStock(invBalanceBatchManager.getAllStoreCurAmount(userContext.getOrgCode(), userContext.getCopyCode(), userContext.getPeriodMonth(), mapDeptMRSummaryDetail.get(key).getInvDict().getInvId()));
							purchasePlanDetail.setStoreStock(invBalanceBatchManager.getStoreCurAmount(userContext.getOrgCode(), userContext.getCopyCode(), userContext.getPeriodMonth(), storeid, mapDeptMRSummaryDetail.get(key).getInvDict().getInvId()));
							purchasePlanDetailstemp.add(purchasePlanDetail);
						}
					}
				this.purchasePlanDetails = purchasePlanDetailstemp;
				records = purchasePlanDetailstemp.size();
				total = records;
				page = 1;
			}
			else{
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = purchasePlanDetailManager
					.getPurchasePlanDetailCriteria(pagedRequests,filters);
			this.purchasePlanDetails = (List<PurchasePlanDetail>) pagedRequests.getList();
			for(PurchasePlanDetail ppd:purchasePlanDetails){
				ppd.setCurStock(invBalanceBatchManager.getAllStoreCurAmount(ppd.getPurchasePlan().getOrgCode(), ppd.getPurchasePlan().getCopyCode(), ppd.getPurchasePlan().getPeriodMonth(), ppd.getInvDict().getInvId()));
				ppd.setStoreStock(invBalanceBatchManager.getStoreCurAmount(ppd.getPurchasePlan().getOrgCode(), ppd.getPurchasePlan().getCopyCode(), ppd.getPurchasePlan().getPeriodMonth(), ppd.getPurchasePlan().getStore().getId(), ppd.getInvDict().getInvId()));
				if(ppd.getNeedAmount()!=null){
				}else{
				ppd.setNeedAmount(0d);
				}
			}
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
			}

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	private Map<String,Double> amounts;
	
	public Map<String, Double> getAmounts() {
		return amounts;
	}

	public String getStoreAndNeedAmount(){
		String invId = this.getRequest().getParameter("invId");
		String storeId = this.getRequest().getParameter("store_id");
		log.info("invId:"+invId+";storeId:"+storeId);
		amounts = new HashMap<String,Double>();
		amounts.put("curStock", invBalanceBatchManager.getAllStoreCurAmount(this.getRequest().getParameter("orgCode"), this.getRequest().getParameter("copyCode"), this.getRequest().getParameter("periodMonth"), invId));
		amounts.put("storeStock", invBalanceBatchManager.getStoreCurAmount(this.getRequest().getParameter("orgCode"), this.getRequest().getParameter("copyCode"), this.getRequest().getParameter("periodMonth"), storeId, invId));
		amounts.put("needAmount", 0d);
		return SUCCESS;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			purchasePlanDetailManager.save(purchasePlanDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "purchasePlanDetail.added" : "purchasePlanDetail.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (purchaseDetailId != null) {
        	purchasePlanDetail = purchasePlanDetailManager.get(purchaseDetailId);
        	this.setEntityIsNew(false);
        } else {
        	purchasePlanDetail = new PurchasePlanDetail();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String purchasePlanDetailGridEdit() {
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
				this.purchasePlanDetailManager.remove(ida);
				gridOperationMessage = this.getText("purchasePlanDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPurchasePlanDetailGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (purchasePlanDetail == null) {
			return "Invalid purchasePlanDetail Data";
		}

		return SUCCESS;

	}
}

