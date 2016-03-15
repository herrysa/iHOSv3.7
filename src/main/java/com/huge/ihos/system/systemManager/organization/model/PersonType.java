package com.huge.ihos.system.systemManager.organization.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table(name = "t_personType")
public class PersonType extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6312367249046237509L;
	private String id;// 主键
	private String code;// 编码
	private String name;// 名称
	private Integer sn;// 序号
	private PersonType parentType;// 上级类别
	private Boolean disabled = false; // 是否可用
	private Boolean leaf = true; // 是否为叶子
	private Date makeDate;// 创建日期
	private Person maker;// 创建人
	private String remark;// 描述
	private Boolean sysFiled = false;// 是否系统字段
	/* 工资帐表临时字段 */
	private List<Double> numberList;
	private List<String> stringList;
	private Boolean showFlag;

	@Id
	@Column(name = "id", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "code", nullable = false, length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "make_date", length = 19, nullable = true)
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	@Column(name = "remark", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "name", nullable = true, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "makerId", nullable = true)
	@JSON(serialize = false)
	public Person getMaker() {
		return maker;
	}

	public void setMaker(Person maker) {
		this.maker = maker;
	}

	@Column(name = "sn", nullable = true)
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentType", nullable = true)
	@JSON(serialize = false)
	public PersonType getParentType() {
		return parentType;
	}

	public void setParentType(PersonType parentType) {
		this.parentType = parentType;
	}

	@Column(name = "disabled", nullable = true, columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "leaf", nullable = true, columnDefinition = "bit default 0")
	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	@Column(name = "sysFiled", nullable = true, columnDefinition = "bit default 0")
	public Boolean getSysFiled() {
		return sysFiled;
	}

	public void setSysFiled(Boolean sysFiled) {
		this.sysFiled = sysFiled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((makeDate == null) ? 0 : makeDate.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((maker == null) ? 0 : maker.hashCode());
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
		PersonType other = (PersonType) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (makeDate == null) {
			if (other.makeDate != null)
				return false;
		} else if (!makeDate.equals(other.makeDate))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (maker == null) {
			if (other.maker != null)
				return false;
		} else if (!maker.equals(other.maker))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HrPersonType [code=" + code + ", makeDate=" + makeDate
				+ ", remark=" + remark + ", name=" + name + ", maker=" + maker
				+ "]";
	}

	@Transient
	public List<Double> getNumberList() {
		return numberList;
	}

	public void setNumberList(List<Double> numberList) {
		this.numberList = numberList;
	}

	@Transient
	public List<String> getStringList() {
		return stringList;
	}

	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}

	@Transient
	public Boolean getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(Boolean showFlag) {
		this.showFlag = showFlag;
	}
}
