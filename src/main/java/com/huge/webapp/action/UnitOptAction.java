package com.huge.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.ihos.excel.ColumnDefine;
import com.huge.ihos.excel.ColumnStyle;
import com.huge.ihos.hql.HqlUtil;
import com.huge.ihos.hql.QueryItem;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.oa.bulletin.model.Bulletin;
import com.huge.ihos.system.oa.bulletin.service.BulletinManager;
import com.huge.ihos.system.oa.bylaw.model.ByLaw;
import com.huge.ihos.system.oa.bylaw.service.ByLawManager;
import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.service.UtilOptService;
import com.huge.util.DateUtil;
import com.huge.util.ExcelUtil;
import com.huge.util.OtherUtil;
import com.huge.util.ReturnUtil;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.PropertyFilter.MatchType;
import com.huge.webapp.util.SpringContextHelper;
import com.huge.webapp.ztree.ZTreeSimpleNode;

public class UnitOptAction
    extends BaseAction {

    private String entityName;

    private String searchItem;

    private String searchValue;
    
    private String returnMessage = "此ID已存在！";

	private DataSource dataSource = SpringContextHelper.getDataSource();

    private String tableName;

    private String treeId;

    private String treeName;

    private String parentId;

    private String order;
    
    private String filter;
    
    private String classTable;
    
	private String classTreeId;
    
    private String classTreeName;
    
    private String classFilter;
    
    private String classOrder;
    
    private String selectTreeSql;
    
	private String formula;
    
    private String taskName;

    private String proArgsStr;
    
    private QueryManager queryManager;
    
    private PeriodManager periodManager;



	public PeriodManager getPeriodManager() {
		return periodManager;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}

	private List nodes;

	private UtilOptService utilOptService;
	
    public UtilOptService getUtilOptService() {
		return utilOptService;
	}

	public void setUtilOptService(UtilOptService utilOptService) {
		this.utilOptService = utilOptService;
	}

	public String checkId() {
        List searchList = userManager.searchDictionary( entityName, searchItem + "='" + searchValue + "'" );
        if ( searchList != null && searchList.size() != 0 ) {
            return ajaxForward( false, returnMessage, false );
        }
        else {
            return null;
        }
    }

    public String searchDictionary() {
        String searchString = null;
        try {
            List<Search> searchList = userManager.searchDictionary( entityName, searchItem + "='" + searchValue + "'" );
            if ( searchList != null && searchList.size() != 0 ) {
                //searchString = JSONArray.fromObject(searchList).toString();
                searchString = searchList.get( 0 ).getTitle();
                return ajaxForward( true, searchString, false );
            }
            else {
                return ajaxForward( false, "查询值为空！", false );
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
            return ajaxForward( false, e.getMessage(), false );
        }

    }

    public String treeSelectNodes() {
        try {
        	nodes = new ArrayList();
        	if(null!=selectTreeSql&&!"".equals(selectTreeSql)){
        		selectTreeSql = UserContextUtil.replaceSysVars(selectTreeSql);
        		log.info("treeSelectSql:"+selectTreeSql);
        		String icon = this.getContextPath();
        		
        		JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
                List rs = jtl.queryForList( selectTreeSql );
                Iterator it = rs.iterator();
                while ( it.hasNext() ) {
                    Map userMap = (Map) it.next();
                    ZTreeSimpleNode zTreeSimpleNode = new ZTreeSimpleNode();
                    zTreeSimpleNode.setId( userMap.get( "id" ).toString() );
                    zTreeSimpleNode.setName( userMap.get( "name" ).toString() );
                    if(userMap.get( "icon" )!=null){
                    	zTreeSimpleNode.setIcon(icon+ userMap.get( "icon" ).toString() );
                    }
                    if((userMap.get( "parent" )==null)?false:true){
                    	zTreeSimpleNode.setpId( userMap.get( "parent" ).toString() );
                    }
                    if(((userMap.get( "isparent" )==null)?false:true)&&"1".equals(userMap.get( "isparent" ).toString())){
                    	zTreeSimpleNode.setIsParent(true);
                    }
                    if(userMap.get("actionUrl")!=null){
                    	zTreeSimpleNode.setActionUrl(userMap.get("actionUrl").toString());
                    }
                    if(((userMap.get( "disCheckAble" )==null)?false:true)&&"1".equals(userMap.get( "disCheckAble" ).toString())){
                    	zTreeSimpleNode.setDisCheckAble(true);
                    }
                    nodes.add( zTreeSimpleNode );
                }
        	}else{
            String nameAlias = treeName.contains(" as ")?treeName.split(" as ")[1]:treeName;
            String sql = "select " + treeId + "," + treeName + "," + parentId + " from " + tableName + " where 1=1 ";
            if ( !filter.equals( "" ) ) {
                sql += "and " + filter;
            }
            if(!order.equals("")){
            	sql += " ORDER BY "+order;
            }
            log.info("treeSelectSql:"+sql);
            JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
            List rs = jtl.queryForList( sql );
            Iterator it = rs.iterator();
            while ( it.hasNext() ) {
                Map userMap = (Map) it.next();
                ZTreeSimpleNode zTreeSimpleNode = new ZTreeSimpleNode();
                zTreeSimpleNode.setId( userMap.get( treeId ).toString() );
                zTreeSimpleNode.setName( userMap.get( nameAlias ).toString() );
                zTreeSimpleNode.setpId( userMap.get( parentId ) == null ? "" : userMap.get( parentId ).toString() );
                nodes.add( zTreeSimpleNode );
            }
            if(classTable!=null&&!classTable.equals("")&&nodes.size()!=0){
	            sql = "select " + classTreeId + "," + classTreeName + " from " + classTable + " where 1=1 ";
	            if ( OtherUtil.measureNotNull(classFilter) ) {
	                sql += "and " + classFilter;
	            }
	            if(!classOrder.equals("")){
	            	sql += " ORDER BY "+classOrder;
	            }
	            rs = jtl.queryForList( sql );
	            it = rs.iterator();
	            while ( it.hasNext() ) {
	                Map userMap = (Map) it.next();
	                ZTreeSimpleNode zTreeSimpleNode = new ZTreeSimpleNode();
	                zTreeSimpleNode.setId( userMap.get( classTreeId ).toString() );
	                zTreeSimpleNode.setName( userMap.get( classTreeName ).toString() );
	                zTreeSimpleNode.setpId("");
	                nodes.add( zTreeSimpleNode );
	            }
            }
        	}
            return ajaxForward( "" );
        }
        catch ( Exception e ) {
            // TODO: handle exception
            e.printStackTrace();
            return ajaxForward( "" );
        }
    }
    
    public String computeFormula() {
    	String rsValue = "";
		try {
			JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
            List rs = jtl.queryForList( "select "+formula+" as rs" );
            rsValue = ((Map)rs.get(0)).get("rs").toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return ajaxForward(rsValue);
	}

    public String executeSp() {
        try {
            Object[] proArgs = proArgsStr.split( "," );
            ReturnUtil returnUtil = this.queryManager.publicPrecess( taskName, proArgs );
            if(returnUtil.getStatusCode()==0){
            	return ajaxForward( true, returnUtil.getMessage(), false );
            }else{
            	return ajaxForward( false, returnUtil.getMessage(), false );
            }
        }
        catch ( Exception e ) {
            // TODO: handle exception
            return ajaxForward( false, e.getMessage(), false );
        }

    }
    
    public String executeSpNoParam() {
        try {
        	String personId = this.getUserManager().getCurrentLoginUser().getPerson().getPersonId();
//        	String checkPerid = periodManager.getCurrentPeriod().getPeriodCode();
        	String checkPerid = this.getLoginPeriod();
            Object[] proArgs = {(Object)checkPerid,(Object)personId};
            //return ajaxForward( true, this.queryManager.publicPrecess( taskName, proArgs ), false );
            ReturnUtil returnUtil = this.queryManager.publicPrecess( taskName, proArgs );
            if(returnUtil.getStatusCode()==0){
            	return ajaxForward( true, returnUtil.getMessage(), false );
            }else if(returnUtil.getStatusCode()<=-1){
            	return ajaxForward( false, returnUtil.getMessage(), false );
            }else{
            	this.setMessage(returnUtil.getMessage());
            	this.setStatusCode(returnUtil.getStatusCode());
            	return SUCCESS;
            }
        }
        catch ( Exception e ) {
            // TODO: handle exception
            return ajaxForward( false, e.getMessage(), false );
        }

    }
    
    public String executeSpNoParamU() {
        try {
        	String userId = ""+this.getUserManager().getCurrentLoginUser().getId();
//        	String checkPerid = periodManager.getCurrentPeriod().getPeriodCode();
        	String checkPerid = this.getLoginPeriod();
            Object[] proArgs = {(Object)checkPerid,(Object)userId};
            //return ajaxForward( true, this.queryManager.publicPrecess( taskName, proArgs ), false );
            ReturnUtil returnUtil = this.queryManager.publicPrecess( taskName, proArgs );
            if(returnUtil.getStatusCode()==0){
            	return ajaxForward( true, returnUtil.getMessage(), false );
            }else if(returnUtil.getStatusCode()<=-1){
            	return ajaxForward( false, returnUtil.getMessage(), false );
            }else{
            	this.setMessage(returnUtil.getMessage());
            	this.setStatusCode(returnUtil.getStatusCode());
            	return SUCCESS;
            }
        }
        catch ( Exception e ) {
            // TODO: handle exception
            return ajaxForward( false, e.getMessage(), false );
        }

    }
    
    public String downLoadExel(){
    	try {
			HttpServletRequest request = this.getRequest();
			HttpServletResponse response = this.getResponse();
			String filePath = request.getParameter("filePath");
			String fileName = request.getParameter("fileName");
			File excelFile = new File(filePath);
			if(!excelFile.exists()){
				return null;
			}
			//String fileName = excelFile.getName();
			filePath = excelFile.getPath();
			response.setHeader( "Content-Disposition", "attachment;filename=" + new String( fileName.getBytes( "GBK" ), "ISO8859-1" ) );//"ISO8859-1"
			OutputStream outs = response.getOutputStream();
			Workbook workbook = ExcelUtil.getWorkBook(filePath);
			workbook.write( outs );
			excelFile.delete();
    	} catch (Exception e) {
			e.printStackTrace();
		} finally{
			
		}
    	
    	return null;
    }
    
    public String downLoadFile(){
    	try {
			HttpServletRequest request = this.getRequest();
			HttpServletResponse response = this.getResponse();
			String filePath = request.getParameter("filePath");
			String fileName = request.getParameter("fileName");
			String isDelete = request.getParameter("delete");
			File file = new File(filePath);
			if(!file.exists()){
				return null;
			}
			//String fileName = excelFile.getName();
			filePath = file.getPath();
			response.setHeader( "Content-Disposition", "attachment;filename=" + new String( fileName.getBytes( "GBK" ), "ISO8859-1" ) );//"ISO8859-1"
			OutputStream outs = response.getOutputStream();
			FileInputStream fileInputStream=new FileInputStream(file);
			byte bytes[]=new byte[1024];
			int len=0;  
			while((len=fileInputStream.read(bytes))!=-1)  
			{
				outs.write(bytes,0,len);   
			}
			outs.close();  
			fileInputStream.close(); 
			if("1".equals(isDelete)){
				file.delete();
			}
    	} catch (Exception e) {
			e.printStackTrace();
		} finally{
			
		}
    	
    	return null;
    }
   /* public static List<QueryItem> buildFromHttpRequest( final HttpServletRequest request, final String filterPrefix ) {
        List<QueryItem> QueryItems = new ArrayList<QueryItem>();

        //从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> paramMap = RequestUtil.getParametersStartingWith( request, filterPrefix + "_" );

        //分析参数Map,构造PropertyFilter列表
        for ( Map.Entry<String, Object> entry : paramMap.entrySet() ) {
            String filterName = entry.getKey();
            String value = (String) entry.getValue();
            //如果value值为空,则忽略此filter.
            if ( StringUtils.isNotBlank( value ) ) {
            	String firstPart = StringUtils.substringBefore( filterName, "_" );
                String matchTypeCode = StringUtils.substring( firstPart, 0, firstPart.length() - 1 );
                String propertyTypeCode = StringUtils.substring( firstPart, firstPart.length() - 1, firstPart.length() );
                String propertyNameStr = StringUtils.substringAfter( filterName, "_" );
                if("EQ".equals(matchTypeCode)){
                	value = "="+value;
                }else if("NE".equals(matchTypeCode)){
                	value = "<>"+value;
                }else if("LIKE".equals(matchTypeCode)){
                	value = "*"+value+"*";
                }else if("LT".equals(matchTypeCode)){
                	value = "<"+value;
                }else if("GT".equals(matchTypeCode)){
                	value = ">"+value;
                }else if("LE".equals(matchTypeCode)){
                	value = "<="+value;
                }else if("GE".equals(matchTypeCode)){
                	value = ">="+value;
                }
            	QueryItem queryItem = new QueryItem( propertyNameStr, value );
            	QueryItems.add( queryItem );
            }
        }

        return QueryItems;
    }*/
    public String outPutExcel(){
    	HttpServletRequest request = this.getRequest();
    	String entityName = request.getParameter("entityName");
    	String colDefineStr = request.getParameter("colDefineStr");
    	List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( request );
    	List<QueryItem> queryItems = new ArrayList<QueryItem>();
    	for(PropertyFilter pf : filters){
    		String name = pf.getPropertyName();
    		Object v = pf.getMatchValue();
    		String op = "";
    		if ( pf.getMatchType().equals( MatchType.LIKE ) ) {
    			op = "*";
    		}else if( pf.getMatchType().equals( MatchType.EQ ) ){
    			op = "=";
            }else if(pf.getMatchType().equals( MatchType.NE )){
            	op = "<>";
            }else if(pf.getMatchType().equals( MatchType.LT )){
            	op = "<";
            }else if(pf.getMatchType().equals( MatchType.GT )){
            	op = ">";
            }else if(pf.getMatchType().equals( MatchType.LE )){
            	op = "<=";
            }else if(pf.getMatchType().equals( MatchType.GE )){
            	op = ">=";
            }else if(pf.getMatchType().equals( MatchType.IN )){
            	op = "in";
            }else if(pf.getMatchType().equals( MatchType.SQ )){
            	op = "sq";
            }
    		QueryItem queryItem = new QueryItem( name,op, v );
			queryItems.add(queryItem);
    	}
    	JSONObject colArr = JSONObject.fromObject(colDefineStr);
    	String[] colNameArr = new String[colArr.size()];
    	int colIndex = 0;
    	Iterator colIt = colArr.keys();
    	List<ColumnStyle> columnStyles = new ArrayList<ColumnStyle>();
    	Map<String,ColumnDefine> columnDefineMap = new HashMap<String, ColumnDefine>();
    	while (colIt.hasNext()) {
			String	key = colIt.next().toString();
			JSONObject col = JSONObject.fromObject(colArr.get(key));
    		String label = col.getString("label");
    		String name = col.getString("name");
    		String width = col.getString("width");
    		String align = col.getString("align");
    		String alias = name;
    		if(alias.contains(".")){
    			alias = alias.replace(".", "_");
    		}
    		colNameArr[colIndex] = name;
    		
    		ColumnStyle columnStyle = new ColumnStyle();
    		columnStyles.add(columnStyle);
    		columnStyle.name = name;
    		columnStyle.columnNum = colIndex;
    		columnStyle.colnumWidth = Integer.parseInt(width);
    		columnStyle.columnHidden =false;
    		columnStyle.align = align;
    		columnStyle.label = label;
    		
    		ColumnDefine columnDefine = new ColumnDefine();
    		columnDefine.name = name;
    		String type = col.getString("type");
    		int dataType = 1;
    		if("integer".equals(type)){
    			dataType = 2;
    		}else if("number".equals(type)||"currency".equals(type)){
    			dataType = 3;
    		}else if("checkbox".equals(type)){
    			dataType = 5;
    		}
    		columnDefine.type = dataType;
    		columnDefineMap.put(name,columnDefine);
    		colIndex++;
		}
    	String outPutType = request.getParameter("outPutType");
    	String page = request.getParameter("page");
    	String pageSize = request.getParameter("pageSize");
    	String sortname = request.getParameter("sortname");
    	String sortorder = request.getParameter("sortorder");
    	HqlUtil hqlUtil = new HqlUtil(entityName);
    	hqlUtil.setFindType(outPutType);
    	hqlUtil.setPage(Integer.parseInt(page));
    	hqlUtil.setPageSize(Integer.parseInt(pageSize));
    	hqlUtil.setColName(colNameArr);
    	String[] sortnameArr = sortname.split(",");
    	String[] sortorderArr = sortorder.split(",");
    	for(int orderIndex = 0; orderIndex<sortnameArr.length; orderIndex++){
    		String orderName = sortnameArr[orderIndex].trim();
    		String orderAsc = "";
    		if(orderIndex<sortorderArr.length){
    			orderAsc = sortorderArr[orderIndex].trim();
    		}
    		if(!orderName.equals("")){
    			if(orderAsc.equals("")){
    				orderAsc = "asc";
    			}
    			hqlUtil.addSort(orderName+" "+orderAsc);
    		}
    	}
    	//hqlUtil.setQueryItems(buildFromHttpRequest(request,"filter"));
    	hqlUtil.setQueryItems(queryItems);
    	List<Map<String, String>> rowList = utilOptService.getByHqlUtil(hqlUtil);
    	String title = request.getParameter("title");
    	String excelFullPath = request.getRealPath( "//home//temporary//");
    	excelFullPath += "//"+DateUtil.convertDateToString("yyyyMMddhhmmss",Calendar.getInstance().getTime())+".xls";
    	ExcelUtil.createExcelStyle(rowList,columnStyles,columnDefineMap,title,excelFullPath);
    	this.setMessage(excelFullPath+"@@"+title+".xls");
    	return SUCCESS;
    }
    
    private BulletinManager bulletinManager;
    private ByLawManager byLawManager;
    private List<Bulletin> bulletinList;
    private List<ByLaw> byLawList;

	public void setBulletinManager(BulletinManager bulletinManager) {
		this.bulletinManager = bulletinManager;
	}

	public void setByLawManager(ByLawManager byLawManager) {
		this.byLawManager = byLawManager;
	}

	public List<Bulletin> getBulletinList() {
		return bulletinList;
	}

	public void setBulletinList(List<Bulletin> bulletinList) {
		this.bulletinList = bulletinList;
	}

	public List<ByLaw> getByLawList() {
		return byLawList;
	}

	public void setByLawList(List<ByLaw> byLawList) {
		this.byLawList = byLawList;
	}

	public String mainPage(){
    	this.currentUser = this.getSessionUser();
    	bulletinList = this.bulletinManager.getBulletinsByUser( currentUser );
        byLawList = this.byLawManager.getByLawsByUser( currentUser );
    	return SUCCESS;
    }
	
	
	String hql;
	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public String hasResult(){
		String validatorType = this.getRequest().getParameter("validatorType");
		List list = null;
		if("sql".equals(validatorType)){
			list = utilOptService.getBySqlToMap(hql);
		}else{
			list = utilOptService.getByHql(hql);
		}
		if ( list != null && list.size() != 0 ) {
            return ajaxForward( true, "1", false );
        }
        else {
        	return ajaxForward( false, "0", false );
        }
	}
	
	
	List<Map<String, String>> result;
	public List<Map<String, String>> getResult() {
		return result;
	}

	public void setResult(List<Map<String, String>> result) {
		this.result = result;
	}

	public String autocomplete(){
		try {
			result = new ArrayList<Map<String,String>>();
			HttpServletRequest request = ServletActionContext.getRequest();
			String q = request.getParameter( "q" );
			q = URLDecoder.decode( q, "UTF-8" );
			String sql = request.getParameter( "sql" );
			sql = sql.replaceAll("&#039;", "'");
			sql = sql.replaceAll("%q%", "%"+q+"%");
			List<Map<String, Object>> resultList = userManager.getBySqlToMap(sql);
			for(Map<String, Object> row : resultList){
				String idValue = "",nameValue = "",showValue = "";
				Set<Entry<String, Object>> colSet = row.entrySet();
				for(Entry<String, Object> colEntry : colSet){
					String colname = colEntry.getKey();
					Object value = colEntry.getValue();
					String v = "";
					if(value!=null){
						v = value.toString();
					}
					if(colname.equals("id")){
						idValue = v;
					}else if(colname.equals("name")){
						nameValue = v;
					}
					showValue += v+",";
				}
				showValue = OtherUtil.subStrEnd(showValue, ",");
				Map<String, String> rowMap = new HashMap<String, String>();
				rowMap.put("id", idValue);
				rowMap.put("name", nameValue);
				rowMap.put("showValue", showValue);
				result.add(rowMap);
			}
			request.setAttribute("result", result);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this.autocompleteList = matetypeManager.searchDictionary( entity, cloumns, q, extra1, extra2 );
		
		return SUCCESS;
	}
    
    public String getEntityName() {
        return entityName;
    }

    public void setEntityName( String entityName ) {
        this.entityName = entityName;
    }

    public String getSearchItem() {
        return searchItem;
    }

    public void setSearchItem( String searchItem ) {
        this.searchItem = searchItem;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue( String searchValue ) {
        this.searchValue = searchValue;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName( String tableName ) {
        this.tableName = tableName;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId( String treeId ) {
        this.treeId = treeId;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName( String treeName ) {
        this.treeName = treeName;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter( String filter ) {
        this.filter = filter;
    }

    public List getNodes() {
        return nodes;
    }

    public void setNodes( List nodes ) {
        this.nodes = nodes;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId( String parentId ) {
        this.parentId = parentId;
    }
    
    public String getClassTable() {
		return classTable;
	}

	public void setClassTable(String classTable) {
		this.classTable = classTable;
	}

	public String getClassTreeId() {
		return classTreeId;
	}

	public void setClassTreeId(String classTreeId) {
		this.classTreeId = classTreeId;
	}

	public String getClassTreeName() {
		return classTreeName;
	}

	public void setClassTreeName(String classTreeName) {
		this.classTreeName = classTreeName;
	}

	public String getClassFilter() {
		return classFilter;
	}

	public void setClassFilter(String classFilter) {
		this.classFilter = classFilter;
	}
	
	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getProArgsStr() {
		return proArgsStr;
	}

	public void setProArgsStr(String proArgsStr) {
		this.proArgsStr = proArgsStr;
	}

	public QueryManager getQueryManager() {
		return queryManager;
	}

	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getClassOrder() {
		return classOrder;
	}

	public void setClassOrder(String classOrder) {
		this.classOrder = classOrder;
	}
	public String getSelectTreeSql() {
		return selectTreeSql;
	}

	public void setSelectTreeSql(String selectTreeSql) {
		this.selectTreeSql = selectTreeSql;
	}
	public String getSupcanXMLPath() {
		return supcanXMLPath;
	}

	public void setSupcanXMLPath(String supcanXMLPath) {
		this.supcanXMLPath = supcanXMLPath;
	}

	private String supcanXMLPath;
	public String supcanXML(){
		return SUCCESS;
	}
}
