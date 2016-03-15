package com.huge.ihos.system.util.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.huge.webapp.util.SpringContextHelper;

import net.sf.json.JSONObject;



public class TreeFactory {
	private String treeParam;
	
	public TreeFactory(String treeParam){
		this.treeParam = treeParam;
	}
	
	public List<Map<String, String>> getTreeNodes(String contextPath){
		List<Map<String, String>> treeNodes = new ArrayList<Map<String,String>>();
		JSONObject paramJson = JSONObject.fromObject(treeParam);
		Iterator paramIt = paramJson.keys();
		while (paramIt.hasNext()) {
			Object paramKey = paramIt.next();
			Object paramValue = paramJson.get(paramKey);
			if(paramValue instanceof Map){
				ITree iTree = (ITree)SpringContextHelper.getBean(paramKey+"Tree");
				if(iTree!=null){
					iTree.init((Map<String, String>)paramValue);
					iTree.setIconPah(contextPath);
					treeNodes.addAll(iTree.getTreeNodes());
				}
			}
			
		}
		return treeNodes;
	}
}
