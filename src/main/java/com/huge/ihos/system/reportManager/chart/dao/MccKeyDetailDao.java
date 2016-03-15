package com.huge.ihos.system.reportManager.chart.dao;

import java.sql.SQLException;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.reportManager.chart.model.MccKeyDetail;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the MccKeyDetail table.
 */
public interface MccKeyDetailDao
    extends GenericDao<MccKeyDetail, String> {

    public JQueryPager getMccKeyDetailCriteria( final String parentId, final JQueryPager paginatedList );

    /**
     * 表盘数据源
     * @param mccKeyId
     * @return
     */
    public Object[] clockDialMethod( String[] data )
        throws SQLException;
}