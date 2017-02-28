package com.huge.ihos.material.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.dao.InvMainDao;
import com.huge.ihos.material.model.InvMain;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("invMainDao")
public class InvMainDaoHibernate extends GenericDaoHibernate<InvMain, String> implements InvMainDao {

	public InvMainDaoHibernate() {
		super(InvMain.class);
	}

	public JQueryPager getInvMainCriteria(JQueryPager paginatedList, List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("ioId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, InvMain.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getInvMainCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public boolean checkAllInitInvMainConfirm(String orgCode, String copyCode, String kjYear,String storeId) {
		String hql = "select count(ioId) from " + this.getPersistentClass().getSimpleName() + " where orgCode=? and copyCode=? and yearMonth like ? and store.id=? and ioType =1 and typeCode=39 and status<'2'";// TODO
		Object[] args = { orgCode, copyCode, kjYear+"%",storeId };
		List l = this.hibernateTemplate.find(hql, args);
		Integer c = ((Long) l.get(0)).intValue();
		if (c > 0)
			return false;
		else
			return true;
	}
	
	@Override
	public InvMain getInvMainByNo(String no, String orgCode, String copyCode) {
		String hql = " from " + this.getPersistentClass().getSimpleName() + " where ioBillNumber=? and orgCode=? and copyCode=?";// TODO
		Object[] args = { no,orgCode, copyCode};
		List<InvMain> l = this.hibernateTemplate.find(hql, args);
		if(l!=null && l.size()==1){
			return l.get(0);
		}
		return null;
	}

	@Override
	public boolean checkAllDocsInStore(String storeId,String orgCode,String copyCode,String kjYear) {
		String hql = "select count(ioId) from " + this.getPersistentClass().getSimpleName() + " where store.id =? and orgCode = ? and copyCode = ? and yearMonth like ? and typeCode!=39";// TODO
		Object[] args = {storeId ,orgCode,copyCode,kjYear+"%"};
		List l = this.hibernateTemplate.find(hql, args);
		Integer c = ((Long) l.get(0)).intValue();
		if (c > 0)
			return false;
		else
			return true;
	}

	@Override
	public void deleteInvDictAccount(String storeId, String orgCode,String copyCode,String kjYear) {
		Session session = this.getSessionFactory().getCurrentSession();
		String sql = "delete from mm_invDictDetail_account where storeId = '"+storeId+"'"
				+ " and orgCode = '"+orgCode+"' "
				+ " and copyCode = '"+copyCode+"'"
				+ " and yearMonth like '"+kjYear+"%'";
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
	}
	
	
	
	
	
}
