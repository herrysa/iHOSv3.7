package com.huge.ihos.system.configuration.dictionary.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.configuration.dictionary.model.Dictionary;
import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.ihos.system.configuration.dictionary.service.DictionaryItemManager;
import com.huge.ihos.system.configuration.dictionary.service.DictionaryManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class DictionaryItemAction
    extends JqGridBaseAction
     {

    /**
     * 
     */
    private static final long serialVersionUID = 7901562028082838679L;

    private DictionaryItemManager dictionaryItemManager;

    private DictionaryManager dictionaryManager;

    private List<DictionaryItem> dictionaryItems;

    private List<DictionaryItem> dictionaryItemsSelectList;

    private DictionaryItem dictionaryItem;

    private String dictionaryId;

    private String dicCode;

    private Long dictionaryItemId;

    public String save() {
        String error = isValid();
        if ( !error.equalsIgnoreCase( SUCCESS ) ) {
            gridOperationMessage = error;
            return SUCCESS;
        }
        if ( dictionaryId != null && !dictionaryId.equals( "" ) ) {
            dictionaryItem.setDictionary( dictionaryManager.get( Long.parseLong( dictionaryId ) ) );
            dictionaryItemManager.save( dictionaryItem );
        }
        return ajaxForward( "字典项目添加成功！" );
    }

    public String edit() {
        if ( dictionaryItemId != null ) {
            dictionaryItem = dictionaryItemManager.get( dictionaryItemId );
        }
        else {
            dictionaryItem = new DictionaryItem();
        }

        return SUCCESS;
    }

    public String dictionaryItemList() {
        log.debug( "enter list method!" );
        try {
            if ( dictionaryId != null ) {
                List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
                JQueryPager pagedRequests = null;
                pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
                pagedRequests = dictionaryItemManager.getDictionaryItemCriteria( pagedRequests, new Long( dictionaryId ) );
                this.dictionaryItems = (List<DictionaryItem>) pagedRequests.getList();
                records = pagedRequests.getTotalNumberOfRows();
                total = pagedRequests.getTotalNumberOfPages();
                page = pagedRequests.getPageNumber();
            }

        }
        catch ( Exception e ) {
            log.error( "List Error", e );
        }
        return SUCCESS;
    }

    public String searchDictionaryItemsListByCode() {
        String diccode = this.getRequest().getParameter( "dicCode" );
        this.dictionaryItemsSelectList = this.dictionaryItemManager.getAllItemsByCode( diccode );
        //		Iterator itr = dictionaryItemsSelectList.iterator();
        //		while (itr.hasNext()) {
        //			DictionaryItem item = (DictionaryItem) itr.next();
        //			String v = item.getValue();
        //			
        //			byte[] bs = v.getBytes();
        //			   //用新的字符编码生成字符串
        //			try {
        //				item.setValue(new String(bs, "GBK")) ;
        //			} catch (UnsupportedEncodingException e) {
        //				e.printStackTrace();
        //			}
        //			
        //			// item.setValue(v.)
        //		}
        return SUCCESS;
    }

    public String dictionaryItemEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    int removeId = Integer.parseInt( ids.nextToken() );
                    log.debug( "Delete Customer " + removeId );
                    dictionaryItemManager.removeItem( new Long( removeId ) );
                }
                gridOperationMessage = this.getText( "dictionaryItem.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                Dictionary dic = dictionaryManager.get( new Long( this.dictionaryId ) );
                dictionaryItem.setDictionary( dic );
                dictionaryItemManager.saveItem( dictionaryItem );

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
        if ( dictionaryItem == null ) {
            return "Invalid dictionaryItem Data";
        }

        return SUCCESS;

    }

    public List<DictionaryItem> getDictionaryItems() {
        return dictionaryItems;
    }

    public void setDictionaryItems( List<DictionaryItem> dictionaryItems ) {
        this.dictionaryItems = dictionaryItems;
    }

    public DictionaryItem getDictionaryItem() {
        return dictionaryItem;
    }

    public void setDictionaryItem( DictionaryItem dictionaryItem ) {
        this.dictionaryItem = dictionaryItem;
    }

    //	public String getDictionaryItemId() {
    //		return dictionaryItemId;
    //	}
    //	public void setDictionaryItemId(String dictionaryItemId) {
    //		this.dictionaryItemId = dictionaryItemId;
    //	}
    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId( String dictionaryId ) {
        this.dictionaryId = dictionaryId;
    }

    public DictionaryItemManager getDictionaryItemManager() {
        return dictionaryItemManager;
    }

    public void setDictionaryItemManager( DictionaryItemManager dictionaryItemManager ) {
        this.dictionaryItemManager = dictionaryItemManager;
    }

    public DictionaryManager getDictionaryManager() {
        return dictionaryManager;
    }

    public void setDictionaryManager( DictionaryManager dictionaryManager ) {
        this.dictionaryManager = dictionaryManager;
    }

    public List<DictionaryItem> getDictionaryItemsSelectList() {
        return dictionaryItemsSelectList;
    }

    public void setDictionaryItemsSelectList( List<DictionaryItem> dictionaryItemsSelectList ) {
        this.dictionaryItemsSelectList = dictionaryItemsSelectList;
    }



    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode( String dicCode ) {
        this.dicCode = dicCode;
    }

    public Long getDictionaryItemId() {
        return dictionaryItemId;
    }

    public void setDictionaryItemId( Long dictionaryItemId ) {
        this.dictionaryItemId = dictionaryItemId;
    }
}
