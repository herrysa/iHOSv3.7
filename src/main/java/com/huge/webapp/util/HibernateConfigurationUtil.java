package com.huge.webapp.util;

import javax.persistence.Entity;
import javax.persistence.Table;

public class HibernateConfigurationUtil{
	/*private static Configuration configuration;

	public static Configuration getConfiguration() {
		if (configuration == null) {
			// 取sessionFactory的时候要加上&
			LocalSessionFactoryBean factory = (LocalSessionFactoryBean) SpringContextHelper.getBean("&sessionFactory");
			configuration = factory.getConfiguration();
		}
		return configuration;
	}

	private static <T> PersistentClass getPersistentClass(Class<T> clazz) {
		synchronized (SpringContextHelper.class) {
			PersistentClass pc = getConfiguration().getClassMapping(clazz.getSimpleName());
			if (pc == null) {
				try {
					configuration = configuration.addClass(clazz);
				} catch (MappingException e) {
					configuration = configuration.addAnnotatedClass(clazz);
				}
				pc = configuration.getClassMapping(clazz.getName());
			}
			return pc;
		}
	}*/

	public static <T> String getTableName(Class<T> clazz) {
        String tableName = clazz.getSimpleName();  
        if (clazz.isAnnotationPresent(Entity.class)) {  
            Table entityAnnotation = clazz.getAnnotation(Table.class);  
            String tempTableName = entityAnnotation.name();  
            if (tempTableName != null && !"".equals(tempTableName))  
                tableName = tempTableName;  
        }  
        return tableName;  
	}

	/*public static <T> String getPKColumnName(Class<T> clazz) {
		return getPersistentClass(clazz).getTable().getPrimaryKey()
				.getColumn(0).getName();
	}
	public static <T> String getEntityPKColumnName(Class<T> clazz) {
		return getPersistentClass(clazz).getDeclaredIdentifierProperty().getName();
	}

	public static <T> String getColumnName(Class<T> clazz, String propertyName) {
		String columnName = "";
		PersistentClass persistentClass = getPersistentClass(clazz);
		Property property = persistentClass.getProperty(propertyName);
		Iterator<?> iterator = property.getColumnIterator();
		if (iterator.hasNext()) {
			Column column = (Column) iterator.next();
			columnName += column.getName();
		}
		return columnName;
	}*/

}
