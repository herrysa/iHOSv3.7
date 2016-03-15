package com.huge.ihos.formula.dao;

import org.junit.Assert;
import org.junit.Test;
import com.huge.dao.BaseDaoTestCase;
import org.springframework.beans.factory.annotation.Autowired;

public class UpCostDaoTest extends BaseDaoTestCase {
    @Autowired
    private UpCostDao upCostDao;

    @Test
    public void testDummy() {
    	log.info("here is a dummy test.");
      	Assert.assertTrue(true);
    }
}