package com.huge.webapp.action;

import org.junit.Test;

public class SourcecostActionTest
    extends BaseActionTestCase {
    /*    private SourcecostAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new SourcecostAction();
     SourcecostManager sourcecostManager = (SourcecostManager) applicationContext.getBean("sourcecostManager");
     action.setSourcecostManager(sourcecostManager);

     // add a test sourcecost to the database
     Sourcecost sourcecost = new Sourcecost();

     // enter all required fields
     sourcecost.setCheckPeriod("LzDnAj");
     sourcecost.setIfallot(Boolean.FALSE);

     sourcecostManager.save(sourcecost);
     }

     @Test
     public void testGetAllSourcecosts() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getSourcecosts().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getSourcecosts().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setSourceCostId(-1L);
     assertNull(action.getSourcecost());
     assertEquals("success", action.edit());
     assertNotNull(action.getSourcecost());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setSourceCostId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getSourcecost());

     Sourcecost sourcecost = action.getSourcecost();
     // update required fields
     sourcecost.setCheckPeriod("SbDtWm");
     sourcecost.setIfallot(Boolean.FALSE);

     action.setSourcecost(sourcecost);

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
     Sourcecost sourcecost = new Sourcecost();
     sourcecost.setSourceCostId(-2L);
     action.setSourcecost(sourcecost);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "SourcecostActionTest" );
    }
}