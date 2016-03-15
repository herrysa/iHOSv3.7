package com.huge.ihos.formula.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.formula.dao.AllotPrincipleDao;
import com.huge.ihos.formula.model.AllotPrinciple;
import com.huge.ihos.formula.service.AllotPrincipleManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "allotPrincipleManager" )
public class AllotPrincipleManagerImpl
    extends GenericManagerImpl<AllotPrinciple, String>
    implements AllotPrincipleManager {
    AllotPrincipleDao allotPrincipleDao;

    @Autowired
    public AllotPrincipleManagerImpl( AllotPrincipleDao allotPrincipleDao ) {
        super( allotPrincipleDao );
        this.allotPrincipleDao = allotPrincipleDao;
    }

    public JQueryPager getAllotPrincipleCriteria( JQueryPager paginatedList ) {
        return allotPrincipleDao.getAllotPrincipleCriteria( paginatedList );
    }

}