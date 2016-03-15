package com.huge.ihos.accounting.voucherFrom.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.accounting.voucherFrom.model.VoucherFrom;
import com.huge.ihos.accounting.voucherFrom.dao.VoucherFromDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("voucherFromDao")
public class VoucherFromDaoHibernate extends GenericDaoHibernate<VoucherFrom, String> implements VoucherFromDao {

    public VoucherFromDaoHibernate() {
        super(VoucherFrom.class);
    }
    
    public JQueryPager getVoucherFromCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("voucherFromCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, VoucherFrom.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getVoucherFromCriteria", e);
			return paginatedList;
		}

	}
}
