package com.huge.ihos.system.configuration.syvariable.service.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.syvariable.dao.VariableDao;
import com.huge.ihos.system.configuration.syvariable.model.Variable;
import com.huge.ihos.system.configuration.syvariable.service.VariableManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.SpringContextHelper;





@Service("variableManager")
public class VariableManagerImpl extends GenericManagerImpl<Variable, String> implements VariableManager {
    private VariableDao variableDao;

    @Autowired
    public VariableManagerImpl(VariableDao variableDao) {
        super(variableDao);
        this.variableDao = variableDao;
    }
    
    public JQueryPager getSyVariableCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return variableDao.getSyVariableCriteria(paginatedList,filters);
	}
   
    @Override
    public List<Variable> getVariableByStatus(Boolean isSys) {
    	return this.variableDao.getVariableByStatus(isSys);
    }

    @Override
    public List<Variable> getEnableVariables() {
    	return this.variableDao.getEnableVariables();
    }
    @Override
    public Variable saveVariable(Variable object) {
    	Variable variable = this.variableDao.save(object);
    	if(variable != null) {
    		String variableName = variable.getVariableName();
    		variableName = variableName.replaceAll("%", "");
    		String len = "50";
    		if(variableName.startsWith("DP")) {
    			len = "MAX";
    		}
    		String sql = "alter table SY_VARIABLE_VALUE add " + variableName + " varchar("+len+") ";
    		DataSource dataSource = SpringContextHelper.getDataSource();
    		JdbcTemplate jtl = new JdbcTemplate(dataSource);
    		jtl.execute(sql);
    	}
    	return variable;
    }
}