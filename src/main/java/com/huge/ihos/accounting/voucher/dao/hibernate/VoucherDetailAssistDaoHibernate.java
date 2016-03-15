package com.huge.ihos.accounting.voucher.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.accounting.voucher.model.VoucherDetailAssist;
import com.huge.ihos.accounting.voucher.dao.VoucherDetailAssistDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("voucherDetailAssistDao")
public class VoucherDetailAssistDaoHibernate extends GenericDaoHibernate<VoucherDetailAssist, String> implements VoucherDetailAssistDao {

    public VoucherDetailAssistDaoHibernate() {
        super(VoucherDetailAssist.class);
    }
    
    public JQueryPager getVoucherDetailAssistCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("voucherDetailAssistId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, VoucherDetailAssist.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getVoucherDetailAssistCriteria", e);
			return paginatedList;
		}

	}
}
