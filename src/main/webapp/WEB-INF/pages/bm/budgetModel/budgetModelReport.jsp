<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var reportDefineReportXml = "";
var budgetReportDefine = {
		key:"${random}_budgetReport_gridtable",
		main:{
			SetSource : '${ctx}/home/supcan/userdefine/datasource.xml',
			//Build : '${ctx}/home/supcan/userdefine/blank.xml',
			Build : 'getBmReportXml?modelId=${modelId}',
			Load :''
		},
		event:{
			"Load":function( id,p1, p2, p3, p4){
			},
			"Opened":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				grid.func("AddUserFunctions", "${ctx}/home/supcan/userdefine/func.xml");
				grid.func("SetAutoCalc","0");
				grid.func("CallFunc","2");
				grid.func("SetBatchFunctionURL","batchFunc \r\n functions=10000;timeout=9999 \r\n user=normal");
			},
			"Toolbar":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				if(p1=="104"){
					var reportXml = grid.func("GetFileXML", "");
					console.log(reportXml);
					$.ajax({
			            url: 'saveBmReportXml',
			            type: 'post',
			            dataType: 'json',
			            data :{modelId:${modelId},reportXml:reportXml},
			            async:false,
			            error: function(data){
			            alertMsg.error("系统错误！");
			            },
			            success: function(data){
			            	formCallBack(data);
			            }
			        });
					grid.func("CancelEvent", "");
				}
			}
		},
		callback:{
			onComplete:function(id){
				var grid = eval("("+id+")");
				
				grid.func("SetItemLibraryURL", "getBmReportIndexXml");
			}
		}
	}; 
	if(reportDefineReportXml){
		reportDefineReportXml = reportDefineReportXml.replaceAll("&#034;","\"");
		reportDefineReportXml = reportDefineReportXml.replaceAll("&lt;","<");
		reportDefineReportXml = reportDefineReportXml.replaceAll("&gt;",">");
		//alert(reportDefineReportXml);
		//reportPlanDefine.main.Build = reportDefineReportXml;
	}
	
    supcanGridMap.put('budgetReport_gridtable_${random}',budgetReportDefine); 
 	jQuery(document).ready(function(){
 		//reportPlanDefine.main.Build = initreportColModel();
 		//alert(reportPlanDefine.main.Build);
 		var bmReportFullSize = jQuery("#${random}_bmReportContent").innerHeight()-28;
 		jQuery("#${random}_budgetReport_container").css("height",bmReportFullSize);
		var bmReportLayout = $('#${random}_budgetReport_container').layout({ 
			applyDefaultStyles: false ,
			west__size : 290,
			east__size : 290,
			spacing_open:5,//边框的间隙  
			spacing_closed:5,//关闭时边框的间隙 
			resizable :true,
			resizerClass :"ui-layout-resizer-blue",
			slidable :true,
			resizerDragOpacity :1, 
			resizerTip:"可调整大小",
			onresize_end : function(paneName,element,state,options,layoutName){
				if("center" == paneName){
					//gridResize(null,null,gridtable,"single");
				}
			}
		});
 		tabResize();
 		
 		//uploadDesigntime、uploadRuntime
 		insertReportToDiv("${random}_budgetReport_gridtable_container","budgetReport_gridtable_${random}","workmode=uploadDesigntime","100%");
 		
 		var grid = eval("(budgetReport_gridtable_${random})");
 		var ztreesetting_bmReport = {
				view : {
					dblClickExpand : false,
					showLine : true,
					selectedMulti : false
				},
				callback : {
					onClick : selectIndexToCell
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
 		var cellIndexMap = {};
 		function selectIndexToCell(event, treeId, treeNode, clickFlag) { 
			var grid = eval("(budgetReport_gridtable_${random})");
			var subIndexs = treeNode.children;
			if(subIndexs!=null&&subIndexs.length!=0){
				for(var i=0;i<subIndexs.length;i++){
					var childNode = subIndexs[i];
					var cell = grid.func("GetCurrentCell",""+i);
					grid.func("SetCellData",cell+" \r\n{index."+childNode.name+"}");
					cellIndexMap[cell] = childNode.id;
				}
			}else{
				var cell = grid.func("GetCurrentCell",""+0);
				grid.func("SetCellData",cell+" \r\n{index."+treeNode.name+"}");
				cellIndexMap[cell] = treeNode.id;
			}
			
 		}
 		$.get("getBudgetIndexTree", {
			"_" : $.now()
		}, function(data) {
			var budgetIndexTreeData = data.budgetIndexTreeNodes;
			var budgetIndexTree = $.fn.zTree.init($("#bmReportIndexTree"),
					ztreesetting_bmReport, budgetIndexTreeData);
			var nodes = budgetIndexTree.getNodes();
			budgetIndexTree.expandNode(nodes[0], true, false, true);
			budgetIndexTree.selectNode(nodes[0]);
			
		});
 		setTimeout(function(){
 		//grid.func("build",reportDefineReportXml);
 			//jQuery("#${random}_budgetReport_gridtable_container").css("overflow","hidden");
 		},200);
 		
 		jQuery("#${random}_editBudgetModelReport").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 	 		grid.func("SetAutoCalc","0");
 			grid.func("CallFunc","2");
 			grid.func("CallFunc","143");
 		});
 		var cellVarMap = {};
 		jQuery("#${random}_showBudgetModelReport").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var rows = grid.func("GetRows");
 			var cols = grid.func("GetCols");
 			for(var row=0;row<rows;row++){
 				for(var col=0;col<cols;col++){
 					//var cell = grid.func("GetCellName",row+"\r\n"+col);
 					var cellData = grid.func("getCellData",row+"\r\n"+col);
 					if(cellData.indexOf("{VAR.")!=-1){
 						var bmrSysVarTree = $.fn.zTree.getZTreeObj("${random}_bmrSysVarTree");
 						var pattern = new RegExp("\\{(.| )+?\\}","igm");
 						var matchStrArr = cellData.match(pattern);
 						for(var strIndex=0;strIndex<matchStrArr.length;strIndex++){
 							var matchStr = matchStrArr[strIndex];
 							var s = matchStr.replace("{VAR.","");
 							s = s.replace("}","");
 							var sysVarNode = bmrSysVarTree.getNodeByParam("name",s,null);
 							var vv = sysVarNode.value;
 							cellData = cellData.replace(matchStr,vv);
 						}
						grid.func("SetCellData",row+"\r\n"+col+"\r\n"+cellData);
 						
 					}
					if(cellData.indexOf("{INDEX.")!=-1){
						grid.func("SetCellData",row+"\r\n"+col+"\r\n");
 					}
 				}
 			}
 			grid.func("SetAutoCalc","10000");
			grid.func("CallFunc","64");
			grid.func("CallFunc","163");
 		});
 		jQuery("#${random}_selectIndexToCell").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			grid.func("GrayWindow",'1 \r\n 255');
 		});
 		jQuery("#${random}_saveBudgetModelReport").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var reportXml = grid.func("GetFileXML", "");
 			var cellIndexStr = JSON.stringify(cellIndexMap);
			$.ajax({
	            url: 'saveBmReportXml',
	            type: 'post',
	            dataType: 'json',
	            data :{modelId:"${modelId}",cellIndex:cellIndexStr,reportXml:reportXml},
	            async:false,
	            error: function(data){
	            alertMsg.error("系统错误！");
	            },
	            success: function(data){
	            	formCallBack(data);
	            }
	        });
 		});
 		jQuery("#${random}_submitReport").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var sss = grid.func("GetUploadXML");
 			console.log(sss);
 		});
 	});
 	function sourcepayinSum1(checkperiod1,checkperiod2,deptId,chargeType){
 		//return checkperiod1;
 		console.log(chargeType);
 		var sum;
 		var sql = "select sum(amount) from v_sourcepayin where checkPeriod BETWEEN '"+checkperiod1+"' and '"+checkperiod2+"' and kdDeptId='"+deptId+"'";
 		$.ajax({
            url: 'getBySql?sql='+sql,
            type: 'post',
            dataType: 'json',
            async:false,
            error: function(data){
            alertMsg.error("系统错误！");
            },
            success: function(data){
            	console.log(data.sqlResult);
            	sum = data.sqlResult;
            }
        });
 		
 		return sum;
 	}
 	var batchCell = {time:0,pretreatment:0,cellLength:0,cellNum:0,over:0,rs:{},
 		start:function(){
 			debugger;
	 		batchCell.timer=setInterval(function(){
	 			console.log(batchCell.pretreatment);
	 			if(batchCell.pretreatment=3){
	 				batchCell.doAjax();
	 				clearInterval(batchCell.timer);
	 			}else{
	 				if(batchCell.cellLength>batchCell.cellNum){
	 					batchCell.cellNum = batchCell.cell.length;
	 				}else{
	 					batchCell.pretreatment++;
	 				}
	 			}
	 		},0);
 		},
 		doAjax:function(){
 			console.log("a");
 			$.ajax({
 	            url: 'getListBySql?sql='+batchCell.sql,
 	            type: 'post',
 	            dataType: 'json',
 	            async:false,
 	            error: function(data){
 	            alertMsg.error("系统错误！");
 	            },
 	            success: function(data){
 	            	console.log(this.pretreatment);
 	            	var grid = eval("(budgetReport_gridtable_${random})");
 	            	var sqlMap = data.sqlMap;
 	            	if(sqlMap){
 	            		for(var i in sqlMap){
 	            			var rs = sqlMap[i];
 	            			batchCell.rs[rs.k] = rs.v;
 	            		}
 	            		batchCell.over = 1;
 	            	}
 	            }
 	        });
 		}
 	};
 	
 	function xxx(){
 		
 	}
 	
 	function batchSourcepayinSum(key,checkperiod1,checkperiod2,chargeType){
 		var sql = "select kdDeptId k,sum(amount) v from v_sourcepayin where checkPeriod BETWEEN '"+checkperiod1+"' and '"+checkperiod2+"' GROUP BY kdDeptId";
 		batchCell.pretreatment = 1;
 		batchCell.sql = sql;
 		if(batchCell.over==0){
 			batchCell.doAjax();
 		}
 		var sum;
 		debugger;
 		while(true){
 			console.log("w");
 			if(batchCell.over==1){
 				var value = batchCell.rs[key];
 				if(value){
 					return value;
 				}else{
 					return "";
 				}
 			}
 		}
 	}
 	function ssss(){
 		var grid = eval("(budgetReport_gridtable_${random})");
 		grid.func("SetCellData","D2\r\n1111");
 	}
 	
 	function batchFunc(){
 		var grid = eval("(budgetReport_gridtable_${random})");
 		grid.func("SetBatchFunctionURL","batchFunc \r\n functions=50;timeout=9999 \r\n user=normal");
 	}
 </script>
 <div class="page" style="height: 100%;">
	<div id="${random}_bmReportContent" class="pageContent" style="height: 100%;">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="${random}_editBudgetModelReport" class="changebutton"  href="javaScript:"
					><span>编辑报表
					</span>
				</a>
				</li>
				<li><a id="${random}_showBudgetModelReport" class="changebutton"  href="javaScript:"
					><span>预览
					</span>
				</a>
				</li>
				<li><a id="${random}_submitReport" class="changebutton"  href="javaScript:"
					><span>提交
					</span>
				</a>
				</li>
				<li><a id="${random}_saveBudgetModelReport" class="changebutton"  href="javaScript:"
					><span>保存
					</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="${random}_budgetReport_container">
			<div id="${random}_budgetReport_layout-center" class="pane ui-layout-center" style="display: block; overflow: hidden;">
				<div id="${random}_budgetReport_gridtable_container" style="height:100%;overflow: hidden;"></div>
			</div>
			<div id="${random}_budgetReport_layout-east" class="pane ui-layout-east" style="padding:0px;display: block; overflow: hidden;">
				
				<div class="tabs" currentIndex="0" eventType="click" id="${random}_bmRightTabs" tabcontainer="${random}_budgetReport_layout-east" extraHeight=0 extraWidth=10>
						<div class="tabsHeader">
							<div class="tabsHeaderContent">
								<ul>
									<li><a href="javascript:;"><span>系统变量</span> </a></li>
									<li><a href="javascript:;"><span>预算指标</span> </a></li>
								</ul>
							</div>
						</div>
						<div id="${random}_bmRightTabsContent" class="tabsContent"
							style="height: 250px;">
							<div id="${random}_systemVarTree" style="padding:5px">
								<%-- <a style="position: relative; float: right;top:5px" id="${random}_bmReportSysVar_expandTree" href="javaScript:">展开</a> --%>
								<div style="marigin:10px">查询：<input /></div>
								<script>
									/* jQuery("#${random}_bmReportSysVar_expandTree").click(function(){
										var thisText = jQuery(this).text();
										var budgetSysVarTee = $.fn.zTree.getZTreeObj("${random}_bmrSysVarTree");
										if(thisText=="展开"){
											budgetSysVarTee.expandAll(true);
											jQuery(this).text("折叠");
										}else{
											budgetSysVarTee.expandAll(false);
											jQuery(this).text("展开");
										}
									}); */
									var setting_bmSysVar = {
											view : {
												dblClickExpand : false,
												showLine : true,
												selectedMulti : false
											},
											callback : {
												onClick : selectVarToCell
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
							 		function selectVarToCell(event, treeId, treeNode, clickFlag) { 
										var grid = eval("(budgetReport_gridtable_${random})");
										var subIndexs = treeNode.children;
										if(subIndexs!=null&&subIndexs.length!=0){
											for(var i=0;i<subIndexs.length;i++){
												var childNode = subIndexs[i];
												var cell = grid.func("GetCurrentCell",""+i);
												grid.func("SetCellData",cell+" \r\n{VAR."+childNode.name+"}");
											}
										}else{
											var cell = grid.func("GetCurrentCell",""+0);
											grid.func("SetCellData",cell+" \r\n{VAR."+treeNode.name+"}");
										}
										
							 		}
							 		var bmrSysvarTree = $.fn.zTree.init($("#${random}_bmrSysVarTree"),setting_bmSysVar);
							 		var sysVarStr = '${fns:getAllVariableStr()}';
							 		var sysVarJson = eval("("+sysVarStr+")");
							 		var varIndex = 0;
							 		for(var vName in sysVarJson){
							 			var vValue = sysVarJson[vName];
							 			var svNode = {id:varIndex,name:vName,value:vValue};
							 			bmrSysvarTree.addNodes(null, svNode);
							 		}
							 		/* $.get("getSystemTree", {
										"_" : $.now()
									}, function(data) {
										var budgetIndexTreeData = data.budgetIndexTreeNodes;
										var budgetIndexTree = $.fn.zTree.init($("#bmReportIndexTree"),
												ztreesetting_bmReport, budgetIndexTreeData);
										var nodes = budgetIndexTree.getNodes();
										budgetIndexTree.expandNode(nodes[0], true, false, true);
										budgetIndexTree.selectNode(nodes[0]);
										
									}); */
								</script>
								<div id="${random}_bmrSysVarTree" class="ztree"></div>
							</div>
							<div id="${random}_bmIndexTree">
								<a style="position: relative; float: right;top:5px" id="bmReportIndex_expandTree" href="javaScript:">展开</a>
								<script>
									jQuery("#bmReportIndex_expandTree").click(function(){
										var thisText = jQuery(this).text();
										var budgetIndexTee = $.fn.zTree.getZTreeObj("bmReportIndexTree");
										if(thisText=="展开"){
											budgetIndexTee.expandAll(true);
											jQuery(this).text("折叠");
										}else{
											budgetIndexTee.expandAll(false);
											jQuery(this).text("展开");
										}
									});
								</script>
								<div id="bmReportIndexTree" class="ztree"></div>
							</div>
						</div>
						<div class="tabsFooter">
							<div class="tabsFooterContent"></div>
						</div>
					</div>
			</div>
		</div>
	
	</div> 
 </div>