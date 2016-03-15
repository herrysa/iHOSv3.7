package com.huge.ihos.indicatoranalysis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;
/**
 * 指标分析类型
 * @author Gaozhengyang
 * @date 2014年11月5日
 */
@Entity
@Table( name = "t_indicator_type" )
public class IndicatorType extends BaseObject implements Serializable {
	private static final long serialVersionUID = -8847291588954663836L;
	private String code;
	private String name;
	
	@Id
	@Column(name = "code",nullable = false, length = 20)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name",nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		IndicatorType other = (IndicatorType) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "IndicatorType [ code=" + code + ", name=" + name
				+ "]";
	}

}
