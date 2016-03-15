package com.huge.ihos.gz.gzAccount.service.impl;

import java.util.List;
import com.huge.ihos.gz.gzAccount.dao.GzAccountDefineDao;
import com.huge.ihos.gz.gzAccount.model.GzAccountDefine;
import com.huge.ihos.gz.gzAccount.service.GzAccountDefineManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("gzAccountDefineManager")
public class GzAccountDefineManagerImpl extends GenericManagerImpl<GzAccountDefine, String> implements GzAccountDefineManager {
    private GzAccountDefineDao gzAccountDefineDao;

    @Autowired
    public GzAccountDefineManagerImpl(GzAccountDefineDao gzAccountDefineDao) {
        super(gzAccountDefineDao);
        this.gzAccountDefineDao = gzAccountDefineDao;
    }
    
    public JQueryPager getGzAccountDefineCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return gzAccountDefineDao.getGzAccountDefineCriteria(paginatedList,filters);
	}
}