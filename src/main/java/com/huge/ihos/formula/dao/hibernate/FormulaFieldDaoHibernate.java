package com.huge.ihos.formula.dao.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.foundation.util.StringUtil;
import com.huge.ihos.formula.dao.FormulaFieldDao;
import com.huge.ihos.formula.model.Formula;
import com.huge.ihos.formula.model.FormulaEntity;
import com.huge.ihos.formula.model.FormulaField;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.SpringContextHelper;

@Repository( "formulaFieldDao" )
public class FormulaFieldDaoHibernate
    extends GenericDaoHibernate<FormulaField, String>
    implements FormulaFieldDao {

    public FormulaFieldDaoHibernate() {
        super( FormulaField.class );
    }

    public JQueryPager getFormulaFieldCriteria( JQueryPager paginatedList, String searchEntity, String searchFieldName, String searchFieldTitle,
                                                String searchDisabled ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                // paginatedList.setSortCriterion("formulaFieldId");
                paginatedList.setSortCriterion( null );

            HibernateCallback callback =
                new FormulaFieldSearchCallBack( paginatedList, FormulaField.class, searchEntity, searchFieldName, searchFieldTitle, searchDisabled );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, FormulaField.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getFormulaFieldCriteria", e );
            return paginatedList;
        }
    }

    class FormulaFieldSearchCallBack
        extends JqueryPagerHibernateCallBack {
        String searchEntity;

        String searchFieldName;

        String searchFieldTitle;

        String searchDisabled;

        FormulaFieldSearchCallBack( final JQueryPager paginatedList, final Class object, String searchEntity, String searchFieldName,
                                    String searchFieldTitle, String searchDisabled ) {
            super( paginatedList, object );
            this.searchEntity = searchEntity;
            this.searchFieldName = searchFieldName;
            this.searchFieldTitle = searchFieldTitle;
            this.searchDisabled = searchDisabled;
        }

        @Override
        public Criteria getCustomCriterion( Criteria criteria ) {

            /*
             * criteria.add(Restrictions.eq("status", status));
             * criteria.createAlias("dataCollectionPeriod", "period");
             * criteria.add(Restrictions.eq("period.periodCode", periodCode));
             */

            if ( this.searchEntity != null && !this.searchEntity.equalsIgnoreCase( "" ) && !this.searchEntity.equalsIgnoreCase( "all" ) ) {
                criteria.createAlias( "formulaEntity", "entity" );
                criteria.add( Restrictions.eq( "entity.tableName", this.searchEntity ) );
            }
            if ( this.searchFieldName != null && !this.searchFieldName.equalsIgnoreCase( "" ) ) {
                criteria.add( Restrictions.like( "fieldName", this.searchFieldName, MatchMode.ANYWHERE ) );
            }
            if ( this.searchFieldTitle != null && !this.searchFieldTitle.equalsIgnoreCase( "" ) ) {
                criteria.add( Restrictions.like( "fieldTitle", this.searchFieldTitle, MatchMode.ANYWHERE ) );
            }
            if ( this.searchDisabled != null && !this.searchDisabled.equalsIgnoreCase( "" ) ) {
                Boolean dis = Boolean.parseBoolean( this.searchDisabled );
                criteria.add( Restrictions.eq( "disabled", dis ) );
            }
            return criteria;
        }
    }

    public String initFormulaFieldByTargetTable( FormulaEntity en ) {

        String hqlDelete = "delete FormulaField ff where ff.formulaEntity.formulaEntityId=?";
        this.getHibernateTemplate().bulkUpdate( hqlDelete, en.getFormulaEntityId() );

        String sql = "select * from " + en.getTableName() + " where 1=2";

        JdbcTemplate jt = new JdbcTemplate( SpringContextHelper.getDataSource() );
        SqlRowSet srs = jt.queryForRowSet( sql );
        SqlRowSetMetaData rmd = srs.getMetaData();
        for ( int i = 0; i < rmd.getColumnCount(); i++ ) {
            // System.out.println("fieldName:" + rmd.getColumnName(i + 1) +
            // " typeCode:" + rmd.getColumnType(i + 1) + " typeName:" +
            // rmd.getColumnTypeName(i + 1));
            System.out.println( en.getTableName() + "_" + rmd.getColumnName( i + 1 ) );
            if ( rmd.getColumnType( i + 1 ) == 3 ) {
                FormulaField ff = new FormulaField();
                ff.setFormulaEntity( en );
                ff.setFormulaFieldId( en.getTableName() + "_" + rmd.getColumnName( i + 1 ) );
                ff.setFieldName( rmd.getColumnName( i + 1 ) );
                ff.setFieldTitle( rmd.getColumnName( i + 1 ) );
                ff.setCalcType( "手工" );
                ff.setDefaultValue( "" );
                ff.setDirection( "" );
                ff.setKeyClass( "" );
                ff.setFormula( new Formula() );
                ff.setDisabled( true );
                ff.setExecOrder( 0 );
                // this.log.error(ff.toString());
                this.save( ff );
            }
            // System.out.println("fieldName:" + rmd.getColumnName(i + 1) +
            // " typeCode:" + rmd.getColumnType(i + 1) + " typeName:" +
            // rmd.getColumnTypeName(i + 1));

        }

        // TODO Auto-generated method stub
        return null;
    }

    public String updateDisabled( String id, boolean value ) {
        String hql = "update FormulaField ff set ff.disabled=? where ff.formulaFieldId=?";
        int ret = this.getHibernateTemplate().bulkUpdate( hql, value, id );
        return ret + "";
    }

    public FormulaField save( FormulaField ff ) {
        Formula fa = ff.getFormula();
        fa.setSqlExpress( convertToSqlExpress( fa.getFormula(), ff ) );
        fa.setSqlCondition( convertToSqlExpress( fa.getCondition(), ff ) );
        ff.setFormula( fa );
        return super.save( ff );
    }

    /* (non-Javadoc)
     * @see com.huge.ihos.formula.dao.FormulaFieldDao#convertToSqlExpress(java.lang.String, com.huge.ihos.formula.model.FormulaField)
     */
    public String convertToSqlExpress( String source, final FormulaField sff ) {
        List fields = this.getAllFormulaFieldByEntityId( sff.getFormulaEntity().getFormulaEntityId() );
        fields.add( sff );

        Iterator itr = fields.iterator();
        while ( itr.hasNext() ) {
            FormulaField ff = (FormulaField) itr.next();
            String fn = ff.getFieldName();
            String ft = "{"+ff.getFieldTitle()+"}";

            while(source.indexOf( ft )>0){
            
            
            
            /*source = StringUtil.replaceString( source, ft + " ", fn + " " );
            source = StringUtil.replaceString( source, ft + "=", fn + "=" );
            source = StringUtil.replaceString( source, ft + "+", fn + "+" );
            source = StringUtil.replaceString( source, ft + "-", fn + "-" );
            source = StringUtil.replaceString( source, ft + "*", fn + "*" );
            source = StringUtil.replaceString( source, ft + "/", fn + "/" );
            source = StringUtil.replaceString( source, ft + ")", fn + ")" );
            source = StringUtil.replaceString( source, ft + ",", fn + "," );
            source = StringUtil.replaceString( source, "(" + ft, "(" + fn );
            source = StringUtil.replaceString( source, ft + ">", fn + ">" );
            source = StringUtil.replaceString( source, ft + "<", fn + "<" );
            source = StringUtil.replaceString( source, ft + "\r\n", fn + "\r\n" );*/
            source = StringUtil.replaceString( source, ft , fn );
            
            }

        }
        source = StringUtil.replaceString( source, "核算期间", "checkPeriod" );
        return source;

    }

    public List getAllFormulaFieldByEntityId( String entityId ) {

        String hql = "from FormulaField ff where ff.formulaEntity.formulaEntityId=? and ff.disabled=false order by ff.formulaFieldId";
        List list = this.getHibernateTemplate().find( hql, entityId );
        return list;
    }

    public List getAllDeptKeyByQuickSelect( String entityId, String quickKeyStr ) {

        String hql =
            "from FormulaField ff where ff.formulaEntity.formulaEntityId=? and ( ff.fieldTitle like '%" + quickKeyStr + "%' or ff.fieldName like '%"
                + quickKeyStr + "%') and ff.disabled=false order by ff.formulaFieldId";
        List list = this.getHibernateTemplate().find( hql, entityId );
        return list;
    }

    public List getAllFormulaField() {

        String hql = "from FormulaField ff where ff.disabled=false order by ff.formulaFieldId";
        List list = this.getHibernateTemplate().find( hql );
        return list;
    }

    /*public List getAllFormulaFieldByquickSelect(String entityId,String quickSelect) {
    	
    	String sql = " select * from t_formulaField ff where ff.formulaEntityId=? and ff.disabled=0 and(ff.name like '%"+quickSelect+"%' or ff.deptCode like '%"+quickSelect+"%') order by ff.formulaFieldId";
    	List list = (List) this.sessionFactory.getCurrentSession().createSQLQuery(sql);
    	return list;
    }*/

    public List getAllDistinctKeyclass() {
        String sql = "select distinct keyclass from t_formulafield";
        Query query = (Query) this.sessionFactory.getCurrentSession().createSQLQuery( sql );
        List<FormulaField> list = query.list();
        return list;
    }

}
