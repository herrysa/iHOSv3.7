package com.huge.ihos.system.reportManager.chart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.chart.dao.MccKeyDetailDao;
import com.huge.ihos.system.reportManager.chart.model.MccKeyDetail;
import com.huge.ihos.system.reportManager.chart.service.MccKeyDetailManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;


@Service( "mccKeyDetailManager" )
public class MccKeyDetailManagerImpl
    extends GenericManagerImpl<MccKeyDetail, String>
    implements MccKeyDetailManager {
    MccKeyDetailDao mccKeyDetailDao;

    @Autowired
    public MccKeyDetailManagerImpl( MccKeyDetailDao mccKeyDetailDao ) {
        super( mccKeyDetailDao );
        this.mccKeyDetailDao = mccKeyDetailDao;
    }

    public JQueryPager getMccKeyDetailCriteria( String parentId, JQueryPager paginatedList ) {
        return mccKeyDetailDao.getMccKeyDetailCriteria( parentId, paginatedList );
    }

}