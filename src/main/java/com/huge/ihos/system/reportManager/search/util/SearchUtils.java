package com.huge.ihos.system.reportManager.search.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;

import com.huge.foundation.common.AppException;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.HaspDogHandler;
import com.huge.util.javabean.MapUtils;
import com.huge.webapp.util.SpringContextHelper;

public class SearchUtils {
	
	
	
	public final String SYS_ORGNAME_KEY = "orgName";
    public final String SYS_CURRENTPERIOD_KEY = "currentPeriod";

    private final String HSQJ_BEGIN = "hsqj_begin";

    private final String HSQJ_END = "hsqj_end";

    private final String HSQJ_YEAR = "hsqj_year";

    private final String HSQJ_MONTH = "hsqj_month";
    private final String HSQJ_QC = "hsqj_qc";
    
    

    private final String DC_CJQJ_KEY = "cjPeriod";

    private final String CJQJ_BEGIN = "cjqj_begin";

    private final String CJQJ_END = "cjqj_end";

    public final String USER_USERNAME_KEY = "userName";
    
    public final String USER_USERID_KEY = "userId";

    public final String USER_PERSONNAME_KEY = "personName";

    public final String USER_PERSONID_KEY = "personId";

    public final String USER_DEPTNAME_KEY = "deptName";

    public final String USER_DEPTID_KEY = "deptId";

    public final String USER_DEPTCODE_KEY = "deptCode";

    public final String SEARCH_STARTCHECKPERIOD_KEY = "CHECKPERIOD";

    public final String SEARCH_ENDCHECKPERIOD_KEY = "CHECKPERIOD1";
    
    private final String MONTH_BEGIN = "month_begin";
    private final String MONTH_END = "month_end";

    /*
     * private String[] searchPro_keys = {}; private String[] userPro_keys = {"userName","personName"}; private String[]
     * sysPro_keys = {};
     */
    private Map systemProperties;

    private Map userProperties;

    private Map searchItemProperties;

    public Map getSystemPropertiesMap() {
        return this.systemProperties;
    }

    public Map getUserPropertiesMap() {
        return this.userProperties;
    }

    public Map getSearchProperitesMap() {
        return this.searchItemProperties;
    }

    public SearchUtils() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        this.systemProperties = this.initSystemProperties();
        this.userProperties = this.initUserProperties( user );
    }

    public SearchUtils( HttpServletRequest req, String searchName ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        QueryManager queryManager = (QueryManager) SpringContextHelper.getBean( "queryManager" );
        Map map = queryManager.getSearchItemDefineAndValue( req, searchName );

        this.searchItemProperties = this.initSearchItemProperties( map );
        this.systemProperties = this.initSystemProperties();
        this.userProperties = this.initUserProperties( user );

    }

    /**
     * @param src
     * @return
     */
    private String replaceVarToValue( String src ) {
		Set<Entry<String, String>> entrySet = systemProperties.entrySet();
		Iterator<Entry<String, String>> entryIt = entrySet.iterator();
		while(entryIt.hasNext()){
			Entry<String, String> entry = entryIt.next();
			String key = entry.getKey();
			String value = entry.getValue();
			try {
				src = src.replace(key, value);
			} catch (Exception e) {
			}
		}
        /*HashMap map = new HashMap();
        map.putAll( this.systemProperties );
        map.putAll( userProperties );
        if ( src.indexOf( "%HSQJ%" ) >= 0 )
            src = StringUtil.replaceString( src, "%HSQJ%", (String) map.get( SYS_CURRENTPERIOD_KEY ) );
        if ( src.indexOf( "%HSQJ_BEGIN%" ) >= 0 )
            src = StringUtil.replaceString( src, "%HSQJ_BEGIN%", (String) map.get( HSQJ_BEGIN ) );
        if ( src.indexOf( "%HSQJ_END%" ) >= 0 )
            src = StringUtil.replaceString( src, "%HSQJ_END%", (String) map.get( HSQJ_END ) );
        if ( src.indexOf( "%HSQJ_YEAR%" ) >= 0 )
            src = StringUtil.replaceString( src, "%HSQJ_YEAR%", (String) map.get( HSQJ_YEAR ) );
        if ( src.indexOf( "%HSQJ_MONTH%" ) >= 0 )
            src = StringUtil.replaceString( src, "%HSQJ_MONTH%", (String) map.get( HSQJ_MONTH ) );
        if ( src.indexOf( "%HSQJ_QC%" ) >= 0 )
        	src = StringUtil.replaceString( src, "%HSQJ_QC%", (String) map.get( HSQJ_MONTH ) );
        
        
        
        

        if ( src.indexOf( "%CJQJ%" ) >= 0 )
            src = StringUtil.replaceString( src, "%CJQJ%", (String) map.get( DC_CJQJ_KEY ) );
        if ( src.indexOf( "%CJQJ_BEGIN%" ) >= 0 )
            src = StringUtil.replaceString( src, "%CJQJ_BEGIN%", (String) map.get( CJQJ_BEGIN ) );
        if ( src.indexOf( "%CJQJ_END%" ) >= 0 )
            src = StringUtil.replaceString( src, "%CJQJ_END%", (String) map.get( CJQJ_END ) );
        if ( src.indexOf( "%MONTH_BEGIN%" ) >= 0 )
            src = StringUtil.replaceString( src, "%MONTH_BEGIN%", (String) map.get( MONTH_BEGIN ) );
        if ( src.indexOf( "%MONTH_END%" ) >= 0 )
            src = StringUtil.replaceString( src, "%MONTH_END%", (String) map.get( MONTH_END ) );
        if ( src.indexOf( "%PERSON_ID%" ) >= 0 )
            src = StringUtil.replaceString( src, "%PERSON_ID%", (String) map.get( USER_PERSONID_KEY ) );
        if ( src.indexOf( "%DEPT_ID%" ) >= 0 )
            src = StringUtil.replaceString( src, "%DEPT_ID%", (String) map.get( USER_DEPTID_KEY ) );
        if ( src.indexOf( "%DEPT_CODE%" ) >= 0 )
            src = StringUtil.replaceString( src, "%DEPT_CODE%", (String) map.get( USER_DEPTCODE_KEY ) );
        if ( src.indexOf( "%DEPT_NAME%" ) >= 0 )
            src = StringUtil.replaceString( src, "%DEPT_NAME%", (String) map.get( USER_DEPTNAME_KEY ) );
        if ( src.indexOf( "%JJFGKS%" ) >= 0 ) {
            //JjDeptMapManager jm = (JjDeptMapManager) SpringContextHelper.getBean( "jjDeptMapManager" );
        	//TODO 添加部门数据权限控制

            //src = StringUtil.replaceString( src, "%JJFGKS%", jm.getDeptIdInS( (String) map.get( USER_PERSONID_KEY ) ) );
        }*/
        /*
         * if (str.indexOf("%DEPT_CODE%") >= 0) str = StringUtil.replaceString(str, "%DEPT_CODE%",
         * paramUserProp.getDept().getDeptCode()); if (str.indexOf("%DEPT_ID%") >= 0) str =
         * StringUtil.replaceString(str, "%DEPT_ID%", paramUserProp.getDept().getId()); if (str.indexOf("%DEPT_NAME%")
         * >= 0) str = StringUtil.replaceString(str, "%DEPT_NAME%", paramUserProp.getDept().getName()); if
         * (str.indexOf("%ORG_CODE%") >= 0) str = StringUtil.replaceString(str, "%ORG_CODE%",
         * paramUserProp.getOrg().getOrgCode()); if (str.indexOf("%ORG_NAME%") >= 0) str = StringUtil.replaceString(str,
         * "%ORG_NAME%", paramUserProp.getOrg().getName()); if (str.indexOf("%ORG_ID%") >= 0) str =
         * StringUtil.replaceString(str, "%ORG_ID%", paramUserProp.getOrg().getId());
         */
        /*
         * if (str.indexOf("%NONO_OLD%") >= 0) str = StringUtil.replaceString(str, "%NONO_OLD%",
         * (String)paroTable.get("NONO_OLD")); if (str.indexOf("%NONO_NEW%") >= 0) str = StringUtil.replaceString(str,
         * "%NONO_NEW%", (String)paroTable.get("NONO_NEW"));
         */
        return src;

    }

    public String realSQL( String srcSql ) {
        return this.replaceVarToValue( srcSql );
    }

    public String realUrlString( String srcUrl ) {
        return this.replaceVarToValue( srcUrl );
    }

    private Map initUserProperties( User user ) {
        Map map = new HashMap();
        map.put( this.USER_USERNAME_KEY, user.getUsername() );
        map.put( this.USER_USERID_KEY, ""+user.getId() );
        try {
			map.put(SYS_ORGNAME_KEY,  HaspDogHandler.getInstance().getDogService().getHospitalName());
		} catch (AppException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
        if ( user.getPerson() != null ) {
            map.put( this.USER_PERSONID_KEY, user.getPerson().getPersonId() );
            map.put( this.USER_PERSONNAME_KEY, user.getPerson().getName() );
            if ( user.getPerson().getDepartment() != null ) {
                map.put( this.USER_DEPTNAME_KEY, user.getPerson().getDepartment().getName() );
                map.put( this.USER_DEPTID_KEY, user.getPerson().getDepartment().getDepartmentId() );
                map.put( this.USER_DEPTCODE_KEY, user.getPerson().getDepartment().getDeptCode() );
            }

        }
        else {
            map.put( this.USER_PERSONID_KEY, "" );
            map.put( this.USER_PERSONNAME_KEY, "" );
            map.put( this.USER_DEPTNAME_KEY, "" );
            map.put( this.USER_DEPTID_KEY, "" );
            map.put( this.USER_DEPTCODE_KEY, "" );
        }

        return map;
    }

    private Map initSearchItemProperties( Map searchMap ) {
        Map map = new HashMap();
        if ( searchMap.containsKey( SEARCH_STARTCHECKPERIOD_KEY ) ) {
            String cpValue = (String) searchMap.get( SEARCH_STARTCHECKPERIOD_KEY );
            searchMap.put( "STARTCYEAR", cpValue.substring( 0, 4 ) );
            searchMap.put( "STARTCMONTH", cpValue.substring( 4, 6 ) );
        }
        if ( searchMap.containsKey( SEARCH_ENDCHECKPERIOD_KEY) ) {
            String cpValue = (String) searchMap.get( SEARCH_ENDCHECKPERIOD_KEY );
            searchMap.put( "ENDCYEAR", cpValue.substring( 0, 4 ) );
            searchMap.put( "ENDCMONTH", cpValue.substring( 4, 6 ) );
        }

        return searchMap;
    }

    /*
     * private Map initDCCJProperties( Map dccjMap ) { Map map = new HashMap(); if ( dccjMap.containsKey(
     * DC_START_CJQJ_KEY ) ) { String cpValue = (String) dccjMap.get( DC_START_CJQJ_KEY ); dccjMap.put( "STARTCYEAR",
     * cpValue.substring( 0, 4 ) ); dccjMap.put( "STARTCMONTH", cpValue.substring( 4, 6 ) ); } if ( dccjMap.containsKey(
     * SEARCH_ENDCHECKPERIOD_KEY ) ) { String cpValue = (String) dccjMap.get( SEARCH_ENDCHECKPERIOD_KEY ); dccjMap.put(
     * "ENDCYEAR", cpValue.substring( 0, 4 ) ); dccjMap.put( "ENDCMONTH", cpValue.substring( 4, 6 ) ); } return dccjMap;
     * }
     */

    private Map initSystemProperties() {
        Map map = new HashMap();
        UserContext userContext = UserContextUtil.getUserContext();
        map = MapUtils.mapObjToMapStr(userContext.getSysVars());
        
        
//        Period cp = pm.getCurrentPeriod();
        //TODO 替换RequestContext需要整体检查
       /* String periodCode = UserContextUtil.getUserContext().getPeriodMonth();
        PeriodMonth cp = null;//pm.getPeriodByCode(periodCode);

        
         * private final String HSQJ_BEGIN = "hsqj_begin"; private final String HSQJ_END = "hsqj_end"; private final
         * String DC_CJQJ_KEY = "cjPeriod"; private final String CJQJ_BEGIN = "cjqj_begin"; private final String
         * CJQJ_END = "cjqj_end";
         

        map.put( SYS_CURRENTPERIOD_KEY, cp.getPeriodMonthCode());

        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );

        // searchMap.put( "STARTCYEAR", cpValue.substring( 0, 4 ) );
        map.put( HSQJ_BEGIN, sdf.format( cp.getBeginDate() ) );
        map.put( HSQJ_END, sdf.format( cp.getEndDate() ) );
        map.put( HSQJ_YEAR, cp.getPeriodYear().getPeriodYearCode() );
        map.put( HSQJ_MONTH, cp.getMonth() );
        map.put( HSQJ_QC, cp.getPeriodYear().getPeriodYearCode()+"01" );
        
        Calendar  calendar =Calendar.getInstance();//获取当前日期 
		//calendar.add(Integer.parseInt(cp.getCmonth()), -1);
        calendar.set(Calendar.YEAR, Integer.parseInt(cp.getPeriodYear().getPeriodYearCode()));
		calendar.set(Calendar.MONTH,cp.getMonth()-1);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		
        map.put( MONTH_BEGIN, sdf.format( calendar.getTime()) );
        calendar.set(Calendar.MONTH,cp.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH,0);
        
        map.put( MONTH_END, sdf.format( calendar.getTime()) );
        
        //TODO 采集期间
//        Period dp = pm.getCurrentDCPeriod();
        if ( cp != null ) {
          //  map.put( DC_CJQJ_KEY, cp.getPeriodCode() );
          //  map.put( CJQJ_BEGIN, sdf.format( cp.getStartDate() ) );
          //  map.put( CJQJ_END, sdf.format( cp.getEndDate() ) );

        }
        else {

        }*/

        return map;
    }

    /*
     * private Map initDCProperties(PeriodManager pm){ Map map = new HashMap(); return map; }
     */

}
