package com.huge.ihos.system.configuration.dictionary.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.exceptions.DuplicateRecordException;
import com.huge.ihos.system.configuration.dictionary.model.Dictionary;
import com.huge.ihos.system.configuration.dictionary.service.DictionaryItemManager;
import com.huge.ihos.system.configuration.dictionary.service.DictionaryManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class DictionaryAction
    extends JqGridBaseAction
     {
    /**
     * 
     */
    private static final long serialVersionUID = -4032298776344009079L;

    private DictionaryManager dictionaryManager;

    private DictionaryItemManager dictionaryItemManager;

    public DictionaryItemManager getDictionaryItemManager() {
		return dictionaryItemManager;
	}

	public void setDictionaryItemManager( DictionaryItemManager dictionaryItemManager ) {
        this.dictionaryItemManager = dictionaryItemManager;
    }

    private List<Dictionary> dictionaries;

    private Dictionary dictionary;

    private Long dictionaryId;

    public String dictionaryList() {
        log.debug( "enter list method!" );
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = dictionaryManager.getAppManagerCriteriaWithSearch( pagedRequests, Dictionary.class, filters );
            //.getDictionaryCriteria(pagedRequests);
            this.dictionaries = (List<Dictionary>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "List Error", e );
        }
        return SUCCESS;
    }

    public String save() {
        String error = isValid();
        if ( !error.equalsIgnoreCase( SUCCESS ) ) {
            gridOperationMessage = error;
            return ajaxForwardError( gridOperationMessage );
        }
        try {
            dictionaryManager.saveDictionary( dictionary );
        }
        catch ( DuplicateRecordException dre ) {
            gridOperationMessage = dre.getMessage();
            return ajaxForwardError( gridOperationMessage );
        }
        return ajaxForward( "字典添加成功！" );
    }

    public String edit() {
        if ( dictionaryId != null ) {
            dictionary = dictionaryManager.get( dictionaryId );
        }
        else {
            dictionary = new Dictionary();
        }

        return SUCCESS;
    }

    public String dictionaryEdit() {
        boolean flag = true;
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    int removeId = Integer.parseInt( ids.nextToken() );
                    log.debug( "Delete Customer " + removeId );
                    Dictionary dictionary = dictionaryManager.get( new Long( removeId ) );
                    if ( dictionary.getDictionaryItemses().size() != 0 ) {
                        gridOperationMessage = "请先删除'" + dictionary.getName() + "'下的条目！";
                        flag = false;
                        break;
                    }
                    else {
                        dictionaryManager.remove( new Long( removeId ) );
                    }

                }
                if ( flag ) {
                    gridOperationMessage = this.getText( "dictionary.deleted" );
                }
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                try {
                    dictionaryManager.saveDictionary( dictionary );
                }
                catch ( DuplicateRecordException dre ) {
                    gridOperationMessage = dre.getMessage();
                    return SUCCESS;
                }
                gridOperationMessage = "Dictionary Saved";
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            // TODO: handle exception
            log.error( "checkDictionaryGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( false, "删除错误，请检查数据！", false );
        }
    }

    private String isValid() {
        if ( dictionary == null ) {
            return "Invalid dictionary Data";
        }

        return SUCCESS;

    }

    public DictionaryManager getDictionaryManager() {
        return dictionaryManager;
    }

    public void setDictionaryManager( DictionaryManager dictionaryManager ) {
        this.dictionaryManager = dictionaryManager;
    }

    public List<Dictionary> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries( List<Dictionary> dictionaries ) {
        this.dictionaries = dictionaries;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary( Dictionary dictionary ) {
        this.dictionary = dictionary;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId( Long dictionaryId ) {
        this.dictionaryId = dictionaryId;
    }
}
