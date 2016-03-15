package com.huge.ihos.system.reportManager.search.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.search.dao.TestDao;
import com.huge.ihos.system.reportManager.search.model.Test;
import com.huge.ihos.system.reportManager.search.service.TestManager;
import com.huge.service.impl.GenericManagerImpl;

@Service( "testManager" )
public class TestManagerImpl
    extends GenericManagerImpl<Test, Long>
    implements TestManager {
    TestDao testDao;

    @Autowired
    public TestManagerImpl( TestDao testDao ) {
        super( testDao );
        this.testDao = testDao;
    }

    public List<Test> getAllTest() {
        return testDao.getAllTest();
    }

}