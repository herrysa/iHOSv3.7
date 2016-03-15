package com.huge.ihos.system.reportManager.search.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.search.dao.TestValueDao;
import com.huge.ihos.system.reportManager.search.model.TestValue;
import com.huge.ihos.system.reportManager.search.service.TestValueManager;
import com.huge.service.impl.GenericManagerImpl;

@Service( "testValueManager" )
public class TestValueManagerImpl
    extends GenericManagerImpl<TestValue, Long>
    implements TestValueManager {
    TestValueDao testDao;

    @Autowired
    public TestValueManagerImpl( TestValueDao testDao ) {
        super( testDao );
        this.testDao = testDao;
    }

    public List<TestValue> getAllTestValue() {
        return testDao.getAllTest();
    }

}