package com.huge.ihos.formula.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.formula.dao.FormulaEntityDao;
import com.huge.ihos.formula.model.FormulaEntity;
import com.huge.ihos.formula.service.FormulaEntityManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "formulaEntityManager" )
public class FormulaEntityManagerImpl
    extends GenericManagerImpl<FormulaEntity, String>
    implements FormulaEntityManager {
    FormulaEntityDao formulaEntityDao;

    @Autowired
    public FormulaEntityManagerImpl( FormulaEntityDao formulaEntityDao ) {
        super( formulaEntityDao );
        this.formulaEntityDao = formulaEntityDao;
    }

    public JQueryPager getFormulaEntityCriteria( JQueryPager paginatedList ) {
        return formulaEntityDao.getFormulaEntityCriteria( paginatedList );
    }

}