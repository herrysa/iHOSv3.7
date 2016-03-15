package com.huge.ihos.formula.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.formula.dao.FormulaEntityDao;
import com.huge.ihos.formula.dao.FormulaFieldDao;
import com.huge.ihos.formula.model.FormulaEntity;
import com.huge.ihos.formula.model.FormulaField;
import com.huge.ihos.formula.service.FormulaFieldManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "formulaFieldManager" )
public class FormulaFieldManagerImpl
    extends GenericManagerImpl<FormulaField, String>
    implements FormulaFieldManager {
    FormulaFieldDao formulaFieldDao;

    FormulaEntityDao formulaEntityDao;

    public FormulaEntityDao getFormulaEntityDao() {
        return formulaEntityDao;
    }

    @Autowired
    public void setFormulaEntityDao( FormulaEntityDao formulaEntityDao ) {
        this.formulaEntityDao = formulaEntityDao;
    }

    @Autowired
    public FormulaFieldManagerImpl( FormulaFieldDao formulaFieldDao ) {
        super( formulaFieldDao );
        this.formulaFieldDao = formulaFieldDao;
    }

    public JQueryPager getFormulaFieldCriteria( JQueryPager paginatedList, String searchEntity, String searchFieldName, String searchFieldTitle,
                                                String searchDisabled ) {
        return formulaFieldDao.getFormulaFieldCriteria( paginatedList, searchEntity, searchFieldName, searchFieldTitle, searchDisabled );
    }

    public String initFormulaFieldByTargetTable( String tableName ) {
        FormulaEntity fe = this.formulaEntityDao.getByTableName( tableName );

        return this.formulaFieldDao.initFormulaFieldByTargetTable( fe );
    }

    public String updateDisabled( String id, boolean value ) {
        //		String hql = "";
        return this.formulaFieldDao.updateDisabled( id, value );
    }

    public List getAllDistinctKeyclass() {
        return this.formulaFieldDao.getAllDistinctKeyclass();
    }

    public List getAllFormulaFieldByEntityId( String entityId ) {
        return this.formulaFieldDao.getAllFormulaFieldByEntityId( entityId );
    }

    public List getAllFormulaField() {
        return this.formulaFieldDao.getAllFormulaField();
    }

    /*public List getAllFormulaFieldByquickSelect(String entityId,String quickSelect) {
    	return this.formulaFieldDao.getAllFormulaFieldByquickSelect(entityId,quickSelect);
    }*/
    public List getAllDeptKeyByQuickSelect( String entityId, String quickKeyStr ) {
        return formulaFieldDao.getAllDeptKeyByQuickSelect( entityId, quickKeyStr );
    }
}
