package com.huge.ihos.system.reportManager.search.webapp.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.foundation.common.GeneralAppException;
import com.huge.ihos.excel.ColumnDefine;
import com.huge.ihos.excel.DataSet;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.reportManager.search.exinterface.SearchCallback;
import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.ihos.system.reportManager.search.util.ColumnDef;
import com.huge.ihos.system.reportManager.search.util.ExcelUtil;
import com.huge.ihos.system.reportManager.search.util.ExportHelper;
import com.huge.ihos.system.reportManager.search.util.SearchCriteria;
import com.huge.ihos.system.reportManager.search.util.SearchUtils;
import com.huge.util.OtherUtil;
import com.huge.util.ReturnUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.SpringContextHelper;

public class QueryPublicAction
    extends JqGridBaseAction
     {

    /**
     * 
     */
    private static final long serialVersionUID = -2001594562944773104L;

    private QueryManager queryManager;

    private String searchName;

    private String actionName;

    private String params;

    // task use
    private String taskName;

    private String TASKNAMEKEY = "ARGS";

    private Object queryEntity;

    private String jsonMessages;

    private Search search;

    private String jsonStatus = "success";

    private int jsonCode = -1;

	private String fileName;

    private DataSource dataSource;
    
    private String allParam;
    
    private String editType;
    
    private String snapCode;

    public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}

	public String executePublic() {
        log.info( "begin query public action process,search name is:  " + this.searchName + " action name is: " + this.actionName );
        try {
            if ( this.actionName.equalsIgnoreCase( "delete" ) ) {
                this.jsonMessages = this.delete();
            }
            else if ( this.actionName.equalsIgnoreCase( "saveRow" ) ) {
                this.jsonMessages = this.saveRow();
            }
            else if ( this.actionName.equalsIgnoreCase( "process" ) ) {
            	SearchCriteria criteria = this.queryManager.getSearchCriteriagetSearchCriteria( getRequest(), searchName );
            	String dataSql = criteria.getRealSql();
				Object[] args = criteria.getRealAgrs();
				for(Object arg : args){
					String argStr = arg.toString();
					dataSql = dataSql.replaceFirst("\\?", "'"+argStr+"'");
				}
            	ReturnUtil returnUtil = this.process(dataSql);
            	if(returnUtil.getStatusCode()!=0){
            		this.jsonStatus = "error";
            	}
            	this.jsonCode = returnUtil.getStatusCode();
                this.jsonMessages = returnUtil.getMessage();
                if(jsonCode<0){
                	this.setStatusCode(300);
                }else if(jsonCode==0){
                	this.setStatusCode(200);
                }else{
                	this.setStatusCode(jsonCode);
                }
                this.setMessage(returnUtil.getMessage());
            }

            else if ( this.actionName.equalsIgnoreCase( "outputExcel" ) ) {
                this.jsonMessages = this.outputExcel();
            }
            else if ( this.actionName.equalsIgnoreCase( "processByCondition" ) ) {
            	String businessCode = getRequest().getParameter("businessTypeCode");
            	if(businessCode==null){
            		businessCode = "''";
            	}
            	String userId = UserContextUtil.getLoginUserId();
            	JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" ) );
            	String deleteSql = "delete tmp_conditionID where userId='"+userId+"' and businessTypeCode='"+businessCode+"'";
            	jt.execute(deleteSql);
            	Search search = queryManager.getSearchBySearchName(searchName);
            	String idCol = search.getMyKey();
            	HttpServletRequest request = getRequest();
            	SearchCriteria criteria = this.queryManager.getSearchCriteriagetSearchCriteria( request, searchName );
            	String dataSql = criteria.getRealSql();
				Object[] args = criteria.getRealAgrs();
				for(Object arg : args){
					String argStr = arg.toString();
					dataSql = dataSql.replaceFirst("\\?", "'"+argStr+"'");
				}
				String conditionIdSql = "INSERT INTO tmp_conditionID (dataId,businessTypeCode,userId) SELECT "+idCol+",'"+businessCode+"','"+userId+"' FROM ("+dataSql+") idrs";
				jt.execute(conditionIdSql);
				
				ReturnUtil returnUtil = this.process(dataSql);
            	if(returnUtil.getStatusCode()!=0){
            		this.jsonStatus = "error";
            	}
            	this.jsonCode = returnUtil.getStatusCode();
                this.jsonMessages = returnUtil.getMessage();
                if(jsonCode<0){
                	this.setStatusCode(300);
                }else if(jsonCode==0){
                	this.setStatusCode(200);
                }else{
                	this.setStatusCode(jsonCode);
                }
                this.setMessage(returnUtil.getMessage());
				//queryManager.s
            }
        }
        catch ( Exception ex ) {
            this.jsonMessages = ex.getMessage();
            this.jsonStatus = "error";
            // throw new GeneralAppException(jsonMessages);
        }
        return this.SUCCESS;

    }

    public String outputExcel() {
        Search search = null;
        if ( searchName != null && !searchName.equalsIgnoreCase( "" ) ) {
            search = this.queryManager.getSearchBySearchName( searchName );
        }
        SearchCriteria cri = this.queryManager.getSearchCriteriagetSearchCriteria( getRequest(), searchName );
        Map<String, Object> map = queryManager.getExcelQueryCriteria( cri );
        List list = (List) map.get( "list" );
        HttpServletRequest req = this.getRequest();
        String fileName2 = null;
        if ( fileName != null && !fileName.equals( "" ) && !fileName.equals( "undefined" ) ){
        	if(fileName.contains(".xls")){
        		fileName = fileName.replace(".xls", "");
        	}
            fileName2 = req.getRealPath( "//home//template//" + fileName + ".xls" ); //获得绝对路径
        }
        SearchOption[] ops = queryManager.getSearchAllOptionsBySearchIdOrdered( search.getSearchId() );
        try {
            HttpServletResponse resp = ServletActionContext.getResponse();
            DataSource ds = (DataSource) SpringContextHelper.getBean( "dataSource" );
            if ( fileName2 != null )
                ExcelUtil.read( list, resp, ops, fileName2, req, searchName ,search.getTitle() );
            else
                ExcelUtil.exportExcelByList( list, resp, ops, search.getTitle() );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String outputExcelByRow() {
    	try {
		
        Search search = null;
        if ( searchName != null && !searchName.equalsIgnoreCase( "" ) ) {
            search = this.queryManager.getSearchBySearchName( searchName );
        }
        
        //SearchCriteria cri = this.queryManager.getSearchCriteriagetSearchCriteria( getRequest(), searchName );
       // Map<String, Object> map = queryManager.getExcelQueryCriteria( cri );
       // List list = (List) map.get( "list" );
        HttpServletRequest req = this.getRequest();
        SearchUtils su = new SearchUtils( req, searchName );
        Map systemProperties = su.getSystemPropertiesMap();
        Map userProperties = su.getUserPropertiesMap();
        Map searchItemProperties = su.getSearchProperitesMap();
        
        DataSet userDataSet = new DataSet();
        userDataSet.name = "user";
        List<Map<String, String>> userPropertiesList = new ArrayList<Map<String,String>>();
        userPropertiesList.add(userProperties);
        userDataSet.rowList = userPropertiesList;
        
        DataSet queryDataSet = new DataSet();
        userDataSet.name = "query";
        List<Map<String, String>> queryPropertiesList = new ArrayList<Map<String,String>>();
        queryPropertiesList.add(searchItemProperties);
        queryDataSet.rowList = queryPropertiesList;
        
        DataSet systemDataSet = new DataSet();
        userDataSet.name = "system";
        List<Map<String, String>> systemPropertiesList = new ArrayList<Map<String,String>>();
        systemPropertiesList.add(systemProperties);
        systemDataSet.rowList = systemPropertiesList;
        
        String fileName2 = null;
        if ( fileName != null && !fileName.equals( "" ) && !fileName.equals( "undefined" ) ){
        	if(fileName.contains(".xls")){
        		fileName = fileName.replace(".xls", "");
        	}
            fileName2 = req.getRealPath( "//home//template//" + fileName + ".xls" ); //获得绝对路径
        }
        SearchOption[] ops = queryManager.getSearchAllOptionsBySearchIdOrdered( search.getSearchId() );
        ColumnDef[] methods = ExportHelper.getColumnDefs( ops );
        String rowData = req.getParameter("rowData");
        JSONObject sheetData = JSONObject.fromObject(rowData);
        Iterator<String> sheetKeyIt = sheetData.keys();
        Map<String, List<Map<String , String>>> sheetDataMap = new LinkedHashMap<String, List<Map<String,String>>>();
        Map<String , Map<String, DataSet>> excelData = new LinkedHashMap<String, Map<String,DataSet>>();
        while (sheetKeyIt.hasNext()) {
			String sheetKey = sheetKeyIt.next();
			Map<String, String> rowDataMap = (Map)sheetData.get(sheetKey);
			List<Map<String , String>> rowList = new ArrayList<Map<String,String>>();
			rowList.add(rowDataMap);
			DataSet dataSet = new DataSet();
			dataSet.rowList = rowList;
			Map<String, DataSet> dataSetMap = new HashMap<String, DataSet>();
			dataSetMap.put("data", dataSet);
			dataSetMap.put("user", userDataSet);
			dataSetMap.put("query", queryDataSet);
			dataSetMap.put("system", systemDataSet);
			Map<String, ColumnDefine> columnDeFineMap = new HashMap<String, ColumnDefine>();
			for(ColumnDef  columnDef : methods){
				ColumnDefine columnDefine = new ColumnDefine();
				columnDefine.name = columnDef.getFieldName();
				columnDefine.type = columnDef.getType();
				columnDeFineMap.put(columnDefine.name, columnDefine);
			}
			dataSet.columnDefineMap = columnDeFineMap;
			excelData.put(sheetKey, dataSetMap);
		}
        this.setMessage(queryManager.outputExcel(fileName2, excelData )+"@@"+search.getTitle()+".xls");
//        try {
//            HttpServletResponse resp = ServletActionContext.getResponse();
//            DataSource ds = (DataSource) SpringContextHelper.getBean( "dataSource" );
//            if ( fileName2 != null )
//                ExcelUtil.read( list, resp, ops, fileName2, req, searchName ,search.getTitle() );
//            else
//                ExcelUtil.exportExcelByList( list, resp, ops, search.getTitle() );
//        }
//        catch ( Exception e ) {
//            e.printStackTrace();
//        }
    	} catch (Exception e) {
			e.printStackTrace();
		}
        return SUCCESS;
    }
    
    public String  outputExcelFileExit(){
    	HttpServletRequest req = this.getRequest();
    	try {
    		if(fileName!=null){
    			if ( fileName != null && !fileName.equals( "" ) && !fileName.equals( "undefined" ) ){
    	        	if(fileName.contains(".xls")){
    	        		fileName = fileName.replace(".xls", "");
    	        	}
    	        	File file = new File(req.getRealPath( "//home//template//") + "\\" +fileName + ".xls" );
    	        	System.out.println("文件路径："+req.getRealPath( "//home//template//") + "\\" +fileName + ".xls" );
    	        	System.out.println("文件是否存在："+file.exists());
    	        	if(file.exists()){
    	        		return ajaxForward(true, "1", false);
    	        	}else{
    	        		return ajaxForward(false, "0", false);
    	        	}
    	        }else{
        			return ajaxForward(false, "0", false);
        		}
    		}else{
    			return ajaxForward(false, "0", false);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "0", false);
		}
    }

    private String saveRow() {
    	String msg = null;
    	try {
        //HttpServletRequest req = this.getRequest();
        Search search = this.queryManager.getSearchBySearchName( searchName );
        //SearchOption[] ops = this.queryManager.getSearchEditOptionsBySearchNameOrdered( search.getSearchId() );

        String tableName = search.getFormName();
        
        
        String[] allParams=allParam.split(";");
    	Map<String,String[]> entityMap=new HashMap<String, String[]>();
    	for (int i = 0; i < allParams.length; i++) {
			String[] ps=allParams[i].split("\\|");
			String key=ps[ps.length-1];
			entityMap.put(key, ps);
		}
        if(OtherUtil.measureNull(editType)){
        	String tableId = search.getMyKey();
        	msg=this.queryManager.updateQueryRow(tableName,tableId,entityMap);
        }
        else
        	msg=this.queryManager.addQueryRow(tableName,entityMap);
        	
        
        
        //String tableIdValue=id;*/
        /*String[] param = new String[ops.length];
        String[] values = new String[ops.length + 1];
        for ( int i = 0; i < ops.length; i++ ) {
            param[i] = ops[i].getFieldName();
            values[i] = req.getParameter( ops[i].getFieldNameUpperCase() );
            if ( ( "Number".equals( ops[i].getFieldType() ) ) || ( "Integer".equals( ops[i].getFieldType() ) )
                || ( "Money".equals( ops[i].getFieldType() ) ) ) {
                if ( values[i].equals( "" ) )
                    values[i] = "0";
            }

        }
        values[ops.length] = id;*/

        //msg = this.queryManager.updateQueryRow( tableName, tableId, param, values );
    	} catch (Exception e) {
			e.printStackTrace();
		}
        return msg;
    }

    private String delete() {
        log.info( "begin query public delete action." + "get id is: " + this.getId() );
        StringTokenizer ids = new StringTokenizer( id, "," );
        List list = new ArrayList();
        while ( ids.hasMoreTokens() ) {
            list.add( ids.nextToken() );
        }
        String[] idarray = new String[list.size()];
        list.toArray( idarray );

        String msg = this.queryManager.publicDelete( searchName, idarray );
        if ( msg.equalsIgnoreCase( "success" ) ) {
            msg = this.getText( "public.delete.success" );
        }
        else {
            msg = this.getText( "public.delete.failure", msg );
        }

        return msg;
    }

    private ReturnUtil process(String dataSql) {
        if ( taskName == null || this.searchName == null )
            throw new GeneralAppException( "配置参数错误" );
        String[] args = this.getRequest().getParameterValues( "ARGS" );
        String itemValue = this.getRequest().getParameter( "itemValue" );
        String[] itemValueArr = null;
        String callback = this.getRequest().getParameter( "callback" );
        Map<String , String> itemValueMap = new HashMap<String, String>();
        itemValueMap.put("dataSql", dataSql);
        if(itemValue!=null&&itemValue!=""&&!"".equals(itemValue)){
        	itemValueArr = itemValue.split("%7C");
        }
        {
            StringBuilder sb = new StringBuilder();
            for ( int i = 0; i < args.length; i++ ) {
                sb.append( args[i] );
                if ( i < args.length - 1 )
                    sb.append( "," );
            }
            log.info( "begin query public process action." + "get id is: " + this.getId() + "get args is: " + sb );
            if(itemValueArr!=null){
            	for(String value: itemValueArr){
                	String nameV = value;
                	if(nameV.contains("=")){
                		String[] nv = nameV.split("=");
                		if(nv.length>1&&(nv[1]!="null"&&nv[1]!=""&&nv[1]!="undefine")){
                			log.info( "searchItem:"+nv[0] );
                			log.info( "searchItemValue:"+nv[1] );
                			itemValueMap.put(nv[0], nv[1]);
                		}
                	}
                }
            }
            
        }
        //String period = this.queryManager.getCurrentPeriod();
        String period = this.getLoginPeriod();
        Object[] proArgs = prapareProcessArgs( args, id, period ,itemValueMap);
        //		this.jsonMessages= this.queryManager.publicPrecess(taskName, proArgs);
        itemValueMap.put("ids", this.getId());
        itemValueMap.put("snapCode", this.snapCode);
        String[] callbackArr = null;
        SearchCallback searchCallback = new SearchCallback();
        String afterFunc = "";
        String afterProcess = "";
        if(callback!=null&&!"".equals(callback)){
        	callbackArr = callback.split(",");
        	for(String value: callbackArr){
        		String cb = value;
        		if(cb.contains(":")){
        			String[] cbArr = cb.split(":");
        			if(cbArr[0].equals("beforeFunc")){
        				searchCallback.exeCallback(cbArr[1]+"_"+cbArr[0], itemValueMap);
        			}else if(cbArr[0].equals("afterFunc")){
        				afterFunc = cbArr[1]+"_"+cbArr[0];
        			}else if(cbArr[0].equals("afterProcess")){
        				afterProcess = cbArr[1];
        			}
        		}
        	}
        }
        ReturnUtil returnUtil = this.queryManager.publicPrecess( taskName, proArgs );
        if(!"".equals(afterFunc)){
        	searchCallback.exeCallback(afterFunc, itemValueMap);
        }
        if(!"".equals(afterProcess)){
        	returnUtil = this.queryManager.publicPrecess( afterProcess, proArgs );
        }
        return returnUtil;
    }

    private Object[] prapareProcessArgs( String[] argNames, String ids, String curPeriod ,Map<String , String> itemValueMap) {
        String argName = "";
        int len = argNames.length;
        Object[] args = new Object[len];
        //TODO 加入参数序列 loginPersonId,loginDeptId
        for ( int i = 0; i < len; i++ ) {
            argName = argNames[i];
            if ( argName.equals( "CurrentPeriod" ) )
                args[i] = curPeriod;
            if ( argName.equals( "SelIDS" ) ) {
                if ( ids != null && ids.length() > 0 )
                    args[i] = ids;
                else {
                    this.jsonMessages = "没有选择需要处理的记录，请选择。";
                    throw new GeneralAppException( "没有选择需要处理的记录，请选择。" );

                }
            }
            SearchUtils su = new SearchUtils();
            Map uMap = su.getUserPropertiesMap();
            String itemValue = itemValueMap.get(argName);
            if ( argName.equals( "PERSONID" ) )
                args[i] = uMap.get( su.USER_PERSONID_KEY );
            else if ( argName.equals( "DEPTID" ) )
                args[i] = uMap.get( su.USER_DEPTID_KEY );
            else if( argName.equals( "SNAPCODE" ) ){
            	args[i] = this.snapCode;
            }else if(itemValue!=null&&!"".equals(itemValue)){
            	args[i] = itemValue;
            }

        }

        return args;
    }

    public String getJsonStatus() {
        return jsonStatus;
    }

    public void setJsonStatus( String jsonStatus ) {
        this.jsonStatus = jsonStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName( String taskName ) {
        this.taskName = taskName;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch( Search search ) {
        this.search = search;
    }

    public String getJsonMessages() {
        return jsonMessages;
    }

    public void setJsonMessages( String jsonMessages ) {
        this.jsonMessages = jsonMessages;
    }

    public int getJsonCode() {
		return jsonCode;
	}

	public void setJsonCode(int jsonCode) {
		this.jsonCode = jsonCode;
	}

    public QueryManager getQueryManager() {
        return queryManager;
    }

    public void setQueryManager( QueryManager queryManager ) {
        this.queryManager = queryManager;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName( String searchName ) {
        this.searchName = searchName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName( String actionName ) {
        this.actionName = actionName;
    }

    public Object getQueryEntity() {
        return queryEntity;
    }

    public void setQueryEntity( Object queryEntity ) {
        this.queryEntity = queryEntity;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource( DataSource dataSource ) {
        this.dataSource = dataSource;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }

    public void setParams( String params ) {
        this.params = params;
    }

	public String getAllParam() {
		return allParam;
	}

	public void setAllParam(String allParam) {
		this.allParam = allParam;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

}
