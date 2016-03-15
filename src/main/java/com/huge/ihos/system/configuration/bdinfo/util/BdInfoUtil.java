package com.huge.ihos.system.configuration.bdinfo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.huge.exceptions.BusinessException;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.ihos.system.configuration.bdinfo.service.BdInfoManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.PropertyFilter.MatchType;
import com.huge.webapp.util.SpringContextHelper;

/**
 * @author Administrator
 *
 */
public class BdInfoUtil{
	//util全局动作开关
	//private boolean orgJoin = false;
	//private boolean deptJoin = false;
	private boolean onlyShowCustom = false;
	private boolean onlyShowMain = false;
	private boolean filterXT = false;
	private boolean filterDisabled = false;
	private boolean initShow = true;
	private boolean resultToLowerCase = false;
	private boolean unionInfilter = false;
	//util全局动作开关结束
	
	public BdInfo bdInfo;
	private String tableAlias;
	
	private boolean toMainAlias;
	//private boolean orgFilter;
	//private boolean deptFilter;
	//private boolean copyFilter;
	private boolean periodYearFilter;
	private boolean makeSQLed;
	
	private FieldInfo pkCol;
	private FieldInfo nameCol;
	private FieldInfo codeCol;
	private FieldInfo disabledCol;
	private FieldInfo parentCol;
	private FieldInfo orgCol;
	private FieldInfo deptCol;
	private FieldInfo copyCol;
	private FieldInfo periodYearCol;
	//private List<String> fieldList = new ArrayList<String>();
	//private Map<String, FieldInfo> fieldMap;
	/**
	 * 存储 列名-别名
	 */
	//private Map<String, String> colNameMap = new HashMap<String, String>();
	/**
	 * 存储 列名-别名
	 */
	private Map<String, String> aliasMap;
	/**
	 * 存储 主要列名[ID,CODE,NAME]-别名
	 */
	private Map<String, String> mainColMap;
	/**
	 * 存储 列名-自定义别名
	 */
	private Map<String, String> customColMap;
	/**
	 * 存储 需要查询的列名-别名
	 */
	//private Map<String, String> queryColMap = new HashMap<String, String>();
	private List<TableCol> tableColList;
	private List<TableJoin> joinBdInfos;
	private List<TableOrder> sortList;
	private List<QueryItem> queryItemList;
	private Map<String, String> groupMap;
	private List<TableUnion> unionList;
	
	private List<Object> prameterList;
	
	private String joinStr;
	private String colStr;
	private String joinColStr;
	private String unionColStr;
	private String whereStr;
	private String sortStr;
	private String unionStr;
	private String unionwhereStr;
	
	private List<TableCol> showTableColList;
	private boolean hasUnion = false;
	
	private BdInfoManager bdInfoManager = (BdInfoManager)SpringContextHelper.getBean("bdInfoManager");

	public BdInfoUtil(){
		
	}
	
	public BdInfoUtil(BdInfo bdInfo){
		addBdInfo(bdInfo);
	}
	
	public void init(){
		toMainAlias = false;
		//orgFilter = false;
		//deptFilter = false;
		//copyFilter = false;
		periodYearFilter = false;
		makeSQLed = false;
		
		pkCol = null;
		disabledCol = null;
		orgCol = null;
		copyCol = null;
		periodYearCol = null;
		
		aliasMap = new HashMap<String, String>();
		mainColMap = new HashMap<String, String>();
		customColMap = new HashMap<String, String>();
		tableColList = new ArrayList<TableCol>();
		joinBdInfos = new ArrayList<TableJoin>();
		sortList = new ArrayList<TableOrder>();
		queryItemList = new ArrayList<BdInfoUtil.QueryItem>();
		groupMap = new HashMap<String, String>();
		unionList = new ArrayList<TableUnion>();
		
		prameterList = new ArrayList<Object>();
		
		joinStr = "";
		colStr = "";
		joinColStr = "";
		whereStr = "";
		sortStr = "";
		unionwhereStr = "";
	}
	
	public void addBdInfo(BdInfo bdInfo){
		init();
		this.bdInfo = bdInfo;
		tableAlias = this.bdInfo.getTableAlias();
		if(tableAlias==null||"".equals(tableAlias)){
			tableAlias = "_"+this.bdInfo.getTableName();
		}
		this.bdInfo.setTableAlias(tableAlias);
		Set<FieldInfo> fieldInfos = bdInfo.getFieldInfos();
		if(fieldInfos!=null){
			//fieldMap = new HashMap<String, FieldInfo>();
			Iterator itr = fieldInfos.iterator();
			while ( itr.hasNext() ) {
				boolean aliasFlag = false;
				FieldInfo field = (FieldInfo) itr.next();
				String colNameUpCase = field.getFieldCode().toUpperCase();
				//fieldMap.put(field.getFieldCode(), field);
				//colNameMap.put(field.getFieldCode().toUpperCase(),field.getFieldCode().toUpperCase());
				aliasMap.put(colNameUpCase,colNameUpCase);
				TableCol tableColTemp = null;
				//fieldList.add(field.getFieldCode());
				boolean added = false;
				if(field.getIsPkCol()){
					String alias = "ID";
					pkCol = field;
					if(filterXT){
						addFilter(new PropertyFilter("NES_"+pkCol.getFieldCode(),"XT"));
					}
					aliasMap.put(alias,colNameUpCase);
					mainColMap.put(colNameUpCase,alias);
					tableColTemp = new TableCol();
					tableColTemp.setColCode(colNameUpCase);
					tableColTemp.setColAlias(colNameUpCase);
					tableColTemp.setMainCol(true);
					tableColList.add(tableColTemp);
					aliasFlag = true;
					added = true;
				}
				if(field.getIsDisabledCol()){
					disabledCol = field;
					if(filterDisabled){
						addFilter(new PropertyFilter("EQB_"+disabledCol.getFieldCode(),"false"));
					}
				}
				if(field.getIsCodeCol()){
					String alias = "CODE";
					codeCol = field;
					aliasMap.put(alias,colNameUpCase);
					tableColTemp = new TableCol();
					tableColTemp.setColCode(colNameUpCase);
					tableColTemp.setMainCol(true);
					if(added){
						tableColTemp.setDuplicationCol(true);
						tableColTemp.setColAlias(alias);
					}else{
						mainColMap.put(colNameUpCase,alias);
						tableColTemp.setColAlias(colNameUpCase);
						added = true;
					}
					tableColList.add(tableColTemp);
					aliasFlag = true;
				}
				if(field.getIsNameCol()){
					String alias = "NAME";
					nameCol = field;
					aliasMap.put(alias,colNameUpCase);
					tableColTemp = new TableCol();
					tableColTemp.setColCode(colNameUpCase);
					tableColTemp.setMainCol(true);
					if(added){
						tableColTemp.setDuplicationCol(true);
						tableColTemp.setColAlias(alias);
					}else{
						mainColMap.put(colNameUpCase,alias);
						tableColTemp.setColAlias(colNameUpCase);
						added = true;
					}
					tableColList.add(tableColTemp);
					aliasFlag = true;
				}
				if(field.getIsParentPk()){
					parentCol = field;
					String alias = "pId";
					aliasMap.put(alias,colNameUpCase);
					tableColTemp = new TableCol();
					tableColTemp.setColCode(colNameUpCase);
					tableColTemp.setMainCol(true);
					if(added){
						tableColTemp.setDuplicationCol(true);
						tableColTemp.setColAlias(alias);
					}else{
						mainColMap.put(colNameUpCase,alias);
						tableColTemp.setColAlias(colNameUpCase);
						added = true;
					}
					tableColList.add(tableColTemp);
					aliasFlag = true;
				}
				if(field.getIsOrgCol()){
					//orgFilter = true;
					//initShow = false;
					orgCol = field;
					String alias = "ORGCODE";
					aliasMap.put(alias,colNameUpCase);
					tableColTemp = new TableCol();
					tableColTemp.setColCode(colNameUpCase);
					tableColTemp.setMainCol(true);
					if(added){
						tableColTemp.setDuplicationCol(true);
						tableColTemp.setColAlias(alias);
					}else{
						mainColMap.put(colNameUpCase,alias);
						tableColTemp.setColAlias(colNameUpCase);
						added = true;
					}
					tableColList.add(tableColTemp);
					aliasFlag = true;
					/*if(this.isOrgJoin()){
						addJoin(field);
					}*/
				}
				if(field.getIsDeptCol()){
					//deptFilter = true;
					deptCol = field;
					String alias = "DEPTCODE";
					aliasMap.put(alias,colNameUpCase);
					tableColTemp = new TableCol();
					tableColTemp.setColCode(colNameUpCase);
					tableColTemp.setMainCol(true);
					if(added){
						tableColTemp.setDuplicationCol(true);
						tableColTemp.setColAlias(alias);
					}else{
						mainColMap.put(colNameUpCase,alias);
						tableColTemp.setColAlias(colNameUpCase);
						added = true;
					}
					tableColList.add(tableColTemp);
					aliasFlag = true;
					/*if(this.isDeptJoin()){
						addJoin(field);
					}*/
					//initShow = false;
				}
				if(field.getIsCopyCol()){
					//copyFilter = true;
					copyCol = field;
					String alias = "COPYCODE";
					aliasMap.put(alias,colNameUpCase);
					tableColTemp = new TableCol();
					tableColTemp.setColCode(colNameUpCase);
					tableColTemp.setMainCol(true);
					if(added){
						tableColTemp.setDuplicationCol(true);
						tableColTemp.setColAlias(alias);
					}else{
						mainColMap.put(colNameUpCase,alias);
						tableColTemp.setColAlias(colNameUpCase);
						added = true;
					}
					tableColList.add(tableColTemp);
					aliasFlag = true;
				}
				if(field.getIsPeriodYearCol()){
					periodYearFilter = true;
					periodYearCol = field;
					String alias = "PERIODYEAR";
					aliasMap.put(alias,colNameUpCase);
					tableColTemp = new TableCol();
					tableColTemp.setColCode(colNameUpCase);
					tableColTemp.setMainCol(true);
					if(added){
						tableColTemp.setDuplicationCol(true);
						tableColTemp.setColAlias(alias);
					}else{
						mainColMap.put(colNameUpCase,alias);
						tableColTemp.setColAlias(colNameUpCase);
						added = true;
					}
					tableColList.add(tableColTemp);
					aliasFlag = true;
				}
				if(!aliasFlag){
					tableColTemp = new TableCol();
					tableColTemp.setColCode(colNameUpCase);
					tableColTemp.setColAlias(colNameUpCase);
					tableColList.add(tableColTemp);
				}
			}
		}
	}
	
	public String makeSQL(){
		String sql = "";
		joinStr = makeJoin();
		colStr = makeColNames();
		joinColStr = makeJoinColNames();
		String col = "";
		col = colStr+joinColStr;
		if(isHasUnion()){
			col += ",9 unionOrder";
		}
		whereStr = makeWhere();
		makeGroup();
		sql = "SELECT "+col+" FROM "+joinStr+" WHERE 1=1 "+whereStr;
		unionStr = makeUnion();
		if(!"".equals(unionStr)){
			sortStr = makeUnionSort();
			//unionwhereStr = makeUnionWhere();
			sql = "SELECT * FROM("+sql+unionStr+") AS u"+" WHERE 1=1 "+unionwhereStr+sortStr;
		}else{
			sortStr = makeSort();
			sql += sortStr;
		}
		makeSQLed = true;
		return sql;
	}
	
	public String makeCountSQL(){
		String sql = "";
		if(!makeSQLed){
			joinStr = makeJoin();
			whereStr = makeWhere();
			makeGroup();
			//sortStr = makeSort();
			unionStr = makeUnion();
			//unionwhereStr = makeUnionWhere();
		}
		if(!"".equals(unionStr)){
			String col = "";
			col = colStr+joinColStr;
			if(isHasUnion()){
				col += ",9 unionOrder";
			}
			sql = "SELECT "+col+" FROM "+joinStr+" WHERE 1=1 "+whereStr;
			sql = "SELECT count(*) as cnt FROM("+sql+unionStr+") AS U"+" WHERE 1=1 "+unionwhereStr;
		}else{
			sql = "SELECT count(*) as cnt FROM "+joinStr+" WHERE 1=1 "+whereStr;
			//sql += sortStr;
		}
		return sql;
	}
	
	public String makeSubQuerySQL(){
		String sql = "";
		joinStr = makeJoin();
		//colStr = makeColNames();
		//joinColStr = makeJoinColNames();
		whereStr = makeWhere();
		makeGroup();
		sql = "SELECT "+pkCol.getFieldCode()+" FROM "+joinStr+" WHERE 1=1 "+whereStr;
		//unionStr = makeUnion();
		/*if(!"".equals(unionStr)){
			sortStr = makeUnionSort();
			sql = "SELECT * FROM("+sql+unionStr+") AS u"+" WHERE 1=1 "+unionwhereStr+sortStr;
		}else{
			sortStr = makeSort();
			sql += sortStr;
		}*/
		//makeSQLed = true;
		return sql;
	}
	
	public String makeJoin(){
		String tableName = this.bdInfo.getTableName();
		tableName += " "+tableAlias;
		for(TableJoin tableJoin : joinBdInfos){
			
			String colName = tableJoin.getColCode();
			String joinType = tableJoin.getJoinType();
			BdInfoUtil bdInfoUtil = tableJoin.getBdInfoUtil();
			BdInfo joinBdInfo = bdInfoUtil.bdInfo;
			tableName += " "+joinType+" "+joinBdInfo.getTableName()+" "+joinBdInfo.getTableAlias()+" ON "+tableAlias+"."+colName+"="+joinBdInfo.getTableAlias()+"."+bdInfoUtil.getPkCol().getFieldCode();
		}
		return tableName;
	}
	
	public String makeJoinColNames(){
		String colStr = "";
		for(TableJoin tableJoin : joinBdInfos){
			String colName = tableJoin.getColCode();
			BdInfoUtil bdInfoUtil = tableJoin.getBdInfoUtil();
			List<TableCol> joinTableColList = bdInfoUtil.tableColList;
			boolean joinOnlyShowMain = bdInfoUtil.onlyShowMain;
			boolean joinOnlyShowCustom = bdInfoUtil.onlyShowCustom;
			String joinTableAlias = bdInfoUtil.tableAlias;
			for(TableCol tableCol : joinTableColList){
				String aliasColName = "";
				boolean added = false;
				if(joinOnlyShowMain){
					if(tableCol.isMainCol()){
						if(tableCol.isDuplicationCol()){
							aliasColName = tableCol.getColAlias();
						}else{
							aliasColName = bdInfoUtil.mainColMap.get(tableCol.getColCode());
						}
						added = true;
					}else if(tableCol.isNecessaryCol){
						aliasColName = tableCol.getColAlias();
						added = true;
					}
				}else if(joinOnlyShowCustom){
					if(tableCol.isCustomCol()){
						if(tableCol.isDuplicationCol()){
							aliasColName = tableCol.getColAlias();
						}else{
							aliasColName = bdInfoUtil.customColMap.get(tableCol.getColCode());
						}
						added = true;
					}else if(tableCol.isNecessaryCol){
						aliasColName = tableCol.getColAlias();
						added = true;
					}
				}else{
					if(!tableCol.isDuplicationCol()){
						aliasColName = tableCol.getColAlias();
						added = true;
					}
				}
				if(added){
					tableCol.setColAlias(colName+"__"+aliasColName);
					showTableColList.add(tableCol);
					String col = joinTableAlias+"."+tableCol.getColCode()+" ";
					String defaultValue = tableCol.getDefaultValue();
					if(defaultValue==null){
						defaultValue = "";
					}else{
						defaultValue += " ";
					}
					if(tableCol.isDummyCol()){
						col = "";
					}
					colStr += defaultValue+col+colName+"__"+aliasColName+",";
				}
				
			}
		}
		colStr = OtherUtil.subStrEnd(colStr, ",");
		if(!"".equals(colStr)){
			colStr = ","+colStr;
		}
		return colStr;
	}
	
	public String makeWhere(){
		String whereStr = "";
		for(QueryItem queryItem:queryItemList){
			String colName = queryItem.getItemCode();
			String tableAlias = this.tableAlias+".";
			if(OtherUtil.isNumeric(colName)){
				tableAlias = "";
			}
			String operator = queryItem.getItemOperator();
			Object v = queryItem.getItemValue();
			
			if(queryItem.isPrepared()){
				whereStr += " AND "+tableAlias+colName+operator+"?";
				prameterList.add(v);
			}else{
				whereStr += " AND "+tableAlias+colName+operator+v.toString();
			}
		}
		return whereStr;
	}
	
	public String makeColNames(){
		String colStr = "";
		showTableColList = new ArrayList<BdInfoUtil.TableCol>();
		for(TableCol tableCol : tableColList){
			String aliasColName = "";
			boolean added = false;
			if(onlyShowMain){
				if(tableCol.isMainCol()){
					if(tableCol.isDuplicationCol()){
						aliasColName = tableCol.getColAlias();
					}else{
						aliasColName = mainColMap.get(tableCol.getColCode());
					}
					added = true;
				}else if(tableCol.isNecessaryCol){
					aliasColName = tableCol.getColAlias();
					added = true;
				}
			}else if(onlyShowCustom){
				if(tableCol.isCustomCol()){
					if(tableCol.isDuplicationCol()){
						aliasColName = tableCol.getColAlias();
					}else{
						aliasColName = mainColMap.get(tableCol.getColCode());
					}
					added = true;
				}else if(tableCol.isNecessaryCol){
					aliasColName = tableCol.getColAlias();
					added = true;
				}
			}else{
				aliasColName = tableCol.getColAlias();
				added = true;
			}
			if(added){
				tableCol.setColAlias(aliasColName);
				showTableColList.add(tableCol);
				String col = tableAlias+"."+tableCol.getColCode()+" ";
				String defaultValue = tableCol.getDefaultValue();
				if(defaultValue==null){
					defaultValue = "";
				}else{
					defaultValue += " ";
				}
				if(tableCol.isDummyCol()){
					col = "";
				}
				colStr += defaultValue+col+aliasColName+",";
			}
		}
		colStr = OtherUtil.subStrEnd(colStr, ",");
		return colStr;
	}
	
	public void makeGroup(){
		
	}
	
	public String makeSort(){
		String sortStr = "";
		if(sortList.size()>0){
			for(TableOrder tableOrder : sortList){
				String colCode = tableOrder.getColCode();
				String direction = tableOrder.getDirection();
				String tableAlias = tableOrder.getTableAlias();
				if(tableOrder.isAlias()&&!isHasUnion()){
					colCode = aliasMap.get(colCode);
					if(colCode==null||"".equals(colCode)){
						continue;
					}
				}
				if(tableAlias==null||"".equals(tableAlias)){
					tableAlias = "";
				}else{
					tableAlias += ".";
				}
				sortStr += tableAlias+colCode+" "+direction+",";
			}
			sortStr = OtherUtil.subStrEnd(sortStr, ",");
		}else{
			sortStr += pkCol.getFieldCode()+" ASC";
		}
		sortStr = " ORDER BY " + sortStr;
		return sortStr;
	}
	
	public String makeUnion(){
		String unionStr = "";
		if(unionList.size()>0){
			for(TableUnion tableUnion : unionList){
				String uninColStr = "";
				if(tableUnion.getUnionBdInfoUtil()==null){
					if(tableUnion.defaultValueMap!=null){
						Map<String, String> defaultValueMap = tableUnion.defaultValueMap;
						for(TableCol tableCol : showTableColList){
							String colName = tableCol.getColCode();
							String aliasColName = tableCol.getColAlias();
							String defaultValue = defaultValueMap.get(colName);
							if(defaultValue==null||!"".equals(defaultValue)){
								defaultValue = defaultValueMap.get(aliasColName);
							}
							if(defaultValue==null){
								defaultValue = "' ' ";
							}else{
								defaultValue += " ";
							}
							uninColStr += defaultValue+aliasColName+",";
						}
					}
				}
				if(!"".equals(uninColStr)){
					uninColStr = OtherUtil.subStrEnd(uninColStr, ",");
					uninColStr += ",1 unionOrder";
					unionStr += uninColStr;
				}
			}
		}
		if(!"".equals(unionStr)){
			unionStr = " UNION SELECT "+unionStr;
		}
		return unionStr;
	}
	
	public String makeUnionWhere(){
		String whereStr = "";
		for(QueryItem queryItem:queryItemList){
			String colName = queryItem.getItemCode();
			for(TableCol tableCol :showTableColList){
				if(tableCol.getColCode().equals(colName)){
					colName = tableCol.getColAlias();
					break;
				}
			}
			String operator = queryItem.getItemOperator();
			Object v = queryItem.getItemValue();
			if(queryItem.isPrepared()){
				whereStr += " AND "+colName+operator+"?";
				prameterList.add(v);
			}else{
				whereStr += " AND "+colName+operator+v.toString();
			}
		}
		return whereStr;
	}
	
	public String makeUnionSort(){
		String sortStr = "unionOrder asc ,";
		if(sortList.size()>0){
			for(TableOrder tableOrder : sortList){
				String colCode = tableOrder.getColCode();
				String direction = tableOrder.getDirection();
				String tableAlias = "u.";
				if(tableOrder.isAlias()){
					colCode = aliasMap.get(colCode);
					if(colCode==null||"".equals(colCode)){
						continue;
					}
					for(TableCol tableCol :showTableColList){
						if(tableCol.getColCode().equals(colCode)){
							colCode = tableCol.getColAlias();
							break;
						}
					}
				}
				sortStr += tableAlias+colCode+" "+direction+",";
			}
			sortStr = OtherUtil.subStrEnd(sortStr, ",");
		}
		else{
			sortStr += pkCol.getFieldCode()+" ASC";
		}
		sortStr = " ORDER BY " + sortStr;
		return sortStr;
	}
	
	public void addFilter(PropertyFilter pf){
        String colName = pf.getPropertyName();
        if(colName.contains(".")){
        	String[] joinCol = colName.split(".");
        	
        }
        //colName = tableAlias+"."+colName;
        QueryItem queryItem = new QueryItem();
    	queryItem.setItemCode(colName.toUpperCase());
        if ( pf.getMatchType().equals( MatchType.LIKE ) ) {
        	String v = (String) pf.getMatchValue();
            boolean bp = v.startsWith( "*" );
            boolean ep = v.endsWith( "*" );
            v = v.replaceAll( "\\*", "" );
            if ( bp && ep )
                v = "%"+v+"%";
            else if ( bp && !ep )
            	v = "%"+v;
            else if ( !bp && ep )
            	v = v+"%";
           	queryItem.setItemOperator(" LIKE ");
           	queryItem.setItemValue("'"+v+"'");
           	queryItem.setPrepared(false);
        }
        else if ( pf.getMatchType().equals( MatchType.EQ ) ) {
           	queryItem.setItemOperator("=");
           	queryItem.setItemValue(pf.getMatchValue());
           	queryItem.setPrepared(true);
        }
        else if ( pf.getMatchType().equals( MatchType.GE ) ) {
           	queryItem.setItemOperator(">=");
           	queryItem.setItemValue(pf.getMatchValue());
           	queryItem.setPrepared(true);
        }
        else if ( pf.getMatchType().equals( MatchType.GT ) ) {
        	queryItem.setItemOperator(">");
           	queryItem.setItemValue(pf.getMatchValue());
           	queryItem.setPrepared(true);
        }
        else if ( pf.getMatchType().equals( MatchType.IN ) ) {
        	Object v = pf.getMatchValue();
        	String vStr = v.toString();
        	String[] vArr = null;
        	String inStr = "";
        	if(v instanceof String[]){
        		vArr = (String[])v;
        	}else{
        		vArr = vStr.split(",");
        	}
        	inStr = "(";
        	for(int vIndex=0;vIndex<vArr.length;vIndex++){
        		String iv =  vArr[vIndex];
        		if(vIndex==0){
        			if(iv.startsWith("SELECT")){
        				inStr += iv;
        				break;
        			}
        			inStr += "'"+ iv+"'" ;
        		}else{
        			inStr += ","+ "'" + iv + "'";
        		}
        	}
        	inStr += ")";
        	queryItem.setItemOperator(" IN ");
           	queryItem.setItemValue(inStr);
           	queryItem.setPrepared(false);
        }
        else if ( pf.getMatchType().equals( MatchType.NI ) ) {
        	Object v = pf.getMatchValue();
        	String vStr = v.toString();
        	String[] vArr = null;
        	String inStr = "";
        	if(v instanceof String[]){
        		vArr = (String[])v;
        	}else{
        		vArr = vStr.split(",");
        	}
        	inStr = "(";
        	for(int vIndex=0;vIndex<vArr.length;vIndex++){
        		String iv =  vArr[vIndex];
        		if(vIndex==0){
        			if(iv.startsWith("SELECT")){
        				inStr += iv;
        				break;
        			}
        			inStr += "'"+ iv+"'" ;
        		}else{
        			inStr += ","+ "'" + iv + "'";
        		}
        	}
        	inStr += ")";
        	queryItem.setItemOperator(" NOT IN ");
           	queryItem.setItemValue(inStr);
           	queryItem.setPrepared(false);
        }
        else if ( pf.getMatchType().equals( MatchType.ISNOTNULL ) ) {
        	queryItem.setItemOperator("NOT IN");
           	//queryItem.setItemValue(inStr);
           	queryItem.setPrepared(false);
        }
        else if ( pf.getMatchType().equals( MatchType.ISNULL ) ) {
        	queryItem.setItemOperator("NOT IN");
           	//queryItem.setItemValue(inStr);
           	queryItem.setPrepared(false);
        }
        else if ( pf.getMatchType().equals( MatchType.LE ) ) {
        	queryItem.setItemOperator("<=");
           	queryItem.setItemValue(pf.getMatchValue());
           	queryItem.setPrepared(true);
        }
        else if ( pf.getMatchType().equals( MatchType.LT ) ) {
        	queryItem.setItemOperator("<");
           	queryItem.setItemValue(pf.getMatchValue());
           	queryItem.setPrepared(true);
        }
        else if ( pf.getMatchType().equals( MatchType.NE ) ) {
        	queryItem.setItemOperator("<>");
           	queryItem.setItemValue(pf.getMatchValue());
           	queryItem.setPrepared(true);
        }
        else if ( pf.getMatchType().equals( MatchType.OA ) ) {
        	/*if(i==0){
        		Field orderEntrysField = CriteriaImpl.class.getDeclaredField("orderEntries");
        		orderEntrysField.setAccessible(true);  
        		//orderEntrys = (List) orderEntrysField.get(criteria);
        		orderEntrysField.set(criteria,new ArrayList());
        	}
        	//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName().toString(), CriteriaSpecification.LEFT_JOIN );
        	criteria.addOrder(Order.asc(fieldName.toString()));*/
        }
        else if ( pf.getMatchType().equals( MatchType.OD ) ) {
        	/*if(i==0){
        		Field orderEntrysField = CriteriaImpl.class.getDeclaredField("orderEntries");
        		orderEntrysField.setAccessible(true);  
        		//orderEntrys = (List) orderEntrysField.get(criteria);
        		orderEntrysField.set(criteria,new ArrayList());
        		i++;
        	}
        	//criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, pf.getPropertyName().toString(), CriteriaSpecification.LEFT_JOIN );
        	criteria.addOrder(Order.desc(fieldName.toString()));*/
        }else if( pf.getMatchType().equals(MatchType.SQ)){
        	//criteria.add( Restrictions.sqlRestriction(pf.getMatchValue().toString()) );
        }
        else {
            throw new BusinessException( "查询条件错误，未知的查询操作符。" );
        }
        queryItemList.add(queryItem);
	}
	
	/**
	 * 
	 * 
	*/
	public void addFilters(List<PropertyFilter> filters){
		Iterator itr = filters.iterator();
		while ( itr.hasNext() ) {
			PropertyFilter pf = (PropertyFilter) itr.next();
			addFilter(pf);
		}
	}

	
	
	public void aliasColName(Map<String, String> aliasMap){
		Set<String> aliasSet = aliasMap.keySet();
		Iterator<String> itr = aliasSet.iterator();
		while(itr.hasNext()){
			String aliasName = itr.next();
			this.aliasMap.put(aliasName.toUpperCase(), aliasMap.get(aliasName).toUpperCase());
		}
	}
	
	public void addSort( String sortField ,String direction ,boolean isAlias) {
		if( sortField != null ){
			TableOrder tableOrder = new TableOrder();
			tableOrder.setColCode(sortField.toUpperCase());
			tableOrder.setAlias(isAlias);
			if(direction != null){
				tableOrder.setDirection(direction);
			}else{
				tableOrder.setDirection("ASC" );
			}
			tableOrder.setTableAlias(tableAlias);
			sortList.add(tableOrder);
		}
	}

	public void addSorts( Map<String, String> sortMap ) {
		Set<String> sortSet = sortMap.keySet();
		Iterator<String> itr = sortSet.iterator();
		while(itr.hasNext()){
			String colName = itr.next();
			sortMap.put(colName.toUpperCase(), sortMap.get(colName).toUpperCase());
		}
	}
	
	public void addGroup(String groupCol , String groupFunc){
		if(groupCol!=null){
			groupMap.put(groupCol,groupFunc);
		}
	}
	
	public void addGroups(Map<String, String> groupMap ){
		Set<String> groupSet = groupMap.keySet();
		Iterator<String> itr = groupSet.iterator();
		while(itr.hasNext()){
			String colName = itr.next();
			this.groupMap.put(colName, groupMap.get(colName));
		}
	}
	
	public void addJoin(String colName){
		
		//joinBdInfos.add(bdInfoUtil);
	}
	public void addJoin(FieldInfo fieldInfo){
		String fkTable = fieldInfo.getFkTable();
		BdInfo fkBdInfo = bdInfoManager.findByTableName(fkTable);
		if(fkBdInfo!=null){
			BdInfoUtil bdInfoUtil = new BdInfoUtil(fkBdInfo);
			TableJoin tableJoin = new TableJoin();
			tableJoin.setColCode(fieldInfo.getFieldCode());
			tableJoin.setBdInfoUtil(bdInfoUtil);
			tableJoin.setJoinType("LEFT JOIN");
			joinBdInfos.add(tableJoin);
		}
	}
	
	public String addJoin(BdInfoUtil bdInfoUtil){
		String fkTable = bdInfoUtil.bdInfo.getTableName();
		String colName = getFkColName(this.bdInfo,fkTable);
		/*String fkTable = bdInfoUtil.bdInfo.getTableName();
		Set<FieldInfo> fieldInfos = this.bdInfo.getFieldInfos();
		for(FieldInfo fieldInfo : fieldInfos){
			Boolean isFk = fieldInfo.getIsFk();
			if(isFk!=null&&isFk==true){
				String joinTable = fieldInfo.getFkTable();
				if(joinTable.equals(fkTable)){
					colName = fieldInfo.getFieldName();
					break;
				}
			}
		}*/
		if(colName!=null){
			TableJoin tableJoin = new TableJoin();
			tableJoin.setColCode(colName);
			tableJoin.setBdInfoUtil(bdInfoUtil);
			tableJoin.setJoinType("LEFT JOIN");
			joinBdInfos.add(tableJoin);
		}
		return colName;
	}
	
	public static String getFkColName(BdInfo bdInfo,String fkTable){
		String colName = null;
		Set<FieldInfo> fieldInfos = bdInfo.getFieldInfos();
		for(FieldInfo fieldInfo : fieldInfos){
			Boolean isFk = fieldInfo.getIsFk();
			if(isFk!=null&&isFk==true){
				String joinTable = fieldInfo.getFkTable();
				if(joinTable.equals(fkTable)){
					colName = fieldInfo.getFieldCode();
					break;
				}
			}
		}
		return colName;
	}
	
	public void addJoin(String colName , BdInfoUtil bdInfoUtil){
		TableJoin tableJoin = new TableJoin();
		tableJoin.setColCode(colName);
		tableJoin.setBdInfoUtil(bdInfoUtil);
		tableJoin.setJoinType("LEFT JOIN");
		joinBdInfos.add(tableJoin);
	}
	
	public void addUnion(String unionCode,Map<String, String> defaultValueMap){
		TableUnion tableUnion = new TableUnion();
		tableUnion.setUnionCode(unionCode);
		tableUnion.setDefaultValueMap(defaultValueMap);
		unionList.add(tableUnion);
	}
	
	public void addCustomCol(String col,String alias,String defaultValue,boolean isNecessary){
		TableCol tableColTemp = new TableCol();
		tableColTemp.setColCode(col);
		tableColTemp.setColAlias(alias);
		tableColTemp.setCustomCol(true);
		if(defaultValue!=null){
			tableColTemp.setDefaultValue(defaultValue);
		}
		tableColTemp.setNecessaryCol(isNecessary);
		tableColList.add(tableColTemp);
	}
	public void addCol(String col,String alias,String defaultValue,boolean isNecessary){
		TableCol tableColTemp = new TableCol();
		tableColTemp.setColCode(col.toUpperCase());
		tableColTemp.setColAlias(alias);
		if(defaultValue!=null){
			tableColTemp.setDefaultValue(defaultValue);
			tableColTemp.setDummyCol(true);
		}
		tableColTemp.setNecessaryCol(isNecessary);
		tableColList.add(tableColTemp);
	}
	
/*	public Map<String, String> getCustomColMap() {
		return customColMap;
	}
	
	public void addCustomCol(String col,String alias){
		customColMap.put(col.toUpperCase(), alias.toUpperCase());
	}
	
	public void setCustomColMap(Map<String, String> customColMap) {
		this.customColMap = customColMap;
	}*/
	
	public boolean isOnlyShowCustom() {
		return onlyShowCustom;
	}

	public void setOnlyShowCustom(boolean onlyShowCustom) {
		this.onlyShowCustom = onlyShowCustom;
	}
	
	public boolean isOnlyShowMain() {
		return onlyShowMain;
	}

	public void setOnlyShowMain(boolean onlyShowMain) {
		this.onlyShowMain = onlyShowMain;
	}
	
	public boolean isFilterXT() {
		return filterXT;
	}

	public void setFilterXT(boolean filterXT) {
		this.filterXT = filterXT;
	}
	
	/*public boolean isOrgFilter() {
		return orgFilter;
	}

	public void setOrgFilter(boolean orgFilter) {
		this.orgFilter = orgFilter;
	}
	public boolean isDeptFilter() {
		return deptFilter;
	}

	public void setDeptFilter(boolean deptFilter) {
		this.deptFilter = deptFilter;
	}
	public boolean isCopyFilter() {
		return copyFilter;
	}

	public void setCopyFilter(boolean copyFilter) {
		this.copyFilter = copyFilter;
	}*/

	public boolean isPeriodYearFilter() {
		return periodYearFilter;
	}

	public void setPeriodYearFilter(boolean periodYearFilter) {
		this.periodYearFilter = periodYearFilter;
	}
	
	public boolean isInitShow() {
		return initShow;
	}

	public void setInitShow(boolean initShow) {
		this.initShow = initShow;
	}
	
	public boolean isResultToLowerCase() {
		return resultToLowerCase;
	}

	public void setResultToLowerCase(boolean resultToLowerCase) {
		this.resultToLowerCase = resultToLowerCase;
	}
	
	public boolean isUnionInfilter() {
		return unionInfilter;
	}

	public void setUnionInfilter(boolean unionInfilter) {
		this.unionInfilter = unionInfilter;
	}
	
	public FieldInfo getOrgCol() {
		return orgCol;
	}

	public void setOrgCol(FieldInfo orgCol) {
		this.orgCol = orgCol;
	}
	public FieldInfo getPkCol() {
		return pkCol;
	}

	public void setPkCol(FieldInfo pkCol) {
		this.pkCol = pkCol;
	}
	public FieldInfo getNameCol() {
		return nameCol;
	}

	public void setNameCol(FieldInfo nameCol) {
		this.nameCol = nameCol;
	}
	public FieldInfo getCodeCol() {
		return codeCol;
	}

	public void setCodeCol(FieldInfo codeCol) {
		this.codeCol = codeCol;
	}
	public FieldInfo getDisabledCol() {
		return disabledCol;
	}
	public void setDisabledCol(FieldInfo disabledCol) {
		this.disabledCol = disabledCol;
	}
	public FieldInfo getParentCol() {
		return parentCol;
	}

	public void setParentCol(FieldInfo parentCol) {
		this.parentCol = parentCol;
	}
	public boolean isToMainAlias() {
		return toMainAlias;
	}

	public void setToMainAlias(boolean toMainAlias) {
		this.toMainAlias = toMainAlias;
	}
	public boolean isHasUnion() {
		if(unionList!=null&&unionList.size()>0){
			hasUnion = true;
		}
		return hasUnion;
	}

	public void setHasUnion(boolean hasUnion) {
		this.hasUnion = hasUnion;
	}
	public String getUnionwhereStr() {
		return unionwhereStr;
	}

	public void setUnionwhereStr(String unionwhereStr) {
		this.unionwhereStr = unionwhereStr;
	}

	public List<Object> getPrameterList() {
		return prameterList;
	}

	public void setPrameterList(List<Object> prameterList) {
		this.prameterList = prameterList;
	}
/*	public boolean isOrgJoin() {
		return orgJoin;
	}

	public void setOrgJoin(boolean orgJoin) {
		this.orgJoin = orgJoin;
	}

	public boolean isDeptJoin() {
		return deptJoin;
	}

	public void setDeptJoin(boolean deptJoin) {
		this.deptJoin = deptJoin;
	}*/

	public boolean isFilterDisabled() {
		return filterDisabled;
	}

	public void setFilterDisabled(boolean filterDisabled) {
		this.filterDisabled = filterDisabled;
	}
	
	//TODO
	public List<Map<String, Object>> formatResult(List rs){
		List<Map<String, Object>> formatRs = new ArrayList<Map<String,Object>>();
		for(int row=0;row<rs.size();row++){
			Map<String, Object> rowMap = (Map)rs.get(row);
			Map<String, Object> formatRowMap = new HashMap<String, Object>();
			Set<String> colSet = rowMap.keySet();
			Iterator<String> it = colSet.iterator();
			while(it.hasNext()){
				String col = it.next();
				Object colData = rowMap.get(col);
				if(isResultToLowerCase()){
					col = col.toLowerCase();
					if(col.equals("pid")){
						col = "pId";
					}
				}
				col = col.replaceAll("__", ".");
				col = col.replaceAll(".HIBERNATE_ROW_NR.", "_HIBERNATE_ROW_NR_");
				formatRowMap.put(col, colData);
			}
			formatRs.add(formatRowMap);
		}
		return formatRs;
	}
	
	
	class TableCol{
		private String colCode;
		private String colAlias;
		private boolean isMainCol=false;
		private boolean isCustomCol=false;
		private boolean isNecessaryCol=false;
		private boolean isDummyCol=false;
		private boolean isDuplicationCol=false;
		
		public boolean isDuplicationCol() {
			return isDuplicationCol;
		}
		public void setDuplicationCol(boolean isDuplicationCol) {
			this.isDuplicationCol = isDuplicationCol;
		}
		public boolean isDummyCol() {
			return isDummyCol;
		}
		public void setDummyCol(boolean isDummyCol) {
			this.isDummyCol = isDummyCol;
		}
		private String defaultValue=null;
		
		public boolean isCustomCol() {
			return isCustomCol;
		}
		public void setCustomCol(boolean isCustomCol) {
			this.isCustomCol = isCustomCol;
		}
		public String getColCode() {
			return colCode;
		}
		public void setColCode(String colCode) {
			this.colCode = colCode;
		}
		public String getColAlias() {
			return colAlias;
		}
		public void setColAlias(String colAlias) {
			this.colAlias = colAlias;
		}
		public boolean isMainCol() {
			return isMainCol;
		}
		public void setMainCol(boolean isMainCol) {
			this.isMainCol = isMainCol;
		}
		public String getDefaultValue() {
			return defaultValue;
		}
		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}
		public boolean isNecessaryCol() {
			return isNecessaryCol;
		}
		public void setNecessaryCol(boolean isNecessaryCol) {
			this.isNecessaryCol = isNecessaryCol;
		}
	}
	
	class TableJoin{
		private String colCode;
		private String joinType;
		private BdInfoUtil bdInfoUtil;
		
		public String getColCode() {
			return colCode;
		}
		public void setColCode(String colCode) {
			this.colCode = colCode;
		}
		public String getJoinType() {
			return joinType;
		}
		public void setJoinType(String joinType) {
			this.joinType = joinType;
		}
		public BdInfoUtil getBdInfoUtil() {
			return bdInfoUtil;
		}
		public void setBdInfoUtil(BdInfoUtil bdInfoUtil) {
			this.bdInfoUtil = bdInfoUtil;
		}
		
	}
	
	class TableOrder{
		private String colCode;
		private String direction;
		private String tableAlias;
		private boolean isAlias = false;
		public String getColCode() {
			return colCode;
		}
		public void setColCode(String colCode) {
			this.colCode = colCode;
		}
		public String getDirection() {
			return direction;
		}
		public void setDirection(String direction) {
			this.direction = direction;
		}
		public boolean isAlias() {
			return isAlias;
		}
		public void setAlias(boolean isAlias) {
			this.isAlias = isAlias;
		}
		public String getTableAlias() {
			return tableAlias;
		}
		public void setTableAlias(String tableAlias) {
			this.tableAlias = tableAlias;
		}
	}
	
	class QueryItem{
		private String itemCode;
		private String itemOperator;
		private Object itemValue;
		private boolean prepared;
		
		public String getItemCode() {
			return itemCode;
		}
		public void setItemCode(String itemCode) {
			this.itemCode = itemCode;
		}
		public String getItemOperator() {
			return itemOperator;
		}
		public void setItemOperator(String itemOperator) {
			this.itemOperator = itemOperator;
		}
		public Object getItemValue() {
			return itemValue;
		}
		public void setItemValue(Object itemValue) {
			this.itemValue = itemValue;
		}
		public boolean isPrepared() {
			return prepared;
		}
		public void setPrepared(boolean prepared) {
			this.prepared = prepared;
		}
		
		
	}
	
	class TableUnion{
		private String UnionCode;
		private BdInfoUtil unionBdInfoUtil;
		private boolean inFilter = false;
		private Map<String, String> colNap;
		private Map<String,	String> defaultValueMap;
		
		public String getUnionCode() {
			return UnionCode;
		}
		public void setUnionCode(String unionCode) {
			UnionCode = unionCode;
		}
		public BdInfoUtil getUnionBdInfoUtil() {
			return unionBdInfoUtil;
		}
		public void setUnionBdInfoUtil(BdInfoUtil unionBdInfoUtil) {
			this.unionBdInfoUtil = unionBdInfoUtil;
		}
		public Map<String, String> getColNap() {
			return colNap;
		}
		public void setColNap(Map<String, String> colNap) {
			this.colNap = colNap;
		}
		public Map<String, String> getDefaultValueMap() {
			return defaultValueMap;
		}
		public void setDefaultValueMap(Map<String, String> defaultValueMap) {
			this.defaultValueMap = defaultValueMap;
		}
		public boolean isInFilter() {
			return inFilter;
		}
		public void setInFilter(boolean inFilter) {
			this.inFilter = inFilter;
		}
	}
	
	public static void main(String[] args) {
		Map<String, String> c = new HashMap<String, String>();
		c.put("a", ""+1);
		c.put("b", ""+1);
		c.put("c", ""+1);
		Map<String, String> cc = new HashMap<String, String>();
		cc.put("b", ""+11);
		cc.put("d", ""+11);
		c.putAll(cc);
		System.out.println(c.get("b"));
	}
}
