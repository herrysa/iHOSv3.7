<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
package ${basepackage}.service.impl;

import java.util.List;
import ${basepackage}.dao.${pojo.shortName}Dao;
import ${basepackage}.model.${pojo.shortName};
import ${basepackage}.service.${pojo.shortName}Manager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("${pojoNameLower}Manager")
public class ${pojo.shortName}ManagerImpl extends GenericManagerImpl<${pojo.shortName}, ${pojo.getJavaTypeName(pojo.identifierProperty, jdk5)}> implements ${pojo.shortName}Manager {
    private ${pojo.shortName}Dao ${pojoNameLower}Dao;

    @Autowired
    public ${pojo.shortName}ManagerImpl(${pojo.shortName}Dao ${pojoNameLower}Dao) {
        super(${pojoNameLower}Dao);
        this.${pojoNameLower}Dao = ${pojoNameLower}Dao;
    }
    
    public JQueryPager get${pojo.shortName}Criteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return ${pojoNameLower}Dao.get${pojo.shortName}Criteria(paginatedList,filters);
	}
}