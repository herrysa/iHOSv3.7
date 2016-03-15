package com.huge.ihos.accounting.voucherFrom.service.impl;

import java.util.List;
import com.huge.ihos.accounting.voucherFrom.dao.VoucherFromDao;
import com.huge.ihos.accounting.voucherFrom.model.VoucherFrom;
import com.huge.ihos.accounting.voucherFrom.service.VoucherFromManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("voucherFromManager")
public class VoucherFromManagerImpl extends GenericManagerImpl<VoucherFrom, String> implements VoucherFromManager {
    private VoucherFromDao voucherFromDao;

    @Autowired
    public VoucherFromManagerImpl(VoucherFromDao voucherFromDao) {
        super(voucherFromDao);
        this.voucherFromDao = voucherFromDao;
    }
    
    public JQueryPager getVoucherFromCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return voucherFromDao.getVoucherFromCriteria(paginatedList,filters);
	}
}