package com.huge.ihos.bm.loanBill.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.loanBill.model.BmLoanbill;
import com.huge.ihos.bm.loanBill.dao.BmLoanbillDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("bmLoanbillDao")
public class BmLoanbillDaoHibernate extends GenericDaoHibernate<BmLoanbill, String> implements BmLoanbillDao {

    public BmLoanbillDaoHibernate() {
        super(BmLoanbill.class);
    }
    
    public JQueryPager getBmLoanbillCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("billId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BmLoanbill.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBmLoanbillCriteria", e);
			return paginatedList;
		}

	}
}
