package com.huge.ihos.search.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class MccKeyDetailActionTest
    extends BaseActionTestCase {
    /*    private MccKeyDetailAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new MccKeyDetailAction();
     MccKeyDetailManager mccKeyDetailManager = (MccKeyDetailManager) applicationContext.getBean("mccKeyDetailManager");
     action.setMccKeyDetailManager(mccKeyDetailManager);

     // add a test mccKeyDetail to the database
     MccKeyDetail mccKeyDetail = new MccKeyDetail();

     // enter all required fields

     mccKeyDetailManager.save(mccKeyDetail);
     }

     @Test
     public void testGetAllMccKeyDetails() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getMccKeyDetails().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getMccKeyDetails().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setMccKeyDetailId(-1L);
     assertNull(action.getMccKeyDetail());
     assertEquals("success", action.edit());
     assertNotNull(action.getMccKeyDetail());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setMccKeyDetailId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getMccKeyDetail());

     MccKeyDetail mccKeyDetail = action.getMccKeyDetail();
     // update required fields

     action.setMccKeyDetail(mccKeyDetail);

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
     MccKeyDetail mccKeyDetail = new MccKeyDetail();
     mccKeyDetail.setMccKeyDetailId(-2L);
     action.setMccKeyDetail(mccKeyDetail);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "MccKeyDetailActionTest" );
    }
}