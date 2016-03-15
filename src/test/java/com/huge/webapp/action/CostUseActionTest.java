package com.huge.webapp.action;

import org.junit.Test;

public class CostUseActionTest
    extends BaseActionTestCase {
    /*    private CostUseAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new CostUseAction();
     CostUseManager costUseManager = (CostUseManager) applicationContext.getBean("costUseManager");
     action.setCostUseManager(costUseManager);

     // add a test costUse to the database
     CostUse costUse = new CostUse();

     // enter all required fields
     costUse.setCostUseName("YhSkFzTtHvMeEoWpJyZg");
     costUse.setDisabled(Boolean.FALSE);

     costUseManager.save(costUse);
     }

     @Test
     public void testGetAllCostUses() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getCostUses().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getCostUses().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setCostUseId(-1L);
     assertNull(action.getCostUse());
     assertEquals("success", action.edit());
     assertNotNull(action.getCostUse());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setCostUseId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getCostUse());

     CostUse costUse = action.getCostUse();
     // update required fields
     costUse.setCostUseName("DvJhCtDwHoWwJoAdUgVk");
     costUse.setDisabled(Boolean.FALSE);

     action.setCostUse(costUse);

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
     CostUse costUse = new CostUse();
     costUse.setCostUseId(-2L);
     action.setCostUse(costUse);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "CostUseActionTest" );
    }
}