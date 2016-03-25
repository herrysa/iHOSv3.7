package com.huge.ihos.update.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.PersonManager;
import com.huge.ihos.update.model.JjDeptMap;
import com.huge.ihos.update.service.JjDeptMapManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;




public class JjDeptMapPagedAction extends JqGridBaseAction   {

	private JjDeptMapManager jjDeptMapManager;
	private PersonManager personManager;
	private List<JjDeptMap> jjDeptMaps;
	private JjDeptMap jjDeptMap;
	private String id;


 
	public String jjDeptMapGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = jjDeptMapManager
					.getJjDeptMapCriteria(pagedRequests,filters);
			this.jjDeptMaps = (List<JjDeptMap>) pagedRequests.getList();
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
			Person p=personManager.get(jjDeptMap.getPersonId().getPersonId());
				if (this.isEntityIsNew()&&OtherUtil.measureNotNull(jjDeptMap.getPersonId())) {
					boolean b=jjDeptMapManager.existCode("jj_t_jjdeptmap", "personId", p.getPersonId(),"add");
					if (b) {
						return ajaxForwardError("该人员分管科室权限已设置！");
					}
				}
			jjDeptMap.setDeptId(p.getDepartment());
			jjDeptMapManager.save(jjDeptMap);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "jjDeptMap.added" : "jjDeptMap.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	jjDeptMap = jjDeptMapManager.get(Integer.parseInt(id));
        	this.setEntityIsNew(false);
        } else {
        	jjDeptMap = new JjDeptMap();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String jjDeptMapGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					Integer removeId = Integer.parseInt(ids.nextToken());
					log.debug("Delete Customer " + removeId);
					JjDeptMap jjDeptMap = jjDeptMapManager.get(removeId);
					jjDeptMapManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("jjDeptMap.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkJjDeptMapGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (jjDeptMap == null) {
			return "Invalid jjDeptMap Data";
		}

		return SUCCESS;

	}

	public JjDeptMapManager getJjDeptMapManager() {
		return jjDeptMapManager;
	}

	public void setJjDeptMapManager(JjDeptMapManager jjDeptMapManager) {
		this.jjDeptMapManager = jjDeptMapManager;
	}

	public List<JjDeptMap> getjjDeptMaps() {
		return jjDeptMaps;
	}

	public void setJjDeptMaps(List<JjDeptMap> jjDeptMaps) {
		this.jjDeptMaps = jjDeptMaps;
	}

	public JjDeptMap getJjDeptMap() {
		return jjDeptMap;
	}

	public void setJjDeptMap(JjDeptMap jjDeptMap) {
		this.jjDeptMap = jjDeptMap;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }
	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}
}

