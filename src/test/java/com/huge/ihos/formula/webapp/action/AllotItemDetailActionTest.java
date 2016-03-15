package com.huge.ihos.formula.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class AllotItemDetailActionTest
    extends BaseActionTestCase {
    /*    private AllotItemDetailAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new AllotItemDetailAction();
     AllotItemDetailManager allotItemDetailManager = (AllotItemDetailManager) applicationContext.getBean("allotItemDetailManager");
     action.setAllotItemDetailManager(allotItemDetailManager);

     // add a test allotItemDetail to the database
     AllotItemDetail allotItemDetail = new AllotItemDetail();

     // enter all required fields
     allotItemDetail.setCostRatio("PhSkSfUnZqOhNlOmSsXlIzRqOlFcPoNpXyRjVbKmPiTrOuPlAfZyYbMjLoYfHhVlWgOrZnNhRwPrNuKjIbHqWaEuRzEkNtGhDpGmNcHjSjXkJyKgDkThCbVcFjLxLnCmWuBfWcZgJoDzHfHxAgDwMeTwWnUmGpLoXtWzKyFgBxJrXzJoElQiItZoHpQlIkGgFyEcUxFjPjCeQqFkVdTsCcHbNxXgXtOzTmRuCyMmKxWdNdRtJeEsLuPiSoNaVzY");

     allotItemDetailManager.save(allotItemDetail);
     }

     @Test
     public void testGetAllAllotItemDetails() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getAllotItemDetails().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getAllotItemDetails().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setAllotItemDetailId(-1L);
     assertNull(action.getAllotItemDetail());
     assertEquals("success", action.edit());
     assertNotNull(action.getAllotItemDetail());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setAllotItemDetailId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getAllotItemDetail());

     AllotItemDetail allotItemDetail = action.getAllotItemDetail();
     // update required fields
     allotItemDetail.setCostRatio("MiHuHqQpSvOhDfAxDdDlEnIfPyYvUuUoMzNvBuTjFnThScKyWgDeApHsQnQuJmSzCkEcIiQcAlVsPuQhUrLcLqKwHhOtDlJoXsGfUnGjGxRzLxQuGqTbGmBzGnJeKnRzTlSbUaEsLsTeOxEyIgEjObBeEoBbVlIcPuCoKsWwKzOeAmRcIkHiPzOhZiTyYqSvOaDwCuOaAqUjAvWjNvLdZfItHmByMjWoHlPyLjRkLuJgLpLjDoHxAfGxJgFgGzS");

     action.setAllotItemDetail(allotItemDetail);

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
     AllotItemDetail allotItemDetail = new AllotItemDetail();
     allotItemDetail.setAllotItemDetailId(-2L);
     action.setAllotItemDetail(allotItemDetail);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "AllotItemDetailActionTest" );
    }
}