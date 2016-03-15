package com.huge.ihos.system.datacollection.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.datacollection.model.DataCollectionTaskStep;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStepExecute;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskDefineManager;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskManager;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskStepExecuteManager;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskStepManager;
import com.huge.webapp.action.BaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class DataCollectionTaskStepExecuteAction
    extends BaseAction
  {

    //DataCollectionTaskStepManager dataCollectionTaskStepManager;
    private DataCollectionTaskStepExecuteManager dataCollectionTaskStepExecuteManager;

    private List dataCollectionTaskStepExecutes;

    private DataCollectionTaskStepExecute dataCollectionTaskStepExecute;

    private Long stepExecuteId;

    private PagerFactory pagerFactory;

    // entity paging
    protected Integer page = 0;

    protected Integer total = 0;

    protected Integer records = 0;

    private String id;

    protected String oper;

    private String gridOperationMessage;

    private Long taskId;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId( Long taskId ) {
        this.taskId = taskId;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal( Integer total ) {
        this.total = total;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords( Integer records ) {
        this.records = records;
    }

    public String getOper() {
        return oper;
    }

    public void setOper( String oper ) {
        this.oper = oper;
    }

    public String getGridOperationMessage() {
        return gridOperationMessage;
    }

    public void setGridOperationMessage( String gridOperationMessage ) {
        this.gridOperationMessage = gridOperationMessage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage( Integer page ) {
        this.page = page;
    }

    public PagerFactory getPagerFactory() {
        return pagerFactory;
    }

    public void setPagerFactory( PagerFactory pagerFactory ) {
        this.pagerFactory = pagerFactory;
    }

    public void setDataCollectionTaskStepExecuteManager( DataCollectionTaskStepExecuteManager dataCollectionTaskStepExecuteManager ) {
        this.dataCollectionTaskStepExecuteManager = dataCollectionTaskStepExecuteManager;
    }

    public List getDataCollectionTaskStepExecutes() {
        return dataCollectionTaskStepExecutes;
    }

    /**
     * Grab the entity from the database before populating with request
     * parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        /*
         * if (getRequest().getMethod().equalsIgnoreCase("post")) { // prevent
         * failures on new String dataCollectionTaskStepExecuteId =
         * getRequest().
         * getParameter("dataCollectionTaskStepExecute.stepExecuteId"); if
         * (dataCollectionTaskStepExecuteId != null &&
         * !dataCollectionTaskStepExecuteId.equals("")) {
         * dataCollectionTaskStepExecute =
         * dataCollectionTaskStepExecuteManager.get(new
         * Long(dataCollectionTaskStepExecuteId)); } }
         */
        stepTypeList = this.getDictionaryItemManager().getAllItemsByCode( "dataCollectionStepType" );
        this.clearSessionMessages();
    }

    List stepTypeList;

    /*
     * public String list() { dataCollectionTaskStepExecutes =
     * dataCollectionTaskStepExecuteManager.search(query,
     * DataCollectionTaskStepExecute.class); return SUCCESS; }
     */

    public List getStepTypeList() {
        return stepTypeList;
    }

    public void setStepTypeList( List stepTypeList ) {
        this.stepTypeList = stepTypeList;
    }

    public void setStepExecuteId( Long stepExecuteId ) {
        this.stepExecuteId = stepExecuteId;
    }

    public DataCollectionTaskStepExecute getDataCollectionTaskStepExecute() {
        return dataCollectionTaskStepExecute;
    }

    public void setDataCollectionTaskStepExecute( DataCollectionTaskStepExecute dataCollectionTaskStepExecute ) {
        this.dataCollectionTaskStepExecute = dataCollectionTaskStepExecute;
    }

    public String delete() {
        dataCollectionTaskStepExecuteManager.remove( dataCollectionTaskStepExecute.getStepExecuteId() );
        saveMessage( getText( "dataCollectionTaskStepExecute.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( stepExecuteId != null ) {
            dataCollectionTaskStepExecute = dataCollectionTaskStepExecuteManager.get( stepExecuteId );
        }
        else {
            dataCollectionTaskStepExecute = new DataCollectionTaskStepExecute();
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

        boolean isNew = ( dataCollectionTaskStepExecute.getStepExecuteId() == null );

        dataCollectionTaskStepExecuteManager.save( dataCollectionTaskStepExecute );

        String key = ( isNew ) ? "dataCollectionTaskStepExecute.added" : "dataCollectionTaskStepExecute.updated";
        saveMessage( getText( key ) );

        return "edit_success";
    }

    public String dataCollectionTaskStepExecuteGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = dataCollectionTaskStepExecuteManager.getDataCollectionTaskStepExecuteCriteria( pagedRequests, this.taskId );
            // pagedRequests =
            // dataCollectionTaskStepExecuteManager.getDataCollectionTaskStepExecuteCriteria(pagedRequests);
            this.dataCollectionTaskStepExecutes = (List<DataCollectionTaskStepExecute>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "dataCollectionTaskStepExecuteGridList Error", e );
        }
        return SUCCESS;
    }

    public String dataCollectionTaskStepExecuteGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );
                    dataCollectionTaskStepExecuteManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "dataCollectionTaskStepExecute.deleted" );
                return SUCCESS;
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                dataCollectionTaskStepExecuteManager.save( dataCollectionTaskStepExecute );
                String key = ( oper.equals( "add" ) ) ? "dataCollectionTaskStepExecute.added" : "dataCollectionTaskStepExecute.updated";
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

    private String jsonStatus = "success";

    private String jsonMessages;

    public String getJsonStatus() {
        return jsonStatus;
    }

    public void setJsonStatus( String jsonStatus ) {
        this.jsonStatus = jsonStatus;
    }

    public String getJsonMessages() {
        return jsonMessages;
    }

    public void setJsonMessages( String jsonMessages ) {
        this.jsonMessages = jsonMessages;
    }

    public DataCollectionTaskStepExecuteManager getDataCollectionTaskStepExecuteManager() {
        return dataCollectionTaskStepExecuteManager;
    }

    public String disabledStepExecute() {
        try {
            StringTokenizer ids = new StringTokenizer( id, "," );
            while ( ids.hasMoreTokens() ) {
                String removeId = ids.nextToken();
                dataCollectionTaskStepExecuteManager.updateDisabled( removeId, false );
                DataCollectionTaskStepExecute dse = this.dataCollectionTaskStepExecuteManager.get( Long.parseLong( removeId ) );
                DataCollectionTaskStep ds = dse.getDataCollectionTaskStep();//.setIsUsed(false);
                ds.setIsUsed( false );
                this.dataCollectionTaskStepManager.save( ds );
            }
        }
        catch ( Exception e ) {
            this.jsonStatus = "error";
            this.jsonMessages = e.getMessage();
            return SUCCESS;
        }
        this.jsonStatus = "success";
        this.jsonMessages = "成功。";
        return SUCCESS;
    }

    public String enabledStepExecute() {
        try {
            StringTokenizer ids = new StringTokenizer( id, "," );
            while ( ids.hasMoreTokens() ) {
                String removeId = ids.nextToken();
                dataCollectionTaskStepExecuteManager.updateDisabled( removeId, true );
                DataCollectionTaskStepExecute dse = this.dataCollectionTaskStepExecuteManager.get( Long.parseLong( removeId ) );
                DataCollectionTaskStep ds = dse.getDataCollectionTaskStep();//.setIsUsed(false);
                ds.setIsUsed( true );
                this.dataCollectionTaskStepManager.save( ds );
            }
        }
        catch ( Exception e ) {
            this.jsonStatus = "error";
            this.jsonMessages = e.getMessage();
            return SUCCESS;
        }
        this.jsonStatus = "success";
        this.jsonMessages = "成功。";
        return SUCCESS;
    }

    private String isUsedString;

    public String getIsUsedString() {
        return isUsedString;
    }

    public void setIsUsedString( String isUsedString ) {
        this.isUsedString = isUsedString;
    }

    public String getStepExecuteIsUsed() {
        isUsedString = dataCollectionTaskStepExecuteManager.getStepExecuteIsUsed();
        return SUCCESS;
    }

    /**
     * @TODO you should add some validation rules those are difficult on client
     *       side.
     * @return
     */
    private String isValid() {
        if ( dataCollectionTaskStepExecute == null ) {
            return "Invalid dataCollectionTaskStepExecute Data";
        }

        return SUCCESS;

    }

    Long taskExecId;

    Long stepExecId;

    DataCollectionTaskStep dataCollectionTaskStep;

    DataCollectionTaskDefineManager dataCollectionTaskDefineManager;

    DataCollectionTaskManager dataCollectionTaskManager;

    DataCollectionTaskStepManager dataCollectionTaskStepManager;

    String deleteStepExecIds;

    public String getDeleteStepExecIds() {
        return deleteStepExecIds;
    }

    public void setDeleteStepExecIds( String deleteStepExecIds ) {
        this.deleteStepExecIds = deleteStepExecIds;
    }

    public DataCollectionTaskStepManager getDataCollectionTaskStepManager() {
        return dataCollectionTaskStepManager;
    }

    public void setDataCollectionTaskStepManager( DataCollectionTaskStepManager dataCollectionTaskStepManager ) {
        this.dataCollectionTaskStepManager = dataCollectionTaskStepManager;
    }

    public DataCollectionTaskManager getDataCollectionTaskManager() {
        return dataCollectionTaskManager;
    }

    public void setDataCollectionTaskManager( DataCollectionTaskManager dataCollectionTaskManager ) {
        this.dataCollectionTaskManager = dataCollectionTaskManager;
    }

    public DataCollectionTaskDefineManager getDataCollectionTaskDefineManager() {
        return dataCollectionTaskDefineManager;
    }

    public void setDataCollectionTaskDefineManager( DataCollectionTaskDefineManager dataCollectionTaskDefineManager ) {
        this.dataCollectionTaskDefineManager = dataCollectionTaskDefineManager;
    }

    public DataCollectionTaskStep getDataCollectionTaskStep() {
        return dataCollectionTaskStep;
    }

    public void setDataCollectionTaskStep( DataCollectionTaskStep dataCollectionTaskStep ) {
        this.dataCollectionTaskStep = dataCollectionTaskStep;
    }

    public Long getTaskExecId() {
        return taskExecId;
    }

    public void setTaskExecId( Long taskExecId ) {
        this.taskExecId = taskExecId;
    }

    public Long getStepExecId() {
        return stepExecId;
    }

    public void setStepExecId( Long stepExecId ) {
        this.stepExecId = stepExecId;
    }

    private int editType = 0;//0:new,1:edit

    public void setEditType( int editType ) {
        this.editType = editType;
    }

    public int getEditType() {
        return editType;
    }

    public String editTaskStepExec() {
        if ( stepExecId != null ) {
            this.editType = 1;
            this.dataCollectionTaskStep = this.dataCollectionTaskStepExecuteManager.get( stepExecId ).getDataCollectionTaskStep();
        }
        else {
            this.editType = 0;
            this.dataCollectionTaskStep = new DataCollectionTaskStep();
            dataCollectionTaskStep.setDataCollectionTaskDefine( this.dataCollectionTaskManager.get( this.taskExecId ).getDataCollectionTaskDefine() );
        }
        return SUCCESS;
    }

    public String saveTaskStepExec() {
        try {
            boolean isNew = ( dataCollectionTaskStep.getStepId() == null );
            if ( isNew ) {
                dataCollectionTaskStep = this.dataCollectionTaskStepManager.save( dataCollectionTaskStep );

                DataCollectionTaskStepExecute dtse = new DataCollectionTaskStepExecute();
                dtse.setDataCollectionTaskStep( dataCollectionTaskStep );
                dtse.setDataCollectionTask( this.dataCollectionTaskManager.get( this.taskExecId ) );
                dtse.setStatus( DataCollectionTaskStepExecute.STEP_STATUS_PREPARED );
                dtse.setIsUsed( dataCollectionTaskStep.getIsUsed() );
                this.dataCollectionTaskStepExecuteManager.save( dtse );
            }
            else {
                this.dataCollectionTaskStepManager.save( dataCollectionTaskStep );
                DataCollectionTaskStepExecute dtse = this.dataCollectionTaskStepExecuteManager.get( stepExecId );
                dtse.setDataCollectionTaskStep( dataCollectionTaskStep );
                dtse.setDataCollectionTask( this.dataCollectionTaskManager.get( this.taskExecId ) );
                dtse.setStatus( DataCollectionTaskStepExecute.STEP_STATUS_PREPARED );
                dtse.setIsUsed( dataCollectionTaskStep.getIsUsed() );
                this.dataCollectionTaskStepExecuteManager.save( dtse );
            }
            return ajaxForward( true, "数据采集执行步骤保存成功！", true );
        }
        catch ( Exception e ) {
            // TODO: handle exception
            return ajaxForward( false, "数据采集执行步骤保存失败,具体信息为： \n" + e.getMessage(), false );
        }
    }

    public String deleteTaskStepExec() {
        try {
            StringTokenizer ids = new StringTokenizer( deleteStepExecIds, "," );
            while ( ids.hasMoreTokens() ) {
                Long removeId = Long.parseLong( ids.nextToken() );
                this.dataCollectionTaskStepExecuteManager.remove( removeId );
                /*DataCollectionTaskStepExecute se = this.dataCollectionTaskStepExecuteManager.get( removeId );
                int stepUsedCount = dataCollectionTaskStepExecuteManager.getStepDefineUsedCount( se.getDataCollectionTaskStep().getStepId() );
                if ( stepUsedCount > 1 ) {
                    return ajaxForward( false, se.getDataCollectionTaskStep().getStepName() + ":的定义已经被" + stepUsedCount + "个采集任务所使用，不能删除！", false );
                }
                else {
                    Long defId = se.getDataCollectionTaskStep().getStepId();
                    this.dataCollectionTaskStepExecuteManager.remove( removeId );
                    this.dataCollectionTaskStepManager.remove( defId );
                }*/

            }
            return ajaxForward( true, "数据采集执行步骤删除成功！", false );
        }
        catch ( Exception e ) {
            // TODO: handle exception
            return ajaxForward( false, "数据采集执行步骤删除失败,具体信息为： \n" + e.getMessage(), false );
        }
    }
}
