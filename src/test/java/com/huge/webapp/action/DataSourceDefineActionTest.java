package com.huge.webapp.action;

import org.junit.Test;

public class DataSourceDefineActionTest
    extends BaseActionTestCase {
    /*    private DataSourceDefineAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new DataSourceDefineAction();
     DataSourceDefineManager dataSourceDefineManager = (DataSourceDefineManager) applicationContext.getBean("dataSourceDefineManager");
     action.setDataSourceDefineManager(dataSourceDefineManager);

     // add a test dataSourceDefine to the database
     DataSourceDefine dataSourceDefine = new DataSourceDefine();

     // enter all required fields
     dataSourceDefine.setDataSourceName("" + Math.random());

     dataSourceDefineManager.save(dataSourceDefine);
     }

     @Test
     public void testGetAllDataSourceDefines() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getDataSourceDefines().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getDataSourceDefines().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setDataSourceDefineId(-1L);
     assertNull(action.getDataSourceDefine());
     assertEquals("success", action.edit());
     assertNotNull(action.getDataSourceDefine());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setDataSourceDefineId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getDataSourceDefine());

     DataSourceDefine dataSourceDefine = action.getDataSourceDefine();
     // update required fields
     dataSourceDefine.setDataSourceName("LeLqKtDxQjIxMhGdKrUjUqNwArNvKnZbYyZaUaLzDsQgTxTdMz");

     action.setDataSourceDefine(dataSourceDefine);

     assertEquals("input", action.save());
     assertFalse(action.hasActionErrors());
     assertFalse(action.hasFieldErrors());
     assertNotNull(request.getSession().getAttribute("messages"));
     }

     @Test
     public void testRemove() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setDelete("");
     DataSourceDefine dataSourceDefine = new DataSourceDefine();
     dataSourceDefine.setDataSourceDefineId(-2L);
     action.setDataSourceDefine(dataSourceDefine);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "DataSourceDefineActionTest" );
    }
}