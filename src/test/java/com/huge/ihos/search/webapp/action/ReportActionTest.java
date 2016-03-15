package com.huge.ihos.search.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class ReportActionTest
    extends BaseActionTestCase {
    /*    private ReportAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new ReportAction();
     ReportManager reportManager = (ReportManager) applicationContext.getBean("reportManager");
     action.setReportManager(reportManager);

     // add a test report to the database
     Report report = new Report();

     // enter all required fields
     report.setGroupName("ChClXvNxFvUgNwDbErArVxYcRiDsUb");
     report.setReportName("SsOhIbTjHaCwOwWdZoDcQpNmDfDsOdViBbMuAkHgKyLySwByWi");

     reportManager.save(report);
     }

     @Test
     public void testGetAllReports() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getReports().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getReports().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setReportId(-1L);
     assertNull(action.getReport());
     assertEquals("success", action.edit());
     assertNotNull(action.getReport());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setReportId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getReport());

     Report report = action.getReport();
     // update required fields
     report.setGroupName("RgMyRqEzOuJgSgQfKrVxQyQwCrBoKh");
     report.setReportName("EaIsWsHiWxUzPsEdNpWsEjVaQmQhKvHoVpXsSsZgFlVoXgPxMm");

     action.setReport(report);

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
     Report report = new Report();
     report.setReportId(-2L);
     action.setReport(report);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "ReportActionTest" );
    }
}