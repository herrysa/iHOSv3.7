package com.huge.ihos.nursescore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jj_dict_NurseYearRate")
public class NurseYearRate {

	private String code;
	private String title;
	private String minyears;
	private String maxyears = "";
	private Double rate;

	@Id
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "minyears")
	public String getMinyears() {
		return minyears;
	}

	public void setMinyears(String minyears) {
		this.minyears = minyears;
	}

	@Column(name = "maxyears")
	public String getMaxyears() {
		return maxyears;
	}

	public void setMaxyears(String maxyears) {
		this.maxyears = maxyears;
	}

	@Column(name = "rate")
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
		result = prime * result + ((maxyears == null) ? 0 : maxyears.hashCode());
		result = prime * result + ((minyears == null) ? 0 : minyears.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		NurseYearRate other = (NurseYearRate) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (maxyears == null) {
			if (other.maxyears != null)
				return false;
		} else if (!maxyears.equals(other.maxyears))
			return false;
		if (minyears == null) {
			if (other.minyears != null)
				return false;
		} else if (!minyears.equals(other.minyears))
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NurseYearRate [code=" + code + ", title=" + title + ", minyears=" + minyears + ", maxyears=" + maxyears
				+ ", rate=" + rate + "]";
	}

}