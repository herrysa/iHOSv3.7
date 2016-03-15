package com.huge.ihos.accounting.voucherType.service.impl;

import java.util.List;
import com.huge.ihos.accounting.voucherType.dao.VoucherTypeDao;
import com.huge.ihos.accounting.voucherType.model.VoucherType;
import com.huge.ihos.accounting.voucherType.service.VoucherTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("voucherTypeManager")
public class VoucherTypeManagerImpl extends GenericManagerImpl<VoucherType, String> implements VoucherTypeManager {
    private VoucherTypeDao voucherTypeDao;

    @Autowired
    public VoucherTypeManagerImpl(VoucherTypeDao voucherTypeDao) {
        super(voucherTypeDao);
        this.voucherTypeDao = voucherTypeDao;
    }
    
    public JQueryPager getVoucherTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return voucherTypeDao.getVoucherTypeCriteria(paginatedList,filters);
	}
}