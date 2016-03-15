package com.huge.ihos.formula.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class AllotSetActionTest
    extends BaseActionTestCase {
    /*    private AllotSetAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new AllotSetAction();
     AllotSetManager allotSetManager = (AllotSetManager) applicationContext.getBean("allotSetManager");
     action.setAllotSetManager(allotSetManager);

     // add a test allotSet to the database
     AllotSet allotSet = new AllotSet();

     // enter all required fields
     allotSet.setAllotSetName("NcJcXwXhNjMqIwQzEgXdRvReTiZoWaMfRrFoCmTyZcKgWyKvCeLeWgNfKcOuOnAsLnOhXeWhPtOgUkMxQwQpMsThTpUxNvFySmCf");
     allotSet.setDisabled(Boolean.FALSE);

     allotSetManager.save(allotSet);
     }

     @Test
     public void testGetAllAllotSets() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getAllotSets().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getAllotSets().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setAllotSetId(-1L);
     assertNull(action.getAllotSet());
     assertEquals("success", action.edit());
     assertNotNull(action.getAllotSet());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setAllotSetId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getAllotSet());

     AllotSet allotSet = action.getAllotSet();
     // update required fields
     allotSet.setAllotSetName("GtQiWxEfYbSjFsLeWtEbQfFvEvTfQoNtJrUoDbWkImZrWmEiMcMqSxTtAtOvYuIhJfNgHcNlPnHkVgMeFaFrYfIuNbDqFbNyVjVl");
     allotSet.setDisabled(Boolean.FALSE);

     action.setAllotSet(allotSet);

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
     AllotSet allotSet = new AllotSet();
     allotSet.setAllotSetId(-2L);
     action.setAllotSet(allotSet);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "AllotSetActionTest" );
    }
}