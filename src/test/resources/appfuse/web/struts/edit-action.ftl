<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<#assign getIdMethodName = pojo.getGetterSignature(pojo.identifierProperty)>
<#assign setIdMethodName = 'set' + pojo.getPropertyName(pojo.identifierProperty)>
<#assign identifierType = pojo.getJavaTypeName(pojo.identifierProperty, jdk5)>
package ${basepackage}.webapp.action;

import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import ${basepackage}.model.${pojo.shortName};
import ${basepackage}.service.${pojo.shortName}Manager;
import org.sbbs.base.webapp.action.BaseJqgridEditAction;

public class ${pojo.shortName}EditAction extends BaseJqgridEditAction {	

	private ${pojo.shortName}			model;
	private ${pojo.shortName}Manager	${pojoNameLower}Manager;
	
	public ${pojo.shortName} getModel() {
		return model;
	}
	
	public void setModel(${pojo.shortName} model) {
		this.model = model;
	}
	
	public ${pojo.shortName}Manager get${pojo.shortName}Manager() {
		return ${pojoNameLower}Manager;
	}
	
	public void set${pojo.shortName}Manager(${pojo.shortName}Manager ${pojoNameLower}Manager) {
		this.${pojoNameLower}Manager = ${pojoNameLower}Manager;
	}
	
	public String edit() {
		if (getModelId() != null) {
			this.setModel(this.${pojoNameLower}Manager.get(Long.parseLong(this.getModelId())));
			this.setEditType(EDIT_TYPE_EDIT);
		} else {
			this.setModel(new ${pojo.shortName}());
			this.setEditType(EDIT_TYPE_NEW);
		}
		return this.SUCCESS;
		
	}
	
	public String save() {
		try {
			HttpServletRequest req = this.getRequest();
			boolean isNew = (this.getEditType() == 1);
			this.get${pojo.shortName}Manager().save((${pojo.shortName}) model);
			this.setMessage(getText((isNew) ? "${pojoNameLower}.added": "${pojoNameLower}.updated", "no msg key found,save successed."));
		} catch (Exception e) {
			this.setMessage(getText("error.saved",	new String[] { e.getMessage() }));
			this.setReturnCode(RETURN_STATUS_ERROR);
		}
		return this.SUCCESS;
	}
	
	public String delete() {
		try {
			
			StringTokenizer ids = new StringTokenizer(this.getDelIds(), ",");
			while (ids.hasMoreTokens()) {
				Long removeId = Long.parseLong(ids.nextToken());
				this.get${pojo.shortName}Manager().remove(removeId);
			}
			this.setMessage(getText("${pojoNameLower}.deleted",	"no msg key found,delete successed."));
		} catch (Exception e) {
			this.setMessage(getText("error.deleted",new String[] { e.getMessage() }));
			this.setReturnCode(RETURN_STATUS_ERROR);
		}
		return this.SUCCESS;
	}
	
}