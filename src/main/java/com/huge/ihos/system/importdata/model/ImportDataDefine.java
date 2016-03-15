package com.huge.ihos.system.importdata.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table(name = "sy_importdata_define")
public class ImportDataDefine extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3295420229494457020L;
	//主键
	private String interfaceId;
	//方案名称
	private String interfaceName;
	//备注
	private String remark;

	private Set<ImportDataDefineDetail> importDataDefineDetails = new HashSet<ImportDataDefineDetail>();
	
	//private Set<ImportDataCheck> ImportDataChecks = new HashSet<ImportDataCheck>();
	
	@Id
	@Column(name = "interface_id", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	@Column(name = "interface_name", length = 50)
	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@OneToMany(mappedBy="importDataDefine",cascade=CascadeType.ALL)
	public Set<ImportDataDefineDetail> getImportDataDefineDetails() {
		return importDataDefineDetails;
	}

	public void setImportDataDefineDetails(Set<ImportDataDefineDetail> importDataDefineDetails) {
		this.importDataDefineDetails = importDataDefineDetails;
	}

	//@OneToMany(mappedBy="importDataDefine",cascade=CascadeType.ALL)
	/*public Set<ImportDataCheck> getImportDataChecks() {
		return ImportDataChecks;
	}

	public void setImportDataChecks(Set<ImportDataCheck> importDataChecks) {
		ImportDataChecks = importDataChecks;
	}*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((interfaceId == null) ? 0 : interfaceId.hashCode());
		result = prime * result + ((interfaceName == null) ? 0 : interfaceName.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImportDataDefine other = (ImportDataDefine) obj;
		if (interfaceId == null) {
			if (other.interfaceId != null)
				return false;
		} else if (!interfaceId.equals(other.interfaceId))
			return false;
		if (interfaceName == null) {
			if (other.interfaceName != null)
				return false;
		} else if (!interfaceName.equals(other.interfaceName))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImportDataDefine [interfaceId=" + interfaceId + ", interfaceName=" + interfaceName + ", remark=" + remark + "]";
	}

}