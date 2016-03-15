package com.huge.ihos.system.reportManager.search.webapp.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.huge.exceptions.BusinessException;
import com.huge.ihos.period.model.Period;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.reportManager.search.model.SearchItem;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.ihos.system.reportManager.search.model.SearchUrl;
import com.huge.ihos.system.reportManager.search.model.ViewDef;
import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.ihos.system.reportManager.search.util.SearchCriteria;
import com.huge.ihos.system.reportManager.search.util.SearchUtils;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class QueryExAction
    extends JqGridBaseAction
     {
    private String searchName;

    private ViewDef viewDef;

    private SearchOption[] searchOptions;

    private SearchItem[] searchItems;

    private SearchUrl[] searchUrls;

    private String[] searchItemInit;

    private List queryList = new ArrayList();

    private String _vKey;

    private String _vKeyTitle;

    private String _aKey;

    private String _vValues;

    private String _aValues;

    private String _valueField;

    private String _valueType;

    private QueryManager queryManager;

    private PeriodManager periodManager;

    private String tarID;

    private String tarName;

    private String gridID;

    private String gridName;

    private String random = "" + Math.round( Math.random() * 10000 );

    public String queryExList()
        throws Exception {
        try {

            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );

            // this.searchName = this.getRequest().getParameter("searchName");

            SearchCriteria cri = this.queryManager.getSearchCriteriagetSearchCriteria( getRequest(), searchName );
            pagedRequests = queryManager.getQueryCriteria( pagedRequests, cri );

            this.queryList = (List) pagedRequests.getList();
            this.queryList = processExList( this.queryList );

            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();
        }
        catch ( Exception e ) {
            log.error( "searchGridList Error", e );
        }

        return SUCCESS;
    }

    private List processExList( List realList )
        throws UnsupportedEncodingException {
        String[] vValues = this._vValues.toUpperCase().split( "," );
        for ( int i = 0; i < vValues.length; i++ ) {
            vValues[i] = vValues[i].trim();
        }
        
        
        
       // Map rowMap = new HashMap();
        Map rowMap = new LinkedHashMap();
        for ( int i = 0; i < vValues.length; i++ ) {
            Map m = new HashMap();

           // byte ptext[] = vValues[i].getBytes( "UTF-8" );
           // String value = new String( ptext, "UTF-8" );
            m.put( this._vKey.toUpperCase(), vValues[i] );
            rowMap.put( vValues[i], m );
        }
        for ( Iterator iterator = realList.iterator(); iterator.hasNext(); ) {
            Map rm = (Map) iterator.next();
            Object rkn = rm.get( this._vKey.toUpperCase() );

            Map exMap = (Map) rowMap.get( rkn );
            if ( exMap != null ) {

                exMap.put( rm.get( this._aKey.toUpperCase() ), rm.get( this._valueField.toUpperCase() ) );
            }
        }
        
       /* List list = new ArrayList();
        for ( int i = 0; i < vValues.length; i++ ) {
            list.add( rowMap.get( vValues[i] ));
        }
        */
        
        
        return new ArrayList( rowMap.values() );
     //   return list;
    }

    public String queryExSearchOptions()
        throws Exception {
        if ( this.searchName == null || this.searchName.equals( "" ) )
            throw new BusinessException( "searchName参数没有正确设置." );

        if ( this._vKey == null || this._vKey.equals( "" ) )
            throw new BusinessException( "_vKey参数没有正确设置." );
        if ( this._aKey == null || this._aKey.equals( "" ) )
            throw new BusinessException( "_aKey参数没有正确设置." );

        String searchId = this.queryManager.getSearchBySearchName( searchName ).getSearchId();
        // just for queryEx
        this.searchOptions = processExOption();

        this.searchItems = this.queryManager.getSearchItemsBySearchNameOrdered( searchId );
        this.viewDef = this.queryManager.getViewDef( searchName );
        this.searchUrls = this.queryManager.getSearchUrlsBySearchNameOrdered( searchId );
//        this.currentPeriod = this.periodManager.getCurrentPeriod().getPeriodCode();
        this.currentPeriod = this.getLoginPeriod();
        this.searchItemInit = (String[]) this.initSearchItemValue( this.getRequest(), searchItems );
        return SUCCESS;
    }

    private SearchOption[] processExOption() {
        List ol = new ArrayList();
        ol.add( manualNewOption( this._vKey, false, null ) );

        if ( this._aKey.equalsIgnoreCase( "checkPeriod" ) ) {
            String cs1 = this.getRequest().getParameter( "checkPeriod" );
            String cs2 = this.getRequest().getParameter( "checkPeriod1" );

           /* try {
                int c1 = Integer.parseInt( cs1 );
                int c2 = Integer.parseInt( cs2 );

                for ( int i = c1; i <= c2; i++ ) {
                    ol.add( manualNewOption( i + "", true ,this._valueType) );
                }

            }*/
            List<Period> l=this.periodManager.getPeriodsBetween( cs1, cs2 );
            try {
                for ( Iterator iterator = l.iterator(); iterator.hasNext(); ) {
                    Period period = (Period) iterator.next();
                    
                    ol.add( manualNewOption( period.getPeriodCode(), true ,this._valueType) );
                }
            }
            catch ( NumberFormatException e ) {
                e.printStackTrace();
            }
        }
        else {
            String[] kStr = this._aValues.split( "," );
            for ( int i = 0; i < kStr.length; i++ ) {
                ol.add( manualNewOption( kStr[i], true, this._valueType ) );
            }
        }
        SearchOption[] o = new SearchOption[ol.size()];
        o = (SearchOption[]) ol.toArray( o );
        return o;
    }

    private String[] initSearchItemValue( HttpServletRequest req, SearchItem[] sops ) {

        SearchUtils su = new SearchUtils();

        List l = new ArrayList();
        for ( int i = 0; i < sops.length; i++ ) {
            String htmlFieldName = sops[i].getHtmlField();
            String htmlFieldValue = req.getParameter( htmlFieldName );

            if ( ( htmlFieldValue != null ) && ( htmlFieldValue.length() > 0 ) ) {

                // TODO ZUO变量替换，例如：%HSQJ%
                // sb.append("&").append(htmlFieldName).append("=").append(htmlFieldValue);
                String sv = su.realUrlString( htmlFieldValue );

                l.add( htmlFieldName + "=" + sv );
                sops[i].setInitValueString( sv );
            }
        }
        String[] s = new String[l.size()];
        l.toArray( s );
        return s;
    }

    // 此方法需要改造
    private SearchOption manualNewOption( String title, boolean isValue, String valueType ) {
       /* SearchOption so = new SearchOption();
        so.setAlignType( "center" );
        so.setDisplayWidth( "10" );
        so.setEditType( "Label" );
        so.setFieldName( title );
        if ( isValue ) {
            so.setFieldType( valueType );
            // so.setFieldType( "Money" );
            so.setDisplayFormat( null );
            so.setTitle( title );
        }
        else {
            // so.setTitle( "指标名称" );
            so.setFieldType( "String" );
        }
        so.setVisible( true );

        return so;*/
        
        
        
        SearchOption so = new SearchOption();
        so.setEditType( "Label" );
        so.setFieldName( title );
        if ( isValue ) {
            //so.setFieldType( "Money" );
            so.setFieldType( valueType );
            so.setDisplayFormat( null );
            so.setTitle( title );
            so.setAlignType( "right" );
            so.setDisplayWidth( "10" );
        }
        else {
            so.setTitle( this._vKeyTitle );
//            so.setTitle( "指标名称" );
            so.setFieldType( "String" );
            so.setAlignType( "left" );
            so.setDisplayWidth( "20" );
        }
        so.setVisible( true );

        return so;
        
    }


    public List getQueryList() {
        return queryList;
    }

    public void setQueryList( List queryList ) {
        this.queryList = queryList;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName( String searchName ) {
        this.searchName = searchName;
    }

    public ViewDef getViewDef() {
        return viewDef;
    }

    public void setViewDef( ViewDef viewDef ) {
        this.viewDef = viewDef;
    }

    public SearchOption[] getSearchOptions() {
        return searchOptions;
    }

    public void setSearchOptions( SearchOption[] searchOptions ) {
        this.searchOptions = searchOptions;
    }

    public SearchItem[] getSearchItems() {
        return searchItems;
    }

    public void setSearchItems( SearchItem[] searchItems ) {
        this.searchItems = searchItems;
    }

    public SearchUrl[] getSearchUrls() {
        return searchUrls;
    }

    public void setSearchUrls( SearchUrl[] searchUrls ) {
        this.searchUrls = searchUrls;
    }

    public String[] getSearchItemInit() {
        return searchItemInit;
    }

    public void setSearchItemInit( String[] searchItemInit ) {
        this.searchItemInit = searchItemInit;
    }

    public String get_vKey() {
        return _vKey;
    }

    public void set_vKey( String _vKey ) {
        this._vKey = _vKey;
    }

    public String get_vKeyTitle() {
        return _vKeyTitle;
    }

    public void set_vKeyTitle( String _vKeyTitle ) {
        this._vKeyTitle = _vKeyTitle;
    }

    public String get_aKey() {
        return _aKey;
    }

    public void set_aKey( String _aKey ) {
        this._aKey = _aKey;
    }

    public String get_vValues() {
        return _vValues;
    }

    public void set_vValues( String _vValues ) {
        this._vValues = _vValues;
    }

    public String get_aValues() {
        return _aValues;
    }

    public void set_aValues( String _aValues ) {
        this._aValues = _aValues;
    }

    public String get_valueField() {
        return _valueField;
    }

    public void set_valueField( String _valueField ) {
        this._valueField = _valueField;
    }

    public String get_valueType() {
        return _valueType;
    }

    public void set_valueType( String _valueType ) {
        this._valueType = _valueType;
    }

    public QueryManager getQueryManager() {
        return queryManager;
    }

    public void setQueryManager( QueryManager queryManager ) {
        this.queryManager = queryManager;
    }

    public PeriodManager getPeriodManager() {
        return periodManager;
    }

    public void setPeriodManager( PeriodManager periodManager ) {
        this.periodManager = periodManager;
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

    public String getRandom() {
        return random;
    }

    public void setRandom( String random ) {
        this.random = random;
    }

}
