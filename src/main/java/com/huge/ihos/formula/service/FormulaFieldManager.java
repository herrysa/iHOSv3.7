package com.huge.ihos.formula.service;

import java.util.List;

import com.huge.ihos.formula.model.FormulaField;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface FormulaFieldManager
    extends GenericManager<FormulaField, String> {
    public JQueryPager getFormulaFieldCriteria( final JQueryPager paginatedList, String searchEntity, String searchFieldName,
                                                String searchFieldTitle, String searchDisabled );

    public String initFormulaFieldByTargetTable( String tableName );

    public String updateDisabled( String id, boolean value );

    public List getAllDistinctKeyclass();

    public List getAllFormulaFieldByEntityId( String entityId );

    public List getAllFormulaField();

    public List getAllDeptKeyByQuickSelect( String entityId, String quickKeyStr );
}