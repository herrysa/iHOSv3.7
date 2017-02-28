package com.huge.ihos.bm.loanBill.service.impl;

import java.util.List;
import com.huge.ihos.bm.loanBill.dao.BmLoanbillDetailDao;
import com.huge.ihos.bm.loanBill.model.BmLoanbillDetail;
import com.huge.ihos.bm.loanBill.service.BmLoanbillDetailManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("bmLoanbillDetailManager")
public class BmLoanbillDetailManagerImpl extends GenericManagerImpl<BmLoanbillDetail, String> implements BmLoanbillDetailManager {
    private BmLoanbillDetailDao bmLoanbillDetailDao;

    @Autowired
    public BmLoanbillDetailManagerImpl(BmLoanbillDetailDao bmLoanbillDetailDao) {
        super(bmLoanbillDetailDao);
        this.bmLoanbillDetailDao = bmLoanbillDetailDao;
    }
    
    public JQueryPager getBmLoanbillDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return bmLoanbillDetailDao.getBmLoanbillDetailCriteria(paginatedList,filters);
	}
}