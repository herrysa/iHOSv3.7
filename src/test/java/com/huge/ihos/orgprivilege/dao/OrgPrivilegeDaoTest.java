package com.huge.ihos.orgprivilege.dao;

import org.junit.Assert;
import org.junit.Test;
import com.huge.dao.BaseDaoTestCase;
import org.springframework.beans.factory.annotation.Autowired;

public class OrgPrivilegeDaoTest extends BaseDaoTestCase {
    @Autowired
    private OrgPrivilegeDao orgPrivilegeDao;

    @Test
    public void testDummy() {
    	log.info("here is a dummy test.");
      	Assert.assertTrue(true);
    }
}