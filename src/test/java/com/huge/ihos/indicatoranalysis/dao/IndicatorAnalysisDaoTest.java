package com.huge.ihos.indicatoranalysis.dao;

import org.junit.Assert;
import org.junit.Test;
import com.huge.dao.BaseDaoTestCase;
import org.springframework.beans.factory.annotation.Autowired;

public class IndicatorAnalysisDaoTest extends BaseDaoTestCase {
    @Autowired
    private IndicatorAnalysisDao indicatorAnalysisDao;

    @Test
    public void testDummy() {
    	log.info("here is a dummy test.");
      	Assert.assertTrue(true);
    }
}