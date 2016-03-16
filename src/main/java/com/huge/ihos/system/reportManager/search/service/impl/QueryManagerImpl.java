package com.huge.ihos.system.reportManager.search.service.impl;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.huge.foundation.util.StringUtil;
import com.huge.ihos.excel.DataSet;
import com.huge.ihos.period.dao.PeriodDao;
import com.huge.ihos.system.reportManager.search.dao.QueryDao;
import com.huge.ihos.system.reportManager.search.dao.SearchDao;
import com.huge.ihos.system.reportManager.search.dao.SearchEntityDao;
import com.huge.ihos.system.reportManager.search.dao.SearchItemDao;
import com.huge.ihos.system.reportManager.search.dao.SearchLinkDao;
import com.huge.ihos.system.reportManager.search.dao.SearchOptionDao;
import com.huge.ihos.system.reportManager.search.dao.SearchUrlDao;
import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.model.SearchEntity;
import com.huge.ihos.system.reportManager.search.model.SearchEntityCluster;
import com.huge.ihos.system.reportManager.search.model.SearchItem;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.ihos.system.reportManager.search.model.SearchUrl;
import com.huge.ihos.system.reportManager.search.model.ViewDef;
import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.ihos.system.reportManager.search.util.LSearchBracket;
import com.huge.ihos.system.reportManager.search.util.RSearchBracket;
import com.huge.ihos.system.reportManager.search.util.SearchBoolean;
import com.huge.ihos.system.reportManager.search.util.SearchCriteria;
import com.huge.ihos.system.reportManager.search.util.SearchOperator;
import com.huge.ihos.system.reportManager.search.util.SearchUtils;
import com.huge.ihos.system.systemManager.role.dao.RoleDao;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.user.dao.UserDao;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.DateUtil;
import com.huge.util.ExcelUtil;
import com.huge.util.OtherUtil;
import com.huge.util.ReturnUtil;
import com.huge.webapp.pagers.JQueryPager;

@Service( "queryManager" )
public class QueryManagerImpl
    implements QueryManager {
    protected final Log log = LogFactory.getLog( getClass() );

    public SearchDao getSearchDao() {
        return searchDao;
    }

    @Autowired
    public void setSearchDao( SearchDao searchDao ) {
        this.searchDao = searchDao;
    }

    public SearchItemDao getSearchItemDao() {
        return searchItemDao;
    }

    @Autowired
    public void setSearchItemDao( SearchItemDao searchItemDao ) {
        this.searchItemDao = searchItemDao;
    }

    public SearchOptionDao getSearchOptionDao() {
        return searchOptionDao;
    }

    @Autowired
    public void setSearchOptionDao( SearchOptionDao searchOptionDao ) {
        this.searchOptionDao = searchOptionDao;
    }

    public SearchUrlDao getSearchUrlDao() {
        return searchUrlDao;
    }

    @Autowired
    public void setSearchUrlDao( SearchUrlDao searchUrlDao ) {
        this.searchUrlDao = searchUrlDao;
    }

    public SearchLinkDao getSearchLinkDao() {
        return searchLinkDao;
    }

    @Autowired
    public void setSearchLinkDao( SearchLinkDao searchLinkDao ) {
        this.searchLinkDao = searchLinkDao;
    }

    public QueryDao getQueryDao() {
        return queryDao;
    }

    @Autowired
    public void setQueryDao( QueryDao queryDao ) {
        this.queryDao = queryDao;
    }

    public PeriodDao getPeriodDao() {
        return periodDao;
    }

    @Autowired
    public void setPeriodDao( PeriodDao periodDao ) {
        this.periodDao = periodDao;
    }

    SearchDao searchDao;

    SearchItemDao searchItemDao;

    SearchOptionDao searchOptionDao;

    SearchUrlDao searchUrlDao;

    SearchLinkDao searchLinkDao;

    PeriodDao periodDao;

    QueryDao queryDao;

    UserDao userDao;

    SearchEntityDao searchEntityDao;

    RoleDao roleDao;

    public RoleDao getRoleDao() {
        return roleDao;
    }

    @Autowired
    public void setRoleDao( RoleDao roleDao ) {
        this.roleDao = roleDao;
    }

    public SearchEntityDao getSearchEntityDao() {
        return searchEntityDao;
    }

    @Autowired
    public void setSearchEntityDao( SearchEntityDao searchEntityDao ) {
        this.searchEntityDao = searchEntityDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao( UserDao userDao ) {
        this.userDao = userDao;
    }

    public ViewDef getViewDef( String searchName ) {
        Search search = this.searchDao.getBySearchName( searchName );
        return new ViewDef( search );
    }

    public String updateQueryList( String searchName, String id, HttpServletRequest req ) {
        Search search = this.getSearchBySearchName( searchName );
        SearchOption[] ops = getSearchEditOptionsBySearchNameOrdered( search.getSearchId() );
        String tableName = search.getFormName();
        String tableId = search.getMyKey();
        String tableIdValue = id;
        String[] param = new String[ops.length];
        String[] values = new String[ops.length];
        for ( int i = 0; i < ops.length; i++ ) {
            param[i] = ops[i].getFieldName();
            values[i] = req.getParameter( ops[i].getFieldNameUpperCase() );
        }
        String sql = "update " + tableName + " set ";
        for ( int i = 0; i < param.length; i++ )
            sql = sql + param[i] + "='" + values[i] + "',";
        sql = sql.substring( 0, sql.length() - 1 );

        sql = sql + " where " + tableId + "='" + tableIdValue + "'";
        return "";
    }

    public String updateQueryRow( String tableName, String idName, String[] fieldNames, String[] values ) {
        return this.queryDao.updateQueryRow( tableName, idName, fieldNames, values );
    }

    public Map<String, Object> getExcelQueryCriteria( SearchCriteria cri ) {
        return this.queryDao.getExcelQueryCriteria( cri );
    }

    public Map getSearchItemDefineAndValue( HttpServletRequest req, String searchName ) {
        Map map = new HashMap();
        Search search = this.searchDao.getBySearchName( searchName );

        SearchItem[] itemArray = new SearchItem[0];
        Set items = search.getSearchitems();
        if ( items.size() > 0 ) {
            itemArray = new SearchItem[items.size()];
            items.toArray( itemArray );
        }
        for ( int i = 0; i < itemArray.length; i++ ) {
            if ( !itemArray[i].isSearchFlag() )
                continue;
            String htmlFieldName = itemArray[i].getHtmlField();
            String htmlFieldValue = req.getParameter( htmlFieldName );
            if ( ( htmlFieldValue == null ) || ( htmlFieldValue.length() <= 0 ) )
                continue;
            else
            	htmlFieldName = htmlFieldName.toUpperCase();
                map.put( htmlFieldName, htmlFieldValue );
        }

        return map;

    }

    public SearchCriteria getSearchCriteriagetSearchCriteria( HttpServletRequest req, String searchName ) {
        SearchCriteria criteria = new SearchCriteria();
        Search search = this.searchDao.getBySearchName( searchName );

        SearchItem[] itemArray = this.searchItemDao.getEnabledSearchItemsBySearchIdOrdered( search.getSearchId() );
        /*
         * Set items = search.getSearchitems(); if (items.size() > 0) {
         * itemArray = new SearchItem[items.size()]; items.toArray(itemArray); }
         */
        for ( int i = 0; i < itemArray.length; i++ ) {
            if ( !itemArray[i].isSearchFlag() )
                continue;
            String htmlFieldName = itemArray[i].getHtmlField();
            String htmlFieldValue = req.getParameter( htmlFieldName );
            if ( ( htmlFieldValue == null ) || ( htmlFieldValue.length() <= 0 ) ){
            	if(itemArray[i].getMustArg()!=null&&itemArray[i].getMustArg()==true){
            		htmlFieldValue = "";
            	}else{
            		continue;
            	}
            }
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
        criteria = this.processExtraWhereSql( req, searchName, criteria );
        SearchOption[] sumOpts = this.getSearchSumOptionsBySearchNameOrdered( searchName );

        criteria.setSumOpts( sumOpts );
        System.out.println( criteria.getSourceSql().toUpperCase().trim() );
        return criteria;

    }

    private SearchCriteria processExtraWhereSql( HttpServletRequest req, String searchName, SearchCriteria cri ) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();
        User user = (User) userDao.loadUserByUsername( username );
        Set<Role> roles = user.getRoles();
        Iterator<Role> roleIt = roles.iterator();
        List rid = new ArrayList();
        while ( roleIt.hasNext() ) {
            Role r = (Role) roleIt.next();
            rid.add( r.getId() );
        }
        Long[] rida = new Long[rid.size()];
        rid.toArray( rida );// 用户的roles

        Search search = searchDao.getBySearchName( searchName );
        String entityName = search.getEntityName();
        if ( entityName != null && entityName.length() > 0 ) {
            SearchEntity entity = searchEntityDao.getSearchEntityByName( entityName );// 当前查询的实体名进而查到尸体id

            List entityClusters = roleDao.getDataPrivilegeOfRolesAndEntityid( rida, entity.getEntityId() );// 查出适用的所有尸体cluster

            String exaStr = null;// 生成额为的sql条件语句
            for ( Iterator iterator = entityClusters.iterator(); iterator.hasNext(); ) {
                SearchEntityCluster object = (SearchEntityCluster) iterator.next();
                if ( exaStr == null )
                    exaStr = "(" + object.getExpression() + ")";
                else
                    exaStr = exaStr + " or (" + object.getExpression() + ")";
            }
            if ( exaStr == null )
                exaStr = " 1=1 ";

            // 更新实际查询所需要的sql
            //String mysql = cri.getSourceSql();

            //mysql = mysql + " and ( "+exaStr+" )";
            SearchUtils su = new SearchUtils();

            exaStr = su.realSQL( exaStr );
            cri.setExtraWhereSql( exaStr );
        }
        return cri;
    }

    public JQueryPager getQueryCriteria( JQueryPager paginatedList, SearchCriteria cri ) {
        return this.getQueryDao().getQueryCriteria( paginatedList, cri );
    }

    public SearchOption[] getSearchOptionsBySearchNameOrdered( String searchId ) {
        /* Search search = this.searchDao.getBySearchName(searchName); */

        SearchOption[] opts = this.searchOptionDao.getSearchOptionsBySearchIdOrdered( searchId );
        return opts;
    }

    public SearchOption[] getSearchSumOptionsBySearchNameOrdered( String searchId ) {
        /* Search search = this.searchDao.getBySearchName(searchName); */

        SearchOption[] opts = this.searchOptionDao.getSearchSumOptionsBySearchIdOrdered( searchId );
        return opts;
    }

    public SearchOption[] getSearchAllOptionsBySearchIdOrdered( String searchId ) {
        /* Search search = this.searchDao.getBySearchName(searchName); */

        SearchOption[] opts = this.searchOptionDao.getSearchAllOptionsBySearchIdOrdered( searchId );
        return opts;
    }

    public SearchOption[] getSearchEditOptionsBySearchNameOrdered( String searchId ) {
        /* Search search = this.searchDao.getBySearchName(searchName); */

        SearchOption[] opts = this.searchOptionDao.getSearchEditOptionsBySearchIdOrdered( searchId );
        return opts;
    }

    public SearchItem[] getSearchItemsBySearchNameOrdered( String searchId ) {
        /* Search search = this.searchDao.getBySearchName(searchname); */

        SearchItem[] items = this.searchItemDao.getEnabledSearchItemsBySearchIdOrdered( searchId );
        return items;
    }

    public Search getSearchById( String searchId ) {
        return this.searchDao.get( searchId );
    }

    public Search getSearchBySearchName( String searchName ) {
        return this.searchDao.getBySearchName( searchName );
    }

    public String publicDelete( String searchName, String[] ids ) {
        Search search = this.searchDao.getBySearchName( searchName );

        return this.queryDao.deletePublic( search.getFormName(), search.getMyKey(), ids );
    }

    /*public String getCurrentPeriod() {
        return this.getPeriodDao().getCurrentPeriod().getPeriodCode();
    }*/

    public ReturnUtil publicPrecess( String taskName, Object[] args ) {
        return this.getQueryDao().processPublic( taskName, args );
    }

    public SearchUrl[] getSearchUrlsBySearchNameOrdered( String searchId ) {
        /* Search search = this.searchDao.getBySearchName(searchname); */

        SearchUrl[] opts = this.searchUrlDao.getSearchUrlsBySearchIdOrdered( searchId );
        return opts;
    }

    public String formateOptionData( String type, String formatStr, Object data ) {
        if ( this.log.isInfoEnabled() )
            this.log.info( "field type:" + type + " format String:" + formatStr + " field data:" + data );
        String formatedData = null;
        if ( data != null )
            if ( type.equalsIgnoreCase( "String" ) ) {

                if ( formatStr != null && !formatStr.equalsIgnoreCase( "" ) ) {
                    int f = Integer.parseInt( formatStr );
                    if ( ( (String) data ).length() >= f )
                        formatedData = ( (String) data ).substring( 0, f - 1 );
                    else
                        formatedData = data.toString();
                }
                else {
                    formatedData = data.toString();
                }

            }
            else if ( type.equalsIgnoreCase( "Integer" ) ) {
                if ( formatStr != null && !formatStr.equalsIgnoreCase( "" ) ) {
                    NumberFormat formatter;
                    int ind = formatStr.indexOf( "." );
                    if ( ind >= 0 )
                        formatter = new DecimalFormat( "#" );
                    else
                        formatter = new DecimalFormat( formatStr );
                    formatedData = formatter.format( data );
                }
                else {
                    formatedData = data.toString();
                }

            }
            else if ( type.equalsIgnoreCase( "Number" ) ) {
                if ( formatStr != null && !formatStr.equalsIgnoreCase( "" ) ) {
                    NumberFormat formatter = new DecimalFormat( formatStr );
                    formatedData = formatter.format( data );
                }
                else {
                    formatedData = data.toString();
                }

            }
            else if ( type.equalsIgnoreCase( "Date" ) ) {
                if ( formatStr != null && !formatStr.equalsIgnoreCase( "" ) ) {
                    SimpleDateFormat sdf = new SimpleDateFormat( formatStr );
                    formatedData = sdf.format( data );
                }
                else {
                    formatedData = data.toString();
                }
            }
            else if ( type.equalsIgnoreCase( "Money" ) ) {
                if ( formatStr != null && !formatStr.equalsIgnoreCase( "" ) ) {
                    NumberFormat formatter = new DecimalFormat( formatStr );
                    formatedData = formatter.format( data );
                }
                else {
                    formatedData = data.toString();
                }
            }
            else if ( type.equalsIgnoreCase( "Percent" ) ) {
                if ( formatStr != null && !formatStr.equalsIgnoreCase( "" ) ) {
                    String ft = "#.##'%'";
                    NumberFormat formatter;
                    int ind = formatStr.indexOf( "'%'" );
                    if ( ind >= 0 )
                        formatter = new DecimalFormat( formatStr );
                    else
                        formatter = new DecimalFormat( ft );
                    double db = Double.parseDouble( data.toString() );
                    formatedData = formatter.format( db * 100 );
                }
                else {
                    formatedData = data.toString();
                }
            }
            else if ( type.equalsIgnoreCase( "Link" ) ) {
                formatedData = data.toString();
            }

        /*
         * INSERT INTO t_dictionary_items
         * (value,displayContent,orderNo,dictionary_id) VALUES
         * ('String','String',0,28); INSERT INTO t_dictionary_items
         * (value,displayContent,orderNo,dictionary_id) VALUES
         * ('Integer','Integer',1,28); INSERT INTO t_dictionary_items
         * (value,displayContent,orderNo,dictionary_id) VALUES
         * ('Number','Number',2,28); INSERT INTO t_dictionary_items
         * (value,displayContent,orderNo,dictionary_id) VALUES
         * ('Date','Date',3,28); -- INSERT INTO t_dictionary_items
         * (value,displayContent,orderNo,dictionary_id) VALUES
         * ('Time','Time',4,28); -- INSERT INTO t_dictionary_items
         * (value,displayContent,orderNo,dictionary_id) VALUES
         * ('TimeStamp','TimeStamp',5,28); INSERT INTO t_dictionary_items
         * (value,displayContent,orderNo,dictionary_id) VALUES
         * ('Boolean','Boolean',6,28); INSERT INTO t_dictionary_items
         * (value,displayContent,orderNo,dictionary_id) VALUES
         * ('Money','Money',7,28); -- INSERT INTO t_dictionary_items
         * (value,displayContent,orderNo,dictionary_id) VALUES
         * ('Image','Image',8,28); INSERT INTO t_dictionary_items
         * (value,displayContent,orderNo,dictionary_id) VALUES
         * ('Percent','Percent',9,28);
         */

        return formatedData;
    }

    public static void main( String[] args ) {
        NumberFormat formatter = new DecimalFormat( "##'元'" );
        float f = 5.12345f;
        System.out.println( formatter.format( f ) );
        String ft = "#.##'%'";
        double db = Double.parseDouble( "5.12345" );
        formatter = new DecimalFormat( ft );
        String formatedData = formatter.format( db * 100 );
        System.out.println( formatedData );
    }

	@Override
	public String updateQueryRow(String tableName,String tableId, Map map) {
		
		String [] sqls=new String[map.size()];
		System.out.println(map.size());
		//String[] mapKeys= new String[0];// =(String[]) map.keySet().toArray();
		
		Set mapKeys = map.keySet();
		int i=0;
		for (Iterator iterator = mapKeys.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();

			String sql="update "+tableName+" set";
			
			Object[] params=(Object[]) map.get(object);
			
			for (int j = 0; j < params.length-1; j++) {
				String[] paramNV =params[j].toString().split(":");
				if(paramNV.length==2)
					sql+=" "+paramNV[0]+" = '"+paramNV[1]+"',";
				else
					sql+=" "+paramNV[0]+" = '',";
			}
			sql=OtherUtil.subStrEnd(sql, ",");
			String[] idNV=object.toString().split(":");
			sql+=" where "+tableId+" = '"+idNV[1]+"';";
			
			sqls[i]=sql;
			i++;
		}
		queryDao.updateQueryRow(sqls);
		
		return "success";
	}
	public String addQueryRow( String tableName, Map map){
		
		String sql="INSERT INTO "+tableName+" ";
		Object object=(Object)map.keySet().iterator().next();
		Object[] params=(Object[]) map.get(object);
		String column="";
		String values="";
		for (int i = 0; i < params.length-1; i++) {
			String[] paramNV=params[i].toString().split(":");
			if(paramNV.length==2){
				column+=paramNV[0]+",";
				values+="'"+paramNV[1]+"',";
			}else{
				column+=paramNV[0]+",";
				values+="'',";
			}
		}
		column=OtherUtil.subStrEnd(column, ",");
		values=OtherUtil.subStrEnd(values, ",");
		sql+="("+column+") values ("+values+")";
		
		queryDao.executeSqls(sql);
		
		return "success";
	}

	@Override
    public SearchOption[] getFormedSearchOptions( String searchId ){
    	return searchOptionDao.getFormedSearchOptions(searchId);
    }

	@Override
	public String outputExcel(String templateName,
			Map<String , Map<String, DataSet>> excelData ) {
		String outputFile = "";
		try {
			File templFile = new File(templateName);
			String templFileName = templFile.getName();
			String[] fileNameArr = templFileName.split("\\.");
			outputFile = templFile.getPath().replace(templFileName, fileNameArr[0]+DateUtil.convertDateToString("yyyyMMddhhmmss",Calendar.getInstance().getTime())+"."+fileNameArr[1]);
			ExcelUtil.writeExcelByTemplate(templateName,excelData,outputFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFile;
	}
	
	@Override
	public SearchUrl[] getSearchUrlByRight(String userId, String searchId) {
		return searchUrlDao.getSearchUrlByRight(userId, searchId);
	}
}





























