package com.huge.ihos.system.reportManager.search.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.search.dao.TestDateDao;
import com.huge.ihos.system.reportManager.search.model.TestDate;
import com.huge.ihos.system.reportManager.search.service.TestDateManager;
import com.huge.service.impl.GenericManagerImpl;

@Service( "testDateManager" )
public class TestDateManagerImpl
    extends GenericManagerImpl<TestDate, Long>
    implements TestDateManager {
    TestDateDao testDao;

    @Autowired
    public TestDateManagerImpl( TestDateDao testDao ) {
        super( testDao );
        this.testDao = testDao;
    }

    public List<TestDate> getAllTest() {
        return testDao.getAllTest();
    }

}