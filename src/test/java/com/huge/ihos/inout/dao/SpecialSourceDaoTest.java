package com.huge.ihos.inout.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.dao.BaseDaoTestCase;

public class SpecialSourceDaoTest extends BaseDaoTestCase {
    @Autowired
    private SpecialSourceDao specialSourceDao;

    @Test
    public void testDummy() {
    	List list=specialSourceDao.changeSpecialItemList("收入");
    	System.out.println(list.size());
    }
}