package com.huge.webapp.action;

import org.junit.Test;

public class SourcepayinActionTest
    extends BaseActionTestCase {
    /*    private SourcepayinAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new SourcepayinAction();
     SourcepayinManager sourcepayinManager = (SourcepayinManager) applicationContext.getBean("sourcepayinManager");
     action.setSourcepayinManager(sourcepayinManager);

     // add a test sourcepayin to the database
     Sourcepayin sourcepayin = new Sourcepayin();

     // enter all required fields
     sourcepayin.setAmount("MuEoMwKxMiQfOzMySvEwBrLtRnZzHgQpIlWfBrHyLtJyMlCeTeDjRlVnPkNnHkFoQqXbIcAeQqSfZqGpYaNmPmJkTkRfZcQeBvBoAwNeNbIcTnSwEgLqLoShUpKiXlMeNtMoOiTaVrOzWgErAdClObKeZbXzYsMySaBfDoJdRvZwGnPeRsAaPvZwVgKwMnIhRfHxMdGlHjBoUnQlIdMwDgMuIyKcWgCqZpHsPqPvPkUlMuKfXoArUrUaJdGqMwI");
     sourcepayin.setCheckPeriod("OrTdSb");
     sourcepayin.setOutin("TyYiQa");

     sourcepayinManager.save(sourcepayin);
     }

     @Test
     public void testGetAllSourcepayins() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getSourcepayins().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getSourcepayins().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setSourcePayinId(-1L);
     assertNull(action.getSourcepayin());
     assertEquals("success", action.edit());
     assertNotNull(action.getSourcepayin());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setSourcePayinId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getSourcepayin());

     Sourcepayin sourcepayin = action.getSourcepayin();
     // update required fields
     sourcepayin.setAmount("BrUpWoVoLlXmRjHtPnPtDzZhYbOtAhOoOaGoUdZkArIbKdCiBmJhBuJmLmXbCwIcVfBxUfNsSlVcKcXyEnCyUwPuNgHlQuKrCfJgXwFeCgDvEpCwBnLlRuLtFySaYeBbYsUfLjCwMhRcCnCfRxRqZoLaEsCgIuIbGsBtCkOnYxFhAjCzHbZoKjPdThPiGoDySpVlThXtHjZqVgRjMuRlLiKqFmMfTiUoEfSmXrCyXvHwXgYaNiDeTyLdGqDxEyN");
     sourcepayin.setCheckPeriod("DwGmPx");
     sourcepayin.setOutin("FqAuOx");

     action.setSourcepayin(sourcepayin);

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
     Sourcepayin sourcepayin = new Sourcepayin();
     sourcepayin.setSourcePayinId(-2L);
     action.setSourcepayin(sourcepayin);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "SourcepayinActionTest" );
    }
}