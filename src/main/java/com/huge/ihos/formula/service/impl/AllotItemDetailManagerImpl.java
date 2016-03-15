package com.huge.ihos.formula.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.formula.dao.AllotItemDetailDao;
import com.huge.ihos.formula.model.AllotItemDetail;
import com.huge.ihos.formula.service.AllotItemDetailManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "allotItemDetailManager" )
public class AllotItemDetailManagerImpl
    extends GenericManagerImpl<AllotItemDetail, Long>
    implements AllotItemDetailManager {
    AllotItemDetailDao allotItemDetailDao;

    @Autowired
    public AllotItemDetailManagerImpl( AllotItemDetailDao allotItemDetailDao ) {
        super( allotItemDetailDao );
        this.allotItemDetailDao = allotItemDetailDao;
    }

    public JQueryPager getAllotItemDetailCriteria( JQueryPager paginatedList, Long allotItemId ) {
        return allotItemDetailDao.getAllotItemDetailCriteria( paginatedList, allotItemId );
    }

}