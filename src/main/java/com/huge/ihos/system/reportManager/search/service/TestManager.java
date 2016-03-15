package com.huge.ihos.system.reportManager.search.service;

import java.util.List;

import com.huge.ihos.system.reportManager.search.model.Test;
import com.huge.service.GenericManager;

public interface TestManager
    extends GenericManager<Test, Long> {
    public List<Test> getAllTest();
}