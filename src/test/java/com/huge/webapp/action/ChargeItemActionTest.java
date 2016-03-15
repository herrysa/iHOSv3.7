package com.huge.webapp.action;

import org.junit.Test;

public class ChargeItemActionTest
    extends BaseActionTestCase {
    /*    private ChargeItemAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new ChargeItemAction();
     ChargeItemManager chargeItemManager = (ChargeItemManager) applicationContext.getBean("chargeItemManager");
     action.setChargeItemManager(chargeItemManager);

     // add a test chargeItem to the database
     ChargeItem chargeItem = new ChargeItem();

     // enter all required fields
     chargeItem.setChargeItemId("TqJfEuBfCoWmDlQgDfBz");
     chargeItem.setDisabled(Boolean.FALSE);

     chargeItemManager.save(chargeItem);
     }

     @Test
     public void testGetAllChargeItems() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getChargeItems().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getChargeItems().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setChargeItemNo(-1L);
     assertNull(action.getChargeItem());
     assertEquals("success", action.edit());
     assertNotNull(action.getChargeItem());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setChargeItemNo(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getChargeItem());

     ChargeItem chargeItem = action.getChargeItem();
     // update required fields
     chargeItem.setChargeItemId("MeLaSyHvSuJgWxMfMmFd");
     chargeItem.setDisabled(Boolean.FALSE);

     action.setChargeItem(chargeItem);

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
     ChargeItem chargeItem = new ChargeItem();
     chargeItem.setChargeItemNo(-2L);
     action.setChargeItem(chargeItem);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "ChargeItemActionTest" );
    }
}