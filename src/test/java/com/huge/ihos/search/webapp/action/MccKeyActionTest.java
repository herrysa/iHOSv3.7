package com.huge.ihos.search.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class MccKeyActionTest
    extends BaseActionTestCase {
    /*    private MccKeyAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new MccKeyAction();
     MccKeyManager mccKeyManager = (MccKeyManager) applicationContext.getBean("mccKeyManager");
     action.setMccKeyManager(mccKeyManager);

     // add a test mccKey to the database
     MccKey mccKey = new MccKey();

     // enter all required fields

     mccKeyManager.save(mccKey);
     }

     @Test
     public void testGetAllMccKeys() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getMccKeys().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getMccKeys().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setMccKeyId(-1L);
     assertNull(action.getMccKey());
     assertEquals("success", action.edit());
     assertNotNull(action.getMccKey());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setMccKeyId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getMccKey());

     MccKey mccKey = action.getMccKey();
     // update required fields

     action.setMccKey(mccKey);

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
     MccKey mccKey = new MccKey();
     mccKey.setMccKeyId(-2L);
     action.setMccKey(mccKey);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "MccKeyActionTest" );
    }
}