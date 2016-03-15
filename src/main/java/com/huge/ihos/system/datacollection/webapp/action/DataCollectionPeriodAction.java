package com.huge.ihos.system.datacollection.webapp.action;

import java.util.Iterator;

import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.datacollection.model.DataCollectionTask;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskDefineManager;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskManager;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskStepExecuteManager;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskStepManager;
import com.huge.ihos.system.datacollection.service.InterLoggerManager;
import com.huge.webapp.action.JqGridBaseAction;

public class DataCollectionPeriodAction
    extends JqGridBaseAction
{

    /**
     * 
     */
    private static final long serialVersionUID = 231104340359554145L;

    public String getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod( String currentPeriod ) {
        this.currentPeriod = currentPeriod;
    }

    public Integer getTaskTotal() {
        return taskTotal;
    }

    public void setTaskTotal( Integer taskTotal ) {
        this.taskTotal = taskTotal;
    }

    public Integer getPeriodTaskTotal() {
        return periodTaskTotal;
    }

    public void setPeriodTaskTotal( Integer periodTaskTotal ) {
        this.periodTaskTotal = periodTaskTotal;
    }

    public Integer getPeriodCompleteTaskNum() {
        return periodCompleteTaskNum;
    }

    public void setPeriodCompleteTaskNum( Integer periodCompleteTaskNum ) {
        this.periodCompleteTaskNum = periodCompleteTaskNum;
    }

    public Integer getPeriodRemainTaskNum() {
        return periodRemainTaskNum;
    }

    public void setPeriodRemainTaskNum( Integer periodRemainTaskNum ) {
        this.periodRemainTaskNum = periodRemainTaskNum;
    }

    public Boolean getCanCreate() {
        return canCreate;
    }

    public void setCanCreate( Boolean canCreate ) {
        this.canCreate = canCreate;
    }

    public Boolean getCanClose() {
        return canClose;
    }

    public void setCanClose( Boolean canClose ) {
        this.canClose = canClose;
    }
    @Override
    public void prepare() throws Exception {
        super.prepare();
        // TODO Auto-generated method stub

    }

    public PeriodManager getPeriodManager() {
        return periodManager;
    }

    public void setPeriodManager( PeriodManager periodManager ) {
        this.periodManager = periodManager;
    }

    public DataCollectionTaskDefineManager getDataCollectionTaskDefineManager() {
        return dataCollectionTaskDefineManager;
    }

    public void setDataCollectionTaskDefineManager( DataCollectionTaskDefineManager dataCollectionTaskDefineManager ) {
        this.dataCollectionTaskDefineManager = dataCollectionTaskDefineManager;
    }

    public DataCollectionTaskManager getDataCollectionTaskManager() {
        return dataCollectionTaskManager;
    }

    public void setDataCollectionTaskManager( DataCollectionTaskManager dataCollectionTaskManager ) {
        this.dataCollectionTaskManager = dataCollectionTaskManager;
    }

    // 当前期间
    private String currentPeriod;

    // 任务定义数量
    private Integer taskTotal = 0;

    // 当前期间任务数量
    private Integer periodTaskTotal = 0;

    // 当前期间已经完成任务数量
    private Integer periodCompleteTaskNum = 0;

    // 当前期间未完成任务数量
    private Integer periodRemainTaskNum = 0;

    // 是否需要创建当期任务
    private Boolean canCreate = false;

    // 是否能够关闭当前数据采集期间
    private Boolean canClose = false;

    private PeriodManager periodManager;

    private DataCollectionTaskDefineManager dataCollectionTaskDefineManager;

    private DataCollectionTaskManager dataCollectionTaskManager;

    private DataCollectionTaskStepExecuteManager dataCollectionTaskStepExecuteManager;

    private DataCollectionTaskStepManager dataCollectionTaskStepManager;

    private InterLoggerManager interLoggerManager;

    public InterLoggerManager getInterLoggerManager() {
        return interLoggerManager;
    }

    public void setInterLoggerManager( InterLoggerManager interLoggerManager ) {
        this.interLoggerManager = interLoggerManager;
    }

    public DataCollectionTaskStepManager getDataCollectionTaskStepManager() {
        return dataCollectionTaskStepManager;
    }

    public void setDataCollectionTaskStepManager( DataCollectionTaskStepManager dataCollectionTaskStepManager ) {
        this.dataCollectionTaskStepManager = dataCollectionTaskStepManager;
    }

    public DataCollectionTaskStepExecuteManager getDataCollectionTaskStepExecuteManager() {
        return dataCollectionTaskStepExecuteManager;
    }

    public void setDataCollectionTaskStepExecuteManager( DataCollectionTaskStepExecuteManager dataCollectionTaskStepExecuteManager ) {
        this.dataCollectionTaskStepExecuteManager = dataCollectionTaskStepExecuteManager;
    }

    public String showPeriodSum() {
        try {
//			Period period = this.periodManager.getCurrentDCPeriod();
//			Period period = this.periodManager.getPeriodByCode(this.getLoginPeriod());
			this.currentPeriod = this.getLoginPeriod();
			this.taskTotal = this.dataCollectionTaskDefineManager
					.getAllTaskDefineCount();
			this.periodTaskTotal = this.dataCollectionTaskManager.getPeriodTaskCount(currentPeriod);

			this.periodCompleteTaskNum = this.dataCollectionTaskManager.getPeriodCompleteTaskNum(currentPeriod);

			this.periodRemainTaskNum = this.dataCollectionTaskManager.getPeriodRemainTaskNum(currentPeriod);

			if ( this.periodTaskTotal == 0 )
				this.canCreate = true;
			if ( this.periodTaskTotal == this.periodCompleteTaskNum && this.periodTaskTotal != 0 )
				this.canClose = true;
			return this.SUCCESS;
		} catch (Exception e) {
			//setResultName("input");
			//currentPeriod="当前期间未打开";
			//return ajaxForward(false, "当前期间关闭，请打开后再试！", false);
			return ajaxForward(false, "数据错误！", false);
		}
    }

    public String closeDataCollectionPeriod() {
        try {
//        Period period = this.periodManager.getCurrentDCPeriod();
        this.periodManager.closeDataCollectPeriod(  this.getLoginPeriod() );
        return this.SUCCESS;
        } catch (Exception e) {
          // setResultName("input");
         //   currentPeriod="当前期间未打开";
            return ajaxForward( false, "数据采集期间关闭失败,具体的错误信息为: " + e.getMessage(), false, this.ERROR );
        }
    }

    public String createPeriodTask() {
    	try {
    		String period = this.getLoginPeriod();
    		String jjDeptId = this.getSessionUser().getPerson().getDepartment().getDepartmentId();
            this.dataCollectionTaskManager.createPeriodTask( period , jjDeptId);
            return ajaxForward( true, "操作成功！", false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward( false, "操作失败！", false);
		}
        //Period period = this.periodManager.getCurrentDCPeriod();
        //this.periodTaskTotal = this.dataCollectionTaskManager.getPeriodTaskCount( period.getPeriodCode() );
        
    	/*if ( this.periodTaskTotal == 0 ) {

            this.dataCollectionTaskManager.createPeriodTask( this.getLoginPeriod() );
            return this.SUCCESS;
        }
        else {
            return ajaxForward( false, "当前数据采集期间内已有采集任务，请先清除再进行任务初始化创建。", false, this.ERROR );
        }*/
    }

    public String clearPeriodTask() {
//        Period period = this.periodManager.getCurrentDCPeriod();
        this.interLoggerManager.deleteByPeriodCode(  this.getLoginPeriod() );

        Iterator<DataCollectionTask> dctIt = this.dataCollectionTaskManager.getByPeriod(this.getLoginPeriod()).iterator();
        while ( dctIt.hasNext() ) {
            this.dataCollectionTaskStepExecuteManager.clearStepExecuteByTask( dctIt.next() );
        }
        this.dataCollectionTaskManager.clearPeriodTask(this.getLoginPeriod() );
        return this.SUCCESS;
    }
}
