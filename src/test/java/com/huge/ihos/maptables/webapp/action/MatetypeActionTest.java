package com.huge.ihos.maptables.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class MatetypeActionTest
    extends BaseActionTestCase {
    /*    private MatetypeAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new MatetypeAction();
     MatetypeManager matetypeManager = (MatetypeManager) applicationContext.getBean("matetypeManager");
     action.setMatetypeManager(matetypeManager);

     // add a test matetype to the database
     Matetype matetype = new Matetype();

     // enter all required fields
     matetype.setMateType("QzZwVyAqGvEcJhElEeVqVdCdLbOzDlQjUtDuYiCqKdDvCuGcYtBmFzBwHcIx");

     matetypeManager.save(matetype);
     }

     @Test
     public void testGetAllMatetypes() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getMatetypes().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getMatetypes().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setMateTypeId(-1L);
     assertNull(action.getMatetype());
     assertEquals("success", action.edit());
     assertNotNull(action.getMatetype());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setMateTypeId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getMatetype());

     Matetype matetype = action.getMatetype();
     // update required fields
     matetype.setMateType("MpKqLcIcKgOlIhWcCdZyIwYqJvToXpBaYsMxPmUnJnDzJgWvFfMmVnQpFqZv");

     action.setMatetype(matetype);

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
     Matetype matetype = new Matetype();
     matetype.setMateTypeId(-2L);
     action.setMatetype(matetype);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "MatetypeActionTest" );
    }
}