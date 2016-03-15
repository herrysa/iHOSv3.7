package com.huge.ihos.system.systemManager.organization.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.ihos.system.systemManager.organization.dao.PersonTypeDao;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.ihos.system.systemManager.organization.service.PersonTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("personTypeManager")
public class PersonTypeManagerImpl extends GenericManagerImpl<PersonType, String> implements PersonTypeManager {
    private PersonTypeDao personTypeDao;

    @Autowired
    public PersonTypeManagerImpl(PersonTypeDao personTypeDao) {
        super(personTypeDao);
        this.personTypeDao = personTypeDao;
    }
    
    public JQueryPager getPersonTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return personTypeDao.getPersonTypeCriteria(paginatedList,filters);
	}
    @Override
    public PersonType getPersonTypeByName(String name){
    	if(OtherUtil.measureNull(name)){
    		return null;
    	}
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_name", name));
    	List<PersonType> personTypes = new ArrayList<PersonType>();
    	personTypes=this.personTypeDao.getByFilters(filters);
    	if(personTypes!=null&&personTypes.size()>0){
    		return personTypes.get(0);
    	}else{
    		return null;
    	}
    }
    @Override
    public PersonType savePersonType(PersonType personType){
    	personType = this.save(personType);
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_parentType.id", personType.getId()));
		List<PersonType> hrPersonTypes = new ArrayList<PersonType>();
		hrPersonTypes = this.getByFilters(filters);
		if(hrPersonTypes != null&&hrPersonTypes.size()>0){
			 personType.setLeaf(false);
		}else{
			 personType.setLeaf(true);
		}
		personType=this.save(personType);
		if(personType.getParentType() != null){
			PersonType personTypeParent = new PersonType(); 
			personTypeParent = this.get(personType.getParentType().getId());
			personTypeParent.setLeaf(false);
			this.save(personTypeParent);
		}
		return personType;
    }

	@Override
	public PersonType getPersonTypeByCode(String code) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_code", code));
    	List<PersonType> personTypes = this.personTypeDao.getByFilters(filters);
    	if(personTypes != null && personTypes.size() == 1){
    		return personTypes.get(0);
    	}else{
    		return null;
    	}
	}
    @Override
    public void deletePersonType(String[] ids){
    	for(int i=0;i<ids.length;i++){
    		String id = ids[i];
    		PersonType personType = new PersonType();
    		personType = this.get(id);
    		if(personType.getParentType() != null){
    			personType = this.get(personType.getParentType().getId());
    			personType.setLeaf(true);
    			this.save(personType);
    		}
    		this.remove(id);
    	}
    }
    @Override
    public List<String> personTypeNameList(){
    	List<String> personTypeNameList = new ArrayList<String>();
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQB_disabled","0"));
    	filters.add(new PropertyFilter("EQB_leaf","1"));
    	List<PersonType> personTypes = personTypeDao.getByFilters(filters);
    	if(OtherUtil.measureNotNull(personTypes)&&!personTypes.isEmpty()){
    		for(PersonType personType:personTypes){
    			personTypeNameList.add(personType.getName());
    		}
    	}
    	return personTypeNameList;
    }
    @Override
    public List<DictionaryItem> personTypeDictionaryList(){
    	List<DictionaryItem> personTypeNameList = new ArrayList<DictionaryItem>();
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQB_disabled","0"));
    	filters.add(new PropertyFilter("EQB_leaf","1"));
    	List<PersonType> personTypes = personTypeDao.getByFilters(filters);
    	if(OtherUtil.measureNotNull(personTypes)&&!personTypes.isEmpty()){
    		for(PersonType personType:personTypes){
    			DictionaryItem itemTemp = new DictionaryItem();
    			itemTemp.setContent(personType.getName());
    			itemTemp.setValue(personType.getName());
    			personTypeNameList.add(itemTemp);
    		}
    	}
    	return personTypeNameList;
    }
}