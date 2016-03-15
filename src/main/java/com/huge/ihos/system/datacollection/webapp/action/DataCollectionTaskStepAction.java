package com.huge.ihos.system.datacollection.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.datacollection.model.DataCollectionTask;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStep;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStepExecute;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskDefineManager;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskManager;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskStepExecuteManager;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskStepManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class DataCollectionTaskStepAction
    extends JqGridBaseAction
   {
    private DataCollectionTaskStepManager dataCollectionTaskStepManager;

    private DataCollectionTaskDefineManager dataCollectionTaskDefineManager;

    private List dataCollectionTaskSteps;

    private DataCollectionTaskStep dataCollectionTaskStep;

    private Long stepId;

    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId( Long parentId ) {
        this.parentId = parentId;
    }

    public DataCollectionTaskDefineManager getDataCollectionTaskDefineManager() {
        return dataCollectionTaskDefineManager;
    }

    public void setDataCollectionTaskDefineManager( DataCollectionTaskDefineManager dataCollectionTaskDefineManager ) {
        this.dataCollectionTaskDefineManager = dataCollectionTaskDefineManager;
    }

    public void setDataCollectionTaskStepManager( DataCollectionTaskStepManager dataCollectionTaskStepManager ) {
        this.dataCollectionTaskStepManager = dataCollectionTaskStepManager;
    }

    public List getDataCollectionTaskSteps() {
        return dataCollectionTaskSteps;
    }



    /*   public String list() {
           dataCollectionTaskSteps = dataCollectionTaskStepManager.search(query, DataCollectionTaskStep.class);
           return SUCCESS;
       }*/

    public void setStepId( Long stepId ) {
        this.stepId = stepId;
    }

    public DataCollectionTaskStep getDataCollectionTaskStep() {
        return dataCollectionTaskStep;
    }

    public void setDataCollectionTaskStep( DataCollectionTaskStep dataCollectionTaskStep ) {
        this.dataCollectionTaskStep = dataCollectionTaskStep;
    }

    public String delete() {
        dataCollectionTaskStepManager.remove( dataCollectionTaskStep.getStepId() );
        saveMessage( getText( "dataCollectionTaskStep.deleted" ) );

        return "edit_success";
    }

    
    private List dataCollectionStepTypeList;
    
    
    public List getDataCollectionStepTypeList() {
		return dataCollectionStepTypeList;
	}

/*	public void setDataCollectionStepTypeList(List dataCollectionStepTypeList) {
		this.dataCollectionStepTypeList = dataCollectionStepTypeList;
	}*/

	public String edit() {
    	
		this.dataCollectionStepTypeList=this.getDictionaryItemManager().getAllItemsByCode("dataCollectionStepType");
    	
        if ( stepId != null ) {
            dataCollectionTaskStep = dataCollectionTaskStepManager.get( stepId );
        }
        else {
            dataCollectionTaskStep = new DataCollectionTaskStep();
            dataCollectionTaskStep.setDataCollectionTaskDefine( this.getDataCollectionTaskDefineManager().get( parentId ) );
        }

        return SUCCESS;
    }

    PeriodManager periodManager;

    DataCollectionTaskManager dataCollectionTaskManager;

    DataCollectionTaskStepExecuteManager dataCollectionTaskStepExecuteManager;

    public DataCollectionTaskStepExecuteManager getDataCollectionTaskStepExecuteManager() {
        return dataCollectionTaskStepExecuteManager;
    }

    public void setDataCollectionTaskStepExecuteManager( DataCollectionTaskStepExecuteManager dataCollectionTaskStepExecuteManager ) {
        this.dataCollectionTaskStepExecuteManager = dataCollectionTaskStepExecuteManager;
    }

    public DataCollectionTaskManager getDataCollectionTaskManager() {
        return dataCollectionTaskManager;
    }

    public void setDataCollectionTaskManager( DataCollectionTaskManager dataCollectionTaskManager ) {
        this.dataCollectionTaskManager = dataCollectionTaskManager;
    }

    public PeriodManager getPeriodManager() {
        return periodManager;
    }

    public void setPeriodManager( PeriodManager periodManager ) {
        this.periodManager = periodManager;
    }

    public String save()
        throws Exception {
        if ( cancel != null ) {
            return "cancel";
        }

        if ( delete != null ) {
            return delete();
        }

        boolean isNew = ( dataCollectionTaskStep.getStepId() == null );

        Long pid = dataCollectionTaskStep.getDataCollectionTaskDefine().getDataCollectionTaskDefineId();
        dataCollectionTaskStep.setDataCollectionTaskDefine( this.getDataCollectionTaskDefineManager().get( pid ) );
        dataCollectionTaskStep = dataCollectionTaskStepManager.save( dataCollectionTaskStep );

        if ( isNew ) {
            Long defId = dataCollectionTaskStep.getDataCollectionTaskDefine().getDataCollectionTaskDefineId();
//            String pcode = this.periodManager.getCurrentDCPeriod().getPeriodCode();
            String pcode = this.getLoginPeriod();

            DataCollectionTask taskexec = this.dataCollectionTaskManager.getByePeriodCodeAndDefineId( pcode, defId );
            if ( taskexec != null ) {
                DataCollectionTaskStepExecute dtse = new DataCollectionTaskStepExecute();
                dtse.setDataCollectionTaskStep( dataCollectionTaskStep );
                dtse.setDataCollectionTask( taskexec );
                dtse.setStatus( DataCollectionTaskStepExecute.STEP_STATUS_PREPARED );
                dtse.setIsUsed( dataCollectionTaskStep.getIsUsed() );
                this.dataCollectionTaskStepExecuteManager.save( dtse );
            }

        }
        else {
            Long defId = dataCollectionTaskStep.getDataCollectionTaskDefine().getDataCollectionTaskDefineId();
//            String pcode = this.periodManager.getCurrentDCPeriod().getPeriodCode();
            String pcode = this.getLoginPeriod();

            DataCollectionTask taskexec = this.dataCollectionTaskManager.getByePeriodCodeAndDefineId( pcode, defId );
            if ( taskexec != null ) {
                DataCollectionTaskStepExecute dtse =
                    this.dataCollectionTaskStepExecuteManager.getByTaskExecIdAndStepDefineId( taskexec.getDataCollectionTaskId(),
                                                                                              dataCollectionTaskStep.getStepId() );
                if ( dtse != null ) {
                    dtse.setIsUsed( dataCollectionTaskStep.getIsUsed() );
                    this.dataCollectionTaskStepExecuteManager.save( dtse );
                }
                else {
                    dtse = new DataCollectionTaskStepExecute();
                    dtse.setDataCollectionTaskStep( dataCollectionTaskStep );
                    dtse.setDataCollectionTask( taskexec );
                    dtse.setStatus( DataCollectionTaskStepExecute.STEP_STATUS_PREPARED );
                    dtse.setIsUsed( dataCollectionTaskStep.getIsUsed() );
                    this.dataCollectionTaskStepExecuteManager.save( dtse );
                }
            }

        }

        String key = ( isNew ) ? "dataCollectionTaskStep.added" : "dataCollectionTaskStep.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String dataCollectionTaskStepGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = dataCollectionTaskStepManager.getDataCollectionTaskStepCriteria( pagedRequests, this.parentId );
            this.dataCollectionTaskSteps = (List<DataCollectionTaskStep>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "dataCollectionTaskStepGridList Error", e );
        }
        return SUCCESS;
    }

    public String dataCollectionTaskStepGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );

                    DataCollectionTaskStep dcts = dataCollectionTaskStepManager.get( removeId );
                    int stepUsedCount = dataCollectionTaskStepExecuteManager.getStepDefineUsedCount( removeId );
                    if ( stepUsedCount > 0 ) {
                        return ajaxForward( false, dcts.getStepName() + ":的定义已经被" + stepUsedCount + "个采集任务所使用，不能删除！", false );
                    }
                    else {
                        dataCollectionTaskStepManager.remove( removeId );
                    }

                }
                gridOperationMessage = this.getText( "dataCollectionTaskStep.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                dataCollectionTaskStepManager.save( dataCollectionTaskStep );
                String key = ( oper.equals( "add" ) ) ? "dataCollectionTaskStep.added" : "dataCollectionTaskStep.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( false, "删除错误，请检查数据！", false );
        }
    }
    
    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( dataCollectionTaskStep == null ) {
            return "Invalid dataCollectionTaskStep Data";
        }

        return SUCCESS;

    }
}