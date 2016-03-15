package com.huge.ihos.formula.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.dao.BaseDaoTestCase;

public class UpItemDaoTest extends BaseDaoTestCase {
    @Autowired
    private UpItemDao upItemDao;

    @Test
    public void testDummy() {
    	log.info("here is a dummy test.");
      	Assert.assertTrue(true);
    }
}