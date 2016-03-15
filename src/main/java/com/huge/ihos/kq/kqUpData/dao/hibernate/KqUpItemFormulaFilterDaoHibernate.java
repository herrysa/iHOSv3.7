package com.huge.ihos.kq.kqUpData.dao.hibernate;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.kq.kqUpData.dao.KqUpItemFormulaDao;
import com.huge.ihos.kq.kqUpData.dao.KqUpItemFormulaFilterDao;
import com.huge.ihos.kq.kqUpData.model.KqUpItemFormula;
import com.huge.ihos.kq.kqUpData.model.KqUpItemFormulaFilter;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("kqUpItemFormulaFilterDao")
public class KqUpItemFormulaFilterDaoHibernate extends GenericDaoHibernate<KqUpItemFormulaFilter, String> implements KqUpItemFormulaFilterDao {
	private KqUpItemFormulaDao kqUpItemFormulaDao;
    public KqUpItemFormulaFilterDaoHibernate() {
        super(KqUpItemFormulaFilter.class);
    }
    @Autowired
    public void setKqUpItemFormulaDao(KqUpItemFormulaDao kqUpItemFormulaDao) {
		this.kqUpItemFormulaDao = kqUpItemFormulaDao;
	}
    
    public JQueryPager getKqUpItemFormulaFilterCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("filterId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, KqUpItemFormulaFilter.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getKqUpItemFormulaFilterCriteria", e);
			return paginatedList;
		}

	}
    @Override
    public void saveKqUpItemFormulaFilterGridData(String kqUpItemFormulaJsonStr,String formulaId){
    	KqUpItemFormula kqUpItemFormula = kqUpItemFormulaDao.get(formulaId);
    	JSONObject json = JSONObject.fromObject(kqUpItemFormulaJsonStr);
    	JSONArray allDatas = json.getJSONArray("edit");
    	String hql = "select filterId from " + this.getPersistentClass().getSimpleName() + " where  kqUpItemFormula.formulaId=?";
		hql += " ORDER BY filterId ASC";
		List<Object> ids = new ArrayList<Object>();
		ids = this.getHibernateTemplate().find(hql,formulaId);
		String id = "";
    	for(int i=0;i<allDatas.size();i++){
    		 //获取每一个JsonObject对象
		    JSONObject myjObject = allDatas.getJSONObject(i);
			if(myjObject.size()>0){
				KqUpItemFormulaFilter kqUpItemFormulaFilter = new KqUpItemFormulaFilter();
				id = myjObject.getString("filterId");
				if(OtherUtil.measureNotNull(id)){
					kqUpItemFormulaFilter = this.get(id);
					ids.remove(id);
				}
				kqUpItemFormulaFilter.setCode(myjObject.getString("code"));
				kqUpItemFormulaFilter.setName(myjObject.getString("name"));
				kqUpItemFormulaFilter.setOper(myjObject.getString("oper"));
				kqUpItemFormulaFilter.setSearchValue(myjObject.getString("searchValue"));
				kqUpItemFormulaFilter.setKqUpItemFormula(kqUpItemFormula);
				this.save(kqUpItemFormulaFilter);
			}
    	}
    	String[] ida=new String[ids.size()];
    	ids.toArray(ida);
    	this.remove(ida);
    }
}
