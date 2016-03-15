package com.huge.ihos.formula.service;

import com.huge.ihos.formula.model.FormulaEntity;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface FormulaEntityManager
    extends GenericManager<FormulaEntity, String> {
    public JQueryPager getFormulaEntityCriteria( final JQueryPager paginatedList );
}