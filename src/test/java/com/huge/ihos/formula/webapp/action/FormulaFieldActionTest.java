package com.huge.ihos.formula.webapp.action;

import org.junit.Test;

import com.huge.webapp.action.BaseActionTestCase;

public class FormulaFieldActionTest
    extends BaseActionTestCase {
    /*    private FormulaFieldAction action;
     @Autowired
     private CompassGps compassGps;

     @Before
     public void onSetUp() {
     super.onSetUp();
     compassGps.index();

     action = new FormulaFieldAction();
     FormulaFieldManager formulaFieldManager = (FormulaFieldManager) applicationContext.getBean("formulaFieldManager");
     action.setFormulaFieldManager(formulaFieldManager);

     // add a test formulaField to the database
     FormulaField formulaField = new FormulaField();

     // enter all required fields
     formulaField.setCalcType("MeNcGrAxQoFrXxDfDuJvYbYzSxYyZjXwFbPcHxQiSqAcOpHnEj");
     formulaField.setDefaultValue("JwHnPgVpRaUsHcAdIcXxMlNrQqTyZcQyJeKgSsPcXfMvBdLmOb");
     formulaField.setDirection("FdKlGmYnKaJkIiZwIzMiTfHlGyFuLpPqOaQsMuBpGzStWlAnWu");
     formulaField.setFieldName("" + Math.random());
     formulaField.setFieldTitle("RpFiUeHqXgFkQuQdXbQdZtZdScKqEqMnRdWkDrMzXiBxWyIcYn");
     formulaField.setInherited(Boolean.FALSE);
     formulaField.setKeyClass("JwSdCcWkBiBzNuElWxAvUwExVmKzAlCkSgJgKmIhZpNsPxYdBi");

     formulaFieldManager.save(formulaField);
     }

     @Test
     public void testGetAllFormulaFields() throws Exception {
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertTrue(action.getFormulaFields().size() >= 1);
     }

     @Test
     public void testSearch() throws Exception {
     action.setQ("*");
     assertEquals(action.list(), ActionSupport.SUCCESS);
     assertEquals(4, action.getFormulaFields().size());
     }

     @Test
     public void testEdit() throws Exception {
     log.debug("testing edit...");
     action.setFormulaFieldId(-1L);
     assertNull(action.getFormulaField());
     assertEquals("success", action.edit());
     assertNotNull(action.getFormulaField());
     assertFalse(action.hasActionErrors());
     }

     @Test
     public void testSave() throws Exception {
     MockHttpServletRequest request = new MockHttpServletRequest();
     ServletActionContext.setRequest(request);
     action.setFormulaFieldId(-1L);
     assertEquals("success", action.edit());
     assertNotNull(action.getFormulaField());

     FormulaField formulaField = action.getFormulaField();
     // update required fields
     formulaField.setCalcType("OwUrUkGgYtFwIkRcQqPnVpElDaHfWcQsTnFhZwVwGvGqJcFkNj");
     formulaField.setDefaultValue("BbXqIxBbYwKiDmIoDzOeSgZhIiCuZiGjZaOgZtXsZkVpPqVbZa");
     formulaField.setDirection("LeCxDaXuGfYnNhUtSqVfFfGyInAbAqHhTpQhBeEhLaXoBmRuNu");
     formulaField.setFieldName("LbSnRnXcDeLmBbOwHpKuSzGeEqEdJyHuCvJeDrVcEtDnEgRhEi");
     formulaField.setFieldTitle("XsVsPmUpTzLoHzYmGmVgYbHaYiEsXjBaPxPwPxUdOwNaGmIbWq");
     formulaField.setInherited(Boolean.FALSE);
     formulaField.setKeyClass("JlTfTcRwDbFpPdZsMoHlSlQxYlMcOxPiTfFbXlTqCgOfJdCsDe");

     action.setFormulaField(formulaField);

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
     FormulaField formulaField = new FormulaField();
     formulaField.setFormulaFieldId(-2L);
     action.setFormulaField(formulaField);
     assertEquals("success", action.delete());
     assertNotNull(request.getSession().getAttribute("messages"));
     }*/
    @Test
    public void testDummy() {
        this.log.debug( "FormulaFieldActionTest" );
    }
}