package com.huge.ihos.hr.trainTeacher.model;

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

@Entity
@Table(name = "hr_train_teacher")
public class TrainTeacher extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4233624448720439481L;
	private String id;// 主键
	private String code;// 教师编号
	private String name;// 教师姓名
	private Date birthDay;// 教师出生日期
	private String sex;// 教师性别
	private String educationLevel;// 教师学历
	private String profession;// 教师专业
	private String school;// 毕业院校
	private String remark;// 教师简介
	private String courses;// 教授课程
	private String phone;// 电话
	private String email;// Email地址
	private String category;// 教师类别
	private String workUnit;// 工作单位
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthDay", length = 19, nullable = true)
	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	@Column(name = "workUnit", nullable = true, length = 50)
	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
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

	@Column(name = "sex", nullable = true, length = 2)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "education_level", nullable = true, length = 20)
	public String getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	@Column(name = "profession", nullable = true, length = 20)
	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	@Column(name = "school", nullable = true, length = 30)
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name = "courses", nullable = true, length = 50)
	public String getCourses() {
		return courses;
	}

	public void setCourses(String courses) {
		this.courses = courses;
	}

	@Column(name = "phone", nullable = true, length = 20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "email", nullable = true, length = 20)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "category", nullable = true, length = 20)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((birthDay == null) ? 0 : birthDay.hashCode());
		result = prime * result
				+ ((workUnit == null) ? 0 : workUnit.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result
				+ ((educationLevel == null) ? 0 : educationLevel.hashCode());
		result = prime * result
				+ ((profession == null) ? 0 : profession.hashCode());
		result = prime * result + ((school == null) ? 0 : school.hashCode());
		result = prime * result + ((courses == null) ? 0 : courses.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
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
		TrainTeacher other = (TrainTeacher) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (birthDay == null) {
			if (other.birthDay != null)
				return false;
		} else if (!birthDay.equals(other.birthDay))
			return false;
		if (workUnit == null) {
			if (other.workUnit != null)
				return false;
		} else if (!workUnit.equals(other.workUnit))
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
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (educationLevel == null) {
			if (other.educationLevel != null)
				return false;
		} else if (!educationLevel.equals(other.educationLevel))
			return false;
		if (profession == null) {
			if (other.profession != null)
				return false;
		} else if (!profession.equals(other.profession))
			return false;
		if (school == null) {
			if (other.school != null)
				return false;
		} else if (!school.equals(other.school))
			return false;
		if (courses == null) {
			if (other.courses != null)
				return false;
		} else if (!courses.equals(other.courses))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrainTeacher [code=" + code + ", birthDay=" + birthDay
				+ ", workUnit=" + workUnit + ", remark=" + remark + ", name="
				+ name + ", sex=" + sex + ", educationLevel=" + educationLevel
				+ ", profession=" + profession + ", school=" + school
				+ ", courses=" + courses + ", phone=" + phone + ", email="
				+ email + ", category=" + category + "]";
	}
}
