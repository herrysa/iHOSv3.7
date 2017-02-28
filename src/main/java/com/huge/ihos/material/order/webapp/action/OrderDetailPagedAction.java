package com.huge.ihos.material.order.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.material.order.model.OrderDetail;
import com.huge.ihos.material.order.service.ImportOrderLogManager;
import com.huge.ihos.material.order.service.OrderDetailManager;
import com.huge.ihos.material.service.StoreInvSetManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class OrderDetailPagedAction extends JqGridBaseAction implements Preparable {
	private static final long serialVersionUID = 6492328200608759452L;
	private OrderDetailManager orderDetailManager;
	private List<OrderDetail> orderDetails;
	private OrderDetail orderDetail;
	private String orderDetailId;
	private String storeId;
	private StoreInvSetManager storeInvSetManager;
	private ImportOrderLogManager importOrderLogManager;

	public void setImportOrderLogManager(ImportOrderLogManager importOrderLogManager) {
		this.importOrderLogManager = importOrderLogManager;
	}

	public void setStoreInvSetManager(StoreInvSetManager storeInvSetManager) {
		this.storeInvSetManager = storeInvSetManager;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public void setOrderDetailManager(OrderDetailManager orderDetailManager) {
		this.orderDetailManager = orderDetailManager;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String orderDetailGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = orderDetailManager
					.getOrderDetailCriteria(pagedRequests,filters);
			this.orderDetails = (List<OrderDetail>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
			if(storeId!=null){
				for(OrderDetail detail:orderDetails){
					InventoryDict invDict = detail.getInvDict();
					//通过入库记录表得出  条件是orderNo和invDictId
					Double inAmount = this.importOrderLogManager.getInAmountFromImportOrderLog(detail.getOrder().getOrderId(), invDict.getInvId());
					if(inAmount==null){
						inAmount = 0d;
					}
					detail.setLastAmount(detail.getAmount()-inAmount);
					if(detail.getLastAmount().equals(new Double(0))){
						detail.setState("0");
						continue;
					}
					if(invDict.getIsPublic()){
						continue;
					}
					if(!storeInvSetManager.isMatch(storeId, invDict.getInvId())){
						detail.setState("0");
					}
				}
			}
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
			orderDetailManager.save(orderDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "orderDetail.added" : "orderDetail.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (orderDetailId != null) {
        	orderDetail = orderDetailManager.get(orderDetailId);
        	this.setEntityIsNew(false);
        } else {
        	orderDetail = new OrderDetail();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String orderDetailGridEdit() {
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
				this.orderDetailManager.remove(ida);
				gridOperationMessage = this.getText("orderDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkOrderDetailGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (orderDetail == null) {
			return "Invalid orderDetail Data";
		}

		return SUCCESS;

	}
}

