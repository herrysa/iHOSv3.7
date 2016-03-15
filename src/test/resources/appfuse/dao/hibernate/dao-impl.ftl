<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
package ${basepackage}.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import ${basepackage}.model.${pojo.shortName};
import ${basepackage}.dao.${pojo.shortName}Dao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("${pojoNameLower}Dao")
public class ${pojo.shortName}DaoHibernate extends GenericDaoHibernate<${pojo.shortName}, ${pojo.getJavaTypeName(pojo.identifierProperty, jdk5)}> implements ${pojo.shortName}Dao {

    public ${pojo.shortName}DaoHibernate() {
        super(${pojo.shortName}.class);
    }
    
    public JQueryPager get${pojo.shortName}Criteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("${pojo.identifierProperty.name}");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ${pojo.shortName}.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("get${pojo.shortName}Criteria", e);
			return paginatedList;
		}

	}
}
