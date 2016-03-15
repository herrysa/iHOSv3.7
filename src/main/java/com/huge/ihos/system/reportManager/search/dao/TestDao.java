package com.huge.ihos.system.reportManager.search.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.reportManager.search.model.Test;

/**
 * An interface that provides a data management interface to the Search table.
 */
public interface TestDao
    extends GenericDao<Test, Long> {

    public List<Test> getAllTest();
}