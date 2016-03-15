package com.huge.ihos.hr.attachment.webapp.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.util.JSONUtils;

import com.huge.ihos.hr.attachment.model.Attachment;
import com.huge.ihos.hr.attachment.service.AttachmentManager;
import com.huge.util.OptFile;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class AttachmentPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7750063913544024934L;
	private AttachmentManager attachmentManager;
	private List<Attachment> attachments;
	private Attachment attachment;
	private String id;
	private String type;
	private String foreignKey;
	private String fileFullPath;

	public AttachmentManager getAttachmentManager() {
		return attachmentManager;
	}

	public void setAttachmentManager(AttachmentManager attachmentManager) {
		this.attachmentManager = attachmentManager;
	}

	public List<Attachment> getattachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		HttpServletRequest request = this.getRequest();
		fileFullPath= request.getRealPath( "//home//attachment//");
		fileFullPath += "//";
		this.clearSessionMessages();
	}
	public String attachmentGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = attachmentManager
					.getAttachmentCriteria(pagedRequests,filters);
			this.attachments = (List<Attachment>) pagedRequests.getList();
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
			 if(OtherUtil.measureNull(attachment.getMaker().getPersonId()))
				 attachment.setMaker(null);
			attachmentManager.save(attachment);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "attachment.added" : "attachment.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	attachment = attachmentManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	attachment = new Attachment();
        	if(type!=null){
        		attachment.setType(type);
        		attachment.setForeignKey(foreignKey);
        	}
        	attachment.setMakeDate(new Date());
        	//attachment.setMakeDate((Date) (new DateConverter().convert(Date.class,this.getCurrentSystemVariable().getBusinessDate())));
        	attachment.setMaker(this.getSessionUser().getPerson());
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    public String show(){
    	return SUCCESS;
    }
    
	@SuppressWarnings("deprecation")
	public String attachmentGridEdit() {
		try {
			if (oper.equals("del")) {
				List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id,",");
				String serverPath = "//home//attachment//";
			    serverPath = this.getRequest().getRealPath(serverPath);
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					attachment=attachmentManager.get(removeId);
				     String filePath = serverPath + "\\" + attachment.getPath();
				     File img = new File(filePath);
				     img.delete();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.attachmentManager.remove(ida);
				gridOperationMessage = this.getText("attachment.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkAttachmentGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (attachment == null) {
			return "Invalid attachment Data";
		}

		return SUCCESS;

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}
	
	//附件上传和删除
    private String Filename;

    public void setFilename(String filename) {
     Filename = filename;
    }

    private File attachmentFile;

    public File getAttachmentFile() {
     return attachmentFile;
    }

    public void setAttachmentFile(File attachmentFile) {
     this.attachmentFile = attachmentFile;
    }

    @SuppressWarnings("deprecation")
    public String uploadAttachmentFile() {
     HttpServletRequest req = this.getRequest();
     String fileName = this.Filename;
     if (fileName != null && !fileName.equals("")) {
      fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
     } else {
      fileName = (new Date()).getTime() + ".txt";
     }
     String serverPath = "//home//attachment//";
     serverPath = req.getRealPath(serverPath);
     OptFile.mkParent(serverPath + "\\" + fileName);
     File targetFile = new File(serverPath + "\\" + fileName);
     try {
      OptFile.copyFile(attachmentFile, targetFile);
     } catch (IOException e) {
      e.printStackTrace();
     }

     return this.ajaxForwardSuccess(fileName);
    }

    @SuppressWarnings("deprecation")
    public String deleteAttachmentFile() {
     String serverPath = "//home//attachment//";
     serverPath = this.getRequest().getRealPath(serverPath);
     String filePath = serverPath + "\\" + this.Filename;
     File img = new File(filePath);
     img.delete();
     return this.ajaxForwardSuccess("附件删除成功");
    }

	public String getFileFullPath() {
		return fileFullPath;
	}

	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}
	
	public String attachmentFileExist(){
		HttpServletRequest request = this.getRequest();
		String filePath = request.getParameter("filePath");
		File file = new File(filePath);
		if(!file.exists()){
			type="文件不存在";
		}else{
			type="";
		}
		return SUCCESS;
	}
}

