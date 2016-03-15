package com.huge.webapp.action;

import org.junit.Test;

public class PeriodActionTest
    extends BaseActionTestCase {
    /*    private PeriodAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new PeriodAction();
     PeriodManager periodManager = (PeriodManager) applicationContext.getBean("periodManager");
     action.setPeriodManager(periodManager);

     // add a test period to the database
     Period period = new Period();

     // enter all required fields
     period.setCdpClodedFlag(Boolean.FALSE);
     period.setCmonth("Ps");
     period.setCpClosedFlag(Boolean.FALSE);
     period.setCurrentFlag(Boolean.FALSE);
     period.setCyear("ClSa");
     period.setEndDate(new java.util.Date());
     period.setPeriodCode("" + Math.random());
     period.setStartDate(new java.util.Date());

     periodManager.save(period);
     }

     @Test
     public void testGetAllPeriods() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getPeriods().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getPeriods().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setPeriodId(-1L);
     assertNull(action.getPeriod());
     assertEquals("success", action.edit());
     assertNotNull(action.getPeriod());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setPeriodId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getPeriod());

     Period period = action.getPeriod();
     // update required fields
     period.setCdpClodedFlag(Boolean.FALSE);
     period.setCmonth("Rd");
     period.setCpClosedFlag(Boolean.FALSE);
     period.setCurrentFlag(Boolean.FALSE);
     period.setCyear("VvFd");
     period.setEndDate(new java.util.Date());
     period.setPeriodCode("UcRtCv");
     period.setStartDate(new java.util.Date());

     action.setPeriod(period);

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
     Period period = new Period();
     period.setPeriodId(-2L);
     action.setPeriod(period);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "PeriodActionTest" );
    }
}