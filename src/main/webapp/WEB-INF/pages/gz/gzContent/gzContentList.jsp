
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	/*外部数据源全局变量*/
	var importDataColModels;
	/*外部数据全局配置*/
	var importDataCheck = {
			notUpdate : ["period","issueNumber","orgCode","orgName","deptId","deptCode","deptName","personId","personCode","personName","empType","branchCode","branchName"],
			required : ["period","orgCode","personCode"],
			unique : "period", //唯一完整性配置
			sys : {"period":"PERIODMONTH"}
	};

	var gzContentGridDefine = {
			key:"gzContent_gridtable",
			main:{
				Build : '',
				Load :'',
			},
			event:{
				//单元格数据修改
				"EditChanged":function( id,p1, p2, p3, p4){
					var grid = eval("("+id+")");
					var v = grid.func("GetCellData",p1+"\r\n"+p2);
					var gzIdTemp =  grid.func("GetCellData",p1+"\r\n gzId");
					var rowIndex = jQuery.inArray(gzIdTemp, gzIds);
					if(rowIndex > -1){
						gzContents[rowIndex][p2] = v;
						gzContents[rowIndex]["isEdit"] = '1';
						var changedData = gzContentCalculate(rowIndex,p1);
						//console.log(JSON.stringify(changedData));
						var nextRow = parseInt(p1)+1;
						grid.func("SetRowCellData", p1+'\r\n '+JSON.stringify(changedData));
						grid.func("SelectCell", nextRow+'\r\n '+p2);
					}
				},
 				//单元格双击
 				"DblClicked":function( id,p1, p2, p3, p4){
 					var grid = eval("("+id+")");
 					var colEditAble = grid.func("GetColEditAble",p2);
 					if(colEditAble == 1){//编辑列不弹出卡片
 						return;
 					}
 					var dialogHeight = 600;
 					var cols = grid.func("GetCols","");//获取列数
 					var rows = grid.func("getRows","");//获取行数
 					var dialogRows = 0;
 					if(cols > 0&&rows>0){
 						for(var colIndex = 0;colIndex < cols;colIndex++){
 							var isAbsHide = grid.func("GetColProp",colIndex+" \r\n isAbsHide");//绝对隐藏
 							var isHide = grid.func("GetColProp",colIndex+" \r\n isHide");//隐藏
 							if(isAbsHide == 1||isHide == 1){
 								continue;
 							}
 							dialogRows ++;
 						}
 					}
 					if(dialogRows > 0){
 						if(dialogRows/2 == 0){
 							dialogHeight = 70 + dialogRows/2*30;
 						}else{
 							dialogRows++;
 							dialogHeight = 70 + dialogRows/2*30;
 						}
 					}
 					if(dialogHeight > 600){
 						dialogHeight = 600;
 					}
 					var winTitle='<s:text name="gzContentEdit.title"/>';
 				 	var url = "editGzContent?navTabId=gzContent_gridtable&id="+p1;
 				 	url = encodeURI(url);
 				 	$.pdialog.open(url,'inheritGzContent',winTitle, {ifr:true,hasSupcan:"gzContent_gridtable",mask:true,resizable:true,maxable:true,width : 700,height : dialogHeight});
 				},
 				"MenuBeforePopup":function( id,p1, p2, p3, p4){//鼠标右键菜单即将弹出
 					var grid = eval("("+id+")");
 					var menuTemp = "id=gridRefresh;text=刷新;";
 					grid.func("AddMenu", menuTemp);
 				},
 				"MenuClicked":function( id,p1, p2, p3, p4){//鼠标右键菜单自定义功能被选中
 					var grid = eval("("+id+")");
 					if("gridRefresh" == p1){
 						grid.func("setProp", "sort \r\n orgCode,deptCode,personCode");
 					}
 				}
			},callback:{
				onComplete:function(id){
					var grid = eval("("+id+")");
// 					grid.func("InsertCol", "0\r\nname=checked;isCheckboxOnly=true");
					grid.func("EnableMenu","print,copy,addSort,selectCol,export,separate,showRuler,enter \r\n false");//打印预览,复制,加入多重排序,自定义显示隐藏列,转换输出,分屏冻结,显示/隐藏左标尺,回车键行为	
					var gzCustomLayout = jQuery("#gzContent_gzCustomLayout").val();
					if(gzCustomLayout){
						grid.func("setCustom", gzCustomLayout);
					}
					grid.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
					gzContentWarning();
					grid.func("GrayWindow","0");//遮罩/还原的动作
				}
			}
		};
	supcanGridMap.put("gzContent_gridtable",gzContentGridDefine);
	
	var gzContents;//工资数据新值
	var gzContentWarningObj = {};
	var gzIds = [];//工资Id数组
	var gzContentsOld;//工资数据旧值
	var gzFormulaJsonObj = {};//公式JSON
	var gzContentValueJson = {};//公式存值Json
	var gzContenCalColumns = [];//计算项
	var curPeriod;//当前期间
	var curIssueNumber;//当前发放次数
	var lastPeriod;//t_monthperson取值期间
	var curPeriodStatus;//期间状态(空位可用，非空时内容即为当前状态)
	var curCheckStatus;//工资审核状态
	var gzTypeId;//当前工资类别
	var gzContentGridColumns;
	var gzContentGridGzColMap;
	/*可编辑的列*/
	var editAbleColumns = new Array();
	/*不用保存的列*/
	var notSaveColumns = ["orgCode","period","issueNumber","maker","makeDate","checker","checkDate","status","sex","birthday","duty","educationalLevel",
	                    			"salaryNumber","idNumber","jobTitle","postType","ratio","workBegin","isEdit","disabled",
	                    			"personId","deptId","empType","personName","deptCode","personCode","deptName","orgName"];
	/*需要保存的列(非工资项中的)*/
	var needSaveColumns = ["gzId"];
	jQuery(document).ready(function() {
		//alert(jQuery("#gzContent_pageHeader").outerHeight());
		//alert(jQuery("#gzContent_panelBar").outerHeight());
		var gzContentFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('gzContent_container','gzContent_gridtable',gzContentFullSize);
		if("${gzType}"){
			jQuery("#gzContent_gzTypeName").text("${gzType.gzTypeName}");
			gzTypeId = "${gzType.gzTypeId}";//工资类别
		}else{
			jQuery("#gzContent_gzTypeName").text("未定义当前工资类别");
			gzTypeId = "";//工资类别
		}
		curPeriod = "${curPeriod}";//当前期间
		gzContentLeftTree();
		curIssueNumber = "${curIssueNumber}";//当前发放次数
		lastPeriod = "${lastPeriod}";//t_monthperson取值期间
		curPeriodStatus = "${curPeriodStatus}";//期间状态
		curCheckStatus = "${curCheckStatus}";
		if(curCheckStatus){
			jQuery("#gzContent_checkFlag").show();
		}
		if(curPeriodStatus&&curPeriodStatus.indexOf("已结账")> -1){
			jQuery("#gzContent_checkFlag").show();
			jQuery("#gzContent_checkFlagContent").text("已结账");
		}
		initGzContentGrid();
		jQuery.ajax({
	    	url: 'getCurrentGzContentFormula',
	        data: {gzTypeId:gzTypeId},
	        type: 'post',
	        dataType: 'json',
	        async:false,
	        error: function(data){
	        },
	        success: function(data){
	        	var jsonMap = data.jsonMap;
	        	
	        	if(jsonMap){
	        		for(var empTypeTemp in jsonMap){
	        			if(!gzFormulaJsonObj[empTypeTemp]){
							gzFormulaJsonObj[empTypeTemp] = {};
						}
	        			if(!gzContentValueJson[empTypeTemp]){
	        				gzContentValueJson[empTypeTemp] = {};
	        			}
		        		var jsonObjectListTemp = jsonMap[empTypeTemp];
	    				if(jsonObjectListTemp){
	    					for(var index in jsonObjectListTemp){
	    						var objTemp = jsonObjectListTemp[index];
	    						var gzItem = objTemp["gzItem"];
	    						if(!gzContentValueJson[empTypeTemp][gzItem]){
	    							gzContentValueJson[empTypeTemp][gzItem] = 1;
	    						}else{
	    							gzContentValueJson[empTypeTemp][gzItem] += 1;
	    						}
	    						var itemName = objTemp["name"];
	    						var conditionFormula = objTemp["conditionFormula"];
	    						var conditionParameter = objTemp["conditionParameter"];
	    						var conditionParameterDataType = objTemp["conditionParameterDataType"];
	    						var resultFormula = objTemp["resultFormula"];
	    						var resultParameter = objTemp["resultParameter"];
	    						var resultParameterDataType = objTemp["resultParameterDataType"];
	    						var gzItemJson = {
	    								conditionFormula:conditionFormula,
	    								resultFormula:resultFormula,
	    								calValue:"UnCal"
	    						};
	    						
	    						if("true" != conditionFormula && conditionParameter){
	    								var cps = conditionParameter.split(',');
	    								var cpsDT = conditionParameterDataType.split(',');
	    								var cpJson = [];
	    								for(var cpIndex in cps){
	    									var cp = cps[cpIndex];
	    									var cpDT = cpsDT[cpIndex];
	    									cpJson[cpIndex] = {pName:cp,pDT:cpDT};
	    								}
	    								gzItemJson.conditionParameter = cpJson;
	    						}
	    						if(resultParameter){
	    							var rps = resultParameter.split(',');
	    							var rpsDT = resultParameterDataType.split(',');
	    							var rpJson = [];
	    							for(var rpIndex in rps){
	    								var rp = rps[rpIndex];
	    								var rpDT = rpsDT[rpIndex];
	    								rpJson[rpIndex] = {pName:rp,pDT:rpDT};
	    							}
	    							gzItemJson.resultParameter = rpJson;
	    						}
	    						var callBackItem = objTemp["callBackItem"];
	    						var callBackFormula = objTemp["callBackFormula"];
	    						var callBackParameter = objTemp["callBackParameter"];
	    						var callBackParameterDataType = objTemp["callBackParameterDataType"];
	    						if(callBackItem&&callBackFormula){
	    							gzItemJson.callBackItem = callBackItem;
	    							gzItemJson.callBackFormula = callBackFormula;
	    							if(callBackParameter){
	    								var cbps = callBackParameter.split(',');
		    							var cbpsDT = callBackParameterDataType.split(',');
		    							var cbpJson = [];
		    							for(var cbpIndex in cbps){
		    								var cbp = cbps[cbpIndex];
		    								var cbpDT = cbpsDT[cbpIndex];
		    								cbpJson[cbpIndex] = {pName:cbp,pDT:cbpDT};
		    							}
		    							gzItemJson.callBackParameter = cbpJson;
	    							}
	    						}
	    						
	    						if(!gzFormulaJsonObj[empTypeTemp][gzItem]){
	    							gzFormulaJsonObj[empTypeTemp][gzItem] = {};
	    						}
	    						var jsonLength = getJsonLength(gzFormulaJsonObj[empTypeTemp][gzItem]);
	    						gzFormulaJsonObj[empTypeTemp][gzItem][jsonLength] = gzItemJson;
	    					}
		        		}
		        	}
	        	}
	        	//console.log(JSON.stringify(gzContentValueJson));
	        }
	    });
		

		/*查询框初始化*/
		jQuery("#gzContent_personType").treeselect({
			dataType:"sql",
			optType:"multi",
			sql:"SELECT id,name,parentType parent FROM t_personType where disabled=0  ORDER BY code",
			exceptnullparent:false,
			selectParent:false,
			ifr:true,
			lazy:false
		});
// 		var sql="select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department where disabled=0 and deptId <> 'XT'"
// 		sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ";
// 		sql += " ORDER BY orderCol ";
// 		jQuery("#gzContent_dept").treeselect({
// 			dataType:"sql",
// 			optType:"multi",
// 			sql:sql,
// 			exceptnullparent:false,
// 			selectParent:false,
// 			minWidth : '180px',
// 			ifr:true,
// 			lazy:false
// 		});
		gridContainerResize('gzContent','layout');
		
		jQuery("#gzContent_hideZeroCell").bind("click",function(){
			var checked = jQuery(this).attr("checked");
			var gzContentChanged = gzContent_gridtable.func("GetRowChanged", "-1\r\n NMD");
			if(gzContentChanged){
				alertMsg.error('请先保存当前数据后再进行零不显示！');
				jQuery(this).removeAttr("checked");
				return;
			}
			if(checked == "checked"){
				if(gzContents){
					var gzContentsHideZero = cloneObj(gzContents);
					for(var index in gzContentsHideZero){
						var gzAccount = gzContentsHideZero[index];
						for(var column in gzAccount){
							if(gzAccount[column] === 0){
								gzAccount[column] = null;
							}
						}
					}
					var gzContentGridData = {};
					gzContentGridData.Record = gzContentsHideZero;
					gzContent_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
					gzContent_gridtable.func("load", JSON.stringify(gzContentGridData));
	        		gzContentWarning();
	        		gzContentSearchFormReLoad();
	    			gzContent_gridtable.func("GrayWindow","0");//遮罩/还原的动作
				}
			}else{
				var gzContentGridData = {};
				gzContentGridData.Record = gzContents;
				gzContent_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
				gzContent_gridtable.func("load", JSON.stringify(gzContentGridData));
        		gzContentWarning();
        		gzContentSearchFormReLoad();
    			gzContent_gridtable.func("GrayWindow","0");//遮罩/还原的动作
			}
		});
  	});
	/*部门树start*/
	function gzContentLeftTree(){
		var url = "makeGzDepartmentTree?curPeriod="+curPeriod+"&gzTypeId=" + gzTypeId;
		$.get(url, {"_" : $.now()}, function(data) {
			var gzContentTreeData = data.deptTreeNodes;
			var gzContentTree = $.fn.zTree.init($("#gzContentTreeLeft"),ztreesetting_gzContentTree, gzContentTreeData);
			var nodes = gzContentTree.getNodes();
			gzContentTree.expandNode(nodes[0], true, false, true);
			gzContentTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'gzContentTreeLeft',
				showCode:jQuery('#gzContent_showCode')[0],
				disabledDept:true,
				disabledPerson: true,
				showCount:jQuery("#gzContent_showPersonCount")[0] });
		});
		jQuery("#gzContent_expandTree").text("展开");
	}
	var ztreesetting_gzContentTree = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				fontCss : setDisabledDeptFontCss
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var nodeId = treeNode.id;
					var isParent = treeNode.isParent;
					var filterStr = "1=1";
					if(nodeId != "-1"){
						if(treeNode.subSysTem === 'DEPT' && !isParent){
							filterStr = "deptId = "+ nodeId;
						}else{
							filterStr = "1=2";
							var nodeArr = [];
							var deptIdStr = "";
			    			getAllChildrenZTreeNodes(treeNode,"gzContentTreeLeft",nodeArr);
			    			if(nodeArr){
			    				jQuery.each(nodeArr,function(index,nodeTemp){
			    					if(!nodeTemp.isParent){
			    						var idTemp = nodeTemp.id;
			    						deptIdStr += idTemp + ",";
			    					}
			    				});
			    			}
			    			if(deptIdStr){
			    				deptIdStr = deptIdStr.substring(0,deptIdStr.length-1);
			    				filterStr = "indexOfArray('"+deptIdStr+"', deptId)>-1";
			    			}
						}
					}
					jQuery("#gzContent_dept_id").val(filterStr);
					gzContentSearchFormReLoad();
				}
			},
			data : {
				key : {
					name : "name"
				},
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId"
				}
			}
	};
	/*部门树end*/
	
	

	function initGzContentGrid(){
		jQuery.ajax({
	    	url: 'gzContentColumnInfo',
	        data: {gzTypeId:gzTypeId},
	        type: 'post',
	        dataType: 'json',
	        async:false,
	        error: function(data){
	        },
	        success: function(data){   
	        	var gzItems = data.gzItems;
	        	var columns ="";
	        	var gzColMap = new Map();
	        	for(var itemIndex in gzItems){
	        		var itemCode = gzItems[itemIndex].itemCode;
	        		gzColMap.put(itemCode,gzItems[itemIndex]);
	        		columns += "gz."+itemCode+" "+itemCode+",";
	        		if(jQuery.inArray(itemCode, notSaveColumns) == -1){
	        			needSaveColumns.push(itemCode);
	        		}
	    		}
	        	if(gzItems&&gzItems.length>0){
	        		columns = columns.substring(0,columns.length-1);
	        	}
	        	gzContentGridColumns = columns;
	        	gzContentGridGzColMap = gzColMap;
	        	var colModelDatas = initGzContentColModel(gzItems);
	        	initGzContentGridScript(colModelDatas,columns,gzColMap);
	        	//外部数据导入函数调用
	        	importDataColModels = initImportDataColModel(gzItems);
	        }
	    });
	}
	
	function initGzContentGridScript(colModelDatas,columns,gzColMap){
		var gzContentGrid = cloneObj(supCanTreeListGrid);
		gzContentGrid.Cols = colModelDatas;
		gzContentGridDefine.main.Build = JSON.stringify(gzContentGrid);
		gzContentGridTableload();
	}
	function gzContentGridTableload(){
		jQuery.ajax({
			url: 'gzContentGridList?columns='+gzContentGridColumns,
			data: {curPeriod:curPeriod,curIssueNumber:curIssueNumber,gzTypeId:gzTypeId,curPeriodStatus:curPeriodStatus,lastPeriod:lastPeriod},
			type: 'post',
			dataType: 'json',
			async:true,
			error: function(data){
			},
			success: function(data){
				gzContents = data.gzContentSets;
				gzIds = [];
				if(gzContents){
					jQuery.each(gzContents,function(index,gzContent){
						gzIds.push(gzContent["gzId"]);
					});
				}
				gzContentsOld = cloneObj(gzContents);
				var gzContentGridData = {};
				gzContentGridData.Record = gzContents;
				gzContentGridDefine.main.Load = JSON.stringify(gzContentGridData);
				insertTreeListToDiv("gzContent_gridtable_div","gzContent_gridtable","","100%");
			}
		});
	}
	
	/*列初始化*/
	function initGzContentColModel(data){
		var colModelDatas = [
{name:'gzId',index:'gzId',align:'center',text : '<s:text name="gzContent.gzId" />',width:80,isHide:"absHide",editable:false,dataType:'string'},
{name:'status',index:'status',align:'center',text : '<s:text name="gzContent.status" />',width:80,isHide:"absHide",editable:false,dataType:'string'},
{name:'period',index:'period',align:'center',text : '<s:text name="gzContent.period" />',width:80,isHide:false,editable:false,dataType:'string',totalExpress:"合计",totalAlign:"center"},
{name:'issueNumber',index:'issueNumber',align:'right',text : '<s:text name="gzContent.issueNumber" />',width:80,isHide:false,editable:false,dataType:'int'},
{name:'maker',index:'maker',align:'left',text : '<s:text name="gzContent.maker" />',width:80,isHide:true,editable:false,dataType:'string'},
{name:'makeDate',index:'makeDate',width:'80px',align:'center',text : '<s:text name="gzContent.makeDate" />',isHide:true,dataType:'date',editable:false},
{name:'sex',index:'sex',align:'center',text : '<s:text name="gzContent.sex" />',width:80,isHide:"absHide",editable:false,dataType:'string'},
{name:'birthday',index:'birthday',width:'80px',align:'center',text : '<s:text name="gzContent.birthday" />',isHide:"absHide",editable:false,dataType:'date'},
{name:'duty',index:'duty',width:'80px',align:'left',text : '<s:text name="gzContent.duty" />',isHide:"absHide",editable:false,dataType:'string'},
{name:'educationalLevel',index:'educationalLevel',width:'80px',align:'left',text : '<s:text name="gzContent.educationalLevel" />',isHide:"absHide",editable:false,dataType:'string'},
{name:'salaryNumber',index:'salaryNumber',width:'80px',align:'left',text : '<s:text name="gzContent.salaryNumber" />',isHide:"absHide",editable:false,dataType:'string'},
{name:'idNumber',index:'idNumber',width:'80px',align:'left',text : '<s:text name="gzContent.idNumber" />',isHide:"absHide",editable:false,dataType:'string'},
{name:'jobTitle',index:'jobTitle',width:'80px',align:'left',text : '<s:text name="gzContent.jobTitle" />',isHide:"absHide",editable:false,dataType:'string'},
{name:'postType',index:'postType',width:'80px',align:'left',text : '<s:text name="gzContent.postType" />',isHide:"absHide",editable:false,dataType:'string'},
{name:'ratio',index:'ratio',width:'80px',align:'left',text : '<s:text name="gzContent.ratio" />',isHide:"absHide",editable:false,dataType:'double'},
{name:'disabled',index:'disabled',width:'80px',align:'left',text : '<s:text name="gzContent.disabled" />',isHide:"absHide",editable:false,dataType:'string'},
{name:'workBegin',index:'workBegin',width:'80px',align:'left',text : '<s:text name="gzContent.workBegin" />',isHide:"absHide",editable:false,dataType:'date'} 
		];  
		gzContenCalColumns = [];
		for(var index in data){
	 		var row = data[index];
	 		var calculateType = row.calculateType;
	 		var gzContentHide = row.gzContentHide;
	 		if(calculateType == '1' && (row.itemType == '0' || row.itemType == '3')){
	 			gzContenCalColumns.push(row.itemCode);//计算项
	 		}
	 		var editable = calculateType == '0'?true:false;
	 		if(jQuery.inArray(row.itemCode, notSaveColumns) > -1){
	 			editable = false;
    		}
	 		if(editable){
	 			editAbleColumns.push(row.itemCode);
	 		}
	 		if(curPeriodStatus||curCheckStatus){
	 			editable = false;
	 		}
	 		var colModelData = {  
	            name :  row.itemCode,  
	            index : row.itemId,
	 		    text : row.itemShowName,
	 		    align : supCanParseAlign(row.itemType), 
	 		  	editAble:editable,
	 		    width : 80  
	 		}
	 		if(editable){
	 			colModelData.headerTextColor = "#0000FF";
	 		}
	 		//0000FF #2e6e9e
	 		if(gzContentHide){
	 			colModelData.isHide = true;
	 		}
	 		//colModelData = addGzToFormatter(colModelData,row);
	 		colModelData = supCanAddToEditOption(colModelData,row);
	 		colModelDatas.push(colModelData);
	 		/*限额提醒*/
	 		var warning = row.warning;
	 		var warningType = row.warningType+"";//1:大于;2:大于等于;3:小于;4:小于等于
	 		var warningValue = row.warningValue;
	 		if(warning&&warningType&&warningValue){
	 			switch(warningType){
	 				case '1':
	 					warningType = ">";
	 					break;
	 				case '2':
	 					warningType = ">=";
	 					break;
	 				case '3':
	 					warningType = "<";
	 					break;
	 				case '4':
	 					warningType = "<=";
	 					break;
	 			}
	 			gzContentWarningObj[row.itemCode] = {warningType:warningType,
	 					warningValue:warningValue};
	 		}
		} 
		var colModelDataChecker = {  
	            name :  "checker",  
	            index : "checker",
	 		    text : "审核人",
	 		    align : "left", 
	 		    dataType:"string",
	 		  	editAble:false,
	 		  	isHide:true,
	 		    width : 80  
	 		};
		var colModelDataCheckDate = {  
				 name :  "checkDate",  
		         index : "checkDate",
		 		 text : "审核日期",
		 		 align : "center", 
		 		 dataType:"date",
		 		 editAble:false,
		 		 isHide:true,
		 		 width : 80 
	 		};
		colModelDatas.push(colModelDataChecker);
		colModelDatas.push(colModelDataCheckDate);
		return colModelDatas;
	}

	function gzContentCalculate(rowIndex,gridIndex){
		var gzContent = gzContents[rowIndex];
		if(!gzContent){
			return;
		}
		var changedData = {};
		if(gzContenCalColumns){
			for(var codeTempIndex in gzContenCalColumns){
				var codeTemp = gzContenCalColumns[codeTempIndex];
				//gzContent_gridtable.func("SetCellData", gridIndex +" \r\n "+codeTemp+" \r\n 0");
				changedData[codeTemp] = 0;
	            gzContents[rowIndex][codeTemp] = "0";
			}
		}
		var empType = gzContent["empType"];
		if(gzFormulaJsonObj[empType]){
			var gzContentJson = cloneObj(gzFormulaJsonObj[empType]);
			var valueJson = cloneObj(gzContentValueJson[empType]);
			while (getJsonLength(valueJson) > 0){
				for(var itemCode in gzContentJson){
					var	gzItemJson = gzContentJson[itemCode];
					for(var gzItemIndex in gzItemJson){
						if(!valueJson[itemCode]){
							continue;
						}
						var gzFormulaTempJson = gzItemJson[gzItemIndex];
						var conditionFormula = gzFormulaTempJson.conditionFormula;
						var conditionFlag = false;
						var valueJsonFlag = true;
						if(conditionFormula != "true"){
							var conditionParameter = gzFormulaTempJson.conditionParameter;
							if(conditionParameter){
								for(var cpIndex in conditionParameter){
									var pName = conditionParameter[cpIndex].pName;
									var pDT = conditionParameter[cpIndex].pDT;
									var cpValue = gzContent[pName];
									if(!cpValue&&cpValue!=0){
										cpValue = '';
									}
									if(pDT == "0"||pDT == "3"){//0:数字型;1:字符型;2:日期型;3:整型
										if(!cpValue){
											cpValue = 0;
										}
									}else if(pDT == "1"||pDT == "2"){
										cpValue = "'" + cpValue + "'";
									}
									if(valueJson[pName]){
										cpValue = "'UnCal'";
										valueJsonFlag = false;
									}
									pName = '\\['+pName+'\\]';
									conditionFormula = conditionFormula.replaceAll(pName, cpValue);
								}
							}
							try {
 								conditionFlag = eval(conditionFormula);
							}catch(e){
								conditionFlag = false;
							}
						}else{
							conditionFlag = true;
						}
						var resultFormula = gzFormulaTempJson.resultFormula;
						var resultFlag = true;
						if(conditionFlag){
							var resultParameter = gzFormulaTempJson.resultParameter;
							if(resultParameter){
								for(var rpIndex in resultParameter){
									var pName = resultParameter[rpIndex].pName;
									var pDT = resultParameter[rpIndex].pDT;
									if(valueJson[pName]){
										resultFlag = false;
									}else{
										var rpValue = gzContent[pName];
										if(!rpValue){
											rpValue = 0;
										}
										if(pDT == "0"||pDT == "3"){//0:数字型;1:字符型;2:日期型;3:整型
											if(!rpValue){
												rpValue = 0;
											}
										}else if(pDT == "1"||pDT == "2"){
											rpValue = "'" + rpValue + "'";
										}
										pName = '\\['+pName+'\\]';
										resultFormula = resultFormula.replaceAll(pName, rpValue);
									}
								}
							}
							if(resultFlag){
								 try {
									//console.log(resultFormula);
						            var result = eval(resultFormula);
						            //console.log(result);
						            var numTemp = new Number(+result);
						            result = roundToFixed(numTemp,2);
						            //console.log(result);
									//gzContent_gridtable.func("SetCellData", gridIndex +" \r\n "+itemCode+" \r\n"+result);
									changedData[itemCode] = result;
						            gzContents[rowIndex][itemCode] = result;
						            var callBackItem = gzFormulaTempJson.callBackItem;
						            var callBackFormula = gzFormulaTempJson.callBackFormula;
						            if(callBackItem && callBackFormula){
						            	var callBackParameter = gzFormulaTempJson.callBackParameter;
						            	if(callBackParameter){
											for(var cbpIndex in callBackParameter){
												var pName = callBackParameter[cbpIndex].pName;
												var pDT = callBackParameter[cbpIndex].pDT;
												var cbpValue = gzContent[pName];
												if(!cbpValue){
													cbpValue = 0;
												}
												if(pDT == "0"||pDT == "3"){//0:数字型;1:字符型;2:日期型;3:整型
													if(!cbpValue){
														cbpValue = 0;
													}
												}else if(pDT == "1"||pDT == "2"){
													cbpValue = "'" + cbpValue + "'";
												}
												pName = '\\['+pName+'\\]';
												callBackFormula = callBackFormula.replaceAll(pName, cbpValue);
											}
										}
						            	var cbresult = eval(callBackFormula);
								        var numTemp = new Number(+cbresult);
								        cbresult = roundToFixed(numTemp,2);
								        //gzContent_gridtable.func("SetCellData", gridIndex +" \r\n "+callBackItem+" \r\n"+cbresult);
								        changedCells.push({"row":gridIndex,"col":callBackItem,"value":cbresult});
							            gzContents[rowIndex][callBackItem] = cbresult;
						            }
						            //console.log(resultFormula+":"+result);
						            delete valueJson[itemCode];
						         } catch (e) {
						        	// console.log(resultFormula+":error:"+e.message);
						        	 delete valueJson[itemCode];
						         }
							}
						}else{//条件不成立并且没有条件中没有参数
							if(valueJsonFlag){
								valueJson[itemCode] -= 1;
								if(valueJson[itemCode] === 0){
									delete valueJson[itemCode];
								}
							}
						}
					}
				}
			}
		}
		
		gzContentWarning(gridIndex);
		return changedData;
	}
	/*计算，不修改单元格内容*/
	function gzContentCalculateNoUpdate(rowIndex,gridIndex,changedCells){
			var gzContent = gzContents[rowIndex];
			if(!gzContent){
				return changedCells;
			}
			if(gzContenCalColumns){
				for(var codeTempIndex in gzContenCalColumns){
					var codeTemp = gzContenCalColumns[codeTempIndex];
					if(gzContents[rowIndex][codeTemp] + "" !== "0"){
						gzContents[rowIndex]["isEdit"] = '1';
						changedCells[changedCells.length] = {"row":gridIndex,"col":codeTemp};
					}
		            gzContents[rowIndex][codeTemp] = "0";
				}
			}
			var empType = gzContent["empType"];
			if(gzFormulaJsonObj[empType]){
				var gzContentJson = cloneObj(gzFormulaJsonObj[empType]);//本人员类别下的所有公式
				var valueJson = cloneObj(gzContentValueJson[empType]);//本人员类别下面所有的需要计算的列
// 				for(var codeTemp in valueJson){
// 		            gzContents[rowIndex][codeTemp] = "0";
// 				}
				while (getJsonLength(valueJson) > 0){//列计算完毕跳出循环
					for(var itemCode in gzContentJson){//依次取出每一个需要计算的列code
						var	gzItemJson = gzContentJson[itemCode];//取出该列的公式集合
						for(var gzItemIndex in gzItemJson){
							if(!valueJson[itemCode]){//该列已计算直接continue
								continue;
							}
							var gzFormulaTempJson = gzItemJson[gzItemIndex];//每个小公式
							var conditionFormula = gzFormulaTempJson.conditionFormula;//公式条件
							var conditionFlag = false;//条件标志
							var valueJsonFlag = true;//结果标志
							if(conditionFormula != "true"){//没有配置条件的直接为true
								var conditionParameter = gzFormulaTempJson.conditionParameter;
								if(conditionParameter){
									for(var cpIndex in conditionParameter){
										var pName = conditionParameter[cpIndex].pName;
										var pDT = conditionParameter[cpIndex].pDT;
										var cpValue = gzContent[pName];
										if(!cpValue&&cpValue!=0){
											cpValue = '';
										}
										if(pDT == "0"||pDT == "3"){//0:数字型;1:字符型;2:日期型;3:整型
											if(!cpValue){
												cpValue = 0;
											}
										}else if(pDT == "1"||pDT == "2"){
											cpValue = "'" + cpValue + "'";
										}
										if(valueJson[pName]){//如果参数没有计算到，为UnCal
											cpValue = "'UnCal'";
											valueJsonFlag = false;
										}
										pName = '\\['+pName+'\\]';
										conditionFormula = conditionFormula.replaceAll(pName, cpValue);
									}
								}
								try {
	 								conditionFlag = eval(conditionFormula);
								}catch(e){
									conditionFlag = false;
								}
							}else{
								conditionFlag = true;
							}
							var resultFormula = gzFormulaTempJson.resultFormula;//结果表达式
							var resultFlag = true;
							if(conditionFlag){//条件为真时执行结果
								var resultParameter = gzFormulaTempJson.resultParameter;
								if(resultParameter){
									for(var rpIndex in resultParameter){
										var pName = resultParameter[rpIndex].pName;
										var pDT = resultParameter[rpIndex].pDT;
										if(valueJson[pName]){//如果参数没有计算到，结果标志为false
											resultFlag = false;
										}else{
											var rpValue = gzContent[pName];
											if(!rpValue){
												rpValue = 0;
											}
											if(pDT == "0"||pDT == "3"){//0:数字型;1:字符型;2:日期型;3:整型
												if(!rpValue){
													rpValue = 0;
												}
											}else if(pDT == "1"||pDT == "2"){
												rpValue = "'" + rpValue + "'";
											}
											pName = '\\['+pName+'\\]';
											resultFormula = resultFormula.replaceAll(pName, rpValue);
										}
									}
								}
								if(resultFlag){//结果标志成立时，执行结果表达式
									 try {
							            var result = eval(resultFormula);
							            var numTemp = new Number(+result);
							            result = roundToFixed(numTemp,2);
										//gzContent_gridtable.func("SetCellData", gridIndex +" \r\n "+itemCode+" \r\n"+result);
							            var resultOld = gzContents[rowIndex][itemCode];
						            	changedCells[changedCells.length]={"row":gridIndex,"col":itemCode};
						            	gzContents[rowIndex][itemCode] = result;
						            	gzContents[rowIndex]["isEdit"] = '1';
							            /* if(resultOld != result){
							            } */
							            var callBackItem = gzFormulaTempJson.callBackItem;
							            var callBackFormula = gzFormulaTempJson.callBackFormula;
							            if(callBackItem && callBackFormula){
							            	var callBackParameter = gzFormulaTempJson.callBackParameter;
							            	if(callBackParameter){
												for(var cbpIndex in callBackParameter){
													var pName = callBackParameter[cbpIndex].pName;
													var pDT = callBackParameter[cbpIndex].pDT;
													var cbpValue = gzContent[pName];
													if(!cbpValue){
														cbpValue = 0;
													}
													if(pDT == "0"||pDT == "3"){//0:数字型;1:字符型;2:日期型;3:整型
														if(!cbpValue){
															cbpValue = 0;
														}
													}else if(pDT == "1"||pDT == "2"){
														cbpValue = "'" + cbpValue + "'";
													}
													pName = '\\['+pName+'\\]';
													callBackFormula = callBackFormula.replaceAll(pName, cbpValue);
												}
											}
							            	var cbresult = eval(callBackFormula);
									        var numTemp = new Number(+cbresult);
									        cbresult = roundToFixed(numTemp,2);
									        var cbresultOld = gzContents[rowIndex][callBackItem];
							            	 changedCells[changedCells.length]={"row":gridIndex,"col":callBackItem};
									         gzContents[rowIndex][callBackItem] = cbresult;
									         gzContents[rowIndex]["isEdit"] = '1';
								            /* if(cbresultOld != cbresult){
								            } */
							            }
							           // console.log(resultFormula+":"+result);
							            delete valueJson[itemCode];//计算完成后，删除需要计算的列
							         } catch (e) {
							        	// console.log(resultFormula+":error:"+e.message);
							        	 delete valueJson[itemCode];
							         }
								}
							}else{//条件不成立并且没有条件中没有参数
								if(valueJsonFlag){
									valueJson[itemCode] -= 1;
									if(valueJson[itemCode] === 0){
										delete valueJson[itemCode];
									}
								}
							}
						}
					}
				}
			}
			return changedCells;
	}
	/*公式计算*/
/* 	function gzContentCalculateOld(rowIndex,gridIndex){
		var gzContent = gzContents[rowIndex];
		var empType = gzContent["empType"];
		if(!jsonMap){
			return;
		}
		var gzItemsCalculated = new Array();
		for(var empTypeTemp in jsonMap){
			if(empTypeTemp == empType){
				var jsonObjectListTemp = jsonMap[empTypeTemp];
				if(jsonObjectListTemp){
					for(var index in jsonObjectListTemp){
						var jsonObjectTemp = jsonObjectListTemp[index];
						var gzItem = jsonObjectTemp["gzItem"];
						var itemName = jsonObjectTemp["name"];
						var conditionFormula = jsonObjectTemp["conditionFormula"];
						var conditionParameter = jsonObjectTemp["conditionParameter"];
						var conditionParameterDataType = jsonObjectTemp["conditionParameterDataType"];
						var resultFormula = jsonObjectTemp["resultFormula"];
						var resultParameter = jsonObjectTemp["resultParameter"];
						var resultParameterDataType = jsonObjectTemp["resultParameterDataType"];
						var otherExped = jsonObjectTemp["otherExped"];
						var otherExpType = jsonObjectTemp["otherExpType"];
						var otherExpValue = jsonObjectTemp["otherExpValue"];
						if(jQuery.inArray(gzItem, gzItemsCalculated) == -1){
							var conditionFlag;
							if(conditionFormula != "true"){
								if(conditionParameter){
									var cps = conditionParameter.split(',');
									var cpsDT = conditionParameterDataType.split(',');
									for(var cpIndex in cps){
										var cp = cps[cpIndex];
										var cpDT = cpsDT[cpIndex];
										var cpValue = gzContent[cp];
										if(!cpValue&&cpValue!=0){
											cpValue = '';
										}
										if(cpDT == "0"||cpDT == "3"){//0:数字型;1:字符型;2:日期型;3:整型
											if(!cpValue){
												cpValue = 0;
											}
										}else if(cpDT == "1"||cpDT == "2"){
											cpValue = "'" + cpValue + "'";
										}
										cp = '\\['+cp+'\\]';
										conditionFormula = conditionFormula.replaceAll(cp, cpValue);
									}
								}
								try {
	 								conditionFlag = eval(conditionFormula);
								}catch(e){
									conditionFlag = false;
								}
							}else{
								conditionFlag = true;
							}
							if(conditionFlag){
								if(resultParameter){
									var rps = resultParameter.split(',');
									var rpsDT = resultParameterDataType.split(',');
									for(var rpIndex in rps){
										var rp = rps[rpIndex];
										var rpDT = rpsDT[rpIndex];
										var rpValue = gzContent[rp];
										if(!rpValue){
											rpValue = 0;
										}
										if(rpDT == "0"||rpDT == "3"){//0:数字型;1:字符型;2:日期型;3:整型
											if(!rpValue){
												rpValue = 0;
											}
										}else if(rpDT == "1"||rpDT == "2"){
											rpValue = "'" + rpValue + "'";
										}
										rp = '\\['+rp+'\\]';
										resultFormula = resultFormula.replaceAll(rp, +rpValue);
									}
								}
								 try {
						             var result = eval(resultFormula);
						             if(otherExped){
						            	 if(otherExpType == '1'){
						            		 result = +result + (+otherExpValue); 
						            	 }else if(otherExpType == '2'){
						            		 result = +result - (+otherExpValue); 
						            	 }else if(otherExpType == '3'){
						            		 result = +result * (+otherExpValue); 
						            	 }
						             }
									gzContent_gridtable.func("SetCellData", gridIndex +" \r\n "+gzItem+" \r\n"+result);
						            gzContents[rowIndex][gzItem] = result;
						            if(itemName == "存零"){
						            	var addZeroValue = gzContents[rowIndex][otherExpType];
						            	var realGzItemValue = gzContents[rowIndex][otherExpValue];
						            	realGzItemValue = +realGzItemValue + (+addZeroValue) - result;
						            	gzContent_gridtable.func("SetCellData", gridIndex +" \r\n "+otherExpValue+" \r\n"+realGzItemValue);
							            gzContents[rowIndex][otherExpValue] = realGzItemValue;
						            }
						         } catch (e) {
						         }
						         gzItemsCalculated.push(gzItem);
							}
						}
					}
				}
			}
		}
	} */
	/*查询*/
	function gzContentSearchFormReLoad(){
		var branchCode = jQuery("#gzContent_branch").val();
		var deptName = jQuery("#gzContent_dept").val();
		var deptIds = jQuery("#gzContent_dept_id").val();
		var personName = jQuery("#gzContent_personName").val();
		var empTypes = jQuery("#gzContent_personType").val();
		var conditionFormula = jQuery("#gzContent_multiSearch_content").val();
		var disabled = jQuery("#gzContent_disable").val();
		var deptType = jQuery("#gzContent_deptType").val();
		var filterStr = "1==1 ";
		
		if(deptIds){
			filterStr += " and " +deptIds;
		}
		if(branchCode){
			filterStr += " and branchCode == '" +branchCode+ "' ";
		}
		if(disabled){
			filterStr += " and disabled == '"+disabled+"' ";
		}
		if(personName){
			if(personName.indexOf("*") == -1){
				filterStr += " and personName == '"+personName+"' ";
			}else{
				personName = personName.replaceAll("\\*","");
				filterStr += " and indexOf(personName, '"+personName+"')>=0 ";
			}
		}
		if(deptName){
			if(deptName.indexOf("*")== -1){
				filterStr += " and deptName == '"+deptName+"' ";
			}else{
				deptName = deptName.replaceAll("\\*","");
				filterStr += " and indexOf(deptName, '"+deptName+"')>=0 ";
			}
		}
		if(empTypes){
			filterStr += " and indexOfArray('"+empTypes+"', empType)>=0 ";
		}
		if(deptType){
			filterStr += " and indexOfArray('"+deptType+"', deptType)>=0 ";
		}
		if(conditionFormula){
			filterStr += " and " + conditionFormula;
		}
		filterStr = filterStr.trim();
		gzContent_gridtable.func("Filter", filterStr);
	}
	/*重新加载数据*/
	function gzContentGridTableReLoadData(){
		jQuery.ajax({
			url: 'gzContentGridList?columns='+gzContentGridColumns,
			data: {curPeriod:curPeriod,curIssueNumber:curIssueNumber,gzTypeId:gzTypeId,curPeriodStatus:curPeriodStatus,lastPeriod:lastPeriod},
			type: 'post',
			dataType: 'json',
			async:true,
			error: function(data){
			},
			success: function(data){
				gzContents = data.gzContentSets;
				gzContentsOld = cloneObj(gzContents);
				gzIds = [];
				if(gzContents){
					jQuery.each(gzContents,function(index,gzContent){
						gzIds.push(gzContent["gzId"]);
					});
				}
				var gzContentGridData = {};
				var checked = jQuery("#gzContent_hideZeroCell").attr("checked");
				if(checked == "checked" && gzContents){
					var gzContentsHideZero = cloneObj(gzContents);
					for(var index in gzContentsHideZero){
						var gzAccount = gzContentsHideZero[index];
						for(var column in gzAccount){
							if(gzAccount[column] === 0){
								gzAccount[column] = null;
							}
						}
					}
					gzContentGridData.Record = gzContentsHideZero;
				}else{
					gzContentGridData.Record = gzContents;
				}
				gzContent_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
				gzContent_gridtable.func("load", JSON.stringify(gzContentGridData));
        		gzContentWarning();
        		gzContentSearchFormReLoad();
    			gzContent_gridtable.func("GrayWindow","0");//遮罩/还原的动作
			}
		});
	}
	// 左侧部门树人员数变动
	function gzContentLeftTreeReload(nodeIds,opt) {
		var optNum = 0;
		if(opt == "del") {
			optNum = -1;
		} else if(opt == "add") {
			optNum = 1;
		}
		if(nodeIds) {
			var nodeIdArr = nodeIds.split(",");
			var treeObj = $.fn.zTree.getZTreeObj("gzContentTreeLeft");
			for(var i = 0;i < nodeIdArr.length - 1; i++) {
				var nodeId = nodeIdArr[i];
				var node = treeObj.getNodeByParam("id",nodeId,null);
				var personCount = node.personCount;
				node.personCount = parseInt(personCount) + optNum;
				treeObj.updateNode(node);
				changeLeftTreePersonCount(node,optNum);
				toggleDisabledOrCount({treeId:'gzContentTreeLeft',showCode:jQuery('#gzContent_showCode')[0],disabledDept:true,disabledPerson:true,showCount:jQuery("#gzContent_showPersonCount")[0]});
			}
		}
	}
	
	function changeLeftTreePersonCount(node,optNum) {
		var treeObj = $.fn.zTree.getZTreeObj("gzContentTreeLeft");
		var node = treeObj.getNodeByParam("id",node.pId,null);
		if(node.id != "-1") {
			var personCount = node.personCount;
			node.personCount = parseInt(personCount) + optNum;
			treeObj.updateNode(node);
			changeLeftTreePersonCount(node,optNum);
		}
	}
	
	/*----------------------------------tooBar start-----------------------------------------------*/
	var gzContent_menuButtonArrJson = "${menuButtonArrJson}";
	var gzContent_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(gzContent_menuButtonArrJson,false)));
	var gzContent_toolButtonBar = new ToolButtonBar({el:$('#gzContent_toolbuttonbar'),collection:gzContent_toolButtonCollection,attributes:{
		tableId : 'gzContent_gridtable',
		baseName : 'gzContent',
		width : 600,
		height : 600,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : '<s:text name="gzContentNew.title"/>',
		editTitle : null
	}}).render();
	
	var gzContent_function = new scriptFunction();
	gzContent_function.optBeforeCall = function(e,$this,param){
		var pass = false;
		if(param.gzTypeIdCheck){
			if(!gzTypeId){
				alertMsg.error("未定义当前工资类别！");
				return pass;
			}
		}
		if(param.periodCheck){
			if(curPeriodStatus){
				alertMsg.error(curPeriodStatus);
				return pass;
			}
		}
		if(param.statusCheck){
			if(curCheckStatus){
				alertMsg.error("工资数据已审核！");
				return pass;
			}
		}
		if(param.selectRecord){
			var sid = jQuery("#gzContent_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
	        	alertMsg.error("请选择记录！");
				return pass;
			}
	        if(param.singleSelect){
	        	if(sid.length != 1){
		        	alertMsg.error("请选择一条记录！");
					return pass;
				}
	        }
		}
        return true;
	};

	//初始化
	gzContent_toolButtonBar.addCallBody('11020101',function(e,$this,param){
		alertMsg.confirm("确认初始化?", {
			okCall : function() {
				var gridDivOffset = jQuery("#gzContent_gridtable_div").offset();
				var gridDivWidth = jQuery("#gzContent_gridtable_div").width();
				var gridDivHeight = jQuery("#gzContent_gridtable_div").height();
				jQuery(".progress").css('top',gridDivHeight/2);
				jQuery(".progress").css('left',gridDivOffset.left+gridDivWidth/2-170);
				jQuery(".progress").css('width','250px');
				jQuery(".progress").show();
				bgiframe(jQuery(".progress")); 
				setTimeout(function(){
					gzContent_gridtable.func("GrayWindow",'1 \r\n 255');
					var comPercent = 0 ;
					var progressTimer = setInterval(function(){
						if(comPercent==99){
							clearInterval(progressTimer);
						}
						jQuery(".progress>.progress-val").text("正在初始化 "+comPercent+"%");
						jQuery(".progress>.progress-bar>.progress-in").css('width',comPercent+"%");
						comPercent++;
					},50);
					jQuery.ajax({
						url: 'refreshGzContent?columns=',
						data: {curPeriod:curPeriod,curIssueNumber:curIssueNumber,
							lastPeriod:lastPeriod,gzTypeId:gzTypeId},
						type: 'post',
						dataType: 'json',
						async:true,
						beforeSend:function(XHR){
							jQuery("#progressBar").hide();
						},
						error: function(data){
							alertMsg.error("系统错误!");
						},
						success: function(data){
							if(data.statusCode != 200){
								clearInterval(progressTimer);
								bgiframe(jQuery(".progress"),null,null,null,null,null,'del');
						    	jQuery(".progress").hide();
						    	jQuery(".progress>.progress-val").text("0%");
								jQuery(".progress>.progress-bar>.progress-in").css('width',"0%");
								jQuery(".progress").css('width','220px');
								gzContent_gridtable.func("GrayWindow","0");
								alertMsg.error(data.message);
							}else{
								clearInterval(progressTimer);
								jQuery(".progress>.progress-val").text("初始化完成 100%");
								jQuery(".progress>.progress-bar>.progress-in").css('width',"100%");
								setTimeout(function(){
									gzContentGridTableReLoadData();
									gzContentLeftTree();
									bgiframe(jQuery(".progress"),null,null,null,null,null,'del');
							    	jQuery(".progress").hide();
							    	jQuery(".progress>.progress-val").text("0%");
									jQuery(".progress>.progress-bar>.progress-in").css('width',"0%");
									jQuery(".progress").css('width','220px');
									gzContent_gridtable.func("GrayWindow","0");
									
								},500);
							}
						}
					});
				},500);
				
			}
		});
	});
	gzContent_toolButtonBar.addBeforeCall('11020101',function(e,$this,param){
		return gzContent_function.optBeforeCall(e,$this,param);
	},{gzTypeIdCheck:"gzTypeIdCheck",periodCheck:"periodCheck",statusCheck:"statusCheck"});
	//添加
	gzContent_toolButtonBar.addCallBody('11020102',function(e,$this,param){
		var winTitle='<s:text name="gzContentPersonList.title"/>';
		var url = "gzContentPersonAdd?navTabId=gzContent_gridtable";
		url = encodeURI(url);
		$.pdialog.open(url,'gzContentPersonAdd',winTitle, {ifr:true,hasSupcan:"gzContent_gridtable",mask:true,resizable:true,maxable:true,width : 850,height : 600});
	});
	gzContent_toolButtonBar.addBeforeCall('11020102',function(e,$this,param){
		return gzContent_function.optBeforeCall(e,$this,param);
	},{gzTypeIdCheck:"gzTypeIdCheck",periodCheck:"periodCheck",statusCheck:"statusCheck"});
	//删除
	gzContent_toolButtonBar.addCallBody('11020103',function(e,$this,param){
		var rowId = gzContent_gridtable.func("GetCurrentRow",""); 
		if(rowId){
			var rowIds = rowId.split(",");
			var cellDataStr = "";
			var deptIds = "";
			for(var rowIndex = 0;rowIndex<rowIds.length;rowIndex++){
				var cellData = gzContent_gridtable.func("GetCellData",rowIds[rowIndex] +"\r\n gzId"); 
				cellDataStr += cellData + ",";
				var deptId = gzContent_gridtable.func("GetCellData",rowIds[rowIndex] +"\r\n deptId");
				deptIds += deptId + ",";
			}
			cellDataStr = cellDataStr.substring(0,cellDataStr.length-1);
			var url = "gzContentGridEdit?oper=del";
				alertMsg.confirm("确认删除?", {
					okCall : function() {
						$.post(url,{id:cellDataStr},function(data) {
							formCallBack(data);
							if(data.statusCode == 200){
								gzContentLeftTreeReload(deptIds,"del");
								gzContentGridTableReLoadData();
							}
						});
					}
				});
		}else{
			alertMsg.error('请选择记录！');
			return;
		}
	});
	gzContent_toolButtonBar.addBeforeCall('11020103',function(e,$this,param){
		return gzContent_function.optBeforeCall(e,$this,param);
	},{gzTypeIdCheck:"gzTypeIdCheck",periodCheck:"periodCheck",statusCheck:"statusCheck"});
	//卡片
	gzContent_toolButtonBar.addCallBody('11020104',function(e,$this,param){
		var rowId = gzContent_gridtable.func("GetCurrentRow",""); 
		if(rowId){
			var rowIds = rowId.split(",");
			if(rowIds.length>1){
				alertMsg.error('请选择一条记录！');
				return;
			}
			var dialogHeight = 600;
				var cols = gzContent_gridtable.func("GetCols","");//获取列数
				var rows = gzContent_gridtable.func("getRows","");//获取行数
				var dialogRows = 0;
				if(cols > 0&&rows>0){
					for(var colIndex = 0;colIndex < cols;colIndex++){
						var isAbsHide = gzContent_gridtable.func("GetColProp",colIndex+" \r\n isAbsHide");//绝对隐藏
						var isHide = gzContent_gridtable.func("GetColProp",colIndex+" \r\n isHide");//隐藏
						if(isAbsHide == 1||isHide == 1){
							continue;
						}
						dialogRows ++;
					}
				}
				if(dialogRows > 0){
					if(dialogRows/2 == 0){
						dialogHeight = 70 + dialogRows/2*30;
					}else{
						dialogRows++;
						dialogHeight = 70 + dialogRows/2*30;
					}
				}
				if(dialogHeight > 600){
					dialogHeight = 600;
				}
			var winTitle='<s:text name="gzContentEdit.title"/>';
			var url = "editGzContent?navTabId=gzContent_gridtable&id="+rowId;
			url = encodeURI(url);
			$.pdialog.open(url,'inheritGzContent',winTitle, {ifr:true,hasSupcan:"gzContent_gridtable",mask:true,resizable:true,maxable:true,width : 700,height : dialogHeight});
		}else{
			alertMsg.error('请选择记录！');
			return;
		}
	});
	gzContent_toolButtonBar.addBeforeCall('11020104',function(e,$this,param){
		return gzContent_function.optBeforeCall(e,$this,param);
	},{gzTypeIdCheck:"gzTypeIdCheck"});
	//批量修改
	gzContent_toolButtonBar.addCallBody('11020105',function(e,$this,param){
		if(!gzContents||gzContents.length==0){
			alertMsg.error('无数据需要修改！');
			return;
		}
		var winTitle='<s:text name="gzContentBatchEdit.title"/>';
		var url = "batchEditGzContent?navTabId=gzContent_gridtable&gzTypeId=${gzType.gzTypeId}";
		url = encodeURI(url);
		$.pdialog.open(url,'batchEditGzContent',winTitle, {ifr:true,hasSupcan:"gzContent_gridtable",mask:true,resizable:false,maxable:false,width : 700,height : 550});
	});
	gzContent_toolButtonBar.addBeforeCall('11020105',function(e,$this,param){
		return gzContent_function.optBeforeCall(e,$this,param);
	},{gzTypeIdCheck:"gzTypeIdCheck",periodCheck:"periodCheck",statusCheck:"statusCheck"});
	//重新计算
	gzContent_toolButtonBar.addCallBody('11020106',function(e,$this,param){
		alertMsg.confirm("确认重新计算?", {
			okCall : function() {
				//openProgressBar('reComputeGz',"重新计算中，请稍等...",true);
				var gridDivOffset = jQuery("#gzContent_gridtable_div").offset();
				var gridDivWidth = jQuery("#gzContent_gridtable_div").width();
				var gridDivHeight = jQuery("#gzContent_gridtable_div").height();
				jQuery(".progress").css('top',gridDivHeight/2);
				jQuery(".progress").css('left',gridDivOffset.left+gridDivWidth/2-150);
				jQuery(".progress").show();
				bgiframe(jQuery(".progress")); 
				setTimeout(function(){
					jQuery("#background").show();
			    	gzContent_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
					var rows = gzContent_gridtable.func("getRows", "");
					var changedCells = [],calComplete = false , calCallbackCalled = false;
					if(rows > 0){
						var rowPointer = 0,gridLock = false;
						var calTimer = setInterval(function(){
							if(!gridLock&&rowPointer<rows){
								gridLock = true;
								var gzIdTemp = gzContent_gridtable.func("GetCellData",rowPointer+"\r\n gzId");
								var rowIndex = jQuery.inArray(gzIdTemp, gzIds);
								changedCells = gzContentCalculateNoUpdate(rowIndex,rowPointer,changedCells);
								var comPercent = Math.ceil(rowPointer/rows*100);
								if(comPercent==100){
									comPercent = 99;
								}
								jQuery(".progress>.progress-val").text("正在计算 "+comPercent+"%");
								jQuery(".progress>.progress-bar>.progress-in").css('width',comPercent+"%");
								rowPointer++;
								gridLock = false;
							}
							if(rowPointer==rows){
								calComplete = true;
								clearInterval(calTimer);
							}
						},10);
						/* for(var gridIndex = 0;gridIndex < rows;gridIndex++){
							var gzIdTemp = gzContent_gridtable.func("GetCellData",gridIndex+"\r\n gzId");
							var rowIndex = jQuery.inArray(gzIdTemp, gzIds);
							//changedCells = gzContentCalculateNoUpdate(rowIndex,gridIndex,changedCells);
							jQuery("#currentPeriod").text(gridIndex);
							//sleep(10);
						} */
					}
					var calCallBackTimer = setInterval(function(){
						if(calComplete&&!calCallbackCalled){
							calCallbackCalled = true;
							var gzContentGridData = {};
							gzContentGridData.Record = gzContents;
							gzContent_gridtable.func("load", JSON.stringify(gzContentGridData));
							gzContentSearchFormReLoad();
							gzContentWarning();
				    	if(changedCells&&getJsonLength(changedCells)>0){
				    		//gzContent_gridtable.func("GrayWindow","1");//遮罩/还原的动作
							jQuery.each(changedCells,function(index,changedCell){
								gzContent_gridtable.func("SetCellChanged",changedCell.row+"\r\n"+changedCell.col+"\r\n M");
							});
						}
				    	gzContent_gridtable.func("GrayWindow","0");//遮罩/还原的动作
				    	//closeProgressBar('reComputeGz',null,true)
				    	jQuery(".progress>.progress-val").text("计算完成100%");
						jQuery(".progress>progress-bar>.progress-in").css('width',"100%");
						setTimeout(function(){
					    	bgiframe(jQuery(".progress"),null,null,null,null,null,'del');
					    	jQuery(".progress").hide();
					    	jQuery(".progress>.progress-val").text("0%");
							jQuery(".progress>.progress-bar>.progress-in").css('width',"0%");
							jQuery("#background").hide();
						},1000);
				    	//alertMsg.correct('重新计算成功！');
				    	clearInterval(calCallBackTimer);
						}
					},100);
					
		    },800);
			}
		});
		//$("#progressBar").text("重新计算中，请稍等...");
		//$("#background,#progressBar").show();
	});
	gzContent_toolButtonBar.addBeforeCall('11020106',function(e,$this,param){
		return gzContent_function.optBeforeCall(e,$this,param);
	},{gzTypeIdCheck:"gzTypeIdCheck",periodCheck:"periodCheck",statusCheck:"statusCheck"});
	//保存
	gzContent_toolButtonBar.addCallBody('11020107',function(e,$this,param){
		if(!gzContents||gzContents.length==0){
			alertMsg.error('无数据需要保存！');
			return;
		}
		var needSaveColumn = "";
		jQuery.each(needSaveColumns,function(key,value){
			needSaveColumn += value + ",";
		});
		needSaveColumn = needSaveColumn.substring(0,needSaveColumn.length-1);
		var gzContentStr = "" , editPointer = 0 , gzContentStrArr = new Array();
		var maxStrLength = 1000000;
		for(var index in gzContents){
			var gzContent = gzContents[index];
			var gzContentOld = gzContentsOld[index];
			var gzContentStrTemp = '';
			if(gzContent["isEdit"]=='1'){
				editPointer++;
				jQuery.each(needSaveColumns,function(key,value){
					if(value=='gzItem_14'){
						debugger
					}
					var oldValue = gzContentOld[value];
					var newValue = gzContent[value];
					var valueTemp , changed = false;
					if((value!="gzId"&&oldValue!=newValue&&newValue!=="null")||value=="gzId"){
						valueTemp = newValue;
						changed = true;
					}
					if(valueTemp == undefined){
						//gzContentStrTemp += '"'+ key +'":' + '"'+ valueTemp +'",';
						valueTemp = "";
					}
					if(valueTemp!=""&&valueTemp == 0){
						gzContentStrTemp += '|';
					}else{
						if(changed&&valueTemp === ""){
							valueTemp = "kcnull";
						}
						gzContentStrTemp += valueTemp +',';
					}
				});
			}
			if(gzContentStrTemp){
				gzContentStrTemp = gzContentStrTemp.substring(0,gzContentStrTemp.length-1);
				//gzContentStrTemp = '{' + gzContentStrTemp + '}';
				gzContentStr += gzContentStrTemp +"@";
			}
			if(gzContentStr.length>maxStrLength&&gzContentStr.length%maxStrLength==0){
				/* if(gzContentStr){
					gzContentStr = gzContentStr.substring(0,gzContentStr.length-1);
					gzContentStr = '[' + gzContentStr + ']';
				} */
				gzContentStr = gzContentStr.substring(0,gzContentStr.length-1);
				gzContentStrArr.push(gzContentStr);
				gzContentStr = "";
			}
		}
		if(gzContentStr.length<=maxStrLength){
			if(gzContentStr){
				/* gzContentStr = gzContentStr.substring(0,gzContentStr.length-1);
				gzContentStr = '[' + gzContentStr + ']'; */
			}else{
				alertMsg.error('无数据需要保存！');
				return;
			}
			jQuery.ajax({
		    	url: 'saveGzContent',
		        data: {gzContentStr:gzContentStr,needSaveColumn:needSaveColumn
		        	,curPeriod:curPeriod,curIssueNumber:curIssueNumber,gzTypeId:gzTypeId},
		        type: 'post',
		        dataType: 'json',
		        async:false,
		        error: function(data){
		        	alertMsg.error('系统错误！');
		        },
		        success: function(data){
		        	if("success" == data.message){
		        		gzContentsOld = cloneObj(gzContents);
		        		//gzContentGridTableReLoadData();
		        		gzContent_gridtable.func("ResetChanged","");
		        		setTimeout(function(){
			        		alertMsg.correct('保存成功！');
		        		},1000);
		        	}else{
		        		alertMsg.error('系统错误！');
		        	}
		        }
		    });
		}else{
			var saveComplete = false , savePointer = 0 ,ajaxNum = gzContentStrArr.length, saveLock = false;
			gzContent_gridtable.func("GrayWindow",'1 \r\n 255');
			var gridDivOffset = jQuery("#gzContent_gridtable_div").offset();
			var gridDivWidth = jQuery("#gzContent_gridtable_div").width();
			var gridDivHeight = jQuery("#gzContent_gridtable_div").height();
			jQuery(".progress").css('top',gridDivHeight/2);
			jQuery(".progress").css('left',gridDivOffset.left+gridDivWidth/2-150);
			//jQuery(".progress").css('width','300px');
			jQuery(".progress>.progress-val").text("正在保存 0%");
			jQuery(".progress").show();
			bgiframe(jQuery(".progress")); 
			
			var saveTimer = setInterval(function(){
				if(!saveLock){
					saveLock = true;
					jQuery.ajax({
				    	url: 'saveGzContent',
				        data: {gzContentStr:gzContentStrArr[savePointer],needSaveColumn:needSaveColumn
				        	,curPeriod:curPeriod,curIssueNumber:curIssueNumber,gzTypeId:gzTypeId},
				        type: 'post',
				        dataType: 'json',
				        async:false,
				        error: function(data){
				        	alertMsg.error('系统错误！');
				        },
				        success: function(data){
				        	if("success" == data.message){
				        		var comPercent = Math.ceil((savePointer+1)/ajaxNum*100);
								if(comPercent==100){
									comPercent = 99;
								}
								jQuery(".progress>.progress-val").text("正在保存 "+comPercent+"%");
								jQuery(".progress>.progress-bar>.progress-in").css('width',comPercent+"%");
				        		savePointer++;
				        		if(savePointer==gzContentStrArr.length){
				        			saveComplete = true;
				        			clearInterval(saveTimer);
				        		}
				        		saveLock = false;
				        	}else{
				        		alertMsg.error('系统错误！');
				        	}
				        }
				    });
				}
				
			},100);
			
			var saveCallBackTimer = setInterval(function(){
				if(saveComplete){
					jQuery(".progress>.progress-val").text("保存成功 100%");
					jQuery(".progress>.progress-bar>.progress-in").css('width',"100%");
					gzContentsOld = cloneObj(gzContents);
	        		//gzContentGridTableReLoadData();
	        		gzContent_gridtable.func("ResetChanged","");
	        		/* setTimeout(function(){
		        		alertMsg.correct('保存成功！');
	        		},500); */
	        		gzContent_gridtable.func("GrayWindow","0");//遮罩/还原的动作
	        		setTimeout(function(){
				    	bgiframe(jQuery(".progress"),null,null,null,null,null,'del');
				    	jQuery(".progress").hide();
				    	jQuery(".progress>.progress-val").text("0%");
						jQuery(".progress>.progress-bar>.progress-in").css('width',"0%");
					},1000);
	        		clearInterval(saveCallBackTimer);
				}
			},10);
		}
		
	});
	gzContent_toolButtonBar.addBeforeCall('11020107',function(e,$this,param){
		return gzContent_function.optBeforeCall(e,$this,param);
	},{gzTypeIdCheck:"gzTypeIdCheck",periodCheck:"periodCheck",statusCheck:"statusCheck"});
	//继承上月数据
	gzContent_toolButtonBar.addCallBody('11020108',function(e,$this,param){
		var winTitle='<s:text name="gzContentInherit.title"/>';
	 	var url = "inheritGzContent?navTabId=gzContent_gridtable";
	 	url = encodeURI(url);
	 	$.pdialog.open(url,'inheritGzContent',winTitle, {ifr:true,hasSupcan:"gzContent_gridtable",mask:true,resizable:false,maxable:false,width : 300,height : 500});
	});
	gzContent_toolButtonBar.addBeforeCall('11020108',function(e,$this,param){
		return gzContent_function.optBeforeCall(e,$this,param);
	},{gzTypeIdCheck:"gzTypeIdCheck",periodCheck:"periodCheck",statusCheck:"statusCheck"});
	//审核
	gzContent_toolButtonBar.addCallBody('11020109',function(e,$this,param){
		if(!gzContents||gzContents.length==0){
			alertMsg.error('无数据需要审核！');
			return;
		}
		if(curCheckStatus){
			alertMsg.error('已审核的数据不能再次审核！');
			return;
		}
		var gzContentChanged = gzContent_gridtable.func("GetRowChanged", "-1\r\n NMD");
		if(gzContentChanged){
			alertMsg.error('请先保存当前数据后再进行审核！');
			return;
		}
		alertMsg.confirm("确认审核?", {
			okCall : function() {
				jQuery.ajax({
			    	url: 'gzContentGridEdit',
			    	data: {curPeriod:curPeriod,curIssueNumber:curIssueNumber,oper:"check",gzTypeId:gzTypeId},
			        type: 'post',
			        dataType: 'json',
			        async:false,
			        error: function(data){
			        	alertMsg.error('系统错误！');
			        },
			        success: function(data){
			        	if(data.statusCode == 200){
			        		if(editAbleColumns){
			        			jQuery.each(editAbleColumns,function(key,value){
			        				gzContent_gridtable.func("SetColEditAble",value+" \r\n false");
			        				gzContent_gridtable.func("SetColProp", value+" \r\n headerTextColor \r\n #000000");
			        			});
			        		}
			        		curCheckStatus = "checked";
			        		jQuery("#gzContent_checkFlag").show();
			        		gzContentGridTableReLoadData();
			        		
			        		alertMsg.correct('审核成功！');
			        	}else{
			        		alertMsg.error(data.message);
			        	}
			        }
			    });
			}
		});
	});
	gzContent_toolButtonBar.addBeforeCall('11020109',function(e,$this,param){
		return gzContent_function.optBeforeCall(e,$this,param);
	},{gzTypeIdCheck:"gzTypeIdCheck",periodCheck:"periodCheck"});
	//销审
	gzContent_toolButtonBar.addCallBody('11020110',function(e,$this,param){
		if(!gzContents||gzContents.length==0){
			alertMsg.error('无数据需要销审！');
			return;
		}
		if(!curCheckStatus){
			alertMsg.error('未审核的数据不能销审！');
			return;
		}
		alertMsg.confirm("确认销审?", {
			okCall : function() {
				jQuery.ajax({
			    	url: 'gzContentGridEdit',
			        data: {curPeriod:curPeriod,curIssueNumber:curIssueNumber,oper:"cancelCheck",gzTypeId:gzTypeId},
			        type: 'post',
			        dataType: 'json',
			        async:false,
			        error: function(data){
			        	alertMsg.error('系统错误！');
			        },
			        success: function(data){
			        	if(data.statusCode == 200){
			        		if(editAbleColumns){
			        			jQuery.each(editAbleColumns,function(key,value){
			        				gzContent_gridtable.func("SetColEditAble",value+" \r\n true");
			        				gzContent_gridtable.func("SetColProp", value+" \r\n headerTextColor \r\n #0000FF");
			        			});
			        		}
			        		curCheckStatus = "";
			        		gzContentGridTableReLoadData();
			        		jQuery("#gzContent_checkFlag").hide();
			        		alertMsg.correct('销审成功！');
			        	}else{
			        		alertMsg.error(data.message);
			        	}
			        }
			    });
			}
		});
	});
	gzContent_toolButtonBar.addBeforeCall('11020110',function(e,$this,param){
		return gzContent_function.optBeforeCall(e,$this,param);
	},{gzTypeIdCheck:"gzTypeIdCheck",periodCheck:"periodCheck"});
	
	//外部数据导入
	gzContent_toolButtonBar.addCallBody('11020111',function(e,$this,param){
		importDataFunc("gz_gzContent");
	});
	gzContent_toolButtonBar.addBeforeCall('11020111',function(e,$this,param){
		return gzContent_function.optBeforeCall(e,$this,param);
	},{gzTypeIdCheck:"gzTypeIdCheck",periodCheck:"periodCheck",statusCheck:"statusCheck"});
	
	var gzContent_setColShowButton = {id:'1001020188',buttonLabel:'格式',className:"settlebutton",show:true,enable:true,
			callBody:function(){
				var url = "colShowTemplForm?entityName=com.huge.ihos.gz.gzContent.model.GzContent";
				url += "&navTabId=gzContent_gridtable&oper=supcan";
				$.pdialog.open(url, 'setColShow', "格式", {
					ifr:true,hasSupcan:"gzContent_gridtable",mask:true,resizable:false,maxable:false,
					width : 500,
					height : 300
				});
			}};
	gzContent_toolButtonBar.addButton(gzContent_setColShowButton);
	gzContent_toolButtonBar.addBeforeCall('1001020188',function(e,$this,param){
		return gzContent_function.optBeforeCall(e,$this,param);
	},{});
	var gzContent_setColShowButton = {id:'1001020189',buttonLabel:'关闭查询区',className:"settlebutton",show:true,enable:true,
			callBody:function(){
				toggleFilterArea('gzContent','layout','button_1001020189');
				//alert(jQuery("#gzContent_pageHeader").height());
			}};
	gzContent_toolButtonBar.addButton(gzContent_setColShowButton); 
	/*----------------------------------tooBar end-----------------------------------------------*/
	/*高级查询*/
	function gzContentMultiSearch(){
		var winTitle='<s:text name="gzContentMultiSearch.title"/>';
		var url = "gzContentMultiSearch?navTabId=gzContent_gridtable&gzTypeId="+gzTypeId;
		url = encodeURI(url);
		$.pdialog.open(url,'gzContentMultiSearch',winTitle, {ifr:true,hasSupcan:"gzContent_gridtable",mask:true,resizable:false,maxable:false,width : 700,height : 550});
	}
	/*限额提醒*/
	function gzContentWarning(rowId){
		if(rowId){
			if(gzContentWarningObj){
				for(var code in gzContentWarningObj){
					var warningType = gzContentWarningObj[code].warningType;
					var warningValue = gzContentWarningObj[code].warningValue;
					var gzCellData = gzContent_gridtable.func("GetCellData",rowId +" \r\n " + code);
					var expressTemp = gzCellData +" "+ warningType +" "+ warningValue;
					try {
			            var result = eval(expressTemp);
			            if(result){
			            	gzContent_gridtable.func("SetCellBackColor",rowId +" \r\n " +code+" \r\n #FF0000");
			            }else{
			            	gzContent_gridtable.func("SetCellBackColor",rowId +" \r\n " +code+" \r\n #FFFFFF");
			            }
					}catch(e){
						gzContent_gridtable.func("SetCellBackColor",rowId +" \r\n " +code+" \r\n #FFFFFF");
					}
				}
			}
		}else{
			var rows = gzContent_gridtable.func("getRows","");//获取行数
			if(gzContentWarningObj&&rows>0){
				for(var rowId = 0;rowId<rows-1;rowId++){
					for(var code in gzContentWarningObj){
						var warningType = gzContentWarningObj[code].warningType;
						var warningValue = gzContentWarningObj[code].warningValue;
						var gzCellData = gzContent_gridtable.func("GetCellData",rowId +" \r\n " + code);
						var expressTemp = gzCellData +" "+ warningType +" "+ warningValue;
						try {
				            var result = eval(expressTemp);
				            if(result){
				            	gzContent_gridtable.func("SetCellBackColor",rowId +" \r\n " +code+" \r\n #FF0000");
				            }else{
				            	gzContent_gridtable.func("SetCellBackColor",rowId +" \r\n " +code+" \r\n #FFFFFF");
				            }
						}catch(e){
							gzContent_gridtable.func("SetCellBackColor",rowId +" \r\n " +code+" \r\n #FFFFFF");
						}
					}
				}
			}
		}
	}
	

	/*外部数据导入需要的列信息
	*/
	function initImportDataColModel(data) {
		var importColModels = new Array();
		//期间
		var periodColModel = {
				detailId : "period",
				detailName: "期间",
				detailType: "1",
				entityName:"period",
				isConstraint:'Yes',
				isUpdate : 'No',
				sn : 0
		}
		importColModels.push(periodColModel);
		//发放次数
		var issueNumberColModel = {
				detailId : "issueNumber",
				detailName:"发放次数",
				detailType:"3",
				entityName:"issueNumber",
				isConstraint:'No',
				isUpdate : 'No',
				sn : 1
		}
		importColModels.push(issueNumberColModel);
		for(var index in data) {
			var row = data[index];
			var requiredItems = importDataCheck.required;
			var notUpdateItems = importDataCheck.notUpdate;
			var isConstraint = "No";
			var isUpdate = "Yes";
			for(var i = 0; i < requiredItems.length; i++) {
				if(row.itemCode == requiredItems[i]) {
					isConstraint = "Yes";
					break;
				}
			}
			for(var j = 0; j < notUpdateItems.length; j++) {
				if(row.itemCode == notUpdateItems[j]) {
					isUpdate = "No";
					break;
				}
			}
			var importColModel = {
					detailId : row.itemCode,
					detailName:row.itemName,
					detailType:row.itemType,
					entityName:row.itemCode,
					isConstraint : isConstraint,
					isUpdate : isUpdate,
					sn : index + 2
			}
			importColModels.push(importColModel);
			
		}
		return importColModels;
	}
	
	/*外部数据导入函数*/
	function importDataFunc(tableName) {alert();
		//打开窗口
		var winTitle = "外部数据导入";
		var url = "findAllImportDataDefine?tableName="+tableName+"&navTabId=gzContent_gridtable";
		url += "&subSystemCode=GZ&callBackFunc=gzContentGridTableReLoadData()";
		url = encodeURI(url);
		$.pdialog.open(url,'findAllImportDataDefine',winTitle,{ifr:true,hasSupcan:"gzContent_gridtable",mask:true,resizable:false,maxable:false,width : 622,height : 390});
	}
	function sleep(n) {
		var start = new Date().getTime();
		while(true)  if(new Date().getTime()-start > n) break;
	}
</script>
<div class="page" id="gzContent_page">
	<div id="gzContent_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="gzContent_search_form" class="queryarea-form" >
					<s:hidden id="gzContent_multiSearch_conditionExp"/>
					<s:hidden id="gzContent_multiSearch_content"/>
					<s:hidden id="gzContent_multiSearch_filters"/>
					<input type="hidden" id="gzContent_gzCustomLayout" value='<s:property value="gzCustomLayout" escapeHtml="false"/>'>
					<label class="queryarea-label" >
       					<s:text name='gzContent.gzType'/>:
       					<font id="gzContent_gzTypeName" style="color: red;"></font>
					 </label>
					 <label class="queryarea-label" style="${herpType=='S1'?'display:none':''}">
       					<s:text name='gzContent.branch'/>:
       					<s:select id="gzContent_branch" list="branchs" listKey="branchCode" listValue="branchName" headerKey="" headerValue="--"></s:select>
					 </label>
					<label class="queryarea-label" >
      					<s:select id="gzContent_deptType" list="deptTypes" key='gzContent.deptType' listKey="deptTypeName" listValue="deptTypeName" headerKey="" headerValue="--"></s:select>
					 </label>
					<label class="queryarea-label" >
       					<s:text name='gzContent.deptName'/>:
      					<input type="text" id="gzContent_dept" style="width:120px"/>
					 	<input type="hidden" id="gzContent_dept_id" name="deptIds" />
					 </label>
					 <label class="queryarea-label" >
       					<s:text name='gzContent.personName'/>:
      					<input type="text" id="gzContent_personName" name="personName" style="width:120px"/>
					 </label>
					 <label class="queryarea-label" >
       					<s:text name='gzContent.personType'/>:
      					<input type="text" id="gzContent_personType" name="empTypes" style="width:120px"/>
					 	<input type="hidden" id="gzContent_personType_id"  />
					 </label>
					 <label class="queryarea-label">
						<fmt:message key='person.disable'/>：
						<s:select list="#{'':'--','1':'是','0':'否'}"  name="gzContent_disable"></s:select>
					</label>
					 <label class="queryarea-label" >
       					<s:text name='gzContentMultiSearch.title'/>:
       					<input type="text" id="gzContent_multiSearch" onclick="gzContentMultiSearch()" readonly="readonly"/>
       				 </label>
       				 <label class="queryarea-label" >
							<input type="checkbox" id="gzContent_hideZeroCell" />零不显示
						</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="gzContentSearchFormReLoad()"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="gzContentSearchFormReLoad()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
		<div id="gzContent_buttonBar" class="panelBar">
			<ul class="toolBar" id="gzContent_toolbuttonbar">
				<%-- <li>
					<a class="addbutton" href="javaScript:refreshGzContent()" ><span>刷新</span></a>
				</li>
				<li>
					<a  class="zbcomputebutton"  href="javaScript:gzContentCardEdit()"><span>卡片</span></a>
				</li>
				<li>
					<a id="gzContent_gridtable_save_custom" class="savebutton"  href="javaScript:gzContentSave()"><span>保存</span>
				</a>
				</li>
				<li>
					<a class="initbutton" href="javaScript:inheritGzContent()" ><span>继承上月数据</span>
				</a>
				</li>
				<li>
					<a class="checkbutton"  href="javaScript:gzContentCheck()"><span><s:text name="button.check" /></span></a>
				</li>
				<li>
					<a class="delallbutton"  href="javaScript:gzContentCancelCheck()"><span><s:text name="button.cancelCheck" /></span></a>
				</li> --%>
			</ul>
		</div>
		<div style="margin-top:-20px;margin-right:5px;float:right;display: none;" id="gzContent_checkFlag">
			<font style="color:red;" id="gzContent_checkFlagContent">已审核</font>
		</div>
		<div id="gzContent_container">
		<div id="gzContent_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
					<span>
						显示机构编码
						<input id="gzContent_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'gzContentTreeLeft',showCode:this,disabledDept:true,disabledPerson:true,showCount:jQuery('#gzContent_showPersonCount')[0]})"/>
					</span>
					<span>
						显示人员数
						<input id="gzContent_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'gzContentTreeLeft',showCode:jQuery('#gzContent_showCode')[0],disabledDept:true,disabledPerson:true,showCount:this})"/>
					</span>
					<label id="gzContent_expandTree" onclick="toggleExpandHrTree(this,'gzContentTreeLeft')">展开</label>
				</div>
				<div class="treeTopCheckBox">
					<span>
						快速查询：
						<input type="text" onKeyUp="searchTree('gzContentTreeLeft',this)"/>
					</span>
				</div>
				<div id="gzContentTreeLeft" class="ztree"></div>
			</div>
			<div id="gzContent_layout-center" class="pane ui-layout-center">
			
		<div id="gzContent_gridtable_div"  class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="gzContent_gridtable_navTabId" value="${sessionScope.navTabId}">
			<s:hidden id="gzContent_gridtable_editting"/>
			<s:hidden id="gzContent_gridtable_initialized"/>
			<label style="display: none" id="gzContent_gridtable_addTile">
				<s:text name="gzContentNew.title"/>
			</label> 
			<label style="display: none"
				id="gzContent_gridtable_editTile">
				<s:text name="gzContentEdit.title"/>
			</label>
 			<!--<table id="gzContent_gridtable"></table> -->
			<!--<div id="gzContentPager"></div>-->
		</div>
		</div>
		</div>
	</div>
</div>