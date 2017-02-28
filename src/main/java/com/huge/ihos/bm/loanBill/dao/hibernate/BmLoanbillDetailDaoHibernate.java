package com.huge.ihos.bm.loanBill.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.loanBill.model.BmLoanbillDetail;
import com.huge.ihos.bm.loanBill.dao.BmLoanbillDetailDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("bmLoanbillDetailDao")
public class BmLoanbillDetailDaoHibernate extends GenericDaoHibernate<BmLoanbillDetail, String> implements BmLoanbillDetailDao {

    public BmLoanbillDetailDaoHibernate() {
        super(BmLoanbillDetail.class);
    }
    
    public JQueryPager getBmLoanbillDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("billdetailId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BmLoanbillDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBmLoanbillDetailCriteria", e);
			return paginatedList;
		}

	}
}
