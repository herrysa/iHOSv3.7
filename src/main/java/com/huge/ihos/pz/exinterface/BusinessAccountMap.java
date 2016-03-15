package com.huge.ihos.pz.exinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.foundation.util.StringUtil;
import com.huge.ihos.pz.businesstype.model.BusinessType;
import com.huge.ihos.pz.businesstype.model.BusinessTypeD;
import com.huge.ihos.pz.businesstype.model.BusinessTypeJ;
import com.huge.ihos.pz.businesstype.service.BusinessTypeManager;
import com.huge.ihos.system.reportManager.search.dao.SearchItemDao;
import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.model.SearchItem;
import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.ihos.system.reportManager.search.util.LSearchBracket;
import com.huge.ihos.system.reportManager.search.util.RSearchBracket;
import com.huge.ihos.system.reportManager.search.util.SearchBoolean;
import com.huge.ihos.system.reportManager.search.util.SearchCriteria;
import com.huge.ihos.system.reportManager.search.util.SearchOperator;
import com.huge.util.OtherUtil;
import com.huge.util.TestTimer;
import com.huge.util.UUIDGenerator;
import com.huge.webapp.util.SpringContextHelper;

public class BusinessAccountMap {

	private BusinessTypeManager businessTypeManager = (BusinessTypeManager)SpringContextHelper.getBean("businessTypeManager");
	private QueryManager queryManager = (QueryManager)SpringContextHelper.getBean("queryManager");
	private SearchItemDao searchItemDao = (SearchItemDao)SpringContextHelper.getBean("searchItemDao");
	@SuppressWarnings("unused")
	public void businessAccountMap(Map param){
		String businessId ;
		String businessIdSql;
		String checkperiod;
		StringBuffer dataIdSql = new StringBuffer();
		String ids ;
		Object checkperiodO = param.get("checkperiod");
		Object bidO = param.get("businessId");
		Object idsO = param.get("ids");
		Object snapCode = param.get("snapCode");
		if(bidO!=null){
			//整理业务配置表
			businessId = bidO.toString();
			businessIdSql = "'"+businessId+"'";
			BusinessType businessType = businessTypeManager.get(businessId);
			Set<BusinessTypeJ> businessTypeJs = businessType.getBusinessTypeJs();
			Set<BusinessTypeD> businessTypeDs = businessType.getBusinessTypeDs();
			
	        List<String> mapColList = new ArrayList<String>();
	        String acountColName = "";
	        boolean rowToColJ = false;
	        List<String> mapColDList = new ArrayList<String>();
	        String acountColDName = "";
	        boolean rowToColD = false;
	        //取出作对照的列和款及科目列
	        for(BusinessTypeJ businessTypeJ : businessTypeJs){
	        	String field = businessTypeJ.getFieldName();
	        	if(businessTypeJ.getAccCol()!=null&&businessTypeJ.getAccCol()){
	        		if(businessTypeJ.getRowToCol()!=null&&businessTypeJ.getRowToCol()){
	        			rowToColJ = true;
	        		}
	        		acountColName = field;   //会计科目列
	        	}else{
	        		mapColList.add(field);  //作对照的列
	        	}
	        }
	        
	        for(BusinessTypeD businessTypeD : businessTypeDs){
	        	String field = businessTypeD.getFieldName();
	        	if(businessTypeD.getAccCol()!=null&&businessTypeD.getAccCol()){
	        		if(businessTypeD.getRowToCol()!=null&&businessTypeD.getRowToCol()){
	        			rowToColD = true;
	        		}
	        		acountColDName = field;
	        	}else{
	        		mapColDList.add(field);
	        	}
	        }
	        
	        boolean dataFromSearch = true;
	        String dataSql = "";
	        String dataIdCol = "";
	        String collectTable = "";
	        if(idsO==null||"".equals(idsO.toString())){
	        	collectTable = businessType.getCollectTempTable();
				if(OtherUtil.measureNotNull(collectTable)){
					dataFromSearch = false;
				}
	        }
			//取出对应search的sql
	        if(dataFromSearch){
	        	String searchName = businessType.getSearchName();
				Search search = queryManager.getSearchBySearchName( searchName );
				dataIdCol = search.getMyKey();
				SearchCriteria criteria = new SearchCriteria();
				
				SearchItem[] itemArray = this.searchItemDao.getEnabledSearchItemsBySearchIdOrdered( search.getSearchId() );
		        /*
		         * Set items = search.getSearchitems(); if (items.size() > 0) {
		         * itemArray = new SearchItem[items.size()]; items.toArray(itemArray); }
		         */
		        for ( int i = 0; i < itemArray.length; i++ ) {
		            if ( !itemArray[i].isSearchFlag() )
		                continue;
		            String htmlFieldName = itemArray[i].getHtmlField();
		            Object pValue = param.get( htmlFieldName );
		            String htmlFieldValue = null;
		            if(pValue!=null){
		            	htmlFieldValue = pValue.toString();
		            }
		            if ( ( htmlFieldValue == null ) || ( htmlFieldValue.length() <= 0 ) )
		                continue;
		            String dbFieldName = itemArray[i].getField();
		            String operator = itemArray[i].getOperator();
		            if ( operator == null )
		                operator = "";
		            if ( ( operator.equals( ">" ) ) || ( operator.equals( ">=" ) ) || ( operator.equals( "<" ) ) || ( operator.equals( "<=" ) )
		                || ( operator.equals( "<>" ) ) )
		                htmlFieldValue = operator + htmlFieldValue;
		            /*else if(operator.equals("in")){
		            	
		            	
		            	
		            	htmlFieldValue = operator +"(" +htmlFieldValue+")";
		            	
		            }*/
		            else if ( operator.equals( "*" ) )
		                htmlFieldValue = htmlFieldValue + operator;
		            else if ( operator.equals( "**" ) )
		                htmlFieldValue = operator + htmlFieldValue + operator;
		            // else if(operator.equalsIgnoreCase("like"))
		            // htmlFieldValue = operator + " %" +htmlFieldValue+"%";
		            boolean bool = itemArray[i].getMutiSelect();

		            if ( bool ) {
		                String[] arrayOfString = StringUtil.strToArray( htmlFieldValue, "," );
		                if ( arrayOfString.length > 1 )
		                    for ( int j = 0; j < arrayOfString.length; j++ )
		                        if ( j == 0 )
		                            criteria.addBinding( SearchBoolean.AND, LSearchBracket.LEFT, dbFieldName, SearchOperator.EQUAL, arrayOfString[j],
		                                                 htmlFieldName );
		                        else if ( j == arrayOfString.length - 1 )
		                            criteria.addBinding( SearchBoolean.OR, dbFieldName, SearchOperator.EQUAL, arrayOfString[j], RSearchBracket.RIGHT,
		                                                 htmlFieldName );
		                        else
		                            criteria.addBinding( SearchBoolean.OR, dbFieldName, SearchOperator.EQUAL, arrayOfString[j], htmlFieldName );
		                else
		                    criteria.addBinding( dbFieldName, htmlFieldValue, htmlFieldName );
		            }
		            else {
		                criteria.addBinding( dbFieldName, htmlFieldValue, htmlFieldName );
		            }
		        }
				
				criteria.setSourceSql( search.getMysql() );
				criteria.getWhereReplaceCount();
				dataSql = criteria.getRealSql();
				Object[] args = criteria.getRealAgrs();
				for(Object arg : args){
					String argStr = arg.toString();
					dataSql = dataSql.replaceFirst("\\?", "'"+argStr+"'");
				}
	        }else{
	        	dataIdCol = "tempId";
	        	dataSql = "SELECT * FROM "+collectTable+" WHERE snapCode='"+snapCode.toString()+"'";
	        }
			
			
			//取出对应业务的对照数据表
			String mapJSql = "SELECT * FROM "+businessType.getBusinessId()+"_J";
        	List<Map<String, Object>> mapJList = businessTypeManager.getBySqlToMap(mapJSql);
        	String mapDSql = "SELECT * FROM "+businessType.getBusinessId()+"_D";
        	List<Map<String, Object>> mapDList = businessTypeManager.getBySqlToMap(mapDSql);
        	Map<String, Map<String, Object>> mapJMap = new HashMap<String, Map<String, Object>>();
        	for(Map<String, Object> mapJData : mapJList){
        		String dataKey = "";
        		for(String field : mapColList){
        			dataKey += mapJData.get(field).toString();
        		}
        		mapJMap.put(dataKey, mapJData); //根据前面拼的对照列作key
        	}
        	Map<String, Map<String, Object>> mapDMap = new HashMap<String, Map<String, Object>>();
        	for(Map<String, Object> mapDData : mapDList){
        		String dataKey = "";
        		for(String field : mapColDList){
        			dataKey += mapDData.get(field).toString();
        		}
        		mapDMap.put(dataKey, mapDData);
        	}
			
        	//处理从页面传数据ID和全部数据的情况
			List<String> insertList = new ArrayList<String>();
			StringBuffer insertSqls = new StringBuffer();
			List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
			String[] dataIdArr = null;
			boolean noId = false;
			if(idsO==null||"".equals(idsO.toString())){
				dataList = businessTypeManager.getBySqlToMap(dataSql);
				noId = true;
			}else{
				ids = idsO.toString();
				dataIdArr = ids.split(",");
				int dataIdIndex = 0;
				for(String dataId :dataIdArr){
					dataId = "'"+dataId+"'";
					if(dataIdIndex==dataIdArr.length-1){
						dataIdSql.append(dataId);
					}else{
						dataIdSql.append(dataId+",");
					}
			        dataSql += " and "+dataIdCol+"="+dataId;
			        dataList.addAll(businessTypeManager.getBySqlToMap(dataSql));
			        dataIdIndex++;
				}
			}
			
			String dataInSql = dataIdSql.toString();
			if(noId){
				dataInSql = "SELECT u."+dataIdCol+" FROM ("+dataSql+") u";
			}
			String deleteSql = "DELETE PZ_mapTemp WHERE dataId IN ("+dataInSql+") AND businessId="+businessIdSql+" AND direction IN ('借','贷')";
			businessTypeManager.executeSql(deleteSql);
			
			JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" ) );
			//处理数据
			for(Map<String, Object> dataMap :dataList){
				String dataId = dataMap.get(dataIdCol).toString();
		        dataId = "'"+dataId+"'";
		        
		        Map<String, Object> dataUpcase = new HashMap<String, Object>();
		        Set<Entry<String, Object>> dataSet= dataMap.entrySet();
		        for(Entry<String, Object> dataEntry : dataSet){
		        	String key = dataEntry.getKey();
		        	Object v = dataEntry.getValue();
		        	dataUpcase.put(key.toUpperCase(), v);
		        }
		        
		        String dataKey = "";
		        for(String field : mapColList){
        			dataKey += dataUpcase.get(field.toUpperCase()).toString();
        		}
		        Map<String, Object> mapJData = mapJMap.get(dataKey);
		        if(mapJData!=null){
		        	Object acountColValueO = mapJData.get(acountColName);
		        	String acountColValue = "";
		        	if(acountColValueO!=null){
		        		acountColValue = acountColValueO.toString();
		        		Object acctIdO = null;
		        		if(rowToColJ){
		        			acctIdO = mapJData.get(acountColName+"_"+acountColValue);
		        		}else{
		        			acctIdO = mapJData.get(acountColName);
		        		}
		        		if(acctIdO!=null){
		        			String acctId = acctIdO.toString();
		        			String mapId = "'"+UUIDGenerator.getInstance().getNextValue()+"',";//"'"+businessType.getBusinessId()+"_"+dataId+"',";
		        			acctId = "'"+acctId+"',";
		        			String direction = "'借'";
		        			String values = mapId+dataId+","+businessIdSql+","+acctId+direction;
		        			String insertSql = "insert into PZ_mapTemp (mapId,dataId,businessId,acctId,direction) VALUES ("+values+")";
		        			insertList.add(insertSql);
		        			//insertSqls.append(insertSql+";");
		        		}
		        	}
		        }
		        
		        String dataKeyD = "";
		        for(String field : mapColDList){
        			dataKeyD += dataUpcase.get(field.toUpperCase()).toString();
        		}
		        Map<String, Object> mapDData = mapDMap.get(dataKeyD);
		        if(mapDData!=null){
		        	Object acountColValueO = mapDData.get(acountColDName);
		        	String acountColValue = "";
		        	if(acountColValueO!=null){
		        		acountColValue = acountColValueO.toString();
		        		Object acctIdO = null;
		        		if(rowToColD){
		        			acctIdO = mapDData.get(acountColDName+"_"+acountColValue);
		        		}else{
		        			acctIdO = mapDData.get(acountColDName);
		        		}
		        		if(acctIdO!=null){
		        			String acctId = acctIdO.toString();
		        			String mapId = "'"+UUIDGenerator.getInstance().getNextValue()+"',";//"'"+businessType.getBusinessId()+"_"+dataId+"',";
		        			acctId = "'"+acctId+"',";
		        			String direction = "'贷'";
		        			String values = mapId+dataId+","+businessIdSql+","+acctId+direction;
		        			String insertSql = "insert into PZ_mapTemp (mapId,dataId,businessId,acctId,direction) VALUES ("+values+")";
		        			insertList.add(insertSql);
		        			//insertSqls.append(insertSql+";");
		        		}
		        	}
		        }
		      if(insertList.size()>0&&insertList.size()%1000==0){
				TestTimer ttTestTimer= new TestTimer("sqllllll");
				ttTestTimer.begin();
				String[] sqlArr = new String[insertList.size()];
				sqlArr = insertList.toArray(sqlArr);
				//businessTypeManager.executeSql(insertSqls.toString());
				jt.batchUpdate(sqlArr);
				ttTestTimer.done();
				insertList.clear();
				//insertSqls.setLength(0);
		      }
			}
			//TestTimer ttTestTimer= new TestTimer("sqllllll");
			//ttTestTimer.begin();
			String[] sqlArr = new String[insertList.size()];
			sqlArr = insertList.toArray(sqlArr);
			//businessTypeManager.executeSql(insertSqls.toString());
			if(sqlArr!=null&&sqlArr.length>0){
				jt.batchUpdate(sqlArr);
			}
			//ttTestTimer.done();
			//businessTypeManager.executeSqlList(insertList);
		}
	}
	
	public static void main(String[] args) {
		String aaa="adsfadsf?adsfasd?";
		System.out.println(aaa.replaceFirst("\\?", "33"));
	}
}
