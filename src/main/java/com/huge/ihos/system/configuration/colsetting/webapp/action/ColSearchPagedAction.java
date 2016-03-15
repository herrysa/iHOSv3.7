package com.huge.ihos.system.configuration.colsetting.webapp.action;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.system.configuration.colsetting.model.ColSearch;
import com.huge.ihos.system.configuration.colsetting.service.ColSearchManager;
import com.huge.ihos.system.configuration.colsetting.service.ColShowManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ColSearchPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764641891460524007L;
	private ColSearchManager colSearchManager;
	private ColShowManager colShowManager;
	private List<ColSearch> colSearches;
	private ColSearch colSearch;
	private String id;
	private String entityName;
	private String templName;
	private String hiddens;
	private String cols = "";
	private String titles = "";
	private String operators = "";
	private String values = "";
	private List<HashMap<String,String>> templtList;
	private List<HashMap<String,String>> templtShowList;
	private String colSelectStr="";
	private String colTypeSelectStr="";
	private List<TableCOl> colList;
	private String grid;
	
	public String getHiddens() {
		return hiddens;
	}

	public void setHiddens(String hiddens) {
		this.hiddens = hiddens;
	}
	public List<HashMap<String, String>> getTempltShowList() {
		return templtShowList;
	}

	public void setTempltShowList(List<HashMap<String, String>> templtShowList) {
		this.templtShowList = templtShowList;
	}
	public List<HashMap<String,String>> getTempltList() {
		return templtList;
	}

	public void setTempltList(List<HashMap<String,String>> templtList) {
		this.templtList = templtList;
	}

	public List<ColSearch> getColSearches() {
		return colSearches;
	}

	public void setColSearches(List<ColSearch> colSearches) {
		this.colSearches = colSearches;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}
	
	public String getTemplName() {
		return templName;
	}

	public void setTemplName(String templName) {
		this.templName = templName;
	}
	public String getColTypeSelectStr() {
		return colTypeSelectStr;
	}
	
	public void setColTypeSelectStr(String colTypeSelectStr) {
		this.colTypeSelectStr = colTypeSelectStr;
	}
	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public List<TableCOl> getColList() {
		return colList;
	}

	public void setColList(List<TableCOl> colList) {
		this.colList = colList;
	}

	public String getColSelectStr() {
		return colSelectStr;
	}

	public void setColSelectStr(String colSelectStr) {
		this.colSelectStr = colSelectStr;
	}

	public ColSearchManager getColSearchManager() {
		return colSearchManager;
	}

	public void setColSearchManager(ColSearchManager colSearchManager) {
		this.colSearchManager = colSearchManager;
	}
	public ColShowManager getColShowManager() {
		return colShowManager;
	}

	public void setColShowManager(ColShowManager colShowManager) {
		this.colShowManager = colShowManager;
	}
	public List<ColSearch> getcolSearches() {
		return colSearches;
	}

	public void setColSearchs(List<ColSearch> colSearches) {
		this.colSearches = colSearches;
	}

	public ColSearch getColSearch() {
		return colSearch;
	}

	public void setColSearch(ColSearch colSearch) {
		this.colSearch = colSearch;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }
	
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	
	public String getFieldTypeCode(String col,Map<String, String> fieldMap){
		String ft = null;
		String[] colPath = col.split("\\.");
		if(colPath.length>1){
			Map<String, String> fieldPathMap = fieldMap;
			for(int pi=0;pi<colPath.length;pi++){
				String colClass = fieldPathMap.get(colPath[pi]);
				if(colClass!=null){
					if(pi==colPath.length-1){
						ft = colClass;
					}else{
						fieldPathMap = findEntityFields(colClass);
					}
				}
				
			}
		}else{
			ft = fieldMap.get(col);
		}
		if(ft==null){
			ft="S";
		}else if("java.lang.Integer".equals(ft)||"int".equals(ft)){
			ft="I";
		}else if("java.lang.Long".equals(ft)||"long".equals(ft)){
			ft="L";
		}else if("java.lang.Double".equals(ft)||"double".equals(ft)){
			ft="N";
		}else if("java.lang.Date".equals(ft)){
			ft="D";
		}else if("java.sql.Timestamp".equals(ft)){
			ft="T";
		}else if("java.lang.Boolean".equals(ft)||"boolean".equals(ft)){
			ft="B";
		}else if("java.math.BigDecimal".equals(ft)){
			ft="G";
		}else{
			ft="S";
		}
		return ft;
	}
	
	public String colSearchListPre(){
		try {
		User user = this.getSessionUser();
		templtList = colSearchManager.getAllTempl(entityName,""+user.getId());	
		templtShowList = colShowManager.getAllTempl(entityName,""+user.getId(),null);
		/*if(templtShowList!=null&templtShowList.size()!=0){
			templateName = templtShowList.get(0).get("templetName");
			List<ColShow> colShows = colShowManager.getByTemplName(templName);
		}*/
		Map<String, String> fieldMap = findEntityFields(entityName);
		colList = new ArrayList<ColSearchPagedAction.TableCOl>();
		String[] colArr = cols.split(",");
		String[] titleArr = titles.split(",");
		String[] hiddenArr = hiddens.split(",");
		for(int colIndex=0;colIndex<colArr.length;colIndex++){
			colSelectStr += colArr[colIndex]+":"+titleArr[colIndex]+";";
			TableCOl tableCOl = new TableCOl();
			tableCOl.setCol(colArr[colIndex]);
			tableCOl.setTitle(titleArr[colIndex]);
			tableCOl.setHidden(hiddenArr[colIndex]);
			colList.add(tableCOl);
			String ft = getFieldTypeCode(colArr[colIndex],fieldMap);
			
			colTypeSelectStr += colArr[colIndex]+":"+ft+";";
		}
		if(!"".equals(colSelectStr)){
			colSelectStr = colSelectStr.substring(0, colSelectStr.length()-1);
		}
		if(!"".equals(colTypeSelectStr)){
			colTypeSelectStr = colTypeSelectStr.substring(0, colTypeSelectStr.length()-1);
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String colSearchGridList() {
		log.debug("enter list method!");
		try {
			User user = this.getSessionUser();
			//String entity = entityName.split("\\.")[entityName.split("\\.").length-1];
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			PropertyFilter userFilter = new PropertyFilter("EQS_userId", ""+user.getId());
			filters.add(userFilter);
			if(entityName!=null){
				PropertyFilter entityFilter = new PropertyFilter("EQS_entityName", entityName);
				filters.add(entityFilter);
			}
			if(templateName!=null){
				PropertyFilter entityFilter = new PropertyFilter("EQS_templetName", templateName);
				filters.add(entityFilter);
			}else{
				templtList = colSearchManager.getAllTempl(entityName,""+user.getId());
				if(templtList!=null&templtList.size()!=0){
					templateName = templtList.get(0).get("templetName");
					PropertyFilter entityFilter = new PropertyFilter("EQS_templetName", templateName);
					filters.add(entityFilter);
				}
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = colSearchManager
					.getColSearchCriteria(pagedRequests,filters);
			this.colSearches = (List<ColSearch>) pagedRequests.getList();
			List<ColSearch> colSearchsCopy = new ArrayList<ColSearch>();
			colSearchsCopy.addAll(colSearches);
			String[] colArr = cols.split(",");
			String[] titleArr = titles.split(",");
			Map<String, String> fieldMap = findEntityFields(entityName);
			for(int colIndex=0;colIndex<colArr.length;colIndex++){
				boolean flag = false;
				for(ColSearch colSearch : colSearchsCopy){
					if(colArr[colIndex].equals(colSearch.getCol())){
						colSearch.setLabel(titleArr[colIndex]);
						colSearch.setFiledType(getFieldTypeCode(colArr[colIndex],fieldMap));
						flag = true;
						break;
					}
				}
				if(!flag){
					ColSearch colSearch = new ColSearch();
					colSearch.setId(""+Math.random()*1000);
					colSearch.setCol(colArr[colIndex]);
					colSearch.setLabel(titleArr[colIndex]);
					colSearch.setFiledType(getFieldTypeCode(colArr[colIndex],fieldMap));
					colSearches.add(colSearch);
				}
				colSelectStr += colArr[colIndex]+":"+titleArr[colIndex]+";";
			}
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
			colSearchManager.save(colSearch);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "colSearch.added" : "colSearch.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	colSearch = colSearchManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	colSearch = new ColSearch();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String colSearchGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					ColSearch colSearch = colSearchManager.get(new String(removeId));
					colSearchManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("colSearch.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkColSearchGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (colSearch == null) {
			return "Invalid colSearch Data";
		}

		return SUCCESS;

	}
	
	class TableCOl{
		private String col;
		private String title;
		private String hidden;
		
		public String getHidden() {
			return hidden;
		}
		public void setHidden(String hidden) {
			this.hidden = hidden;
		}
		public String getCol() {
			return col;
		}
		public void setCol(String col) {
			this.col = col;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
	}
	
	public String saveColSearchTempl(){
		try {
			User user = this.getSessionUser();
			colSearchManager.delByTemplName(templName,entityName,""+user.getId());
			String[] colArr = cols.split(",");
			String[] operatorArr = operators.split(",");
			String[] valueArr = values.split(",");
			Calendar calendar = Calendar.getInstance();
			for(int colIndex=0;colIndex<colArr.length;colIndex++){
				
				ColSearch colSearch = new ColSearch();
				colSearch.setCol(colArr[colIndex]);
				colSearch.setOperator(operatorArr[colIndex]);
				colSearch.setValue(valueArr[colIndex]);
				colSearch.setEntityName(entityName);
				colSearch.setTempletName(templName);
				colSearch.setEditTime(calendar.getTimeInMillis());
				colSearch.setUserId(""+user.getId());
				//colSearch.setLabel(titleArr[colIndex]);
				colSearchManager.save(colSearch);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public Map<String, String> findEntityFields(String entityName){
		Class<?> entity = null;
        try {
        	entity = Class.forName(entityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("===============本类属性========================");
        // 取得本类的全部属性
        Map<String, String> fieldMap = new HashMap<String, String>();
        Field[] field = entity.getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            // 权限修饰符
            int mo = field[i].getModifiers();
            //String priv = Modifier.toString(mo);
            // 属性类型
            Class<?> type = field[i].getType();
            /*System.out.println(priv + " " + type.getName() + " "
                    + field[i].getName() + ";");*/
            fieldMap.put(field[i].getName(), type.getName());
        }
        /*System.out.println("===============实现的接口或者父类的属性========================");
        // 取得实现的接口或者父类的属性
        Field[] filed1 = entity.getFields();
        for (int j = 0; j < filed1.length; j++) {
            // 权限修饰符
            int mo = filed1[j].getModifiers();
            String priv = Modifier.toString(mo);
            // 属性类型
            Class<?> type = filed1[j].getType();
            System.out.println(priv + " " + type.getName() + " "
                    + filed1[j].getName() + ";");
        }*/
        return fieldMap;
	}
	public static void main(String[] args) {
		try {
			
		Class<?> entity = Class.forName("com.huge.ihos.accounting.account.model.Account");
		 Field[] fields = entity.getDeclaredFields();
		 for(int i=0;i<fields.length;i++){
			 System.out.println(fields[i].getName()+"    "+fields[i].getType().getName());
			 //System.out.println(field.getGenericType());
		 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

