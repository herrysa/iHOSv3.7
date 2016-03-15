package com.huge.ihos.system.reportManager.search.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.reportManager.search.model.SearchEntity;

public interface SearchEntityDao
    extends GenericDao<SearchEntity, Long> {
    SearchEntity getSearchEntityByName( String name );
}
