<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var reportDefineReportXml = "";
var budgetReportDefine = {
		key:"${random}_budgetReport_gridtable",
		main:{
			SetSource : '${ctx}/home/supcan/userdefine/datasource.xml',
			//Build : '${ctx}/home/supcan/userdefine/blank.xml',
			Build : 'getBmUpdataReportXml?updataId=${updataId}',
			Load :''
		},
		event:{
			"Load":function( id,p1, p2, p3, p4){
			},
			"Opened":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				grid.func("AddUserFunctions", "${ctx}/home/supcan/userdefine/func.xml");
				//grid.func("SetAutoCalc","0");
				//grid.func("CallFunc","2");
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
			            data :{modelId:'${modelId}',reportXml:reportXml},
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
				
				grid.func("SetUploadXML", "getBmUpdataXml?updataId=${updataId}");
				grid.func("SelectCell", "");
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
 		tabResize();
 		
 		//uploadDesigntime、uploadRuntime
 		insertReportToDiv("${random}_budgetReport_gridtable_container","budgetReport_gridtable_${random}","workmode=uploadRuntime","100%");
 		
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
 		jQuery("#${random}_saveBmReportData").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var updataXml = grid.func("GetUploadXML", "");
			$.ajax({
	            url: 'saveBmUpdata',
	            type: 'post',
	            dataType: 'json',
	            data :{updataId:"${updataId}",updataXml:updataXml},
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
 	
 	function sv(str){
 		var sysVarStr = '${fns:getAllVariableStr()}';
 		var sysVarJson = eval("("+sysVarStr+")");
 		var varIndex = 0;
 		for(var vName in sysVarJson){
 			var vValue = sysVarJson[vName];
 			str = str.replace(vName,vValue);
 		}
 		return str;
 	}
 </script>
 <div class="page" style="height: 100%;">
	<div id="${random}_bmReportContent" class="pageContent" style="height: 100%;">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="${random}_saveBmReportData" class="changebutton"  href="javaScript:"
					><span>保存
					</span>
				</a>
				</li>
				<%-- <li><a id="${random}_submitReport" class="changebutton"  href="javaScript:"
					><span>提交
					</span>
				</a>
				</li> --%>
			</ul>
		</div>
		<div id="${random}_budgetReport_gridtable_container" style="height:100%;overflow: hidden;"></div>
	
	</div> 
 </div>