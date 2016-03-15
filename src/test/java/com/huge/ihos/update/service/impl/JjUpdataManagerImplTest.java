package com.huge.ihos.update.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.update.service.JjUpdataManager;
import com.huge.service.BaseManagerTestCase;

public class JjUpdataManagerImplTest extends BaseManagerTestCase {
	 @Autowired
    private JjUpdataManager manager;

        @Test
    public void testDummy() throws Exception {
    	log.info("here is a dummy test.");
        Assert.assertTrue(true);
    }
}