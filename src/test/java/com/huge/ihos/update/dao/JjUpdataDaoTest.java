package com.huge.ihos.update.dao;

import org.junit.Assert;
import org.junit.Test;
import com.huge.dao.BaseDaoTestCase;
import org.springframework.beans.factory.annotation.Autowired;

public class JjUpdataDaoTest extends BaseDaoTestCase {
    @Autowired
    private JjUpdataDao jjUpdataDao;

    @Test
    public void testDummy() {
    	log.info("here is a dummy test.");
      	Assert.assertTrue(true);
    }
}