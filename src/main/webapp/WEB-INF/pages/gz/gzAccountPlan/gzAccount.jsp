
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var gzAccountPlanDefine = {
		key:"${random}_gzAccountPlan_gridtable",
		main:{
			Build : '',
			Load :''
			//OpenFreeformBar:gzContentFreeformBar
			//OpenFreeformBar:'<freeform><Properties><backGround bgColor="#E5EAFF,#FAFFFF"/></Properties><Fonts><Font faceName="微软雅黑" charSet="134" height="-13"/><Font faceName="微软雅黑" charSet="134" height="14"/><Font faceName="微软雅黑" charSet="134" height="-16" weight="700"/></Fonts><Objects><TableLayout id="toolbar1" x="0" y="0" width="100%"><col width="92"/><col width="0.10"/><col width="74"/><tr height="22"><td><Image id="img1" src="bar2.zip#bar_left.bmp" arrange="center,middle" alpha="255"/></td><td><Image id="img2" src="bar2.zip#bar_center.bmp" arrange="tile" alpha="255"/></td><td><Image id="img3" src="bar2.zip#bar_right.bmp" arrange="center,middle" alpha="255"/></td></tr></TableLayout></Objects></freeform> \r\n tBar'
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
					grid.func("dom_setProp", layerDom + "\r\n #text \r\n 工资类别：${gzType.gzTypeName}"); //设定text
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
				grid.func("dom_delete", printDOM); //销毁对象 
				var customLayout = jQuery("#${random}_gzAccountCustomLayout").val();
				if(customLayout){
					grid.func("setCustom", customLayout);
				}
			}
		}
	}; 
	
    supcanGridMap.put('gzAccountPlan_gridtable_${random}',gzAccountPlanDefine); 
 	jQuery(document).ready(function(){
 		if("${gzAccountDefine.defineId}" == "09"){
 			jQuery("#${random}_filterSpan_1").hide();
 		}
 	    /*隐藏部分查询框*/
 	    var showFilterStr = "${gzAccountDefine.filters}";
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
		var requiredFilterStr = "${gzAccountDefine.requiredFilter}";
		if (requiredFilterStr) {
			var requiredFilters = requiredFilterStr.split(",");
			jQuery.each(requiredFilters, function(key, value) {
				jQuery("#${random}_filterSpan_" + value).find("input:visible").addClass("required");
			});
		}
 	    /*次结或者月结*/
 	    if("${gzType.issueType}" == "1"){
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
 	    
 	   	var gzAccountPlanFullSize = jQuery("#container").innerHeight()-82;
 	   	var searchBarHeight = jQuery("#${random}_gzAccountPlan_searchBar").height();
		//jQuery("#gzAccountPlan_searchBar").height(95);
		jQuery("#${random}_gzAccountPlan_gridtable_div").height(gzAccountPlanFullSize-searchBarHeight);
		jQuery("#${random}_gzAccountPlan_gridtable_container").height(gzAccountPlanFullSize-searchBarHeight-10);
 	    
 	    
 	    /*工资类别名称*/
 	    jQuery("#${random}_filter_gzType").text("${gzType.gzTypeName}");
		var reverseColumn = "${gzAccountDefine.reverseColumn}";//反转
		if(reverseColumn){//反转
			gzAccountReverseGrid("${random}","${gzAccountDefine.defineId}","${gzTypeId}");
		}else{
			gzAccountPlanGrid("${random}","${gzAccountDefine.defineId}","${gzTypeId}");
		}
		/*打印预览*/
		jQuery("#${random}_gzAccount_printView").bind("click",function(){
			var grid = eval("("+'gzAccountPlan_gridtable_${random}'+")");
			grid.func("PrintPreview","");
		});
		/*打印*/
		jQuery("#${random}_gzAccount_print").bind("click",function(){
			var grid = eval("("+'gzAccountPlan_gridtable_${random}'+")");
			grid.func("Print","isOpenSysDialog=true");
		});
		/*方案*/
		jQuery("#${random}_gzAccount_planChange").bind("click",function(){
			var urlString = "gzAccount?openType=pdialog&title=设置${gzAccountDefine.defineName}方案&accountType=${gzAccountDefine.defineId}&menuId=${menuId}&width=${width}&height=${height}";
			var planId = "${planId}";
			urlString += "&planId="+planId+"&gzTypeId=${gzTypeId}&oper=${random}";
			//排序
			var grid = eval("("+'gzAccountPlan_gridtable_${random}'+")");
			var gzAccountCustomLayout = grid.func("getCustom", "");
			var customLayoutDOM = grid.func("dom_new", gzAccountCustomLayout);
			var ColsDom = grid.func("dom_find", customLayoutDOM + "\r\n Col");
			var colsArr = ColsDom.split(",");
			var sortColumns = {};
			for(var index in colsArr){
				var colTemp = colsArr[index];
				var colId = grid.func("DOM_GetProp", colTemp + "\r\n id");
				sortColumns[colId] = index;
			}
			grid.func("dom_delete", customLayoutDOM); //销毁对象 
			jQuery("#${random}_gzAccount_sortColumns").val(JSON.stringify(sortColumns));
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
			jQuery("#${random}_gzAccount_printSetting").val(JSON.stringify(printSettingObj));
			urlString = encodeURI(urlString);
			$.pdialog.open(urlString,'dialog${menuId}','设置${gzAccountDefine.defineName}方案', {ifr:true,hasSupcan:"all",mask:true,resizable:false,maxable:false,width : "${width}",height : "${height}"});
		});
		/*保存格式*/
		jQuery("#${random}_gzAccount_customLayout").bind("click",function(){
			var planId = "${planId}";
			if(!planId || planId === "null"){
				alertMsg.error("请选择方案！");
				return;
			}
			var grid = eval("("+'gzAccountPlan_gridtable_${random}'+")");
			var gzAccountCustomLayout = grid.func("getCustom", "");
			var url = "saveGzAccountPlanCustomLayout";
	    	$.post(url,{planId:planId,gzAccountCustomLayout:gzAccountCustomLayout},function(data){
	    		formCallBack(data);
	    	});
		});
		/*刷新*/
		jQuery("#${random}_gzAccount_refresh").bind("click",function(){
			var checked = jQuery("${random}_filterSpan_hideZeroCell").attr("checked");
			 /*必填项*/
			var requiredInputs = jQuery("#${random}_gzAccountPlan_searchBar").find(".required");
			 if(requiredInputs){
				var inputFlag = false;
				jQuery.each(requiredInputs,function(key,requiredInput){
					var inputValue = requiredInput.value;
					if(!inputValue){
						inputFlag = true;
					}
				});
				if(inputFlag){
					alertMsg.error("请填写必填项！");
					return;
				}
			}
			if("${gzAccountDefine.reverseColumn}"){//反转
				var colModelDatas= [
	{name:'gzItems',index:'gzItems',align:'center',totalExpress:"合计",totalAlign:"center",text : '<s:text name="工资项目" />',width:80,isHide:false,editable:false,dataType:'string'}
	];
				var colArr = gzAccountReverseCols("${random}");
				gzItems = [];
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
				var gzAccountPlanGrid = gzAccountSupcanPlanGrid(colModelDatas);
				eval("("+'gzAccountPlan_gridtable_${random}'+")").func("build", JSON.stringify(gzAccountPlanGrid));
				var gzFilterObj = gzAccountFilterLoad("${random}","${gzTypeId}");
				gzFilterObj.gzAccountItemsStr = jQuery("#${random}_gzAccountItemsStr").val();
				jQuery.ajax({
					url: 'gzAccountReverseGridList?accountType=${gzAccountDefine.defineId}',
					data: gzFilterObj,
					type: 'post',
					dataType: 'json',
					async:true,
					error: function(data){
					},
					success: function(data){
						var gzAccounts = data.gzAccountContents;
						if(checked == "checked"){
							if(gzAccounts){
								for(var index in gzAccounts){
									var gzAccount = gzAccounts[index];
									for(var column in gzAccount){
										if(gzAccount[column] === 0){
											gzAccount[column] = null;
										}
									}
								}
							}
						}
//	 					jQuery("#${random}_gzRowNum").text(gzAccounts.length);
						var gzAccountGridData = {};
						gzAccountGridData.Record = gzAccounts;
						eval("("+'gzAccountPlan_gridtable_${random}'+")").func("load", JSON.stringify(gzAccountGridData));
					}
				});
			}else{//正常
				jQuery.ajax({
					url: 'gzAccountGridList?accountType=${accountType}&columns='+jQuery("#${random}_gzAccount_columns").val(),
					data: gzAccountFilterLoad("${random}","${gzTypeId}"),
					type: 'post',
					dataType: 'json',
					async:true,
					error: function(data){
					},
					success: function(data){
						var gzAccounts = data.gzAccountContents;
						if(checked == "checked"){
							if(gzAccounts){
								for(var index in gzAccounts){
									var gzAccount = gzAccounts[index];
									for(var column in gzAccount){
										if(gzAccount[column] === 0){
											gzAccount[column] = null;
										}
									}
								}
							}
						}
//	 					jQuery("#${random}_gzRowNum").text(gzAccounts.length);
						var gzAccountGridData = {};
						gzAccountGridData.Record = gzAccounts;
						eval("("+'gzAccountPlan_gridtable_${random}'+")").func("load", JSON.stringify(gzAccountGridData));
					}
				});
			}
		});
		/*部门小计*/
		jQuery("#${random}_filterSpan_deptNameCheck").bind("click",function(){
			var ptChecked = jQuery("#${random}_filterSpan_personTypeNameCheck").attr("checked");
			if(ptChecked){
				jQuery(this).removeAttr("checked");
				alertMsg.error("正在进行人员类别小计！");
				return;
			}
			var cols = eval("("+'gzAccountPlan_gridtable_${random}'+")").func("GetCols","");//获取列数
			var gzItemsStr = jQuery("#${random}_gzAccount_gzItems").val();
			var gzItems = JSON.parse(gzItemsStr);
			var colNames = new Array();
			if(cols > 0){
				for(var colIndex = 0;colIndex < cols;colIndex++){
					var colNameTemp = eval("("+'gzAccountPlan_gridtable_${random}'+")").func("GetColProp",colIndex+" \r\n name");//name属性
					colNames.push(colNameTemp);
				}
			}
			var checked = jQuery(this).attr("checked");
			if(checked == "checked"){
				if(jQuery.inArray("deptId", colNames)>-1){
					eval("("+'gzAccountPlan_gridtable_${random}'+")").func("setProp", "sort \r\n deptId");
				}else if(jQuery.inArray("deptCode", colNames)>-1){
					eval("("+'gzAccountPlan_gridtable_${random}'+")").func("setProp", "sort \r\n deptCode");
				}else if(jQuery.inArray("deptName", colNames)>-1){
					eval("("+'gzAccountPlan_gridtable_${random}'+")").func("setProp", "sort \r\n deptName");
				}else{
					alertMsg.error("无可排序的部门列，不可小计！");
					jQuery(this).removeAttr("checked");
					return;
				}
			}
			if(checked == "checked"){
				eval("("+'gzAccountPlan_gridtable_${random}'+")").func("OpenLoadMask","");
				eval("("+'gzAccountPlan_gridtable_${random}'+")").func("SetColProp", "deptName \r\n subtotalExpress \r\n ='小计'");
				for(var itemIndex in gzItems){
					var item = gzItems[itemIndex];
					if(item.itemType=='0'){
						eval("("+'gzAccountPlan_gridtable_${random}'+")").func("SetColProp", item.itemCode+" \r\n subtotalExpress \r\n =@sum");
			 		}
				}
				eval("("+'gzAccountPlan_gridtable_${random}'+")").func("CloseLoadMask","");
			}else{
				eval("("+'gzAccountPlan_gridtable_${random}'+")").func("OpenLoadMask","");
				eval("("+'gzAccountPlan_gridtable_${random}'+")").func("SetColProp", "deptName \r\n subtotalExpress \r\n  ");
				for(var itemIndex in gzItems){
					var item = gzItems[itemIndex];
					if(item.itemType=='0'){
						eval("("+'gzAccountPlan_gridtable_${random}'+")").func("SetColProp", item.itemCode+" \r\n subtotalExpress \r\n ");
			 		}
				}
				eval("("+'gzAccountPlan_gridtable_${random}'+")").func("CloseLoadMask","");
			}
		});
		/*人员类别小计*/
		jQuery("#${random}_filterSpan_personTypeNameCheck").bind("click",function(){
			var ptChecked = jQuery("#${random}_filterSpan_deptNameCheck").attr("checked");
			if(ptChecked){
				jQuery(this).removeAttr("checked");
				alertMsg.error("正在进行部门小计！");
				return;
			}
			var gzItemsStr = jQuery("#${random}_gzAccount_gzItems").val();
			var gzItems = JSON.parse(gzItemsStr);
			var cols = eval("("+'gzAccountPlan_gridtable_${random}'+")").func("GetCols","");//获取列数
			var colNames = new Array();
			if(cols > 0){
				for(var colIndex = 0;colIndex < cols;colIndex++){
					var colNameTemp = eval("("+'gzAccountPlan_gridtable_${random}'+")").func("GetColProp",colIndex+" \r\n name");//name属性
					colNames.push(colNameTemp);
				}
			}
			var checked = jQuery(this).attr("checked");
			if(checked == "checked"){
				if(jQuery.inArray("empType", colNames)>-1){
					eval("("+'gzAccountPlan_gridtable_${random}'+")").func("setProp", "sort \r\n empType");
				}else{
					alertMsg.error("无可排序的人员类别列，不可小计！");
					jQuery(this).removeAttr("checked");
					return;
				}
			}
			if(checked == "checked"){
				eval("("+'gzAccountPlan_gridtable_${random}'+")").func("OpenLoadMask","");
				eval("("+'gzAccountPlan_gridtable_${random}'+")").func("SetColProp", "empType \r\n subtotalExpress \r\n ='小计'");
				for(var itemIndex in gzItems){
					var item = gzItems[itemIndex];
					if(item.itemType=='0'){
						eval("("+'gzAccountPlan_gridtable_${random}'+")").func("SetColProp", item.itemCode+" \r\n subtotalExpress \r\n =@sum");
			 		}
				}
				eval("("+'gzAccountPlan_gridtable_${random}'+")").func("CloseLoadMask","");
			}else{
				eval("("+'gzAccountPlan_gridtable_${random}'+")").func("OpenLoadMask","");
				eval("("+'gzAccountPlan_gridtable_${random}'+")").func("SetColProp", "empType \r\n subtotalExpress \r\n  ");
				for(var itemIndex in gzItems){
					var item = gzItems[itemIndex];
					if(item.itemType=='0'){
						eval("("+'gzAccountPlan_gridtable_${random}'+")").func("SetColProp", item.itemCode+" \r\n subtotalExpress \r\n ");
			 		}
				}
				eval("("+'gzAccountPlan_gridtable_${random}'+")").func("CloseLoadMask","");
			}
		});
		/*隐藏无工资人员*/
		jQuery("#${random}_filterSpan_hideGzIsNull").bind("click",function(){
			var gzItemsStr = jQuery("#${random}_gzAccount_gzItems").val();
			var gzItems = JSON.parse(gzItemsStr);
			var filterStr = '1=1';
			eval("("+'gzAccountPlan_gridtable_${random}'+")").func("OpenLoadMask","");
			var checked = jQuery(this).attr("checked");
			if(checked == "checked"){
				var filterStrTemp = "";
				for(var itemIndex in gzItems){
					var item = gzItems[itemIndex];
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
			eval("("+'gzAccountPlan_gridtable_${random}'+")").func("Filter", filterStr);
//	 		var rowNum = gzAccountPlan_gridtable.func("getRows", "");
//	 		jQuery("#${random}_gzRowNum").text(rowNum);
			eval("("+'gzAccountPlan_gridtable_${random}'+")").func("CloseLoadMask","");
		});
		/*隐藏无效小数*/
		jQuery("#${random}_filterSpan_hideInvalidDeci").bind("click",function(){
			var gzItemsStr = jQuery("#${random}_gzAccount_gzItems").val();
			var gzItems = JSON.parse(gzItemsStr);
			eval("("+'gzAccountPlan_gridtable_${random}'+")").func("OpenLoadMask","");
			var checked = jQuery(this).attr("checked");
			if(checked == "checked"){
				for(var itemIndex in gzItems){
					var item = gzItems[itemIndex];
					
					if(item.itemType=='0'){
						eval("("+'gzAccountPlan_gridtable_${random}'+")").func("SetColProp", item.itemCode+" \r\n decimal \r\n -1");
					}
				}
			}else{
				for(var itemIndex in gzItems){
					var item = gzItems[itemIndex];
					if(item.itemType=='0'){
						eval("("+'gzAccountPlan_gridtable_${random}'+")").func("SetColProp", item.itemCode+" \r\n decimal \r\n "+item.scale);
					}
				}
			}
			eval("("+'gzAccountPlan_gridtable_${random}'+")").func("CloseLoadMask","");
		});
		jQuery("#${random}_filterSpan_hideZeroCell").bind("click",function(){
			var checked = jQuery(this).attr("checked");
			 /*必填项*/
			var requiredInputs = jQuery("#${random}_gzAccountPlan_searchBar").find(".required");
			 if(requiredInputs){
				var inputFlag = false;
				jQuery.each(requiredInputs,function(key,requiredInput){
					var inputValue = requiredInput.value;
					if(!inputValue){
						inputFlag = true;
					}
				});
				if(inputFlag){
					alertMsg.error("请填写必填项！");
					return;
				}
			}
			if("${gzAccountDefine.reverseColumn}"){//反转
				var colModelDatas= [
	{name:'gzItems',index:'gzItems',align:'center',totalExpress:"合计",totalAlign:"center",text : '<s:text name="工资项目" />',width:80,isHide:false,editable:false,dataType:'string'}
	];
				var colArr = gzAccountReverseCols("${random}");
				gzItems = [];
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
				var gzAccountPlanGrid = gzAccountSupcanPlanGrid(colModelDatas);
				eval("("+'gzAccountPlan_gridtable_${random}'+")").func("build", JSON.stringify(gzAccountPlanGrid));
				var gzFilterObj = gzAccountFilterLoad("${random}","${gzTypeId}");
				gzFilterObj.gzAccountItemsStr = jQuery("#${random}_gzAccountItemsStr").val();
				jQuery.ajax({
					url: 'gzAccountReverseGridList?accountType=${gzAccountDefine.defineId}',
					data: gzFilterObj,
					type: 'post',
					dataType: 'json',
					async:true,
					error: function(data){
					},
					success: function(data){
						var gzAccounts = data.gzAccountContents;
						if(checked == "checked"){
							if(gzAccounts){
								for(var index in gzAccounts){
									var gzAccount = gzAccounts[index];
									for(var column in gzAccount){
										if(gzAccount[column] === 0){
											gzAccount[column] = null;
										}
									}
								}
							}
						}
						var gzAccountGridData = {};
						gzAccountGridData.Record = gzAccounts;
						eval("("+'gzAccountPlan_gridtable_${random}'+")").func("load", JSON.stringify(gzAccountGridData));
					}
				});
			}else{//正常
				jQuery.ajax({
					url: 'gzAccountGridList?accountType=${accountType}&columns='+jQuery("#${random}_gzAccount_columns").val(),
					data: gzAccountFilterLoad("${random}","${gzTypeId}"),
					type: 'post',
					dataType: 'json',
					async:true,
					error: function(data){
					},
					success: function(data){
						var gzAccounts = data.gzAccountContents;
						if(checked == "checked"){
							if(gzAccounts){
								for(var index in gzAccounts){
									var gzAccount = gzAccounts[index];
									for(var column in gzAccount){
										if(gzAccount[column] === 0){
											gzAccount[column] = null;
										}
									}
								}
							}
						}
//	 					jQuery("#${random}_gzRowNum").text(gzAccounts.length);
						var gzAccountGridData = {};
						gzAccountGridData.Record = gzAccounts;
						eval("("+'gzAccountPlan_gridtable_${random}'+")").func("load", JSON.stringify(gzAccountGridData));
					}
				});
			}
		});
	}); 
	function gzAccountPlanGrid(random,accountType,gzTypeId){
		jQuery.ajax({
	    	url: 'gzAccountColumnInfo',
	        data: {gzAccountItemsStr:jQuery("#${random}_gzAccountItemsStr").val(),
	        	accountType:accountType,gzTypeId:gzTypeId},
	        type: 'post',
	        dataType: 'json',
	        async:false,
	        error: function(data){
	        },
	        success: function(data){   
	        	var gzItems = data.gzItems;
	        	jQuery("#"+random+"_gzAccount_gzItems").val(JSON.stringify(gzItems));
	        	var gzAccountFilterStr = jQuery("#"+random+"_gzAccountFilterStr").val();
	        	var orgInit = "";
	        	var deptInit = "";
	        	var personTypeInit = "";
	    		var deptTypeInit = "";
	    		var deptDetailInit = "";
	    		var gzTypeIdInit = "";
	        	if(gzAccountFilterStr){
	        		var gzAccountFilter = JSON.parse(gzAccountFilterStr);
	        		for(var filterCode in gzAccountFilter){
	        			var filterValue = gzAccountFilter[filterCode];
	        			switch(filterCode){
	        			case "orgCode":
	        				orgInit = filterValue;
	        				jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
	        				break;
	    				case "deptIds":
	    					deptInit = filterValue;
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
						case "gzTypeIds":
							gzTypeIdInit = filterValue;
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
	        	if("${gzType.issueType}" != "1"){
	        		var fromPeriodTime = jQuery("#"+random+"_filter_fromPeriodTime").val();
		        	var toPeriodTime = jQuery("#"+random+"_filter_toPeriodTime").val();
		        	if(!fromPeriodTime && !toPeriodTime){
		        		jQuery("#"+random+"_filter_fromPeriodTime").val("${curPeriod}");
		        		jQuery("#"+random+"_filter_toPeriodTime").val("${curPeriod}");
		        	}
	        	}
	        	gzAccountInitTreeSelect(random,orgInit,deptInit,personTypeInit,deptTypeInit,deptDetailInit,gzTypeIdInit);
	        	var columns ="";
	        	var gzColMap = new Map();
	        	for(var itemIndex in gzItems){
	        		var itemCode = gzItems[itemIndex].itemCode;
	        		gzColMap.put(itemCode,gzItems[itemIndex]);
	        		columns += itemCode +",";
	    		}
	        	if(gzItems&&gzItems.length>0){
	        		columns = columns.substring(0,columns.length-1);
	        	}
	        	jQuery("#"+random+"_gzAccount_columns").val(columns);
	        	var colModelDatas = initgzAccountColModel(gzItems);
	        	initGzAccountGridScript(random,colModelDatas,columns,accountType,gzTypeId);
	        }
	    });
	}
	
	function initGzAccountGridScript(random,colModelDatas,columns,accountType,gzTypeId){
		var gzAccountPlanGrid = gzAccountSupcanPlanGrid(colModelDatas);
		gzAccountPlanDefine.main.Build = JSON.stringify(gzAccountPlanGrid);
		gzAccountGridTableload(random,columns,accountType,gzTypeId);
	}
	
	function gzAccountGridTableload(random,columns,accountType,gzTypeId){
		jQuery.ajax({
			url: 'gzAccountGridList?accountType='+accountType+'&columns='+columns,
			data: gzAccountFilterLoad(random,gzTypeId),
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
				gzAccountPlanDefine.main.Load = JSON.stringify(gzAccountGridData);
				insertTreeListToDiv(random+"_gzAccountPlan_gridtable_container","gzAccountPlan_gridtable_"+random,"","100%");
			}
		});
	}
		
	function initgzAccountColModel(data){
		var colModelDatas = [
];
		if("${gzAccountDefine.defineId}" == "05" || "${gzAccountDefine.defineId}" == "06" ){
			colModelDatas.push({name:'period',index:'period',align:'center',text : '期间',width:80,isHide:false,editable:false,dataType:'string'});
			colModelDatas.push({name:'issueNumber',index:'issueNumber',align:'right',text : '发放次数',width:60,isHide:false,editable:false,dataType:'int'});
		}else{
			colModelDatas.push({name:'period',index:'period',align:'center',totalExpress:"合计",totalAlign:"center",text : '期间',width:80,isHide:false,editable:false,dataType:'string'});
			colModelDatas.push({name:'issueNumber',index:'issueNumber',align:'right',text : '发放次数',width:60,isHide:false,editable:false,dataType:'int'});
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
    		//alert(JSON.stringify(colModelDatas));
    		return colModelDatas;
    }
	
	
	/*过滤条件*/
	function gzAccountFilterLoad(random,gzTypeId){
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
		//var deptIds = jQuery("#"+random+"_filter_deptIds").val();
		var deptIds = jQuery("#"+random+"_filter_deptIds_id").val();
		var empTypes = jQuery("#"+random+"_filter_empTypes").val();
		var personName = jQuery("#"+random+"_filter_personName").val();
		var personCode = jQuery("#"+random+"_filter_personCode").val();
		var deptType = jQuery("#"+random+"_filterSpan_a7").find("input:radio[name=deptType][checked=checked]").val();
		var deptLevelFrom = jQuery("#"+random+"_filter_deptLevelFrom").val();
		var deptLevelTo = jQuery("#"+random+"_filter_deptLevelTo").val();
		var deptDetailIds = jQuery("#"+random+"_filter_deptDetailIds_id").val();
		var gzTypeIds = jQuery("#"+random+"_filter_gzTypeIds_id").val();
		var personId = jQuery("#"+random+"_filter_personId").val();
		var includeUnCheck = jQuery("#"+random+"_filter_includeUnCheck").attr("checked");
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
		if(fromPeriodTime){
			gzFilterObj.fromPeriodTime = fromPeriodTime;
		}
		if(toPeriodTime){
			gzFilterObj.toPeriodTime = toPeriodTime;
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
	/*期间集合*/
	function gzAccountReverseCols(random){
		var modelStatusStr = jQuery("#"+random+"_modelStatusStr").val();
		var modelStatusObj = [];
		if(modelStatusStr){
			modelStatusObj = JSON.parse(modelStatusStr);
		}
		var colArr = new Array();
		if("${gzType.issueType}" == "0"&&modelStatusObj){
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
		if("${gzType.issueType}" == "1"&&modelStatusObj){
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
// 		if(fromperiod&&toperiod){
// 			var fromyear = +fromperiod.substr(0,4);
// 			var frommonth = +fromperiod.substr(4,2);
// 			var toyear = +toperiod.substr(0,4);
// 			var tomonth = +toperiod.substr(4,2);
// 			if(fromyear == toyear){
// 				for(var monthi= frommonth;monthi <= tomonth;monthi++){
// 					if(monthi<10){
// 						colArr.push(fromyear+"0"+monthi);
// 					}else{
// 						colArr.push(fromyear+""+monthi);
// 					}
// 				}
// 			}else if(toyear>fromyear){
// 				for(var yeari=fromyear;yeari<=toyear;yeari++){
// 					if(yeari==fromyear){
// 						for(var monthi= frommonth;monthi <= 12;monthi++){
// 							if(monthi<10){
// 								colArr.push(yeari+"0"+monthi);
// 							}else{
// 								colArr.push(yeari+""+monthi);
// 							}
// 						}
// 					}else if(yeari==toyear){
// 						for(var monthi= 1;monthi <= tomonth;monthi++){
// 							if(monthi<10){
// 								colArr.push(yeari+"0"+monthi);
// 							}else{
// 								colArr.push(yeari+""+monthi);
// 							}
// 						}
// 					}else{
// 						for(var monthi= 1;monthi <= 12;monthi++){
// 							if(monthi<10){
// 								colArr.push(yeari+"0"+monthi);
// 							}else{
// 								colArr.push(yeari+""+monthi);
// 							}
// 						}
// 					}
// 				}
// 			}
// 		}
		return colArr;
	}
	/*反转*/
	function gzAccountReverseGrid(random,accountType,gzTypeId){
		/*查询框填充*/
		var gzAccountFilterStr = jQuery("#"+random+"_gzAccountFilterStr").val();
    	var orgInit = "";
    	var deptInit = "";
    	var personTypeInit = "";
		var deptTypeInit = "";
		var deptDetailInit = "";
		var gzTypeIdInit = "";
    	if(gzAccountFilterStr){
    		var gzAccountFilter = JSON.parse(gzAccountFilterStr);
    		for(var filterCode in gzAccountFilter){
    			var filterValue = gzAccountFilter[filterCode];
    			switch(filterCode){
    			case "orgCode":
    				orgInit = filterValue;
    				jQuery("#"+random+"_filter_"+filterCode+"_id").val(filterValue);
    				break;
				case "deptIds":
					deptInit = filterValue;
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
				case "gzTypeIds":
					gzTypeIdInit = filterValue;
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
    	if("${gzType.issueType}" != "1"){
    		var fromPeriodTime = jQuery("#"+random+"_filter_fromPeriodTime").val();
        	var toPeriodTime = jQuery("#"+random+"_filter_toPeriodTime").val();
        	if(!fromPeriodTime && !toPeriodTime){
        		jQuery("#"+random+"_filter_fromPeriodTime").val("${firstPeriod}");
        		jQuery("#"+random+"_filter_toPeriodTime").val("${curPeriod}");
        	}
    	}
    	gzAccountInitTreeSelect(random,orgInit,deptInit,personTypeInit,deptTypeInit,deptDetailInit,gzTypeIdInit);
		
		var colModelDatas= [
{name:'gzItems',index:'gzItems',align:'center',totalExpress:"合计",totalAlign:"center",text : '<s:text name="工资项目" />',width:80,isHide:false,editable:false,dataType:'string'}
];
		var colArr = gzAccountReverseCols(random);
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
		jQuery("#"+random+"_gzAccount_gzItems").val(JSON.stringify(gzItems));
		var gzAccountPlanGrid = gzAccountSupcanPlanGrid(colModelDatas);
		gzAccountPlanDefine.main.Build = JSON.stringify(gzAccountPlanGrid);
		var gzFilterObj = gzAccountFilterLoad(random,gzTypeId);
		gzFilterObj.accountType = accountType;
		gzFilterObj.gzAccountItemsStr = jQuery("#"+random+"_gzAccountItemsStr").val();
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
				gzAccountPlanDefine.main.Load = JSON.stringify(gzAccountGridData);
				insertTreeListToDiv(random+"_gzAccountPlan_gridtable_container","gzAccountPlan_gridtable_"+random,"","100%");
			}
		});
	}
	/*基础参数*/
	function gzAccountSupcanPlanGrid(colModelDatas){
		var gzAccountPlanGrid = cloneObj(supCanTreeListGrid);
		gzAccountPlanGrid.Properties.Title = "${gzAccountDefine.defineName}";
		gzAccountPlanGrid.Cols = colModelDatas;
		gzAccountPlanGrid.Properties.editAble = false;
		gzAccountPlanGrid.Properties.isShowRuler = true;
		gzAccountPlanGrid.Properties.sort = "period,orgCode,deptCode,personCode";
		return gzAccountPlanGrid;
	}
	//树初始化
	function gzAccountInitTreeSelect(random,orgInit,deptInit,personTypeInit,deptTypeInit,deptDetailInit,gzTypeInit){
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
		//工资类别
		jQuery("#"+random+"_filter_gzTypeIds").treeselect({
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
	}
</script>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a id="${random}_gzAccount_printView" class="previewbutton" href="javaScript:" ><span>打印预览</span></a>
				</li>
				<li>
					<a id="${random}_gzAccount_print" class="printbutton"  href="javaScript:"><span>打印</span></a>
				</li>
				<li>
					<a id="${random}_gzAccount_planChange" class="changebutton"  href="javaScript:"><span>方案</span></a>
				</li>
				<li>
					<a id="${random}_gzAccount_refresh" class="addbutton" href="javaScript:" ><span>刷新</span></a>
				</li>
				<li>
					<a id="${random}_gzAccount_customLayout" class="savebutton" href="javaScript:" ><span>保存格式</span></a>
				</li>
			</ul>
		</div>
		<div style="margin:5px;border-width:2px;border-style: solid ;border-color: #12127D">
		<div style="margin:2px;border-width:1px;border-style: solid ;border-color: #12127D">
		<div id="${random}_gzAccountPlan_searchBar" style="background-color: #DFF1FE;padding-top:10px;padding-bottom:5px;">
			<div align="center" >
				<label id="gzAccountTitle_${random}" style="font-size:22px;color:#15428B;font-family:宋体">${gzAccountDefine.defineName }</label>
				<hr id="gzAccountTitle_line1_${random}" style="margin-top:5px;width:130px;color:rgb(24, 127, 207);"/>
				<hr id="gzAccountTitle_line2_${random}" style="width:130px;color:rgb(24, 127, 207);margin-top:-10px"/>
				<script>
					var titleWidth = jQuery("#gzAccountTitle_${random}").width();
					jQuery("#gzAccountTitle_line1_${random}").width(titleWidth+10);
					jQuery("#gzAccountTitle_line2_${random}").width(titleWidth+10);
				</script>
			</div>
			<span id="${random}_filterSpan_1" style="position:relative;top:-20px;left:15px;height:1px">
				<label style="padding:0;" >
					工资类别:
				</label>
					<font style="color:red" id="${random}_filter_gzType"></font>
			</span>	
			<div style="overflow: hidden;position:relative;top:-5px;">
				<div style="display: none;">
					<input type="hidden" id="${random}_gzAccount_printSetting">
					<input type="hidden" id="${random}_gzAccount_sortColumns">
					<input type="hidden" id="${random}_filter_printer">
					<input type="hidden" id="${random}_filter_paperNumber">
					<input type="hidden" id="${random}_filter_oriantation">
					<input type="hidden" id="${random}_filter_scale">
					<input type="hidden" id="${random}_gzAccount_columns">
					<input type="hidden" id="${random}_gzAccount_gzItems">
					<input type="hidden" id="${random}_modelStatusStr" value='<s:property value="modelStatusStr" escapeHtml="false"/>'>
					<input type="hidden" id="${random}_gzAccountCustomLayout" value='<s:property value="gzAccountCustomLayout" escapeHtml="false"/>'>
					<input type="hidden" id="${random}_gzAccountItemsStr" value='<s:property value="gzAccountItemsStr" escapeHtml="false"/>'>
					<input type="hidden" id="${random}_gzAccountFilterStr" value='<s:property value="gzAccountFilterStr" escapeHtml="false"/>'>
				</div>
				<div id="${random}_filterArea_A" style="float:left;margin-left:15px;width: 500px;padding-bottom: 2px;" class="searchContent">
					<span id="${random}_filterSpan_2" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
							人数:
							</label>
						<font id='${random}_gzRowNum' style="color:red"></font>
					</span>
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
								<%-- <select name="accountBean.toCheckYear" id="${random}_filter_checkYear" style="float:none">
										<option value='2014'>2014</option>
										<option value='2015'>2015</option>
								</select> --%>
								<input type="text" id="${random}_filter_checkYear" style="width: 35px;" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyy'});}"/>年
								<select id="${random}_filter_checkNumber" name="accountBean.toCheckNumber" style="float:none;margin:0;padding:0">
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
								<%-- <input type="text" id="${random}_filter_checkYear" style="width: 50px;" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyy'});}"/>
								<s:text name='年'/>
								<input type="text" id="${random}_filter_checkNumber" style="width: 50px;"/>
								<s:text name='次'/> --%>
						</span>
					</span>
					<span id="${random}_filterSpan_c1" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<span id="${random}_filterSpan_c1_period" style="display:none;width:150px;white-space:nowrap">
							<label style="float:none;white-space:nowrap" >
								期间:
								<input id="${random}_filter_fromPeriodTime" style="width: 41px;" type="text" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}"/>
								至:
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
								<label style="width:51px;display:inline-block;text-align:right;padding:0;" >单位:
								</label>
								<input type="text" id="${random}_filter_orgCode"
									style="float: none; width: 80px;" /> 
									<input id="${random}_filter_orgCode_id" type="hidden"/>
					</span>
					<span id="${random}_filterSpan_a3" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
							部门:
						</label>
						<input  id="${random}_filter_deptIds_id" type="hidden">
						<input  id="${random}_filter_deptIds" type="text" style="width: 80px;">
					</span>
					<span id="${random}_filterSpan_a4" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
							人员类别:
						</label>
						<input type="text"  id="${random}_filter_empTypes" style="width: 80px;">
						<input type="hidden"  id="${random}_filter_empTypes_id">
					</span>
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
						工资类别:
					</label>
						<input id="${random}_filter_gzTypeIds" type="text" style="width: 80px;">
						<input id="${random}_filter_gzTypeIds_id" type="hidden">
					</span>
					<span id="${random}_filterSpan_a10" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;">
							人员:
						</label>
						<input id="${random}_filter_personIdName" type="text" style="width: 80px;">
						<input id="${random}_filter_personId" type="hidden">
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
							<input type="checkbox" id="${random}_filterSpan_hideGzIsNull" />隐藏无工资人员
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
		<div id="${random}_gzAccountPlan_gridtable_div"  style="margin:0px;background-color: #DFF1FE;" buttonBar="width:500;height:300">
			<input type="hidden" id="${random}_gzAccountPlan_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="${random}_gzAccountPlan_gridtable_container" style="margin-left:15px;margin-right:15px;margin-buttom:15px"></div>
		</div>
		</div>
		</div>
	</div>
