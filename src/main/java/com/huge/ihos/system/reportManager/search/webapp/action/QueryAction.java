package com.huge.ihos.system.reportManager.search.webapp.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.reportManager.chart.service.MccKeyManager;
import com.huge.ihos.system.reportManager.search.model.Header;
import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.model.SearchItem;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.ihos.system.reportManager.search.model.SearchUrl;
import com.huge.ihos.system.reportManager.search.model.ViewDef;
import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.ihos.system.reportManager.search.util.SearchCriteria;
import com.huge.ihos.system.reportManager.search.util.SearchUtils;
import com.huge.ihos.system.systemManager.dataprivilege.model.PrivilegeClass;
import com.huge.ihos.system.systemManager.dataprivilege.model.UserDataPrivilegeBean;
import com.huge.ihos.system.systemManager.dataprivilege.service.PrivilegeClassManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.taglib.select.SqlSelect;
import com.huge.webapp.taglib.select.StringSelect;
import com.huge.webapp.util.SpringContextHelper;

public class QueryAction
    extends JqGridBaseAction
     {
    private QueryManager queryManager;

    private String searchId;

    private String searchName;
    
    private String searchTitle;

	private String actionName;

    private PeriodManager periodManager;

    private MccKeyManager mccKeyManager;

    private List queryList = new ArrayList();

    private SearchOption[] searchOptions;

    private ViewDef viewDef;

    private SearchItem[] searchItems;

    private SearchUrl[] searchUrls;

    private int sumSearchOptionsNum;

    private Map userdata = new HashMap();

    private int editOptionsNum;

    private String tarID;

    private String tarName;

    private String gridID;

    private String gridName;

    private String random = "" + Math.round( Math.random() * 10000 );

    private String isSingle;

    private String headLeve = "1"; // 1,2,3层

    private String reportType = "0"; //0:queryList;1:showReport;2:tabReport

    private String chartStr;//图形配置

    private String _key;

    private String _field;

    private String keyName;
    
    private DataSource dataSource = SpringContextHelper.getDataSource();
    
    private int colNum = 1;

    public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public String get_key() {
        return _key;
    }

    public void set_key( String _key ) {
        this._key = _key;
    }

    public String get_field() {
        return _field;
    }

    public void set_field( String _field ) {
        this._field = _field;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName( String keyName ) {
        this.keyName = keyName;
    }

    public int getEditOptionsNum() {
        return editOptionsNum;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom( String random ) {
        this.random = random;
    }
    
    public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

    public String queryList()
        throws Exception {
        try {

            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );

            // this.searchName = this.getRequest().getParameter("searchName");

            SearchCriteria cri = this.queryManager.getSearchCriteriagetSearchCriteria( getRequest(), searchName );
            pagedRequests = queryManager.getQueryCriteria( pagedRequests, cri );

            this.queryList = (List) pagedRequests.getList();
            if ( this.actionName != null && this.actionName.equalsIgnoreCase( "queryEx" ) ) {
                this.queryList = processExList( this.queryList );
            }

            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();
            this.userdata = pagedRequests.getSumData();
        }
        catch ( Exception e ) {
            log.error( "searchGridList Error", e );
        }

        return SUCCESS;
    }

    public String chartList()
        throws Exception {
        try {

            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests.setPageSize(100);
            SearchCriteria cri = this.queryManager.getSearchCriteriagetSearchCriteria( getRequest(), searchName );
            pagedRequests = queryManager.getQueryCriteria( pagedRequests, cri );
            Object[] agrs = cri.getRealAgrs();
            LinkedHashSet<String> keyName = new LinkedHashSet<String>();
            for(int i=3;i<agrs.length;i++){
            	keyName.add(agrs[i].toString());
            }
            this.queryList = (List) pagedRequests.getList();
            chartStr = mccKeyManager.zheXianSearch( queryList ,searchTitle,keyName);

        }
        catch ( Exception e ) {
            log.error( "searchGridList Error", e );
        }

        return "baseChartS";
    }

    /*public String queryChartEx(){
    	
    }*/
    public String queryDefinePreview()
        throws Exception {

        this.searchName = this.queryManager.getSearchById( this.searchId ).getSearchName();

        return queryList();

    }

    private String[] searchItemInit;

    public String[] getSearchItemInit() {
        return searchItemInit;
    }

    /*
     * public void setSearchItemInit(Map searchItemInit) { this.searchItemInit =
     * searchItemInit; }
     */

    private List processExList( List realList )
        throws UnsupportedEncodingException {
        String[] keys = this._key.toUpperCase().split( "," );
        String[] kn = this.keyName.toUpperCase().split( "," );
        //List l = new ArrayList( kn.length );
        Map lm = new HashMap();
        for ( int i = 0; i < kn.length; i++ ) {
            Map m = new HashMap();

            byte ptext[] = kn[i].getBytes( "UTF-8" );
            String value = new String( ptext, "UTF-8" );
            m.put( "keyName".toUpperCase(), value );
            lm.put( kn[i], m );
        }
        //SearchOption[] so = this.processExOption();

        for ( Iterator iterator = realList.iterator(); iterator.hasNext(); ) {
            Map rm = (Map) iterator.next();
            Object rkn = rm.get( keys[0] );

            Map exMap = (Map) lm.get( rkn );
            if ( exMap != null ) {

                exMap.put( rm.get( keys[1] ), rm.get( this._field.toUpperCase() ) );
            }
        }
        return new ArrayList( lm.values() );
    }

    private SearchOption[] processExOption() {
        String[] kStr = this._key.split( "," );

        List ol = new ArrayList();
        ol.add( manualNewOption( kStr[0], false ) );

        String cs1 = this.getRequest().getParameter( "checkPeriod" );
        String cs2 = this.getRequest().getParameter( "checkPeriod1" );

        try {
            int c1 = Integer.parseInt( cs1 );
            int c2 = Integer.parseInt( cs2 );

            for ( int i = c1; i <= c2; i++ ) {
                ol.add( manualNewOption( i + "", true ) );
            }

        }
        catch ( NumberFormatException e ) {
            e.printStackTrace();
        }
        SearchOption[] o = new SearchOption[ol.size()];
        o = (SearchOption[]) ol.toArray( o );
        return o;
    }

    private SearchOption manualNewOption( String title, boolean isValue ) {
        SearchOption so = new SearchOption();
        so.setDisplayWidth( "10" );
        so.setEditType( "Label" );
        so.setFieldName( title );
        if ( isValue ) {
            so.setFieldType( "Money" );
            so.setDisplayFormat( null );
            so.setTitle( title );
            so.setAlignType( "right" );
        }
        else {
            so.setTitle( "指标名称" );
            so.setFieldType( "String" );
            so.setAlignType( "left" );
        }
        so.setVisible( true );

        return so;
    }

    PrivilegeClassManager privilegeClassManager;
	public void setPrivilegeClassManager(PrivilegeClassManager privilegeClassManager) {
		this.privilegeClassManager = privilegeClassManager;
	}

	String jzStatusStr;
	public String getJzStatusStr() {
		return jzStatusStr;
	}

	public void setJzStatusStr(String jzStatusStr) {
		this.jzStatusStr = jzStatusStr;
	}

	public String querySearchOptions()
        throws Exception {
    	String periodCode = this.getLoginPeriod();
    	if(periodCode == null){
    		return "noPeriod";
    	}
    	Search search = this.queryManager.getSearchBySearchName( searchName );
        String searchId = search.getSearchId();
        
        searchTitle = search.getTitle();
        if ( this.actionName != null && this.actionName.equalsIgnoreCase( "queryEx" ) ) {

            this.searchOptions = processExOption();

        }
        else {
            this.searchOptions = initSelectSearchOptions( this.queryManager.getSearchOptionsBySearchNameOrdered( searchId ) );

        }
        this.searchItems = this.queryManager.getSearchItemsBySearchNameOrdered( searchId );
        for(SearchItem searchItem : searchItems){
        	if("dpSelect".equals(searchItem.getUserTag())||"dpSelectR".equals(searchItem.getUserTag())){
        		String dpClassCode = searchItem.getParam1();
        		PrivilegeClass privilegeClass = privilegeClassManager.get(dpClassCode);
        		if(privilegeClass!=null){
        			Set<UserDataPrivilegeBean> userDataPrivilegeBeans = UserContextUtil.findUserDataPrivilege(dpClassCode);
        			String dpStr = "";
        			for(UserDataPrivilegeBean udp : userDataPrivilegeBeans){
        				dpStr += udp.getItem()+","+udp.getItemName()+";";
        			}
        			if(!"".equals(dpStr)){
        				dpStr = OtherUtil.subStrEnd(dpStr, ";");
        			}
        			if("dpSelect".equals(searchItem.getUserTag())){
        				searchItem.setUserTag("stringSelect");
        			}else{
        				searchItem.setUserTag("stringSelectR");
        			}
        			searchItem.setParam1(dpStr);
        		}
        	}
        }

        this.viewDef = this.queryManager.getViewDef( searchName );
        User user = this.getSessionUser();
        this.searchUrls = this.searchUrls = this.queryManager.getSearchUrlByRight(""+user.getId(), searchId);
        String modelSql = "SELECT model.code,menu.menuName FROM sy_model model LEFT JOIN t_menu menu ON model.menuId=menu.menuId";
        //queryManager.
        JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" ) );
        List<Map<String, Object>> modelList = jt.queryForList(modelSql);
        Map<String, String> modelMap = new HashMap<String, String>();
        for(Map<String, Object> model : modelList){
        	Object code = model.get("code");
        	Object menuName = model.get("menuName");
        	modelMap.put(code.toString(), menuName.toString());
        }
        Map<String, String> jzStatus = new HashMap<String, String>(); 
        for(SearchUrl searchUrl :this.searchUrls){
        	String url = searchUrl.getUrl();
        	String[] urlArr = url.split("\\(");
        	String jzSystem = searchUrl.getJzSystem();
        	if(urlArr.length<2||jzSystem==null||"".equals(jzSystem)){
        		continue;
        	}else{
        		String[] systemArr = jzSystem.split(",");
           		for(String system : systemArr){
        			boolean jz = UserContextUtil.isModuleClosed(system,"月",UserContextUtil.getLoginPeriod());
        			if(jz){
        				String nemuname = modelMap.get(system);
        				jzStatus.put(urlArr[0], nemuname);
        				break;
        			}
        		}
        	}
        }
        jzStatusStr = JSONObject.fromObject(jzStatus).toString();
        this.sumSearchOptionsNum = this.queryManager.getSearchSumOptionsBySearchNameOrdered( searchId ).length;
//        this.currentPeriod = this.periodManager.getCurrentPeriod().getPeriodCode();
        this.currentPeriod =  this.getLoginPeriod();
        this.editOptionsNum = this.queryManager.getSearchEditOptionsBySearchNameOrdered( searchId ).length;
        this.initMultiHeaders( searchOptions );

        this.searchItemInit = (String[]) this.initSearchItemValue( this.getRequest(), searchItems );

        if ( this.actionName == null )

            return SUCCESS;
        else if ( this.actionName.equalsIgnoreCase( "querySelectSingle" ) ) {
            return "selectSingle";

        }
        else if ( this.actionName.equalsIgnoreCase( "querySelectMulti" ) ) {
            return "selectMulti";
        }
        return SUCCESS;
    }

    private SearchOption[] initSelectSearchOptions( SearchOption[] sos ) {
        for ( int i = 0; i < sos.length; i++ ) {
            SearchOption so = sos[i];
            String[][] opts = null;
            if ( so.getEditType().startsWith( "stringSelect" ) ) {
                opts = StringSelect.to2Array( so.getParam1() );

            }
            else if ( so.getEditType().startsWith( "sqlSelect" ) ) {
                opts = SqlSelect.to2Array( so.getParam1() );

            }
            if ( opts != null && opts.length > 0 )
                if ( !so.getEditType().endsWith( "R" ) ) {
                    so.setParam2( this.getJqgridInitSelectString( opts, true ) );
                }
                else {
                    so.setParam2( this.getJqgridInitSelectString( opts, false ) );

                }
        }

        return sos;
    }

    private String getJqgridInitSelectString( String[][] opts, boolean isRequire ) {
        String str = "";
        if ( isRequire )
            str = " : ;";

        for ( int i = 0; i < opts.length; i++ ) {
            str = str + opts[i][0] + ":" + opts[i][1];
            if ( i < opts.length - 1 )
                str = str + ";";
        }

        return str;

    }

    private String[] initSearchItemValue( HttpServletRequest req, SearchItem[] sops ) {

        SearchUtils su = new SearchUtils();

        List l = new ArrayList();
        for ( int i = 0; i < sops.length; i++ ) {
            String htmlFieldName = sops[i].getHtmlField();
            String userTag = sops[i].getUserTag();
            String htmlFieldValue = req.getParameter( htmlFieldName );

            if ( ( htmlFieldValue != null ) && ( htmlFieldValue.length() > 0 ) ) {

                // TODO ZUO变量替换，例如：%HSQJ%
                // sb.append("&").append(htmlFieldName).append("=").append(htmlFieldValue);
                String sv = su.realUrlString( htmlFieldValue );

                l.add( htmlFieldName + "=" + sv );
                sops[i].setInitValueString( sv );
            }else if("stringSelectR".equals(userTag)){
            	String stringSelect = sops[i].getParam1();
            	String[] ss = stringSelect.split(";");
            	if(ss.length>0){
            		l.add( htmlFieldName + "=" + ss[0] );
                    sops[i].setInitValueString( ss[0] );
            	}
            }else if("sqlSelectR".equals(userTag)){
            	String sqlString = sops[i].getParam1();
            	JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" ) );

                sqlString = sqlString.replaceAll( "&#039;", "'" ).replaceAll( "&#034;", "'" );

                SearchUtils suTemp = new SearchUtils();
                
                sqlString = suTemp.realSQL( sqlString );
                
                SqlRowSet rs = jt.queryForRowSet( sqlString );
                //ArrayList l1 = new ArrayList();
                //ArrayList l2 = new ArrayList();
                if(rs.next()){
                	String selectSqlValue = rs.getString( 1 );
                	
                	l.add( htmlFieldName + "=" + selectSqlValue );
                    sops[i].setInitValueString( selectSqlValue );
                }
               /* while ( rs.next() ) {

                    l1.add( rs.getString( 1 ) );
                    l2.add( rs.getString( 2 ) );
                }*/
            }
        }
        String[] s = new String[l.size()];
        l.toArray( s );
        return s;
    }

    private List formatPageList( List srcList, SearchOption[] ops ) {
        /*
         * for (Iterator iterator = srcList.iterator(); iterator.hasNext();) {
         * Map row = (Map) iterator.next();
         * 
         * for (int i = 0; i < ops.length; i++) { SearchOption sp = ops[i];
         * 
         * String ft = sp.getFieldType(); String df = sp.getDisplayFormat();
         * Object data = row.get(sp.getFieldName()); // if (df != null &&
         * df.equals("")) { if (!ft.equalsIgnoreCase("Boolean")) { String
         * formatedData = this.queryManager.formateOptionData(ft, df, data);
         * row.put(sp.getFieldName(), formatedData); } // }
         * 
         * }
         * 
         * }
         */

        return srcList;
    }

    public String getIsSingle() {
        return isSingle;
    }

    public void setIsSingle( String isSingle ) {
        this.isSingle = isSingle;
    }

    public String getTarID() {
        return tarID;
    }

    public void setTarID( String tarID ) {
        this.tarID = tarID;
    }

    public String getTarName() {
        return tarName;
    }

    public void setTarName( String tarName ) {
        this.tarName = tarName;
    }

    public String getGridID() {
        return gridID;
    }

    public void setGridID( String gridID ) {
        this.gridID = gridID;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName( String gridName ) {
        this.gridName = gridName;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId( String searchId ) {
        this.searchId = searchId;
    }

    public SearchUrl[] getSearchUrls() {
        return searchUrls;
    }

    public void setSearchUrls( SearchUrl[] searchUrls ) {
        this.searchUrls = searchUrls;
    }

    public SearchItem[] getSearchItems() {
        return searchItems;
    }

    public void setSearchItems( SearchItem[] searchItems ) {
        this.searchItems = searchItems;
    }

    public ViewDef getViewDef() {
        return viewDef;
    }

    public void setViewDef( ViewDef viewDef ) {
        this.viewDef = viewDef;
    }

    public int getSumSearchOptionsNum() {
        return sumSearchOptionsNum;
    }

    public void setSumSearchOptionsNum( int sumSearchOptionsNum ) {
        this.sumSearchOptionsNum = sumSearchOptionsNum;
    }

    public SearchOption[] getSearchOptions() {
        return searchOptions;
    }

    public void setSearchOptions( SearchOption[] searchOptions ) {
        this.searchOptions = searchOptions;
    }

    public Map getUserdata() {
        return userdata;
    }

    public void setUserdata( Map userdata ) {
        this.userdata = userdata;
    }

    public List getQueryList() {
        return queryList;
    }

    public void setQueryList( List queryList ) {
        this.queryList = queryList;
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



    public void setPeriodManager( PeriodManager periodManager ) {
        this.periodManager = periodManager;
    }

    private Header[] secondHeaders;

    private Header[] thirdHeaders;

    public Header[] getSecondHeaders() {
        return secondHeaders;
    }

    public Header[] getThirdHeaders() {
        return thirdHeaders;
    }

    private void initMultiHeaders( SearchOption[] searchOptions ) {
        if ( searchOptions != null ) {
            List<Header> sl = new ArrayList();
            List<Header> tl = new ArrayList();
            Header th = null;
            Header sh = null;
            for ( int i = 0; i < searchOptions.length; i++ ) {
                SearchOption so = searchOptions[i];
                /*if(!so.isVisible()){
                	continue;
                }*/
                if ( so.getHeaderLevel() > 1 ) {
                    if ( so.getHeaderLevel() == 3 ) {

                        if ( th == null ) {
                            th = new Header();
                            th.setHeaderTitleText( so.getHeaders()[so.getHeaderLevel() - 3] );
                            th.setNumberOfColumns( 1 );
                            th.setStartColumnName( so.getFieldNameUpperCase() );
                            th.setIndexOfCol(so.getOorder());
                            tl.add( th );
                        }
                        else {
                            if ( th.getHeaderTitleText().equalsIgnoreCase( so.getHeaders()[so.getHeaderLevel() - 3] ) ) {
                                th.setNumberOfColumns( th.getNumberOfColumns() + 1 );
                            }
                            else {
                                Header t = new Header();

                                t.setHeaderTitleText( so.getHeaders()[so.getHeaderLevel() - 3] );
                                t.setNumberOfColumns( 1 );
                                t.setStartColumnName( so.getFieldNameUpperCase() );
                                t.setIndexOfCol(so.getOorder());
                                tl.add( t );
                                th = t;
                            }
                        }

                    }

                }
                else {
                    continue;
                }

            }

            for ( int i = 0; i < searchOptions.length; i++ ) {
                SearchOption so = searchOptions[i];
                /*if(!so.isVisible()){
                	continue;
                }*/
                if ( so.getHeaderLevel() > 1 ) {
                    if ( so.getHeaderLevel() >= 2 ) {

                        if ( sh == null ) {
                            sh = new Header();
                            sh.setHeaderTitleText( so.getHeaders()[so.getHeaderLevel() - 2] );
                            sh.setNumberOfColumns( 1 );
                            sh.setStartColumnName( so.getFieldNameUpperCase() );
                            sh.setIndexOfCol(so.getOorder());
                            sl.add( sh );
                        }
                        else {
                            if ( sh.getHeaderTitleText().equalsIgnoreCase( so.getHeaders()[so.getHeaderLevel() - 2] ) ) {
                                sh.setNumberOfColumns( sh.getNumberOfColumns() + 1 );
                            }
                            else {
                                Header t = new Header();

                                t.setHeaderTitleText( so.getHeaders()[so.getHeaderLevel() - 2] );
                                t.setNumberOfColumns( 1 );
                                t.setStartColumnName( so.getFieldNameUpperCase() );
                                t.setIndexOfCol(so.getOorder());
                                sl.add( t );
                                sh = t;
                            }
                        }

                    }

                }
                else {
                    continue;
                }

            }
            List<Header> sl2 = new ArrayList<Header>();
            for(Header header : sl){
            	if(tl==null||tl.size()==0){
            		sl2 = sl;
            		break;
            	}
            	boolean thirdFlag = false;
            	for(Header headerThird : tl){
            		if(header.getHeaderTitleText().equals(headerThird.getHeaderTitleText())){
            			if(header.getIndexOfCol()<headerThird.getIndexOfCol()){
            				headerThird.setStartColumnName(header.getStartColumnName());
            			}
            			headerThird.setNumberOfColumns(headerThird.getNumberOfColumns()+header.getNumberOfColumns());
            			thirdFlag = true;
            			break;
            		}
            	}
            	if(!thirdFlag){
            		sl2.add(header);
            	}
            }
            secondHeaders = new Header[sl2.size()];
            thirdHeaders = new Header[tl.size()];
            sl2.toArray( secondHeaders );
            tl.toArray( thirdHeaders );
            if ( thirdHeaders != null && thirdHeaders.length != 0 ) {
                headLeve = "3";
            }
            else if ( secondHeaders != null && secondHeaders.length != 0 ) {
                headLeve = "2";
            }
        }
    }

    public String addDataByForm(){
    	try {
    		String tableName = null;
    		String tableId = null;
			if(searchName!=null){
				Search search = this.queryManager.getSearchBySearchName( searchName );
		        String searchId = search.getSearchId();
				searchOptions = this.queryManager.getFormedSearchOptions(searchId);
				String selectSql = "";
				List<Map<String, Object>> rs = null;
				Map<String, Object> rs0 = null;
				if(id!=null&&!id.equals("")){
					tableName = search.getFormName();
					tableId = search.getMyKey();
					selectSql = "select * from "+tableName+" where "+tableId+"='"+id+"'";
					JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
		            //jtl.execute(selectSql);
					rs = jtl.queryForList(selectSql);
					if(rs!=null&&rs.size()>0){
						rs0 = rs.get(0);
						for(SearchOption searchOption : searchOptions){
							String field = searchOption.getTableField();
							Object v = rs0.get(field);
							if(v!=null){
								searchOption.setTableFieldValue(v.toString());
								if("autocomplete".equals(searchOption.getUserTag())){
									String param2 = searchOption.getParam2();
									if(param2!=null&&!param2.equals("")){
										param2 = param2.replace("%q%", "%"+v.toString()+"%");
										rs = jtl.queryForList(param2);
										if(rs!=null&&rs.size()>0){
											rs0 = rs.get(0);
											Object name = rs0.get("name");
											if(name!=null){
												searchOption.setTableFieldNameValue(name.toString());
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
    }
    
    public String saveQueryData(){
    	String returnMessage = null;
    	try {
    		String tableName = null;
    		String tableId = null;
    		//String tableName = null;
    		if(searchName!=null){
				Search search = this.queryManager.getSearchBySearchName( searchName );
				tableName = search.getFormName();
				tableId = search.getMyKey();
		        //String searchId = search.getSearchId();
				//searchOptions = this.queryManager.getFormedSearchOptions(searchId);
    		}
			HttpServletRequest request= this.getRequest();
			String queryFormData = request.getParameter("queryFormData");
			JSONObject form = JSONObject.fromObject(queryFormData);
			Iterator<String> fit = form.keys();
			Map<String, Map<String, String>> rsmap = new HashMap<String, Map<String,String>>();
			Map<String, String> tablemap = new HashMap<String, String>();
			Map<String, String> keymap = new HashMap<String, String>();
			String insertSql = "";
			String columStr = "";
			String valueStr = "(";
			if(this.isEntityIsNew()){
				returnMessage = "添加成功！";
				columStr = "(";
			}else{
				returnMessage = "修改成功！";
			}
			while (fit.hasNext()) {
				String key = (String) fit.next();
				String v = form.getString(key);
				//String[] keyArr = key.split("\\.");
				if(key.equals("addFilters")){
					continue;
				}
				if(this.isEntityIsNew()){
					columStr += key+",";
					valueStr += "'"+v+"',";
				}else{
					columStr += key+"='"+v+"',";
				}
				
				/*Map<String, String> fmap = rsmap.get(keyArr[0]);
				if(fmap==null){
					fmap = new HashMap<String, String>();
				}
				tablemap.put(keyArr[0], keyArr[2]);
				keymap.put(keyArr[0], keyArr[3]);
				
				fmap.put(keyArr[1], v);
				rsmap.put(keyArr[0], fmap);*/
			}
			if(!"(".equals(columStr)){
				columStr = columStr.substring(0, columStr.length()-1);
				if(this.isEntityIsNew()){
					columStr += ")"; 
				}
			}
			if(!"(".equals(valueStr)){
				valueStr = valueStr.substring(0, valueStr.length()-1);
				valueStr += ")"; 
			}
			if(this.isEntityIsNew()&&!"(".equals(columStr)&&!"(".equals(valueStr)){
				insertSql = "INSERT INTO "+tableName+" " + columStr + " VALUES  " + valueStr;
			}else if(!this.isEntityIsNew()&&id!=null&&!"(".equals(columStr)){
				insertSql = "update "+tableName+" set " + columStr + " where "+ tableId +"='"+id+"'";
			}
			
			System.out.println(insertSql);
			JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
            jtl.execute(insertSql);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,e.getMessage(),false);
		}
		return ajaxForward(returnMessage);
    }
    
    public String getHeadLeve() {
        return headLeve;
    }

    public void setHeadLeve( String headLeve ) {
        this.headLeve = headLeve;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType( String reportType ) {
        this.reportType = reportType;
    }

    public void setMccKeyManager( MccKeyManager mccKeyManager ) {
        this.mccKeyManager = mccKeyManager;
    }

    public String getChartStr() {
        return chartStr;
    }

    public void setChartStr( String chartStr ) {
        this.chartStr = chartStr;
    }
}
