package com.huge.ihos.formula.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.formula.model.FormulaEntity;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the FormulaEntity table.
 */
public interface FormulaEntityDao
    extends GenericDao<FormulaEntity, String> {

    public FormulaEntity getByTableName( String tableName );

    public JQueryPager getFormulaEntityCriteria( final JQueryPager paginatedList );
}