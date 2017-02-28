package com.huge.ihos.system.systemManager.busiprocess.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.huge.model.BaseObject;

/**
 * 实时业务-存储过程
 * 
 * @author gaozhengyang
 * @date 2014年3月20日
 */
@Entity
@Table(name = "sy_business_process", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"code", "sequence" }) })
public class BusiProcess extends BaseObject implements Serializable {
	private static final long serialVersionUID = -2278771349643078395L;

	private Long id;
	private String code;// 业务编码
	private String name;// 业务名称
	private String spName;// 存储过程名
	private String tableName;// 对应的表名
	private Integer sequence;// 存储过程执行顺序
	private String remark;
	private Boolean disabled = false;
	private Boolean ignoreError = false;// 是否忽略执行结果

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "code", length = 10, nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", length = 100, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "spName", length = 50, nullable = false)
	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	@Column(name = "tableName", length = 50)
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "sequence", length = 10, nullable = false)
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "disabled", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "ignoreError", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIgnoreError() {
		return ignoreError;
	}

	public void setIgnoreError(Boolean ignoreError) {
		this.ignoreError = ignoreError;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((sequence == null) ? 0 : sequence.hashCode());
		result = prime * result + ((spName == null) ? 0 : spName.hashCode());
		result = prime * result
				+ ((ignoreError == null) ? 0 : ignoreError.hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
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
		BusiProcess other = (BusiProcess) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		if (spName == null) {
			if (other.spName != null)
				return false;
		} else if (!spName.equals(other.spName))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		if (ignoreError == null) {
			if (other.ignoreError != null)
				return false;
		} else if (!ignoreError.equals(other.ignoreError))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BusinessProcess [id=" + id + ", code=" + code + ", name="
				+ name + ", spName=" + spName + ", tableName=" + tableName
				+ ", sequence=" + sequence + ", remark=" + remark
				+ ", disabled=" + disabled + ",ignoreError=" + ignoreError
				+ "]";
	}

}
