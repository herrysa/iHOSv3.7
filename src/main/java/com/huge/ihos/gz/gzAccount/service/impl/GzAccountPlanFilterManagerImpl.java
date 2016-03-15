package com.huge.ihos.gz.gzAccount.service.impl;

import java.util.List;
import com.huge.ihos.gz.gzAccount.dao.GzAccountPlanFilterDao;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlanFilter;
import com.huge.ihos.gz.gzAccount.service.GzAccountPlanFilterManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("gzAccountPlanFliterManager")
public class GzAccountPlanFilterManagerImpl extends GenericManagerImpl<GzAccountPlanFilter, String> implements GzAccountPlanFilterManager {
    private GzAccountPlanFilterDao gzAccountPlanFliterDao;

    @Autowired
    public GzAccountPlanFilterManagerImpl(GzAccountPlanFilterDao gzAccountPlanFliterDao) {
        super(gzAccountPlanFliterDao);
        this.gzAccountPlanFliterDao = gzAccountPlanFliterDao;
    }
    
    public JQueryPager getGzAccountPlanFliterCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return gzAccountPlanFliterDao.getGzAccountPlanFliterCriteria(paginatedList,filters);
	}
}