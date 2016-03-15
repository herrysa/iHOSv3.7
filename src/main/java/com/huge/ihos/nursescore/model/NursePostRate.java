package com.huge.ihos.nursescore.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table( name = "jj_dict_NursePostRate")
public class NursePostRate {
	
	private String code;
	private String post;
	private String pycode;
	private Double rate ;
	
	@Id
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="post")
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	
	@Column(name="pycode")
	public String getPycode() {
		return pycode;
	}
	public void setPycode(String pycode) {
		this.pycode = pycode;
	}
	
	
	@Column(name="rate")
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((post == null) ? 0 : post.hashCode());
		result = prime * result + ((pycode == null) ? 0 : pycode.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
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
		NursePostRate other = (NursePostRate) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (post == null) {
			if (other.post != null)
				return false;
		} else if (!post.equals(other.post))
			return false;
		if (pycode == null) {
			if (other.pycode != null)
				return false;
		} else if (!pycode.equals(other.pycode))
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NursePostRate [code=" + code + ", post=" + post + ", pycode="
				+ pycode + ", rate=" + rate + "]";
	}
	
	
	
	
}