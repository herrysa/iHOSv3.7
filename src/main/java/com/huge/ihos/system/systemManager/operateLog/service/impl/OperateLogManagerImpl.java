package com.huge.ihos.system.systemManager.operateLog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.operateLog.dao.OperateLogDao;
import com.huge.ihos.system.systemManager.operateLog.model.OperateLog;
import com.huge.ihos.system.systemManager.operateLog.service.OperateLogManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service( "operateLogManager" )
public class OperateLogManagerImpl
    extends GenericManagerImpl<OperateLog, Long>
    implements OperateLogManager {
    private OperateLogDao operateLogDao;

    @Autowired
    public OperateLogManagerImpl( OperateLogDao operateLogDao ) {
        super( operateLogDao );
        this.operateLogDao = operateLogDao;
    }

    public JQueryPager getOperateLogCriteria( JQueryPager paginatedList, List<PropertyFilter> filters ) {
        return operateLogDao.getOperateLogCriteria( paginatedList, filters );
    }
}