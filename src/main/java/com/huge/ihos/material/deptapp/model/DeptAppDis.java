package com.huge.ihos.material.deptapp.model;

import java.util.Set;
/**
 * 科室申领发放的辅助类
 * 表示申领单的一条明细记录 一次通过数量和按批次发放明细数量
 * @author Gaozhengyang
 * @date 2014年8月13日
 */
public class DeptAppDis {
	private String deptAppDetailId;
	private Double throughAmount;
	private String remark;
	private Set<BatchDis> batchDiss;
	
	public String getDeptAppDetailId() {
		return deptAppDetailId;
	}
	public void setDeptAppDetailId(String deptAppDetailId) {
		this.deptAppDetailId = deptAppDetailId;
	}
	public Double getThroughAmount() {
		return throughAmount;
	}
	public void setThroughAmount(Double throughAmount) {
		this.throughAmount = throughAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Set<BatchDis> getBatchDiss() {
		return batchDiss;
	}
	public void setBatchDiss(Set<BatchDis> batchDiss) {
		this.batchDiss = batchDiss;
	}

	public class BatchDis{
		private String batchId;
		private Double disAmount;
		private Double disPrice;
		public String getBatchId() {
			return batchId;
		}
		public void setBatchId(String batchId) {
			this.batchId = batchId;
		}
		public Double getDisAmount() {
			return disAmount;
		}
		public void setDisAmount(Double disAmount) {
			this.disAmount = disAmount;
		}
		public Double getDisPrice() {
			return disPrice;
		}
		public void setDisPrice(Double disPrice) {
			this.disPrice = disPrice;
		}
		
	}
}
