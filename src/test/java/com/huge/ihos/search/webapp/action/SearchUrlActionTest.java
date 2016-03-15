package com.huge.ihos.search.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class SearchUrlActionTest
    extends BaseActionTestCase {
    /*    private SearchUrlAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new SearchUrlAction();
     SearchUrlManager searchUrlManager = (SearchUrlManager) applicationContext.getBean("searchUrlManager");
     action.setSearchUrlManager(searchUrlManager);

     // add a test searchUrl to the database
     SearchUrl searchUrl = new SearchUrl();

     // enter all required fields
     searchUrl.setNullFlag(Boolean.FALSE);
     searchUrl.setTitle("UjNtBvDuThSxQwNuYtSdWfPgUjBgUgTxBaBvKxMsDlDyUhFtMz");

     searchUrlManager.save(searchUrl);
     }

     @Test
     public void testGetAllSearchUrls() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getSearchUrls().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getSearchUrls().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setSearchUrlId(-1L);
     assertNull(action.getSearchUrl());
     assertEquals("success", action.edit());
     assertNotNull(action.getSearchUrl());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setSearchUrlId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getSearchUrl());

     SearchUrl searchUrl = action.getSearchUrl();
     // update required fields
     searchUrl.setNullFlag(Boolean.FALSE);
     searchUrl.setTitle("OtYaLePlUoLpTdMrUiJzNuRpZqSlOzCoJuMaYrIlVeBqJcDrZi");

     action.setSearchUrl(searchUrl);

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
     SearchUrl searchUrl = new SearchUrl();
     searchUrl.setSearchUrlId(-2L);
     action.setSearchUrl(searchUrl);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "SearchUrlActionTest" );
    }
}