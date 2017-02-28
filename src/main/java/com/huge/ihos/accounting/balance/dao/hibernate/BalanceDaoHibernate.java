package com.huge.ihos.accounting.balance.dao.hibernate;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.CriteriaUtil;
import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.accounting.account.model.Account;
import com.huge.ihos.accounting.balance.dao.BalanceDao;
import com.huge.ihos.accounting.balance.model.Balance;
import com.huge.ihos.accounting.glabstract.model.GLAbstract;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.PropertyFilter.MatchType;


@Repository("balanceDao")
public class BalanceDaoHibernate extends GenericDaoHibernate<Balance, String> implements BalanceDao {

    public BalanceDaoHibernate() {
        super(Balance.class);
    }
    public JQueryPager getBalanceCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
    	try {
    		if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("balanceId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Balance.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBalanceCriteria", e);
			return paginatedList;
		}
	}
    
	@Override
	public List<Balance> getBalWithAssistByAcct(String acctId) {
		List<Balance> resultList = new ArrayList<Balance>();
		if(StringUtils.isNotEmpty(acctId)){
			String sql = "select * from gl_balance where acctId = '"+ acctId+"' and isAssit = 1";
			Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery(sql).addEntity(Balance.class);
			resultList = query.list();
			return resultList;
		} else {
			return resultList;
		}
	}
	@Override
	public List<Balance> getBalancesByAcct(String acctId) {
		List<Balance> resultList = new ArrayList<Balance>();
		if(StringUtils.isNotEmpty(acctId)){
			String sql = "select * from gl_balance where acctId = '"+ acctId+"' ";
			Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery(sql).addEntity(Balance.class);
			resultList = query.list();
			return resultList;
		} else {
			return resultList;
		}
	}
	@Override
	public List<Balance> getBalancesByAcctCode(String acctCode, String orgCode,
			String kjYear, String copyCode) {
		List<Balance> balanceList = new ArrayList<Balance>();
		Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		if(acctCode != null){
			acctCode = acctCode.trim();
		}
		while(acctCode.length() >= 4){
			String sql = "select * from gl_balance where acctCode = '"+acctCode+"' and orgCode = '"+orgCode+"' and kjYear = '"+kjYear+"' and copyCode = '"+copyCode+"'";
			Query query = session.createSQLQuery(sql).addEntity(Balance.class);
			List<Balance> balanceTempList = query.list();
			if(balanceTempList!=null && balanceTempList.size() == 1 ){
				Balance balanceTemp = balanceTempList.get(0);
				balanceList.add(balanceTemp);
			}
			acctCode = acctCode.substring(0, acctCode.length()-2);
		}
		return balanceList;
	}
	@Override
	public List<HashMap<String, String>> isBalance(HashMap<String, String> environment) {
		List<HashMap<String ,String>> resultList = new ArrayList<HashMap<String ,String>>();
		Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		
		String sql = "select acctType.AccttypeId, acctType.Accttype, sum(balance.initBalance) "
					+" from GL_accountType acctType inner join gl_account acct on acctType.AccttypeId = acct.accttypeId inner join gl_balance balance on balance.acctId = acct.acctId"
					+" where acct.kjYear = '"+environment.get("kjYear")+"' and acct.orgCode='"+environment.get("orgCode")+"' and acct.copyCode='"+environment.get("copyCode")+"' and acct.leaf=1"
					+" group by acctType.AccttypeId, acctType.Accttype";
		Query query = session.createSQLQuery(sql);
		List<Object[]> list = query.list();
		for(Object[] objects : list){
			HashMap<String, String> row = new HashMap<String ,String>();
			String typeId = objects[0].toString().trim();
			String acctType = objects[1].toString().trim();
			String balanceTotal = objects[2].toString().trim();
			String direction = "";
			if("资产类".equals(acctType)  || "费用类".equals(acctType)){
				direction = "借";
			} else {
				direction = "贷";
			}
			row.put("typeId", typeId);
			row.put("acctType", acctType);
			row.put("direction", direction);
			row.put("balanceTotal", balanceTotal);
			resultList.add(row);
		}
		return resultList;
	}
	@Override
	public List<Balance> getBalanceCriteriaWithCount(JQueryPager paginatedList,
			List<PropertyFilter> filters) {
		Criteria criteria = getCriteria();
		criteria = getCustomCriterion(criteria,paginatedList,filters,"");
		List<Balance> balanceList = criteria.list();
		return balanceList;
	}
	
	public Criteria getCustomCriterion( Criteria criteria, JQueryPager paginatedList, List<PropertyFilter> filters, String group_on) {
        Iterator itr = filters.iterator();
        if ( group_on.equals( "OR" ) ) {
            Disjunction disjunction = Restrictions.disjunction();
            while ( itr.hasNext() ) {
                PropertyFilter pf = (PropertyFilter) itr.next();
                //criteria = CriteriaUtil.createAliasCriteria((CriteriaImpl) criteria, pf.getPropertyName());
                if ( pf.getMatchType().equals( MatchType.LIKE ) ) {
                    String v = (String) pf.getMatchValue();
                    boolean bp = v.startsWith( "*" );
                    boolean ep = v.endsWith( "*" );
                    v = v.replaceAll( "\\*", "" );
                    if ( bp && ep )
                        disjunction.add( Restrictions.like( pf.getPropertyName(), v, MatchMode.ANYWHERE ) );
                    else if ( bp && !ep )
                        disjunction.add( Restrictions.like( pf.getPropertyName(), v, MatchMode.END ) );

                    else if ( !bp && ep )
                        disjunction.add( Restrictions.like( pf.getPropertyName(), v, MatchMode.START ) );
                    else
                        disjunction.add( Restrictions.like( pf.getPropertyName(), v, MatchMode.EXACT ) );

                }
                else if ( pf.getMatchType().equals( MatchType.EQ ) ) {
                    disjunction.add( Restrictions.eq( pf.getPropertyName(), pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.GE ) ) {
                    disjunction.add( Restrictions.ge( pf.getPropertyName(), pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.GT ) ) {
                    disjunction.add( Restrictions.gt( pf.getPropertyName(), pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.IN ) ) {
                    disjunction.add( Restrictions.in( pf.getPropertyName(), (Object[]) pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.ISNOTNULL ) ) {
                    disjunction.add( Restrictions.isNotNull( pf.getPropertyName() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.ISNULL ) ) {
                    disjunction.add( Restrictions.isNull( pf.getPropertyName() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.LE ) ) {
                    disjunction.add( Restrictions.le( pf.getPropertyName(), pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.LT ) ) {
                    disjunction.add( Restrictions.lt( pf.getPropertyName(), pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.NE ) ) {
                    disjunction.add( Restrictions.ne( pf.getPropertyName(), pf.getMatchValue() ) );
                }
                else {
                    throw new BusinessException( "查询条件错误，未知的查询操作符。" );
                }

            }
            criteria.add( disjunction );
        }
        else {
            while ( itr.hasNext() ) {
                PropertyFilter pf = (PropertyFilter) itr.next();
                criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName() ,1);
                if ( pf.getMatchType().equals( MatchType.LIKE ) ) {
                    String v = (String) pf.getMatchValue();
                    boolean bp = v.startsWith( "*" );
                    boolean ep = v.endsWith( "*" );
                    v = v.replaceAll( "\\*", "" );
                    if ( bp && ep )
                        criteria.add( Restrictions.like( pf.getPropertyName(), v, MatchMode.ANYWHERE ) );
                    else if ( bp && !ep )
                        criteria.add( Restrictions.like( pf.getPropertyName(), v, MatchMode.END ) );

                    else if ( !bp && ep )
                        criteria.add( Restrictions.like( pf.getPropertyName(), v, MatchMode.START ) );
                    else
                        criteria.add( Restrictions.like( pf.getPropertyName(), v, MatchMode.EXACT ) );

                }
                else if ( pf.getMatchType().equals( MatchType.EQ ) ) {
                    criteria.add( Restrictions.eq( pf.getPropertyName(), pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.GE ) ) {
                    criteria.add( Restrictions.ge( pf.getPropertyName(), pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.GT ) ) {
                    criteria.add( Restrictions.gt( pf.getPropertyName(), pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.IN ) ) {
                    criteria.add( Restrictions.in( pf.getPropertyName(), (Object[]) pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.NI ) ) {
                    criteria.add( Restrictions.not(Restrictions.in( pf.getPropertyName(), (Object[]) pf.getMatchValue() ) ));
                }
                else if ( pf.getMatchType().equals( MatchType.ISNOTNULL ) ) {
                    criteria.add( Restrictions.isNotNull( pf.getPropertyName() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.ISNULL ) ) {
                    criteria.add( Restrictions.isNull( pf.getPropertyName() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.LE ) ) {
                    criteria.add( Restrictions.le( pf.getPropertyName(), pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.LT ) ) {
                    criteria.add( Restrictions.lt( pf.getPropertyName(), pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.NE ) ) {
                    criteria.add( Restrictions.ne( pf.getPropertyName(), pf.getMatchValue() ) );
                }
                else {
                    throw new BusinessException( "查询条件错误，未知的查询操作符。" );
                }
            }
        }

        return criteria;
    }
	@Override
	public List<Balance> getAll(HashMap<String, String> environment) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("orgCode", environment.get("orgCode")));
		criteria.add(Restrictions.eq("copyCode", environment.get("copyCode")));
		criteria.add(Restrictions.eq("kjYear", environment.get("kjYear")));
		return criteria.list();
	}
}
