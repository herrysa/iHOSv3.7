package com.huge.ihos.system.reportManager.search.dao;

import java.util.Map;

import com.huge.ihos.system.reportManager.search.util.SearchCriteria;
import com.huge.util.ReturnUtil;
import com.huge.webapp.pagers.JQueryPager;

public interface QueryDao {
    public JQueryPager getQueryCriteria( JQueryPager paginatedList, SearchCriteria cri );

    public String deletePublic( String tableName, String idName, String[] ids );

    public ReturnUtil processPublic( String taskName, Object[] args );

    public Map<String, Object> getExcelQueryCriteria( SearchCriteria cri );

    public String[] queryTableColumn( String tableName );

    public String executeSqls( String sql );

    public String updateQueryRow( String tableName, String idName, String[] fieldNames, String[] values );
    public String updateQueryRow( String [] sqls);
}
