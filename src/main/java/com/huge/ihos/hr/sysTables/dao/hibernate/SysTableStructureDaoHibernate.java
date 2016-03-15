package com.huge.ihos.hr.sysTables.dao.hibernate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.hr.hrPerson.dao.HrPersonCurrentDao;
import com.huge.ihos.hr.sysTables.dao.SysTableContentDao;
import com.huge.ihos.hr.sysTables.dao.SysTableStructureDao;
import com.huge.ihos.hr.sysTables.model.SysTableContent;
import com.huge.ihos.hr.sysTables.model.SysTableStructure;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.PropertyFilter.MatchType;


@Repository("sysTableStructureDao")
public class SysTableStructureDaoHibernate extends GenericDaoHibernate<SysTableStructure, String> implements SysTableStructureDao {

	
    public SysTableStructureDaoHibernate() {
        super(SysTableStructure.class);
    }
    
    private HrPersonCurrentDao hrPersonCurrentDao;
    private SysTableContentDao sysTableContentDao;
	@Autowired
	public void setHrPersonCurrentDao(HrPersonCurrentDao hrPersonCurrentDao) {
		this.hrPersonCurrentDao = hrPersonCurrentDao;
	}
	@Autowired
	public void setSysTableContentDao(SysTableContentDao sysTableContentDao) {
		this.sysTableContentDao = sysTableContentDao;
	}
    @SuppressWarnings("rawtypes")
	public JQueryPager getSysTableStructureCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, SysTableStructure.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getSysTableStructureCriteria", e);
			return paginatedList;
		}

	}
    
    @Override
    public void insertField(String id){
    	SysTableStructure sysTableStructure = this.get(id);
    	String tableName = sysTableStructure.getTableContent().getBdinfo().getTableName();
    	String fieldName = sysTableStructure.getFieldInfo().getFieldCode();
    	String fieldKind = sysTableStructure.getFieldInfo().getDataType();
    	int filedLength = 0;
    	int filedDecimal = 0;
    	if(sysTableStructure.getFieldInfo().getDataLength()!=null){
    		filedLength=sysTableStructure.getFieldInfo().getDataLength();
    	}
    	if(sysTableStructure.getFieldInfo().getDataDecimal()!=null){
    		filedDecimal =sysTableStructure.getFieldInfo().getDataDecimal();
    	}
    	String filedDefault = sysTableStructure.getFieldInfo().getFieldDefault();
    	Boolean notNull = sysTableStructure.getFieldInfo().getNotNull();
    	String defaultName = "df_" +tableName +"_" + fieldName;//默认值约束名字
    	String sql="alter table " + tableName + " add " + fieldName + " ";
    	if(fieldKind.equals("1")){
    		if(filedLength==0){
    			filedLength=50;
    		}
    		sql =sql + "varchar(" + filedLength + ")";
    		if(filedDefault!=null){
    			if(!filedDefault.equals("")){
    				sql = sql + " constraint "+defaultName+" default('"+filedDefault+"')";
    			}
    		}
    	}else if(fieldKind.equals("2")){
    		if(filedLength==0){
    			sql =sql + "decimal(18,"+filedDecimal+")";
    		}else{   			
    			sql =sql + "decimal("+filedLength+","+filedDecimal+")";
    		}
    		if(filedDefault!=null){
    			if(!filedDefault.equals("")){
    				sql = sql + " constraint "+defaultName+" default(("+filedDefault+"))";
    			}
    		}
    	}else if(fieldKind.equals("3")){
    		sql =sql + "bit";
    		if(filedDefault!=null){
    			if(!filedDefault.equals("")){
    				sql = sql +" constraint "+defaultName+ " default(("+filedDefault+"))";
    			}
    		}
    	}else if(fieldKind.equals("4")){
    		sql =sql + "datetime";
    	}else if(fieldKind.equals("5")){
    		sql =sql + "int";
    		if(filedDefault!=null){
    			if(!filedDefault.equals("")){
    				sql = sql + " constraint "+defaultName+" default(("+filedDefault+"))";
    			}
    		}
    	}else if(fieldKind.equals("6")){
    		sql =sql + "money";
    		if(filedDefault!=null){
    			if(!filedDefault.equals("")){
    				sql = sql +" constraint "+defaultName+ " default(("+filedDefault+"))";
    			}
    		}
    	}else if(fieldKind.equals("7")){
    		sql =sql + "varchar(50)";    		 
    	}else{
    		return;
    	}
    	if(notNull){
    		sql=sql+" not null";
    	}
    	excuteSql(sql); 
    }
    
    @Override
    public void deleteField(String id){
    	SysTableStructure sysTableStructure = this.get(id);
    	String tableName = sysTableStructure.getTableContent().getBdinfo().getTableName();
    	String fieldName = sysTableStructure.getFieldInfo().getFieldCode();
    	String defaultName = "df_" +tableName +"_" + fieldName;//默认值约束名字
    	String filedDefault = sysTableStructure.getFieldInfo().getFieldDefault();
    	if(filedDefault!=null){
			if(!filedDefault.equals("")){				
				String filedDeaultSql = "alter table " + tableName + " drop constraint " +defaultName;
				excuteSql(filedDeaultSql); 
			}
		}
    	String sql="alter table " + tableName + " drop column  " + fieldName;    	
    	excuteSql(sql);         
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<SysTableStructure> getTableStructureByTableCode(String tableCode){
    	List<SysTableStructure> sysTableStructures = new ArrayList<SysTableStructure>();
    	String hql = "from " + this.getPersistentClass().getSimpleName() + " where  tableContent.bdinfo.tableName = ? and batchEdit = 1";
		hql += " ORDER BY sn ASC";
		sysTableStructures = this.getHibernateTemplate().find(hql,tableCode);
		return sysTableStructures;
	}
    @Override
    public List<SysTableStructure> getTableStructureDataAndDigital(String tableCode){
    	List<SysTableStructure> sysTableStructures = new ArrayList<SysTableStructure>();
    	String hql = "from " + this.getPersistentClass().getSimpleName() + " where  tableContent.bdinfo.tableName = ? and fieldInfo.dataType in ?";
		hql += " ORDER BY sn ASC";
		String dataType="('2','4','5','6')";
		sysTableStructures = this.getHibernateTemplate().find(hql,tableCode,dataType);
		return sysTableStructures;
    }
	@Override
    public List<Object[]> getDataByTableCode(String tableCode){
    	String sql = "select code,";   	
		List<SysTableStructure> sysTableStructures =this.getTableStructureByTableCode(tableCode);
		if(sysTableStructures != null){
			String mainTablePk =  sysTableStructures.get(0).getTableContent().getTableKind().getMainTablePK();
			sql +=  mainTablePk;
			for(SysTableStructure sysTableStructureTemp:sysTableStructures){
				sql += ","+ sysTableStructureTemp.getFieldInfo().getFieldCode() +" ";
			}
		}
		sql += " from " + tableCode;
    	return getBySql(sql);   	
    }
    @Override
    public List<Object[]> getsysTableData(String code,String tableCode){
    	String sql = "select code,";  
    	//Map<String,String> map = new HashMap<String, String>();
		List<SysTableStructure> sysTableStructures =this.getTableStructureByTableCode( tableCode);
		//map.put("code", null);	
		if(sysTableStructures != null){
			String mainTablePk =  sysTableStructures.get(0).getTableContent().getTableKind().getMainTablePK();
			//map.put(mainTablePk, null);
			sql +=  mainTablePk;
			for(SysTableStructure sysTableStructureTemp:sysTableStructures){
				sql += ","+ sysTableStructureTemp.getFieldInfo().getFieldCode() +" ";
				//map.put(sysTableStructureTemp.getCode(), null);
			}
		}
		sql += " from " + tableCode +" where code='" + code +"'";
    	return getBySql(sql);
    }
    
    @Override
    public void deleteSystableData(String tableCode,String code){
    	String sql="delete from " + tableCode+" where code='"+code+"'";
    	excuteSql(sql);
    }
    
	@SuppressWarnings("unchecked")
	@Override
	public JQueryPager getSubSets(JQueryPager pagedRequests,String subTableCode,List<PropertyFilter> filters,String tableKind) {
		Session session = this.sessionFactory.getCurrentSession();
		List<String> columns = getColumns(subTableCode,tableKind);
		filters=getOrderBySql(subTableCode,filters);
		String sql = createOriginalSql(columns,subTableCode,filters);
		Query query = session.createSQLQuery(sql);
		List<Map<String,Object>> results = query.list();
		pagedRequests.setTotalNumberOfRows(results==null?0:results.size());
		sql = createPageSql(pagedRequests,sql);
		query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		results = query.list();
		pagedRequests.setList(results);
		return pagedRequests;
	}
	
	private String createPageSql(JQueryPager pagedRequests,String sql){
		int seg1 = sql.indexOf("FROM");
		String prevSql = sql.substring(0, seg1);
		int seg2 = sql.indexOf("ORDER BY");
		String middleSql = "",lastSql = "";
		if(seg2>0){
			middleSql = sql.substring(seg1,seg2);
			lastSql = sql.substring(seg2);
		}else{
			middleSql = sql.substring(seg1);
		}
		
		String sortColumn = pagedRequests.getSortCriterion();
		if(sortColumn !=null){
			if(lastSql.equals("")||lastSql.contains(sortColumn)){
				lastSql = "ORDER BY "+sortColumn + " "+pagedRequests.getSortDirection().toSqlString();
			}else{
				lastSql += ","+sortColumn + " "+pagedRequests.getSortDirection().toSqlString();
			}
		}
		sql = prevSql + ",ROW_NUMBER() OVER( "+lastSql+" ) AS rowNumber "+middleSql;
		//String pageSql = "SELECT * FROM ( "+sql+""
		//		+ ") AS B WHERE rowNumber BETWEEN "+pagedRequests.getStart()+" AND "+pagedRequests.getEnd();
		String pageSql = "SELECT * FROM ( "+sql+""
				+ ") AS B WHERE rowNumber > "+pagedRequests.getStart()+" AND rowNumber <= "+pagedRequests.getEnd();
		return pageSql;
	}
	
	private String createOriginalSql(List<String> columns,String table,List<PropertyFilter> filters){
		String sql = "SELECT ";
		for(String column:columns){
			sql += column + " AS "+column +",";
		}
		sql = OtherUtil.subStrEnd(sql, ",");
		sql += " FROM "+table+" WHERE 1=1";
		String conditions = "",orders = "";
		Iterator<PropertyFilter> ite = filters.iterator();
		while ( ite.hasNext() ) {
			PropertyFilter pf = (PropertyFilter) ite.next();
			if(pf.getMatchType().equals( MatchType.OA )||pf.getMatchType().equals( MatchType.OD )||pf.getMatchType().equals( MatchType.GP )){
			}else{
				conditions += " AND "+pf.getPropertyName();
			}
			if(pf.getMatchType().equals( MatchType.EQ )){
				conditions += " = '"+pf.getMatchValue()+"'";
			}else if(pf.getMatchType().equals( MatchType.NE)){
				conditions += " <> '"+pf.getMatchValue()+"'";
			}else if(pf.getMatchType().equals( MatchType.GE )){
				conditions += " >= '"+pf.getMatchValue()+"'";
			}else if(pf.getMatchType().equals( MatchType.GT )){
				conditions += " > '"+pf.getMatchValue()+"'";			 
			}else if(pf.getMatchType().equals( MatchType.LE )){
				conditions += " <= '"+pf.getMatchValue()+"'";	 
			}else if(pf.getMatchType().equals( MatchType.LT)){
				conditions += " < '"+pf.getMatchValue()+"'";	 
			}else if(pf.getMatchType().equals( MatchType.ISNOTNULL )){
				conditions += " IS NOT NULL ";	
			}else if(pf.getMatchType().equals( MatchType.ISNULL )){
				conditions += " IS NULL ";
			}else if(pf.getMatchType().equals( MatchType.IN )){
				conditions += " IN ( "+pf.getMatchValue()+" )";
			}else if(pf.getMatchType().equals( MatchType.NI )){
				conditions += " NOT IN ( "+pf.getMatchValue()+" )";
			}else if(pf.getMatchType().equals( MatchType.LIKE ) ) {
				String v = (String) pf.getMatchValue();
				boolean bp = v.startsWith( "*" );
                boolean ep = v.endsWith( "*" );
                v = v.replaceAll( "\\*", "" );
                if( bp && ep ){
                	conditions += " LIKE '%"+v+"%'";
                }else if( bp && !ep){
                	conditions += " LIKE '%"+v+"'";
                }else if(!bp && ep){
                	conditions += " LIKE '"+v+"%'";
                }else{
                	conditions += " LIKE '"+v+"'";
                }
			}else if(pf.getMatchType().equals( MatchType.OA ) ) {
				orders += pf.getPropertyName()+" ASC,";
			}else if(pf.getMatchType().equals( MatchType.OD ) ) {
				orders += pf.getPropertyName()+" DESC,";
			}else if(pf.getMatchType().equals( MatchType.GP ) ) {
				 
			}else {
                 throw new BusinessException( "查询条件错误，未知的查询操作符。" );
			}
		}
		if(!orders.equals("")){
			orders = OtherUtil.subStrEnd(orders, ",");
			orders = " ORDER BY " +orders;
			conditions += orders;
		}
		if(!conditions.equals("")){
			sql += conditions;
		}
		return sql;
	}
	
	private List<String> getColumns(String subTableCode,String tableKind){
		List<String> columns = new ArrayList<String>();
		columns.add("code");
		columns.add("snapCode");
		if(subTableCode.startsWith("hr_person")||tableKind.equals("hr_person")){
			columns.add("personId");
		}else if(subTableCode.startsWith("hr_dept")||tableKind.equals("hr_dept")){
			columns.add("deptId");
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		//filters.add(new PropertyFilter("EQS_orgCode",orgCode));
		filters.add(new PropertyFilter("EQS_tableContent.bdinfo.tableName",subTableCode));
		filters.add(new PropertyFilter("EQB_disabled","0"));
		filters.add(new PropertyFilter("OAI_sn","sn"));
		List<SysTableStructure> sysTableStructures = this.getByFilters(filters);
		if(sysTableStructures!=null && sysTableStructures.size()>0){
			for(SysTableStructure sts:sysTableStructures){
				columns.add(sts.getFieldInfo().getFieldCode());
			}
		}
		return columns;
	}
	private List<PropertyFilter> getOrderBySql(String subTableCode,List<PropertyFilter> filters){
		List<PropertyFilter> stcfilters = new ArrayList<PropertyFilter>();
		stcfilters.add(new PropertyFilter("EQS_bdinfo.tableName",subTableCode));
		List<SysTableContent> sysTableContents=sysTableContentDao.getByFilters(stcfilters);
		Boolean notOrderBy = true;
		if(sysTableContents!=null&&sysTableContents.size()>0){
			for(SysTableContent stc:sysTableContents){
				if(OtherUtil.measureNotNull(stc.getBdinfo().getOrderByField())){
					notOrderBy=false;
					if(stc.getBdinfo().getOrderByFieldAsc()){
						filters.add(new PropertyFilter( "OAS_"+stc.getBdinfo().getOrderByField(),stc.getBdinfo().getOrderByField()));
					}else{
						filters.add(new PropertyFilter( "ODS_"+stc.getBdinfo().getOrderByField(),stc.getBdinfo().getOrderByField()));
					}
				}
			}
		}
		if(notOrderBy){
			filters.add(new PropertyFilter( "ODS_code","code"));
		}
		return filters;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void saveHrSubData(String foreignId,String snapCode,Object gridAllDatas){
		JSONObject json=JSONObject.fromObject(gridAllDatas);
		//String hql = "from " + this.getPersistentClass().getSimpleName() + " where orgCode=? and tableContent.code = ?";
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where  tableContent.bdinfo.tableName = ?";
		hql += " ORDER BY sn ASC";
		List<SysTableStructure> sysTableStructures =new ArrayList<SysTableStructure>();
		String foreignKey="";
		Map<String,String> mapFields = new HashMap<String, String>();
		String sql="";
		String tableCode="";
		String code="";
		String snapCodeNew="";
		JSONArray allDatas=json.getJSONArray("edit");
		Boolean isView=false;
		for(int i=0;i<allDatas.size();i++){
			 //获取每一个JsonObject对象
		    JSONObject myjObject = allDatas.getJSONObject(i);
			if(myjObject.size()>0){
				if(myjObject.containsKey("hrSubSetName")){
					tableCode=myjObject.getString("hrSubSetName");
					sysTableStructures.clear();
					mapFields.clear();
					sysTableStructures = this.getHibernateTemplate().find(hql,tableCode);
					if(sysTableStructures!=null){
						for(SysTableStructure sysTableStructure:sysTableStructures){
							isView=sysTableStructure.getTableContent().getIsView();
							foreignKey = sysTableStructure.getTableContent().getTableKind().getMainTablePK();
							mapFields.put(sysTableStructure.getFieldInfo().getFieldCode(), sysTableStructure.getFieldInfo().getDataType());
						}
					}	
					if(isView){
						continue;
					}
					sql="delete from "+ tableCode +" where "+foreignKey +" ='"+foreignId+"'";
					excuteSql(sql);
				}else{
					if(isView){
						continue;
					}
				    //sql=""
					Set keys = myjObject.keySet();
					String sqlEnd="";
					for (Object keyTemp : keys) {  
						String tempKey=keyTemp.toString();
				    	if(tempKey.equals("code")){
				    		if(myjObject.getString("code")==null||myjObject.getString("code").equals("")){
								code=OtherUtil.getRandomUUID();
							}else{
								code=myjObject.getString("code");
							}
				    		sql="insert into "+tableCode+" (code";
							sqlEnd="values('"+code+"'";
				    	}else if(tempKey.equals(foreignKey)){
				    		sql+=","+foreignKey;
				    		sqlEnd+=",'"+foreignId+"'";
				    	}else if(tempKey.equals("snapCode")){
				    		if(myjObject.getString("snapCode")==null||myjObject.getString("snapCode").equals("")){
				    			snapCodeNew=snapCode;
							}else{
								snapCodeNew=myjObject.getString("snapCode");
							}
				    		sql+=",snapCode";
				    		sqlEnd+=",'"+snapCodeNew+"'";
				    	}else{
				    		if(mapFields.get(tempKey).equals("4")){
				    			if(myjObject.getString(tempKey).length()>7){
				    				sql+=","+tempKey;
				    				sqlEnd += "," +"(case when len('"+myjObject.getString(tempKey)+"') > 8 and charindex('-','"+myjObject.getString(tempKey)+"') = 0 then cast(substring('"+myjObject.getString(tempKey)+"',1,4) + '-' + substring('"+myjObject.getString(tempKey)+"',5,2) + '-' + substring('"+myjObject.getString(tempKey)+"',7,2) + ' ' + substring('"+myjObject.getString(tempKey)+"',9,2) + ':' + substring('"+myjObject.getString(tempKey)+"',11,2) as datetime) else cast('"+myjObject.getString(tempKey)+"' as datetime) END)";	
				    			}
	    	   				}else if(mapFields.get(tempKey).equals("3")){
	    	   					if(myjObject.getString(tempKey).equals("Yes")){
	    	   						sql+=","+tempKey;
	    	   						sqlEnd+=",'1'";
	    	   					}else{
	    	   						sql+=","+tempKey;
	    	   						sqlEnd+=",'0'";
	    	   					}
	    	   				}else{
	    	   					sql+=","+tempKey;
	    	   					sqlEnd+=",'"+myjObject.getString(tempKey)+"'";
	    	   				}
				    	}
					}
				    sql=sql+")"+sqlEnd+")";
				    excuteSql(sql);
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void batchEditSave(String updateId,String tableCode,String gridAllDatas){
		JSONObject json = JSONObject.fromObject(gridAllDatas);
		JSONArray allDatas = json.getJSONArray("edit");
		String sql = "";
		String sqlEnd = "";
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where  tableContent.bdinfo.tableName = ?";
		hql += " ORDER BY sn ASC";
		List<SysTableStructure> sysTableStructures = this.getHibernateTemplate().find(hql,tableCode);
		String foreignKey = "";
		String uuid = OtherUtil.getRandomUUID();
		Map<String,String> mapFields = new HashMap<String, String>();
		if(sysTableStructures != null){
			for(SysTableStructure sysTableStructure:sysTableStructures){
				foreignKey = sysTableStructure.getTableContent().getTableKind().getMainTablePK();
				mapFields.put(sysTableStructure.getFieldInfo().getFieldCode(), sysTableStructure.getFieldInfo().getDataType());
			}
		}		
		sql = "insert into "+tableCode+" (code,"+foreignKey+",snapCode";
		sqlEnd = " values('"+uuid+"','"+updateId+"','"+hrPersonCurrentDao.get(updateId).getSnapCode()+"'";
		for(int i=0;i<allDatas.size();i++){
			 JSONObject myjObject = allDatas.getJSONObject(i);
			 if(myjObject.size()>0){
				 Set keys = myjObject.keySet();
				 for (Object keyTemp : keys) {  
						String tempKey=keyTemp.toString();
						sql+=","+tempKey;
	   					sqlEnd+=",'"+myjObject.getString(tempKey)+"'";
				 }
			 }
		}
		sql=sql+") "+sqlEnd+")";
		excuteSql(sql);
	}
	@SuppressWarnings("unchecked")
	public void insertHrSubData(String tableCode,String foreignId,String snapCode,Map<String,String> hrSubDataMap){
		if(hrSubDataMap!=null&&hrSubDataMap.size()>0){
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where  tableContent.bdinfo.tableName = ?";
		hql += " ORDER BY sn ASC";
		List<SysTableStructure> sysTableStructures = this.getHibernateTemplate().find(hql,tableCode);
		String foreignKey="";
		String uuid = OtherUtil.getRandomUUID();
		Map<String,String> mapFields = new HashMap<String, String>();
		if(sysTableStructures!=null&&sysTableStructures.size()>0){
			for(SysTableStructure sysTableStructure:sysTableStructures){
				foreignKey = sysTableStructure.getTableContent().getTableKind().getMainTablePK();
				mapFields.put(sysTableStructure.getFieldInfo().getFieldCode(), sysTableStructure.getFieldInfo().getDataType());
			}
		}else{
			return;
		}
		String sql="";
		String sqlEnd="";
		sql="insert into "+tableCode+" (code,"+foreignKey+",snapCode";
		sqlEnd=" values('"+uuid+"','"+foreignId+"','"+snapCode+"'";
		for (String key : hrSubDataMap.keySet()) {
		    String value = hrSubDataMap.get(key);
		    String dataType="1";
		    if(mapFields.containsKey(key)){
		    	dataType=mapFields.get(key);
		    	if(dataType.equals("3")){
		    		if(value.equals("true")||value.equals("1")){
		    			sql+=","+key;
		    			value="1";
	   					sqlEnd+=",'"+value+"'";
		    		}else if(value.equals("false")||value.equals("0")){
		    			sql+=","+key;
		    			value="0";
	   					sqlEnd+=",'"+value+"'";
		    		}
		    	}else if(dataType.equals("4")){
		    		sql+=","+key;
		    		if(value.length()>10){
		    			value=value.substring(0, 10);
		    		}
	    			sqlEnd += "," +"(case when len('"+value+"') > 8 and charindex('-','"+value+"') = 0 then cast(substring('"+value+"',1,4) + '-' + substring('"+value+"',5,2) + '-' + substring('"+value+"',7,2) + ' ' + substring('"+value+"',9,2) + ':' + substring('"+value+"',11,2) as datetime) else cast('"+value+"' as datetime) END)";	
		    	}else{
		    		sql+=","+key;
   					sqlEnd+=",'"+value+"'";
		    	}
		    }
		}
		sql=sql+") "+sqlEnd+")";
		excuteSql(sql);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void updateOrInsertHrSubData(String tableCode,String foreignId,String snapCode,Map<String,String> hrSubDataWhereMap,String operType,Map<String,String> hrSubDataOperMap,Map<String,String> hrSubDataMap){
		try{
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where  tableContent.bdinfo.tableName = ?";
		hql += " ORDER BY sn ASC";
		List<SysTableStructure> sysTableStructures = this.getHibernateTemplate().find(hql,tableCode);
		String foreignKey = "";
		Map<String,String> mapFields = new HashMap<String, String>();
		if(sysTableStructures!=null&&sysTableStructures.size()>0){
			for(SysTableStructure sysTableStructure:sysTableStructures){
				foreignKey = sysTableStructure.getTableContent().getTableKind().getMainTablePK();
				mapFields.put(sysTableStructure.getFieldInfo().getFieldCode(), sysTableStructure.getFieldInfo().getDataType());
			}
		}else{
			return;
		}
		String sql = "";
		String sqlEnd = "";
		sql="select code from "+tableCode;
		if(hrSubDataWhereMap!=null&&hrSubDataWhereMap.size()>0){
			sqlEnd = " where ";
			for (String key : hrSubDataMap.keySet()) {
			    String value = hrSubDataMap.get(key);
			    String dataType="1";
			    if(mapFields.containsKey(key)){
			    	dataType=mapFields.get(key);
			    	if(dataType.equals("3")){
			    		if(value.equals("true")||value.equals("1")){
			    			value="1";
		   					sqlEnd+=" "+key+"='"+value+"' and";
			    		}else if(value.equals("false")||value.equals("0")){
			    			value="0";
		   					sqlEnd+=" "+key+"='"+value+"' and";
			    		}
			    	}else if(dataType.equals("4")){
			    		if(value.length()>10){
			    			value=value.substring(0, 10);
			    		}
			    		sqlEnd+=" "+key+"=" +"(case when len('"+value+"') > 8 and charindex('-','"+value+"') = 0 then cast(substring('"+value+"',1,4) + '-' + substring('"+value+"',5,2) + '-' + substring('"+value+"',7,2) + ' ' + substring('"+value+"',9,2) + ':' + substring('"+value+"',11,2) as datetime) else cast('"+value+"' as datetime) END) and";	
			    	}else{
	   					sqlEnd+=" "+key+"='"+value+"' and";
			    	}
			    }
			}
		}
		if(!sqlEnd.equals("")){
			sqlEnd=sqlEnd+" "+foreignKey+"='"+foreignId+"'";
			sql=sql+" "+sqlEnd;
			List<Map<String, Object>> codeList=new ArrayList<Map<String,Object>>();
			codeList=getBySqlToMap(sql);
			if(codeList!=null&&codeList.size()>0){
				String code=codeList.get(0).get("code").toString();
				if(code!=null&&!code.equals("")){
				if(operType.equals("sum")){
					sql="";
					sqlEnd="";
					sql="update "+tableCode+" set ";
					String getValueSql="";
					if(hrSubDataOperMap!=null&&hrSubDataOperMap.size()>0){
						for (String key : hrSubDataOperMap.keySet()) {
						    String value = hrSubDataOperMap.get(key);
						    String dataType="1";
						    if(mapFields.containsKey(key)){
						    	dataType=mapFields.get(key);
						    	if(dataType.equals("2")){
						    		getValueSql="select "+key+" from "+tableCode+" where code='"+code+"'";
						    		List<Map<String, Object>> getValueList=new ArrayList<Map<String,Object>>();
						    		getValueList=getBySqlToMap(getValueSql);
						    		if(getValueList!=null&&getValueList.size()>0){
						    			String oldValueStr=getValueList.get(0).get(key).toString();
						    			Double oldValue=0.0;
						    			oldValue=Double.parseDouble(oldValueStr);
						    			Double newValue=0.0;
						    			newValue=Double.parseDouble(value);
						    			newValue=newValue+oldValue;
						    			sql+=key+"='"+newValue+"',";
						    		}
						    	}else if(dataType.equals("5")){
						    		getValueSql="select "+key+" from "+tableCode+" where code='"+code+"'";
						    		List<Map<String, Object>> getValueList=new ArrayList<Map<String,Object>>();
						    		getValueList=getBySqlToMap(getValueSql);
						    		if(getValueList!=null&&getValueList.size()>0){
						    			String oldValueStr=getValueList.get(0).get(key).toString();
						    			Integer oldValue=0;
						    			oldValue=Integer.parseInt(oldValueStr);
						    			Integer newValue=0;
						    			newValue=Integer.parseInt(value);
						    			newValue=newValue+oldValue;
						    			sql+=key+"='"+newValue+"',";
						    		}
						    	}
						    }
						}
						sqlEnd=" where code='"+code+"'";
						if(sql.contains(",")){
							sql=sql.substring(0, sql.length()-1);
							sql=sql+" "+sqlEnd;
							excuteSql(sql);
							return;
						}
						
					}
				}
			}
			}
		}
		if(hrSubDataMap!=null&&hrSubDataMap.size()>0){
			String uuid = OtherUtil.getRandomUUID();
			sql="";
			sqlEnd="";
			sql="insert into "+tableCode+" (code,"+foreignKey+",snapCode";
			sqlEnd=" values('"+uuid+"','"+foreignId+"','"+snapCode+"'";
			for (String key : hrSubDataMap.keySet()) {
			    String value = hrSubDataMap.get(key);
			    String dataType="1";
			    if(mapFields.containsKey(key)){
			    	dataType=mapFields.get(key);
			    	if(dataType.equals("3")){
			    		if(value.equals("true")||value.equals("1")){
			    			sql+=","+key;
			    			value="1";
		   					sqlEnd+=",'"+value+"'";
			    		}else if(value.equals("false")||value.equals("0")){
			    			sql+=","+key;
			    			value="0";
		   					sqlEnd+=",'"+value+"'";
			    		}
			    	}else if(dataType.equals("4")){
			    		sql+=","+key;
			    		if(value.length()>10){
			    			value=value.substring(0, 10);
			    		}
		    			sqlEnd += "," +"(case when len('"+value+"') > 8 and charindex('-','"+value+"') = 0 then cast(substring('"+value+"',1,4) + '-' + substring('"+value+"',5,2) + '-' + substring('"+value+"',7,2) + ' ' + substring('"+value+"',9,2) + ':' + substring('"+value+"',11,2) as datetime) else cast('"+value+"' as datetime) END)";	
			    	}else{
			    		sql+=","+key;
	   					sqlEnd+=",'"+value+"'";
			    	}
			    }
			}
			sql=sql+") "+sqlEnd+")";
			excuteSql(sql);
			}
		}
    	catch(Exception e){
    		log.error("updateOrInsertHrSubDataError", e);
    	}
	}
}
