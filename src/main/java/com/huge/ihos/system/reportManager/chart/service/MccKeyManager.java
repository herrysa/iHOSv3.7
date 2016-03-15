package com.huge.ihos.system.reportManager.chart.service;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.huge.ihos.system.reportManager.chart.model.MccKey;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface MccKeyManager
    extends GenericManager<MccKey, String> {
    /**
     * 添加MccKey
     * @param mccKey
     */
    public void saveMccKey( MccKey mccKey );

    public JQueryPager getMccKeyCriteria( final JQueryPager paginatedList );

    /**
     * 根据Ckey查MccKey
     * @param Ckey
     * @return
     */
    public MccKey getCkey( String cKey );

    /**
     * 选择图形并返回结果
     * @param mccKey
     * @return
     * @throws SQLException
     */
    public Object[] choiceChart( MccKey mccKey, Map<String,String> conditionMap )
        throws SQLException;

    /**
     * 本量利分析
     * @param gdParam
     * @param fileName
     * @param picName
     */
    public String graDisPlay( String gdParam, String biaoTi, String dataMsg, String fileName, String picName, String xyName, String biaoZhu )
        throws SQLException;

    /**
     * search
     * @param list
     * @return
     */
    public String zheXianSearch( List list , String searchTitle ,LinkedHashSet<String> keyName);
}