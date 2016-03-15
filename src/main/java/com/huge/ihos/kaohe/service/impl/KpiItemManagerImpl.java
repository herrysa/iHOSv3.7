package com.huge.ihos.kaohe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.formula.dao.FormulaFieldDao;
import com.huge.ihos.formula.model.FormulaField;
import com.huge.ihos.kaohe.dao.KpiItemDao;
import com.huge.ihos.kaohe.model.KpiItem;
import com.huge.ihos.kaohe.service.KpiItemManager;
import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.service.impl.BaseTreeManagerImpl;

@Service( "kpiItemManager" )
public class KpiItemManagerImpl
    extends BaseTreeManagerImpl<KpiItem, Long>
    implements KpiItemManager {
    private KpiItemDao kpiItemDao;
    private DepartmentDao departmentDao;
    private FormulaFieldDao formulaFieldDao;
  /*  public KpiItemDao getKpiItemDao() {
        return kpiItemDao;
    }
    @Autowired
    public void setKpiItemDao( KpiItemDao kpiItemDao ) {
        this.kpiItemDao = kpiItemDao;
    }*/

    @Autowired
    public KpiItemManagerImpl( KpiItemDao kpiItemDao ) {
        super( kpiItemDao );
        this.kpiItemDao = kpiItemDao;
    }

    public DepartmentDao getDepartmentDao() {
        return departmentDao;
    }
    @Autowired
    public void setDepartmentDao( DepartmentDao departmentDao ) {
        this.departmentDao = departmentDao;
    }

    public FormulaFieldDao getFormulaFieldDao() {
        return formulaFieldDao;
    }
    @Autowired
    public void setFormulaFieldDao( FormulaFieldDao formulaFieldDao ) {
        this.formulaFieldDao = formulaFieldDao;
    }

    @Override
    public KpiItem insertNode( KpiItem node ) {
        this.precessBeforeSave( node );
        return super.insertNode( node );
    }

    @Override
    public KpiItem save( KpiItem object ) {
        this.precessBeforeSave( object );
        return super.save( object );
    }

    private KpiItem precessBeforeSave(KpiItem item){
        FormulaField tar = item.getTargetField();
        FormulaField sco = item.getScoreField();
        FormulaField act = item.getActualField();
        Department dep = item.getExecuteDept();
        
        if(tar!=null && tar.getFormulaFieldId()!=null && !tar.getFormulaFieldId().equals( "" )){
            item.setTargetField( this.formulaFieldDao.get( tar.getFormulaFieldId() ) );
        }else{
            item.setTargetField(null);
        }
        if(sco!=null && sco.getFormulaFieldId()!=null && !sco.getFormulaFieldId().equals( "" )){
            item.setScoreField(  this.formulaFieldDao.get( sco.getFormulaFieldId() ) );
        }else{
            item.setScoreField(null);
        }
        if(act!=null && act.getFormulaFieldId()!=null && !act.getFormulaFieldId().equals( "" )){
            item.setActualField(   this.formulaFieldDao.get( act.getFormulaFieldId() ) );
        }else{
            item.setActualField(null);
        }
        
        if(dep!=null && dep.getDepartmentId()!=null && !dep.getDepartmentId().equals( "" )){
            item.setExecuteDept(    this.departmentDao.get( dep.getDepartmentId() ) );
        }else{
            item.setExecuteDept(null);
        }
        
        
            
         return item;   
    }

    @Override
    public List<KpiItem> getTreeByRootIdAndPeriodType( Long rootId, String periodType ) {
        List l1= this.kpiItemDao.getTreeNoLeafByRootIdAndLeafLessThree( rootId );//ByRootIdAndPeriodType(  rootId,  periodType );
        List l2 = this.kpiItemDao.getTreeLeafByRootIdAndPeriodType( rootId, periodType );
        l1.addAll( l2 );
        return l1;
    }
    @Override
    public List<KpiItem> getFullTreeByRootId( Long rootId ){
        return this.kpiItemDao.getFullTreeByRootId( rootId );
    }

    @Override
    public void processNodeLeaf() {
        this.kpiItemDao.processNodeLeaf();
        
    }
}
