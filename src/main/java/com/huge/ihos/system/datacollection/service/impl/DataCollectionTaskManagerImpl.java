package com.huge.ihos.system.datacollection.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.exceptions.BusinessException;
import com.huge.ihos.period.dao.PeriodDao;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.datacollection.dao.DataCollectionTaskDao;
import com.huge.ihos.system.datacollection.dao.DataCollectionTaskDefineDao;
import com.huge.ihos.system.datacollection.dao.DataCollectionTaskStepDao;
import com.huge.ihos.system.datacollection.dao.DataCollectionTaskStepExecuteDao;
import com.huge.ihos.system.datacollection.dao.InterLoggerDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTask;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskDefine;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStep;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStepExecute;
import com.huge.ihos.system.datacollection.model.InterLogger;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service( "dataCollectionTaskManager" )
public class DataCollectionTaskManagerImpl
    extends GenericManagerImpl<DataCollectionTask, Long>
    implements DataCollectionTaskManager {
    private DataCollectionTaskDao dataCollectionTaskDao;

    private PeriodDao periodDao;

    private DataCollectionTaskDefineDao dataCollectionTaskDefineDao;

    private DataCollectionTaskStepDao dataCollectionTaskStepDao;

    private DataCollectionTaskStepExecuteDao dataCollectionTaskStepExecuteDao;

    private InterLoggerDao interLoggerDao;

    public InterLoggerDao getInterLoggerDao() {
        return interLoggerDao;
    }

    @Autowired
    public void setInterLoggerDao( InterLoggerDao interLoggerDao ) {
        this.interLoggerDao = interLoggerDao;
    }

    public DataCollectionTaskStepExecuteDao getDataCollectionTaskStepExecuteDao() {
        return dataCollectionTaskStepExecuteDao;
    }

    @Autowired
    public void setDataCollectionTaskStepExecuteDao( DataCollectionTaskStepExecuteDao dataCollectionTaskStepExecuteDao ) {
        this.dataCollectionTaskStepExecuteDao = dataCollectionTaskStepExecuteDao;
    }

    public DataCollectionTaskStepDao getDataCollectionTaskStepDao() {
        return dataCollectionTaskStepDao;
    }

    @Autowired
    public void setDataCollectionTaskStepDao( DataCollectionTaskStepDao dataCollectionTaskStepDao ) {
        this.dataCollectionTaskStepDao = dataCollectionTaskStepDao;
    }

    public DataCollectionTaskDefineDao getDataCollectionTaskDefineDao() {
        return this.dataCollectionTaskDefineDao;
    }

    @Autowired
    public void setDataCollectionTaskDefineDao( DataCollectionTaskDefineDao dataCollectionTaskDefineDao ) {
        this.dataCollectionTaskDefineDao = dataCollectionTaskDefineDao;
    }

    public PeriodDao getPeriodDao() {
        return periodDao;
    }

    @Autowired
    public void setPeriodDao( PeriodDao periodDao ) {
        this.periodDao = periodDao;
    }

    @Autowired
    public DataCollectionTaskManagerImpl( DataCollectionTaskDao dataCollectionTaskDao ) {
        super( dataCollectionTaskDao );
        this.dataCollectionTaskDao = dataCollectionTaskDao;
    }

    public JQueryPager getDataCollectionTaskCriteria( JQueryPager paginatedList ) {
        return dataCollectionTaskDao.getDataCollectionTaskCriteria( paginatedList );
    }

    /*public Integer getCurrentPeriodTaskCount() {
        Period period = this.periodDao.getCurrentPeriod();
        return getPeriodTaskCount( period.getPeriodCode() );
    }*/

    public Integer getPeriodTaskCount( String period ) {
        return dataCollectionTaskDao.getPeriodTaskCount( period );
    }

    public Integer getPeriodCompleteTaskNum( String period ) {
        return dataCollectionTaskDao.getPeriodCompleteTaskNum( period );
    }

    public Integer getPeriodRemainTaskNum( String period ) {
        return dataCollectionTaskDao.getPeriodRemainTaskNum( period );
    }

    public void createPeriodTask( String periodCode ,String deptId) {
        //List defines = this.dataCollectionTaskDefineDao.getAll();
    	//获取本科室的任务
        List defines = this.dataCollectionTaskDefineDao.getByDept(deptId);
        //List defines = this.dataCollectionTaskDefineDao.searchDictionary("DataCollectionTaskDefine", "isUsed=1");
        //Period period = this.periodDao.getPeriodByCode(periodCode);
        for ( Iterator iterator = defines.iterator(); iterator.hasNext(); ) {
            DataCollectionTaskDefine define = (DataCollectionTaskDefine) iterator.next();
            //停用的采集项目过滤掉
            if(!define.getIsUsed()){
            	continue;
            }
            //根据期间和定义 取出任务 如存在则跳过
            DataCollectionTask dct = dataCollectionTaskDao.getByePeriodCodeAndDefineId(periodCode, define.getDataCollectionTaskDefineId());
            if(dct!=null){
            	continue;
            }
            dct = new DataCollectionTask();
            dct.setInterperiod( periodCode );//DataCollectionPeriod(period);
            dct.setDataCollectionTaskDefine( define );
            dct.setStatus( DataCollectionTask.TASK_STATUS_PREPARED );
            dct.setIsUsed( define.getIsUsed() );
            dct = this.dataCollectionTaskDao.save( dct );
            
            List dctlist =
                this.dataCollectionTaskStepDao.getAllByDefineId( dct.getDataCollectionTaskDefine().getDataCollectionTaskDefineId() );
            Iterator<DataCollectionTaskStep> dctsIt = null;
            if ( dctlist != null ) {
                dctsIt = dctlist.iterator();
            }
            while ( dctsIt.hasNext() ) {
                DataCollectionTaskStep dataCollectionTaskStep = dctsIt.next();
                DataCollectionTaskStepExecute dataCollectionTaskStepExecute = new DataCollectionTaskStepExecute();
                dataCollectionTaskStepExecute.setDataCollectionTaskStep( dataCollectionTaskStep );
                dataCollectionTaskStepExecute.setDataCollectionTask( dct );
                dataCollectionTaskStepExecute.setIsUsed( dataCollectionTaskStep.getIsUsed() );
                dataCollectionTaskStepExecute.setStatus( DataCollectionTaskStepExecute.STEP_STATUS_PREPARED );
                this.dataCollectionTaskStepExecuteDao.save( dataCollectionTaskStepExecute );
            }
        }
    }

    public void createSinglePeriodTask( String periodCode, Long defineId ) {
        DataCollectionTaskDefine define = this.dataCollectionTaskDefineDao.get( defineId );
        DataCollectionTask dct = new DataCollectionTask();
        dct.setInterperiod( periodCode );//DataCollectionPeriod(period);
        dct.setDataCollectionTaskDefine( define );
        dct.setStatus( DataCollectionTask.TASK_STATUS_PREPARED );
        dct.setIsUsed( define.getIsUsed() );
        this.dataCollectionTaskDao.save( dct );

        List dctlist = this.dataCollectionTaskStepDao.getAllByDefineId( dct.getDataCollectionTaskDefine().getDataCollectionTaskDefineId() );
        Iterator<DataCollectionTaskStep> dctsIt = null;
        if ( dctlist != null ) {
            dctsIt = dctlist.iterator();
        }
        while ( dctsIt.hasNext() ) {
            DataCollectionTaskStep dataCollectionTaskStep = dctsIt.next();
            DataCollectionTaskStepExecute dataCollectionTaskStepExecute = new DataCollectionTaskStepExecute();
            dataCollectionTaskStepExecute.setDataCollectionTaskStep( dataCollectionTaskStep );
            dataCollectionTaskStepExecute.setDataCollectionTask( dct );
            dataCollectionTaskStepExecute.setIsUsed( dataCollectionTaskStep.getIsUsed() );
            // TODO
            dataCollectionTaskStepExecute.setStatus( DataCollectionTaskStepExecute.STEP_STATUS_PREPARED );
            this.dataCollectionTaskStepExecuteDao.save( dataCollectionTaskStepExecute );
        }

    }

    public DataCollectionTask getByePeriodCodeAndDefineId( String periodCode, Long defineId ) {
        /*String hql = "from DataCollectionTask where dataCollectionTaskDefine.dataCollectionTaskDefineId=? and interperiod=?";
        this.*/
        return this.dataCollectionTaskDao.getByePeriodCodeAndDefineId( periodCode, defineId );
    }

    public void clearPeriodTask( String period ) {
        dataCollectionTaskDao.clearPeriodTask( period );

    }

    public JQueryPager getDataCollectionCompleteTaskCriteria( JQueryPager paginatedList, String period ) {
        return dataCollectionTaskDao.getDataCollectionCompleteTaskCriteria( paginatedList, period );
    }

    public JQueryPager getDataCollectionRemainTaskCriteria( JQueryPager paginatedList, String period ) {
        return dataCollectionTaskDao.getDataCollectionRemainTaskCriteria( paginatedList, period );
    }

    /*private Hashtable<String, String> prepareSqlReplaceParas( DataCollectionTask task ) {
        Hashtable<String, String> table = new Hashtable<String, String>();
        table.put( "TEMP_TABLE", task.getDataCollectionTaskDefine().getTemporaryTableName() );
        table.put( "DEST_TABLE", task.getDataCollectionTaskDefine().getTargetTableName() );
        table.put( "HSQJ", this.getPeriodDao().getCurrentPeriod().getPeriodCode() );
        return table;
    }*/

    public void prepareExecuteTask( Long taskId ) {
        DataCollectionTask task = this.dataCollectionTaskDao.get( taskId );
        task.setStatus( DataCollectionTask.TASK_STATUS_PREPARED );
        this.dataCollectionTaskDao.save( task );
        List<DataCollectionTaskStepExecute> dataCollectionTaskStepExecutes = this.dataCollectionTaskStepExecuteDao.getByTaskExecId( taskId );
        for ( Iterator iterator = dataCollectionTaskStepExecutes.iterator(); iterator.hasNext(); ) {
            DataCollectionTaskStepExecute dataCollectionTaskStepExecute = (DataCollectionTaskStepExecute) iterator.next();
            dataCollectionTaskStepExecute.setStatus( DataCollectionTaskStepExecute.STEP_STATUS_PREPARED );
            this.dataCollectionTaskStepExecuteDao.save( dataCollectionTaskStepExecute );
        }
    }

    public Map executeTask( Long taskId, Map logsMap ) {
        DataCollectionTask task = this.dataCollectionTaskDao.get( taskId );

        //Hashtable<String, String> paraTable = prepareSqlReplaceParas(task);
        Long defineId = task.getDataCollectionTaskDefine().getDataCollectionTaskDefineId();
        //List list = this.dataCollectionTaskStepDao.getAllByDefineId(defineId);

        List<DataCollectionTaskStepExecute> dataCollectionTaskStepExecutes = this.dataCollectionTaskStepExecuteDao.getByTaskExecId( taskId );

        boolean suc = true;
        List<InterLogger> interLoggers = new ArrayList<InterLogger>();
        //List<DataCollectionTaskStepExecute> dataCollectionTaskStepExecutes = new ArrayList<DataCollectionTaskStepExecute>();
        //Map logsMap = new HashMap<String, List>();
        logsMap.put( "interLoggers", interLoggers );
        logsMap.put( "dataCollectionTaskStepExecutes", dataCollectionTaskStepExecutes );
        DataCollectionTaskStep step = null;
        DataCollectionTaskStepExecute dataCollectionTaskStepExecute = null;
        try {
            for ( Iterator iterator = dataCollectionTaskStepExecutes.iterator(); iterator.hasNext(); ) {
                dataCollectionTaskStepExecute = (DataCollectionTaskStepExecute) iterator.next();
                String execType = dataCollectionTaskStepExecute.getDataCollectionTaskStep().getStepType();
                step = dataCollectionTaskStepExecute.getDataCollectionTaskStep();
                //dataCollectionTaskStepExecute = dataCollectionTaskStepExecuteDao.getByTaskExecId(taskId);
                if ( step.getIsUsed() && dataCollectionTaskStepExecute.getIsUsed() ) {
                    if ( execType.equalsIgnoreCase( DataCollectionTaskStep.STEP_TYPE_PRE_PROCESS ) ) {

                        this.dataCollectionTaskDao.execStepPreProcess( task, step );

                    }
                    else if ( execType.equalsIgnoreCase( DataCollectionTaskStep.STEP_TYPE_REMOTE_PRE_PROCESS ) ) {
                        this.dataCollectionTaskDao.execStepRemotePreProcess( task, step );
                    }

                    else if ( execType.equalsIgnoreCase( DataCollectionTaskStep.STEP_TYPE_COLLECT ) ) {
                        this.dataCollectionTaskDao.execStepCollection( task, step );

                    }
                    else if ( execType.equalsIgnoreCase( DataCollectionTaskStep.STEP_TYPE_IMPORT ) ) {

                        this.dataCollectionTaskDao.execStepImport( task, step );

                        //} else if (execType.equalsIgnoreCase(DataCollectionTaskStep.STEP_TYPE_NON_TRANSACTION)) {
                        // TODO 不明白怎么处理

                    }
                    else if ( execType.equalsIgnoreCase( DataCollectionTaskStep.STEP_TYPE_PROMPT ) ) {

                        String promMsg = this.dataCollectionTaskDao.execStepPrompt( task, step );
                        InterLogger interLogger = new InterLogger();
                        interLogger.setLogDateTime( Calendar.getInstance().getTime() );
                        interLogger.setOperator(UserContextUtil.getUserContext().getLoginPersonName());
                        interLogger.setLogFrom( step.getStepName() );
                        interLogger.setLogMsg( promMsg );
                        interLogger.setTaskInterId( task.getDataCollectionTaskId() + "" );
                        interLogger.setPeriodCode( task.getInterperiod() );
                        interLogger.setPrompt( true );
                        interLoggers.add( interLogger );

                        /*	} else if (execType.equalsIgnoreCase(DataCollectionTaskStep.STEP_TYPE_STORE_PROCEDURE)) {
                        		this.dataCollectionTaskDao.execStepStoreProcedure(task, step);
                         */
                    }
                    else if ( execType.equalsIgnoreCase( DataCollectionTaskStep.STEP_TYPE_VERIFY ) ) {

                        this.dataCollectionTaskDao.execStepVerify( task, step );

                        /*	} else if (execType.equalsIgnoreCase(DataCollectionTaskStep.STEP_TYPE_OTHER)) {
                        		this.dataCollectionTaskDao.execStepOther(task, step);
                         */
                    }

                    InterLogger interLogger = new InterLogger();
                    interLogger.setLogDateTime( Calendar.getInstance().getTime() );
                    interLogger.setOperator(UserContextUtil.getUserContext().getLoginPersonName());
                    interLogger.setLogFrom( step.getStepName() );
                    interLogger.setLogMsg( task.TASK_STATUS_SUCCESS );
                    interLogger.setTaskInterId( task.getDataCollectionTaskId() + "" );
                    interLogger.setPeriodCode( task.getInterperiod() );
                    interLoggers.add( interLogger );

                    //dataCollectionTaskStepExecute.setDataCollectionTaskStep(step);
                    //dataCollectionTaskStepExecute.setDataCollectionTask(task);
                    dataCollectionTaskStepExecute.setStatus( DataCollectionTaskStepExecute.STEP_STATUS_SUCCESS );
                    //dataCollectionTaskStepExecutes.add(dataCollectionTaskStepExecute);
                }
            }
            task.setStatus( DataCollectionTask.TASK_STATUS_SUCCESS );

        }
        catch ( Exception e ) {
            task.setStatus( DataCollectionTask.TASK_STATUS_FAILURE );
            e.printStackTrace();

            String msg = e.getMessage();

            String[] msgs = msg.split( "\tmsg\n" );

            for ( int i = 0; i < msgs.length; i++ ) {
                InterLogger interLogger = new InterLogger();
                interLogger.setLogDateTime( Calendar.getInstance().getTime() );
                interLogger.setOperator(UserContextUtil.getUserContext().getLoginPersonName());
                interLogger.setLogFrom( step.getStepName() );
                interLogger.setLogMsg( msgs[i] );
                interLogger.setTaskInterId( task.getDataCollectionTaskId() + "" );
                interLogger.setPeriodCode( task.getInterperiod() );
                interLoggers.add( interLogger );
            }

            dataCollectionTaskStepExecute.setDataCollectionTaskStep( step );
            dataCollectionTaskStepExecute.setDataCollectionTask( task );
            dataCollectionTaskStepExecute.setStatus( DataCollectionTaskStepExecute.STEP_STATUS_FAILURE );
            dataCollectionTaskStepExecutes.add( dataCollectionTaskStepExecute );
            throw new BusinessException( "数据采集失败，具体信息为：" + e.getMessage() );
        }
        finally {
        }

        this.dataCollectionTaskDao.save( task );
        return logsMap;
    }

    public String updateDisabled( String id, boolean value ) {
        return this.dataCollectionTaskDao.updateDisabled( id, value );
    }

    public int getTaskDefineUsedCount( Long taskDefineId ) {
        return this.dataCollectionTaskDao.getTaskDefineUsedCount( taskDefineId );
    }

	@Override
	public List<DataCollectionTask> getRemainTasks(String period) {
		return dataCollectionTaskDao.getRemainTasks(period);
	}

	@Override
	public boolean isAllowColsePeriod(String period) {
		List<DataCollectionTask> dataCollectionTasks = dataCollectionTaskDao.getRemainTasks(period);
		boolean flag = true;
		for(DataCollectionTask dataCollectionTask : dataCollectionTasks){
			if(dataCollectionTask.getIsUsed()){
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public List<DataCollectionTask> getInUsed() {
		return this.dataCollectionTaskDao.getInUsed();
	}

	@Override
	public List<DataCollectionTask> getByPeriod(String period) {
		return this.dataCollectionTaskDao.getByPeriod(period);
	}
	@Override
	public JQueryPager getDataCollectionTasksForGrid( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters ) {
		Map<String, Object> resultMap = this.dataCollectionTaskDao.getAppManagerCriteriaWithSearch(paginatedList, object, filters);
		List<DataCollectionTask> list = (List<DataCollectionTask>) resultMap.get("list");
		if (!list.isEmpty() && list != null) {
			for (DataCollectionTask dataCollectionTask : list) {
				//DataCollectionTaskDefine define = dataCollectionTask.getDataCollectionTaskDefine();
				List<InterLogger> interLoggers = this.interLoggerDao.getCriteria().add(Restrictions.eq("taskInterId", dataCollectionTask.getDataCollectionTaskId() + "")).list();
				if (!interLoggers.isEmpty() && interLoggers != null) {
					InterLogger interLogger = interLoggers.get(0);
					dataCollectionTask.setOperator(interLogger.getOperator());
					dataCollectionTask.setOperatDate(interLogger.getLogDateTime());
				}
				
			}
		}
		paginatedList.setList(list);
		int count = 0;
		Integer icount = (Integer) resultMap.get("count");
		if (icount != null)
			count = icount.intValue();
		paginatedList.setTotalNumberOfRows(count);
		return paginatedList;
	}
}
