package com.huge.ihos.hql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HqlUtil {

	private String entityName;
	private String[] colName;
	private List<QueryItem> queryItems = new ArrayList<QueryItem>();
	private List sorts = new ArrayList();
	private String findType = "page";
	private int page = 1;
	private int pageSize = 20;
	

	public HqlUtil(String entityName){
		this.entityName = entityName;
	}
	
	public String getHQl(){
		String hql = "";
		String colNameStr = getColName(colName);
		String whereStr = getWhere();
		String orderBy = getOrder();
		hql = "select "+colNameStr+" from "+this.entityName+" as entity ";
		hql +=getJoin(colName);
		if(!"".equals(whereStr)){
			hql += " where "+whereStr;
		}
		if(!"".equals(orderBy)){
			hql += " order by "+orderBy;
		}
		System.out.println(hql);
		return hql;
	}
	
	 public String getColName(String[] colNameArr){
	    	String colNameStr = "";
	    	for(String colName : colNameArr){
	    		String name = colName;
	    		String alias = name;
	    		if(alias.contains(".")){
	    			alias = alias.replace(".", "_");
	    			colNameStr += name+" as "+alias+", ";
	    		}else{
	    			colNameStr += "entity."+name+" as "+alias+", ";
	    		}
	    	}
	    	if(!"".equals(colNameStr)){
	    		colNameStr = colNameStr.trim();
	    		colNameStr = colNameStr.substring(0,colNameStr.length()-1);
	    	}
	    	return colNameStr;
	    	/*if(!"select ".equals(hql)){
	    		hql = hql.substring(0,hql.length()-1);
	    		hql += " from "+entityName+" entity";
	    		return hql;
	    	}else{
	    		return hql = null;
	    	}*/
	    }
	 public String getJoin(String[] colNameArr){
	    	String colNameStr = "";
	    	for(String colName : colNameArr){
	    		String name = colName;
	    		String alias = name;
	    		if(alias.contains(".")){
	    			alias = name.split("\\.")[0];
	    			//alias = alias.replace(".", "_");
	    			colNameStr += "left join entity."+alias+" as "+alias+" ";
	    		}
	    	}
	    	/*if(!"".equals(colNameStr)){
	    		colNameStr = colNameStr.trim();
	    		colNameStr = colNameStr.substring(0,colNameStr.length()-1);
	    	}*/
	    	return colNameStr;
	    	/*if(!"select ".equals(hql)){
	    		hql = hql.substring(0,hql.length()-1);
	    		hql += " from "+entityName+" entity";
	    		return hql;
	    	}else{
	    		return hql = null;
	    	}*/
	    }
	public String getWhere() {
        StringBuffer sb = null;
        Iterator<QueryItem> itr = this.queryItems.iterator();
        if ( !itr.hasNext() )
            return "";
        do {
        	QueryItem queryItem = itr.next();
            Object bindValue = queryItem.getValue();
            String str = queryItem.getField();
            str = " entity."+str;
            if ( sb == null )
                sb = new StringBuffer();
            else
                sb.append( " " + queryItem.getQueryJunction().toString() + " " );
            //sb.append( queryItem.getLBracket().toString() );
           /* if ( ( bindValue instanceof SearchCriteria ) ) {
                SearchCriteria criteria = (SearchCriteria) bindValue;
                sb.append( "(" );
                sb.append( criteria.getWhere() );
                sb.append( ")" );
            }*/
            if ( queryItem.getOperator().equals( QueryOperator.LIKE ) ) {
            	if(bindValue!=null&&bindValue.toString().contains("*")){
            		String bv = bindValue.toString();
            		if(bv.startsWith("*")){
            			bv = "%"+bv.substring(1, bv.length());
            		}
            		if(bv.endsWith("*")){
            			bv = bv.substring(0, bv.length()-1)+"%";
            		}
            		bindValue = bv;
            		
            	}
                sb.append( str + " like '" + bindValue + "'" );
            }
            else if ( queryItem.getOperator().equals( QueryOperator.IN ) ) {
            	String vStr = bindValue.toString();
            	String[] vArr = null;
            	if(bindValue instanceof String[]){
            		vArr = (String[])bindValue;
            	}else{
            		vArr = vStr.split(",");
            	}
            	sb.append( str + " in (");
            	for(int vIndex=0;vIndex<vArr.length;vIndex++){
            		String v =  vArr[vIndex];
            		if(vIndex==0){
            			sb.append("'"+ v+"'" );
            		}else{
            			sb.append(","+ "'" + v + "'");
            		}
            	}
            	sb.append( ")");
            }
            else {
                sb.append( str );
                sb.append( " " + queryItem.getOperator().toString() + "?" );
            }
            //sb.append( binding.getRBracket().toString() );
        }
        while ( itr.hasNext() );
        if ( sb == null )
            return "";
        return sb.toString();
    }
	
	public void addQueryItem( String field,String op,Object searchValue ) {
        this.queryItems.add( new QueryItem(field,op,searchValue) );
    }
	
	public void addSort( String sortField ) {
		if( sortField != null )
			this.sorts.add( sortField );
	}

	public void addSorts( String[] sortFields ) {
		for ( int i = 0; i < sortFields.length; i++ ) {
			if ( sortFields[i] == null )
				continue;
				this.sorts.add( sortFields[i] );
		}
	}
	
	public String getOrder() {
        Iterator itr = this.sorts.iterator();
        StringBuffer sb = null;
        if ( !itr.hasNext() )
            return "";
        for ( int i = 0; itr.hasNext(); i++ ) {
            if ( sb == null )
                sb = new StringBuffer();
            else
                sb.append( "," );
            String str = (String) itr.next();
            str = " entity."+str;
            sb.append( str );
        }
        return sb.toString();
    }
	
	public Object[] getRealAgrs() {
        List list = new ArrayList();
        Iterator itr = queryItems.iterator();
        while ( itr.hasNext() ) {
            QueryItem b = (QueryItem) itr.next();
            if ( !b.getOperator().equals( QueryOperator.LIKE ) &&!b.getOperator().equals(QueryOperator.IN))
                list.add( b.getValue() );
        }

       /* List realList = new ArrayList();
        if ( this.sourceSql.toUpperCase().trim().startsWith( "SELECT " ) ) {
            for ( int i = 0; i < this.replaceWhereCount; i++ ) {
                realList.addAll( list );
            }
        }
        else {
            realList = list;
        }*/

        return list.toArray();
    }
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String[] getColName() {
		return colName;
	}
	public void setColName(String[] colName) {
		this.colName = colName;
	}
	public List<QueryItem> getQueryItems() {
		return queryItems;
	}

	public void setQueryItems(List<QueryItem> queryItems) {
		this.queryItems = queryItems;
	}
	public List getSorts() {
		return sorts;
	}
	public void setSorts(List sorts) {
		this.sorts = sorts;
	}
	public String getFindType() {
		return findType;
	}

	public void setFindType(String findType) {
		this.findType = findType;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
