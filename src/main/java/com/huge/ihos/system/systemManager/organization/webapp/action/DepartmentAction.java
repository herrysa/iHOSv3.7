package com.huge.ihos.system.systemManager.organization.webapp.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huge.ihos.system.datacollection.model.InterLogger;
import com.huge.ihos.system.datacollection.service.InterLoggerManager;
import com.huge.ihos.system.exinterface.ProxySynchronizeToHr;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.DeptTreeNode;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.DeptTypeManager;
import com.huge.ihos.system.systemManager.organization.service.KhDeptTypeManager;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.jgeppert.struts2.jquery.tree.result.TreeNode;

/*@ParentPackage(value = "default")*/
public class DepartmentAction
    extends JqGridBaseAction
     {
    private DepartmentManager departmentManager;
    private KhDeptTypeManager khDeptTypeManager;
    private OrgManager orgManager;
    private BranchManager branchManager;

    private List departments;
    private List<String> departmentAll;

    private Department department;

    private String departmentId;
    private String sysType;



    public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	private List deptClassList;

    private List outinList;

    private List subClassList;
    
    private List jjDeptTypeList;
    
    private List dgroupList;
    
    private String autoPrefixId;
    
    private DeptTreeNode deptTreeNode;



    private DeptTypeManager deptTypeManager;
    



    /**
     * Grab the entity from the database before populating with request parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        this.deptClassList = this.deptTypeManager.getAllExceptDisable();
        this.outinList = this.getDictionaryItemManager().getAllItemsByCode( "outin" );
        this.subClassList = this.getDictionaryItemManager().getAllItemsByCode( "subClass" );
        this.jjDeptTypeList = khDeptTypeManager.getAllExceptDisable();
        this.dgroupList = this.getDictionaryItemManager().getAllItemsByCode("dgroup");
        this.clearSessionMessages();
    }
    
    private List<Branch> branches = new ArrayList<Branch>();
    
    public List<Branch> getBranches() {
		return branches;
	}
    
    public String departmentList(){
    	try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			//menuButtons.get(0).setEnable(false);
			setMenuButtonsToJson(menuButtons);
			this.branches = branchManager.getAllAvailable();
		} catch (Exception e) {
			log.error("enter departmentList error:",e);
		}
    	return SUCCESS;
    }


    public void setDepartmentId( String departmentId ) {
        this.departmentId = departmentId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment( Department department ) {
        this.department = department;
    }

    public String delete() {
        departmentManager.remove( department.getDepartmentId() );
        saveMessage( getText( "department.deleted" ) );

        return "edit_success";
    }

    private int editType = 0; // 0:new,1:edit

    public void setEditType( int editType ) {
        this.editType = editType;
    }

    public int getEditType() {
        return editType;
    }
    
    private boolean inUse;//true:正在使用;false:没有使用 
    
    public boolean getInUse(){
    	return inUse;
    }
    
    public String getAutoPrefixId() {
		return autoPrefixId;
	}

	public void setAutoPrefixId(String autoPrefixId) {
		this.autoPrefixId = autoPrefixId;
	}

	public String edit() {
    	HttpServletRequest req = this.getRequest();
    	String orgCode = req.getParameter("orgCode");
		String pDeptId = req.getParameter("pDeptId");
		this.autoPrefixId = this.getGlobalParamByKey("autoPrefixId");
        if ( departmentId != null ) {
            department = departmentManager.get( departmentId );
            inUse = departmentManager.isInUse(departmentId);
            this.editType = 1;
            this.setEntityIsNew( false );
        }
        else {
        	department = new Department();
        	if(OtherUtil.measureNotNull(orgCode)){
        		department.setOrg(orgManager.get(orgCode));
        	}
        	if(OtherUtil.measureNotNull(pDeptId)){
        		Department pdDepartment = departmentManager.get(pDeptId);
        		department.setParentDept(pdDepartment);
        		department.setOrg(pdDepartment.getOrg());
        	}
            this.editType = 0;
            this.setEntityIsNew( true );
        }
        this.branches = branchManager.getAllAvailable();
        return SUCCESS;
    }

    public String save()
        throws Exception {
        if ( cancel != null ) {
            return "cancel";
        }

        if ( delete != null ) {
            return delete();
        }

        boolean isNew = ( department.getDepartmentId() == null );
        /*	String pid = department.getParentDept().getDepartmentId();
        	if (pid != null && !pid.trim().equalsIgnoreCase("")) {
        		// User tempUser = this.userManager.get(user.getId());
        		department.setParentDept(this.departmentManager.get(pid));
        		//if (!isNew) {
        		//	user.setPassword(tempUser.getPassword());
        		//}
        	}else{
        		
        		department.setParentDept(new Department());
        		
        	}*/
        if(OtherUtil.measureNull(department.getJjDept().getDepartmentId()))
        	department.getJjDept().setDepartmentId(department.getDepartmentId());
        if(OtherUtil.measureNull(department.getYsDept().getDepartmentId()))
        	department.getYsDept().setDepartmentId(department.getDepartmentId());
        if(OtherUtil.measureNull(department.getParentDept().getDepartmentId()))
        	department.setParentDept(null);
        if(OtherUtil.measureNull(department.getYjDept().getDepartmentId())) {
        	department.setYjDept(null);
        }
        if(OtherUtil.measureNull(department.getBranchCode())) {
        	department.setBranch(null);
        	department.setBranchCode(null);
        }
        if(department.getParentDept()!=null){
        	Department pDepartment = departmentManager.get(department.getParentDept().getDepartmentId());
        	if(pDepartment.getLeaf()){
        		return ajaxForward( false, "当前科室的父级科室为末级科室，不能添加科室!", false );
        	}
        }
        if(!isNew){
        	boolean leaf = department.getLeaf();
        	if(leaf && departmentManager.hasChildren(department.getDepartmentId())){
        		 return ajaxForward( false, "当前科室下有子级，不能修改末级标志!", false );
        	}
        }
        String jjDeptId = department.getJjDept().getDepartmentId();
        String ysDeptId = department.getYsDept().getDepartmentId();
        ProxySynchronizeToHr proxySynchronizeToHr = new ProxySynchronizeToHr();
        if(this.getYearStarted()&&!proxySynchronizeToHr.deptInHr(jjDeptId)){
        	 return ajaxForward( false, "当前部门的奖金部门不是HR系统中的部门!", false );
        }
        if(this.getYearStarted()&&!proxySynchronizeToHr.deptInHr(ysDeptId)){
        	 return ajaxForward( false, "当前部门的预算部门不是HR系统中的部门!", false );
        }
        //departmentManager.save( department );
        Department deptTemp = departmentManager.saveDepartment(department, this.getYearStarted(),this.getSessionUser().getPerson(),new Date());
        String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
        deptTreeNode = new DeptTreeNode();
        deptTreeNode.setId(deptTemp.getDepartmentId());
        deptTreeNode.setCode(deptTemp.getDeptCode());
		deptTreeNode.setName(deptTemp.getName());
		deptTreeNode.setNameWithoutPerson(deptTemp.getName());
		deptTreeNode.setpId(deptTemp.getParentDept()==null?deptTemp.getOrgCode():deptTemp.getParentDept().getDepartmentId());
		deptTreeNode.setIsParent(!deptTemp.getLeaf());
		deptTreeNode.setSubSysTem("DEPT");
		deptTreeNode.setActionUrl(deptTemp.getDisabled()?"1":"0");
		deptTreeNode.setIcon(iconPath+"dept.gif");
		deptTreeNode.setDeptCode(deptTemp.getDeptCode());
		deptTreeNode.setOrgCode(deptTemp.getOrgCode());
		deptTreeNode.setPersonCount(""+deptTemp.getPersonCount());
		deptTreeNode.setPersonCountD(""+deptTemp.getPersonCountD());
		deptTreeNode.setPersonCountP(""+deptTemp.getPersonCountP());
		deptTreeNode.setPersonCountDP(""+deptTemp.getPersonCountDP());
		deptTreeNode.setDisplayOrder(3);
        
        String key = ( this.isEntityIsNew() ) ? "department.added" : "department.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String departmentGridList() {
        try {
        	HttpServletRequest request = this.getRequest();
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );

            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            //pagedRequests = departmentManager.getDepartmentCriteria(pagedRequests);
            String orgCode = request.getParameter("orgCode");
            String deptId = request.getParameter("departmentId");
            String showDisabled = request.getParameter("showDisabled");
            if(OtherUtil.measureNotNull(orgCode)){
            	filters.add(new PropertyFilter("EQS_orgCode",orgCode));
            }
            if(OtherUtil.measureNotNull(deptId)){
            	List<Department> depts=departmentManager.getAllDescendants(deptId);
            	if(depts!=null&&!depts.isEmpty()){
            		for(Department deptTemp:depts){
            			deptId+=","+deptTemp.getDepartmentId();
            		}
            	}
				filters.add(new PropertyFilter("INS_departmentId",deptId));
			}
            if(OtherUtil.measureNull(showDisabled)){
				filters.add(new PropertyFilter("EQB_disabled","0"));
			}
            pagedRequests = departmentManager.getAppManagerCriteriaWithSearch( pagedRequests, Department.class, filters );
            this.departments = (List<Department>) pagedRequests.getList();
            List<Department> departmentAllTemp = departmentManager.getByFilters(filters);
            this.departmentAll = new ArrayList<String>();
            if(OtherUtil.measureNotNull(departmentAllTemp)&&!departmentAllTemp.isEmpty()){
            	for(Department deptTemp:departmentAllTemp){
            		departmentAll.add(deptTemp.getDepartmentId());
            	}
            }
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "departmentGridList Error", e );
        }
        return SUCCESS;
    }

    public String departmentGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    department = departmentManager.get(removeId);
                    if(!department.getDisabled()){
                    	return ajaxForward( false, "科室"+department.getName()+"停用后才能删除", false );
                    }
                    if(departmentManager.hasChildren(department.getDeptCode())){
                    	return ajaxForward( false, "科室"+department.getName()+"下有子级不能删除", false );
                    }
                    departmentManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "department.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                departmentManager.save( department );
                String key = ( oper.equals( "add" ) ) ? "department.added" : "department.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( false, gridOperationMessage, false );
        }
    }

    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( department == null ) {
            return "Invalid department Data";
        }

        return SUCCESS;

    }

    private TreeNode departTreeList;

    public TreeNode getDepartTreeList() {
        return this.departTreeList;
    }

    private String ztreeJson;

    public String getZtreejson() {
        JSONArray json;// = JSONArray.fromObject(items);
        List list = new ArrayList();
        Map item = new HashMap();
        item.put( "id", "1fdc" );//id属性  ，数据传递  
        item.put( "name", "namefdc" ); //name属性，显示节点名称  
        item.put( "isParent", false );//设置为父节点，这样所有最外层节点都是统一的图标，看起来会
        list.add( item );
        String jsonString;//="[{name:'pNode 3 - no child', isParent:true}]";
        json = JSONArray.fromObject( list );
        jsonString = json.toString();
        return jsonString;
    }

    /*	@Action(value = "/departmentTree", results = { @Result(name = "success", type = "json", params = {
     "root", "departTreeList" }) }) */
    /*	@Actions( { @Action(value = "/departmentTree", results = { 
     @Result(name = "success", type = "json", params = {
     "root", "departTreeList" 
     }) 
     }) })*/
    public String departmentTree() {
        departTreeList = this.departmentManager.getFullDepartmentList();

        return SUCCESS;
    }
    public void setDepartmentManager( DepartmentManager departmentManager ) {
        this.departmentManager = departmentManager;
    }

    public List getDepartments() {
        return departments;
    }
    public List getDeptClassList() {
        return deptClassList;
    }

    public List getOutinList() {
        return outinList;
    }

    public List getSubClassList() {
        return subClassList;
    }
    public DeptTypeManager getDeptTypeManager() {
        return deptTypeManager;
    }

    public void setDeptTypeManager( DeptTypeManager deptTypeManager ) {
        this.deptTypeManager = deptTypeManager;
    }

    public void setBranchManager(BranchManager branchManager) {
		this.branchManager = branchManager;
	}

	public List getJjDeptTypeList() {
		return jjDeptTypeList;
	}


	public void setJjDeptTypeList(List jjDeptTypeList) {
		this.jjDeptTypeList = jjDeptTypeList;
	}


	public void setKhDeptTypeManager(KhDeptTypeManager khDeptTypeManager) {
		this.khDeptTypeManager = khDeptTypeManager;
	}
	
	public List<DeptTreeNode> getDeptTreeNodes() {
		return deptTreeNodes;
	}

	public void setDeptTreeNodes(List<DeptTreeNode> deptTreeNodes) {
		this.deptTreeNodes = deptTreeNodes;
	}

	
	public List getDgroupList() {
		return dgroupList;
	}

	public DeptTreeNode getDeptTreeNode() {
		return deptTreeNode;
	}

	public void setDeptTreeNode(DeptTreeNode deptTreeNode) {
		this.deptTreeNode = deptTreeNode;
	}

	private List<DeptTreeNode> deptTreeNodes;
	
	public String makeDepartmentTree() {
		String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
		try{
			deptTreeNodes=new ArrayList<DeptTreeNode>();
			DeptTreeNode rootNode = new DeptTreeNode(),deptNode,orgNode;
			rootNode.setId("-1");
			rootNode.setName("组织机构");
			rootNode.setpId(null);
			rootNode.setIsParent(true);
			rootNode.setSubSysTem("ALL");
			rootNode.setActionUrl("0");// 此处的url只用来标识是否停用
			rootNode.setIcon(iconPath+"1_close.png");
			rootNode.setDisplayOrder(1);
			deptTreeNodes.add(rootNode);
			List<Org> orgs = orgManager.getAllAvailable();
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			List<Department> depts = null;
			String deptInOrg = "";
			if(OtherUtil.measureNotNull(orgs)&&!orgs.isEmpty()){
				for(Org orgTemp:orgs){
					int personCount = 0;//包含停用部门；包含停用人员
					int personCountD = 0;//不包含停用部门；包含停用人员
					int personCountP = 0;//包含停用部门；不包含停用人员
					int personCountDP = 0;//不包含停用部门；不包含停用人员
					if(orgTemp.getOrgCode().equals("XT")){
						continue;
					}
					filters.clear();
					filters.add(new PropertyFilter("NES_departmentId","XT"));
					filters.add(new PropertyFilter("EQS_orgCode",orgTemp.getOrgCode()));
					filters.add(new PropertyFilter("OAS_deptCode",""));
					depts=departmentManager.getByFilters(filters);
					if(depts!=null&&!depts.isEmpty()){
						for(Department deptTemp:depts){
							if( deptTemp.getLeaf()){
								personCount += deptTemp.getPersonCount();
								personCountP += deptTemp.getPersonCountWithOutDisabled();
								if(!deptTemp.getDisabled()){
									personCountD += deptTemp.getPersonCount();
									personCountDP += deptTemp.getPersonCountWithOutDisabled();
								}
							}
						}
						orgTemp.setPersonCount(personCount);
						orgTemp.setPersonCountD(personCountD);
						orgTemp.setPersonCountP(personCountP);
						orgTemp.setPersonCountDP(personCountDP);
						for(Department deptTemp:depts){
							if(deptTemp.getLeaf()==true&&(deptTemp.getPersonCount()>0)){
								deptTemp.setPersonCountP(deptTemp.getPersonCountWithOutDisabled());
								if(deptTemp.getDisabled()){
									deptTemp.setPersonCountD(0);
									deptTemp.setPersonCountDP(0);
								}else{
									deptTemp.setPersonCountD(deptTemp.getPersonCount());
									deptTemp.setPersonCountDP(deptTemp.getPersonCountWithOutDisabled());
								}
								setDeptPersonCount(deptTemp,deptTemp.getPersonCount(),deptTemp.getPersonCountD(),deptTemp.getPersonCountP(),deptTemp.getPersonCountDP());
							}
						}
						for(Department deptTemp:depts){
							deptInOrg += deptTemp.getDepartmentId()+",";
							deptNode = new DeptTreeNode();
							deptNode.setId(deptTemp.getDepartmentId());
							deptNode.setCode(deptTemp.getDeptCode());
							deptNode.setName(deptTemp.getName());
							deptNode.setNameWithoutPerson(deptTemp.getName());
							deptNode.setpId(deptTemp.getParentDept()==null?orgTemp.getOrgCode():deptTemp.getParentDept().getDepartmentId());
							deptNode.setIsParent(!deptTemp.getLeaf());
							deptNode.setSubSysTem("DEPT");
							deptNode.setActionUrl(deptTemp.getDisabled()?"1":"0");
							deptNode.setIcon(iconPath+"dept.gif");
							deptNode.setDeptCode(deptTemp.getDeptCode());
							deptNode.setOrgCode(deptTemp.getOrgCode());
							deptNode.setPersonCount(""+deptTemp.getPersonCount());
							deptNode.setPersonCountD(""+deptTemp.getPersonCountD());
							deptNode.setPersonCountP(""+deptTemp.getPersonCountP());
							deptNode.setPersonCountDP(""+deptTemp.getPersonCountDP());
							deptNode.setDisplayOrder(3);
							deptTreeNodes.add(deptNode);
						}
					}
				}
				for(Org orgTemp:orgs){
					if (orgTemp.getPersonCount() > 0) {
						setPersonCountInOrg(orgTemp, orgTemp.getPersonCount(),orgTemp.getPersonCountD(),orgTemp.getPersonCountP(),orgTemp.getPersonCountDP());
					}
				}
				for(Org orgTemp:orgs){
					orgNode = new DeptTreeNode();
					orgNode.setId(orgTemp.getOrgCode());
					orgNode.setCode(orgTemp.getOrgCode());
					orgNode.setName(orgTemp.getOrgname());
					orgNode.setNameWithoutPerson(orgTemp.getOrgname());
					orgNode.setpId(orgTemp.getParentOrgCode()==null?"-1":orgTemp.getParentOrgCode().getOrgCode());
					orgNode.setIsParent(orgTemp.getParentOrgCode()==null);
					orgNode.setSubSysTem("ORG");
					orgNode.setActionUrl(orgTemp.getDisabled()?"1":"0");
					orgNode.setIcon(iconPath+"1_close.png");
					orgNode.setOrgCode(orgTemp.getOrgCode());
					orgNode.setPersonCount(""+orgTemp.getPersonCount());
					orgNode.setPersonCountD(""+orgTemp.getPersonCountD());
					orgNode.setPersonCountP(""+orgTemp.getPersonCountP());
					orgNode.setPersonCountDP(""+orgTemp.getPersonCountDP());
					orgNode.setDisplayOrder(2);
					deptTreeNodes.add(orgNode);				
				}
			}
			
			deptInOrg +="XT";
			filters.clear();
			filters.add(new PropertyFilter("NIS_departmentId",deptInOrg));
			filters.add(new PropertyFilter("OAS_deptCode",""));
			depts=departmentManager.getByFilters(filters);
			if(depts!=null&&!depts.isEmpty()){
				for(Department deptTemp:depts){
					if(deptTemp.getLeaf()==true&&(deptTemp.getPersonCount()>0)){
						deptTemp.setPersonCountP(deptTemp.getPersonCountWithOutDisabled());
						if(deptTemp.getDisabled()){
							deptTemp.setPersonCountD(0);
							deptTemp.setPersonCountDP(0);
						}else{
							deptTemp.setPersonCountD(deptTemp.getPersonCount());
							deptTemp.setPersonCountDP(deptTemp.getPersonCountWithOutDisabled());
						}
						setDeptPersonCount(deptTemp,deptTemp.getPersonCount(),deptTemp.getPersonCountD(),deptTemp.getPersonCountP(),deptTemp.getPersonCountDP());
					}
				}
				for(Department deptTemp:depts){
					deptNode = new DeptTreeNode();
					deptNode.setId(deptTemp.getDepartmentId());
					deptNode.setCode(deptTemp.getDeptCode());
					deptNode.setName(deptTemp.getName());
					deptNode.setNameWithoutPerson(deptTemp.getName());
					deptNode.setpId(deptTemp.getParentDept()==null?"-1":deptTemp.getParentDept().getDepartmentId());
					deptNode.setIsParent(!deptTemp.getLeaf());
					deptNode.setSubSysTem("DEPT");
					deptNode.setActionUrl(deptTemp.getDisabled()?"1":"0");
					deptNode.setIcon(iconPath+"dept.gif");
					deptNode.setDeptCode(deptTemp.getDeptCode());
					deptNode.setOrgCode(deptTemp.getOrgCode());
					deptNode.setPersonCount(""+deptTemp.getPersonCount());
					deptNode.setPersonCountD(""+deptTemp.getPersonCountD());
					deptNode.setPersonCountP(""+deptTemp.getPersonCountP());
					deptNode.setPersonCountDP(""+deptTemp.getPersonCountDP());
					deptNode.setDisplayOrder(3);
					deptTreeNodes.add(deptNode);
				}
			}
			Collections.sort(deptTreeNodes, new Comparator<DeptTreeNode>(){
				@Override
				public int compare(DeptTreeNode node1, DeptTreeNode node2) {
					return node1.getDisplayOrder()-node2.getDisplayOrder();
				}
			});
			
	} catch (Exception e) {
		log.error("makeDepartmentTree Error", e);
	}
	return SUCCESS;
   }
	private void setDeptPersonCount(Department deptTemp,int addPersonNum,int addPersonNumD,int addPersonNumP,int addPersonNumDP){
		Department pDept = deptTemp.getParentDept();
		if(OtherUtil.measureNotNull(pDept)){
			int personCount = pDept.getPersonCount();
			int personCountD = pDept.getPersonCountD();
			int personCountP = pDept.getPersonCountP();
			int personCountDP = pDept.getPersonCountDP();
			pDept.setPersonCount(personCount+addPersonNum);
			pDept.setPersonCountD(personCountD+addPersonNumD);
			pDept.setPersonCountP(personCountP+addPersonNumP);
			pDept.setPersonCountDP(personCountDP+addPersonNumDP);
			setDeptPersonCount(pDept,addPersonNum,addPersonNumD,addPersonNumP,addPersonNumDP);
		}
	}
	private void setPersonCountInOrg(Org orgTemp,int addPersonNum,int addPersonNumD,int addPersonNumP,int addPersonNumDP){
		Org parentOrg = orgTemp.getParentOrgCode();
		if(OtherUtil.measureNotNull(parentOrg)){
			int personCount = parentOrg.getPersonCount();
			int personCountD = parentOrg.getPersonCountD();
			int personCountP = parentOrg.getPersonCountP();
			int personCountDP = parentOrg.getPersonCountDP();
			parentOrg.setPersonCount(personCount + addPersonNum);
			parentOrg.setPersonCountD(personCountD + addPersonNumD);
			parentOrg.setPersonCount(personCountP + addPersonNumP);
			parentOrg.setPersonCountD(personCountDP + addPersonNumDP);
			setPersonCountInOrg(parentOrg, addPersonNum, addPersonNumD,addPersonNumP,addPersonNumDP);
		}
	}
	public List<String> getDepartmentAll() {
		return departmentAll;
	}

	public void setDepartmentAll(List<String> departmentAll) {
		this.departmentAll = departmentAll;
	}

	public OrgManager getOrgManager() {
		return orgManager;
	}

	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}

	public String checkDepartmentName(){
		HttpServletRequest req = this.getRequest();
		String orgCode = req.getParameter("orgCode");
		String fieldName = req.getParameter("fieldName");
		String fieldValue = req.getParameter("fieldValue");
		String returnMessage = req.getParameter("returnMessage");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_orgCode",orgCode));
		filters.add(new PropertyFilter("EQS_"+fieldName,fieldValue));
		List<Department> depts = departmentManager.getByFilters(filters);
		if(depts!=null && depts.size()>0){
			return ajaxForward( false, returnMessage, false );
		}else{
			return null;
		}
	}
	
	public String getDeptById() {
		if(OtherUtil.measureNotNull(id)) {
			this.department = departmentManager.get(id);
		}
		return SUCCESS;
	}
	
	//硕正treeList
	public String departmentSupcanList(){
		 try {
	            HttpServletResponse response = this.getResponse();
	            response.setCharacterEncoding( "UTF-8" );
	            JSONArray jsonArray = new JSONArray();
	            List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
	            filters.add(new PropertyFilter("EQB_disabled","0"));
	            filters.add(new PropertyFilter("NES_departmentId","XT"));
	            filters.add(new PropertyFilter("OAS_deptCode",""));
	            List<Department> deptList = departmentManager.getByFilters(filters);
	            if(OtherUtil.measureNotNull(deptList)&&!deptList.isEmpty()){
	            	for(Department deptTemp:deptList){
	            		JSONObject jsonObject = new JSONObject();
	            		jsonObject.put("departmentId", deptTemp.getDepartmentId());
	            		jsonObject.put("name", deptTemp.getName());
	            		jsonObject.put("deptCode", deptTemp.getDeptCode());
	            		jsonObject.put("cnCode", deptTemp.getCnCode());
	            		if(OtherUtil.measureNull(deptTemp.getParentDept())){
	            			jsonObject.put("parentDept", "");
	            		}else{
	            			jsonObject.put("parentDept", deptTemp.getParentDept().getName());
	            		}
	            		if(OtherUtil.measureNotNull(deptTemp.getOrg())){
	            			jsonObject.put("orgCode", deptTemp.getOrg().getOrgCode());
	            			jsonObject.put("orgName", deptTemp.getOrg().getOrgname());
	            		}else{
	            			jsonObject.put("orgCode", "");
	            			jsonObject.put("orgName", "");
	            		}
	            		jsonArray.add(jsonObject);
	            	}
	            }
	            JSONObject responseObj = new JSONObject();
	            responseObj.put("Record", jsonArray);
	            response.getWriter().write( responseObj.toString() );
	        }
	        catch ( Exception e ) {
	            e.printStackTrace();
	        }
	        return null;
	}
	//检查部门合理性
	private InterLoggerManager interLoggerManager;
	public String checkDeptRationality(){
		String sql = "SELECT deptId,name,deptCode,orgCode,parentDept_id pId,jjdeptId jjId,ysdeptId ysId,jjdepttype jjTypeId FROM t_department WHERE deptId <> 'XT' ORDER BY deptCode";
		List<Map<String, Object>> deptList = departmentManager.getBySqlToMap(sql);
		if(OtherUtil.measureNotNull(deptList)&&!deptList.isEmpty()){
			List<Org> orgList = orgManager.getAll();
			List<String> orgCodeLsit = new ArrayList<String>();
			if(OtherUtil.measureNotNull(orgList)&&!orgList.isEmpty()){
				for(Org org:orgList){
					orgCodeLsit.add(org.getOrgCode());
				}
			}
			List<KhDeptType> khDeptTypes = khDeptTypeManager.getAll();
			List<String> khIdLsit = new ArrayList<String>();
			if(OtherUtil.measureNotNull(khDeptTypes)&&!khDeptTypes.isEmpty()){
				for(KhDeptType khDeptType:khDeptTypes){
					khIdLsit.add(khDeptType.getKhDeptTypeId());
				}
			}
			Map<String, String> deptMap = new HashMap<String, String>();
			List<String> orgErrorList = new ArrayList<String>();
			List<String> khErrorList = new ArrayList<String>();
			List<String> pDeptErrorList = new ArrayList<String>();
			List<String> jjDeptErrorList = new ArrayList<String>();
			List<String> ysDeptErrorList = new ArrayList<String>();
			for(Map<String, Object> map:deptList){
				String deptId = map.get("deptId").toString();
				String name = map.get("name").toString();
				if(OtherUtil.measureNull(map.get("orgCode"))||
						!orgCodeLsit.contains(map.get("orgCode").toString())){
					orgErrorList.add(deptId);
				}
				if(OtherUtil.measureNull(map.get("jjTypeId"))||
						!khIdLsit.contains(map.get("jjTypeId").toString())){
					khErrorList.add(deptId);
				}
				deptMap.put(deptId, name);
			}
			for(Map<String, Object> map:deptList){
				String deptId = map.get("deptId").toString();
				if(OtherUtil.measureNotNull(map.get("pId"))&&
						!deptMap.containsKey(map.get("pId").toString())){
					pDeptErrorList.add(deptId);
				}
				if(OtherUtil.measureNotNull(map.get("jjId"))&&
						!deptMap.containsKey(map.get("jjId").toString())){
					jjDeptErrorList.add(deptId);
				}
				if(OtherUtil.measureNotNull(map.get("ysId"))&&
						!deptMap.containsKey(map.get("ysId").toString())){
					ysDeptErrorList.add(deptId);
				}
			}
			String taskInterId = "deptCheck";
			String period = this.getLoginPeriod();
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_taskInterId",taskInterId));
			List<InterLogger> interLoggers = interLoggerManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(interLoggers)&&!interLoggers.isEmpty()){
				for(InterLogger logger:interLoggers){
					interLoggerManager.remove(logger.getLogId());
				}
			}
			interLoggers = new ArrayList<InterLogger>();
			Date operDate = new Date();
			if(orgErrorList.size()>0){
				String errorText = "";
				for(String deptId:orgErrorList){
					errorText += deptMap.get(deptId) + "(" + deptId + "),";
				}
				errorText = OtherUtil.subStrEnd(errorText, ",");
				InterLogger interLogger = new InterLogger();
				interLogger.setLogDateTime(operDate);
				interLogger.setLogFrom("单位错误");
				interLogger.setLogMsg(errorText);
				interLogger.setTaskInterId(taskInterId);
				interLogger.setPeriodCode(period);
				interLoggers.add(interLogger);
			}
			if(pDeptErrorList.size()>0){
				String errorText = "";
				for(String deptId:pDeptErrorList){
					errorText += deptMap.get(deptId) + "(" + deptId + "),";
				}
				errorText = OtherUtil.subStrEnd(errorText, ",");
				InterLogger interLogger = new InterLogger();
				interLogger.setLogDateTime(operDate);
				interLogger.setLogFrom("上级部门错误");
				interLogger.setLogMsg(errorText);
				interLogger.setTaskInterId(taskInterId);
				interLogger.setPeriodCode(period);
				interLoggers.add(interLogger);
			}
			if(jjDeptErrorList.size()>0){
				String errorText = "";
				for(String deptId:jjDeptErrorList){
					errorText += deptMap.get(deptId) + "(" + deptId + "),";
				}
				errorText = OtherUtil.subStrEnd(errorText, ",");
				InterLogger interLogger = new InterLogger();
				interLogger.setLogDateTime(operDate);
				interLogger.setLogFrom("奖金部门错误");
				interLogger.setLogMsg(errorText);
				interLogger.setTaskInterId(taskInterId);
				interLogger.setPeriodCode(period);
				interLoggers.add(interLogger);
			}
			if(ysDeptErrorList.size()>0){
				String errorText = "";
				for(String deptId:ysDeptErrorList){
					errorText += deptMap.get(deptId) + "(" + deptId + "),";
				}
				errorText = OtherUtil.subStrEnd(errorText, ",");
				InterLogger interLogger = new InterLogger();
				interLogger.setLogDateTime(operDate);
				interLogger.setLogFrom("预算部门错误");
				interLogger.setLogMsg(errorText);
				interLogger.setTaskInterId(taskInterId);
				interLogger.setPeriodCode(period);
				interLoggers.add(interLogger);
			}
			if(khErrorList.size()>0){
				String errorText = "";
				for(String deptId:khErrorList){
					errorText += deptMap.get(deptId) + "(" + deptId + "),";
				}
				errorText = OtherUtil.subStrEnd(errorText, ",");
				InterLogger interLogger = new InterLogger();
				interLogger.setLogDateTime(operDate);
				interLogger.setLogFrom("绩效科室类别错误");
				interLogger.setLogMsg(errorText);
				interLogger.setTaskInterId(taskInterId);
				interLogger.setPeriodCode(period);
				interLoggers.add(interLogger);
			}
			interLoggerManager.saveAll(interLoggers);
			if(interLoggers.size()>0){
				return ajaxForwardError("检查未通过，具体错误请查看日志！");
			}
		}
		return ajaxForwardSuccess("检查通过！");
	}

	public InterLoggerManager getInterLoggerManager() {
		return interLoggerManager;
	}

	public void setInterLoggerManager(InterLoggerManager interLoggerManager) {
		this.interLoggerManager = interLoggerManager;
	}
}