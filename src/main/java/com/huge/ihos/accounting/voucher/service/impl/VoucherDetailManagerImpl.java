package com.huge.ihos.accounting.voucher.service.impl;

import java.util.List;
import com.huge.ihos.accounting.voucher.dao.VoucherDetailDao;
import com.huge.ihos.accounting.voucher.model.VoucherDetail;
import com.huge.ihos.accounting.voucher.service.VoucherDetailManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("voucherDetailManager")
public class VoucherDetailManagerImpl extends GenericManagerImpl<VoucherDetail, String> implements VoucherDetailManager {
    private VoucherDetailDao voucherDetailDao;

    @Autowired
    public VoucherDetailManagerImpl(VoucherDetailDao voucherDetailDao) {
        super(voucherDetailDao);
        this.voucherDetailDao = voucherDetailDao;
    }
    
    public JQueryPager getVoucherDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return voucherDetailDao.getVoucherDetailCriteria(paginatedList,filters);
	}

	@Override
	public void deleteByVoucherId(String voucherId) {
		// TODO Auto-generated method stub
		
	}
}