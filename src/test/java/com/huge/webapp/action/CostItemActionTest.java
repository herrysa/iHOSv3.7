package com.huge.webapp.action;

import org.junit.Test;

public class CostItemActionTest
    extends BaseActionTestCase {
    /*    private CostItemAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new CostItemAction();
     CostItemManager costItemManager = (CostItemManager) applicationContext.getBean("costItemManager");
     action.setCostItemManager(costItemManager);

     // add a test costItem to the database
     CostItem costItem = new CostItem();

     // enter all required fields
     costItem.setBehaviour("EzQkKhOyLo");
     costItem.setClevel(1848344721);
     costItem.setControllable(Boolean.FALSE);
     costItem.setCostDesc("CiHxYwDrHiQwXlKqKqBxZaItKgKxWuCiXrDvBiOfKeXsJmWpAlKqXwCiUkWoUbWhIgQrQjEdQgGcRjFpHoXoPbCqHhMvUdTzAwJa");
     costItem.setCostItemName("DxIvVtWsBmFuGhUrQmZhIzMwNvLgFl");
     costItem.setDisabled(Boolean.FALSE);
     costItem.setLeaf(Boolean.FALSE);

     costItemManager.save(costItem);
     }

     @Test
     public void testGetAllCostItems() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getCostItems().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getCostItems().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setCostItemId(-1L);
     assertNull(action.getCostItem());
     assertEquals("success", action.edit());
     assertNotNull(action.getCostItem());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setCostItemId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getCostItem());

     CostItem costItem = action.getCostItem();
     // update required fields
     costItem.setBehaviour("PtTiCePzGv");
     costItem.setClevel(134231448);
     costItem.setControllable(Boolean.FALSE);
     costItem.setCostDesc("EuSlPjTlXuAyLiOdQdZrQdIqYvCsNbFcAySrXbSmObHiAsQvDoGhXoQfQlRiShJuYvOcDlPvZwPcVuYiIiCmNeVzWoKdNoBrVcEl");
     costItem.setCostItemName("CvMcDjUnUmJwCkNmEuOkYhQrJtDkFq");
     costItem.setDisabled(Boolean.FALSE);
     costItem.setLeaf(Boolean.FALSE);

     action.setCostItem(costItem);

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
     CostItem costItem = new CostItem();
     costItem.setCostItemId(-2L);
     action.setCostItem(costItem);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "CostItemActionTest" );
    }
}