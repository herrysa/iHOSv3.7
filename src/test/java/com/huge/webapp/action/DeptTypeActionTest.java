package com.huge.webapp.action;

import org.junit.Test;

public class DeptTypeActionTest
    extends BaseActionTestCase {
    /*    private DeptTypeAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new DeptTypeAction();
     DeptTypeManager deptTypeManager = (DeptTypeManager) applicationContext.getBean("deptTypeManager");
     action.setDeptTypeManager(deptTypeManager);

     // add a test deptType to the database
     DeptType deptType = new DeptType();

     // enter all required fields
     deptType.setDeptTypeName("SiRsAzGlLzDgUkNeXkYh");
     deptType.setDisabled(Boolean.FALSE);

     deptTypeManager.save(deptType);
     }

     @Test
     public void testGetAllDeptTypes() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getDeptTypes().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getDeptTypes().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setDeptTypeId(-1L);
     assertNull(action.getDeptType());
     assertEquals("success", action.edit());
     assertNotNull(action.getDeptType());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setDeptTypeId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getDeptType());

     DeptType deptType = action.getDeptType();
     // update required fields
     deptType.setDeptTypeName("NzBmSpCkEhSqKqScAoTf");
     deptType.setDisabled(Boolean.FALSE);

     action.setDeptType(deptType);

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
     DeptType deptType = new DeptType();
     deptType.setDeptTypeId(-2L);
     action.setDeptType(deptType);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "DeptTypeActionTest" );
    }
}