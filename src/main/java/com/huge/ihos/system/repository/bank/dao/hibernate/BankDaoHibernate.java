package com.huge.ihos.system.repository.bank.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.repository.bank.dao.BankDao;
import com.huge.ihos.system.repository.bank.model.Bank;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("bankDao")
public class BankDaoHibernate extends GenericDaoHibernate<Bank, String> implements BankDao {

    public BankDaoHibernate() {
        super(Bank.class);
    }
    
    public JQueryPager getBankCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("bankId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Bank.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBankCriteria", e);
			return paginatedList;
		}

	}
}
