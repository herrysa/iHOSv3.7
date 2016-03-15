package com.huge.ihos.orgprivilege.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.orgprivilege.service.OrgPrivilegeManager;
import com.huge.service.BaseManagerTestCase;

public class OrgPrivilegeManagerImplTest extends BaseManagerTestCase {
	@Autowired
    private OrgPrivilegeManager manager;

    @Test
    public void testDummy() throws Exception {
    	log.info("here is a dummy test.");
        Assert.assertTrue(true);
    }
}