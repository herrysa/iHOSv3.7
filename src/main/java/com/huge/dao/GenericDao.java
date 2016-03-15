package com.huge.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.StatelessSession;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huge.ihos.system.configuration.bdinfo.util.BdInfoUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) DAO's for your
 * domain objects.
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public interface GenericDao<T, PK extends Serializable> {

    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
    List<T> getAll();

    /**
     * Gets all records without duplicates.
     * <p>Note that if you use this method, it is imperative that your model
     * classes correctly implement the hashcode/equals methods</p>
     * @return List of populated objects
     */
    List<T> getAllDistinct();

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get( PK id );
    
    List<T> get(PK...ids);
    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id the id of the entity
     * @return - true if it exists, false if it doesn't
     */
    boolean exists( PK id );

    /**
     * 
     * 查找任意属性并返回是否重复
     * @param object实体名
     * @param columnName列名
     * @param valueCode需要查询的值
     * @param oper 操作类型(add,edit)
     * @return
     */
    boolean existCode( String object, String columnName, String valueCode, String oper );
    boolean existCode( String object, String columnName,String columnName2, String valueCode,String valueCode2);
    public boolean existCodeEdit(Long idValue,String tableName,String idColumnName,String columnName,String valueCode);
    public boolean existCodeEdit(Long idValue,String tableName,String idColumnName,String columnName,String valueCode,String columnName2,String valueCode2);

    /**
     * 自动补全通用方法
     * @param entityName 实体名
     * @param str 拼接的字符串
     * @return
     */
    List searchDictionary( String entityName, String str );

    /**
     * 传入字符串返回助记码
     * @param str 字符串
     * @return
     */
    String getPyCodes( String str );
    
    /**
     * 总计
     * @param object 实体名
     * @param column 列名
     * @param currentPeriod 当前期间
     * @return
     */
    String getAmountSum(String object,String column, String currentPeriod );

    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return the persisted object
     */
    T save( T object );

    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
     */
    void remove( PK id );
    
    void remove(PK[] ids);

    /**
     * Find a list of records by using a named query
     * @param queryName query name of the named query
     * @param queryParams a map of the query names and the values
     * @return a list of the records found
     */
    
    void removeAll();
    
    List<T> findByNamedQuery( String queryName, Map<String, Object> queryParams );

    Map<String, Object> getAppManagerCriteria( final JQueryPager paginatedList, final Class object, HibernateCallback cusCallBack );

    Map<String, Object> getAppManagerCriteriaWithSearch( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters );

    Map<String, Object> getAppManagerCriteriaWithSearch( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters,
                                                         String group_on );

    HibernateTemplate getHibernateTemplate();
    
    List<T> getAllExceptDisable();
    
    Criteria getCriteria();
    
    
    /**
     * 执行原生态Sql语句
     * @param sql
     * @return 受影响的行数
     */
	int excuteSql(String sql);
	/**
     * 执行原生态Sql语句
     * @param sql
     * @return 查询出来数据List
     */
	List<Object[]> getBySql(String sql);
	/**
     * 执行原生态Sql语句
     * @param sql
     * @return 查询出来Map数据List
     */
	List<Map<String,Object>> getBySqlToMap(String sql);
	
	//Map<String,Object> getMapBean(String sql);
	
	/**QBE
	 * @param object 查询参照实体
	 * @return
	 */
	List<T> getByExample( T object );
	
	
	/**QBC
	 * @param filters 过滤条件
	 * @return
	 */
	List<T> getByFilters(List<PropertyFilter> filters);
	
	/**
	 * 自定义实体类的filter查询
	 * @param filters
	 * @param filterClass
	 * @return
	 */
	List getByFilters(List<PropertyFilter> filters,Class filterClass);
	
	int getCountByFilters(List<PropertyFilter> filters);
	
	/**
	 * @param hql
	 * @return
	 */
	List<T> getByHql(String hql);
	
	void clearSession();
	
	void flusSession();
	
	public void statelessSave(T object);
	
	public StatelessSession getStatelessSession();
	/**
	 * 判断数据库中某个表中是否存在某一列
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public Boolean getIsDBColumnExist(String tableName,String columnName);
	
	public JQueryPager getBdInfoCriteriaWithSearch( final JQueryPager paginatedList, final BdInfoUtil bdInfoUtil, List<PropertyFilter> filters );
}