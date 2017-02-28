package com.huge.ihos.accounting.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.accounting.account.dao.ProjAccountDao;
import com.huge.ihos.accounting.account.model.ProjAccount;
import com.huge.ihos.accounting.account.service.ProjAccountManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("projAccountManager")
public class ProjAccountManagerImpl extends GenericManagerImpl<ProjAccount, String> implements ProjAccountManager {
    private ProjAccountDao projAccountDao;

    @Autowired
    public ProjAccountManagerImpl(ProjAccountDao projAccountDao) {
        super(projAccountDao);
        this.projAccountDao = projAccountDao;
    }
    
    public JQueryPager getProjAccountCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return projAccountDao.getProjAccountCriteria(paginatedList,filters);
	}
}