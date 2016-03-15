package com.huge.ihos.system.reportManager.search.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.excel.DataSet;
import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.model.SearchItem;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.ihos.system.reportManager.search.model.SearchUrl;
import com.huge.ihos.system.reportManager.search.model.ViewDef;
import com.huge.ihos.system.reportManager.search.util.SearchCriteria;
import com.huge.util.ReturnUtil;
import com.huge.webapp.pagers.JQueryPager;

public interface QueryManager {
    public Search getSearchById( String searchId );

    public Search getSearchBySearchName( String searchName );

    public ViewDef getViewDef( String searchName );

    public SearchCriteria getSearchCriteriagetSearchCriteria( HttpServletRequest req, String searchName );

    public JQueryPager getQueryCriteria( JQueryPager paginatedList, SearchCriteria cri );

    public SearchOption[] getSearchOptionsBySearchNameOrdered( String searchId );

    public SearchItem[] getSearchItemsBySearchNameOrdered( String searchId );

    public SearchUrl[] getSearchUrlsBySearchNameOrdered( String searchId );

    public SearchOption[] getSearchSumOptionsBySearchNameOrdered( String searchId );

    public SearchOption[] getSearchEditOptionsBySearchNameOrdered( String searchId );

    public SearchOption[] getSearchAllOptionsBySearchIdOrdered( String searchId );

    public Map<String, Object> getExcelQueryCriteria( SearchCriteria cri );

    public String updateQueryList( String searchName, String id, HttpServletRequest req );

    public String updateQueryRow( String tableName, String idName, String[] fieldNames, String[] values );
    
    public String updateQueryRow( String tableName,String tableId, Map map);
    
    public String addQueryRow( String tableName, Map map);

    //public String getCurrentPeriod();

    public String publicDelete( String searchName, String[] ids );

    public ReturnUtil publicPrecess( String taskName, Object[] args );

    public String formateOptionData( String type, String formatStr, Object data );

    public Map getSearchItemDefineAndValue( HttpServletRequest req, String searchName );
    
    public SearchOption[] getFormedSearchOptions( String searchId );
    
    public String outputExcel(String templateName,
			Map<String , Map<String, DataSet>> excelData );
    
    public SearchUrl[] getSearchUrlByRight(String userId,String searchId);
}
