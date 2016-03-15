package com.huge.ihos.gz.gzItemFormula.service;


import java.util.List;

import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzItemFormula.model.GzItemFormula;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface GzItemFormulaManager extends GenericManager<GzItemFormula, String> {
     public JQueryPager getGzItemFormulaCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
    /**
     * 保存
     * @param gzItemFormula
     * @param gzItemFormulaJsonStr
     * @return
     */
     public GzItemFormula saveGzItemFormula(GzItemFormula gzItemFormula,String gzItemFormulaJsonStr);
     
     public void deleteGzItemFormula(String idString);
     /**
      * 检查工资删除情况
      * @param gzItem
      * @return
      */
     public String checkGzItemDel(GzItem gzItem);
}