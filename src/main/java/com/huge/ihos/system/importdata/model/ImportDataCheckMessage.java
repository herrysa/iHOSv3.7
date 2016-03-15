package com.huge.ihos.system.importdata.model;

public class ImportDataCheckMessage {
	private boolean isSuccess;
	private int colNum;
	private String message;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ImportDataCheckMessage() {
		super();
	}

	public ImportDataCheckMessage(boolean isSuccess, int colNum, String message) {
		super();
		this.isSuccess = isSuccess;
		this.colNum = colNum;
		this.message = message;
	}

	public ImportDataCheckMessage(boolean isSuccess,String message) {
		super();
		this.isSuccess = isSuccess;
		this.message = message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + colNum;
		result = prime * result + (isSuccess ? 1231 : 1237);
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		/*if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;*/
		ImportDataCheckMessage other = (ImportDataCheckMessage) obj;
		if (colNum != other.colNum)
			return false;
		if (isSuccess != other.isSuccess)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}
	
}
