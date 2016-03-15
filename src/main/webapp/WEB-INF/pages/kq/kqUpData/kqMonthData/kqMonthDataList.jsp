
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var kqMonthDataGridDefine = {
		key : "kqMonthData_gridtable",
		main : {
			Build : '',
			Load : '',
		},
		event : {
			//单元格数据修改
			"EditChanged" : function(id, p1, p2, p3, p4) {
				var v = eval("("+id+")").func("GetCellData",p1+"\r\n"+p2);
				var kqIdTemp =  eval("("+id+")").func("GetCellData",p1+"\r\n kqId");
				var rowIndex = kqIdMDObj[kqIdTemp];
				if(rowIndex){
					kqMonthDatas[rowIndex][p2] = v;
					kqMonthDatas[rowIndex]["isEdit"] = '1';
					kqMonthDataCalculate(rowIndex,p1);//计算
				}
			},
			//单元格双击
			"DblClicked" : function(id, p1, p2, p3, p4) {
			}
		},
		callback : {
			onComplete : function(id) {
				var grid = eval("(" + id + ")");
				grid.func("EnableMenu","print,copy,addSort,selectCol,export,separate,showRuler,enter \r\n false");//打印预览,复制,加入多重排序,自定义显示隐藏列,转换输出,分屏冻结,显示/隐藏左标尺,回车键行为	
			}
		}
	};
	supcanGridMap.put("kqMonthData_gridtable", kqMonthDataGridDefine);
	var kqTypeIdMD;//考勤类别
	var kqMonthDatas;
	var kqIdMDObj = {};
	var kqMonthDataFormulaJsonObj = {};//公式JSON
	var kqMonthDataContentValueJson = {};//公式存值Json
	var kqMonthDatasOld;//
	var curDeptId;//当前部门
	var curPeriod;//当前期间
	var lastPeriod;//t_monthperson取值期间
	var kqCustomLayoutMD;
	var kqMothDataGridColumns;
	var kqUpDataType;//考勤上报类型
	var curCheckStatusMD;//状态 0新建；1审核；2提交；3人事部门审核；4退回
	var curDayDataCheckStatus;//日表状态
	/*可编辑的列*/
	var editAbleColumnsMD = [];
	/*不用保存的列*/
	var notSaveColumnsMD = [];
	/*需要保存的列(非考勤项中的)*/
	var needSaveColumnsMD = [];
	var kqMonthDataCalColumns = [];//计算项
	var kqMonthDataProofColums = {};
	jQuery(document).ready(function() {
		kqTypeIdMD = "${kqTypeId}";
		curDeptId = "${curDeptId}";
		curPeriod = "${curPeriod}";//当前期间
		lastPeriod = "${lastPeriod}";//t_monthperson取值期间
		kqUpDataType = "${kqUpDataType}";
		initKqMonthDataGrid();
		/*查询框初始化*/
		jQuery("#kqMonthData_personType").treeselect({
			dataType:"sql",
			optType:"multi",
			sql:"SELECT id,name,parentType parent FROM t_personType where disabled=0  ORDER BY code",
			exceptnullparent:false,
			selectParent:false,
			ifr:true,
			lazy:false
		});
	});

	function initKqMonthDataGrid(reLoad) {
		needSaveColumnsMD = ["kqId"];
		notSaveColumnsMD = ["orgCode","period","maker","makeDate","checker","checkDate","submiter","submitDate",
		                    "status","sex","birthday","duty","educationalLevel",
                			"salaryNumber","idNumber","jobTitle","postType","ratio","workBegin","isEdit","disabled",
                			"personId","deptId","empType","personName","deptCode","personCode","deptName","orgName"];
		jQuery.ajax({
			url: 'kqMonthDataColumnInfo',
			data: {kqTypeId:kqTypeIdMD,isDayUp:"0",curPeriod:curPeriod,curDeptId:curDeptId},
			type : 'post',
			dataType : 'json',
			async : true,
			error : function(data) {
			},
			success : function(data) {
				jQuery("#kqMonthData_checkFlag").hide();
				jQuery("#kqMonthData_checkFlagContent").text("");
				curDayDataCheckStatus = data.curDayDataCheckStatus;
				curCheckStatusMD = data.curCheckStatus;
				if(curCheckStatusMD === "1"){
					jQuery("#kqMonthData_checkFlag").show();
					jQuery("#kqMonthData_checkFlagContent").text("已审核");
				}
				if(curCheckStatusMD === "2"){
					jQuery("#kqMonthData_checkFlag").show();
					jQuery("#kqMonthData_checkFlagContent").text("已提交");
				}
				if(curCheckStatusMD === "3"){
					jQuery("#kqMonthData_checkFlag").show();
					jQuery("#kqMonthData_checkFlagContent").text("已通过");
				}
				if(curCheckStatusMD === "4"){
					jQuery("#kqMonthData_checkFlag").show();
					jQuery("#kqMonthData_checkFlagContent").text("已退回");
				}
				var kqItems = data.kqUpItems;
				var columns ="";
	        	for(var itemIndex in kqItems){
	        		var itemCode = kqItems[itemIndex]["itemCode"];
	        		columns += "kq."+itemCode+" "+itemCode+",";
	        		if(jQuery.inArray(itemCode, notSaveColumnsMD) == -1){
	        			needSaveColumnsMD.push(itemCode);
	        		}
	    		}
	        	if(kqItems&&kqItems.length>0){
	        		columns = columns.substring(0,columns.length-1);
	        	}
	        	kqMothDataGridColumns = columns;
				var colModelDatas = initKqMonthDataColModel(kqItems);
				initKqMonthDataGridScript(colModelDatas, columns,reLoad);
			}
		});
		/*取公式*/
		jQuery.ajax({
	    	url: 'getCurrentKqUpDataFormula',
	        data: {kqTypeId:kqTypeIdMD,isDayUp:"0"},
	        type: 'post',
	        dataType: 'json',
	        async:false,
	        error: function(data){
	        },
	        success: function(data){
	        	var jsonList = data.jsonList;
	        	if(jsonList){
	        		for(var index in jsonList){
						var objTemp = jsonList[index];
						var kqItem = objTemp["kqItem"];
						if(!kqMonthDataContentValueJson[kqItem]){
							kqMonthDataContentValueJson[kqItem] = 1;
						}else{
							kqMonthDataContentValueJson[kqItem] += 1;
						}
						var itemName = objTemp["name"];
						var conditionFormula = objTemp["conditionFormula"];
						var conditionParameter = objTemp["conditionParameter"];
						var conditionParameterDataType = objTemp["conditionParameterDataType"];
						var resultFormula = objTemp["resultFormula"];
						var resultParameter = objTemp["resultParameter"];
						var resultParameterDataType = objTemp["resultParameterDataType"];
						var kqItemJson = {
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
								kqItemJson.conditionParameter = cpJson;
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
							kqItemJson.resultParameter = rpJson;
						}
						var callBackItem = objTemp["callBackItem"];
						var callBackFormula = objTemp["callBackFormula"];
						var callBackParameter = objTemp["callBackParameter"];
						var callBackParameterDataType = objTemp["callBackParameterDataType"];
						if(callBackItem&&callBackFormula){
							kqItemJson.callBackItem = callBackItem;
							kqItemJson.callBackFormula = callBackFormula;
							if(callBackParameter){
								var cbps = callBackParameter.split(',');
    							var cbpsDT = callBackParameterDataType.split(',');
    							var cbpJson = [];
    							for(var cbpIndex in cbps){
    								var cbp = cbps[cbpIndex];
    								var cbpDT = cbpsDT[cbpIndex];
    								cbpJson[cbpIndex] = {pName:cbp,pDT:cbpDT};
    							}
    							kqItemJson.callBackParameter = cbpJson;
							}
						}
						
						if(!kqMonthDataFormulaJsonObj[kqItem]){
							kqMonthDataFormulaJsonObj[kqItem] = {};
						}
						var jsonLength = getJsonLength(kqMonthDataFormulaJsonObj[kqItem]);
						kqMonthDataFormulaJsonObj[kqItem][jsonLength] = kqItemJson;
					}
	        	}
// 	        	console.log(JSON.stringify(kqMonthDataFormulaJsonObj));
// 	        	console.log(JSON.stringify(kqMonthDataContentValueJson));
	        }
	    });
	}
	function initKqMonthDataGridScript(colModelDatas, columns,reLoad) {
		var kqMonthDataGrid = cloneObj(supCanTreeListGrid);
		kqMonthDataGrid.Cols = colModelDatas;
		kqMonthDataGrid.Properties.Title = "月考勤上报表";
		kqMonthDataGrid.DropLists = [{"id":"deptList",treelist:"supcanXML?supcanXMLPath=kq/departmentList.xml",DataCol:"name",DisplayCol:"name"}];
		var buildStr = JSON.stringify(kqMonthDataGrid);
		/*硕正配置JSON转换*/
		buildStr = parseBuildString(buildStr);
		if(reLoad){
			kqMonthData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
			kqMonthData_gridtable.func("build", buildStr);
			kqMonthData_gridtable.func("GrayWindow",'0');
			kqMonthDataGridTableload(reLoad);
		}else{
			kqMonthDataGridDefine.main.Build = buildStr;
			kqMonthDataGridTableload();
		}
	}
	/*列初始化*/
	function initKqMonthDataColModel(data) {
		editAbleColumnsMD = [];
		var colModelDatas = { 
				"Col_0_Col":{name : 'kqId',align : 'center',text : '<s:text name="kqMonthData.kqId" />',width : 80,isHide : "absHide",editable : false,dataType : 'string'},
				"Col_1_Col":{name : 'status',align : 'center',text : '<s:text name="kqMonthData.status" />',width : 80,isHide : "absHide",editable : false,dataType : 'string'}, 
				"Col_2_Col":{name : 'period',align : 'center',text : '<s:text name="kqMonthData.period" />',width : 80,isHide : false,editable : false,dataType : 'string',totalExpress:"合计",totalAlign:"center"},
				"Col_3_Col":{name : 'maker',align : 'left',text : '<s:text name="kqMonthData.maker" />',width : 80,isHide : true,editable : false,dataType : 'string'}, 
				"Col_4_Col":{name : 'makeDate',width : '80px',align : 'center',text : '<s:text name="kqMonthData.makeDate" />',isHide : true,dataType : 'date',editable : false},
				"Col_5_Col":{name:'sex',align:'center',text : '<s:text name="kqMonthData.sex" />',width:80,isHide:"absHide",editable:false,dataType:'string'},
				"Col_6_Col":{name:'birthday',width:'80px',align:'center',text : '<s:text name="kqMonthData.birthday" />',isHide:"absHide",editable:false,dataType:'date'},
				"Col_7_Col":{name:'duty',width:'80px',align:'left',text : '<s:text name="kqMonthData.duty" />',isHide:"absHide",editable:false,dataType:'string'},
				"Col_8_Col":{name:'educationalLevel',width:'80px',align:'left',text : '<s:text name="kqMonthData.educationalLevel" />',isHide:"absHide",editable:false,dataType:'string'},
				"Col_9_Col":{name:'salaryNumber',width:'80px',align:'left',text : '<s:text name="kqMonthData.salaryNumber" />',isHide:"absHide",editable:false,dataType:'string'},
				"Col_10_Col":{name:'idNumber',width:'80px',align:'left',text : '<s:text name="kqMonthData.idNumber" />',isHide:"absHide",editable:false,dataType:'string'},
				"Col_11_Col":{name:'jobTitle',width:'80px',align:'left',text : '<s:text name="kqMonthData.jobTitle" />',isHide:"absHide",editable:false,dataType:'string'},
				"Col_12_Col":{name:'postType',width:'80px',align:'left',text : '<s:text name="kqMonthData.postType" />',isHide:"absHide",editable:false,dataType:'string'},
				"Col_13_Col":{name:'ratio',width:'80px',align:'left',text : '<s:text name="kqMonthData.ratio" />',isHide:"absHide",editable:false,dataType:'double'},
				"Col_14_Col":{name:'disabled',width:'80px',align:'left',text : '<s:text name="kqMonthData.disabled" />',isHide:"absHide",editable:false,dataType:'string'},
				"Col_15_Col":{name:'workBegin',width:'80px',align:'left',text : '<s:text name="kqMonthData.workBegin" />',isHide:"absHide",editable:false,dataType:'date'}
		};
		kqMonthDataCalColumns = [];
		kqMonthDataProofColums = {};
		if(data){
			for(var index in data){
				var row = data[index];
		 		var calculateType = row.calculateType;
		 		var kqUpDataHide = row.kqUpDataHide;
		 		var frequency = row.frequency;
		 		var editable = calculateType=='0'?true:false;
		 		if(calculateType == '1' && (row.itemType == '0' || row.itemType == '3')){
		 			kqMonthDataCalColumns.push(row.itemCode);//计算项
		 		}
		 		if(editable){
		 			editAbleColumnsMD.push(row.itemCode);
		 		}
		 		var showType = row.showType;//day,kqItem,XT
		 		if(curCheckStatusMD && (curCheckStatusMD == "1" || curCheckStatusMD == "2" || curCheckStatusMD == "3")){
		 			editable = false;
	 	 		}
		 		var colModelData = {  
		            name :  row.itemCode,  
		 		    text : row.itemName,
		 		    align : supCanParseAlign(row.itemType), 
		 		  	editAble:editable,
		 		    width : 80  
		 		}
		 		if(editable){
		 			colModelData.headerTextColor = "#0000FF";
		 		}
		 		//0000FF #2e6e9e
		 		if(kqUpDataHide){
		 			colModelData.isHide = true;
		 		}
		 		//colModelData = addGzToFormatter(colModelData,row);
		 		colModelData = supCanAddToEditOption(colModelData,row);
		 		if(row.itemCode == "kqDeptName"){
                    colModelData["droplistID"] = "deptList"; 
                    colModelData["edittype"] = "editableDroptreelist"; 
                }  
		 		if(frequency){
		 			var groupText = row.itemName;
		 			colModelData["text"] = frequency;
	 				var jsonLength = getJsonLength(colModelDatas);
	 				var gropName = "group_" + jsonLength + "_group";
	 				var groupObj = {name : groupText,jsonArray : [colModelData],id:row.itemCode+"_group"};
	 				if(editable){
	 					groupObj["textColor"] = "#0000FF";
	 				}
	 				colModelDatas[gropName] = groupObj;
	 				kqMonthDataProofColums[row.itemCode] = frequency;
		 		}else{
		 			var jsonLength = getJsonLength(colModelDatas);
					var ColName = "Col_" + jsonLength + "_Col";
					colModelDatas[ColName] = colModelData;
		 		}
// 				colModelDatas.push(colModelData);
			}
		}
		var colModelDataChecker = {
			name : "checker",
			index : "checker",
			text : "审核人",
			align : "left",
			dataType : "string",
			editAble : false,
			isHide : true,
			width : 80
		};
		var colModelDataCheckDate = {
			name : "checkDate",
			index : "checkDate",
			text : "审核日期",
			align : "center",
			dataType : "date",
			editAble : false,
			isHide : true,
			width : 80
		};
		var colModelDataSubmiter = {
			name : "submiter",
			index : "submiter",
			text : "提交人",
			align : "left",
			dataType : "string",
			editAble : false,
			isHide : true,
			width : 80
		};
		var colModelDataSubmitDate = {
			name : "submitDate",
			index : "submitDate",
			text : "提交日期",
			align : "center",
			dataType : "date",
			editAble : false,
			isHide : true,
			width : 80
		};
		var jsonLength = getJsonLength(colModelDatas);
		var colName = "Col_" + jsonLength + "_Col";
		colModelDatas[colName] = colModelDataChecker;
		jsonLength = getJsonLength(colModelDatas);
		colName = "Col_" + jsonLength + "_Col";
		colModelDatas[colName] = colModelDataCheckDate;
		jsonLength = getJsonLength(colModelDatas);
		colName = "Col_" + jsonLength + "_Col";
		colModelDatas[colName] = colModelDataSubmiter;
		jsonLength = getJsonLength(colModelDatas);
		colName = "Col_" + jsonLength + "_Col";
		colModelDatas[colName] = colModelDataSubmitDate;
		return colModelDatas;
	}
	
	function kqMonthDataGridTableload(reLoad) {
		jQuery.ajax({
			url : 'kqMonthDataGridList',
			data : {columns:kqMothDataGridColumns,kqTypeId:kqTypeIdMD,curDeptId:curDeptId,
				curPeriod:curPeriod,lastPeriod:lastPeriod},
			type : 'post',
			dataType : 'json',
			async : true,
			error : function(data) {
			},
			success : function(data) {
				kqMonthDatas = data.kqMonthDataSets;
				kqMonthDatasOld = cloneObj(kqMonthDatas);
				kqIdMDObj = {};
				if(kqMonthDatas){
					for(var index in kqMonthDatas){
						kqIdMDObj[kqMonthDatas[index]["kqId"]] = index;
					}
				}
				var kqMonthDataGridData = {};
				kqMonthDataGridData.Record = kqMonthDatas;
				if(reLoad){
					kqMonthData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
					kqMonthData_gridtable.func("load", JSON.stringify(kqMonthDataGridData));
					kqMonthData_gridtable.func("GrayWindow",'0');
					kqMonthDataSearchFormReLoad();
				}else{
					kqMonthDataGridDefine.main.Load = JSON.stringify(kqMonthDataGridData);
					insertTreeListToDiv("kqMonthData_gridtable_div","kqMonthData_gridtable", "", "100%");	
				}
			}
		});
	}
	
	//计算
	function kqMonthDataCalculate(rowIndex,gridIndex){
		var kqMonthData = kqMonthDatas[rowIndex];
		if(!kqMonthData){
			return;
		}
		if(kqMonthDataCalColumns){
			for(var codeTempIndex in kqMonthDataCalColumns){
				var codeTemp = kqMonthDataCalColumns[codeTempIndex];
				kqMonthData_gridtable.func("SetCellData", gridIndex +" \r\n "+codeTemp+" \r\n 0");
				kqMonthDatas[rowIndex][codeTemp] = "0";
			}
		}
		if(kqMonthDataFormulaJsonObj){
			var kqMonthDataJson = cloneObj(kqMonthDataFormulaJsonObj);
			var valueJson = cloneObj(kqMonthDataContentValueJson);
			for(var codeTemp in valueJson){
				kqMonthData_gridtable.func("SetCellData", gridIndex +" \r\n "+codeTemp+" \r\n 0");
				kqMonthDatas[rowIndex][codeTemp] = "0";
			}
			while (getJsonLength(valueJson) > 0){
				for(var itemCode in kqMonthDataJson){
					var	kqItemJson = kqMonthDataJson[itemCode];
					for(var kqItemIndex in kqItemJson){
						if(!valueJson[itemCode]){
							continue;
						}
						var kqFormulaTempJson = kqItemJson[kqItemIndex];
						var conditionFormula = kqFormulaTempJson.conditionFormula;
						var conditionFlag = false;
						var valueJsonFlag = true;
						if(conditionFormula != "true"){
							var conditionParameter = kqFormulaTempJson.conditionParameter;
							if(conditionParameter){
								for(var cpIndex in conditionParameter){
									var pName = conditionParameter[cpIndex].pName;
									var pDT = conditionParameter[cpIndex].pDT;
									var cpValue = kqMonthData[pName];
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
						var resultFormula = kqFormulaTempJson.resultFormula;
						var resultFlag = true;
						if(conditionFlag){
							var resultParameter = kqFormulaTempJson.resultParameter;
							if(resultParameter){
								for(var rpIndex in resultParameter){
									var pName = resultParameter[rpIndex].pName;
									var pDT = resultParameter[rpIndex].pDT;
									if(valueJson[pName]){
										resultFlag = false;
									}else{
										var rpValue = kqMonthData[pName];
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
						            var result = eval(resultFormula);
						            var numTemp = new Number(+result);
						            result = numTemp.toFixed(2);
						            kqMonthData_gridtable.func("SetCellData", gridIndex +" \r\n "+itemCode+" \r\n"+result);
						            kqMonthDatas[rowIndex][itemCode] = result;
						            var callBackItem = kqFormulaTempJson.callBackItem;
						            var callBackFormula = kqFormulaTempJson.callBackFormula;
						            if(callBackItem && callBackFormula){
						            	var callBackParameter = kqFormulaTempJson.callBackParameter;
						            	if(callBackParameter){
											for(var cbpIndex in callBackParameter){
												var pName = callBackParameter[cbpIndex].pName;
												var pDT = callBackParameter[cbpIndex].pDT;
												var cbpValue = kqMonthData[pName];
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
								        cbresult = numTemp.toFixed(2);
								        kqMonthData_gridtable.func("SetCellData", gridIndex +" \r\n "+callBackItem+" \r\n"+cbresult);
								        kqMonthDatas[rowIndex][callBackItem] = cbresult;
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
	}
	/*计算，不修改单元格内容*/
	function kqMonthDataCalculateNoUpdate(rowIndex,gridIndex,changedCells){
		var kqMonthData = kqMonthDatas[rowIndex];
		if(!kqMonthData){
			return changedCells;
		}
		if(kqMonthDataCalColumns){
			for(var codeTempIndex in kqMonthDataCalColumns){
				var codeTemp = kqMonthDataCalColumns[codeTempIndex];
				kqMonthDatas[rowIndex][codeTemp] = "0";
			}
		}
		if(kqMonthDataFormulaJsonObj){
			var kqMonthDataJson = cloneObj(kqMonthDataFormulaJsonObj);
			var valueJson = cloneObj(kqMonthDataContentValueJson);
			for(var codeTemp in valueJson){
		         kqMonthDatas[rowIndex][codeTemp] = "0";
			}
				while (getJsonLength(valueJson) > 0){//列计算完毕跳出循环
					for(var itemCode in kqMonthDataJson){//依次取出每一个需要计算的列code
						var	kqUpItemJson = kqMonthDataJson[itemCode];//取出该列的公式集合
						for(var kqUpItemIndex in kqUpItemJson){
							if(!valueJson[itemCode]){//该列已计算直接continue
								continue;
							}
							var kqUpFormulaTempJson = kqUpItemJson[kqUpItemIndex];//每个小公式
							var conditionFormula = kqUpFormulaTempJson.conditionFormula;//公式条件
							var conditionFlag = false;//条件标志
							var valueJsonFlag = true;//结果标志
							if(conditionFormula != "true"){//没有配置条件的直接为true
								var conditionParameter = kqUpFormulaTempJson.conditionParameter;
								if(conditionParameter){
									for(var cpIndex in conditionParameter){
										var pName = conditionParameter[cpIndex].pName;
										var pDT = conditionParameter[cpIndex].pDT;
										var cpValue = kqMonthData[pName];
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
							var resultFormula = kqUpFormulaTempJson.resultFormula;//结果表达式
							var resultFlag = true;
							if(conditionFlag){//条件为真时执行结果
								var resultParameter = kqUpFormulaTempJson.resultParameter;
								if(resultParameter){
									for(var rpIndex in resultParameter){
										var pName = resultParameter[rpIndex].pName;
										var pDT = resultParameter[rpIndex].pDT;
										if(valueJson[pName]){//如果参数没有计算到，结果标志为false
											resultFlag = false;
										}else{
											var rpValue = kqMonthData[pName];
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
							            result = numTemp.toFixed(2);
										//kqMonthData_gridtable.func("SetCellData", gridIndex +" \r\n "+itemCode+" \r\n"+result);
							            var resultOld = kqMonthDatas[rowIndex][itemCode];
							            if(resultOld != result){
							            	changedCells[changedCells.length]={"row":gridIndex,"col":itemCode};
							            	kqMonthDatas[rowIndex][itemCode] = result;
							            	kqMonthDatas[rowIndex]["isEdit"] = '1';
							            }
							            var callBackItem = kqUpFormulaTempJson.callBackItem;
							            var callBackFormula = kqUpFormulaTempJson.callBackFormula;
							            if(callBackItem && callBackFormula){
							            	var callBackParameter = kqUpFormulaTempJson.callBackParameter;
							            	if(callBackParameter){
												for(var cbpIndex in callBackParameter){
													var pName = callBackParameter[cbpIndex].pName;
													var pDT = callBackParameter[cbpIndex].pDT;
													var cbpValue = kqMonthData[pName];
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
									        cbresult = numTemp.toFixed(2);
									        var cbresultOld = kqMonthDatas[rowIndex][callBackItem];
								            if(cbresultOld != cbresult){
								            	 changedCells[changedCells.length]={"row":gridIndex,"col":callBackItem};
										         kqMonthDatas[rowIndex][callBackItem] = cbresult;
										         kqMonthDatas[rowIndex]["isEdit"] = '1';
								            }
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
	
	
	/*初始化*/
	function refreshKqMonthData(){
		if(!curDeptId){
			alertMsg.error("当前权限下，无可操作的部门！");
			return;
		}
		if(curCheckStatusMD === "1"){
			alertMsg.error("上报数据已审核!");
			return;
		}
		if(curCheckStatusMD === "2"){
			alertMsg.error("上报数据已提交!");
			return;
		}
		if(curCheckStatusMD === "3"){
			alertMsg.error("上报数据已通过!");
			return;
		}
		alertMsg.confirm("确认初始化?", {
			okCall : function() {
				jQuery.ajax({
					url: 'refreshKqMonthData',
					data: {curPeriod:curPeriod,lastPeriod:lastPeriod,kqTypeId:kqTypeIdMD,curDeptId:curDeptId},
					type: 'post',
					dataType: 'json',
					async:true,
					error: function(data){
						alertMsg.error("系统错误!");
					},
					success: function(data){
						if(data.statusCode != 200){
							alertMsg.error(data.message);
						}else{
							kqMonthDataGridTableReLoadData();
						}
					}
				});
			}
		});
	}
	/*添加*/
	function addKqMonthData(){
		if(!curDeptId){
			alertMsg.error("当前权限下，无可操作的部门！");
			return;
		}
		if(curCheckStatusMD === "1"){
			alertMsg.error("上报数据已审核!");
			return;
		}
		if(curCheckStatusMD === "2"){
			alertMsg.error("上报数据已提交!");
			return;
		}
		if(curCheckStatusMD === "3"){
			alertMsg.error("上报数据已通过!");
			return;
		}
		var winTitle='<s:text name="kqMonthDataPersonList.title"/>';
		var url = "kqMonthDataPersonAdd?navTabId=kqMonthData_gridtable";
		$.pdialog.open(url,'kqMonthDataPersonAdd',winTitle, {ifr:true,hasSupcan:"kqMonthData_gridtable",mask:true,resizable:true,maxable:true,width : 700,height : 600});
	}
	/*删除*/
	function deleteKqMonthData(){
		if(!curDeptId){
			alertMsg.error("当前权限下，无可操作的部门！");
			return;
		}
		if(curCheckStatusMD === "1"){
			alertMsg.error("上报数据已审核!");
			return;
		}
		if(curCheckStatusMD === "2"){
			alertMsg.error("上报数据已提交!");
			return;
		}
		if(curCheckStatusMD === "3"){
			alertMsg.error("上报数据已通过!");
			return;
		}
		var rowId = kqMonthData_gridtable.func("GetCurrentRow",""); 
		if(rowId){
			var rowIds = rowId.split(",");
			var cellDataStr = "";
			for(var rowIndex = 0;rowIndex<rowIds.length;rowIndex++){
				var cellData = kqMonthData_gridtable.func("GetCellData",rowIds[rowIndex] +"\r\n kqId"); 
				cellDataStr += cellData + ",";
			}
			cellDataStr = cellDataStr.substring(0,cellDataStr.length-1);
			 var url = "kqMonthDataGridEdit?oper=del";
				alertMsg.confirm("确认删除?", {
					okCall : function() {
						$.post(url,{id:cellDataStr},function(data) {
							formCallBack(data);
							if(data.statusCode == 200){
								kqMonthDataGridTableReLoadData();
							}
						});
					}
				});
		}else{
			alertMsg.error('请选择记录！');
			return;
		}
	}
	/*汇总*/
	function summaryKqDayData(){
		if(!curDeptId){
			alertMsg.error("当前权限下，无可操作的部门！");
			return;
		}
		if(!curDayDataCheckStatus){
			alertMsg.error('日上报表无数据！');
			return;
		}
		if(curDayDataCheckStatus === "0"){
			alertMsg.error('日上报表未审核！');
			return;
		}
		if(curDayDataCheckStatus === "2"){
			alertMsg.error('日上报表已汇总！');
			return;
		}
		alertMsg.confirm("确认汇总?", {
			okCall : function() {
				jQuery.ajax({
					url: 'summaryKqDayData',
					data: {curPeriod:curPeriod,lastPeriod:lastPeriod,kqTypeId:kqTypeIdMD,curDeptId:curDeptId},
					type: 'post',
					dataType: 'json',
					async:true,
					error: function(data){
						alertMsg.error("系统错误!");
					},
					success: function(data){
						if(data.statusCode != 200){
							alertMsg.error(data.message);
						}else{
							curDayDataCheckStatus = "2";
							kqMonthDataGridTableReLoadData();
						}
					}
				});
			}
		});
	}
	/*重新加载数据*/
	function kqMonthDataGridTableReLoadData(){
		jQuery.ajax({
			url: 'kqMonthDataGridList',
			data : {columns:kqMothDataGridColumns,kqTypeId:kqTypeIdMD,curDeptId:curDeptId,
				curPeriod:curPeriod,lastPeriod:lastPeriod},
			type: 'post',
			dataType: 'json',
			async:true,
			error: function(data){
			},
			success: function(data){
				kqMonthDatas = data.kqMonthDataSets;
				kqIdMDObj = {};
				if(kqMonthDatas){
					for(var index in kqMonthDatas){
						kqIdMDObj[kqMonthDatas[index]["kqId"]] = index;
					}
				}
				kqMonthDatasOld = cloneObj(kqMonthDatas);
				var kqMonthDataGridData = {};
				kqMonthDataGridData.Record = kqMonthDatas;
				kqMonthData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
				kqMonthData_gridtable.func("load", JSON.stringify(kqMonthDataGridData));
				kqMonthDataSearchFormReLoad();
    			kqMonthData_gridtable.func("GrayWindow","0");//遮罩/还原的动作
			}
		});
	}
	//查询
	function kqMonthDataSearchFormReLoad(){
		var kqTypeIdTemp = jQuery("#kqMonthData_kqType").val();
		if(kqTypeIdMD != kqTypeIdTemp){
			var kqMonthDataChanged = kqMonthData_gridtable.func("GetRowChanged", "-1\r\n NMD");
			if(kqMonthDataChanged){
				alertMsg.error('请先保存当前数据后再切换考勤类别！');
				return;
			}
			kqTypeIdMD = kqTypeIdTemp;
			initKqMonthDataGrid("reload");
		}else{
			var personName = jQuery("#kqMonthData_personName").val();
			var empTypes = jQuery("#kqMonthData_personType").val();
//	 		var disabled = jQuery("#kqMonthData_disable").val();
			var filterStr = "1==1 ";
//	 		if(disabled){
//	 			filterStr += " and disabled == '"+disabled+"' ";
//	 		}
			if(personName){
				if(personName.indexOf("*") == -1){
					filterStr += " and personName == '"+personName+"' ";
				}else{
					personName = personName.replaceAll("\\*","");
					filterStr += " and indexOf(personName, '"+personName+"')>=0 ";
				}
			}
			if(empTypes){
				filterStr += " and indexOfArray('"+empTypes+"', empType)>=0 ";
			}
			filterStr = filterStr.trim();
			kqMonthData_gridtable.func("Filter", filterStr);
		}
	}
	//保存
	function saveKqMonthData(){
		if(!curDeptId){
			alertMsg.error("当前权限下，无可操作的部门！");
			return;
		}
		if(curCheckStatusMD === "1"){
			alertMsg.error("上报数据已审核！");
			return;
		}
		if(curCheckStatusMD === "2"){
			alertMsg.error("上报数据已提交！");
			return;
		}
		if(curCheckStatusMD === "3"){
			alertMsg.error("上报数据已通过!");
			return;
		}
		if(!kqMonthDatas||kqMonthDatas.length==0){
			alertMsg.error('无数据需要保存！');
			return;
		}
		var kqMonthDataStr = "";
		for(var index in kqMonthDatas){
			var kqMonthData = kqMonthDatas[index];
			var kqMonthDataOld = kqMonthDatasOld[index];
			var kqMonthDataStrTemp = '';
			if(kqMonthData["isEdit"]=='1'){
				jQuery.each(needSaveColumnsMD,function(key,value){
					var oldValue = kqMonthDataOld[value];
					var newValue = kqMonthData[value];
					var valueTemp;
					if((value!="kqId"&&oldValue!=newValue&&newValue!=="null")||value=="kqId"){
						valueTemp = newValue;
					}
					if(valueTemp !== undefined){
						kqMonthDataStrTemp += '"'+ key +'":' + '"'+ valueTemp +'",';
					}
				});
			}
			if(kqMonthDataStrTemp){
				kqMonthDataStrTemp = kqMonthDataStrTemp.substring(0,kqMonthDataStrTemp.length-1);
				kqMonthDataStrTemp = '{' + kqMonthDataStrTemp + '}';
				kqMonthDataStr += kqMonthDataStrTemp +",";
			}
		}
		if(kqMonthDataStr){
			kqMonthDataStr = kqMonthDataStr.substring(0,kqMonthDataStr.length-1);
			kqMonthDataStr = '[' + kqMonthDataStr + ']';
		}
		var needSaveColumn = "";
		jQuery.each(needSaveColumnsMD,function(key,value){
			needSaveColumn += value + ",";
		});
		needSaveColumn = needSaveColumn.substring(0,needSaveColumn.length-1);
		jQuery.ajax({
	    	url: 'saveKqMonthData',
	        data: {kqMonthDataStr:kqMonthDataStr,needSaveColumn:needSaveColumn
	        	,curPeriod:curPeriod,curDeptId:curDeptId,kqTypeId:kqTypeIdMD},
	        type: 'post',
	        dataType: 'json',
	        async:false,
	        error: function(data){
	        	alertMsg.error('系统错误！');
	        },
	        success: function(data){
	        	if(data.statusCode == 200){
	        		kqMonthDatasOld = cloneObj(kqMonthDatas);
	        		kqMonthDataGridTableReLoadData();
	        		alertMsg.correct('保存成功！');
	        	}else{
	        		alertMsg.error('系统错误！');
	        	}
	        }
	    });
	}
	//审核
	function checkKqMonthData(){
		if(!curDeptId){
			alertMsg.error("当前权限下，无可操作的部门！");
			return;
		}
		if(!kqMonthDatas||kqMonthDatas.length==0){
			alertMsg.error('无数据需要审核！');
			return;
		}
		if(curCheckStatusMD === "1"){
			alertMsg.error("已审核的数据不能再次审核！");
			return;
		}
		if(curCheckStatusMD === "2"){
			alertMsg.error("已提交的数据不能审核！");
			return;
		}
		if(curCheckStatusMD === "3"){
			alertMsg.error("已通过的数据不能审核!");
			return;
		}
		var kqMonthDataChanged = kqMonthData_gridtable.func("GetRowChanged", "-1\r\n NMD");
		if(kqMonthDataChanged){
			alertMsg.error('请先保存当前数据后再进行审核！');
			return;
		}
		if(proofKqMonthData("reLoad") === false){
			return;
		}
		alertMsg.confirm("确认审核?", {
			okCall : function() {
				jQuery.ajax({
			    	url: 'kqMonthDataGridEdit',
			    	data: {curPeriod:curPeriod,curDeptId:curDeptId,oper:"check",kqTypeId:kqTypeIdMD},
			        type: 'post',
			        dataType: 'json',
			        async:false,
			        error: function(data){
			        	alertMsg.error('系统错误！');
			        },
			        success: function(data){
			        	if(data.statusCode == 200){
			        		kqMonthData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
			        		if(editAbleColumnsMD){
			        			jQuery.each(editAbleColumnsMD,function(key,value){
			        				kqMonthData_gridtable.func("SetColEditAble",value+" \r\n false");
			        				kqMonthData_gridtable.func("SetColProp", value+" \r\n headerTextColor \r\n #000000");
			        				kqMonthData_gridtable.func("SetGroupProp", value+"_group"+" \r\n textColor \r\n #000000");
			        			});
			        		}
			        		curCheckStatusMD = "1";
				    		jQuery("#kqMonthData_checkFlag").show();
				    		jQuery("#kqMonthData_checkFlagContent").text("已审核");
				    		kqMonthData_gridtable.func("GrayWindow",'0');//遮罩/还原的动作
			        		kqMonthDataGridTableReLoadData();
			        		alertMsg.correct('审核成功！');
			        	}else{
			        		alertMsg.error(data.message);
			        	}
			        }
			    });
			}
		});
	}
	//销审
	function cancelCheckKqMonthData(){
		if(!curDeptId){
			alertMsg.error("当前权限下，无可操作的部门！");
			return;
		}
		if(!kqMonthDatas||kqMonthDatas.length==0){
			alertMsg.error('无数据需要销审！');
			return;
		}
		if(curCheckStatusMD === "0" || curCheckStatusMD === "4"){
			alertMsg.error('未审核的数据不能销审！');
			return;
		}
		if(curCheckStatusMD === "2"){
			alertMsg.error("已提交的数据不能销审！");
			return;
		}
		if(curCheckStatusMD === "3"){
			alertMsg.error("已通过的数据不能销审！");
			return;
		}
		alertMsg.confirm("确认销审?", {
			okCall : function() {
				jQuery.ajax({
			    	url: 'kqMonthDataGridEdit',
			        data: {curPeriod:curPeriod,curDeptId:curDeptId,oper:"cancelCheck",kqTypeId:kqTypeIdMD},
			        type: 'post',
			        dataType: 'json',
			        async:false,
			        error: function(data){
			        	alertMsg.error('系统错误！');
			        },
			        success: function(data){
			        	if(data.statusCode == 200){
			        		kqMonthData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
			        		if(editAbleColumnsMD){
			        			jQuery.each(editAbleColumnsMD,function(key,value){
			        				kqMonthData_gridtable.func("SetColEditAble",value+" \r\n true");
			        				kqMonthData_gridtable.func("SetColProp", value+" \r\n headerTextColor \r\n #0000FF");
			        				kqMonthData_gridtable.func("SetGroupProp", value+"_group"+" \r\n textColor \r\n #0000FF");
			        			});
			        		}
			        		curCheckStatusMD = "0";
			        		jQuery("#kqMonthData_checkFlag").hide();
			        		kqMonthData_gridtable.func("GrayWindow",'0');//遮罩/还原的动作
			        		kqMonthDataGridTableReLoadData();
			        		alertMsg.correct('销审成功！');
			        	}else{
			        		alertMsg.error(data.message);
			        	}
			        }
			    });
			}
		});
	}
	//提交
	function submitKqMonthData(){
		if(!curDeptId){
			alertMsg.error("当前权限下，无可操作的部门！");
			return;
		}
		if(!kqMonthDatas||kqMonthDatas.length==0){
			alertMsg.error('无数据需要提交！');
			return;
		}
		if(curCheckStatusMD === "0"||curCheckStatusMD === "4"){
			alertMsg.error("未审核的数据不能提交！");
			return;
		}
		if(curCheckStatusMD === "3"){
			alertMsg.error("已通过的数据不能再次提交！");
			return;
		}
		if(curCheckStatusMD === "2"){
			alertMsg.error("已提交的数据不能再次提交！");
			return;
		}
		var kqMonthDataChanged = kqMonthData_gridtable.func("GetRowChanged", "-1\r\n NMD");
		if(kqMonthDataChanged){
			alertMsg.error('请先保存当前数据后再进行提交！');
			return;
		}
		alertMsg.confirm("确认提交?", {
			okCall : function() {
				jQuery.ajax({
			    	url: 'kqMonthDataGridEdit',
			    	data: {curPeriod:curPeriod,curDeptId:curDeptId,oper:"submit",kqTypeId:kqTypeIdMD},
			        type: 'post',
			        dataType: 'json',
			        async:false,
			        error: function(data){
			        	alertMsg.error('系统错误！');
			        },
			        success: function(data){
			        	if(data.statusCode == 200){
			        		curCheckStatusMD = "2";
				    		jQuery("#kqMonthData_checkFlag").show();
				    		jQuery("#kqMonthData_checkFlagContent").text("已提交");
			        		kqMonthDataGridTableReLoadData();
			        		alertMsg.correct('提交成功！');
			        	}else{
			        		alertMsg.error(data.message);
			        	}
			        }
			    });
			}
		});
	}
	//批量修改
	function batchEditKqMonthData(){
		if(!curDeptId){
			alertMsg.error("当前权限下，无可操作的部门！");
			return;
		}
		if(!kqMonthDatas||kqMonthDatas.length==0){
			alertMsg.error('无数据需要修改！');
			return;
		}
		if(curCheckStatusMD === "1"){
			alertMsg.error("上报数据已审核！");
			return;
		}
		if(curCheckStatusMD === "2"){
			alertMsg.error("上报数据已提交！");
			return;
		}
		if(curCheckStatusMD === "3"){
			alertMsg.error("上报数据已通过！");
			return;
		}
		var winTitle='<s:text name="kqDayDataBatchEdit.title"/>';
		var url = "batchEditKqMonthData?navTabId=kqMonthData_gridtable&kqTypeId="+kqTypeIdMD+"&isDayUp=0";
		$.pdialog.open(url,'batchEditKqMonthData',winTitle, {ifr:true,hasSupcan:"kqMonthData_gridtable",mask:true,resizable:false,maxable:false,width : 700,height : 550});
	}
	//检查
	function proofKqMonthData(reLoad){
		if(!curDeptId){
			alertMsg.error("当前权限下，无可操作的部门！");
			return;
		}
		if(!kqMonthDatas||kqMonthDatas.length==0){
			alertMsg.error('无数据需要检查！');
			return;
		}
		if(curCheckStatusMD === "1"){
			alertMsg.error("上报数据已审核！");
			return;
		}
		if(curCheckStatusMD === "2"){
			alertMsg.error("上报数据已提交！");
			return;
		}
		if(curCheckStatusMD === "3"){
			alertMsg.error("上报数据已通过！");
			return;
		}
		var periodDayCount = +"${periodDayCount}";
		var kqMonthDataProofJson = {};
		if(kqMonthDatas){
			for(var indexTemp in kqMonthDatas){
				var kqMonthData = kqMonthDatas[indexTemp];
				for(var column in kqMonthDataProofColums){
					if(kqMonthDataProofColums[column] != "天"){
						continue;
					}
					var frequencyCount = kqMonthData[column];
					if(isNaN(frequencyCount)){
						frequencyCount = 0;
					}
					frequencyCount = +frequencyCount;
					var proofKey = kqMonthData["personId"];
					if(kqMonthDataProofJson[proofKey]){
						frequencyCount += kqMonthDataProofJson[proofKey]["frequencyCount"];
						kqMonthDataProofJson[proofKey] = {deptId:kqMonthData["deptId"],deptCode:kqMonthData["deptCode"],
								deptName:kqMonthData["deptName"],personId:kqMonthData["personId"],personCode:kqMonthData["personCode"],
								personName:kqMonthData["personName"],frequencyCount:frequencyCount};
					}else{
						kqMonthDataProofJson[proofKey] = {deptId:kqMonthData["deptId"],deptCode:kqMonthData["deptCode"],
								deptName:kqMonthData["deptName"],personId:kqMonthData["personId"],personCode:kqMonthData["personCode"],
								personName:kqMonthData["personName"],frequencyCount:frequencyCount};
					}
				}
			}
		}
		var errorMessage = "";
		if(kqMonthDataProofJson){
			for(var index in kqMonthDataProofJson){
				var frequencyCount = kqMonthDataProofJson[index]["frequencyCount"];
				if(frequencyCount != periodDayCount){
					var deptName = kqMonthDataProofJson[index]["deptName"];
					var personName = kqMonthDataProofJson[index]["personName"];
					errorMessage += "部门["+deptName+"]中人员["+personName+"]";
				}
			}
		}
		if(errorMessage){
			alertMsg.error(errorMessage + "天数合计不为"+periodDayCount+"！");
			return false;
		}else{
			if(reLoad){
				return true;
			}else{
				alertMsg.correct('检查成功。');
				return;
			}
		}
	}
</script>

<div class="page">
	<div id="kqMonthData_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="kqMonthData_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='kqMonthData.kqType'/>:
						<select id="kqMonthData_kqType">
							<c:forEach var="kqType" items="${kqTypes}">
								<option value="${kqType.kqTypeId}">${kqType.kqTypeName}</option>
								</c:forEach>
						</select>
				    </label>
						<label class="queryarea-label">
       					<s:text name='kqMonthData.personName'/>:
      					<input type="text" id="kqMonthData_personName" name="personName" style="width:120px"/>
					 </label>
					 <label class="queryarea-label">
       					<s:text name='kqMonthData.empType'/>:
      					<input type="text" id="kqMonthData_personType" name="empTypes" style="width:120px"/>
					 	<input type="hidden" id="kqMonthData_personType_id"  />
					 </label>
					<div class="buttonActive" style="float: right">
						<div class="buttonContent">
							<button type="button"
								onclick="kqMonthDataSearchFormReLoad()">
								<s:text name='button.search' />
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
			<s:if test="kqUpDataType==1">
				<li>
					<a class="addbutton" href="javaScript:refreshKqMonthData()" ><span>初始化</span></a>
				</li>
				<li>
					<a class="addbutton" href="javaScript:addKqMonthData()" ><span>添加</span></a>
				</li>
				<li>
					<a class="delbutton" href="javaScript:deleteKqMonthData()" ><span>删除</span></a>
				</li>
				<li>
					<a class="zbcomputebutton" href="javaScript:batchEditKqMonthData()" ><span>批量修改</span></a>
				</li>
				<li>
					<a class="savebutton" href="javaScript:saveKqMonthData()" ><span>保存</span></a>
				</li>
<!-- 				<li> -->
<!-- 					<a class="addbutton" href="javaScript:" ><span>导入排版</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="addbutton" href="javaScript:" ><span>导入休假</span></a> -->
<!-- 				</li> -->
				<li>
					<a class="checkbutton" href="javaScript:" ><span>检查</span></a>
				</li>
			</s:if>
			<s:else>
				<li>
					<a class="addbutton" href="javaScript:summaryKqDayData()" ><span>汇总</span></a>
				</li>
				<li>
					<a class="zbcomputebutton" href="javaScript:batchEditKqMonthData()" ><span>批量修改</span></a>
				</li>
				<li>
					<a class="savebutton" href="javaScript:saveKqMonthData()" ><span>保存</span></a>
				</li>
			</s:else>
				<li>
					<a class="checkbutton" href="javaScript:checkKqMonthData()" ><span>审核</span></a>
				</li>
				<li>
					<a class="delallbutton" href="javaScript:cancelCheckKqMonthData()" ><span>销审</span></a>
				</li>
				<li>
					<a class="addbutton" href="javaScript:submitKqMonthData()" ><span>提交</span></a>
				</li>
				<li>
					<a class="settingbutton" href="javaScript:proofKqMonthData()" ><span>检查</span></a>
				</li>
			</ul>
		</div>
		<div style="margin-top:-20px;margin-right:5px;float:right;display: none;" id="kqMonthData_checkFlag">
			<font style="color:red;" id="kqMonthData_checkFlagContent">已审核</font>
		</div>
		<div id="kqMonthData_gridtable_div" layoutH="98" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<div id="load_kqMonthData_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
		</div>
	</div>
</div>