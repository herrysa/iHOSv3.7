package com.huge.ihos.system.systemManager.organization.service;


import java.util.List;

import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PersonTypeManager extends GenericManager<PersonType, String> {
     public JQueryPager getPersonTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public PersonType getPersonTypeByName(String name);
     public PersonType savePersonType(PersonType PersonType);
     /**
      * 根据编码获取人员类别，如果未找到返回null
      * @param code 人员类别编码
      * @return
      */
     public PersonType getPersonTypeByCode(String code);
     public void deletePersonType(String[] ids);
     /**
      * 末级非停用类别名称列表
      * @return
      */
     public List<String> personTypeNameList();
     /**
      * 末级非停用类别名称列表
      * @return
      */
     public List<DictionaryItem> personTypeDictionaryList();
}