package com.huge.ihos.update.service.impl;

import java.util.List;

import com.huge.ihos.update.dao.WorkScoreDao;
import com.huge.ihos.update.model.WorkScore;
import com.huge.ihos.update.service.WorkScoreManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("workScoreManager")
public class WorkScoreManagerImpl extends GenericManagerImpl<WorkScore, String> implements WorkScoreManager {
    private WorkScoreDao workScoreDao;

    @Autowired
    public WorkScoreManagerImpl(WorkScoreDao workScoreDao) {
        super(workScoreDao);
        this.workScoreDao = workScoreDao;
    }
    
    public JQueryPager getWorkScoreCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return workScoreDao.getWorkScoreCriteria(paginatedList,filters);
	}
}