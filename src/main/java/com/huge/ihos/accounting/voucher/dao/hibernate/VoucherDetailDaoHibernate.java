package com.huge.ihos.accounting.voucher.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.accounting.voucher.model.VoucherDetail;
import com.huge.ihos.accounting.voucher.dao.VoucherDetailDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("voucherDetailDao")
public class VoucherDetailDaoHibernate extends GenericDaoHibernate<VoucherDetail, String> implements VoucherDetailDao {

    public VoucherDetailDaoHibernate() {
        super(VoucherDetail.class);
    }
    
    public JQueryPager getVoucherDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("voucherDetailId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, VoucherDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getVoucherDetailCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public void deleteByVoucherId(String voucherId) {
		// TODO Auto-generated method stub
		//this.
	}
}
