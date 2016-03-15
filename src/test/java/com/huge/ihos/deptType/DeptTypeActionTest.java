package com.huge.ihos.deptType;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.struts2.StrutsSpringTestCase;

import com.huge.ihos.system.systemManager.organization.webapp.action.DeptTypeAction;
import com.opensymphony.xwork2.ActionProxy;

/**
 * 
 * 此测试是基于junit 3的，方法前都要以test开头。 
 * 为什么不使用junit 4，是因为先少加载一些东西。简洁一些
 */
public class DeptTypeActionTest extends StrutsSpringTestCase {
	protected String[] getContextLocations() {
		return new String[] { "classpath:/applicationContext-resources.xml", "classpath:/applicationContext-dao.xml",
				"classpath*:/applicationContext.xml", "classpath:**/applicationContext*.xml" };
	}

	public void testDeptTypeGridList() throws Exception {
		ActionProxy proxy = getActionProxy("/deptTypeList");
		String result = proxy.execute();
		assertEquals("success", result);
	}

	public void testdeptTypeGridJson() throws Exception {

		ActionProxy proxy = getActionProxy("/deptTypeGridList");
		DeptTypeAction action = (DeptTypeAction) proxy.getAction();
		String result = proxy.execute();
		assertTrue(action.getDeptTypes().size() == 6);
		assertTrue(action.getRecords() == 6);
		assertTrue(action.getTotal() == 1);
		assertTrue(action.getPage() == 1);

		JsonConfig config = new JsonConfig();
		String[] excludeProperties = new String[] {
		// "timestampField"
		};
		config.setExcludes(excludeProperties);

		System.out.println(JSONArray.fromObject(action.getStatusCode(), config).toString());
		System.out.println(JSONArray.fromObject(action.getMessage(), config).toString());
		System.out.println(JSONArray.fromObject(action.getDeptTypes(), config).toString());

	}

	public void testdeptTypeGridJson_1() throws Exception {

		this.request.setParameter("rows", "1");

		ActionProxy proxy = getActionProxy("/deptTypeGridList");
		DeptTypeAction action = (DeptTypeAction) proxy.getAction();
		String result = proxy.execute();
		assertTrue(action.getDeptTypes().size() == 1);
		assertTrue(action.getRecords() == 6);
		assertTrue(action.getTotal() == 6);
		assertTrue(action.getPage() == 1);

		JsonConfig config = new JsonConfig();
		String[] excludeProperties = new String[] {
		// "timestampField"
		};
		config.setExcludes(excludeProperties);

		System.out.println(JSONArray.fromObject(action.getStatusCode(), config).toString());
		System.out.println(JSONArray.fromObject(action.getMessage(), config).toString());
		System.out.println(JSONArray.fromObject(action.getDeptTypes(), config).toString());

	}
}
