package com.huge.ihos.hr.trainCourse.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.trainCourse.model.TrainCourse;
import com.huge.ihos.hr.trainCourse.service.TrainCourseManager;
import com.huge.ihos.hr.trainNeed.model.TrainNeed;
import com.huge.ihos.hr.trainNeed.service.TrainNeedManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




@SuppressWarnings("serial")
public class TrainCoursePagedAction extends JqGridBaseAction implements Preparable {
	private TrainCourseManager trainCourseManager;
	private List<TrainCourse> trainCourses;
	private TrainCourse trainCourse;
	private String id;
	private String needId;
	private TrainNeed trainNeed;
	private TrainNeedManager trainNeedManager;

	public void setTrainCourseManager(TrainCourseManager trainCourseManager) {
		this.trainCourseManager = trainCourseManager;
	}

	public List<TrainCourse> getTrainCourses() {
		return trainCourses;
	}

	public TrainCourse getTrainCourse() {
		return trainCourse;
	}

	public void setTrainCourse(TrainCourse trainCourse) {
		this.trainCourse = trainCourse;
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
	@SuppressWarnings("unchecked")
	public String trainCourseGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = trainCourseManager
					.getTrainCourseCriteria(pagedRequests,filters);
			this.trainCourses = (List<TrainCourse>) pagedRequests.getList();
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
//			JSONUtils.getMorpherRegistry().registerMorpher( new DateMorpher(new String[] { "yyyy-MM-dd" }));
			if(OtherUtil.measureNull(trainCourse.getTrainNeed().getId()))
				trainCourse.setTrainNeed(null);
			if(OtherUtil.measureNull(trainCourse.getTrainTeacher().getId()))
				trainCourse.setTrainTeacher(null);
			trainCourseManager.saveTrainCourse(trainCourse, this.isEntityIsNew());
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "trainCourse.added" : "trainCourse.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	trainCourse = trainCourseManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	trainCourse = new TrainCourse();
        	trainCourse.setYearMonth(this.getLoginPeriod());
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    public String trainCourseCheckList(){
    	if(needId!=null){
    		trainNeed=trainNeedManager.get(needId);
    	}
    	return SUCCESS;
    }
	public String trainCourseGridEdit() {
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
				this.trainCourseManager.remove(ida);
				gridOperationMessage = this.getText("trainCourse.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkTrainCourseGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (trainCourse == null) {
			return "Invalid trainCourse Data";
		}
		return SUCCESS;

	}
	

	public String getNeedId() {
		return needId;
	}

	public void setNeedId(String needId) {
		this.needId = needId;
	}

	public TrainNeed getTrainNeed() {
		return trainNeed;
	}

	public void setTrainNeed(TrainNeed trainNeed) {
		this.trainNeed = trainNeed;
	}

	public TrainNeedManager getTrainNeedManager() {
		return trainNeedManager;
	}

	public void setTrainNeedManager(TrainNeedManager trainNeedManager) {
		this.trainNeedManager = trainNeedManager;
	}
}

