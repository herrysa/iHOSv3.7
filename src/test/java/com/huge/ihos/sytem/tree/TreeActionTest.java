package com.huge.ihos.sytem.tree;

import org.apache.struts2.StrutsSpringTestCase;

import com.huge.ihos.system.util.tree.TreeAction;
import com.opensymphony.xwork2.ActionProxy;

public class TreeActionTest extends StrutsSpringTestCase {

	protected String[] getContextLocations() {
		return new String[] { "classpath:/applicationContext-resources.xml", "classpath:/applicationContext-dao.xml",
				"classpath*:/applicationContext.xml", "classpath:**/applicationContext*.xml" };
	}
	
	public void testMakeTree() throws Exception {
		this.request.setParameter("treeParam", "{dept:{}}");
		ActionProxy proxy = getActionProxy("/makeTree");
		TreeAction treeAction = (TreeAction)proxy.getAction();
		String result = proxy.execute();
		
		
	}
}
