package com.huge.ihos.material.documenttemplate.model;


public class TemplateAssist {
	private String name;
	private String value;
	private Boolean checked;//页面打勾
	private Boolean necessary;//是否必须的，如果是必须的，则checked无效，即不能去掉勾选状态
	private String referId;
	private String referName;
	private String type;

	public TemplateAssist(String name) {
		super();
		this.name = name;
	}

	public TemplateAssist(String value, String name) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public TemplateAssist(String value, String name, Boolean necessary) {
		super();
		this.name = name;
		this.value = value;
		this.necessary = necessary;
		this.checked = necessary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getNecessary() {
		return necessary;
	}

	public void setNecessary(Boolean necessary) {
		this.necessary = necessary;
	}

	public String getReferId() {
		return referId;
	}

	public void setReferId(String referId) {
		this.referId = referId;
	}

	public String getReferName() {
		return referName;
	}

	public void setReferName(String referName) {
		this.referName = referName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
