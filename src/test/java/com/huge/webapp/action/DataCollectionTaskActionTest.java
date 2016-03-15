package com.huge.webapp.action;

import org.junit.Test;

import com.huge.ihos.system.datacollection.webapp.action.DataCollectionTaskAction;

public class DataCollectionTaskActionTest
    extends BaseActionTestCase {
    private DataCollectionTaskAction action;

/*    @Autowired
    private CompassGps compassGps;*/

   /* @Before
    public void onSetUp() {
        super.onSetUp();
   //     compassGps.index();

        action = new DataCollectionTaskAction();
        DataCollectionTaskManager dataCollectionTaskManager = (DataCollectionTaskManager) applicationContext.getBean( "dataCollectionTaskManager" );
        action.setDataCollectionTaskManager( dataCollectionTaskManager );

        // add a test dataCollectionTask to the database
        DataCollectionTask dataCollectionTask = new DataCollectionTask();

        // enter all required fields
        dataCollectionTask.setStatus( "TsPqOwEqKkAbKhYqSiFe" );

        dataCollectionTaskManager.save( dataCollectionTask );
    }

    @Test
    public void testExecTask() {
        //assertEquals(action.execTask(), ActionSupport.SUCCESS);
    }*/

    //    @Test
    //    public void testGetAllDataCollectionTasks() throws Exception {
    //        assertEquals(action.list(), ActionSupport.SUCCESS);
    //        assertTrue(action.getDataCollectionTasks().size() >= 1);
    //    }
    //
    //    @Test
    //    public void testSearch() throws Exception {
    //        action.setQ("*");
    //        assertEquals(action.list(), ActionSupport.SUCCESS);
    //        assertEquals(4, action.getDataCollectionTasks().size());
    //    }
    //
    //    @Test
    //    public void testEdit() throws Exception {
    //        log.debug("testing edit...");
    //        action.setDataCollectionTaskId(-1L);
    //        assertNull(action.getDataCollectionTask());
    //        assertEquals("success", action.edit());
    //        assertNotNull(action.getDataCollectionTask());
    //        assertFalse(action.hasActionErrors());
    //    }
    //
    //    @Test
    //    public void testSave() throws Exception {
    //        MockHttpServletRequest request = new MockHttpServletRequest();
    //        ServletActionContext.setRequest(request);
    //        action.setDataCollectionTaskId(-1L);
    //        assertEquals("success", action.edit());
    //        assertNotNull(action.getDataCollectionTask());
    //
    //        DataCollectionTask dataCollectionTask = action.getDataCollectionTask();
    //        // update required fields
    //        dataCollectionTask.setStatus("TzCvNaFsOiUoKtMuRlXy");
    //
    //        action.setDataCollectionTask(dataCollectionTask);
    //
    //        assertEquals("input", action.save());
    //        assertFalse(action.hasActionErrors());
    //        assertFalse(action.hasFieldErrors());
    //        assertNotNull(request.getSession().getAttribute("messages"));
    //    }
    //
    //    @Test
    //    public void testRemove() throws Exception {
    //        MockHttpServletRequest request = new MockHttpServletRequest();
    //        ServletActionContext.setRequest(request);
    //        action.setDelete("");
    //        DataCollectionTask dataCollectionTask = new DataCollectionTask();
    //        dataCollectionTask.setDataCollectionTaskId(-2L);
    //        action.setDataCollectionTask(dataCollectionTask);
    //        assertEquals("success", action.delete());
    //        assertNotNull(request.getSession().getAttribute("messages"));
    //    }
    @Test
    public void testDummy() {
        this.log.debug( "DataCollectionTaskActionTest" );
    }
}