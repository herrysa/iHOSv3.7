<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var kqDayDataGridDefine = {
		key : "kqDayData_gridtable",
		main : {
			Build : '',
			Load : '',
		},
		event : {
			//单元格数据修改
			"EditChanged" : function(id, p1, p2, p3, p4) {
				var v = eval("("+id+")").func("GetCellData",p1+"\r\n"+p2);
				var kqIdTemp =  eval("("+id+")").func("GetCellData",p1+"\r\n kqId");
				var rowIndex = kqIdDDObj[kqIdTemp];
				if(rowIndex){
					kqDayDatas[rowIndex][p2] = v;
					kqDayDatas[rowIndex]["isEdit"] = '1';
					kqDayDataCalculate(rowIndex,p1);//计算
				}
			},
			//单元格双击
			"DblClicked" : function(id, p1, p2, p3, p4) {
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
		},
		callback : {
			onComplete : function(id) {
				var grid = eval("(" + id + ")");
// 				grid.func("InsertCol", "0\r\nname=checked;isCheckboxOnly=true");
				grid.func("EnableMenu","print,copy,addSort,selectCol,export,separate,showRuler,enter \r\n false");//打印预览,复制,加入多重排序,自定义显示隐藏列,转换输出,分屏冻结,显示/隐藏左标尺,回车键行为	
				var kqCustomLayout = jQuery("#kqUpData_kqCustomLayout").val();
				if(kqCustomLayout){
					kqCustomLayout = kqDayDataColSetting(grid,kqCustomLayout);
					grid.func("setCustom", kqCustomLayout);
				}
			}
		}
	};
	supcanGridMap.put("kqDayData_gridtable", kqDayDataGridDefine);
	var kqTypeIdDD = "";//考勤类别
	var kqUpDataType = "";//考勤上报方式0：日考勤上报;1:月考勤上报
	var kqUpDataGridJson = {};//分组列
	var kqIdDDObj = {};
	var kqDayDatas = "";
	var curCheckStatusDD;//状态 0新建；1审核；2提交；3人事部门审核；4退回
	var kqDayDataDetail = "";//明细数据
	var kqDayDataFormulaJsonObj = {};//公式JSON
	var kqDayDataContentValueJson = {};//公式存值Json
	var kqDayDatasOld = "";//
	var curDeptId = "";//当前部门
	var curPeriod = "";//当前期间
	var curPeriodStatus = "";
	var lastPeriod = "";//t_monthperson取值期间
	var kqCustomLayoutDD = "";
	var curPeriodWeek = "";//当前期间星期
	var kqDayDataGridColumns = "";
	var kqItemsDD = "";
	var kqDayDataShowIconCols = [];//需要显示图片的列
	var kqDayDataIconJson = {};//图片JSON
	/*可编辑的列*/
	var editAbleColumnsDD = [];
	/*不用保存的列*/
	var notSaveColumnsDD = [];
	/*需要保存的列(非考勤项中的)*/
	var needSaveColumnsDD = [];
	
	var kqDayDataCalColumns = [];//计算项
	
	var kqItemShortNameJson = {};//考勤项以简称作为key的json
	
	var kqMonthDataProofColums = {};
	var buttonIdsTemp = ["1006040301","1006040302","1006040303","1006040304",
	                 "1006040305","1006040306","1006040307","1006040308",
	                 "1006040309","1006040310"];
	
	jQuery(document).ready(function() {
		initKqMenuButton();
		kqTypeIdDD = "${kqTypeId}";
		curDeptId = "${curDeptId}";
		curPeriod = "${curPeriod}";//当前期间
		lastPeriod = "${lastPeriod}";//t_monthperson取值期间
		kqUpDataType = "${kqUpDataType}";
		curPeriodWeek = jQuery("#kqDayData_curPeriodWeek").val();//当前期间星期
		if(curPeriodWeek){
			curPeriodWeek = JSON.parse(curPeriodWeek);
		}
		/*查询框初始化*/
		jQuery("#kqDayData_personType").treeselect({
			dataType:"sql",
			optType:"multi",
			sql:"SELECT id,name,parentType parent FROM t_personType where disabled=0  ORDER BY code",
			exceptnullparent:false,
			selectParent:false,
			ifr:true,
			lazy:false
		});
		//考勤项与上报表关联
		jQuery.ajax({
				url : 'getMDKqItems',
				data : {kqTypeId:kqTypeIdDD},
				type : 'post',
				dataType : 'json',
				async : true,
				error : function(data) {
				},
				success : function(data) {
					kqItemsDD = data.kqItems;
					var detailHtml = '<button type="button" style="cursor: pointer;margin-left: 10px;" onclick="kqDayDataChangeCell(\'\')">清空</button>';
					if(kqItemsDD){
						for(var index in kqItemsDD){
							var nameTemp = kqItemsDD[index]["shortName"];
							var itemIcon = kqItemsDD[index]["itemIcon"];
							if(itemIcon){
								var imgSrc = "${ctx}"+itemIcon;
								kqDayDataIconJson[nameTemp] = imgSrc;
								detailHtml += '<button type="button" style="cursor: pointer;margin-left: 10px;" onclick="kqDayDataChangeCell(\''+nameTemp+'\')"><img alt="" src="'+imgSrc+'">'+nameTemp+'</button>';
							}else{
								detailHtml += '<button type="button" style="cursor: pointer;margin-left: 10px;" onclick="kqDayDataChangeCell(\''+nameTemp+'\')">'+nameTemp+'</button>';
							}
						}
					}
					if(kqUpDataType == "0"){
						detailHtml = jQuery("#kqDayData_gridtable_buttonDetail").html() + detailHtml;
						jQuery("#kqDayData_gridtable_buttonDetail").html(detailHtml);
					}
				}
		});
		if(kqUpDataType != "0"){
			jQuery("#kqDayData_gridtable_buttonLi").remove();
			jQuery("#kqDayData_gridtable_buttonDetail").remove();
		}
	
		//勤不显示
		jQuery("#kqDayData_hideQinCell").bind("click",function(){
			var checked = jQuery(this).attr("checked");
			var kqDayDataChanged = kqDayData_gridtable.func("GetRowChanged", "-1\r\n NMD");
			if(kqDayDataChanged){
				alertMsg.error('请先保存当前数据后再进行勤不显示！');
				jQuery(this).removeAttr("checked");
				return;
			}
			if(checked == "checked"){
				if(kqDayDatas){
					var kqDayDatasHideQin = cloneObj(kqDayDatas);
					for(var index in kqDayDatasHideQin){
						var kqDayData = kqDayDatasHideQin[index];
						for(var column in kqDayData){
							if(kqDayData[column] === "勤"){
								kqDayData[column] = null;
							}
						}
					}
					var kqDayDataGridData = {};
					kqDayDataGridData.Record = kqDayDatasHideQin;
					kqDayData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
					kqDayData_gridtable.func("load", JSON.stringify(kqDayDataGridData));
					kqDayDataSearchFormReLoad();
	        		kqDayData_gridtable.func("GrayWindow","0");//遮罩/还原的动作
				}
			}else{
				var kqDayDataGridData = {};
				kqDayDataGridData.Record = kqDayDatas;
				kqDayData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
				kqDayData_gridtable.func("load", JSON.stringify(kqDayDataGridData));
				kqDayDataSearchFormReLoad();
        		kqDayData_gridtable.func("GrayWindow","0");//遮罩/还原的动作
			}
		});
		initKqDayDataGrid();
		var mpPeriodChange = jQuery("#kqUpData_mpPeriodChange").val();
		if(mpPeriodChange){
			var mpObj = JSON.parse(mpPeriodChange);
			var mpDetail = "";
			for(var index in mpObj){
				var mpTemp = mpObj[index];
				mpDetail += "<p style='line-height: 130%;'><b>部门：</b>" + mpTemp["deptName"]+"&nbsp;&nbsp;";
				mpDetail += "<b>新增(" + mpTemp["pInCount"] + ")：</b>" + mpTemp["pIn"]+"&nbsp;&nbsp;";
				mpDetail += "<b>减少(" + mpTemp["pOutCount"] + ")：</b>" + mpTemp["pOut"]+"</p>";
			}
			jQuery("#kqDayData_mpDetail").html(mpDetail);
		}
		gridContainerResize("kqDayData","div",0,80);
	});

	function initKqDayDataGrid(reLoad) {
		needSaveColumnsDD = ["kqId"];
		notSaveColumnsDD = ["orgCode","period","maker","makeDate","checker","checkDate","submiter","submitDate",
		                    "status","sex","birthday","duty","educationalLevel",
                			"salaryNumber","idNumber","jobTitle","postType","ratio","workBegin","isEdit","disabled",
                			"personId","deptId","empType","personName","deptCode","personCode","deptName","orgName"];
		jQuery.ajax({
	    	url: 'kqDayDataColumnInfo',
	        data: {kqTypeId:kqTypeIdDD,curPeriod:curPeriod,curDeptId:curDeptId,kqUpDataType:kqUpDataType},
	        type: 'post',
	        dataType: 'json',
	        async:false,
	        error: function(data){
	        },
	        success: function(data){
	        	jQuery("#kqDayData_checkFlag").hide();
    			jQuery("#kqDayData_checkFlagContent").text("");
	        	curCheckStatusDD = data.curCheckStatus;
	        	curPeriodStatus = data.curPeriodStatus;
	        	if(!kqTypeIdDD){
	    			curPeriodStatus = "请选择考勤类别";
	    		}
	        	initKqMenuButton();
	        	if(curCheckStatusDD === "1"){
	    			jQuery("#kqDayData_checkFlag").show();
	    			jQuery("#kqDayData_checkFlagContent").text("已审核");
	    		}
	        	if(curCheckStatusDD === "2"){
	        		jQuery("#kqDayData_checkFlag").show();
	    			jQuery("#kqDayData_checkFlagContent").text("已提交");
	        	}
	        	if(curCheckStatusDD === "3"){
	        		jQuery("#kqDayData_checkFlag").show();
	    			jQuery("#kqDayData_checkFlagContent").text("已通过");
	        	}
	        	if(curCheckStatusDD === "4"){
	        		jQuery("#kqDayData_checkFlag").show();
	    			jQuery("#kqDayData_checkFlagContent").text("已退回");
	        	}
	        	var kqUpItems = data.kqUpItems;
	        	var columns ="";
	        	for(var itemIndex in kqUpItems){
	        		var itemCode = kqUpItems[itemIndex].itemCode;
	        		columns += "kq."+itemCode+" "+itemCode+",";
	        		if(jQuery.inArray(itemCode, notSaveColumnsDD) == -1){
	        			needSaveColumnsDD.push(itemCode);
	        		}
	    		}
	        	if(kqUpItems&&kqUpItems.length>0){
	        		columns = columns.substring(0,columns.length-1);
	        	}
	        	kqDayDataGridColumns = columns;
	        	var colModelDatas = initKqDayDataColModel(kqUpItems);
	    		initKqDayDataGridScript(colModelDatas, columns,reLoad);
	        }
	    });
		/*取公式*/
		jQuery.ajax({
	    	url: 'getCurrentKqUpDataFormula',
	        data: {kqTypeId:kqTypeIdDD,kqUpDataType:kqUpDataType},
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
						if(!kqDayDataContentValueJson[kqItem]){
							kqDayDataContentValueJson[kqItem] = 1;
						}else{
							kqDayDataContentValueJson[kqItem] += 1;
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
						
						if(!kqDayDataFormulaJsonObj[kqItem]){
							kqDayDataFormulaJsonObj[kqItem] = {};
						}
						var jsonLength = getJsonLength(kqDayDataFormulaJsonObj[kqItem]);
						kqDayDataFormulaJsonObj[kqItem][jsonLength] = kqItemJson;
					}
	        	}
	        	//console.log(JSON.stringify(kqDayDataFormulaJsonObj));
	        	//console.log(JSON.stringify(kqDayDataContentValueJson));
	        }
	    });
	}
	function initKqDayDataGridScript(colModelDatas, columns,reLoad) {
		var kqDayDataGrid = cloneObj(supCanTreeListGrid);
		kqDayDataGrid.Cols = colModelDatas;
		kqDayDataGrid.Properties.Title = "考勤上报表";
		kqDayDataGrid.Properties.curselmode = "excel";
		kqDayDataGrid.DropLists = [{"id":"deptList",treelist:"supcanXML?supcanXMLPath=kq/departmentList.xml",DataCol:"name",DisplayCol:"name"}];
		var buildStr = JSON.stringify(kqDayDataGrid);
		/*硕正配置JSON转换*/
		//buildStr = parseBuildString(buildStr);
		if(reLoad){
			kqDayData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
			kqDayData_gridtable.func("build", buildStr);
			kqDayData_gridtable.func("GrayWindow",'0');
			kqDayDataGridTableload(reLoad);
		}else{
			kqDayDataGridDefine.main.Build = buildStr;
			kqDayDataGridTableload();
		}
	}
	/*列初始化*/
	function initKqDayDataColModel(data) {
		editAbleColumnsDD = [];
		var colModelDatas = [
			{name : 'kqId',align : 'center',text : '<s:text name="kqDayData.kqId" />',width : 80,isHide : "absHide",editable : false,dataType : 'string'},
			{name : 'status',align : 'center',text : '<s:text name="kqDayData.status" />',width : 80,isHide : "absHide",editable : false,dataType : 'string'}, 
			{name : 'period',align : 'center',text : '<s:text name="kqDayData.period" />',width : 80,isHide : false,editable : false,dataType : 'string',totalExpress:"合计",totalAlign:"center"},
			{name : 'maker',align : 'left',text : '<s:text name="kqDayData.maker" />',width : 80,isHide : true,editable : false,dataType : 'string'},
			{name : 'makeDate',width : '80px',align : 'center',text : '<s:text name="kqDayData.makeDate" />',isHide : true,dataType : 'date',editable : false},
			{name:'sex',align:'center',text : '<s:text name="kqDayData.sex" />',width:80,isHide:"absHide",editable:false,dataType:'string'},
			{name:'birthday',width:'80px',align:'center',text : '<s:text name="kqDayData.birthday" />',isHide:"absHide",editable:false,dataType:'date'},
			{name:'duty',width:'80px',align:'left',text : '<s:text name="kqDayData.duty" />',isHide:"absHide",editable:false,dataType:'string'},
			{name:'educationalLevel',width:'80px',align:'left',text : '<s:text name="kqDayData.educationalLevel" />',isHide:"absHide",editable:false,dataType:'string'},
			{name:'salaryNumber',width:'80px',align:'left',text : '<s:text name="kqDayData.salaryNumber" />',isHide:"absHide",editable:false,dataType:'string'},
			{name:'idNumber',width:'80px',align:'left',text : '<s:text name="kqDayData.idNumber" />',isHide:"absHide",editable:false,dataType:'string'},
			{name:'jobTitle',width:'80px',align:'left',text : '<s:text name="kqDayData.jobTitle" />',isHide:"absHide",editable:false,dataType:'string'},
			{name:'postType',width:'80px',align:'left',text : '<s:text name="kqDayData.postType" />',isHide:"absHide",editable:false,dataType:'string'},
			{name:'ratio',width:'80px',align:'left',text : '<s:text name="kqDayData.ratio" />',isHide:"absHide",editable:false,dataType:'double'},
			{name:'disabled',width:'80px',align:'left',text : '<s:text name="kqDayData.disabled" />',isHide:"absHide",editable:false,dataType:'string'},
			{name:'workBegin',width:'80px',align:'left',text : '<s:text name="kqDayData.workBegin" />',isHide:"absHide",editable:false,dataType:'date'}
		] ;
		
		var curPeriodWeekObj = {};
		kqMonthDataProofColums = {};
		if(curPeriodWeek){
			for(var index in curPeriodWeek){
				var curPeriodWeekTemp = curPeriodWeek[index];
				curPeriodWeekObj[curPeriodWeekTemp["code"]] = curPeriodWeekTemp;
			}
		}
		kqDayDataShowIconCols = [];
		kqDayDataCalColumns = [];
		var groupIndex = 0;
		for(var index in data){
	 		var row = data[index];
	 		var calculateType = row.calculateType;
	 		var kqUpDataHide = row.kqUpDataHide;
	 		var frequency = row.frequency;
	 		var editable = calculateType=='0'?true:false;
	 		if(calculateType == '1' && (row.itemType == '0' || row.itemType == '3')){
	 			kqDayDataCalColumns.push(row.itemCode);//计算项
	 		}
	 		var showType = row.showType;//day,kqItem,XT
	 		if("kqItem" == showType && kqUpDataType == "0"){//按日上报时候汇总数据不可改
	 			editable = false;
	 		}
	 		if("day" == showType || ("XT" == showType && row.itemCode != "kqDeptName")){//31天不可改
	 			editable = false;
	 		}
	 		if(editable){
	 			editAbleColumnsDD.push(row.itemCode);
	 		}
	 		if(curCheckStatusDD && (curCheckStatusDD == "1" || curCheckStatusDD == "2" || curCheckStatusDD == "3")){
	 			editable = false;
	 		}
	 		if(curPeriodStatus){
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
			if("day" == showType){
				var curPeriodWeekTemp = curPeriodWeekObj[row.itemCode];
				if(curPeriodWeekTemp){
					kqUpDataGridJson[row.itemCode] = {name:row.itemCode,type:"day",sn:groupIndex++};
	 				var groupText = curPeriodWeekTemp["name"];
	 				var isHoliday = curPeriodWeekTemp["isHoliday"];
	 				var jsonLength = getJsonLength(colModelDatas);
	 				var gropName = "group_" + jsonLength + "_group";
	 				var groupObj = {name : groupText,cols : [colModelData]};
	 				if(isHoliday == "true"){
	 					groupObj["textColor"] = "#f94a52";
	 				}
	 				kqDayDataShowIconCols.push(row.itemCode);
	 				colModelDatas.push(groupObj);
				}
	 		}else if(frequency && "kqItem" == showType){
	 			kqUpDataGridJson[row.itemCode] = {name:row.itemCode,type:"kqItem",sn:groupIndex++};
	 			var groupText = row.itemName;
	 			var shortName = row.shortName;
	 			kqItemShortNameJson[shortName] = {name:groupText,code:row.itemCode,frequency:frequency};
	 			colModelData["text"] = frequency;
 				var jsonLength = getJsonLength(colModelDatas);
 				var gropName = "group_" + jsonLength + "_group";
 				var groupObj = {name : groupText,cols : [colModelData],id:row.itemCode+"_group"};
 				if(editable){
 					groupObj["textColor"] = "#0000FF";
 				}
 				colModelDatas.push(groupObj);
 				kqMonthDataProofColums[row.itemCode] = frequency;
	 		}else{
	 			if(row.itemCode == "kqDeptName"){
	 				colModelData["droplistID"] = "deptList"; 
	 				colModelData["edittype"] = "editableDroptreelist"; 
	 			}
	 			var jsonLength = getJsonLength(colModelDatas);
				var ColName = "Col_" + jsonLength + "_Col";
				colModelDatas.push(colModelData);
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
		colModelDatas.push(colModelDataChecker);
		//colModelDatas.push(colModelDataChecker);
		jsonLength = getJsonLength(colModelDatas);
		colName = "Col_" + jsonLength + "_Col";
		colModelDatas.push(colModelDataCheckDate);
		//colModelDatas.push(colModelDataCheckDate);
		jsonLength = getJsonLength(colModelDatas);
		colName = "Col_" + jsonLength + "_Col";
		colModelDatas.push(colModelDataSubmiter);
		jsonLength = getJsonLength(colModelDatas);
		colName = "Col_" + jsonLength + "_Col";
		colModelDatas.push(colModelDataSubmitDate);
		return colModelDatas;
	}
		
	function kqDayDataGridTableload(reLoad) {
		jQuery.ajax({
			url : 'kqDayDataGridList',
			data : {columns:kqDayDataGridColumns,kqTypeId:kqTypeIdDD,curDeptId:curDeptId,
				curPeriod:curPeriod,lastPeriod:lastPeriod},
			type : 'post',
			dataType : 'json',
			async : true,
			error : function(data) {
			},
			success : function(data) {
				kqDayDatas = data.kqDayDataSets;
				var kqDayDataDetailStr = data.kqDayDataDetailStr;
				kqDayDataDetail = {};
				if(kqDayDataDetailStr){
					kqDayDataDetail = JSON.parse(kqDayDataDetailStr);
				}
				kqIdDDObj = {};
				if(kqDayDatas){
					for(var index in kqDayDatas){
						kqIdDDObj[kqDayDatas[index]["kqId"]] = index;
					}
				}
				kqDayDatasOld = cloneObj(kqDayDatas);
				var kqDayDataGridData = {};
				var checked = jQuery("#kqDayData_hideQinCell").attr("checked");
				if(checked == "checked"){
					var kqDayDatasHideQin = cloneObj(kqDayDatas);
					for(var index in kqDayDatasHideQin){
						var kqDayData = kqDayDatasHideQin[index];
						for(var column in kqDayData){
							if(kqDayData[column] === "勤"){
								kqDayData[column] = null;
							}
						}
					}
					kqDayDataGridData.Record = kqDayDatasHideQin;
				}else{
					kqDayDataGridData.Record = kqDayDatas;
				}
				if(reLoad){
					kqDayData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
					kqDayData_gridtable.func("load", JSON.stringify(kqDayDataGridData));
					kqDayData_gridtable.func("GrayWindow",'0');
					kqDayDataSearchFormReLoad();
				}else{
					kqDayDataGridDefine.main.Load = JSON.stringify(kqDayDataGridData);
					insertTreeListToDiv("kqDayData_gridtable_div","kqDayData_gridtable", "", "100%");
				}
			}
		});
	}
	
	//计算
	function kqDayDataCalculate(rowIndex,gridIndex){
		var kqDayData = kqDayDatas[rowIndex];
		if(!kqDayData){
			return;
		}
		if(kqDayDataCalColumns){
			for(var codeTempIndex in kqDayDataCalColumns){
				var codeTemp = kqDayDataCalColumns[codeTempIndex];
				kqDayData_gridtable.func("SetCellData", gridIndex +" \r\n "+codeTemp+" \r\n 0");
				kqDayDatas[rowIndex][codeTemp] = "0";
			}
		}
		if(kqDayDataFormulaJsonObj){
			var kqDayDataJson = cloneObj(kqDayDataFormulaJsonObj);
			var valueJson = cloneObj(kqDayDataContentValueJson);
// 			for(var codeTemp in valueJson){
// 				kqDayData_gridtable.func("SetCellData", gridIndex +" \r\n "+codeTemp+" \r\n 0");
// 				kqDayDatas[rowIndex][codeTemp] = "0";
// 			}
			while (getJsonLength(valueJson) > 0){
				for(var itemCode in kqDayDataJson){
					var	kqItemJson = kqDayDataJson[itemCode];
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
									var cpValue = kqDayData[pName];
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
										var rpValue = kqDayData[pName];
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
						            result = roundToFixed(numTemp,2);
						            kqDayData_gridtable.func("SetCellData", gridIndex +" \r\n "+itemCode+" \r\n"+result);
						            kqDayDatas[rowIndex][itemCode] = result;
						            var callBackItem = kqFormulaTempJson.callBackItem;
						            var callBackFormula = kqFormulaTempJson.callBackFormula;
						            if(callBackItem && callBackFormula){
						            	var callBackParameter = kqFormulaTempJson.callBackParameter;
						            	if(callBackParameter){
											for(var cbpIndex in callBackParameter){
												var pName = callBackParameter[cbpIndex].pName;
												var pDT = callBackParameter[cbpIndex].pDT;
												var cbpValue = kqDayData[pName];
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
								        kqDayData_gridtable.func("SetCellData", gridIndex +" \r\n "+callBackItem+" \r\n"+cbresult);
								        kqDayDatas[rowIndex][callBackItem] = cbresult;
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
	function kqDayDataCalculateNoUpdate(rowIndex,gridIndex,changedCells){
		var kqDayData = kqDayDatas[rowIndex];
		if(!kqDayData){
			return changedCells;
		}
		if(kqDayDataCalColumns){
			for(var codeTempIndex in kqDayDataCalColumns){
				var codeTemp = kqDayDataCalColumns[codeTempIndex];
				if(kqDayDatas[rowIndex][codeTemp] + "" !== "0"){
					kqDayDatas[rowIndex]["isEdit"] = '1';
					changedCells[changedCells.length] = {"row":gridIndex,"col":codeTemp};
				}
				kqDayDatas[rowIndex][codeTemp] = "0";
			}
		}
		if(kqDayDataFormulaJsonObj){
			var kqDayDataJson = cloneObj(kqDayDataFormulaJsonObj);
			var valueJson = cloneObj(kqDayDataContentValueJson);
// 			for(var codeTemp in valueJson){
// 		         kqDayDatas[rowIndex][codeTemp] = "0";
// 			}
				while (getJsonLength(valueJson) > 0){//列计算完毕跳出循环
					for(var itemCode in kqDayDataJson){//依次取出每一个需要计算的列code
						var	kqUpItemJson = kqDayDataJson[itemCode];//取出该列的公式集合
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
										var cpValue = kqDayData[pName];
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
											var rpValue = kqDayData[pName];
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
										//kqDayData_gridtable.func("SetCellData", gridIndex +" \r\n "+itemCode+" \r\n"+result);
							            var resultOld = kqDayDatas[rowIndex][itemCode];
							            if(resultOld != result){
							            	changedCells[changedCells.length]={"row":gridIndex,"col":itemCode};
							            	kqDayDatas[rowIndex][itemCode] = result;
							            	kqDayDatas[rowIndex]["isEdit"] = '1';
							            }
							            var callBackItem = kqUpFormulaTempJson.callBackItem;
							            var callBackFormula = kqUpFormulaTempJson.callBackFormula;
							            if(callBackItem && callBackFormula){
							            	var callBackParameter = kqUpFormulaTempJson.callBackParameter;
							            	if(callBackParameter){
												for(var cbpIndex in callBackParameter){
													var pName = callBackParameter[cbpIndex].pName;
													var pDT = callBackParameter[cbpIndex].pDT;
													var cbpValue = kqDayData[pName];
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
									        var cbresultOld = kqDayDatas[rowIndex][callBackItem];
								            if(cbresultOld != cbresult){
								            	 changedCells[changedCells.length]={"row":gridIndex,"col":callBackItem};
										         kqDayDatas[rowIndex][callBackItem] = cbresult;
										         kqDayDatas[rowIndex]["isEdit"] = '1';
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
	/*重新加载数据*/
	function kqDayDataGridTableReLoadData(){
		jQuery.ajax({
			url: 'kqDayDataGridList',
			data : {columns:kqDayDataGridColumns,kqTypeId:kqTypeIdDD,curDeptId:curDeptId,
				curPeriod:curPeriod,lastPeriod:lastPeriod},
			type: 'post',
			dataType: 'json',
			async:true,
			error: function(data){
			},
			success: function(data){
				kqDayDatas = data.kqDayDataSets;
				var kqDayDataDetailStr = data.kqDayDataDetailStr;
				kqDayDataDetail = {};
				if(kqDayDataDetailStr){
					kqDayDataDetail = JSON.parse(kqDayDataDetailStr);
				}
				kqIdDDObj = {};
				if(kqDayDatas){
					for(var index in kqDayDatas){
						kqIdDDObj[kqDayDatas[index]["kqId"]] = index;
					}
				}
				kqDayDatasOld = cloneObj(kqDayDatas);
				var kqDayDataGridData = {};
				var checked = jQuery("#kqDayData_hideQinCell").attr("checked");
				if(checked == "checked"){
					var kqDayDatasHideQin = cloneObj(kqDayDatas);
					for(var index in kqDayDatasHideQin){
						var kqDayData = kqDayDatasHideQin[index];
						for(var column in kqDayData){
							if(kqDayData[column] === "勤"){
								kqDayData[column] = null;
							}
						}
					}
					kqDayDataGridData.Record = kqDayDatasHideQin;
				}else{
					kqDayDataGridData.Record = kqDayDatas;
				}
				kqDayData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
				kqDayData_gridtable.func("load", JSON.stringify(kqDayDataGridData));
				if(!curCheckStatusDD){
					curCheckStatusDD = "0";
				}
				kqDayDataSearchFormReLoad();
    			kqDayData_gridtable.func("GrayWindow","0");//遮罩/还原的动作
			}
		});
	}
	/*查询*/
	function kqDayDataSearchFormReLoad(){
		var kqTypeIdTemp = jQuery("#kqDayData_kqType").val();
		if(kqTypeIdDD != kqTypeIdTemp){
			var kqDayDataChanged = kqDayData_gridtable.func("GetRowChanged", "-1\r\n NMD");
			if(kqDayDataChanged){
				alertMsg.error('请先保存当前数据后再切换考勤类别！');
				return;
			}
			kqTypeIdDD = kqTypeIdTemp;
			initKqDayDataGrid("reload");
		}else{
			var personName = jQuery("#kqDayData_personName").val();
			var empTypes = jQuery("#kqDayData_personType").val();
//	 		var disabled = jQuery("#kqDayData_disable").val();
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
			kqDayData_gridtable.func("Filter", filterStr);
		}
	}
	/*修改考勤*/
	function kqDayDataChangeCell(name){
		if(curPeriodStatus){
			alertMsg.error(curPeriodStatus);
			return;
 		}
		if(curCheckStatusDD === "1"){
			alertMsg.error("上报数据已审核！");
			return;
		}
		if(curCheckStatusDD === "2"){
			alertMsg.error("上报数据已提交！");
			return;
		}
		if(curCheckStatusDD === "3"){
			alertMsg.error("上报数据已通过！");
			return;
		}
		//var value = jQuery(obj).text();
		var value = name;
		var gridValue = name;
// 		var displayText = jQuery("#kqDayData_displayChange").text();
// 		if("文字" == displayText&&kqDayDataIconJson[name]){
// 			gridValue = kqDayDataIconJson[name];
// 		}
		var currentCells = kqDayData_gridtable.func("GetCurrentCell", "ColName");
		if(currentCells){
			var currentCellArr = currentCells.split(",");
			var startRow = currentCellArr[0];
			var startCol = currentCellArr[1];
			var endRow = currentCellArr[2];
			var endCol = currentCellArr[3];
			if(startCol.indexOf("day")!=0||endCol.indexOf("day")!=0){
				alertMsg.error('请选择可维护的区域！');
				return;
			}
			currentCells = kqDayData_gridtable.func("GetCurrentCell", "");
			currentCellArr = currentCells.split(",");
			startRow = +currentCellArr[0];
			startCol = +currentCellArr[1];
			endRow = +currentCellArr[2];
			endCol = +currentCellArr[3];
			kqDayData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
			for(var rowIndex = startRow;rowIndex <= endRow;rowIndex++){
				var kqIdTemp =  kqDayData_gridtable.func("GetCellData",rowIndex+"\r\n kqId");
				var rowIndexTemp = kqIdDDObj[kqIdTemp];
				if(rowIndexTemp){
					kqDayDatas[rowIndexTemp]["isEdit"] = '1';
				}
				for(var colIndex = startCol;colIndex <= endCol;colIndex++){
					kqDayData_gridtable.func("SetCellData",rowIndex+"\r\n"+colIndex+"\r\n"+gridValue);
					if(rowIndexTemp){
						var colName = kqDayData_gridtable.func("GetColProp",colIndex+"\r\n name");
						kqDayDatas[rowIndexTemp][colName] = value;
						if(kqDayDataDetail[kqIdTemp+"_"+colName]){
							kqDayDataDetail[kqIdTemp+"_"+colName] = [];
						}
					}
				}
				kqDayDataCalDays(rowIndexTemp,rowIndex);
			}
			kqDayData_gridtable.func("GrayWindow","0");//遮罩/还原的动作
		}else{
			alertMsg.error('请选择记录！');
			return;
		}
	}
	//计算某一行的出勤数据
	function kqDayDataCalDays(rowIndex,rowId){//rowIndex数据序号，rowId索引序号
		var kqDayDataTemp = kqDayDatas[rowIndex];
		var kqIdTemp = kqDayDataTemp["kqId"];
		var calJson = {};
		for(var colIndex in kqDayDataShowIconCols){
			var itemCode = kqDayDataShowIconCols[colIndex];
			var itemValue = kqDayDataTemp[itemCode];
			var detailTemp = kqDayDataDetail[kqIdTemp+"_"+itemCode];
			if(detailTemp && getJsonLength(detailTemp) > 0){
				for(var detailIndex in detailTemp){
					var detailNameTemp = detailTemp[detailIndex]["item"];
					var detailValueTemp = +detailTemp[detailIndex]["value"];
					var kqItemShortNameJsonTemp = kqItemShortNameJson[detailNameTemp];
					if(kqItemShortNameJsonTemp){
						var itemCodeUpdate = kqItemShortNameJsonTemp.code;
						if(calJson[itemCodeUpdate]){
							calJson[itemCodeUpdate] = calJson[itemCodeUpdate] + detailValueTemp;
						}else{
							calJson[itemCodeUpdate] = detailValueTemp;
						}
					}
				}
			}else{
				var kqItemShortNameJsonTemp = kqItemShortNameJson[itemValue];
				if(kqItemShortNameJsonTemp){
					var itemCodeUpdate = kqItemShortNameJsonTemp.code;
					if(calJson[itemCodeUpdate]){
						calJson[itemCodeUpdate] = calJson[itemCodeUpdate] + 1;
					}else{
						calJson[itemCodeUpdate] = 1;
					}
				}
			}
		}
		for(var itemKey in kqItemShortNameJson){
			var kqItemShortNameJsonTemp = kqItemShortNameJson[itemKey];
			var itemCode = kqItemShortNameJsonTemp.code;
			var itemValue = calJson[itemCode];
			if(!itemValue){
				itemValue = "";
			}
			kqDayDatas[rowIndex][itemCode] = itemValue;
			kqDayData_gridtable.func("SetCellData",rowId+"\r\n"+itemCode+"\r\n"+itemValue);
		}
		/* for(var itemCode in calJson){
			kqDayDatas[rowIndex][itemCode] = calJson[itemCode];
			kqDayData_gridtable.func("SetCellData",rowId+"\r\n"+itemCode+"\r\n"+calJson[itemCode]);
		} */
		kqDayDataCalculate(rowIndex,rowId);//计算
	}
	/*明细数据*/
	function kqDayDataDetailChange(){
		if(!kqDayDatas||kqDayDatas.length==0){
			alertMsg.error('无数据！');
			return;
		}
		var viewType = "";
		if(curCheckStatusDD === "0"||curCheckStatusDD === "4"){
			viewType = "all";
		}else{
			viewType = "one";
		}
		if(curPeriodStatus){
			viewType = "one";
 		}
// 		if(curCheckStatusDD === "1"){
// 			alertMsg.error("上报数据已审核！");
// 			return;
// 		}
// 		if(curCheckStatusDD === "2"){
// 			alertMsg.error("上报数据已汇总！");
// 			return;
// 		}
		var currentCells = kqDayData_gridtable.func("GetCurrentCell", "ColName");
		if(currentCells){
			var currentCellArr = currentCells.split(",");
			var startRow = currentCellArr[0];
			var startCol = currentCellArr[1];
			var endRow = currentCellArr[2];
			var endCol = currentCellArr[3];
			if(startCol.indexOf("day")!=0||endCol.indexOf("day")!=0){
				alertMsg.error('请选择天所在的区域！');
				return;
			}
			if(viewType == "one"){
				if(startRow != endRow || startCol != endCol){
					alertMsg.error('请选择一个单元格！');
					return;
				}
				var kqIdTemp =  kqDayData_gridtable.func("GetCellData",startRow+"\r\n kqId");
				var colName = kqDayData_gridtable.func("GetColProp",startCol+"\r\n name");
				var jsonKey = kqIdTemp + "_" + colName;
				if(!kqDayDataDetail[jsonKey] && getJsonLength(kqDayDataDetail[jsonKey]) == 0){
					alertMsg.error('此单元格无详细数据！');
					return;
				}
			}
			var winTitle='<s:text name="kqDayDataDeatil.title"/>';
			var url = "kqDayDataDeatilAdd?navTabId=kqDayData_gridtable&oper="+viewType;
			$.pdialog.open(url,'kqDayDataDeatilAdd',winTitle, {ifr:true,hasSupcan:"kqDayData_gridtable",mask:true,resizable:true,maxable:true,width : 450,height : 400});
		}else{
			alertMsg.error('请选择记录！');
			return;
		}
	}
	function displayChangeKqDayData(){
		if(!kqDayDatas||kqDayDatas.length==0){
			alertMsg.error('无数据需要修改！');
			return;
		}
		var displayText = jQuery("#kqDayData_displayChange").text();
		if("图片" == displayText){
			jQuery("#kqDayData_displayChange").text("文字");
			kqDayData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
			var gridRows = kqDayData_gridtable.func("GetRows","");
			if(kqDayDataShowIconCols&&gridRows>0){
				for(var colIndex in kqDayDataShowIconCols){
					var colTemp = kqDayDataShowIconCols[colIndex];
					for(var rowIndex = 0;rowIndex<gridRows;rowIndex++){
						var cellOldValue = kqDayData_gridtable.func("GetCellData",rowIndex+"\r\n"+colTemp);
						if(cellOldValue){
							var cellNewValue = "";
							if(cellOldValue.indexOf(",")>-1){
								var cellValues = cellOldValue.split(",");
								for(var cvIndex in cellValues){
									var cellValueTemp = cellValues[cvIndex];
									if(kqDayDataIconJson[cellValueTemp]){
										cellNewValue += kqDayDataIconJson[cellValueTemp]+";";
									}
								}
								if(cellNewValue){
									cellNewValue = cellNewValue.substring(0,cellNewValue.length-1);
								}
							}else{
								if(kqDayDataIconJson[cellOldValue]){
									cellNewValue = kqDayDataIconJson[cellOldValue];
								}
							}
							kqDayData_gridtable.func("SetCellData",rowIndex+"\r\n"+colTemp+"\r\n"+cellNewValue);
						}
							
					}
					kqDayData_gridtable.func("SetColProp",colTemp+"\r\n"+"isImage \r\n true");
				}
			}
			kqDayData_gridtable.func("GrayWindow",'0');//遮罩/还原的动作
		}else{
			jQuery("#kqDayData_displayChange").text("图片");
			var kqDayDataGridData = {};
			kqDayDataGridData.Record = kqDayDatas;
			kqDayData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
			var gridRows = kqDayData_gridtable.func("GetRows","");
			if(kqDayDataShowIconCols&&gridRows>0){
				for(var colIndex in kqDayDataShowIconCols){
					var colTemp = kqDayDataShowIconCols[colIndex];
					kqDayData_gridtable.func("SetColProp",colTemp+"\r\n"+"isImage \r\n false");
				}
			}
			kqDayData_gridtable.func("load", JSON.stringify(kqDayDataGridData));
//				kqDayDataSearchFormReLoad();
			kqDayData_gridtable.func("GrayWindow","0");//遮罩/还原的动作
		}
	}
	
	function initKqMenuButton(){
		/*----------------------------------tooBar start-----------------------------------------------*/
		var kqDayData_menuButtonArrJson = "${menuButtonArrJson}";
		var kqDayData_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(kqDayData_menuButtonArrJson,false)));
		var kqDayData_toolButtonBar = new ToolButtonBar({el:$('#kqDayData_toolbuttonbar'),collection:kqDayData_toolButtonCollection,attributes:{
			tableId : 'kqDayData_gridtable',
			baseName : 'kqDayData',
			width : 600,
			height : 600,
			base_URL : null,
			optId : null,
			fatherGrid : null,
			extraParam : null,
			selectNone : "请选择记录。",
			selectMore : "只能选择一条记录。",
			addTitle : '<s:text name="kqDayDataNew.title"/>',
			editTitle : null,
		}});
		if(curPeriodStatus){
			kqDayData_toolButtonBar.disabledAll();
		}else{
			kqDayData_toolButtonBar.enabledAll();
		}
		kqDayData_toolButtonBar.render()
		
		var kqDayData_function = new scriptFunction();
		kqDayData_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.periodStatusCheck){
				if(curPeriodStatus){
					alertMsg.error(curPeriodStatus);
					return pass;
				}
			}
			if(param.deptIdCheck){
				if(!curDeptId){
					alertMsg.error("当前权限下，无可操作的部门！");
					return pass;
				}
			}
			if(param.dataChangedCheck){
		 		var kqDayDataChanged = kqDayData_gridtable.func("GetRowChanged", "-1\r\n NMD");
		 		if(kqDayDataChanged){
		 			alertMsg.error('请先保存当前数据！');
		 			return pass;
		 		}
			}
			if(param.statusCheck){
				var statusCheck = param.statusCheck;
				var statusCheckArr = statusCheck.split(",");
				for(var index in statusCheckArr){
					if(statusCheckArr[index] == "0"||statusCheckArr[index] == "4"){
						if(curCheckStatusDD == "0"||curCheckStatusDD == "4"){
							alertMsg.error("上报数据未审核！");
							return pass;
						}
					}
					if(statusCheckArr[index] == "1"&&curCheckStatusDD == "1"){
						alertMsg.error("上报数据已审核！");
						return pass;
					}
					if(statusCheckArr[index] == "2"&&curCheckStatusDD == "2"){
						alertMsg.error("上报数据已提交！");
						return pass;
					}
					if(statusCheckArr[index] == "3"&&curCheckStatusDD == "3"){
						alertMsg.error("上报数据已通过！");
						return pass;
					}
				}
			}
			if(param.dataCheck){
				if(!kqDayDatas||kqDayDatas.length==0){
					alertMsg.error('无数据！');
					return pass;
				}
			}
	        return true;
		};
		//初始化
		kqDayData_toolButtonBar.addCallBody('1006040301',function(e,$this,param){
			alertMsg.confirm("确认初始化?", {
				okCall : function() {
					jQuery.ajax({
						url: 'refreshKqDayData',
						data: {curPeriod:curPeriod,lastPeriod:lastPeriod,kqTypeId:kqTypeIdDD,curDeptId:curDeptId,kqUpDataType:kqUpDataType},
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
								kqDayDataGridTableReLoadData();
							}
						}
					});
				}
			});
		});
		kqDayData_toolButtonBar.addBeforeCall('1006040301',function(e,$this,param){
			return kqDayData_function.optBeforeCall(e,$this,param);
		},{deptIdCheck:"deptIdCheck",dataChangedCheck:"dataChangedCheck",statusCheck:"1,2,3",periodStatusCheck:"periodStatusCheck"});
		//添加
		kqDayData_toolButtonBar.addCallBody('1006040302',function(e,$this,param){
			var winTitle='<s:text name="kqDayDataPersonList.title"/>';
			var url = "kqDayDataPersonAdd?navTabId=kqDayData_gridtable";
			$.pdialog.open(url,'kqDayDataPersonAdd',winTitle, {ifr:true,hasSupcan:"kqDayData_gridtable",mask:true,resizable:true,maxable:true,width : 700,height : 600});
		});
		kqDayData_toolButtonBar.addBeforeCall('1006040302',function(e,$this,param){
			return kqDayData_function.optBeforeCall(e,$this,param);
		},{deptIdCheck:"deptIdCheck",dataChangedCheck:"dataChangedCheck",statusCheck:"1,2,3",periodStatusCheck:"periodStatusCheck"});
		//删除
		kqDayData_toolButtonBar.addCallBody('1006040303',function(e,$this,param){
			var rowId = kqDayData_gridtable.func("GetCurrentRow",""); 
			if(rowId){
				var rowIds = rowId.split(",");
				var cellDataStr = "";
				for(var rowIndex = 0;rowIndex<rowIds.length;rowIndex++){
					var cellData = kqDayData_gridtable.func("GetCellData",rowIds[rowIndex] +"\r\n kqId"); 
					cellDataStr += cellData + ",";
				}
				cellDataStr = cellDataStr.substring(0,cellDataStr.length-1);
				 var url = "kqDayDataGridEdit?oper=del";
					alertMsg.confirm("确认删除?", {
						okCall : function() {
							$.post(url,{id:cellDataStr},function(data) {
								formCallBack(data);
								if(data.statusCode == 200){
									kqDayDataGridTableReLoadData();
								}
							});
						}
					});
			}else{
				alertMsg.error('请选择记录！');
				return;
			}
		});
		kqDayData_toolButtonBar.addBeforeCall('1006040303',function(e,$this,param){
			return kqDayData_function.optBeforeCall(e,$this,param);
		},{deptIdCheck:"deptIdCheck",dataChangedCheck:"dataChangedCheck",statusCheck:"1,2,3",periodStatusCheck:"periodStatusCheck"});
		//批量修改
		kqDayData_toolButtonBar.addCallBody('1006040304',function(e,$this,param){
			var winTitle='<s:text name="kqDayDataBatchEdit.title"/>';
			var url = "batchEditKqDayData?navTabId=kqDayData_gridtable&kqTypeId="+kqTypeIdDD;
			$.pdialog.open(url,'batchEditKqDayData',winTitle, {ifr:true,hasSupcan:"kqDayData_gridtable",mask:true,resizable:false,maxable:false,width : 700,height : 550});
		});
		kqDayData_toolButtonBar.addBeforeCall('1006040304',function(e,$this,param){
			return kqDayData_function.optBeforeCall(e,$this,param);
		},{deptIdCheck:"deptIdCheck",statusCheck:"1,2,3",dataCheck:"dataCheck",periodStatusCheck:"periodStatusCheck"});
		//重新计算
		kqDayData_toolButtonBar.addCallBody('1006040305',function(e,$this,param){
			alertMsg.confirm("确认重新计算?", {
				okCall : function() {
					openProgressBar('reComputeGz',"重新计算中，请稍等...",true);
					setTimeout(function(){
						kqDayData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
						var rows = kqDayData_gridtable.func("getRows", "");
						var changedCells = [];
						if(rows > 0){
							for(var gridIndex = 0;gridIndex < rows;gridIndex++){
								var kqIdTemp = kqDayData_gridtable.func("GetCellData",gridIndex+"\r\n kqId");
								var rowIndex = kqIdDDObj[kqIdTemp];
								changedCells = kqDayDataCalculateNoUpdate(rowIndex,gridIndex,changedCells);
							}
						}
						var kqDayDataGridData = {};
						kqDayDataGridData.Record = kqDayDatas;
						kqDayData_gridtable.func("load", JSON.stringify(kqDayDataGridData));
						kqDayDataSearchFormReLoad();
			    	if(changedCells&&getJsonLength(changedCells)>0){
			    		//kqDayData_gridtable.func("GrayWindow","1");//遮罩/还原的动作
						jQuery.each(changedCells,function(index,changedCell){
							kqDayData_gridtable.func("SetCellChanged",changedCell.row+"\r\n"+changedCell.col+"\r\n M");
						});
					}
			    	kqDayData_gridtable.func("GrayWindow","0");//遮罩/还原的动作
			    	closeProgressBar('reComputeGz',null,true)
			    	alertMsg.correct('重新计算成功！');
					},500);
				}
			});
		});
		kqDayData_toolButtonBar.addBeforeCall('1006040305',function(e,$this,param){
			return kqDayData_function.optBeforeCall(e,$this,param);
		},{deptIdCheck:"deptIdCheck",statusCheck:"1,2,3",dataCheck:"dataCheck",periodStatusCheck:"periodStatusCheck"});
		//保存
		kqDayData_toolButtonBar.addCallBody('1006040306',function(e,$this,param){
			var kqDayDataStr = "";
			for(var index in kqDayDatas){
				var kqDayData = kqDayDatas[index];
				var kqDayDataOld = kqDayDatasOld[index];
				var kqDayDataStrTemp = '';
				if(kqDayData["isEdit"]=='1'){
					jQuery.each(needSaveColumnsDD,function(key,value){
						var oldValue = kqDayDataOld[value];
						var newValue = kqDayData[value];
						var valueTemp;
						if((value!="kqId"&&oldValue!=newValue&&newValue!=="null")||value=="kqId"){
							valueTemp = newValue;
						}
						if(valueTemp !== undefined){
							kqDayDataStrTemp += '"'+ key +'":' + '"'+ valueTemp +'",';
						}
					});
				}
				if(kqDayDataStrTemp){
					kqDayDataStrTemp = kqDayDataStrTemp.substring(0,kqDayDataStrTemp.length-1);
					kqDayDataStrTemp = '{' + kqDayDataStrTemp + '}';
					kqDayDataStr += kqDayDataStrTemp +",";
				}
			}
			if(kqDayDataStr){
				kqDayDataStr = kqDayDataStr.substring(0,kqDayDataStr.length-1);
				kqDayDataStr = '[' + kqDayDataStr + ']';
			}
			var needSaveColumn = "";
			jQuery.each(needSaveColumnsDD,function(key,value){
				needSaveColumn += value + ",";
			});
			needSaveColumn = needSaveColumn.substring(0,needSaveColumn.length-1);
			var kqDayDataDetailJson = [];
			if(kqDayDataDetail){
				for(var keyTemp in kqDayDataDetail){
					var detailTemp = kqDayDataDetail[keyTemp];
					if(detailTemp){
						for(var indexTemp in detailTemp){
							kqDayDataDetailJson.push(detailTemp[indexTemp]);
						}
					}
				}
			}
			var kqDayDataDetailStr = JSON.stringify(kqDayDataDetailJson);
			jQuery.ajax({
		    	url: 'saveKqDayData',
		        data: {kqDayDataStr:kqDayDataStr,needSaveColumn:needSaveColumn
		        	,curPeriod:curPeriod,curDeptId:curDeptId,kqTypeId:kqTypeIdDD
		        	,kqDayDataDetailStr:kqDayDataDetailStr},
		        type: 'post',
		        dataType: 'json',
		        async:false,
		        error: function(data){
		        	alertMsg.error('系统错误！');
		        },
		        success: function(data){
		        	if(data.statusCode == 200){
		        		kqDayDatasOld = cloneObj(kqDayDatas);
		        		kqDayDataGridTableReLoadData();
		        		alertMsg.correct('保存成功！');
		        	}else{
		        		alertMsg.error('系统错误！');
		        	}
		        }
		    });
		});
		kqDayData_toolButtonBar.addBeforeCall('1006040306',function(e,$this,param){
			return kqDayData_function.optBeforeCall(e,$this,param);
		},{deptIdCheck:"deptIdCheck",statusCheck:"1,2,3",dataCheck:"dataCheck",periodStatusCheck:"periodStatusCheck"});
		//检查
		kqDayData_toolButtonBar.addCallBody('1006040307',function(e,$this,param){
			proofKqDayData();
		});
		kqDayData_toolButtonBar.addBeforeCall('1006040307',function(e,$this,param){
			return kqDayData_function.optBeforeCall(e,$this,param);
		},{deptIdCheck:"deptIdCheck",statusCheck:"1,2,3",dataCheck:"dataCheck",periodStatusCheck:"periodStatusCheck"});
		//审核
		kqDayData_toolButtonBar.addCallBody('1006040308',function(e,$this,param){
			if(proofKqDayData("reLoad") === false){
				return;
			}
			alertMsg.confirm("确认审核?", {
				okCall : function() {
					jQuery.ajax({
				    	url: 'kqDayDataGridEdit',
				    	data: {curPeriod:curPeriod,curDeptId:curDeptId,oper:"check",kqTypeId:kqTypeIdDD},
				        type: 'post',
				        dataType: 'json',
				        async:false,
				        error: function(data){
				        	alertMsg.error('系统错误！');
				        },
				        success: function(data){
				        	if(data.statusCode == 200){
				        		if(editAbleColumnsDD){
				        			jQuery.each(editAbleColumnsDD,function(key,value){
				        				kqDayData_gridtable.func("SetColEditAble",value+" \r\n false");
				        				kqDayData_gridtable.func("SetColProp", value+" \r\n headerTextColor \r\n #000000");
				        				kqDayData_gridtable.func("SetGroupProp", value+"_group"+" \r\n textColor \r\n #000000");
				        			});
				        		}
				        		curCheckStatusDD = "1";
					    		jQuery("#kqDayData_checkFlag").show();
					    		jQuery("#kqDayData_checkFlagContent").text("已审核");
				        		kqDayDataGridTableReLoadData();
				        		alertMsg.correct('审核成功！');
				        	}else{
				        		alertMsg.error(data.message);
				        	}
				        }
				    });
				}
			});
		});
		kqDayData_toolButtonBar.addBeforeCall('1006040308',function(e,$this,param){
			return kqDayData_function.optBeforeCall(e,$this,param);
		},{deptIdCheck:"deptIdCheck",statusCheck:"1,2,3",dataCheck:"dataCheck",dataChangedCheck:"dataChangedCheck",periodStatusCheck:"periodStatusCheck"});
		//销审
		kqDayData_toolButtonBar.addCallBody('1006040309',function(e,$this,param){
			alertMsg.confirm("确认销审?", {
				okCall : function() {
					jQuery.ajax({
				    	url: 'kqDayDataGridEdit',
				        data: {curPeriod:curPeriod,curDeptId:curDeptId,oper:"cancelCheck",kqTypeId:kqTypeIdDD},
				        type: 'post',
				        dataType: 'json',
				        async:false,
				        error: function(data){
				        	alertMsg.error('系统错误！');
				        },
				        success: function(data){
				        	if(data.statusCode == 200){
				        		if(editAbleColumnsDD){
				        			jQuery.each(editAbleColumnsDD,function(key,value){
				        				kqDayData_gridtable.func("SetColEditAble",value+" \r\n true");
				        				kqDayData_gridtable.func("SetColProp", value+" \r\n headerTextColor \r\n #0000FF");
				        				kqDayData_gridtable.func("SetGroupProp", value+"_group"+" \r\n textColor \r\n #0000FF");
				        			});
				        		}
				        		curCheckStatusDD = "0";
				        		jQuery("#kqDayData_checkFlag").hide();
				        		kqDayDataGridTableReLoadData();
				        		alertMsg.correct('销审成功！');
				        	}else{
				        		alertMsg.error(data.message);
				        	}
				        }
				    });
				}
			});
		});
		kqDayData_toolButtonBar.addBeforeCall('1006040309',function(e,$this,param){
			return kqDayData_function.optBeforeCall(e,$this,param);
		},{deptIdCheck:"deptIdCheck",statusCheck:"0,2,4",dataCheck:"dataCheck",periodStatusCheck:"periodStatusCheck"});
//	 	//提交
		kqDayData_toolButtonBar.addCallBody('1006040310',function(e,$this,param){
			alertMsg.confirm("确认提交?", {
				okCall : function() {
					jQuery.ajax({
				    	url: 'kqDayDataGridEdit',
				        data: {curPeriod:curPeriod,curDeptId:curDeptId,oper:"submit",kqTypeId:kqTypeIdDD},
				        type: 'post',
				        dataType: 'json',
				        async:false,
				        error: function(data){
				        	alertMsg.error('系统错误！');
				        },
				        success: function(data){
				        	if(data.statusCode == 200){
				        		curCheckStatusDD = "2";
				        		jQuery("#kqDayData_checkFlagContent").text("已提交");
				        		kqDayDataGridTableReLoadData();
				        		alertMsg.correct('提交成功！');
				        	}else{
				        		alertMsg.error(data.message);
				        	}
				        }
				    });
				}
			});
		});
		kqDayData_toolButtonBar.addBeforeCall('1006040310',function(e,$this,param){
			return kqDayData_function.optBeforeCall(e,$this,param);
		},{deptIdCheck:"deptIdCheck",statusCheck:"0,2,3,4",dataCheck:"dataCheck",periodStatusCheck:"periodStatusCheck"});
		//格式
		var kqDayData_setColShowButton = {id:'1006040311',buttonLabel:'格式',className:"settlebutton",show:true,enable:true,
				callBody:function(){
					var url = "colShowTemplForm?entityName=com.huge.ihos.kq.kqUpData.model.KqDayData";
					url += "&navTabId=kqDayData_gridtable&oper=supcan";
					url += "&colshowType=" + kqUpDataType;
					url = encodeURI(url);
					$.pdialog.open(url, 'setColShow', "格式", {
						ifr:true,hasSupcan:"kqDayData_gridtable",mask:true,resizable:false,maxable:false,
						width : 500,
						height : 300
					});
				}};
		kqDayData_toolButtonBar.addButton(kqDayData_setColShowButton);
		kqDayData_toolButtonBar.addBeforeCall('1006040311',function(e,$this,param){
			return kqDayData_function.optBeforeCall(e,$this,param);
		},{});
		/*----------------------------------tooBar end-----------------------------------------------*/
	}
	
	//检查
	function proofKqDayData(reLoad){
		var kqItemsDDJson = {};
		if(kqItemsDD){
			for(var index in kqItemsDD){
				var nameTemp = kqItemsDD[index]["shortName"];
				kqItemsDDJson[nameTemp] = kqItemsDD[index];
			}
		}
		var errorMessage = "";
		//日上报检查
		if(kqUpDataType != "1"){
			var curPeriodWeekObj = {};
			if(curPeriodWeek){
				for(var index in curPeriodWeek){
					var curPeriodWeekTemp = curPeriodWeek[index];
					curPeriodWeekObj[curPeriodWeekTemp["code"]] = curPeriodWeekTemp;
				}
			}
			var kqDayDataProofJson = {};//31天每天的天数为1检查
			var kqDeptNameProofJson = {};//出勤部门检查
			if(kqDayDatas){
				for(var indexTemp in kqDayDatas){
					var kqDayData = kqDayDatas[indexTemp];
					var kqDeptNameTemp = kqDayData["kqDeptName"];
					var personIdTemp = kqDayData["personId"];
					if(kqDeptNameProofJson[personIdTemp]){
						var proofObjTemp = kqDeptNameProofJson[personIdTemp]["proofObj"];
						if(proofObjTemp[kqDeptNameTemp]){
							proofObjTemp[kqDeptNameTemp] += 1;
						}else{
							proofObjTemp[kqDeptNameTemp] = 1;
						}
					}else{
						kqDeptNameProofJson[personIdTemp] = {deptId:kqDayData["deptId"],deptCode:kqDayData["deptCode"],
								deptName:kqDayData["deptName"],personId:kqDayData["personId"],personCode:kqDayData["personCode"],
								personName:kqDayData["personName"],proofObj:{}};
						kqDeptNameProofJson[personIdTemp].proofObj[kqDeptNameTemp] = 1;
					}
					var kqId = kqDayData["kqId"];
					for(var column in curPeriodWeekObj){
						var detailKey = kqId + "_" + column;
						var frequencyCount = 0;
						if(kqDayDataDetail[detailKey]){
							var kqDayDataDetailTemp = kqDayDataDetail[detailKey];
							for(var detailIndex in kqDayDataDetailTemp){
								var item = kqDayDataDetailTemp[detailIndex]["item"];
								var value = kqDayDataDetailTemp[detailIndex]["value"];
								var frequency = kqItemsDDJson[item]["frequency"] + "";
								if(frequency === "0"){
									frequencyCount += +value;
								}
							}
						}else{
							var item = kqDayData[column];
							if(kqItemsDDJson[item]){
								var frequency = kqItemsDDJson[item]["frequency"] + "";
								if(frequency === "0"){
									frequencyCount += 1;
								}
							}
						}
						var proofKey = kqDayData["personId"] +"_"+column;
						if(kqDayDataProofJson[proofKey]){
							frequencyCount += kqDayDataProofJson[proofKey]["frequencyCount"];
							kqDayDataProofJson[proofKey] = {deptId:kqDayData["deptId"],deptCode:kqDayData["deptCode"],
									deptName:kqDayData["deptName"],personId:kqDayData["personId"],personCode:kqDayData["personCode"],
									personName:kqDayData["personName"],frequencyCount:frequencyCount,column:column};
						}else{
							kqDayDataProofJson[proofKey] = {deptId:kqDayData["deptId"],deptCode:kqDayData["deptCode"],
									deptName:kqDayData["deptName"],personId:kqDayData["personId"],personCode:kqDayData["personCode"],
									personName:kqDayData["personName"],frequencyCount:frequencyCount,column:column};
						}
					}
				}
			}
			var errorMessageJson = {};
			if(kqDayDataProofJson){
				for(var index in kqDayDataProofJson){
					var frequencyCount = kqDayDataProofJson[index]["frequencyCount"];
					if(frequencyCount != 1){
						var deptName = kqDayDataProofJson[index]["deptName"];
						var personName = kqDayDataProofJson[index]["personName"];
						var colTitle = kqDayData_gridtable.func("GetColTitle",kqDayDataProofJson[index]["column"]);
						var errorMessageJsonKey = "部门["+deptName+"]中人员["+personName+"]";
						if(errorMessageJson[errorMessageJsonKey]){
							colTitle = errorMessageJson[errorMessageJsonKey] + "," + colTitle;
							errorMessageJson[errorMessageJsonKey] = colTitle;
						}else{
							errorMessageJson[errorMessageJsonKey] = colTitle;
						}
					}
				}
			}
			if(errorMessageJson){
				for(var errorMessageJsonKey in errorMessageJson){
					errorMessage += errorMessageJsonKey + "数据列[" + errorMessageJson[errorMessageJsonKey] +"]";
				}
				if(errorMessage){
					errorMessage += "天数不为1！"
				}
			}
			errorMessageJson = {};
			for(var personIdTemp in kqDeptNameProofJson){
				var proofObjTemp = kqDeptNameProofJson[personIdTemp].proofObj;
				var deptName = kqDeptNameProofJson[personIdTemp].deptName;
				var personName = kqDeptNameProofJson[personIdTemp].personName;
				for(var kqDeptNameTemp in proofObjTemp){
					if(proofObjTemp[kqDeptNameTemp] > 1){
						var errorMessageJsonKey = "部门["+deptName+"]中人员["+personName+"]";
						var colTitle = kqDeptNameTemp;
						if(errorMessageJson[errorMessageJsonKey]){
							colTitle = errorMessageJson[errorMessageJsonKey] + "," + colTitle;
							errorMessageJson[errorMessageJsonKey] = colTitle;
						}else{
							errorMessageJson[errorMessageJsonKey] = colTitle;
						}
					}
				}
			}
			if(errorMessageJson){
				var errorMessageTemp = "";
				for(var errorMessageJsonKey in errorMessageJson){
					errorMessageTemp += errorMessageJsonKey + "的出勤部门[" + errorMessageJson[errorMessageJsonKey] +"]";
				}
				if(errorMessageTemp){
					errorMessage += errorMessageTemp += "重复！"
				}
			}
		}
		//月上报检查
		if(kqUpDataType == "1"){
			var periodDayCount = +"${periodDayCount}";
			var kqMonthDataProofJson = {};
			if(kqDayDatas){
				for(var indexTemp in kqDayDatas){
					var kqDayData = kqDayDatas[indexTemp];
					for(var column in kqMonthDataProofColums){
						if(kqMonthDataProofColums[column] != "天"){
							continue;
						}
						var frequencyCount = kqDayData[column];
						if(isNaN(frequencyCount)){
							frequencyCount = 0;
						}
						frequencyCount = +frequencyCount;
						var proofKey = kqDayData["personId"];
						if(kqMonthDataProofJson[proofKey]){
							frequencyCount += kqMonthDataProofJson[proofKey]["frequencyCount"];
							kqMonthDataProofJson[proofKey] = {deptId:kqDayData["deptId"],deptCode:kqDayData["deptCode"],
									deptName:kqDayData["deptName"],personId:kqDayData["personId"],personCode:kqDayData["personCode"],
									personName:kqDayData["personName"],frequencyCount:frequencyCount};
						}else{
							kqMonthDataProofJson[proofKey] = {deptId:kqDayData["deptId"],deptCode:kqDayData["deptCode"],
									deptName:kqDayData["deptName"],personId:kqDayData["personId"],personCode:kqDayData["personCode"],
									personName:kqDayData["personName"],frequencyCount:frequencyCount};
						}
					}
				}
			}
			if(kqMonthDataProofJson){
				for(var index in kqMonthDataProofJson){
					var frequencyCount = kqMonthDataProofJson[index]["frequencyCount"];
					if(frequencyCount != periodDayCount){
						var deptName = kqMonthDataProofJson[index]["deptName"];
						var personName = kqMonthDataProofJson[index]["personName"];
						errorMessage += "部门["+deptName+"]中人员["+personName+"]";
					}
				}
				if(errorMessage){
					errorMessage += "天数合计不为"+periodDayCount+"！"
				}
			}
		}
		if(errorMessage){
			if(reLoad){
				alertMsg.error(errorMessage);
				return false;
			}else{
				alertMsg.error(errorMessage);
				return;
			}
		}else{
			if(reLoad){
				return true;
			}else{
				alertMsg.correct('检查成功。');
				return;
			}
		}
	}
	//展开折叠修改
	function kqDayDataDetailChange(obj){
		var text = jQuery(obj).text();
		var filterAreaH = 0 , buttonBarAreaH = 0 ; 
		filterAreaH = jQuery("#kqDayData_pageHeader").outerHeight();
		buttonBarAreaH = jQuery("#kqDayData_buttonBar").outerHeight();
		var workAreaHeight = (jQuery("#container").innerHeight() - jQuery("div.tabsPageHeader").outerHeight() - filterAreaH - buttonBarAreaH);
		var appendixHeight = 80;
		var gridHeight = workAreaHeight - 4 - appendixHeight;
		if("展开" == text){
			containerHeight = containerHeight - 108;
			jQuery("#kqDayData_gridtable_div").height(1);
			jQuery("#kqDayData_tabsContent").height(workAreaHeight - 4 - 20);
			text = "折叠";
		}else{
			jQuery("#kqDayData_gridtable_div").height(workAreaHeight - 4 - appendixHeight);
			jQuery("#kqDayData_tabsContent").height(50);
			text = "展开";
		}
		jQuery(obj).text(text);
	}
	//考勤上报格式调整
	function kqDayDataColSetting(grid,kqCustomLayout){
		var customDom = grid.func("DOM_new",kqCustomLayout);
		var colsDom = grid.func("DOM_Find",customDom + "\r\n Cols");
		var colDom = grid.func("DOM_Find",colsDom + "\r\n Col");
		var gridColJson = {};
		if(colDom){
			var dayCount = 0;
			var colDomArr = colDom.split(",");
			var daySn = 0;
			var colIndex = 0;
			for(var index in colDomArr){
				var colId = grid.func("DOM_GetProp",colDomArr[index] + "\r\n id");
				if(colId.indexOf("day") == 0){
					if(!kqUpDataGridJson[colId]){
						grid.func("DOM_Delete",colDomArr[index]);
						continue;
					}
				}
				if(colId.indexOf("kqItem") == 0){
					if(!kqUpDataGridJson[colId]){
						grid.func("DOM_Delete",colDomArr[index]);
						continue;
					}
				}
				gridColJson[colId] = {name:colId,domId:colDomArr[index],sn:colIndex++,newSn:null};
			}
		}
		var gridIndex = 0;
		for(var itemCode in kqUpDataGridJson){
			if(!gridColJson[itemCode]){
				var colXmlTemp = '<Col id="'+itemCode+'" width="80" isHide="false"/>';
				var colDomTemp = grid.func("DOM_new",colXmlTemp);
				gridColJson[itemCode] = {name:itemCode,domId:colDomTemp,sn:null,newSn:gridIndex++};
			}else{
				var domId = gridColJson[itemCode].domId;
				var newDomId = grid.func("DOM_Clone",domId);
				gridColJson[itemCode].domId = newDomId;
				gridColJson[itemCode].newSn = gridIndex++;
			}
		}
		var colsDomArr = [];
		var addSn = 0;
		var gridColGroupJson = [];
		var indexSn = 0;
		var groupFlag = false;
		for(var itemCode in gridColJson){
			var gridColTemp = gridColJson[itemCode];
			var sn = gridColTemp.sn;
			var newSn = gridColTemp.newSn;
			var domId = gridColTemp.domId;
			if(newSn !== null){
				groupFlag = true;
				addSn = newSn;
				gridColGroupJson[newSn] = gridColTemp;
			}else if(sn !== null){
				if(groupFlag){
					addSn ++;
				}
				sn = indexSn + addSn;
				colsDomArr[sn] = domId;
				indexSn ++;
				groupFlag = false;
			}
		}
		var newSn = 0;
		for(var index = 0;index<colsDomArr.length;index++){
			if(!colsDomArr[index]){
				if(gridColGroupJson[newSn]){
					colsDomArr[index] = gridColGroupJson[newSn].domId;
					delete gridColGroupJson[newSn];
				}
				newSn ++;
			}
		}
		var length = colsDomArr.length;
		for(var index in gridColGroupJson){
			if(gridColGroupJson[index]){
				colsDomArr[length++] = gridColGroupJson[newSn].domId;
			}
		}
		var newcolsXml = "<Cols></Cols>";
		var newColsDom = grid.func("DOM_new",newcolsXml);
		for(var index in colsDomArr){
			grid.func("DOM_InsertChild",newColsDom + "\r\n -1 \r\n" + colsDomArr[index]);
		}
		grid.func("DOM_Delete",colsDom);
		grid.func("DOM_InsertChild",customDom + "\r\n -1 \r\n" + newColsDom);
		kqCustomLayout = grid.func("DOM_Export",customDom);
		grid.func("DOM_Delete",customDom);
		return kqCustomLayout;
	}
</script>

<div class="page" id="kqDayData_page">
	<div id="kqDayData_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
			<input type="hidden" id="kqUpData_mpPeriodChange" value='<s:property value="mpPeriodChange" escapeHtml="false"/>'>
			<input type="hidden" id="kqUpData_kqCustomLayout" value='<s:property value="kqCustomLayout" escapeHtml="false"/>'>
			<input type="hidden" id="kqDayData_curPeriodWeek" value='<s:property value="curPeriodWeek" escapeHtml="false"/>'>
				<form id="kqDayData_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='kqDayData.kqType'/>:
						<select id="kqDayData_kqType">
							<c:forEach var="kqType" items="${kqTypes}">
								<option value="${kqType.kqTypeId}">${kqType.kqTypeName}</option>
								</c:forEach>
						</select>
				    </label>
				    <label class="queryarea-label" style="${herpType=='S2'?'':'display:none'}">
       					<s:text name='kqDayData.branch'/>:
       					<s:select list="branchs" listKey="branchCode" listValue="BranchName" headerKey="" headerValue="--"></s:select>
					 </label>
				 	<label class="queryarea-label">
       					<s:text name='kqDayData.personName'/>:
      					<input type="text" id="kqDayData_personName" name="personName" style="width:120px"/>
					 </label>
					 <label class="queryarea-label">
       					<s:text name='kqDayData.empType'/>:
      					<input type="text" id="kqDayData_personType" name="empTypes" style="width:120px"/>
					 	<input type="hidden" id="kqDayData_personType_id"  />
					 </label>
 					<label class="queryarea-label" style="display: none;" id="kqDayData_hideQinCell_label">
							<input type="checkbox" id="kqDayData_hideQinCell" />勤不显示
					</label>
					<div class="buttonActive" style="float: right">
						<div class="buttonContent">
							<button type="button" onclick="kqDayDataSearchFormReLoad()">
								<s:text name='button.search' />
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="pageContent" id="kqDayData_pageContent">
		<div class="panelBar" id="kqDayData_buttonBar">
			<ul class="toolBar" id="kqDayData_toolbuttonbar">
<!-- 				<li> -->
<!-- 					<a class="addbutton" href="javaScript:refreshKqDayData()" ><span>初始化</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="addbutton" href="javaScript:addKqDayData()" ><span>添加</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="delbutton" href="javaScript:deleteKqDayData()" ><span>删除</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="zbcomputebutton" href="javaScript:batchEditKqDayData()" ><span>批量修改</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="regetdatabutton" href="javaScript:calculateEditKqDayData()" ><span>重新计算</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="savebutton" href="javaScript:saveKqDayData()" ><span>保存</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="settingbutton" href="javaScript:proofKqDayData()" ><span>检查</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="checkbutton" href="javaScript:checkKqDayData()" ><span>审核</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="delallbutton" href="javaScript:cancelCheckKqDayData()" ><span>销审</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="confirmbutton" href="javaScript:submitKqDayData()" ><span>提交</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="confirmbutton" href="javaScript:customLayOutKqDayData()" ><span>格式</span></a> -->
<!-- 				</li> -->
			</ul>
		</div>
		<div style="margin-top:-20px;margin-right:5px;float:right;display: none;" id="kqDayData_checkFlag">
			<font style="color:red;" id="kqDayData_checkFlagContent">已审核</font>
		</div>
		<div id="kqDayData_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<div id="load_kqDayData_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
		</div>
		<div class="tabs" style="margin-top: 1px;">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li id="kqDayData_gridtable_buttonLi"><a><span>出勤类别</span></a></li>
						<li><a><span>人员变动信息</span></a></li>
					</ul>
				</div>
			</div>
			<div style="margin-top: -20px;float: right;margin-right: 5px;"><a style="cursor: pointer;" onclick="kqDayDataDetailChange(this)">展开</a></div>
			<div class="tabsContent" id="kqDayData_tabsContent" style="height: 50px;">
				<div id="kqDayData_gridtable_buttonDetail">
					<button type="button" style="cursor: pointer;" onclick="kqDayDataDetailChange()">明细数据</button>
				</div>
				<div>
					<div id="kqDayData_mpDetail" style="overflow: auto;margin: 5px;"></div>
				</div>
			</div>
		</div>
		
	</div>
</div>