package com.huge.ihos.search.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class SearchOptionActionTest
    extends BaseActionTestCase {
    /*    private SearchOptionAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new SearchOptionAction();
     SearchOptionManager searchOptionManager = (SearchOptionManager) applicationContext.getBean("searchOptionManager");
     action.setSearchOptionManager(searchOptionManager);

     // add a test searchOption to the database
     SearchOption searchOption = new SearchOption();

     // enter all required fields
     searchOption.setFieldName("VpEhQaPpJaXcHfXiIaMwSrUqMkLuVz");
     searchOption.setPrintable(Boolean.FALSE);
     searchOption.setVisible(Boolean.FALSE);

     searchOptionManager.save(searchOption);
     }

     @Test
     public void testGetAllSearchOptions() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getSearchOptions().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getSearchOptions().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setSearchOptionId(-1L);
     assertNull(action.getSearchOption());
     assertEquals("success", action.edit());
     assertNotNull(action.getSearchOption());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setSearchOptionId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getSearchOption());

     SearchOption searchOption = action.getSearchOption();
     // update required fields
     searchOption.setFieldName("MhAuPyOoMpWhElBiDiQiUtNiRoBwSp");
     searchOption.setPrintable(Boolean.FALSE);
     searchOption.setVisible(Boolean.FALSE);

     action.setSearchOption(searchOption);

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
     SearchOption searchOption = new SearchOption();
     searchOption.setSearchOptionId(-2L);
     action.setSearchOption(searchOption);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "SearchOptionActionTest" );
    }
}