package com.huge.ihos.search.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class SearchItemActionTest
    extends BaseActionTestCase {
    /*    private SearchItemAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new SearchItemAction();
     SearchItemManager searchItemManager = (SearchItemManager) applicationContext.getBean("searchItemManager");
     action.setSearchItemManager(searchItemManager);

     // add a test searchItem to the database
     SearchItem searchItem = new SearchItem();

     // enter all required fields
     searchItem.setSearchFlag(Boolean.FALSE);

     searchItemManager.save(searchItem);
     }

     @Test
     public void testGetAllSearchItems() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getSearchItems().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getSearchItems().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setSearchItemId(-1L);
     assertNull(action.getSearchItem());
     assertEquals("success", action.edit());
     assertNotNull(action.getSearchItem());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setSearchItemId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getSearchItem());

     SearchItem searchItem = action.getSearchItem();
     // update required fields
     searchItem.setSearchFlag(Boolean.FALSE);

     action.setSearchItem(searchItem);

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
     SearchItem searchItem = new SearchItem();
     searchItem.setSearchItemId(-2L);
     action.setSearchItem(searchItem);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "SearchItemActionTest" );
    }
}