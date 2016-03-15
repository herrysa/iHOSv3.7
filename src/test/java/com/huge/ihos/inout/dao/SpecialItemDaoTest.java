package com.huge.ihos.inout.dao;

import org.junit.Assert;
import org.junit.Test;
import com.huge.dao.BaseDaoTestCase;
import org.springframework.beans.factory.annotation.Autowired;

public class SpecialItemDaoTest extends BaseDaoTestCase {
    @Autowired
    private SpecialItemDao specialItemDao;

    @Test
    public void testDummy() {
    	log.info("here is a dummy test.");
      	Assert.assertTrue(true);
    }
}