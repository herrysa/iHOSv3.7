package com.huge.ihos.material.order.service.impl;

import java.util.List;
import com.huge.ihos.material.order.dao.ImportOrderLogDetailDao;
import com.huge.ihos.material.order.model.ImportOrderLogDetail;
import com.huge.ihos.material.order.service.ImportOrderLogDetailManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("importOrderLogDetailManager")
public class ImportOrderLogDetailManagerImpl extends GenericManagerImpl<ImportOrderLogDetail, String> implements ImportOrderLogDetailManager {
    private ImportOrderLogDetailDao importOrderLogDetailDao;

    @Autowired
    public ImportOrderLogDetailManagerImpl(ImportOrderLogDetailDao importOrderLogDetailDao) {
        super(importOrderLogDetailDao);
        this.importOrderLogDetailDao = importOrderLogDetailDao;
    }
    
    public JQueryPager getImportOrderLogDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return importOrderLogDetailDao.getImportOrderLogDetailCriteria(paginatedList,filters);
	}
}