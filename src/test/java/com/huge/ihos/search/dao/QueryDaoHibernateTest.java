package com.huge.ihos.search.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.reportManager.search.dao.QueryDao;
import com.huge.util.ReturnUtil;

public class QueryDaoHibernateTest extends BaseDaoTestCase {
	@Autowired
	QueryDao queryDao;

	// @Test
	public void testDeletePublic() {
		String table = "singleentitysample";
		String key = "pkid";
		String[] ids = { "-1", "-2" };

		String msg = queryDao.deletePublic(table, key, ids);
		Assert.assertTrue(msg.equalsIgnoreCase("success"));
		// fail("Not yet implemented");
	}

	// @Test
	public void testDeletePublics() {

		String[] ss = queryDao.queryTableColumn("t_allotPrinciple");
		for (int i = 0; i < ss.length; i++) {
			System.out.println(ss[i]);
		}
		// Assert.assertTrue(msg.equalsIgnoreCase("5"));
		// fail("Not yet implemented");
	}

	// @Test
	public void testProcessPublicSqlserver() {
		Object[] args = { 12, 25 };

		ReturnUtil msg = queryDao.processPublic("dbo.test_proc", args);

		Assert.assertEquals("processed", msg.getMessage());
	}

	// @Test
	public void testProcessPublicMysql() {
		Object[] args = { 12, 25 };

		ReturnUtil msg = queryDao.processPublic("test_proc", args);

		Assert.assertEquals("processed", msg.getMessage());
	}

	@Test
	public void testDummy() throws Exception {
		log.info("here is a dummy test.");
		Assert.assertTrue(true);
	}
}
