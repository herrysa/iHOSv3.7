package com.huge.ihos.search.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class SearchLinkActionTest
    extends BaseActionTestCase {
    /*    private SearchLinkAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new SearchLinkAction();
     SearchLinkManager searchLinkManager = (SearchLinkManager) applicationContext.getBean("searchLinkManager");
     action.setSearchLinkManager(searchLinkManager);

     // add a test searchLink to the database
     SearchLink searchLink = new SearchLink();

     // enter all required fields
     searchLink.setLink("LvYdWoAeXpRuQpXfOyExTjDxBaOxUeUuPuNgGlRkKaGkEgGcTd");

     searchLinkManager.save(searchLink);
     }

     @Test
     public void testGetAllSearchLinks() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getSearchLinks().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getSearchLinks().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setSearchLinkId(-1L);
     assertNull(action.getSearchLink());
     assertEquals("success", action.edit());
     assertNotNull(action.getSearchLink());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setSearchLinkId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getSearchLink());

     SearchLink searchLink = action.getSearchLink();
     // update required fields
     searchLink.setLink("ByKqFqDzWkCtWgTdAmSjYmLdJbNzVdEsBiQvBqLrPzReAjMuHl");

     action.setSearchLink(searchLink);

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
     SearchLink searchLink = new SearchLink();
     searchLink.setSearchLinkId(-2L);
     action.setSearchLink(searchLink);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "SearchLinkActionTest" );
    }
}