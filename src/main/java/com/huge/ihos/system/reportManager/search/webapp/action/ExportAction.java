package com.huge.ihos.system.reportManager.search.webapp.action;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.common.query.ResultMap;
import com.huge.common.util.ImportException;
import com.huge.foundation.util.StringUtil;
import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.ihos.system.reportManager.search.util.ColumnDef;
import com.huge.ihos.system.reportManager.search.util.ExcelUtil;
import com.huge.ihos.system.reportManager.search.util.ExportHelper;
import com.huge.webapp.action.BaseAction;
import com.huge.webapp.util.SpringContextHelper;

public class ExportAction
    extends BaseAction
    {
    private static String rtnToken = "\r\n";

    private static String exportTemplate =
        "select * from t_search where searchId in ('@SEARCHED@');\r\nselect * from t_searchItem where searchId in ('@SEARCHED@');\r\nselect * from t_searchOption where searchId in ('@SEARCHED@');\r\nselect * from t_searchUrl where searchId in ('@SEARCHED@');\r\nselect * from t_searchLink where searchId in ('@SEARCHED@')";

    private QueryManager queryManager;

    private String exportSearchId;

    private String exportSearchName;

    private String exportSql;

    private DataSource dataSource = SpringContextHelper.getDataSource();

    private String _$1;


    public String getExportSql() {
        return exportSql;
    }

    public void setExportSql( String exportSql ) {
        this.exportSql = exportSql;
    }

    public QueryManager getQueryManager() {
        return queryManager;
    }

    public void setQueryManager( QueryManager queryManager ) {
        this.queryManager = queryManager;
    }

    public String getExportSearchId() {
        return exportSearchId;
    }

    public void setExportSearchId( String exportSearchId ) {
        this.exportSearchId = exportSearchId;
    }

    public String getExportSearchName() {
        return exportSearchName;
    }

    public void setExportSearchName( String exportSearchName ) {
        this.exportSearchName = exportSearchName;
    }

    public String exportSearchExcel() {
        Search search = null;
        String exportSql;
        String title = "导出数据.xls";
        if ( exportSearchId != null && !exportSearchId.equalsIgnoreCase( "" ) ) {
            search = this.queryManager.getSearchById( exportSearchId );
            exportSql = StringUtil.replaceString( exportTemplate, "@SEARCHED@", search.getSearchId() );
            title = exportSearchId + ".xls";
        }
        else if ( exportSearchName != null && !exportSearchName.equalsIgnoreCase( "" ) ) {
            search = this.queryManager.getSearchBySearchName( exportSearchName );
            exportSql = StringUtil.replaceString( exportTemplate, "@SEARCHED@", search.getSearchId() );
            title = exportSearchName + ".xls";
        }
        else {
            exportSql = this.exportSql;
        }

        String[] sqlStrs = StringUtil.strToArray( exportSql, ";" );
        try {
            HttpServletResponse resp = ServletActionContext.getResponse();
            resp.setContentType( "application/vnd.ms-excel" );
            resp.setHeader( "Content-Disposition", "attachment;filename=" + new String( title.getBytes( "GBK" ), "ISO8859-1" ) );
            OutputStream outs = resp.getOutputStream();

            // DataSource ds = (DataSource)
            // SpringContextHelper.getBean("dataSource");
            JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
            ExcelUtil.exportExcelBySQL( jtl, sqlStrs, outs );

            outs.flush();
            outs.close();
            this.importResult = "导出Excel成功！";
        }
        catch ( Exception e ) {
            e.printStackTrace();
            this.importResult = e.getMessage();
            //throw new BusinessException(e.getMessage());
        }
        HttpServletRequest request = ServletActionContext.getRequest();

        request.getSession().setAttribute( "importResult", importResult );
        return null;
    }

    private File excelfile;

    public File getExcelfile() {
        return excelfile;
    }

    public void setExcelfile( File excelfile ) {
        this.excelfile = excelfile;
    }

    private String importResult = "";

    public String getImportResult() {
        return importResult;
    }

    public void setImportResult( String importResult ) {
        this.importResult = importResult;
    }

    public String importSearchExcel() {
        // DataSource ds = (DataSource)
        // SpringContextHelper.getBean("dataSource");
        JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
        try {
            ExcelUtil.importExcel( jtl, excelfile.getAbsolutePath() );
        }
        catch ( ImportException e ) {
            e.printStackTrace();
            this.importResult = e.getMessage();
            return ajaxForward( false, importResult, true );
            //return this.SUCCESS;
            // throw new BusinessException(e.getMessage());
        }
        this.importResult = "导入成功。";
        return ajaxForward( true, importResult, true );
    }

    public String exportSql() {
        try {
            String sql = exportSql;
            Object sqlStringBuffer = new StringBuffer();
            Object sqlStringTemp;
            JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
            if ( ( exportSearchId != null ) && ( exportSearchId.trim().length() >= 1 ) ) {
                sql =
                    "select * from t_search where searchId='" + exportSearchId.trim() + "';\n" + "select * from t_searchItem where searchId='"
                        + exportSearchId.trim() + "' order by oorder;\n" + "select * from t_searchOption where searchId='" + exportSearchId.trim()
                        + "' order by oorder;\n" + "select * from t_searchlink where searchId='" + exportSearchId.trim() + "';\n"
                        + "select * from t_searchUrl where searchId='" + exportSearchId.trim() + "' order by oorder";
                // sql = "select mysql from t_search where searchId='" +
                // exportSearchId.trim();
                String[] strs = StringUtil.strToArray( (String) sql, ";" );
                for ( int i = 0; i < strs.length; i++ ) {
                    sqlStringTemp = getExportSQLBySQL( jtl, strs[i], i );
                    ( (StringBuffer) sqlStringBuffer ).append( (String) sqlStringTemp );
                }
            }
            else if ( ( exportSearchName != null ) && ( exportSearchName.trim().length() > 1 ) ) {
                sql =
                    "select * from t_search where searchName='" + exportSearchName.trim() + "';\n"
                        + "select * from t_searchItem where searchId in (select searchId from t_search where searchName='" + exportSearchName.trim()
                        + "') order by oorder;\n"
                        + "select * from t_searchOption where searchId in (select searchId from t_search where searchName='"
                        + exportSearchName.trim() + "') order by oorder;\n"
                        + "select * from t_searchUrl where searchId in (select searchId from t_search where searchName='" + exportSearchName.trim()
                        + "') order by oorder";
                String[] strs = StringUtil.strToArray( (String) sql, ";" );
                for ( int i = 0; i < strs.length; i++ ) {
                    sqlStringTemp = getExportSQLBySQL( jtl, strs[i], i );
                    ( (StringBuffer) sqlStringBuffer ).append( (String) sqlStringTemp );
                }
            }
            else {
                String[] strs = StringUtil.strToArray( (String) sql.trim(), ";" );

                for ( int i = 0; i < strs.length; i++ )
                    if ( strs[i].toUpperCase().indexOf( "SELECT" ) > -1 ) {
                        sqlStringTemp = getExportSQLBySQL( jtl, strs[i], i );
                        ( (StringBuffer) sqlStringBuffer ).append( (String) sqlStringTemp );
                    }
                    else {
                        strs[i] = "select * from " + strs[i];
                        if ( ( strs[i] == null ) || ( strs[i].length() <= 0 ) )
                            continue;
                        sqlStringTemp = getExportSQLBySQL( jtl, strs[i], i );
                        ( (StringBuffer) sqlStringBuffer ).append( (String) sqlStringTemp );
                    }
            }
            sqlStringTemp = ( (StringBuffer) sqlStringBuffer ).toString();

            HttpServletResponse resp = ServletActionContext.getResponse();
            resp.setContentType( "application/x-msdownload" );
            resp.setHeader( "Content-Disposition", "attachment;filename=" + new String( "导出SQL.txt".getBytes( "GBK" ), "ISO8859-1" ) );
            OutputStream outs = resp.getOutputStream();
            outs.write( sqlStringTemp.toString().getBytes() );
            outs.flush();
            outs.close();
        }
        catch ( Exception e ) {
            // TODO: handle exception
        }

        return null;
    }

    public static ResultMap changeToResultMap( Map rsMap ) {
        ResultMap resultMap = new ResultMap();
        Set<Object> keys = rsMap.keySet();
        for ( Object key : keys ) {
            resultMap.put( key, rsMap.get( key ) );
        }
        return resultMap;
    }

    public static String getExportSQLBySQL( JdbcTemplate jtl, String paramString, int paramInt ) {
        StringBuffer localStringBuffer = new StringBuffer();
        try {
            List localList = jtl.queryForList( paramString );
            String str1 = getTableName( paramString );
            String str2 = "insert into " + str1 + " (";
            String str3 = "";
            String str4 = paramString;
            ColumnDef[] arrayOfColumnDef = ExportHelper.getColumnDefs( jtl, str4 );
            if ( localList.size() > 0 ) {
                String[] arrayOfString = (String[]) null;
                for ( int i = 0; i < localList.size(); i++ ) {

                    ResultMap localResultMap = changeToResultMap( (Map) localList.get( i ) );
                    if ( arrayOfString == null ) {
                        arrayOfString = new String[arrayOfColumnDef.length];
                        for ( int j = 0; j < arrayOfColumnDef.length; j++ )
                            arrayOfString[j] = arrayOfColumnDef[j].getFieldName();
                        for ( int j = 0; j < arrayOfString.length; j++ )
                            str2 = str2 + arrayOfString[j] + ",";
                        str2 = str2.substring( 0, str2.length() - 1 ) + ") values(";
                    }
                    str3 = str2;
                    for ( int j = 0; j < arrayOfString.length; j++ )
                        str3 = str3 + _$1( arrayOfColumnDef[j], arrayOfString[j], localResultMap ) + ",";
                    str3 = str3.substring( 0, str3.length() - 1 ) + ");\r\n go \r\n";
                    if ( ( paramInt == 0 ) && ( i == 0 ) )
                        localStringBuffer.append( _$1( str3, 0 ) );
                    else
                        localStringBuffer.append( _$1( str3, 1 ) );
                }
            }
        }
        catch ( Exception localException ) {
            // throw new ExportException(localException.getMessage());
            localException.printStackTrace();
        }
        return localStringBuffer.toString();
    }

    public static String getTableName( String paramString ) {
        String str = paramString.toUpperCase();
        int i = str.indexOf( "FROM" );
        str = str.substring( i + 5 );
        i = str.indexOf( "WHERE" );
        if ( i >= 0 )
            str = str.substring( 0, i - 1 );
        i = str.indexOf( "ORDER BY" );
        if ( i < 0 )
            return str.trim();
        str = str.substring( 0, i - 1 );
        return str.trim();
    }

    private static String _$1( String paramString, int paramInt ) {
        String[] arrayOfString = StringUtil.strToArray( paramString, "\r\n" );
        String str = "";
        for ( int i = 0; i < arrayOfString.length; i++ )
            if ( ( paramInt == 0 ) && ( i == 0 ) )
                // str = str + "\t\t\"" +
                // StringUtil.replaceString(arrayOfString[i], "\"", "\\\"") +
                // "\\n\"\r\n";
                str = str + "\t\t" + StringUtil.replaceString( arrayOfString[i], "\"", "\\\"" ) + "\n";
            else
                // str = str + "\t\t+\"" +
                // StringUtil.replaceString(arrayOfString[i], "\"", "\\\"") +
                // "\\n\"\r\n";
                str = str + "\t\t" + StringUtil.replaceString( arrayOfString[i], "\"", "\\\"" ) + "\n";
        return str;
    }

    private static String _$1( ColumnDef paramColumnDef, String paramString, ResultMap paramResultMap ) {
        String str = "";
        if ( paramResultMap.get( paramString ) == null ) {
            str = str + "null";
        }
        else {
            int i = paramColumnDef.getType();
            if ( ( i == -5 ) || ( i == -7 ) || ( i == 6 ) || ( i == 8 ) || ( i == 3 ) || ( i == 4 ) || ( i == 2 ) || ( i == 5 ) || ( i == 7 )
                || ( i == -6 ) )
                str = str + paramResultMap.getString( paramString );
            else if ( ( i == -100 ) || ( i == 93 ) || ( i == 91 ) )
                str = str + "'" + StringUtil.replaceString( paramResultMap.getString( paramString ), "'", "''" ) + "'";
            else
                str = str + "'" + StringUtil.replaceString( paramResultMap.getString( paramString ), "'", "''" ) + "'";
        }
        return str;
    }
}
