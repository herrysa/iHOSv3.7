package com.huge.ihos.system.configuration.syvariable.webapp.action;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.ihos.system.configuration.syvariable.model.Variable;
import com.huge.ihos.system.configuration.syvariable.service.VariableManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.SpringContextHelper;
import com.opensymphony.xwork2.Preparable;




public class VariablePagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5282703455280240307L;
	private VariableManager variableManager;
	private List<Variable> syVariables;
	private Variable syVariable;
	private String variableName;

	public VariableManager getVariableManager() {
		return variableManager;
	}

	public void setVariableManager(VariableManager variableManager) {
		this.variableManager = variableManager;
	}

	public List<Variable> getsyVariables() {
		return syVariables;
	}

	public void setSyVariables(List<Variable> syVariables) {
		this.syVariables = syVariables;
	}

	public Variable getSyVariable() {
		return syVariable;
	}

	public void setSyVariable(Variable syVariable) {
		this.syVariable = syVariable;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String syVariableGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = variableManager
					.getSyVariableCriteria(pagedRequests,filters);
			this.syVariables = (List<Variable>) pagedRequests.getList();
			UserContext userContext = UserContextUtil.getUserContext();
			if(userContext!=null){
				Map<String, Object> svMap = userContext.getSysVars();
				for(Variable variable : syVariables){
					String key = variable.getVariableName();
					Object value = svMap.get(key);
					String v = "";
					if(value!=null){
						v = value.toString();
					}
					variable.setValue(v);
				}
			}
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
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
			String classCode = syVariable.getPrivaleClass().getClassCode();
			if(OtherUtil.measureNull(classCode)) {
				syVariable.setPrivaleClass(null);
			}
			if(this.isEntityIsNew()) {
				variableManager.saveVariable(syVariable);
			} else {
				variableManager.save(syVariable);
			}
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "syVariable.added" : "syVariable.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
    	List<Variable> variables = this.variableManager.getAll();
    	Integer sn = 0;
    	if(OtherUtil.measureNotNull(variables)) {
    		sn = variables.size();
    	}
        if (variableName != null) {
        	syVariable = variableManager.get(variableName);
        	this.setEntityIsNew(false);
        } else {
        	syVariable = new Variable();
        	syVariable.setSn(sn + 1);
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String syVariableGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					variableManager.remove(removeId);
				}
				gridOperationMessage = this.getText("syVariable.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			} else if("enable".equals(oper)) {
				if(OtherUtil.measureNotNull(variableName)) {
					Variable syVariable = variableManager.get(variableName);
					syVariable.setDisabled(false);
					variableManager.save(syVariable);
				}
			} else if("disable".equals(oper)) {
				if(OtherUtil.measureNotNull(variableName)) {
					Variable syVariable = variableManager.get(variableName);
					syVariable.setDisabled(true);
					variableManager.save(syVariable);
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkSyVariableGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String saveSyVariableSn() {
		try {
			String dataStr = getRequest().getParameter("dataStr");
			if(OtherUtil.measureNotNull(dataStr)) {
				String[] datas = dataStr.split(",");
				for(int i=0;i<datas.length;i++) {
					String[] data = datas[i].split(":");
					String variableName = data[0];
					String sn = data[1];
					Variable variable = this.variableManager.get(variableName);
					variable.setSn(Integer.parseInt(sn));
					this.variableManager.save(variable);
				}
			}
		} catch (Exception e) {
			log.error("saveSyVariableSn error!",e);
		}
		return SUCCESS;
	}
	
	private String isValid() {
		if (syVariable == null) {
			return "Invalid syVariable Data";
		}

		return SUCCESS;

	}
}

