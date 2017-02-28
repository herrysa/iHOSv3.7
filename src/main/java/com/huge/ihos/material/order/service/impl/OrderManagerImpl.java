package com.huge.ihos.material.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.ihos.material.documenttemplate.service.DocumentTemplateManager;
import com.huge.ihos.material.model.InvBillNumberSetting;
import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.material.order.dao.OrderDao;
import com.huge.ihos.material.order.model.Order;
import com.huge.ihos.material.order.model.OrderDetail;
import com.huge.ihos.material.order.service.OrderManager;
import com.huge.ihos.material.purchaseplan.dao.PurchasePlanDao;
import com.huge.ihos.material.purchaseplan.model.PurchasePlan;
import com.huge.ihos.material.purchaseplan.model.PurchasePlanDetail;
import com.huge.ihos.material.purchaseplan.service.PurchasePlanManager;
import com.huge.ihos.system.configuration.procType.dao.ProcTypeDao;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.repository.vendor.model.Vendor;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("orderManager")
public class OrderManagerImpl extends GenericManagerImpl<Order, String> implements OrderManager {
    private OrderDao orderDao;
    private PurchasePlanDao purchasePlanDao;
    private ProcTypeDao procTypeDao;
    private BillNumberManager billNumberManager;
	private DocumentTemplateManager documentTemplateManager;
    private PurchasePlanManager purchasePlanManager;
	
    @Autowired
    public OrderManagerImpl(OrderDao orderDao) {
        super(orderDao);
        this.orderDao = orderDao;
    }
    
    @Autowired
    public void setPurchasePlanDao(PurchasePlanDao purchasePlanDao) {
		this.purchasePlanDao = purchasePlanDao;
	}
    
    @Autowired
	public void setProcTypeDao(ProcTypeDao procTypeDao) {
		this.procTypeDao = procTypeDao;
	}
    
    @Autowired
    public void setPurchasePlanManager(PurchasePlanManager purchasePlanManager){
    	this.purchasePlanManager = purchasePlanManager;
    }
    
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
    @Autowired
    public void setDocumentTemplateManager(DocumentTemplateManager documentTemplateManager) {
		this.documentTemplateManager = documentTemplateManager;
	}
	public JQueryPager getOrderCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return orderDao.getOrderCriteria(paginatedList,filters);
	}

	@Override
	public Order saveOrder(Order order, OrderDetail[] orderDetails) {
		if(OtherUtil.measureNull(order.getOrderId())){
			order.setOrderId(null);
		}
		if(order.getOrderNo()==null || order.getOrderNo().trim().length()==0){
			//String tempOrderNo = invBillNumberManager.createNextBillNumber(InvBillNumberSetting.ORDER, order.getOrgCode(), order.getCopyCode(), order.getPeriodMonth(),true);
			String tempOrderNo = billNumberManager.createNextBillNumber("MM",InvBillNumberSetting.ORDER, true, order.getOrgCode(), order.getCopyCode(),order.getPeriodMonth());

			order.setOrderNo(tempOrderNo);
		}
		Set<OrderDetail> ppds = null;
		if(orderDetails.length>0){
			ppds = new HashSet<OrderDetail>();
		}
		for(int i=0;i<orderDetails.length;i++){
			orderDetails[i].setOrder(order);
			ppds.add(orderDetails[i]);
		}
		if(order.getOrderDetails()!=null){
			order.getOrderDetails().clear();
		}
		order.setOrderDetails(ppds);
		order = this.orderDao.save(order);
		return order;
	}

	
	@Override
	public void remove(String removeId) {
		super.remove(removeId);
		UserContext userContext = UserContextUtil.getUserContext();
		//invBillNumberManager.updateSerialNumber(InvBillNumberSetting.ORDER, userContext.getOrgCode(), userContext.getCopyCode(), userContext.getPeriodMonth());
	}

	@Override
	public void stopOrder(List<String> stopIds) {
		Order order = null;
		for(String stopId:stopIds){
			order = this.orderDao.get(stopId);
			order.setState("3");
			this.orderDao.save(order);
		}
	}

	@Override
	public void antiAuditOrder(List<String> cancelIds, Person person) {
		Order order = null;
		for(String cancelId:cancelIds){
			order = this.orderDao.get(cancelId);
			order.setState("0");
			order.setCheckDate(null);
			order.setCheckPerson(null);
			this.orderDao.save(order);
		}
	}

	@Override
	public void auditOrder(List<String> checkIds, Person person,Date date) {
		Order order = null;
		for(String checkId:checkIds){
			order = this.orderDao.get(checkId);
			order.setState("1");
			order.setCheckDate(date);
			order.setCheckPerson(person);
			this.orderDao.save(order);
		}
	}

	@Override
	public Order extendOrder(List<String> purchaseIds,Person person) {
		Order orderreturn = null;
		PurchasePlan purchasePlan = null;
		Set<PurchasePlanDetail> purchasePlanDetails = null;
		InventoryDict invDict = null;
		Map<String,OrderDetail> map = new HashMap<String,OrderDetail>();
		OrderDetail orderDetail = null;
		Set<OrderDetail> orderDetails = new HashSet<OrderDetail>();
		Map<String,Integer> mapkey = new HashMap<String, Integer>();
		List<List<PurchasePlanDetail>> listgroup = new ArrayList<List<PurchasePlanDetail>>();
		int keyvalue = 0;
		for(String purchaseId:purchaseIds){
			purchasePlan = purchasePlanDao.get(purchaseId);
			purchasePlan.setState("2");
			purchasePlanDao.save(purchasePlan);
			purchasePlanDetails = purchasePlan.getPurchasePlanDetails();
			for(PurchasePlanDetail purchasePlanDetail:purchasePlanDetails){
				String purchaserIdtemp = purchasePlanDetail.getPurchaser().getPersonId();
				String vendorIdtemp  = purchasePlanDetail.getVendor().getVendorId();
				String keytemp = purchaserIdtemp + vendorIdtemp;
				if(mapkey.containsKey(keytemp)){
					listgroup.get(mapkey.get(keytemp)).add(purchasePlanDetail);
				}else{
					mapkey.put(keytemp, keyvalue);
					listgroup.add(new ArrayList<PurchasePlanDetail>());
					listgroup.get(keyvalue).add(purchasePlanDetail);
					keyvalue ++;					
				}
			}			
			}
		for(List<PurchasePlanDetail> listtemp:listgroup){
			Order order = initOrder(person);
			order = orderDao.save(order);
			Person buyperson = null;
			Department deptemp = null;
			Vendor vendor = null;
			for(PurchasePlanDetail purchasePlanDetail:listtemp){
				invDict = purchasePlanDetail.getInvDict();
				buyperson = purchasePlanDetail.getPurchaser();
				deptemp= purchasePlanDetail.getPurchasePlan().getDept();
				vendor = purchasePlanDetail.getVendor();
				if(map.get(invDict.getInvId())!=null){
					orderDetail = map.get(invDict.getInvId());
					orderDetail.setAmount(orderDetail.getAmount()+purchasePlanDetail.getAmount());
				}else{
					orderDetail = new OrderDetail();
					orderDetail.setOrder(order);
					orderDetail.setInvDict(invDict);
					orderDetail.setAmount(purchasePlanDetail.getAmount());
					orderDetail.setPrice(purchasePlanDetail.getPrice());
					orderDetail.setArrivalDate(purchasePlanDetail.getArrivalDate());
					map.put(invDict.getInvId(), orderDetail);
				}
				orderDetails.add(orderDetail);
				PurchasePlan purchasePlanorder =  purchasePlanDetail.getPurchasePlan();
				String purchaseOrderNo = purchasePlanorder.getOrderNo();
				if(purchaseOrderNo == null){
					purchasePlanorder.setOrderNo(order.getOrderNo());
					purchasePlanDao.save(purchasePlanorder);
				}else{
				StringTokenizer purchaseOrderNos = new StringTokenizer(purchaseOrderNo,",");
				List<String> purchaseOrderNoList = new ArrayList<String>();
				while (purchaseOrderNos.hasMoreTokens()) {
					String purchaseOrderNotemp = purchaseOrderNos.nextToken();
					purchaseOrderNoList.add(purchaseOrderNotemp);
				}
				if(!purchaseOrderNoList.contains(order.getOrderNo())){
					purchaseOrderNo = purchaseOrderNo +","+order.getOrderNo();
				}
				purchasePlanorder.setOrderNo(purchaseOrderNo);
				purchasePlanDao.save(purchasePlanorder);
				}
			}
			order.setBuyPerson(buyperson);
			order.setDept(deptemp);
			order.setOrderDetails(orderDetails);
			order.setVendor(vendor);
			orderreturn = orderDao.save(order);
		}
		return orderreturn;
//		Order order = initOrder(sv,person);
//		order = orderDao.save(order);
//		
//		PurchasePlan purchasePlan = null;
//		Set<PurchasePlanDetail> purchasePlanDetails = null;
//		InventoryDict invDict = null;
//		Map<String,OrderDetail> map = new HashMap<String,OrderDetail>();
//		OrderDetail orderDetail = null;
//		Set<OrderDetail> orderDetails = new HashSet<OrderDetail>();
//		for(String purchaseId:purchaseIds){
//			purchasePlan = purchasePlanDao.get(purchaseId);
//			purchasePlanDetails = purchasePlan.getPurchasePlanDetails();
//			for(PurchasePlanDetail purchasePlanDetail:purchasePlanDetails){
//				invDict = purchasePlanDetail.getInvDict();
//				if(map.get(invDict.getInvId())!=null){
//					orderDetail = map.get(invDict.getInvId());
//					orderDetail.setAmount(orderDetail.getAmount()+purchasePlanDetail.getAmount());
//				}else{
//					orderDetail = new OrderDetail();
//					orderDetail.setOrder(order);
//					orderDetail.setInvDict(invDict);
//					orderDetail.setAmount(purchasePlanDetail.getAmount());
//					orderDetail.setPrice(purchasePlanDetail.getPrice());
//					map.put(invDict.getInvId(), orderDetail);
//				}
//				orderDetails.add(orderDetail);
//			}
//		}
//		order.setOrderDetails(orderDetails);
//		order = orderDao.save(order);
//		return order;
	}
	
	private Order initOrder(Person person){
		Order order = new Order();
		UserContext userContext = UserContextUtil.getUserContext();
		order.setOrgCode(userContext.getOrgCode());
    	order.setCopyCode(userContext.getCopyCode());
    	order.setPeriodMonth(userContext.getPeriodMonth());
		//String tempOrderNo = invBillNumberManager.createNextBillNumber(InvBillNumberSetting.ORDER, order.getOrgCode(), order.getCopyCode(), order.getPeriodMonth(),true);
		String tempOrderNo = billNumberManager.createNextBillNumber("MM",InvBillNumberSetting.ORDER, true, order.getOrgCode(), order.getCopyCode(),order.getPeriodMonth());;
		order.setOrderNo(tempOrderNo);
    	order.setState("0");
    	order.setMakePerson(person);
    	order.setMakeDate(userContext.getBusinessDate());
    	order.setProcType(procTypeDao.getProcTypeByCode("02"));
    	DocumentTemplate docTemp = documentTemplateManager.getDocumentTemplateInUse("订单",userContext.getOrgCode(),userContext.getCopyCode());
    	order.setDocTemId(docTemp.getId());
		return order;
	}

	@Override
	public Order unionOrder(List<String> unionIds,Person person) {
		Order unionOrder = initOrder(person);
		unionOrder = orderDao.save(unionOrder);
		Order order = null;
		Set<OrderDetail> orderDetails = null;
		Set<OrderDetail> unionOrderDetails = new HashSet<OrderDetail>();
		OrderDetail unionOrderDetail = null;
		InventoryDict invDict = null;
		Map<String,OrderDetail> map = new HashMap<String,OrderDetail>();
		for(String unionId:unionIds){
			order = orderDao.get(unionId);
			orderDetails = order.getOrderDetails();
			for(OrderDetail orderDetail:orderDetails){
				invDict = orderDetail.getInvDict();
				if(map.get(invDict.getInvId())!=null){
					unionOrderDetail = map.get(invDict.getInvId());
					unionOrderDetail.setAmount(unionOrderDetail.getAmount()+orderDetail.getAmount());
				}else {
					unionOrderDetail = new OrderDetail();
					unionOrder.setBuyPerson(orderDetail.getOrder().getBuyPerson());
					unionOrder.setVendor(orderDetail.getOrder().getVendor());
					unionOrder.setDept(orderDetail.getOrder().getDept());
					unionOrderDetail.setOrder(unionOrder);
					unionOrderDetail.setInvDict(invDict);
					unionOrderDetail.setAmount(orderDetail.getAmount());
					unionOrderDetail.setPrice(orderDetail.getPrice());
					unionOrderDetail.setArrivalDate(orderDetail.getArrivalDate());
					map.put(invDict.getInvId(), unionOrderDetail);
				}
				unionOrderDetails.add(unionOrderDetail);
			}
			order.setState("4");
			order.setRemark(order.getRemark()==null?"":order.getRemark()+"(作废原因：被合并)");
			orderDao.save(order);
		}
		unionOrder.setOrderDetails(unionOrderDetails);
		unionOrder = orderDao.save(unionOrder);
//		orderDao.remove(unionIds.toArray(new String[]{}));
		return unionOrder;
	}
	
	 public void removeOrIdInPurchasePlan(String removeId){
		 Order order = this.get(removeId);
		 String orderNo = order.getOrderNo();
		 List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		 UserContext userContext = UserContextUtil.getUserContext();
		 filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
		 filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
		 PropertyFilter orderNoFilter = new PropertyFilter("LIKES_orderNo", "*"+orderNo+"*");
		 filters.add(orderNoFilter);
		 List<PurchasePlan> purchasePlans = purchasePlanManager.getByFilters(filters);
		 if(purchasePlans!=null){
			 for(PurchasePlan purchasePlan:purchasePlans){
					StringTokenizer orderNos = new StringTokenizer(purchasePlan.getOrderNo(),",");
					String orderNoNew="";
					while (orderNos.hasMoreTokens()) {
						String orderNoTemp = orderNos.nextToken();
						if(orderNoTemp.equals(orderNo)){
						}else{
							orderNoNew = orderNoNew + orderNoTemp +",";
						}						
					}
					if(orderNoNew.equals("")){
						purchasePlan.setState("1");
					}
					purchasePlan.setOrderNo(orderNoNew);
					purchasePlanDao.save(purchasePlan);
			 }
		 }
	 }

}