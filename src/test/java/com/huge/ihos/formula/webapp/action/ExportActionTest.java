package com.huge.ihos.formula.webapp.action;

import junit.framework.Assert;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class ExportActionTest
    extends BaseActionTestCase {

    /*ExportAction ea;
    
    //@Test
    public void testExportExcel(){
    	ea = new ExportAction();
    	
    	QueryManager queryManager = (QueryManager) applicationContext.getBean("queryManager");
    	ea.setQueryManager(queryManager);
    	MockHttpServletRequest req = new MockHttpServletRequest();
    	
    	MockHttpServletResponse res = new MockHttpServletResponse();
    	ServletActionContext.setRequest(req);
    	ServletActionContext.setResponse(res);
    	ea.setExportSearchId("10000");
    	ea.exportSearchExcel();
    	
    }*/
    @Test
    public void dummyTest() {
        Assert.assertTrue( true );
    }
}
