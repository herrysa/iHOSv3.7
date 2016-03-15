package com.huge.ihos.formula.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class AllotPrincipleActionTest
    extends BaseActionTestCase {
    /*    private AllotPrincipleAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new AllotPrincipleAction();
     AllotPrincipleManager allotPrincipleManager = (AllotPrincipleManager) applicationContext.getBean("allotPrincipleManager");
     action.setAllotPrincipleManager(allotPrincipleManager);

     // add a test allotPrinciple to the database
     AllotPrinciple allotPrinciple = new AllotPrinciple();

     // enter all required fields
     allotPrinciple.setAllotPrincipleName("RfZoCrAqNmKrBmJxTqXgHjOyCjOnIdVyBgUqCdMiGeJmLtYeYeTcUkCjDkGiXqGlBvInUmQxLaBpWbAyEdOtHyPxKwNlXiXyZbRq");
     allotPrinciple.setDisabled(Boolean.FALSE);
     allotPrinciple.setParam1("GjMzGjUsOpScWxCrTeAqLrNmRpReIzDeHtHoLbWzRmBfLgXfIpDcJmUtHjYjMuYeHzMfYuWmPzPeAsDzKiWlOuTwDiAiFzEpWgZg");
     allotPrinciple.setParamed(Boolean.FALSE);

     allotPrincipleManager.save(allotPrinciple);
     }

     @Test
     public void testGetAllAllotPrinciples() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getAllotPrinciples().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getAllotPrinciples().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setAllotPrincipleId(-1L);
     assertNull(action.getAllotPrinciple());
     assertEquals("success", action.edit());
     assertNotNull(action.getAllotPrinciple());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setAllotPrincipleId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getAllotPrinciple());

     AllotPrinciple allotPrinciple = action.getAllotPrinciple();
     // update required fields
     allotPrinciple.setAllotPrincipleName("MgJbMxZwYzQuOlMrJmDnHmGzWyCyKaPbSxLcCcGjHkItAtHiOmFsVvDwEuKoHxWaLsWbPnZwSbTiCtMgKvOcOmSbEiEcVuXgCiIs");
     allotPrinciple.setDisabled(Boolean.FALSE);
     allotPrinciple.setParam1("EfXsKvWfFtFsJvTpUuHlJkOeAaTzMjWzItPqNwEyXhBtBqWfRbJaFkEfZdXxTbTaTyRgTgByCaMsIuKeKzRmTdOtGmUzTeMjGoZx");
     allotPrinciple.setParamed(Boolean.FALSE);

     action.setAllotPrinciple(allotPrinciple);

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
     AllotPrinciple allotPrinciple = new AllotPrinciple();
     allotPrinciple.setAllotPrincipleId(-2L);
     action.setAllotPrinciple(allotPrinciple);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "AllotPrincipleActionTest" );
    }
}