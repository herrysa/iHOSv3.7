package com.huge.ihos.bylaw.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.oa.bylaw.dao.ByLawDao;

public class ByLawDaoTest extends BaseDaoTestCase {
    @Autowired
    private ByLawDao byLawDao;

    @Test
    public void testDummy() {
    	log.info("here is a dummy test.");
      	Assert.assertTrue(true);
    }
}