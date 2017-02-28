package com.huge.ihos.accounting.balance.dao.hibernate;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.accounting.balance.dao.BalancePeriodDao;
import com.huge.ihos.accounting.balance.model.BalancePeriod;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("balancePeriodDao")
public class BalancePeriodDaoHibernate extends GenericDaoHibernate<BalancePeriod, String> implements BalancePeriodDao {

    public BalancePeriodDaoHibernate() {
        super(BalancePeriod.class);
    }
    
    public JQueryPager getBalancePeriodCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("balancePeriodId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BalancePeriod.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBalancePeriodCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<BalancePeriod> getBalancePeriodByEnvironment(
			HashMap<String, String> environment) {
		Criteria criteria = getCriteria();
		criteria.createAlias("balance", "balance");
		criteria.add(Restrictions.eq("balance.orgCode", environment.get("orgCode")));
		criteria.add(Restrictions.eq("balance.copyCode", environment.get("copyCode")));
		if(environment.get("periodYear")!=null){
			criteria.add(Restrictions.eq("balance.kjYear", environment.get("periodYear")));
		}
		if(environment.get("periodMonth")!=null){
			if(environment.get("periodMonth").contains(",")){
				criteria.add(Restrictions.in("periodMonth", environment.get("periodMonth").split(",")));
			}else{
				criteria.add(Restrictions.eq("periodMonth", environment.get("periodMonth")));
			}
			criteria.addOrder(Order.asc("periodMonth"));
		}
		if(environment.get("accountId")!=null){
			criteria.add(Restrictions.eq("balance.account.acctId", environment.get("accountId")));
		}
		if(environment.get("acctCode")!=null){
			criteria.add(Restrictions.eq("balance.acctCode", environment.get("acctCode")));
		}
		return criteria.list();
	}

	@Override
	public void updateMonthBalance(String orgCode, String copyCode,
			String kjYear, String month) {
		String sql = " update per set per.monthBalance = bal.initBalance "
					+" from gl_balance_period per,gl_balance bal "
					+" where per.balanceId = bal.balanceId and periodMonth = '"+kjYear+month+"' and " 
					+" bal.orgCode='"+orgCode+"' and bal.copyCode='"+copyCode+"' and bal.kjYear='"+kjYear+"'";
		
		Session session = this.getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
		
	}
}
