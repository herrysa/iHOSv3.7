package com.huge.ihos.system.reportManager.report.dao.hibernate;



import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.reportManager.report.dao.ReportPlanDao;
import com.huge.ihos.system.reportManager.report.model.ReportDefine;
import com.huge.ihos.system.reportManager.report.model.ReportItem;
import com.huge.ihos.system.reportManager.report.model.ReportPlan;
import com.huge.ihos.system.reportManager.report.model.ReportType;
import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.dao.PersonTypeDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("reportPlanDao")
public class ReportPlanDaoHibernate extends GenericDaoHibernate<ReportPlan, String> implements ReportPlanDao {

	private DepartmentDao departmentDao;
	private PersonTypeDao personTypeDao;
    public ReportPlanDaoHibernate() {
        super(ReportPlan.class);
    }
    @Autowired
    public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
    @Autowired
	public void setPersonTypeDao(PersonTypeDao personTypeDao) {
		this.personTypeDao = personTypeDao;
	}
    public JQueryPager getReportPlanCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("planId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ReportPlan.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getReportPlanCriteria", e);
			return paginatedList;
		}
	}
    @Override
    public List<Map<String,Object>> getReportPlanGridData(String columns,List<String> sqlFilterList,ReportDefine reportDefine,Map<String,String> groupFilterMap,List<ReportType> reportTypes)  throws Exception{
    	try{
    		if(OtherUtil.measureNotNull(reportDefine.getGroupSelectItems())){
        		return getReportGroupGridData(columns,sqlFilterList,reportDefine,reportTypes);
        	}
        	String sql = "select " ;
    		String groups = reportDefine.getGroups();
    		List<String> groupColumnList = null;
    		if(OtherUtil.measureNotNull(groups)){
    			String[] groupColumns = groups.split(",");
    			groupColumnList = Arrays.asList(groupColumns);
    			groupColumnList = new ArrayList(groupColumnList);
    		}
    		List<String> numberColumnList = new ArrayList<String>();
    		String attachField = reportDefine.getAttachField();
        	if(OtherUtil.measureNotNull(columns)){
        		String[] columnArr = columns.split(",");
        		String sqlTemp = "";
        		if(OtherUtil.measureNotNull(groupColumnList)&&!groupColumnList.isEmpty()){
        			for(String columnTemp:columnArr){
        				if(groupColumnList.contains(columnTemp)){
        					sqlTemp += "rp." + columnTemp + " AS "+columnTemp+",";
        				}else if("count".equals(columnTemp)){
        					numberColumnList.add("count");
        					sqlTemp += "COUNT(*) AS count,";
        				}else{
        					numberColumnList.add(columnTemp);
        					sqlTemp += "SUM(rp." + columnTemp + ") AS "+columnTemp+ ",";
        				}
        			}
        		}else{
        			for(String columnTemp:columnArr){
        				sqlTemp += "rp." + columnTemp + " AS "+columnTemp+",";
        			}
        		}
        		sqlTemp += "rp.period AS period";
        		if(OtherUtil.measureNotNull(attachField)){
        			String[] attachFieldArr = attachField.split(",");
        			for(String fieldTemp:attachFieldArr){
        				sqlTemp += ",rp."+fieldTemp+" AS "+fieldTemp;
        			}
        		}
        		sql +=  sqlTemp;
        	}else{
        		sql +=  "rp.period AS period";
        		if(OtherUtil.measureNotNull(attachField)){
        			String[] attachFieldArr = attachField.split(",");
        			for(String fieldTemp:attachFieldArr){
        				sql += ",rp."+fieldTemp+" AS "+fieldTemp;
        			}
        		}
        	}
        	sql += " FROM " + reportDefine.getTableName() + " AS rp ";
        	if(OtherUtil.measureNotNull(sqlFilterList)&&!sqlFilterList.isEmpty()){
        		String sqlTemp = "";
        		for(String sqlFilter:sqlFilterList){
        			sqlTemp += " "+ sqlFilter +" AND";
        		}
        		sqlTemp = OtherUtil.subStrEnd(sqlTemp, "AND");
        		sql += " WHERE " + sqlTemp + " ";
        	}
        	if(OtherUtil.measureNotNull(groupColumnList)&&!groupColumnList.isEmpty()){
            		String sqlTemp = "";
            		for(String groupColumn:groupColumnList){
            			sqlTemp += "rp."+groupColumn +",";
            		}
            		sqlTemp += "rp.period";
            		if(OtherUtil.measureNotNull(attachField)){
            			String[] attachFieldArr = attachField.split(",");
            			for(String fieldTemp:attachFieldArr){
            				sqlTemp += ",rp."+fieldTemp;
            			}
            		}
            		sql += " GROUP BY " + sqlTemp + " ";
            }else{
            		sql += " ORDER BY rp.deptCode ";
            }
        	List<Map<String,Object>> reportPlanContentList  = this.getBySqlToMap(sql);
        	List<Map<String,Object>> reportPlanContentResultList  = new ArrayList<Map<String,Object>>();
        	if("dept".equals(reportDefine.getDisplayType())){
        		groupColumnList.add("period");
            	groupColumnList.add("issueNumber");
            	Map<String, List<Map<String,Object>>> gzContentAllMap = new HashMap<String, List<Map<String,Object>>>();
            	if(OtherUtil.measureNotNull(reportPlanContentList)&&!reportPlanContentList.isEmpty()){
            		for(Map<String,Object> mapTemp:reportPlanContentList){
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
            		return reportPlanContentResultList;
            	}
            	for(List<Map<String,Object>> reportPlanContentListTemp:gzContentAllMap.values()){
            		Map<String, Map<String,Object>> gzContentMap = new HashMap<String, Map<String,Object>>();
                	Map<String, String> gzContentFirstMap = new HashMap<String, String>();
                	if(OtherUtil.measureNotNull(reportPlanContentListTemp)&&!reportPlanContentListTemp.isEmpty()){
                		for(Map<String,Object> mapTemp:reportPlanContentListTemp){
                			String deptId =  mapTemp.get("deptId").toString();
                			gzContentMap.put(deptId, mapTemp);
                		}
                		Map<String,Object> mapTemp = reportPlanContentListTemp.get(0);
                		for(String groupColumn:groupColumnList){
                			gzContentFirstMap.put(groupColumn, mapTemp.get(groupColumn).toString());
                		}
                	}else{
                		return reportPlanContentResultList;
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
            					}else if("issueNumber".equals(groupColumn)){
            						sumstringList.add("");
            						stringList.add(gzContentFirstMap.get(groupColumn));
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
                			reportPlanContentResultList.add(tempMap);
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
            			reportPlanContentResultList.add(sumTempMap);
                	}
            	}
        	}else if("empType".equals(reportDefine.getDisplayType())){
        		groupColumnList.add("period");
            	groupColumnList.add("issueNumber");
            	Map<String, Map<String,Object>> gzContentMap = new HashMap<String, Map<String,Object>>();
            	Map<String, List<Map<String,Object>>> gzContentAllMap = new HashMap<String, List<Map<String,Object>>>();
            	if(OtherUtil.measureNotNull(reportPlanContentList)&&!reportPlanContentList.isEmpty()){
            		for(Map<String,Object> mapTemp:reportPlanContentList){
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
            		return reportPlanContentResultList;
            	}
            	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
            	filters.add(new PropertyFilter("OAS_code",""));
            	List<PersonType> personTypes = personTypeDao.getByFilters(filters);
            	String empTypes = groupFilterMap.get("empTypes");
        		List<String> empTypesList = new ArrayList<String>();
        		if(OtherUtil.measureNotNull(empTypes)){
        			empTypesList = Arrays.asList(empTypes.split(","));
        		}
        		JSONObject jsonObject = new JSONObject();
        		if(OtherUtil.measureNotNull(personTypes)&&!personTypes.isEmpty()){
        			for(PersonType personType:personTypes){
        				jsonObject.put(personType.getName(), personType.getLeaf() + "");
        			}
        		}
            	for(List<Map<String,Object>> reportPlanContentListTemp:gzContentAllMap.values()){
            		Map<String, String> gzContentFirstMap = new HashMap<String, String>();
                	if(OtherUtil.measureNotNull(reportPlanContentListTemp)&&!reportPlanContentListTemp.isEmpty()){
                		for(Map<String,Object> mapTemp:reportPlanContentListTemp){
                			String empType =  mapTemp.get("empType").toString();
                			gzContentMap.put(empType, mapTemp);
                		}
                		Map<String,Object> mapTemp = reportPlanContentListTemp.get(0);
                		for(String groupColumn:groupColumnList){
                			gzContentFirstMap.put(groupColumn, mapTemp.get(groupColumn).toString());
                		}
                	}
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
            					}else if("issueNumber".equals(groupColumn)){
            						sumstringList.add("");
            						stringList.add(gzContentFirstMap.get(groupColumn));
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
                			if(!empTypesList.isEmpty()&&!empTypesList.contains(personType.getName())){
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
                			reportPlanContentResultList.add(tempMap);
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
            			reportPlanContentResultList.add(sumTempMap);
                	}
            	}
            	if(OtherUtil.measureNotNull(reportPlanContentResultList)&&!reportPlanContentResultList.isEmpty()){
            		int parentSum = 0;
            		int leafSum = 0;
            		for(Map<String, Object> tempMap:reportPlanContentResultList){
            			String empType = tempMap.get("empType").toString();
            			Object leafobj = jsonObject.get(empType);
            			if(OtherUtil.measureNull(leafobj)){
            				continue;
            			}
            			String leaf = leafobj.toString();
            			if("false".equals(leaf)){
            				parentSum ++;
            			}else{
            				leafSum ++;
            			}
            		}
            		if(parentSum > 0&& leafSum > 0){
            			for(Map<String, Object> tempMap:reportPlanContentResultList){
                			String empType = tempMap.get("empType").toString();
                			Object leafobj = jsonObject.get(empType);
                			if(OtherUtil.measureNull(leafobj)){
                				continue;
                			}
                			String leaf = leafobj.toString();
                			if("true".equals(leaf)){
                				empType = "  " + empType;
                			}
                			tempMap.put("empType", empType);
                		}
            		}
            	}
        	}else{
        		reportPlanContentResultList = reportPlanContentList;
        	}
        	return reportPlanContentResultList;
    	}catch(Exception e){
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
	 //汇总
    private List<Map<String,Object>> getReportGroupGridData(String columns,List<String> sqlFilterList,ReportDefine reportDefine,List<ReportType> reportTypes) throws Exception{
    	try {
    		String sql = "select " ;
        	String groupSelectItems = reportDefine.getGroupSelectItems();
        	String[] groupSelectItemArr = groupSelectItems.split(";");
        	List<String> groupColumnList = new ArrayList<String>();
        	for(String groupSelectItemTemp:groupSelectItemArr){
        		String[] groupItemTemp = groupSelectItemTemp.split(":");
        		groupColumnList.add(groupItemTemp[0]);
        	}
        	List<String> numberColumnList = new ArrayList<String>();
        	String sqlSelectTemp = "";
        	if(OtherUtil.measureNotNull(columns)){
        		String[] columnArr = columns.split(",");
        		if(!groupColumnList.isEmpty()){
        			for(String columnTemp:columnArr){
        				if(groupColumnList.contains(columnTemp)){
        					sqlSelectTemp += "rp." + columnTemp + " AS "+columnTemp+",";
        				}else if("count".equals(columnTemp)){
        					numberColumnList.add("count");
        					sqlSelectTemp += "COUNT(*) AS count,";
        				}else{
        					numberColumnList.add(columnTemp);
        					sqlSelectTemp += "SUM(rp." + columnTemp + ") AS "+columnTemp+ ",";
        				}
        			}
        		}else{
        			for(String columnTemp:columnArr){
        				sqlSelectTemp += "rp." + columnTemp + " AS "+columnTemp+",";
        			}
        		}
        		
        	}else{
        		for(String fieldTemp:groupColumnList){
        			sqlSelectTemp += "rp."+fieldTemp+" AS "+fieldTemp + ",";
        		}
        	}
        	sql +=  OtherUtil.subStrEnd(sqlSelectTemp, ",");
        	sql += " FROM " + reportDefine.getTableName() + " AS rp ";
        	if(OtherUtil.measureNotNull(sqlFilterList)&&!sqlFilterList.isEmpty()){
        		String sqlFilterTemp = "";
        		for(String sqlFilter:sqlFilterList){
        			sqlFilterTemp += " "+ sqlFilter +" AND";
        		}
        		sqlFilterTemp = OtherUtil.subStrEnd(sqlFilterTemp, "AND");
        		sql += " WHERE " + sqlFilterTemp + " ";
        	}
        	String sqlGroupTemp = "";
        	String sqlOrderByTemp = "";
        	for(String groupColumn:groupColumnList){
        		sqlGroupTemp += "rp."+groupColumn +",";
        		sqlOrderByTemp += "rp."+groupColumn +",";
        	}
        	sql += " GROUP BY " + OtherUtil.subStrEnd(sqlGroupTemp, ",") + " ";
        	sql += " ORDER BY " + OtherUtil.subStrEnd(sqlOrderByTemp, ",") + " ";
        	List<Map<String,Object>> reportPlanContentList  = this.getBySqlToMap(sql);
        	List<Map<String,Object>> reslutContentList = new ArrayList<Map<String,Object>>(); 
        	String groupIds = reportDefine.getGroupIds();
        	if(OtherUtil.measureNotNull(reportPlanContentList)&&!reportPlanContentList.isEmpty()){
        		Map<String, String> reportTypeMap = new HashMap<String, String>();
        		String typeColmun = reportDefine.getTableTypeField();
        		for(ReportType reportType:reportTypes){
        			reportTypeMap.put(reportType.getTypeId(), reportType.getTypeName());
        		}
        		for(Map<String, Object> contentTempMap:reportPlanContentList){
        			if(contentTempMap.containsKey(typeColmun)){
        				String cellValue = contentTempMap.get(typeColmun).toString();
        				if(reportTypeMap.containsKey(cellValue)){
        					contentTempMap.put(typeColmun, reportTypeMap.get(cellValue));
        				}
        			}
        		}
        		if(OtherUtil.measureNotNull(groupIds)){
        			String[] groupArr = groupIds.split(",");
        			List<String> groupIdList = Arrays.asList(groupArr);
        			int periodCount = 1;
        			for(String groupId:groupIdList){
        				groupColumnList.remove(groupId);
        			}
        			if(!groupColumnList.isEmpty()){
        				Map<String, List<Map<String, Object>>> calTempMap = new HashMap<String, List<Map<String,Object>>>();
        				for(Map<String, Object> contentTempMap:reportPlanContentList){
        					String keyTemp = "";
        					for(String groupIdTemp:groupIdList){
        						String cellValue = contentTempMap.get(groupIdTemp).toString();
        						keyTemp += cellValue + "_";
        					}
        					List<Map<String, Object>> listTemp = null;
        					if(calTempMap.containsKey(keyTemp)){
        						listTemp = calTempMap.get(keyTemp);
        					}else{
        						listTemp = new ArrayList<Map<String,Object>>();
        					}
        					listTemp.add(contentTempMap);
        					calTempMap.put(keyTemp, listTemp);
        	    		}
        				for(List<Map<String, Object>> listTemp:calTempMap.values()){
        					Map<String, Object> resultMap = new HashMap<String, Object>();
        					for(String addStr:groupColumnList){
        						List<String> addList = new ArrayList<String>();
        						for(Map<String, Object> mapTemp:listTemp){
            						String addValue = mapTemp.get(addStr).toString();
            						if(!addList.contains(addValue)){
            							addList.add(addValue);
            						}
            					}
        						String addValueTemp = "";
        						if("period".equals(addStr)){
        							String maxPeriod = addList.get(0);
        							String minPeriod = addList.get(0);
        							periodCount = addList.size();
        							for(String addTemp:addList){
        								if(addTemp.compareTo(maxPeriod)>0){
        									maxPeriod = addTemp;
        								}
        								if(addTemp.compareTo(minPeriod)<0){
        									minPeriod = addTemp;
        								}
            						}
        							if(minPeriod.equals(maxPeriod)){
        								addValueTemp = minPeriod;
        							}else{
        								addValueTemp = minPeriod + "-" + maxPeriod;
        							}
        						}else{
        							for(String addTemp:addList){
            							addValueTemp += addTemp + ",";
            						}
        							addValueTemp = OtherUtil.subStrEnd(addValueTemp, ",");
        						}
        						resultMap.put(addStr, addValueTemp);
    						}
        					for(String groupIdTemp:groupIdList){
        						resultMap.put(groupIdTemp, listTemp.get(0).get(groupIdTemp));
        					}
        					for(String numberColumn:numberColumnList){
        						Double numberTemp = 0.0;
        						for(Map<String, Object> mapTemp:listTemp){
        							Double numberValue = mapTemp.get(numberColumn)==null?0.0:Double.parseDouble(mapTemp.get(numberColumn).toString());
        							numberTemp += numberValue;
            					}
        						if(!groupIdList.contains("period")&&numberColumn.equals("count")){
        							numberTemp = numberTemp/periodCount;
        							DecimalFormat df = new DecimalFormat("#.00");
        							resultMap.put(numberColumn, df.format(numberTemp));
        						}else{
        							resultMap.put(numberColumn, numberTemp);
        						}
        					}
        					reslutContentList.add(resultMap);
        				}
        			}else{
        				reslutContentList = reportPlanContentList;
        			}
         		}else{
    				reslutContentList = reportPlanContentList;
    			}
        	}else{
    			reslutContentList = reportPlanContentList;
    		}
        	return reslutContentList;
		} catch (Exception e) {
			throw e;
		}
    }
    @Override
    public List<Map<String,Object>> getReportPlanReverseGridData(List<ReportItem> items,List<String> sqlFilterList,ReportDefine reportDefine) throws Exception{
    	if(OtherUtil.measureNull(items)||items.isEmpty()){
			return null;
		}
		try {
			Map<String,String> codeNameMap = new LinkedHashMap<String, String>();
			String sql = "SELECT " ;
			String reverseColumn = reportDefine.getReverseColumn();
			String[] reverseColumnArr = reverseColumn.split(",");
			String sqlTemp = "";
			for(String strTemp:reverseColumnArr){
				if(OtherUtil.measureNotNull(sqlTemp)){
					sqlTemp += "+";
				}
				if("issueNumber".equals(strTemp)){
					sqlTemp += "'(第'+"+"convert(varchar,rp."+strTemp+")+'次)'";
				}else{
					sqlTemp = "convert(varchar,rp."+strTemp+")";
				}
			}
			sql += sqlTemp + " AS reverse ";
			for(ReportItem rpItem:items){
				String itemCode = rpItem.getItemCode();
				String itemName = rpItem.getColName();
				codeNameMap.put(itemCode, itemName);
				sql += ",rp." + itemCode;
			}
			sql += " FROM " + reportDefine.getTableName() + " AS rp ";
			if(OtherUtil.measureNotNull(sqlFilterList)&&!sqlFilterList.isEmpty()){
	    		sqlTemp = "";
	    		for(String sqlFilter:sqlFilterList){
	    			sqlTemp += " "+ sqlFilter +" AND";
	    		}
	    		sqlTemp = OtherUtil.subStrEnd(sqlTemp, "AND");
	    		sql += " WHERE " + sqlTemp + " ";
	    	}
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
					 resultTempMap.put("rpItems", codeNameMap.get(itemCode));
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
