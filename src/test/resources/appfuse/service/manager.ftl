package ${basepackage}.service;


import java.util.List;
import ${basepackage}.model.${pojo.shortName};
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ${pojo.shortName}Manager extends GenericManager<${pojo.shortName}, ${pojo.getJavaTypeName(pojo.identifierProperty, jdk5)}> {
     public JQueryPager get${pojo.shortName}Criteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}