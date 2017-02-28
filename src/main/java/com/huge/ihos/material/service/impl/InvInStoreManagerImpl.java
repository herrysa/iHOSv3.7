package com.huge.ihos.material.service.impl;

import java.util.List;
import com.huge.ihos.material.dao.InvInStoreDao;
import com.huge.ihos.material.model.InvInStore;
import com.huge.ihos.material.service.InvInStoreManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("invInStoreManager")
public class InvInStoreManagerImpl extends GenericManagerImpl<InvInStore, String> implements InvInStoreManager {
    private InvInStoreDao invInStoreDao;

    @Autowired
    public InvInStoreManagerImpl(InvInStoreDao invInStoreDao) {
        super(invInStoreDao);
        this.invInStoreDao = invInStoreDao;
    }
    
    public JQueryPager getInvInStoreCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return invInStoreDao.getInvInStoreCriteria(paginatedList,filters);
	}
}