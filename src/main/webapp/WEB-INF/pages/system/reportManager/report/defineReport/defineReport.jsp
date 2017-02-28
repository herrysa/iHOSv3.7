<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var reportDefineCode = "${code}";
var reportDefineReportXml = "";
var reportPlanDefine = {
		key:"${random}_reportPlan_gridtable",
		main:{
			SetSource : 'getReportDataSourceXml?code=${code}',
			//Build : '${ctx}/home/supcan/userdefine/blank.xml',
			Build : 'getDefineReportXml?code=${code}',
			Load :''
		},
		event:{
			"Load":function( id,p1, p2, p3, p4){
			},
			"Opened":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				grid.func("AddUserFunctions", "getReportFunctionXml?code=${code}");
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
			            url: 'saveDefineReportXml',
			            type: 'post',
			            dataType: 'json',
			            data :{code:reportDefineCode,reportXml:reportXml},
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
			
		}
	}; 
	
    supcanGridMap.put('reportPlan_gridtable_${random}',reportPlanDefine); 
 	jQuery(document).ready(function(){
 		//reportPlanDefine.main.Build = initreportColModel();
 		//alert(reportPlanDefine.main.Build);
 		//tabResize();
 		insertReportToDiv("${random}_reportPlan_gridtable_container","reportPlan_gridtable_${random}","","99%");
 		var grid = eval("(reportPlan_gridtable_${random})");
 		jQuery("#${random}_editReport").click(function(){
 			grid.func("SetAutoCalc","0");
			grid.func("CallFunc","2");
			grid.func("CallFunc","143");
 		});
 		jQuery("#${random}_showReport").click(function(){
 			grid.func("SetAutoCalc","10000");
			grid.func("CallFunc","64");
			grid.func("CallFunc","163");
 		});
 		jQuery("#${random}_saveReport").click(function(){
 			var reportXml = grid.func("GetFileXML", "");
			$.ajax({
	            url: 'saveDefineReportXml',
	            type: 'post',
	            dataType: 'json',
	            data :{code:reportDefineCode,reportXml:reportXml},
	            async:false,
	            error: function(data){
	            	alertMsg.error("系统错误！");
	            },
	            success: function(data){
	            	formCallBack(data);
	            }
	        });
 		});
 	});

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
	<div id="defineReportContent" class="pageContent" style="height: 100%;">
		<div class="panelBar">
			<ul class="toolBar"> 
				<li><a id="${random}_editReport" class="reportbutton"  href="javaScript:"
					><span>编辑报表
					</span>
				</a>
				</li>
				<li><a id="${random}_showReport" class="previewbutton"  href="javaScript:"
					><span>预览
					</span>
				</a>
				</li>
				<li><a id="${random}_saveReport" class="savebutton"  href="javaScript:"
					><span>保存
					</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="${random}_reportPlan_gridtable_container" layoutH=28></div>
	</div> 
 </div>