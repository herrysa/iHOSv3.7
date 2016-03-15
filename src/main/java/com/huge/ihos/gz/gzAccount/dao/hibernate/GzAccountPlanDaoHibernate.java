package com.huge.ihos.gz.gzAccount.dao.hibernate;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.gz.gzAccount.dao.GzAccountPlanDao;
import com.huge.ihos.gz.gzAccount.model.GzAccountDefine;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlan;
import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.ihos.gz.gzType.service.GzTypeManager;
import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.dao.PersonTypeDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("gzAccountPlanDao")
public class GzAccountPlanDaoHibernate extends GenericDaoHibernate<GzAccountPlan, String> implements GzAccountPlanDao {

	private DepartmentDao departmentDao;
	private PersonTypeDao personTypeDao;
	private GzTypeManager gzTypeManager;
    public GzAccountPlanDaoHibernate() {
        super(GzAccountPlan.class);
    }
    @Autowired
    public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
    @Autowired
	public void setPersonTypeDao(PersonTypeDao personTypeDao) {
		this.personTypeDao = personTypeDao;
	}
    @Autowired
    public void setGzTypeManager(GzTypeManager gzTypeManager) {
		this.gzTypeManager = gzTypeManager;
	}
    public JQueryPager getGzAccountPlanCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("planId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, GzAccountPlan.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getGzAccountPlanCriteria", e);
			return paginatedList;
		}

	}


	@Override
	public List<Map<String, Object>> getGzAccountGridData(String columns,List<String> sqlFilterList,GzAccountDefine gzAccountDefine,Map<String,String> groupFilterMap)  throws Exception {
		try {
			String sql = "select " ;
			String groups = gzAccountDefine.getGroups();
			List<String> groupColumnList = null;
			if(OtherUtil.measureNotNull(groups)){
				String[] groupColumns = groups.split(",");
				groupColumnList = Arrays.asList(groupColumns);
				groupColumnList = new ArrayList(groupColumnList);
			}
			List<String> numberColumnList = new ArrayList<String>();
	    	if(OtherUtil.measureNotNull(columns)){
	    		String[] columnArr = columns.split(",");
	    		String sqlTemp = "";
	    		if(OtherUtil.measureNotNull(groupColumnList)&&!groupColumnList.isEmpty()){
	    			for(String columnTemp:columnArr){
	    				if(groupColumnList.contains(columnTemp)){
	    					sqlTemp += "gz." + columnTemp + " AS "+columnTemp+",";
	    				}else if("count".equals(columnTemp)){
	    					numberColumnList.add("count");
	    					sqlTemp += "COUNT(*) AS count,";
	    				}else{
	    					numberColumnList.add(columnTemp);
	    					sqlTemp += "SUM(gz." + columnTemp + ") AS "+columnTemp+ ",";
	    				}
	    			}
	    		}else{
	    			for(String columnTemp:columnArr){
	    				sqlTemp += "gz." + columnTemp + " AS "+columnTemp+",";
	    			}
	    		}
	    		sqlTemp += "gz.period AS period,gz.issueNumber AS issueNumber";
	    		sql +=  sqlTemp;
	    	}else{
	    		sql +=  "gz.period AS period,gz.issueNumber AS issueNumber";
	    	}
	    	if("09".equals(gzAccountDefine.getDefineId())){
	    		sql = "select " ;
	    		String sqlTemp = "";
	    		if(OtherUtil.measureNotNull(columns)){
	    			String[] columnArr = columns.split(",");
	        		for(String columnTemp:columnArr){
	        			 if("count".equals(columnTemp)){
		    					sqlTemp += "COUNT(*) AS count,";
		    			 }else{
		    				 sqlTemp += "SUM(gz." + columnTemp + ") AS "+columnTemp+ ",";
		    			 }
	    			}
	    		}
	    		sqlTemp += "gz.period AS period,gz.issueNumber AS issueNumber,gz.gzTypeId AS gzTypeId";
	    		sql +=  sqlTemp;
	    	}
	    	sql += " FROM gz_gzContent AS gz ";
	    	//sql += " LEFT JOIN t_department AS td ON gz.deptId = td.deptId ";
	    	//sql += " LEFT JOIN t_person AS tp ON gz.personId = tp.personId ";
	    	if(OtherUtil.measureNotNull(sqlFilterList)&&!sqlFilterList.isEmpty()){
	    		String sqlTemp = "";
	    		for(String sqlFilter:sqlFilterList){
	    			sqlTemp += " "+ sqlFilter +" AND";
	    		}
	    		sqlTemp = OtherUtil.subStrEnd(sqlTemp, "AND");
	    		sql += " WHERE " + sqlTemp + " ";
	    	}
	    	if("09".equals(gzAccountDefine.getDefineId())){
	    		String sqlTemp = "";
	    		sqlTemp += "gz.period,gz.issueNumber,gz.gzTypeId";
	    		sql += " GROUP BY " + sqlTemp + " ";
	    	}else{
	    		if(OtherUtil.measureNotNull(groupColumnList)&&!groupColumnList.isEmpty()){
	        		String sqlTemp = "";
	        		for(String groupColumn:groupColumnList){
	        			sqlTemp += "gz."+groupColumn +",";
	        		}
	        		sqlTemp += "gz.period,gz.issueNumber";
	        		sql += " GROUP BY " + sqlTemp + " ";
	        	}else{
	        		sql += " ORDER BY gz.deptCode ";
	        	}
	    	}
//	    	System.out.println("帐表："+sql);
	    	List<Map<String,Object>> gzContentList  = this.getBySqlToMap(sql);
	    	Map<String, String> gzTypeMap = new HashMap<String, String>();
	    	if("09".equals(gzAccountDefine.getDefineId())){
	    		List<GzType> gzTypes = gzTypeManager.allGzTypes(true);
	    		if(OtherUtil.measureNotNull(gzTypes)&&!gzTypes.isEmpty()){
	    			for(GzType gzType:gzTypes){
	    				gzTypeMap.put(gzType.getGzTypeId(), gzType.getGzTypeName());
	    			}
	    		}
	    		if(OtherUtil.measureNotNull(gzContentList)&&!gzContentList.isEmpty()){
	    			Map<String, List<String>> periodGzTypeMap = new HashMap<String, List<String>>();
	    			for(Map<String,Object> mapTemp:gzContentList){
	    				String gzTypeId = mapTemp.get("gzTypeId").toString();
	        			if(gzTypeMap.containsKey(gzTypeId)){
	        				String gzTypeName = gzTypeMap.get(gzTypeId);
	        				mapTemp.put("gzTypeId", gzTypeName);
	        			}
	    			}
	    		}
	    	}
	    	List<Map<String,Object>> gzContentResultList  = new ArrayList<Map<String,Object>>();
	    	if("dept".equals(gzAccountDefine.getDisplayType())){
	    		groupColumnList.add("period");
	        	groupColumnList.add("issueNumber");
	        	Map<String, List<Map<String,Object>>> gzContentAllMap = new HashMap<String, List<Map<String,Object>>>();
	        	if(OtherUtil.measureNotNull(gzContentList)&&!gzContentList.isEmpty()){
	        		for(Map<String,Object> mapTemp:gzContentList){
	        			String period = mapTemp.get("period").toString();
	        			String issueNumber = mapTemp.get("issueNumber").toString();
	        			String checkKey = period +"_"+ issueNumber;
	        			if(gzContentAllMap.containsKey(checkKey)){
	        				List<Map<String,Object>> gzContentAllListTemp = gzContentAllMap.get(checkKey);
	        				gzContentAllListTemp.add(mapTemp);
	        				gzContentAllMap.put(checkKey, gzContentAllListTemp);
	        			}else{
	        				List<Map<String,Object>> gzContentAllListTemp = new ArrayList<Map<String,Object>>();
	        				gzContentAllListTemp.add(mapTemp);
	        				gzContentAllMap.put(checkKey, gzContentAllListTemp);
	        			}
	        		}
	        	}else{
	        		return gzContentResultList;
	        	}
	        	for(List<Map<String,Object>> gzContentListTemp:gzContentAllMap.values()){
	        		Map<String, Map<String,Object>> gzContentMap = new HashMap<String, Map<String,Object>>();
	            	Map<String, String> gzContentFirstMap = new HashMap<String, String>();
	            	if(OtherUtil.measureNotNull(gzContentListTemp)&&!gzContentListTemp.isEmpty()){
	            		for(Map<String,Object> mapTemp:gzContentListTemp){
	            			String deptId =  mapTemp.get("deptId").toString();
	            			gzContentMap.put(deptId, mapTemp);
	            		}
	            		Map<String,Object> mapTemp = gzContentListTemp.get(0);
	            		for(String groupColumn:groupColumnList){
	            			gzContentFirstMap.put(groupColumn, mapTemp.get(groupColumn).toString());
	            		}
	            	}else{
	            		return gzContentResultList;
	            	}
	            	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
	            	filters.add(new PropertyFilter("NES_departmentId","XT"));
	            	filters.add(new PropertyFilter("OAS_deptCode",""));
	            	List<Department> departments = departmentDao.getByFilters(filters);
	            	if(OtherUtil.measureNotNull(departments)&&!departments.isEmpty()){
	            		List<Double> sumNumberList = new ArrayList<Double>();
	            		List<String> sumstringList = new ArrayList<String>();
	            		for(Department dept:departments){
	            			dept.setShowFlag(false);
	            			String deptId = dept.getDepartmentId();
	            			Boolean leaf = dept.getLeaf();
	            			List<Double> numberList = new ArrayList<Double>();
	            			List<String> stringList = new ArrayList<String>();
	            			sumstringList.clear();
	            			for(String groupColumn:groupColumnList){
	        					if("deptId".equals(groupColumn)){
	        						sumstringList.add("");
	        						stringList.add(deptId);
	        					}else if("deptCode".equals(groupColumn)){
	        						sumstringList.add("");
	        						stringList.add(dept.getDeptCode());
	        					}else if("deptName".equals(groupColumn)){
	        						sumstringList.add("");
	        						stringList.add(dept.getName());
	        					}else{
	        						if("period".equals(groupColumn)){
	        							sumstringList.add("合计");
	        						}else{
	        							sumstringList.add(gzContentFirstMap.get(groupColumn));
	        						}
	        						stringList.add(gzContentFirstMap.get(groupColumn));
	        					}
	        				}
	            			dept.setStringList(stringList);
	            			if(!gzContentMap.containsKey(deptId)){
	            				for(String numberColumn:numberColumnList){
	            					numberList.add(0.0);
	            				}
	            			}else{
	            				dept.setShowFlag(true);
	            				Map<String, Object> mapTemp = gzContentMap.get(deptId);
	            				for(String numberColumn:numberColumnList){
	            					Double number = mapTemp.get(numberColumn)==null?0.0:Double.parseDouble(mapTemp.get(numberColumn).toString());
	            					numberList.add(number);
	            				}
	            			}
	            			dept.setNumberList(numberList);
	            		}
	            		for(Department dept:departments){
	            			Boolean leaf = dept.getLeaf();
	            			if(leaf){
	            				countDeptNumber(dept,dept.getNumberList(),dept.getShowFlag());
	            			}
	            		}
	            		String deptType = groupFilterMap.get("deptType");
	            		String deptDetailIds = groupFilterMap.get("deptDetailIds");
	            		List<String> deptDetailIdList = new ArrayList<String>();
	            		if(OtherUtil.measureNotNull(deptDetailIds)){
	            			deptDetailIdList = Arrays.asList(deptDetailIds.split(","));
	            		}
	            		List<String> showDeptIdList = new ArrayList<String>();
	            		for(Department dept:departments){
	            			if(!dept.getShowFlag()){
	            				continue;
	            			}
	            			if(!deptDetailIdList.isEmpty()&&!deptDetailIdList.contains(dept.getDepartmentId())){
	            				continue;
	            			}
	            			if("end".equals(deptType)&&!dept.getLeaf()){
	            				continue;
	            			}
	            			if("level".equals(deptType)){
	            				String deptLevelFrom = groupFilterMap.get("deptLevelFrom");
	            				String deptLevelTo = groupFilterMap.get("deptLevelTo");
	            				if(OtherUtil.measureNotNull(deptLevelFrom)&&OtherUtil.measureNotNull(deptLevelTo)){
	            					int deptFrom = Integer.parseInt(deptLevelFrom);
	            					int deptTo = Integer.parseInt(deptLevelTo);
	            					if(dept.getClevel()<deptFrom||dept.getClevel()>deptTo){
	            						continue;
	            					}
	            				}else if(OtherUtil.measureNotNull(deptLevelFrom)){
	            					int deptFrom = Integer.parseInt(deptLevelFrom);
	            					if(dept.getClevel()<deptFrom){
	            						continue;
	            					}
	            				}else if(OtherUtil.measureNotNull(deptLevelTo)){
	            					int deptTo = Integer.parseInt(deptLevelTo);
	            					if(dept.getClevel()>deptTo){
	            						continue;
	            					}
	            				}
	            			}
	            			showDeptIdList.add(dept.getDepartmentId());
	            			Map<String, Object> tempMap = new HashMap<String, Object>();
	            			List<Double> numberList = dept.getNumberList();
	            			List<String> stringList = dept.getStringList();
	            			for(int i = 0;i<groupColumnList.size();i++){
	            				String groupColumn = groupColumnList.get(i);
	            				tempMap.put(groupColumn, stringList.get(i));
	            			}
	            			for(int i = 0;i<numberColumnList.size();i++){
	            				String numberColumn = numberColumnList.get(i);
	            				tempMap.put(numberColumn, numberList.get(i));
	            			}
	            			gzContentResultList.add(tempMap);
	            		}
	            		//合计
	            		for(Department dept:departments){
	            			if(!showDeptIdList.contains(dept.getDepartmentId())){
	            				continue;
	            			}
	            			if(OtherUtil.measureNotNull(dept.getParentDept())&&showDeptIdList.contains(dept.getParentDept().getDepartmentId())){
	            				continue;
	            			}
	            			List<Double> tempNumberList = dept.getNumberList();
	            			if(sumNumberList.isEmpty()){
	            				sumNumberList = new ArrayList<Double>(tempNumberList);
	            				//Collections.copy(sumNumberList, tempNumberList);
	            			}else{
	            				for(int i=0;i<tempNumberList.size();i++){
	            					Double tempDouble = tempNumberList.get(i) + sumNumberList.get(i);
	            					sumNumberList.set(i, tempDouble);
	            				}
	            			}
	            		}
	            		Map<String, Object> sumTempMap = new HashMap<String, Object>();
	        			for(int i = 0;i<groupColumnList.size();i++){
	        				String groupColumn = groupColumnList.get(i);
	        				sumTempMap.put(groupColumn, sumstringList.get(i));
	        			}
	        			for(int i = 0;i<sumNumberList.size();i++){
	        				String numberColumn = numberColumnList.get(i);
	        				sumTempMap.put(numberColumn, sumNumberList.get(i));
	        			}
	        			gzContentResultList.add(sumTempMap);
	            	}
	        	}
	    	}else if("empType".equals(gzAccountDefine.getDisplayType())){
	    		groupColumnList.add("period");
	        	groupColumnList.add("issueNumber");
	        	Map<String, Map<String,Object>> gzContentMap = new HashMap<String, Map<String,Object>>();
	        	Map<String, List<Map<String,Object>>> gzContentAllMap = new HashMap<String, List<Map<String,Object>>>();
	        	if(OtherUtil.measureNotNull(gzContentList)&&!gzContentList.isEmpty()){
	        		for(Map<String,Object> mapTemp:gzContentList){
	        			String period = mapTemp.get("period").toString();
	        			String issueNumber = mapTemp.get("issueNumber").toString();
	        			String checkKey = period +"_"+ issueNumber;
	        			if(gzContentAllMap.containsKey(checkKey)){
	        				List<Map<String,Object>> gzContentAllListTemp = gzContentAllMap.get(checkKey);
	        				gzContentAllListTemp.add(mapTemp);
	        				gzContentAllMap.put(checkKey, gzContentAllListTemp);
	        			}else{
	        				List<Map<String,Object>> gzContentAllListTemp = new ArrayList<Map<String,Object>>();
	        				gzContentAllListTemp.add(mapTemp);
	        				gzContentAllMap.put(checkKey, gzContentAllListTemp);
	        			}
	        		}
	        	}else{
	        		return gzContentResultList;
	        	}
	        	for(List<Map<String,Object>> gzContentListTemp:gzContentAllMap.values()){
	        		Map<String, String> gzContentFirstMap = new HashMap<String, String>();
	            	if(OtherUtil.measureNotNull(gzContentListTemp)&&!gzContentListTemp.isEmpty()){
	            		for(Map<String,Object> mapTemp:gzContentListTemp){
	            			String empType =  mapTemp.get("empType").toString();
	            			gzContentMap.put(empType, mapTemp);
	            		}
	            		Map<String,Object> mapTemp = gzContentListTemp.get(0);
	            		for(String groupColumn:groupColumnList){
	            			gzContentFirstMap.put(groupColumn, mapTemp.get(groupColumn).toString());
	            		}
	            	}
	            	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
	            	filters.add(new PropertyFilter("OAS_code",""));
	            	List<PersonType> personTypes = personTypeDao.getByFilters(filters);
	            	if(OtherUtil.measureNotNull(personTypes)&&!personTypes.isEmpty()){
	            		List<Double> sumNumberList = new ArrayList<Double>();
	            		List<String> sumstringList = new ArrayList<String>();
	            		for(PersonType personType:personTypes){
	            			personType.setShowFlag(false);
	            			String empType = personType.getName();
	            			Boolean leaf = personType.getLeaf();
	            			List<Double> numberList = new ArrayList<Double>();
	            			List<String> stringList = new ArrayList<String>();
	            			sumstringList.clear();
	            			for(String groupColumn:groupColumnList){
	        					if("empType".equals(groupColumn)){
	        						sumstringList.add("");
	        						stringList.add(empType);
	        					}else{
	        						if("period".equals(groupColumn)){
	        							sumstringList.add("合计");
	        						}else{
	        							sumstringList.add(gzContentFirstMap.get(groupColumn));
	        						}
	        						stringList.add(gzContentFirstMap.get(groupColumn));
	        					}
	        				}
	            			personType.setStringList(stringList);
	            			if(!gzContentMap.containsKey(empType)){
	            				for(String numberColumn:numberColumnList){
	            					numberList.add(0.0);
	            				}
	            			}else{
	            				personType.setShowFlag(true);
	            				Map<String, Object> mapTemp = gzContentMap.get(empType);
	            				for(String numberColumn:numberColumnList){
	            					Double number = mapTemp.get(numberColumn)==null?0.0:Double.parseDouble(mapTemp.get(numberColumn).toString());
	            					numberList.add(number);
	            				}
	            			}
	            			personType.setNumberList(numberList);
	            		}
	            		for(PersonType personType:personTypes){
	            			Boolean leaf = personType.getLeaf();
	            			if(leaf){
	            				countPersonTypeNumber(personType,personType.getNumberList(),personType.getShowFlag());
	            			}
	            		}
	            		List<String> showPersonTypeIdList = new ArrayList<String>();
	            		for(PersonType personType:personTypes){
	            			if(!personType.getShowFlag()){
	            				continue;
	            			}
	            			showPersonTypeIdList.add(personType.getId());
	            			Map<String, Object> tempMap = new HashMap<String, Object>();
	            			List<Double> numberList = personType.getNumberList();
	            			List<String> stringList = personType.getStringList();
	            			for(int i = 0;i<groupColumnList.size();i++){
	            				String groupColumn = groupColumnList.get(i);
	            				tempMap.put(groupColumn, stringList.get(i));
	            			}
	            			for(int i = 0;i<numberColumnList.size();i++){
	            				String numberColumn = numberColumnList.get(i);
	            				tempMap.put(numberColumn, numberList.get(i));
	            			}
	            			gzContentResultList.add(tempMap);
	            		}
	            		//合计
	            		for(PersonType personType:personTypes){
	            			if(!showPersonTypeIdList.contains(personType.getId())){
	            				continue;
	            			}
	            			if(OtherUtil.measureNotNull(personType.getParentType())&&showPersonTypeIdList.contains(personType.getParentType().getId())){
	            				continue;
	            			}
	            			List<Double> tempNumberList = personType.getNumberList();
	            			if(sumNumberList.isEmpty()){
	            				sumNumberList = new ArrayList<Double>(tempNumberList);
	            				//Collections.copy(sumNumberList, tempNumberList);
	            			}else{
	            				for(int i=0;i<tempNumberList.size();i++){
	            					Double tempDouble = tempNumberList.get(i) + sumNumberList.get(i);
	            					sumNumberList.set(i, tempDouble);
	            				}
	            			}
	            		}
	            		Map<String, Object> sumTempMap = new HashMap<String, Object>();
	        			for(int i = 0;i<groupColumnList.size();i++){
	        				String groupColumn = groupColumnList.get(i);
	        				sumTempMap.put(groupColumn, sumstringList.get(i));
	        			}
	        			for(int i = 0;i<sumNumberList.size();i++){
	        				String numberColumn = numberColumnList.get(i);
	        				sumTempMap.put(numberColumn, sumNumberList.get(i));
	        			}
	        			gzContentResultList.add(sumTempMap);
	            	}
	        	}
	    	}else{
	    		gzContentResultList = gzContentList;
	    	}
	    	return gzContentResultList;
		} catch (Exception e) {
			throw e;
		}
	}
	/*计算部门数据*/
	private void countPersonTypeNumber(PersonType personType,List<Double> numberList,Boolean showFlag) {
		PersonType pPersonType = personType.getParentType();
		if(OtherUtil.measureNotNull(pPersonType)){
			List<Double> pNumberList = pPersonType.getNumberList();
			List<Double> pNewNumberList = new ArrayList<Double>();
			for(int i=0;i<numberList.size();i++){
				Double tempDouble = pNumberList.get(i) + numberList.get(i);
				pNewNumberList.add(tempDouble);
			}
			if(showFlag){
				pPersonType.setShowFlag(showFlag);
			}
			pPersonType.setNumberList(pNewNumberList);
			countPersonTypeNumber(pPersonType,numberList,showFlag);
		}
	}
	/*计算人员类别数据*/
	private void countDeptNumber(Department dept,List<Double> numberList,Boolean showFlag) {
		Department pDept = dept.getParentDept();
		if(OtherUtil.measureNotNull(pDept)){
			List<Double> pNumberList = pDept.getNumberList();
			List<Double> pNewNumberList = new ArrayList<Double>();
			for(int i=0;i<numberList.size();i++){
				Double tempDouble = pNumberList.get(i) + numberList.get(i);
				pNewNumberList.add(tempDouble);
			}
			if(showFlag){
				pDept.setShowFlag(showFlag);
			}
			pDept.setNumberList(pNewNumberList);
			countDeptNumber(pDept,numberList,showFlag);
		}
	}
	@Override
	public List<Map<String,Object>> getGzAccountReverseGridData(List<GzItem> items,List<String> sqlFilterList,GzAccountDefine gzAccountDefine) throws Exception{
		if(OtherUtil.measureNull(items)||items.isEmpty()){
			return null;
		}
		try {
			Map<String,String> codeNameMap = new LinkedHashMap<String, String>();
			String sql = "SELECT " ;
			String reverseColumn = gzAccountDefine.getReverseColumn();
//			String[] reverseColumnArr = reverseColumn.split(",");
			String sqlTemp = "";
//			for(String strTemp:reverseColumnArr){
//				if(OtherUtil.measureNull(sqlTemp)){
//					sqlTemp = "convert(varchar,gz."+strTemp+")";
//				}else{
//					sqlTemp += "+'(第'+"+"convert(varchar,gz."+strTemp+")+'次)'";
//				}
//			}
			sqlTemp = "convert(varchar,gz.period)+'(第'+convert(varchar,gz.issueNumber)+'次)'";
			sql += sqlTemp + " AS reverse ";
			//sql += "gz." + reverseColumn;
			for(GzItem gzItem:items){
				String itemCode = gzItem.getItemCode();
				String itemName = gzItem.getColName();
				codeNameMap.put(itemCode, itemName);
				sql += ",gz." + itemCode;
			}
			sql += " FROM gz_gzContent AS gz ";
			if(OtherUtil.measureNotNull(sqlFilterList)&&!sqlFilterList.isEmpty()){
	    		sqlTemp = "";
	    		for(String sqlFilter:sqlFilterList){
	    			sqlTemp += " "+ sqlFilter +" AND";
	    		}
	    		sqlTemp = OtherUtil.subStrEnd(sqlTemp, "AND");
	    		sql += " WHERE " + sqlTemp + " ";
	    	}
//			System.out.println("帐表统计:"+sql);
			List<Map<String,Object>> tempList = this.getBySqlToMap(sql);
			if(OtherUtil.measureNull(tempList)||tempList.isEmpty()){
				return null;
			}
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
			Map<String, Map<String,Object>> resultMap = new LinkedHashMap<String, Map<String,Object>>();
			for(Map<String,Object> tempMap:tempList){
				String reverseValue = tempMap.get("reverse").toString();
				String reverseKey = "period" + reverseValue;
				 for (String itemCode : codeNameMap.keySet()) {
					 Map<String,Object> resultTempMap = null;
					 if(resultMap.containsKey(itemCode)){
						resultTempMap = resultMap.get(itemCode);
					 }else{
						resultTempMap = new HashMap<String, Object>();
					 }
					 Object itemValue = tempMap.get(itemCode);
					 resultTempMap.put(reverseKey, itemValue);
					 resultTempMap.put("gzItems", codeNameMap.get(itemCode));
					 resultMap.put(itemCode, resultTempMap);
				 }
			}
			for(Map<String,Object> tempMap:resultMap.values()){
				resultList.add(tempMap);
			}
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}
}
