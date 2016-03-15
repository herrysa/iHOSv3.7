package com.huge.ihos.system.systemManager.operateLog.webapp.action;

import java.io.OutputStream;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.foundation.util.StringUtil;
import com.huge.ihos.system.reportManager.search.util.ExcelUtil;
import com.huge.ihos.system.systemManager.operateLog.model.OperateLog;
import com.huge.ihos.system.systemManager.operateLog.service.OperateLogManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.SpringContextHelper;

public class OperateLogPagedAction
    extends JqGridBaseAction
     {

    private OperateLogManager operateLogManager;

    private List<OperateLog> operateLogs;

    private OperateLog operateLog;

    private Long operateLogId;

    private DataSource dataSource = SpringContextHelper.getDataSource();

    private String beginTime;

    private String endTime;

    private String[] isAll;

    private String type;

    public OperateLogManager getOperateLogManager() {
        return operateLogManager;
    }

    public void setOperateLogManager( OperateLogManager operateLogManager ) {
        this.operateLogManager = operateLogManager;
    }

    public List<OperateLog> getoperateLogs() {
        return operateLogs;
    }

    public void setOperateLogs( List<OperateLog> operateLogs ) {
        this.operateLogs = operateLogs;
    }

    public OperateLog getOperateLog() {
        return operateLog;
    }

    public void setOperateLog( OperateLog operateLog ) {
        this.operateLog = operateLog;
    }

    public Long getOperateLogId() {
        return operateLogId;
    }

    public void setOperateLogId( Long operateLogId ) {
        this.operateLogId = operateLogId;
    }



    public String operateLogGridList() {
        log.debug( "enter list method!" );
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = operateLogManager.getOperateLogCriteria( pagedRequests, filters );
            this.operateLogs = (List<OperateLog>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "List Error", e );
        }
        return SUCCESS;
    }

    public String save() {
        String error = isValid();
        if ( !error.equalsIgnoreCase( SUCCESS ) ) {
            gridOperationMessage = error;
            return ajaxForwardError( gridOperationMessage );
        }
        try {
            operateLogManager.save( operateLog );
        }
        catch ( Exception dre ) {
            gridOperationMessage = dre.getMessage();
            return ajaxForwardError( gridOperationMessage );
        }
        String key = ( ( this.isEntityIsNew() ) ) ? "operateLog.added" : "operateLog.updated";
        return ajaxForward( this.getText( key ) );
    }

    public String edit() {
        if ( operateLogId != null ) {
            operateLog = operateLogManager.get( operateLogId );
            this.setEntityIsNew( false );
        }
        else {
            operateLog = new OperateLog();
            this.setEntityIsNew( true );
        }
        return SUCCESS;
    }

    public String operateLogGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );
                    log.debug( "Delete Customer " + removeId );
                    OperateLog operateLog = operateLogManager.get( new Long( removeId ) );
                    operateLogManager.remove( new Long( removeId ) );

                }
                gridOperationMessage = this.getText( "operateLog.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkOperateLogGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( false, e.getMessage(), false );
        }
    }

    public String backupOperateLog() {
        String exportSql;
        //String deleteSql;
        String title = "operateLog_bak.xls";
        String exportMess = "";
        beginTime += " 00:00:00";
        endTime += " 23:59:59";
        if ( isAll == null ) {
            exportSql =
                "select * from sy_OperateLog where operateTime >= '" + beginTime + "'" + " and operateTime <= '" + endTime
                    + "' order by operateTime desc";
        }
        else {
            exportSql = "select * from sy_OperateLog order by operateTime desc";
        }

        //deleteSql = "delete from sy_OperateLog where operateTime >= '"+beginTime+"'"+" and operateTime <= '"+endTime+"'";

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

            //jtl.execute(deleteSql);

            exportMess = "导出Excel成功！";
            //operateLogManager.
        }
        catch ( Exception e ) {
            e.printStackTrace();
            exportMess = e.getMessage();
            //throw new BusinessException(e.getMessage());
        }
        HttpServletRequest request = ServletActionContext.getRequest();

        request.getSession().setAttribute( "exportMess", exportMess );
        return null;
    }

    public String deleteOperateLog() {
        String deleteSql;
        String exportMess = "";
        beginTime += " 00:00:00";
        endTime += " 23:59:59";

        if ( isAll == null ) {
            deleteSql = "delete from sy_OperateLog where operateTime >= '" + beginTime + "'" + " and operateTime <= '" + endTime + "'";
        }
        else {
            deleteSql = "delete from sy_OperateLog";
        }

        try {
            JdbcTemplate jtl = new JdbcTemplate( this.dataSource );

            jtl.execute( deleteSql );

            exportMess = "清除成功！";
            //operateLogManager.
        }
        catch ( Exception e ) {
            e.printStackTrace();
            exportMess = e.getMessage();
            //throw new BusinessException(e.getMessage());
            return ajaxForward(true, exportMess ,false);
        }

        return ajaxForward(true, exportMess );
    }

    private String isValid() {
        if ( operateLog == null ) {
            return "Invalid operateLog Data";
        }

        return SUCCESS;

    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime( String beginTime ) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime( String endTime ) {
        this.endTime = endTime;
    }

    public String[] getIsAll() {
        return isAll;
    }

    public void setIsAll( String[] isAll ) {
        this.isAll = isAll;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }
}
