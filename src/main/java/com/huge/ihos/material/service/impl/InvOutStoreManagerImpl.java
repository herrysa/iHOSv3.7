package com.huge.ihos.material.service.impl;

import java.util.List;
import com.huge.ihos.material.dao.InvOutStoreDao;
import com.huge.ihos.material.model.InvOutStore;
import com.huge.ihos.material.service.InvOutStoreManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("invOutStoreManager")
public class InvOutStoreManagerImpl extends GenericManagerImpl<InvOutStore, String> implements InvOutStoreManager {
    private InvOutStoreDao invOutStoreDao;

    @Autowired
    public InvOutStoreManagerImpl(InvOutStoreDao invOutStoreDao) {
        super(invOutStoreDao);
        this.invOutStoreDao = invOutStoreDao;
    }
    
    public JQueryPager getInvOutStoreCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return invOutStoreDao.getInvOutStoreCriteria(paginatedList,filters);
	}
}