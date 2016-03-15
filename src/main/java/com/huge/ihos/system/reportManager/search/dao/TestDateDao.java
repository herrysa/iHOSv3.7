package com.huge.ihos.system.reportManager.search.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.reportManager.search.model.TestDate;

/**
 * An interface that provides a data management interface to the Search table.
 */
public interface TestDateDao
    extends GenericDao<TestDate, Long> {

    public List<TestDate> getAllTest();
}