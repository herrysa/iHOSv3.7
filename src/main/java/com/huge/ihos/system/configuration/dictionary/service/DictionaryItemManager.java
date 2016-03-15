package com.huge.ihos.system.configuration.dictionary.service;

import java.util.List;

import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface DictionaryItemManager
    extends GenericManager<DictionaryItem, Long> {
    public JQueryPager getDictionaryItemCriteria( final JQueryPager paginatedList, Long parentId );

    //@Cacheable(value="dictionItems",key="#code")
    // 	 @Cacheable(value="dictionItems",key="#code")
    public List getAllItemsByCode( String code );

    //@CacheEvict(value = "dictionItems", allEntries=true)
    public void saveItem( DictionaryItem Item );

    //@CacheEvict(value = "dictionItems", allEntries=true)
    public DictionaryItem getItem( Long id );

    // @CacheEvict(value = "dictionItems", allEntries=true)
    public void removeItem( Long id );
    /**
     * 获取数据字典项目值
     * @param code
     * @return
     */
    public List<String> getAllItemValuesByCode( String code );
}
