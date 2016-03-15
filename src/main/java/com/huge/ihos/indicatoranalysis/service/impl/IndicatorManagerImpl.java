package com.huge.ihos.indicatoranalysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.indicatoranalysis.dao.IndicatorDao;
import com.huge.ihos.indicatoranalysis.model.Indicator;
import com.huge.ihos.indicatoranalysis.service.IndicatorManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.util.PropertyFilter;

@Service("indicatorManager")
public class IndicatorManagerImpl extends GenericManagerImpl<Indicator, String>
		implements IndicatorManager {

	@Autowired
	public IndicatorManagerImpl(IndicatorDao indicatorDao) {
		super(indicatorDao);
	}

	@Override
	public Indicator save(Indicator indicator) {
		indicator = super.save(indicator);
		List<Indicator> indicators = this.getByTypeAndLevel(indicator.getIndicatorType().getCode(), indicator.getLevel());
		if (indicators != null && indicators.size() > 0) {
			/*
			 * set seq
			 */
			for (int i = 0; i < indicators.size(); i++) {
				Indicator ind = indicators.get(i);
				ind.setSeq(i + 1);
				super.save(ind);
			}
		}
		/*
		 * change seq by formula and also set oper
		 */
		if (!indicator.getLeaf()) {
			String realFormula = indicator.getRealFormula();
			if (OtherUtil.measureNotNull(indicator.getFormula()) && OtherUtil.measureNotNull(realFormula)) {
				List<String> formulaIds = parseIdBYRealFormula(realFormula);
				int start = 0, end = 0, seq = 0;
				for (int i = 0; i < formulaIds.size(); i++) {
					seq = this.get(formulaIds.get(i)).getSeq();
					if (i == 0) {
						start = seq;
						end = seq;
					} else {
						if (start > seq) {
							start = seq;
						}
						if (end < seq) {
							end = seq;
						}
					}
				}
				int endIndex = start + formulaIds.size();
				int startIndex = start;
				indicators = this.getByTypeAndLevel(indicator.getIndicatorType().getCode(), indicator.getLevel() + 1);
				String[] opers = parseOperBYRealFormula(realFormula,formulaIds.size()+1);
				Indicator ind = null;
				Map<String,Boolean> map = new HashMap<String,Boolean>();
				for (int i = 0,j=0; i < formulaIds.size(); i++) {
					ind = this.get(formulaIds.get(i));
					ind.setSeq(start++);
					if(i==0){
						ind.setLeftOper(opers[j]);
					}
					ind.setRightOper(opers[++j]);
					super.save(ind);
					map.put(formulaIds.get(i), true);
				}
				for(int i=startIndex;i<indicators.size();i++){
					ind = indicators.get(i);
					if(map.get(ind.getId())!=null){
						continue;
					}else{
						ind.setSeq(endIndex++);
						super.save(ind);
					}
				}
			}
		}
		return indicator;
	}
	
	private String[] parseOperBYRealFormula(String formula,int size){
		String[] opers = formula.split("(\\[.*?\\])");
		if(opers.length<size){
			String[] newOpers = new String[size];
			System.arraycopy(opers, 0, newOpers,0, opers.length);
			return newOpers;
		}
		return opers;
	}

	private static List<String> parseIdBYRealFormula(String formula) {// [AAA]+[BBB]-([CCC]*[DDD])
		List<String> ids = new ArrayList<String>();
		int prevIndex = formula.indexOf('[');
		int endIndex = formula.indexOf(']');
		String id = "";
		while (prevIndex >= 0 && endIndex >= 0) {
			id = formula.substring(prevIndex + 1, endIndex);
			ids.add(id);
			formula = formula.replace("[" + id + "]", "");
			prevIndex = formula.indexOf('[');
			endIndex = formula.indexOf(']');
		}
		return ids;
	}

	@Override
	public List<Indicator> getByIndicatorType(String typeCode) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_indicatorType.code", typeCode));
		filters.add(new PropertyFilter("OAS_code", ""));
		List<Indicator> indicators = this.getByFilters(filters);
		if (indicators != null && indicators.size() > 0) {
			return indicators;
		}
		return null;
	}

	private List<Indicator> getByTypeAndLevel(String typeCode, Integer level) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_indicatorType.code", typeCode));
		filters.add(new PropertyFilter("EQI_level", "" + level));
		filters.add(new PropertyFilter("OAS_code", ""));
		List<Indicator> indicators = this.getByFilters(filters);
		if (indicators != null && indicators.size() > 0) {
			return indicators;
		}
		return null;
	}

	@Override
	public List<Indicator> getChildrenByParentId(String parentId) {
		List<Indicator> indicatorList = new ArrayList<Indicator>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_parent.id", parentId));
		filters.add(new PropertyFilter("OAS_code", ""));
		List<Indicator> indicators = this.getByFilters(filters);
		if (indicators != null && indicators.size() > 0) {
			indicatorList.addAll(indicators);
			for (Indicator indicator : indicators) {
				if (indicator.getLeaf()) {
					continue;
				}
				List<Indicator> childIndicators = getChildrenByParentId(indicator
						.getId());
				indicatorList.addAll(childIndicators);
			}
		}
		return indicatorList;
	}
}