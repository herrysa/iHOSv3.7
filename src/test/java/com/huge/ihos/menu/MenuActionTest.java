package com.huge.ihos.menu;

import org.apache.struts2.StrutsSpringTestCase;

import com.opensymphony.xwork2.ActionProxy;

public class MenuActionTest extends StrutsSpringTestCase {

	protected String[] getContextLocations() {
		return new String[] { "classpath:/applicationContext-resources.xml", "classpath:/applicationContext-dao.xml",
				"classpath*:/applicationContext.xml", "classpath:**/applicationContext*.xml" };
	}
	
	/*public void testMenuList() throws Exception {
		ActionProxy proxy = getActionProxy("/menuGridList");
		String result = proxy.execute();
		assertEquals("success", result);
	}*/
}
