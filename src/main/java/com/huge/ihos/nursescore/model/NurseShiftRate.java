package com.huge.ihos.nursescore.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table( name = "jj_dict_NurseShiftRate")
public class NurseShiftRate {
	
	private String code;
	private String shift;
	private String pycode;
	private Double rate ;
	
	@Id
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="shift")
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
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
		result = prime * result + ((pycode == null) ? 0 : pycode.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((shift == null) ? 0 : shift.hashCode());
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
		NurseShiftRate other = (NurseShiftRate) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
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
		if (shift == null) {
			if (other.shift != null)
				return false;
		} else if (!shift.equals(other.shift))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NurseShiftRate [code=" + code + ", shift=" + shift
				+ ", pycode=" + pycode + ", rate=" + rate + "]";
	}
	
	
	
	
}