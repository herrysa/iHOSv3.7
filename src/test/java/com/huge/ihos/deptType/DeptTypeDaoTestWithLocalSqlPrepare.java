package com.huge.ihos.deptType;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.systemManager.organization.dao.DeptTypeDao;
import com.huge.ihos.system.systemManager.organization.model.DeptType;

public class DeptTypeDaoTestWithLocalSqlPrepare extends BaseDaoTestCase {
	@Autowired
	DeptTypeDao deptTypeDao;

	@Before
	public void init() {
		this.executeSqlScript("file:src/test/resources/testSql/t_deptType.sql", true);
/*		this.executeSqlScript("calss:src/test/resources/testSql/t_deptType.sql", true);
*/	}

	@Test
	public void testGetById() {
		String id = "1";
		DeptType dt = this.deptTypeDao.get(id);
		Assert.assertNotNull(dt);
		Assert.assertEquals("1", dt.getDeptTypeId());
		Assert.assertEquals("管理", dt.getDeptTypeName());
	}
}
