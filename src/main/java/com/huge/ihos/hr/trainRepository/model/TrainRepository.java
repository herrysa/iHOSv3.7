package com.huge.ihos.hr.trainRepository.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;
/**
 * 培训知识
 * @date 2015年2月3日
 */
@Entity
@Table(name = "hr_train_repository")
public class TrainRepository extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8997359042768923574L;
	private String id;// 主键
	private String code;// 知识编码
	private String name;// 知识标题
	private String classNumber;// 分类号
	private String contentClass;// 内容分类
	private String timeliness;// 时效性
	private String referenceNumber;// 知识文号
	private String issueUnit;// 颁布单位
	private String creditLine;// 知识题注
	private Date issueDate;// 颁布日期
	private Date implementDate;// 实施日期
	private Date expiryDate;// 失效日期
	private String type;// 知识分类
	private String remark;// 知识备注

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
	@Column(name = "issue_date", length = 19, nullable = true)
	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	@Column(name = "type", nullable = true, length = 20)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "remark", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "name", nullable = true, length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "class_number", nullable = true, length = 20)
	public String getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	@Column(name = "content_class", nullable = true, length = 20)
	public String getContentClass() {
		return contentClass;
	}

	public void setContentClass(String contentClass) {
		this.contentClass = contentClass;
	}

	@Column(name = "timeliness", nullable = true, length = 20)
	public String getTimeliness() {
		return timeliness;
	}

	public void setTimeliness(String timeliness) {
		this.timeliness = timeliness;
	}

	@Column(name = "reference_number", nullable = true, length = 20)
	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	@Column(name = "issue_unit", nullable = true, length = 50)
	public String getIssueUnit() {
		return issueUnit;
	}

	public void setIssueUnit(String issueUnit) {
		this.issueUnit = issueUnit;
	}

	@Column(name = "credit_line", nullable = true, length = 20)
	public String getCreditLine() {
		return creditLine;
	}

	public void setCreditLine(String creditLine) {
		this.creditLine = creditLine;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "implement_date", length = 19, nullable = true)
	public Date getImplementDate() {
		return implementDate;
	}

	public void setImplementDate(Date implementDate) {
		this.implementDate = implementDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiry_date", length = 19, nullable = true)
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((issueDate == null) ? 0 : issueDate.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((classNumber == null) ? 0 : classNumber.hashCode());
		result = prime * result
				+ ((contentClass == null) ? 0 : contentClass.hashCode());
		result = prime * result
				+ ((timeliness == null) ? 0 : timeliness.hashCode());
		result = prime * result
				+ ((referenceNumber == null) ? 0 : referenceNumber.hashCode());
		result = prime * result
				+ ((issueUnit == null) ? 0 : issueUnit.hashCode());
		result = prime * result
				+ ((creditLine == null) ? 0 : creditLine.hashCode());
		result = prime * result
				+ ((implementDate == null) ? 0 : implementDate.hashCode());
		result = prime * result
				+ ((expiryDate == null) ? 0 : expiryDate.hashCode());
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
		TrainRepository other = (TrainRepository) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (issueDate == null) {
			if (other.issueDate != null)
				return false;
		} else if (!issueDate.equals(other.issueDate))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
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
		if (classNumber == null) {
			if (other.classNumber != null)
				return false;
		} else if (!classNumber.equals(other.classNumber))
			return false;
		if (contentClass == null) {
			if (other.contentClass != null)
				return false;
		} else if (!contentClass.equals(other.contentClass))
			return false;
		if (timeliness == null) {
			if (other.timeliness != null)
				return false;
		} else if (!timeliness.equals(other.timeliness))
			return false;
		if (referenceNumber == null) {
			if (other.referenceNumber != null)
				return false;
		} else if (!referenceNumber.equals(other.referenceNumber))
			return false;
		if (issueUnit == null) {
			if (other.issueUnit != null)
				return false;
		} else if (!issueUnit.equals(other.issueUnit))
			return false;
		if (creditLine == null) {
			if (other.creditLine != null)
				return false;
		} else if (!creditLine.equals(other.creditLine))
			return false;
		if (implementDate == null) {
			if (other.implementDate != null)
				return false;
		} else if (!implementDate.equals(other.implementDate))
			return false;
		if (expiryDate == null) {
			if (other.expiryDate != null)
				return false;
		} else if (!expiryDate.equals(other.expiryDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrainRepository [code=" + code + ", issueDate=" + issueDate
				+ ", type=" + type + ", remark=" + remark + ", name=" + name
				+ ", classNumber=" + classNumber + ", contentClass="
				+ contentClass + ", timeliness=" + timeliness
				+ ", referenceNumber=" + referenceNumber + ", issueUnit="
				+ issueUnit + ", creditLine=" + creditLine + ", implementDate="
				+ implementDate + ", expiryDate=" + expiryDate + "]";
	}
}
