package com.huge.ihos.material.order.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.model.InvMain;
import com.huge.ihos.material.order.dao.ImportOrderLogDao;
import com.huge.ihos.material.order.dao.OrderDetailDao;
import com.huge.ihos.material.order.model.ImportOrderLog;
import com.huge.ihos.material.order.model.ImportOrderLogDetail;
import com.huge.ihos.material.order.model.Order;
import com.huge.ihos.material.order.model.OrderDetail;
import com.huge.ihos.material.order.service.ImportOrderLogDetailManager;
import com.huge.ihos.material.order.service.ImportOrderLogManager;
import com.huge.ihos.material.order.service.OrderManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("importOrderLogManager")
public class ImportOrderLogManagerImpl extends GenericManagerImpl<ImportOrderLog, String> implements ImportOrderLogManager {
    private ImportOrderLogDao importOrderLogDao;
    private OrderDetailDao orderDetailDao;
    private ImportOrderLogDetailManager importOrderLogDetailManager;
    private OrderManager orderManager;
    
    @Autowired
	public void setOrderManager(OrderManager orderManager) {
		this.orderManager = orderManager;
	}
	@Autowired
    public ImportOrderLogManagerImpl(ImportOrderLogDao importOrderLogDao) {
        super(importOrderLogDao);
        this.importOrderLogDao = importOrderLogDao;
    }
    @Autowired
	public void setOrderDetailDao(OrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}
    @Autowired
    public void setImportOrderLogDetailManager(ImportOrderLogDetailManager importOrderLogDetailManager) {
		this.importOrderLogDetailManager = importOrderLogDetailManager;
	}
	public JQueryPager getImportOrderLogCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return importOrderLogDao.getImportOrderLogCriteria(paginatedList,filters);
	}
	
	@Override
	public Double getInAmountFromImportOrderLog(String orderId, String invId) {
		Order order = this.orderManager.get(orderId);
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_orgCode",order.getOrgCode()));
		filters.add(new PropertyFilter("EQS_copyCode",order.getCopyCode()));
		filters.add(new PropertyFilter("EQS_yearMonth",order.getPeriodMonth()));
		filters.add(new PropertyFilter("EQS_orderNo",order.getOrderNo()));
		filters.add(new PropertyFilter("EQS_invDict.invId",invId));
		List<ImportOrderLog> importOrderLogs = this.getByFilters(filters);
		if(importOrderLogs!=null && importOrderLogs.size()==1){
			Double inAmount = 0d;
			ImportOrderLog importOrderLog = importOrderLogs.get(0);
			Set<ImportOrderLogDetail> logDetails = importOrderLog.getImportOrderLogDetails();
			for(ImportOrderLogDetail logDetail:logDetails){
				inAmount += logDetail.getImportAmount();
			}
			return inAmount;
		}else{
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void setImportLog(InvMain invMain, String fromOrderData,String type,Person person) {
		String invMainNo = invMain.getIoBillNumber();
		/**
		 * 删除入库单时 根据入库单号
		 * 1.删除入库logDetail
		 * 2.检测是否还有该订单的logDetail，如果没有 则删除logDetail对应的主log
		 * 3.根据订单入库记录修改订单状态
		 */
		if("delete".equals(type)){
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_invMainNo",invMainNo));
			List<ImportOrderLogDetail> logDetails = importOrderLogDetailManager.getByFilters(filters);
			Set<String> logIds = new HashSet<String>();
			//1
			for(ImportOrderLogDetail logDetail:logDetails){
				logIds.add(logDetail.getImportOrderLog().getLogId());
				importOrderLogDetailManager.remove(logDetail.getLogDetailId());
			}
			//2
			Iterator<String> ite = logIds.iterator();
			while(ite.hasNext()){
				String logId = ite.next();
				filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_importOrderLog.logId",logId));
				List<ImportOrderLogDetail> details = this.importOrderLogDetailManager.getByFilters(filters);
				if(details==null || details.size()==0){
					this.remove(logId);
				}
			}
			//3
			filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("INS_orderNo",invMain.getOrderNo()));
			List<Order> orders = this.orderManager.getByFilters(filters);
			for(Order order:orders){
				order.setState(isComplete(order.getOrderId()));
				this.orderManager.save(order);
			}
			
		}else if("confirm".equals(type)){
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_invMainNo",fromOrderData));
			List<ImportOrderLogDetail> logDetails = importOrderLogDetailManager.getByFilters(filters);
			for(ImportOrderLogDetail logDetail:logDetails){
				logDetail.setInvMainNo(invMainNo);
				logDetail = importOrderLogDetailManager.save(logDetail);
			}
			
		}else{
			Date curDate = new Date();
			JSONObject jso = JSONObject.fromObject(fromOrderData);
			JSONArray jsa = jso.getJSONArray("elements");
			if(jsa.size()>0){
				Object[] objects = jsa.toArray();
				Map<String,Object> map = null;
				JSONArray details = null;
				String orderId = null;
				Order order = null;
				for(Object object:objects){//order
					map = (Map<String,Object>)object;
					orderId = (String)map.get("key");
					order = this.orderManager.get(orderId);
					ImportOrderLog orderLog = null;
					details = (JSONArray)map.get("value");//数组
					if(details.size()>0){
						Object[] objs = details.toArray();
						String orderDetailId = null;
						OrderDetail orderDetail = null;
						Double inAmount = 0d;
						for(Object obj:objs){//orderDetail
							map = (Map<String,Object>)obj;
							orderDetailId = (String)map.get("orderDetailId");
							orderDetail = this.orderDetailDao.get(orderDetailId);
							inAmount = Double.parseDouble(map.get("inAmount").toString());
							orderLog = this.getImportOrderLog(order.getOrderNo(), orderDetail.getInvDict().getInvId());
							Set<ImportOrderLogDetail> logDetails = null;
							if(orderLog==null){
								orderLog = new ImportOrderLog();
								orderLog.setOrgCode(order.getOrgCode());
								orderLog.setCopyCode(order.getCopyCode());
								orderLog.setYearMonth(order.getPeriodMonth());
								orderLog.setOrderNo(order.getOrderNo());
								orderLog.setInvDict(orderDetail.getInvDict());
								logDetails = new HashSet<ImportOrderLogDetail>();
							}else{
								logDetails = orderLog.getImportOrderLogDetails();
							}
							
							ImportOrderLogDetail logDetail = new ImportOrderLogDetail();
							logDetail.setImportOrderLog(orderLog);
							logDetail.setImportAmount(inAmount);
							logDetail.setImportTime(curDate);
							logDetail.setImportPerson(person);
							logDetail.setInvMainNo(invMainNo);
							logDetails.add(logDetail);
							orderLog.setImportOrderLogDetails(logDetails);
							orderLog = this.save(orderLog);
						}
					}
					order.setState(isComplete(orderId));//已完成
					this.orderManager.save(order);
				}
			}
		}
	}
	/**
	 * 检测订单是否已完成
	 * 获取订单所包含的材料的集合
	 * 逐条材料检查入库记录是否已全部入库
	 * @param orderId
	 * @return "3":已完成;"2":部分完成;"1":一点未入库
	 */
	private String isComplete(String orderId){
		Order order = this.orderManager.get(orderId);
		Set<OrderDetail> orderDetails = order.getOrderDetails();
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		for(OrderDetail detail:orderDetails){
			String invId = detail.getInvDict().getInvId();
			Double importAmount = this.getInAmountFromImportOrderLog(orderId, invId);
			if(importAmount==null){
				map.put(invId, null);
			}else{
				if(detail.getAmount().equals(importAmount)){
					map.put(invId, true);
				}else{
					map.put(invId, false);
				}
			}
		}
		Collection<Boolean> completes = map.values();
		if(!completes.contains(true) && !completes.contains(false)){
			return "1";
		}else if(!completes.contains(true)){
			return "2";
		}else{
			return "3";
		}
	}
	private ImportOrderLog getImportOrderLog(String orderNo, String invId) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_orderNo",orderNo));
		filters.add(new PropertyFilter("EQS_invDict.invId",invId));
		List<ImportOrderLog> logs = this.getByFilters(filters);
		if(logs!=null && logs.size()==1){
			return logs.get(0);
		}
		return null;
	}
}