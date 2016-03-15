package com.huge.ihos.strategy.dao;

import org.junit.Assert;
import org.junit.Test;
import com.huge.dao.BaseDaoTestCase;
import org.springframework.beans.factory.annotation.Autowired;

public class StrategyDaoTest extends BaseDaoTestCase {
    @Autowired
    private StrategyDao strategyDao;

    @Test
    public void testDummy() {
    	log.info("here is a dummy test.");
      	Assert.assertTrue(true);
    }
}