package com.huge.ihos.material.service;


import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.huge.exceptions.BillNumCreateException;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.model.InvMain;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InvMainManager extends GenericManager<InvMain, String> {
     public JQueryPager getInvMainCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public InvMain saveInvMain(InvMain main,InvDetail[] details) throws BillNumCreateException;
     //public void initConfirm(String[] invMainIds);
     public void initConfirm(String[] invMainIds,Date cfd,Person cfp);
 	public void initUnConfirm(String[] invMainIds);
 	public void bookStore(String orgCode,String copyCode,String kjYear,String[] storeId,Date startDate);
 	public void unBookStore(String[] storeIds,String orgCode,String copyCode,String kjYear);
 	public void invInAudit(String[] invMinIds,Date aud,Person aup);
 	public void invInUnAudit(String[] invMinIds);
 	
 	
 	
	public void confirmOut(String[] invMainIds, Date cfd, Person cfp );
	public void auditOut(String[] invMainIds, Date cfd, Person cfp);

	public void auditOutNot(String[] idArray);
	public void removeInvMain(InvMain invMain);
	public Iterator<InvDetail> beforeOutAudit(String[] invMinIds);

	public InvMain getInvMainByNo(String no,String orgCode,String copyCode);

	public InvMain saveInvOutFromDeptApp(InvMain invMain, InvDetail[] ids,String deptAppId,String deptAppDetailIds);
	/**
	 * 删除出库单
	 * @param outId
	 */
	public void removeInvMainOut(String outId);
	/**
	 * 根据订单入库的保存逻辑，需要记录入库明细
	 * @param invMain
	 * @param ids
	 * @param fromOrderData
	 * @return
	 */
	public InvMain saveInvMainFromOrder(InvMain invMain, InvDetail[] ids,String fromOrderData,Person sv);
	/**
	 * 删除入库单
	 * @param string
	 */
	public void removeInvMainIn(String inId);
}