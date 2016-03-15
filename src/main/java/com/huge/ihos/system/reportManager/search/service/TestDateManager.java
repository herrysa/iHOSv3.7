package com.huge.ihos.system.reportManager.search.service;

import java.util.List;

import com.huge.ihos.system.reportManager.search.model.TestDate;
import com.huge.service.GenericManager;

public interface TestDateManager
    extends GenericManager<TestDate, Long> {
    public List<TestDate> getAllTest();
}