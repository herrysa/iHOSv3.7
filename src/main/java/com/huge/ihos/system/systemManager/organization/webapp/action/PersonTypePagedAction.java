package com.huge.ihos.system.systemManager.organization.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.util.JSONUtils;

import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.ihos.system.systemManager.organization.service.PersonTypeManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;
import com.opensymphony.xwork2.Preparable;



@SuppressWarnings("serial")
public class PersonTypePagedAction extends JqGridBaseAction implements Preparable {

	private PersonTypeManager personTypeManager;
	private List<PersonType> personTypes;
	private PersonType personType;
	private String id;
	private String parentId;

	public void setPersonTypeManager(PersonTypeManager personTypeManager) {
		this.personTypeManager = personTypeManager;
	}

	public List<PersonType> getPersonTypes() {
		return personTypes;
	}

	public void setPersonTypes(List<PersonType> personTypes) {
		this.personTypes = personTypes;
	}

	public PersonType getPersonType() {
		return personType;
	}

	public void setPersonType(PersonType personType) {
		this.personType = personType;
	}

	public void setPersonTypeTreeNodes(List<ZTreeSimpleNode> personTypeTreeNodes) {
		this.personTypeTreeNodes = personTypeTreeNodes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String personTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = personTypeManager
					.getPersonTypeCriteria(pagedRequests,filters);
			this.personTypes = (List<PersonType>) pagedRequests.getList();
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
			 if(OtherUtil.measureNull(personType.getParentType().getId()))
				 personType.setParentType(null);
			 personType=personTypeManager.savePersonType(personType);
			 parentId= OtherUtil.measureNull(personType.getParentType())?"-1":personType.getParentType().getId();
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "personType.added" : "personType.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	personType = personTypeManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	personType = new PersonType();
        	if(parentId!=null){
        		if(parentId.equals("-1")){
        			personType.setParentType(null);
        		}else{
        			personType.setParentType(personTypeManager.get(parentId));
        		}
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String personTypeGridEdit() {
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
				this.personTypeManager.deletePersonType(ida);
				gridOperationMessage = this.getText("personType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkHrPersonTypeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (personType == null) {
			return "Invalid personType Data";
		}

		return SUCCESS;

	}
	private List<ZTreeSimpleNode> personTypeTreeNodes;
	 
	public List<ZTreeSimpleNode> getPersonTypeTreeNodes() {
		return personTypeTreeNodes;
	}
	/* 
	 * 修改时间：2015/10/10
	 * 修改人：焦帅
	 * 修改原因：人员类别树按照类别编码排序
	 * 修改内容：原172行，现173——175行
	 */
	public String makePersonTypeTree(){
		personTypeTreeNodes=new ArrayList<ZTreeSimpleNode>();
		try{
			//personTypes=personTypeManager.getAll();
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("OAS_code","code"));
			personTypes = personTypeManager.getByFilters(filters);
			ZTreeSimpleNode rootNode = new ZTreeSimpleNode();
			rootNode.setId("-1");
			rootNode.setName("人员类别");
			rootNode.setpId(null);
			rootNode.setIsParent(true);
			rootNode.setSubSysTem("ALL");
			//rootNode.setIcon(iconPath+"category.png");
			personTypeTreeNodes.add(rootNode);
			if(personTypes!=null&&personTypes.size()>0){
				for(PersonType personTypeTemp:personTypes){
					ZTreeSimpleNode typeNode = new ZTreeSimpleNode();
					typeNode.setId(personTypeTemp.getId());
					typeNode.setName(personTypeTemp.getName());
					typeNode.setIsParent(false);
					typeNode.setSubSysTem("TYPE");
					//typeNode.setIcon(iconPath+"category.png");
					if(personTypeTemp.getParentType()!=null){
						typeNode.setpId(personTypeTemp.getParentType().getId());
					}else{
						typeNode.setpId("-1");
					}
					personTypeTreeNodes.add(typeNode);
				}
			}
		}catch(Exception e){
			 log.error("makePersonTypeTree Error", e);
		}
		return SUCCESS;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String personTypeSupcanList(){
		 try {
	            HttpServletResponse response = this.getResponse();
	            response.setCharacterEncoding( "UTF-8" );
	            String responsestr = "{\"Record\": [";
	            List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
	            filters.add(new PropertyFilter("EQB_disabled","0"));
	            filters.add(new PropertyFilter("EQB_leaf","1"));
	            filters.add(new PropertyFilter("OAS_code",""));
	            this.personTypes = personTypeManager.getByFilters(filters);
	            if(OtherUtil.measureNotNull(personTypes)&&!personTypes.isEmpty()){
	            	for(PersonType ptTemp:personTypes){
	            		responsestr += "{";
	            		if(OtherUtil.measureNull(ptTemp.getParentType())){
	            			responsestr += "\"pName\":\"\",";
	            		}else{
	            			responsestr += "\"pName\":\""+ ptTemp.getParentType().getName() +"\",";
	            		}
	            		responsestr += "\"id\":\""+ ptTemp.getId() +"\",";
	            		responsestr += "\"code\":\""+ ptTemp.getCode() +"\",";
	            		responsestr += "\"name\":\""+ ptTemp.getName() +"\"},";
	            	}
	            	responsestr = OtherUtil.subStrEnd(responsestr, ",");
	            }
	            responsestr += "]}";
	            response.getWriter().write( responsestr );
	        }
	        catch ( Exception e ) {
	            e.printStackTrace();
	        }
	        return null;
	}
}

