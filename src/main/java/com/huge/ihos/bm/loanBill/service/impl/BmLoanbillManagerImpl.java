package com.huge.ihos.bm.loanBill.service.impl;

import java.util.List;
import com.huge.ihos.bm.loanBill.dao.BmLoanbillDao;
import com.huge.ihos.bm.loanBill.model.BmLoanbill;
import com.huge.ihos.bm.loanBill.service.BmLoanbillManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("bmLoanbillManager")
public class BmLoanbillManagerImpl extends GenericManagerImpl<BmLoanbill, String> implements BmLoanbillManager {
    private BmLoanbillDao bmLoanbillDao;

    @Autowired
    public BmLoanbillManagerImpl(BmLoanbillDao bmLoanbillDao) {
        super(bmLoanbillDao);
        this.bmLoanbillDao = bmLoanbillDao;
    }
    
    public JQueryPager getBmLoanbillCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return bmLoanbillDao.getBmLoanbillCriteria(paginatedList,filters);
	}
}