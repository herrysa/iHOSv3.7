package com.huge.ihos.indicatoranalysis.dao;

import org.junit.Assert;
import org.junit.Test;
import com.huge.dao.BaseDaoTestCase;
import org.springframework.beans.factory.annotation.Autowired;

public class IndicatorTypeDaoTest extends BaseDaoTestCase {
    @Autowired
    private IndicatorTypeDao indicatorTypeDao;

    @Test
    public void testDummy() {
    	log.info("here is a dummy test.");
      	Assert.assertTrue(true);
    }
}