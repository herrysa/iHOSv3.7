package com.huge.ihos.system.util.tree;

import java.util.List;
import java.util.Map;

public interface ITree {

	public void init(Map<String, String> param);
	
	public List<Map<String, String>> getTreeNodes();
	
	public void setIconPah(String iconPah);
	
}
