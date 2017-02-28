package com.huge.ihos.material.documenttemplate.webapp.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.ihos.material.documenttemplate.model.TemplateAssist;
import com.huge.ihos.material.documenttemplate.service.DocumentTemplateManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.util.PropertiesUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class DocumentTemplatePagedAction extends JqGridBaseAction implements Preparable {
	private static final long serialVersionUID = -3897392702271019697L;
	private DocumentTemplateManager documentTemplateManager;
	private List<DocumentTemplate> documentTemplates;
	private DocumentTemplate documentTemplate;
	private String id;
	private String templateType;
	
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public void setDocumentTemplateManager(
			DocumentTemplateManager documentTemplateManager) {
		this.documentTemplateManager = documentTemplateManager;
	}

	public List<DocumentTemplate> getDocumentTemplates() {
		return documentTemplates;
	}

	public DocumentTemplate getDocumentTemplate() {
		return documentTemplate;
	}

	public void setDocumentTemplate(DocumentTemplate documentTemplate) {
		this.documentTemplate = documentTemplate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	private List<String> templateTypes;
	
	public List<String> getTemplateTypes() {
		return templateTypes;
	}
	
	private static final PropertiesUtil docTemReader = new PropertiesUtil("com/huge/ihos/material/documenttemplate/webapp/package_docTem_zh_CN.properties");

	public void prepare() throws Exception {
		this.clearSessionMessages();
		String docType = docTemReader.getProperty("docType");
		String[] docTypes = docType.split(",");
		templateTypes = Arrays.asList(docTypes);
	}

	@SuppressWarnings("unchecked")
	public String documentTemplateGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = documentTemplateManager.getDocumentTemplateCriteria(pagedRequests, filters);
			this.documentTemplates = (List<DocumentTemplate>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			documentTemplate.setInputArea(documentTemplate.getInputArea().replaceAll(" +", ""));
			documentTemplate.setInputAreaValue(documentTemplate.getInputAreaValue().replaceAll(" +", ""));
			documentTemplate.setListArea(documentTemplate.getListArea().replaceAll(" +", ""));
			documentTemplate.setListAreaValue(documentTemplate.getListAreaValue().replaceAll(" +", ""));
			documentTemplate.setFootArea(documentTemplate.getFootArea().replaceAll(" +", ""));
			documentTemplate.setFootAreaValue(documentTemplate.getFootAreaValue().replaceAll(" +", ""));
			documentTemplateManager.save(documentTemplate);
		} catch (Exception dre) {
			log.error("save Error", dre);
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "documentTemplate.added"
				: "documentTemplate.updated";
		return ajaxForward(this.getText(key));
	}

	private List<TemplateAssist> inputs;
	private List<TemplateAssist> lists;
	private List<TemplateAssist> foots;
	
	public List<TemplateAssist> getInputs() {
		return inputs;
	}

	public List<TemplateAssist> getLists() {
		return lists;
	}

	public List<TemplateAssist> getFoots() {
		return foots;
	}

	public String edit() {
		if(OtherUtil.measureNull(templateType)){
			templateType = "期初入库单";
		}
		inputs = getDocTemAllSets(templateType+"_input");
		lists = getDocTemAllSets(templateType+"_list");
		foots = getDocTemAllSets(templateType+"_foot");
		if (id != null) {
			documentTemplate = documentTemplateManager.get(id);
			String[] iareas = documentTemplate.getInputArea().split(",");
			String[] ivalues = documentTemplate.getInputAreaValue().split(",");
			for (TemplateAssist ss : inputs) {
				ss.setChecked(false);
				for (int i=0;i<ivalues.length;i++) {
					if(ss.getValue().equals(ivalues[i].trim())){
						ss.setChecked(true);
						ss.setName(iareas[i]);
						break;
					}
				}
			}
			String[] lareas = documentTemplate.getListArea().split(",");
			String[] lvalues = documentTemplate.getListAreaValue().split(",");
			for (TemplateAssist ss : lists) {
				ss.setChecked(false);
				for (int i=0;i<lvalues.length;i++) {
					if(ss.getValue().equals(lvalues[i].trim())){
						ss.setChecked(true);
						ss.setName(lareas[i]);
						break;
					}
				}
			}
			String[] fareas = documentTemplate.getFootArea().split(",");
			String[] fvalues = documentTemplate.getFootAreaValue().split(",");
			for (TemplateAssist ss : foots) {
				ss.setChecked(false);
				for (int i=0;i<fvalues.length;i++) {
					if(ss.getValue().equals(fvalues[i].trim())){
						ss.setChecked(true);
						ss.setName(fareas[i]);
						break;
					}
				}
			}
			this.setEntityIsNew(false);
		} else {
			documentTemplate = new DocumentTemplate();
			UserContext userContext = UserContextUtil.getUserContext();
			documentTemplate.setOrgCode(userContext.getOrgCode());
			documentTemplate.setCopyCode(userContext.getCopyCode());
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	/**
	 * 获取对应模板类型的数据
	 * @return
	 */
	public String getDocTemDatas(){
		inputs = getDocTemAllSets(templateType+"_input");
		lists = getDocTemAllSets(templateType+"_list");
		foots = getDocTemAllSets(templateType+"_foot");
		return SUCCESS;
	}
	/**
	 * 预览模板
	 * @return
	 */
	public String preview() {
		documentTemplate = documentTemplateManager.get(id);
		templateType = documentTemplate.getTemplateType();
		String redirectURL = docTemReader.getProperty(templateType+"_editURL");
		this.setForwardUrl(redirectURL);
		return SUCCESS;
	}

	private boolean exists = false;
	
	public boolean getExists(){
		return this.exists;
	}
	
	public String checkTemplateName(){
		String name = this.getRequest().getParameter("templateName");
		String type = this.getRequest().getParameter("templateType");
		/*HashMap<String,String> condition = new HashMap<String,String>();
		condition.put("orgCode", this.getCurrentSystemVariable().getOrgCode());
		condition.put("copyCode", this.getCurrentSystemVariable().getCopyCode());
		condition.put("templateName", name);
		condition.put("templateType", type);*/
		UserContext userContext = UserContextUtil.getUserContext();
		DocumentTemplate documentTemplateFind = new DocumentTemplate();
		documentTemplateFind.setOrgCode(userContext.getOrgCode());
		documentTemplateFind.setCopyCode(userContext.getCopyCode());
		documentTemplateFind.setTemplateName(name);
		documentTemplateFind.setTemplateType(type);
		exists = documentTemplateManager.existByExample(documentTemplateFind);
		//exists = documentTemplateManager.existCode("mm_document_template", condition);
		return SUCCESS;
	}
	
	public String documentTemplateGridEdit() {
		try {
			StringTokenizer ids = new StringTokenizer(id, ",");
			if (oper.equals("del")) {//delete check docTemp is used by doc
				List<String> idl = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					documentTemplate = documentTemplateManager.get(removeId);
					templateType = documentTemplate.getTemplateType();
					String tableName = docTemReader.getProperty(templateType+"_table");
					boolean isUsed = documentTemplateManager.isUsedByDoc(removeId,tableName);
					if(isUsed){
						return ajaxForward(false, "模板 ["+documentTemplate.getTemplateName()+"] 已被单据引用,不能删除！", false);
					}
					idl.add(removeId);
				}
				String[] ida = new String[idl.size()];
				idl.toArray(ida);
				this.documentTemplateManager.remove(ida);
				gridOperationMessage = this.getText("documentTemplate.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			if (oper.equals("enable")) {
				documentTemplates = new ArrayList<DocumentTemplate>();
				while (ids.hasMoreTokens()) {
					String useId = ids.nextToken();
					documentTemplate = documentTemplateManager.get(useId);
					// 判断同一类型的单据是否有正在使用的，如果有则提示错误不允许修改，如果没有则update
					UserContext userContext = UserContextUtil.getUserContext();
					boolean using = documentTemplateManager
							.isUsedInSameType(documentTemplate
									.getTemplateType(),userContext.getOrgCode(),userContext.getCopyCode());
					if (using) {
						return ajaxForward(false, "同一类型的单据不允许使用两种模板！", false);
					} else {
						documentTemplate.setBeUsed(true);
						documentTemplate.setUseDate(userContext.getBusinessDate());
						documentTemplates.add(documentTemplate);
					}
				}
				documentTemplateManager.saveAll(documentTemplates);
				return ajaxForward(true, "启用模板成功！", false);
			}
			if (oper.equals("disable")) {
				documentTemplates = new ArrayList<DocumentTemplate>();
				while (ids.hasMoreTokens()) {
					String useId = ids.nextToken();
					documentTemplate = documentTemplateManager.get(useId);
					documentTemplate.setBeUsed(false);
					documentTemplate.setUseDate(null);
					documentTemplates.add(documentTemplate);
				}
				documentTemplateManager.saveAll(documentTemplates);
				return ajaxForward(true, "停用模板成功！", false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkDocumentTemplateGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (documentTemplate == null) {
			return "Invalid documentTemplate Data";
		}
		return SUCCESS;

	}
	
	private List<TemplateAssist> getDocTemAllSets(String key){
		List<TemplateAssist> result = new ArrayList<TemplateAssist>();
		String str = docTemReader.getProperty(key);
		String[] strArr = str.split(",");
		String[] value = null;
		String dbValue = null;
		for(String s:strArr){
			value = s.split("_");
			if(key.endsWith("_input")){
				dbValue = value[0]+"_"+value[2]+"_"+value[3];
			}else{
				dbValue = value[0];
			}
			result.add(new TemplateAssist(dbValue,value[1],Boolean.parseBoolean(value[2])));
		}
		if(key.endsWith("foot")){
			result.add(new TemplateAssist("sign","签字人",false));
		}
		return result;
	}
}
