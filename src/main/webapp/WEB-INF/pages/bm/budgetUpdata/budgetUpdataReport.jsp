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
			},
			"Toolbar":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				if(p1=="104"){
					grid.func("CancelEvent", "");
				}
			}
		},
		callback:{
			onComplete:function(id){
				var grid = eval("("+id+")");
				
				grid.func("SetUploadXML", "getBmUpdataXml?updataId=${updataId}");
				grid.func("SelectCell", "");
				if("${reportType}"=="show"){
					grid.func("Swkrntpomzqa", "1, 2"); 
				}
			}
		}
	}; 
	
    supcanGridMap.put('budgetReport_gridtable_${random}',budgetReportDefine); 
 	jQuery(document).ready(function(){
 		//uploadDesigntime、uploadRuntime
 		insertReportToDiv("${random}_budgetReport_gridtable_container","budgetReport_gridtable_${random}","workmode=uploadRuntime","99%");
 		
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
 						"_" : $.now(),xfId:"${xfId}",xfType:'2',navTabId:'budgetModelXf_gridtable'
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
 			sysVarJson['%BMMODEL_MODELTYPE%'] = "${budgetUpdata.modelXfId.modelId.modelTypeTxt}";
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
				<li><a id="${random}_saveBmReportData" class="submitbutton"  href="javaScript:"
					><span>保存
					</span>
				</a>
				</li>
				<%-- <li><a id="${random}_saveBmReportData" class="submitbutton"  href="javaScript:"
					><span>引入上该模板次填报数据
					</span>
				</a>
				</li> --%>
				</s:if>
				<li><a id="${random}_freshReportData" class="previewbutton"  href="javaScript:"
					><span>刷新
					</span>
				</a>
				</li>
				<s:if test="reportType==1">
				<%-- <li><a id="${random}_userdefine" class="editbutton"  href="javaScript:"><span>编辑汇总表</span></a>
				</li> --%>
				<li><a id="${random}_reBm" class="delallbutton"  href="javaScript:"><span>驳回预算</span></a>
				</li>
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