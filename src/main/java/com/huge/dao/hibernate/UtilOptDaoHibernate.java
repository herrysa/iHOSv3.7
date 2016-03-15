package com.huge.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huge.dao.UtilOptDao;
import com.huge.ihos.hql.HqlUtil;
import com.huge.webapp.util.PropertyFilter;

@Repository( "utilOptDao" )
public class UtilOptDaoHibernate implements UtilOptDao{
	
	@Autowired
	SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<Map<String, String>> getByFilters(String entityName , List<PropertyFilter> filters) {
		try {
			String hql = "from Sourcepayin as entity where entity.sourcePayinId=6447849 group by sourcePayinId";
			
			//Class entity = Class.forName(entityName);
			//Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entity);
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, String>> qq = query.list();
			List<Map<String, String>> qq2 = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return qq2;
			/*Iterator<PropertyFilter> it = filters.iterator();
			while ( it.hasNext() ) {
	                PropertyFilter pf = (PropertyFilter) it.next();
	               // criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName() ,1);
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
	                } else if ( pf.getMatchType().equals( MatchType.GP ) ) {
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
	                	criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getMatchValue().toString(), CriteriaSpecification.LEFT_JOIN );
	                	criteria.addOrder(Order.asc(pf.getMatchValue().toString()));
	                }
	                else if ( pf.getMatchType().equals( MatchType.OD ) ) {
	                	criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getMatchValue().toString(), CriteriaSpecification.LEFT_JOIN );
	                	criteria.addOrder(Order.desc(pf.getMatchValue().toString()));
	                }
	                else {
	                    throw new BusinessException( "查询条件错误，未知的查询操作符。" );
	                }
	            }
			return criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		}

	@Override
	public List<Map<String, String>> getByHqlUtil(HqlUtil hqlUtil) {
		try {
			String hql = hqlUtil.getHQl();
			/*hql = "select entity.checkPeriod as checkPeriod, kdDept.name as kdDept_name, zxDept.name as zxDept_name, chargeType.chargeTypeName as chargeType_chargeTypeName, entity.amount as amount,"
					+"entity.outin as outin, chargeItem.chargeItemId as chargeItem_chargeItemId, chargeItem.chargeItemName as chargeItem_chargeItemName,"
					+"entity.lici as lici, operator.name as operator_name, entity.manual as manual, entity.remarks as remarks from " +
					"Sourcepayin as entity left join entity.kdDept as kdDept left join entity.zxDept as zxDept left join entity.chargeType as chargeType " +
					"left join entity.chargeItem as chargeItem  left join entity.operator as operator " +
					" where  entity.checkPeriod >=? AND  entity.checkPeriod <=? order by  entity.sourcePayinId desc";*/
			Object[] args = hqlUtil.getRealAgrs();
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			for(int i=0;i<args.length;i++){
				Object object = args[i];
				query.setParameter(i, object);
			}
			if("page".equals(hqlUtil.getFindType())){
				query.setMaxResults(hqlUtil.getPageSize());
				query.setFirstResult((hqlUtil.getPage()-1)*hqlUtil.getPageSize());
			}
			return query.list();
			//hql += " where ";
			//List<Map<String, String>> qq = query.list();
			/*Iterator<PropertyFilter> it = filters.iterator();
			while ( it.hasNext() ) {
	                PropertyFilter pf = (PropertyFilter) it.next();
	               // criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName() ,1);
	                if ( pf.getMatchType().equals( MatchType.LIKE ) ) {
	                    String v = (String) pf.getMatchValue();
	                    boolean bp = v.startsWith( "*" );
	                    boolean ep = v.endsWith( "*" );
	                    v = v.replaceAll( "\\*", "" );
	                    if ( bp && ep )
	                       // criteria.add( Restrictions.like( pf.getPropertyName(), v, MatchMode.ANYWHERE ) );
	                    	
	                    else if ( bp && !ep )
	                        //criteria.add( Restrictions.like( pf.getPropertyName(), v, MatchMode.END ) );

	                    else if ( !bp && ep )
	                        //criteria.add( Restrictions.like( pf.getPropertyName(), v, MatchMode.START ) );
	                    else
	                        criteria.add( Restrictions.like( pf.getPropertyName(), v, MatchMode.EXACT ) );

	                }
	                else if ( pf.getMatchType().equals( MatchType.EQ ) ) {
	                    //criteria.add( Restrictions.eq( pf.getPropertyName(), pf.getMatchValue() ) );
	                	hql += "entity."+pf.getPropertyName()+"='"+pf.getMatchValue()+"'";
	                }
	                else if ( pf.getMatchType().equals( MatchType.GE ) ) {
	                    //criteria.add( Restrictions.ge( pf.getPropertyName(), pf.getMatchValue() ) );
	                	hql += "entity."+pf.getPropertyName()+">='"+pf.getMatchValue()+"'";
	                }
	                else if ( pf.getMatchType().equals( MatchType.GT ) ) {
	                    //criteria.add( Restrictions.gt( pf.getPropertyName(), pf.getMatchValue() ) );
	                	hql += "entity."+pf.getPropertyName()+">'"+pf.getMatchValue()+"'";
	                }
	                else if ( pf.getMatchType().equals( MatchType.IN ) ) {
	                   // criteria.add( Restrictions.in( pf.getPropertyName(), (Object[]) pf.getMatchValue() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.NI ) ) {
	                   // criteria.add( Restrictions.not(Restrictions.in( pf.getPropertyName(), (Object[]) pf.getMatchValue() ) ));
	                }
	                else if ( pf.getMatchType().equals( MatchType.ISNOTNULL ) ) {
	                  //  criteria.add( Restrictions.isNotNull( pf.getPropertyName() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.ISNULL ) ) {
	                  //  criteria.add( Restrictions.isNull( pf.getPropertyName() ) );
	                }
	                else if ( pf.getMatchType().equals( MatchType.LE ) ) {
	                    //criteria.add( Restrictions.le( pf.getPropertyName(), pf.getMatchValue() ) );
	                    hql += "and entity."+pf.getPropertyName()+"<='"+pf.getMatchValue()+"'";
	                }
	                else if ( pf.getMatchType().equals( MatchType.LT ) ) {
	                    //criteria.add( Restrictions.lt( pf.getPropertyName(), pf.getMatchValue() ) );
	                    hql += "entity."+pf.getPropertyName()+"<'"+pf.getMatchValue()+"'";
	                }
	                else if ( pf.getMatchType().equals( MatchType.NE ) ) {
	                   // criteria.add( Restrictions.ne( pf.getPropertyName(), pf.getMatchValue() ) );
	                	hql += "entity."+pf.getPropertyName()+"!='"+pf.getMatchValue()+"'";
	                } else if ( pf.getMatchType().equals( MatchType.GP ) ) {
	                	if(pf.getMatchValue().toString().contains(",")){
	        				String[] groupByArr = pf.getMatchValue().toString().split(",");
	        				ProjectionList prolist=Projections.projectionList(); 
	        				for(String gb:groupByArr){
	        					if(gb.contains(".")){
	        						//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, gb, CriteriaSpecification.LEFT_JOIN );
	        					}
	        					prolist.add(Projections.groupProperty(gb));
	        				}
	        				//criteria.setProjection(prolist);
	        			}else{
	        				//criteria.setProjection(Projections.groupProperty(pf.getMatchValue().toString()));
	        			}
	                }
	                else if ( pf.getMatchType().equals( MatchType.OA ) ) {
	                	//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getMatchValue().toString(), CriteriaSpecification.LEFT_JOIN );
	                	//criteria.addOrder(Order.asc(pf.getMatchValue().toString()));
	                }
	                else if ( pf.getMatchType().equals( MatchType.OD ) ) {
	                	//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getMatchValue().toString(), CriteriaSpecification.LEFT_JOIN );
	                	//criteria.addOrder(Order.desc(pf.getMatchValue().toString()));
	                }
	                else {
	                    throw new BusinessException( "查询条件错误，未知的查询操作符。" );
	                }
	            }*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List getByHql(String hql) {
		 Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		 return query.list();
	}
	@Override
	public List<Map<String, Object>> getBySqlToMap(String sql){
		 Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		 query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 return query.list();
	}
}
