package com.huge.ihos.kq.kqUpData.service.impl;

import java.util.List;
import com.huge.ihos.kq.kqUpData.dao.KqDayDataDetailDao;
import com.huge.ihos.kq.kqUpData.model.KqDayDataDetail;
import com.huge.ihos.kq.kqUpData.service.KqDayDataDetailManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("kqDayDataDetailManager")
public class KqDayDataDetailManagerImpl extends GenericManagerImpl<KqDayDataDetail, String> implements KqDayDataDetailManager {
    private KqDayDataDetailDao kqDayDataDetailDao;

    @Autowired
    public KqDayDataDetailManagerImpl(KqDayDataDetailDao kqDayDataDetailDao) {
        super(kqDayDataDetailDao);
        this.kqDayDataDetailDao = kqDayDataDetailDao;
    }
    
    public JQueryPager getKqDayDataDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return kqDayDataDetailDao.getKqDayDataDetailCriteria(paginatedList,filters);
	}
}