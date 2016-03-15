package com.huge.webapp.util;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.huge.ihos.multidatasource.DynamicSessionFactoryHolder;

public class SpringContextHelper
    implements ApplicationContextAware {

    private static ApplicationContext context;

    /*
     * 注入ApplicationContext
     */
    public void setApplicationContext( ApplicationContext context )
        throws BeansException {
        //在加载Spring时自动获得context
        SpringContextHelper.context = context;
        //		System.out.println(SpringContextHelper.context);
    }

    public static Object getBean( String beanName ) {
    	if("dataSource".equals(beanName)){
        	return getDataSource();
    	}
        return context.getBean( beanName );
    }
    
    public static DataSource getDataSource(){
    	SessionFactory  sessionFactory = (SessionFactory)getBean(DynamicSessionFactoryHolder.getSessionFactoryName());
    	return SessionFactoryUtils.getDataSource(sessionFactory);
    }
}
