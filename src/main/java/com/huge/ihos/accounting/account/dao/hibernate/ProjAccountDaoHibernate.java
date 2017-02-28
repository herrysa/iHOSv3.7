package com.huge.ihos.accounting.account.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.accounting.account.dao.ProjAccountDao;
import com.huge.ihos.accounting.account.model.ProjAccount;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("projAccountDao")
public class ProjAccountDaoHibernate extends GenericDaoHibernate<ProjAccount, String> implements ProjAccountDao {

    public ProjAccountDaoHibernate() {
        super(ProjAccount.class);
    }
    
    public JQueryPager getProjAccountCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("projAcctId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ProjAccount.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getProjAccountCriteria", e);
			return paginatedList;
		}

	}
}
