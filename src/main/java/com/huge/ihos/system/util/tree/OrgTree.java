package com.huge.ihos.system.util.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.webapp.util.PropertyFilter;

@Component
public class OrgTree implements ITree{

	private String isParent = "1";
	private String herpType;
	private String c_org;
	private String dpType;
	private String parentCol;
	private String iconPah;

	@Autowired
	private OrgManager orgManager;
	
	public void init(Map<String, String> param){
		this.herpType = param.get("herpType");
		this.c_org = param.get("c_org");
		this.dpType = param.get("dpType");
		this.parentCol = param.get("parentCol");
		this.isParent = param.get("isParent");
	}
	
	@Override
	public List<Map<String, String>> getTreeNodes() {
		List<Org> orgList;
		if(true){
			return new ArrayList<Map<String, String>>();
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQB_disabled","0"));
		filters.add(new PropertyFilter("NES_orgCode","XT"));
		filters.add(new PropertyFilter("OAS_orgCode",""));
		if("1".equals(dpType)){
			filters.add(new PropertyFilter("INS_orgCode",UserContextUtil.findUserDataPrivilegeStr("org_dp", "1")));
		}else if("2".equals(dpType)){
			filters.add(new PropertyFilter("INS_orgCode",UserContextUtil.findUserDataPrivilegeStr("org_dp", "2")));
		}
		orgList = orgManager.getByFilters(filters);
		List<Map<String, String>> orgNodes = new ArrayList<Map<String,String>>();
		for(Org org : orgList){
			Map<String, String> orgMap = new HashMap<String, String>();
			orgMap.put("id", org.getOrgCode());
			orgMap.put("name", org.getOrgname());
			Org pOrg = org.getParentOrgCode();
			if(pOrg!=null){
				orgMap.put("pId", pOrg.getOrgCode());
			}else{
				orgMap.put("pId", "");
			}
			orgMap.put("icon", iconPah+"/scripts/zTree/css/zTreeStyle/img/diy/1_close.png");
			orgMap.put("isParent", isParent);
			//orgMap.put("id", org.getOrgCode());
			//orgMap.put("id", org.getOrgCode());
			orgNodes.add(orgMap);
		}
		return orgNodes;
	}
	
	@Override
	public void setIconPah(String iconPah) {
		this.iconPah = iconPah;
	}
}
