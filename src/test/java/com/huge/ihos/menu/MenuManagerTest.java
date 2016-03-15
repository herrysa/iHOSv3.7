package com.huge.ihos.menu;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.service.BaseManagerTestCase;

public class MenuManagerTest extends BaseManagerTestCase {

	@Autowired
	MenuManager manager;
	
	@Test
	public void testMenuCach(){
		this.manager.getHibernateTemplate().setCacheQueries(true);
		this.manager.getAll();
		
		this.manager.getAll();
	}
}
