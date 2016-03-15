package com.huge.ihos.hr.trainRecord.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.trainRecord.model.TrainRecord;
import com.huge.ihos.hr.trainRecord.service.TrainRecordManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class TrainRecordPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4705492309753019754L;
	private TrainRecordManager trainRecordManager;
	private List<TrainRecord> trainRecords;
	private TrainRecord trainRecord;
	private String id;
	private String gridAllDatas;

	public TrainRecordManager getTrainRecordManager() {
		return trainRecordManager;
	}

	public void setTrainRecordManager(TrainRecordManager trainRecordManager) {
		this.trainRecordManager = trainRecordManager;
	}

	public List<TrainRecord> gettrainRecords() {
		return trainRecords;
	}

	public void setTrainRecords(List<TrainRecord> trainRecords) {
		this.trainRecords = trainRecords;
	}

	public TrainRecord getTrainRecord() {
		return trainRecord;
	}

	public void setTrainRecord(TrainRecord trainRecord) {
		this.trainRecord = trainRecord;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		try {
			  List<MenuButton> menuButtons = this.getMenuButtons();
			   //menuButtons.get(0).setEnable(false);
			  setMenuButtonsToJson(menuButtons);
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
		this.clearSessionMessages();
	}
	public String trainRecordGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = trainRecordManager
					.getTrainRecordCriteria(pagedRequests,filters);
			this.trainRecords = (List<TrainRecord>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			if(OtherUtil.measureNull(trainRecord.getTrainNeed().getId()))
				trainRecord.setTrainNeed(null);
			if(OtherUtil.measureNull(trainRecord.getMaker().getPersonId()))
				trainRecord.setMaker(null);
			if(OtherUtil.measureNull(trainRecord.getChecker().getPersonId()))
				trainRecord.setChecker(null);
			if(OtherUtil.measureNull(trainRecord.getDoner().getPersonId()))
				trainRecord.setDoner(null);
			trainRecordManager.saveTrainRecord(trainRecord,this.isEntityIsNew(),gridAllDatas);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "trainRecord.added" : "trainRecord.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	trainRecord = trainRecordManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	trainRecord = new TrainRecord();
        	trainRecord.setState("1");
        	trainRecord.setMaker(this.getSessionUser().getPerson());
        	trainRecord.setMakeDate(new Date());
        	trainRecord.setYearMonth(this.getLoginPeriod());
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String trainRecordGridEdit() {
		try {
			if (oper.equals("del")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.trainRecordManager.remove(ida);
				gridOperationMessage = this.getText("trainRecord.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("check")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				trainRecordManager.auditTrainRecord(checkIds,this.getSessionUser().getPerson(),new Date());
				gridOperationMessage = this.getText("审核成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("cancelCheck")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> cancelCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					cancelCheckIds.add(cancelCheckId);
				}
				trainRecordManager.antiTrainRecord(cancelCheckIds);
				gridOperationMessage = this.getText("销审成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("done")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> doneIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String doneId = ids.nextToken();
					doneIds.add(doneId);
				}
				trainRecordManager.doneTrainRecord(doneIds, this.getSessionUser().getPerson(),new Date());
				gridOperationMessage = this.getText("记入档案成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}
			
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkTrainRecordGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (trainRecord == null) {
			return "Invalid trainRecord Data";
		}

		return SUCCESS;

	}

	public String getGridAllDatas() {
		return gridAllDatas;
	}

	public void setGridAllDatas(String gridAllDatas) {
		this.gridAllDatas = gridAllDatas;
	}
}

