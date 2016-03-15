package com.huge.webapp.action;

import org.junit.Test;

public class PersonActionTest
    extends BaseActionTestCase {
    /*    private PersonAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new PersonAction();
     PersonManager personManager = (PersonManager) applicationContext.getBean("personManager");
     action.setPersonManager(personManager);

     // add a test person to the database
     Person person = new Person();

     // enter all required fields
     person.setDepartmentId(1.7974646509405868E9L);
     person.setDepartmentName("FcYjXuJiBzDrScLeQhJpBbYjPkJwVaKiNpLlNrGvJvTqCuDpRc");
     person.setEnabled(Boolean.FALSE);
     person.setName("AiKdXjKlQrVlEtGeCuIb");
     person.setSex("BlObXpYfKf");

     personManager.save(person);
     }

     @Test
     public void testGetAllPersons() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getPersons().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getPersons().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setPersonId(-1L);
     assertNull(action.getPerson());
     assertEquals("success", action.edit());
     assertNotNull(action.getPerson());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setPersonId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getPerson());

     Person person = action.getPerson();
     // update required fields
     person.setDepartmentId(1.5940000785670135E9L);
     person.setDepartmentName("VoUpOeVmHbEiHeDvWeMeKgLiNoIdWpHkUbDcCkKeFcYsDzXhSe");
     person.setEnabled(Boolean.FALSE);
     person.setName("ToTbDgBcVtQfRjMlFpFf");
     person.setSex("VyXwVzNuNb");

     action.setPerson(person);

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
     Person person = new Person();
     person.setPersonId(-2L);
     action.setPerson(person);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "PersonActionTest" );
    }
}