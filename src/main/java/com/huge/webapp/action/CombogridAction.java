package com.huge.webapp.action;

import java.util.List;

import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class CombogridAction extends JqGridBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String comboGridList() {
		log.debug("enter list method!");
		try {
			/*if (this.searchTerm != null) {
				this.searchTerm = "%" + this.searchTerm + "%";
			} else {
				this.searchTerm = "%%";
			}*/
 			if(!"".equals(sql)){
 				UserContext userContext = UserContextUtil.getUserContext();
 				if(sql.contains("%ORGCODE%")){
 					sql = sql.replace("%ORGCODE%", userContext.getOrgCode());
 				}
 				if(sql.contains("%COPYCODE%")){
 					sql = sql.replace("%COPYCODE%", userContext.getCopyCode());
 				}
 				if(sql.contains("%PERIODYEAR%")){//待修改2015-10-28
 					//sql = sql.replace("%PERIODYEAR%", userContext.getKjYear());
 				}
				if(sql.contains("where") || sql.contains("WHERE")){
					sql += " and (";
				}else{
					sql += " where 1=1 and (";
				}
				
				String escape = " escape '\\' ";
				String[] cloumnArr = cloumns.split(",");
				String[] specialCharArr = {};
				if(!"".equals(specialChar)){
					specialCharArr = specialChar.split(",");
				}
				boolean escapeFlag = false;
				Object[] args = new Object[cloumnArr.length];
				for( int i = 0; i < specialCharArr.length; i++ ){
					if(searchTerm.contains(specialCharArr[i])){
						searchTerm = searchTerm.replace(specialCharArr[i], "\\"+specialCharArr[i]);
						escapeFlag = true;
					}
				}
				
				for ( int i = 0; i < cloumnArr.length; i++ ) {
					sql += cloumnArr[i] + " like ? ";
					args[i] = "%"+searchTerm+"%";
					if(escapeFlag){
						sql += escape;
					}
					sql += " or ";
				}
				sql=OtherUtil.subStrEnd(sql, "or ");
				sql += ")";
		/*	List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());*/
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = comboGridManager.getComboGridCriteria(pagedRequests, sql, args);
			this.rows = pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
			}

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	private String sql = "";
	private String cloumns = "";
	private String specialChar = "";

	private String searchTerm = "";
	private List rows;
	private com.huge.service.ComboGridManager comboGridManager;
	public String getSpecialChar() {
		return specialChar;
	}

	public void setSpecialChar(String specialChar) {
		this.specialChar = specialChar;
	}


	public String getCloumns() {
		return cloumns;
	}
	
	public void setCloumns(String cloumns) {
		this.cloumns = cloumns;
	}
	public List getRows() {
		return rows;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public void setComboGridManager(com.huge.service.ComboGridManager comboGridManager) {
		this.comboGridManager = comboGridManager;
	}

}
