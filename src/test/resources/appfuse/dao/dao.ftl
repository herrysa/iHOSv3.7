package ${basepackage}.dao;

<#assign classbody>
<#assign pojoName = pojo.importType(pojo.getDeclarationName())>
import java.util.List;

import ${pojo.packageName}.${pojoName};
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ${pojoName} table.
 */
public interface ${pojoName}Dao extends GenericDao<${pojoName}, ${pojo.getJavaTypeName(pojo.identifierProperty, jdk5)}> {
	public JQueryPager get${pojoName}Criteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
</#assign>
${pojo.generateImports()
}
${classbody}
}