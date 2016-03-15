package com.huge.ihos.system.configuration.syvariable.service;


import java.util.List;

import com.huge.ihos.system.configuration.syvariable.model.Variable;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface VariableManager extends GenericManager<Variable, String> {
     public JQueryPager getSyVariableCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 根据是否是系统级变量查询
      * 
      */
     public List<Variable> getVariableByStatus(Boolean isSys);
     /**
      * 获取非停用列表
      */
     public List<Variable> getEnableVariables();
     
     public Variable saveVariable(Variable variable);
}