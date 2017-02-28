package com.huge.dao.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huge.dao.GenericDao;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.system.configuration.bdinfo.util.BdInfoUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.SortOrderEnum;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.PropertyFilter.MatchType;

/**
 * This class serves as the Base class for all other DAOs - namely to hold common CRUD methods that they might all use.
 * You should only need to extend this class when your require custom CRUD logic.
 * <p/>
 * <p>
 * To register this class in your Spring context file, use the following XML.
 * 
 * <pre>
 *      &lt;bean id="fooDao" class="com.huge.dao.hibernate.GenericDaoHibernate"&gt;
 *          &lt;constructor-arg value="com.huge.model.Foo"/&gt;
 *      &lt;/bean&gt;
 * </pre>
 * 
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public class GenericDaoHibernate<T, PK extends Serializable>
    implements GenericDao<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog( getClass() );

    private Class<T> persistentClass;

    protected HibernateTemplate hibernateTemplate;

    protected SessionFactory sessionFactory;

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public void setPersistentClass( Class<T> persistentClass ) {
        this.persistentClass = persistentClass;
    }

    /**
     * Constructor that takes in a class to see which type of entity to persist. Use this constructor when subclassing.
     * 
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoHibernate( final Class<T> persistentClass ) {
        this.persistentClass = persistentClass;
    }

    /**
     * Constructor that takes in a class and sessionFactory for easy creation of DAO.
     * 
     * @param persistentClass the class type you'd like to persist
     * @param sessionFactory the pre-configured Hibernate SessionFactory
     */
    public GenericDaoHibernate( final Class<T> persistentClass, SessionFactory sessionFactory ) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate( sessionFactory );
    }

    public HibernateTemplate getHibernateTemplate() {
        return this.hibernateTemplate;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Autowired
    @Required
    public void setSessionFactory( SessionFactory sessionFactory ) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate( sessionFactory );
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings( "unchecked" )
    public List<T> getAll() {
        return hibernateTemplate.loadAll( this.persistentClass );
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings( "unchecked" )
    public List<T> getAllDistinct() {
        Collection result = new LinkedHashSet( getAll() );
        return new ArrayList( result );
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings( "unchecked" )
    public T get( PK id ) {
        T entity = (T) hibernateTemplate.get( this.persistentClass, id );

        if ( entity == null ) {
            log.warn( "Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found..." );
            throw new ObjectRetrievalFailureException( this.persistentClass, id );
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings( "unchecked" )
    public boolean exists( PK id ) {
        T entity = (T) hibernateTemplate.get( this.persistentClass, id );
        return entity != null;
    }
    
    @Override
	public boolean existByExample(T object) {
		List<T> list = this.getByExample(object);
		if(list!=null&&list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean existByFilter(List<PropertyFilter> filters) {
		List<T> list = this.getByFilters(filters);
		if(list!=null&&list.size()>0){
			return true;
		}else{
			return false;
		}
	}

    @SuppressWarnings( "unchecked" )
    public boolean existCode( String object, String columnName, String valueCode, String oper ) {
         String sql="select t."+columnName+" from "+object+" t where t."+columnName+"='"+valueCode+"'";
        //String sql = "select t.? from ? t where t.?=?";
        Query query = this.sessionFactory.getCurrentSession().createSQLQuery( sql );
        /*query.setString( 0, columnName );
        query.setString( 1, object );
        query.setString( 2, columnName );
        query.setString( 3, valueCode );*/
        List list=query.list();
        if(list.size()==0){
        	return false;
        }
        //boolean b = query.isReadOnly();
        return true;
    }
    public boolean existCodeEdit(Long idValue,String tableName,String idColumnName,String columnName,String valueCode){
    	String sql="select t."+idColumnName+" from "+tableName+" t where t."+columnName+"='"+valueCode+"'";
    	Query query = this.sessionFactory.getCurrentSession().createSQLQuery( sql );
    	List list=query.list();
    	if (list.size()==0) {
			return false;
		}
    	Long oldId=((BigDecimal) list.get(0)).longValue();
    	if (oldId.compareTo(idValue)==0) {
    		return false;
		}
    	return true;
    }
    public boolean existCodeEdit(Long idValue,String tableName,String idColumnName,String columnName,String valueCode,String columnName2,String valueCode2){
    	String sql="select t."+idColumnName+" from "+tableName+" t where t."+columnName+"='"+valueCode+"' and t."+columnName2+"='"+valueCode2+"'";
    	Query query = this.sessionFactory.getCurrentSession().createSQLQuery( sql );
    	List list=query.list();
    	if (list.size()==0) {
			return false;
		}
    	Long oldId=((BigDecimal) list.get(0)).longValue();
    	if (oldId.compareTo(idValue)==0) {
    		return false;
    	}
    	return true;
    }
    @SuppressWarnings( "unchecked" )
    public boolean existCode( String object, String columnName, String columnName2, String valueCode, String valueCode2 ) {
    	String sql="select t."+columnName+" from "+object+" t where t."+columnName+"='"+valueCode+"' and t."+columnName2+"='"+valueCode2+"'";
    	Query query = this.sessionFactory.getCurrentSession().createSQLQuery( sql );
    	List list=query.list();
    	if(list.size()==0){
    		return false;
    	}
    	return true;
    }

    public String getPyCodes( String str ) {
        String hql = "select dbo.func_getpycodes(?)";
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery( hql );
        query.setString( 0, str );
        String valueStr = (String) query.list().get( 0 );

        return valueStr;
    }

    public List searchDictionary( String entityName, String str ) {
        String hql = "from " + entityName + " where " + str;
        List list = getHibernateTemplate().find( hql );
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings( "unchecked" )
    public T save( T object ) {
        return (T) hibernateTemplate.merge( object );
    }

    /**
     * {@inheritDoc}
     */
    public void remove( PK id ) {
        hibernateTemplate.delete( this.get( id ) );
    }
    
    public void remove(PK[] ids) {
		for (int i = 0; i < ids.length; i++) {
			hibernateTemplate.delete(this.get(ids[i]));
		}
	}

    @Override
    public void removeAll() {
        hibernateTemplate.deleteAll( this.getAll() );

    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings( "unchecked" )
    public List<T> findByNamedQuery( String queryName, Map<String, Object> queryParams ) {
        String[] params = new String[queryParams.size()];
        Object[] values = new Object[queryParams.size()];

        int index = 0;
        for ( String s : queryParams.keySet() ) {
            params[index] = s;
            values[index++] = queryParams.get( s );
        }

        return hibernateTemplate.findByNamedQueryAndNamedParam( queryName, params, values );
    }

    /* (non-Javadoc)
     * @see com.huge.dao.GenericDao#getAppManagerCriteria(com.huge.webapp.pagers.JQueryPager, java.lang.Class, org.springframework.orm.hibernate3.HibernateCallback)
     */
    @SuppressWarnings( "unchecked" )
    public Map<String, Object> getAppManagerCriteria( final JQueryPager paginatedList, final Class object, HibernateCallback cusCallBack ) {
        log.debug( "getAppManagerCriteria" );
        log.debug( "paginatedList " + paginatedList );

        HibernateCallback callback;
        if ( cusCallBack != null )
            callback = cusCallBack;
        else
            callback = new DefaultJQueryCriterionHibernateCallBack( paginatedList, object );
        try {
            //
            //this.getHibernateTemplate().setEntityInterceptor(new EntityExecInterceptor());
            
            
           // this.getHibernateTemplate().e
            Map<String, Object> resultMap = (Map<String, Object>) getHibernateTemplate().execute( callback );
            return resultMap;
        }
        catch ( Exception e ) {
            log.error( "Get Crtiteria Error", e );
            List recList = new ArrayList();
            int c = 0;
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put( "list", recList );
            resultMap.put( "count", new Integer( c ) );
            return resultMap;

        }

    }

    @SuppressWarnings( "unchecked" )
    public Map<String, Object> getAppManagerCriteriaWithSearch( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters ) {
        HibernateCallback cusCallBack = new JqueryPagerHibernateWithSearchCallBack( paginatedList, object, filters );
        return this.getAppManagerCriteria( paginatedList, object, cusCallBack );
    }

    @SuppressWarnings( "unchecked" )
    public Map<String, Object> getAppManagerCriteriaWithSearch( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters,
                                                                String group_on ) {
        HibernateCallback cusCallBack = new JqueryPagerHibernateWithSearchCallBack( paginatedList, object, filters, group_on );
        return this.getAppManagerCriteria( paginatedList, object, cusCallBack );
    }

    // JqueryPagerHibernateWithSearchCallBack

    /**
     * ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc'] ['equal','not equal', 'less', 'less or
     * equal','greater','greater or equal', 'begins with','does not begin with','is in','is not in', 'ends with','does
     * not end with','contains','does not contain']
     */

    /*
     * private Criterion createCriterion(String field, String oper, String data) { if (oper.equalsIgnoreCase("bw")) {
     * data = data + "%"; return Restrictions.like(field, data).ignoreCase(); } if (oper.equalsIgnoreCase("cn")) { data
     * = "%" + data + "%"; return Restrictions.like(field, data).ignoreCase(); } if (oper.equalsIgnoreCase("eq")) {
     * return Restrictions.eq(field, data).ignoreCase(); } return null; }
     */

    /**
     * 这个地方应该可以优化
     * 
     * @param clazz
     * @param field
     * @param data
     * @return
     */
    /*
     * private Object convertFieldStringToObject(Class clazz, String field, String data) { Class fieldType = null; try {
     * fieldType = clazz.getField(field).getType(); Object fieldobj = fieldType.newInstance(); if (fieldobj instanceof
     * String) { return data; } if (fieldobj instanceof java.util.Date) { return DateUtil.convertStringToDate(data); }
     * if (fieldobj instanceof Boolean) { return new BooleanConverter().convert(fieldType, data); } if(fieldobj
     * instanceof Integer){ return new IntegerConverter().convert(fieldType, data); } if(fieldobj instanceof Double){
     * return new DoubleConverter().convert(fieldType, data); } if(fieldobj instanceof Float){ return new
     * FloatConverter().convert(fieldType, data); } if(fieldobj instanceof Long){ return new
     * LongConverter().convert(fieldType, data); } } catch (SecurityException e) { 
     * e.printStackTrace(); } catch (NoSuchFieldException e) {  e.printStackTrace(); }
     * catch (InstantiationException e) {  e.printStackTrace(); } catch
     * (IllegalAccessException e) {  e.printStackTrace(); } catch (ParseException e) {
     *  e.printStackTrace(); } return data; }
     */
    class DefaultJQueryCriterionHibernateCallBack
        implements HibernateCallback {
        JQueryPager paginatedList;

        Class object;

        DefaultJQueryCriterionHibernateCallBack( final JQueryPager paginatedList, final Class object ) {
            this.paginatedList = paginatedList;
            this.object = object;
        }

        public Object doInHibernate( Session session )
            throws HibernateException, SQLException {
            
           // String entityName = ActionEntityThreadLocalHolder.getActionEntityName();
            
            //TODO 加入实体执行回调
            session = ActionEntityHelper.getSession( session );//openSession( new EntityExecInterceptor(entityName) );
            
           // ActionEntityThreadLocalHolder.setActionEntityName( null );
            
            
            
            Criteria criteria = session.createCriteria( object );

            String searchField = paginatedList.getSearchField();
            String searchFor = paginatedList.getSearchFor();
            String orderBy = paginatedList.getSortCriterion();

            String groupOp = paginatedList.getGroupOp();
            String[] searchFields = paginatedList.getSearchFields();
            String[] searchOps = paginatedList.getSearchOpers();
            String[] searchFors = paginatedList.getSearchFors();

            criteria.setFirstResult( paginatedList.getStart() );
            criteria.setMaxResults( paginatedList.getPageSize() );

            /*
             * if (orderBy != null) { String[] orderByArr = orderBy.split("\\."); int i=0; for(String
             * ordeLavel:orderByArr){ if(i==orderByArr.length-1){ if (paginatedList.getSortDirection() ==
             * SortOrderEnum.DESCENDING) criteria.addOrder(Order.desc(ordeLavel)); else
             * criteria.addOrder(Order.asc(ordeLavel)); break; } criteria = criteria.createCriteria(ordeLavel);
             * //criteria =criteria.createAlias(ordeLavel, ordeLavel); //criteria =
             * CriteriaUtil.createAliasCriteria((CriteriaImpl) criteria, ordeLavel); i++; } if(i==0){ if
             * (paginatedList.getSortDirection() == SortOrderEnum.DESCENDING) criteria.addOrder(Order.desc(orderBy));
             * else criteria.addOrder(Order.asc(orderBy)); } // if (paginatedList.getSortDirection() ==
             * SortOrderEnum.DESCENDING) //
             * criteria.createCriteria(orderTemp1).createCriteria(orderTemp2).addOrder(Order.desc(orderBy)); //
             * //criteria.addOrder(Order.desc(orderBy)); // else //
             * criteria.createCriteria(orderTemp1).createCriteria(orderTemp2).addOrder(Order.asc(orderBy)); //
             * //criteria.addOrder(Order.asc(orderBy)); }
             */

            if ( orderBy != null ) {
                //criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, orderBy, );
                criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, orderBy, CriteriaSpecification.LEFT_JOIN );
                if ( paginatedList.getSortDirection() == SortOrderEnum.DESCENDING )
                    criteria.addOrder( Order.desc( orderBy ) );
                else
                    criteria.addOrder( Order.asc( orderBy ) );
            }

            boolean search = false;
            if ( groupOp == null ) {
                if ( searchField != null && searchFor != null ) {
                    search = true;
                    log.debug( "Setting search option " + searchField );
                    String searchOper = paginatedList.getSearchOper();
                    if ( searchOper == null )
                        searchOper = "bw";
                    /*
                     * criteria.add(createCriterion(searchField, searchOper, searchFor));
                     */
                    try {
                        criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, searchField );
                        criteria.add( CriteriaUtil.createCriterion( object, searchField, searchOper, searchFor ) );

                    }
                    catch ( Exception e ) {
                        e.printStackTrace();
                    }

                }
            }
            else {

                if ( ( searchFields.length > 0 ) && ( searchFields.length == searchFors.length ) ) {
                    search = true;
                    Junction op = null;
                    if ( groupOp.equalsIgnoreCase( "and" ) )
                        op = Restrictions.conjunction();
                    else
                        op = Restrictions.disjunction();
                    for ( int i = 0; i < searchFields.length; i++ ) {
                        /*
                         * op.add(createCriterion(searchFields[i], searchOps[i], searchFors[i]));
                         */

                        try {
                            criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, searchFields[i] );
                            op.add( CriteriaUtil.createCriterion( object, searchFields[i], searchOps[i], searchFors[i] ) );
                        }
                        catch ( Exception e ) {
                            e.printStackTrace();
                        }
                    }
                    criteria.add( op );

                }
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();
            List recList = criteria.list();
            resultMap.put( "list", recList );
            int count = 0;
            if ( search ) {
                criteria.setProjection( Projections.rowCount() );
                List countList = criteria.list();
                if ( !countList.isEmpty() )
                    count = ( (Long) countList.get( 0 ) ).intValue();
                resultMap.put( "count", new Integer( count ) );
            }
            else {
                Criteria criteriaC = session.createCriteria( object );
                criteriaC.setProjection( Projections.rowCount() );
                List countList = criteriaC.list();
                if ( !countList.isEmpty() )
                    count = ( (Long) countList.get( 0 ) ).intValue();
                resultMap.put( "count", new Integer( count ) );

            }

            return resultMap;

        }

    }

    @Override
    public List<T> getAllExceptDisable() {
        this.getHibernateTemplate().enableFilter( "disableFilter" ).setParameter( "disabled", true );
        return getAll();
    }

    @Override
    public List<T> get( PK... ids ) {
        Criteria c = this.sessionFactory.getCurrentSession().createCriteria( this.persistentClass );
        c.add( Restrictions.in( "id", ids ) );
      //  Object[] retVal = (Object[]) Array.newInstance( this.persistentClass, ids.length );
 
        return c.list();
    }
	@Override
	public String getAmountSum(String object, String column,
			String currentPeriod) {
		String sum = "0";
        try {
            List amount = getHibernateTemplate().find( "select sum(sp."+column+") from "+object+" sp where sp.checkPeriod='" + currentPeriod + "'" );
            if ( amount != null && amount.size() != 0 ) {
                if(amount.get(0)!=null){
                	sum = amount.get( 0 ).toString();
                }
            }
        }
        catch ( Exception e ) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return sum;
	}

	@Override
	public Criteria getCriteria() {
		Criteria c = this.sessionFactory.getCurrentSession().createCriteria( this.persistentClass );
		return c;
	}

	@Override
	public List<T> getByFilters(List<PropertyFilter> filters){
		try {
			Criteria criteria = getCriteria();
			Iterator<PropertyFilter> it = filters.iterator();
			 while ( it.hasNext() ) {
	                PropertyFilter pf = (PropertyFilter) it.next();
	                criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName() ,1);
	                String fieldName= CriteriaUtil.getSearchAliasFieldname(pf.getPropertyName());
	                if ( pf.getMatchType().equals( MatchType.LIKE ) ) {
	                    String v = (String) pf.getMatchValue();
	                    boolean bp = v.startsWith( "*" );
	                    boolean ep = v.endsWith( "*" );
	                    v = v.replaceAll( "\\*", "" );
	                    if ( bp && ep )
	                        criteria.add( Restrictions.like( fieldName, v, MatchMode.ANYWHERE ) );
	                    else if ( bp && !ep )
	                        criteria.add( Restrictions.like( fieldName, v, MatchMode.END ) );

	                    else if ( !bp && ep )
	                        criteria.add( Restrictions.like( fieldName, v, MatchMode.START ) );
	                    else
	                        criteria.add( Restrictions.like( fieldName, v, MatchMode.EXACT ) );

	                }
	                else if ( pf.getMatchType().equals( MatchType.EQ ) ) {
	                    criteria.add( Restrictions.eq( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.GE ) ) {
	                    criteria.add( Restrictions.ge( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.GT ) ) {
	                    criteria.add( Restrictions.gt( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.IN ) ) {
//	                	Object[] matchValueObjs = (Object[]) pf.getMatchValue();
//	                	int mvLength = matchValueObjs.length;
//	                	if(mvLength > 1000){
//	                		Criterion criterion = null;
//	                		Object[] newObjs = OtherUtil.splitArray(matchValueObjs, 1000);
//	                		for (Object objTemp : newObjs) {
//	                			Object[] objsTemp = (Object[])objTemp;
//	                			criterion = Restrictions.or(criterion, Restrictions.in(fieldName, objsTemp));
//	                		}
//	                		criteria.add(criterion);
//	                	}else{
//	                		criteria.add( Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) );
//	                	}
	                	criteria.add( Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.NI ) ) {
	                    criteria.add( Restrictions.not(Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) ));
	                }
	                else if ( pf.getMatchType().equals( MatchType.ISNOTNULL ) ) {
	                    criteria.add( Restrictions.isNotNull( fieldName ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.ISNULL ) ) {
	                    criteria.add( Restrictions.isNull( fieldName ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.LE ) ) {
	                    criteria.add( Restrictions.le( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.LT ) ) {
	                    criteria.add( Restrictions.lt( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.NE ) ) {
	                    criteria.add( Restrictions.ne( fieldName, pf.getMatchValue() ) );
	                }else if ( pf.getMatchType().equals( MatchType.GP ) ) {
	                	if(pf.getMatchValue().toString().contains(",")){
	        				String[] groupByArr = pf.getMatchValue().toString().split(",");
	        				ProjectionList prolist=Projections.projectionList(); 
	        				for(String gb:groupByArr){
	        					if(gb.contains(".")){
	        						criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, gb, CriteriaSpecification.LEFT_JOIN );
	        					}
	        					prolist.add(Projections.groupProperty(gb));
	        				}
	        				criteria.setProjection(prolist);
	        			}else{
	        				criteria.setProjection(Projections.groupProperty(pf.getMatchValue().toString()));
	        			}
	                }
	                else if ( pf.getMatchType().equals( MatchType.OA ) ) {
	                	//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName().toString(), CriteriaSpecification.LEFT_JOIN );
	                	criteria.addOrder(Order.asc(fieldName.toString()));
	                }
	                else if ( pf.getMatchType().equals( MatchType.OD ) ) {
	                	//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName().toString(), CriteriaSpecification.LEFT_JOIN );
	                	criteria.addOrder(Order.desc(fieldName.toString()));
	                }else if( pf.getMatchType().equals(MatchType.SQ)){
	                	criteria.add( Restrictions.sqlRestriction(pf.getMatchValue().toString()) );
	                }
	                else {
	                    throw new BusinessException( "查询条件错误，未知的查询操作符。" );
	                }
	            }
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List getByFilters(List<PropertyFilter> filters,Class filterClass){
		try {
			Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria( filterClass );
			Iterator<PropertyFilter> it = filters.iterator();
			 while ( it.hasNext() ) {
	                PropertyFilter pf = (PropertyFilter) it.next();
	                criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName() ,1);
	                String fieldName= CriteriaUtil.getSearchAliasFieldname(pf.getPropertyName());
	                if ( pf.getMatchType().equals( MatchType.LIKE ) ) {
	                    String v = (String) pf.getMatchValue();
	                    boolean bp = v.startsWith( "*" );
	                    boolean ep = v.endsWith( "*" );
	                    v = v.replaceAll( "\\*", "" );
	                    if ( bp && ep )
	                        criteria.add( Restrictions.like( fieldName, v, MatchMode.ANYWHERE ) );
	                    else if ( bp && !ep )
	                        criteria.add( Restrictions.like( fieldName, v, MatchMode.END ) );

	                    else if ( !bp && ep )
	                        criteria.add( Restrictions.like( fieldName, v, MatchMode.START ) );
	                    else
	                        criteria.add( Restrictions.like( fieldName, v, MatchMode.EXACT ) );

	                }
	                else if ( pf.getMatchType().equals( MatchType.EQ ) ) {
	                    criteria.add( Restrictions.eq( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.GE ) ) {
	                    criteria.add( Restrictions.ge( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.GT ) ) {
	                    criteria.add( Restrictions.gt( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.IN ) ) {
//	                	Object[] matchValueObjs = (Object[]) pf.getMatchValue();
//	                	int mvLength = matchValueObjs.length;
//	                	if(mvLength > 1000){
//	                		Criterion criterion = null;
//	                		Object[] newObjs = OtherUtil.splitArray(matchValueObjs, 1000);
//	                		for (Object objTemp : newObjs) {
//	                			Object[] objsTemp = (Object[])objTemp;
//	                			criterion = Restrictions.or(criterion, Restrictions.in(fieldName, objsTemp));
//	                		}
//	                		criteria.add(criterion);
//	                	}else{
//	                		criteria.add( Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) );
//	                	}
	                	criteria.add( Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.NI ) ) {
	                    criteria.add( Restrictions.not(Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) ));
	                }
	                else if ( pf.getMatchType().equals( MatchType.ISNOTNULL ) ) {
	                    criteria.add( Restrictions.isNotNull( fieldName ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.ISNULL ) ) {
	                    criteria.add( Restrictions.isNull( fieldName ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.LE ) ) {
	                    criteria.add( Restrictions.le( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.LT ) ) {
	                    criteria.add( Restrictions.lt( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.NE ) ) {
	                    criteria.add( Restrictions.ne( fieldName, pf.getMatchValue() ) );
	                }else if ( pf.getMatchType().equals( MatchType.GP ) ) {
	                	if(pf.getMatchValue().toString().contains(",")){
	        				String[] groupByArr = pf.getMatchValue().toString().split(",");
	        				ProjectionList prolist=Projections.projectionList(); 
	        				for(String gb:groupByArr){
	        					if(gb.contains(".")){
	        						criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, gb, CriteriaSpecification.LEFT_JOIN );
	        					}
	        					prolist.add(Projections.groupProperty(gb));
	        				}
	        				criteria.setProjection(prolist);
	        			}else{
	        				criteria.setProjection(Projections.groupProperty(pf.getMatchValue().toString()));
	        			}
	                }
	                else if ( pf.getMatchType().equals( MatchType.OA ) ) {
	                	//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName().toString(), CriteriaSpecification.LEFT_JOIN );
	                	criteria.addOrder(Order.asc(fieldName.toString()));
	                }
	                else if ( pf.getMatchType().equals( MatchType.OD ) ) {
	                	//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName().toString(), CriteriaSpecification.LEFT_JOIN );
	                	criteria.addOrder(Order.desc(fieldName.toString()));
	                }else if( pf.getMatchType().equals(MatchType.SQ)){
	                	criteria.add( Restrictions.sqlRestriction(pf.getMatchValue().toString()) );
	                }
	                else {
	                    throw new BusinessException( "查询条件错误，未知的查询操作符。" );
	                }
	            }
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int getCountByFilters(List<PropertyFilter> filters){
		try {
			Criteria criteria = getCriteria();
			Iterator<PropertyFilter> it = filters.iterator();
			 while ( it.hasNext() ) {
	                PropertyFilter pf = (PropertyFilter) it.next();
	                criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName() ,1);
	                String fieldName= CriteriaUtil.getSearchAliasFieldname(pf.getPropertyName());
	                if ( pf.getMatchType().equals( MatchType.LIKE ) ) {
	                    String v = (String) pf.getMatchValue();
	                    boolean bp = v.startsWith( "*" );
	                    boolean ep = v.endsWith( "*" );
	                    v = v.replaceAll( "\\*", "" );
	                    if ( bp && ep )
	                        criteria.add( Restrictions.like( fieldName, v, MatchMode.ANYWHERE ) );
	                    else if ( bp && !ep )
	                        criteria.add( Restrictions.like( fieldName, v, MatchMode.END ) );

	                    else if ( !bp && ep )
	                        criteria.add( Restrictions.like( fieldName, v, MatchMode.START ) );
	                    else
	                        criteria.add( Restrictions.like( fieldName, v, MatchMode.EXACT ) );

	                }
	                else if ( pf.getMatchType().equals( MatchType.EQ ) ) {
	                    criteria.add( Restrictions.eq( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.GE ) ) {
	                    criteria.add( Restrictions.ge( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.GT ) ) {
	                    criteria.add( Restrictions.gt( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.IN ) ) {
//	                	Object[] matchValueObjs = (Object[]) pf.getMatchValue();
//	                	int mvLength = matchValueObjs.length;
//	                	if(mvLength > 1000){
//	                		Criterion criterion = null;
//	                		Object[] newObjs = OtherUtil.splitArray(matchValueObjs, 1000);
//	                		for (Object objTemp : newObjs) {
//	                			Object[] objsTemp = (Object[])objTemp;
//	                			criterion = Restrictions.or(criterion, Restrictions.in(fieldName, objsTemp));
//	                		}
//	                		criteria.add(criterion);
//	                	}else{
//	                		criteria.add( Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) );
//	                	}
	                	criteria.add( Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.NI ) ) {
	                    criteria.add( Restrictions.not(Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) ));
	                }
	                else if ( pf.getMatchType().equals( MatchType.ISNOTNULL ) ) {
	                    criteria.add( Restrictions.isNotNull( fieldName ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.ISNULL ) ) {
	                    criteria.add( Restrictions.isNull( fieldName ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.LE ) ) {
	                    criteria.add( Restrictions.le( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.LT ) ) {
	                    criteria.add( Restrictions.lt( fieldName, pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.NE ) ) {
	                    criteria.add( Restrictions.ne( fieldName, pf.getMatchValue() ) );
	                }else if ( pf.getMatchType().equals( MatchType.GP ) ) {
	                	if(pf.getMatchValue().toString().contains(",")){
	        				String[] groupByArr = pf.getMatchValue().toString().split(",");
	        				ProjectionList prolist=Projections.projectionList(); 
	        				for(String gb:groupByArr){
	        					if(gb.contains(".")){
	        						criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, gb, CriteriaSpecification.LEFT_JOIN );
	        					}
	        					prolist.add(Projections.groupProperty(gb));
	        				}
	        				criteria.setProjection(prolist);
	        			}else{
	        				criteria.setProjection(Projections.groupProperty(pf.getMatchValue().toString()));
	        			}
	                }
	                else if ( pf.getMatchType().equals( MatchType.OA ) ) {
	                	//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName().toString(), CriteriaSpecification.LEFT_JOIN );
	                	criteria.addOrder(Order.asc(fieldName.toString()));
	                }
	                else if ( pf.getMatchType().equals( MatchType.OD ) ) {
	                	//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName().toString(), CriteriaSpecification.LEFT_JOIN );
	                	criteria.addOrder(Order.desc(fieldName.toString()));
	                }else if( pf.getMatchType().equals(MatchType.SQ)){
	                	criteria.add( Restrictions.sqlRestriction(pf.getMatchValue().toString()) );
	                }
	                else {
	                    throw new BusinessException( "查询条件错误，未知的查询操作符。" );
	                }
	            }
			 criteria.setProjection(Projections.rowCount());
			return ((Long)criteria.uniqueResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int excuteSql(String sql)    {    
        int result ;  
        Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
        //SQLQuery query = this.getSession().createSQLQuery(sql);    
        result = query.executeUpdate();    
        return result;    
    }    
	
	@Override
	public List<Object[]> getBySql(String sql){
		 Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		 return query.list();
	}
	@Override
	public List<T> getByExample(T object) {
		Criteria criteria = this.getCriteria();
		return criteria.add(AssociationExample.create(object)).list();
	}
	
	@Override
	public List<Map<String, Object>> getBySqlToMap(String sql){
		 Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		 query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 return query.list();
	}

	@Override
	public List<T> getByHql(String hql) {
		 Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		 return query.list();
	}

	@Override
	public void clearSession() {
		this.sessionFactory.getCurrentSession().clear();
	}
	@Override
	public void flusSession() {
		this.sessionFactory.getCurrentSession().flush();
	}
	
	@Override
	public void statelessSave(T object){
		StatelessSession session = sessionFactory.openStatelessSession();
		session.insert(object);
	}
	
	
	@Override
	public StatelessSession getStatelessSession(){
		return sessionFactory.openStatelessSession();
	}
	@Override
	public Boolean getIsDBColumnExist(String tableName,String columnName){
		Boolean isDBColumnExist = false;
		String sql = "select name,id from syscolumns where id=object_id('"+tableName+"') AND name = '"+columnName+"'";
		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		List reslutList = query.list();
		if(OtherUtil.measureNotNull(reslutList)&&!reslutList.isEmpty()){
			isDBColumnExist = true;
		}
		return isDBColumnExist;
	}

	@Override
	public JQueryPager getBdInfoCriteriaWithSearch(JQueryPager paginatedList, BdInfoUtil bdInfoUtil,List<PropertyFilter> filters) {
		HibernateCallback cusCallBack = new JqueryPagerHibernateBdinfoCallBack( paginatedList, bdInfoUtil, filters );
		log.debug( "getBdInfoCriteriaWithSearch" );
		log.debug( "paginatedList " + paginatedList );
		String orderName = paginatedList.getSortCriterion();
		String orderDirec = "ASC";
		if(paginatedList.getSortDirection()==SortOrderEnum.DESCENDING){
			orderDirec = "DESC";
		}
		if(orderName!=null&&!"".equals(orderName)){
			bdInfoUtil.addSort(orderName, orderDirec,false);
		}
		HibernateCallback callback = null;
		if( cusCallBack != null )
			callback = cusCallBack;
		try {
			Map<String, Object> resultMap = (Map<String, Object>) getHibernateTemplate().execute( callback );
			paginatedList.setList((List)resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
		}catch ( Exception e ) {
			log.error( "Get Crtiteria Error", e );
			e.printStackTrace();
		}
		return paginatedList;
	}
}
