package com.huge.ihos.gz.gzItemFormula.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzItemFormula.dao.GzItemFormulaDao;
import com.huge.ihos.gz.gzItemFormula.dao.GzItemFormulaFilterDao;
import com.huge.ihos.gz.gzItemFormula.model.GzItemFormula;
import com.huge.ihos.gz.gzItemFormula.model.GzItemFormulaFilter;
import com.huge.ihos.gz.gzItemFormula.service.GzItemFormulaManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("gzItemFormulaManager")
public class GzItemFormulaManagerImpl extends GenericManagerImpl<GzItemFormula, String> implements GzItemFormulaManager {
    private GzItemFormulaDao gzItemFormulaDao;
    private GzItemFormulaFilterDao gzItemFormulaFilterDao;

    @Autowired
    public GzItemFormulaManagerImpl(GzItemFormulaDao gzItemFormulaDao) {
        super(gzItemFormulaDao);
        this.gzItemFormulaDao = gzItemFormulaDao;
    }
    @Autowired
    public void setGzItemFormulaFilterDao(GzItemFormulaFilterDao gzItemFormulaFilterDao) {
		this.gzItemFormulaFilterDao = gzItemFormulaFilterDao;
	}
    
    public JQueryPager getGzItemFormulaCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return gzItemFormulaDao.getGzItemFormulaCriteria(paginatedList,filters);
	}
    @Override
    public GzItemFormula saveGzItemFormula(GzItemFormula gzItemFormula,String gzItemFormulaJsonStr){
    	gzItemFormula = gzItemFormulaDao.save(gzItemFormula);
    	if(OtherUtil.measureNotNull(gzItemFormulaJsonStr)){
    		gzItemFormulaFilterDao.saveGzItemFormulaFilterGridData(gzItemFormulaJsonStr, gzItemFormula.getFormulaId());
    	}
    	return gzItemFormula;
    }
    @Override
    public void deleteGzItemFormula(String idString){
    	String[] ids = idString.split(",");
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("INS_gzItemFormula.formulaId",idString));
    	List<GzItemFormulaFilter> gzItemFormulaFilters = gzItemFormulaFilterDao.getByFilters(filters);
    	if(OtherUtil.measureNotNull(gzItemFormulaFilters)&&!gzItemFormulaFilters.isEmpty()){
    		int length = gzItemFormulaFilters.size();
    		String[] filterIds = new String[length];
    		for(int i=0;i<length;i++){
    			GzItemFormulaFilter gzItemFormulaFilter = gzItemFormulaFilters.get(i);
    			filterIds[i] = gzItemFormulaFilter.getFilterId();
    		}
    		gzItemFormulaFilterDao.remove(filterIds);
    	}
    	this.remove(ids);
    }
    public String checkGzItemDel(GzItem gzItem){
    	String itemCode = gzItem.getItemCode();
    	String itemName = gzItem.getItemName();
    	String gzTypeId = gzItem.getGzType().getGzTypeId();
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_gzItem.gzType.gzTypeId",gzTypeId));
    	List<GzItemFormula> gzItemFormulas = this.getByFilters(filters);
    	String resultStr = "";
    	if(OtherUtil.measureNotNull(gzItemFormulas)&&!gzItemFormulas.isEmpty()){
    		for(GzItemFormula formulaTemp:gzItemFormulas){
    			String conditionParameter = formulaTemp.getConditionParameter();
    			String resultParameter = formulaTemp.getResultParameter();
    			if(OtherUtil.measureNotNull(conditionParameter)){
    				String[] checkArr = conditionParameter.split(",");
    				List<String> checkList = Arrays.asList(checkArr);
    				if(checkList.contains(itemCode)){
    					String checkItemName = formulaTemp.getGzItem().getItemName();
    					String checkFormulaName = formulaTemp.getName();
    					resultStr += "工资项:"+itemName+" 被工资项:"+checkItemName+" 中公式:"+checkFormulaName+"条件中使用.";
    				}
    			}
    			if(OtherUtil.measureNotNull(resultParameter)){
    				String[] checkArr = resultParameter.split(",");
    				List<String> checkList = Arrays.asList(checkArr);
    				if(checkList.contains(itemCode)){
    					String checkItemName = formulaTemp.getGzItem().getItemName();
    					String checkFormulaName = formulaTemp.getName();
    					resultStr += "工资项:"+itemName+" 被工资项:"+checkItemName+" 中公式:"+checkFormulaName+"结果中使用.";
    				}
    			}
    		}
    	}
    	return resultStr;
    }
}