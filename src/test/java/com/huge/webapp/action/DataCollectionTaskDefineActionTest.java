package com.huge.webapp.action;

import org.junit.Test;

public class DataCollectionTaskDefineActionTest
    extends BaseActionTestCase {
    /*    private DataCollectionTaskDefineAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new DataCollectionTaskDefineAction();
     DataCollectionTaskDefineManager dataCollectionTaskDefineManager = (DataCollectionTaskDefineManager) applicationContext.getBean("dataCollectionTaskDefineManager");
     action.setDataCollectionTaskDefineManager(dataCollectionTaskDefineManager);

     // add a test dataCollectionTaskDefine to the database
     DataCollectionTaskDefine dataCollectionTaskDefine = new DataCollectionTaskDefine();

     // enter all required fields
     dataCollectionTaskDefine.setIsUsed(Boolean.FALSE);
     dataCollectionTaskDefine.setTargetTableName("KmBwGzWaLxTaFmJlYvClSrReSfLaQsEsArAgEyQaYwVlDrCfUr");
     dataCollectionTaskDefine.setTemporaryTableName("TiVjRaWbLuFuJwScTsUfBtDxMkTqUaMpDpCeMrNiWcJyFiMaLy");

     dataCollectionTaskDefineManager.save(dataCollectionTaskDefine);
     }

     @Test
     public void testGetAllDataCollectionTaskDefines() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getDataCollectionTaskDefines().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getDataCollectionTaskDefines().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setDataCollectionTaskDefineId(-1L);
     assertNull(action.getDataCollectionTaskDefine());
     assertEquals("success", action.edit());
     assertNotNull(action.getDataCollectionTaskDefine());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setDataCollectionTaskDefineId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getDataCollectionTaskDefine());

     DataCollectionTaskDefine dataCollectionTaskDefine = action.getDataCollectionTaskDefine();
     // update required fields
     dataCollectionTaskDefine.setIsUsed(Boolean.FALSE);
     dataCollectionTaskDefine.setTargetTableName("GbLuUnMnCsPzPdWeHbPiEsBxDdIjXvGtCzUqDcYaOsIxHwQuGt");
     dataCollectionTaskDefine.setTemporaryTableName("WwEjUzUbLyYeJdGbEsRhCkOuMmEbWxHnSjOyOvOkMlSiDmGnGd");

     action.setDataCollectionTaskDefine(dataCollectionTaskDefine);

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
     DataCollectionTaskDefine dataCollectionTaskDefine = new DataCollectionTaskDefine();
     dataCollectionTaskDefine.setDataCollectionTaskDefineId(-2L);
     action.setDataCollectionTaskDefine(dataCollectionTaskDefine);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "DataCollectionTaskDefineActionTest" );
    }
}