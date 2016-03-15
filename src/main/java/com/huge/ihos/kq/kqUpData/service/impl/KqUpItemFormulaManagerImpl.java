package com.huge.ihos.kq.kqUpData.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.kq.kqUpData.dao.KqUpItemFormulaDao;
import com.huge.ihos.kq.kqUpData.dao.KqUpItemFormulaFilterDao;
import com.huge.ihos.kq.kqUpData.model.KqUpItem;
import com.huge.ihos.kq.kqUpData.model.KqUpItemFormula;
import com.huge.ihos.kq.kqUpData.model.KqUpItemFormulaFilter;
import com.huge.ihos.kq.kqUpData.service.KqUpItemFormulaManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("kqUpItemFormulaManager")
public class KqUpItemFormulaManagerImpl extends GenericManagerImpl<KqUpItemFormula, String> implements KqUpItemFormulaManager {
    private KqUpItemFormulaDao kqUpItemFormulaDao;
    private KqUpItemFormulaFilterDao kqUpItemFormulaFilterDao;

    @Autowired
    public void setKqUpItemFormulaFilterDao(KqUpItemFormulaFilterDao kqUpItemFormulaFilterDao) {
		this.kqUpItemFormulaFilterDao = kqUpItemFormulaFilterDao;
	}
    
    @Autowired
    public KqUpItemFormulaManagerImpl(KqUpItemFormulaDao kqUpItemFormulaDao) {
        super(kqUpItemFormulaDao);
        this.kqUpItemFormulaDao = kqUpItemFormulaDao;
    }
    
    public JQueryPager getKqUpItemFormulaCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return kqUpItemFormulaDao.getKqUpItemFormulaCriteria(paginatedList,filters);
	}
    @Override
    public KqUpItemFormula saveKqUpItemFormula(KqUpItemFormula kqUpItemFormula,String kqUpItemFormulaJsonStr){
    	kqUpItemFormula = kqUpItemFormulaDao.save(kqUpItemFormula);
    	if(OtherUtil.measureNotNull(kqUpItemFormulaJsonStr)){
    		kqUpItemFormulaFilterDao.saveKqUpItemFormulaFilterGridData(kqUpItemFormulaJsonStr, kqUpItemFormula.getFormulaId());
    	}
    	return kqUpItemFormula;
    }
    @Override
    public String checkKqUpItemDel(KqUpItem kqUpItem){
    	String itemCode = kqUpItem.getItemCode();
    	String itemName = kqUpItem.getItemName();
    	String kqTypeId = kqUpItem.getKqType().getKqTypeId();
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_kqUpItem.kqType.kqTypeId",kqTypeId));
    	List<KqUpItemFormula> kqUpItemFormulas = this.getByFilters(filters);
    	String resultStr = "";
    	if(OtherUtil.measureNotNull(kqUpItemFormulas)&&!kqUpItemFormulas.isEmpty()){
    		for(KqUpItemFormula formulaTemp:kqUpItemFormulas){
    			String conditionParameter = formulaTemp.getConditionParameter();
    			String resultParameter = formulaTemp.getResultParameter();
    			if(OtherUtil.measureNotNull(conditionParameter)){
    				String[] checkArr = conditionParameter.split(",");
    				List<String> checkList = Arrays.asList(checkArr);
    				if(checkList.contains(itemCode)){
    					String checkItemName = formulaTemp.getKqUpItem().getItemName();
    					String checkFormulaName = formulaTemp.getName();
    					resultStr += "考勤项:"+itemName+" 被考勤项:"+checkItemName+" 中公式:"+checkFormulaName+"条件中使用.";
    				}
    			}
    			if(OtherUtil.measureNotNull(resultParameter)){
    				String[] checkArr = resultParameter.split(",");
    				List<String> checkList = Arrays.asList(checkArr);
    				if(checkList.contains(itemCode)){
    					String checkItemName = formulaTemp.getKqUpItem().getItemName();
    					String checkFormulaName = formulaTemp.getName();
    					resultStr += "考勤项:"+itemName+" 被考勤项:"+checkItemName+" 中公式:"+checkFormulaName+"结果中使用.";
    				}
    			}
    		}
    	}
    	return resultStr;
    }
    @Override
    public void deleteKqUpItemFormula(String idString){
    	String[] ids = idString.split(",");
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("INS_kqUpItemFormula.formulaId",idString));
    	List<KqUpItemFormulaFilter> kqUpItemFormulaFilters = kqUpItemFormulaFilterDao.getByFilters(filters);
    	if(OtherUtil.measureNotNull(kqUpItemFormulaFilters)&&!kqUpItemFormulaFilters.isEmpty()){
    		int length = kqUpItemFormulaFilters.size();
    		String[] filterIds = new String[length];
    		for(int i=0;i<length;i++){
    			KqUpItemFormulaFilter kqUpItemFormulaFilter = kqUpItemFormulaFilters.get(i);
    			filterIds[i] = kqUpItemFormulaFilter.getFilterId();
    		}
    		kqUpItemFormulaFilterDao.remove(filterIds);
    	}
    	this.remove(ids);
    }
}