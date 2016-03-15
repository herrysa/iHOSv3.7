package com.huge.ihos.formula.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class FormulaEntityActionTest
    extends BaseActionTestCase {
    /*    private FormulaEntityAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new FormulaEntityAction();
     FormulaEntityManager formulaEntityManager = (FormulaEntityManager) applicationContext.getBean("formulaEntityManager");
     action.setFormulaEntityManager(formulaEntityManager);

     // add a test formulaEntity to the database
     FormulaEntity formulaEntity = new FormulaEntity();

     // enter all required fields
     formulaEntity.setEntityName("" + Math.random());
     formulaEntity.setTableName("" + Math.random());

     formulaEntityManager.save(formulaEntity);
     }

     @Test
     public void testGetAllFormulaEntities() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getFormulaEntities().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getFormulaEntities().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setFormulaEntityId(-1L);
     assertNull(action.getFormulaEntity());
     assertEquals("success", action.edit());
     assertNotNull(action.getFormulaEntity());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setFormulaEntityId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getFormulaEntity());

     FormulaEntity formulaEntity = action.getFormulaEntity();
     // update required fields
     formulaEntity.setEntityName("OtTyJwKwDoWuObBnMsKcSiJcIrWsRaMvDfQaEvSpJnNbSxJqBq");
     formulaEntity.setTableName("SuXvKsXaZeXmWqQsTnPcFtKcZiGeQaMkHkSwJaNoBxScIdFpPz");

     action.setFormulaEntity(formulaEntity);

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
     FormulaEntity formulaEntity = new FormulaEntity();
     formulaEntity.setFormulaEntityId(-2L);
     action.setFormulaEntity(formulaEntity);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "FormulaEntityActionTest" );
    }
}