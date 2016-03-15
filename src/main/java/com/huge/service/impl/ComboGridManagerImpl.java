package com.huge.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.RowSelection;
import org.hibernate.impl.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.search.dao.hibernate.UpperAliasToEntityMapResultTransformer;
import com.huge.ihos.system.reportManager.search.dao.hibernate.UpperCaseColumnMapRowMapper;
import com.huge.service.ComboGridManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.SpringContextHelper;
@Service("comboGridManager")
public class ComboGridManagerImpl implements ComboGridManager {
	protected SessionFactory sessionFactory;
	private DataSource dataSource = SpringContextHelper.getDataSource();

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public JQueryPager getComboGridCriteria(JQueryPager paginatedList, String sql, Object[] args ) {
		Session session = this.sessionFactory.getCurrentSession();
		// SearchResult sr = new SearchResult();
		//Object[] args = { term };
		List recList = getPageList(session, paginatedList, sql, args);
		paginatedList.setList(recList);
		Integer count = getPageCount(session, sql, args);
		paginatedList.setTotalNumberOfRows(count);

		return paginatedList;
	}

	private List getPageList(Session session, JQueryPager paginatedList, String sql, Object[] args) {

		Query q = session.createSQLQuery(sql);
		UpperAliasToEntityMapResultTransformer tran1 = UpperAliasToEntityMapResultTransformer.INSTANCE;

		q.setResultTransformer(tran1);
		q.setFirstResult(paginatedList.getFirstRecordIndex());// getFirstResult());
		q.setMaxResults(paginatedList.getPageSize());// getMaxResults());
		q = this.setQueryArgs(q, args);

		String qs = q.getQueryString();
		SessionFactoryImpl sf = (SessionFactoryImpl) session.getSessionFactory();
		final Dialect dialect = sf.getDialect();

		String limsql = dialect.getLimitString(sql.trim(), // use of trim() here
															// is ugly?
				paginatedList.getFirstRecordIndex(), paginatedList.getPageSize());

		RowSelection selection = new RowSelection();
		selection.setFirstRow(paginatedList.getStart());
		selection.setMaxRows(paginatedList.getPageSize()-1);

		args = prepareSqlArgs(dialect, args, selection);
		
		if(null!=paginatedList.getSortCriterion()){
			limsql += " order by " + paginatedList.getSortCriterion()+" "+ paginatedList.getSortDirection().toSqlString();
		}
		//JdbcTemplate jt = new JdbcTemplate(SessionFactoryUtils.getDataSource(sessionFactory));
		JdbcTemplate jt = new JdbcTemplate(dataSource);
		UpperCaseColumnMapRowMapper rm = new UpperCaseColumnMapRowMapper();
		List recList = jt.query(limsql, args, rm);

		return recList;
	}

	private int getPageCount(Session session, String sql, Object[] args) {
		String countQueryString = " select count(*) as cnt " + removeSelect(removeOrders(sql));

		Query q = session.createSQLQuery(countQueryString);
		q = this.setQueryArgs(q, args);

		List countlist = q.list();
		int totalCount = 0;
		if (countlist.size() > 1) {
			totalCount = countlist.size();
			return totalCount;
		}
		Object obj = countlist.get(0);
		if (obj instanceof String) {
			totalCount = Integer.parseInt((String) obj);
		}
		if (obj instanceof Integer) {
			totalCount = (Integer) obj;
		}
		if (obj instanceof Long) {
			totalCount = Integer.parseInt("" + obj);
		}
		if (obj instanceof Map) {
			totalCount = Integer.parseInt("" + ((Map) obj).get("cnt"));
		}
		if (obj instanceof BigInteger) {
			totalCount = ((BigInteger) obj).intValue();
		}

		return totalCount;
	}

	private static String removeSelect(final String hql) {
		final int beginPos = hql.toLowerCase().indexOf("from");
		return hql.substring(beginPos);
	}

	private static String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	private Query setQueryArgs(Query query, Object[] args) {
		if (args != null)
			for (int i = 0; i < args.length; i++)
				query.setParameter(i, args[i]);
		return query;
	}

	private Object[] prepareSqlArgs(final Dialect dialect, Object[] srcArgs, final RowSelection selection) {

		int firstRow = dialect.convertToFirstRowValue(getFirstRow(selection));
		int lastRow = getMaxOrLimit(selection, dialect);
		boolean hasFirstRow = dialect.supportsLimitOffset() && (firstRow > 0 || dialect.forceLimitUsage());
		boolean reverse = dialect.bindLimitParametersInReverseOrder();

		int limitArgNum = hasFirstRow ? 2 : 1;
		int firstRowIndex = 1 + (reverse ? 1 : 0);
		int lastRowIndex = 1 + (reverse || !hasFirstRow ? 0 : 1);

		if (hasFirstRow) {
			srcArgs = insertArgs(srcArgs, firstRowIndex - 1, firstRow);
		}
		srcArgs = insertArgs(srcArgs, lastRowIndex - 1, lastRow);
		return srcArgs;
	}

	private static int getFirstRow(RowSelection selection) {
		if (selection == null || selection.getFirstRow() == null) {
			return 0;
		} else {
			return selection.getFirstRow().intValue();
		}
	}

	private static int getMaxOrLimit(final RowSelection selection, final Dialect dialect) {
		final int firstRow = dialect.convertToFirstRowValue(getFirstRow(selection));
		final int lastRow = selection.getMaxRows().intValue();
		if (dialect.useMaxForLimit()) {
			return lastRow + firstRow;
		} else {
			return lastRow;
		}
	}

	static Object[] insertArgs(Object[] srcArgs, int pos, Object arg) {

		Object[] newArgs = new Object[srcArgs.length + 1];
		System.arraycopy(srcArgs, 0, newArgs, 0, srcArgs.length);
		newArgs[srcArgs.length] = arg;
		return newArgs;
	}
}
