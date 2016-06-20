package com.huge.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.bdinfo.util.BdInfoUtil;
import com.huge.service.GenericManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

/**
 * This class serves as the Base class for all other Managers - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * <p/>
 * <p>
 * To register this class in your Spring context file, use the following XML.
 * 
 * <pre>
 *     &lt;bean id="userManager" class="com.huge.service.impl.GenericManagerImpl"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;bean class="com.huge.dao.hibernate.GenericDaoHibernate"&gt;
 *                 &lt;constructor-arg value="com.huge.model.User"/&gt;
 *                 &lt;property name="sessionFactory" ref="sessionFactory"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/constructor-arg&gt;
 *     &lt;/bean&gt;
 * </pre>
 * <p/>
 * <p>
 * If you're using iBATIS instead of Hibernate, use:
 * 
 * <pre>
 *     &lt;bean id="userManager" class="com.huge.service.impl.GenericManagerImpl"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;bean class="com.huge.dao.ibatis.GenericDaoiBatis"&gt;
 *                 &lt;constructor-arg value="com.huge.model.User"/&gt;
 *                 &lt;property name="dataSource" ref="dataSource"/&gt;
 *                 &lt;property name="sqlMapClient" ref="sqlMapClient"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/constructor-arg&gt;
 *     &lt;/bean&gt;
 * </pre>
 * 
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class GenericManagerImpl<T, PK extends Serializable>
    implements GenericManager<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass())
     * from Commons Logging
     */
    protected final Log log = LogFactory.getLog( getClass() );

    /**
     * GenericDao instance, set by constructor of child classes
     */
    protected GenericDao<T, PK> dao;

   // @Autowired
   // private CompassSearchHelper compass;

    public GenericManagerImpl() {
    }

    public GenericManagerImpl( GenericDao<T, PK> genericDao ) {
        this.dao = genericDao;
    }

    /**
     * {@inheritDoc}
     */
    public List<T> getAll() {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public T get( PK id ) {
        return dao.get( id );
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists( PK id ) {
        return dao.exists( id );
    }

    public boolean existCode( String object, String columnName, String valueCode, String oper ) {
        return dao.existCode( object, columnName, valueCode, oper );
    }
    public boolean existCode( String object, String columnName,String columnName2, String valueCode,String valueCode2) {
    	return dao.existCode( object, columnName,columnName2, valueCode,valueCode2);
    }
    public boolean existCodeEdit(Long idValue,String tableName,String idColumnName,String columnName,String valueCode){
    	return dao.existCodeEdit(idValue, tableName, idColumnName, columnName, valueCode);
    }
    public boolean existCodeEdit(Long idValue,String tableName,String idColumnName,String columnName,String valueCode,String columnName2,String valueCode2){
    	return dao.existCodeEdit(idValue, tableName, idColumnName, columnName, valueCode, columnName2, valueCode2);
    }

    public List searchDictionary( String entity, String[] dicAttr, String dicValue, String pinJieS, String pinJieE ) {
        String str = "";
        if ( OtherUtil.measureNotNull( pinJieS ) )
            str += pinJieS;
        for ( int i = 0; i < dicAttr.length; i++ ) {
        	str += dicAttr[i] + " like '%" + dicValue + "%' or ";
        }
        str=OtherUtil.subStrEnd(str, "or ");
        if ( OtherUtil.measureNotNull( pinJieE ) )
            str += pinJieE;
        return dao.searchDictionary( entity, str );
    }

    /**
     * {@inheritDoc}
     */
    public T save( T object ) {
        return dao.save( object );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
	public void saveAll(List<T> list) {
    	for(T object:list){
    		dao.save( object );
    	}
	}

    /**
     * {@inheritDoc}
     */
    public void remove( PK id ) {
        dao.remove( id );
    }
    
    public void remove(PK[] ids){
		dao.remove(ids);
	}

    public void removeAll(){
    	dao.removeAll();
    }
    /**
     * {@inheritDoc}
     * <p/>
     * Search implementation using Compass.
     */
   /* @SuppressWarnings( "unchecked" )
    public List<T> search( String q, Class clazz ) {
        if ( q == null || "".equals( q.trim() ) ) {
            return getAll();
        }

        List<T> results = new ArrayList<T>();

        CompassSearchCommand command = new CompassSearchCommand( q );
        CompassSearchResults compassResults = compass.search( command );
        CompassHit[] hits = compassResults.getHits();

        if ( log.isDebugEnabled() && clazz != null ) {
            log.debug( "Filtering by type: " + clazz.getName() );
        }

        for ( CompassHit hit : hits ) {
            if ( clazz != null ) {
                if ( hit.data().getClass().equals( clazz ) ) {
                    results.add( (T) hit.data() );
                }
            }
            else {
                results.add( (T) hit.data() );
            }
        }

        if ( log.isDebugEnabled() ) {
            log.debug( "Number of results for '" + q + "': " + results.size() );
        }

        return results;
    }*/

    public JQueryPager getAppManagerCriteriaWithSearch( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters ) {
        Map<String, Object> resultMap = this.dao.getAppManagerCriteriaWithSearch( paginatedList, object, filters );
        paginatedList.setList( (List) resultMap.get( "list" ) );
        int count = 0;
        Integer icount = (Integer) resultMap.get( "count" );
        if ( icount != null )
            count = icount.intValue();
        paginatedList.setTotalNumberOfRows( count );
        return paginatedList;
    }
    
    @Override
	public JQueryPager getAppManagerCriteriaWithSearch(
			JQueryPager paginatedList, Class object,
			List<PropertyFilter> filters, String group_on) {
    	Map<String, Object> resultMap = this.dao.getAppManagerCriteriaWithSearch( paginatedList, object, filters ,group_on);
        paginatedList.setList( (List) resultMap.get( "list" ) );
        int count = 0;
        Integer icount = (Integer) resultMap.get( "count" );
        if ( icount != null )
            count = icount.intValue();
        paginatedList.setTotalNumberOfRows( count );
        return paginatedList;
	}

    @Override
    public HibernateTemplate getHibernateTemplate() {
        return dao.getHibernateTemplate();
    }

	@Override
	public List<T> getAllExceptDisable() {
		return dao.getAllExceptDisable();
	}

	@Override
	public String getAmountSum(String object, String column,
			String currentPeriod) {
		return dao.getAmountSum(object, column, currentPeriod);
	}
	
	@Override
	public String pyCode(String str) {
		return dao.getPyCodes(str);
	}

	@Override
	public List<T> getByFilters(List<PropertyFilter> filters) {
		return dao.getByFilters(filters);
	}
	@Override
	public List getByFilters(List<PropertyFilter> filters,Class filterClass){
		return dao.getByFilters(filters, filterClass);
	}
	
	@Override
	public List<T> getByExample(T object) {
		return dao.getByExample(object);
	}

	@Override
	public List<T> getByHql(String hql) {
		return dao.getByHql(hql);
	}
	@Override
	public void executeSqlList(List<String> sqlList){
		try {
			if(OtherUtil.measureNotNull(sqlList)&&!sqlList.isEmpty()){
				for(String sqlTemp:sqlList){
					dao.excuteSql(sqlTemp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void executeSql(String sql){
		dao.excuteSql(sql);
	}
	@Override
	public List<Object[]> getBySql(String sql) {
		return dao.getBySql(sql);
	}
	@Override
	public List<Map<String, Object>> getBySqlToMap(String sql){
		return dao.getBySqlToMap(sql);
	}
	@Override
	public List<T> get(PK... ids) {
		return dao.get(ids);
	}

	@Override
	public void clearSession() {
		dao.clearSession();
		
	}

	@Override
	public void flusSession() {
		dao.flusSession();
	}

	@Override
	public void statelessSaveAll(List<T> list) {
		StatelessSession session = dao.getStatelessSession();
		Transaction tx = session.beginTransaction();
		for(T object:list){
			session.insert( object );
    	}
		tx.commit();
	}
	@Override
	public Boolean getIsDBColumnExist(String tableName,String columnName){
		return dao.getIsDBColumnExist(tableName, columnName);
	}

	@Override
	public JQueryPager getBdInfoCriteriaWithSearch(JQueryPager paginatedList, BdInfoUtil bdInfoUtil,List<PropertyFilter> filters) {
		return dao.getBdInfoCriteriaWithSearch(paginatedList, bdInfoUtil, filters);
	}
}
