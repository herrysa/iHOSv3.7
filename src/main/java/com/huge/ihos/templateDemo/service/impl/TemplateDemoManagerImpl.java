package com.huge.ihos.templateDemo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.globalparam.model.GlobalParam;
import com.huge.ihos.templateDemo.dao.TemplateDemoDao;
import com.huge.ihos.templateDemo.service.TemplateDemoManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service( "templateDemoManager" )
public class TemplateDemoManagerImpl
    extends GenericManagerImpl<GlobalParam, Long>
    implements TemplateDemoManager {
    TemplateDemoDao templateDemoDao;

    @Autowired
    public TemplateDemoManagerImpl( TemplateDemoDao templateDemoDao ) {
        super( templateDemoDao );
        this.templateDemoDao = templateDemoDao;
    }

    public JQueryPager getGlobalParamCriteria( JQueryPager paginatedList, List<PropertyFilter> filters ) {
        return templateDemoDao.getGlobalParamCriteria( paginatedList, filters );
    }
}