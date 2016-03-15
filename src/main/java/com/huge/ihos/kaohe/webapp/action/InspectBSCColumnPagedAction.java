package com.huge.ihos.kaohe.webapp.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.kaohe.model.InspectBSCColumn;
import com.huge.ihos.kaohe.service.InspectBSCColumnManager;
import com.huge.ihos.kaohe.service.InspectTemplManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class InspectBSCColumnPagedAction extends JqGridBaseAction  {

	private InspectBSCColumnManager inspectBSCColumnManager;
	private InspectTemplManager inspectTemplManager;
	

	private List<InspectBSCColumn> inspectBSCColumns;
	private InspectBSCColumn inspectBSCColumn;
	private Long columnId;
	
	private String inspectTemplId;
	
	private String columnNames;
	private String statuss;
	private String columnWidths;
		
	private Map columnMap;
	
	
	public Map getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map columnMap) {
		this.columnMap = columnMap;
	}

	public InspectTemplManager getInspectTemplManager() {
		return inspectTemplManager;
	}

	public void setInspectTemplManager(InspectTemplManager inspectTemplManager) {
		this.inspectTemplManager = inspectTemplManager;
	}
	public String getInspectTemplId() {
		return inspectTemplId;
	}

	public void setInspectTemplId(String inspectTemplId) {
		this.inspectTemplId = inspectTemplId;
	}
	
	public String getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

	public String getStatuss() {
		return statuss;
	}

	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}

	
	

	public String getColumnWidths() {
		return columnWidths;
	}

	public void setColumnWidths(String columnWidths) {
		this.columnWidths = columnWidths;
	}

	public InspectBSCColumnManager getInspectBSCColumnManager() {
		return inspectBSCColumnManager;
	}

	public void setInspectBSCColumnManager(InspectBSCColumnManager inspectBSCColumnManager) {
		this.inspectBSCColumnManager = inspectBSCColumnManager;
	}

	public List<InspectBSCColumn> getinspectBSCColumns() {
		return inspectBSCColumns;
	}

	public void setInspectBSCColumns(List<InspectBSCColumn> inspectBSCColumns) {
		this.inspectBSCColumns = inspectBSCColumns;
	}

	public InspectBSCColumn getInspectBSCColumn() {
		return inspectBSCColumn;
	}

	public void setInspectBSCColumn(InspectBSCColumn inspectBSCColumn) {
		this.inspectBSCColumn = inspectBSCColumn;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

	public String inspectBSCColumnGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = inspectBSCColumnManager
					.getInspectBSCColumnCriteria(pagedRequests,filters);
			this.inspectBSCColumns = (List<InspectBSCColumn>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			inspectBSCColumnManager.save(inspectBSCColumn);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "inspectBSCColumn.added" : "inspectBSCColumn.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	inspectBSCColumn = inspectBSCColumnManager.get(columnId);
        	this.setEntityIsNew(false);
        } else {
        	inspectBSCColumn = new InspectBSCColumn();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String inspectBSCColumnGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					log.debug("Delete Customer " + removeId);
					InspectBSCColumn inspectBSCColumn = inspectBSCColumnManager.get(new Long(removeId));
					inspectBSCColumnManager.remove(new Long(removeId));
					
				}
				gridOperationMessage = this.getText("inspectBSCColumn.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInspectBSCColumnGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (inspectBSCColumn == null) {
			return "Invalid inspectBSCColumn Data";
		}

		return SUCCESS;

	}
	
	public String saveColumnSet(){
		try {
			inspectBSCColumnManager.delByInspectTemplId(inspectTemplId);
			String[] columnNameArr = columnNames.split(",");
			String[] statusArr = statuss.split(",");
			String[] columnWidthArr = columnWidths.split(",");
			for(int i=0;i<columnNameArr.length;i++){
				InspectBSCColumn inspectBSCColumn = new InspectBSCColumn();
				inspectBSCColumn.setColumnName(columnNameArr[i]);
				inspectBSCColumn.setInspectTempl(inspectTemplManager.get(inspectTemplId));
				inspectBSCColumn.setStatus(statusArr[i].equals("true")?true:false);
				inspectBSCColumn.setDisOrder(new Long(i+1));
				inspectBSCColumn.setWidth(columnWidthArr[i]);
				inspectBSCColumnManager.save(inspectBSCColumn);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ajaxForward("保存成功！");
	}
	
	public String getColumnInfo(){
		try {
			List<InspectBSCColumn> inspectBSCColumns = inspectBSCColumnManager.findByInspectTemplId(inspectTemplId);
			columnMap = new LinkedHashMap();
			for(InspectBSCColumn inspectBSCColumn : inspectBSCColumns){
				columnMap.put(inspectBSCColumn.getColumnName(), inspectBSCColumn);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SUCCESS;
	}
}

