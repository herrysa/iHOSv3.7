package com.huge.ihos.accounting.voucher.service.impl;

import java.util.List;
import com.huge.ihos.accounting.voucher.dao.VoucherDetailAssistDao;
import com.huge.ihos.accounting.voucher.model.VoucherDetailAssist;
import com.huge.ihos.accounting.voucher.service.VoucherDetailAssistManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("voucherDetailAssistManager")
public class VoucherDetailAssistManagerImpl extends GenericManagerImpl<VoucherDetailAssist, String> implements VoucherDetailAssistManager {
    private VoucherDetailAssistDao voucherDetailAssistDao;

    @Autowired
    public VoucherDetailAssistManagerImpl(VoucherDetailAssistDao voucherDetailAssistDao) {
        super(voucherDetailAssistDao);
        this.voucherDetailAssistDao = voucherDetailAssistDao;
    }
    
    public JQueryPager getVoucherDetailAssistCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return voucherDetailAssistDao.getVoucherDetailAssistCriteria(paginatedList,filters);
	}
}