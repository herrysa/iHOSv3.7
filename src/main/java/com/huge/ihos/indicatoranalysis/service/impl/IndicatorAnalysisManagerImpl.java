package com.huge.ihos.indicatoranalysis.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.dao.GenericSPDao;
import com.huge.ihos.indicatoranalysis.dao.IndicatorAnalysisDao;
import com.huge.ihos.indicatoranalysis.model.Indicator;
import com.huge.ihos.indicatoranalysis.model.IndicatorAnalysis;
import com.huge.ihos.indicatoranalysis.service.IndicatorAnalysisManager;
import com.huge.ihos.indicatoranalysis.service.IndicatorManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.ComputeExpression;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("indicatorAnalysisManager")
public class IndicatorAnalysisManagerImpl extends GenericManagerImpl<IndicatorAnalysis, String> implements IndicatorAnalysisManager {
    private IndicatorAnalysisDao indicatorAnalysisDao;
    private IndicatorManager indicatorManager;
    private GenericSPDao spDao;
    
    @Autowired
    public void setSpDao(GenericSPDao spDao) {
		this.spDao = spDao;
	}
	@Autowired
    public IndicatorAnalysisManagerImpl(IndicatorAnalysisDao indicatorAnalysisDao) {
        super(indicatorAnalysisDao);
        this.indicatorAnalysisDao = indicatorAnalysisDao;
    }
    @Autowired
    public void setIndicatorManager(IndicatorManager indicatorManager) {
		this.indicatorManager = indicatorManager;
	}

	public JQueryPager getIndicatorAnalysisCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return indicatorAnalysisDao.getIndicatorAnalysisCriteria(paginatedList,filters);
	}
	
	@Override
	public List<IndicatorAnalysis> getByLevel(String checkPeriod, String indicatorTypeCode,Integer level, boolean oneLevel) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_checkPeriod",checkPeriod));
		filters.add(new PropertyFilter("EQS_indicator.indicatorType.code",indicatorTypeCode));
		if(oneLevel){
			filters.add(new PropertyFilter("EQI_indicator.level",""+level));
		}else{
			filters.add(new PropertyFilter("LEI_indicator.level",""+level));
		}
		filters.add(new PropertyFilter("OAS_indicator.code",""));
		return this.getByFilters(filters);
	}
	
	@Override
	public List<IndicatorAnalysis> getById(String checkPeriod ,String indicatorId) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_checkPeriod",checkPeriod));
		List<Indicator> indicators = this.indicatorManager.getChildrenByParentId(indicatorId);
		String indicatorIds = "";
		if(indicators!=null && indicators.size()>0){
			for(Indicator indicator:indicators){
				indicatorIds += indicator.getId()+",";
			}
		}
		indicatorIds += indicatorId;
		filters.add(new PropertyFilter("INS_indicator.id",indicatorIds));
		filters.add(new PropertyFilter("OAS_indicator.code",""));
		return this.getByFilters(filters);
	}
	
	@Override
	public List<IndicatorAnalysis> getByTypeCode(String checkPeriod,String indicatorTypeCode) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_checkPeriod",checkPeriod));
		filters.add(new PropertyFilter("EQS_indicator.indicatorType.code",indicatorTypeCode));
		filters.add(new PropertyFilter("OAS_indicator.code",""));
		return this.getByFilters(filters);
	}
	
	@Override
	public void initIndicatorValue(String checkPeriod, String indicatorTypeCode) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_checkPeriod",checkPeriod));
		filters.add(new PropertyFilter("EQS_indicator.indicatorType.code",indicatorTypeCode));
		List<IndicatorAnalysis> indicatorAnalysisList = this.getByFilters(filters);
		if(indicatorAnalysisList!=null && indicatorAnalysisList.size()>0){
			for(IndicatorAnalysis ia:indicatorAnalysisList){
				this.remove(ia.getId());
			}
		}
		List<Indicator> indicators = indicatorManager.getByIndicatorType(indicatorTypeCode);
		IndicatorAnalysis indicatorAnalysis = null;;
		for(Indicator indicator:indicators){
			indicatorAnalysis = new IndicatorAnalysis();
			indicatorAnalysis.setCheckPeriod(checkPeriod);
			indicatorAnalysis.setIndicator(indicator);
			indicatorAnalysis.setValue(0d);
			this.save(indicatorAnalysis);
		}
	}
	
	@Override
	public boolean exeSp(String checkPeriod, String indicatorTypeCode,String taskName) {
		Object[] args = {checkPeriod,indicatorTypeCode};
		String returnMessage = this.spDao.processSp( taskName, args );
		return "处理成功。".equals(returnMessage);
	}
	@Override
	public void calculateIndicatorValue(String checkPeriod,String indicatorTypeCode,String taskName) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_checkPeriod",checkPeriod));
		filters.add(new PropertyFilter("EQS_indicator.indicatorType.code",indicatorTypeCode));
		filters.add(new PropertyFilter("NEB_indicator.leaf","1"));
		filters.add(new PropertyFilter("ODI_indicator.level","indicator.level"));
		List<IndicatorAnalysis> indicatorAnalysisList = this.getByFilters(filters);
		Indicator indicator = null;
		for(IndicatorAnalysis ia:indicatorAnalysisList){
			indicator = ia.getIndicator();
			if(indicator.getLeaf()){
				continue;
			}
			ia.setValue(parseFormulaGetValue(indicator.getRealFormula(),checkPeriod));;
			this.save(ia);
		}
	}
	
	private Double parseFormulaGetValue(String formula,String checkPeriod){
		DecimalFormat decimalFormat = new DecimalFormat("#0.000000");
		Double value = 0d;
		if(formula==null || formula.trim().equals("")){
			value = 0d;
		}else{
			int prevIndex = formula.indexOf('[');
			int endIndex = formula.indexOf(']');
			String id = "";
			while(prevIndex>=0 && endIndex>=0){
				id = formula.substring(prevIndex, endIndex+1);
				value = getValueById(checkPeriod,id.substring(1, id.length()-1));
				String fValue = decimalFormat.format(value);
				if(fValue.startsWith("-")){
					fValue = "(0" +fValue+ ")";
				}
				formula = formula.replace(id,fValue);
				prevIndex = formula.indexOf('[');
				endIndex = formula.indexOf(']');
			}
			value = new ComputeExpression(formula).getDoubleResult();
		}
		return value;
	}
	
	private Double getValueById(String checkPeriod,String indicatorId){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_checkPeriod",checkPeriod));
		filters.add(new PropertyFilter("EQS_indicator.id",indicatorId));
		List<IndicatorAnalysis> indicators = this.getByFilters(filters);
		if(indicators!=null && indicators.size()==1){
			return indicators.get(0).getValue();
		}
		return 0d;
	}
}