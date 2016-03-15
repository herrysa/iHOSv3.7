package com.huge.ihos.system.datacollection.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.datacollection.model.InterLogger;
import com.huge.ihos.system.datacollection.service.InterLoggerManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class InterLoggerAction
    extends JqGridBaseAction
     {
    private InterLoggerManager interLoggerManager;

    private List interLoggers;
    
    private String taskInterId;

    private InterLogger interLogger;

    private Long logId;

    private String dataCollectionTaskId;

    private String stepName;

    public String getStepName() {
        return stepName;
    }

    public void setStepName( String stepName ) {
        this.stepName = stepName;
    }

    public String getDataCollectionTaskId() {
        return dataCollectionTaskId;
    }

    public void setDataCollectionTaskId( String dataCollectionTaskId ) {
        this.dataCollectionTaskId = dataCollectionTaskId;
    }

    public void setInterLoggerManager( InterLoggerManager interLoggerManager ) {
        this.interLoggerManager = interLoggerManager;
    }

    public List getInterLoggers() {
        return interLoggers;
    }

  

    /*   public String list() {
           interLoggers = interLoggerManager.search(query, InterLogger.class);
           return SUCCESS;
       }*/

    public void setLogId( Long logId ) {
        this.logId = logId;
    }

    public InterLogger getInterLogger() {
        return interLogger;
    }

    public void setInterLogger( InterLogger interLogger ) {
        this.interLogger = interLogger;
    }

    public String delete() {
        interLoggerManager.remove( interLogger.getLogId() );
        saveMessage( getText( "interLogger.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( logId != null ) {
            interLogger = interLoggerManager.get( logId );
        }
        else {
            interLogger = new InterLogger();
        }

        return SUCCESS;
    }

    public String save()
        throws Exception {
        if ( cancel != null ) {
            return "cancel";
        }

        if ( delete != null ) {
            return delete();
        }

        boolean isNew = ( interLogger.getLogId() == null );

        interLoggerManager.save( interLogger );

        String key = ( isNew ) ? "interLogger.added" : "interLogger.updated";
        saveMessage( getText( key ) );

        return "edit_success";
    }

    public String interLoggerGridList() {
        try {

            /*String interLogId =null;
            HttpServletRequest req = this.getRequest();
            interLogId =(String)this.getRequest().getSession().getAttribute("currentInterId");	
            if(interLogId==null){
            	interLogId =(String)this.getRequest().getParameter("currentInterId");	
            }*/
        	if("view".equals(oper)){
        		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
        		this.interLoggers = interLoggerManager.getByFilters(filters);
        	}else{
        		JQueryPager pagedRequests = null;
                pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
                pagedRequests = interLoggerManager.getInterLoggerCriteria( pagedRequests, this.dataCollectionTaskId );
                this.interLoggers = (List<InterLogger>) pagedRequests.getList();
                records = pagedRequests.getTotalNumberOfRows();
                total = pagedRequests.getTotalNumberOfPages();
                page = pagedRequests.getPageNumber();
        	}
        }
        catch ( Exception e ) {
            log.error( "interLoggerGridList Error", e );
        }
        return SUCCESS;
    }

    public String interLoggerStepGridList() {
        try {

            /*String interLogId =null;
            HttpServletRequest req = this.getRequest();
            interLogId =(String)this.getRequest().getSession().getAttribute("currentInterId");	
            if(interLogId==null){
            	interLogId =(String)this.getRequest().getParameter("currentInterId");	
            }*/
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = interLoggerManager.getInterLoggerCriteria( pagedRequests, this.dataCollectionTaskId, this.stepName );
            this.interLoggers = (List<InterLogger>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "interLoggerGridList Error", e );
        }
        return SUCCESS;
    }

    public String interLoggerGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );
                    interLoggerManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "interLogger.deleted" );
                return SUCCESS;
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                interLoggerManager.save( interLogger );
                String key = ( oper.equals( "add" ) ) ? "interLogger.added" : "interLogger.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return SUCCESS;
        }
    }

    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( interLogger == null ) {
            return "Invalid interLogger Data";
        }

        return SUCCESS;

    }

	public String getTaskInterId() {
		return taskInterId;
	}

	public void setTaskInterId(String taskInterId) {
		this.taskInterId = taskInterId;
	}
}