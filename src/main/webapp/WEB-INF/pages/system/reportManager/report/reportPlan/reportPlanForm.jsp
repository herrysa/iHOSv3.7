<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script>
var reportPlanFormDefine = {
	key:"reportPlanForm_gridtable",
	main:{
		Build : '',
		Load :''
	},
	event:{
		//加载完毕
		"Load":function( id,p1, p2, p3, p4){//合计行加颜色与转换字体
			var grid = eval("("+id+")");
			if("${reportDefine.displayType}"){
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
			var printer = jQuery("#reportPlanForm_printer").val();
			var paperNumber = jQuery("#reportPlanForm_paperNumber").val();
			var oriantation = jQuery("#reportPlanForm_oriantation").val();
			var scale = jQuery("#reportPlanForm_scale").val();
			
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
			var layerDom;
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
			grid.func("dom_delete", printDOM); //销毁对象 
			if(reportCustomLayout){
				grid.func("setCustom", reportCustomLayout);
			}
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
			if(paperNumber && paperNumber !== '-1'){
				jQuery("#reportPlanForm_paperNumber").val(paperNumber);
			}
			if(oriantation){
				jQuery("#reportPlanForm_oriantation").val(oriantation);
			}
			if(scale){
				jQuery("#reportPlanForm_scale").val(scale);
			}
		},
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
			jQuery("#reportPlanForm_printer").html(printerHtml);
		}
	}
}; 
if("${reportDefine.displayType}" == "Report"){
	//已执行计算
	reportPlanFormDefine.event.Calced = function( id,p1, p2, p3, p4){
		var grid = eval("("+id+")");
		//打印设置
		var printer = jQuery("#reportPlanForm_printer").val();
		var paperNumber = jQuery("#reportPlanForm_paperNumber").val();
		var oriantation = jQuery("#reportPlanForm_oriantation").val();
		var scale = jQuery("#reportPlanForm_scale").val();
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
	};
	
}
supcanGridMap.put('reportPlanForm_gridtable',reportPlanFormDefine);

	var reportPlanItems = {};//选择的报表项
	var requiredItems = [];
	var requiredItemStr = "";
	var reportCustomLayout = "";
	var allReportItems;//所有报表项
	var configurable = "";
	var sysReportPlanObj = {};
	var printViewShow = "";
	jQuery(document).ready(function(){
		//打印纸初始化
		var supCanPageHtml = "";
		if(supCanPages){
			for(var pageNumber in supCanPages){
				supCanPageHtml += '<option value="'+pageNumber+'">' + supCanPages[pageNumber] + '</option>';
			}
		}
		jQuery("#reportPlanForm_paperNumber").html(supCanPageHtml);
		jQuery("#reportPlanForm_paperNumber").val("9");
		if("${curType}"){//类别初始化
			jQuery("#reportPlanForm_typeList").val("${curType}");
		}
		if("${reportDefine.curType}"){//固定类别统计
			jQuery("#reportPlanForm_typeList").val("${reportDefine.curType}");
			jQuery("#reportPlanForm_typeList").attr("disabled","disabled");
			jQuery("#reportPlanForm_typeListTR1").hide();
			jQuery("#reportPlanForm_typeListTR2").hide();
		}
		
		var reportPlanSysStr = jQuery("#reportPlanForm_reportPlanSysStr").val();
		if(reportPlanSysStr){
			sysReportPlanObj = JSON.parse(reportPlanSysStr);
		}
		configurable = "${reportDefine.configurable}";
		requiredItemStr = "${reportDefine.requiredItems}";
		if(requiredItemStr){
			requiredItems = requiredItemStr.split(",");
		}
		if(configurable != "true"){
			jQuery("#reportPlanForm_toRight").attr("disabled","disabled");
			jQuery("#reportPlanForm_AlltoRight").attr("disabled","disabled");
			jQuery("#reportPlanForm_toLeft").attr("disabled","disabled");
			jQuery("#reportPlanForm_ALLtoLeft").attr("disabled","disabled");
			jQuery("#reportPlanForm_planName").attr("readonly","readonly");
			jQuery("#reportPlanForm_reportTitle").attr("readonly","readonly");
			jQuery("#reportPlanForm_toPublic").bind("click",function(){return false;});
			jQuery("#reportPlanForm_toDepartment").bind("click",function(){return false;});
			jQuery("#reportPlanForm_toRole").bind("click",function(){return false;});
			jQuery("#reportPlanForm_delPlan").hide();
		}
		var planItemsJsonStr = jQuery("#reportPlanForm_planItemsJsonStr").val();
		if(planItemsJsonStr){
			allReportItems = JSON.parse(planItemsJsonStr);
			reportPlanFormSelectLoad("${oper}");
		}
		/*初始化页面*/
		/*次结或者月结*/
		
		if ("{reportType.issueType}" == "次") {
			jQuery("#reportPlanForm_a1_yearDiv").show();
			jQuery("#reportPlanForm_a1_periodDiv").hide();
			jQuery("#reportPlanForm_c1_yearDiv").show();
			jQuery("#reportPlanForm_c1_periodDiv").hide();
		} else {
			jQuery("#reportPlanForm_a1_yearDiv").hide();
			jQuery("#reportPlanForm_a1_periodDiv").show();
			jQuery("#reportPlanForm_c1_yearDiv").hide();
			jQuery("#reportPlanForm_c1_periodDiv").show();
		}
		/*隐藏部分查询框*/
		var showFilterStr = "${reportDefine.filters}";
		if (showFilterStr){
			var showFilters = showFilterStr.split(",");
			jQuery.each(showFilters, function(key, value) {
				jQuery("#reportPlanForm_" + value).show();
			});
		}
		/*treeSelect初始化*/
		reportPlanFormInitTreeSelect("", "", "","","","","","");
		//获取帐表中排序后的列
		if("${oper}"){
			reportCustomLayout = jQuery("#${oper}_reportCustomLayout").val();
			var reportTitle = jQuery("#reportTitle_${oper}").text();
			jQuery("#reportPlanForm_reportTitle").val(reportTitle);
		}
		//初始化方案 
		var options = jQuery("#reportPlanForm_planList option");
		if (options.length > 0) {
		//设置第一个为选中状态
			if(!"${planId}" || "${planId}" === "null"){
				jQuery("#reportPlanForm_planList option").eq(0).attr('selected', 'true');
			}else{
				jQuery("#reportPlanForm_planList option[value='"+ "${planId}" + "']").attr('selected', 'true');
			}
			reportPlanInit("${oper}");
		}

		var planClickTimer;
		jQuery("#reportPlanForm_planList").click(function() {
			clearTimeout(planClickTimer);
			planClickTimer = setTimeout(function() {
				reportPlanInit()
			}, 300);
		});
		jQuery("#reportPlanForm_planList").dblclick(function() {
			clearTimeout(planClickTimer);
			reportPlanSubmit();
		});
				
		jQuery("#reportPlanForm_items1").on('dblclick','',function(){
			reportPlanItemMoveToRight(jQuery(this).find("option:selected")[0]);
		});
		jQuery("#reportPlanForm_items2").on('dblclick','',function(){
			reportPlanItemMoveToLeft(jQuery(this).find("option:selected")[0]);
		});
		//初始化打印预览treeList
		var reverseColumn = "${reportDefine.reverseColumn}";//反转
		if(reverseColumn){//反转
			initReportPlanFormReverseGrid();
		}else{
			initReportPlanFormGrid();
		}
});
	//初始化打印预览treeList
	function initReportPlanFormGrid(reLoad){
		var typeId = jQuery("#reportPlanForm_typeList").val();
		var defineId = "${defineId}";
		jQuery.ajax({
	    	url: 'reportPlanColumnInfo',
	        data: {reportPlanItemsStr:JSON.stringify(reportPlanItems),
	        	defineId:defineId,typeId:typeId},
	        type: 'post',
	        dataType: 'json',
	        async:false,
	        error: function(data){
	        },
	        success: function(data){   
	        	var reportItems = data.reportItems;
	        	var columns = "";
	        	for(var itemIndex in reportItems){
	        		var itemCode = reportItems[itemIndex].itemCode;
	        		columns += itemCode +",";
	    		}
	        	if(reportItems&&reportItems.length>0){
	        		columns = columns.substring(0,columns.length-1);
	        	}
	        	var colModelDatas = initReportPlanFormColModel(reportItems);
	        	initReportPlanFormGridScript(colModelDatas,columns,defineId,typeId,reLoad);
	        }
	    });
	}
	//反转
	/*反转*/
	function initReportPlanFormReverseGrid(reLoad){
		var typeId = jQuery("#reportPlanForm_typeList").val();
		var defineId = "${defineId}";
		var colModelDatas= [
{name:'rpItems',index:'rpItems',align:'center',totalExpress:"合计",totalAlign:"center",text : '${subSystemPrefix}项目',width:80,isHide:false,editable:false,dataType:'string'}
];
		var colArr = reportPlanFormReverseCols();
		var reportItems = [];
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
				reportItems[key]={itemType:"0",itemCode:"period"+value,scale:2};
			});
		}
		var reportPlanGrid = reportPlanFormSupcanPlanGrid(colModelDatas);
		reportPlanFormDefine.main.Build = JSON.stringify(reportPlanGrid);
		var rpFilterObj = reportPlanFormFilterLoad(typeId);
		rpFilterObj.defineId = defineId;
		rpFilterObj.reportPlanItemsStr = JSON.stringify(reportPlanItems);
		jQuery.ajax({
			url: 'reportReverseGridList',
			data: rpFilterObj,
			type: 'post',
			dataType: 'json',
			async:true,
			error: function(data){
			},
			success: function(data){
				var reports = data.reportPlanContents;
				var reportGridData = {};
				reportGridData.Record = reports;
				if(reLoad){
					reportPlanForm_gridtable.func("load", JSON.stringify(reportGridData));
					var reportTitle = jQuery("#reportPlanForm_reportTitle").val();
					if(!reportTitle){
						reportTitle = "${reportDefine.defineName}";
					}
					reportPlanForm_gridtable.func("SetProp","Title\r\n"+reportTitle)
					reportPlanFormPrintSet();
					reportPlanForm_gridtable.func("PrintPreview","");
				}else{
					reportPlanFormDefine.main.Load = JSON.stringify(reportGridData);
					insertTreeListToDiv("reportPlanForm_gridtable_container","reportPlanForm_gridtable","","100%");
				}
			}
		});
	}
	/*期间集合*/
	function reportPlanFormReverseCols(){
		var modelStatusStr = jQuery("#reportPlanForm_modelStatusStr").val();
		var modelStatusObj = [];
		if(modelStatusStr){
			modelStatusObj = JSON.parse(modelStatusStr);
		}
		var colArr = new Array();
		var issueType = jQuery("#reportPlanForm_typeList").find("option:selected").attr("issueType");
		if(issueType == "月"&&modelStatusObj){
			var fromperiod = jQuery("#reportPlanForm_fromPeriodTime").val();
			var toperiod = jQuery("#reportPlanForm_toPeriodTime").val();
			if(!fromperiod&&!toperiod){
				fromperiod = "${firstPeriod}";
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
		if(issueType == "次"&&modelStatusObj){
			var fromCheckYear = jQuery("#reportPlanForm_fromCheckYear").val();
			var fromCheckNumber = jQuery("#reportPlanForm_fromCheckNumber").val();
			var toCheckYear = jQuery("#reportPlanForm_filter_toCheckYear").val();
			var toCheckNumber = jQuery("#reportPlanForm_filter_toCheckNumber").val();
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
	
	function initReportPlanFormGridScript(colModelDatas,columns,defineId,typeId,reLoad){
		if("${reportDefine.displayType}" == "Report"){
			var deptIds = jQuery("#reportPlanForm_deptIds_id").val();
			var gzStubsDeptNumber = "${gzStubsDeptNumber}";
			gzStubsDeptNumber = +gzStubsDeptNumber;
			if(!gzStubsDeptNumber){
				gzStubsDeptNumber = 10;
			}
			if(deptIds && deptIds.split(",").length <= gzStubsDeptNumber){
				if(reLoad){
					reportPlanForm_gridtable.func("Build", colModelDatas);
				}else{
					reportPlanFormDefine.main.Build = colModelDatas;
				}
				reportPlanFormTableload(columns,defineId,typeId,reLoad);
			}else{
				if(reLoad){
					reportPlanForm_gridtable.func("Build", colModelDatas);
					reportPlanForm_gridtable.func("calc", "mode=asynch;range=current");
				}else{
					reportPlanFormDefine.main.Build = colModelDatas;
					insertReportToDiv("reportPlanForm_gridtable_container","reportPlanForm_gridtable","Rebar=None; Border=single;Hue=Lilian;isAnimateFocus=false; PagesTabPercent=0; SeperateBar=none","100%");				
				}
			}
		}else{
			var reportPlanFormGrid = reportPlanFormSupcanPlanGrid(colModelDatas);
			if(reLoad){
				reportPlanForm_gridtable.func("build", JSON.stringify(reportPlanFormGrid));
			}else{
				reportPlanFormDefine.main.Build = JSON.stringify(reportPlanFormGrid);
			}
			reportPlanFormTableload(columns,defineId,typeId,reLoad);
		}
	}
	
	function initReportPlanFormColModel(data){
		if("${reportDefine.displayType}" == "Report"){
			var colColumns = jQuery("#reportPlanForm_colColumns").val();
			var colMax = 6;
			if(colColumns&&!isNaN(colColumns)){
				colColumns = +colColumns;
				if(colColumns>0){
					colMax = colColumns;
				}
			}
			var reportXml = '<?xml version="1.0" encoding="UTF-8" ?>';
			reportXml += '<Report>';
			reportXml += '<WorkSheet name="${reportDefine.defineName}">';
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
			thXml += '<TD fontIndex="1" leftBorder="1" topBorder="1" leftBorderColor="#a6c9e2" topBorderColor="#a6c9e2" bgColor="#dfeffc" leftBorder="1" topBorder="1" align="center" datatype="1" formula="=head(&apos;ds1\\Record\\jsonobject&apos;, &apos;period&apos;)"/>';
			thXml += '<TD fontIndex="1" leftBorder="1" topBorder="1" bgColor="#dfeffc" leftBorderColor="#a6c9e2" topBorderColor="#a6c9e2" align="center" datatype="1" formula="=head(&apos;ds1\\Record\\jsonobject&apos;, &apos;issueNumber&apos;)"/>';
			colIndex = 2;
			/*定义数据行*/
			var tdXml = '<TR height="23" sequence="1"  areaNumber="2">';
			tdXml += '<TD leftBorder="0" topBorder="0" isArea="true"/>'
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
			dataSourceXml += '<name alias="${reportDefine.defineName}">Record\\jsonobject</name>';
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
			if(!"${reportDefine.groupSelectItems}"){
				if("${reportDefine.displayType}"){
					colModelDatas.push({name:'period',index:'period',align:'center',text : '<s:text name="期间" />',width:80,isHide:false,editable:false,dataType:'string'});
				}else{
					colModelDatas.push({name:'period',index:'period',align:'center',totalExpress:"合计",totalAlign:"center",text : '<s:text name="期间" />',width:80,isHide:false,editable:false,dataType:'string'});
				}
			}
			var attachField = "${reportDefine.attachField}";
			var attachFieldName = "${reportDefine.attachFieldName}";
			if(attachField&&attachFieldName){
				var attachFieldArr = attachField.split(",");
				var attachFieldNameArr = attachFieldName.split(",");
				for(var index in attachFieldArr){
					var nameTemp = attachFieldArr[index];
					var textTemp = attachFieldNameArr[index];
					var attachObj = {name:nameTemp,align:'right',
							text : textTemp,width:60,isHide:false,editable:false,dataType:'int'};
					colModelDatas.push(attachObj);
				}
			}
			var groupIds = jQuery("#reportPlanForm_groupIds_id").val();
			if("${reportDefine.groupIds}"){
				groupIds = "${reportDefine.groupIds}";
			}
			var countColChange = false;
			if(groupIds){
				var groupIdArr = groupIds.split(",");
				if(jQuery.inArray("period",groupIdArr) == -1){
					countColChange = true;
				}
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
	    	 		if("${reportDefine.displayType}"){
	    	 			colModelData.totalExpress = "";
	    	 		}
	    	 		if(row.sumColumn){//合计列
	    	 			colModelData.totalExpress = "合计";
	    				colModelData.totalAlign = colModelData.align;
	    	 		}
	    	 		if(countColChange && row.itemCode == "count"){
	    	 			colModelData.text = "平均人数";
	    	 			colModelData.dataType = "double";
	    	 		}
	    	 		colModelDatas.push(colModelData);
	    		} 
	    		return colModelDatas;
		}
    }
	function reportPlanFormSupcanPlanGrid(colModelDatas){
		var reportPlanFormGrid = cloneObj(supCanTreeListGrid);
		reportPlanFormGrid.Cols = colModelDatas;
		reportPlanFormGrid.Properties.Title = "${reportDefine.defineName}";
		reportPlanFormGrid.Properties.sort = "period,orgCode,deptCode,personCode";
		return reportPlanFormGrid;
	}
	
	function reportPlanFormTableload(columns,defineId,typeId,reLoad){
		jQuery.ajax({
			url: 'reportPlanGridList?defineId='+defineId+'&columns='+columns,
			data: reportPlanFormFilterLoad(typeId),
			type: 'post',
			dataType: 'json',
			async:true,
			error: function(data){
			},
			success: function(data){
				var reportPlanContents = data.reportPlanContents;
				var reportPlanFormGridData = {};
				reportPlanFormGridData.Record = reportPlanContents;
				if("${reportDefine.displayType}" == "Report"){
					if(reLoad){
						reportPlanForm_gridtable.func("setSource", "ds1 \r\n "+JSON.stringify(reportPlanFormGridData));
						reportPlanForm_gridtable.func("calc", "mode=asynch;range=current");
					}else{
						reportPlanFormDefine.main.SetSource = "ds1 \r\n " + JSON.stringify(reportPlanFormGridData);
						insertReportToDiv("reportPlanForm_gridtable_container","reportPlanForm_gridtable","Rebar=None; Border=single;Hue=Lilian;isAnimateFocus=false; PagesTabPercent=0; SeperateBar=none","100%");
					}
				}else{
					if(reLoad){
						reportPlanForm_gridtable.func("load", JSON.stringify(reportPlanFormGridData));
						var reportTitle = jQuery("#reportPlanForm_reportTitle").val();
						if(!reportTitle){
							reportTitle = "${reportDefine.defineName}";
						}
						reportPlanForm_gridtable.func("SetProp","Title\r\n"+reportTitle);
						reportPlanFormPrintSet();
						reportPlanForm_gridtable.func("PrintPreview","");
					}else{
						reportPlanFormDefine.main.Load = JSON.stringify(reportPlanFormGridData);
						insertTreeListToDiv("reportPlanForm_gridtable_container","reportPlanForm_gridtable","","100%");
					}
				}
			}
		});
	}
	//过滤条件
	function reportPlanFormFilterLoad(typeId){
		var periodTime = jQuery("#reportPlanForm_periodTime").val();
		var checkYear = jQuery("#reportPlanForm_checkYear").val();
		var checkNumber = jQuery("#reportPlanForm_checkNumber").val();
		var fromPeriodTime = jQuery("#reportPlanForm_fromPeriodTime").val();
		var toPeriodTime = jQuery("#reportPlanForm_toPeriodTime").val();
		var fromCheckYear = jQuery("#reportPlanForm_fromCheckYear").val();
		var fromCheckNumber = jQuery("#reportPlanForm_fromCheckNumber").val();
		var toCheckYear = jQuery("#reportPlanForm_toCheckYear").val();
		var toCheckNumber = jQuery("#reportPlanForm_toCheckNumber").val();
		//var orgCode = jQuery("#reportPlanForm_orgCode").val();
		var orgCode = jQuery("#reportPlanForm_orgCode_id").val();
		var branchCode = jQuery("#reportPlanForm_branchCode_id").val();
		var deptTypes = jQuery("#reportPlanForm_deptTypes_id").val();
		//var deptIds = jQuery("#reportPlanForm_deptIds").val();
		var deptIds = jQuery("#reportPlanForm_deptIds_id").val();
		var empTypes = jQuery("#reportPlanForm_empTypes").val();
		var empTypesa12 = jQuery("#reportPlanForm_empTypesa12").val();
		var personName = jQuery("#reportPlanForm_personName").val();
		var personCode = jQuery("#reportPlanForm_personCode").val();
		var deptType = jQuery("#reportPlanForm_a7").find("input:radio[name=deptType][checked=checked]").val();
		var deptLevelFrom = jQuery("#reportPlanForm_deptLevelFrom").val();
		var deptLevelTo = jQuery("#reportPlanForm_deptLevelTo").val();
		var deptDetailIds = jQuery("#reportPlanForm_deptDetailIds_id").val();
		var typeIds = jQuery("#reportPlanForm_typeIds_id").val();
		var personId = jQuery("#reportPlanForm_personId").val();
		var includeUnCheck = jQuery("#reportPlanForm_includeUnCheck").attr("checked");
		var groupIds = jQuery("#reportPlanForm_groupIds_id").val();
		var reportPlanFilter = {};
		reportPlanFilter.typeId = typeId;
		if(periodTime){
			reportPlanFilter.periodTime = periodTime;
		}
		if(checkYear){
			reportPlanFilter.checkYear = checkYear;
		}
		if(checkNumber){
			reportPlanFilter.checkNumber = checkNumber;
		}
		var issueType = jQuery("#reportPlanForm_typeList").find("option:selected").attr("issueType");
		if(issueType == "月"){
			if(!fromPeriodTime&&!toPeriodTime){
				if("${reportDefine.reverseColumn}"){
					reportPlanFilter.fromPeriodTime = "${firstPeriod}";
				}else{
					reportPlanFilter.fromPeriodTime = "${curPeriod}";
				}
				reportPlanFilter.toPeriodTime = "${curPeriod}";
			}
		}
		if(fromPeriodTime){
			reportPlanFilter.fromPeriodTime = fromPeriodTime;
		}
		if(toPeriodTime){
			reportPlanFilter.toPeriodTime = toPeriodTime;
		}
		if(fromCheckYear){
			reportPlanFilter.fromCheckYear = fromCheckYear;
		}
		if(fromCheckNumber){
			reportPlanFilter.fromCheckNumber = fromCheckNumber;
		}
		if(toCheckYear){
			reportPlanFilter.toCheckYear = toCheckYear;
		}
		if(toCheckNumber){
			reportPlanFilter.toCheckNumber = toCheckNumber;
		}
		if(orgCode){
			reportPlanFilter.orgCode = orgCode;
		}
		if(branchCode){
			reportPlanFilter.branchCode = branchCode;
		}
		if(deptIds){
			reportPlanFilter.deptIds = deptIds;
		}
		if(deptTypes){
			reportPlanFilter.deptTypes = deptTypes;
		}
		if(empTypes){
			reportPlanFilter.empTypes = empTypes;
		}
		if(empTypesa12){
			reportPlanFilter.empTypesa12 = empTypesa12;
		}
		if(personName){
			reportPlanFilter.personName = personName;
		}
		if(personCode){
			reportPlanFilter.personCode = personCode;
		}
		if(deptType){
			reportPlanFilter.deptType = deptType;
		}
		if(deptLevelFrom){
			reportPlanFilter.deptLevelFrom = deptLevelFrom;
		}
		if(deptLevelTo){
			reportPlanFilter.deptLevelTo = deptLevelTo;
		}
		if(deptDetailIds){
			reportPlanFilter.deptDetailIds = deptDetailIds;
		}
		if(typeIds){
			reportPlanFilter.typeIds = typeIds;
		}
		if(personId){
			reportPlanFilter.personId = personId;
		}
		if(includeUnCheck){
			reportPlanFilter.includeUnCheck = includeUnCheck;
		}
		if(groupIds){
			reportPlanFilter.groupIds = groupIds;
		}
		return reportPlanFilter;
	}
	
	function reportPlanInit(random) {
		if(!random){
			reportPlanFormClearContent();
		}
		var planId = jQuery("#reportPlanForm_planList").find("option:selected").val();
		var text = jQuery("#reportPlanForm_planList").find("option:selected").text();
		if (!planId || !text) {
			return;
		}
		jQuery("#reportPlanForm_planName").val(text);
		jQuery.ajax({
			url : 'getSelectedReportPlan',
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
				if (data.reportPlans) {
					var reportPlan = data.reportPlans[0];
					if (reportPlan) {
						if (reportPlan.toPublic == "1") {
							jQuery("#reportPlanForm_toPublic").attr("checked", "checked");
						}
						if (reportPlan.toDepartment == "1") {
							jQuery("#reportPlanForm_toDepartment").attr("checked", "checked");
						}
						if (reportPlan.toRole == "1") {
							jQuery("#reportPlanForm_toRole").attr("checked", "checked");
						}
						if(!random){
							var reportTitle = reportPlan.reportTitle;
							if(reportTitle){
								jQuery("#reportPlanForm_reportTitle").val(reportTitle);
							}
						}
					}
					if(!random){
						reportCustomLayout = reportPlan.customLayout;
					}
				}
				if(!random){
					if (data.reportPlanItems) {
						initReportPlanItems(data.reportPlanItems);
					}
				}
				if (data.reportPlanFilters) {
					initReportPlanFilters(data.reportPlanFilters,random);
				}
			}
		});
	}

	function checkSelItem(id1, id2) {
		var selectItemvalue = jQuery("#" + id1).val();
		var selectItemid = jQuery("#" + id2).val()
		if (selectItemvalue.indexOf("全选") == 0) {
			var selectFinalValue = selectItemvalue.substring(3,
					selectItemvalue.length);
			var selectFinal_id = selectItemid.substring(3, selectItemid.length);
			jQuery("#" + id1).val(selectFinalValue);
			jQuery("#" + id2).val(selectFinal_id);
		}
	}
	//右移
	function reportPlanItemtoRight() {
		var selectItems = jQuery("#reportPlanForm_items1").find("option:selected");
		if(!selectItems || selectItems.length<=0){
			alertMsg.error("请选择要移动的${subSystemPrefix}项！");
			return;
		}
		jQuery.each(selectItems,function(index,selectItem){
	 		var itemText = jQuery(selectItem).text();
	 		var itemValue = jQuery(selectItem).val();
	 		jQuery("#reportPlanForm_items2").append("<option value='" + itemValue
					+ "' >" + itemText
					+ "</option>");
		 	jQuery(selectItem).remove();
		 	var reportPlanItemIndex = getJsonLength(reportPlanItems);
			reportPlanItems[itemValue]={"name":itemText,"oldName":itemText,isThousandSeparat:"1",
					headerFontIndex:'1',fontIndex:'0',headerTextColor:'#000000',sn:reportPlanItemIndex};
		});
	}
	//左移
	function reportPlanItemtoLeft() {
		var selectItems = jQuery("#reportPlanForm_items2").find("option:selected");
		if(!selectItems || selectItems.length<=0){
			alertMsg.error("请选择要移动的${subSystemPrefix}项！");
			return;
		}
		var leftItemArr = [];
		var leftItems = jQuery("#reportPlanForm_items1 option");
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
// 	 			jQuery("#reportPlanForm_items1").append("<option value='" + itemValue
// 		 				+ "'>" + itemText
// 		 				+ "</option>");
		 		jQuery(selectItem).remove();
		 		leftItemArr.push(itemValue);
		 		delete reportPlanItems[itemValue];
	 		}else{
	 			errorMessage += itemText + ",";
	 		}
		});
		//左侧初始化
		var allReportPlanItemHtml = "";
		jQuery.each(allReportItems,function(index,reportItem){
				var itemCode = reportItem.itemCode;
				var itemName = reportItem.itemName;
				if(jQuery.inArray(itemCode, leftItemArr) > -1){
					allReportPlanItemHtml += "<option value='" + itemCode
					+ "'>"+ itemName + "</option>";
				}
			});
		jQuery("#reportPlanForm_items1").html(allReportPlanItemHtml);
		//reportPlanItems排序
		if(reportPlanItems){
			var index = 0;
			for(var itemValue in reportPlanItems){
				reportPlanItems[itemValue]["sn"] = index;
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
	function reportPlanItemAlltoRight() {
		var rightSelect = jQuery("#reportPlanForm_items2");
		jQuery("#reportPlanForm_items1 option").each(function(index,selectItem){
	 		var itemText = jQuery(selectItem).text();
	 		var itemValue = jQuery(selectItem).val();
					rightSelect.append("<option value='" + itemValue
							+ "' >"
							+ itemText + "</option>");
			var reportPlanItemIndex = getJsonLength(reportPlanItems);
			reportPlanItems[itemValue]={"name":itemText,"oldName":itemText,isThousandSeparat:"1",
				headerFontIndex:'1',fontIndex:'0',headerTextColor:'#000000',sn:reportPlanItemIndex};	
				});
		jQuery("#reportPlanForm_items1").empty();
	}
	//全部左移
	function reportPlanItemALLtoLeft() {
		//左侧初始化
		var allReportPlanItemHtml = "";
		jQuery.each(allReportItems,function(index,reportItem){
				var itemCode = reportItem.itemCode;
				var itemName = reportItem.itemName;
				if(jQuery.inArray(itemCode, requiredItems)== -1){
					allReportPlanItemHtml += "<option value='" + itemCode
					+ "'>"+ itemName + "</option>";
					jQuery("#reportPlanForm_items2 option[value='"+ itemCode + "']").remove();
				}else{
					errorMessage += itemName + ",";
				}
			});
		jQuery("#reportPlanForm_items1").html(allReportPlanItemHtml);
		reportPlanItems = {};
		if(errorMessage){
			errorMessage = errorMessage.substring(0,errorMessage.length-1);
			alertMsg.error(errorMessage+"为系统项！");
			return;
		}
	}
//确定按钮
	function reportPlanSubmit() {
		var displayType = "${reportDefine.displayType}";
		var url = "reportPlanShow?defineId=${defineId}&menuId=${menuId}&width=${width}&height=${height}"; 
		var typeId = jQuery("#reportPlanForm_typeList").val();
		if(!typeId){
			alertMsg.error("请选择${subSystemPrefix}类别！");
			return;
		}
		var typeName = jQuery("#reportPlanForm_typeList").find("option:selected").text();
		var issueType = jQuery("#reportPlanForm_typeList").find("option:selected").attr("issueType");
		url += "&typeId="+typeId+"&typeJson=";
		var reportTypeJson = {typeId:typeId,typeName:typeName,issueType:issueType};
		url += JSON.stringify(reportTypeJson);	
		var savePlanChecked = jQuery("#reportPlanForm_savePlan").attr("checked");
		var planName = jQuery("#reportPlanForm_planName").val();
		var reportTitle = jQuery("#reportPlanForm_reportTitle").val();
		if(!reportTitle){
			alertMsg.error("报表标题不能为空！");
			return;
		}
		if(savePlanChecked == "checked"){
			if (!planName) {
				alertMsg.error("请填写方案名，再保存！");
				return;
			}
			if(sysReportPlanObj[planName]){
				alertMsg.error("该方案为系统方案，请修改方案名！");
				return;
			}
			var savePlan = 1;
			var toPublic = jQuery("#reportPlanForm_toPublic").attr("checked")=="checked"?1:0;
			var toDepartment = jQuery("#reportPlanForm_toDepartment").attr("checked")=="checked"?1:0;
			var toRole = jQuery("#reportPlanForm_toRole").attr("checked")=="checked"?1:0;
			url += "&savePlan="+savePlan+"&toPublic="+toPublic+"&toDepartment="+toDepartment+"&toRole="+toRole+"&planName="+planName;
		}
		var periodTime = jQuery("#reportPlanForm_periodTime").val();
		var checkYear = jQuery("#reportPlanForm_checkYear").val();
		var checkNumber = jQuery("#reportPlanForm_checkNumber").val();
		var fromPeriodTime = jQuery("#reportPlanForm_fromPeriodTime").val();
		var toPeriodTime = jQuery("#reportPlanForm_toPeriodTime").val();
		var fromCheckYear = jQuery("#reportPlanForm_fromCheckYear").val();
		var fromCheckNumber = jQuery("#reportPlanForm_fromCheckNumber").val();
		var toCheckYear = jQuery("#reportPlanForm_toCheckYear").val();
		var toCheckNumber = jQuery("#reportPlanForm_toCheckNumber").val();
		var orgCode = jQuery("#reportPlanForm_orgCode_id").val();
		var branchCode = jQuery("#reportPlanForm_branchCode_id").val();
		var deptTypes = jQuery("#reportPlanForm_deptTypes_id").val();
		var deptIds = jQuery("#reportPlanForm_deptIds_id").val();
		var empTypes = jQuery("#reportPlanForm_empTypes_id").val();
		var empTypesa12 = jQuery("#reportPlanForm_empTypesa12_id").val();
		var personName = jQuery("#reportPlanForm_personName").val();
		var personCode = jQuery("#reportPlanForm_personCode").val();
		var colColumns = jQuery("#reportPlanForm_colColumns").val();
		var deptType = jQuery("#reportPlanForm_a7").find("input:radio[name=deptType][checked=checked]").val();
		var deptLevelFrom = jQuery("#reportPlanForm_deptLevelFrom").val();
		var deptLevelTo = jQuery("#reportPlanForm_deptLevelTo").val();
		var deptDetailIds = jQuery("#reportPlanForm_deptDetailIds_id").val();
		var typeIds = jQuery("#reportPlanForm_typeIds_id").val();
		var personId = jQuery("#reportPlanForm_personId").val();
		var personIdName = jQuery("#reportPlanForm_personIdName").val();
		var includeUnCheck = jQuery("#reportPlanForm_includeUnCheck").attr("checked");
		var groupIds = jQuery("#reportPlanForm_groupIds_id").val();
		//打印控制
		var printer = jQuery("#reportPlanForm_printer").val();
		var paperNumber = jQuery("#reportPlanForm_paperNumber").val();
		var oriantation = jQuery("#reportPlanForm_oriantation").val();
		var scale = jQuery("#reportPlanForm_scale").val();
		var reportPlanFilter = {};
		if(periodTime){
			reportPlanFilter.periodTime = periodTime;
		}
		if(checkYear){
			reportPlanFilter.checkYear = checkYear;
		}
		if(checkNumber){
			reportPlanFilter.checkNumber = checkNumber;
		}
		if(fromPeriodTime){
			reportPlanFilter.fromPeriodTime = fromPeriodTime;
		}
		if(toPeriodTime){
			reportPlanFilter.toPeriodTime = toPeriodTime;
		}
		if(fromCheckYear){
			reportPlanFilter.fromCheckYear = fromCheckYear;
		}
		if(fromCheckNumber){
			reportPlanFilter.fromCheckNumber = fromCheckNumber;
		}
		if(toCheckYear){
			reportPlanFilter.toCheckYear = toCheckYear;
		}
		if(toCheckNumber){
			reportPlanFilter.toCheckNumber = toCheckNumber;
		}
		if(orgCode){
			reportPlanFilter.orgCode = orgCode;
		}
		if(branchCode){
			reportPlanFilter.branchCode = branchCode;
		}
		if(deptIds){
			reportPlanFilter.deptIds = deptIds;
		}
		if(deptTypes){
			reportPlanFilter.deptTypes = deptTypes;
		}
		if(empTypes){
			reportPlanFilter.empTypes = empTypes;
		}
		if(empTypesa12){
			reportPlanFilter.empTypesa12 = empTypesa12;
		}
		if(personName){
			reportPlanFilter.personName = personName;
		}
		if(personCode){
			reportPlanFilter.personCode = personCode;
		}
		if(colColumns){
			reportPlanFilter.colColumns = colColumns;
		}
		if(deptType){
			reportPlanFilter.deptType = deptType;
		}
		if(deptLevelFrom){
			reportPlanFilter.deptLevelFrom = deptLevelFrom;
		}
		if(deptLevelTo){
			reportPlanFilter.deptLevelTo = deptLevelTo;
		}
		if(deptDetailIds){
			reportPlanFilter.deptDetailIds = deptDetailIds;
		}
		if(typeIds){
			reportPlanFilter.typeIds = typeIds;
		}
		if(personId){
			reportPlanFilter.personId = personId;
		}
		if(personIdName){
			reportPlanFilter.personIdName = personIdName;
		}
		if(includeUnCheck){
			reportPlanFilter.includeUnCheck = includeUnCheck;
		}
		//打印
		if(printer){
			reportPlanFilter.printer = printer;
		}
		if(paperNumber){
			reportPlanFilter.paperNumber = paperNumber;
		}
		if(oriantation){
			reportPlanFilter.oriantation = oriantation;
		}
		if(scale){
			reportPlanFilter.scale = scale;
		}
		//分组
		if(groupIds){
			reportPlanFilter.groupIds = groupIds;
		}
		var reportPlanItemsStr = JSON.stringify(reportPlanItems);
		var reportPlanFilterStr = JSON.stringify(reportPlanFilter);
		var planId = jQuery("#reportPlanForm_planList").val();
 		var displayType = "${reportDefine.displayType}";
 		menuClick('${menuId}');
 		url = encodeURI(url);
 		var modelStatusStr = jQuery("#reportPlanForm_modelStatusStr").val();
 		navTab.openTab("navTab${menuId}",url,{title : '${reportDefine.defineName}',fresh : true,data : {
 			reportPlanItemsStr:reportPlanItemsStr,reportPlanFilterStr:reportPlanFilterStr,tabOpenType:"menuClick",
 			planId:planId,reportCustomLayout:reportCustomLayout,subSystemPrefix:"${subSystemPrefix}",
 			reportTypeTreeNodeStr:"${reportTypeTreeNodeStr}",reportTitle:reportTitle,gzStubsDeptNumber:"${gzStubsDeptNumber}",
 			modelStatusStr:modelStatusStr
 		}});
 		$.pdialog.close('dialog${menuId}');
	}
	/* 下拉框的点击转移 */
	function reportPlanItemMoveToRight(obj) {
		if(configurable != "true"){
			alertMsg.error("该帐表为不可配置类型！");
			return;
		}
		//1.获取要移动的option 
		var s_option = jQuery(obj);
		//2.在右侧的select中进行添加option
		jQuery("#reportPlanForm_items2").append(
				"<option value='" + s_option.val()
						+ "' >"
						+ s_option.text() + "</option>");
		//3.删除掉左侧的option
		s_option.remove();
		var reportPlanItemIndex = getJsonLength(reportPlanItems);
		reportPlanItems[s_option.val()] = {"name":s_option.text(),"oldName":s_option.text(),isThousandSeparat:"1",
				headerFontIndex:'1',fontIndex:'0',headerTextColor:'#000000',sn:reportPlanItemIndex};
	}
	function reportPlanItemMoveToLeft(obj) {
		if(configurable != "true"){
			alertMsg.error("该帐表为不可配置类型！");
			return;
		}
		var leftItemArr = [];
		var leftItems = jQuery("#reportPlanForm_items1 option");
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
			jQuery("#reportPlanForm_items1").append(
					"<option value='" + s_option.val()
							+ "'>"
							+ s_option.text() + "</option>");
			//3.删除掉右侧的option
			s_option.remove();
			delete reportPlanItems[s_option.val()];
			leftItemArr.push(itemValue);
			//左侧初始化
			var allReportPlanItemHtml = "";
			jQuery.each(allReportItems,function(index,reportItem){
					var itemCode = reportItem.itemCode;
					var itemName = reportItem.itemName;
					if(jQuery.inArray(itemCode, leftItemArr) > -1){
						allReportPlanItemHtml += "<option value='" + itemCode
						+ "'>"+ itemName + "</option>";
					}
				});
			jQuery("#reportPlanForm_items1").html(allReportPlanItemHtml);
			//reportPlanItems排序
			if(reportPlanItems){
				var index = 0;
				for(var itemValue in reportPlanItems){
					reportPlanItems[itemValue]["sn"] = index;
					index ++;
				}
			}
		}else{
			alertMsg.error(itemText+"为系统项！");
			return;
		}
	}
	/*两个select初始化*/
	function reportPlanFormSelectLoad(random){
		if(allReportItems){
			var allReportPlanItemHtml = "";
			jQuery.each(allReportItems,function(index,reportItem){
				var itemCode = reportItem.itemCode;
				var itemName = reportItem.itemShowName;
				allReportPlanItemHtml += "<option value='" + itemCode
					+ "'>"
					+ itemName + "</option>"
			});
			jQuery("#reportPlanForm_items1").html(allReportPlanItemHtml);
			reportPlanItems = {};
			if(random){//根据报表初始化
				var reportPlanItemStr = jQuery("#"+ random +"_reportItemsStr").val();
				if(reportPlanItemStr){
					reportPlanItems = JSON.parse(reportPlanItemStr);
				}
 				var reportPlanItemsTemp = {};
				var sortColumns = jQuery("#"+random+"_report_sortColumns").val();
				if(sortColumns){
					var reportPlanSortItems = JSON.parse(sortColumns);
 					for(var itemCode in reportPlanSortItems){
 						jQuery("#reportPlanForm_items1 option[value='"+ itemCode + "']").remove()
 						if(reportPlanItems[itemCode]){
 							reportPlanItemsTemp[itemCode] = reportPlanItems[itemCode];
 							delete reportPlanItems[itemCode];
 						}
 					}
				}
 				if(reportPlanItems){
 					for(var itemCode in reportPlanItems){
 						if(reportPlanItems[itemCode]){
 							reportPlanItemsTemp[itemCode] = reportPlanItems[itemCode];
 						}
 					}
 				}
 				reportPlanItems = cloneObj(reportPlanItemsTemp);
			}else{
				if(requiredItems){
					var reportPlanItemIndex = 0;
					jQuery.each(requiredItems,function(index,itemCode){
						var colName = jQuery("#reportPlanForm_items1 option[value='"+ itemCode + "']").text();
						jQuery("#reportPlanForm_items1 option[value='"+ itemCode + "']").remove();
						reportPlanItems[itemCode] = {"name":colName,"oldName":colName,isThousandSeparat:"1",
								headerFontIndex:'1',fontIndex:'0',headerTextColor:'#000000',sn:reportPlanItemIndex};
						reportPlanItemIndex ++;
					});
				}
			}
			//reportPlanItems排序
			if(reportPlanItems){
				var planItemHtml = "";
				var reportPlanItemIndex = 0;
				for(var itemCode in reportPlanItems){
					var planItem = reportPlanItems[itemCode];
					planItem.sn = reportPlanItemIndex;
					var colName = planItem.name;
					reportPlanItemIndex ++;
					planItemHtml += "<option value='" + itemCode
								+ "'>" + colName + "</option>";
				}
				jQuery("#reportPlanForm_items2").html(planItemHtml);
			}
		}
	}
	function initReportPlanItems(items) {
		reportPlanItems = {};
		jQuery("#reportPlanForm_items2").empty();
		jQuery("#reportPlanForm_items1").empty();
		reportPlanFormSelectLoad();
		if(items){
			for(var index in items){
				var item = items[index];
				var itemCode = item.itemCode;
				var colName = item.colName;
				var colNameTemp = jQuery("#reportPlanForm_items1 option[value='"+ itemCode + "']").text();
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
				jQuery("#reportPlanForm_items1 option[value='"+ itemCode + "']").remove();
				//jQuery("#reportPlanForm_items2").append("<option value='" + itemCode
				//			+ "'>" + colName + "</option>");
				reportPlanItems[itemCode] = {"name":item.colName,"oldName":colName
						,isThousandSeparat:"1",headerFontIndex:'1',fontIndex:'0',headerTextColor:'#000000',sn:index};
				if(isThousandSeparat){
					reportPlanItems[itemCode].isThousandSeparat = isThousandSeparat;
				}
				if(fontIndex){
					reportPlanItems[itemCode].fontIndex = fontIndex;
				}
				if(headerFontIndex){
					reportPlanItems[itemCode].headerFontIndex = headerFontIndex;
				}
				if(headerTextColor){
					reportPlanItems[itemCode].headerTextColor = headerTextColor;
				}
			}
// 			var reportPlanItemsTemp = {};
// 			if(reportPlanSortItems){
// 				for(var itemCode in reportPlanSortItems){
// 					if(reportPlanItems[itemCode]){
// 						reportPlanItemsTemp[itemCode] = reportPlanItems[itemCode];
// 						delete reportPlanItems[itemCode];
// 					}
// 				}
// 			}
// 			if(reportPlanItems){
// 				for(var itemCode in reportPlanItems){
// 					if(reportPlanItems[itemCode]){
// 						reportPlanItemsTemp[itemCode] = reportPlanItems[itemCode];
// 					}
// 				}
// 			}
// 			reportPlanItems = cloneObj(reportPlanItemsTemp);
			//reportPlanItems排序
			if(reportPlanItems){
				var reportPlanItemHtml = "";
				var reportPlanItemIndex = 0;
				for(var itemCode in reportPlanItems){
					var reportPlanItem = reportPlanItems[itemCode];
					reportPlanItem.sn = reportPlanItemIndex;
					var colName = reportPlanItem.name;
					reportPlanItemIndex ++;
					reportPlanItemHtml += "<option value='" + itemCode + "'>" + colName + "</option>";
				}
				jQuery("#reportPlanForm_items2").html(reportPlanItemHtml);
			}
		}
	}
//切换方案时清空数据
	function reportPlanFormClearContent() {
		reportCustomLayout = "";
		//jQuery("#reportPlanForm_reportTitle").val("${reportDefine.defineName}");
		jQuery("#reportPlanForm_planName").val("");
		jQuery("#reportPlanForm_toPulbic").removeAttr("checked");
		jQuery("#reportPlanForm_toDepartment").removeAttr("checked");
		jQuery("#reportPlanForm_toRole").removeAttr("checked");
		jQuery("#reportPlanForm_periodTime").val("");
		jQuery("#reportPlanForm_checkYear").val("");
		jQuery("#reportPlanForm_checkNumber option:selected").removeAttr("selected");
		jQuery("#reportPlanForm_fromPeriodTime").val("");
		jQuery("#reportPlanForm_toPeriodTime").val("");
		jQuery("#reportPlanForm_fromCheckYear").val("");
		jQuery("#reportPlanForm_fromCheckNumber option:selected").removeAttr("selected");
		jQuery("#reportPlanForm_toCheckYear").val("");
		jQuery("#reportPlanForm_toCheckNumber option:selected").removeAttr("selected");
		jQuery("#reportPlanForm_orgCode").val("");
		jQuery("#reportPlanForm_orgCode_id").val("");
		jQuery("#reportPlanForm_branchCode").val("");
		jQuery("#reportPlanForm_branchCode_id").val("");
		jQuery("#reportPlanForm_deptTypes").val("");
		jQuery("#reportPlanForm_deptTypes_id").val("");
		jQuery("#reportPlanForm_deptIds").val("");
		jQuery("#reportPlanForm_deptIds_id").val("");
		jQuery("#reportPlanForm_empTypes").val("");
		jQuery("#reportPlanForm_empTypes_id").val("");
		jQuery("#reportPlanForm_empTypesa12").val("");
		jQuery("#reportPlanForm_empTypesa12_id").val("");
		jQuery("#reportPlanForm_personName").val("");
		jQuery("#reportPlanForm_personCode").val("");
		jQuery("#reportPlanForm_a7").find("input:radio[name=deptType]").removeAttr("checked");
		jQuery("#reportPlanForm_deptLevelFrom option:selected").removeAttr("selected");
		jQuery("#reportPlanForm_deptLevelTo option:selected").removeAttr("selected");
		jQuery("#reportPlanForm_deptDetailIds").val("");
		jQuery("#reportPlanForm_deptDetailIds_id").val("");
		jQuery("#reportPlanForm_typeIds").val("");
		jQuery("#reportPlanForm_typeIds_id").val("");
		jQuery("#reportPlanForm_includeUnCheck").removeAttr("checked");
		jQuery("#reportPlanForm_groupIds").val("");
		jQuery("#reportPlanForm_groupIds_id").val("");
	}

	function initReportPlanFilters(reportPlanFilters,random) {
		var orgInit = "";
		var deptInit = "";
		var branchInit = "";
		var deptTypesInit = "";
		var personTypeInit = "";
		var personTypea12Init = "";
		var deptTypeInit = "";
		var deptDetailInit = "";
		var typeInit = "";
		var groupIdsInit = "";
		var printSettingObj = {};
		if(random){
			var printSetting = jQuery("#"+random+"_report_printSetting").val();
			printSettingObj = JSON.parse(printSetting);
		}
		jQuery.each(reportPlanFilters,function(key, reportPlanFilter) {
					var filterCode = reportPlanFilter.filterCode;
					var filterValue = reportPlanFilter.filterValue;
					switch (filterCode) {
					case "orgCode":
						orgInit = filterValue;
						jQuery("#reportPlanForm_" + filterCode + "_id").val(filterValue);
						break;
					case "branchCode":
						branchInit = filterValue;
						jQuery("#reportPlanForm_" + filterCode + "_id").val(filterValue);
						break;
					case "deptIds":
						deptInit = filterValue;
						jQuery("#reportPlanForm_" + filterCode + "_id").val(filterValue);
						break;
					case "deptTypes":
						deptTypesInit = filterValue;
						jQuery("#reportPlanForm_" + filterCode + "_id").val(filterValue);
						break;
					case "empTypes":
						personTypeInit = filterValue;
						jQuery("#reportPlanForm_" + filterCode + "_id").val(filterValue);
						break;
					case "empTypesa12":
						personTypea12Init = filterValue;
						jQuery("#reportPlanForm_" + filterCode + "_id").val(filterValue);
						break;
					case "deptType":
						deptTypeInit = filterValue;
						break;
					case "deptDetailIds":
						deptDetailInit = filterValue;
						jQuery("#reportPlanForm_" + filterCode + "_id").val(filterValue);
						break;
					case "typeIds":
						typeInit = filterValue;
						jQuery("#reportPlanForm_" + filterCode + "_id").val(filterValue);
						break;
					case "groupIds":
						groupIdsInit = filterValue;
						jQuery("#reportPlanForm_" + filterCode + "_id").val(filterValue);
						break;
					case "includeUnCheck":
						jQuery("#reportPlanForm_" + filterCode).attr("checked","checked");
						break;
					case "printer":
					case "paperNumber":
					case "oriantation":
					case "scale":
						if(printSettingObj[filterCode]){
							jQuery("#reportPlanForm_" + filterCode).val(printSettingObj[filterCode]);
						}else{
							jQuery("#reportPlanForm_" + filterCode).val(filterValue);
						}
						break;
					default:
						jQuery("#reportPlanForm_" + filterCode).val(filterValue);
						break;
					}
				});
		reportPlanFormInitTreeSelect(orgInit,branchInit, deptInit,deptTypesInit, personTypeInit,deptTypeInit,deptDetailInit,typeInit,groupIdsInit,personTypea12Init);
	}
	//树初始化
	function reportPlanFormInitTreeSelect(orgInit,branchInit, deptInit,deptTypesInit, personTypeInit,deptTypeInit,deptDetailInit,typeInit,groupIdsInit,personTypea12Init) {
		//单位
		var sql = "select orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon from T_Org where disabled=0 and orgCode<>'XT' ORDER BY orgCode";
		jQuery("#reportPlanForm_orgCode").treeselect({
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
		//院区
		sql = "select branchCode id,branchName name,'' parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon from t_branch where disabled = '0' and branchCode <> 'XT' order by branchCode";
		jQuery("#reportPlanForm_branchCode").treeselect({
			optType : "single",
			dataType : 'sql',
			sql : sql,
			exceptnullparent :false,
			lazy : false,
			minWidth : '120px',
			selectParent : false,
			ifr:true,
			initSelect : branchInit
		});
		//部门
		sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department where disabled=0 and deptId <> 'XT'"
		sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ";
		sql += " ORDER BY orderCol ";
		jQuery("#reportPlanForm_deptIds").treeselect({
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
		//部门类别
		sql = "select deptTypeName id,deptTypeName name,'-1' pId  from t_deptType where disabled = '0'";
		jQuery("#reportPlanForm_deptTypes").treeselect({
			optType : "multi",
			dataType : 'sql',
			sql : sql,
			exceptnullparent :false,
			lazy : false,
			minWidth : '120px',
			selectParent : false,
			ifr:true,
			initSelect : deptTypesInit
		});
		//人员类别
		jQuery("#reportPlanForm_empTypes").treeselect({
			dataType : "sql",
			optType : "multi",
			sql : "SELECT t.name id,t.name,(select name from t_personType b where b.id =t.parentType)  parent FROM t_personType t where t.disabled=0  ORDER BY t.code",
			exceptnullparent : false,
			selectParent : false,
			lazy : false,
			minWidth : '120px',
			ifr:true,
			initSelect : personTypeInit
		});
		//人员类别
		jQuery("#reportPlanForm_empTypesa12").treeselect({
			dataType : "sql",
			optType : "multi",
			sql : "SELECT t.name id,t.name,(select name from t_personType b where b.id =t.parentType)  parent FROM t_personType t where t.disabled=0  ORDER BY t.code",
			exceptnullparent : true,
			selectParent : true,
			lazy : false,
			minWidth : '120px',
			chkboxType: {"Y":"", "N":""},
			ifr:true,
			initSelect : personTypea12Init
		});
		//类别
		var reportTypeTreeNodeStr = jQuery("#reportPlanForm_reportTypeTreeNodeStr").val();
		var typeNodes = [];
		if(reportTypeTreeNodeStr){
			typeNodes = JSON.parse(reportTypeTreeNodeStr);
		}
		jQuery("#reportPlanForm_typeIds").treeselect({
			optType : "multi",
			dataType : 'nodes',
			zNodes : typeNodes,
			exceptnullparent : false,
			selectParent : false,
			ifr:true,
			lazy : false,
			minWidth : '120px',
			initSelect : typeInit
		});
		//部门类别选择
		jQuery("#reportPlanForm_a7").find("input:radio[name=deptType]").click(function(){
			var deptType = jQuery(this).val();
			if(deptType=='level'){
				jQuery("#reportPlanForm_deptLevelFrom").removeAttr("disabled");
				jQuery("#reportPlanForm_deptLevelTo").removeAttr("disabled");
			}else if(deptType=='end'){
				jQuery("#reportPlanForm_deptLevelFrom").attr("disabled","true").val("");
				jQuery("#reportPlanForm_deptLevelTo").attr("disabled","true").val("");
			}else{
				jQuery("#reportPlanForm_deptLevelFrom").attr("disabled","true").val("");
				jQuery("#reportPlanForm_deptLevelTo").attr("disabled","true").val("");
			}
			jQuery("#reportPlanForm_deptDetailIds").val("");
			jQuery("#reportPlanForm_deptDetailIds_id").val("");
		});
		if(deptTypeInit||deptDetailInit){
			jQuery("#reportPlanForm_a7").find("input:radio[name=deptType][value="+deptTypeInit+"]").attr("checked","checked");
			var deptLevelFrom = jQuery("#reportPlanForm_deptLevelFrom").val();
			var deptLevelTo = jQuery("#reportPlanForm_deptLevelTo").val();
			var sql = "";
			if(!deptDetailInit){
				deptDetailInit = "";
			}
			if(deptTypeInit=='level'){
				jQuery("#reportPlanForm_deptLevelFrom").removeAttr("disabled");
				jQuery("#reportPlanForm_deptLevelTo").removeAttr("disabled");
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
				jQuery("#reportPlanForm_deptDetailIds").treeselect({
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
				jQuery("#reportPlanForm_deptLevelFrom").attr("disabled","true");
				jQuery("#reportPlanForm_deptLevelTo").attr("disabled","true");
				sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
				sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
				sql += " ORDER BY orderCol ";
				jQuery("#reportPlanForm_deptDetailIds").treeselect({
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
				jQuery("#reportPlanForm_deptLevelFrom").attr("disabled","true");
				jQuery("#reportPlanForm_deptLevelTo").attr("disabled","true");
				sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
				sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
				sql += " ORDER BY orderCol ";
				jQuery("#reportPlanForm_deptDetailIds").treeselect({
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
		jQuery("#reportPlanForm_deptDetailIds").bind("focus",function(){
			var deptType = jQuery("#reportPlanForm_a7").find("input:radio[name=deptType][checked=checked]").val();
			var deptLevelFrom = jQuery("#reportPlanForm_deptLevelFrom").val();
			var deptLevelTo = jQuery("#reportPlanForm_deptLevelTo").val();
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
				jQuery("#reportPlanForm_deptDetailIds").treeselect({
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
				jQuery("#reportPlanForm_deptDetailIds").treeselect({
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
				jQuery("#reportPlanForm_deptDetailIds").treeselect({
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
		jQuery("#reportPlanForm_personIdName").combogrid({
	        url : 'comboGridSqlList',
	        combogridId:"reportPlanForm_combogrid",
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
	        	jQuery("#reportPlanForm_personId").val(ui.item.PERSONID).blur();
	        	jQuery("#reportPlanForm_personIdName").val(ui.item.NAME).blur();
	            return false;
	        }
	    });
		//汇总项目
		var groupSelectItemStr = "${reportDefine.groupSelectItems}";
		var groupNodes = [];
		if(groupSelectItemStr){
			var groupSelectItemArr = groupSelectItemStr.split(";");
			for(var index in groupSelectItemArr){
				var groupSelectItemTemp = groupSelectItemArr[index];
				var groupItemArr = groupSelectItemTemp.split(":");
				if(groupItemArr.length == 2){
					groupNodes.push({id:groupItemArr[0],name:groupItemArr[1]})
				}
			}
		}
		jQuery("#reportPlanForm_groupIds").treeselect({
			optType : "multi",
			dataType : 'nodes',
			zNodes : groupNodes,
			exceptnullparent : true,
			lazy : false,
			initSelect:groupIdsInit,
			ifr:true,
			minWidth : '120px',
			selectParent : true
		});    	
	}

	//高级按钮的方法 
	function setReportPlanItems() {
		var typeId = jQuery("#reportPlanForm_typeList").val();
		var url = "reportPlanSet?typeId="+ typeId+"&subSystemPrefix=${subSystemPrefix}&defineId=${defineId}";
		url = encodeURI(url);
		$.pdialog.open(url, 'reportPlanSet', '${subSystemPrefix}项信息', {
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
	function reportPlanTypeChange() {
		reportPlanItems = {};
		reportPlanFormClearContent();
		var typeId = jQuery("#reportPlanForm_typeList").val();
		var issueType = jQuery("#reportPlanForm_typeList").find("option:selected").attr("issueType");
		jQuery("#reportPlanForm_planList").empty();
		jQuery("#reportPlanForm_items2").empty();
		jQuery("#reportPlanForm_items1").empty();
		jQuery.ajax({
					url : 'reportPlanTypeChange',
					data : {
						defineId : "${defineId}",
						typeId : typeId
					},
					type : 'post',
					dataType : 'json',
					async : true,
					error : function(data) {
						alertMsg.error("系统错误！");
					},
					success : function(data) {
						var reportPlans = data.reportPlans;
						var reportPlanSysStr = data.reportPlanSysStr;
						var reportItems = data.reportItems;
						var modelStatusStr = data.modelStatusStr;
						jQuery("#reportPlanForm_modelStatusStr").val(modelStatusStr);
						sysReportPlanObj = {};
						if(reportPlanSysStr){
							sysReportPlanObj = JSON.parse(reportPlanSysStr);
						}
						if (issueType == "次") {
							jQuery("#reportPlanForm_a1_yearDiv").show();
							jQuery("#reportPlanForm_a1_periodDiv").hide();
							jQuery("#reportPlanForm_c1_yearDiv").show();
							jQuery("#reportPlanForm_c1_periodDiv").hide();
						} else {
							jQuery("#reportPlanForm_a1_yearDiv").hide();
							jQuery("#reportPlanForm_a1_periodDiv").show();
							jQuery("#reportPlanForm_c1_yearDiv").hide();
							jQuery("#reportPlanForm_c1_periodDiv").show();
						}
						allReportItems = reportItems;
						if (reportItems) {
							var reportItemHtml = "";
							jQuery.each(reportItems, function(key, reportItem) {
								reportItemHtml += '<option value="'+ reportItem.itemCode
									+ '" ondblclick="reportPlanItemMoveToRight(this)">'
									+ reportItem.itemShowName + '</option>';
							});
							jQuery("#reportPlanForm_items1").html(reportItemHtml);
							reportPlanItems = {};
							if(requiredItems){
								var reportPlanItemIndex = 0;
								jQuery.each(requiredItems,function(index,itemCode){
									var colName = jQuery("#reportPlanForm_items1 option[value='"+ itemCode + "']").text();
									jQuery("#reportPlanForm_items1 option[value='"+ itemCode + "']").remove();
									jQuery("#reportPlanForm_items2").append("<option value='" + itemCode
												+ "'>"
												+ colName + "</option>");
									reportPlanItems[itemCode] = {"name":colName,"oldName":colName,isThousandSeparat:"1",
											headerFontIndex:'1',fontIndex:'0',headerTextColor:'#000000',sn:reportPlanItemIndex};
									reportPlanItemIndex ++;
								});
							}
						}
						if(reportPlans) {
							var reportPlanHtml = "";
							jQuery.each(reportPlans,function(key, reportPlan) {
												reportPlanHtml += '<option value="'+reportPlan.planId+'" >'
														+ reportPlan.planName
														+ '</option>';
											});
							jQuery("#reportPlanForm_planList").html(reportPlanHtml);
							//初始化方案 
							var options = jQuery("#reportPlanForm_planList option");
							if (options.length > 0) {
								//设置第一个为选中状态
								jQuery("#reportPlanForm_planList option").eq(0).attr('selected', 'true');
								reportPlanInit();
							}
						}
					}
		});
	}
	/*删除方案*/
	function reportPlanFormPlanDel(){
		var selectItem = jQuery("#reportPlanForm_planList").find("option:selected");
		var planName = selectItem.text();
		var planId = selectItem.val();
		if(sysReportPlanObj[planName]){
			alertMsg.error("系统方案不能删除！");
			return;
		}
		if(!planId){
			alertMsg.error("请选择方案！");
			return;
		}
		alertMsg.confirm("确认要删除该方案?",{
    		okCall:function(){
    			var url = "reportPlanGridEdit?planId="+planId+"&oper=del";  
    			url = encodeURI(url);
		    	$.post(url,{},function(data){
		    		formCallBack(data);
		    		if(data.statusCode == 200){
		    			jQuery("#reportPlanForm_planList option[value='"+ planId + "']").remove();
		    		}
		    	});
    		}
		});
	}
	//打印预览
	function reportPlanPrintView(){
		printViewShow = "reLoad";
		//初始化打印预览treeList
		var reverseColumn = "${reportDefine.reverseColumn}";//反转
		if(reverseColumn){//反转
			initReportPlanFormReverseGrid("reLoad");
		}else{
			initReportPlanFormGrid("reLoad");
		}
	}
	//排序
	function sortReportPlanItems(){
		var winTitle = '${subSystemPrefix}项排序';
		var url = "sortReportPlanItems?subSystemPrefix=${subSystemPrefix}";
		url = encodeURI(url);
		$.pdialog.open(url,'sortReportPlanItems',winTitle, {mask:true,width : 650,height : 400,maxable:true,resizable:true,ifr:true});
		stopPropagation();
	}
	//打印设置
	function reportPlanFormPrintSet(random){
		var grid = eval("(reportPlanForm_gridtable)");
		var printXml = grid.func("GetProp", "Print");
		var printDOM = grid.func("dom_new", printXml);
		var header = grid.func("dom_find", printDOM + "\r\n header");
		if(header){
			var layerDoms = grid.func("dom_find", header + "\r\n layer");
			if(layerDoms){
				var layerIdArr = layerDoms.split(",");
				for(var index in layerIdArr){
					if(index == 0){
						continue;
					}
					var layerId = layerIdArr[index];
					grid.func("dom_Delete", layerId);
				}
			}
		}else{
			header = grid.func("dom_new", "");
		}
		grid.func("dom_setName", header + "\r\n header");
		grid.func("dom_insertChild", printDOM + "\r\n 0 \r\n" + header);
		var layerDom;
		var layerSn = 1;
		var layerDom;
		if(!"${reportDefine.groupSelectItems}"){ 
			var typeStr = jQuery("#"+random+"_typeJson").val();
			var typeName = jQuery("#reportPlanForm_typeList").find("option:selected").text();
			var text = "${subSystemPrefix}类别:" + typeName;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var periodTime = jQuery("#reportPlanForm_periodTime").val();
		if(periodTime){
			var text = "期间:" + periodTime;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var checkYear = jQuery("#reportPlanForm_checkYear").val();
		var checkNumber = jQuery("#reportPlanForm_checkNumber").val();
		if(checkYear||checkNumber){
			var text = "发放次数:";
			if(checkYear){
				text += checkYear + "年";
			}else{
				text += "    " + "年";
			}
			if(checkNumber){
				text += checkNumber + "次";
			}else{
				text += " " + "次";
			}
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var fromPeriodTime = jQuery("#reportPlanForm_fromPeriodTime").val();
		var toPeriodTime = jQuery("#reportPlanForm_toPeriodTime").val();
		if(fromPeriodTime||toPeriodTime){
			var text = "期间:";
			if(fromPeriodTime){
				text += fromPeriodTime;
			}else{
				text += "    ";
			}
			text += "至";
			if(toPeriodTime){
				text += toPeriodTime;
			}
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var fromCheckYear = jQuery("#reportPlanForm_fromCheckYear").val();
		var fromCheckNumber = jQuery("#reportPlanForm_fromCheckNumber").val();
		var toCheckYear = jQuery("#reportPlanForm_toCheckYear").val();
		var toCheckNumber = jQuery("#reportPlanForm_toCheckNumber").val();
		if(fromCheckYear||fromCheckNumber||toCheckYear||toCheckNumber){
			var text = "发放次数:从";
			if(fromCheckYear){
				text += fromCheckYear + "年";
			}else{
				text += "    " + "年";
			}
			if(fromCheckNumber){
				text += fromCheckNumber + "年";
			}else{
				text += "    " + "年";
			}
			text += "至";
			if(toCheckYear){
				text += toCheckYear + "年";
			}else{
				text += "    " + "年";
			}
			if(toCheckNumber){
				text += toCheckNumber + "年";
			}else{
				text += "    " + "年";
			}
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var orgCode = jQuery("#reportPlanForm_orgCode").val();
		if(orgCode){
			var text = "单位:" + orgCode;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var branchCode = jQuery("#reportPlanForm_branchCode").val();
		if(branchCode) {
			var text = "院区:" + branchCode;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var deptIds = jQuery("#reportPlanForm_deptIds").val();
		if(deptIds){
			var text = "部门:" + deptIds;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var deptTypes = jQuery("#reportPlanForm_deptTypes").val();
		if(deptTypes){
			var text = "部门类别:" + deptTypes;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var empTypes = jQuery("#reportPlanForm_empTypes").val();
		if(empTypes){
			var text = "人员类别:" + empTypes;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var empTypesa12 = jQuery("#reportPlanForm_empTypesa12").val();
		if(empTypes){
			var text = "人员类别:" + empTypesa12;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var personName = jQuery("#reportPlanForm_personName").val();
		if(personName){
			var text = "人员姓名:" + personName;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var personCode = jQuery("#reportPlanForm_personCode").val();
		if(personCode){
			var text = "人员编码:" + personCode;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var deptTypeText = "";
		var deptType = jQuery("#reportPlanForm_a7").find("input:radio[name=deptType][checked=checked]").val();
		if(deptType == "all"){
			deptTypeText = "全部";
		}else if(deptType == "end"){
			deptTypeText = "末级";
		}else if(deptType == "level"){
			deptTypeText = "级次";
			var deptLevelFrom = jQuery("#reportPlanForm_deptLevelFrom").val();
			var deptLevelTo = jQuery("#reportPlanForm_deptLevelTo").val();
			if(deptLevelFrom||deptLevelTo){
				if(deptLevelFrom){
					deptTypeText += " 从" + deptLevelFrom;
				}else{
					deptTypeText += " 从" + " ";
				}
				deptTypeText += "至";
				if(deptLevelTo){
					deptTypeText += deptLevelTo;
				}
			}
		}
		if(deptTypeText){
			var text = "部门:" + deptTypeText;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var deptDetailIds = jQuery("#reportPlanForm_deptDetailIds").val();
		if(deptDetailIds){
			var text = "部门明细:" + deptDetailIds;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var typeIds = jQuery("#reportPlanForm_typeIds").val();
		if(typeIds){
			var text = "${subSystemPrefix}类别:" + typeIds;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var personIdName = jQuery("#reportPlanForm_personIdName").val();
		if(personIdName){
			var text = "人员:" + personIdName;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var groupIds = jQuery("#reportPlanForm_groupIds").val();
		if(groupIds){
			var text = "汇总项目:" + groupIds;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var includeUnCheck = jQuery("#reportPlanForm_includeUnCheck").attr("checked");
		if(includeUnCheck){
			var text = "含未审核:是";
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		printXml = grid.func("dom_export", printDOM); //输出xml
		grid.func("SetProp", "Print \r\n" + printXml); //完成
		grid.func("dom_delete", printDOM); //销毁对象 
	}
	function reportPlanFormLayerDom(layerDom,grid,header,layerSn,text){
		if(text&&text.length>20){
			text = text.substr(0,20) + "...";
		}
		var layerY = 50 + Math.floor((layerSn-1)/3)*20;
		var layerX = layerSn%3;
		var align = "left";
		switch(layerX){
			case 1:
				align = "left";
				break;
			case 2:
				align = "center";
				break;
			case 0:
				align = "right";
				break;	
		}
		layerDom = grid.func("dom_new", ""); //创建一个空的DOM元素
		grid.func("dom_setName", layerDom + "\r\n layer"); //设定元素名
		grid.func("DOM_SetProp", layerDom + "\r\n y \r\n "+layerY); 
		grid.func("DOM_SetProp", layerDom + "\r\n align \r\n "+align); 
		grid.func("dom_setProp", layerDom + "\r\n #text \r\n "+text); //设定text
		grid.func("dom_insertChild", header + "\r\n -1 \r\n" + layerDom);
	}
</script>
<div class="page">
	<div class="pageContent">
			<div>
				<div style="width: 1px;height: 1px;" id="reportPlanForm_gridtable_container"></div>
				<input type="hidden" id="reportPlanForm_modelStatusStr" value='<s:property value="modelStatusStr" escapeHtml="false"/>'>
				<input type="hidden" id="reportPlanForm_reportTypeTreeNodeStr" value='<s:property value="reportTypeTreeNodeStr" escapeHtml="false"/>'>
				<input type="hidden" id="reportPlanForm_reportPlanSysStr" value='<s:property value="reportPlanSysStr" escapeHtml="false"/>'>
				<input type="hidden" id="reportPlanForm_planItemsJsonStr" value='<s:property value="planItemsJsonStr" escapeHtml="false"/>'>
			</div>
			<div class="pageFormContent" layoutH="57" style="width:700px;background: #e4ebf6;">
				<div style="float: left;">
					<table>
						<tr id="reportPlanForm_typeListTR1">
							<td><span>${subSystemPrefix}类别:</span></td>
							<td colspan="2">
								<select onchange="reportPlanTypeChange()" id="reportPlanForm_typeList" style="width: 120px;">
									<c:forEach var="reportType" items="${reportTypes}">
										<option value="${reportType.typeId}" issueType="${reportType.issueType}">${reportType.typeName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr height="10px" id="reportPlanForm_typeListTR2">
							<td colspan="3">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td style="text-align: top"><span style="margin-top: 20px">报表标题:</span>
							</td>
							<td colspan="2"><input id="reportPlanForm_reportTitle" type="text" style="width: 120px;"  value="${reportDefine.defineName}"/></td>
						</tr>
						<tr height="10px">
							<td colspan="3">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td valign="top">方案列表:</td>
							<td colspan="2"><select id="reportPlanForm_planList" size="8" style="width: 120px;">
								<c:forEach var="reportPlan" items="${reportPlans}">
									<option value="${reportPlan.planId}">${reportPlan.planName}</option>
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
							<td colspan="2"><input id="reportPlanForm_planName" type="text" style="width: 120px;"/></td>
						</tr>
						<tr height="10px">
							<td colspan="3">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<table>
									<tr>
										<td>公共:</td>
										<td><input id="reportPlanForm_toPublic" type="checkbox" style="margin-top: 0px" /></td>
										<td>部门:</td>
										<td><input id="reportPlanForm_toDepartment" type="checkbox" style="margin-top: 0px" /></td>
										<td>角色:</td>
										<td><input id="reportPlanForm_toRole" type="checkbox" style="margin-top: 0px" /></td>
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
								保存方案 :<input id="reportPlanForm_savePlan" type="checkbox">
								<div class="buttonActive" id="reportPlanForm_delPlan" style="float: right;margin-right: 5px;width: 60px;">
									<div class="buttonContent">
										<button type="button" onclick="reportPlanFormPlanDel()">删除方案</button>
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
								<legend style="color: #06c; font-weight: 800; background: #fff;">${subSystemPrefix}项配置</legend>
								<div style="width: 250px">
									<div class="buttonActive" style="margin-left: 170px;">
										<div class="buttonContent">
											<button type="button" onclick="sortReportPlanItems()">排序</button>
										</div>
									</div>
									<div class="buttonActive" style="margin-left: 5px;">
										<div class="buttonContent">
											<button type="button" onclick="setReportPlanItems()">高级</button>
										</div>
									</div>
									<div style="float: left;">
										<div style="float: left; margin-left: 5px;">
											<select id="reportPlanForm_items1" size="12" multiple="multiple" style="width: 100px">
											</select>
										</div>
										<div style="float: left;">
											<div style="width: 40px">
												<button id="reportPlanForm_toRight" style="width: 40px; margin-top: 20px;" type="button"
													onclick="reportPlanItemtoRight()">&gt;</button>
											</div>
											<div style="width: 40px">
												<button id="greportPlanForm_AlltoRight" style="width: 40px; margin-top: 20px" type="button"
													onclick="reportPlanItemAlltoRight()">&gt;&gt;</button>
											</div>
											<div style="width: 40px">
												<button id="reportPlanForm_toLeft" style="width: 40px; margin-top: 20px" type="button"
													onclick="reportPlanItemtoLeft()">&lt;</button>
											</div>
											<div style="width: 40px">
												<button id="reportPlanForm_ALLtoLeft" style="width: 40px; margin-top: 20px" type="button"
													onclick="reportPlanItemALLtoLeft()">&lt;&lt;</button>
											</div>
										</div>
										<div style="float: left;">
											<div>
												<select id="reportPlanForm_items2" multiple="multiple" size="12"  style="width: 100px;">
												</select>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset style="height: 246px;width:205px; float:left;margin:3px;color: #333;border: #06c solid 1px;">
								<legend  style="color: #06c;font-weight: 800;background: #fff;">查询条件</legend>

								<div id="reportPlanForm_a1" style="display: none;">
									<div style="margin-top: 5px;" id="reportPlanForm_a1_periodDiv">
										<label style="width:52px;">期间:</label>
										<input id="reportPlanForm_periodTime" style="width: 45px;"
											onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}">
									</div>
									<div style="margin-top: 5px;" id="reportPlanForm_a1_yearDiv">
										<label style="width:52px;">发放次数:</label>
										<input id="reportPlanForm_checkYear" style="width: 35px;"
											onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyy'});}">
										年
										<select id="reportPlanForm_checkNumber" style="float:none">
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
								<div id="reportPlanForm_c1" style="display: none;">
									<div style="margin-top: 5px;"
										id="reportPlanForm_c1_periodDiv">
										<label style="width:52px;">期间:</label>
										从<input id="reportPlanForm_fromPeriodTime" style="width: 43px;"
											onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}">
										至<input id="reportPlanForm_toPeriodTime" style="width: 43px;"
											onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}">
									</div>
									<div id="reportPlanForm_c1_yearDiv">
										<div style="margin-top: 5px;">
										<label style="width:52px;">发放次数:</label>
											从
											<input id="reportPlanForm_fromCheckYear" style="width: 35px;" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyy'});}">
										年
										<select id="reportPlanForm_fromCheckNumber" style="float:none">
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
											<input id="reportPlanForm_toCheckYear" style="width: 35px;" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyy'});}">
											年
										<select id="reportPlanForm_toCheckNumber" style="float:none;">
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
								<div style="margin-top: 5px;display: none;" id="reportPlanForm_a2">
									<label style="width:52px;">单位:</label>
									<input id="reportPlanForm_orgCode" style="width: 100px;" />
									<input id="reportPlanForm_orgCode_id" type="hidden"/>
								</div>
								<s:if test="%{herpType == 'S2' || herpType == 'M2'}">
								<div style="margin-top: 5px;display: none;" id="reportPlanForm_a13">
									<label style="width:52px;">院区:</label>
									<input id="reportPlanForm_branchCode" style="width: 100px;" />
									<input id="reportPlanForm_branchCode_id" type="hidden"/>
								</div>
								</s:if>
								<div style="margin-top: 5px;display: none;" id="reportPlanForm_a3">
									<label style="width:52px;">部门:</label>
									<input id="reportPlanForm_deptIds" style="width: 100px;" /> 
									<input id="reportPlanForm_deptIds_id" type="hidden" />
								</div>
								<div style="margin-top: 5px;display: none;" id="reportPlanForm_a14">
									<label style="width:52px;">部门类别:</label>
									<input id="reportPlanForm_deptTypes" style="width: 100px;" />
									<input id="reportPlanForm_deptTypes_id" type="hidden"/>
								</div>
								<div style="margin-top: 5px;display: none;" id="reportPlanForm_a4">
									<label style="width:52px;">人员类别:</label>
									<input id="reportPlanForm_empTypes" style="width: 100px; " />
									<input type="hidden" id="reportPlanForm_empTypes_id"/>
								</div>
								<!-- <div style="margin-top: 5px;display: none;" id="reportPlanForm_a12">
									<label style="width:52px;">人员类别:</label>
									<input id="reportPlanForm_empTypesa12" style="width: 100px; " />
									<input type="hidden" id="reportPlanForm_empTypesa12_id"/>
								</div> -->
								<div style="margin-top: 5px;display: none;" id="reportPlanForm_a5">
									<label style="width:52px;">人员姓名:</label> 
									<input id="reportPlanForm_personName" style="width: 100px;">
								</div>
								<div style="margin-top: 5px;display: none;" id="reportPlanForm_a6">
									<label style="width:52px;">人员编码: </label>
									<input id="reportPlanForm_personCode" style="width: 100px;">
								</div>
								<div style="margin-top: 5px;display: none;" id="reportPlanForm_a7">
									<label style="width:52px;">部门: </label>
									<input type="radio" name="deptType" value="all"/>全部
									<input type="radio" name="deptType" value="end"/>末级
									<input type="radio" name="deptType" value="level"/>级次
									从<select id="reportPlanForm_deptLevelFrom" style="float: none;" disabled="disabled">
										<option value=""></option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
									</select>
									到<select id="reportPlanForm_deptLevelTo" style="float: none;" disabled="disabled">
										<option value=""></option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
									</select>
								</div>
								<div style="margin-top: 5px;display: none;" id="reportPlanForm_a8">
									<label style="width:52px;">部门明细: </label>
									<input  id="reportPlanForm_deptDetailIds" style="width: 100px;">
									<input  id="reportPlanForm_deptDetailIds_id" type="hidden">
								</div>
								<div style="margin-top: 5px;display: none;" id="reportPlanForm_a9">
									<label style="width:52px;">${subSystemPrefix}类别: </label>
									<input  id="reportPlanForm_typeIds" style="width: 100px;">
									<input  id="reportPlanForm_typeIds_id" type="hidden">
								</div>
								<div style="margin-top: 5px;display: none;" id="reportPlanForm_a10">
									<label style="width:52px;">人员: </label>
									<input id="reportPlanForm_personIdName" style="width: 100px;">
									<input id="reportPlanForm_personId" type="hidden">
								</div>
								<div style="margin-top: 5px;display: none;" id="reportPlanForm_a12">
									<label style="width:52px;">汇总项目: </label>
									<input  id="reportPlanForm_groupIds" style="width: 100px;">
									<input  id="reportPlanForm_groupIds_id" type="hidden">
								</div>
								<div style="margin-top: 5px;display: none;" id="reportPlanForm_a11">
									<label style="width:52px;">含未审核: </label>
									<span style="width: 100px;display: block;">
										<input id="reportPlanForm_includeUnCheck" type="checkBox">
									</span>
								</div>
							</fieldset>
						</div>
						<div style="width:485px">
								<div style="margin-top: 5px;display: none;width: 400px;" id="reportPlanForm_d1">
									<label style="width:65px;">每行显示数: </label>
									<input id="reportPlanForm_colColumns" style="width: 100px;">
								</div>
								<div style="margin-top: 5px;width: 400px;display: none;" id="reportPlanForm_d2">
									<label style="width:65px;">打印机: </label>
									<select id="reportPlanForm_printer" style="float: none;">
									</select>
								</div>
								<div style="margin-top: 5px;width: 400px;display: none;" id="reportPlanForm_d3">
									<label style="width:65px;">纸型: </label>
									<select id="reportPlanForm_paperNumber" style="float: none;">
									</select>
								</div>
								<div style="margin-top: 5px;width: 400px;display: none;" id="reportPlanForm_d4">
									<label style="width:65px;">方向: </label>
									<select id="reportPlanForm_oriantation" style="float: none;">
										<option value="portrait">纵向</option>
										<option value="landscape">横向</option>
									</select>
								</div>
								<div style="margin-top: 5px;width: 400px;display: none;" id="reportPlanForm_d5">
									<label style="width:65px;">缩放比例: </label>
									<input id="reportPlanForm_sfbl" style="width: 100px;" value="100">
								</div>
								<div style="margin-top: 5px;width: 400px;display: none;" id="reportPlanForm_d6">
									<label style="width:65px;">打印比例: </label>
									<input id="reportPlanForm_scale" style="width: 100px;" value="100">
								</div>
								<div style="margin-top: 5px;width: 400px;display: none;" id="reportPlanForm_d7">
									<div class="buttonActive" style="margin-left: 10px;">
										<div class="buttonContent">
											<button type="button" onclick="reportPlanPrintView()">打印预览</button>
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
								<button type="button" onclick="reportPlanSubmit()">确定</button>
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
	</div>
</div>