package com.huge.ihos.deptType;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.system.systemManager.organization.model.DeptType;
import com.huge.ihos.system.systemManager.organization.service.DeptTypeManager;
import com.huge.service.BaseManagerTestCase;

public class DeptTypeManagerTest extends BaseManagerTestCase {
	@Autowired
	DeptTypeManager deptTypeManager;

	@Test
	public void testGetById() {
		String id = "1";
		DeptType dt = this.deptTypeManager.get(id);
		Assert.assertNotNull(dt);
		Assert.assertEquals("1", dt.getDeptTypeId());
		Assert.assertEquals("管理", dt.getDeptTypeName());
	}
}
