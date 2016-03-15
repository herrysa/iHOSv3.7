package com.huge.ihos.hr.trainTeacher.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.util.JSONUtils;

import com.huge.ihos.hr.trainTeacher.model.TrainTeacher;
import com.huge.ihos.hr.trainTeacher.service.TrainTeacherManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class TrainTeacherPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7567701264622360473L;
	private TrainTeacherManager trainTeacherManager;
	private List<TrainTeacher> trainTeachers;
	private TrainTeacher trainTeacher;
	private String id;
	private List sexList;
	private List educationList;
	private List professionalList;
	private List teacherCategoryList;

	public TrainTeacherManager getTrainTeacherManager() {
		return trainTeacherManager;
	}

	public void setTrainTeacherManager(TrainTeacherManager trainTeacherManager) {
		this.trainTeacherManager = trainTeacherManager;
	}

	public List getSexList() {
        return sexList;
    }
	public List getEducationList() {
        return educationList;
    }
	public List getProfessionalList() {
        return professionalList;
    }
	public List getTeacherCategoryList() {
        return teacherCategoryList;
    }
	
	public List<TrainTeacher> gettrainTeachers() {
		return trainTeachers;
	}

	public void setTrainTeachers(List<TrainTeacher> trainTeachers) {
		this.trainTeachers = trainTeachers;
	}

	public TrainTeacher getTrainTeacher() {
		return trainTeacher;
	}

	public void setTrainTeacher(TrainTeacher trainTeacher) {
		this.trainTeacher = trainTeacher;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		sexList= this.getDictionaryItemManager().getAllItemsByCode( "sex" );
		educationList= this.getDictionaryItemManager().getAllItemsByCode( "education" );
		professionalList= this.getDictionaryItemManager().getAllItemsByCode( "professional" );
		teacherCategoryList= this.getDictionaryItemManager().getAllItemsByCode( "teacherCategory" );
		this.clearSessionMessages();
	}
	public String trainTeacherGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = trainTeacherManager
					.getTrainTeacherCriteria(pagedRequests,filters);
			this.trainTeachers = (List<TrainTeacher>) pagedRequests.getList();
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
			JSONUtils.getMorpherRegistry().registerMorpher( new DateMorpher(new String[] { "yyyy-MM-dd" }));
			trainTeacherManager.save(trainTeacher);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "trainTeacher.added" : "trainTeacher.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	trainTeacher = trainTeacherManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	trainTeacher = new TrainTeacher();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String trainTeacherGridEdit() {
		try {
			if (oper.equals("del")) {
				List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.trainTeacherManager.remove(ida);
				gridOperationMessage = this.getText("trainTeacher.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkTrainTeacherGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (trainTeacher == null) {
			return "Invalid trainTeacher Data";
		}

		return SUCCESS;

	}
}

