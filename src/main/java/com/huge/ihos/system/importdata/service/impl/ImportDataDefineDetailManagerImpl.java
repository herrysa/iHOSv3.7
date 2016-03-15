package com.huge.ihos.system.importdata.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.importdata.dao.ImportDataDefineDetailDao;
import com.huge.ihos.system.importdata.model.ImportDataDefineDetail;
import com.huge.ihos.system.importdata.service.ImportDataDefineDetailManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("importDataDefineDetailManager")
public class ImportDataDefineDetailManagerImpl extends GenericManagerImpl<ImportDataDefineDetail, String> implements ImportDataDefineDetailManager {
    private ImportDataDefineDetailDao importDataDefineDetailDao;

    @Autowired
    public ImportDataDefineDetailManagerImpl(ImportDataDefineDetailDao importDataDefineDetailDao) {
        super(importDataDefineDetailDao);
        this.importDataDefineDetailDao = importDataDefineDetailDao;
    }
    
    public JQueryPager getImportDataDefineDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return importDataDefineDetailDao.getImportDataDefineDetailCriteria(paginatedList,filters);
	}
}