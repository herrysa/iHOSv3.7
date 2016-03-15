
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
		},callback:{
			onComplete:function(id){
				var grid = eval("("+id+")");
				grid.func("Swkrntpomzqa", "1, 2"); //所有单元格内容不得修改
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
				printXml = grid.func("dom_export", printDOM); //输出xml
				grid.func("SetProp", "Print \r\n" + printXml); //完成
				grid.func("dom_delete", printDOM); //销毁对象 
				//setTimeout("gzAccountPlanRulerFontChange('gzAccountPlan_gridtable_${random}')", 2000);
				//grid.func("EnableMenu","print,copy,addSort,selectCol,export,separate,showRuler,enter \r\n false");//打印预览,复制,加入多重排序,自定义显示隐藏列,转换输出,分屏冻结,显示/隐藏左标尺,回车键行为	
			}
		}
	}; 
	
    supcanGridMap.put('gzAccountPlan_gridtable_${random}',gzAccountPlanDefine); 
 	jQuery(document).ready(function() {
 	    
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
 	    	jQuery("#${random}_filterSpan_c1_period").hide();
 	    	jQuery("#${random}_filterSpan_c1_year").show().parent().width(300);;
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
 	    /*打印预览*/
 	    jQuery("#${random}_gzAccount_printView").bind("click",function(){
 	    	var grid = eval("("+'gzAccountPlan_gridtable_${random}'+")");
 			grid.func("CallFunc","18");//调用工具条上的功能
 	    });
 	    /*打印*/
 	    jQuery("#${random}_gzAccount_print").bind("click",function(){
 	    	var grid = eval("("+'gzAccountPlan_gridtable_${random}'+")");
 			grid.func("Print", "isOpenSysDialog=true; WorkSheet=all");
 	    });
		/*方案*/
		jQuery("#${random}_gzAccount_planChange").bind("click",function(){
			var urlString = "gzAccount?openType=pdialog&title=设置${gzAccountDefine.defineName}方案&accountType=${gzAccountDefine.defineId}&menuId=${menuId}&width=${width}&height=${height}";
			var planId = "${planId}";
			urlString += "&planId="+planId+"&gzTypeId=${gzTypeId}&oper=${random}";
			//打印设置
			var grid = eval("("+'gzAccountPlan_gridtable_${random}'+")");
			var printSettingObj = {};
			var printXml = grid.func("GetProp", "Print");
			var printDOM = grid.func("dom_new", printXml);
			/*打印纸及相关配置*/
			var paper = grid.func("dom_find", printDOM + "\r\n paper");
			if(paper){
				printSettingObj.paperNumber = grid.func("DOM_GetProp", paper + "\r\n paperNumber");
				printSettingObj.oriantation = grid.func("DOM_GetProp", paper + "\r\n oriantation");
				printSettingObj.scale = grid.func("DOM_GetProp", paper + "\r\n scale");
			}
			grid.func("dom_delete", printDOM); //销毁对象 
			jQuery("#${random}_gzAccount_printSetting").val(JSON.stringify(printSettingObj));
			urlString = encodeURI(urlString);
			$.pdialog.open(urlString,'dialog${menuId}','设置${gzAccountDefine.defineName}方案', {ifr:true,hasSupcan:"all",mask:true,resizable:false,maxable:false,width : "${width}",height : "${height}"});
		});
		/*刷新*/
		jQuery("#${random}_gzAccount_refresh").bind("click",function(){
			var checked = jQuery("${random}_filterSpan_hideZeroCell").attr("checked");
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
			jQuery.ajax({
				url: 'gzAccountGridList?accountType=${accountType}&columns='+jQuery("#${random}_gzAccount_columns").val(),
				data: gzAccountFilterLoad("${random}","${gzTypeId}"),
				type: 'post',
				dataType: 'json',
				async:true,
				error: function(data){
					alertMsg.error("系统错误！");
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
					eval("("+'gzAccountPlan_gridtable_${random}'+")").func("setSource", "ds1 \r\n "+JSON.stringify(gzAccountGridData));
					eval("("+'gzAccountPlan_gridtable_${random}'+")").func("calc", "mode=asynch;range=current");
				}
			});
			 /*必填项*/
		});
		/*部门小计*/
		jQuery("#${random}_filterSpan_deptNameCheck").bind("click",function(){
		});
		/*人员类别小计*/
		jQuery("#${random}_filterSpan_personTypeNameCheck").bind("click",function(){
		});
		/*隐藏无工资人员*/
		jQuery("#${random}_filterSpan_hideGzIsNull").bind("click",function(){
		});
		/*隐藏无效小数*/
		jQuery("#${random}_filterSpan_hideInvalidDeci").bind("click",function(){
			/* eval("("+'gzAccountPlan_gridtable_${random}'+")").func("GrayWindow","1");
			var checked = jQuery(this).attr("checked");
			if(checked == "checked"){
				for(var itemIndex in gzItems){
					var item = gzItems[itemIndex];
					if(item.itemType=='0'){
						var rows = eval("("+'gzAccountPlan_gridtable_${random}'+")").func("GetRows","");
						var cols = eval("("+'gzAccountPlan_gridtable_${random}'+")").func("GetCols","");
						for(var rowIndex = 0;rowIndex < rows;rowIndex++){
							for(var colIndex = 0;colIndex < cols;colIndex++){
								var datatype = eval("("+'gzAccountPlan_gridtable_${random}'+")").func("GetCellProp",rowIndex+" \r\n "+colIndex+" \r\n datatype");
								if(datatype == "double"){
									eval("("+'gzAccountPlan_gridtable_${random}'+")").func("SetCellProp",rowIndex+" \r\n "+colIndex+" \r\n decimal \\ -1");
								}
							}
						}
						//eval("("+'gzAccountPlan_gridtable_${random}'+")").func("SetColProp", item.itemCode+" \r\n decimal \r\n -1");
					}
				}
			}else{
				for(var itemIndex in gzItems){
					var item = gzItems[itemIndex];
					if(item.itemType=='0'){
						var rows = eval("("+'gzAccountPlan_gridtable_${random}'+")").func("GetRows","");
						var cols = eval("("+'gzAccountPlan_gridtable_${random}'+")").func("GetCols","");
						for(var rowIndex = 0;rowIndex < rows;rowIndex++){
							for(var colIndex = 0;colIndex < cols;colIndex++){
								var datatype = eval("("+'gzAccountPlan_gridtable_${random}'+")").func("GetCellProp",rowIndex+" \r\n "+colIndex+" \r\n datatype");
								if(datatype == "double"){
									eval("("+'gzAccountPlan_gridtable_${random}'+")").func("SetCellProp",rowIndex+" \r\n "+colIndex+" \r\n decimal \\ 2");
								}
							}
						}
						//eval("("+'gzAccountPlan_gridtable_${random}'+")").func("SetColProp", item.itemCode+" \r\n decimal \r\n "+item.scale);
					}
				}
			}
			eval("("+'gzAccountPlan_gridtable_${random}'+")").func("GrayWindow","1"); */
		});
		jQuery("#${random}_filterSpan_hideZeroCell").bind("click",function(){
			var checked = jQuery(this).attr("checked");
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
			jQuery.ajax({
				url: 'gzAccountGridList?accountType=${accountType}&columns='+jQuery("#${random}_gzAccount_columns").val(),
				data: gzAccountFilterLoad("${random}","${gzTypeId}"),
				type: 'post',
				dataType: 'json',
				async:true,
				error: function(data){
					alertMsg.error("系统错误！");
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
					eval("("+'gzAccountPlan_gridtable_${random}'+")").func("setSource", "ds1 \r\n "+JSON.stringify(gzAccountGridData));
					eval("("+'gzAccountPlan_gridtable_${random}'+")").func("calc", "mode=asynch;range=current");
				}
			});
		});
		gzAccountPlanGrid("${random}","${gzAccountDefine.defineId}","${gzTypeId}");
	}); 
	function gzAccountPlanGrid(random,accountType,gzTypeId){
// 		var planId = ${planId};
		jQuery.ajax({
	    	url: 'gzAccountColumnInfo?accountType='+accountType+'&gzTypeId='+gzTypeId,
	        data: {gzAccountItemsStr:jQuery("#"+random+"_gzAccountItemsStr").val()},
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
	        	gzAccountInitTreeSelect(random,orgInit,deptInit,personTypeInit);
	        	var columns ="";
	        	var gzColMap = new Map();
	        	for(var itemIndex in gzItems){
	        		var itemCode = gzItems[itemIndex].itemCode;
	        		gzColMap.put(itemCode,gzItems[itemIndex]);
	        		//columns += "gz."+itemCode+" "+itemCode+",";
	        		columns += itemCode +",";
	    		}
	        	if(gzItems&&gzItems.length>0){
	        		columns = columns.substring(0,columns.length-1);
	        	}
	        	jQuery("#"+random+"_gzAccount_columns").val(columns);
	        	var reportXml = initgzAccountColModel(gzItems,random);
	        	//initGzAccountGridScript(reportXml,columns,gzColMap);
	        	gzAccountGridTableload(reportXml,random,columns,accountType,gzTypeId);
	        }
	    });
	}
	
	function gzAccountGridTableload(reportXml,random,columns,accountType,gzTypeId){
		var deptIds = jQuery("#"+random+"_filter_deptIds_id").val();
		var gzStubsDeptNumber = jQuery("#"+random+"_gzStubsDeptNumber").val();
		gzStubsDeptNumber = +gzStubsDeptNumber;
		if(!gzStubsDeptNumber){
			gzStubsDeptNumber = 10;
		}
		if(deptIds && deptIds.split(",").length <= gzStubsDeptNumber){
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
//	 				jQuery("#"+random+"_gzRowNum").text(gzAccounts.length);
					var gzAccountGridData = {};
					gzAccountGridData.Record = gzAccounts;
					gzAccountPlanDefine.main.SetSource = "ds1 \r\n "+JSON.stringify(gzAccountGridData);
					//gzAccountPlanDefine.main.Fill = "mode=asynch;range=current";
					gzAccountPlanDefine.main.Build = reportXml;
					//gzAccountPlanDefine.main.SeparateView = "1";
					insertReportToDiv(random+"_gzAccountPlan_gridtable_container","gzAccountPlan_gridtable_"+random,"Rebar=None; Border=single;Hue=Lilian;isAnimateFocus=false; PagesTabPercent=0; SeperateBar=none","100%");
				}
			});
		}else{
			gzAccountPlanDefine.main.Build = reportXml;
			//gzAccountPlanDefine.main.SeparateView = "1";
			insertReportToDiv(random+"_gzAccountPlan_gridtable_container","gzAccountPlan_gridtable_"+random,"Rebar=None; Border=single;Hue=Lilian;isAnimateFocus=false; PagesTabPercent=0; SeperateBar=none","100%");
		}
	}
		
	function initgzAccountColModel(data,random){
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
// 		reportXml += '<Font faceName="Verdana" height="-13" weight="400"/>';
// 		reportXml += '<Font faceName="Verdana" height="-13" weight="400"/>';
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
		return gzFilterObj;
	}
	/*期间集合*/
	function gzAccountReverseCols(random){
		var fromperiod = jQuery("#"+random+"_filter_fromPeriodTime").val();
		var toperiod = jQuery("#"+random+"_filter_toPeriodTime").val();
		var colArr = new Array();
		if(fromperiod&&toperiod){
			var fromyear = +fromperiod.substr(0,4);
			var frommonth = +fromperiod.substr(4,2);
			var toyear = +toperiod.substr(0,4);
			var tomonth = +toperiod.substr(4,2);
			if(fromyear == toyear){
				for(var monthi= frommonth;monthi <= tomonth;monthi++){
					if(monthi<10){
						colArr.push(fromyear+"0"+monthi);
					}else{
						colArr.push(fromyear+""+monthi);
					}
				}
			}else if(toyear>fromyear){
				for(var yeari=fromyear;yeari<=toyear;yeari++){
					if(yeari==fromyear){
						for(var monthi= frommonth;monthi <= 12;monthi++){
							if(monthi<10){
								colArr.push(yeari+"0"+monthi);
							}else{
								colArr.push(yeari+""+monthi);
							}
						}
					}else if(yeari==toyear){
						for(var monthi= 1;monthi <= tomonth;monthi++){
							if(monthi<10){
								colArr.push(yeari+"0"+monthi);
							}else{
								colArr.push(yeari+""+monthi);
							}
						}
					}else{
						for(var monthi= 1;monthi <= 12;monthi++){
							if(monthi<10){
								colArr.push(yeari+"0"+monthi);
							}else{
								colArr.push(yeari+""+monthi);
							}
						}
					}
				}
			}
		}
		return colArr;
	}
	//树初始化
	function gzAccountInitTreeSelect(random,orgInit,deptInit,personTypeInit){
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
	}
	/* function gzAccountPlanRulerFontChange(id){
		var grid = eval("("+id+")");
		var rowNum = grid.func("GetRows","");
		if(rowNum){
			for(var rowIndex = 0;rowIndex<rowNum;rowIndex++){
				grid.func("SetRowProp",rowIndex +"\r\n ds \r\n false");
			}
		}
	} */
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
			</ul>
		</div>
		<div style="margin:5px;border-width:2px;border-style: solid ;border-color: #12127D">
		<div style="margin:2px;border-width:1px;border-style: solid ;border-color: #12127D">
		<div id="${random}_gzAccountPlan_searchBar" style="background-color: #DFF1FE;padding-top:10px;padding-bottom:5px;">
			<div align="center">
				<label id="gzAccountTitle_${random}" style="font-size:22px;color:#15428B;font-family:宋体">${gzAccountDefine.defineName }</label>
				<hr id="gzAccountTitle_line1_${random}" style="margin-top:5px;width:130px;color:rgb(24, 127, 207);"/>
				<hr id="gzAccountTitle_line2_${random}" style="width:130px;color:rgb(24, 127, 207);margin-top:-10px"/>
				<script>
					var titleWidth = jQuery("#gzAccountTitle_${random}").width();
					jQuery("#gzAccountTitle_line1_${random}").width(titleWidth+20);
					jQuery("#gzAccountTitle_line2_${random}").width(titleWidth+20);
				</script>
			</div>
			<span id="${random}_filterSpan_1" style="position:relative;top:-20px;left:15px;">
						<label style="padding:0;" >
							<s:text name='工资类别'/>:
						</label>
							<font style="color:red" id="${random}_filter_gzType"></font>
			</span>	
			<div style="overflow: hidden;position:relative;top:-5px;">
				<div>
					<input type="hidden" id="${random}_gzAccount_printSetting">
					<input type="hidden" id="${random}_filter_printer">
					<input type="hidden" id="${random}_filter_paperNumber">
					<input type="hidden" id="${random}_filter_oriantation">
					<input type="hidden" id="${random}_filter_scale">
					<input type="hidden" id="${random}_gzStubsDeptNumber" value="${gzStubsDeptNumber}">
					<input type="hidden" id="${random}_filter_colColumns">
					<input type="hidden" id="${random}_gzAccount_columns">
					<input type="hidden" id="${random}_gzAccount_gzItems">
					<input type="hidden" id="${random}_gzAccountItemsStr" value='<s:property value="gzAccountItemsStr" escapeHtml="false"/>'>
					<input type="hidden" id="${random}_gzAccountFilterStr" value='<s:property value="gzAccountFilterStr" escapeHtml="false"/>'>
				</div>
				<div id="${random}_filterArea_A" style="float:left;margin-left:15px;width: 500px;padding-bottom: 2px;" class="searchContent">
					<span id="${random}_filterSpan_2" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
							<s:text name='人数'/>:
							</label>
						<font id='${random}_gzRowNum' style="color:red"></font>
					</span>
					<span id="${random}_filterSpan_a1" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<span id="${random}_filterSpan_a1_period" style="display:none;width:150px;white-space:nowrap">
							<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
								<s:text name='期间'/>:
							</label>
							<input id="${random}_filter_periodTime" style="width: 80px;" type="text" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}"/>
						</span>
						<span style="display:none;width:150px;white-space:nowrap" id="${random}_filterSpan_a1_year">
								<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
									<s:text name='发放次数'/>:
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
								<s:text name='期间'/>:
								<input id="${random}_filter_fromPeriodTime" style="width: 41px;" type="text" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}"/>
								<s:text name='至'/>:
								<input id="${random}_filter_toPeriodTime" style="width: 41px;" type="text" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}"/>
							</label>&nbsp;&nbsp;
						</span>
						<span style="display: none;" id="${random}_filterSpan_c1_year">
							<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
								<s:text name='发放次数'/>:
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
							<s:text name='部门'/>:
						</label>
						<input  id="${random}_filter_deptIds_id" type="hidden">
						<input  id="${random}_filter_deptIds" type="text" style="width: 80px;">
					</span>
					<span id="${random}_filterSpan_a4" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
							<s:text name='人员类别'/>:
						</label>
						<input type="text"  id="${random}_filter_empTypes" style="width: 80px;">
						<input type="hidden"  id="${random}_filter_empTypes_id">
					</span>
					<span id="${random}_filterSpan_a5" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;">
							<s:text name='人员姓名'/>:
						</label>
						<input  id="${random}_filter_personName" type="text" style="width: 80px;">
					</span>
					<span id="${random}_filterSpan_a6" style="display:none;width:150px;white-space:nowrap;padding-top:2px">
						<label style="width:51px;display:inline-block;text-align:right;padding:0;" >
							<s:text name='人员编码'/>:
						</label>
						<input  id="${random}_filter_personCode" type="text" style="width: 80px;">
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
							<input type="checkbox" id="${random}_filterSpan_hideGzIsNull" />隐藏无工资员工
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