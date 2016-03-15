package com.huge.ihos.system.util.tree;

import java.util.List;
import java.util.Map;

import com.huge.webapp.action.BaseAction;

public class TreeAction extends BaseAction{

	private String treeParam;
	private List<Map<String, String>> nodes;
	public String getTreeParam() {
		return treeParam;
	}
	public void setTreeParam(String treeParam) {
		this.treeParam = treeParam;
	}
	public List<Map<String, String>> getNodes() {
		return nodes;
	}
	public void setNodes(List<Map<String, String>> nodes) {
		this.nodes = nodes;
	}
	public String makeTree(){
		
		TreeFactory treeFactory = new TreeFactory(treeParam);
		nodes = treeFactory.getTreeNodes(this.getContextPath());
		return SUCCESS;
	}
}
