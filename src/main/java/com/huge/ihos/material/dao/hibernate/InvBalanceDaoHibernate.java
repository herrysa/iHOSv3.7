package com.huge.ihos.material.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.material.dao.InvBalanceDao;
import com.huge.ihos.material.model.InvBalance;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.model.InvMain;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("invBalanceDao")
public class InvBalanceDaoHibernate extends GenericDaoHibernate<InvBalance, String> implements InvBalanceDao {

	public InvBalanceDaoHibernate() {
		super(InvBalance.class);
	}

	public JQueryPager getInvBalanceCriteria(JQueryPager paginatedList, List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, InvBalance.class, filters);
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

	public InvBalance getBalance(String orgCode, String copyCode, String yearMonth, String storeId, String ivnDicId) {
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where orgCode=? and copyCode=? and yearMonth=? and store.id=? and invDict.invId=?";
		Object[] args = { orgCode, copyCode, yearMonth, storeId, ivnDicId };
		List l = this.hibernateTemplate.find(hql, args);
		if (l.size() == 1)
			return (InvBalance) l.get(0);
		else if (l.size() > 1) {
			throw new BusinessException("同一期间内同一材料的Balance数据多于一条，请检查。" + "期间: " + yearMonth + " 材料ID: " + ivnDicId);
			// return null;
		} else
			return null;
	}

	private void updateInBalance(InvBalance ib, InvMain im, InvDetail idl) {
		ib.setInAmount(ib.getInAmount()+idl.getInAmount());
		ib.setInMoney(ib.getInMoney()+idl.getInMoney());
		ib.setYearInAmount(ib.getYearInAmount()+idl.getInAmount());
		ib.setYearInMoney(ib.getYearInMoney()+idl.getInMoney());
		this.save(ib);
	}

	private void updateOutBalance(InvBalance ib, InvMain im, InvDetail idl) {
		ib.setOutAmount(ib.getOutAmount()+idl.getInAmount());
		ib.setOutMoney(ib.getOutMoney()+idl.getInMoney());
		ib.setYearOutAmount(ib.getYearOutAmount()+idl.getInAmount());
		ib.setYearOutMoney(ib.getYearOutMoney()+idl.getInMoney());
		this.save(ib);
		
		
	}

	@Override
	public void updateBalance(InvMain im, InvDetail idl,String type) {
		InvBalance ib = this.getBalance(im.getOrgCode(), im.getCopyCode(), im.getYearMonth(), im.getStore().getId(), idl.getInvDict().getInvId());
		// TODO 需要再加判断条件，单据的业务类型为期初入库，否则余额记录不存在是不正常的情况。
		if (ib == null) {
			ib = new InvBalance();
			ib.setOrgCode(im.getOrgCode());
			ib.setCopyCode(im.getCopyCode());
			ib.setYearMonth(im.getYearMonth());
			ib.setStore(im.getStore());
			ib.setInvDict(idl.getInvDict());
		//	ib = this.save(ib);
		}
		// 1 入库 1 出库 2
		if (im.getIoType().equals("1")) {
			this.updateInBalance(ib, im, idl);
		} else if (im.getIoType().equals("2")) {
			//出库销审
			if(type!=null && type.equals("auditOutNot")){
				this.updateInBalance(ib, im, idl);
			}else{
				this.updateOutBalance(ib, im, idl);
			}
		}
	}

	@Override
	public void delete(String storeId, String orgCode, String copyCode,String kjYear) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "delete InvBalance where store.id = ? and orgCode = ? and copyCode= ? and yearMonth like ?";
		Query query = session.createQuery(hql);
		query.setString(0, storeId);
		query.setString(1, orgCode);
		query.setString(2, copyCode);
		query.setString(3, kjYear+"%");
		query.executeUpdate();
	}
}