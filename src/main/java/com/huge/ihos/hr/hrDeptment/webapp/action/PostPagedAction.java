package com.huge.ihos.hr.hrDeptment.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.PostManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class PostPagedAction extends JqGridBaseAction implements Preparable {
	
	private PostManager postManager;
	private List<Post> posts;
	private Post post;
	private String id;
	private String deptId;
	private String deptName;
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;
	private HrOrgManager hrOrgManager;
	private HrPersonCurrentManager hrPersonCurrentManager;
	
	public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
	}

	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

	public void setPostManager(PostManager postManager) {
		this.postManager = postManager;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
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
	
	public String postList(){
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter postList error:", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String postGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			String orgCodes = hrOrgManager.getAllAvailableString();
			if(orgCodes==null){
				orgCodes = "";
			}
			filters.add(new PropertyFilter("INS_hrDept.orgCode",orgCodes));
			
			String orgCode = request.getParameter("orgCode");
			String deptIds = hrDepartmentCurrentManager.getAllDeptIds(orgCode, deptId);
			if(deptIds!=null){
				filters.add(new PropertyFilter("INS_hrDept.departmentId",deptIds));
			}
			String showDisabled = request.getParameter("showDisabled");
			if(OtherUtil.measureNull(showDisabled)){
				filters.add(new PropertyFilter("EQB_hrDept.disabled","0"));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = postManager.getPostCriteria(pagedRequests,filters);
			this.posts = (List<Post>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("postGridList Error", e);
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
			post=postManager.save(post);
			deptId=post.getHrDept().getDepartmentId();
			deptName=post.getHrDept().getName();
		} catch (Exception dre) {
			log.error("save Post error:",dre);
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "post.added" : "post.updated";
		return ajaxForward(this.getText(key));
	}
	private List<String> directSupervisorList;
	
    public List<String> getDirectSupervisorList() {
		return directSupervisorList;
	}
    
    private String deptIdStr;
	public String edit() {
        if (id != null) {
        	post = postManager.get(id);
        	deptId = post.getHrDept().getDepartmentId();
        	this.setEntityIsNew(false);
        } else {
        	post = new Post();
        	if(deptId!=null){
        		HrDepartmentCurrent hrDept = this.hrDepartmentCurrentManager.get(deptId);
        		post.setCodeSn(this.postManager.getPostCodeSn(deptId));
        		post.setDeptSnapCode(hrDept.getSnapCode());
        		post.setHrDept(hrDept);
        	}
        	this.setEntityIsNew(true);
        }
        /*
         * 直接上级
         */
        HrDepartmentCurrent hrDept = this.hrDepartmentCurrentManager.get(deptId);
        Map<String,HrDepartmentCurrent> map = new HashMap<String,HrDepartmentCurrent>();
//        map.put(deptId, hrDept);
		map = getOnlyParentDept(map,hrDept);
		map.put(deptId, hrDept);
		String deptIds = "";
		Set<String> deptIdSet = map.keySet();
		if(!deptIdSet.isEmpty()){
			for(String deptId:deptIdSet){
				deptIds += deptId+",";
			}
			deptIds = OtherUtil.subStrEnd(deptIds, ",");
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_hrDept.departmentId",deptIds));
		if(OtherUtil.measureNotNull(id)){
			filters.add(new PropertyFilter("NES_id",id));
		}
		posts = this.postManager.getByFilters(filters);
		directSupervisorList = new ArrayList<String>();
		if(posts!=null && !posts.isEmpty()){
			for(Post post:posts){
				directSupervisorList.add(post.getName());
			}
		}
		/*需要校验岗位的部门*/
		map.clear();
		map.put(deptId, hrDept);
		if(hrDept.getClevel()!=1){
			map = getParentDept(map,hrDept);
		}
		deptIds = "";
		deptIdSet = map.keySet();
		for(String deptId:deptIdSet){
			deptIds += deptId+",";
		}
		deptIds = OtherUtil.subStrEnd(deptIds, ",");
		deptIdStr = deptIds;
        return SUCCESS;
    }
	private Map<String,HrDepartmentCurrent> getOnlyParentDept(Map<String,HrDepartmentCurrent> hrDeptMap,HrDepartmentCurrent hrDept){
		HrDepartmentCurrent parentDept = hrDept.getParentDept();
		if(parentDept==null){
			return hrDeptMap;
		}else{
			hrDeptMap.put(parentDept.getDepartmentId(), parentDept);
			hrDeptMap = getParentDept(hrDeptMap,parentDept);
		}
		return hrDeptMap;
	}
	public String postGridEdit() {
		try {
			if (oper.equals("del")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					idl.add(removeId);
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.postManager.remove(ida);
				gridOperationMessage = this.getText("post.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("enable")){
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					post = this.postManager.get(removeId);
					post.setDisabled(false);
					postManager.save(post);
				}
				gridOperationMessage = this.getText("岗位启用成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("disable")){
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					post = this.postManager.get(removeId);
					post.setDisabled(true);
					postManager.save(post);
				}
				gridOperationMessage = this.getText("岗位停用成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPostGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String checkPostName(){
		String postName = this.getRequest().getParameter("postName");
		Map<String,HrDepartmentCurrent> map = new HashMap<String,HrDepartmentCurrent>();
		HrDepartmentCurrent hrDept = hrDepartmentCurrentManager.get(deptId);
		map.put(deptId, hrDept);
		if(hrDept.getClevel()!=1){
			map = getParentDept(map,hrDept);
		}
		String deptIds = "";
		Set<String> deptIdSet = map.keySet();
		for(String deptId:deptIdSet){
			deptIds += deptId+",";
		}
		deptIds = OtherUtil.subStrEnd(deptIds, ",");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_hrDept.departmentId",deptIds));
		posts = this.postManager.getByFilters(filters);
		boolean exist = false;
		if(posts!=null && !posts.isEmpty()){
			for(Post post:posts){
				if(postName.equals(post.getName())){
					exist = true;
					break;
				}
			}
		}
		if(exist){
			return ajaxForward(false, "此岗位名称已存在。", false);
		}else{
			return null;
		}
	}
	
	public String checkPostRemovable(){
		if(id!=null){
		   Boolean removable=postManager.postRemovable(id);
		   if(removable){
			   return null;
		   }else{
			   return ajaxForward(false, "此岗位已使用。", false); 
		   }
		}
		return null;
	}
	
	private Map<String,HrDepartmentCurrent> getParentDept(Map<String,HrDepartmentCurrent> hrDeptMap,HrDepartmentCurrent hrDept){
		HrDepartmentCurrent parentDept = hrDept.getParentDept();
		if(parentDept==null){
			return hrDeptMap;
		}else if(parentDept.getClevel()==1){
			hrDeptMap.put(parentDept.getDepartmentId(), parentDept);
			/*List<HrDepartmentCurrent> hrDeptList = hrDepartmentCurrentManager.getAllDescendants(parentDept.getDepartmentId());
			if(hrDeptList!=null && !hrDeptList.isEmpty()){
				for(HrDepartmentCurrent dept:hrDeptList){
					if(hrDeptMap.get(dept.getDepartmentId())==null){
						hrDeptMap.put(dept.getDepartmentId(), dept);
					}
				}
			}*/
		}else{
			hrDeptMap = getParentDept(hrDeptMap,parentDept);
		}
		return hrDeptMap;
	}
	
	public String checkPostIsUsing(){
		String postId = this.getRequest().getParameter("postId");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_postType.id",postId));
		List<HrPersonCurrent> hrPersons = hrPersonCurrentManager.getByFilters(filters);
		if(hrPersons!=null && !hrPersons.isEmpty()){
			return ajaxForward(false, "此岗位正在被人员使用。", false);
		}else{
			return null;
		}
	}
	
	public List<ZTreeSimpleNode> nodes;
	
	public List<ZTreeSimpleNode> getNodes() {
		return nodes;
	}

	public String getPostForRecruitNeed(){
		String oldPostId = this.getRequest().getParameter("oldPostId");
		Map<String,HrDepartmentCurrent> map = new HashMap<String,HrDepartmentCurrent>();
		HrDepartmentCurrent hrDept = hrDepartmentCurrentManager.get(deptId);
		map.put(deptId, hrDept);
		if(hrDept.getClevel()!=1){
			map = getParentDept(map,hrDept);
		}
		String deptIds = "";
		Set<String> deptIdSet = map.keySet();
		for(String deptId:deptIdSet){
			deptIds += deptId+",";
		}
		deptIds = OtherUtil.subStrEnd(deptIds, ",");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_hrDept.departmentId",deptIds));
		filters.add(new PropertyFilter("EQB_disabled", "0"));
		filters.add(new PropertyFilter("OAS_code",""));
		posts = this.postManager.getByFilters(filters);
		nodes = new ArrayList<ZTreeSimpleNode>();
		if(posts!=null && !posts.isEmpty()){
			ZTreeSimpleNode node = null;
			for(Post post:posts){
				if(post.getId().equals(oldPostId)){
					continue;
				}
				node = new ZTreeSimpleNode();
				node.setId(post.getId());
				node.setName(post.getName());
				node.setIsParent(false);
				nodes.add(node);
			}
		}
		return SUCCESS;
	}
	
	public String getPostForDept(){
		nodes = new ArrayList<ZTreeSimpleNode>();
		if(deptId!=null&&!deptId.equals("")){
			String deptIds = deptId+",";
			List<HrDepartmentCurrent> hrDeptList = hrDepartmentCurrentManager.getAllDescendants(deptId);
			if(hrDeptList!=null&&hrDeptList.size()>0){
				for(HrDepartmentCurrent hrDepartmentCurrent:hrDeptList){
					deptIds += hrDepartmentCurrent.getDepartmentId()+",";
				}
			}
			deptIds = OtherUtil.subStrEnd(deptIds, ",");
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("INS_hrDept.departmentId",deptIds));
			filters.add(new PropertyFilter("OAS_name",""));
			posts = this.postManager.getByFilters(filters);
			List<String> postNames=new ArrayList<String>();
			if(posts!=null && !posts.isEmpty()){
				for(Post post:posts){
					if(!postNames.contains(post.getName())){
						postNames.add(post.getName());
					}
				}
			}
			if(postNames.size()>0){
				ZTreeSimpleNode node = null;
				for(String str:postNames){
					node = new ZTreeSimpleNode();
					node.setId(str);
					node.setName(str);
					node.setIsParent(false);
					nodes.add(node);
				}
			}
		}
		return SUCCESS;
	}
	
	public String getPostForOrg(){
		nodes = new ArrayList<ZTreeSimpleNode>();
		String orgCode = this.getRequest().getParameter("orgCode");
		if(orgCode!=null&&!orgCode.equals("")){
			String deptIds = "";
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_orgCode",orgCode));
			List<HrDepartmentCurrent> hrDeptList = hrDepartmentCurrentManager.getByFilters(filters);
			if(hrDeptList!=null&&hrDeptList.size()>0){
				for(HrDepartmentCurrent hrDepartmentCurrent:hrDeptList){
					deptIds += hrDepartmentCurrent.getDepartmentId()+",";
				}
			}
			deptIds = OtherUtil.subStrEnd(deptIds, ",");
			filters.clear();
			filters.add(new PropertyFilter("INS_hrDept.departmentId",deptIds));
			filters.add(new PropertyFilter("OAS_name",""));
			posts = this.postManager.getByFilters(filters);
			List<String> postNames=new ArrayList<String>();
			if(posts!=null && !posts.isEmpty()){
				for(Post post:posts){
					if(!postNames.contains(post.getName())){
						postNames.add(post.getName());
					}
				}
			}
			if(postNames.size()>0){
				ZTreeSimpleNode node = null;
				for(String str:postNames){
					node = new ZTreeSimpleNode();
					node.setId(str);
					node.setName(str);
					node.setIsParent(false);
					nodes.add(node);
				}
			}
		}
		return SUCCESS;
	}
	
	private String isValid() {
		if (post == null) {
			return "Invalid post Data";
		}
		return SUCCESS;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptIdStr() {
		return deptIdStr;
	}

	public void setDeptIdStr(String deptIdStr) {
		this.deptIdStr = deptIdStr;
	}
}

