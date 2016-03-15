package com.huge.webapp.action;

import org.junit.Test;

public class DataCollectionTaskStepActionTest
    extends BaseActionTestCase {
    /*    private DataCollectionTaskStepAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new DataCollectionTaskStepAction();
     DataCollectionTaskStepManager dataCollectionTaskStepManager = (DataCollectionTaskStepManager) applicationContext.getBean("dataCollectionTaskStepManager");
     action.setDataCollectionTaskStepManager(dataCollectionTaskStepManager);

     // add a test dataCollectionTaskStep to the database
     DataCollectionTaskStep dataCollectionTaskStep = new DataCollectionTaskStep();

     // enter all required fields
     dataCollectionTaskStep.setExecOrder(1145271169);
     dataCollectionTaskStep.setExecSql("XhXwCuTdWmQiRbNsBcDbKjMuVnKyKhZwRgLwAvIsKzZbQfTjRmCoJpGhIwKdUdXlNpScIaOqMtFfWfCeBcJwUnZxVwYqYqTvBqUdDqJlCsQgQbTbUfZdRhJjYpZrXhRhFkOuIoPpReRkEzPbDkRjVyVyQgJeJzHkEwSiCmQsFtVsIqDlPqKlQqJlDpSwNiFuTlXcWjDcVxKlWhMcSiElEeIhXcPdPtVwNyFkHdFjOjBxSiAxGuYzIvWyFaUfFjEgWjQpXkNqGcUbXxQtHwAyGnXkQaXyFqIcSfClFdCqYhHzFeXlDrNpEwLgBqEmWpYsSfRsCkZiJsEcPeDeUzGqIyVsTmRfXgStVtVzNsGnSiYxGqOxHoPvGcObSiMfFaRbAfFtDjOaMaSxTnHkYrWpFuDeKoJqQvJlBbKwSeJvZvSrLeWcVcUaWbJlPxSrBaFdVlIaFiAoUgKjEiOnTfLdKdQyLlLjLvJxUxIrNrTiIqMjExSxGnAi");
     dataCollectionTaskStep.setIsUsed(Boolean.FALSE);
     dataCollectionTaskStep.setStepName("RbSwAqMcUbTbQlCcIzWsBiTeEkIxYdIcSuQaVcHzNsGbHtLuEv");
     dataCollectionTaskStep.setStepType("LzUnImXgYqAsFbJqZrSzRtUiCdZvMyUjApZqAuUrWeFrJbNsDyAdZuSiWhXoYmUnVzRxFwTqJxTnJtYqCxZeJhVnGiSaZaNlYpMiUvKzJoUoXhFtKjHdJyAyKnPkBeBgVbToEdOcKaVrJhCkHqFzVrLfNcLvZkQwItEjCbXnQyTpPdSjCwIrYpWpHjHaIoHeOnPeZmSaGaSgYfSuBdJiMlHuItBgFdQnHsWyGpHrDaNjMzSeDiMqPaPvAjJsDqC");

     dataCollectionTaskStepManager.save(dataCollectionTaskStep);
     }

     @Test
     public void testGetAllDataCollectionTaskSteps() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getDataCollectionTaskSteps().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getDataCollectionTaskSteps().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setStepId(-1L);
     assertNull(action.getDataCollectionTaskStep());
     assertEquals("success", action.edit());
     assertNotNull(action.getDataCollectionTaskStep());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setStepId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getDataCollectionTaskStep());

     DataCollectionTaskStep dataCollectionTaskStep = action.getDataCollectionTaskStep();
     // update required fields
     dataCollectionTaskStep.setExecOrder(870817476);
     dataCollectionTaskStep.setExecSql("LlGdEgFqRvHlOrDqFwJuNxTaFpYzAdAhZaVfSdApWoDwQpDpPzFcTsXmAbYuUcPxYuIbYsFxVlJfHrTlOyHnFfCpPyOcYoMqSdFyExIvHkUcJcOrMrMxCvBdSfGgMiAcPrSnAlMzLeDwShHdXxDuMoDzWmSsYuNgRtPeDdVbDrCwVfSpXxGiOyPxIwIoBpYkWjSrQmTwIpJtYtEnZeWnYjIxBfAsCuRtCyFwEwSqMbThFmSgTyTrFnLdVpAeAeTaArPyHjBiQwZaKpCeJcCcViLcYvIkNzBjVcMoQkHlLtNoLgOfSgZbFwNxUrTgKpBoEuApViHdPxNaSxFwIdJaYsYyXsCyJtRmOiIzNhHhIuDpDbNbDhCzOlAtIcRiSsEsZrIpInLcLiHiUiVmUxOlIlEdVbVjQfWlNoRvNoKtIdIoPoOeYyVaFyZlZxFyRwKuRfCzUcEnIzBaNjLjEnGyLgAhYfMyFsUbLoThYuNvNuIsPvUlPnVk");
     dataCollectionTaskStep.setIsUsed(Boolean.FALSE);
     dataCollectionTaskStep.setStepName("WkMkSgKiLdLoZgIuIlCxHqHpUmMhJpGeZvGrTrUyOuQkMhQuIj");
     dataCollectionTaskStep.setStepType("IlTaKyKmTvWbCtWmEpQoHaEhYtAuEmYfFpZoOnJeNwPmZkSaTuCjUiWkFiDyMqVpOgQoEmMuXbXgLsEmSaEqNmFgSvDfBuKpJmUjAyWiUiJkThKxEcVpDiUaHfDbCoPwMgGoXlSwAhAvXrDpAlQzKxTxGoXjEfFlPoQlUfMcMeHpHoWcFpVlOfJyZqIuGoCtGmHaCsAxPtQpAgPbWcMuQsYoEqXtYwKlNtWwJtEkEuBmCmQtApZvGtBdEzPbBkW");

     action.setDataCollectionTaskStep(dataCollectionTaskStep);

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
     DataCollectionTaskStep dataCollectionTaskStep = new DataCollectionTaskStep();
     dataCollectionTaskStep.setStepId(-2L);
     action.setDataCollectionTaskStep(dataCollectionTaskStep);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "DataCollectionTaskStepActionTest" );
    }
}