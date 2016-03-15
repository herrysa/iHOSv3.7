package com.huge.ihos.maptables.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class AcctcostmapActionTest
    extends BaseActionTestCase {
    /*    private AcctcostmapAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new AcctcostmapAction();
     AcctcostmapManager acctcostmapManager = (AcctcostmapManager) applicationContext.getBean("acctcostmapManager");
     action.setAcctcostmapManager(acctcostmapManager);

     // add a test acctcostmap to the database
     Acctcostmap acctcostmap = new Acctcostmap();

     // enter all required fields

     acctcostmapManager.save(acctcostmap);
     }

     @Test
     public void testGetAllAcctcostmaps() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getAcctcostmaps().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getAcctcostmaps().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setAcctcode(-1L);
     assertNull(action.getAcctcostmap());
     assertEquals("success", action.edit());
     assertNotNull(action.getAcctcostmap());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setAcctcode(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getAcctcostmap());

     Acctcostmap acctcostmap = action.getAcctcostmap();
     // update required fields

     action.setAcctcostmap(acctcostmap);

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
     Acctcostmap acctcostmap = new Acctcostmap();
     acctcostmap.setAcctcode(-2L);
     action.setAcctcostmap(acctcostmap);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "AcctcostmapActionTest" );
    }
}