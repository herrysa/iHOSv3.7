package com.huge.ihos.singletest.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class SingleEntitySampleActionTest
    extends BaseActionTestCase {
    /*    private SingleEntitySampleAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new SingleEntitySampleAction();
     SingleEntitySampleManager singleEntitySampleManager = (SingleEntitySampleManager) applicationContext.getBean("singleEntitySampleManager");
     action.setSingleEntitySampleManager(singleEntitySampleManager);

     // add a test singleEntitySample to the database
     SingleEntitySample singleEntitySample = new SingleEntitySample();

     // enter all required fields
     singleEntitySample.setBigDecimalField("OtBlGhPgXgChHnXkHeDzPvDpIdAuRfLqDsHxUfNaLeZoGtAuHlRbHyAgSvMpLhEzIzUdEbNqIzVgPfVqEtHcOfNvWvNuDeAyNqDqIoJcYoEhZeFjDiDrVjAaVqZjUnLoGjSmYgNdMeXkBxHqDpQeKkUnWiPuGjOsRnCoCeApRdThDfSnYaNpUuRuJmItNeEvEsLcXpVpQcGvNbGnJdYxVaBeVyUbLbZqIjMpTiDnGvUvTlPeQbAiPeUfIxDxXnZ");
     singleEntitySample.setBooleanField(Boolean.FALSE);
     singleEntitySample.setDateField(new java.util.Date());
     singleEntitySample.setDoubleField(new Double(9.369987776303882E307));
     singleEntitySample.setFloatField(new Float(2.9103844E38));
     singleEntitySample.setIntField(1567811437);
     singleEntitySample.setStringField("" + Math.random());

     singleEntitySampleManager.save(singleEntitySample);
     }

     @Test
     public void testGetAllSingleEntitySamples() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getSingleEntitySamples().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getSingleEntitySamples().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setPkid(-1L);
     assertNull(action.getSingleEntitySample());
     assertEquals("success", action.edit());
     assertNotNull(action.getSingleEntitySample());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setPkid(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getSingleEntitySample());

     SingleEntitySample singleEntitySample = action.getSingleEntitySample();
     // update required fields
     singleEntitySample.setBigDecimalField("PqKvJsLsGuGqNxLvKzIcTeWmAlUaDqHeCjRaZuBxLbBqXxSlNwJmIwNeWgRdJtHpYyFeUbRoBcDtQkZuNyVpJtEyMqLzPzWaJhKuLgTiWfQqGgVcUrCrHhCdSnInLaFrEfYfHjHgCfKjZoSlXoWfLjJbOfDoQlHgArCcBzKoCsZyAuAqFbHbUhVqLxQsClJfZgPjNoEeHiRyIaEhJwGyPtYdTvMsApZoHaUfQiCdDjWlCjIbSeGfUcNlNiYzOzB");
     singleEntitySample.setBooleanField(Boolean.FALSE);
     singleEntitySample.setDateField(new java.util.Date());
     singleEntitySample.setDoubleField(new Double(1.609541801443695E308));
     singleEntitySample.setFloatField(new Float(2.40201E38));
     singleEntitySample.setIntField(1034937309);
     singleEntitySample.setStringField("TqNbGhUqQdBtYlUkDuDbSlPkKlCsElOzZwJfHkOnSwScZiReMm");

     action.setSingleEntitySample(singleEntitySample);

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
     SingleEntitySample singleEntitySample = new SingleEntitySample();
     singleEntitySample.setPkid(-2L);
     action.setSingleEntitySample(singleEntitySample);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "SingleEntitySampleActionTest" );
    }
}