package com.huge.ihos.formula.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.formula.dao.FormulaEntityDao;
import com.huge.ihos.formula.model.FormulaEntity;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "formulaEntityDao" )
public class FormulaEntityDaoHibernate
    extends GenericDaoHibernate<FormulaEntity, String>
    implements FormulaEntityDao {

    public FormulaEntityDaoHibernate() {
        super( FormulaEntity.class );
    }

    public JQueryPager getFormulaEntityCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                // paginatedList.setSortCriterion("formulaEntityId");
                paginatedList.setSortCriterion( null );

            // TODO create a callback like this:HibernateCallback callback = new
            // FormulaEntityByParentCallBack(paginatedList, FormulaEntity.class,
            // parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, FormulaEntity.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getFormulaEntityCriteria", e );
            return paginatedList;
        }
    }

    /*
     * class FormulaEntityByParentCallBack extends JqueryPagerHibernateCallBack
     * { Long parentId;
     * 
     * FormulaEntityByParentCallBack(final JQueryPager paginatedList, final
     * Class object, Long parentId) { super(paginatedList, object);
     * this.parentId = parentId; }
     * 
     * @Override public Criteria getCustomCriterion(Criteria criteria) {
     * Restrictions.eq("formulaEntity.formulaEntityId", parentId); return
     * criteria; } }
     */

    public FormulaEntity getByTableName( String tableName ) {
        String hql = "from FormulaEntity fe where fe.tableName=?";

        List list = this.getHibernateTemplate().find( hql, tableName );

        if ( list.size() > 0 )
            return (FormulaEntity) list.get( 0 );
        else
            return null;
    }
}
