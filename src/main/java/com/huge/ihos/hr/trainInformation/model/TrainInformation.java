package com.huge.ihos.hr.trainInformation.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

/**
 * 培训资料
 */
@Entity
@Table(name = "hr_train_information")
public class TrainInformation extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7103430406735908167L;
	private String id;// 主键
	private String code;// 资料编号
	private String name;// 资料名称
	private String author;// 资料作者
	private Double expense;// 资料费用
	private String press;// 出版社
	private String type;// 资料类型
	private String informationClass;// 资料分类
	private String remark;// 资料描述
	private Boolean disabled = false; // 是否可用

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

	@Column(name = "disabled", nullable = true)
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
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

	@Column(name = "author", nullable = true, length = 20)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "expense", nullable = true, precision = 12, scale = 2)
	public Double getExpense() {
		return expense;
	}

	public void setExpense(Double expense) {
		this.expense = expense;
	}

	@Column(name = "press", nullable = true, length = 30)
	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	@Column(name = "type", nullable = true, length = 20)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "information_class", nullable = true, length = 20)
	public String getInformationClass() {
		return informationClass;
	}

	public void setInformationClass(String informationClass) {
		this.informationClass = informationClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((expense == null) ? 0 : expense.hashCode());
		result = prime * result + ((press == null) ? 0 : press.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime
				* result
				+ ((informationClass == null) ? 0 : informationClass.hashCode());
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
		TrainInformation other = (TrainInformation) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
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
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (expense == null) {
			if (other.expense != null)
				return false;
		} else if (!expense.equals(other.expense))
			return false;
		if (press == null) {
			if (other.press != null)
				return false;
		} else if (!press.equals(other.press))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (informationClass == null) {
			if (other.informationClass != null)
				return false;
		} else if (!informationClass.equals(other.informationClass))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrainInformation [code=" + code + ", remark=" + remark
				+ ", name=" + name + ", author=" + author + ", expense="
				+ expense + ", press=" + press + ", type=" + type
				+ ", informationClass=" + informationClass + "]";
	}
}
