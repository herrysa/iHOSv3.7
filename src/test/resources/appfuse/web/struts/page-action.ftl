<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<#assign getIdMethodName = pojo.getGetterSignature(pojo.identifierProperty)>
<#assign setIdMethodName = 'set' + pojo.getPropertyName(pojo.identifierProperty)>
<#assign identifierType = pojo.getJavaTypeName(pojo.identifierProperty, jdk5)>
package ${basepackage}.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import ${basepackage}.model.${pojo.shortName};
import ${basepackage}.service.${pojo.shortName}Manager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ${pojo.shortName}PagedAction extends JqGridBaseAction implements Preparable {

	private ${pojo.shortName}Manager ${pojoNameLower}Manager;
	private List<${pojo.shortName}> ${util.getPluralForWord(pojoNameLower)};
	private ${pojo.shortName} ${pojoNameLower};
	private ${identifierType} ${pojo.identifierProperty.name};

	public ${pojo.shortName}Manager get${pojo.shortName}Manager() {
		return ${pojoNameLower}Manager;
	}

	public void set${pojo.shortName}Manager(${pojo.shortName}Manager ${pojoNameLower}Manager) {
		this.${pojoNameLower}Manager = ${pojoNameLower}Manager;
	}

	public List<${pojo.shortName}> get${util.getPluralForWord(pojoNameLower)}() {
		return ${util.getPluralForWord(pojoNameLower)};
	}

	public void set${pojo.shortName}s(List<${pojo.shortName}> ${util.getPluralForWord(pojoNameLower)}) {
		this.${util.getPluralForWord(pojoNameLower)} = ${util.getPluralForWord(pojoNameLower)};
	}

	public ${pojo.shortName} get${pojo.shortName}() {
		return ${pojoNameLower};
	}

	public void set${pojo.shortName}(${pojo.shortName} ${pojoNameLower}) {
		this.${pojoNameLower} = ${pojoNameLower};
	}

	public ${identifierType} ${getIdMethodName}() {
		return ${pojo.identifierProperty.name};
	}

	public void ${setIdMethodName}(${identifierType} ${pojo.identifierProperty.name}) {
        this.${pojo.identifierProperty.name} = ${pojo.identifierProperty.name};
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String ${pojoNameLower}GridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = ${pojoNameLower}Manager
					.get${pojo.shortName}Criteria(pagedRequests,filters);
			this.${util.getPluralForWord(pojoNameLower)} = (List<${pojo.shortName}>) pagedRequests.getList();
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
			${pojoNameLower}Manager.save(${pojoNameLower});
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "${pojoNameLower}.added" : "${pojoNameLower}.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (${pojo.identifierProperty.name} != null) {
        	${pojoNameLower} = ${pojoNameLower}Manager.get(${pojo.identifierProperty.name});
        	this.setEntityIsNew(false);
        } else {
        	${pojoNameLower} = new ${pojo.shortName}();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String ${pojoNameLower}GridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					log.debug("Delete Customer " + removeId);
					${pojo.shortName} ${pojoNameLower} = ${pojoNameLower}Manager.get(new ${identifierType}(removeId));
					${pojoNameLower}Manager.remove(new ${identifierType}(removeId));
					
				}
				gridOperationMessage = this.getText("${pojoNameLower}.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("check${pojo.shortName}GridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (${pojoNameLower} == null) {
			return "Invalid ${pojoNameLower} Data";
		}

		return SUCCESS;

	}
}

