
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var reportPlanDefine = {
		key:"${random}_reportPlan_gridtable",
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
			}
		},
		callback:{
			onComplete:function(id){
				var grid = eval("("+id+")");
				grid.func("EnableMenu","print,copy,addSort,selectCol,export,separate,showRuler,enter \r\n false");//打印预览,复制,加入多重排序,自定义显示隐藏列,转换输出,分屏冻结,显示/隐藏左标尺,回车键行为	
				//打印设置
				var printer = jQuery("#${random}_filter_printer").val();
				var paperNumber = jQuery("#${random}_filter_paperNumber").val();
				var oriantation = jQuery("#${random}_filter_oriantation").val();
				var scale = jQuery("#${random}_filter_scale").val();
				
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
				if("${reportDefine.displayType}" !== "Report"){
					//工资类别
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
					var customLayout = jQuery("#${random}_reportCustomLayout").val();
					if(customLayout){
						grid.func("setCustom", customLayout);
					}
				}else{
					grid.func("Swkrntpomzqa", "1, 2"); //所有单元格内容不得修改
				}
			}
		}
	}; 
	
    supcanGridMap.put('reportPlan_gridtable_${random}',reportPlanDefine); 
 	jQuery(document).ready(function(){
 		if("${reportDefine.displayType}" == "Report"){
 			jQuery("#${random}_report_customLayoutLi").hide();
 		}
		if("${reportDefine.groupSelectItems}"){
			jQuery("#${random}_filterSpan_1").hide();
		}
 	    /*隐藏部分查询框*/
 	    var showFilterStr = "${reportDefine.filters}";
 	    if(showFilterStr){
 	    	var showFilters = showFilterStr.split(",");
 	    	jQuery.each(showFilters,function(key,value){
 	    		jQuery("#${random}_filterSpan_"+value).show();
 	    		jQuery("#${random}_filterSpan_"+value).css("display","inline-block");
 	    		//jQuery("#${random}_filterSpan_"+value).width(150);
 	    		if(value.indexOf("b") > -1){
 	    			jQuery("#${random}_filterSpan_"+value).width(120);
 	    		}else if(value=='a7'){
 	    			jQuery("#${random}_filterSpan_"+value).width(303);	
 	    		}else{
 	    			jQuery("#${random}_filterSpan_"+value).width(150);	
 	    		}
 	    	});
 	    }
 	   /*必填项*/
		var requiredFilterStr = "${reportDefine.requiredFilter}";
		if (requiredFilterStr) {
			var requiredFilters = requiredFilterStr.split(",");
			jQuery.each(requiredFilters, function(key, value) {
				jQuery("#${random}_filterSpan_" + value).find("input:visible").addClass("required");
			});
		}
 	    /*次结或者月结*/
 	    var issueType = "";
		var typeStr = jQuery("#${random}_typeJson").val();
		var typeName = "";
		if(typeStr){
			var typeJson = JSON.parse(typeStr);
			issueType = typeJson.issueType;
			typeName = typeJson.typeName;
		}
 	    if(issueType == "次"){
 	    	jQuery("#${random}_filterSpan_a1_period").hide();
 	    	jQuery("#${random}_filterSpan_a1_year").show();
 	    	//jQuery("#${random}_filterSpan_a1_year").arent().width(300).show();
 	    	jQuery("#${random}_filterSpan_c1_period").hide();
 	    	jQuery("#${random}_filterSpan_c1_year").show().parent().width(300);
 	    }else{
 	    	jQuery("#${random}_filterSpan_a1_period").show();
 	    	jQuery("#${random}_filterSpan_a1_year").hide();
 	    	jQuery("#${random}_filterSpan_c1_period").show();
 	    	jQuery("#${random}_filterSpan_c1_year").hide();
 	    }
 	    
 	   	var reportPlanFullSize = jQuery("#container").innerHeight()-82;
 	   	var searchBarHeight = jQuery("#${random}_reportPlan_searchBar").height();
		//jQuery("#reportPlan_searchBar").height(95);
		jQuery("#${random}_reportPlan_gridtable_div").height(reportPlanFullSize-searchBarHeight);
		jQuery("#${random}_reportPlan_gridtable_container").height(reportPlanFullSize-searchBarHeight-10);
 	    
 	    
 	    /*工资类别名称*/
 	    jQuery("#${random}_filter_type").text(typeName);
		var reverseColumn = "${reportDefine.reverseColumn}";//反转
		if(reverseColumn){//反转
			reportReverseGrid("${random}","${reportDefine.defineId}","${typeId}");
		}else{
			reportPlanGrid("${random}","${reportDefine.defineId}","${typeId}");
		}
		/*打印预览*/
		jQuery("#${random}_report_printView").bind("click",function(){
			var grid = eval("("+'reportPlan_gridtable_${random}'+")");
			if("${reportDefine.displayType}" !== "Report"){
				reportPlanShowPrintSet("${random}");
				grid.func("PrintPreview","");
			}else{
				grid.func("CallFunc","18");//调用工具条上的功能
			}
		});
		/*打印*/
		jQuery("#${random}_report_print").bind("click",function(){
			var grid = eval("("+'reportPlan_gridtable_${random}'+")");
			if("${reportDefine.displayType}" !== "Report"){
				grid.func("Print","isOpenSysDialog=true");
				reportPlanShowPrintSet("${random}");
			}else{
				grid.func("Print", "isOpenSysDialog=true; WorkSheet=all");
			}
		});
		/*方案*/
		jQuery("#${random}_report_planChange").bind("click",function(){
			var urlString = "reportPlanList?openType=pdialog&title=设置${reportDefine.defineName}方案&defineId=${reportDefine.defineId}&menuId=${menuId}&width=${width}&height=${height}";
			var planId = "${planId}";
			urlString += "&planId="+planId+"&typeId=${typeId}&oper=${random}";
			//排序
			var grid = eval("("+'reportPlan_gridtable_${random}'+")");
			if("${reportDefine.displayType}" !== "Report"){
				var reportCustomLayout = grid.func("getCustom", "");
				jQuery("#${random}_reportCustomLayout").val(reportCustomLayout);
				var customLayoutDOM = grid.func("dom_new", reportCustomLayout);
				var ColsDom = grid.func("dom_find", customLayoutDOM + "\r\n Col");
				var colsArr = ColsDom.split(",");
				var sortColumns = {};
				for(var index in colsArr){
					var colTemp = colsArr[index];
					var colId = grid.func("DOM_GetProp", colTemp + "\r\n id");
					sortColumns[colId] = index;
				}
				grid.func("dom_delete", customLayoutDOM); //销毁对象 
				jQuery("#${random}_report_sortColumns").val(JSON.stringify(sortColumns));
			}
			//打印设置
			var printSettingObj = {};
			var printXml = grid.func("GetProp", "Print");
			var printDOM = grid.func("dom_new", printXml);
			/*打印纸及相关配置*/
			var paper = grid.func("dom_find", printDOM + "\r\n paper");
			if(paper){
				printSettingObj.paperNumber = grid.func("DOM_GetProp", paper + "\r\n paperNumber") + "";
				printSettingObj.oriantation = grid.func("DOM_GetProp", paper + "\r\n oriantation");
				printSettingObj.scale = grid.func("DOM_GetProp", paper + "\r\n scale");
			}
			grid.func("dom_delete", printDOM); //销毁对象 
			jQuery("#${random}_report_printSetting").val(JSON.stringify(printSettingObj));
			urlString = encodeURI(urlString);
			$.pdialog.open(urlString,'dialog${menuId}','设置${reportDefine.defineName}方案', {ifr:true,hasSupcan:"all",mask:true,resizable:false,maxable:false,width : "${width}",height : "${height}"});
		});
		/*保存格式*/
		jQuery("#${random}_report_customLayout").bind("click",function(){
			var planId = "${planId}";
			if(!planId || planId === "null"){
				alertMsg.error("请选择方案！");
				return;
			}
			var grid = eval("("+'reportPlan_gridtable_${random}'+")");
			var reportCustomLayout = grid.func("getCustom", "");
			var url = "saveReportPlanCustomLayout";
	    	$.post(url,{planId:planId,reportCustomLayout:reportCustomLayout},function(data){
	    		formCallBack(data);
	    	});
		});
		/*刷新*/
		jQuery("#${random}_report_refresh").bind("click",function(){
			var grid = eval("("+'reportPlan_gridtable_${random}'+")");
			var checked = jQuery("${random}_filterSpan_hideZeroCell").attr("checked");
			var hideInvalidDeci = jQuery("#${random}_filterSpan_hideInvalidDeci").attr("checked");
			if("${reportDefine.reverseColumn}"){//反转
			var colModelDatas= [
				{name:'rpItems',index:'rpItems',align:'center',totalExpress:"合计",totalAlign:"center",text : '${subSystemPrefix}项目',width:80,isHide:false,editable:false,dataType:'string'}
				];
						var colArr = reportReverseCols("${random}");
						var rpItems = [];
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
								rpItems[key]={itemType:"0",itemCode:"period"+value,scale:2};
							});
						}
						jQuery("#${random}_report_reportItems").val(JSON.stringify(rpItems));
						var reportPlanGrid = reportSupcanPlanGrid(colModelDatas);
						grid.func("build", JSON.stringify(reportPlanGrid));
						var rpFilterObj = reportFilterLoad("${random}","${typeId}");
						rpFilterObj.defineId = "${defineId}";
						rpFilterObj.reportPlanItemsStr = jQuery("#${random}_reportItemsStr").val();
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
								grid.func("load", JSON.stringify(reportGridData));
							}
						});
			}else{//正常
				if("${reportDefine.displayType}" == "Report"){
					var deptIds = jQuery("#${random}_filter_deptIds_id").val();
					var gzStubsDeptNumber = jQuery("#${random}_gzStubsDeptNumber").val();
					gzStubsDeptNumber = +gzStubsDeptNumber;
					if(!gzStubsDeptNumber){
						gzStubsDeptNumber = 10;
					}
					if(deptIds){
						var deptIdArr = deptIds.split(",");
						if(deptIdArr.length > gzStubsDeptNumber){
							alertMsg.error("最多只能选择"+gzStubsDeptNumber+"个部门！");
							return;
						}
					}else{
						alertMsg.error("请选择需要显示工资条的部门！");
						return;
					}
					jQuery.ajax({
						url: 'reportPlanGridList?defineId=${defineId}&columns='+jQuery("#${random}_report_columns").val(),
						data: reportFilterLoad("${random}","${typeId}"),
						type: 'post',
						dataType: 'json',
						async:true,
						error: function(data){
							alertMsg.error("系统错误！");
						},
						success: function(data){
							var reportPlanContents = data.reportPlanContents;
							if(checked == "checked"){
								if(reportPlanContents){
									for(var index in reportPlanContents){
										var reportPlanContent = reportPlanContents[index];
										for(var column in reportPlanContent){
											if(reportPlanContent[column] === 0){
												reportPlanContent[column] = null;
											}
										}
									}
								}
							}
							var reportGridData = {};
							reportGridData.Record = reportPlanContents;
							grid.func("setSource", "ds1 \r\n "+JSON.stringify(reportGridData));
							grid.func("calc", "mode=asynch;range=current");
						}
					});
				}else{
					jQuery.ajax({
						url: 'reportPlanGridList?defineId=${defineId}&columns='+jQuery("#${random}_report_columns").val(),
						data: reportFilterLoad("${random}","${typeId}"),
						type: 'post',
						dataType: 'json',
						async:true,
						error: function(data){
						},
						success: function(data){
							var reportPlanContents = data.reportPlanContents;
							if(checked == "checked"){
								if(reportPlanContents){
									for(var index in reportPlanContents){
										var report = reportPlanContents[index];
										for(var column in report){
											if(report[column] === 0){
												report[column] = null;
											}
										}
									}
								}
							}
							var groupIds = jQuery("#${random}_filter_groupIds_id").val();
							grid.func("SetColProp", "count \r\n Title \r\n 人数");
							grid.func("SetColProp", "count \r\n dataType \r\n int");
							if("${reportDefine.groupIds}"){
								groupIds = "${reportDefine.groupIds}";
							}
							if(groupIds){
								var groupIdArr = groupIds.split(",");
								if(jQuery.inArray("period",groupIdArr) == -1){
									grid.func("SetColProp", "count \r\n Title \r\n 平均人数");
									grid.func("SetColProp", "count \r\n dataType \r\n double");
									if(hideInvalidDeci == 'checked'){
										grid.func("SetColProp", "count \r\n decimal \r\n -1");
									}else{
										grid.func("SetColProp", "count \r\n decimal \r\n 2");
									}
								}
							}
							var reportGridData = {};
							reportGridData.Record = reportPlanContents;
							grid.func("load", JSON.stringify(reportGridData));
						}
					});
				}
			}
		});
		/*部门小计*/
		jQuery("#${random}_filterSpan_deptNameCheck").bind("click",function(){
			var grid = eval("("+'reportPlan_gridtable_${random}'+")");
			var ptChecked = jQuery("#${random}_filterSpan_personTypeNameCheck").attr("checked");
			if(ptChecked){
				jQuery(this).removeAttr("checked");
				alertMsg.error("正在进行人员类别小计！");
				return;
			}
			var cols = grid.func("GetCols","");//获取列数
			var reportItemsStr = jQuery("#${random}_report_reportItems").val();
			var reportItems = JSON.parse(reportItemsStr);
			var colNames = new Array();
			if(cols > 0){
				for(var colIndex = 0;colIndex < cols;colIndex++){
					var colNameTemp = grid.func("GetColProp",colIndex+" \r\n name");//name属性
					colNames.push(colNameTemp);
				}
			}
			var checked = jQuery(this).attr("checked");
			if(checked == "checked"){
				if(jQuery.inArray("deptId", colNames)>-1){
					grid.func("setProp", "sort \r\n deptId");
				}else if(jQuery.inArray("deptCode", colNames)>-1){
					grid.func("setProp", "sort \r\n deptCode");
				}else if(jQuery.inArray("deptName", colNames)>-1){
					grid.func("setProp", "sort \r\n deptName");
				}else{
					alertMsg.error("无可排序的部门列，不可小计！");
					jQuery(this).removeAttr("checked");
					return;
				}
			}
			if(checked == "checked"){
				grid.func("OpenLoadMask","");
				grid.func("SetColProp", "deptName \r\n subtotalExpress \r\n ='小计'");
				for(var itemIndex in reportItems){
					var item = reportItems[itemIndex];
					if(item.itemType=='0'){
						grid.func("SetColProp", item.itemCode+" \r\n subtotalExpress \r\n =@sum");
			 		}
				}
				grid.func("CloseLoadMask","");
			}else{
				grid.func("OpenLoadMask","");
				grid.func("SetColProp", "deptName \r\n subtotalExpress \r\n  ");
				for(var itemIndex in reportItems){
					var item = reportItems[itemIndex];
					if(item.itemType=='0'){
						grid.func("SetColProp", item.itemCode+" \r\n subtotalExpress \r\n ");
			 		}
				}
				grid.func("CloseLoadMask","");
			}
		});
		/*人员类别小计*/
		jQuery("#${random}_filterSpan_personTypeNameCheck").bind("click",function(){
			var grid = eval("("+'reportPlan_gridtable_${random}'+")");
			var ptChecked = jQuery("#${random}_filterSpan_deptNameCheck").attr("checked");
			if(ptChecked){
				jQuery(this).removeAttr("checked");
				alertMsg.error("正在进行部门小计！");
				return;
			}
			var reportItemsStr = jQuery("#${random}_report_reportItems").val();
			var reportItems = JSON.parse(reportItemsStr);
			var cols = grid.func("GetCols","");//获取列数
			var colNames = new Array();
			if(cols > 0){
				for(var colIndex = 0;colIndex < cols;colIndex++){
					var colNameTemp = grid.func("GetColProp",colIndex+" \r\n name");//name属性
					colNames.push(colNameTemp);
				}
			}
			var checked = jQuery(this).attr("checked");
			if(checked == "checked"){
				if(jQuery.inArray("empType", colNames)>-1){
					grid.func("setProp", "sort \r\n empType");
				}else{
					alertMsg.error("无可排序的人员类别列，不可小计！");
					jQuery(this).removeAttr("checked");
					return;
				}
			}
			if(checked == "checked"){
				grid.func("OpenLoadMask","");
				grid.func("SetColProp", "empType \r\n subtotalExpress \r\n ='小计'");
				for(var itemIndex in reportItems){
					var item = reportItems[itemIndex];
					if(item.itemType=='0'){
						grid.func("SetColProp", item.itemCode+" \r\n subtotalExpress \r\n =@sum");
			 		}
				}
				grid.func("CloseLoadMask","");
			}else{
				grid.func("OpenLoadMask","");
				grid.func("SetColProp", "empType \r\n subtotalExpress \r\n  ");
				for(var itemIndex in reportItems){
					var item = reportItems[itemIndex];
					if(item.itemType=='0'){
						grid.func("SetColProp", item.itemCode+" \r\n subtotalExpress \r\n ");
			 		}
				}
				grid.func("CloseLoadMask","");
			}
		});
		/*隐藏无工资人员*/
		jQuery("#${random}_filterSpan_hideRpIsNull").bind("click",function(){
			var grid = eval("("+'reportPlan_gridtable_${random}'+")");
			var reportItemsStr = jQuery("#${random}_report_reportItems").val();
			var reportItems = JSON.parse(reportItemsStr);
			var filterStr = '1=1';
			grid.func("OpenLoadMask","");
			var checked = jQuery(this).attr("checked");
			if(checked == "checked"){
				var filterStrTemp = "";
				for(var itemIndex in reportItems){
					var item = reportItems[itemIndex];
					if(item.itemType=='0'){
						if(filterStrTemp){
							filterStrTemp += " or "+item.itemCode+" !=0 ";
						}else{
							filterStrTemp = " "+item.itemCode+" !=0 ";
						}
					}
				}
				if(filterStrTemp){
					filterStr = filterStr + " and (" + filterStrTemp + ")";	
				}
			}
			grid.func("Filter", filterStr);
			grid.func("CloseLoadMask","");
		});
		/*隐藏无效小数*/
		jQuery("#${random}_filterSpan_hideInvalidDeci").bind("click",function(){
			var grid = eval("("+'reportPlan_gridtable_${random}'+")");
			var reportItemsStr = jQuery("#${random}_report_reportItems").val();
			var reportItems = JSON.parse(reportItemsStr);
			grid.func("OpenLoadMask","");
			var checked = jQuery(this).attr("checked");
			if(checked == "checked"){
				for(var itemIndex in reportItems){
					var item = reportItems[itemIndex];
					if(item.itemType=='0'){
						grid.func("SetColProp", item.itemCode+" \r\n decimal \r\n -1");
					}
				}
				grid.func("SetColProp","count \r\n decimal \r\n -1");
			}else{
				for(var itemIndex in reportItems){
					var item = reportItems[itemIndex];
					if(item.itemType=='0'){
						grid.func("SetColProp", item.itemCode+" \r\n decimal \r\n 2");
					}
				}
				grid.func("SetColProp","count \r\n decimal \r\n 2");
			}
			grid.func("CloseLoadMask","");
		});
		jQuery("#${random}_filterSpan_hideZeroCell").bind("click",function(){
			var grid = eval("("+'reportPlan_gridtable_${random}'+")");
			var checked = jQuery(this).attr("checked");
			if("${reportDefine.reverseColumn}"){//反转
				var colModelDatas= [
				{name:'rpItems',index:'rpItems',align:'center',totalExpress:"合计",totalAlign:"center",text : '${subSystemPrefix}项目',width:80,isHide:false,editable:false,dataType:'string'}
				];
						var colArr = reportReverseCols("${random}");
						var rpItems = [];
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
								rpItems[key]={itemType:"0",itemCode:"period"+value,scale:2};
							});
						}
						jQuery("#${random}_report_reportItems").val(JSON.stringify(rpItems));
						var reportPlanGrid = reportSupcanPlanGrid(colModelDatas);
						grid.func("build", JSON.stringify(reportPlanGrid));
						var rpFilterObj = reportFilterLoad("${random}","${typeId}");
						rpFilterObj.defineId = "${defineId}";
						rpFilterObj.reportPlanItemsStr = jQuery("#${random}_reportItemsStr").val();
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
								grid.func("load", JSON.stringify(reportGridData));
							}
						});
			}else{//正常
				if("${reportDefine.displayType}" !== "Report"){
					jQuery.ajax({
						url: 'reportPlanGridList?defineId=${defineId}&columns='+jQuery("#${random}_report_columns").val(),
						data: reportFilterLoad("${random}","${typeId}"),
						type: 'post',
						dataType: 'json',
						async:true,
						error: function(data){
						},
						success: function(data){
							var reportPlanContents = data.reportPlanContents;
							if(checked == "checked"){
								if(reportPlanContents){
									for(var index in reportPlanContents){
										var report = reportPlanContents[index];
										for(var column in report){
											if(report[column] === 0){
												report[column] = null;
											}
										}
									}
								}
							}
							var reportGridData = {};
							reportGridData.Record = reportPlanContents;
							grid.func("load", JSON.stringify(reportGridData));
						}
					});
				}else{
					var deptIds = jQuery("#${random}_filter_deptIds_id").val();
					var gzStubsDeptNumber = jQuery("#${random}_gzStubsDeptNumber").val();
					gzStubsDeptNumber = +gzStubsDeptNumber;
					if(!gzStubsDeptNumber){
						gzStubsDeptNumber = 10;
					}
					if(deptIds){
						var deptIdArr = deptIds.split(",");
						if(deptIdArr.length > gzStubsDeptNumber){
							alertMsg.error("最多只能选择"+gzStubsDeptNumber+"个部门！");
							return;
						}
					}else{
						alertMsg.error("请选择需要显示工资条的部门！");
						return;
					}
					jQuery.ajax({
						url: 'reportPlanGridList?defineId=${defineId}&columns='+jQuery("#${random}_report_columns").val(),
						data: reportFilterLoad("${random}","${typeId}"),
						type: 'post',
						dataType: 'json',
						async:true,
						error: function(data){
							alertMsg.error("系统错误！");
						},
						success: function(data){
							var reportPlanContents = data.reportPlanContents;
							if(checked == "checked"){
								if(reportPlanContents){
									for(var index in reportPlanContents){
										var reportPlanContent = reportPlanContents[index];
										for(var column in reportPlanContent){
											if(reportPlanContent[column] === 0){
												reportPlanContent[column] = null;
											}
										}
									}
								}
							}
							var reportGridData = {};
							reportGridData.Record = reportPlanContents;
							grid.func("setSource", "ds1 \r\n "+JSON.stringify(reportGridData));
							grid.func("calc", "mode=asynch;range=current");
						}
					});
				}
			}
		});
	}); 
	function reportPlanGrid(random,defineId,typeId){
		jQuery.ajax({
	    	url: 'reportPlanColumnInfo',
	        data: {reportPlanItemsStr:jQuery("#${random}_reportItemsStr").val(),
	        	defineId:defineId,typeId:typeId},
	        type: 'post',
	        dataType: 'json',
	        async:false,
	        error: function(data){
	        },
	        success: function(data){   
	        	var reportItems = data.reportItems;
	        	jQuery("#"+random+"_report_reportItems").val(JSON.stringify(reportItems));
	        	var reportFilterStr = jQuery("#"+random+"_reportFilterStr").val();
	        	var orgInit = "";
	        	var branchInit = "";
	        	var deptInit = "";
	        	var deptTypesInit = "";
	        	var personTypeInit = "";
	        	var personTypea12Init = "";
	    		var deptTypeInit = "";
	    		var deptDetailInit = "";
	    		var typeIdInit = "";
	    		var groupIdsInit = "";
	        	if(reportFilterStr){
	        		var reportFilter = JSON.parse(reportFilterStr);
	        		for(var filterCode in reportFilter){
	        			var filterValue = reportFilter[filterCode];
	        			switch(filterCode){
	        			case "orgCode":
	        				orgInit = filterValue;
	        				jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
	        				break;
	        			case "branchCode":
	        				branchInit = filterValue;
	        				jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
	        				break;
	    				case "deptIds":
	    					deptInit = filterValue;
	    					jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
        					break;
	    				case "deptTypes":
	    					deptTypesInit = filterValue;
	    					jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
        					break;
	    				case "empTypes":
							personTypeInit = filterValue;
							jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
							break;
	    				case "empTypesa12":
	    					personTypea12Init = filterValue;
							jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
							break;
						case "deptType":
							deptTypeInit = filterValue;
							break;
						case "deptDetailIds":
							deptDetailInit = filterValue;
							jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
							break;
						case "typeIds":
							typeIdInit = filterValue;
							jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
							break;
						case "groupIds":
							groupIdsInit = filterValue;
							jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
							break;
						case "includeUnCheck":
							jQuery("#"+random+"_filter_" + filterCode).attr("checked","checked");
							break;
	    				default:
	    					jQuery("#"+random+"_filter_"+filterCode).val(filterValue);
	    				break;
	        			}
	        		}
	        	}
	        	var typeStr = jQuery("#"+random+"_typeJson").val();
				var issueType = "";
				if(typeStr){
					var typeJson = JSON.parse(typeStr);
					issueType = typeJson.issueType;
				}
	        	if(issueType != "次"){
	        		var fromPeriodTime = jQuery("#"+random+"_filter_fromPeriodTime").val();
		        	var toPeriodTime = jQuery("#"+random+"_filter_toPeriodTime").val();
		        	if(!fromPeriodTime && !toPeriodTime){
		        		jQuery("#"+random+"_filter_fromPeriodTime").val("${curPeriod}");
		        		jQuery("#"+random+"_filter_toPeriodTime").val("${curPeriod}");
		        	}
	        	}
	        	reportInitTreeSelect(random,orgInit,branchInit,deptInit,deptTypesInit,personTypeInit,deptTypeInit,deptDetailInit,typeIdInit,groupIdsInit,personTypea12Init);
	        	var columns = "";
	        	for(var itemIndex in reportItems){
	        		var itemCode = reportItems[itemIndex].itemCode;
	        		columns += itemCode +",";
	    		}
	        	if(reportItems&&reportItems.length>0){
	        		columns = columns.substring(0,columns.length-1);
	        	}
	        	jQuery("#"+random+"_report_columns").val(columns);
	        	var colModelDatas = initreportColModel(reportItems,random);
	        	initreportGridScript(random,colModelDatas,columns,defineId,typeId);
	        }
	    });
	}
	
	function initreportGridScript(random,colModelDatas,columns,defineId,typeId){
		if("${reportDefine.displayType}" !== "Report"){
			var reportPlanGrid = reportSupcanPlanGrid(colModelDatas);
			reportPlanDefine.main.Build = JSON.stringify(reportPlanGrid);
		}else{
			reportPlanDefine.main.Build = colModelDatas;
		}
		reportGridTableload(random,columns,defineId,typeId);
	}
	
	function reportGridTableload(random,columns,defineId,typeId){
		if("${reportDefine.displayType}" !== "Report"){
			jQuery.ajax({
				url: 'reportPlanGridList?defineId='+defineId+'&columns='+columns,
				data: reportFilterLoad(random,typeId),
				type: 'post',
				dataType: 'json',
				async:true,
				error: function(data){
				},
				success: function(data){
					var reportPlanContents = data.reportPlanContents;
					var reportGridData = {};
					reportGridData.Record = reportPlanContents;
					reportPlanDefine.main.Load = JSON.stringify(reportGridData);
					insertTreeListToDiv(random+"_reportPlan_gridtable_container","reportPlan_gridtable_"+random,"","100%");
				}
			});
		}else{
			var deptIds = jQuery("#"+random+"_filter_deptIds_id").val();
			var gzStubsDeptNumber = jQuery("#"+random+"_gzStubsDeptNumber").val();
			gzStubsDeptNumber = +gzStubsDeptNumber;
			if(!gzStubsDeptNumber){
				gzStubsDeptNumber = 10;
			}
			if(deptIds && deptIds.split(",").length <= gzStubsDeptNumber){
				jQuery.ajax({
					url: 'reportPlanGridList?defineId='+defineId+'&columns='+columns,
					data: reportFilterLoad(random,typeId),
					type: 'post',
					dataType: 'json',
					async:true,
					error: function(data){
					},
					success: function(data){
						var reportPlanContents = data.reportPlanContents;
						var reportGridData = {};
						reportGridData.Record = reportPlanContents;
						reportPlanDefine.main.SetSource = "ds1 \r\n " + JSON.stringify(reportGridData);
						insertReportToDiv(random+"_reportPlan_gridtable_container","reportPlan_gridtable_"+random,"Rebar=None; Border=single;Hue=Lilian;isAnimateFocus=false; PagesTabPercent=0; SeperateBar=none","100%");
					}
				});
			}else{
				insertReportToDiv(random+"_reportPlan_gridtable_container","reportPlan_gridtable_"+random,"Rebar=None; Border=single;Hue=Lilian;isAnimateFocus=false; PagesTabPercent=0; SeperateBar=none","100%");
			}
		}
	}
		
	function initreportColModel(data,random){
		if("${reportDefine.displayType}" == "Report"){
			var colColumns = jQuery("#"+random+"_filter_colColumns").val();
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
				var weight;//400/700 对应 非粗体/粗体
				if(bold === "0"){
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
		if(!"${reportDefine.groupSelectItems}"){
			var periodObj = {name:'period',index:'period',align:'center',text : '期间',width:80,isHide:false,editable:false,dataType:'string'};
			if(!"${reportDefine.displayType}"){
				periodObj.totalExpress = "合计";
				totalAlign = "center";
			}
			colModelDatas.push(periodObj);
		}
		var attachField = "${reportDefine.attachField}";
		var attachFieldName = "${reportDefine.attachFieldName}";
		var groupIds = jQuery("#"+random+"_filter_groupIds_id").val();
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
	
	
	/*过滤条件*/
	function reportFilterLoad(random,typeId){
		var periodTime = jQuery("#"+random+"_filter_periodTime").val();
		var checkYear = jQuery("#"+random+"_filter_checkYear").val();
		var checkNumber = jQuery("#"+random+"_filter_checkNumber").val();
		var fromPeriodTime = jQuery("#"+random+"_filter_fromPeriodTime").val();
		var toPeriodTime = jQuery("#"+random+"_filter_toPeriodTime").val();
		var fromCheckYear = jQuery("#"+random+"_filter_fromCheckYear").val();
		var fromCheckNumber = jQuery("#"+random+"_filter_fromCheckNumber").val();
		var toCheckYear = jQuery("#"+random+"_filter_toCheckYear").val();
		var toCheckNumber = jQuery("#"+random+"_filter_toCheckNumber").val();
		//var orgCode = jQuery("#"+random+"_filter_orgCode").val();
		var orgCode = jQuery("#"+random+"_filter_orgCode_id").val();
		var branchCode = jQuery("#"+random+"_filter_branchCode_id").val();
		var deptTypes = jQuery("#"+random+"_filter_deptTypes_id").val();
		//var deptIds = jQuery("#"+random+"_filter_deptIds").val();
		var deptIds = jQuery("#"+random+"_filter_deptIds_id").val();
		var empTypes = jQuery("#"+random+"_filter_empTypes").val();
		var empTypesa12 = jQuery("#"+random+"_filter_empTypesa12").val();
		var personName = jQuery("#"+random+"_filter_personName").val();
		var personCode = jQuery("#"+random+"_filter_personCode").val();
		var deptType = jQuery("#"+random+"_filterSpan_a7").find("input:radio[name=deptType][checked=checked]").val();
		var deptLevelFrom = jQuery("#"+random+"_filter_deptLevelFrom").val();
		var deptLevelTo = jQuery("#"+random+"_filter_deptLevelTo").val();
		var deptDetailIds = jQuery("#"+random+"_filter_deptDetailIds_id").val();
		var typeIds = jQuery("#"+random+"_filter_typeIds_id").val();
		var personId = jQuery("#"+random+"_filter_personId").val();
		var includeUnCheck = jQuery("#"+random+"_filter_includeUnCheck").attr("checked");
		var groupIds = jQuery("#"+random+"_filter_groupIds_id").val();
		var reportFilterObj = {};
		reportFilterObj.typeId = typeId;
		if(periodTime){
			reportFilterObj.periodTime = periodTime;
		}
		if(checkYear){
			reportFilterObj.checkYear = checkYear;
		}
		if(checkNumber){
			reportFilterObj.checkNumber = checkNumber;
		}
		if(fromPeriodTime){
			reportFilterObj.fromPeriodTime = fromPeriodTime;
		}
		if(toPeriodTime){
			reportFilterObj.toPeriodTime = toPeriodTime;
		}
		if(fromCheckYear){
			reportFilterObj.fromCheckYear = fromCheckYear;
		}
		if(fromCheckNumber){
			reportFilterObj.fromCheckNumber = fromCheckNumber;
		}
		if(toCheckYear){
			reportFilterObj.toCheckYear = toCheckYear;
		}
		if(toCheckNumber){
			reportFilterObj.toCheckNumber = toCheckNumber;
		}
		if(orgCode){
			reportFilterObj.orgCode = orgCode;
		}
		if(branchCode){
			reportFilterObj.branchCode = branchCode;
		}
		if(deptIds){
			reportFilterObj.deptIds = deptIds;
		}
		if(deptTypes){
			reportFilterObj.deptTypes = deptTypes;
		}
		if(empTypes){
			reportFilterObj.empTypes = empTypes;
		}
		if(empTypesa12){
			reportFilterObj.empTypesa12 = empTypesa12;
		}
		if(personName){
			reportFilterObj.personName = personName;
		}
		if(personCode){
			reportFilterObj.personCode = personCode;
		}
		if(deptType){
			reportFilterObj.deptType = deptType;
		}
		if(deptLevelFrom){
			reportFilterObj.deptLevelFrom = deptLevelFrom;
		}
		if(deptLevelTo){
			reportFilterObj.deptLevelTo = deptLevelTo;
		}
		if(deptDetailIds){
			reportFilterObj.deptDetailIds = deptDetailIds;
		}
		if(typeIds){
			reportFilterObj.typeIds = typeIds;
		}
		if(personId){
			reportFilterObj.personId = personId;
		}
		if(includeUnCheck){
			reportFilterObj.includeUnCheck = includeUnCheck;
		}
		if(groupIds){
			reportFilterObj.groupIds = groupIds;
		}
		return reportFilterObj;
	}
	/*反转期间集合*/
	function reportReverseCols(random){
		var modelStatusStr = jQuery("#"+random+"_modelStatusStr").val();
		var modelStatusObj = [];
		if(modelStatusStr){
			modelStatusObj = JSON.parse(modelStatusStr);
		}
		var colArr = new Array();
		var typeStr = jQuery("#"+random+"_typeJson").val();
		var issueType = "";
		if(typeStr){
			var typeJson = JSON.parse(typeStr);
			issueType = typeJson.issueType;
		}
    	if(issueType == "月"&&modelStatusObj){
			var fromperiod = jQuery("#"+random+"_filter_fromPeriodTime").val();
			var toperiod = jQuery("#"+random+"_filter_toPeriodTime").val();
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
	/*反转*/
	function reportReverseGrid(random,defineId,typeId){
		/*查询框填充*/
		var reportFilterStr = jQuery("#"+random+"_reportFilterStr").val();
    	var orgInit = "";
    	var branchInit = "";
    	var deptInit = "";
    	var deptTypesInit = "";
    	var personTypeInit = "";
		var deptTypeInit = "";
		var deptDetailInit = "";
		var typeIdInit = "";
		var groupIdsInit = "";
    	if(reportFilterStr){
    		var reportFilter = JSON.parse(reportFilterStr);
    		for(var filterCode in reportFilter){
    			var filterValue = reportFilter[filterCode];
    			switch(filterCode){
    			case "orgCode":
    				orgInit = filterValue;
    				jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
    				break;
    			case "branchCode":
    				branchInit = filterValue;
    				jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
    				break;
				case "deptIds":
					deptInit = filterValue;
					jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
					break;
				case "deptTypes":
					deptTypesInit = filterValue;
					jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
					break;
				case "empTypes":
					personTypeInit = filterValue;
					jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
					break;
				case "deptType":
					deptTypeInit = filterValue;
					break;
				case "deptDetailIds":
					deptDetailInit = filterValue;
					jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
					break;
				case "typeIds":
					typeIdInit = filterValue;
					jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
					break;
				case "includeUnCheck":
					jQuery("#"+random+"_filter_" + filterCode).attr("checked","checked");
					break;
				case "groupIds":
					groupIdsInit = filterValue;
					jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
					break;
				default:
					jQuery("#"+random+"_filter_"+filterCode).val(filterValue);
				break;
    			}
    		}
    	}
    	var typeStr = jQuery("#"+random+"_typeJson").val();
		var issueType = "";
		if(typeStr){
			var typeJson = JSON.parse(typeStr);
			issueType = typeJson.issueType;
		}
    	if(issueType != "次"){
    		var fromPeriodTime = jQuery("#"+random+"_filter_fromPeriodTime").val();
        	var toPeriodTime = jQuery("#"+random+"_filter_toPeriodTime").val();
        	if(!fromPeriodTime && !toPeriodTime){
        		jQuery("#"+random+"_filter_fromPeriodTime").val("${firstPeriod}");
        		jQuery("#"+random+"_filter_toPeriodTime").val("${curPeriod}");
        	}
    	}
    	reportInitTreeSelect(random,orgInit,branchInit,deptInit,deptTypesInit,personTypeInit,deptTypeInit,deptDetailInit,typeIdInit,groupIdsInit);
		
		var colModelDatas= [
{name:'rpItems',index:'rpItems',align:'center',totalExpress:"合计",totalAlign:"center",text : '${subSystemPrefix}项目',width:80,isHide:false,editable:false,dataType:'string'}
];
		var colArr = reportReverseCols(random);
		var rpItems = [];
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
				rpItems[key]={itemType:"0",itemCode:"period"+value,scale:2};
			});
		}
		jQuery("#"+random+"_report_reportItems").val(JSON.stringify(rpItems));
		var reportPlanGrid = reportSupcanPlanGrid(colModelDatas);
		reportPlanDefine.main.Build = JSON.stringify(reportPlanGrid);
		var rpFilterObj = reportFilterLoad(random,typeId);
		rpFilterObj.defineId = defineId;
		rpFilterObj.reportPlanItemsStr = jQuery("#"+random+"_reportItemsStr").val();
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
				reportPlanDefine.main.Load = JSON.stringify(reportGridData);
				insertTreeListToDiv(random+"_reportPlan_gridtable_container","reportPlan_gridtable_"+random,"","100%");
			}
		});
	}
	/*基础参数*/
	function reportSupcanPlanGrid(colModelDatas){
		var reportPlanGrid = cloneObj(supCanTreeListGrid);
		reportPlanGrid.Properties.Title = "${reportTitle}";
		reportPlanGrid.Cols = colModelDatas;
		reportPlanGrid.Properties.editAble = false;
		reportPlanGrid.Properties.isShowRuler = true;
		reportPlanGrid.Properties.sort = "period,orgCode,deptCode,personCode";
		return reportPlanGrid;
	}
	//树初始化
	function reportInitTreeSelect(random,orgInit,branchInit,deptInit,deptTypesInit,personTypeInit,deptTypeInit,deptDetailInit,typeInit,groupIdsInit,personTypea12Init){
		//单位
		var sql = "select orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon from T_Org where disabled=0 and orgCode<>'XT' ORDER BY orgCode";
		jQuery("#"+random+"_filter_orgCode").treeselect({
			optType : "single",
			dataType : 'sql',
			sql : sql,
			exceptnullparent : true,
			lazy : false,
			minWidth : '120px',
			selectParent : false,
			ifr:true,
			initSelect:orgInit
		});
		//院区
		sql = "select branchCode id,branchName name,'' parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon from t_branch where disabled = '0' and branchCode <> 'XT' order by branchCode";
		jQuery("#"+random+"_filter_branchCode").treeselect({
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
		jQuery("#"+random+"_filter_deptIds").treeselect({
			optType : "multi",
			dataType : 'sql',
			sql : sql,
			exceptnullparent : true,
			lazy : false,
			minWidth : '180px',
			selectParent : false,
			ifr:true,
			initSelect:deptInit
		});
		//部门类别
		sql = "select deptTypeName id,deptTypeName name,'-1' pId  from t_deptType where disabled = '0'";
		jQuery("#"+random+"_filter_deptTypes").treeselect({
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
		jQuery("#"+random+"_filter_empTypes").treeselect({
			dataType : "sql",
			optType : "multi",
			sql : "SELECT t.name id,t.name,(select name from t_personType b where b.id =t.parentType)  parent FROM t_personType t where t.disabled=0  ORDER BY t.code",
			exceptnullparent : false,
			selectParent : false,
			lazy : false,
			ifr:true,
			minWidth : '120px',
			initSelect:personTypeInit
		});
		//人员类别
		jQuery("#"+random+"_filter_empTypesa12").treeselect({
			dataType : "sql",
			optType : "multi",
			sql : "SELECT t.name id,t.name,(select name from t_personType b where b.id =t.parentType)  parent FROM t_personType t where t.disabled=0  ORDER BY t.code",
			exceptnullparent : true,
			selectParent : true,
			lazy : false,
			ifr:true,
			minWidth : '120px',
			chkboxType: {"Y":"", "N":""},
			initSelect : personTypea12Init
		});
		//工资类别
		var reportTypeTreeNodeStr = jQuery("#"+random+"_reportTypeTreeNodeStr").val();
		var typeNodes = [];
		if(reportTypeTreeNodeStr){
			typeNodes = JSON.parse(reportTypeTreeNodeStr);
		}
		jQuery("#"+random+"_filter_typeIds").treeselect({
			optType : "multi",
			dataType : 'nodes',
			zNodes : typeNodes,
			exceptnullparent : false,
			selectParent : false,
			lazy : false,
			minWidth : '120px',
			ifr:true,
			initSelect : typeInit
		});
		//部门类别选择
		jQuery("#"+random+"_filterSpan_a7").find("input:radio[name=deptType]").click(function(){
			var deptType = jQuery(this).val();
			if(deptType=='level'){
				jQuery("#"+random+"_filter_deptLevelFrom").removeAttr("disabled");
				jQuery("#"+random+"_filter_deptLevelTo").removeAttr("disabled");
			}else if(deptType=='end'){
				jQuery("#"+random+"_filter_deptLevelFrom").attr("disabled","true").val("");
				jQuery("#"+random+"_filter_deptLevelTo").attr("disabled","true").val("");
			}else{
				jQuery("#"+random+"_filter_deptLevelFrom").attr("disabled","true").val("");
				jQuery("#"+random+"_filter_deptLevelTo").attr("disabled","true").val("");
			}
			jQuery("#"+random+"_filter_deptDetailIds").val("");
			jQuery("#"+random+"_filter_deptDetailIds_id").val("");
		});
		if(deptTypeInit||deptDetailInit){
			jQuery("#"+random+"_filterSpan_a7").find("input:radio[name=deptType][value="+deptTypeInit+"]").attr("checked","checked");
			var deptLevelFrom = jQuery("#"+random+"_filter_deptLevelFrom").val();
			var deptLevelTo = jQuery("#"+random+"_filter_deptLevelTo").val();
			var sql = "";
			if(!deptDetailInit){
				deptDetailInit = "";
			}
			if(deptTypeInit=='level'){
				jQuery("#"+random+"_filter_deptLevelFrom").removeAttr("disabled");
				jQuery("#"+random+"_filter_deptLevelTo").removeAttr("disabled");
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
				jQuery("#"+random+"_filter_deptDetailIds").treeselect({
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
				jQuery("#"+random+"_filter_deptLevelFrom").attr("disabled","true");
				jQuery("#"+random+"_filter_deptLevelTo").attr("disabled","true");
				sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
				sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
				sql += " ORDER BY orderCol ";
				jQuery("#"+random+"_filter_deptDetailIds").treeselect({
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
				jQuery("#"+random+"_filter_deptLevelFrom").attr("disabled","true");
				jQuery("#"+random+"_filter_deptLevelTo").attr("disabled","true");
				sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol,'0' disCheckAble  from t_department where disabled=0 and deptId <> 'XT'";
				sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol,'1' disCheckAble from T_Org where disabled=0 AND orgCode<>'XT' ";
				sql += " ORDER BY orderCol ";
				jQuery("#"+random+"_filter_deptDetailIds").treeselect({
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
		jQuery("#"+random+"_filter_deptDetailIds").bind("focus",function(){
			var deptType = jQuery("#"+random+"_filterSpan_a7").find("input:radio[name=deptType][checked=checked]").val();
			var deptLevelFrom = jQuery("#"+random+"_filter_deptLevelFrom").val();
			var deptLevelTo = jQuery("#"+random+"_filter_deptLevelTo").val();
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
				jQuery("#"+random+"_filter_deptDetailIds").treeselect({
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
				jQuery("#"+random+"_filter_deptDetailIds").treeselect({
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
				jQuery("#"+random+"_filter_deptDetailIds").treeselect({
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
		jQuery("#"+random+"_filter_personIdName").combogrid({
	        url : 'comboGridSqlList',
	        combogridId:"reportPlanShow_combogrid"+random,
	        queryParams : {
	            sql : sqlTemp,
	            cloumns : 'tp.personCode,tp.name,td.name,td.deptCode'
	        },
	        ifr:true,
	        autoFocus : false,
	        showOn : false, 
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
	        	jQuery("#"+random+"_filter_personId").val(ui.item.PERSONID).blur();
	        	jQuery("#"+random+"_filter_personIdName").val(ui.item.NAME).blur();
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
		jQuery("#"+random+"_filter_groupIds").treeselect({
			optType : "multi",
			dataType : 'nodes',
			zNodes : groupNodes,
			exceptnullparent : true,
			lazy : false,
			ifr:true,
			initSelect : groupIdsInit,
			minWidth : '120px',
			selectParent : true
		});    	
	}
	function reportPlanShowPrintSet(random){
		var grid = eval("("+'reportPlan_gridtable_'+random+")");
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
			var typeName = "";
			if(typeStr){
				var typeJson = JSON.parse(typeStr);
				typeName = typeJson.typeName;
			}		
			var text = "${subSystemPrefix}类别:" + typeName;
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var periodTime = jQuery("#"+random+"_filter_periodTime").val();
		if(periodTime){
			var text = "期间:" + periodTime;
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var checkYear = jQuery("#"+random+"_filter_checkYear").val();
		var checkNumber = jQuery("#"+random+"_filter_checkNumber").val();
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
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var fromPeriodTime = jQuery("#"+random+"_filter_fromPeriodTime").val();
		var toPeriodTime = jQuery("#"+random+"_filter_toPeriodTime").val();
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
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var fromCheckYear = jQuery("#"+random+"_filter_fromCheckYear").val();
		var fromCheckNumber = jQuery("#"+random+"_filter_fromCheckNumber").val();
		var toCheckYear = jQuery("#"+random+"_filter_toCheckYear").val();
		var toCheckNumber = jQuery("#"+random+"_filter_toCheckNumber").val();
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
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var orgCode = jQuery("#"+random+"_filter_orgCode").val();
		if(orgCode){
			var text = "单位:" + orgCode;
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var branchCode = jQuery("#"+random+"_filter_branchCode").val();
		if(branchCode){
			var text = "院区:" + branchCode;
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var deptIds = jQuery("#"+random+"_filter_deptIds").val();
		if(deptIds){
			var text = "部门:" + deptIds;
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var deptTypes = jQuery("#"+random+"_filter_deptTypes").val();
		if(deptTypes){
			var text = "部门类别:" + deptTypes;
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var empTypes = jQuery("#"+random+"_filter_empTypes").val();
		if(empTypes){
			var text = "人员类别:" + empTypes;
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var empTypesa12 = jQuery("#"+random+"_filter_empTypesa12").val();
		if(empTypesa12){
			var text = "人员类别:" + empTypesa12;
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var personName = jQuery("#"+random+"_filter_personName").val();
		if(personName){
			var text = "人员姓名:" + personName;
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var personCode = jQuery("#"+random+"_filter_personCode").val();
		if(personCode){
			var text = "人员编码:" + personCode;
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var deptTypeText = "";
		var deptType = jQuery("#"+random+"_filterSpan_a7").find("input:radio[name=deptType][checked=checked]").val();
		if(deptType == "all"){
			deptTypeText = "全部";
		}else if(deptType == "end"){
			deptTypeText = "末级";
		}else if(deptType == "level"){
			deptTypeText = "级次";
			var deptLevelFrom = jQuery("#"+random+"_filter_deptLevelFrom").val();
			var deptLevelTo = jQuery("#"+random+"_filter_deptLevelTo").val();
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
		var deptDetailIds = jQuery("#"+random+"_filter_deptDetailIds").val();
		if(deptDetailIds){
			var text = "部门明细:" + deptDetailIds;
			reportPlanFormLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var typeIds = jQuery("#"+random+"_filter_typeIds").val();
		if(typeIds){
			var text = "${subSystemPrefix}类别:" + typeIds;
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var personIdName = jQuery("#"+random+"_filter_personIdName").val();
		if(personIdName){
			var text = "人员:" + personIdName;
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var groupIds = jQuery("#"+random+"_filter_groupIds").val();
		if(groupIds){
			var text = "汇总项目:" + groupIds;
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		var includeUnCheck = jQuery("#"+random+"_filter_includeUnCheck").attr("checked");
		if(includeUnCheck){
			var text = "含未审核:是";
			reportPlanShowLayerDom(layerDom,grid,header,layerSn,text);
			layerSn ++;
		}
		printXml = grid.func("dom_export", printDOM); //输出xml
		grid.func("SetProp", "Print \r\n" + printXml); //完成
		grid.func("dom_delete", printDOM); //销毁对象 
	}
	function reportPlanShowLayerDom(layerDom,grid,header,layerSn,text){
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
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a id="${random}_report_printView" class="previewbutton" href="javaScript:" ><span>打印预览</span></a>
				</li>
				<li>
					<a id="${random}_report_print" class="printbutton"  href="javaScript:"><span>打印</span></a>
				</li>
				<li>
					<a id="${random}_report_planChange" class="changebutton"  href="javaScript:"><span>方案</span></a>
				</li>
				<li>
					<a id="${random}_report_refresh" class="addbutton" href="javaScript:" ><span>刷新</span></a>
				</li>
				<li id="${random}_report_customLayoutLi">
					<a id="${random}_report_customLayout" class="savebutton" href="javaScript:" ><span>保存格式</span></a>
				</li>
			</ul>
		</div>
		<div style="margin:5px;border-width:2px;border-style: solid ;border-color: #12127D">
		<div style="margin:2px;border-width:1px;border-style: solid ;border-color: #12127D">
		<div id="${random}_reportPlan_searchBar" style="background-color: #DFF1FE;padding-top:10px;padding-bottom:5px;">
			<div align="center" >
				<label id="reportTitle_${random}" style="font-size:22px;color:#15428B;font-family:宋体">${reportTitle}</label>
				<hr id="reportTitle_line1_${random}" style="margin-top:5px;width:130px;color:rgb(24, 127, 207);"/>
				<hr id="reportTitle_line2_${random}" style="width:130px;color:rgb(24, 127, 207);margin-top:-10px"/>
				<script>
					var titleWidth = jQuery("#reportTitle_${random}").width();
					jQuery("#reportTitle_line1_${random}").width(titleWidth+10);
					jQuery("#reportTitle_line2_${random}").width(titleWidth+10);
				</script>
			</div>
			<span id="${random}_filterSpan_1" style="position:relative;top:-20px;left:15px;height:1px">
				<label style="padding:0;" >
					${subSystemPrefix}类别:
				</label>
					<font style="color:red" id="${random}_filter_type"></font>
			</span>	
			<div style="overflow: hidden;position:relative;top:-5px;">
				<div style="display: none;">
					<input type="hidden" id="${random}_report_printSetting">
					<input type="hidden" id="${random}_report_sortColumns">
					<input type="hidden" id="${random}_filter_printer">
					<input type="hidden" id="${random}_filter_paperNumber">
					<input type="hidden" id="${random}_filter_oriantation">
					<input type="hidden" id="${random}_filter_scale">
					<input type="hidden" id="${random}_gzStubsDeptNumber" value="${gzStubsDeptNumber}">
					<input type="hidden" id="${random}_filter_colColumns">
					<input type="hidden" id="${random}_report_columns">
					<input type="hidden" id="${random}_report_reportItems">
					<input type="hidden" id="${random}_reportTypeTreeNodeStr" value='<s:property value="reportTypeTreeNodeStr" escapeHtml="false"/>'>
					<input type="hidden" id="${random}_modelStatusStr" value='<s:property value="modelStatusStr" escapeHtml="false"/>'>
					<input type="hidden" id="${random}_reportCustomLayout" value='<s:property value="reportCustomLayout" escapeHtml="false"/>'>
					<input type="hidden" id="${random}_reportItemsStr" value='<s:property value="reportPlanItemsStr" escapeHtml="false"/>'>
					<input type="hidden" id="${random}_reportFilterStr" value='<s:property value="reportPlanFilterStr" escapeHtml="false"/>'>
					<input type="hidden" id="${random}_typeJson" value='<s:property value="typeJson" escapeHtml="false"/>'>
				</div>
				<div id="${random}_filterArea_A" style="float:left;margin-left:15px;width: 500px;padding-bottom: 2px;" class="searchContent">
					<span id="${random}_filterSpan_a1" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<span id="${random}_filterSpan_a1_period" style="display:none;width:150px;white-space:nowrap">
							<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
								期间:
							</label>
							<input id="${random}_filter_periodTime" style="width: 80px;" type="text" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}"/>
						</span>
						<span style="display:none;width:150px;white-space:nowrap" id="${random}_filterSpan_a1_year">
								<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
									发放次数:
								</label>
								<input type="text" id="${random}_filter_checkYear" style="width: 35px;" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyy'});}"/>年
								<select id="${random}_filter_checkNumber" style="float:none;margin:0;padding:0">
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
						</span>
					</span>
					<span id="${random}_filterSpan_c1" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<span id="${random}_filterSpan_c1_period" style="display:none;width:150px;white-space:nowrap">
							<label style="float:none;white-space:nowrap" >
								期间:
								<input id="${random}_filter_fromPeriodTime" style="width: 41px;" type="text" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}"/>
								至
								<input id="${random}_filter_toPeriodTime" style="width: 41px;" type="text" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}"/>
							</label>&nbsp;&nbsp;
						</span>
						<span style="display: none;" id="${random}_filterSpan_c1_year">
							<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
								发放次数:
							</label>
							从<input type="text" id="${random}_filter_fromCheckYear" style="width: 35px;" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyy'});}"/>年
								<select id="${random}_filter_fromCheckNumber" style="float:none;margin:0;padding:0">
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
							至&nbsp;<input type="text" id="${random}_filter_toCheckYear" style="width: 35px;" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyy'});}"/>年
								<select id="${random}_filter_toCheckNumber" style="float:none;margin:0;padding:0">
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
						</span>
					</span>
					<span style="display: none;width:150px;padding-top:2px" id="${random}_filterSpan_a2">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
							单位:
						</label>
						<input type="text" id="${random}_filter_orgCode" style="float: none; width: 80px;" /> 
						<input id="${random}_filter_orgCode_id" type="hidden"/>
					</span>
					<s:if test="%{herpType == 'S2' || herpType == 'M2'}">
						<span style="display: none;width:150px;padding-top:2px" id="${random}_filterSpan_a13">
							<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
								院区:
							</label>
							<input type="text" id="${random}_filter_branchCode" style="float: none; width: 80px;" /> 
							<input id="${random}_filter_branchCode_id" type="hidden"/>
						</span>
					</s:if>
					<span id="${random}_filterSpan_a3" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
							部门:
						</label>
						<input  id="${random}_filter_deptIds_id" type="hidden">
						<input  id="${random}_filter_deptIds" type="text" style="width: 80px;">
					</span>
					<span id="${random}_filterSpan_a14" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
							部门类别:
						</label>
						<input  id="${random}_filter_deptTypes_id" type="hidden">
						<input  id="${random}_filter_deptTypes" type="text" style="width: 80px;">
					</span>
					<span id="${random}_filterSpan_a4" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
							人员类别:
						</label>
						<input type="text"  id="${random}_filter_empTypes" style="width: 80px;">
						<input type="hidden"  id="${random}_filter_empTypes_id">
					</span>
					<%-- <span id="${random}_filterSpan_a12" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
							人员类别:
						</label>
						<input type="text"  id="${random}_filter_empTypesa12" style="width: 80px;">
						<input type="hidden"  id="${random}_filter_empTypesa12_id">
					</span> --%>
					<span id="${random}_filterSpan_a5" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;">
							人员姓名:
						</label>
						<input  id="${random}_filter_personName" type="text" style="width: 80px;">
					</span>
					<span id="${random}_filterSpan_a6" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
							人员编码:
						</label>
						<input  id="${random}_filter_personCode" type="text" style="width: 80px;">
					</span>
					<span id="${random}_filterSpan_a7" style="display:none;width:300px;white-space:nowrap;padding-top:2px">
						<label style="display:inline-block;text-align:right;padding:0;" >
							部门:
						</label>
						<input type="radio" name="deptType" value="all"/>全部
						<input type="radio" name="deptType" value="end" />末级
						<input type="radio" name="deptType" value="level"/>级次
						从<select id="${random}_filter_deptLevelFrom" disabled="disabled">
							<option value=''></option>
							<option value='1'>1</option>
							<option value='2'>2</option>
							<option value='3'>3</option>
							<option value='4'>4</option>
							<option value='5'>5</option>
						</select>
						到<select id="${random}_filter_deptLevelTo" disabled="disabled">
							<option value=''></option>
							<option value='1'>1</option>
							<option value='2'>2</option>
							<option value='3'>3</option>
							<option value='4'>4</option>
							<option value='5'>5</option>
						</select>
					</span>
					<span id="${random}_filterSpan_a8" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
					<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
						部门明细:
					</label>
						<input id="${random}_filter_deptDetailIds" type="text" style="width: 80px;">
						<input id="${random}_filter_deptDetailIds_id" type="hidden">
					</span>
					<span id="${random}_filterSpan_a9" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
					<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
						${subSystemPrefix}类别:
					</label>
						<input id="${random}_filter_typeIds" type="text" style="width: 80px;">
						<input id="${random}_filter_typeIds_id" type="hidden">
					</span>
					<span id="${random}_filterSpan_a10" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;">
							人员:
						</label>
						<input id="${random}_filter_personIdName" type="text" style="width: 80px;">
						<input id="${random}_filter_personId" type="hidden">
					</span>
					<span id="${random}_filterSpan_a12" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;">
							汇总项目:
						</label>
						<input id="${random}_filter_groupIds" type="text" style="width: 80px;">
						<input id="${random}_filter_groupIds_id" type="hidden">
					</span>
					<span id="${random}_filterSpan_a11" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;">
							含未审核:
						</label>
						<input id="${random}_filter_includeUnCheck" type="checkbox">
					</span>
				</div>
				<div id="${random}_filterArea_B" style="float:left;width:250px;" >
					<span id="${random}_filterSpan_b1" style="display: none;">
						<label style="float:none;white-space:nowrap" >
							<input type="checkbox" id="${random}_filterSpan_deptNameCheck" />部门小计
						</label>
					</span>
					<span id="${random}_filterSpan_b2" style="display: none;">
						<label style="float:none;white-space:nowrap" >
							<input type="checkbox" id="${random}_filterSpan_personTypeNameCheck" />人员类别小计
						</label>
					</span>
					<span id="${random}_filterSpan_b3" style="display: none;">
						<label style="float:none;white-space:nowrap" >
							<input type="checkbox" id="${random}_filterSpan_hideRpIsNull" />隐藏无${subSystemPrefix}人员
						</label>
					</span>
					<span id="${random}_filterSpan_b4" style="display: none;">
						<label style="float:none;white-space:nowrap" >
							<input type="checkbox" id="${random}_filterSpan_hideInvalidDeci" />隐藏无效小数
						</label>
					</span>
					<span id="${random}_filterSpan_b5" style="display: none;">
						<label style="float:none;white-space:nowrap" >
							<input type="checkbox" id="${random}_filterSpan_hideZeroCell" />零不显示
						</label>
					</span>
				</div>	
			</div>
		</div>
		<div id="${random}_reportPlan_gridtable_div"  style="margin:0px;background-color: #DFF1FE;" buttonBar="width:500;height:300">
			<input type="hidden" id="${random}_reportPlan_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="${random}_reportPlan_gridtable_container" style="margin-left:15px;margin-right:15px;margin-buttom:15px"></div>
		</div>
		</div>
		</div>
	</div>
