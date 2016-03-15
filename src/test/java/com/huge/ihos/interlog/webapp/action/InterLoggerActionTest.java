package com.huge.ihos.interlog.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class InterLoggerActionTest
    extends BaseActionTestCase {
    /*    private InterLoggerAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new InterLoggerAction();
     InterLoggerManager interLoggerManager = (InterLoggerManager) applicationContext.getBean("interLoggerManager");
     action.setInterLoggerManager(interLoggerManager);

     // add a test interLogger to the database
     InterLogger interLogger = new InterLogger();

     // enter all required fields
     interLogger.setLogDateTime(new java.util.Date());
     interLogger.setLogFrom("EcPvMhSfCrDgFnWnGxLvZzWiEoZvRhRjTqHzLqIeIiMwAcWlAr");
     interLogger.setLogMsg("SmBrBdOzJfAlWqStDyIjVqSkTqQwHmKyMlNkTePpQtZcPgYzBt");
     interLogger.setTaskInterId("HvOcVaCjVvIsKdMoAvRcUoOaMpBzVrZwRwAeJeOcJlKlHvTlSw");

     interLoggerManager.save(interLogger);
     }

     @Test
     public void testGetAllInterLoggers() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getInterLoggers().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getInterLoggers().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setLogId(-1L);
     assertNull(action.getInterLogger());
     assertEquals("success", action.edit());
     assertNotNull(action.getInterLogger());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setLogId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getInterLogger());

     InterLogger interLogger = action.getInterLogger();
     // update required fields
     interLogger.setLogDateTime(new java.util.Date());
     interLogger.setLogFrom("YwKpKfSsLpThMoPtMmLpOpKjTtSgIjAuYuTdCnQpWuDySzYqZt");
     interLogger.setLogMsg("XdClPfPaGxPcLwLrQjAtHuLuAjSdGgNgZuYxEyIcZrHdZwYjBy");
     interLogger.setTaskInterId("RxXmVlZwRfFoSkZtBwEfDhLyHjJjEpHrBkLeRsDuUjFeFsQaPw");

     action.setInterLogger(interLogger);

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
     InterLogger interLogger = new InterLogger();
     interLogger.setLogId(-2L);
     action.setInterLogger(interLogger);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "InterLoggerActionTest" );
    }
}