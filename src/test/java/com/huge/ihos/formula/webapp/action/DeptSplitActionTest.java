package com.huge.ihos.formula.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class DeptSplitActionTest
    extends BaseActionTestCase {
    /*    private DeptSplitAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new DeptSplitAction();
     DeptSplitManager deptSplitManager = (DeptSplitManager) applicationContext.getBean("deptSplitManager");
     action.setDeptSplitManager(deptSplitManager);

     // add a test deptSplit to the database
     DeptSplit deptSplit = new DeptSplit();

     // enter all required fields
     deptSplit.setCostratio("UjTeKmUyJwGlTnVnPyAwIhNbAvHkPbKxUvMyJaOvVtYvIhFcLbKkMhHkSqRiWoNsNmKsEkElLhNwXiDmNhHvUeSsXdJcIeRlXfAkScFiMcWePdDnYcHdMeDjWuDgHfXzOyVwJzXlQqBrNnVbQiUgIcPnTuOaQhDsZmZkBbZdZhKnQvIwDpHdLzHvHsUkDfLeUrWeMvSkCsYxVpEmPyOxCzFaMuVwHcOfHqZbSwHhKqIaAiSuUcDfUoCeRaQdBaN");
     deptSplit.setDeptid("MnOfUwNkTzRrPaNcCxJg");
     deptSplit.setDeptname("CsRvAwSbWePyBcKdBbZbLjRxOfAhMyJwSiSpMcLkHpYcJvBvIw");
     deptSplit.setDisabled(Boolean.FALSE);
     deptSplit.setPubdeptid("KsArWcSoUuEaPxQhCvOo");
     deptSplit.setPubdeptname("XuKgUkGaRsLnOoPgSjZaIuNgOuHoPcLfIhYtCkLpItTaOzNyWu");

     deptSplitManager.save(deptSplit);
     }

     @Test
     public void testGetAllDeptSplits() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getDeptSplits().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getDeptSplits().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setDeptSplitId(-1L);
     assertNull(action.getDeptSplit());
     assertEquals("success", action.edit());
     assertNotNull(action.getDeptSplit());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setDeptSplitId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getDeptSplit());

     DeptSplit deptSplit = action.getDeptSplit();
     // update required fields
     deptSplit.setCostratio("VcEoXnWkPtSiVlYuExSpGzWwNvXtEjEqSqXcXzUxZuFdLiXpHxGuKrXrIgRrLwFxExZeEfAfKuCsAqDtXcHoEnKhZaZfWcXzTvHgVhNoZfQsHsUiRzXdWzZqNxAaQlOsOtLySkXdOeAlHuSzKjHhLxAtOvSrMcTpRaBtPjWhQdQiCoJyHtVrKxRqVzFxRfEjGgGxTwVuQvFqPzOzKcDjXdGyZoIsWiVmKoLmQmBiHgWjLgEzJuOoHbTjTiIgNyK");
     deptSplit.setDeptid("LvArWbGvKgCmRqFiFmSz");
     deptSplit.setDeptname("SoLvYbPyJyKaUfTqHiHxIeMlYyQkQvUpPmUuUtQpWpHzReRjVv");
     deptSplit.setDisabled(Boolean.FALSE);
     deptSplit.setPubdeptid("YgLvSiYvHzQxShBpBbAj");
     deptSplit.setPubdeptname("DwUlNhSnCdNvWcWmLgLlVePiRzTqFqWbWmZsIoQjDkTnNcEpCe");

     action.setDeptSplit(deptSplit);

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
     DeptSplit deptSplit = new DeptSplit();
     deptSplit.setDeptSplitId(-2L);
     action.setDeptSplit(deptSplit);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "DeptSplitActionTest" );
    }
}