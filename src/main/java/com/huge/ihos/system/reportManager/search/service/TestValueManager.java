package com.huge.ihos.system.reportManager.search.service;

import java.util.List;

import com.huge.ihos.system.reportManager.search.model.TestValue;
import com.huge.service.GenericManager;

public interface TestValueManager
    extends GenericManager<TestValue, Long> {
    public List<TestValue> getAllTestValue();
}