package com.huge.ihos.material.order.webapp.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huge.exceptions.BillNumCreateException;
import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.ihos.material.documenttemplate.service.DocumentTemplateManager;
import com.huge.ihos.material.model.InvBillNumberSetting;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.order.model.Order;
import com.huge.ihos.material.order.model.OrderDetail;
import com.huge.ihos.material.order.service.OrderDetailManager;
import com.huge.ihos.material.order.service.OrderManager;
import com.huge.ihos.system.configuration.procType.model.ProcType;
import com.huge.ihos.system.configuration.procType.service.ProcTypeManager;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.repository.paymode.model.PayCondition;
import com.huge.ihos.system.repository.paymode.service.PayConditionManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class OrderPagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = -234476059881034394L;
	private OrderManager orderManager;
	private List<Order> orders;
	private Order order;
	private String orderId;
	private ProcTypeManager procTypeManager;
	private List<ProcType> procTypeList;
	private List<PayCondition> payConList;
	private PayConditionManager payConditionManager;
	private String orderDetailJson;
	private BillNumberManager billNumberManager;
	private String orderNo;
	private OrderDetailManager orderDetailManager;
	
	public void setOrderDetailManager(OrderDetailManager orderDetailManager) {
		this.orderDetailManager = orderDetailManager;
	}

	public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}

	public void setOrderDetailJson(String orderDetailJson) {
		this.orderDetailJson = orderDetailJson;
	}

	public List<PayCondition> getPayConList() {
		return payConList;
	}

	public void setPayConditionManager(PayConditionManager payConditionManager) {
		this.payConditionManager = payConditionManager;
	}

	public List<ProcType> getProcTypeList() {
		return procTypeList;
	}

	public void setProcTypeManager(ProcTypeManager procTypeManager) {
		this.procTypeManager = procTypeManager;
	}

	public void setOrderManager(OrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
		this.procTypeList = this.procTypeManager.getAllExceptDisable();
		this.payConList = this.payConditionManager.getAllExceptDisable();
	}
	@SuppressWarnings("unchecked")
	public String orderGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			//SystemVariable systemVariable = this.getCurrentSystemVariable();
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext
					.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext
					.getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = orderManager
					.getOrderCriteria(pagedRequests,filters);
			this.orders = (List<Order>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	private String importOrderCons;
	private Map<String,String> importOrderMap;

	public Map<String, String> getImportOrderMap() {
		return importOrderMap;
	}

	public void setImportOrderCons(String importOrderCons) {
		this.importOrderCons = importOrderCons;
	}
	
	@SuppressWarnings("unchecked")
	public String orderMainAndDetailList(){
		JSONObject jso = JSONObject.fromObject(importOrderCons);
		JSONArray jsa = jso.getJSONArray("elements");
		if(jsa.size()>0){
			Object[] objects = jsa.toArray();
			Map<String,Object> map = null;
			importOrderMap = new HashMap<String,String>();
			String key = null,value = null;
			for(Object obj:objects){
				map = (Map<String,Object>)obj;
				key = (String)map.get("key");
				value = (String)map.get("value");
				importOrderMap.put(key, value);
			}
		}
		this.setRandom(this.getRequest().getParameter("random"));
		return SUCCESS;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			OrderDetail[] orderDetails = gson.fromJson(this.orderDetailJson, OrderDetail[].class);
			//JSONUtils.getMorpherRegistry().registerMorpher( new DateMorpher(new String[] { "yyyy-MM-dd" }));
			//JSONArray jsa = JSONArray.fromObject(this.orderDetailJson);
			//OrderDetail[] orderDetails = (OrderDetail[]) JSONArray.toArray(jsa, OrderDetail.class);
			if(order.getVendor().getVendorId()!=null && order.getVendor().getVendorId().equals("")){
				order.setVendor(null);
			}
			order = orderManager.saveOrder(order,orderDetails);
		} catch (BillNumCreateException bnce){
			return ajaxForwardError(bnce.getMessage());
		}  catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "order.added" : "order.updated";
		String saveType = this.getRequest().getParameter("saveType");
		if (saveType != null && saveType.equalsIgnoreCase("saveStay")) {
			this.setCallbackType(saveType);
			this.setForwardUrl(order.getOrderId());
			return ajaxForward(true, this.getText(key), false);
		} else {
			return ajaxForward(true, this.getText(key), true);
		}
	}
	private DocumentTemplateManager documentTemplateManager;
	
	public void setDocumentTemplateManager(DocumentTemplateManager documentTemplateManager) {
		this.documentTemplateManager = documentTemplateManager;
	}
	private DocumentTemplate docTemp;
	
	public DocumentTemplate getDocTemp() {
		return docTemp;
	}
	private String selectInvDictByVendor;
	
	public String getSelectInvDictByVendor() {
		return selectInvDictByVendor;
	}
    public String edit() {
    	selectInvDictByVendor = this.getGlobalParamByKey("selectInvDictByVendor");
    	UserContext userContext = UserContextUtil.getUserContext();
    	String orgCode = userContext.getOrgCode();
    	String copyCode = userContext.getCopyCode();
    	String period = userContext.getPeriodMonth();
    	
    	String docTemId = this.getRequest().getParameter("docTemId");
        if (orderId != null) {
        	order = orderManager.get(orderId);
        	docTemId = order.getDocTemId();
        	if(docTemId!=null && !(docTemId.trim().equals(""))){//useDocTemp
				docTemp = documentTemplateManager.get(docTemId);
			}else{//not useDocTemp
				docTemp = documentTemplateManager.initDocumentTemplate("订单",orgCode,copyCode);
			}
        	this.setEntityIsNew(false);
        }else if(orderNo !=null){
        	PropertyFilter orderNoFilter = new PropertyFilter("EQS_orderNo", orderNo);
        	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_orgCode", userContext
					.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext
					.getCopyCode()));
        	filters.add(orderNoFilter);
        	List<Order> orders = orderManager.getByFilters(filters);
        	if(orders != null && orders.size()>0){
        		order= orders.get(0);
        		order.setState("2");
        		docTemId = order.getDocTemId();
            	if(docTemId!=null && !(docTemId.trim().equals(""))){//useDocTemp
    				docTemp = documentTemplateManager.get(docTemId);
    			}else{//not useDocTemp
    				docTemp = documentTemplateManager.initDocumentTemplate("订单",orgCode,copyCode);
    			}
            	this.setEntityIsNew(false);
        	}
        }else {
        	if(docTemId!=null){//preview
        		docTemp = documentTemplateManager.get(docTemId);
        	}else{//new
        		docTemp = documentTemplateManager.getDocumentTemplateInUse("订单",orgCode,copyCode);
        		docTemId = docTemp.getId();
        	}
        	order = new Order();
        	order.setOrgCode(orgCode);
        	order.setCopyCode(copyCode);
        	order.setPeriodMonth(period);
        	order.setState("0");
        	order.setMakePerson(this.getSessionUser().getPerson());
        	order.setMakeDate(userContext.getBusinessDate());
        	order.setProcType(procTypeManager.getProcTypeByCode("02"));
        	order.setDocTemId(docTemId);
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    public String extendOrder(){
    	try {
			StringTokenizer ids = new StringTokenizer(id,",");
			List<String> purchaseIds = new ArrayList<String>();
			while (ids.hasMoreTokens()) {
				String purchaseId = ids.nextToken();
				purchaseIds.add(purchaseId);
			}
			UserContext userContext = UserContextUtil.getUserContext();
			order = orderManager.extendOrder(purchaseIds,this.getSessionUser().getPerson());
			orderId = order.getOrderId();
			gridOperationMessage = this.getText("订单已生成");
			return ajaxForward(true, gridOperationMessage, false);
		}catch (BillNumCreateException bnce){
			return ajaxForwardError(bnce.getMessage());
		}catch (Exception e) {
			log.error("extendOrder Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
    }
    
    public String unionOrder(){
    	try {
			StringTokenizer ids = new StringTokenizer(id,",");
			List<String> unionIds = new ArrayList<String>();
			while (ids.hasMoreTokens()) {
				String removeId = ids.nextToken();
				unionIds.add(removeId);
			}
			order = orderManager.unionOrder(unionIds,this.getSessionUser().getPerson());
			orderId = order.getOrderId();
			gridOperationMessage = this.getText("订单合并成功,请编辑");
			return ajaxForward(true, gridOperationMessage, false);
		} catch (BillNumCreateException bnce){
			return ajaxForwardError(bnce.getMessage());
		} catch (Exception e) {
			log.error("unionOrder Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
    }
	public String orderGridEdit() {
		try {
			UserContext userContext = UserContextUtil.getUserContext();
	    	String orgCode = userContext.getOrgCode();
	    	String copyCode = userContext.getCopyCode();
	    	String period = userContext.getPeriodMonth();
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,",");
				boolean isLastNumber = true;
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					order = orderManager.get(removeId);
					//isLastNumber = invBillNumberManager.isLastNumber(order.getOrderNo(), InvBillNumberSetting.ORDER, orgCode, copyCode, period);
					isLastNumber = false;
					if(!isLastNumber){
						return ajaxForward(false, "只能删除最后一条新建记录!", false);
					}else{
						this.orderManager.removeOrIdInPurchasePlan(removeId);
						this.orderManager.remove(removeId);
					}
				}
				gridOperationMessage = this.getText("order.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("check")) {
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				orderManager.auditOrder(checkIds,this.getSessionUser().getPerson(),userContext.getBusinessDate());
				gridOperationMessage = this.getText("审核成功");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("cancelCheck")) {
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> cancelIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String cancelId = ids.nextToken();
					cancelIds.add(cancelId);
				}
				orderManager.antiAuditOrder(cancelIds,this.getSessionUser().getPerson());
				gridOperationMessage = this.getText("销审成功");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("abandon")){
				StringTokenizer ids = new StringTokenizer(id,",");
				orders = new ArrayList<Order>();
				while (ids.hasMoreTokens()) {
					String abandonId = ids.nextToken();
					order = this.orderManager.get(abandonId);
					order.setState("4");
					orders.add(order);
				}
				orderManager.saveAll(orders);
				gridOperationMessage = this.getText("作废订单成功");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("copy")){
				StringTokenizer ids = new StringTokenizer(id,",");
				orders = new ArrayList<Order>();
				while (ids.hasMoreTokens()) {				
					String copyId = ids.nextToken();
					order = this.orderManager.get(copyId);
					//String tempOrderNo = invBillNumberManager.createNextBillNumber(InvBillNumberSetting.ORDER, order.getOrgCode(), order.getCopyCode(), order.getPeriodMonth(),true);//生成单号
					String tempOrderNo = billNumberManager.createNextBillNumber("MM",InvBillNumberSetting.ORDER, true, order.getOrgCode(), order.getCopyCode(),order.getPeriodMonth());//生成单号
					docTemp = documentTemplateManager.getDocumentTemplateInUse("订单",orgCode,copyCode);
					Order orderTemp = new Order();
					orderTemp = order.clone();
					orderTemp.setState("0");
					orderTemp.setOrderId(null);
					orderTemp.setOrderNo(tempOrderNo);					
					orderTemp.setDocTemId(docTemp.getId());
					orderTemp.setCheckPerson(null);//审核人清空
					orderTemp.setCheckDate(null);//审核日期清空
					orderTemp.setMakePerson(this.getSessionUser().getPerson());
					orderTemp.setMakeDate(userContext.getBusinessDate());
					Set<OrderDetail> orderDetailSetOld = order.getOrderDetails();
					Set<OrderDetail> orderDetailSetNew = new HashSet<OrderDetail>();
					for(OrderDetail orderDetailTemp:orderDetailSetOld){
						OrderDetail orderDetailTempClone = new OrderDetail();
						orderDetailTempClone = orderDetailTemp.clone();
						orderDetailTempClone.setOrderDetailId(null);
						orderDetailTempClone.setOrder(orderTemp);
						orderDetailSetNew.add(orderDetailTempClone);
					}
					orderTemp.setOrderDetails(orderDetailSetNew);
					orders.add(orderTemp);
				}
				orderManager.saveAll(orders);
				gridOperationMessage = this.getText("复制成功");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkOrderGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	private Collection<InvDetail> invDetails;
	private String orderNos;
	
	public String getOrderNos() {
		return orderNos;
	}

	public Collection<InvDetail> getInvDetails() {
		return invDetails;
	}

	@SuppressWarnings("unchecked")
	public String importOrderToInvMainIn(){
		String importJson = this.getRequest().getParameter("importJson");
		JSONObject jso = JSONObject.fromObject(importJson);
		JSONArray jsa = jso.getJSONArray("elements");
		if(jsa.size()>0){
			Object[] objects = jsa.toArray();
			Map<String,Object> map = null;
			JSONArray details = null;
			Map<String,InvDetail> listMap = new HashMap<String,InvDetail>();
			for(Object obj:objects){
				map = (Map<String,Object>)obj;
				orderId = (String)map.get("key");
				order = this.orderManager.get(orderId);
				orderNos = order.getOrderNo()+",";
				details = (JSONArray)map.get("value");//数组
				listMap = parseImportJson(listMap,details);
			}
			invDetails = listMap.values();
			orderNos = OtherUtil.subStrEnd(orderNos, ",");
		}
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	private Map<String,InvDetail> parseImportJson(Map<String,InvDetail> listMap,JSONArray jsa){
		if(jsa.size()>0){
			Object[] objects = jsa.toArray();
			Map<String,Object> map = null;
			String orderDetailId = null;
			OrderDetail orderDetail = null;
			Double inAmount = 0d;
			InvDetail invDetail = null;
			for(Object obj:objects){
				map = (Map<String,Object>)obj;
				orderDetailId = (String)map.get("orderDetailId");
				orderDetail = this.orderDetailManager.get(orderDetailId);
				inAmount = Double.parseDouble(map.get("inAmount").toString());
				String invId = orderDetail.getInvDict().getInvId();
				if(listMap.get(invId)==null){
					invDetail = new InvDetail();
					invDetail.setInvDict(orderDetail.getInvDict());
					invDetail.setInAmount(inAmount);
					invDetail.setInPrice(orderDetail.getPrice());
					invDetail.setInMoney(invDetail.getInPrice()*inAmount);
					listMap.put(invId, invDetail);
				}else{
					invDetail = listMap.get(invId);
					invDetail.setInAmount(invDetail.getInAmount()+inAmount);
					invDetail.setInMoney(invDetail.getInPrice()*invDetail.getInAmount());
					listMap.put(invId, invDetail);
				}
			}
		}
		return listMap;
	}
	private String isValid() {
		if (order == null) {
			return "Invalid order Data";
		}

		return SUCCESS;

	}

}

