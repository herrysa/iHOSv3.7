<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script>
var gzAccountPlanReportDefine = {
	key:"gzAccountPlanReport_gridtable",
	main:{
		Build : '',
		Load :''
	},
	event:{
		//加载完毕
		"Load":function( id,p1, p2, p3, p4){//合计行加颜色与转换字体
			var grid = eval("("+id+")");
			if("${gzAccountDefine.defineId}" == "05" || "${gzAccountDefine.defineId}" == "06" ){
				var sumRowId = grid.func("find", "period=合计");
				if(sumRowId != -1){
					var cols = grid.func("GetCols","");
					cols = +cols;
					for(var colIndex = 0;colIndex < cols;colIndex ++){
						grid.func("SetCellBackColor",sumRowId+"\r\n"+colIndex+"\r\n#dfeffc");
						grid.func("SetCellFontIndex",sumRowId+"\r\n"+colIndex+"\r\n1");
					}
				}
			}
			//打印设置
			var printer = jQuery("#${random}_accountBean_printer").val();
			var paperNumber = jQuery("#${random}_accountBean_paperNumber").val();
			var oriantation = jQuery("#${random}_accountBean_oriantation").val();
			var scale = jQuery("#${random}_accountBean_scale").val();
			
			var printXml = grid.func("GetProp", "Print");
			var printDOM = grid.func("dom_new", printXml);
			//打印机设置
// 			var printer = grid.func("dom_find", printDOM + "\r\n printer");
// 			if(!printer){
// 				printer = grid.func("dom_new", "");
// 				grid.func("dom_setName", printer + "\r\n printer");
// 				grid.func("dom_insertChild", printDOM + "\r\n 0 \r\n" + printer);
// 			}
// 			grid.func("dom_setProp", printer + "\r\n #text \r\n " + "Fax"); //设定text
			/*打印纸及相关配置*/
			var paper = grid.func("dom_find", printDOM + "\r\n paper");
			if(!paper){
				paper = grid.func("dom_new", "");
				grid.func("dom_setName", paper + "\r\n paper");
				grid.func("dom_insertChild", printDOM + "\r\n 0 \r\n" + paper);
			}
			if(paperNumber){
				grid.func("DOM_SetProp", paper + "\r\n paperNumber \r\n "+paperNumber); 
			}
			if(oriantation){
				grid.func("DOM_SetProp", paper + "\r\n oriantation \r\n "+oriantation); 
			}
			if(scale){
				grid.func("DOM_SetProp", paper + "\r\n scale \r\n "+scale); 
			}
			if("${gzAccountDefine.defineId}" != "02"){
				/*页眉*/
				var header = grid.func("dom_find", printDOM + "\r\n header");
				if(!header){
					header = grid.func("dom_new", "");
					grid.func("dom_setName", header + "\r\n header");
					grid.func("dom_insertChild", printDOM + "\r\n 0 \r\n" + header);
				}
				//工资类别
				var layerDom;
				if("${gzAccountDefine.defineId}" != "09"){
					layerDom = grid.func("dom_new", ""); //创建一个空的DOM元素
					grid.func("dom_setName", layerDom + "\r\n layer"); //设定元素名
					grid.func("DOM_SetProp", layerDom + "\r\n y \r\n 50"); 
					grid.func("DOM_SetProp", layerDom + "\r\n align \r\n left"); 
					var gzTypeName = jQuery("#${random}_curGzType_gzTypeName").find("option:selected").text();
					grid.func("dom_setProp", layerDom + "\r\n #text \r\n 工资类别："+gzTypeName); //设定text
					grid.func("dom_insertChild", header + "\r\n -1 \r\n" + layerDom);
				}
				/*页脚*/
				var footer = grid.func("dom_find", printDOM + "\r\n footer");
				if(!footer){
					footer = grid.func("dom_new", "");
					grid.func("dom_setName", footer + "\r\n footer");
					grid.func("dom_insertChild", printDOM + "\r\n -1 \r\n" + footer);
				}else{
					grid.func("DOM_Delete", footer);
					footer = grid.func("dom_new", "");
					grid.func("dom_setName", footer + "\r\n footer");
					grid.func("dom_insertChild", printDOM + "\r\n -1 \r\n" + footer);
				}
				layerDom = grid.func("dom_new", ""); //创建一个空的DOM元素
				grid.func("dom_setName", layerDom + "\r\n layer"); //设定元素名
				grid.func("DOM_SetProp", layerDom + "\r\n y \r\n 0");
				grid.func("DOM_SetProp", layerDom + "\r\n align \r\n left"); 
				grid.func("dom_setProp", layerDom + "\r\n #text \r\n 审核人：__________"); //设定text
				grid.func("dom_insertChild", footer + "\r\n -1 \r\n" + layerDom);
				layerDom = grid.func("dom_new", ""); //创建一个空的DOM元素
				grid.func("dom_setName", layerDom + "\r\n layer"); //设定元素名
				grid.func("DOM_SetProp", layerDom + "\r\n y \r\n 0"); 
				grid.func("DOM_SetProp", layerDom + "\r\n align \r\n center"); 
				grid.func("dom_setProp", layerDom + "\r\n #text \r\n 打印人：${operPersonName}"); //设定text
				grid.func("dom_insertChild", footer + "\r\n -1 \r\n" + layerDom);
				layerDom = grid.func("dom_new", ""); //创建一个空的DOM元素
				grid.func("dom_setName", layerDom + "\r\n layer"); //设定元素名
				grid.func("DOM_SetProp", layerDom + "\r\n y \r\n 0"); 
				grid.func("DOM_SetProp", layerDom + "\r\n align \r\n right"); 
				grid.func("dom_setProp", layerDom + "\r\n #text \r\n 打印日期："+ (new Date().format('yyyy-MM-dd'))); //设定text
				grid.func("dom_insertChild", footer + "\r\n -1 \r\n" + layerDom);
				layerDom = grid.func("dom_new", ""); //创建一个空的DOM元素
				grid.func("dom_setName", layerDom + "\r\n layer"); //设定元素名
				grid.func("DOM_SetProp", layerDom + "\r\n y \r\n 30"); 
				grid.func("DOM_SetProp", layerDom + "\r\n align \r\n center"); 
				grid.func("dom_setProp", layerDom + "\r\n #text \r\n =if(hPages()>1, '第'+vPage()+'页(第' +hPage()+ '部分), 共'+vPages()+'页', '第'+Page()+'页, 共'+Pages()+'页')"); //设定text
				grid.func("dom_insertChild", footer + "\r\n -1 \r\n" + layerDom);
				//grid.func("dom_SetProp", pageHeader + "\r\n left \r\n 10"); //设置
				printXml = grid.func("dom_export", printDOM); //输出xml
				grid.func("SetProp", "Print \r\n" + printXml); //完成
			}
			grid.func("dom_delete", printDOM); //销毁对象 
		},
		//打印预览窗口关闭
		"Previewed":function( id,p1, p2, p3, p4){
			var grid = eval("("+id+")");
			var printXml = grid.func("GetProp", "Print");
			var printDOM = grid.func("dom_new", printXml);
			var printer,paperNumber,oriantation,scale;
			/*打印纸及相关配置*/
			var paper = grid.func("dom_find", printDOM + "\r\n paper");
			if(paper){
				paperNumber = grid.func("DOM_GetProp", paper + "\r\n paperNumber") + '';
				oriantation = grid.func("DOM_GetProp", paper + "\r\n oriantation");
				scale = grid.func("DOM_GetProp", paper + "\r\n scale");
			}
			grid.func("dom_delete", printDOM); //销毁对象 
			//打印设置
			//var printer = jQuery("#${random}_accountBean_printer").val();
			if(paperNumber && paperNumber !== '-1'){
				jQuery("#${random}_accountBean_paperNumber").val(paperNumber);
			}
			if(oriantation){
				jQuery("#${random}_accountBean_oriantation").val(oriantation);
			}
			if(scale){
				jQuery("#${random}_accountBean_scale").val(scale);
			}
		},
		//已执行计算
		"Calced":function( id,p1, p2, p3, p4){
			var grid = eval("("+id+")");
			//打印设置
			var printer = jQuery("#${random}_accountBean_printer").val();
			var paperNumber = jQuery("#${random}_accountBean_paperNumber").val();
			var oriantation = jQuery("#${random}_accountBean_oriantation").val();
			var scale = jQuery("#${random}_accountBean_scale").val();
			var printXml = grid.func("GetProp", "Print");
			var printDOM = grid.func("dom_new", printXml);
			/*打印纸及相关配置*/
			var paper = grid.func("dom_find", printDOM + "\r\n paper");
			if(!paper){
				paper = grid.func("dom_new", "");
				grid.func("dom_setName", paper + "\r\n paper");
				grid.func("dom_insertChild", printDOM + "\r\n 0 \r\n" + paper);
			}
			if(paperNumber){
				grid.func("DOM_SetProp", paper + "\r\n paperNumber \r\n "+paperNumber); 
			}
			if(oriantation){
				grid.func("DOM_SetProp", paper + "\r\n oriantation \r\n "+oriantation); 
			}
			if(scale){
				grid.func("DOM_SetProp", paper + "\r\n scale \r\n "+scale); 
			}
			printXml = grid.func("dom_export", printDOM); //输出xml
			grid.func("SetProp", "Print \r\n" + printXml); //完成
			grid.func("dom_delete", printDOM); //销毁对象 
			if(printViewShow){
				grid.func("CallFunc","18");
			}
		}
	},
	callback:{
		onComplete:function(id){
			var grid = eval("("+id+")");
			grid.func("EnableMenu","print,copy,addSort,selectCol,export,separate,showRuler,enter \r\n false");//打印预览,复制,加入多重排序,自定义显示隐藏列,转换输出,分屏冻结,显示/隐藏左标尺,回车键行为	
			var printers = grid.func("GetPrinters","");
			var printerHtml = "";
			if(printers){
				var printerArr = printers.split(",");
				for(var indexTemp in printerArr){
					printerHtml += "<option>"+printerArr[indexTemp]+"</option>";
				}
			}
			jQuery("#${random}_accountBean_printer").html(printerHtml);
			if(printSettingObj){
				if(printSettingObj.paperNumber && printSettingObj.paperNumber !== "-1"){
					jQuery("#${random}_accountBean_paperNumber").val(printSettingObj.paperNumber);
				}
				if(printSettingObj.oriantation){
					jQuery("#${random}_accountBean_oriantation").val(printSettingObj.oriantation);
				}
				if(printSettingObj.scale){
					jQuery("#${random}_accountBean_scale").val(printSettingObj.scale);
				}
			}
		}
	}
}; 
supcanGridMap.put('gzAccountPlanReport_gridtable',gzAccountPlanReportDefine);

	var gzAccountItems = {};//选择的工资项
	var gzAccountSortItems = {};//排序的工资项
	var requiredItems = [];
	var requiredItemStr = "";
	var gzAccountCustomLayout;
	var allGzItems;
	var configurable = "";
	var sysGzPlanObj = {};
	var printSettingObj = {};
	var printViewShow = "";
	jQuery(document).ready(function(){
		//打印纸初始化
		var supCanPageHtml = "";
		if(supCanPages){
			for(var pageNumber in supCanPages){
				supCanPageHtml += '<option value="'+pageNumber+'">' + supCanPages[pageNumber] + '</option>';
			}
		}
		jQuery("#${random}_accountBean_paperNumber").html(supCanPageHtml);
		jQuery("#${random}_accountBean_paperNumber").val("9");
		jQuery("#${random}_curGzType_gzTypeName").val("${curGzType.gzTypeId}");
		if("${gzAccountDefine.defineId}" == "09"){
			jQuery("#${random}_curGzType_gzTypeName").attr("disabled","disabled");
			//var optionHtml = '<option value="XT">系统</option>';
			//jQuery("#${random}_curGzType_gzTypeName").html(optionHtml);
		}
		//获取帐表中排序后的列
		if("${oper}"){
			var sortColumns = jQuery("#${oper}_gzAccount_sortColumns").val();
			if(sortColumns){
				gzAccountSortItems = JSON.parse(sortColumns);
			}
			var printSetting = jQuery("#${oper}_gzAccount_printSetting").val();
			printSettingObj = JSON.parse(printSetting);
		}
				var gzAccountPlanSysStr = jQuery("#${random}_gzAccountPlanSysStr").val();
				if(gzAccountPlanSysStr){
					sysGzPlanObj = JSON.parse(gzAccountPlanSysStr);
				}
				configurable = "${gzAccountDefine.configurable}";
				requiredItemStr = "${gzAccountDefine.requiredItems}";
				if(requiredItemStr){
					requiredItems = requiredItemStr.split(",");
				}
				if(configurable != "true"){
					jQuery("#${random}gz_planForm_toRight").attr("disabled","disabled");
					jQuery("#${random}gz_planForm_AlltoRight").attr("disabled","disabled");
					jQuery("#${random}gz_planForm_toLeft").attr("disabled","disabled");
					jQuery("#${random}gz_planForm_ALLtoLeft").attr("disabled","disabled");
					jQuery("#${random}plan_Name").attr("readonly","readonly");
					jQuery("#${random}_accountBean_toPublic").bind("click",function(){return false;});
					jQuery("#${random}_accountBean_toDepartment").bind("click",function(){return false;});
					jQuery("#${random}_accountBean_toRole").bind("click",function(){return false;});
					jQuery("#${random}_accountBean_planDel").hide();
				}
				var gzItemsJsonStr = jQuery("#${random}_gzItemsJsonStr").val();
				if(gzItemsJsonStr){
					allGzItems = JSON.parse(gzItemsJsonStr);
					gzAccountFormSelectLoad();
				}
				/*初始化页面*/
				/*次结或者月结*/
				if ("${curGzType.issueType}" == "1") {
					jQuery("#${random}_accountBean_a1_yearDiv").show();
					jQuery("#${random}_accountBean_a1_periodDiv").hide();
					jQuery("#${random}_accountBean_c1_yearDiv").show();
					jQuery("#${random}_accountBean_c1_periodDiv").hide();
				} else {
					jQuery("#${random}_accountBean_a1_yearDiv").hide();
					jQuery("#${random}_accountBean_a1_periodDiv").show();
					jQuery("#${random}_accountBean_c1_yearDiv").hide();
					jQuery("#${random}_accountBean_c1_periodDiv").show();
				}
				/*隐藏部分查询框*/
				var showFilterStr = "${gzAccountDefine.filters}";
				if (showFilterStr){
					var showFilters = showFilterStr.split(",");
					jQuery.each(showFilters, function(key, value) {
						jQuery("#${random}_accountBean_" + value).show();
					});
				}

				/*必填项*/
				var requiredFilterStr = "${gzAccountDefine.requiredFilter}";
				if (requiredFilterStr) {
					var requiredFilters = requiredFilterStr.split(",");
					jQuery.each(requiredFilters, function(key, value) {
						jQuery("#${random}_accountBean_" + value).find(
								"input:visible").addClass("required");
					});
				}
				/*treeSelect初始化*/
				gzAccountPlanFormInitTreeSelect("", "", "","","","");
				//初始化方案 
				var options = jQuery("#${random}gz_planList option");
				if (options.length > 0) {
					//设置第一个为选中状态
					if(!"${planId}" || "${planId}" === "null"){
						jQuery("#${random}gz_planList option").eq(0).attr(
								'selected', 'true');
					}else{
						jQuery("#${random}gz_planList option[value='"+ "${planId}" + "']").attr(
								'selected', 'true');
					}
					planInit();
				}

				var planClickTimer;
				jQuery("#${random}gz_planList").click(function() {
					clearTimeout(planClickTimer);
					planClickTimer = setTimeout(function() {
						planInit()
					}, 300);
				});
				jQuery("#${random}gz_planList").dblclick(function() {
					clearTimeout(planClickTimer);
					gzAccountSubmit();
				});
				
				jQuery("#${random}gz_items1").on('dblclick','',function(){
					gzItemMoveToRight(jQuery(this).find("option:selected")[0]);
				});
				jQuery("#${random}gz_items2").on('dblclick','',function(){
					gzItemMoveToLeft(jQuery(this).find("option:selected")[0]);
				});
				//初始化打印预览treeList
				var reverseColumn = "${gzAccountDefine.reverseColumn}";//反转
				if(reverseColumn){//反转
					initGzAccountReportPreviewReverseGrid();
				}else{
					initGzAccountReportPreviewGrid();
				}
	});
	//初始化打印预览treeList
	function initGzAccountReportPreviewGrid(reLoad){
		var gzTypeId = jQuery("#${random}_curGzType_gzTypeName").val();
		var accountType = "${accountType}";
		jQuery.ajax({
	    	url: 'gzAccountColumnInfo',
	        data: {gzAccountItemsStr:JSON.stringify(gzAccountItems),
	        	accountType:accountType,gzTypeId:gzTypeId},
	        type: 'post',
	        dataType: 'json',
	        async:false,
	        error: function(data){
	        },
	        success: function(data){   
	        	var gzItems = data.gzItems;
	        	var columns = "";
	        	for(var itemIndex in gzItems){
	        		var itemCode = gzItems[itemIndex].itemCode;
	        		columns += itemCode +",";
	    		}
	        	if(gzItems&&gzItems.length>0){
	        		columns = columns.substring(0,columns.length-1);
	        	}
	        	var colModelDatas = initGzAccountReportPreviewColModel(gzItems);
	        	initGzAccountReportPreviewGridScript(colModelDatas,columns,accountType,gzTypeId,reLoad);
	        }
	    });
	}
	//反转
	/*反转*/
	function initGzAccountReportPreviewReverseGrid(reLoad){
		var gzTypeId = jQuery("#${random}_curGzType_gzTypeName").val();
		var accountType = "${accountType}";
		var colModelDatas= [
{name:'gzItems',index:'gzItems',align:'center',totalExpress:"合计",totalAlign:"center",text : '<s:text name="工资项目" />',width:80,isHide:false,editable:false,dataType:'string'}
];
		var colArr = gzAccountReportPreviewReverseCols();
		var gzItems = [];
		if(colArr){
			jQuery.each(colArr,function(key,value){
				var colModelData = {  
	    	            name :  "period"+value,  
	    	            index : "period"+value,
	    	 		    text : value,
	    	 		    decimal:'2',
	    	 		   	align:'right',
	    	 		    totalExpress:'@sum',
	    	 		    totalAlign:'right',
	    	 		    dataType:'double',
	    	 		    editable:false,
	    	 		    isHide:false,
	    	 		    width : 100
	    	 		}
	    	 		colModelDatas.push(colModelData);
				gzItems[key]={itemType:"0",itemCode:"period"+value,scale:2};
			});
		}
		var gzAccountPlanGrid = gzAccountReportPreviewSupcanPlanGrid(colModelDatas);
		gzAccountPlanReportDefine.main.Build = JSON.stringify(gzAccountPlanGrid);
		var gzFilterObj = gzAccountReportPreviewFilterLoad(gzTypeId);
		gzFilterObj.accountType = accountType;
		gzFilterObj.gzAccountItemsStr = JSON.stringify(gzAccountItems);
		jQuery.ajax({
			url: 'gzAccountReverseGridList',
			data: gzFilterObj,
			type: 'post',
			dataType: 'json',
			async:true,
			error: function(data){
			},
			success: function(data){
				var gzAccounts = data.gzAccountContents;
// 				jQuery("#${random}_gzRowNum").text(gzAccounts.length);
				var gzAccountGridData = {};
				gzAccountGridData.Record = gzAccounts;
				if(reLoad){
					gzAccountPlanReport_gridtable.func("load", JSON.stringify(gzAccountGridData));
					gzAccountPlanReport_gridtable.func("PrintPreview","");
				}else{
					gzAccountPlanReportDefine.main.Load = JSON.stringify(gzAccountGridData);
					insertTreeListToDiv("${random}_gzAccountPlanReport_gridtable_container","gzAccountPlanReport_gridtable","","100%");
				}
			}
		});
	}
	
	/*期间集合*/
	function gzAccountReportPreviewReverseCols(random){
		var modelStatusStr = jQuery("#${random}_gzAccountPlanModelStatusStr").val();
		var modelStatusObj = [];
		if(modelStatusStr){
			modelStatusObj = JSON.parse(modelStatusStr);
		}
		var colArr = new Array();
		var issueType = jQuery("#${random}_curGzType_gzTypeName").find("option:selected").attr("issueType");
		if(issueType == "0"&&modelStatusObj&&getJsonLength(modelStatusObj)>0){
			var fromperiod = jQuery("#"+random+"_filter_fromPeriodTime").val();
			var toperiod = jQuery("#"+random+"_filter_toPeriodTime").val();
			if(!fromperiod){
				fromperiod = "${firstPeriod}";
			}
			if(!toperiod){
				toperiod = "${curPeriod}";
			}
			for(var index in modelStatusObj){
				var modelStatus = modelStatusObj[index];
				var checkperiod = modelStatus.checkperiod;
				var checkNumber = modelStatus.checkNumber;
				if(fromperiod&&toperiod){
					if(checkperiod >= fromperiod && checkperiod<= toperiod){
						colArr.push(checkperiod+"(第"+checkNumber+"次)");
					}
				}else if(fromperiod){
					if(checkperiod >= fromperiod){
						colArr.push(checkperiod+"(第"+checkNumber+"次)");
					}
				}else if(toperiod){
					if(checkperiod<= toperiod){
						colArr.push(checkperiod+"(第"+checkNumber+"次)");
					}
				}else{
					colArr.push(checkperiod+"(第"+checkNumber+"次)");
				}
			}
		}
		if(issueType == "1"&&modelStatusObj&&getJsonLength(modelStatusObj)>0){
			var fromCheckYear = jQuery("#"+random+"_filter_fromCheckYear").val();
			var fromCheckNumber = jQuery("#"+random+"_filter_fromCheckNumber").val();
			var toCheckYear = jQuery("#"+random+"_filter_toCheckYear").val();
			var toCheckNumber = jQuery("#"+random+"_filter_toCheckNumber").val();
			for(var index in modelStatusObj){
				var modelStatus = modelStatusObj[index];
				var checkperiod = modelStatus.checkperiod;
				var checkNumber = modelStatus.checkNumber;
				var fromFlag = false;
				var toFlag = false;
				if(fromCheckYear&&fromCheckNumber){
					fromCheckYear = fromCheckYear + "01";
					if(checkperiod >= fromCheckYear&&checkNumber >= fromCheckNumber){
						fromFlag = true;
					}
				}else if(fromCheckYear){
					fromCheckYear = fromCheckYear + "01";
					if(checkperiod >= fromCheckYear){
						fromFlag = true;
					}
				}else if(fromCheckNumber){
					if(checkNumber >= fromCheckNumber){
						fromFlag = true;
					}
				}else{
					fromFlag = false;
				}
				if(toCheckYear&&toCheckNumber){
					toCheckYear = toCheckYear + "12";
					if(checkperiod <= toCheckYear&&checkNumber <= toCheckNumber){
						toFlag = true;
					}
				}else if(toCheckYear){
					toCheckYear = toCheckYear + "12";
					if(checkperiod <= toCheckYear){
						toFlag = true;
					}
				}else if(toCheckNumber){
					if(toCheckNumber <= toCheckNumber){
						toFlag = true;
					}
				}else{
					toFlag = false;
				}
				if(fromCheckYear||fromCheckNumber||toCheckYear||toCheckNumber){
					if(toFlag||fromFlag){
						colArr.push(checkperiod+"(第"+checkNumber+"次)");
					}
				}else{
					colArr.push(checkperiod+"(第"+checkNumber+"次)");
				}
			}
		}	
		return colArr;
	}
	
	function initGzAccountReportPreviewGridScript(colModelDatas,columns,accountType,gzTypeId,reLoad){
		if("${gzAccountDefine.defineId}" == "02"){
			var deptIds = jQuery("#${random}_accountBean_deptIds_id").val();
			var gzStubsDeptNumber = "${gzStubsDeptNumber}";
			gzStubsDeptNumber = +gzStubsDeptNumber;
			if(!gzStubsDeptNumber){
				gzStubsDeptNumber = 10;
			}
			if(deptIds && deptIds.split(",").length <= gzStubsDeptNumber){
				if(reLoad){
					gzAccountPlanReport_gridtable.func("Build", colModelDatas);
				}else{
					gzAccountPlanReportDefine.main.Build = colModelDatas;
				}
				gzAccountReportPreviewGridTableload(columns,accountType,gzTypeId,reLoad);
			}else{
				if(reLoad){
					gzAccountPlanReport_gridtable.func("Build", colModelDatas);
					gzAccountPlanReport_gridtable.func("calc", "mode=asynch;range=current");
				}else{
					gzAccountPlanReportDefine.main.Build = colModelDatas;
					insertReportToDiv("${random}_gzAccountPlanReport_gridtable_container","gzAccountPlanReport_gridtable","Rebar=None; Border=single;Hue=Lilian;isAnimateFocus=false; PagesTabPercent=0; SeperateBar=none","100%");				
				}
			}
		}else{
			var gzAccountPlanGrid = gzAccountReportPreviewSupcanPlanGrid(colModelDatas);
			if(reLoad){
				gzAccountPlanReport_gridtable.func("build", JSON.stringify(gzAccountPlanGrid));
			}else{
				gzAccountPlanReportDefine.main.Build = JSON.stringify(gzAccountPlanGrid);
			}
			gzAccountReportPreviewGridTableload(columns,accountType,gzTypeId,reLoad);
		}
	}
	
	function initGzAccountReportPreviewColModel(data){
		if("${gzAccountDefine.defineId}" == "02"){
			var colColumns = jQuery("#${random}_accountBean_colColumns").val();
			var colMax = 6;
			if(colColumns&&!isNaN(colColumns)){
				colColumns = +colColumns;
				if(colColumns>0){
					colMax = colColumns;
				}
			}
			var reportXml = '<?xml version="1.0" encoding="UTF-8" ?>';
			reportXml += '<Report>';
			reportXml += '<WorkSheet name="${gzAccountDefine.defineName}">';
			reportXml += '<Properties>';
			reportXml += '<BackGround bgColor="#FFFFFF"/>';
			reportXml += '<Other isRowHeightAutoExtendAble="false" AutoBreakLine="1" LineDistance="1"/>';
			reportXml += '</Properties>';
			reportXml += '<Fonts>';
			for(var index in supCanFont){
				var supCanFontTemp = supCanFont[index];
				var faceName = supCanFontTemp.faceName;
				var bold = supCanFontTemp.bold;
				var underline = supCanFontTemp.underline;
				var weight;
				if(bold === "0"){//400/700 对应 非粗体/粗体
					weight = "400";
				}else{
					weight = "700";
				}
				reportXml += '<Font faceName="'+faceName+'" underline="'+underline+'" height="-13" weight="'+weight+'"/>';
			}
//	 		reportXml += '<Font faceName="Verdana" height="-13" weight="400"/>';
//	 		reportXml += '<Font faceName="Verdana" height="-13" weight="400"/>';
			reportXml += '</Fonts>';
			reportXml += '<Table>';
			/*定义列数与列宽*/
			var colXml = '';
			colXml += '<Col width="12"/>';
			for(var i=0;i<colMax;i++){
				colXml += '<Col width="100"/>';
			}
			colXml += '<Col width="12"/>';
			reportXml += colXml;
			/*定义表头行*/
			var thXml = '<TR height="23" sequence="0" areaNumber="1">';
			thXml += '<TD leftBorder="0" topBorder="0" formula="=datarow(&apos;ds1\\Record\\jsonobject&apos;)"  isArea="true"/>';
			//thXml += '<TD fontIndex="1" leftBorder="1" topBorder="1" leftBorderColor="#a6c9e2" topBorderColor="#a6c9e2" bgColor="#dfeffc" leftBorder="1" topBorder="1" align="center" datatype="1" formula="=headrow(&apos;ds1\\Record\\jsonobject&apos;)"/>';
			thXml += '<TD fontIndex="1" leftBorder="1" topBorder="1" leftBorderColor="#a6c9e2" topBorderColor="#a6c9e2" bgColor="#dfeffc" leftBorder="1" topBorder="1" align="center" datatype="1" formula="=head(&apos;ds1\\Record\\jsonobject&apos;, &apos;period&apos;)"/>';
			thXml += '<TD fontIndex="1" leftBorder="1" topBorder="1" bgColor="#dfeffc" leftBorderColor="#a6c9e2" topBorderColor="#a6c9e2" align="center" datatype="1" formula="=head(&apos;ds1\\Record\\jsonobject&apos;, &apos;issueNumber&apos;)"/>';
			colIndex = 2;
			/*定义数据行*/
			var tdXml = '<TR height="23" sequence="1"  areaNumber="2">';
			tdXml += '<TD leftBorder="0" topBorder="0" isArea="true"/>'
			//tdXml += '<TD fontIndex="2" leftBorder="1" topBorder="1" leftBorderColor="#a6c9e2" topBorderColor="#a6c9e2" datatype="1" formula="=datarow(&apos;ds1\\Record\\jsonobject&apos;)"/>';
			tdXml += '<TD fontIndex="0" leftBorder="1" topBorder="1" leftBorderColor="#a6c9e2" topBorderColor="#a6c9e2" datatype="1" formula="=data(&apos;ds1\\Record\\jsonobject&apos;, datarowNumber(), &apos;period&apos;)"/>';
			tdXml += '<TD fontIndex="0" leftBorder="1" topBorder="1" leftBorderColor="#a6c9e2" topBorderColor="#a6c9e2" datatype="1" align="right" formula="=data(&apos;ds1\\Record\\jsonobject&apos;, datarowNumber(), &apos;issueNumber&apos;)"/>';
			/*定义末行(补齐下边线用)*/
			//var tfXml = '<TR height="1">';
			//tfXml += '<TD  topBorder="0"  leftBorder="0"/>';
			//tfXml += '<TD  topBorder="1" leftBorder="0" topBorderColor="#a6c9e2"/>';
			//tfXml += '<TD  topBorder="1" leftBorder="0" topBorderColor="#a6c9e2"/>';
			var dataSourceXml = '<DataSources Version="255" isAutoCalculateWhenOpen="true" isSaveCalculateResult="false">';
			dataSourceXml += '<DataSource type="4"><!-- Desc: Supcan Report Component DataSource Specification -->';
			dataSourceXml += '<Data>';
			dataSourceXml += '<ID>ds1</ID>';
			dataSourceXml += '<Version>2</Version>';
			dataSourceXml += '<Type>4</Type>';
			dataSourceXml += '<TypeMeaning>JSON</TypeMeaning>';
			dataSourceXml += '<Source></Source>';
			dataSourceXml += '<XML_RecordAble_Nodes>';
			dataSourceXml += '<Node>';
			dataSourceXml += '<name alias="${gzAccountDefine.defineName}">Record\\jsonobject</name>';
			dataSourceXml += '</Node>';
			dataSourceXml += '</XML_RecordAble_Nodes>';
			dataSourceXml += '<Columns>';
			dataSourceXml += '<Column>';
			dataSourceXml += '<name>Record\\jsonobject\\period</name>';
			dataSourceXml += '<text>期间</text>';
			dataSourceXml += '<type>string</type>';
			dataSourceXml += '<visible>true</visible>';
			dataSourceXml += '<sequence>1</sequence>';
			dataSourceXml += '</Column>';
			dataSourceXml += '<Column>';
			dataSourceXml += '<name>Record\\jsonobject\\issueNumber</name>';
			dataSourceXml += '<text>发放次数</text>';
			dataSourceXml += '<type>int</type>';
			dataSourceXml += '<visible>true</visible>';
			dataSourceXml += '<sequence>2</sequence>';
			dataSourceXml += '</Column>';
			var colIndex = 2;
			var areaNumber = 3;
			var rowIndex = 1;
			for(var index in data){
		 		var row = data[index];
		 		var name = row.itemCode;
		 		var text = row.colName;
		 		var isThousandSeparat = row.isThousandSeparat;
		 		var fontIndex = row.fontIndex;
		 		var headerFontIndex = row.headerFontIndex;
		 		var headerTextColor = row.headerTextColor;
		 		var width = 80;
		 		var align;
		 		var dataType;
		 		var dataTypeNum;
		 		var decimal;
		 		switch(row.itemType){
				case "0"://数值型
					dataType = "double";
					align = "right";
					dataTypeNum = "6";
					decimal = "2";
					break;
				case "2"://日期型
					dataType = "date";
					align = "center";
					dataTypeNum = "4";
					decimal = "0";
					break;
				case "3"://整型
					dataType = "int";
					align = "right";
					dataTypeNum = "6";
					decimal = "0";
					break;
				default:
					dataType = "string";
					align = "left";
					dataTypeNum = "1";
					decimal = "0";
					break;
				}
		 		if(colIndex == colMax){
		 			colIndex = 0;
		 			thXml += '<TD  leftBorder="1" topBorder="0"  leftBorderColor="#a6c9e2"/>';
		 	 		tdXml += '<TD  leftBorder="1" topBorder="0"  leftBorderColor="#a6c9e2"/>';
		 			thXml += '</TR>';
		 			tdXml += '</TR>';
		 			reportXml += thXml;
		 			reportXml += tdXml;
		 			rowIndex += 2;
		 			thXml = '<TR height="23" sequence="'+(areaNumber++)+'" areaNumber="'+(areaNumber++)+'">';
		 			tdXml = '<TR height="23" sequence="'+(areaNumber++)+'"  areaNumber="'+(areaNumber++)+'">';
		 			thXml += '<TD leftBorder="0" topBorder="0"/>';
		 			tdXml += '<TD leftBorder="0" topBorder="0"/>'
		 			thXml += '<TD leftBorder="1" topBorder="1" leftBorderColor="#a6c9e2" topBorderColor="#a6c9e2" bgColor="#dfeffc" align="center" datatype="1" formula="=head(&apos;ds1\\Record\\jsonobject&apos;, &apos;'+name+'&apos;)"';
		 			if(headerTextColor){
		 				thXml += ' textColor="'+headerTextColor+'" ';
		 			}
		 			if(headerFontIndex){
		 				thXml += ' fontIndex="'+headerFontIndex+'" ';
		 			}else{
		 				thXml += ' fontIndex="0" ';
		 			}
		 			thXml += '/>';
		 			tdXml += '<TD leftBorder="1" topBorder="1" leftBorderColor="#a6c9e2" topBorderColor="#a6c9e2"  isDataSource="true" align="'+align+'" decimal="'+decimal+'" dataType="'+dataTypeNum+'" formula="=data(&apos;ds1\\Record\\jsonobject&apos;, datarowNumber(), &apos;'+name+'&apos;)"';
		 			if(isThousandSeparat){
		 				tdXml += ' isThousandSeparat="'+isThousandSeparat+'" ';
		 			}
		 			if(fontIndex){
		 				tdXml += ' fontIndex="'+fontIndex+'" ';
		 			}else{
		 				tdXml += ' fontIndex="0" ';
		 			}
		 			tdXml += '/>';
		 		}else{
		 			thXml += '<TD leftBorder="1" topBorder="1" leftBorderColor="#a6c9e2" topBorderColor="#a6c9e2" bgColor="#dfeffc" align="center" datatype="1" formula="=head(&apos;ds1\\Record\\jsonobject&apos;, &apos;'+name+'&apos;)"';
		 			if(headerTextColor){
		 				thXml += ' textColor="'+headerTextColor+'" ';
		 			}
		 			if(headerFontIndex){
		 				thXml += ' fontIndex="'+headerFontIndex+'" ';
		 			}else{
		 				thXml += ' fontIndex="0" ';
		 			}
		 			thXml += '/>';
		 			tdXml += '<TD leftBorder="1" topBorder="1" leftBorderColor="#a6c9e2" topBorderColor="#a6c9e2"  isDataSource="true" align="'+align+'" decimal="'+decimal+'" dataType="'+dataTypeNum+'" formula="=data(&apos;ds1\\Record\\jsonobject&apos;, datarowNumber(), &apos;'+name+'&apos;)"';
		 			if(isThousandSeparat){
		 				tdXml += ' isThousandSeparat="'+isThousandSeparat+'" ';
		 			}
		 			if(fontIndex){
		 				tdXml += ' fontIndex="'+fontIndex+'" ';
		 			}else{
		 				tdXml += ' fontIndex="0" ';
		 			}
		 			tdXml += '/>';
		 		}
		 		colIndex ++;
		 		
		 		//tfXml += '<TD  topBorder="1" leftBorder="0" topBorderColor="#a6c9e2"/>';
		 		dataSourceXml += '<Column>';
				dataSourceXml += '<name>Record\\jsonobject\\'+name+'</name>';
				dataSourceXml += '<text>'+text+'</text>';
				dataSourceXml += '<type>'+dataType+'</type>';
				dataSourceXml += '<visible>true</visible>';
				dataSourceXml += '<sequence>'+(index + 3)+'</sequence>';
				dataSourceXml += '</Column>';
			}
	 		for(var indexTemp = colIndex;indexTemp < colMax;indexTemp++ ){
	 			thXml += '<TD  leftBorder="1" topBorder="1" topBorderColor="#a6c9e2"  leftBorderColor="#a6c9e2"/>';
	 	 		tdXml += '<TD  leftBorder="1" topBorder="1" topBorderColor="#a6c9e2"  leftBorderColor="#a6c9e2"/>';
	 		}
			/*定义最右列(补齐左边线用)*/
			thXml += '<TD  leftBorder="1" topBorder="0"  leftBorderColor="#a6c9e2"/>';
	 		tdXml += '<TD  leftBorder="1" topBorder="0"  leftBorderColor="#a6c9e2"/>';
	 		//tfXml += '<TD leftBorder="0" topBorder="0"/>'
			thXml += '</TR>';
			tdXml += '</TR>';
			//tfXml += '</TR>';
			dataSourceXml += '</Columns>';
			dataSourceXml += '</Data>';
			dataSourceXml += '</DataSource>';
			dataSourceXml += '</DataSources>';
			var blankRowXml = '<TR height="13" sequence="6"  areaNumber="'+(areaNumber++)+'">';
			blankRowXml += '<TD leftBorder="0" topBorder="0"/>';
			for(var i=0;i<colMax;i++){
				blankRowXml += '<TD leftBorder="0" topBorder="1" topBorderColor="#a6c9e2"/>';
			}
			blankRowXml += '<TD leftBorder="0" topBorder="0" topBorderColor="#a6c9e2"/>';
			blankRowXml += '</TR>';
			var dotRowXml = '<TR height="13" sequence="7"  areaNumber="'+(areaNumber++)+'">';
			for(var i=0;i<colMax;i++){
				dotRowXml += '<TD leftBorder="0" topBorder="3" topBorderStyle="dot"/>';
			}
			dotRowXml += '<TD leftBorder="0" topBorder="3" topBorderStyle="dot"/>';
			for(var i=0;i<colMax;i++){
				dotRowXml += '<TD leftBorder="0" topBorder="3" topBorderStyle="dot"/>';
			}
			dotRowXml += '<TD leftBorder="0" topBorder="3" topBorderStyle="dot"/>';
			dotRowXml += '</TR>';
			rowIndex += 2;
			reportXml += thXml;
			reportXml += tdXml;
			reportXml += blankRowXml;
			reportXml += dotRowXml;
			//reportXml += tfXml;
			reportXml += '</Table>';
			reportXml += '<Merges>';
			reportXml += '<Range row1="0" col1="0" row2="'+rowIndex+'" col2="0"/>';
			reportXml += '</Merges>';
			reportXml += '<PrintPage>';
			reportXml += '<Paper>';
			reportXml += '<Margin left="3" top="3" right="3" bottom="3"/>';
			reportXml += '</Paper>';
			reportXml += '<Page align="center" vAlign="middle" isIgnoreValidBorder="true">';
			//每页打印行数RowsPerPage；垂直打印页数MultiPageV；水平打印页数MultiPageH;printSequence超宽时的打印顺序0 / 1, 先纵后横/先横后纵
			//reportXml += '<Page-break printSequence="0" dataSourceRowsPerPage="1" dataSourceMultiPageV="12" dataSourceMultiPageH="1">';
			//reportXml += '<FixedRowCols headerRows="1" footerRows="0"/>';
			//reportXml += '</Page-break>';
			reportXml += '<PageCode isPrint="false"/>';
			reportXml += '</Page>';
			reportXml += '</PrintPage>';
			reportXml += '</WorkSheet>';
			reportXml += dataSourceXml;
			reportXml += '</Report>';
	    	return reportXml;
		}else{
			var colModelDatas = [];
			if("${gzAccountDefine.defineId}" == "05" || "${gzAccountDefine.defineId}" == "06" ){
				colModelDatas.push({name:'period',index:'period',align:'center',text : '<s:text name="期间" />',width:80,isHide:false,editable:false,dataType:'string'});
				colModelDatas.push({name:'issueNumber',index:'issueNumber',align:'right',text : '<s:text name="发放次数" />',width:60,isHide:false,editable:false,dataType:'int'});
			}else{
				colModelDatas.push({name:'period',index:'period',align:'center',totalExpress:"合计",totalAlign:"center",text : '<s:text name="期间" />',width:80,isHide:false,editable:false,dataType:'string'});
				colModelDatas.push({name:'issueNumber',index:'issueNumber',align:'right',text : '<s:text name="发放次数" />',width:60,isHide:false,editable:false,dataType:'int'});
			}
			if("${gzAccountDefine.defineId}" == "09"){
				colModelDatas.push({name:'gzTypeId',index:'gzTypeId',align:'left',text : '工资类别',width:80,isHide:false,editable:false,dataType:'string'});
			}
	        	for(var index in data){
	    	 		var row = data[index];
	    	 		var colModelData = {  
	    	            name :  row.itemCode,  
	    	            index : row.colSn,
	    	 		    text : row.colName,
	    	 		    align : supCanParseAlign(row.itemType),
	    	 		    width : 80
	    	 		}
	    	 		if(row.isThousandSeparat){
	    	 			colModelData.isThousandSeparat = row.isThousandSeparat;
	    	 		}
	    	 		if(row.fontIndex){
	    	 			colModelData.fontIndex = row.fontIndex;
	    	 		}
	    	 		if(row.headerFontIndex){
	    	 			colModelData.headerFontIndex = row.headerFontIndex;
	    	 		}
	    	 		if(row.headerTextColor){
	    	 			colModelData.headerTextColor = row.headerTextColor;
	    	 		}
	    	 		colModelData = supCanAddToEditOption(colModelData,row);
	    	 		if("${gzAccountDefine.defineId}" == "05" || "${gzAccountDefine.defineId}" == "06"){
	    	 			colModelData.totalExpress = "";
	    	 		}
	    	 		colModelDatas.push(colModelData);
	    		} 
	    		return colModelDatas;
		}
    }
	function gzAccountReportPreviewSupcanPlanGrid(colModelDatas){
		var gzAccountPlanGrid = cloneObj(supCanTreeListGrid);
		gzAccountPlanGrid.Cols = colModelDatas;
		gzAccountPlanGrid.Properties.Title = "${gzAccountDefine.defineName}";
		gzAccountPlanGrid.Properties.sort = "period,orgCode,deptCode,personCode";
		return gzAccountPlanGrid;
	}
	
	function gzAccountReportPreviewGridTableload(columns,accountType,gzTypeId,reLoad){
		jQuery.ajax({
			url: 'gzAccountGridList?accountType='+accountType+'&columns='+columns,
			data: gzAccountReportPreviewFilterLoad(gzTypeId),
			type: 'post',
			dataType: 'json',
			async:true,
			error: function(data){
			},
			success: function(data){
				var gzAccounts = data.gzAccountContents;
				var gzAccountGridData = {};
				gzAccountGridData.Record = gzAccounts;
				if("${gzAccountDefine.defineId}" == "02"){
					if(reLoad){
						gzAccountPlanReport_gridtable.func("setSource", "ds1 \r\n "+JSON.stringify(gzAccountGridData));
						gzAccountPlanReport_gridtable.func("calc", "mode=asynch;range=current");
					}else{
						gzAccountPlanReportDefine.main.SetSource = "ds1 \r\n "+JSON.stringify(gzAccountGridData);
						insertReportToDiv("${random}_gzAccountPlanReport_gridtable_container","gzAccountPlanReport_gridtable","Rebar=None; Border=single;Hue=Lilian;isAnimateFocus=false; PagesTabPercent=0; SeperateBar=none","100%");
					}
				}else{
					if(reLoad){
						gzAccountPlanReport_gridtable.func("load", JSON.stringify(gzAccountGridData));
						gzAccountPlanReport_gridtable.func("PrintPreview","");
					}else{
						gzAccountPlanReportDefine.main.Load = JSON.stringify(gzAccountGridData);
						insertTreeListToDiv("${random}_gzAccountPlanReport_gridtable_container","gzAccountPlanReport_gridtable","","100%");
					}
				}
			}
		});
	}
	//过滤条件
	function gzAccountReportPreviewFilterLoad(gzTypeId){
		var periodTime = jQuery("#${random}_accountBean_periodTime").val();
		var checkYear = jQuery("#${random}_accountBean_checkYear").val();
		var checkNumber = jQuery("#${random}_accountBean_checkNumber").val();
		var fromPeriodTime = jQuery("#${random}_accountBean_fromPeriodTime").val();
		var toPeriodTime = jQuery("#${random}_accountBean_toPeriodTime").val();
		var fromCheckYear = jQuery("#${random}_accountBean_fromCheckYear").val();
		var fromCheckNumber = jQuery("#${random}_accountBean_fromCheckNumber").val();
		var toCheckYear = jQuery("#${random}_accountBean_toCheckYear").val();
		var toCheckNumber = jQuery("#${random}_accountBean_toCheckNumber").val();
		//var orgCode = jQuery("#${random}_accountBean_orgCode").val();
		var orgCode = jQuery("#${random}_accountBean_orgCode_id").val();
		//var deptIds = jQuery("#${random}_accountBean_deptIds").val();
		var deptIds = jQuery("#${random}_accountBean_deptIds_id").val();
		var empTypes = jQuery("#${random}_accountBean_empTypes").val();
		var personName = jQuery("#${random}_accountBean_personName").val();
		var personCode = jQuery("#${random}_accountBean_personCode").val();
		var deptType = jQuery("#${random}_accountBean_a7").find("input:radio[name=deptType][checked=checked]").val();
		var deptLevelFrom = jQuery("#${random}_accountBean_deptLevelFrom").val();
		var deptLevelTo = jQuery("#${random}_accountBean_deptLevelTo").val();
		var deptDetailIds = jQuery("#${random}_accountBean_deptDetailIds_id").val();
		var gzTypeIds = jQuery("#${random}_accountBean_gzTypeIds_id").val();
		var personId = jQuery("#${random}_accountBean_personId").val();
		var includeUnCheck = jQuery("#${random}_accountBean_includeUnCheck").attr("checked");
		var gzFilterObj = {};
		gzFilterObj.gzTypeId = gzTypeId;
		if(periodTime){
			gzFilterObj.periodTime = periodTime;
		}
		if(checkYear){
			gzFilterObj.checkYear = checkYear;
		}
		if(checkNumber){
			gzFilterObj.checkNumber = checkNumber;
		}
		var issueType = jQuery("#${random}_curGzType_gzTypeName").find("option:selected").attr("issueType");
		if(issueType != "1"){
			if(fromPeriodTime){
				gzFilterObj.fromPeriodTime = fromPeriodTime;
			}else{
				if("${gzAccountDefine.reverseColumn}"){
					gzFilterObj.fromPeriodTime = "${firstPeriod}";
				}else{
					gzFilterObj.fromPeriodTime = "${curPeriod}";
				}
				
			}
			if(toPeriodTime){
				gzFilterObj.toPeriodTime = toPeriodTime;
			}else{
				gzFilterObj.toPeriodTime = "${curPeriod}";
			}
		}else{
			if(fromPeriodTime){
				gzFilterObj.fromPeriodTime = fromPeriodTime;
			}
			if(toPeriodTime){
				gzFilterObj.toPeriodTime = toPeriodTime;
			}
		}
		if(fromCheckYear){
			gzFilterObj.fromCheckYear = fromCheckYear;
		}
		if(fromCheckNumber){
			gzFilterObj.fromCheckNumber = fromCheckNumber;
		}
		if(toCheckYear){
			gzFilterObj.toCheckYear = toCheckYear;
		}
		if(toCheckNumber){
			gzFilterObj.toCheckNumber = toCheckNumber;
		}
		if(orgCode){
			gzFilterObj.orgCode = orgCode;
		}
		if(deptIds){
			gzFilterObj.deptIds = deptIds;
		}
		if(empTypes){
			gzFilterObj.empTypes = empTypes;
		}
		if(personName){
			gzFilterObj.personName = personName;
		}
		if(personCode){
			gzFilterObj.personCode = personCode;
		}
		if(deptType){
			gzFilterObj.deptType = deptType;
		}
		if(deptLevelFrom){
			gzFilterObj.deptLevelFrom = deptLevelFrom;
		}
		if(deptLevelTo){
			gzFilterObj.deptLevelTo = deptLevelTo;
		}
		if(deptDetailIds){
			gzFilterObj.deptDetailIds = deptDetailIds;
		}
		if(gzTypeIds){
			gzFilterObj.gzTypeIds = gzTypeIds;
		}
		if(personId){
			gzFilterObj.personId = personId;
		}
		if(includeUnCheck){
			gzFilterObj.includeUnCheck = includeUnCheck;
		}
		return gzFilterObj;
	}
	
	function planInit() {
		gzAccountPlanClearContent();
		var planId = jQuery("#${random}gz_planList").find("option:selected").val();
		var text = jQuery("#${random}gz_planList").find("option:selected").text();
		if (!planId || !text) {
			return;
		}
		jQuery("#${random}plan_Name").val(text);
		jQuery.ajax({
			url : 'getSelectedGzPlan',
			data : {
				planId : planId
			},
			type : 'post',
			dataType : 'json',
			async : true,
			error : function(data) {
				alertMsg.error("系统错误！");
			},
			success : function(data) {
				if (data.gzAccountPlans) {
					var gzAccountPlan = data.gzAccountPlans[0];
					if (gzAccountPlan) {
						if (gzAccountPlan.toPublic == "1") {
							jQuery("#${random}_accountBean_toPublic").attr(
									"checked", "checked");
						}
						if (gzAccountPlan.toDepartment == "1") {
							jQuery("#${random}_accountBean_toDepartment").attr(
									"checked", "checked");
						}
						if (gzAccountPlan.toRole == "1") {
							jQuery("#${random}_accountBean_toRole").attr(
									"checked", "checked");
						}
					}
					gzAccountCustomLayout = gzAccountPlan.customLayout;
				}
				if (data.items) {
					InitGzItem(data.items);
				}
				if (data.gzAccountPlanFilters) {
					InitFilters(data.gzAccountPlanFilters);
				}
			}
		});
	}

	function checkSelItem(id1, id2) {
		var selectItemvalue = jQuery("#${random}" + id1).val();
		var selectItemid = jQuery("#" + id2).val()
		if (selectItemvalue.indexOf("全选") == 0) {
			var selectFinalValue = selectItemvalue.substring(3,
					selectItemvalue.length);
			var selectFinal_id = selectItemid.substring(3, selectItemid.length);
			jQuery("#${random}" + id1).val(selectFinalValue);
			jQuery("#${random}" + id2).val(selectFinal_id);
		}
	}
	//右移
	function gzItemtoRight() {
		var selectItems = jQuery("#${random}gz_items1").find("option:selected");
		if(!selectItems || selectItems.length<=0){
			alertMsg.error("请选择要移动的工资项！");
			return;
		}
		jQuery.each(selectItems,function(index,selectItem){
	 		var itemText = jQuery(selectItem).text();
	 		var itemValue = jQuery(selectItem).val();
	 		jQuery("#${random}gz_items2").append("<option value='" + itemValue
					+ "' >" + itemText
					+ "</option>");
		 	jQuery(selectItem).remove();
		 	var gzAccountItemIndex = getJsonLength(gzAccountItems);
			gzAccountItems[itemValue]={"name":itemText,"oldName":itemText,isThousandSeparat:"1",
					headerFontIndex:'1',fontIndex:'0',headerTextColor:'#000000',sn:gzAccountItemIndex};
		});
	}
	//左移
	function gzItemtoLeft() {
		var selectItems = jQuery("#${random}gz_items2").find("option:selected");
		if(!selectItems || selectItems.length<=0){
			alertMsg.error("请选择要移动的工资项！");
			return;
		}
		var leftItemArr = [];
		var leftItems = jQuery("#${random}gz_items1 option");
		if(leftItems){
			jQuery.each(leftItems,function(index,leftItem){
				var itemValue = jQuery(leftItem).val();
				leftItemArr.push(itemValue);
			});
		}
		var errorMessage = "";
		jQuery.each(selectItems,function(index,selectItem){
	 		var itemText = jQuery(selectItem).text();
	 		var itemValue = jQuery(selectItem).val();
	 		if(jQuery.inArray(itemValue, requiredItems) == -1){
// 	 			jQuery("#${random}gz_items1").append("<option value='" + itemValue
// 		 				+ "'>" + itemText
// 		 				+ "</option>");
		 		jQuery(selectItem).remove();
		 		leftItemArr.push(itemValue);
		 		delete gzAccountItems[itemValue];
	 		}else{
	 			errorMessage += itemText + ",";
	 		}
		});
		//左侧初始化
		var allGzItemHtml = "";
		jQuery.each(allGzItems,function(index,gzItem){
				var itemCode = gzItem.itemCode;
				var itemName = gzItem.itemName;
				if(jQuery.inArray(itemCode, leftItemArr) > -1){
					allGzItemHtml += "<option value='" + itemCode
					+ "'>"+ itemName + "</option>";
				}
			});
		jQuery("#${random}gz_items1").html(allGzItemHtml);
		//gzAccountItems排序
		if(gzAccountItems){
			var index = 0;
			for(var itemValue in gzAccountItems){
				gzAccountItems[itemValue]["sn"] = index;
				index ++;
			}
		}
		if(errorMessage){
			errorMessage = errorMessage.substring(0,errorMessage.length-1);
			alertMsg.error(errorMessage+"为系统项！");
			return;
		}
	}
	//全部右移
	function gzItemAlltoRight() {
		var rightSelect = jQuery("#${random}gz_items2");
		jQuery("#${random}gz_items1 option").each(function(index,selectItem){
	 		var itemText = jQuery(selectItem).text();
	 		var itemValue = jQuery(selectItem).val();
					rightSelect.append("<option value='" + itemValue
							+ "' >"
							+ itemText + "</option>");
			var gzAccountItemIndex = getJsonLength(gzAccountItems);
			gzAccountItems[itemValue]={"name":itemText,"oldName":itemText,isThousandSeparat:"1",
				headerFontIndex:'1',fontIndex:'0',headerTextColor:'#000000',sn:gzAccountItemIndex};	
				});
		jQuery("#${random}gz_items1").empty();
	}
	//全部左移
	function gzItemALLtoLeft() {
		//左侧初始化
		var allGzItemHtml = "";
		jQuery.each(allGzItems,function(index,gzItem){
				var itemCode = gzItem.itemCode;
				var itemName = gzItem.itemName;
				if(jQuery.inArray(itemCode, requiredItems)== -1){
					allGzItemHtml += "<option value='" + itemCode
					+ "'>"+ itemName + "</option>";
					jQuery("#${random}gz_items2 option[value='"+ itemCode + "']").remove();
				}else{
					errorMessage += itemName + ",";
				}
			});
		jQuery("#${random}gz_items1").html(allGzItemHtml);
		gzAccountItems = {};
		if(errorMessage){
			errorMessage = errorMessage.substring(0,errorMessage.length-1);
			alertMsg.error(errorMessage+"为系统项！");
			return;
		}
	}
	function gzAccountSubmit() {
		var displayType = "${gzAccountDefine.displayType}";
		var url;
		if (displayType == "Report"){
			url = "gzAccountReportByPlan?accountType=${accountType}&menuId=${menuId}&width=${width}&height=${height}"; 
		}else{
			url = "gzAccountByPlan?accountType=${accountType}&menuId=${menuId}&width=${width}&height=${height}"; 
		}
		var gzTypeId = jQuery("#${random}_curGzType_gzTypeName").val();
		if(!gzTypeId){
			alertMsg.error("请选择工资类别！");
			return;
		}
		url += "&gzTypeId="+gzTypeId;
		var savePlanChecked = jQuery("#${random}_accountBean_savePlan").attr("checked");
		var planName = jQuery("#${random}plan_Name").val();
		if(savePlanChecked == "checked"){
			if (!planName) {
				alertMsg.error("请填写方案名，再保存！");
				return;
			}
			if(sysGzPlanObj[planName]){
				alertMsg.error("该方案为系统方案，请修改方案名！");
				return;
			}
			var savePlan = 1;
			var toPublic = jQuery("#${random}_accountBean_toPublic").attr("checked")=="checked"?1:0;
			var toDepartment = jQuery("#${random}_accountBean_toDepartment").attr("checked")=="checked"?1:0;
			var toRole = jQuery("#${random}_accountBean_toRole").attr("checked")=="checked"?1:0;
			url += "&savePlan="+savePlan+"&toPublic="+toPublic+"&toDepartment="+toDepartment+"&toRole="+toRole+"&planName="+planName;
		}
		var periodTime = jQuery("#${random}_accountBean_periodTime").val();
		var checkYear = jQuery("#${random}_accountBean_checkYear").val();
		var checkNumber = jQuery("#${random}_accountBean_checkNumber").val();
		var fromPeriodTime = jQuery("#${random}_accountBean_fromPeriodTime").val();
		var toPeriodTime = jQuery("#${random}_accountBean_toPeriodTime").val();
		var fromCheckYear = jQuery("#${random}_accountBean_fromCheckYear").val();
		var fromCheckNumber = jQuery("#${random}_accountBean_fromCheckNumber").val();
		var toCheckYear = jQuery("#${random}_accountBean_toCheckYear").val();
		var toCheckNumber = jQuery("#${random}_accountBean_toCheckNumber").val();
		var orgCode = jQuery("#${random}_accountBean_orgCode_id").val();
		var deptIds = jQuery("#${random}_accountBean_deptIds_id").val();
		var empTypes = jQuery("#${random}_accountBean_empTypes_id").val();
		var personName = jQuery("#${random}_accountBean_personName").val();
		var personCode = jQuery("#${random}_accountBean_personCode").val();
		var colColumns = jQuery("#${random}_accountBean_colColumns").val();
		var deptType = jQuery("#${random}_accountBean_a7").find("input:radio[name=deptType][checked=checked]").val();
		var deptLevelFrom = jQuery("#${random}_accountBean_deptLevelFrom").val();
		var deptLevelTo = jQuery("#${random}_accountBean_deptLevelTo").val();
		var deptDetailIds = jQuery("#${random}_accountBean_deptDetailIds_id").val();
		var gzTypeIds = jQuery("#${random}_accountBean_gzTypeIds_id").val();
		var personId = jQuery("#${random}_accountBean_personId").val();
		var personIdName = jQuery("#${random}_accountBean_personIdName").val();
		var includeUnCheck = jQuery("#${random}_accountBean_includeUnCheck").attr("checked");
		//打印控制
		var printer = jQuery("#${random}_accountBean_printer").val();
		var paperNumber = jQuery("#${random}_accountBean_paperNumber").val();
		var oriantation = jQuery("#${random}_accountBean_oriantation").val();
		var scale = jQuery("#${random}_accountBean_scale").val();
		var gzAccountFilter = {};
		if(periodTime){
			gzAccountFilter.periodTime = periodTime;
		}
		if(checkYear){
			gzAccountFilter.checkYear = checkYear;
		}
		if(checkNumber){
			gzAccountFilter.checkNumber = checkNumber;
		}
		if(fromPeriodTime){
			gzAccountFilter.fromPeriodTime = fromPeriodTime;
		}
		if(toPeriodTime){
			gzAccountFilter.toPeriodTime = toPeriodTime;
		}
		if(fromCheckYear){
			gzAccountFilter.fromCheckYear = fromCheckYear;
		}
		if(fromCheckNumber){
			gzAccountFilter.fromCheckNumber = fromCheckNumber;
		}
		if(toCheckYear){
			gzAccountFilter.toCheckYear = toCheckYear;
		}
		if(toCheckNumber){
			gzAccountFilter.toCheckNumber = toCheckNumber;
		}
		if(orgCode){
			gzAccountFilter.orgCode = orgCode;
		}
		if(deptIds){
			gzAccountFilter.deptIds = deptIds;
		}
		if(empTypes){
			gzAccountFilter.empTypes = empTypes;
		}
		if(personName){
			gzAccountFilter.personName = personName;
		}
		if(personCode){
			gzAccountFilter.personCode = personCode;
		}
		if(colColumns){
			gzAccountFilter.colColumns = colColumns;
		}
		if(deptType){
			gzAccountFilter.deptType = deptType;
		}
		if(deptLevelFrom){
			gzAccountFilter.deptLevelFrom = deptLevelFrom;
		}
		if(deptLevelTo){
			gzAccountFilter.deptLevelTo = deptLevelTo;
		}
		if(deptDetailIds){
			gzAccountFilter.deptDetailIds = deptDetailIds;
		}
		if(gzTypeIds){
			gzAccountFilter.gzTypeIds = gzTypeIds;
		}
		if(personId){
			gzAccountFilter.personId = personId;
		}
		if(personIdName){
			gzAccountFilter.personIdName = personIdName;
		}
		if(includeUnCheck){
			gzAccountFilter.includeUnCheck = includeUnCheck;
		}
		//打印
		if(printer){
			gzAccountFilter.printer = printer;
		}
		if(paperNumber){
			gzAccountFilter.paperNumber = paperNumber;
		}
		if(oriantation){
			gzAccountFilter.oriantation = oriantation;
		}
		if(scale){
			gzAccountFilter.scale = scale;
		}
		var gzAccountItemsStr = JSON.stringify(gzAccountItems);
		var gzAccountFilterStr = JSON.stringify(gzAccountFilter);
		var planId = jQuery("#${random}gz_planList").val();
 		var displayType = "${gzAccountDefine.displayType}";
 		menuClick('${menuId}');
 		url = encodeURI(url);
 		var modelStatusStr = jQuery("#${random}_gzAccountPlanModelStatusStr").val();
 		navTab.openTab("navTab${menuId}",url,{title : '${gzAccountDefine.defineName}',fresh : true,data : {
 			gzAccountItemsStr:gzAccountItemsStr,gzAccountFilterStr:gzAccountFilterStr,tabOpenType:"menuClick",
 			planId:planId,gzAccountCustomLayout:gzAccountCustomLayout,modelStatusStr:modelStatusStr
 		}});
 		$.pdialog.close('dialog${menuId}');
	}
	/* 下拉框的点击转移 */
	function gzItemMoveToRight(obj) {
		if(configurable != "true"){
			alertMsg.error("该帐表为不可配置类型！");
			return;
		}
		//1.获取要移动的option 
		var s_option = jQuery(obj);
		//2.在右侧的select中进行添加option
		jQuery("#${random}gz_items2").append(
				"<option value='" + s_option.val()
						+ "' >"
						+ s_option.text() + "</option>");
		//3.删除掉左侧的option
		s_option.remove();
		var gzAccountItemIndex = getJsonLength(gzAccountItems);
		gzAccountItems[s_option.val()] = {"name":s_option.text(),"oldName":s_option.text(),isThousandSeparat:"1",
				headerFontIndex:'1',fontIndex:'0',headerTextColor:'#000000',sn:gzAccountItemIndex};
	}
	function gzItemMoveToLeft(obj) {
		if(configurable != "true"){
			alertMsg.error("该帐表为不可配置类型！");
			return;
		}
		var leftItemArr = [];
		var leftItems = jQuery("#${random}gz_items1 option");
		if(leftItems){
			jQuery.each(leftItems,function(index,leftItem){
				var itemValue = jQuery(leftItem).val();
				leftItemArr.push(itemValue);
			});
		}
		//1.获取要移动的option 
		var s_option = jQuery(obj);
		var itemValue = s_option.val();
		var itemText = s_option.text(); 
		if(jQuery.inArray(itemValue, requiredItems) == -1){
			//2.在左侧的select中进行添加option
			jQuery("#${random}gz_items1").append(
					"<option value='" + s_option.val()
							+ "'>"
							+ s_option.text() + "</option>");
			//3.删除掉右侧的option
			s_option.remove();
			delete gzAccountItems[s_option.val()];
			leftItemArr.push(itemValue);
			//左侧初始化
			var allGzItemHtml = "";
			jQuery.each(allGzItems,function(index,gzItem){
					var itemCode = gzItem.itemCode;
					var itemName = gzItem.itemName;
					if(jQuery.inArray(itemCode, leftItemArr) > -1){
						allGzItemHtml += "<option value='" + itemCode
						+ "'>"+ itemName + "</option>";
					}
				});
			jQuery("#${random}gz_items1").html(allGzItemHtml);
			//gzAccountItems排序
			if(gzAccountItems){
				var index = 0;
				for(var itemValue in gzAccountItems){
					gzAccountItems[itemValue]["sn"] = index;
					index ++;
				}
			}
		}else{
			alertMsg.error(itemText+"为系统项！");
			return;
		}
	}
	/*方案列表修改*/
	function gzAccountPlanChange() {
		var curPlan = jQuery("#${random}gz_planList").val();
	}

	/*两个select初始化*/
	function gzAccountFormSelectLoad(){
		if(allGzItems){
			var allGzItemHtml = "";
			jQuery.each(allGzItems,function(index,gzItem){
				var itemCode = gzItem.itemCode;
				var itemName = gzItem.itemShowName;
					allGzItemHtml += "<option value='" + itemCode
					+ "'>"
					+ itemName + "</option>"
			});
			jQuery("#${random}gz_items1").html(allGzItemHtml);
			gzAccountItems = {};
			if(requiredItems){
				var gzAccountItemIndex = 0;
				jQuery.each(requiredItems,function(index,itemCode){
					var colName = jQuery("#${random}gz_items1 option[value='"+ itemCode + "']").text();
					jQuery("#${random}gz_items1 option[value='"+ itemCode + "']").remove();
					//jQuery("#${random}gz_items2").append("<option value='" + itemCode + "'"
					//		+ " >" + colName + "</option>");
					gzAccountItems[itemCode] = {"name":colName,"oldName":colName,isThousandSeparat:"1",
							headerFontIndex:'1',fontIndex:'0',headerTextColor:'#000000',sn:gzAccountItemIndex};
					gzAccountItemIndex ++;
				});
				var gzAccountItemsTemp = {};
				if(gzAccountSortItems){
					for(var itemCode in gzAccountSortItems){
						if(gzAccountItems[itemCode]){
							gzAccountItemsTemp[itemCode] = gzAccountItems[itemCode];
							delete gzAccountItems[itemCode];
						}
					}
				}
				if(gzAccountItems){
					for(var itemCode in gzAccountItems){
						if(gzAccountItems[itemCode]){
							gzAccountItemsTemp[itemCode] = gzAccountItems[itemCode];
						}
					}
				}
				gzAccountItems = cloneObj(gzAccountItemsTemp);
				//gzAccountItems排序
				if(gzAccountItems){
					var gzItemHtml = "";
					var gzAccountItemIndex = 0;
					for(var itemCode in gzAccountItems){
						var gzAccountItem = gzAccountItems[itemCode];
						gzAccountItem.sn = gzAccountItemIndex;
						var colName = gzAccountItem.name;
						gzAccountItemIndex ++;
						gzItemHtml += "<option value='" + itemCode
									+ "'>" + colName + "</option>";
					}
					jQuery("#${random}gz_items2").html(gzItemHtml);
				}
			}
		}
	}
	function InitGzItem(items) {
		gzAccountItems = {};
		jQuery("#${random}gz_items2").empty();
		jQuery("#${random}gz_items1").empty();
		gzAccountFormSelectLoad();
		if(items){
			for(var index in items){
				var item = items[index];
				var itemCode = item.itemCode;
				var colName = item.colName;
				var colNameTemp = jQuery("#${random}gz_items1 option[value='"+ itemCode + "']").text();
				if(colNameTemp){
					colName = colNameTemp;
				}else{
					continue;
				}
				var colSn = item.colSn;
				var isThousandSeparat = item.isThousandSeparat;
				var fontIndex = item.fontIndex;
				var headerFontIndex = item.headerFontIndex;
				var headerTextColor = item.headerTextColor;
				jQuery("#${random}gz_items1 option[value='"+ itemCode + "']").remove();
				//jQuery("#${random}gz_items2").append("<option value='" + itemCode
				//			+ "'>" + colName + "</option>");
				gzAccountItems[itemCode] = {"name":item.colName,"oldName":colName
						,isThousandSeparat:"1",headerFontIndex:'1',fontIndex:'0',headerTextColor:'#000000',sn:index};
				if(isThousandSeparat){
					gzAccountItems[itemCode].isThousandSeparat = isThousandSeparat;
				}
				if(fontIndex){
					gzAccountItems[itemCode].fontIndex = fontIndex;
				}
				if(headerFontIndex){
					gzAccountItems[itemCode].headerFontIndex = headerFontIndex;
				}
				if(headerTextColor){
					gzAccountItems[itemCode].headerTextColor = headerTextColor;
				}
			}
			var gzAccountItemsTemp = {};
			if(gzAccountSortItems){
				for(var itemCode in gzAccountSortItems){
					if(gzAccountItems[itemCode]){
						gzAccountItemsTemp[itemCode] = gzAccountItems[itemCode];
						delete gzAccountItems[itemCode];
					}
				}
			}
			if(gzAccountItems){
				for(var itemCode in gzAccountItems){
					if(gzAccountItems[itemCode]){
						gzAccountItemsTemp[itemCode] = gzAccountItems[itemCode];
					}
				}
			}
			gzAccountItems = cloneObj(gzAccountItemsTemp);
			//gzAccountItems排序
			if(gzAccountItems){
				var gzItemHtml = "";
				var gzAccountItemIndex = 0;
				for(var itemCode in gzAccountItems){
					var gzAccountItem = gzAccountItems[itemCode];
					gzAccountItem.sn = gzAccountItemIndex;
					var colName = gzAccountItem.name;
					gzAccountItemIndex ++;
					gzItemHtml += "<option value='" + itemCode
								+ "'>" + colName + "</option>";
				}
				jQuery("#${random}gz_items2").html(gzItemHtml);
			}
		}
	}

	function gzAccountPlanClearContent() {
		jQuery("#${random}plan_Name").val("");
		jQuery("#${random}_accountBean_toPulbic").removeAttr("checked");
		jQuery("#${random}_accountBean_toDepartment").removeAttr("checked");
		jQuery("#${random}_accountBean_toRole").removeAttr("checked");
		jQuery("#${random}_accountBean_periodTime").val("");
		jQuery("#${random}_accountBean_checkYear").val("");
		jQuery("#${random}_accountBean_checkNumber option:selected").removeAttr("selected");
		jQuery("#${random}_accountBean_fromPeriodTime").val("");
		jQuery("#${random}_accountBean_toPeriodTime").val("");
		jQuery("#${random}_accountBean_fromCheckYear").val("");
		jQuery("#${random}_accountBean_fromCheckNumber option:selected").removeAttr("selected");
		jQuery("#${random}_accountBean_toCheckYear").val("");
		jQuery("#${random}_accountBean_toCheckNumber option:selected").removeAttr("selected");
		jQuery("#${random}_accountBean_orgCode").val("");
		jQuery("#${random}_accountBean_orgCode_id").val("");
		jQuery("#${random}_accountBean_deptIds").val("");
		jQuery("#${random}_accountBean_deptIds_id").val("");
		jQuery("#${random}_accountBean_empTypes").val("");
		jQuery("#${random}_accountBean_empTypes_id").val("");
		jQuery("#${random}_accountBean_personName").val("");
		jQuery("#${random}_accountBean_personCode").val("");
		jQuery("#${random}_accountBean_a7").find("input:radio[name=deptType]").removeAttr("checked");
		jQuery("#${random}_accountBean_deptLevelFrom option:selected").removeAttr("selected");
		jQuery("#${random}_accountBean_deptLevelTo option:selected").removeAttr("selected");
		jQuery("#${random}_accountBean_deptDetailIds").val("");
		jQuery("#${random}_accountBean_deptDetailIds_id").val("");
		jQuery("#${random}_accountBean_personIdName").val("");
		jQuery("#${random}_accountBean_gzTypeIds").val();
		jQuery("#${random}_accountBean_gzTypeIds_id").val();
		jQuery("#${random}_accountBean_personId").val("");
		jQuery("#${random}_accountBean_includeUnCheck").removeAttr("checked");
	}

	function InitFilters(gzAccountPlanFilters) {
		var orgInit = "";
		var deptInit = "";
		var personTypeInit = "";
		var deptTypeInit = "";
		var deptDetailInit = "";
		var gzTypeInit = "";
		jQuery.each(gzAccountPlanFilters,
				function(key, gzAccountPlanFilter) {
					var filterCode = gzAccountPlanFilter.filterCode;
					var filterValue = gzAccountPlanFilter.filterValue;
					switch (filterCode) {
					case "orgCode":
						orgInit = filterValue;
						jQuery("#${random}_accountBean_" + filterCode + "_id").val(filterValue);
						break;
					case "deptIds":
						deptInit = filterValue;
						jQuery("#${random}_accountBean_" + filterCode + "_id").val(filterValue);
						break;
					case "empTypes":
						personTypeInit = filterValue;
						jQuery("#${random}_accountBean_" + filterCode + "_id").val(filterValue);
						break;
					case "deptType":
						deptTypeInit = filterValue;
						break;
					case "deptDetailIds":
						deptDetailInit = filterValue;
						jQuery("#${random}_accountBean_" + filterCode + "_id").val(filterValue);
						break;
					case "gzTypeIds":
						gzTypeInit = filterValue;
						jQuery("#${random}_accountBean_" + filterCode + "_id").val(filterValue);
						break;
					case "includeUnCheck":
						jQuery("#${random}_accountBean_" + filterCode).attr("checked","checked");
						break;
					default:
						jQuery("#${random}_accountBean_" + filterCode).val(filterValue);
						break;
					}
					//jQuery("#${random}_accountBean_" + filterCode).find(":hidden").val("");
				});
		gzAccountPlanFormInitTreeSelect(orgInit, deptInit, personTypeInit,deptTypeInit,deptDetailInit,gzTypeInit);
	}
	//树初始化
	function gzAccountPlanFormInitTreeSelect(orgInit, deptInit, personTypeInit,deptTypeInit,deptDetailInit,gzTypeInit) {
		//单位
		var sql = "select orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon from T_Org where disabled=0 and orgCode<>'XT' ORDER BY orgCode";
		jQuery("#${random}_accountBean_orgCode").treeselect({
			optType : "single",
			dataType : 'sql',
			sql : sql,
			exceptnullparent :false,
			lazy : false,
			minWidth : '120px',
			selectParent : false,
			ifr:true,
			initSelect : orgInit
		});
		//部门
		sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department where disabled=0 and deptId <> 'XT'"
		sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ";
		sql += " ORDER BY orderCol ";
		jQuery("#${random}_accountBean_deptIds").treeselect({
			optType : "multi",
			dataType : 'sql',
			sql : sql,
			exceptnullparent :false,
			lazy : false,
			minWidth : '180px',
			selectParent : false,
			ifr:true,
			initSelect : deptInit
		});
		//人员类别
		jQuery("#${random}_accountBean_empTypes").treeselect({
			dataType : "sql",
			optType : "multi",
			sql : "SELECT t.name id,t.name,(select name from t_personType b where b.id =t.parentType)  parent FROM t_personType t where t.disabled=0  ORDER BY t.code",
			exceptnullparent : false,
			selectParent : false,
			lazy : false,
			minWidth : '120px',
			initSelect : personTypeInit
		});
		//工资类别
		jQuery("#${random}_accountBean_gzTypeIds").treeselect({
			dataType : "sql",
			optType : "multi",
			sql : "SELECT gzTypeId id,gzTypeName name FROM gz_gzType WHERE gzTypeId <> 'XT'",
			exceptnullparent : false,
			selectParent : false,
			lazy : false,
			minWidth : '120px',
			ifr:true,
			initSelect : gzTypeInit
		});
		//部门类别选择
		jQuery("#${random}_accountBean_a7").find("input:radio[name=deptType]").click(function(){
			var deptType = jQuery(this).val();
			if(deptType=='level'){
				jQuery("#${random}_accountBean_deptLevelFrom").removeAttr("disabled");
				jQuery("#${random}_accountBean_deptLevelTo").removeAttr("disabled");
			}else if(deptType=='end'){
				jQuery("#${random}_accountBean_deptLevelFrom").attr("disabled","true").val("");
				jQuery("#${random}_accountBean_deptLevelTo").attr("disabled","true").val("");
			}else{
				jQuery("#${random}_accountBean_deptLevelFrom").attr("disabled","true").val("");
				jQuery("#${random}_accountBean_deptLevelTo").attr("disabled","true").val("");
			}
			jQuery("#${random}_accountBean_deptDetailIds").val("");
			jQuery("#${random}_accountBean_deptDetailIds_id").val("");
		});
		if(deptTypeInit||deptDetailInit){
			jQuery("#${random}_accountBean_a7").find("input:radio[name=deptType][value="+deptTypeInit+"]").attr("checked","checked");
			var deptLevelFrom = jQuery("#${random}_accountBean_deptLevelFrom").val();
			var deptLevelTo = jQuery("#${random}_accountBean_deptLevelTo").val();
			var sql = "";
			if(!deptDetailInit){
				deptDetailInit = "";
			}
			if(deptTypeInit=='level'){
				jQuery("#${random}_accountBean_deptLevelFrom").removeAttr("disabled");
				jQuery("#${random}_accountBean_deptLevelTo").removeAttr("disabled");
				if(!deptLevelFrom&&!deptLevelTo){
					sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
					sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
					sql += " ORDER BY orderCol ";
				}else if(!deptLevelFrom){
					sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
					sql += " and clevel <= '" + deptLevelTo +"'";
					sql += " union select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'1' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
					sql += " and clevel > '" + deptLevelTo +"'";
					sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
					sql += " ORDER BY orderCol ";
				}else if(!deptLevelTo){
					sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
					sql += " and clevel >= '"+ deptLevelFrom + "'";
					sql += " union select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'1' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
					sql += " and clevel < '"+ deptLevelFrom + "'";
					sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
					sql += " ORDER BY orderCol ";
				}else{
					sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
					sql += " and clevel >= '"+ deptLevelFrom + "' and clevel <= '" + deptLevelTo +"'";
					sql += " union select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'1' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
					sql += " and(clevel < '"+ deptLevelFrom + "' or clevel > '" + deptLevelTo +"')";
					sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
					sql += " ORDER BY orderCol ";
				}
				jQuery("#${random}_accountBean_deptDetailIds").treeselect({
					optType : "multi",
					dataType : 'sql',
					sql : sql,
					exceptnullparent : true,
					lazy : false,
					minWidth : '180px',
					selectParent : true,
					ifr:true,
					initSelect:deptDetailInit
				});
			}else if(deptTypeInit=='end'){
				jQuery("#${random}_accountBean_deptLevelFrom").attr("disabled","true");
				jQuery("#${random}_accountBean_deptLevelTo").attr("disabled","true");
				sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
				sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
				sql += " ORDER BY orderCol ";
				jQuery("#${random}_accountBean_deptDetailIds").treeselect({
						optType : "multi",
						dataType : 'sql',
						sql : sql,
						exceptnullparent : false,
						lazy : false,
						minWidth : '180px',
						selectParent : false,
						ifr:true,
						initSelect:deptDetailInit
				});
			}else{
				jQuery("#${random}_accountBean_deptLevelFrom").attr("disabled","true");
				jQuery("#${random}_accountBean_deptLevelTo").attr("disabled","true");
				sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
				sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
				sql += " ORDER BY orderCol ";
				jQuery("#${random}_accountBean_deptDetailIds").treeselect({
						optType : "multi",
						dataType : 'sql',
						sql : sql,
						exceptnullparent : true,
						lazy : false,
						minWidth : '180px',
						selectParent : true,
						ifr:true,
						chkboxType: {"Y":"", "N":""},
						initSelect:deptDetailInit
				});
			}
		}
		//部门明细
		jQuery("#${random}_accountBean_deptDetailIds").bind("focus",function(){
			var deptType = jQuery("#${random}_accountBean_a7").find("input:radio[name=deptType][checked=checked]").val();
			var deptLevelFrom = jQuery("#${random}_accountBean_deptLevelFrom").val();
			var deptLevelTo = jQuery("#${random}_accountBean_deptLevelTo").val();
			var sql = "";
			if(deptType=='level'){
				if(!deptLevelFrom&&!deptLevelTo){
					sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
					sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
					sql += " ORDER BY orderCol ";
				}else if(!deptLevelFrom){
					sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
					sql += " and clevel <= '" + deptLevelTo +"'";
					sql += " union select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'1' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
					sql += " and clevel > '" + deptLevelTo +"'";
					sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
					sql += " ORDER BY orderCol ";
				}else if(!deptLevelTo){
					sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
					sql += " and clevel >= '"+ deptLevelFrom + "'";
					sql += " union select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'1' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
					sql += " and clevel < '"+ deptLevelFrom + "'";
					sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
					sql += " ORDER BY orderCol ";
				}else{
					sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
					sql += " and clevel >= '"+ deptLevelFrom + "' and clevel <= '" + deptLevelTo +"'";
					sql += " union select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'1' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
					sql += " and(clevel < '"+ deptLevelFrom + "' or clevel > '" + deptLevelTo +"')";
					sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
					sql += " ORDER BY orderCol ";
				}
				jQuery("#${random}_accountBean_deptDetailIds").treeselect({
					optType : "multi",
					dataType : 'sql',
					sql : sql,
					exceptnullparent : true,
					lazy : false,
					minWidth : '180px',
					selectParent : true,
					ifr:true,
					initSelect:null
				});
			}else if(deptType=='end'){
				sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
				sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
				sql += " ORDER BY orderCol ";
				jQuery("#${random}_accountBean_deptDetailIds").treeselect({
						optType : "multi",
						dataType : 'sql',
						sql : sql,
						exceptnullparent : false,
						lazy : false,
						minWidth : '180px',
						selectParent : false,
						ifr:true,
						initSelect:null
				});
			}else{
				sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
				sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
				sql += " ORDER BY orderCol ";
				jQuery("#${random}_accountBean_deptDetailIds").treeselect({
						optType : "multi",
						dataType : 'sql',
						sql : sql,
						exceptnullparent : true,
						lazy : false,
						minWidth : '180px',
						selectParent : true,
						ifr:true,
						chkboxType: {"Y":"", "N":""},
						initSelect:null
				});
			}
		});
		//人员
		var sqlTemp = "SELECT tp.personId AS personId,tp.personCode AS personCode,tp.name as name,CASE tp.disabled WHEN 1 THEN '是' ELSE '否' END AS disabled,";
		sqlTemp += " td.name AS deptName,td.deptCode AS deptCode,org.orgCode AS orgCode,org.orgname AS orgname FROM t_person tp ";
		sqlTemp += " LEFT JOIN t_department td ON td.deptId = tp.dept_id ";
		sqlTemp += " LEFT JOIN T_Org org ON td.orgCode = org.orgCode ";
		sqlTemp += " WHERE td.disabled = '0' AND td.deptId <> 'XT' AND tp.personId <> 'XT' ";
		jQuery("#${random}_accountBean_personIdName").combogrid({
	        url : 'comboGridSqlList',
	        queryParams : {
	            sql : sqlTemp,
	            cloumns : 'tp.personCode,tp.name,td.name,td.deptCode'
	        },
	        autoFocus : false,
	        showOn : false, 
	        ifr:true,
	        rows:10,
	        width:540,
	        sidx:"orgCode,deptCode,personCode",
	        colModel : [ {
	            'columnName' : 'PERSONID',
	            'width' : '15',
	            'align' : 'left',
	            'label' : '人员Id',
	            hidden : true
	        }, {
	            'columnName' : 'PERSONCODE',
	            'width' : '15',
	            'align' : 'left',
	            'label' : '人员编码',
	            hidden : false
	        }, {
	            'columnName' : 'NAME',
	            'width' : '15',
	            'align' : 'left',
	            'label' : '姓名',
	            hidden : false
	        }, {
	            'columnName' : 'DISABLED',
	            'width' : '8',
	            'align' : 'left',
	            'label' : '停用',
	            hidden : false
	        }, {
	            'columnName' : 'DEPTCODE',
	            'width' : '15',
	            'align' : 'left',
	            'label' : '部门编码',
	            hidden : false
	        }, {
	            'columnName' : 'DEPTNAME',
	            'width' : '25',
	            'align' : 'left',
	            'label' : '部门名称',
	            hidden : false 
	        }, {
	            'columnName' : 'ORGNAME',
	            'width' : '15',
	            'align' : 'left',
	            'label' : '单位',
	            hidden : false
	        }
	        ],
	        _create: function( event, item ) {
	        },
	        focus: function( event, ui ) {
	        },
	        select : function(event, ui) {
	        	jQuery("#${random}_accountBean_personId").val(ui.item.PERSONID).blur();
	        	jQuery("#${random}_accountBean_personIdName").val(ui.item.NAME).blur();
	            return false;
	        }
	    });
	}

	//高级按钮的方法 
	function addPlanItems() {
		var gzTypeId = jQuery("#${random}_curGzType_gzTypeName").val();
		var url = "gzAccountByPlanItem?gzTypeId=" + gzTypeId+"&random=${random}";
		url = encodeURI(url);
		$.pdialog.open(url, 'addGzAccountPlanItem', '工资项信息', {
			mask : true,
			width : 650,
			height : 400,
			maxable : true,
			resizable : true,
			ifr:true
		});
		stopPropagation();
	}
	/*工资类别变化*/
	function accountBeanGzTypeChange() {
		gzAccountItems = {};
		gzAccountPlanClearContent();
		/*必填项*/
		var requiredFilterStr = "${gzAccountDefine.requiredFilter}";
		if (requiredFilterStr) {
			var requiredFilters = requiredFilterStr.split(",");
			jQuery.each(requiredFilters, function(key, value) {
				jQuery("#${random}_accountBean_" + value).find(".required").removeClass("required");
			});
		}
		var gzTypeId = jQuery("#${random}_curGzType_gzTypeName").val();
		jQuery("#${random}gz_planList").empty();
		jQuery("#${random}gz_items2").empty();
		jQuery("#${random}gz_items1").empty();
		jQuery.ajax({
					url : 'gzAccountPlanGztypeChange',
					data : {
						accountType : "${accountType}",
						gzTypeId : gzTypeId
					},
					type : 'post',
					dataType : 'json',
					async : true,
					error : function(data) {
						alertMsg.error("系统错误！");
					},
					success : function(data) {
						var gzAccountPlans = data.gzAccountPlans;
						var gzAccountPlanSysStr = data.gzAccountPlanSysStr;
						var modelStatusStr = data.modelStatusStr;
						jQuery("#${random}_gzAccountPlanModelStatusStr").val(modelStatusStr);
						sysGzPlanObj = {};
						if(gzAccountPlanSysStr){
							sysGzPlanObj = JSON.parse(gzAccountPlanSysStr);
						}
						var gzItems = data.gzItems;
						var issueType = data.issueType;
						if (issueType == "1") {
							jQuery("#${random}_accountBean_a1_yearDiv").show();
							jQuery("#${random}_accountBean_a1_periodDiv").hide();
							jQuery("#${random}_accountBean_c1_yearDiv").show();
							jQuery("#${random}_accountBean_c1_periodDiv").hide();
						} else {
							jQuery("#${random}_accountBean_a1_yearDiv").hide();
							jQuery("#${random}_accountBean_a1_periodDiv").show();
							jQuery("#${random}_accountBean_c1_yearDiv").hide();
							jQuery("#${random}_accountBean_c1_periodDiv").show();
						}
						/*必填项*/
						//  					var requiredFilterStr = "${gzAccountDefine.requiredFilter}";
						if (requiredFilterStr) {
							var requiredFilters = requiredFilterStr.split(",");
							jQuery.each(requiredFilters, function(key, value) {
								jQuery("#${random}_accountBean_" + value).find(
										"input:visible").addClass("required");
							});
						}
						allGzItems = gzItems;
						if (gzItems) {
							var gzItemHtml = "";
							jQuery.each(gzItems, function(key, gzItem) {
								gzItemHtml += '<option value="'+ gzItem.itemCode
									+ '" ondblclick="gzItemMoveToRight(this)">'
									+ gzItem.itemShowName + '</option>';
							});
							jQuery("#${random}gz_items1").html(gzItemHtml);
							gzAccountItems = {};
							if(requiredItems){
								var gzAccountItemIndex = 0;
								jQuery.each(requiredItems,function(index,itemCode){
									var colName = jQuery("#${random}gz_items1 option[value='"+ itemCode + "']").text();
									jQuery("#${random}gz_items1 option[value='"+ itemCode + "']").remove();
									jQuery("#${random}gz_items2").append("<option value='" + itemCode
												+ "'>"
												+ colName + "</option>");
									gzAccountItems[itemCode] = {"name":colName,"oldName":colName,isThousandSeparat:"1",
											headerFontIndex:'1',fontIndex:'0',headerTextColor:'#000000',sn:gzAccountItemIndex};
									gzAccountItemIndex ++;
								});
							}
						}
						if (gzAccountPlans) {
							var gzAccountPlanHtml = "";
							jQuery.each(gzAccountPlans,function(key, gzAccountPlan) {
												gzAccountPlanHtml += '<option value="'+gzAccountPlan.planId+'" >'
														+ gzAccountPlan.planName
														+ '</option>';
											});
							jQuery("#${random}gz_planList").html(gzAccountPlanHtml);
							//初始化方案 
							var options = jQuery("#${random}gz_planList option");
							if (options.length > 0) {
								//设置第一个为选中状态
								jQuery("#${random}gz_planList option").eq(0).attr('selected', 'true');
								planInit();
							}
						}
					}
				});
	}
	/*删除方案*/
	function gzAccountPlanDel(){
		var selectItem = jQuery("#${random}gz_planList").find("option:selected");
		var planName = selectItem.text();
		var planId = selectItem.val();
		if(sysGzPlanObj[planName]){
			alertMsg.error("系统方案不能删除！");
			return;
		}
		if(!planId){
			alertMsg.error("请选择方案！");
			return;
		}
		alertMsg.confirm("确认要删除该方案?",{
    		okCall:function(){
    			var url = "gzAccountPlanGridEdit?planId="+planId+"&oper=del";  
    			url = encodeURI(url);
		    	$.post(url,{},function(data){
		    		formCallBack(data);
		    		if(data.statusCode == 200){
		    			jQuery("#${random}gz_planList option[value='"+ planId + "']").remove();
		    		}
		    	});
    		}
		});
	}
	//打印预览
	function gzAccountPlanPrintView(){
		printViewShow = "reLoad";
		//初始化打印预览treeList
		var reverseColumn = "${gzAccountDefine.reverseColumn}";//反转
		if(reverseColumn){//反转
			initGzAccountReportPreviewReverseGrid("reLoad");
		}else{
			initGzAccountReportPreviewGrid("reLoad");
		}
	}
	//排序
	function sortPlanItems(){
		var winTitle = '工资项排序';
		var url = "sortGzAccountPlan?random=${random}";
		url = encodeURI(url);
		$.pdialog.open(url,'sortGzAccountPlan',winTitle, {mask:true,width : 650,height : 400,maxable:true,resizable:true,ifr:true});
		stopPropagation();
	}
</script>
<div class="page">
	<div class="pageContent">
		<form id="${random}gzAccountPlanForm" method="post" action=""
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div>
				<div style="width: 1px;height: 1px;" id="${random}_gzAccountPlanReport_gridtable_container"></div>
				<s:hidden key="gzAccountDefine.defineId"></s:hidden>
				<input type="hidden" id="${random}_gzAccountPlanModelStatusStr" value='<s:property value="modelStatusStr" escapeHtml="false"/>'>
				<input type="hidden" id="${random}_gzAccountPlanSysStr" value='<s:property value="gzAccountPlanSysStr" escapeHtml="false"/>'>
				<input type="hidden" id="${random}_gzAccountCustomLayout" value='<s:property value="gzAccountCustomLayout" escapeHtml="false"/>'>
				<input type="hidden" id="${random}_gzItemsJsonStr" value='<s:property value="gzItemsJsonStr" escapeHtml="false"/>'>
			</div>
			<div class="pageFormContent" layoutH="57" style="width:700px;background: #e4ebf6;">
				<div style="float: left;">
					<table>
						<tr>
							<td><span>工资类别:</span></td>
							<td colspan="2"><select onchange="accountBeanGzTypeChange()"
								id="${random}_curGzType_gzTypeName" name='accountBean.gzType'
								style="width: 120px;">
									<c:forEach var="gzType" items="${gzTypeList}">
										<option value="${gzType.gzTypeId}" issueType="${gzType.issueType}">${gzType.gzTypeName}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr height="10px">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td valign="top">方案列表:</td>
							<td colspan="2"><select id="${random}gz_planList" size="8"
								style="width: 120px;" onchange="gzAccountPlanChange()">
									<c:forEach var="gzAccountPlan" items="${gzAccountPlans}">
										<option value="${gzAccountPlan.planId}">${gzAccountPlan.planName}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr height="10px">
							<td colspan="3">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td style="text-align: top"><span style="margin-top: 20px">方案名称:</span>
							</td>
							<td colspan="2"><input id="${random}plan_Name" type="text"
								style="width: 120px;" name="accountBean.planName" /></td>
						</tr>
						<tr height="10px">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td colspan="3">
								<table>
									<tr>
										<td>公共:</td>
										<td><input id="${random}_accountBean_toPublic"
											type="checkbox" style="margin-top: 0px" /></td>
										<td>部门:</td>
										<td><input id="${random}_accountBean_toDepartment"
											type="checkbox" style="margin-top: 0px" /></td>
										<td>角色:</td>
										<td><input id="${random}_accountBean_toRole"
											type="checkbox" style="margin-top: 0px" /></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr height="10px">
							<td colspan="3">
								&nbsp;
							</td>
						</tr>
						<tr height="10px">
							<td colspan="3">
								保存方案 :<input id="${random}_accountBean_savePlan"
											type="checkbox">
								<div class="buttonActive" id="${random}_accountBean_planDel" style="float: right;margin-right: 5px;width: 60px;">
									<div class="buttonContent">
										<button type="button" onclick="gzAccountPlanDel()">删除方案</button>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="tabs" currentIndex="0" eventType="click"
					style="float: left;margin-left: 10px;">
					<div class="tabsHeader">
						<div class="tabsHeaderContent">
							<ul>
								<li><a href="javascript:;"><span>查询设置</span></a></li>
								<li><a href="javascript:;"><span>打印设置</span></a></li>
							</ul>
						</div>
					</div>

					<div class="tabsContent" layoutH="96">
						<div style="width:485px">
							<fieldset style="height: 246px;width:250px; float: left; margin: 3px; color: #333; border: #06c solid 1px;">
								<legend style="color: #06c; font-weight: 800; background: #fff;">工资项配置</legend>
								<div style="width: 250px">
									<div class="buttonActive" style="margin-left: 170px;">
										<div class="buttonContent">
											<button type="button" onclick="sortPlanItems()">排序</button>
										</div>
									</div>
									<div class="buttonActive" style="margin-left: 5px;">
										<div class="buttonContent">
											<button type="button" onclick="addPlanItems()">高级</button>
										</div>
									</div>
									<div style="float: left;">
										<div style="float: left; margin-left: 5px;">
											<select id="${random}gz_items1" size="12" multiple="multiple" style="width: 100px">
											</select>
										</div>
										<div style="float: left;">
											<div style="width: 40px">
												<button id="${random}gz_planForm_toRight" style="width: 40px; margin-top: 20px;" type="button"
													onclick="gzItemtoRight()">&gt;</button>
											</div>
											<div style="width: 40px">
												<button id="${random}gz_planForm_AlltoRight" style="width: 40px; margin-top: 20px" type="button"
													onclick="gzItemAlltoRight()">&gt;&gt;</button>
											</div>
											<div style="width: 40px">
												<button id="${random}gz_planForm_toLeft" style="width: 40px; margin-top: 20px" type="button"
													onclick="gzItemtoLeft()">&lt;</button>
											</div>
											<div style="width: 40px">
												<button id="${random}gz_planForm_ALLtoLeft" style="width: 40px; margin-top: 20px" type="button"
													onclick="gzItemALLtoLeft()">&lt;&lt;</button>
											</div>
										</div>
										<div style="float: left;">
											<div>
												<select id="${random}gz_items2" multiple="multiple" size="12"  style="width: 100px;">
												</select>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset style="height: 246px;width:205px; float:left;margin:3px;color: #333;border: #06c solid 1px;">
								<legend  style="color: #06c;font-weight: 800;background: #fff;">查询条件</legend>

								<div id="${random}_accountBean_a1" style="display: none;">
									<div style="margin-top: 5px;"
										id="${random}_accountBean_a1_periodDiv">
										<label style="width:52px;">期间:</label>
										<input id="${random}_accountBean_periodTime" style="width: 45px;"
											onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}">
									</div>
									<div style="margin-top: 5px;"
										id="${random}_accountBean_a1_yearDiv">
										<label style="width:52px;">发放次数:</label>
										<input id="${random}_accountBean_checkYear" style="width: 35px;"
											onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyy'});}">
										年
										<select id="${random}_accountBean_checkNumber" name="accountBean.checkNumber" style="float:none">
											<option value=''></option>
											<option value='1'>1</option>
											<option value='2'>2</option>
											<option value='3'>3</option>
											<option value='4'>4</option>
											<option value='5'>5</option>
											<option value='6'>6</option>
											<option value='7'>7</option>
											<option value='8'>8</option>
											<option value='9'>9</option>
											<option  value='10'>10</option>
											<option value='11'>11</option>
											<option value='12'>12</option>
											<option value='13'>13</option>
										</select>次
									</div>
								</div>
								<div id="${random}_accountBean_c1" style="display: none;">
									<div style="margin-top: 5px;"
										id="${random}_accountBean_c1_periodDiv">
										<label style="width:52px;">期间:</label>
										从<input id="${random}_accountBean_fromPeriodTime" style="width: 43px;"
											onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}">
										至<input id="${random}_accountBean_toPeriodTime" style="width: 43px;"
											onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}">
									</div>
									<div id="${random}_accountBean_c1_yearDiv">
										<div style="margin-top: 5px;">
										<label style="width:52px;">发放次数:</label>
											从
											<input id="${random}_accountBean_fromCheckYear" style="width: 35px;"
											onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyy'});}">
										年
										<select id="${random}_accountBean_fromCheckNumber" style="float:none">
											<option value=''></option>
											<option value='1'>1</option>
											<option value='2'>2</option>
											<option value='3'>3</option>
											<option value='4'>4</option>
											<option value='5'>5</option>
											<option value='6'>6</option>
											<option value='7'>7</option>
											<option value='8'>8</option>
											<option value='9'>9</option>
											<option value='10'>10</option>
											<option value='11'>11</option>
											<option value='12'>12</option>
											<option value='13'>13</option>
										</select>次
										</div>
										<div style="margin-top: 2px;">
											<label style="width:52px;">&nbsp;&nbsp;</label>
											至
											<input id="${random}_accountBean_toCheckYear" style="width: 35px;"
											onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyy'});}">
											年
										<select id="${random}_accountBean_toCheckNumber" style="float:none">
											<option value=''></option>
											<option value='1'>1</option>
											<option value='2'>2</option>
											<option value='3'>3</option>
											<option value='4'>4</option>
											<option value='5'>5</option>
											<option value='6'>6</option>
											<option value='7'>7</option>
											<option value='8'>8</option>
											<option value='9'>9</option>
											<option value='10'>10</option>
											<option value='11'>11</option>
											<option value='12'>12</option>
											<option value='13'>13</option>
										</select>次
										</div>
									</div>
								</div>
								<div style="margin-top: 5px;display: none;" id="${random}_accountBean_a2">
									<label style="width:52px;">单位:</label>
									<input id="${random}_accountBean_orgCode" style="width: 100px;" />
									<input id="${random}_accountBean_orgCode_id" type="hidden"/>
								</div>
								<div style="margin-top: 5px;display: none;" id="${random}_accountBean_a3">
									<label style="width:52px;">部门:</label>
									<input id="${random}_accountBean_deptIds" style="width: 100px;" /> 
									<input id="${random}_accountBean_deptIds_id" type="hidden" />
								</div>
								<div style="margin-top: 5px;display: none;" id="${random}_accountBean_a4">
									<label style="width:52px;">人员类别:</label>
									<input id="${random}_accountBean_empTypes" style="width: 100px; " />
									<input type="hidden" id="${random}_accountBean_empTypes_id"/>
								</div>
								<div style="margin-top: 5px;display: none;" id="${random}_accountBean_a5">
									<label style="width:52px;">人员姓名:</label> 
									<input id="${random}_accountBean_personName" style="width: 100px;">
								</div>
								<div style="margin-top: 5px;display: none;" id="${random}_accountBean_a6">
									<label style="width:52px;">人员编码: </label>
									<input id="${random}_accountBean_personCode" style="width: 100px;">
								</div>
								<div style="margin-top: 5px;display: none;" id="${random}_accountBean_a7">
									<label style="width:52px;">部门: </label>
									<input type="radio" name="deptType" value="all"/>全部
									<input type="radio" name="deptType" value="end"/>末级
									<input type="radio" name="deptType" value="level"/>级次
									从<select id="${random}_accountBean_deptLevelFrom" style="float: none;" disabled="disabled">
										<option value=""></option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
									</select>
									到<select id="${random}_accountBean_deptLevelTo" style="float: none;" disabled="disabled">
										<option value=""></option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
									</select>
								</div>
								<div style="margin-top: 5px;display: none;" id="${random}_accountBean_a8">
									<label style="width:52px;">部门明细: </label>
									<input  id="${random}_accountBean_deptDetailIds" style="width: 100px;">
									<input  id="${random}_accountBean_deptDetailIds_id" type="hidden">
								</div>
								<div style="margin-top: 5px;display: none;" id="${random}_accountBean_a9">
									<label style="width:52px;">工资类别: </label>
									<input  id="${random}_accountBean_gzTypeIds" style="width: 100px;">
									<input  id="${random}_accountBean_gzTypeIds_id" type="hidden">
								</div>
								<div style="margin-top: 5px;display: none;" id="${random}_accountBean_a10">
									<label style="width:52px;">人员: </label>
									<input id="${random}_accountBean_personIdName" style="width: 100px;">
									<input id="${random}_accountBean_personId" type="hidden">
								</div>
								<div style="margin-top: 5px;display: none;" id="${random}_accountBean_a11">
									<label style="width:52px;">含未审核: </label>
									<span style="width: 100px;display: block;">
										<input id="${random}_accountBean_includeUnCheck" type="checkBox">
									</span>
								</div>
							</fieldset>
						</div>
						<div style="width:485px">
								<div style="margin-top: 5px;display: none;width: 400px;" id="${random}_accountBean_d1">
									<label style="width:65px;">每行显示数: </label>
									<input id="${random}_accountBean_colColumns" style="width: 100px;">
								</div>
								<div style="margin-top: 5px;width: 400px;display: none;" id="${random}_accountBean_d2">
									<label style="width:65px;">打印机: </label>
									<select id="${random}_accountBean_printer" style="float: none;">
									</select>
								</div>
								<div style="margin-top: 5px;width: 400px;display: none;" id="${random}_accountBean_d3">
									<label style="width:65px;">纸型: </label>
									<select id="${random}_accountBean_paperNumber" style="float: none;">
									</select>
								</div>
								<div style="margin-top: 5px;width: 400px;display: none;" id="${random}_accountBean_d4">
									<label style="width:65px;">方向: </label>
									<select id="${random}_accountBean_oriantation" style="float: none;">
										<option value="portrait">纵向</option>
										<option value="landscape">横向</option>
									</select>
								</div>
								<div style="margin-top: 5px;width: 400px;display: none;" id="${random}_accountBean_d5">
									<label style="width:65px;">缩放比例: </label>
									<input id="${random}_accountBean_sfbl" style="width: 100px;" value="100">
								</div>
								<div style="margin-top: 5px;width: 400px;display: none;" id="${random}_accountBean_d6">
									<label style="width:65px;">打印比例: </label>
									<input id="${random}_accountBean_scale" style="width: 100px;" value="100">
								</div>
								<div style="margin-top: 5px;width: 400px;display: none;" id="${random}_accountBean_d7">
									<div class="buttonActive" style="margin-left: 10px;">
										<div class="buttonContent">
											<button type="button" onclick="gzAccountPlanPrintView()">打印预览</button>
										</div>
									</div>
								</div>
						</div>
					</div>
					<div class="tabsFooter">
						<div class="tabsFooterContent"></div>
					</div>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="gzAccountSubmit()">确定</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.close('dialog${menuId}');">
									<s:text name="button.cancel" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>