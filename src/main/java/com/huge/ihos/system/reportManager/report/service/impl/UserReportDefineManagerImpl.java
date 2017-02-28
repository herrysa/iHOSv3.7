package com.huge.ihos.system.reportManager.report.service.impl;

import java.util.List;
import com.huge.ihos.system.reportManager.report.dao.UserReportDefineDao;
import com.huge.ihos.system.reportManager.report.model.UserReportDefine;
import com.huge.ihos.system.reportManager.report.service.UserReportDefineManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("userReportDefineManager")
public class UserReportDefineManagerImpl extends GenericManagerImpl<UserReportDefine, String> implements UserReportDefineManager {
    private UserReportDefineDao userReportDefineDao;

    @Autowired
    public UserReportDefineManagerImpl(UserReportDefineDao userReportDefineDao) {
        super(userReportDefineDao);
        this.userReportDefineDao = userReportDefineDao;
    }
    
    public JQueryPager getUserReportDefineCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return userReportDefineDao.getUserReportDefineCriteria(paginatedList,filters);
	}
}