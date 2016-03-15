package com.huge.ihos.templateDemo.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.systemManager.globalparam.model.GlobalParam;
import com.huge.ihos.system.systemManager.globalparam.service.GlobalParamManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class TemplateDemoAction
    extends JqGridBaseAction
     {
    /**
     * 
     */
    private static final long serialVersionUID = -4032298776344009079L;

    private GlobalParamManager globalParamManager;

    private List<GlobalParam> globalParams;

    private GlobalParam globalParam;

    private Long paramId;


    public String globalparamGridList() {
        log.debug( "enter list method!" );
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = globalParamManager.getGlobalParamCriteria( pagedRequests, filters );
            this.globalParams = (List<GlobalParam>) pagedRequests.getList();
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
            globalParamManager.save( globalParam );
        }
        catch ( Exception dre ) {
            gridOperationMessage = dre.getMessage();
            return ajaxForwardError( gridOperationMessage );
        }
        String key = ( ( this.isEntityIsNew() ) ) ? "globalParam.added" : "globalParam.updated";
        return ajaxForward( this.getText( key ) );
    }

    public String edit() {
        if ( paramId != null ) {
            globalParam = globalParamManager.get( paramId );
            this.setEntityIsNew( false );
        }
        else {
            globalParam = new GlobalParam();
            this.setEntityIsNew( true );
        }
        return SUCCESS;
    }

    public String globalparamGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );
                    log.debug( "Delete Customer " + removeId );
                    GlobalParam globalParam = globalParamManager.get( new Long( removeId ) );
                    globalParamManager.remove( new Long( removeId ) );

                }
                gridOperationMessage = this.getText( "globalParam.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkGlobalParamGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( false, e.getMessage(), false );
        }
    }

    private String isValid() {
        if ( globalParam == null ) {
            return "Invalid globalParam Data";
        }

        return SUCCESS;

    }

    public GlobalParamManager getGlobalParamManager() {
        return globalParamManager;
    }

    public void setGlobalParamManager( GlobalParamManager globalParamManager ) {
        this.globalParamManager = globalParamManager;
    }

    public List<GlobalParam> getGlobalParams() {
        return globalParams;
    }

    public void setGlobalParams( List<GlobalParam> globalParams ) {
        this.globalParams = globalParams;
    }

    public GlobalParam getGlobalParam() {
        return globalParam;
    }

    public void setGlobalParam( GlobalParam globalParam ) {
        this.globalParam = globalParam;
    }

    public Long getParamId() {
        return paramId;
    }

    public void setParamId( Long paramId ) {
        this.paramId = paramId;
    }

}
