package com.huge.ihos.system.reportManager.chart.dao;

import java.sql.SQLException;
import java.util.Map;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.reportManager.chart.model.MccKey;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the MccKey table.
 */
public interface MccKeyDao
    extends GenericDao<MccKey, String> {

    public JQueryPager getMccKeyCriteria( final JQueryPager paginatedList );

    /**
     * 根据Ckey查MccKey
     * @param Ckey
     * @return
     */
    public MccKey getCkey( String cKey );

    /**
     * 图表数据源
     * @param mySql
     * @return
     * @throws SQLException
     */
    public Map<String, Object[]> obtainDataMethod( String mySql )
        throws SQLException;

    /**
     * 获取本量利数据
     * @param sql
     * @return
     */
    public Float[] obtainDatagradisPlay( String sql, String[] columnName )
        throws SQLException;
}