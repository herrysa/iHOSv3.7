package com.huge.ihos.accounting.account.dao.hibernate;



import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.accounting.account.dao.AccountTypeDao;
import com.huge.ihos.accounting.account.model.AccountType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("accountTypeDao")
public class AccountTypeDaoHibernate extends GenericDaoHibernate<AccountType, String> implements AccountTypeDao {

    public AccountTypeDaoHibernate() {
        super(AccountType.class);
    }
    
    public JQueryPager getAccountTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("accttypeId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, AccountType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getAccountTypeCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<AccountType> getAll(HashMap<String, String> environment) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(AccountType.class);
		criteria.add(Restrictions.eq("orgCode", environment.get("orgCode")));
		criteria.add(Restrictions.eq("copyCode", environment.get("copyCode")));
		return criteria.list();
	}

	@Override
	public List<AccountType> getAllAccountTypeByOC(String orgCode, String copyCode) {
		List<AccountType> accountTypes = new ArrayList<AccountType>();
		try {
			String sql = "from AccountType acctp where acctp.orgCode =:orgCode and acctp.copyCode =:copyCode";
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query query = session.createQuery(sql);
			query.setString("orgCode", orgCode);
			query.setString("copyCode", copyCode);
			accountTypes = query.list();
		} catch (HibernateException e) {
			log.error("getAllAccountTypeByOC",e);
		}
		return accountTypes;
	}
    
    
}
