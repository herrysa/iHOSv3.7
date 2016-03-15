package com.huge.ihos.formula.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.formula.model.FormulaEntity;
import com.huge.ihos.formula.model.FormulaField;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the FormulaField
 * table.
 */
public interface FormulaFieldDao
    extends GenericDao<FormulaField, String> {

    public JQueryPager getFormulaFieldCriteria( final JQueryPager paginatedList, String searchEntity, String searchFieldName,
                                                String searchFieldTitle, String searchDisabled );

    public String initFormulaFieldByTargetTable( FormulaEntity en );

    public String updateDisabled( String id, boolean value );

    public String convertToSqlExpress( String source, final FormulaField sff );

    public List getAllFormulaFieldByEntityId( String entityId );

    public List getAllFormulaField();

    /*public List getAllFormulaFieldByquickSelect(String entityId,String quickSelect);*/
    public List getAllDistinctKeyclass();

    public List getAllDeptKeyByQuickSelect( String entityId, String quickKeyStr );

}
