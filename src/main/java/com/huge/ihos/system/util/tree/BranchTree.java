package com.huge.ihos.system.util.tree;

import java.util.List;
import java.util.Map;

import com.huge.util.OtherUtil;

public class BranchTree implements ITree {

	private String isParent = "1";
	private String herpType;
	private String c_org;
	private String dpType;
	private String parentCol;
	private String iconPah;

	@Override
	public void init(Map<String, String> param) {
		this.herpType = param.get("herpType");
		this.c_org = param.get("c_org");
		this.dpType = param.get("dpType");
		this.parentCol = param.get("parentCol");
		this.isParent = OtherUtil.measureNotNull(param.get("isParent")) ? param.get("isParent") : isParent;
	}

	@Override
	public List<Map<String, String>> getTreeNodes() {
		
		return null;
	}

	@Override
	public void setIconPah(String iconPah) {
		// TODO Auto-generated method stub

	}

}
