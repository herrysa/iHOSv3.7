package com.huge.ihos.material.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.material.dao.InvBalanceBatchDao;
import com.huge.ihos.material.model.InvBalanceBatch;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.model.InvMain;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("invBalanceBatchDao")
public class InvBalanceBatchDaoHibernate extends GenericDaoHibernate<InvBalanceBatch, String> implements InvBalanceBatchDao {

	public InvBalanceBatchDaoHibernate() {
		super(InvBalanceBatch.class);
	}

	public JQueryPager getInvBalanceBatchCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {
		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(
					paginatedList, InvBalanceBatch.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getInvBalanceBatchCriteria", e);
			return paginatedList;
		}

	}

	private void updateInBatchBalance(InvBalanceBatch ib, InvMain im, InvDetail idl) {
		ib.setBatchInAmount(ib.getBatchInAmount() + idl.getInAmount());
		ib.setBatchInMoney(ib.getBatchInMoney() + idl.getInMoney());
		ib.setBatchYearInAmount(ib.getBatchYearInAmount() + idl.getInAmount());
		ib.setBatchYearInMoney(ib.getBatchYearInMoney() + idl.getInMoney());
		ib.setCurAmount(ib.getCurAmount()+idl.getInAmount());
		ib.setCurMoney(ib.getCurMoney()+idl.getInMoney());
		this.save(ib);
	}

	private void updateOutBatchBalance(InvBalanceBatch ib, InvMain im, InvDetail idl) {
		ib.setBatchOutAmount(ib.getBatchOutAmount() + idl.getInAmount());
		ib.setBatchOutMoney(ib.getBatchOutMoney() + idl.getInMoney());
		ib.setBatchYearOutAmount(ib.getBatchYearOutAmount() + idl.getInAmount());
		ib.setBatchYearOutMoney(ib.getBatchYearOutMoney() + idl.getInMoney());
		ib.setCurAmount(ib.getCurAmount()-idl.getInAmount());
		ib.setCurMoney(ib.getCurMoney()-idl.getInMoney());
		this.save(ib);
	}

	@Override
	public InvBalanceBatch getBatchBalance(String orgCode, String copyCode,
			String yearMonth, String storeId, String ivnDicId, String batchNo) {
		String hql = "from "
				+ this.getPersistentClass().getSimpleName()
				+ " where orgCode=? and copyCode=? and yearMonth =? and store.id=? and invDict.invId=? and invBatch.id=?";
		Object[] args = { orgCode, copyCode, yearMonth, storeId, ivnDicId,
				batchNo };
		List l = this.hibernateTemplate.find(hql, args);
		if (l.size() == 1)
			return (InvBalanceBatch) l.get(0);
		else if (l.size() > 1) {
			throw new BusinessException("同一期间内同一材料的Balance数据多于一条，请检查。" + "期间: "
					+ yearMonth + " 材料ID: " + ivnDicId + " 批号:" + batchNo);
			// return null;
		} else
			return null;
	}

	@Override
	public void updateBatchBalance(InvMain im, InvDetail idl,String type) {
		InvBalanceBatch ib = this.getBatchBalance(im.getOrgCode(), im.getCopyCode(),
				im.getYearMonth(), im.getStore().getId(), idl.getInvDict()
						.getInvId(), idl.getInvBatch().getId());
		// TODO 需要再加判断条件，单据的业务类型为期初入库，否则余额记录不存在是不正常的情况。
		if (ib == null) {
			ib = new InvBalanceBatch();
			ib.setOrgCode(im.getOrgCode());
			ib.setCopyCode(im.getCopyCode());
			ib.setYearMonth(im.getYearMonth());
			ib.setStore(im.getStore());
			ib.setInvDict(idl.getInvDict());
			ib.setInvBatch(idl.getInvBatch());
			ib.setPrice(idl.getInPrice());
			// TODO 关于条形码还需要从头到尾的梳理下，这里先临时处理。
			ib.setBarCode("");
			ib.setBatchSn("");
			// ib = this.save(ib);
		}
		// 1 入库 1 出库 2
		if (im.getIoType().equals("1")) {
			this.updateInBatchBalance(ib, im, idl);
		} else if (im.getIoType().equals("2")) {
			if(type!=null && type.equals("auditOutNot")){
				this.updateInBatchBalance(ib, im, idl);
			}else{
				this.updateOutBatchBalance(ib, im, idl);
			}
		}

	}

	@Override
	public void delete(String storeId, String orgCode, String copyCode,String kjYear) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "delete InvBalanceBatch where store.id = ? and orgCode = ? and copyCode= ? and yearMonth like ?";
		Query query = session.createQuery(hql);
		query.setString(0, storeId);
		query.setString(1, orgCode);
		query.setString(2, copyCode);
		query.setString(3, kjYear+"%");
		query.executeUpdate();
	}

	@Override
	public Double getSumCurAmount(String orgCode, String copyCode,String yearMonth, String storeId, String invDictId) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "select sum(curAmount) from InvBalanceBatch where orgCode = ? and copyCode = ? and yearMonth = ? and invDict.invId = ?";
		if(storeId!=null){
			hql += " and store.id = ?";
		}
		Query query = session.createQuery(hql);
		query.setString(0, orgCode);
		query.setString(1, copyCode);
		query.setString(2, yearMonth);
		query.setString(3, invDictId);
		if(storeId!=null){
			query.setString(4, storeId);
		}
		Object object =  query.uniqueResult();
		return Double.parseDouble(object==null?"0":object.toString());
	}
}
