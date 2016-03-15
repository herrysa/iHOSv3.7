package com.huge.ihos.system.reportManager.search.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.reportManager.search.model.TestValue;

/**
 * An interface that provides a data management interface to the Search table.
 */
public interface TestValueDao
    extends GenericDao<TestValue, Long> {

    public List<TestValue> getAllTest();
}