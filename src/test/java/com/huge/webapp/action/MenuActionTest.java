package com.huge.webapp.action;

import org.junit.Test;

public class MenuActionTest
    extends BaseActionTestCase {
    /*    private MenuAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new MenuAction();
     MenuManager menuManager = (MenuManager) applicationContext.getBean("menuManager");
     action.setMenuManager(menuManager);

     // add a test menu to the database
     Menu menu = new Menu();

     // enter all required fields
     menu.setLeaf(Boolean.FALSE);
     menu.setMenuLevel(4.087760912957297E8L);
     menu.setMenuName("UtBtQyZdLzJuDdEuAsRaXjEeNjGiPnWiIrBlYjNyJjIoMsKvCs");

     menuManager.save(menu);
     }

     @Test
     public void testGetAllMenus() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getMenus().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getMenus().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setMenuId(-1L);
     assertNull(action.getMenu());
     assertEquals("success", action.edit());
     assertNotNull(action.getMenu());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setMenuId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getMenu());

     Menu menu = action.getMenu();
     // update required fields
     menu.setLeaf(Boolean.FALSE);
     menu.setMenuLevel(1.416928041436554E9L);
     menu.setMenuName("SbQyElRhYpHbXzIeQkQhCzLnWqMvBtSgCoWxQhJfAfRiNxMpLf");

     action.setMenu(menu);

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
     Menu menu = new Menu();
     menu.setMenuId(-2L);
     action.setMenu(menu);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "MenuActionTest" );
    }
}