package com.huge.ihos.system.datacollection.webapp.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.datacollection.model.DataCollectionTask;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskDefine;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStepExecute;
import com.huge.ihos.system.datacollection.model.InterLogger;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskDefineManager;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskManager;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskStepExecuteManager;
import com.huge.ihos.system.datacollection.service.InterLoggerManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.util.UUIDGenerator;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class DataCollectionTaskAction extends JqGridBaseAction {
	private DataCollectionTaskManager dataCollectionTaskManager;

	private PeriodManager periodManager;

	private List dataCollectionTasks;

	private DataCollectionTask dataCollectionTask;

	private Long dataCollectionTaskId;

	public PeriodManager getPeriodManager() {
		return periodManager;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}

	public void setDataCollectionTaskManager(DataCollectionTaskManager dataCollectionTaskManager) {
		this.dataCollectionTaskManager = dataCollectionTaskManager;
	}

	public List getDataCollectionTasks() {
		return dataCollectionTasks;
	}

	private DataCollectionTaskStepExecuteManager dataCollectionTaskStepExecuteManager;

	public DataCollectionTaskStepExecuteManager getDataCollectionTaskStepExecuteManager() {
		return dataCollectionTaskStepExecuteManager;
	}

	public void setDataCollectionTaskStepExecuteManager(DataCollectionTaskStepExecuteManager dataCollectionTaskStepExecuteManager) {
		this.dataCollectionTaskStepExecuteManager = dataCollectionTaskStepExecuteManager;
	}

	public InterLoggerManager getInterLoggerManager() {
		return interLoggerManager;
	}

	public void setInterLoggerManager(InterLoggerManager interLoggerManager) {
		this.interLoggerManager = interLoggerManager;
	}

	public DataCollectionTaskManager getDataCollectionTaskManager() {
		return dataCollectionTaskManager;
	}

	private InterLoggerManager interLoggerManager;

	/**
	 * Grab the entity from the database before populating with request
	 * parameters
	 */
	@Override
	public void prepare() throws Exception {
		super.prepare();
		if (getRequest().getMethod().equalsIgnoreCase("post")) {
			// prevent failures on new
			String dataCollectionTaskId = getRequest().getParameter("dataCollectionTask.dataCollectionTaskId");
			if (dataCollectionTaskId != null && !dataCollectionTaskId.equals("")) {
				dataCollectionTask = dataCollectionTaskManager.get(new Long(dataCollectionTaskId));
			}
		}
		this.clearSessionMessages();
	}

	/*
	 * public String list() { dataCollectionTasks =
	 * dataCollectionTaskManager.search(query, DataCollectionTask.class); return
	 * SUCCESS; }
	 */

	public void setDataCollectionTaskId(Long dataCollectionTaskId) {
		this.dataCollectionTaskId = dataCollectionTaskId;
	}

	public DataCollectionTask getDataCollectionTask() {
		return dataCollectionTask;
	}

	public void setDataCollectionTask(DataCollectionTask dataCollectionTask) {
		this.dataCollectionTask = dataCollectionTask;
	}

	public String delete() {
		dataCollectionTaskManager.remove(dataCollectionTask.getDataCollectionTaskId());
		saveMessage(getText("dataCollectionTask.deleted"));

		return "edit_success";
	}

	public String edit() {
		if (dataCollectionTaskId != null) {
			dataCollectionTask = dataCollectionTaskManager.get(dataCollectionTaskId);
		} else {
			dataCollectionTask = new DataCollectionTask();
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

		boolean isNew = (dataCollectionTask.getDataCollectionTaskId() == null);

		dataCollectionTaskManager.save(dataCollectionTask);

		String key = (isNew) ? "dataCollectionTask.added" : "dataCollectionTask.updated";
		saveMessage(getText(key));

		return "edit_success";
	}
	private String menuButtonDetailArrJson;

	public String getMenuButtonDetailArrJson() {
		return menuButtonDetailArrJson;
	}

	public void setMenuButtonDetailArrJson(String menuButtonDetailArrJson) {
		this.menuButtonDetailArrJson = menuButtonDetailArrJson;
	}

	public String dataCollectionCompleteTaskList() {
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			List<MenuButton> buttonList = new ArrayList<MenuButton>();
			List<MenuButton> detailButtonList = new ArrayList<MenuButton>();
			if (!menuButtons.isEmpty() && menuButtons != null) {
				for (MenuButton button : menuButtons) {
					String id = button.getId();
					/*if (this.getLoginPeriodClosed() || !this.getLoginPeriodStarted()) {
						if (!"000302".equals(id) && !"000334".equals(id)) {
							button.setEnable(false);
						}
					}*/
					String regex = "^\\d*0\\d{1}$";
					boolean flag = Pattern.compile(regex).matcher(id).find();
					if (flag) {
						buttonList.add(button);
						continue;
					}
					String regex2 = "^\\d*3\\d{1}$";
					boolean flag2 = Pattern.compile(regex2).matcher(id).find();
					if(flag2) {
						detailButtonList.add(button);
						continue;
					}
				}
			}
			JSONArray menuButtonArr = JSONArray.fromObject(detailButtonList);
			menuButtonDetailArrJson = menuButtonArr.toString();
			menuButtonDetailArrJson = URLEncoder.encode(menuButtonDetailArrJson, "utf-8");
			setMenuButtonsToJson(buttonList);
		} catch (UnsupportedEncodingException e) {
			log.error("dataCollectionCompleteTaskList error", e);
		}
		return SUCCESS;
	}


	public String dataCollectionRemainTaskList() {
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			List<MenuButton> buttonList = new ArrayList<MenuButton>();
			List<MenuButton> detailButtonList = new ArrayList<MenuButton>();
			if (!menuButtons.isEmpty() && menuButtons != null) {
				for (MenuButton button : menuButtons) {
					String id = button.getId();
					/*if (this.getLoginPeriodClosed() || !this.getLoginPeriodStarted()) {
						if (!"000313".equals(id) && !"000344".equals(id)) {
							button.setEnable(false);
						}
					}*/
					String regex = "^\\d*1\\d{1}$";
					boolean flag = Pattern.compile(regex).matcher(id).find();
					if (flag) {
						buttonList.add(button);
						continue;
					}
					String regex2 = "^\\d*4\\d{1}$";
					boolean flag2 = Pattern.compile(regex2).matcher(id).find();
					if(flag2) {
						detailButtonList.add(button);
						continue;
					}
				}

			}
			JSONArray menuButtonArr = JSONArray.fromObject(detailButtonList);
			menuButtonDetailArrJson = menuButtonArr.toString();
			menuButtonDetailArrJson = URLEncoder.encode(menuButtonDetailArrJson, "utf-8");
			setMenuButtonsToJson(buttonList);
		} catch (Exception e) {
			log.error("dataCollectionRemainTaskList error", e);
		}
		return SUCCESS;
	}

	public String dataCollectionTaskGridList() {
		try {
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = dataCollectionTaskManager.getDataCollectionTaskCriteria(pagedRequests);
			this.dataCollectionTasks = (List<DataCollectionTask>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("dataCollectionTaskGridList Error", e);
		}
		return SUCCESS;
	}

	public String dataCollectionTaskGridCompleteList() {
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			String currentPeriod = this.getLoginPeriod();
			if (currentPeriod == null) {

			}
			PropertyFilter periodFilter = new PropertyFilter("EQS_interperiod", currentPeriod);
			filters.add(periodFilter);
			PropertyFilter deptFilter = new PropertyFilter("EQS_dataCollectionTaskDefine.department.departmentId", this.getSessionUser().getPerson().getDepartment().getDepartmentId());
			filters.add(deptFilter);
			PropertyFilter successFilter = new PropertyFilter("EQS_status", DataCollectionTask.TASK_STATUS_SUCCESS);
			filters.add(successFilter);
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = dataCollectionTaskManager.getDataCollectionTasksForGrid(pagedRequests, DataCollectionTask.class, filters);
			//dataCollectionTaskManager.getDataCollectionCompleteTaskCriteria( pagedRequests, this.getPeriodManager().getCurrentDCPeriod().getPeriodCode() );
			this.dataCollectionTasks = (List<DataCollectionTask>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("dataCollectionTaskGridList Error", e);
		}
		return SUCCESS;
	}

	public String dataCollectionTaskGridRemainList() {
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			String currentPeriod = this.getLoginPeriod();
			if (currentPeriod == null) {

			}
			PropertyFilter periodFilter = new PropertyFilter("EQS_interperiod", currentPeriod);
			filters.add(periodFilter);
			PropertyFilter deptFilter = new PropertyFilter("EQS_dataCollectionTaskDefine.department.departmentId", this.getSessionUser().getPerson().getDepartment().getDepartmentId());
			filters.add(deptFilter);
			PropertyFilter successFilter = new PropertyFilter("NES_status", DataCollectionTask.TASK_STATUS_SUCCESS);
			filters.add(successFilter);
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());

			pagedRequests = dataCollectionTaskManager.getDataCollectionTasksForGrid(pagedRequests, DataCollectionTask.class, filters);
			/*dataCollectionTaskManager.getDataCollectionRemainTaskCriteria( pagedRequests,
			                                                               this.getPeriodManager().getCurrentDCPeriod().getPeriodCode() );*/
			this.dataCollectionTasks = (List<DataCollectionTask>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("dataCollectionTaskGridList Error", e);
		}
		return SUCCESS;
	}

	public String dataCollectionTaskGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					//List teList = dataCollectionTaskStepExecuteManager.getByTaskExecIdAndStepDefineId(removeId, null);
					List teList = dataCollectionTaskStepExecuteManager.getByTaskExecId(removeId);
					if (teList != null && teList.size() != 0) {
						return ajaxForward(false, "该任务下有明细存在，不能被删除！", false);
					}
					dataCollectionTaskManager.remove(removeId);
				}
				gridOperationMessage = this.getText("dataCollectionTask.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}

			if (oper.equals("add") || oper.equals("edit")) {
				String error = isValid();
				if (!error.equalsIgnoreCase(SUCCESS)) {
					gridOperationMessage = error;
					return SUCCESS;
				}
				dataCollectionTaskManager.save(dataCollectionTask);
				String key = (oper.equals("add")) ? "dataCollectionTask.added" : "dataCollectionTask.updated";
				gridOperationMessage = this.getText(key);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPeriodGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, gridOperationMessage, false);
		}
	}

	/**
	 * @TODO you should add some validation rules those are difficult on client
	 *       side.
	 * @return
	 */
	private String isValid() {
		if (dataCollectionTask == null) {
			return "Invalid dataCollectionTask Data";
		}

		return SUCCESS;

	}

	public String execTask() {
		Map logsMap = new HashMap<String, List>();
		String message = "";
		String interId = getRuntimeInterId();
		DataCollectionTask taskP = this.dataCollectionTaskManager.get(dataCollectionTaskId);
		//task.setStatus(DataCollectionTask.TASK_STATUS_FAILURE);
		//taskP.setInterRunTimeId(interId);
		dataCollectionTaskManager.save(taskP);

		try {
			this.dataCollectionTaskManager.executeTask(this.dataCollectionTaskId, logsMap);
			List<InterLogger> interLoggers = new ArrayList<InterLogger>();
			interLoggers = (List<InterLogger>) logsMap.get("interLoggers");
			for (int i = 0; i < interLoggers.size(); i++) {
				InterLogger interLogger = interLoggers.get(i);
				interLogger.setTaskInterId(interId);
				interLoggerManager.save(interLogger);
			}
		} catch (Exception e) {
			List<InterLogger> interLoggers = new ArrayList<InterLogger>();
			List<DataCollectionTaskStepExecute> dataCollectionTaskStepExecutes = new ArrayList<DataCollectionTaskStepExecute>();
			interLoggers = (List<InterLogger>) logsMap.get("interLoggers");
			dataCollectionTaskStepExecutes = (List<DataCollectionTaskStepExecute>) logsMap.get("dataCollectionTaskStepExecutes");
			for (Iterator iterator = dataCollectionTaskStepExecutes.iterator(); iterator.hasNext();) {
				DataCollectionTaskStepExecute dataCollectionTaskStepExecute = (DataCollectionTaskStepExecute) iterator.next();
				//DataCollectionTaskStepExecute dataCollectionTaskStepExecute = dataCollectionTaskStepExecutes.get(i);
				dataCollectionTaskStepExecuteManager.save(dataCollectionTaskStepExecute);
			}

			for (int i = 0; i < interLoggers.size(); i++) {
				InterLogger interLogger = interLoggers.get(i);

				interLoggerManager.save(interLogger);
			}
			DataCollectionTask task = this.dataCollectionTaskManager.get(dataCollectionTaskId);
			task.setStatus(DataCollectionTask.TASK_STATUS_FAILURE);
			dataCollectionTaskManager.save(task);
			//throw new BusinessException("数据采集失败，具体信息为：" + e.getMessage());
			message = "数据采集失败，具体信息为：" + e.getMessage();
		}
		if (message.length() > 0) {
			return ajaxForward(false, message, false);
		} else {
			return ajaxForward(true, "任务采集成功！", false);
		}
	}

	public String execTasks() {
		String msg = "";
		String promptMsg = "";
		if (this.log.isDebugEnabled())
			this.log.debug("exec task ids is: " + this.id);
		StringTokenizer ids = new StringTokenizer(id, ",");
		while (ids.hasMoreTokens()) {
			int eid = Integer.parseInt(ids.nextToken());

			//String interId = getRuntimeInterId();
			DataCollectionTask taskP = this.dataCollectionTaskManager.get(new Long(eid));
			//task.setStatus(DataCollectionTask.TASK_STATUS_FAILURE);
			//taskP.setInterRunTimeId(interId);
			dataCollectionTaskManager.save(taskP);

			this.interLoggerManager.deleteByTaskInterId(eid + "");

			dataCollectionTaskManager.prepareExecuteTask(new Long(eid));
			Map logsMap = new HashMap<String, List>();
			try {
				this.dataCollectionTaskManager.executeTask(new Long(eid), logsMap);
				List<InterLogger> interLoggers = new ArrayList<InterLogger>();
				interLoggers = (List<InterLogger>) logsMap.get("interLoggers");
				for (int i = 0; i < interLoggers.size(); i++) {
					InterLogger interLogger = interLoggers.get(i);
					if (interLogger.isPrompt())
						promptMsg += interLogger.getLogFrom() + " " + interLogger.getLogMsg() + "<br/>";
					interLoggerManager.save(interLogger);
				}
			} catch (Exception e) {

				List<InterLogger> interLoggers = new ArrayList<InterLogger>();
				List<DataCollectionTaskStepExecute> dataCollectionTaskStepExecutes = new ArrayList<DataCollectionTaskStepExecute>();
				interLoggers = (List<InterLogger>) logsMap.get("interLoggers");
				dataCollectionTaskStepExecutes = (List<DataCollectionTaskStepExecute>) logsMap.get("dataCollectionTaskStepExecutes");

				for (Iterator iterator = dataCollectionTaskStepExecutes.iterator(); iterator.hasNext();) {
					DataCollectionTaskStepExecute dataCollectionTaskStepExecute = (DataCollectionTaskStepExecute) iterator.next();
					//DataCollectionTaskStepExecute dataCollectionTaskStepExecute = dataCollectionTaskStepExecutes.get(i);
					dataCollectionTaskStepExecuteManager.save(dataCollectionTaskStepExecute);
				}
				for (int i = 0; i < interLoggers.size(); i++) {
					InterLogger interLogger = interLoggers.get(i);
					/*DataCollectionTaskStepExecute dataCollectionTaskStepExecute = dataCollectionTaskStepExecutes.get(i);
					dataCollectionTaskStepExecuteManager.save(dataCollectionTaskStepExecute);*/
					interLoggerManager.save(interLogger);
				}

				String taskName = this.dataCollectionTaskManager.get(new Long(eid)).getDataCollectionTaskDefine().getDataCollectionTaskDefineName();
				msg = msg + taskName + "具体的错误信息为: " + e.getMessage() + "\n";
				DataCollectionTask task = this.dataCollectionTaskManager.get(new Long(eid));
				task.setStatus(DataCollectionTask.TASK_STATUS_FAILURE);
				dataCollectionTaskManager.save(task);

			}

		}
		if (msg.length() > 0) {
			return ajaxForward(false, "数据采集失败!<br/>具体信息为：" + msg, false);
		} else {

			return ajaxForward(true, "任务采集成功！<br/>" + promptMsg, false);
		}
	}

	private String getRuntimeInterId() {
		return UUIDGenerator.getInstance().getNextValue16();
	}

	public String showTaskInterLog() {
		DataCollectionTask taskP = this.dataCollectionTaskManager.get(dataCollectionTaskId);
		//this.getRequest().getSession().setAttribute("currentInterId", taskP.getInterRunTimeId());
		return SUCCESS;
	}

	Long taskExecId;

	Long taskId;

	DataCollectionTaskDefine dataCollectionTaskDefine;

	private DataCollectionTaskDefineManager dataCollectionTaskDefineManager;

	//PeriodManager periodManager;
	private int editType = 0;//0:new,1:edit

	public void setEditType(int editType) {
		this.editType = editType;
	}

	public int getEditType() {
		return editType;
	}

	public DataCollectionTaskDefine getDataCollectionTaskDefine() {
		return dataCollectionTaskDefine;
	}

	public void setDataCollectionTaskDefine(DataCollectionTaskDefine dataCollectionTaskDefine) {
		this.dataCollectionTaskDefine = dataCollectionTaskDefine;
	}

	public DataCollectionTaskDefineManager getDataCollectionTaskDefineManager() {
		return dataCollectionTaskDefineManager;
	}

	public void setDataCollectionTaskDefineManager(DataCollectionTaskDefineManager dataCollectionTaskDefineManager) {
		this.dataCollectionTaskDefineManager = dataCollectionTaskDefineManager;
	}

	public Long getTaskExecId() {
		return taskExecId;
	}

	public void setTaskExecId(Long taskExecId) {
		this.taskExecId = taskExecId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String enableTask() {
		try {
			StringTokenizer ids = new StringTokenizer(id, ",");
			while (ids.hasMoreTokens()) {
				String removeId = ids.nextToken();
				this.dataCollectionTaskManager.updateDisabled(removeId, true);

				DataCollectionTaskDefine dctd = this.dataCollectionTaskManager.get(Long.parseLong(removeId)).getDataCollectionTaskDefine();
				dctd.setIsUsed(true);
				this.dataCollectionTaskDefineManager.save(dctd);
			}
			return ajaxForward(true, "数据采集任务启用成功！", false);
		} catch (Exception e) {
			return ajaxForward(false, "数据采集任务启用失败,具体信息为： \n" + e.getMessage(), false);
		}
	}

	public String disableTask() {
		try {
			StringTokenizer ids = new StringTokenizer(id, ",");
			while (ids.hasMoreTokens()) {
				String removeId = ids.nextToken();
				this.dataCollectionTaskManager.updateDisabled(removeId, false);

				DataCollectionTaskDefine dctd = this.dataCollectionTaskManager.get(Long.parseLong(removeId)).getDataCollectionTaskDefine();
				dctd.setIsUsed(false);
				this.dataCollectionTaskDefineManager.save(dctd);
			}
			return ajaxForward(true, "数据采集任务停用成功！", false);
		} catch (Exception e) {
			return ajaxForward(false, "数据采集任务停用失败,具体信息为： \n" + e.getMessage(), false);
		}
	}

	public String editTaskExec() {
		if (taskExecId != null) {
			this.editType = 1;
			this.dataCollectionTaskDefine = this.dataCollectionTaskManager.get(taskExecId).getDataCollectionTaskDefine();
		} else {
			this.editType = 0;
			this.dataCollectionTaskDefine = new DataCollectionTaskDefine();
			dataCollectionTaskDefine.setDepartment(this.getSessionUser().getPerson().getDepartment().getJjDept());
		}
		return SUCCESS;
	}

	public String saveTaskExec() {
		try {
			boolean isNew = (dataCollectionTaskDefine.getDataCollectionTaskDefineId() == null);
			if (isNew) {
				dataCollectionTaskDefine = this.dataCollectionTaskDefineManager.save(dataCollectionTaskDefine);

				DataCollectionTask dtse = new DataCollectionTask();
				dtse.setDataCollectionTaskDefine(dataCollectionTaskDefine);
				dtse.setStatus(DataCollectionTask.TASK_STATUS_PREPARED);
				//                dtse.setInterperiod( this.periodManager.getCurrentDCPeriod().getPeriodCode() );
				dtse.setInterperiod(this.getLoginPeriod());
				dtse.setIsUsed(dataCollectionTaskDefine.getIsUsed());
				this.dataCollectionTaskManager.save(dtse);
			} else {
				dataCollectionTaskDefine = this.dataCollectionTaskDefineManager.save(dataCollectionTaskDefine);
				DataCollectionTask dtse = this.dataCollectionTaskManager.get(taskExecId);
				dtse.setDataCollectionTaskDefine(dataCollectionTaskDefine);
				dtse.setStatus(DataCollectionTask.TASK_STATUS_PREPARED);
				//                dtse.setInterperiod( this.periodManager.getCurrentDCPeriod().getPeriodCode() );
				dtse.setInterperiod(this.getLoginPeriod());
				dtse.setIsUsed(dataCollectionTaskDefine.getIsUsed());
				this.dataCollectionTaskManager.save(dtse);
			}
			return ajaxForward(true, "数据采集任务保存成功！", true);
		} catch (Exception e) {
			return ajaxForward(false, "数据采集任务保存失败,具体信息为： \n" + e.getMessage(), false);
		}
	}

	public String deleteTaskExec() {
		try {
			StringTokenizer ids = new StringTokenizer(id, ",");
			while (ids.hasMoreTokens()) {
				Long removeId = Long.parseLong(ids.nextToken());
				List teList = dataCollectionTaskStepExecuteManager.getByTaskExecId(removeId);
				if (teList != null && teList.size() != 0) {
					return ajaxForward(false, "该任务下有明细存在，不能被删除！", false);
				}
				this.dataCollectionTaskManager.remove(removeId);
				/*DataCollectionTask se = this.dataCollectionTaskManager.get( removeId );
				int taskUsedCount =
				    dataCollectionTaskManager.getTaskDefineUsedCount( se.getDataCollectionTaskDefine().getDataCollectionTaskDefineId() );
				if ( taskUsedCount > 1 ) {
				    return ajaxForward( false, se.getDataCollectionTaskDefine().getDataCollectionTaskDefineName() + ":的定义已经被" + taskUsedCount
				        + "个采集任务所使用，不能删除！", false );
				}
				else {
				    //Long defId = se.getDataCollectionTaskDefine().getDataCollectionTaskDefineId();
				    this.dataCollectionTaskManager.remove( removeId );
				    //this.dataCollectionTaskDefineManager.remove( defId );
				}*/

			}
			return ajaxForward(true, "数据采集任务删除成功！", false);
		} catch (Exception e) {
			return ajaxForward(false, "数据采集任务删除失败,具体信息为： \n" + e.getMessage(), false);
		}
	}
}
