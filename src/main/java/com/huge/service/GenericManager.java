package com.huge.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huge.ihos.system.configuration.bdinfo.util.BdInfoUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

/**
 * Generic Manager that talks to GenericDao to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) managers
 * for your domain objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public interface GenericManager<T, PK extends Serializable> {

    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
    List<T> getAll();

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
     * @param id the identifier (primary key) of the object to get
     * @return - true if it exists, false if it doesn't
     */
    boolean exists( PK id );

    boolean existCode( String object, String columnName, String valueCode, String oper );
    boolean existCode( String object, String columnName,String columnName2, String valueCode, String valueCode2);
    public boolean existCodeEdit(Long idValue,String tableName,String idColumnName,String columnName,String valueCode);
    public boolean existCodeEdit(Long idValue,String tableName,String idColumnName,String columnName,String valueCode,String columnName2,String valueCode2);

    /**
     * 自动补全的通用方法
     * @param enttiy 实体名
     * @param dicAttr 要查询的字段
     * @param dicValue 查询内容
     * @param pinJieS 自定义条件(开始)
     * @param pinJieE 自定义条件(结束)
     * @return
     */
    List searchDictionary( String enttiy, String dicAttr[], String dicValue, String pinJieS, String pinJieE );
    /**
     * 总计
     * @param object
     * @param column
     * @param currentPeriod
     * @return
     */
    String getAmountSum(String object,String column, String currentPeriod );
    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return the updated object
     */
    T save( T object );

    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
     */
    
    /**
     * Generic method to save a list of object - handles both update and insert.
     * @param list
     */
    void saveAll(List<T> list);
    
    void remove( PK id );
    
    void remove(PK[] ids);

    /**
     * Generic method to search for an object.
     * @param searchTerm the search term
     * @param clazz type of class to search for.
     * @return a list of matched objects
     */
    
    void removeAll();
    
    //List<T> search( String searchTerm, Class clazz );

    JQueryPager getAppManagerCriteriaWithSearch( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters );

    HibernateTemplate getHibernateTemplate();
    
    List<T> getAllExceptDisable();
    
    String pyCode(String str);
    
    List<T> getByFilters(List<PropertyFilter> filters);
    /**
     * 自定义实体类的filter查询
     * @param filters
     * @param filterClass
     * @return
     */
    List getByFilters(List<PropertyFilter> filters,Class filterClass);
    
    List<T> getByExample( T object );
    
    List<T> getByHql( String hql );
    /**
     * 执行select的sql语句，返回Object[]
     * @param sql
     * @return
     */
    List<Object[]> getBySql(String sql);
    /**
     * 执行select的sql语句，返回map
     * @param sql
     * @return
     */
    List<Map<String, Object>> getBySqlToMap(String sql);
    /**
     * 执行sqlList
     * @param sqlList
     */
    void executeSqlList(List<String> sqlList);
    /**
     * 执行sql
     * @param sql
     */
    void executeSql(String sql);
    
    void clearSession();
	
	void flusSession();
	
	public void statelessSaveAll(List<T> list);
	/**
	 * 判断数据库中某个表中是否存在某一列
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public Boolean getIsDBColumnExist(String tableName,String columnName);
	
	public JQueryPager getBdInfoCriteriaWithSearch( final JQueryPager paginatedList, final BdInfoUtil bdInfoUtil, List<PropertyFilter> filters );
}
