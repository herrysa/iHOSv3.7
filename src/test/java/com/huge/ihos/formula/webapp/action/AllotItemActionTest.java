package com.huge.ihos.formula.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class AllotItemActionTest
    extends BaseActionTestCase {
    /*    private AllotItemAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new AllotItemAction();
     AllotItemManager allotItemManager = (AllotItemManager) applicationContext.getBean("allotItemManager");
     action.setAllotItemManager(allotItemManager);

     // add a test allotItem to the database
     AllotItem allotItem = new AllotItem();

     // enter all required fields
     allotItem.setAllotCost("XbGdJiCzDyBkOkFpYiFxQgUnUsZzGvJrRrOfUlRxXmBuSsYtLnOwLeRrLeBdGkUnBwEkKjCqYuGcWbLgYtStRyBtLgNxXeRxSpUfPfGlWqXnDfKnTlMdEcAlXfXlLlNsByUkAiYaBvXkBwTuGeGcGvOxKzHlEkYrVjTrZpQnSmYyLnYmCfPdQuOyNpHwOlByDgXlDxJaMsQcUiJmYxAaXlSnEwIzRyDkOnHcIzUeCiYwPxHbTuZbQyVrOhHuQdG");
     allotItem.setCheckPeriod("TjRqIu");

     allotItemManager.save(allotItem);
     }

     @Test
     public void testGetAllAllotItems() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getAllotItems().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getAllotItems().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setAllotItemId(-1L);
     assertNull(action.getAllotItem());
     assertEquals("success", action.edit());
     assertNotNull(action.getAllotItem());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setAllotItemId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getAllotItem());

     AllotItem allotItem = action.getAllotItem();
     // update required fields
     allotItem.setAllotCost("KuAgTuOcHpNiMfQvOmJdFiLoMsLiCyPoSbHuPiQiIdVcCdWfPtWaGxEqXdBmJcGaCzTeYuOgGgQaXrTeNdGmYgFnPfZhDpLaDySfFdAgKlHwEhGtLcFwQtEtXlAzZwOwPnCzHtFvMjUvDhVvNlXlTySwRlTiSrXuBkPyRhUsTbVcOeHxDeNzCmCeLeQiXpKpZfXlEzNvPnIzEqPoTmPdPnLlKmUiKbAnWqNjNbQaLhEdPjJtPeThDiZgEcQqQkT");
     allotItem.setCheckPeriod("NcVqEo");

     action.setAllotItem(allotItem);

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
     AllotItem allotItem = new AllotItem();
     allotItem.setAllotItemId(-2L);
     action.setAllotItem(allotItem);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "AllotItemActionTest" );
    }
}