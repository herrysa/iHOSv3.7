package com.huge.ihos.formula.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.formula.dao.AllotSetDao;
import com.huge.ihos.formula.model.AllotSet;
import com.huge.ihos.formula.service.AllotSetManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "allotSetManager" )
public class AllotSetManagerImpl
    extends GenericManagerImpl<AllotSet, String>
    implements AllotSetManager {
    AllotSetDao allotSetDao;

    @Autowired
    public AllotSetManagerImpl( AllotSetDao allotSetDao ) {
        super( allotSetDao );
        this.allotSetDao = allotSetDao;
    }

    public JQueryPager getAllotSetCriteria( JQueryPager paginatedList ) {
        return allotSetDao.getAllotSetCriteria( paginatedList );
    }

}