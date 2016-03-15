package com.huge.ihos.datacollection.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class DataCollectionTaskStepExecuteActionTest
    extends BaseActionTestCase {
    /*    private DataCollectionTaskStepExecuteAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new DataCollectionTaskStepExecuteAction();
     DataCollectionTaskStepExecuteManager dataCollectionTaskStepExecuteManager = (DataCollectionTaskStepExecuteManager) applicationContext.getBean("dataCollectionTaskStepExecuteManager");
     action.setDataCollectionTaskStepExecuteManager(dataCollectionTaskStepExecuteManager);

     // add a test dataCollectionTaskStepExecute to the database
     DataCollectionTaskStepExecute dataCollectionTaskStepExecute = new DataCollectionTaskStepExecute();

     // enter all required fields

     dataCollectionTaskStepExecuteManager.save(dataCollectionTaskStepExecute);
     }

     @Test
     public void testGetAllDataCollectionTaskStepExecutes() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getDataCollectionTaskStepExecutes().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getDataCollectionTaskStepExecutes().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setStepExecuteId(-1L);
     assertNull(action.getDataCollectionTaskStepExecute());
     assertEquals("success", action.edit());
     assertNotNull(action.getDataCollectionTaskStepExecute());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setStepExecuteId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getDataCollectionTaskStepExecute());

     DataCollectionTaskStepExecute dataCollectionTaskStepExecute = action.getDataCollectionTaskStepExecute();
     // update required fields

     action.setDataCollectionTaskStepExecute(dataCollectionTaskStepExecute);

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
     DataCollectionTaskStepExecute dataCollectionTaskStepExecute = new DataCollectionTaskStepExecute();
     dataCollectionTaskStepExecute.setStepExecuteId(-2L);
     action.setDataCollectionTaskStepExecute(dataCollectionTaskStepExecute);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "DataCollectionTaskStepExecuteActionTest" );
    }
}