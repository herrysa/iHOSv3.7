package com.huge.webapp.action;

import org.junit.Test;

public class DataSourceTypeActionTest
    extends BaseActionTestCase {
    /*    private DataSourceTypeAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new DataSourceTypeAction();
     DataSourceTypeManager dataSourceTypeManager = (DataSourceTypeManager) applicationContext.getBean("dataSourceTypeManager");
     action.setDataSourceTypeManager(dataSourceTypeManager);

     // add a test dataSourceType to the database
     DataSourceType dataSourceType = new DataSourceType();

     // enter all required fields
     dataSourceType.setDataSourceTypeName("" + Math.random());
     dataSourceType.setIsNeedFile(Boolean.FALSE);

     dataSourceTypeManager.save(dataSourceType);
     }

     @Test
     public void testGetAllDataSourceTypes() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getDataSourceTypes().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getDataSourceTypes().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setDataSourceTypeId(-1L);
     assertNull(action.getDataSourceType());
     assertEquals("success", action.edit());
     assertNotNull(action.getDataSourceType());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setDataSourceTypeId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getDataSourceType());

     DataSourceType dataSourceType = action.getDataSourceType();
     // update required fields
     dataSourceType.setDataSourceTypeName("FyTwZoKcOsFeBkNxMtFzNnIyLfHeUyJcXmJnPcPfOlKzZjXaKi");
     dataSourceType.setIsNeedFile(Boolean.FALSE);

     action.setDataSourceType(dataSourceType);

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
     DataSourceType dataSourceType = new DataSourceType();
     dataSourceType.setDataSourceTypeId(-2L);
     action.setDataSourceType(dataSourceType);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "DataSourceTypeActionTest" );
    }
}