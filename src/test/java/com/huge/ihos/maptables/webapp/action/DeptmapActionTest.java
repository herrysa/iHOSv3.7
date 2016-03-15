package com.huge.ihos.maptables.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class DeptmapActionTest
    extends BaseActionTestCase {
    /*    private DeptmapAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new DeptmapAction();
     DeptmapManager deptmapManager = (DeptmapManager) applicationContext.getBean("deptmapManager");
     action.setDeptmapManager(deptmapManager);

     // add a test deptmap to the database
     Deptmap deptmap = new Deptmap();

     // enter all required fields
     deptmap.setMarktype("WqDoCgSuEfJrPqKkAiBnPlBkWbLiJqRkXfZeXoFxByUvCtRfTcLgXmRnAiZv");
     deptmap.setSourcecode("PyZnBlLrFhOuJtBrQuHiUxKyVxLeVnDuAwZfYfBsIoGzJbJuHa");
     deptmap.setSourcename("QwJqYmOmYrFeHqFxMcZgLkTjQbGkNnWbZxGjOnEzXoUmAdRvLlGnLsYpPyPaJfRtOaPmDnSpNyQaAqDfAiEdPjOaVvFcKzHdCoYa");

     deptmapManager.save(deptmap);
     }

     @Test
     public void testGetAllDeptmaps() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getDeptmaps().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getDeptmaps().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setId(-1L);
     assertNull(action.getDeptmap());
     assertEquals("success", action.edit());
     assertNotNull(action.getDeptmap());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getDeptmap());

     Deptmap deptmap = action.getDeptmap();
     // update required fields
     deptmap.setMarktype("UnIxEqZaRfEdIrCeVyPwWeDaVmVrErRiJoVsJySwIiTtUwIgEhIoYvVdTiJp");
     deptmap.setSourcecode("MhNyUuJpWmHrJlRrRfEsFxUkNrUdQgMyNmIwYsKiFjUmJeKgTe");
     deptmap.setSourcename("PrCeGyQkCuBvKfIaItRqZoKlOeCrCcIgUvRaDkYhVqPnYxVcEtZjGeReNfLyYbNrDxWoEgLuWcCqBvWoTeUfBgZgGcEuNjSyNaHr");

     action.setDeptmap(deptmap);

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
     Deptmap deptmap = new Deptmap();
     deptmap.setId(-2L);
     action.setDeptmap(deptmap);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "DeptmapActionTest" );
    }
}