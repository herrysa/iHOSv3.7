package com.huge.dao.hibernate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;

import com.huge.exceptions.BusinessException;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.PropertyFilter.MatchType;

public class JqueryPagerHibernateWithSearchCallBack
    extends JqueryPagerHibernateCallBack {

    List<PropertyFilter> filters;

    String group_on = "";

    JqueryPagerHibernateWithSearchCallBack( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters ) {
        super( paginatedList, object );
        this.filters = filters;

    }

    JqueryPagerHibernateWithSearchCallBack( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters, String group_on ) {
        super( paginatedList, object );
        this.filters = filters;
        this.group_on = group_on;

    }

    @Override
    public Criteria getCustomCriterion( Criteria criteria ) {
    	if(filters.size()==0){
    		return criteria;
    	}
    	Map<String, Queue<PropertyFilter>> propertyMap = new HashMap<String, Queue<PropertyFilter>>();
    	Iterator itr = this.filters.iterator();
    	String filedName = "";
    	while ( itr.hasNext() ) {
    		PropertyFilter pf = (PropertyFilter) itr.next();
    		String propertyName = pf.getPropertyName();
    		Queue<PropertyFilter> pfQueue = propertyMap.get(propertyName);
    		if(pfQueue==null){
    			pfQueue = new LinkedList<PropertyFilter>();
    			propertyMap.put(propertyName, pfQueue);
    		}
    		pfQueue.offer(pf);
    		filedName += "'"+propertyName+"',";
    	}
    	if(OtherUtil.measureNull(group_on)){
    		filedName = filedName.substring(0, filedName.length()-1);
    		group_on = "{op:'and',filter:["+filedName+"]}";
    	}
		JSONObject groupJson = JSONObject.fromObject(group_on);
		String op = groupJson.get("op").toString();
		Junction junction = null;
		if("and".equals(op)){
			junction = Restrictions.conjunction();
		}else if("or".equals(op)){
			junction = Restrictions.disjunction();
		}
		criteria.add(junction);
		JSONArray groupFilters = (JSONArray)groupJson.get("filter");
		Object filter1 = groupFilters.get(0);
		if(filter1 instanceof JSONObject){
			Iterator<JSONObject> fliterIt = groupFilters.iterator();
			while(fliterIt.hasNext()){
				JSONObject subGroupJson = fliterIt.next();
				getGroupCriteria(criteria,junction,subGroupJson,propertyMap);
			}
		}else{
			Iterator<String> fliterIt = groupFilters.iterator();
			while(fliterIt.hasNext()){
				String propertyName = fliterIt.next();
				if("*".equals(propertyName)){
					Set<Entry<String, Queue<PropertyFilter>>> entrySet = propertyMap.entrySet();
					for(Entry<String, Queue<PropertyFilter>> entry : entrySet){
						Queue<PropertyFilter> pfQueue = entry.getValue();
						Iterator<PropertyFilter> qIt = pfQueue.iterator();
						while(qIt.hasNext()){
							PropertyFilter pf = qIt.next();
							if(pf!=null){
								addFilter(criteria,junction,pf);
							}
						}
					}
				}else{
					Queue<PropertyFilter> pfQueue = propertyMap.get(propertyName);
					PropertyFilter pf = null;
					pf = pfQueue.poll();
					if(pf!=null){
						addFilter(criteria,junction,pf);
					}
				}
			}
		}
		return criteria;
    }
    
    private void getGroupCriteria(Criteria criteria,Junction junction,JSONObject groupJson,Map<String, Queue<PropertyFilter>> propertyMap){
    	String op = groupJson.get("op").toString();
		JSONArray groupFilters = (JSONArray)groupJson.get("filter");
		Junction subJunction = null;
		if("and".equals(op)){
			subJunction = Restrictions.conjunction();
		}else if("or".equals(op)){
			subJunction = Restrictions.disjunction();
		}
		junction.add(subJunction);
		Object filter1 = groupFilters.get(0);
		if(filter1 instanceof JSONObject){
			Iterator<JSONObject> fliterIt = groupFilters.iterator();
			while(fliterIt.hasNext()){
				JSONObject subGroupJson = fliterIt.next();
				getGroupCriteria(criteria,subJunction,subGroupJson,propertyMap);
			}
		}else{
			Iterator<String> fliterIt = groupFilters.iterator();
			while(fliterIt.hasNext()){
				String propertyName = fliterIt.next();
				if("*".equals(propertyName)){
					Set<Entry<String, Queue<PropertyFilter>>> entrySet = propertyMap.entrySet();
					for(Entry<String, Queue<PropertyFilter>> entry : entrySet){
						Queue<PropertyFilter> pfQueue = entry.getValue();
						Iterator<PropertyFilter> qIt = pfQueue.iterator();
						while(qIt.hasNext()){
							PropertyFilter pf = qIt.next();
							if(pf!=null){
								addFilter(criteria,subJunction,pf);
							}
						}
					}
				}else{
					Queue<PropertyFilter> pfQueue = propertyMap.get(propertyName);
					PropertyFilter pf = null;
					pf = pfQueue.poll();
					if(pf!=null){
						addFilter(criteria,subJunction,pf);
					}
				}
			}
		}
    }
    
	private void addFilter(Criteria criteria,Junction junction,PropertyFilter pf){
		try {
			criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName() ,1);
			String fieldName= CriteriaUtil.getSearchAliasFieldname(pf.getPropertyName());
			if(pf.getMatchType().equals( MatchType.LIKE)){
	            String v = (String) pf.getMatchValue();
	            boolean bp = v.startsWith( "*" );
	            boolean ep = v.endsWith( "*" );
	            v = v.replaceAll( "\\*", "" );
	            if ( bp && ep )
	            	junction.add( Restrictions.like( fieldName, v, MatchMode.ANYWHERE ) );
	            else if ( bp && !ep )
	            	junction.add( Restrictions.like( fieldName, v, MatchMode.END ) );
	
	            else if ( !bp && ep )
	            	junction.add( Restrictions.like( fieldName, v, MatchMode.START ) );
	            else
	            	junction.add( Restrictions.like( fieldName, v, MatchMode.EXACT ) );
	
	        }
	        else if ( pf.getMatchType().equals( MatchType.EQ ) ) {
	        	junction.add( Restrictions.eq( fieldName, pf.getMatchValue() ) );
	        }
	        else if ( pf.getMatchType().equals( MatchType.GE ) ) {
	        	junction.add( Restrictions.ge( fieldName, pf.getMatchValue() ) );
	        }
	        else if ( pf.getMatchType().equals( MatchType.GT ) ) {
	        	junction.add( Restrictions.gt( fieldName, pf.getMatchValue() ) );
	        }
	        else if ( pf.getMatchType().equals( MatchType.IN ) ) {
	        	junction.add( Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) );
	        }
	        else if ( pf.getMatchType().equals( MatchType.NI ) ) {
	        	junction.add( Restrictions.not(Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) ));
	        }
	        else if ( pf.getMatchType().equals( MatchType.ISNOTNULL ) ) {
	        	junction.add( Restrictions.isNotNull( fieldName ) );
	        }
	        else if ( pf.getMatchType().equals( MatchType.ISNULL ) ) {
	        	junction.add( Restrictions.isNull( fieldName ) );
	        }
	        else if ( pf.getMatchType().equals( MatchType.LE ) ) {
	        	junction.add( Restrictions.le( fieldName, pf.getMatchValue() ) );
	        }
	        else if ( pf.getMatchType().equals( MatchType.LT ) ) {
	        	junction.add( Restrictions.lt( fieldName, pf.getMatchValue() ) );
	        }
	        else if ( pf.getMatchType().equals( MatchType.NE ) ) {
	        	
	        	junction.add( Restrictions.ne( fieldName, pf.getMatchValue() ) );
	        }
	        else if ( pf.getMatchType().equals( MatchType.OA ) ) {
				Field orderEntrysField = CriteriaImpl.class.getDeclaredField("orderEntries");
				orderEntrysField.setAccessible(true);
				List orderEntrys = (List) orderEntrysField.get(criteria);
				if(orderEntrys==null){
					orderEntrysField.set(criteria,new ArrayList());
				}
				
				criteria.addOrder(Order.asc(fieldName.toString()));
	        }
	        else if ( pf.getMatchType().equals( MatchType.OD ) ) {
				Field orderEntrysField = CriteriaImpl.class.getDeclaredField("orderEntries");
				orderEntrysField.setAccessible(true);  
				List orderEntrys = (List) orderEntrysField.get(criteria);
				if(orderEntrys==null){
					orderEntrysField.set(criteria,new ArrayList());
				}
	        	criteria.addOrder(Order.desc(fieldName.toString()));
	        }else if( pf.getMatchType().equals(MatchType.SQ)){
	        	junction.add( Restrictions.sqlRestriction(pf.getMatchValue().toString()) );
	        }
	        else {
	            throw new BusinessException( "查询条件错误，未知的查询操作符。" );
	        }
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public Criteria getCustomCriterion1( Criteria criteria ) {
        Iterator itr = this.filters.iterator();
        int i = 0;
        try {
        if ( group_on.equals( "OR" ) ) {
            Disjunction disjunction = Restrictions.disjunction();
            while ( itr.hasNext() ) {
                PropertyFilter pf = (PropertyFilter) itr.next();
                criteria = CriteriaUtil.createAliasCriteria((CriteriaImpl) criteria, pf.getPropertyName());
                String fieldName= CriteriaUtil.getSearchAliasFieldname(pf.getPropertyName());
                if ( pf.getMatchType().equals( MatchType.LIKE ) ) {
                    String v = (String) pf.getMatchValue();
                    boolean bp = v.startsWith( "*" );
                    boolean ep = v.endsWith( "*" );
                    v = v.replaceAll( "\\*", "" );
                    if ( bp && ep )
                        disjunction.add( Restrictions.like( fieldName, v, MatchMode.ANYWHERE ) );
                    else if ( bp && !ep )
                        disjunction.add( Restrictions.like( fieldName, v, MatchMode.END ) );

                    else if ( !bp && ep )
                        disjunction.add( Restrictions.like( fieldName, v, MatchMode.START ) );
                    else
                        disjunction.add( Restrictions.like( fieldName, v, MatchMode.EXACT ) );

                }
                else if ( pf.getMatchType().equals( MatchType.EQ ) ) {
                    disjunction.add( Restrictions.eq( fieldName, pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.GE ) ) {
                    disjunction.add( Restrictions.ge( fieldName, pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.GT ) ) {
                    disjunction.add( Restrictions.gt( fieldName, pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.IN ) ) {
//                	Object[] matchValueObjs = (Object[]) pf.getMatchValue();
//                	int mvLength = matchValueObjs.length;
//                	if(mvLength > 1000){
//                		Criterion criterion = null;
//                		Object[] newObjs = OtherUtil.splitArray(matchValueObjs, 1000);
//                		for (Object objTemp : newObjs) {
//                			Object[] objsTemp = (Object[])objTemp;
//                			if(criterion == null){
//                				criterion = Restrictions.in(fieldName, objsTemp);
//                			}else{
//                				criterion = Restrictions.or(criterion, Restrictions.in(fieldName, objsTemp));
//                			}
//                		}
//                		disjunction.add(criterion);
//                	}else{
//                		disjunction.add( Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) );
//                	}
                    disjunction.add( Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.ISNOTNULL ) ) {
                    disjunction.add( Restrictions.isNotNull( fieldName ) );
                }
                else if ( pf.getMatchType().equals( MatchType.ISNULL ) ) {
                    disjunction.add( Restrictions.isNull( fieldName ) );
                }
                else if ( pf.getMatchType().equals( MatchType.LE ) ) {
                    disjunction.add( Restrictions.le( fieldName, pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.LT ) ) {
                    disjunction.add( Restrictions.lt( fieldName, pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.NE ) ) {
                    disjunction.add( Restrictions.ne( fieldName, pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.OA ) ) {
                	if(i==0){
                		Field orderEntrysField = CriteriaImpl.class.getDeclaredField("orderEntries");
                		orderEntrysField.setAccessible(true);  
                		//orderEntrys = (List) orderEntrysField.get(criteria);
                		orderEntrysField.set(criteria,new ArrayList());
                		i++;
                	}
                	//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName().toString(), CriteriaSpecification.LEFT_JOIN );
                	criteria.addOrder(Order.asc(fieldName.toString()));
                }
                else if ( pf.getMatchType().equals( MatchType.OD ) ) {
                	if(i==0){
                		Field orderEntrysField = CriteriaImpl.class.getDeclaredField("orderEntries");
                		orderEntrysField.setAccessible(true);  
                		//orderEntrys = (List) orderEntrysField.get(criteria);
                		orderEntrysField.set(criteria,new ArrayList());
                	}
                	//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName().toString(), CriteriaSpecification.LEFT_JOIN );
                	criteria.addOrder(Order.desc(fieldName.toString()));
                }else if( pf.getMatchType().equals(MatchType.SQ)){
                	criteria.add( Restrictions.sqlRestriction(pf.getMatchValue().toString()) );
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
                String fieldName= CriteriaUtil.getSearchAliasFieldname(pf.getPropertyName());
                if ( pf.getMatchType().equals( MatchType.LIKE ) ) {
                    String v = (String) pf.getMatchValue();
                    boolean bp = v.startsWith( "*" );
                    boolean ep = v.endsWith( "*" );
                    v = v.replaceAll( "\\*", "" );
                    if ( bp && ep )
                        criteria.add( Restrictions.like( fieldName, v, MatchMode.ANYWHERE ) );
                    else if ( bp && !ep )
                        criteria.add( Restrictions.like( fieldName, v, MatchMode.END ) );

                    else if ( !bp && ep )
                        criteria.add( Restrictions.like( fieldName, v, MatchMode.START ) );
                    else
                        criteria.add( Restrictions.like( fieldName, v, MatchMode.EXACT ) );

                }
                else if ( pf.getMatchType().equals( MatchType.EQ ) ) {
                    criteria.add( Restrictions.eq( fieldName, pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.GE ) ) {
                    criteria.add( Restrictions.ge( fieldName, pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.GT ) ) {
                    criteria.add( Restrictions.gt( fieldName, pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.IN ) ) {
//                	Object[] matchValueObjs = (Object[]) pf.getMatchValue();
//                	int mvLength = matchValueObjs.length;
//                	if(mvLength > 1000){
//                		Disjunction disjunction = Restrictions.disjunction();
//                		Object[] newObjs = OtherUtil.splitArray(matchValueObjs, 1000);
//                		for (Object objTemp : newObjs) {
//                			Object[] objsTemp = (Object[])objTemp;
//                			disjunction.add(Restrictions.in(fieldName, objsTemp));
//                		}
//                		criteria.add(disjunction);
//                	}else{
//                		criteria.add( Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) );
//                	}
                		criteria.add( Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.NI ) ) {
                    criteria.add( Restrictions.not(Restrictions.in( fieldName, (Object[]) pf.getMatchValue() ) ));
                }
                else if ( pf.getMatchType().equals( MatchType.ISNOTNULL ) ) {
                    criteria.add( Restrictions.isNotNull( fieldName ) );
                }
                else if ( pf.getMatchType().equals( MatchType.ISNULL ) ) {
                    criteria.add( Restrictions.isNull( fieldName ) );
                }
                else if ( pf.getMatchType().equals( MatchType.LE ) ) {
                    criteria.add( Restrictions.le( fieldName, pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.LT ) ) {
                    criteria.add( Restrictions.lt( fieldName, pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.NE ) ) {
                	
                    criteria.add( Restrictions.ne( fieldName, pf.getMatchValue() ) );
                }
                else if ( pf.getMatchType().equals( MatchType.OA ) ) {
                	if(i==0){
                		Field orderEntrysField = CriteriaImpl.class.getDeclaredField("orderEntries");
                		orderEntrysField.setAccessible(true);  
                		//orderEntrys = (List) orderEntrysField.get(criteria);
                		orderEntrysField.set(criteria,new ArrayList());
                	}
                	//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName().toString(), CriteriaSpecification.LEFT_JOIN );
                	criteria.addOrder(Order.asc(fieldName.toString()));
                }
                else if ( pf.getMatchType().equals( MatchType.OD ) ) {
                	if(i==0){
                		Field orderEntrysField = CriteriaImpl.class.getDeclaredField("orderEntries");
                		orderEntrysField.setAccessible(true);  
                		//orderEntrys = (List) orderEntrysField.get(criteria);
                		orderEntrysField.set(criteria,new ArrayList());
                		i++;
                	}
                	//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName().toString(), CriteriaSpecification.LEFT_JOIN );
                	criteria.addOrder(Order.desc(fieldName.toString()));
                }else if( pf.getMatchType().equals(MatchType.SQ)){
                	criteria.add( Restrictions.sqlRestriction(pf.getMatchValue().toString()) );
                }
                else {
                    throw new BusinessException( "查询条件错误，未知的查询操作符。" );
                }
            }
        }
        } catch (Exception e) {
			e.printStackTrace();
		}
        return criteria;
    }
}
