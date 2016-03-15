package com.huge.ihos.accounting.voucherType.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.accounting.voucherType.model.VoucherType;
import com.huge.ihos.accounting.voucherType.dao.VoucherTypeDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("voucherTypeDao")
public class VoucherTypeDaoHibernate extends GenericDaoHibernate<VoucherType, String> implements VoucherTypeDao {

    public VoucherTypeDaoHibernate() {
        super(VoucherType.class);
    }
    
    public JQueryPager getVoucherTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("voucherTypeId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, VoucherType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getVoucherTypeCriteria", e);
			return paginatedList;
		}

	}
}
