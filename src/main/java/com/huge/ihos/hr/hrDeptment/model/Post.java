package com.huge.ihos.hr.hrDeptment.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;
import com.huge.util.OtherUtil;

@Entity
@Table(name = "hr_post")
public class Post extends BaseObject implements Serializable,Cloneable {




	/**
	 * 
	 */
	private static final long serialVersionUID = -1807405696196069654L;

	private String id;//主键ID
	private String code;//岗位代码
	private Integer codeSn;//岗位编码序号
	private String name;//岗位名称
	private HrDepartmentCurrent hrDept;
	private HrDepartmentHis hrDeptHis;
	private String deptSnapCode;
	private String postSeries;//岗位系别
	private String postLevel;//岗位级别
	private String directSupervisor;//直接上级
	private String postOrder;//岗位等次
	private Double basicSalary;//基本薪点
	private Double minSalary;//最低工资
	private Double maxSalary;//最高工资
	private String remark;//岗位概要
	private String dutySeries;//职务系列
	private Boolean disabled;//停用
	//lock
			private String lockState;//锁状态
	
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
	
	@Column(name = "code", length = 50, nullable = false)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "name", length = 50, nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//snap
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptId", nullable = false)
	public HrDepartmentCurrent getHrDept() {
		return hrDept;
	}

	public void setHrDept(HrDepartmentCurrent hrDept) {
		this.hrDept = hrDept;
	}
	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "deptId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "dept_snapCode", nullable = false, insertable = false, updatable = false) })
	public HrDepartmentHis getHrDeptHis() {
		return hrDeptHis;
	}

	public void setHrDeptHis(HrDepartmentHis hrDeptHis) {
		this.hrDeptHis = hrDeptHis;
	}

	@Column(name = "dept_snapCode", nullable = true, length = 14)
	public String getDeptSnapCode() {
		return deptSnapCode;
	}

	public void setDeptSnapCode(String deptSnapCode) {
		this.deptSnapCode = deptSnapCode;
	}
	//snap end
	
	@Column(name = "post_level", length = 50, nullable = true)
	public String getPostLevel() {
		return postLevel;
	}
	public void setPostLevel(String postLevel) {
		this.postLevel = postLevel;
	}
	
	@Column(name = "post_series", length = 50, nullable = true)
	public String getPostSeries() {
		return postSeries;
	}
	public void setPostSeries(String postSeries) {
		this.postSeries = postSeries;
	}
	
	@Column(name = "direct_supervisor", length = 50, nullable = true)
	public String getDirectSupervisor() {
		return directSupervisor;
	}
	public void setDirectSupervisor(String directSupervisor) {
		this.directSupervisor = directSupervisor;
	}
	
	@Column(name = "post_order", length = 50, nullable = true)
	public String getPostOrder() {
		return postOrder;
	}
	public void setPostOrder(String postOrder) {
		this.postOrder = postOrder;
	}
	
	@Column( name = "basic_salary", nullable = true,precision=12,scale=2)
	public Double getBasicSalary() {
		if(OtherUtil.measureNotNull(basicSalary)){
			BigDecimal bg = new BigDecimal(basicSalary);
			return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return basicSalary;
	}
	public void setBasicSalary(Double basicSalary) {
		this.basicSalary = basicSalary;
	}
	
	@Column( name = "min_salary", nullable = true,precision=12,scale=2)
	public Double getMinSalary() {
		if(OtherUtil.measureNotNull(minSalary)){
			BigDecimal bg = new BigDecimal(minSalary);
			return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return minSalary;
	}
	public void setMinSalary(Double minSalary) {
		this.minSalary = minSalary;
	}
	
	@Column( name = "max_salary", nullable = true,precision=12,scale=2)
	public Double getMaxSalary() {
		if(OtherUtil.measureNotNull(maxSalary)){
			BigDecimal bg = new BigDecimal(maxSalary);
			return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return maxSalary;
	}
	public void setMaxSalary(Double maxSalary) {
		this.maxSalary = maxSalary;
	}
	
	@Column( name = "remark",  length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "duty_series", length = 50, nullable = true)
	public String getDutySeries() {
		return dutySeries;
	}
	public void setDutySeries(String dutySeries) {
		this.dutySeries = dutySeries;
	}
	
	@Column( name = "codeSn", nullable = true)
	public Integer getCodeSn() {
		return codeSn;
	}
	public void setCodeSn(Integer codeSn) {
		this.codeSn = codeSn;
	}
	
	@Column(name = "disabled", nullable = true,columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	@Column(name = "lock_state", nullable = true, length = 200)
	public String getLockState() {
		return lockState;
	}

	public void setLockState(String lockState) {
		this.lockState = lockState;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((postSeries == null) ? 0 : postSeries.hashCode());
		result = prime * result + ((postLevel == null) ? 0 : postLevel.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((directSupervisor == null) ? 0 : directSupervisor.hashCode());
		result = prime * result + ((postOrder == null) ? 0 : postOrder.hashCode());
		result = prime * result + ((basicSalary == null) ? 0 : basicSalary.hashCode());		
		result = prime * result + ((minSalary == null) ? 0 : minSalary.hashCode());
		result = prime * result + ((maxSalary == null) ? 0 : maxSalary.hashCode());
		result = prime * result + ((dutySeries == null) ? 0 : dutySeries.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());
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
		Post other = (Post) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (postSeries == null) {
			if (other.postSeries != null)
				return false;
		} else if (!postSeries.equals(other.postSeries))
			return false;
		if (postLevel == null) {
			if (other.postLevel != null)
				return false;
		} else if (!postLevel.equals(other.postLevel))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (directSupervisor == null) {
			if (other.directSupervisor != null)
				return false;
		} else if (!directSupervisor.equals(other.directSupervisor))
			return false;
		if (postOrder == null) {
			if (other.postOrder != null)
				return false;
		} else if (!postOrder.equals(other.postOrder))
			return false;
		if (basicSalary == null) {
			if (other.basicSalary != null)
				return false;
		} else if (!basicSalary.equals(other.basicSalary))
			return false;		
		if (minSalary == null) {
			if (other.minSalary != null)
				return false;
		} else if (!minSalary.equals(other.minSalary))
			return false;
		if (maxSalary == null) {
			if (other.maxSalary != null)
				return false;
		} else if (!maxSalary.equals(other.maxSalary))
			return false;
		if (dutySeries == null) {
			if (other.dutySeries != null)
				return false;
		} else if (!dutySeries.equals(other.dutySeries))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Post [code=" + code + ", name=" + name + ", postSeries=" + postSeries + ", postLevel=" + postLevel + 
				", remark=" + remark + ", directSupervisor=" + directSupervisor + ", postOrder=" + postOrder + ", basicSalary=" + basicSalary + 
				", minSalary=" + minSalary + ", maxSalary=" + maxSalary + ", dutySeries=" + dutySeries + 
				", disabled=" + disabled + "]";
	}
	
	@Override
	public Post clone() {
		Post o = null;
		try {
			o = (Post) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	
}