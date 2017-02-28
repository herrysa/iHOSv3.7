<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var reportDefineReportXml = "";
var budgetReportDefine = {
		key:"${random}_budgetReport_gridtable",
		main:{
			SetSource : 'getBmReportDataSourceXml?modelId=${modelId}',
			//Build : '${ctx}/home/supcan/userdefine/blank.xml',
			Build : 'getBmUpdataReportXml?updataId=${updataId}',
			Load :''
		},
		event:{
			"Load":function( id,p1, p2, p3, p4){
			},
			"Opened":function( id,p1, p2, p3, p4){
				console.log("open");
				if("${reportModel}"!="report"){
					var grid = eval("("+id+")");
					grid.func("AddUserFunctions", "getBmReportFunctionXml");
					//grid.func("SetAutoCalc","0");
					//grid.func("CallFunc","2");
					grid.func("SetBatchFunctionURL","batchFunc \r\n functions=10000;timeout=9999 \r\n user=normal");
					//grid.func("Swkrntpomzqa", "1, 2"); 
					grid.func("SetAutoCalc","10000");
					setTimeout(function(){
						grid.func("CallFunc","64");
						grid.func("CallFunc","163");
					},300);
				}
				
			},
			"Calced":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				var taborderStr = grid.func("GetMemo"," \r\n taborder");
	 			/* if(taborderStr){
	 				console.log(taborderStr);
	 				var taborder = eval("("+taborderStr+")");
	 				for(var i in taborder){
	 					var tab = taborder[i];
	 					var tabArr = i.split("");
	 					//grid.func("addEditAbleOnly", "cell=C4");
	 					grid.func("SetCellProp",tabArr[0]+" \r\n "+tabArr[1]+" \r\n taborder \r\n "+tab);
	 				}
	 				grid.func("RebuildTabOrder","");
	 			} */
	 			/* var deptCol = grid.func("GetMemo"," \r\n deptCol");
				var indexCode = grid.func("GetMemo"," \r\n indexCol");
				var dataCellStr = grid.func("GetMemo"," \r\n dataCell");
	 			var dataCell = {};
	 			if(dataCellStr){
	 				dataCell = eval("("+dataCellStr+")");
	 			} 
	 			var oldDeptCell = grid.func("FindCell", "hasArrow=2"); 
 				var oldDeptCellArr = oldDeptCell.split(",");
 				for(var ci=0;ci<oldDeptCellArr.length;ci++){
 					var cell = oldDeptCellArr[ci];
 					grid.func("SetCellProp",cell+" \r\n Arrow \r\n ");
 					grid.func("SetCellProp",cell+" \r\n hasArrow \r\n ");
 				}
 				var oldIndexCell = grid.func("FindCell", "hasArrow=1"); 
 				var oldIndexCellArr = oldIndexCell.split(",");
 				for(var ci=0;ci<oldIndexCellArr.length;ci++){
 					var cell = oldIndexCellArr[ci];
 					grid.func("SetCellProp",cell+" \r\n Arrow \r\n ");
 					grid.func("SetCellProp",cell+" \r\n hasArrow \r\n ");
 				}
 				for(var i in dataCell){
 					var alias = dataCell[i];
 					var ij = i.split("");
 					grid.func("SetCellProp",ij[0]+" \r\n "+ij[1]+" \r\n Alias \r\n "+alias);
 					grid.func("SetCellProp",ij[0]+" \r\n "+ij[1]+" \r\n isProtected \r\n");
 				} */
 				/* if("${reportModel}"!="report"){
					grid.func("SetUploadXML", "getBmUpdataXml?updataId=${updataId}&modelType=${modelType}");
					grid.func("SelectCell", "");
				} */
			},
			"Toolbar":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				if(p1=="104"){
					grid.func("CancelEvent", "");
				}
			},
			"EditChanged":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				var thisHasArrow = grid.func("GetCellProp",p1+" \r\n "+p2+" \r\n hasArrow");
				if(!thisHasArrow){
					var cols = grid.func("GetCols");
					var assistColStr = grid.func("GetMemo"," \r\n assistColArr");
					var indexColStr = grid.func("GetMemo"," \r\n indexColArr");
					var assistColArr = eval("("+assistColStr+")");
					var indexColArr = eval("("+indexColStr+")");
					var arrowCells = grid.func("FindCell", "hasArrow=1");
					var arrowCellArr = arrowCells.split(",");
					for(var i=0;i<cols;i++){
						var hasArrow = grid.func("GetCellProp",p1+" \r\n "+i+" \r\n hasArrow");
						if(hasArrow==1){
							var cellRow = p1;
							var cellCol = i;
							var alias = "";
							for(var colIndex in assistColArr){
								var col = assistColArr[colIndex];
								if(!col&&col!=0){
									continue;
								}else{
									var assisData = grid.func("getCellData", cellRow+" \r\n "+colIndex);
									alias += assisData+"@";
								}
							}
							var indexCode = indexColArr[cellCol];
							if(!indexCode){
								indexCode = "";
							}
							alias += indexCode;
							//dataCell[cellRow+cellCol] = alias;
							grid.func("SetCellProp",cellRow+" \r\n "+cellCol+" \r\n Alias \r\n "+alias);
							console.log(alias);
						}
					}
				}
			}
		},
		callback:{
			onComplete:function(id){
				console.log("com");
				var grid = eval("("+id+")");
				if("${reportModel}"!="report"){
					var dataColCode = grid.func("GetMemo"," \r\n dataColCode");
					grid.func("SetUploadXML", "getBmUpdataXml?updataId=${updataId}&modelType=${modelType}&dataColCode="+dataColCode);
					grid.func("SelectCell", "");
				}
				if("${reportType}"=="show"||"${reportModel}"=="report"){
					grid.func("Swkrntpomzqa", "1, 2"); 
				}
				if("${modelType}"=="3"){
					
				}
				//grid.func("SubscribeEvent", "SelChanged, EditChanged");　　
			}
		}
	}; 
	
    supcanGridMap.put('budgetReport_gridtable_${random}',budgetReportDefine); 
 	jQuery(document).ready(function(){
 		//uploadDesigntime、uploadRuntime
 		if("${reportModel}"!="report"&&"${reportType}"!="show"){
 			insertReportToDiv("${random}_budgetReport_gridtable_container","budgetReport_gridtable_${random}","workmode=uploadRuntime;Rebar=Print","99%");
 		}else{
 			insertReportToDiv("${random}_budgetReport_gridtable_container","budgetReport_gridtable_${random}","Rebar=Print","99%");
 		}
 		var grid = eval("(budgetReport_gridtable_${random})");
 		jQuery("#${random}_freshReportData").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			grid.func("SetAutoCalc","10000");
			grid.func("CallFunc","64");
			grid.func("CallFunc","163");
 		});
 		jQuery("#${random}_reBm").click(function(){
 			if(!"${xfId}"){
 				alertMsg.error("当前没有上报中的预算！");
 				return ;
 			}
 			
 	    	alertMsg.confirm("确认驳回预算？驳回后,之前填报的数据将作废！", {
 				okCall: function(){
 					jQuery.post("budgetModel_Xf", {
 						"_" : $.now(),xfId:"${xfId}",xfType:'2',msg:'驳回预算',navTabId:'budgetModelXf_gridtable'
 					}, function(data) {
 						formCallBack(data);
 					},"json");
 					
 				}
 			});
 		});
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
 			var assistColStr = grid.func("GetMemo"," \r\n assistColArr");
			var indexColStr = grid.func("GetMemo"," \r\n indexColArr");
			var assistColArr = new Array();
			if(assistColStr){
				assistColArr = eval("("+assistColStr+")");
			}
			var indexColArr = new Array();
			if(indexColStr){
				indexColArr = eval("("+indexColStr+")");
			}
 			var arrowCells = grid.func("FindCell", "hasArrow=1");
 			var arrowCellArr = arrowCells.split(",");
 			var aliasArr = new Array();
 			for(var i in arrowCellArr){
 				var arrowCell = arrowCellArr[i];
 				var cellRow = grid.func("GetCellRow",arrowCell);
 				var cellCol = grid.func("GetCellCol",arrowCell);
 				var alias = "";
 				for(var colIndex in assistColArr){
 					var col = assistColArr[colIndex];
 					if(!col&&col!=0){
 						continue;
 					}else{
 						var assisData = grid.func("getCellData", cellRow+" \r\n "+colIndex);
 						alias += assisData+"@";
 					}
 				}
 				var indexCode = indexColArr[cellCol];
 				if(!indexCode){
 					indexCode = "";
 				}
 				alias += indexCode;
 				if(aliasArr.indexOf(alias)!=-1){
 					cellRow = parseInt(cellRow);
 					alertMsg.error("第"+(cellRow+1)+"行有重复记录，请检查！");
 					return ;
 				}
 				aliasArr.push(alias);
 				grid.func("SetCellProp",cellRow+" \r\n "+cellCol+" \r\n Alias \r\n "+alias);
 			}
 			var uploadCells = grid.func("GetUploadCells");
 			var uploadCellArr = uploadCells.split(",");
 			var taborderBack = {};
 			for(var i in uploadCellArr){
 				var uploadCell = uploadCellArr[i];
 				var hasArrow = grid.func("GetCellProp",uploadCell+" \r\n hasArrow");
 				if(hasArrow!=1){
 					var taborder = grid.func("GetCellProp",uploadCell+" \r\n taborder");
 					taborderBack[uploadCell] = taborder;
 					grid.func("SetCellProp",uploadCell+" \r\n taborder \r\n 0");
 				}
 			}
 			var updataXml = grid.func("GetUploadXML", "");
 			for(var uploadCell in taborderBack){
 				var taborder = taborderBack[uploadCell];
 				grid.func("SetCellProp",uploadCell+" \r\n taborder \r\n "+taborder);
 			}
 			var modelChanged = grid.func("GetMemo"," \r\n modelChanged");
 			var reportXml = "";
 			/* if(modelChanged==1){
 			}else if(modelChanged==2){
 				reportXml = 'null';
 			} */
	 			reportXml = grid.func("GetFileXML", "isSaveCalculateResult=true");
 			var dataColCode = grid.func("GetMemo"," \r\n dataColCode");
			$.ajax({
	            url: 'saveBmUpdata',
	            type: 'post',
	            dataType: 'json',
	            data :{updataId:"${updataId}",updataXml:updataXml,reportXml:reportXml,dataColCode:dataColCode,modelType:"${modelType}"},
	            async:false,
	            error: function(data){
	            alertMsg.error("系统错误！");
	            },
	            success: function(data){
	            	formCallBack(data);
	            }
	        });
 		});
 		jQuery("#${random}_getLastBmReportData").click(function(){
 			alertMsg.confirm("确认引入？引入后，当前编辑的数据将被覆盖！", {
 				okCall: function(){
 					var grid = eval("(budgetReport_gridtable_${random})");
 					var dataColCode = grid.func("GetMemo"," \r\n dataColCode");
	 				grid.func("SetUploadXML", "getBmUpdataXml?updataId=${updataId}&reportType=lastUpdata&dataColCode="+dataColCode);
 				}
 			});
 		});
 		jQuery("#${random}_saveHzBmReportXml").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var cells = grid.func("FindCell", "left(formula,1)='='"); 
 			var cellArr = cells.split(",");
 			for(var i in cellArr){
 				var cell = cellArr[i];
 				var cellTxt = grid.func("GetCellText", cell+"");
 				grid.func("SetCellData", cell+"\r\n");
 				grid.func("SetCellData", cell+"\r\n"+cellTxt);
 			}
 			var rows = grid.func("GetRows","");
 			for(var row=0;row<rows;row++){
 				var isds = grid.func("GetRowProp",row+"\r\nds");
 				if(isds==1){
 					grid.func("SetRowProp",row+"\r\nds\r\n0");
 				}
 			}
 			var fileXml = grid.func("GetFileXML", "isSaveCalculateResult=true"); 
 			$.ajax({
	            url: 'saveBmUpdataXml',
	            type: 'post',
	            dataType: 'json',
	            data :{updataId:"${updataId}",modelType:"${modelType}",updataXml:fileXml,navTabId:'${random}_budgetModelHz_gridtable'},
	            async:false,
	            error: function(data){
	            alertMsg.error("系统错误！");
	            },
	            success: function(data){
	            	formCallBack(data);
	            }
	        });
 		});
 		jQuery("#${random}_saveZnBmReportXml").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var cells = grid.func("FindCell", "left(formula,1)='='"); 
 			var cellArr = cells.split(",");
 			for(var i in cellArr){
 				var cell = cellArr[i];
 				var cellTxt = grid.func("GetCellText", cell+"");
 				grid.func("SetCellData", cell+"\r\n");
 				grid.func("SetCellData", cell+"\r\n"+cellTxt);
 			}
 			var rows = grid.func("GetRows","");
 			for(var row=0;row<rows;row++){
 				var isds = grid.func("GetRowProp",row+"\r\nds");
 				if(isds==1){
 					grid.func("SetRowProp",row+"\r\nds\r\n0");
 				}
 			}
 			var fileXml = grid.func("GetFileXML", "isSaveCalculateResult=true"); 
 			$.ajax({
	            url: 'saveBmUpdataXml',
	            type: 'post',
	            dataType: 'json',
	            data :{updataId:"${updataId}",modelType:"${modelType}",updataXml:fileXml,navTabId:'${random}_budgetModelZn_gridtable'},
	            async:false,
	            error: function(data){
	            alertMsg.error("系统错误！");
	            },
	            success: function(data){
	            	formCallBack(data);
	            }
	        });
 		});
 		jQuery("#${random}_copyBmReportRow1").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var currentCells = grid.func("GetCurrentCells");
 			var currentCellArr = currentCells.split(":");
 			var currentRow = grid.func("GetCellRow",currentCellArr[0]);
 			grid.func("SelectCell", "A1 \r\n \r\n -1"); 
 		});
 		jQuery("#${random}_copyBmReportRow").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			grid.func("SetMemo"," \r\n modelChanged \r\n 1");
 			var cols = grid.func("GetCols");
 			var currentCells = grid.func("GetCurrentCells");
 			var currentCellArr = currentCells.split(":");
 			var currentRow = grid.func("GetCellRow",currentCellArr[0]);
 			currentRow = parseInt(currentRow);
 			//var firstCellArr = currentCellArr[0].split("");
 			grid.func("SelectCell", currentRow+" \r\n A \r\n "+currentRow+" \r\n -1");
 			var maxColNum = grid.func("GetCellCol",currentCellArr[1]);
 			var hasArrowArr = new Array();
 			var maxTaborder = 0;
 			for(var i=0;i<maxColNum;i++){
 				var hasArrow = grid.func("GetCellProp",currentRow+" \r\n "+i+" \r\n hasArrow");
 				if(maxTaborder>0){
	 				maxTaborder = grid.func("GetCellProp",currentRow+" \r\n "+i+" \r\n taborder");
 				}
 				if(hasArrow==1){
	 				hasArrowArr[i] = 1;
 				}
 			}
 			maxTaborder = parseInt(maxTaborder);
 			var maxColArr = currentCellArr[1].split("");
 			grid.func("SetProp","WorkMode=uploadDesigntime");
 			grid.func("CallFunc","20");
 			grid.func("InsertRows",currentRow+" \r\n 1 \r\n 0");
 			grid.func("SelectCell", "A"+(currentRow+2)+"  \r\n "+maxColArr[0]+(currentRow+2)); 
 			grid.func("CallFunc","34");
 			for(var i=0;i<maxColNum;i++){
 				var hasArrow = hasArrowArr[i];
 				var thisTaborder = grid.func("GetCellProp",(currentRow+1)+" \r\n "+i+" \r\n taborder");
 				if(thisTaborder>0){
 					maxTaborder += 10;
 					grid.func("SetCellProp",(currentRow+1)+" \r\n "+i+" \r\n taborder \r\n "+maxTaborder);
 				}
 				if(hasArrow==1){
 					grid.func("SetCellProp",(currentRow+1)+" \r\n "+i+" \r\n hasArrow \r\n 1");
 				}
 			}
 			grid.func("SetProp","WorkMode=uploadRuntime");
 			grid.func("RebuildTabOrder");
 			var fileXml = grid.func("GetFileXML", "isSaveCalculateResult=true"); 
 			//console.log(fileXml);
 			//grid.func("CloneArea", "A5:D5 \r\n row=9");

 		});
 		jQuery("#${random}_delBmReportRow").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var currentCells = grid.func("GetCurrentCells");
 			var currentCellArr = currentCells.split(":");
 			var currentRow = grid.func("GetCellRow",currentCellArr[0]);
 			grid.func("DeleteRows", currentRow+" \r\n 1");
 		});
 		jQuery("#${random}_initBmReport").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			grid.func("SetMemo"," \r\n modelChanged \r\n 2");
 			grid.func("Build",'getBmUpdataReportXml?updataId=${updataId}&reportType=init');
 			 
 		});
 		jQuery("#${random}_submitReport").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var sss = grid.func("GetUploadXML");
 			console.log(sss);
 		});
 		jQuery("#${random}_userdefine").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			grid.func("SetProp","WorkMode \r\n uploadDesigntime");
 			var a = grid.func("GetProp","WorkMode");
 			alert(a);
 		});
 	});
 	
 	function sv(str){
 		var sysVarStr = '${fns:getAllVariableStr()}';
 		var sysVarJson = eval("("+sysVarStr+")");
 		if("${budgetUpdata.updataId}"){
 			sysVarJson['%BMMODEL_DEPTCODE%'] = "${budgetUpdata.department.deptCode}";
 			sysVarJson['%BMMODEL_DEPTNAME%'] = "${budgetUpdata.department.name}";
 			sysVarJson['%BMMODEL_MODELCODE%'] = "${budgetUpdata.modelXfId.modelId.modelCode}";
 			sysVarJson['%BMMODEL_MODELNAME%'] = "${budgetUpdata.modelXfId.modelId.modelName}";
 			sysVarJson['%BMMODEL_PERIODTYPE%'] = "${budgetUpdata.modelXfId.modelId.periodType}";
 			<c:forEach items="${processLogs}" var="pl">
 			sysVarJson['${pl.label_name}'] = '${pl.label_value}';
 			sysVarJson['${pl.person_name}'] = '${pl.person_value}';
 			sysVarJson['${pl.optDate_name}'] = '${pl.optDate_value}';
 			</c:forEach>
 		}
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
				<s:if test="reportType!='show'">
					<s:if test="reportModel!='report'">
						<li><a id="${random}_saveBmReportData" class="savebutton" href="javaScript:"><span>保存</span></a></li>
					</s:if>
					<s:else>
						<li><a class="savebutton" style='color:#808080;' href="javaScript:"><span>保存</span></a></li>
					</s:else>
					<s:if test="assistType!=null&&assistType!=''">
					<s:if test="reportModel!='report'">
						<li><a id="${random}_copyBmReportRow" class="savebutton" href="javaScript:"><span>复制行</span></a></li>
					</s:if>
					<s:else>
						<li><a class="savebutton" style='color:#808080;' href="javaScript:"><span>复制行</span></a></li>
					</s:else>
					<s:if test="reportModel!='report'">
						<li><a id="${random}_delBmReportRow" class="delbutton" href="javaScript:"><span>删除行</span></a></li>
					</s:if>
					<s:else>
						<li><a class="delbutton" style='color:#808080;' href="javaScript:"><span>删除行</span></a></li>
					</s:else>
					<s:if test="reportModel!='report'">
						<li><a id="${random}_initBmReport" class="initbutton" href="javaScript:"><span>恢复初始状态</span></a></li>
					</s:if>
					<s:else>
						<li><a class="delbutton" style='color:#808080;' href="javaScript:"><span>恢复初始状态</span></a></li>
					</s:else>
					</s:if>
					<s:if test="reportModel!='report'">
						<li><a id="${random}_getLastBmReportData" class="importbutton"  href="javaScript:"><span>引入上次填报数据</span></a></li>
					</s:if>
					<s:else>
						<li><a class="importbutton" style='color:#808080;' href="javaScript:"><span>引入上次填报数据</span></a></li>
					</s:else>
				</s:if>
				<s:if test="reportType=='show'">
				<li><a id="${random}_freshReportData" class="previewbutton"  href="javaScript:"><span>刷新</span></a></li>
				</s:if>
				<s:if test="modelType==2">
				<%-- <li><a id="${random}_userdefine" class="editbutton"  href="javaScript:"><span>编辑汇总表</span></a>
				</li> --%>
				<li><a id="${random}_reBm" class="delallbutton"  href="javaScript:"><span>驳回预算</span></a>
				</li>
					<s:if test="reportType!='show'&&reportModel!='report'">
						<li><a id="${random}_saveHzBmReportXml" class="closebutton"  href="javaScript:"><span>完成汇总</span></a></li>
					</s:if>
					<%-- <s:else>
						<li><a class="closebutton" style='color:#808080;' href="javaScript:"><span>完成汇总</span></a></li>
					</s:else> --%>
				</s:if>
				<s:if test="modelType==3">
					<s:if test="reportType!='show'&&reportModel!='report'">
						<li><a id="${random}_saveZnBmReportXml" class="closebutton"  href="javaScript:"><span>完成上报</span></a></li>
					</s:if>
					<%-- <s:else>
						<li><a class="closebutton" style='color:#808080;' href="javaScript:"><span>完成上报</span></a></li>
					</s:else> --%>
				</s:if>
				<%-- <li><a id="${random}_submitReport" class="changebutton"  href="javaScript:"
					><span>提交
					</span>
				</a>
				</li> --%>
			</ul>
		</div>
		<div id="${random}_budgetReport_gridtable_container" layoutH=30 style="overflow: hidden;"></div>
	
	</div> 
 </div>