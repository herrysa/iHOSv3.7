package com.huge.ihos.material.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.material.dao.InvBatchDao;
import com.huge.ihos.material.model.InvBatch;
import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("invBatchDao")
public class InvBatchDaoHibernate extends GenericDaoHibernate<InvBatch, String> implements InvBatchDao {

	public InvBatchDaoHibernate() {
		super(InvBatch.class);
	}

	public JQueryPager getInvBatchCriteria(JQueryPager paginatedList, List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, InvBatch.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getInvBalanceCriteria", e);
			return paginatedList;
		}

	}

	public String getBatchUnitBarNo(String invDictId, String batchNo, String org, String copy,Store store) {
		InvBatch ib = this.getInvBatchByDictAndBatchNo(invDictId, batchNo, org, copy,store);

		if (ib == null) {
			ib = new InvBatch();
			ib.setBatchNo(batchNo);
			InventoryDict invd = new InventoryDict();
			invd.setInvId(invDictId);
			ib.setInvDict(invd);
			ib.setStore(store);
			ib = this.save(ib);
		}

		int ibSn = ib.getBatchSerial();
		ib.setBatchSerial(ibSn + 1);
		ib = this.save(ib);

		return ib.getInvDict().getInvCode() + ib.getBatchNo() + ibSn;
	}

	public InvBatch getInvBatchByDictAndBatchNo(String invDictId, String batchNo, String org, String copy,Store store) {
		String hql = "from InvBatch where invDict.invId=? and batchNo=? and orgCode=? and copyCode = ? and store_id=?";
		if(batchNo==null || "".equals(batchNo)){
			batchNo = "默认批号";
		}
		Object[] args = { invDictId, batchNo, org, copy ,store.getId()};
		List<InvBatch> l = this.hibernateTemplate.find(hql, args);
		if (l.size() == 0)
			return null;
		else if (l.size() == 1)
			return (InvBatch) l.get(0);
		else
			throw new BusinessException("同一材料同一批号同一仓库数据多于一条，请检查。" + "材料ID: " + invDictId + " 批号: " + batchNo);

	}

	public InvBatch addBatch(String invid, String org, String copy, String batchNo, Date vd,Store store) {
		InvBatch ib = null;// this.getInvBatchByDictAndBatchNo(invDictId,"默认批号",org,copy);
		ib = new InvBatch();
		
		InventoryDict invd = new InventoryDict();
		invd.setInvId(invid);
		ib.setInvDict(invd);
		if(batchNo==null || "".equals(batchNo)){
			batchNo = "默认批号";
		}
		ib.setBatchNo(batchNo);
		ib.setInvDict(invd);
		ib.setOrgCode(org);
		ib.setCopyCode(copy);
		ib.setStore(store);
		if(vd!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(vd);
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)+1);
			ib.setValidityDate(cal.getTime());
		}else{
			ib.setValidityDate(null);
		}
		ib.setProductionDate(new Date());
		ib = this.save(ib);
		return ib;
	}

	public InvBatch addDefaultBatch(String invDictId, String org, String copy, int vdc) {
		InvBatch ib = null;// this.getInvBatchByDictAndBatchNo(invDictId,"默认批号",org,copy);
		ib = new InvBatch();
		
		InventoryDict invd = new InventoryDict();
		invd.setInvId(invDictId);
		ib.setInvDict(invd);
		
		ib.setBatchNo("默认批号");
		ib.setInvDict(invd);
		ib.setOrgCode(org);
		ib.setCopyCode(copy);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, vdc);
		ib.setValidityDate(cal.getTime());
		
		ib = this.save(ib);
		return ib;
	}
}