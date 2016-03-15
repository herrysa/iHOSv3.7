package com.huge.ihos.gz.gzItemPersonType.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table(name = "gz_gzItem_PersonType")
public class GzItemPersonType extends BaseObject implements Serializable{
  

	/**
	 * 
	 */
	private static final long serialVersionUID = 7629240409828881919L;

	private String mappingId ;   //人员类别与工资进行对照
	
	private String formulaId ;   //工资项目公式的id
	
	private String empType ;   //人员类别
	
	private String gzTypeId ;      //工资类别id
	
    
	
	@Id
	@Column(name = "mappingId", length = 32)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}  
	@Column(name = "formulaId", length = 32,nullable=false)
	public String getFormulaId() {
		return formulaId;
	}

	public void setFormulaId(String formulaId) {
		this.formulaId = formulaId;
	}
	@Column(name = "empType", length = 20,nullable=false)
	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}
	@Column(name = "gzTypeId", length = 32,nullable=false)
	public String getGzTypeId() {
		return gzTypeId;
	}

	public void setGzTypeId(String gzTypeId) {
		this.gzTypeId = gzTypeId;
	}		
	@Override
	public String toString() {
		return "GzItemPersonType [mappingId=" + mappingId + ", formulaId="
				+ formulaId + ", empType=" + empType+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GzItemPersonType other = (GzItemPersonType) obj;
		if (mappingId == null) {
			if (other.mappingId != null)
				return false;
		} else if (!mappingId.equals(other.mappingId))
			return false;
		if (formulaId == null) {
			if (other.formulaId != null)
				return false;
		} else if (!formulaId.equals(other.formulaId))
			return false;
		if (empType == null) {
			if (other.empType != null)
				return false;
		} else if (!empType.equals(other.empType))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mappingId == null) ? 0 : mappingId.hashCode());
		result = prime * result
				+ ((formulaId == null) ? 0 : formulaId.hashCode());
		result = prime * result
				+ ((empType == null) ? 0 : empType.hashCode());
		return result;
	}
}
