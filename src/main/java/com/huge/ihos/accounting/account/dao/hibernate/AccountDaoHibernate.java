package com.huge.ihos.accounting.account.dao.hibernate;



import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.accounting.account.dao.AccountDao;
import com.huge.ihos.accounting.account.model.Account;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("accountDao")
public class AccountDaoHibernate extends GenericDaoHibernate<Account, String> implements AccountDao {

    public AccountDaoHibernate() {
        super(Account.class);
    }
    
    public JQueryPager getAccountCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("acctId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Account.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getAccountCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public Account getAccountByNumber(int number,String selectId) {
		Account account = null;
		try {
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			String sql = "select * from ( select * , number = row_number() over( order by acctcode) from GL_account where acctId like '"+selectId+"%' ) m where number = "+number;
			Query query = session.createSQLQuery(sql).addEntity(Account.class);
			account = (Account)query.list().get(0);
		} catch (HibernateException e) {
			log.error("getAccountByNumber",e);
		}
		return account;
	}

	@Override
	public void updateAccountDisabled(String acctId,String lossDirection) {
		try {
			String sql = "update Account set LossDirection = '"+lossDirection+"' where acctId like '"+acctId+"%'";
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query query = session.createQuery(sql);
			query.executeUpdate();
		} catch (HibernateException e) {
			log.error("updateAccountDisabled",e);
		}
	}

	@Override
	public int hasChildren(String parentId) {
		int hasChildren = -1;
		try {
			String sql = "from Account where acctId like '"+parentId+"%'";
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query query = session.createQuery(sql);
			if(query.list().size()==0){//不存在
				hasChildren = -1;
			}else if(query.list().size()==1){//顶级
				hasChildren = 0;
			}else{//有孩子
				hasChildren = 1;
			}
		} catch (HibernateException e) {
			log.error("hasChildren",e);
		}
		return hasChildren;
	}

	@Override
	public List<Account> getAll(HashMap<String, String> environment) {
		Criteria criteria =  this.getSessionFactory().getCurrentSession().createCriteria(Account.class);
		criteria.add(Restrictions.eq("orgCode", environment.get("orgCode")));
		criteria.add(Restrictions.eq("copyCode", environment.get("copyCode")));
		criteria.add(Restrictions.eq("kjYear", environment.get("kjYear")));
		return criteria.list();
	}

	@Override
	public List<Account> getAllAccountByOCK(String orgCode, String copyCode,
			String kjYear) {
		List<Account> accounts = new ArrayList<Account>();
		try {
			String sql = "from Account acct where acct.orgCode =:orgCode and acct.copyCode =:copyCode and acct.kjYear =:kjYear";
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query query = session.createQuery(sql);
			query.setString("orgCode", orgCode);
			query.setString("copyCode", copyCode);
			query.setString("kjYear", kjYear);
			accounts = query.list();
		} catch (HibernateException e) {
			log.error("getAllAccountByOCK",e);
		}
		return accounts;
	}
	
	

}
