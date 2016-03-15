package com.huge.ihos.multidatasource;

import com.huge.webapp.util.SpringContextHelper;

public class DynamicSessionFactoryHolder {
	private static ThreadLocal<String> holder = new ThreadLocal<String>(){
		public String initialValue() {
			return ((DynamicDatabaseManager)SpringContextHelper.getBean("selectedDataSource")).getDefaultSessionFactory();
		}
	};
	
	public static void setSessionFactoryName(String sessionFactoryName) {
		holder.set(sessionFactoryName);
	}
	
	public static String getSessionFactoryName(){
		return holder.get();
	}
	
	public static void removeSessionFactoryName(){
		holder.remove();
	}
}
