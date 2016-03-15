package com.huge.ihos.kq.kqUpData.service;


import java.util.List;

import com.huge.ihos.kq.kqUpData.model.KqUpItem;
import com.huge.ihos.kq.kqUpData.model.KqUpItemFormula;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface KqUpItemFormulaManager extends GenericManager<KqUpItemFormula, String> {
     public JQueryPager getKqUpItemFormulaCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 
      * @param kqUpItemFormula
      * @param kqUpItemFormulaJsonStr
      * @return
      */
     public KqUpItemFormula saveKqUpItemFormula(KqUpItemFormula kqUpItemFormula,String kqUpItemFormulaJsonStr);
     /**
      * 检查考勤删除情况
      * @param gzItem
      * @return
      */
     public String checkKqUpItemDel(KqUpItem kqUpItem);
     /**
      * 删除
      * @param idString
      */
     public void deleteKqUpItemFormula(String idString);
}