package com.huge.webapp.action;

import org.junit.Test;

public class ChargeTypeActionTest
    extends BaseActionTestCase {
    /*    private ChargeTypeAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new ChargeTypeAction();
     ChargeTypeManager chargeTypeManager = (ChargeTypeManager) applicationContext.getBean("chargeTypeManager");
     action.setChargeTypeManager(chargeTypeManager);

     // add a test chargeType to the database
     ChargeType chargeType = new ChargeType();

     // enter all required fields
     chargeType.setChargeTypeName("IhRyZhCbFeTnNvGkYvRrJyUqPxLdMg");
     chargeType.setClevel(1.4998233755769207E9L);
     chargeType.setDisabled(Boolean.FALSE);
     chargeType.setLeaf(Boolean.FALSE);

     chargeTypeManager.save(chargeType);
     }

     @Test
     public void testGetAllChargeTypes() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getChargeTypes().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getChargeTypes().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setChargeTypeId(-1L);
     assertNull(action.getChargeType());
     assertEquals("success", action.edit());
     assertNotNull(action.getChargeType());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setChargeTypeId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getChargeType());

     ChargeType chargeType = action.getChargeType();
     // update required fields
     chargeType.setChargeTypeName("OeYfLhIsEcAsBsTcLmQbXqCvFfViUa");
     chargeType.setClevel(1.2121339625191498E9L);
     chargeType.setDisabled(Boolean.FALSE);
     chargeType.setLeaf(Boolean.FALSE);

     action.setChargeType(chargeType);

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
     ChargeType chargeType = new ChargeType();
     chargeType.setChargeTypeId(-2L);
     action.setChargeType(chargeType);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "ChargeTypeActionTest" );
    }
}