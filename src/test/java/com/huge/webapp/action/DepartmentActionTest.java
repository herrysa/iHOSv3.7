package com.huge.webapp.action;

import org.junit.Test;

public class DepartmentActionTest
    extends BaseActionTestCase {
    /*    private DepartmentAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new DepartmentAction();
     DepartmentManager departmentManager = (DepartmentManager) applicationContext.getBean("departmentManager");
     action.setDepartmentManager(departmentManager);

     // add a test department to the database
     Department department = new Department();

     // enter all required fields
     department.setDeptCode("JeMdNyLmDhCuWiAqWrQi");
     department.setName("" + Math.random());

     departmentManager.save(department);
     }

     @Test
     public void testGetAllDepartments() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getDepartments().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getDepartments().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setDepartmentId(-1L);
     assertNull(action.getDepartment());
     assertEquals("success", action.edit());
     assertNotNull(action.getDepartment());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setDepartmentId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getDepartment());

     Department department = action.getDepartment();
     // update required fields
     department.setDeptCode("DkRbRjLyDcRlTaPeCyCx");
     department.setName("NjVnMrYbCfNyCjOrOwVyQxQnMtUiEsByMaEuPqJjKvUrGkJqYi");

     action.setDepartment(department);

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
     Department department = new Department();
     department.setDepartmentId(-2L);
     action.setDepartment(department);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "DepartmentActionTest" );
    }
}