package com.huge.ihos.accounting.balance.dao.hibernate;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.accounting.balance.dao.BalanceAssistDao;
import com.huge.ihos.accounting.balance.model.BalanceAssist;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("balanceAssistDao")
public class BalanceAssistDaoHibernate extends GenericDaoHibernate<BalanceAssist, String> implements BalanceAssistDao {

    public BalanceAssistDaoHibernate() {
        super(BalanceAssist.class);
    }
    
    public JQueryPager getBalanceAssistCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("balanceAssistId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BalanceAssist.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBalanceAssistCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<BalanceAssist> getAssistByBalanceId(String balanceId) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("balance.balanceId", balanceId));
		return criteria.list();
	}

	@Override
	public List<HashMap<String, Object>> getAssistMapByBalanceId(
			String balanceId) {
		String sql = "select num, ltrim(balanceAssistId) balanceAssistId, assistCode, assistValue,assistName from gl_balance_assist where balanceId='"+balanceId+"'";
		Session session = this.getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<HashMap<String,Object>> list = query.list();
		;
		return list;
	}

	@Override
	public List<HashMap<String,Object>> getAssistGroup(String balanceId) {
		String sql = "select num, ltrim(balanceId) balanceId, avg(beginJ) beginJie,avg(beginD) beginDai, avg(yearBalance) yearBalance,avg(initBalance) initBalance from gl_balance_assist where balanceId='"+balanceId+"' group by num,balanceId";
		Session session = this.getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<HashMap<String,Object>> list = query.list();
		return list;
	}

	@Override
	public Boolean removeAssist(String balanceId) {
		try{
			String sql = "delete from gl_balance_assist where balanceId = '"+balanceId+"'";
			Session session = this.getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.executeUpdate();
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public List<HashMap<String, Object>> getByNumBalance(String balanceId,
			String num) {
		String sql = "select num, ltrim(balanceId) balanceId, avg(beginJ) beginJie,avg(beginD) beginDai, avg(yearBalance) yearBalance,avg(initBalance) initBalance from gl_balance_assist where balanceId='"+balanceId+"' and num="+num+" group by num,balanceId";
		Session session = this.getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<HashMap<String,Object>> list = query.list();
		return list;
	}

	@Override
	public List<HashMap<String, String>> getAssistTypes(String balanceId,
			String typeCode) {
		String sql = "select DISTINCT RTRIM(assistName) as assistName, RTRIM(assistValue) as assistValue from GL_balance_assist where balanceId = '"+balanceId+"' and assistCode='"+typeCode+"'";
		Session session = this.getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<HashMap<String,String>> list = query.list();
		return list;
	}

	@Override
	public List<HashMap<String, Object>> getAssistSumByBalance(String balanceId) {
		String sql = "select sum(beginJie) 'beginJie',sum(beginDai) 'beginDai',sum(yearBalance) 'yearBalance', sum(initBalance) 'initBalance' from (select num, ltrim(balanceId) balanceId, avg(beginJ) beginJie,avg(beginD) beginDai, avg(yearBalance) yearBalance,avg(initBalance) initBalance from gl_balance_assist where balanceId = '"+balanceId+"'  group by num,balanceId) t";
		Session session = this.getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<HashMap<String,Object>> list = query.list();
		return list;
	}
}
