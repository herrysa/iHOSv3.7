package com.huge.ihos.search.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.huge.ihos.excel.ColumnDefine;
import com.huge.ihos.excel.DataSet;
import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.ihos.system.reportManager.search.service.impl.QueryManagerImpl;
import com.huge.ihos.system.reportManager.search.util.SearchCriteria;
import com.huge.service.BaseManagerTestCase;
import com.huge.webapp.pagers.JQueryPager;

public class QueryManagerImplTest
    extends BaseManagerTestCase {
    private Log log = LogFactory.getLog( QueryManagerImplTest.class );

    @Autowired
    QueryManager queryManager;

   // @Test
    public void testIoc() {
        //QueryManagerImpl qi = (QueryManagerImpl)queryManager;
        assertTrue( queryManager != null );
        //assertTrue(qi.getSearchDao() != null);
        //assertTrue(qi.getSearchItemDao() != null);
        //assertTrue(qi.getSearchLinkDao() != null);
        //assertTrue(qi.getSearchOptionDao() != null);
        //assertTrue(qi.getSearchUrlDao() != null);
    }

  //  @Test
    public void testQueryList() {
        String searchName = "TestSearchSample";
        JQueryPager jq = new JQueryPager();
        jq.setPageSize( 20 );

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter( "htmlBooleanField", "1" );

        SearchCriteria cri = this.queryManager.getSearchCriteriagetSearchCriteria( req, searchName );
        jq = queryManager.getQueryCriteria( jq, cri );
        Assert.assertNotNull( jq );
    }
    
    @Test
    public void outputExcel(){
    	String templateName = "D://JX005.xls";
    	List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
    	Map<String, String> row = new HashMap<String, String>();
    	row.put("FIELD328", "001");
    	row.put("FIELD03", "003");
    	row.put("FIELD05", "005");
    	dataList.add(row);
    	Map<String, String> row2 = new HashMap<String, String>();
    	row2.put("FIELD03", "002");
    	row2.put("FIELD05", "005");
    	//dataList.add(row2);
    	DataSet dataSet = new DataSet();
    	dataSet.multiRow = true;
    	dataSet.maxDataRowLength = dataList.size();
    	dataSet.rowList =  dataList;
    	dataSet.name = "data";
    	Map<String, ColumnDefine> columndefineMap = new HashMap<String, ColumnDefine>();
    	ColumnDefine columnDefine = new ColumnDefine();
    	columnDefine.name = "FIELD328";
    	columnDefine.type = 3;
    	columndefineMap.put(columnDefine.name, columnDefine);
    	ColumnDefine columnDefine3 = new ColumnDefine();
    	columnDefine3.name = "FIELD03";
    	columnDefine3.type = 1;
    	columndefineMap.put(columnDefine3.name, columnDefine3);
    	ColumnDefine columnDefine5 = new ColumnDefine();
    	columnDefine5.name = "FIELD05";
    	columnDefine5.type = 1;
    	columndefineMap.put(columnDefine5.name, columnDefine5);
    	dataSet.columnDefineMap = columndefineMap;
    	Map<String, DataSet> dataSetMap = new HashMap<String, DataSet>();
    	dataSetMap.put(dataSet.name, dataSet);
    	Map<String, Map<String, DataSet>> excelData = new HashMap<String, Map<String,DataSet>>();
    	excelData.put("data", dataSetMap);
    	queryManager.outputExcel(templateName, excelData );
    }
    
    //@Test
    public void trimData(){
    	QueryManagerImpl queryManagerImpl = new QueryManagerImpl();
    	List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
    	Map<String, String> row = new HashMap<String, String>();
    	row.put("FIELD01", "001");
    	row.put("FIELD03", "003");
    	row.put("FIELD05", "005");
    	dataList.add(row);
    	Map<String, String> row2 = new HashMap<String, String>();
    	row2.put("FIELD03", "002");
    	row2.put("FIELD05", "005");
    	dataList.add(row2);
    	DataSet dataSet = new DataSet();
    	dataSet.multiRow = true;
    	dataSet.rowList =  dataList;
    	dataSet.name = "data";
    	Map<String, ColumnDefine> columndefineMap = new HashMap<String, ColumnDefine>();
    	ColumnDefine columnDefine = new ColumnDefine();
    	columnDefine.name = "FIELD01";
    	columnDefine.type = 1;
    	columndefineMap.put(columnDefine.name, columnDefine);
    	ColumnDefine columnDefine3 = new ColumnDefine();
    	columnDefine3.name = "FIELD03";
    	columnDefine3.type = 1;
    	columndefineMap.put(columnDefine3.name, columnDefine3);
    	ColumnDefine columnDefine5 = new ColumnDefine();
    	columnDefine5.name = "FIELD05";
    	columnDefine5.type = 1;
    	columndefineMap.put(columnDefine5.name, columnDefine5);
    	dataSet.columnDefineMap = columndefineMap;
    	Map<String, DataSet> dataSetMap = new HashMap<String, DataSet>();
    	dataSetMap.put(dataSet.name, dataSet);
    	//queryManagerImpl.trimCellData(dataSetMap);
    	assertEquals("002", dataSetMap.get("data").cellDataMap.get("FIELD031").value);
    }
    
   /* @Test
    public void trimCellAndRow(){
    	QueryManagerImpl queryManagerImpl = new QueryManagerImpl();
    	
    	queryManagerImpl.trimCellAndRow(sheet, dataVertical);
    }*/
}
