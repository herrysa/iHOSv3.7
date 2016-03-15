package com.huge.ihos.hr.trainInformation.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.attachment.model.Attachment;
import com.huge.ihos.hr.attachment.service.AttachmentManager;
import com.huge.ihos.hr.trainInformation.model.TrainInformation;
import com.huge.ihos.hr.trainInformation.service.TrainInformationManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class TrainInformationPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4384433542658595003L;
	private TrainInformationManager trainInformationManager;
	private List<TrainInformation> trainInformations;
	private TrainInformation trainInformation;
	private AttachmentManager attachmentManager;
	private List<Attachment> attachments;
	private String id;
	private List informationTypeList;
	private List informationClassList;
	private String fileFullPath;

	public TrainInformationManager getTrainInformationManager() {
		return trainInformationManager;
	}

	public void setTrainInformationManager(TrainInformationManager trainInformationManager) {
		this.trainInformationManager = trainInformationManager;
	}

	public List getInformationTypeList() {
        return informationTypeList;
    }
	
	public List getInformationClassList() {
        return informationClassList;
    }
	
	public List<TrainInformation> gettrainInformations() {
		return trainInformations;
	}

	public void setTrainInformations(List<TrainInformation> trainInformations) {
		this.trainInformations = trainInformations;
	}

	public TrainInformation getTrainInformation() {
		return trainInformation;
	}

	public void setTrainInformation(TrainInformation trainInformation) {
		this.trainInformation = trainInformation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		informationTypeList= this.getDictionaryItemManager().getAllItemsByCode( "informationType" );
		informationClassList= this.getDictionaryItemManager().getAllItemsByCode( "informationClass" );
		this.clearSessionMessages();
	}
	public String trainInformationGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = trainInformationManager
					.getTrainInformationCriteria(pagedRequests,filters);
			this.trainInformations = (List<TrainInformation>) pagedRequests.getList();
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
			trainInformationManager.save(trainInformation);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "trainInformation.added" : "trainInformation.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	trainInformation = trainInformationManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	trainInformation = new TrainInformation();
        	this.setEntityIsNew(true);
        }
        HttpServletRequest request = this.getRequest();
        fileFullPath= request.getRealPath( "//home//attachment//");
		fileFullPath += "//";
        return SUCCESS;
    }
	public String trainInformationGridEdit() {
		try {
			if (oper.equals("del")) {
				List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id,",");
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
//					filters.clear();
//					filters.add(new PropertyFilter("EQS_orgCode", this.getCurrentSystemVariable().getOrgCode()));
//					filters.add(new PropertyFilter("EQS_type","trainInformation"));
//					filters.add(new PropertyFilter("EQS_foreignKey",removeId));
//					attachments=attachmentManager.getByFilters(filters);
//					if(attachments!=null&&attachments.size()>0){
//						gridOperationMessage = this.getText("trainInformation.hasAttachment");
//						return ajaxForward(false, gridOperationMessage, false);
//					}
					idl.add(removeId);
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.trainInformationManager.remove(ida);
				gridOperationMessage = this.getText("trainInformation.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkTrainInformationGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (trainInformation == null) {
			return "Invalid trainInformation Data";
		}

		return SUCCESS;

	}

	public AttachmentManager getAttachmentManager() {
		return attachmentManager;
	}

	public void setAttachmentManager(AttachmentManager attachmentManager) {
		this.attachmentManager = attachmentManager;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getFileFullPath() {
		return fileFullPath;
	}

	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}
}

