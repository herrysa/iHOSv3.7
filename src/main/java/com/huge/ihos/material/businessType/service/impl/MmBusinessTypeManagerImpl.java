package com.huge.ihos.material.businessType.service.impl;

import java.util.List;
import com.huge.ihos.material.businessType.dao.MmBusinessTypeDao;
import com.huge.ihos.material.businessType.model.MmBusinessType;
import com.huge.ihos.material.businessType.service.MmBusinessTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("mmBusinessTypeManager")
public class MmBusinessTypeManagerImpl extends GenericManagerImpl<MmBusinessType, String> implements MmBusinessTypeManager {
    private MmBusinessTypeDao businessTypeDao;

    @Autowired
    public MmBusinessTypeManagerImpl(MmBusinessTypeDao businessTypeDao) {
        super(businessTypeDao);
        this.businessTypeDao = businessTypeDao;
    }
    
    public JQueryPager getBusinessTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return businessTypeDao.getBusinessTypeCriteria(paginatedList,filters);
	}

	@Override
	public List<MmBusinessType> getBusTypeByIo(String io) {
		return businessTypeDao.getBusTypeByIo(io);
	}
}