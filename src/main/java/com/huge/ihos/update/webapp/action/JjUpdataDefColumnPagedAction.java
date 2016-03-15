package com.huge.ihos.update.webapp.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.ihos.update.model.JjUpdataDefColumn;
import com.huge.ihos.update.service.JjUpdataDefColumnManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.SpringContextHelper;
import com.opensymphony.xwork2.Preparable;




public class JjUpdataDefColumnPagedAction extends JqGridBaseAction   {

	private JjUpdataDefColumnManager jjUpdataDefColumnManager;
	private List<JjUpdataDefColumn> jjUpdataDefColumns;
	private JjUpdataDefColumn jjUpdataDefColumn;
	private Long columnId;
	private DataSource dataSource;
	
	private String columnIdStr;
	private String titleStr;
	private String formulaStr;
	private String typeStr;
	private String disorderStr;

	private String disableStr;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public JjUpdataDefColumnManager getJjUpdataDefColumnManager() {
		return jjUpdataDefColumnManager;
	}

	public void setJjUpdataDefColumnManager(JjUpdataDefColumnManager jjUpdataDefColumnManager) {
		this.jjUpdataDefColumnManager = jjUpdataDefColumnManager;
	}

	public List<JjUpdataDefColumn> getjjUpdataDefColumns() {
		return jjUpdataDefColumns;
	}

	public void setJjUpdataDefColumns(List<JjUpdataDefColumn> jjUpdataDefColumns) {
		this.jjUpdataDefColumns = jjUpdataDefColumns;
	}

	public JjUpdataDefColumn getJjUpdataDefColumn() {
		return jjUpdataDefColumn;
	}

	public void setJjUpdataDefColumn(JjUpdataDefColumn jjUpdataDefColumn) {
		this.jjUpdataDefColumn = jjUpdataDefColumn;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

	public String getColumnIdStr() {
		return columnIdStr;
	}

	public void setColumnIdStr(String columnIdStr) {
		this.columnIdStr = columnIdStr;
	}

	public String getTitleStr() {
		return titleStr;
	}

	public void setTitleStr(String titleStr) {
		this.titleStr = titleStr;
	}

	public String getFormulaStr() {
		return formulaStr;
	}

	public void setFormulaStr(String formulaStr) {
		this.formulaStr = formulaStr;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	public String getDisorderStr() {
		return disorderStr;
	}

	public void setDisorderStr(String disorderStr) {
		this.disorderStr = disorderStr;
	}
	public String getDisableStr() {
		return disableStr;
	}

	public void setDisableStr(String disableStr) {
		this.disableStr = disableStr;
	}
	
	public String jjUpdataDefColumnGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = jjUpdataDefColumnManager
					.getJjUpdataDefColumnCriteria(pagedRequests,filters);
			this.jjUpdataDefColumns = (List<JjUpdataDefColumn>) pagedRequests.getList();
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
			jjUpdataDefColumnManager.save(jjUpdataDefColumn);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "jjUpdataDefColumn.added" : "jjUpdataDefColumn.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (columnId != null) {
        	jjUpdataDefColumn = jjUpdataDefColumnManager.get(columnId);
        	this.setEntityIsNew(false);
        } else {
        	jjUpdataDefColumn = new JjUpdataDefColumn();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String jjUpdataDefColumnGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					log.debug("Delete Customer " + removeId);
					JjUpdataDefColumn jjUpdataDefColumn = jjUpdataDefColumnManager.get(new Long(removeId));
					jjUpdataDefColumnManager.remove(new Long(removeId));
					
				}
				gridOperationMessage = this.getText("jjUpdataDefColumn.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkJjUpdataDefColumnGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (jjUpdataDefColumn == null) {
			return "Invalid jjUpdataDefColumn Data";
		}

		return SUCCESS;

	}
	
	public String init(){
		//jjUpdataDefColumnManager.getHibernateTemplate().execute("")
		try {
			boolean isExit = false;
			//jjUpdataDefColumnManager.removeAll();
			jjUpdataDefColumns = jjUpdataDefColumnManager.getAll();
			
			Connection conn = SpringContextHelper.getDataSource().getConnection();//this.dataSource.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM jj_t_Updata where 1=2");
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			for (int i = 1; i <= numberOfColumns; i++) {
				JjUpdataDefColumn jjUpdataDefColumn = new JjUpdataDefColumn();
				String columnName = rsMetaData.getColumnName(i);
				if(columnName.contains("def_")){
					for(JjUpdataDefColumn jjUpdataDefColumnTemp:jjUpdataDefColumns){
						if(jjUpdataDefColumnTemp.getColumnName().equals(columnName)){
							isExit = true;
							break;
						}
					}
					if(!isExit){
						jjUpdataDefColumn.setColumnName(columnName);
						jjUpdataDefColumn.setColumnType(rsMetaData.getColumnTypeName(i));
						jjUpdataDefColumnManager.save(jjUpdataDefColumn);
					}else{
						isExit = false;
					}
				}
			}
		    
			//dataSource.getConnection().get
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	/*private   class  ProductModleRowMapper  implements  RowMapper{  
		  
        public Object mapRow(final ResultSet rs, final int rowNum) throws SQLException {  
            ProductModleVO vo = new ProductModleVO();  
            vo.setProModleId((String)rs.getString("id"));  
            vo.setProModleName((String)rs.getString("name"));  
            return vo;  
        }  
          
    }  */
	public String saveDefColumn(){
		
		try {
			String[] columnArr = null;
			String[] titleArr = null;
			String[] formulaArr = null;
			String[] typeArr = null;
			String[] disorderStrArr = null;
			String[] disableArr = null;
			if(columnIdStr!=null){
				columnArr = columnIdStr.split(",");
				titleArr = titleStr.split(",");
				formulaArr = formulaStr.split(",");
				typeArr = typeStr.split(",");
				disorderStrArr = disorderStr.split(",");
				disableArr = disableStr.split(",");
				for(int i=0;i<columnArr.length-1;i++){
					JjUpdataDefColumn jjUpdataDefColumn = jjUpdataDefColumnManager.get(Long.parseLong(columnArr[i]));
					jjUpdataDefColumn.setTitle(titleArr[i]);
					jjUpdataDefColumn.setFormula(formulaArr[i]);
					jjUpdataDefColumn.setType(typeArr[i]);
					jjUpdataDefColumn.setOrder(Integer.parseInt(disorderStrArr[i]));
					boolean dis = false;
					System.out.println(disableArr[i]);
					if(disableArr[i].equals("checked")){
						dis = true;
					}
					jjUpdataDefColumn.setDisabled(dis);
					jjUpdataDefColumnManager.save(jjUpdataDefColumn);
				}
			}
			return ajaxForward(true,"保存成功！",false);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ajaxForward(false,"保存失败！",false);
		}
		
	}
}

