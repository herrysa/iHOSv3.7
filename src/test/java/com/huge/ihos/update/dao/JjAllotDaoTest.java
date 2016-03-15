package com.huge.ihos.update.dao;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Ignore;
import org.junit.Test;

import com.huge.dao.BaseDaoTestCase;

public class JjAllotDaoTest extends BaseDaoTestCase {
	@Autowired
	private JjAllotDao jjAllotDao;

	@Ignore
	public void testItemName() {
		String IN = jjAllotDao.getCurrentItemName("201207");
		System.out.println(IN);
	}

	@Ignore
	public void testGetRealValue() {
		System.out.println(jjAllotDao.getRealDeptAmount("D0000909912", "201207"));

	}

	@Test
	public void testDummy() throws Exception {
		Assert.assertTrue(true);
	}
}