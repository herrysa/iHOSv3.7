package com.huge.ihos.system.util.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.util.PropertyFilter;

@Component
public class DeptTree implements ITree {

	private String isParent = "1";
	private String herpType;
	private String c_org;
	private String dpType;
	private String parentCol;
	private String leaf;
	private String jjLeaf;
	private String iconPah;
	private String orderCol;
	
	@Autowired
	private DepartmentManager departmentManager;
	
	public void init(Map<String, String> param){
		this.herpType = param.get("herpType");
		this.c_org = param.get("c_org");
		this.dpType = param.get("dpType");
		this.parentCol = param.get("parentCol");
		this.leaf = param.get("leaf");
		this.jjLeaf = param.get("jjLeaf");
		this.isParent = param.get("isParent");
		this.orderCol = param.get("orderCol");
	}
	
	@Override
	public List<Map<String, String>> getTreeNodes() {
		List<Department> deptList;
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQB_disabled","0"));
		filters.add(new PropertyFilter("NES_departmentId","XT"));
		if("internalCode".equals(this.orderCol)) {
			filters.add(new PropertyFilter("OAS_internalCode",""));
		} else {
			filters.add(new PropertyFilter("OAS_deptCode",""));
		}
		if("1".equals(dpType)){
			filters.add(new PropertyFilter("INS_departmentId",UserContextUtil.findUserDataPrivilegeStr("dept_dp", "1")));
		}else if("2".equals(dpType)){
			filters.add(new PropertyFilter("INS_departmentId",UserContextUtil.findUserDataPrivilegeStr("dept_dp", "2")));
		}
		deptList = departmentManager.getByFilters(filters);
		List<Map<String, String>> deptNodes = new ArrayList<Map<String,String>>();
		for(Department dept : deptList){
			Map<String, String> deptMap = new HashMap<String, String>();
			deptMap.put("id", dept.getDepartmentId());
			deptMap.put("name", dept.getName());
			Department pDept = dept.getParentDept();
			if(pDept!=null){
				deptMap.put("pId", pDept.getDepartmentId());
			}else{
				if("org".equals(parentCol)){
					Org pOrg = dept.getOrg();
					if(pOrg!=null){
						deptMap.put("pId", pOrg.getOrgCode());
					}else{
						deptMap.put("pId", "");
					}
				}else if("deptType".equals(parentCol)){
					String deptType = dept.getDeptClass();
					if(deptType!=null&&!deptType.equals("")){
						deptMap.put("pId", deptType);
					}else{
						deptMap.put("pId", "未分类");
					}
				}else if("jjDeptType".equals(parentCol)){
					KhDeptType khDeptType = dept.getJjDeptType();
					if(khDeptType!=null){
						deptMap.put("pId", khDeptType.getKhDeptTypeId());
					}else{
						deptMap.put("pId", "未分类");
					}
				}
			}
			
			deptMap.put("icon", iconPah+"/scripts/zTree/css/zTreeStyle/img/diy/dept.gif");
			deptMap.put("isParent", isParent);
			//orgMap.put("id", org.getOrgCode());
			//orgMap.put("id", org.getOrgCode());
			deptNodes.add(deptMap);
		}
		return deptNodes;
	}

	@Override
	public void setIconPah(String iconPah) {
		this.iconPah = iconPah;
	}

}
