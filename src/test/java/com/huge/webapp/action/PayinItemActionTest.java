package com.huge.webapp.action;

import org.junit.Test;

public class PayinItemActionTest
    extends BaseActionTestCase {
    /*    private PayinItemAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new PayinItemAction();
     PayinItemManager payinItemManager = (PayinItemManager) applicationContext.getBean("payinItemManager");
     action.setPayinItemManager(payinItemManager);

     // add a test payinItem to the database
     PayinItem payinItem = new PayinItem();

     // enter all required fields
     payinItem.setDisabled(Boolean.FALSE);
     payinItem.setPayinItemName("PjFbSgTwIkOwEvHqTfPaFeFlBqKuQm");

     payinItemManager.save(payinItem);
     }

     @Test
     public void testGetAllPayinItems() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getPayinItems().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getPayinItems().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setPayinItemId(-1L);
     assertNull(action.getPayinItem());
     assertEquals("success", action.edit());
     assertNotNull(action.getPayinItem());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setPayinItemId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getPayinItem());

     PayinItem payinItem = action.getPayinItem();
     // update required fields
     payinItem.setDisabled(Boolean.FALSE);
     payinItem.setPayinItemName("JcTpHqViWaFcLyQkMxToYaZsAoDeUa");

     action.setPayinItem(payinItem);

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
     PayinItem payinItem = new PayinItem();
     payinItem.setPayinItemId(-2L);
     action.setPayinItem(payinItem);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "PayinItemActionTest" );
    }
}