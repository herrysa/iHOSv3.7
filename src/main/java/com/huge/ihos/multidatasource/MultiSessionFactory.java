package com.huge.ihos.multidatasource;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.TypeHelper;
import org.hibernate.classic.Session;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class MultiSessionFactory implements SessionFactory,ApplicationContextAware {

	private final Log log = LogFactory.getLog(MultiSessionFactory.class);
	private SessionFactory sessionFactory;
	private ApplicationContext applicationContext;

	public SessionFactory getSessionFactory(String sessionFactoryName) {
		log.debug("sessionFactoryName:"+sessionFactoryName);
		try {
			if (sessionFactoryName == null || "".equals(sessionFactoryName)) {
				return sessionFactory;
			}
			return (SessionFactory) this.applicationContext.getBean(sessionFactoryName);
		} catch (NoSuchBeanDefinitionException e) {
			throw new RuntimeException("There is not the sessionFactory <name:"
					+ sessionFactoryName + "> in the applicationContext!");
		}
	}

	public SessionFactory getSessionFactory() {
		String currentSessionFactory = DynamicSessionFactoryHolder.getSessionFactoryName();
		return this.getSessionFactory(currentSessionFactory);
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Reference getReference() throws NamingException {
		return this.getSessionFactory().getReference();
	}

	@Override
	public void close() throws HibernateException {
		this.getSessionFactory().close();
	}

	@Override
	public boolean containsFetchProfileDefinition(String arg0) {
		return this.getSessionFactory().containsFetchProfileDefinition(arg0);
	}

	@Override
	public void evict(Class arg0) throws HibernateException {
		this.getSessionFactory().evict(arg0);
	}

	@Override
	public void evict(Class arg0, Serializable arg1) throws HibernateException {
		this.getSessionFactory().evict(arg0, arg1);
	}

	@Override
	public void evictCollection(String arg0) throws HibernateException {
		this.getSessionFactory().evictCollection(arg0);
	}

	@Override
	public void evictCollection(String arg0, Serializable arg1)
			throws HibernateException {
		this.getSessionFactory().evictCollection(arg0, arg1);
	}

	@Override
	public void evictEntity(String arg0) throws HibernateException {
		this.getSessionFactory().evictEntity(arg0);
	}

	@Override
	public void evictEntity(String arg0, Serializable arg1)
			throws HibernateException {
		this.getSessionFactory().evictEntity(arg0, arg1);
	}

	@Override
	public void evictQueries() throws HibernateException {
		this.getSessionFactory().evictQueries();
	}

	@Override
	public void evictQueries(String arg0) throws HibernateException {
		this.getSessionFactory().evictQueries(arg0);
	}

	@Override
	public Map<String, ClassMetadata> getAllClassMetadata() {
		return this.getSessionFactory().getAllClassMetadata();
	}

	@Override
	public Map getAllCollectionMetadata() {
		return this.getSessionFactory().getAllCollectionMetadata();
	}

	@Override
	public Cache getCache() {
		return this.getSessionFactory().getCache();
	}

	@Override
	public ClassMetadata getClassMetadata(Class arg0) {
		return this.getSessionFactory().getClassMetadata(arg0);
	}

	@Override
	public ClassMetadata getClassMetadata(String arg0) {
		return this.getSessionFactory().getClassMetadata(arg0);
	}

	@Override
	public CollectionMetadata getCollectionMetadata(String arg0) {
		return this.getSessionFactory().getCollectionMetadata(arg0);
	}

	@Override
	public Session getCurrentSession() throws HibernateException {
		SessionHolder sessionHolder = (SessionHolder)TransactionSynchronizationManager.getResource(this);
		Session cs= (Session)sessionHolder.getSession();
		return cs;
//		return this.getSessionFactory().getCurrentSession();
	}

	@Override
	public Set getDefinedFilterNames() {
		return this.getSessionFactory().getDefinedFilterNames();
	}

	@Override
	public FilterDefinition getFilterDefinition(String arg0)
			throws HibernateException {
		return this.getSessionFactory().getFilterDefinition(arg0);
	}

	@Override
	public Statistics getStatistics() {
		return this.getSessionFactory().getStatistics();
	}

	@Override
	public TypeHelper getTypeHelper() {
		return this.getSessionFactory().getTypeHelper();
	}

	@Override
	public boolean isClosed() {
		return this.getSessionFactory().isClosed();
	}

	@Override
	public Session openSession() throws HibernateException {
		return this.getSessionFactory().openSession();
	}

	@Override
	public Session openSession(Interceptor arg0) throws HibernateException {
		return this.getSessionFactory().openSession(arg0);
	}

	@Override
	public Session openSession(Connection arg0) {
		return this.getSessionFactory().openSession(arg0);
	}

	@Override
	public Session openSession(Connection arg0, Interceptor arg1) {
		return this.getSessionFactory().openSession(arg0, arg1);
	}

	@Override
	public StatelessSession openStatelessSession() {
		return this.getSessionFactory().openStatelessSession();
	}

	@Override
	public StatelessSession openStatelessSession(Connection arg0) {
		return this.getSessionFactory().openStatelessSession(arg0);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
