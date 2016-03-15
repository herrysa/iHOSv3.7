package com.huge.ihos.gz.gzItemFormula.dao.hibernate;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.gz.gzItemFormula.dao.GzItemFormulaDao;
import com.huge.ihos.gz.gzItemFormula.dao.GzItemFormulaFilterDao;
import com.huge.ihos.gz.gzItemFormula.model.GzItemFormula;
import com.huge.ihos.gz.gzItemFormula.model.GzItemFormulaFilter;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("gzItemFormulaFilterDao")
public class GzItemFormulaFilterDaoHibernate extends GenericDaoHibernate<GzItemFormulaFilter, String> implements GzItemFormulaFilterDao {

	private GzItemFormulaDao gzItemFormulaDao;
	@Autowired
	public void setGzItemFormulaDao(GzItemFormulaDao gzItemFormulaDao) {
		this.gzItemFormulaDao = gzItemFormulaDao;
	}
    public GzItemFormulaFilterDaoHibernate() {
        super(GzItemFormulaFilter.class);
    }
    
    public JQueryPager getGzItemFormulaFilterCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("filterId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, GzItemFormulaFilter.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getGzItemFormulaFilterCriteria", e);
			return paginatedList;
		}

	}
    @Override
    public void saveGzItemFormulaFilterGridData(String gzItemFormulaJsonStr,String formulaId){
    	GzItemFormula gzItemFormula = gzItemFormulaDao.get(formulaId);
    	JSONObject json=JSONObject.fromObject(gzItemFormulaJsonStr);
    	JSONArray allDatas=json.getJSONArray("edit");
    	String hql = "select filterId from " + this.getPersistentClass().getSimpleName() + " where  gzItemFormula.formulaId=?";
		hql += " ORDER BY filterId ASC";
		List<Object> ids=new ArrayList<Object>();
		ids=this.getHibernateTemplate().find(hql,formulaId);
		String id="";
    	for(int i=0;i<allDatas.size();i++){
    		 //获取每一个JsonObject对象
		    JSONObject myjObject = allDatas.getJSONObject(i);
			if(myjObject.size()>0){
				GzItemFormulaFilter gzItemFormulaFilter = new GzItemFormulaFilter();
				id = myjObject.getString("filterId");
				if(OtherUtil.measureNotNull(id)){
					gzItemFormulaFilter = this.get(id);
					ids.remove(id);
				}
				gzItemFormulaFilter.setCode(myjObject.getString("code"));
				gzItemFormulaFilter.setName(myjObject.getString("name"));
				gzItemFormulaFilter.setOper(myjObject.getString("oper"));
				gzItemFormulaFilter.setSearchValue(myjObject.getString("searchValue"));
				gzItemFormulaFilter.setGzItemFormula(gzItemFormula);
				this.save(gzItemFormulaFilter);
			}
    	}
    	String[] ida=new String[ids.size()];
    	ids.toArray(ida);
    	this.remove(ida);
    }
}
