package com.huge.ihos.update.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.update.service.JjAllotManager;
import com.huge.service.BaseManagerTestCase;

public class JjAllotManagerImplTest extends BaseManagerTestCase {
	 @Autowired
    private JjAllotManager manager;

        @Test
    public void testDummy() throws Exception {
    	log.info("here is a dummy test.");
        Assert.assertTrue(true);
    }
}