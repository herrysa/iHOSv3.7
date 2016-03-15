package com.huge.ihos.update.dao;

import org.junit.Assert;
import org.junit.Test;
import com.huge.dao.BaseDaoTestCase;
import org.springframework.beans.factory.annotation.Autowired;

public class JjUpdataDefColumnDaoTest extends BaseDaoTestCase {
    @Autowired
    private JjUpdataDefColumnDao jjUpdataDefColumnDao;

    @Test
    public void testDummy() {
    	log.info("here is a dummy test.");
      	Assert.assertTrue(true);
    }
}