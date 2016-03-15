package com.huge.ihos.indicatoranalysis.service;

import java.util.List;

import com.huge.ihos.indicatoranalysis.model.Indicator;
import com.huge.service.GenericManager;

public interface IndicatorManager extends GenericManager<Indicator, String> {
	/**
	 * 根据指标类型code获取指标集合
	 * @param typeCode 指标类型code
	 * @return
	 */
	public List<Indicator> getByIndicatorType(String typeCode);
	/**
	 * 根据指标Id获取所有孩子
	 * @param parentId
	 */
	public List<Indicator> getChildrenByParentId(String parentId);
}