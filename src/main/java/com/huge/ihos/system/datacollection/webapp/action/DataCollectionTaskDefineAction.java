package com.huge.ihos.system.datacollection.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskDefine;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStep;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskDefineManager;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskManager;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskStepManager;
import com.huge.ihos.system.datacollection.service.DataSourceDefineManager;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class DataCollectionTaskDefineAction extends JqGridBaseAction {
	private DataCollectionTaskDefineManager dataCollectionTaskDefineManager;

	private List dataCollectionTaskDefines;

	private DataCollectionTaskDefine dataCollectionTaskDefine;

	private DataSourceDefineManager dataSourceDefineManager;

	private Long dataCollectionTaskDefineId;

	private DataCollectionTaskStepManager dataCollectionTaskStepManager;

	public DataCollectionTaskStepManager getDataCollectionTaskStepManager() {
		return dataCollectionTaskStepManager;
	}

	public void setDataCollectionTaskStepManager(DataCollectionTaskStepManager dataCollectionTaskStepManager) {
		this.dataCollectionTaskStepManager = dataCollectionTaskStepManager;
	}

	DataCollectionTaskManager dataCollectionTaskManager;

	PeriodManager periodManager;

	public PeriodManager getPeriodManager() {
		return periodManager;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}

	public DataCollectionTaskManager getDataCollectionTaskManager() {
		return dataCollectionTaskManager;
	}

	public void setDataCollectionTaskManager(DataCollectionTaskManager dataCollectionTaskManager) {
		this.dataCollectionTaskManager = dataCollectionTaskManager;
	}

	public void setDataCollectionTaskDefineManager(DataCollectionTaskDefineManager dataCollectionTaskDefineManager) {
		this.dataCollectionTaskDefineManager = dataCollectionTaskDefineManager;
	}

	public DataSourceDefineManager getDataSourceDefineManager() {
		return dataSourceDefineManager;
	}

	public void setDataSourceDefineManager(DataSourceDefineManager dataSourceDefineManager) {
		this.dataSourceDefineManager = dataSourceDefineManager;
	}

	public List getDataCollectionTaskDefines() {
		return dataCollectionTaskDefines;
	}

	/*   public String list() {
	       dataCollectionTaskDefines = dataCollectionTaskDefineManager.search(query, DataCollectionTaskDefine.class);
	       return SUCCESS;
	   }*/

	public void setDataCollectionTaskDefineId(Long dataCollectionTaskDefineId) {
		this.dataCollectionTaskDefineId = dataCollectionTaskDefineId;
	}

	public DataCollectionTaskDefine getDataCollectionTaskDefine() {
		return dataCollectionTaskDefine;
	}

	public void setDataCollectionTaskDefine(DataCollectionTaskDefine dataCollectionTaskDefine) {
		this.dataCollectionTaskDefine = dataCollectionTaskDefine;
	}

	public String delete() {

		dataCollectionTaskDefineManager.remove(dataCollectionTaskDefine.getDataCollectionTaskDefineId());
		saveMessage(getText("dataCollectionTaskDefine.deleted"));

		return "edit_success";
	}

	public String edit() {
		if (dataCollectionTaskDefineId != null) {
			dataCollectionTaskDefine = dataCollectionTaskDefineManager.get(dataCollectionTaskDefineId);
		} else {
			dataCollectionTaskDefine = new DataCollectionTaskDefine();
		}

		return SUCCESS;
	}

	public String save() throws Exception {
		if (cancel != null) {
			return "cancel";
		}

		if (delete != null) {
			return delete();
		}

		boolean isNew = (dataCollectionTaskDefine.getDataCollectionTaskDefineId() == null);
		Long dsdId = dataCollectionTaskDefine.getDataSourceDefine().getDataSourceDefineId();
		dataCollectionTaskDefine.setDataSourceDefine(this.dataSourceDefineManager.get(dsdId));
		dataCollectionTaskDefine = dataCollectionTaskDefineManager.save(dataCollectionTaskDefine);

		/*Period period = this.periodManager.getCurrentDCPeriod();
		if(period!=null){
		    int periodTaskTotal = this.dataCollectionTaskManager.getPeriodTaskCount( period );
		
		    if ( periodTaskTotal > 0 ) {
		        if ( isNew ) {
		            this.dataCollectionTaskManager.createSinglePeriodTask( period,
		                                                                   dataCollectionTaskDefine.getDataCollectionTaskDefineId() );
		        }
		        else {
		            DataCollectionTask dtse =
		                this.dataCollectionTaskManager.getByePeriodCodeAndDefineId( period,
		                                                                            dataCollectionTaskDefine.getDataCollectionTaskDefineId() );
		            //dtse.setDataCollectionTaskDefine(dataCollectionTaskDefine);
		            //dtse.setStatus(DataCollectionTask.TASK_STATUS_PREPARED);
		            //dtse.setInterperiod(this.periodManager.getCurrentDCPeriod().getPeriodCode());
		            dtse.setIsUsed( dataCollectionTaskDefine.getIsUsed() );
		            this.dataCollectionTaskManager.save( dtse );
		        }
		    }
		    String returnMess = "";
		    String key = ( isNew ) ? "dataCollectionTaskDefine.added" : "dataCollectionTaskDefine.updated";
		    saveMessage( getText( key ) );
		    returnMess = getText( key );
		    return ajaxForward( getText( key ) );
		}*/
		/* else{
		 	return ajaxForward( false,"请设置采集期间！",false );
		 }*/
		String key = (isNew) ? "dataCollectionTaskDefine.added" : "dataCollectionTaskDefine.updated";
		saveMessage(getText(key));
		return ajaxForward(getText(key));
	}

	public String dataCollectionTaskDefineGridList() {
		try {
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = dataCollectionTaskDefineManager.getDataCollectionTaskDefineCriteria(pagedRequests);
			this.dataCollectionTaskDefines = (List<DataCollectionTaskDefine>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("dataCollectionTaskDefineGridList Error", e);
		}
		return SUCCESS;
	}

	public String dataCollectionTaskDefineGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					DataCollectionTaskDefine dctd = this.dataCollectionTaskDefineManager.get(removeId);
					int taskUsedCount = dataCollectionTaskManager.getTaskDefineUsedCount(removeId);
					if (taskUsedCount > 0) {
						return ajaxForward(false, dctd.getDataCollectionTaskDefineName() + ":的定义已经被" + taskUsedCount + "个采集任务所使用，不能删除！", false);
					} else {
						List datacollectionTaskStepList = dataCollectionTaskStepManager.getAllByDefineId(removeId);
						if (datacollectionTaskStepList != null && datacollectionTaskStepList.size() != 0) {
							return ajaxForward(false, "该采集任务定义了明细步骤，不能被删除！", false);
						}
						dataCollectionTaskDefineManager.remove(removeId);
					}

					//dataCollectionTaskDefineManager.remove(removeId);
				}
				gridOperationMessage = this.getText("dataCollectionTaskDefine.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}

			if (oper.equals("add") || oper.equals("edit")) {
				String error = isValid();
				if (!error.equalsIgnoreCase(SUCCESS)) {
					gridOperationMessage = error;
					return SUCCESS;
				}

				Long dsdId = dataCollectionTaskDefine.getDataSourceDefine().getDataSourceDefineId();
				dataCollectionTaskDefine.setDataSourceDefine(this.dataSourceDefineManager.get(dsdId));
				dataCollectionTaskDefineManager.save(dataCollectionTaskDefine);
				String key = (oper.equals("add")) ? "dataCollectionTaskDefine.added" : "dataCollectionTaskDefine.updated";
				gridOperationMessage = this.getText(key);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPeriodGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			gridOperationMessage = e.getMessage();
			return ajaxForward(false, gridOperationMessage, false);
		}
	}

	public String copy() {
		try {
			if (OtherUtil.measureNotNull(id)) {
				DataCollectionTaskDefine dataCollectionTaskDefine = dataCollectionTaskDefineManager.get(Long.parseLong(id));
				DataCollectionTaskDefine defineCopy = (DataCollectionTaskDefine) dataCollectionTaskDefine.clone();
				List<DataCollectionTaskStep> dataCollectionTaskSteps = dataCollectionTaskStepManager.getAllByDefineId(Long.parseLong(id));
				List<DataCollectionTaskStep> taskSteps = new ArrayList<DataCollectionTaskStep>();
				defineCopy.setDataCollectionTaskDefineId(null);
				String newName = dataCollectionTaskDefine.getDataCollectionTaskDefineName() + "-副本" + DateUtil.getSnapCode();
				defineCopy.setDataCollectionTaskDefineName(newName);
				DataCollectionTaskDefine define = dataCollectionTaskDefineManager.getByNameDefine(newName);
				if(define == null) {
					dataCollectionTaskDefineManager.save(defineCopy);
					defineCopy = dataCollectionTaskDefineManager.getByNameDefine(newName);
					for (DataCollectionTaskStep step : dataCollectionTaskSteps) {
						DataCollectionTaskStep stepClone = (DataCollectionTaskStep) step.clone();
						stepClone.setDataCollectionTaskDefine(defineCopy);
						stepClone.setStepId(null);
						taskSteps.add(stepClone);
					}
					dataCollectionTaskStepManager.saveAll(taskSteps);
				} else {
					return ajaxForwardError("采集任务副本已存在！");
				}
			}
		} catch (Exception e) {
			log.debug("DataCollectionTaskDefine Copy Error!", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @TODO you should add some validation rules those are difficult on client side.
	 * @return
	 */
	private String isValid() {
		if (dataCollectionTaskDefine == null) {
			return "Invalid dataCollectionTaskDefine Data";
		}

		return SUCCESS;

	}
}